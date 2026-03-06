package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;

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

Blocks.removeOre.buildVisibility = BuildVisibility.shown; 
Blocks.oreThorium.buildVisibility = BuildVisibility.shown; 
    }
}
