package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
blocks.oreCopper.BuildVisibility = BuildVisibility.shown;
blocks.oreCoal.BuildVisibilty = BuildVisibility.shown;
blocks.oreLead.BuildVisibility = BuildVisibility.shown;
blocks.sand.BuildVisibility = BuildVisibility.shown;
    }
}
