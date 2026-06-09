ггpackage Tomodrek;

import mindustry.content.Items;
import mindustry.mod.Mod;
import mindustry.type.Item;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.BuildVisibility;


public class TomodrekBlocks extends Mod {
    public static String[] nameWall001 = {"blue-stone-wall","blue-stone-crystall", "crystal-wall"};
    public static String[] nameFloor002 = { "blue-stone-floor"};
    public static String[] nameProp003 = {"blue-stone-boulder"};
    public static String[] nameFloor004 = {"crystall-floor"};
    public static String[] nameItem005 = {"afatite", "blue-stone", "dark-scrap", "crystall-cluster"};
    public static Item afatite, blueStone, darkScrap, crystallCluster;
    public static void load() {



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
        for (String name : nameWall001) {
            new StaticWall(name) {{
                size = 1;
                variants = 2;
            }};

        }
        for (String name : nameFloor002) {
            new Floor(name) {{
                size = 1;
                variants = 5;
            }};
        }

        for (String name : nameProp003) {
            new Prop(name) {{
                size = 1;
                variants = 2;
            }};
        }
        for (String name : nameFloor004) {
          new Floor(name) {{
                size = 1;
                variants = 3;
            }};
        }
    /*    for (String name : nameItem005) {
            new Item(name, arc.graphics.Color.valueOf("172C26FF")) {{
                if(name.equals("blue-stone")) {
                    radioactivity = 115f;
                }
                if(name.equals("afatite")) {
                    radioactivity = 115f;
                }
                if(name.equals("crystall-cluster")) {
                    radioactivity = 0.2f;
                }
            }};
        } */
        afatite = new mindustry.type.Item("afatite", arc.graphics.Color.valueOf("4F8BFF")) {{
            flammability = 0f;
            radioactivity = 0.5f;
        }};


        blueStone = new mindustry.type.Item("blue-stone", arc.graphics.Color.valueOf("8B67C9")) {{
            flammability = 0f;
            charge = 0.0001f;
            radioactivity = 0f;
        }};


        darkScrap = new mindustry.type.Item("dark-scrap", arc.graphics.Color.valueOf("4F7366")) {{
            flammability = 0f;
        }};


        crystallCluster = new mindustry.type.Item("crystall-cluster", arc.graphics.Color.valueOf("6767C9")) {{
            flammability = 0f;
            charge = 0.5f;
            radioactivity = 0.2f;
        }};

    }


    }
    

