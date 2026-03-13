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

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
Blocks.oreCopper.buildVisibility = BuildVisibility.shown;
Blocks.oreCoal.buildVisibility = BuildVisibility.shown;
Blocks.oreCoal.requirements(Category.defense, ItemStack.with(Items.copper, 500, Items.lead, 500));
Blocks.oreLead.buildVisibility = BuildVisibility.shown;
Blocks.sand.buildVisibility = BuildVisibility.shown;
Blocks.sand.requirements(Category.defense, ItemStack.with(Items.copper, 500, Items.lead, 500, Items.coal, 450));
Blocks.oreTitanium.buildVisibility = BuildVisibility.shown;
Blocks.oreTitanium.requirements(Category.defense, ItemStack.with(Items.copper, 650, Items.lead, 650, Items.coal, 500, Items.sand, 500));

Blocks.removeOre.buildVisibility = BuildVisibility.shown; 
Blocks.oreThorium.buildVisibility = BuildVisibility.shown;
Blocks.oreThorium.requirements(Category.defense, ItemStack.with(Items.titanium, 2500, Items.plastanium, 1250));
Blocks.space.buildVisibility = BuildVisibility.shown;
Blocks.basalt.buildVisibility = BuildVisibility.shown;
for (Block block : content.blocks()) {
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
   
} 
}
