package Tomodrek;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.jar.*;

import arc.math.Mathf;
import arc.util.*;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.UnitTypes;
import mindustry.core.GameState;
import mindustry.editor.MapResizeDialog;
import mindustry.entities.units.BuildPlan;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.*;
import mindustry.mod.Mod;
import mindustry.type.Category;
import mindustry.type.Planet;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;

import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Router;
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
    int www;
    BaseDialog Dialog002;
    String w = "Напиши", ww = "ww";
    float slider001 = 64f;
    String nameField = "Result";
String text005;
    public static Object custom3DScene;
  public static  HashMap<DoubleInt, Integer> TestHeatMap = new HashMap<DoubleInt, Integer>();

    public static boolean show3DScene = false;
    private float timeTracker = 0f;
    // public static Tomodrek.CustomSceneRender custom3DScene;

    private float animTime = 0f;
    float calculatePlus;
    float calculateMinus;
    float calculateNumber;
float calculateSum;
float calculateLastResult;
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


                    float slider001 = s;

                }).height(64).width(256f);
                @Nullable
                Player player = Vars.player;
                table.bottom();
                table.button("", () -> {

                }).with((buttonw) -> {
                    buttonw.getStyle().up = Tex.button;
                    buttonw.getStyle().over = Tex.alphaaaa;
                    buttonw.getStyle().down = Tex.buttonDown;
                });
                // Call.connect(Vars.player.con, "pivomind.pro", 6567);
            });

            Vars.ui.menufrag.addButton("Modomodrek", () -> {
                Dialog001.clear();
                Dialog001.show();
                Dialog001.button("close", ()-> Dialog001.hide()).size(50f, 90f);
                Dialog001.button("-", () -> {
                    calculateMinus++;
                    calculateSum++;
                    calculateLastResult -= calculateNumber;
                    calculateNumber = 0;
                });
                Dialog001.button("+", () -> {
                    calculatePlus++;
                    calculateSum++;
                    calculateLastResult += calculateNumber;
                    calculateNumber = 0;

                });
                Dialog001.button("*", () -> {
                    calculatePlus++;
                    calculateSum++;
                    calculateLastResult *= calculateNumber;
                    calculateNumber = 0;

                });
                Dialog001.button("/", () -> {
                    calculatePlus++;
                    calculateSum++;
                    calculateLastResult /= calculateNumber;
                    calculateNumber = 0;
                });
                Dialog001.button("clear", () -> {
                    calculatePlus++;
                    calculateSum++;
                  calculateLastResult = 0;
                    calculateNumber = 0;
                });
Dialog001.top();
                Dialog001.field("Result: " + calculateLastResult, field->{
                    nameField = "Result" + calculateLastResult;
                }).size(450f, 30f).name(nameField).with(field ->{
                    field.setDisabled(false);
                    field.update(() -> {
                        field.setText("Result: " + calculateLastResult +  " Number: " + calculateNumber);
                    });
                });
                Dialog001.center();
               for(float i = 0; i <= 9; i++) {
                   float finalI = i;
                   Dialog001.button("" + i, () -> {
                       calculateNumber *= 10;
                       calculateNumber += finalI;
                   }).bottom();
               }

            });

                    Vars.ui.settings.addCategory("Калькулятор", Icon.powerOld, table -> {
                        table.clear();

                        table.button("close", ()-> Dialog001.hide()).size(50f, 90f);
                        table.button("-", () -> {
                            calculateMinus++;
                            calculateSum++;
                            calculateLastResult -= calculateNumber;
                            calculateNumber = 0;
                        });
                        table.button("+", () -> {
                            calculatePlus++;
                            calculateSum++;
                            calculateLastResult += calculateNumber;
                            calculateNumber = 0;

                        });
                        table.button("*", () -> {
                            calculatePlus++;
                            calculateSum++;
                            calculateLastResult *= calculateNumber;
                            calculateNumber = 0;

                        });
                        table.button("/", () -> {
                            calculatePlus++;
                            calculateSum++;
                            calculateLastResult /= calculateNumber;
                            calculateNumber = 0;
                        });
                        table.button("clear", () -> {
                            calculatePlus++;
                            calculateSum++;
                            calculateLastResult = 0;
                            calculateNumber = 0;
                        });
                        table.top();
                        table.field("Result: " + calculateLastResult, field->{
                            nameField = "Result" + calculateLastResult;
                        }).size(450f, 30f).name(nameField).with(field ->{
                            field.setDisabled(false);
                            field.update(() -> {
                                field.setText("Result: " + calculateLastResult +  " Number: " + calculateNumber);
                            });
                        });
                        table.center();
                        for(float i = 0; i <= 9; i++) {
                            float finalI = i;
                            table.button("" + i, () -> {
                                calculateNumber *= 10;
                                calculateNumber += finalI;
                            }).bottom();
                        }
                    });
        });

        Events.run(Trigger.update, () -> {
                    if (Core.input.keyTap(KeyCode.end)) {
                        try {
                            mindustry.Vars.mods.load();
                        } catch (Exception e) {
                            arc.util.Log.err(e);
                        }
                    }
            if (Core.input.keyTap(KeyCode.f6)) {
                Player player = Vars.player;
                Unit unit = player.unit();
                //   if (Vars.state.rules.unitAmmo == false) {
                //     Vars.state.rules.unitAmmo = true;
                //   } else {
                //      Vars.state.rules.unitAmmo = false;
                //   }
               // new MapResizeDialogTO((width, height, shiftX, shiftY) -> {
        //        }).show();

                if (Vars.state.rules.editor == false) {
                    Vars.state.rules.editor = true;

                } else {
                    Vars.state.rules.editor = false;

                }
            }



        });
        Events.run(Trigger.update, () -> {
            if (Core.input.keyTap(KeyCode.f5)) {


                for (Block block : Vars.content.blocks()) {
                    block.buildVisibility = BuildVisibility.shown;
                    block.canPickup = true;
                    block.commandable = true;
                    block.canOverdrive = true;

                }
                for (UnitType unit : Vars.content.units()) {
                    unit.hidden = false;
                    unit.useUnitCap = true;

                }
                for (Planet planet : Vars.content.planets()) {
                    planet.visible = true;
                    planet.hideDatabase = false;
                    planet.alwaysUnlocked = true;
                    planet.maxZoom = 299f;

                }
                for (StatusEffect status : Vars.content.statusEffects()) {
                    status.show = true;
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
            if (Core.input.keyTap(KeyCode.f2)) {
                if (task == null) {
                } else {

                    task.cancel();
                }
                Vars.mods.getMod("tomodrek").meta.hidden = true;
            }
           

            if(Core.input.keyTap(KeyCode.plus)) {


            }


        });
        Events.run(Trigger.update, () -> {
            Vars.state.rules.fog = false;
            Vars.state.rules.staticFog = false;
          //  Vars.ios = true;
            Vars.mobile = false;
            MapResizeDialog.minSize = -1;
            Vars.state.rules.schematicsAllowed = true;
            Vars.state.afterGameOver = true;

        });

        Events.run(Trigger.drawOver, () -> {

            arc.graphics.g3d.Camera3D camera = new arc.graphics.g3d.Camera3D();
            camera.position.set(0f, 0f,  www++);

            //camera.lookAt(0, 0, z++);
            camera.update();
        });
        if (Core.app.isAndroid()) {
//androidAcc.androidAcc();
        }


    }

 /*  static class androidAcc {
       static void androidAcc() {
            try {
                android.content.Context context = (android.content.Context) arc.Core.app;
                android.hardware.SensorManager sm = (android.hardware.SensorManager) context.getSystemService(android.content.Context.SENSOR_SERVICE);
                android.hardware.Sensor accel = sm.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);

                android.hardware.SensorEventListener myListener = new android.hardware.SensorEventListener() {
                    @Override
                    public void onSensorChanged(android.hardware.SensorEvent event) {
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        float s = 0.5f;
                        arc.Core.camera.position.y -= y;
                        if (!(z >= 8.5f && z <= 10.5)) {
                            arc.Core.camera.position.x -= x;
                        }
                        arc.Core.camera.update();

                    }

                    @Override
                    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {
                    }
                };

                if (accel != null) {
                    sm.registerListener(myListener, accel, android.hardware.SensorManager.SENSOR_DELAY_GAME);
                }
            } catch (Exception e) {
                arc.util.Log.err("[Tomodrek] Sensor error: " + e.getMessage());
            }
        }
    }
  /*  void TestClassLoader() {
        try  {
            File file = new File("Extra.jar"); URL url = file.toURI().toURL(); URL[] urls = new URL[]{url};
// 2. Создаем сам загрузчик.
// Важно передать текущий ClassLoader как родителя (parent),
// чтобы новый загрузчик видел классы Mindustry.
            URLClassLoader loader = new URLClassLoader(urls, this.getClass().getClassLoader());

// 3. Загружаем нужный нам класс по его полному имени
            Class<?> myClass = loader.loadClass("Tomodrek.ExtraContent");

// 4. Создаем объект этого класса
            Object instance = myClass.getDeclaredConstructor().newInstance();

// 5. Вызываем метод (если знаем имя)
            myClass.getMethod("run").invoke(instance);
        } catch (Exception e) { e.printStackTrace(); }
    } */
    public void testHeat() {
        int xblock = 0;
        int yblock = 0;

        for(int i = 0; i < 365; i++) {
            for(int x = 0; x < 10; x++) {
float xd = Mathf.cosDeg(i);
float yd = Mathf.sinDeg(i);
xd *= x;
yd *= x;
xd += xblock;
yd += yblock;
int xdint = (int) xd;
int ydint = (int) yd;
int Heat = TestHeatMap.getOrDefault(new DoubleInt(xblock, yblock), 0);
if(Heat <= 5) {
    TestHeatMap.put(new DoubleInt(xdint, ydint), Heat + x);
}
            }
        }
    }
}
class DoubleInt {
    public int x;
    public int y;
    public DoubleInt(int x, int y) {
        this.x = x;
        this.y = y;

    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleInt)) return false;
        DoubleInt other = (DoubleInt) o;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }
}

