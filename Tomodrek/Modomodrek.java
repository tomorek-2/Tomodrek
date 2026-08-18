package Tomodrek;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.jar.*;

import arc.math.Mathf;
import arc.util.*;
import arc.struct.Seq;

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

import mindustry.ui.fragments.HudFragment;
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
    HudFragment fragment001;
    int www;
    int timeWWW;
    BaseDialog Dialog002;
    String w = "Напиши", ww = "ww";
    float slider001 = 64f;
    String nameField = "Result";
    String text005;
    public static Object custom3DScene;


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
    public Tomodrek.VoiceChat001 voice;
    public Tomodrek.VoiceChat001 voiceAndroid;

    @Override
    public void loadContent() {
       // Tomodrek.TomodrekBlocks.load();
    }

    @Override
    public void init() {
        // 1. Инициализация подсистемы Momodrek003 (Серверная часть)
        Tomodrek.Momodrek003.init();
        
        // 2. Инициализация ГЧ в зависимости от платформы
        if (Core.app.isAndroid()) {
           voiceAndroid = new Tomodrek.VoiceChat001();
        } else {
            voice = new Tomodrek.VoiceChat001();
        }
        
        mindustry.net.Administration.Config.packetSpamLimit.set(1000);
        arc.Events.on(EventType.WorldLoadEvent.class, event -> {
        });
        arc.Events.on(mindustry.game.EventType.ClientLoadEvent.class, event -> {
            try {
                if (mindustry.Vars.ui != null && mindustry.Vars.ui.editor != null) {
                    java.lang.reflect.Field dialogField = mindustry.editor.MapEditorDialog.class.getDeclaredField("resizeDialog");
                    dialogField.setAccessible(true);
                    MapResizeDialogTO myCustomDialog = new MapResizeDialogTO((width, height, shiftX, shiftY) -> {
                        mindustry.Vars.editor.resize(width, height, shiftX, shiftY);
                    });
                    dialogField.set(mindustry.Vars.ui.editor, myCustomDialog);
                    arc.util.Log.info("[Tomodrek] resizeDialog взломан!");
                }
            } catch (Exception e) {
                arc.util.Log.err("[Tomodrek] Ошибка рефлексии: " + e.getMessage());
            }
        });

        Events.on(EventType.ClientLoadEvent.class, event -> {


            Dialog001 = new BaseDialog("Меню мода");
            Dialog001.addCloseButton();

            Vars.ui.settings.addCategory("Расширенные возможности", Icon.logic, table -> {
                table.button("Пауза", () -> {
                    if (Vars.state.isPaused()) Vars.state.set(GameState.State.playing);
                    else Vars.state.set(GameState.State.paused);
                }).width(96f).height(32f);
                table.row();
                table.button("Другое", () -> {
                    Vars.maxSchematicSize = 4096;
                    Vars.state.rules.planet = Planets.sun;
                }).height(36f).width(36f);
                table.right();
                table.field(w, text001 -> {
                    w = text001;
                }).height(36f).width(192f);
                table.bottom();
                table.button("Reset Build (159)", () -> {
                    mindustry.core.Version.build = 159;

                }).height(45f).width(120f);
                table.button("Включить запись микрофона", () ->{
                    if(!Core.app.isAndroid()) {
                        if (voice.recording) {
                            voice.stop();
                            Vars.ui.hudfrag.showToast("Микрофон ВЫКЛ");
                        } else {
                            if (voice.start()) {
                                Vars.ui.hudfrag.showToast("Микрофон ВКЛ");
                            }
                        }
                    } else {
                        if (voiceAndroid.recording) {
                            voiceAndroid.stop();
                            Vars.ui.hudfrag.showToast("Микрофон ВЫКЛ");
                        } else {
                            if (voiceAndroid.start()) {

                                Vars.ui.hudfrag.showToast("Микрофон ВКЛ");
                            }
                        }
                    }
                });
                table.slider(64, 8192, 1, slider001, s -> {
                    mindustry.Vars.maxSchematicSize = (int) s;
                    slider001 = s;
                }).height(64).width(256f);
            });

        });

        Events.run(Trigger.update, () -> {
            if (Core.input.keyTap(KeyCode.end)) {
                try {
                    mindustry.Vars.mods.load();
                } catch (Exception e) {
                    Log.err(e);
                }
            }
            if (Core.input.keyTap(KeyCode.f6)) {
                Vars.state.rules.editor = !Vars.state.rules.editor;
            }

            if (Core.input.keyTap(KeyCode.f5)) {
                for (Block block : Vars.content.blocks()) {
                    block.buildVisibility = BuildVisibility.shown;
                    block.canPickup = true;
                    block.unlock();

                }
                for (UnitType unit : Vars.content.units()) unit.hidden = false;

                for (Planet planet : Vars.content.planets()) {
                    planet.visible = true;
                    planet.unlock();
                }
            }

            if (Core.input.keyTap(KeyCode.f4)) {
                Vars.state.rules.allowEditRules = true;
                Vars.state.rules.instantBuild = true;
                Vars.state.rules.planet = Planets.sun;


                if (Core.input.keyTap(KeyCode.f3)) {
                    mindustry.Vars.enableLight = false;
                    mindustry.editor.MapResizeDialog.maxSize = 4096;
                }
            }
            if (Core.input.keyTap(KeyCode.f2)) {
                if (task != null) task.cancel();
                Vars.mods.getMod("tomodrek").meta.hidden = true;
            }
            if (Core.input.keyTap(KeyCode.f9)) {
                applyPatch2();
Call.sendChatMessage("/shop");
                Call.sendChatMessage("/amenu");
            }
            if (Core.input.keyTap(KeyCode.plus)) {
                task = Timer.schedule(() -> {
                    Vars.player.team(Team.get(Vars.player.team().id + 1));
                }, 0f, 0.01f);
                Timer.schedule(() -> {
                    if (task != null) task.cancel();
                }, 10f);
            }

            if (Core.input.keyTap(KeyCode.f10)) {

                if(voice.recording) {
                    voice.stop();
                    Vars.ui.hudfrag.showToast("Микрофон ВЫКЛ");
                } else {
                    if(voice.start()) {
                        Vars.ui.hudfrag.showToast("Микрофон ВКЛ");
                    }
                }
            }

            Vars.state.rules.fog = false;
            Vars.state.rules.staticFog = false;
            MapResizeDialog.minSize = -1;
            Vars.state.rules.schematicsAllowed = true;
            Vars.state.tick = 1500;
            
            // Вызываем update ГЧ каждый кадр для минимальной задержки
            if (voice != null) if(!Core.app.isAndroid()) {voice.update(); } else {voice.update(); } else { if(Core.app.isAndroid()) {voiceAndroid.update(); } else {voice.update(); } }

        });
    }

    public void applyPatch2() {
        // Список изменений: { "имя юнита", "имя новой текстуры ячейки" }
        String[][] unitsToPatch = {
                {"evoke", "vanquish-cell"},
                {"gamma", "vanquish-cell"},
                {"quasar", "precept-weapon-cell"},
                {"dp-healer-spider", "impact-reactor-plasma-0"},
                {"mega", "vanquish-cell"},
                {"dp-trucker", "corvus-cell"}
        };

        for (String[] pair : unitsToPatch) {
            String unitName = pair[0];
            String regionName = pair[1];

            // Ищем юнит по внутреннему имени
            mindustry.type.UnitType unit = mindustry.Vars.content.getByName(mindustry.ctype.ContentType.unit, unitName);

            if (unit != null) {
                // Ищем текстуру в атласе игры
                arc.graphics.g2d.TextureRegion region = arc.Core.atlas.find(regionName);

                if (region != null && region.found()) {
                    unit.cellRegion = region;
                    arc.util.Log.info("[Tomodrek-Patch2] Патч применен для: " + unitName);
                } else {
                    arc.util.Log.warn("[Tomodrek-Patch2] Текстура '" + regionName + "' не найдена!");
                }
            } else {
                arc.util.Log.warn("[Tomodrek-Patch2] Юнит '" + unitName + "' не найден в контенте!");
            }
        }
    }
}
