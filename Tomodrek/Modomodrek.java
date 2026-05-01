package Tomodrek;
import arc.util.*;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.core.GameState;
import mindustry.editor.MapResizeDialog;
import mindustry.gen.Call;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.world.Tile;
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
    String w = "Напиши", ww = "ww";
   float slider001 = 64f;
    String text0002;
    SettingsMenuDialog.SettingsTable table001;
    @Override
    public void loadContent() {
        Tomodrek.qwerWalls.load();
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
                Dialog001.cont.add("Заглушка");
                Dialog001.field(w, text002 -> {
                    String text0002 = text002;
            }).expand((int)slider001, 65);

            String s001 = Liquids.cryofluid.emoji();
Dialog002 = Dialog001;
            // Добавляем кнопку в настройки

                Vars.ui.settings.addCategory("Расширенные возможности", Icon.logic, table -> {
                    table.button("Пауза", () -> {
                        //Call.connect(Vars.player.con, "pivomind.pro", 6567);
                        SettingsMenuDialog.SettingsTable table001 = table;
                        if (Vars.state.isPaused()) {
                            // Снять с паузы
                            Vars.state.set(GameState.State.playing);
                        } else {
                            // Поставить на паузу
                            Vars.state.set(GameState.State.paused);
                        }
                    }).width(96f).height(32f);
                    table.row();
                   // table.button("@", () -> {
                  //      mindustry.Vars.maxSchematicSize = 4096;
                  //      Vars.state.rules.planet = Planets.sun;
                  //  }).height(36f).width(36f);
                    //table.x(10f);
                    table.right();
                    table.field(w, text001 -> {
                      String w = text001;
                    }).height(36f).width(192f);
                    table.bottom();
                    table.button("157 или 156", () -> {
                        if(mindustry.core.Version.build == 157) {
                            mindustry.core.Version.build = 156;
                        } else {
                            mindustry.core.Version.build = 157;
                        }
                        table.setPosition(slider001, 120f);
                    }).height(45f).width(50f).expand((int)slider001, 65);
                    table.slider( 64, 8192, 1, 5, s -> {
                        mindustry.Vars.maxSchematicSize = (int)s;
                        //Events.fire(EventType.ClientLoadEvent.class);

                       float slider001 = s;

                    }).height(64).width(256f);
                    @Nullable
                    Player player = Vars.player;

                   // Call.connect(Vars.player.con, "pivomind.pro", 6567);
                });

                Dialog001.show();
                Dialog001.hide();
            Vars.ui.menufrag.addButton("Modomodrek",() -> {
                Dialog001.show();
                Dialog001.setPosition(slider001, 35f);
            });


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
    mindustry.editor.MapResizeDialog.maxSize = 4096;


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
