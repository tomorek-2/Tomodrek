/*
 * Copyright (c) 2026 tomorek-2
 * Licensed under the GNU GPL v3.0
 */
package Tomodrek;


import java.util.Arrays;
import java.util.HashMap;

import arc.Core;
import arc.Events;

import arc.struct.Seq;

import arc.util.Http;
import arc.util.Log;
import arc.util.Timer;
import mindustry.Vars;

import mindustry.ai.Pathfinder;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.GroundAI;
import mindustry.content.Planets;
import mindustry.content.UnitTypes;
import mindustry.ctype.Content;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.PathTile;
import mindustry.gen.Player;

import mindustry.mod.Plugin;

import arc.util.CommandHandler;
import mindustry.net.Administration;
import mindustry.game.EventType.*;
import mindustry.net.NetConnection;
import mindustry.ui.Menus;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

import Tomodrek.LoadJSONConfig;

public class Momodrek001 extends Plugin {
    String LICENSE = " \n" +
            "Copyright (c) 2026 tomorek-2\n" +
            "Licensed under the GNU GPL v3.0";

    String uuid003;


    int playerMenuId;
    int dopMenuId;
    int dopMenuId001;
    int dopMenuId002;
NetConnection netc;
int kickCurrentMenuId;

int menuId2;

    HashMap<String, AdminData> DataAmenu = new HashMap(); // String это uuid

    String name005;

    int kickMenuId2;
    String mapsCommand = "";
    String mapsCommandWIP = "";
    String agit;

    int online001, online002, online003, online004, online005; //Счётчик активности,
    String[][] timeOptions;
    String[][] options3;
    String body001;
    String locale; //Локализация. "А ВОТ С HASHMAP БыЛО ПРОИЗВОДИТЕЛЬНЕЙ ЧЕМ С SWITCH". Я забыл что существует вообще bundle

