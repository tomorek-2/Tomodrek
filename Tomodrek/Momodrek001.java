package Tomodrek;
import arc.Core;
import arc.Events;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.game.Schematics;
import arc.util.CommandHandler;
import mindustry.net.Administration;
import mindustry.game.EventType.*;
import mindustry.ui.Menus;
import mindustry.ui.dialogs.BaseDialog;

public class Momodrek001 extends Plugin {
    int menuId;
    String name001;
    String uuid001;

    private Seq<String> uuids = new Seq<>();
    private Seq<String[]> name002 = new Seq<>();

  @Override
  public void init() {
    //Blocks.vault.requirements(Category.effect, ItemStack.with(Items.copper, 2000, Items.lead, 2000, Items.thorium, 4000));
   // mindustry.Vars.maxSchematicSize = 1024;
      menuId = Menus.registerMenu((player, selection) -> {
          // Действие по клику: получаем UUID из массива
          if (uuids != null && selection >= 0 && selection < uuids.size) {
             name001 = player.name();
             uuid001 = player.uuid();
          }


      });

    Events.on(EventType.PlayerJoin.class, event -> {

   });

    Timer.schedule(() -> {
      for (Player player : Groups.player) {


        player.sendMessage("Переходите на канал ТГ через команду /telegram");
      }
    }, 5f, 600f);
  }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("telegram", "ТГ", (args, player) -> {
            Call.openURI("https://t.me/LazyCatV");
        });
        handler.<Player>register("amenu", "Для администрации", (args, player) -> {


        for (Administration.PlayerInfo info : Vars.netServer.admins.playerInfo.values()) {
            uuids.add(info.id);
            name002.add(new String[]{info.lastName});
        }
            String[][] options = new String[name002.size][1];
            for (int i = 0; i < name002.size; i++) {
                options[i][0] = name002.get(i)[0];
            }


            Call.menu(player.con, menuId, "Игроки", "Выберите игрока:", options);
        });
    }
    //public void kick(String reason){
  //        kick(reason, null, 30 * 1000);
  //    }
}
