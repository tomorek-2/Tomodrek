package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;

public class Modomodrek extends Mod {
    @Override
    public void loadContent() {
        qwerWalls.load();
oreCopper.BuildVisibility = BuildVisibility.shown;
oreCoal.BuildVisibilty = BuildVisibility.shown;
oreLead.BuildVisibility = BuildVisibility.shown;
sand.BuildVisibility = BuildVisibility.shown;
    }
}
