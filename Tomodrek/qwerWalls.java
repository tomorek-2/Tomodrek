package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.world.meta.BuildVisibility;


public class qwerWalls extends Mod {
    
    public static void load() {
        
        Wall qwertyWall = new Wall("qwerWall") {{
            health = 50;
            size = 1;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
            
        }};
        new qwerWall("qwerWallHeal") {{
            health = 500;
            size = 1;
            category = Category.logic;
            buildVisibility = BuildVisibility.shown;
            
        }};
new PortalBlock("PortalBlocktoIP") {{
            health = 500;
            description = "Этот блок отправляет игрока на сервер, пока не работает";
            size = 1;
            category = Category.logic;
            buildVisibility = BuildVisibility.shown;
            
        }};
    }
    
                         }
