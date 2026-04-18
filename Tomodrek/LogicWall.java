package Tomodrek;



import arc.util.Timer;
import mindustry.gen.Call;
import mindustry.world.blocks.defense.Wall;
import mindustry.type.Category;
import mindustry.content.Items;
import mindustry.gen.Building;      
import mindustry.type.Item;        
import mindustry.world.blocks.distribution.Router;
import mindustry.game.EventType;
import mindustry.game.EventType.*;
import arc.Events;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.logic.MemoryBlock;

import mindustry.world.meta.BuildVisibility;
import arc.util.io.Writes;
import arc.util.io.Reads;
import mindustry.gen.Player;


public class LogicWall extends Wall {
    public LogicWall(String name) {
        super(name);
  update = true;
configurable = true; 
solid = true;
  health = 450;
 size = 1;
        itemCapacity = 30;
   }

    public class LogicWallBuild extends Building {
        @Override
        public void updateTile() {
            super.updateTile();
            Events.on(EventType.PlayerChatEvent.class, event -> {

                String mess1 = event.message;
                Player Player1 = event.player;
                if ("О великий Томорек, спаси наши грешные души, вылечи наш блок.".equals(mess1)) {
                    if (Player1 == null) {
                    } else {
                        health += 2.5f;


                    }
                }

            });

        }
            }

    


            

         
            
        }




