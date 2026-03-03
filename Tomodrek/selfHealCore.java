package Tomodrek;
import Mindustry.world.blocks.defense.CoreBlock;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.gen.Building;      
import mindustry.type.Item;        
import mindustry.world.blocks.distribution.Router;

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
supersuper.updateTile(); 
    heal(1f);
      if (health < 1000) { 
      heal(15f);
      }
      else {
      } 
    } 
  } 