    @Override
  public void init() {

Events.on(EventType.WorldLoadEvent.class, event -> {

    Timer.schedule(() -> {

        if(
                LoadJSONConfig.getConfig("EnableAutoDisableBlocks").equals("true")
        ) {
            arc.struct.Seq<mindustry.gen.Building> allBuildingsSharded = mindustry.game.Team.sharded.data().buildings;

            arc.struct.Seq<mindustry.gen.Building> allBuildingsMalis = Team.malis.data().buildings;


            allBuildingsSharded.each(building -> {
                building.enabled = false;
            });
            allBuildingsMalis.each(building -> {
                building.enabled = false;
            });
        }
    }, 2f, LoadJSONConfig.getConfigInt("IntervalEnabledOffAllBlocksMalis"));
});

      //Vars.netServer.admins.addActionFilter((player, s2, s3, s4) -> {
        //Счётчик зашедших игроков
Events.on(EventType.PlayerJoin.class, event -> {
    if(event.player.locale == null) event.player.kick("locale cannot be null");
    online001++;
   // if(LoadJSONConfig.PlayerShadowBanned(event.player.uuid())) {
    //   event.player.team(Team.derelict);
 //   }

    event.player.name = "<"+LoadJSONConfig.getLevel(event.player.uuid(), event.player)+">"+event.player.name;
});

//Счётчик вышедших игроков
        Events.on(EventType.PlayerLeave.class, event -> {
            online002++;
            if(DataAmenu.containsKey(event.player.uuid()))
            DataAmenu.remove(event.player.uuid());
        });
        //Счётчик поставленных блоков
        Events.on(EventType.BlockBuildEndEvent.class, event -> {
            online003++;
        });
        //Счётчик, когда игрок брал под управление объект
        Events.on(EventType.UnitControlEvent.class, event -> {
            online004++;
        });
        //Сколько игроков написали в чат
        Events.on(EventType.PlayerChatEvent.class, event -> {
            online005++;
        });
        //Необходимо для мута.
        Vars.netServer.admins.addChatFilter((player, message) -> {
if(LoadJSONConfig.PlayerShadowBanned(player.uuid())) {
    return null;
} else {
    return message;
}
        });
        LoadJSONConfig.loadConfig();

        //Устаревшая логика, должна прийти на замену база данных

        menuId2 = Menus.registerMenu((player, selection) -> {

            if(selection == -1) return;
            if(LoadJSONConfig.isModer(player.uuid(), player)) {
                LoadJSONConfig.loadConfig();
           var playerData =     DataAmenu.getOrDefault(player.uuid(), null);
                if(playerData == null) {
                    player.sendMessage("Простите, но в плагине произошла ошибка, playerData оказалась null.");
                    return;
                }

             /*   for (Player player2 : Groups.player) {
                    playerData.listUuidsPlayers.add(player2.uuid());
                    playerData.listnamesPlayers.add(new String[]{player2.name, " ", player2.lastText, player2.uuid()});

                } */
                name005 = playerData.listnamesPlayers.get(selection)[0];
                playerData.targetUuid = playerData.listUuidsPlayers.get(selection);
                playerData.targetPlayer = Groups.player.find(p -> p.uuid().equals(playerData.targetUuid));

if(player.locale.equals("ru")) {
 timeOptions = new String[][]{
            {"1 день"},
            {"1 неделя"},
            {"1 месяц"},
            {"Навсегда"},
            {"Разкик"},
            {"Перенаправить на локальный сервер"},
            {"Сделать игрока админом"},
            {"Теневой бан", "мут"}

    };
} else {
 timeOptions = new String[][]{
            {"1 day"},
            {"1 week"},
            {"1 month"},
            {"Forever"},
            {"Unban"},
            {"Redirect to local server"},
            {"Make them admin"},
            {"Shadowban", "mute"}
    };
}
                Call.menu(player.con, kickMenuId2, "Выберите срок", "Для игрока: " + name005 + " " + playerData.targetUuid, timeOptions);

            }

        });
        
      dopMenuId = Menus.registerMenu((player, selection) -> {
          if(LoadJSONConfig.isModer(player.uuid(), player)) {
              if (selection == 0) {
                  Events.fire(new GameOverEvent(Team.derelict));

              }
              if (selection == 1) {
                  if (LoadJSONConfig.isModer(player.uuid(), player)) {
                      Core.settings.manualSave();
                      Vars.netServer.admins.save();
                      LoadJSONConfig.SaveShadowBansList();
                      Log.warn("Админ сохранил данные, " + player.ip() + "uuid:" + player.uuid());
                  }
              }
              if (selection == 2) {
                  for (Block block : Vars.content.blocks()) {
                      block.buildVisibility = BuildVisibility.shown;
                  }
              }
          }

      });

        //С addActionFilter и с HashMap можно приват сделать, логика проста как табуретка.
        mindustry.Vars.netServer.admins.addActionFilter(action -> {
           if(action.tile != null && action.tile.x >= LoadJSONConfig.getConfigInt("LimitXStartAction") && action.tile.x <= LoadJSONConfig.getConfigInt("LimitXEndAction")&& action.tile.y >= LoadJSONConfig.getConfigInt("LimitYStartAction") && action.tile.y <= LoadJSONConfig.getConfigInt("LimitYEndAction")) {
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
    if(LoadJSONConfig.isModer(player.uuid(), player)) {
        switch (selection) {
            case 0:
            case 1:

                AdminData tmpData = new AdminData();

    var  playerData =       DataAmenu.getOrDefault(player.uuid(), tmpData);
    if(playerData == null) {playerData = tmpData;

    }
    playerData.listnamesPlayers.clear();
                playerData.listUuidsPlayers.clear();
                for (Player player002 : Groups.player) {
                    playerData.listnamesPlayers.add(new String[]{player002.name, " ", "", player002.uuid()});
                    playerData.listUuidsPlayers.add(player002.uuid());
                }

                String[][] options002 = new String[playerData.listnamesPlayers.size][1];
                for (int i = 0; i < playerData.listnamesPlayers.size; i++) {
                    options002[i][0] = playerData.listnamesPlayers.get(i)[0];
                }
if(!DataAmenu.containsKey(player.uuid())) {
    DataAmenu.put(player.uuid(), playerData);
}

                Call.menu(player.con, menuId2, "Текущие игроки", "Choose player", options002);

                break;
        }
    }
});
      playerMenuId = Menus.registerMenu((player, selection) -> {
          if(LoadJSONConfig.isModer(player.uuid(), player)) {

          switch (selection) {
              case 0:

                  if(player.locale.equals("ru")) {
                    options3 = new String[][]{
                              {"Выбор всех игроков"},
                              {"Выбор онлайн игроков"}
                      };
                  } else {
              options3 = new String[][]{
                              {"Select all players"},
                              {"Select online players"}
                      };
                  }
                  Call.menu(player.con, kickCurrentMenuId, "", "", options3);
                  break;
              case 1:
                  String[][] options2;
                  if(player.locale.equals("ru")) {
                      options2 = new String[][]{
                              {"Скип карты"},
                              {"Сохранение данных, не трогайте, пожалейте диск хоста"},
                              {"for цикл на показ всех блоков"}
                      };
                  } else {
                      options2 = new String[][]{
                              {"Skip Map"},
                              {"Save Data (Don't touch, spare the host's disk)"},
                              {"for loop to show all blocks"}
                      };
                  }
                  Call.menu(player.con, dopMenuId, "Выбор действия", "", options2);
                  break;
              case 2:

                  String[][] options4;

                  // Безопасная проверка локали (защита от NullPointerException, если locale == null)
                  if(player.locale != null && player.locale.equals("ru")) {
                      body001 = "Статистика";
                      options4 = new String[][]{
                              {"Игроков зашло: " + online001},
                              {"Игроков вышло: " + online002},
                              {"Блоков построено: " + online003},
                              {"Юнитов захвачено игроками: " + online004},
                              {"Сообщений написали игроки в чате: " + online005},
                              {"ОЗУ" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " МБ"},
                              {"Пинг до 8.8.8.8:"}
                      };
                  } else {
                      body001 = "Statistics";
                      options4 = new String[][]{
                              {"Players joined: " + online001},
                              {"Players left: " + online002},
                              {"Blocks built: " + online003},
                              {"Units controlled: " + online004},
                              {"Messages sent: " + online005},
                              {"RAM" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB"}
                      };
                  }
                  Call.menu(player.con, dopMenuId001, body001, "", options4);
              default:
                  return;
          }
          }
              });

        dopMenuId001 = Menus.registerMenu((player, selection) -> {
if(selection == 6) {
    Http.get("http://google.com", response -> {
        int code = response.getStatus().code;


        String[][] options4 = new String[][]{
                {"Статус Google: " + code},
        };

        Call.menu(player.con, dopMenuId002, "Statistics", "", options4);
    });



}

        });

        dopMenuId002 = Menus.registerMenu((player, selection) -> {

        });

      kickMenuId2 = Menus.registerMenu((player, selection) -> {
          if(LoadJSONConfig.isModer(player.uuid(), player)) {
              var  playerData =        DataAmenu.getOrDefault(player.uuid(), null);
              if(playerData == null) {
                 return;

              }
          if (playerData.targetUuid == null) return;

          Player target1 = Groups.player.find(p -> p.uuid().equals(playerData.targetUuid));
          long duration = 0;
          String reason = "";

          Administration.PlayerInfo info003 = Vars.netServer.admins.getInfo(playerData.targetUuid);
          switch (selection) {
              case 0:
                  duration = 24 * 60 * 60 * 1000;
                  reason = "1 день";

                  if (target1 != null) {
                      target1.con.kick("Вы наказаны на " + reason, duration);
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                  } else {
                      Log.err("Нетц цели для разкика, 329 строка");
                  }


                  break;
              case 1:
                  duration = 7 * 24 * 60 * 60 * 1000;
                  reason = "1 неделя";

                  if (target1 != null) {
                      target1.con.kick("Вы наказаны на " + reason, duration);
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                  }
                  break;
              case 2:
                  duration = 30L * 24 * 60 * 60 * 1000;
                  reason = "1 месяц";


                  if (target1 != null) {
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, duration);
                      target1.con.kick("Вы наказаны на " + reason, duration);

                  }
                  break;
              case 3:
                  duration = 0;
                  reason = "навсегда (бан)";
                  // Administration.PlayerInfo info003 = Vars.netServer.admins.getInfo(uuid001);
                  uuid003 = player.uuid();
                  if (LoadJSONConfig.isAdmin(uuid003, player)) {
                      String uuid0004 = info003.id;
                      if (target1 != null && info003.banned) {
                          Vars.netServer.admins.unbanPlayerID(uuid0004);
                      } else {
                          Vars.netServer.admins.banPlayerID(uuid0004);
                          netc = Seq.with(Vars.net.getConnections()).find(con -> con.uuid.equals(playerData.targetUuid));
                          if (netc == null) {

                          } else {
                              Call.connect(netc, "127.0.0.1", 6567);
                          }

                      }

                  }


                  break;
              case 4:
                  if (target1 != null) {
                      Vars.netServer.admins.handleKicked(info003.id, info003.lastIP, 0);

                  }
                  break;
              case 5:
                  netc = Seq.with(Vars.net.getConnections()).find(con -> con.uuid.equals(playerData.targetUuid));
                  if (netc == null) {

                  } else {
                      Call.connect(netc, "127.0.0.1", 6567);
                  }
                  break;
              case 6:
                  if (target1.admin()) {
                      target1.admin = false;
                  } else {
                      target1.admin = true;
                  }

                  break;

              case 7:
                  LoadJSONConfig.AddPlayerInShadowBan(playerData.targetUuid);
                  break;
              case 8:
                  LoadJSONConfig.AddPlayerInShadowBan(playerData.targetUuid);
              default:
                  return;

          }
          }
      });




    Timer.schedule(() -> {
        int rand5 = arc.math.Mathf.random(1, 5);
//Код, который необходим для агитации.
      for (Player player : Groups.player) {
        switch(player.locale()) {
            case "ru":
            switch (rand5) {
                case 1:


                    agit = LoadJSONConfig.getConfig("LocalRu1Agit");
                    break;
                case 2:

         agit = LoadJSONConfig.getConfig("LocalRu2Agit");
                    break;
                case 3:

                    agit = LoadJSONConfig.getConfig("LocalRu3Agit");
                    break;
                case 4:

                     agit = LoadJSONConfig.getConfig("LocalRu4Agit");
                    break;
                case 5:

                    agit = LoadJSONConfig.getConfig("LocalRu5Agit");
                    break;
            }
            break;
            default:
                switch (rand5) {
                    case 1:


                        agit = LoadJSONConfig.getConfig("LocalEn1Agit");
                        break;
                    case 2:

                        agit = LoadJSONConfig.getConfig("LocalEn2Agit");
                        break;
                    case 3:

                        agit = LoadJSONConfig.getConfig("LocalEn3Agit");
                        break;
                    case 4:

                        agit = LoadJSONConfig.getConfig("LocalEn4Agit");
                        break;
                    case 5:

                        agit = LoadJSONConfig.getConfig("LocalEn5Agit");
                        break;
                }
                break;
        }

        //player.sendMessage("Есть пожелания к плагину? Напишите через команду /telegram");
          player.sendMessage(agit);
          }

    }, 5f, Tomodrek.LoadJSONConfig.getConfigInt("IntervalSendInChatAgit"));
  }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("aboutPlugin","<github/telegram>", "Контактная информация разработчика плагина. Релизов плагина нет.", (args, player) -> {
            if (args != null && args.length != 0 && args[0] != null) {
                switch (args[0].toLowerCase().trim()) {

                    case "github":
                        Call.openURI(player.con, "https://github.com/tomorek-2/Tomodrek");

                        return;
                    case "telegram":
                        Call.openURI(player.con, "https://t.me/tomorek");
                        return;
                }
            }

        });
        handler.<Player>register("tg", "ТГ канал сервера ", (args, player) -> {
            Call.openURI(player.con, LoadJSONConfig.getConfig("URLCommandTelegramChannelServer"));


        });
        handler.<Player>register("chat", "Чат сервера в ТГ", (args, player) -> {
            Call.openURI(player.con, LoadJSONConfig.getConfig("URLCommandChat"));


        });
        handler.<Player>register("me", "Информация о себе", (args, player) -> {
           player.sendMessage("IP: " + player.ip() + " UUID: " + player.uuid() + " Locale: " + player.locale());


        });
        handler.<Player>register("amenu", "Для администрации", (args, player) -> {
            if (LoadJSONConfig.isModer(player.uuid(), player)) {


Tomodrek.LoadJSONConfig.loadConfig();

                String Body001;
                if(player.locale.equals("ru")) {
                    Body001 = "Статистика";
                } else {
                    Body001 = "Statistics";
                }

                String[][] timeOptions = {
                        {"Банить/кикать игроков"},
                        {"Другое"},
                        {Body001},
                        {"Не сделано"},
                        {"Не сделано"}
                };
                Call.menu(player.con, playerMenuId, "Разделы", "Выберите раздел:", timeOptions);
            }

        });
        handler.<Player>register("maps", "Карты", (args, player) -> {
for(mindustry.maps.Map map : Vars.maps.all()) {
    if(map.custom) {
        if(player.locale.equals("ru")) {
            mapsCommandWIP = "Кастом";
        } else {
            mapsCommandWIP = "custom";
        }
    } else {
        if(player.locale.equals("ru")) {
            mapsCommandWIP = "Встроенная";
        } else {
            mapsCommandWIP = "Built-in map";

        }

    }
     mapsCommand = "[white]" +  "Map " + map.name() + " [gold]" + mapsCommandWIP + " " + map.width + "x" + map.height;
    player.sendMessage(mapsCommand);

}


        });
        handler.<Player>register("ser", "<serpulo/erekir>", "переключение между серверами", (args, player) -> {
            if (args != null && args.length != 0 && args[0] != null) {
                switch (args[0].toLowerCase().trim()) {
                    case "erekir":
                        Call.connect(player.con, LoadJSONConfig.getConfig("IPerekir"), Tomodrek.LoadJSONConfig.getConfigInt("PortErekir"));
                        break;
                    case "serpulo":
                        Call.connect(player.con, LoadJSONConfig.getConfig("IPSerpulo"),  Tomodrek.LoadJSONConfig.getConfigInt("PortSerpulo"));
                        break;
                    default:
                        Call.connect(player.con, "8.8.8.8", 6567);
                }

            } else {
                switch (player.locale) {
                    case "ru":
                        locale = "Не введён аргумент чтобы подключится к серверу. Введите erekir/serpulo";
                        break;
                    default:
                        locale = "Argument to connect to the server is missing. Enter erekir/serpulo";
                }

                player.sendMessage(locale);
            }
        });
        }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("asay", "<text...>", "Отправить сообщение админам", args -> {
            String raw = "[red][server]: [white]" + args[0];
            Groups.player.each(mindustry.gen.Player::admin, a -> a.sendMessage(raw));
            arc.util.Log.info("[Server] " + args[0]);
        });
    }
}
class AdminData {
    public Seq<String> listUuidsPlayers = new Seq<>(200);
    public Seq<String[]> listnamesPlayers = new Seq<>(200);
public Player targetPlayer;
public String targetUuid;
}
