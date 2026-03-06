package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
Blocks.oreCopper.buildVisibility = BuildVisibility.shown;
Blocks.oreCoal.buildVisibility = BuildVisibility.shown;
Blocks.oreLead.buildVisibility = BuildVisibility.shown;
Blocks.sand.buildVisibility = BuildVisibility.shown;
Blocks.oreTitanium.buildVisibility = BuildVisibility.shown;
Blocks.removeOre.buildVisibility = BuildVisibility.shown; 
Blocks.oreThorium.buildVisibility = BuildVisibility.shown; 
    }
}
