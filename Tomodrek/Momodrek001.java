package Tomodrek;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.struct.Seq;

import arc.util.Log;
import arc.util.Timer;
import arc.util.serialization.JsonReader;
import arc.util.serialization.JsonValue;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import mindustry.mod.Plugin;

import arc.util.CommandHandler;
import mindustry.net.Administration;
import mindustry.game.EventType.*;
import mindustry.net.NetConnection;
import mindustry.ui.Menus;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

//ДА ПОЧЕМУ INTELLIJ ОТКАЗЫВАЕТСЯ ПРИНИМАТЬ ИЗМЕНЕНИЯ?!
public class Momodrek001 extends Plugin {
    int menuId;
    String name001;
    String uuid001;
    int kickMenuId;
    String uuid003;
    private int page = 0;
    Seq<String[]> b;
    Administration.PlayerInfo info002;
    private Seq<String> uuids = new Seq<>();
    private Seq<String[]> name002 = new Seq<>();
    int playerMenuId;
    int dopMenuId;
NetConnection netc;
int kickCurrentMenuId;
Seq<String[]> name003 = new Seq<>();
int menuId2;
Seq<String> uuidss = new Seq<>();
    Seq<String[]> name004 = new Seq<>();
    String name005;
    String uuid004;
    int kickMenuId2;
    String swq = "";
    String swqe = "";
    String agit;
    @Override
  public void init() {

      //Vars.netServer.admins.addActionFilter((player, s2, s3, s4) -> {


    //  });
      menuId = Menus.registerMenu((player, selection) -> {
          AdminChecker.loadConfig();
          if (uuids != null && selection >= 0 && selection < uuids.size) {
              name001 = name002.get(selection)[0];
              uuid001 = uuids.get(selection);

              Player targetPlayer = Groups.player.find(p -> p.uuid().equals(uuid001));

              String[][] timeOptions = {
                      {"1 день"},
                      {"1 неделя"},
                      {"1 месяц"},
                      {"Навсегда"},
                      {"Разкик"},
                      {"Перенаправить на локальный сервер"}
              };
              Call.menu(player.con, kickMenuId, "Выберите срок", "Для игрока: " + name001 + " " + uuid001, timeOptions);

          }

      });
        menuId2 = Menus.registerMenu((player, selection) -> {
            AdminChecker.loadConfig();
            for(Player player2 : Groups.player) {
uuidss.add(player2.uuid());
                name004.add(new String[] { player2.name," ", player2.lastText, player2.uuid()});

            }
            name005 = name004.get(selection)[0];
            uuid004 = uuidss.get(selection);
                Player targetPlayer2 = Groups.player.find(p -> p.uuid().equals(uuid004));

                String[][] timeOptions = {
                        {"1 день"},
                        {"1 неделя"},
                        {"1 месяц"},
                        {"Навсегда"},
                        {"Разкик"},
                        {"Перенаправить на локальный сервер"},
                        {"Сделать игрока админом"}
                };
                Call.menu(player.con, kickMenuId2, "Выберите срок", "Для игрока: " + name005 + " " + uuid004, timeOptions);



        });
      dopMenuId = Menus.registerMenu((player, selection) -> {
          if(selection == 0) {
              Events.fire(new GameOverEvent(Team.derelict));
             // Call.
          }
          if(selection == 1) {
              if (AdminChecker.isAdmin(player.uuid())) {
                  Core.settings.manualSave();
                  Vars.netServer.admins.save();
                  Log.warn("Админ сохранил данные, " + player.ip() + "uuid:" + player.uuid());
              }
          }
          if(selection == 2) {
              for(Block block : Vars.content.blocks()) {
                  block.buildVisibility = BuildVisibility.shown;
              }
          }
          

      });
        mindustry.Vars.netServer.admins.addActionFilter(action -> {
           if(action.tile != null && action.tile.x >= 348 && action.tile.x <= 451 && action.tile.y >= 348 && action.tile.y <= 451) {
           //    return true;
               if(action.block == mindustry.content.Blocks.logicDisplay) {
                   if(action.player.admin) {
                       return true;
                   } else {
                       return false;
                   }
               } else {
                   return true;
               }
           } else {

               if(action.tile != null) {

                   if(action.player.admin) {
                       return true;
                   } else {
                       return false;
                   }
               } else {
                   return true;
               }
           }

        });
kickCurrentMenuId = Menus.registerMenu((player, selection) -> {
    switch (selection) {
        case 0:
            for (Administration.PlayerInfo info : Vars.netServer.admins.playerInfo.values()) {
                uuids.add(info.id);


                name002.add(new String[] { info.lastName," ", info.lastSentMessage, info.id});

            }
            String[][] options = new String[name002.size][1];
            for (int i = 0; i < name002.size; i++) {
                options[i][0] = name002.get(i)[0];
            }

            Call.menu(player.con, menuId, "Игроки", "Выберите игрока:", options);
            break;
        case 1:
            name004.clear();
            uuidss.clear();
for(Player player002 : Groups.player) {
name004.add(new String[] { player002.name, " ", "", player002.uuid()});
        }

String[][] options002 = new String[name004.size][1];
            for (int i = 0; i < name004.size; i++) {
                options002[i][0] = name004.get(i)[0];
            }

            Call.menu(player.con, menuId2, "Текущие игроки", "Choose player", options002);

            break;
    }

});
      playerMenuId = Menus.registerMenu((player, selection) -> {
          String[][] timeOptions = {
                  {"Банить/кикать игроков"},
                  {"Улучшение процесса"},
                  {"Не сделано"},
                  {"Не сделано"},
                  {"Не сделано"}
          };
          switch (selection) {
              case 0:
                 // Call.menu(player.con, menuId, " ", "Меню для фич", timeOptions);
           /*       for (Administration.PlayerInfo info : Vars.netServer.admins.playerInfo.values()) {
                      uuids.add(info.id);


                      name002.add(new String[] { info.lastName," ", info.lastSentMessage, info.id});

                  }
                  String[][] options = new String[name002.size][1];
                  for (int i = 0; i < name002.size; i++) {
                      options[i][0] = name002.get(i)[0];
                  }

                  Call.menu(player.con, menuId, "Игроки", "Выберите игрока:", options);
*/
           String[][] options3 = {
                  {"Выбор всех игроков"},
                  {"Выбор онлайн игроков"}
              };
Call.menu(player.con, kickCurrentMenuId, "", "", options3);
              break;
              case 1:
                 String[][] options2 = {
                         {"Скип карты"},
                         {"Сохранение данных, не трогайте, пожалейте диск хоста"},
                         {"for цикл на показ всех блоков"}
                 };
Call.menu(player.con, dopMenuId, "Выбор действия", "", options2);
                  break;
              default:
                  return;

          }
              });
      kickMenuId2 = Menus.registerMenu((player, selection) -> {
          if (uuid004 == null) return;
         // Player target = Groups.player.find(p -> p.uuid().equals(uuid001));
          Player target1 = Groups.player.find(p -> p.uuid().equals(uuid004));
          long duration = 0;
          String reason = "";

          Administration.PlayerInfo info003 = Vars.netServer.admins.getInfo(uuid004);
          switch (selection) {
              case 0:
                  duration = 24 * 60 * 60 * 1000;
                  reason = "1 день";

                  if(target1 != null) {
                      target1.con.kick("Вы наказаны на " + reason, duration);
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                  } else {
                      Log.err("Нетц цели для разкика, 250 строка");
                  }



                  break;
              case 1:
                  duration = 7 * 24 * 60 * 60 * 1000;
                  reason = "1 неделя";

                  if(target1 != null) {
                      target1.con.kick("Вы наказаны на " + reason, duration);
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                  } else {
                      Log.err("Нетц цели для разкика, 250 строка");
                  }
                  break;
              case 2:
                  duration = 30L * 24 * 60 * 60 * 1000;
                  reason = "1 месяц";


                  if(target1 != null) {
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                      target1.con.kick("Вы наказаны на " + reason, duration);
                  } else {
                      Log.err("Нетц цели для разкика, 250 строка");
                  }
                  break;
              case 3:
                  duration = 0;
                  reason = "навсегда (бан)";
                 // Administration.PlayerInfo info003 = Vars.netServer.admins.getInfo(uuid001);
                  uuid003 = player.uuid();
                  if (AdminChecker.isAdmin(uuid003)) {
String uuid0004 = target1.uuid();
                      if(target1 != null && info003.banned) {
                          Vars.netServer.admins.unbanPlayerID(uuid0004);
                      } else {
                          Vars.netServer.admins.banPlayerID(uuid0004);
                      }
                  }


                  break;
              case 4:
                  if (target1 != null) {
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, 0);

                  }
                  break;
              case 5:
            netc = Seq.with(Vars.net.getConnections()).find(con -> con.uuid.equals(uuid004));
            if(netc == null) {

            } else {
                Call.connect(netc, "127.0.0.1", 6567);
            }
                  break;
              case 6:
                  if(target1.admin()) {
                      target1.admin = false;
                  } else {
                      target1.admin = true;
                  }
              default:
                  return;


          }
      });
        kickMenuId = Menus.registerMenu((player, selection) -> {
            if (uuid001 == null) return;

            Player target = Groups.player.find(p -> p.uuid().equals(uuid001));
            long duration = 0;
            String reason = "";

            Administration.PlayerInfo info002 = Vars.netServer.admins.getInfo(uuid001);
            switch (selection) {
                case 0:
                    duration = 24 * 60 * 60 * 1000;
                    reason = "1 день";

                    if(target != null) {
                        target.con.kick("Вы наказаны на " + reason, duration);
                        Vars.netServer.admins.handleKicked(info002.id, info002.lastIP, duration);
                    }
                    break;
                case 1:
                    duration = 7 * 24 * 60 * 60 * 1000;
                    reason = "1 неделя";

                    if(target != null) {
                        target.con.kick("Вы наказаны на " + reason, duration);
                        Vars.netServer.admins.handleKicked(info002.id, info002.lastIP, duration);
                    }
                    break;
                case 2:
                    duration = 30L * 24 * 60 * 60 * 1000;
                    reason = "1 месяц";


                    if(target != null) {
                        Vars.netServer.admins.handleKicked(info002.id, info002.lastIP, duration);
                        target.con.kick("Вы наказаны на " + reason, duration);
                    }
                    break;
                case 3:
                    duration = 0;
                    reason = "навсегда (бан)";
                    // Administration.PlayerInfo info003 = Vars.netServer.admins.getInfo(uuid001);
                    uuid003 = player.uuid();
                    if (AdminChecker.isAdmin(uuid003)) {

                        if(info002 != null && info002.banned) {
                            Vars.netServer.admins.unbanPlayerID(uuid001);
                        } else {
                            Vars.netServer.admins.banPlayerID(uuid001);
                        }
                    }


                    break;
                case 4:
                    if (target != null) {
                        Vars.netServer.admins.handleKicked(info002.id, info002.lastIP, 0);
                    }
                    break;
                case 5:
                    netc = Seq.with(Vars.net.getConnections()).find(con -> con.uuid.equals(uuid001));
                    if(netc == null) {

                    } else {
                        Call.connect(netc, "127.0.0.1", 6567);
                    }
                    break;
                default:
                    return;


            }
        });


