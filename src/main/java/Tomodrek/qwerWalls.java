package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.world.meta.BuildVisibility;


public class qwerWalls extends Mod {
    @Override
    public void loadContent() {
        
        Wall qwertyWall = new Wall("qwerWall") {{
            health = 50;
            size = 1;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
            
        }};
        new QwerWall("qwerWallHeal") {{
            health = 50f;
            size = 1;
            category = Category.logic;
        }};
    }
                         }
