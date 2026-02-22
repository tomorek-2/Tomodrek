package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.Building;

public class QwerWall extends Wall {
    public QwerWall(String name) {
        super(name);
  update(); 
  health = 450;
 size = 1;
    }

    public class QwerWallBuild extends Building {
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
