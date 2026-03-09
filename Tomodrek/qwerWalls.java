package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.world.meta.BuildVisibility;
import mindustry.type.Item;        
import mindustry.world.blocks.distribution.Router;

import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.entities.bullet.ContinuousLaserBulletType;

import mindustry.content.Liquids;
import mindustry.world.consumers.ConsumePower;

import mindustry.Vars;



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
new BadSolarPanel("BadSolarPanel") {{
health = 25;
description = "Солнечная панель которая из хлама разрушается сама по себе но пока единственный вариант";
category = Category.power;
size = 1;

     category = Category.defense;
            buildVisibility = BuildVisibility.shown;
            powerProduction = 1f;
}};
 
    }
    
                         }
