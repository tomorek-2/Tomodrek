package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;
import mindustry.Vars;
import mindustry.game.Team;
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


public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();

for (Block block : Vars.content.blocks()) {
    block.buildVisibility = BuildVisibility.shown;
}
} 




    @Override
    public void init() {
Vars.state.rules.unitAmmo = true;
 
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
unit.heal(1f);
unit.addItem(unit.stack.item, 1);
});

Events.run(Trigger.update, () -> {
if(Core.input.keyTap(KeyCode.f6)) {
Player player = Vars.player;
Unit unit = player.unit();
unit.heal(1f);
unit.addItem(unit.stack.item, 1);
    if(Vars.state.rules.unitAmmo == true) {
        Vars.state.rules.unitAmmo = false;
    } 
    else {
         Vars.state.rules.unitAmmo = true;
    }
}      //Я этот код не понимаю с клавишами
});
} 
} 
