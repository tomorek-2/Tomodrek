package Tomodrek;
import mindustry.mod.Mod;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.type.Category;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.game.Schematics;
import arc.Events;
import mindustry.game.EventType;
import mindustry.world.Block;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.game.EventType.PlayerChatEvent;
import arc.Input;
import arc.input.KeyCode;
import mindustry.game.EventType.Trigger;
import arc.Core;
import mindustry.game.Rules;
import mindustry.core.World;
import java.lang.reflect.Field;
import mindustry.game.FogControl;

import java.lang.reflect.Modifier;
import arc.util.Log;
import mindustry.mod.Plugin;

import mindustry.game.Schematics;
public class Momodrek001 extends Plugin {
 // @Override
  public void init() {
    mindustry.content.Blocks.vault.requirements(Category.effect, new ItemStack(Items.titanium, 10, Items.thorium, 4000, Items.copper, 2000, Items.lead, 2000));
    mindustry.Vars.maxSchematicSize = 1024;
  }
}
