package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
Blocks.oreCopper.BuildVisibility = BuildVisibility.shown;
Blocks.oreCoal.BuildVisibilty = BuildVisibility.shown;
Blocks.oreLead.BuildVisibility = BuildVisibility.shown;
Blocks.sand.BuildVisibility = BuildVisibility.shown;
    }
}
