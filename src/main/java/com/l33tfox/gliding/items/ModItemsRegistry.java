package com.l33tfox.gliding.items;

import com.l33tfox.gliding.Gliding;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItemsRegistry {

    // registers glider items
    public static final Item WOODEN_GLIDER = register("wooden_glider", new GliderItem(-0.26, 1.01, new Item.Settings().maxCount(1)));
    public static final Item STONE_GLIDER = register("stone_glider", new GliderItem(-0.22, 1.02, new Item.Settings().maxCount(1)));
    public static final Item IRON_GLIDER = register("iron_glider", new GliderItem(-0.18, 1.03, new Item.Settings().maxCount(1)));
    public static final Item GOLDEN_GLIDER = register("golden_glider", new GliderItem(-0.16, 1.04, new Item.Settings().maxCount(1)));
    public static final Item DIAMOND_GLIDER = register("diamond_glider", new GliderItem(-0.14, 1.05, new Item.Settings().maxCount(1)));
    public static final Item NETHERITE_GLIDER = register("netherite_glider", new GliderItem(-0.12, 1.06, new Item.Settings().maxCount(1).fireproof()));

    public static void initialize() {
        // get the event for modifying entries in the tools group and register an event handler that adds the mod items.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
            itemGroup.add(ModItemsRegistry.WOODEN_GLIDER);
            itemGroup.add(ModItemsRegistry.STONE_GLIDER);
            itemGroup.add(ModItemsRegistry.IRON_GLIDER);
            itemGroup.add(ModItemsRegistry.GOLDEN_GLIDER);
            itemGroup.add(ModItemsRegistry.DIAMOND_GLIDER);
            itemGroup.add(ModItemsRegistry.NETHERITE_GLIDER);
        });
    }

    // helper method for registering new mod items
    private static Item register(String id, Item item) {
        // create the identifier for the item
        Identifier itemID = Identifier.of(Gliding.MOD_ID, id);

        // register and return the item
        return Registry.register(Registries.ITEM, itemID, item);
    }
}
