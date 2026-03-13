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
Unit unit = player.unit();
unit.heal(1f);
});
}
}
