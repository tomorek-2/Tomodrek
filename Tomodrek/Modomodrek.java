package Tomodrek;

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
import arc.Core;
import mindustry.game.Rules;
import mindustry.core.World;
import java.lang.reflect.Field;
import mindustry.game.FogControl;

import java.lang.reflect.Modifier;
import arc.util.Log;

import mindustry.game.Schematics;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();


} 




    @Override
    public void init() {
        
            try {
        Field w01w = Vars.class.getDeclaredField("discordURL");
        w01w.setAccessible(true);
            w01w.set(null, "github.com/tomorek-2/Tomodrek");
            } catch (Exception e) {
    e.printStackTrace();
        }
        
   mindustry.Vars.maxSchematicSize = 1024;

 
        Events.on(EventType.WorldLoadEvent.class, event -> {
            Vars.state.rules.unitAmmo = true;
System.out.println("unitAmmo = true");
            for (Team team : Team.all) {
                Vars.state.rules.teams.get(team).infiniteAmmo = false;
            }
        });
Events.on(PlayerChatEvent.class, event -> {
Player player = event.player;
Unit unit = player.unit();
    if(unit == null) {}
    else {
        
unit.heal(1f);
unit.addItem(unit.stack.item, 1);
    }
});

Events.run(Trigger.update, () -> {
if(Core.input.keyTap(KeyCode.f6)) {
Player player = Vars.player;
Unit unit = player.unit();

    if(Vars.state.rules.unitAmmo == true) {
        Vars.state.rules.unitAmmo = false;
    } 
    else {
         Vars.state.rules.unitAmmo = true;
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
    try {
    Field field = FogControl.class.getDeclaredField("enabled");
    field.setAccessible(true);
    field.set(Vars.fogControl, false);
} catch (Exception e) {
    e.printStackTrace();
}

    }
         }
        

         }
     });
       
    Events.run(Trigger.update, () -> {
        if (Vars.state.rules.fog) {
            Vars.state.rules.fog = false;
            
        }
    });

} 
    
} 
