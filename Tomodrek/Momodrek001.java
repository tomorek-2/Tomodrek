package Tomodrek;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.game.Schematics;
public class Momodrek001 extends Plugin {
  @Override
  public void init() {
    //Blocks.vault.requirements(Category.effect, ItemStack.with(Items.copper, 2000, Items.lead, 2000, Items.thorium, 4000));
   // mindustry.Vars.maxSchematicSize = 1024;
    Timer.schedule(() -> {
      for (Player player : Groups.player) {


        player.sendMessage("Переходите на канал ТГ @gncjbo");
      }
    }, 5f, 600f);
  }
}
