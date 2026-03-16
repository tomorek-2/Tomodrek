package Tomodrek;



import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.gen.Building;      
import mindustry.type.Item;        
import mindustry.world.blocks.distribution.Router;
import mindustry.game.EventType;
import arc.Events;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.logic;
import mindustry.world.blocks.logic.MemoryBlock;
     
import mindustry.world.meta.BuildVisibility;


public class qwerWall extends MemoryBlock {
    public qwerWall(String name) {
        super(name);
  update = true;
configurable = true; 
solid = true;
  health = 450;
 size = 1;
hasItems = true;
        itemCapacity = 30;
    }
@Override
public boolean canPlaceOn(Tile tile, Team team, int rotation) {
    return team == Team.crux && super.canPlaceOn(tile, team, rotation);
}
    public class qwerWallBuild extends MemoryBuild {
        @Override
        public void updateTile() {
            super.updateTile();
          //  if (health() < 300) {
        //        heal(1f);
    //        } 
double valueInt = memory[0];
if(valueInt < 100) {
double valueInt0 = valueInt / 100;
heal(valueInt0);
} 
                
             
             
            int CountThorium = items.get(Items.thorium);
             if (CountThorium > 20) {
                  heal(2f);
            }
             
           
        } 
            @Override
        public boolean acceptItem(Building source, Item item) {
            
            return item == Items.thorium && items.get(item) < itemCapacity;
               }

        }
 
    
}