    Events.on(EventType.PlayerJoin.class, event -> {

   });

    Timer.schedule(() -> {
        int rand5 = arc.math.Mathf.random(1, 5);
        switch(rand5) {
            case 1:
             agit = "Гайды по схемодельству и новости сервера в телеге - /tg";
            break;
            case 2:
              agit = "Хочешь предложить свою схему или правку? Присоединяйся к телеграмму - /tg";
                break;
            case 3:
                 agit = "Не пропусти обновления сервера и новые гайды — подписывайся на телегу - /tg";
                break;
            case 4:
                 agit = "Обсуждаем схемы, принимаем идеи и учимся строить вместе. Наш канал в телеге - /tg";
                break;
            case 5:
                agit = "Знаешь как улучшить схему на сервере? Пиши в телегу - /tg";
                break;
        }
      for (Player player : Groups.player) {


        //player.sendMessage("Есть пожелания к плагину? Напишите через команду /telegram");
          player.sendMessage(agit);
          }
    }, 5f, 499f);
  }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("telegramPlugin", "ТГ создателя плагина", (args, player) -> {
            Call.openURI("https://t.me/tomorek");


        });
        handler.<Player>register("tg", "ТГ канал сервера ", (args, player) -> {
            Call.openURI("https://t.me/mindustry_schems");


        });
        handler.<Player>register("chat", "Чат сервера в ТГ", (args, player) -> {
            Call.openURI("https://t.me/chatlybiteleizavodov");


        });
        handler.<Player>register("amenu", "Для администрации", (args, player) -> {
            if (AdminChecker.isModer(player.uuid())) {
                name002.clear();
                uuids.clear();

            /*    for (Administration.PlayerInfo info : Vars.netServer.admins.playerInfo.values()) {
                    uuids.add(info.id);


                    name002.add(new String[] { info.lastName," ", info.lastSentMessage, info.id});

                }
                String[][] options = new String[name002.size][1];
                for (int i = 0; i < name002.size; i++) {
                    options[i][0] = name002.get(i)[0];
                } */
                String[][] timeOptions = {
                        {"Банить/кикать игроков"},
                        {"Другое"},
                        {"Не сделано"},
                        {"Не сделано"},
                        {"Не сделано"}
                };
                Call.menu(player.con, playerMenuId, "Разделы", "Выберите раздел:", timeOptions);
            }

        });
        handler.<Player>register("maps", "Карты", (args, player) -> {
for(mindustry.maps.Map map : Vars.maps.all()) {
    if(map.custom) {
        swqe = "Кастом";
    } else {
        swqe = "Встроенная";
    }
     swq = " Карта " + map.name() + " [gold]" + swqe;
    player.sendMessage(swq);

}
//player.sendMessage(swq);

        });
        }

    //public void kick(String reason){
  //        kick(reason, null, 30 * 1000);
  //    }

}
class AdminChecker {

