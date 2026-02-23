package Tomodrek;

import mindustry.world.blocks.defense.Wall;
import mindustry.gen.Building;

public class qwerWall extends Wall {
    public qwerWall(String name) {
        super(name);
  update = true;
  health = 450;
 size = 1;
    }

    public class qwerWallBuild extends Building {
        @Override
        public void updateTile() {
            super.updateTile();
            if (health() < 300) {
                heal(1f);
            } else {
                damage(1f);
                
            }
            @Override
        public boolean acceptItem(Building source, Item item) {
            
            return item == Items.thorium && items.get(item) < itemCapacity;
                }
        }
    }
}
