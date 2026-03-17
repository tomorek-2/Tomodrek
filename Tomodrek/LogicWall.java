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
import mindustry.world.blocks.logic.MemoryBlock;

import mindustry.world.meta.BuildVisibility;


public class LogicWall extends MemoryBlock {
    public LogicWall(String name) {
        super(name);
  update = true;
configurable = true; 
solid = true;
  health = 450;
 size = 1;
   }

    public class LogicWallBuild extends MemoryBuild {
        @Override
        public void updateTile() {
            super.updateTile();
          //  if (health() < 300) {
        //        heal(1f);
    //        } 
double valueInt = memory[0];
if(valueInt < 100) {
double valueInt0 = valueInt / 100;
heal((float)valueInt0);
} 
    

        @Override
        public void write(Writes write){
            super.write(write);
            write.b(0);
        }

        @Override
        public void read(Reads read, byte version){
            super.read(read, version);
            read.b();
        }
    


            

        } 
            
        }


}
