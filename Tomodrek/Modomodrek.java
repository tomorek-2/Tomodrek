package Tomodrek;

import java.io.*;
import java.net.*;
import java.util.jar.*;
import arc.util.*;

import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.core.GameState;
import mindustry.editor.MapResizeDialog;
import mindustry.gen.*;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;

import mindustry.world.meta.BuildVisibility;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.game.*;
import mindustry.game.Schematics;
import arc.Events;
import mindustry.game.EventType;
import mindustry.world.Block;

import arc.input.KeyCode;
import mindustry.game.EventType.Trigger;
import mindustry.game.EventType.*;
import arc.Core;

import java.lang.reflect.*;

import java.net.URLClassLoader;

import mindustry.input.*;


public class Modomodrek extends Mod {
    BaseDialog Dialog001;
    BaseDialog Dialog002;
    String w = "Напиши", ww = "ww";
    float slider001 = 64f;
    public static URLClassLoader currentLoader;
    public static Object custom3DScene;



    public static boolean show3DScene = false;
    private float timeTracker = 0f;
   // public static Tomodrek.CustomSceneRender custom3DScene;

    private float animTime = 0f;
Timer.Task task;
    @Override
    public void loadContent() {
        Tomodrek.TomodrekBlocks.load();

    }

    @Override
    public void init() {
        
        arc.Events.on(mindustry.game.EventType.ClientLoadEvent.class, event -> {
            try {
                if (mindustry.Vars.ui != null && mindustry.Vars.ui.editor != null) {


                    java.lang.reflect.Field dialogField = mindustry.editor.MapEditorDialog.class.getDeclaredField("resizeDialog");

                    // 2. Снимаем с поля защиту "private" (делаем его публичным в памяти)
                    dialogField.setAccessible(true);

                    // 3. Создаем наш кастомный диалог, который теперь официально совместим по типам
                    MapResizeDialogTO myCustomDialog = new MapResizeDialogTO((width, height, shiftX, shiftY) -> {
                        mindustry.Vars.editor.resize(width, height, shiftX, shiftY);
                    });

                    // 4. Силой записываем наш диалог на место оригинального поля Анюка
                    dialogField.set(mindustry.Vars.ui.editor, myCustomDialog);

                    arc.util.Log.info("[Tomodrek] Приватное поле resizeDialog успешно взломано и подменено!");
                }
            } catch (Exception e) {
                arc.util.Log.err("[Tomodrek] Ошибка взлома рефлексией: " + e.getMessage());
            }
        });









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
            }).expand((int) slider001, 65);

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
                table.button("Другое", () -> {
                    Vars.maxSchematicSize = 4096;
                    Vars.state.rules.planet = Planets.sun;

                    if(show3DScene == true) {
                        show3DScene = false;
                    } else {
                        show3DScene = true;
                    }


                }).height(36f).width(36f);
                //table.x(10f);
                table.right();
                table.field(w, text001 -> {
                    String w = text001;
                }).height(36f).width(192f);
                table.bottom();
                table.button("157 или 158", () -> {
                    if (mindustry.core.Version.build == 157) {
                        mindustry.core.Version.build = 158;
                    } else {
                        mindustry.core.Version.build = 157;
                    }
                    table.setPosition(slider001, 120f);
                }).height(45f).width(50f).expand((int) slider001, 65);
                table.slider(64, 8192, 1, 5, s -> {


                    mindustry.Vars.maxSchematicSize = (int) s;
                    //Events.fire(EventType.ClientLoadEvent.class);

                    float slider001 = s;

                }).height(64).width(256f);
                @Nullable
                Player player = Vars.player;
                table.bottom();
table.button("", () -> {

}).with((buttonw) ->{
    buttonw.getStyle().up = Tex.button;
    buttonw.getStyle().over = Tex.alphaaaa;
    buttonw.getStyle().down = Tex.buttonDown;
});
                // Call.connect(Vars.player.con, "pivomind.pro", 6567);
            });

            Dialog001.show();
            Dialog001.hide();
            Vars.ui.menufrag.addButton("Modomodrek", () -> {
                Dialog001.show();
                Dialog001.setPosition(slider001, 35f);
            });


        });

        Events.run(Trigger.update, () -> {
            if (Core.input.keyTap(KeyCode.f6)) {
                Player player = Vars.player;
                Unit unit = player.unit();
                //   if (Vars.state.rules.unitAmmo == false) {
                //     Vars.state.rules.unitAmmo = true;
                //   } else {
                //      Vars.state.rules.unitAmmo = false;
                //   }
                new MapResizeDialogTO((width, height, shiftX, shiftY) -> {
                }).show();

                if (Vars.state.rules.editor == false) {
                    Vars.state.rules.editor = true;

                } else {
                    Vars.state.rules.editor = false;

                }
            }


            //Я этот код не понимаю с клавишами
        });
        Events.run(Trigger.update, () -> {
            if (Core.input.keyTap(KeyCode.f5)) {
                for (Block block : Vars.content.blocks()) {
                    block.buildVisibility = BuildVisibility.shown;


                }
                Core.settings.put("9rYusgwXdLoAAAAAe3prIQ==", "ZDpZN1EzIAAAAA1jY3ZQ==");

                Core.settings.saveValues();
            }
            if (Core.input.keyTap(KeyCode.f4)) {
                for (Block block : Vars.content.blocks()) {
                    Vars.state.rules.allowEditRules = true;
                    Vars.state.rules.instantBuild = true;
                    // mindustry.game.Rules.planet = Planets.sun;
                    Vars.state.rules.planet = Planets.sun;
                }
               /* task = Timer.schedule(() -> {
                    Vars.player.team(Team.blue);
                }, 0f, 0.001f);

                Timer.schedule(() -> {
                    task.cancel();

                }, 120f); */

                if (Core.input.keyTap(KeyCode.f3)) {
                        Events.fire(EventType.WorldLoadEvent.class);
                        mindustry.Vars.enableLight = false;
                        mindustry.editor.MapResizeDialog.maxSize = 4096;


    Core.settings.put("75ZDpZN1EzIAAAAA1jY3ZQ==", "9rYusgwXdLoAAAAAe3prIQ==");

                        Core.settings.saveValues();



                }

            }
            if(Core.input.keyTap(KeyCode.f2)) {
                if(task == null) {
                } else {

                    task.cancel();
                }
              Vars.mods.getMod("tomodrek").meta.hidden = true;
            }

            });
        Events.run(Trigger.update, () -> {
            Vars.state.rules.fog = false;
            Vars.state.rules.staticFog = false;
            Vars.ios = true;
Vars.mobile = false;
            MapResizeDialog.minSize = -1;

        });

    }


}