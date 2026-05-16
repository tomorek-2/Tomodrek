package Tomodrek;
import arc.files.Fi;
import arc.math.Mathf;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import java.io.*;
import java.net.*;
import java.util.jar.*;
import arc.util.*;
import arc.util.serialization.JsonReader;
import arc.util.serialization.JsonValue;
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

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mindustry.game.Schematics;
import mindustry.input.*;
import mindustry.input.InputHandler;

import Tomodrek.*;

import static mindustry.Vars.discordURL;
import static mindustry.Vars.editor;

public class Modomodrek extends Mod {
    BaseDialog Dialog001;
    BaseDialog Dialog002;
    String w = "Напиши", ww = "ww";
    float slider001 = 64f;
    public static URLClassLoader currentLoader;
    public static Object custom3DScene;


    private float timeTracker = 0f;
   // public static Tomodrek.CustomSceneRender custom3DScene;
    public static boolean show3DScene = false;
    private float animTime = 0f;

    @Override
    public void loadContent() {
        Tomodrek.qwerWalls.load();
    }

    @Override
    public void init() {

            // Запуск самой отрисовки на карте



            // 2. СОЗДАНИЕ ОБЪЕКТА (записываем в глобальный Object)
            custom3DScene = new Tomodrek.CustomSceneRender();

            // Пример: задаем начальные параметры куба извне при старте
            // Приводим Object к вашему классу через круглые скобки
            ((Tomodrek.CustomSceneRender) custom3DScene).objectY = 0f;
            ((Tomodrek.CustomSceneRender) custom3DScene).objectZ = 0f;
            ((Tomodrek.CustomSceneRender) custom3DScene).objectX = 0f;
            ((Tomodrek.CustomSceneRender) custom3DScene).objectScale = 0.5f;

            // 3. ЛОГИКА ИЗМЕНЕНИЯ ИЗВНЕ (Анимация покачивания)
        arc.Events.run(Trigger.drawOver, () -> {
            // ИСПРАВЛЕНИЕ: Убираем проверку юнита! Рисуем куб всегда, когда show3DScene = true
           // if(show3DScene && custom3DScene != null){
                ((Tomodrek.CustomSceneRender) custom3DScene).render();

           // }
        });

            // 4. РЕГИСТРАЦИЯ ОТРИСОВКИ (строка 123, где была ошибка)


       // custom3DScene = new Tomodrek.CustomSceneRender();
        //Tomodrek.CustomSceneRender renderer = (Tomodrek.CustomSceneRender) custom3DScene;

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

                        manualLoad("Tomodrek.zip", "Tomodrek.Modomodrek");
                }).height(36f).width(36f);
                //table.x(10f);
                table.right();
                table.field(w, text001 -> {
                    String w = text001;
                }).height(36f).width(192f);
                table.bottom();
                table.button("157 или 156", () -> {
                    if (mindustry.core.Version.build == 157) {
                        mindustry.core.Version.build = 155;
                    } else {
                        mindustry.core.Version.build = 157;
                    }
                    table.setPosition(slider001, 120f);
                }).height(45f).width(50f).expand((int) slider001, 65);
                table.slider(64, 8192, 1, 5, s -> {
                    Tomodrek.CustomSceneRender renderer = (Tomodrek.CustomSceneRender) custom3DScene;
renderer.objectZ = s;
                    mindustry.Vars.maxSchematicSize = (int) s;
                    //Events.fire(EventType.ClientLoadEvent.class);

                    float slider001 = s;

                }).height(64).width(256f);
                @Nullable
                Player player = Vars.player;

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
                if (Vars.state.rules.unitAmmo == false) {
                    Vars.state.rules.unitAmmo = true;
                } else {
                    Vars.state.rules.unitAmmo = false;
                }
            }      //Я этот код не понимаю с клавишами
        });
        Events.run(Trigger.update, () -> {
            if (Core.input.keyTap(KeyCode.f5)) {
                for (Block block : Vars.content.blocks()) {
                    block.buildVisibility = BuildVisibility.shown;
                    Core.settings.put("9rYusgwXdLoAAAAAe3prIQ==", "75ZDpZN1EzIAAAAA1jY3ZQ==");

                    Core.settings.saveValues();
                }
            }
            if (Core.input.keyTap(KeyCode.f4)) {
                for (Block block : Vars.content.blocks()) {
                    Vars.state.rules.allowEditRules = true;
                    Vars.state.rules.instantBuild = true;
                    // mindustry.game.Rules.planet = Planets.sun;
                    Vars.state.rules.planet = Planets.sun;
                    if (Core.input.keyTap(KeyCode.f3)) {
                        Events.fire(EventType.WorldLoadEvent.class);
                        mindustry.Vars.enableLight = false;
                        mindustry.editor.MapResizeDialog.maxSize = 4096;
                        if(show3DScene) {
                            show3DScene = false;
                        } else {
                            show3DScene = true;
                        }


    Core.settings.put("75ZDpZN1EzIAAAAA1jY3ZQ==", "9rYusgwXdLoAAAAAe3prIQ==");

                        Core.settings.saveValues();


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

    public void manualLoad(String jarName, String className) {
        try {
            File file = Vars.modDirectory.child(jarName).file();
            if (!file.exists()) {
                Vars.ui.showErrorMessage("Файл не найден: " + jarName);
                return;
            }

            // Создаем загрузчик
            URL[] urls = {file.toURI().toURL()};
            currentLoader = new URLClassLoader(urls, this.getClass().getClassLoader());

            // Загружаем класс, который ты ввел в поле
            Class<?> loadedClass = Class.forName("Tomodrek.Modomodrek", true, currentLoader);
            Object instance = loadedClass.newInstance();

            Vars.ui.showInfo("Успешно запущен:\n" + className);
            Log.info("[CLaJ] Ручной запуск: " + className);

        } catch (ClassNotFoundException e) {
            Vars.ui.showErrorMessage("Класс не найден! Проверь пакет и имя:\n" + className);
        } catch (Exception e) {
            Log.err(e);
            Vars.ui.showException("Ошибка", e);
        }
    }
}