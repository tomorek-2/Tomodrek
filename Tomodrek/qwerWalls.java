package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.laserTurret;
import mindustry.world.blocks.defense;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.world.meta.BuildVisibility;
import mindustry.type.Item;        
import mindustry.world.blocks.distribution.Router;

import mindustry.world.blocks.defense.turrets;
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
new selfHealCore("selfHealCore") {{
            health = 500;
            description = "Ядро которое само исцеляется на 1 ХП, если здоровье меньше 1000 ХП то хилиться больше";
            size = 3;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
itemCapacity = 3000;
}};
new laserTurret("SuperArcProMax") {{
size = 2;
buildVisibility = BuildVisibility.shown;
enabled = false;
hasPower = true;
hasLiquid = true;        
  }};
    }
    
                         }
