package Tomodrek;

import mindustry.mod.Mod;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.BuildVisibility;


public class TomodrekBlocks extends Mod {
    public static String[] nameWall001 = {"blue-stone-wall","blue-stone-crystall"};
    public static String[] nameWall002 = { ""};
    public static void load() {
        
        Wall qwertyWall = new Wall("qwerWall") {{
            health = 50;
            size = 1;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
            
        }};
        new Tomodrek.qwerWall("qwerWallHeal") {{
            health = 500;
            size = 1;
            category = Category.logic;
            buildVisibility = BuildVisibility.shown;
            
        }};
new Tomodrek.selfHealCore("selfHealCore") {{
            health = 5000;
            description = "Ядро которое само исцеляется на 1 ХП, если здоровье меньше 1000 ХП то хилиться больше";
            size = 3;
            category = Category.defense;
            buildVisibility = BuildVisibility.shown;
itemCapacity = 3000;
}};
new Tomodrek.BadSolarPanel("BadSolarPanel") {{
health = 25;
description = "Солнечная панель которая из хлама разрушается сама по себе но пока единственный вариант";
category = Category.power;
size = 1;

     
            buildVisibility = BuildVisibility.shown;
            powerProduction = 1f;
}};
new Tomodrek.KeyBoardBlock("keyboardlogicinputblock") {{
health = 500;
size = 1;
buildVisibility = BuildVisibility.shown;
category = Category.logic;
}};
 for(String name : nameWall001) {
new StaticWall(name) {{
    size = 1;
    variants = 2;
}};

 }
 for(String name : nameWall002) {
     new StaticWall(name) {{
         size = 1;
         variants = 1;
     }};
 }
    }
    
                         }
