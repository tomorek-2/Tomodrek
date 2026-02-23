package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.Building;

public class qwerWall extends Wall {
    public qwerWall(String name) {
        super(name);
  
  health = 450;
 size = 1;
    }

    public class qwerWallBuild extends Building {
        @Override
        public void update() {
            super.update();
            if (health() < 300) {
                heal(1f);
            } else {
                
            }
        }
    }
}
