package Tomodrek;
import arc.util.*;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.core.GameState;
import mindustry.gen.Call;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.game.*;
import mindustry.game.Schematics;
import arc.Events;
import mindustry.game.EventType;
import mindustry.world.Block;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.game.EventType.PlayerChatEvent;
import arc.Input;
import arc.input.KeyCode;
import mindustry.game.EventType.Trigger;
import mindustry.game.EventType.*;
import arc.Core;
import java.lang.reflect.*;
import java.lang.reflect.Modifier;

import mindustry.game.Schematics;
import mindustry.input.*;
import mindustry.input.InputHandler;

public class Modomodrek extends Mod {
    BaseDialog Dialog001;
    BaseDialog Dialog002;
    @Override
    public void loadContent() {
        qwerWalls.load();
}
    @Override
    public void init() {
  // mindustry.Vars.maxSchematicSize = 2048;
        Events.on(EventType.ClientLoadEvent.class, event -> {
           // for (Team team : Team.all) {
         //       Vars.state.rules.teams.get(team).infiniteAmmo = false;
         //   }
                Dialog001 = new BaseDialog("Меню мода");
                Dialog001.addCloseButton();
                Dialog001.cont.add("Привет, это твое меню!").row();
                Dialog001.cont.button("Закрыть", Dialog001::hide).size(10f, 10f);
            String s001 = Liquids.cryofluid.emoji();
Dialog002 = Dialog001;
            // Добавляем кнопку в настройки
                Vars.ui.settings.addCategory("расширенные выозможности", Icon.logic, table -> {
                    table.button("Пауза", () -> {
                        //Call.connect(Vars.player.con, "pivomind.pro", 6567);
                        if (Vars.state.isPaused()) {
                            // Снять с паузы
                            Vars.state.set(GameState.State.playing);
                        } else {
                            // Поставить на паузу
                            Vars.state.set(GameState.State.paused);
                        }
                    }).width(96f).height(128f);
                    table.row();
                    table.button("@", () -> {
                        mindustry.Vars.maxSchematicSize = 4096;
                        Vars.state.rules.planet = Planets.sun;
                    }).height(36f).width(36f);
                    @Nullable
                    Player player = Vars.player;

                   // Call.connect(Vars.player.con, "pivomind.pro", 6567);
                });
                Dialog001.show();
                Dialog001.hide();

        });
Events.on(PlayerChatEvent.class, event -> {
Player player = event.player;
@Nullable
Unit unit = player.unit();
        if(player == null) {
        } else {
            if (unit != null)  {

                unit.health *= 2f;
                unit.addItem(unit.stack.item, 1);
            }
        }
    });
Events.run(Trigger.update, () -> {
if(Core.input.keyTap(KeyCode.f6)) {
Player player = Vars.player;
Unit unit = player.unit();
    if(Vars.state.rules.unitAmmo == false) {
        Vars.state.rules.unitAmmo = true;
    } 
    else {
         Vars.state.rules.unitAmmo = false;
    }
}      //Я этот код не понимаю с клавишами
});
     Events.run(Trigger.update, () -> {
if(Core.input.keyTap(KeyCode.f5)) {   
    for (Block block : Vars.content.blocks()) {
    block.buildVisibility = BuildVisibility.shown;
}
}
         if(Core.input.keyTap(KeyCode.f4)) {   
    for (Block block : Vars.content.blocks()) {
        Vars.state.rules.allowEditRules = true;
        Vars.state.rules.instantBuild = true;
       // mindustry.game.Rules.planet = Planets.sun;
        Vars.state.rules.planet = Planets.sun;
if(Core.input.keyTap(KeyCode.f3)) {   
    Events.fire(EventType.WorldLoadEvent.class);
    mindustry.Vars.enableLight = false;

    //Dialog001 = new BaseDialog("Меню мода");
   // Dialog001.addCloseButton();
   // Dialog001.cont.add("Привет, это твое меню!").row();
   // Dialog001.cont.button("Закрыть", Dialog001::hide).size(2100f, 110f);

    // Добавляем кнопку в настройки
   // Vars.ui.settings.addCategory("My Mod", Icon.logic, table -> {
     //   table.button("Открыть меню", Dialog001::show).width(240f);

           // Dialog001.show();
          //  Dialog001.hide();







    }
        }

        }

     });
    Events.run(Trigger.update, () -> {
            Vars.state.rules.fog = false;
        Vars.state.rules.staticFog = false;
        Vars.ios = true;
    });
}
} //