    private static String[] rootAdmins = {"", ""};
    private static String[] admins = {"", ""};
    private static String[] moders = {"75ZDpZN1EzIAAAAA1jY3ZQ==", "uuid-moder-2"};
    private static String[] reserve = {"uuid-reserve-1", "uuid-reserve-2"};


    private static boolean contains(String[] array, String value) {
        for (String item : array) {
            if (item.equals(value)) return true;
        }
        return false;
    }
    public static void loadConfig() {
        try {
            Fi file = Core.files.local("config/config/admins.json");
            if (file.exists()) {
                String content = file.readString();
                JsonValue json = new JsonReader().parse(content);
                rootAdmins = json.get("rootAdmins").asStringArray();
                admins = json.get("admins").asStringArray();
                moders = json.get("moders").asStringArray();
                reserve = json.get("reserve").asStringArray();
            } else {
                Log.warn("Файл конфигурации не найден по пути: " + file.path());

                return;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static int getLevel(String uuid) {
        if (contains(rootAdmins, uuid)) return 0;
        if (contains(admins, uuid)) return 1;
        if (contains(moders, uuid)) return 2;
        if (contains(reserve, uuid)) return 3;
        return 4;
    }


    public static boolean isRoot(String uuid) { return getLevel(uuid) == 0; }
    public static boolean isAdmin(String uuid) { return getLevel(uuid) <= 1; }
    public static boolean isModer(String uuid) { return getLevel(uuid) <= 2; }
}
