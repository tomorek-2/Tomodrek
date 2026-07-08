package Tomodrek;
//import mindustry.world.blocks.defense;
import static Tomodrek.Modomodrek.TestHeatMap;

import arc.math.Mathf;
import arc.util.Log;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.gen.Building;      
import mindustry.type.Item;        
import mindustry.world.blocks.storage.CoreBlock;

//import mindustry.world.blocks.defense.CoreBlock;

import mindustry.world.meta.BuildVisibility;
public class selfHealCore extends CoreBlock {
  public selfHealCore(String name) { 
    super(name);
    health = 4500;
    size = 3;
  } 
  public class selfHealCoreBuilding extends CoreBuild {
    @Override 
    public void updateTile() {
super.updateTile(); 
    heal(1f);
      if (health < 1000) { 
      heal(15f);
      }
      else {
      }
      int xblock = tile.x;
      int yblock = tile.y;

      for(int i = 0; i < 700; i += 3) {
        for(int x = 0; x < 10; x++) {
          float xd = Mathf.cosDeg(i) * x + xblock;
          float yd = Mathf.sinDeg(i) * x + yblock;

          int xdint = (int) xd;
          int ydint = (int) yd;

          Tomodrek.DoubleInt pos = new Tomodrek.DoubleInt(xdint, ydint);
          int Heat = TestHeatMap.getOrDefault(pos, 0);


          int power = 12 - x;

          if(Heat <= 100) {
            TestHeatMap.put(pos, Heat + power);
          }
        }
      }
    } 
  } 
} 
