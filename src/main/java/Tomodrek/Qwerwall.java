package tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.world.meta.BuildVisibility;

public class TomodrekMod extends Mod {
    @Override
    public void loadContent() { 
        
        qwertyWall= new Wall("qwerWall") {{
            health = 300;
            size = 1;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
            requirements(Category.defense, ItemStack.with(Items.copper, 20));
        }};

        
        }
