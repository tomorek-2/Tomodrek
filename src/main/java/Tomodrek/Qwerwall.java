package Tomodrek;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;

public class Tomodrek extends Mod {
    @Override
    public void loadContent() {
        // Здесь будут инициализироваться ваши блоки.
        // Рекомендуется вынести их в отдельный вложенный класс (например, MyBlocks)
        // и вызывать MyBlocks.load();
        qwerwall.load();
    }

    /** Класс, содержащий все блоки мода. */
    public static class qwerwall {
        // Пример объявления блока (раскомментируйте и настройте под себя)
        // public static Block myWall;

        public static void load() {
           package Tomodrek;  // или твой пакет


    qwerWall new Wall("Qwerwall" ) {
      
        health = 50;    
        size = 1;        
    }
}
            
        }
    }
  }
