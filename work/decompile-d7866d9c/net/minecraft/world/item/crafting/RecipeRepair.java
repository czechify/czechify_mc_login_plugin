package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;

public class RecipeRepair extends IRecipeComplex {

    public RecipeRepair(MinecraftKey minecraftkey) {
        super(minecraftkey);
    }

    public boolean a(InventoryCrafting inventorycrafting, World world) {
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < inventorycrafting.getSize(); ++i) {
            ItemStack itemstack = inventorycrafting.getItem(i);

            if (!itemstack.isEmpty()) {
                list.add(itemstack);
                if (list.size() > 1) {
                    ItemStack itemstack1 = (ItemStack) list.get(0);

                    if (itemstack.getItem() != itemstack1.getItem() || itemstack1.getCount() != 1 || itemstack.getCount() != 1 || !itemstack1.getItem().usesDurability()) {
                        return false;
                    }
                }
            }
        }

        return list.size() == 2;
    }

    public ItemStack a(InventoryCrafting inventorycrafting) {
        List<ItemStack> list = Lists.newArrayList();

        ItemStack itemstack;

        for (int i = 0; i < inventorycrafting.getSize(); ++i) {
            itemstack = inventorycrafting.getItem(i);
            if (!itemstack.isEmpty()) {
                list.add(itemstack);
                if (list.size() > 1) {
                    ItemStack itemstack1 = (ItemStack) list.get(0);

                    if (itemstack.getItem() != itemstack1.getItem() || itemstack1.getCount() != 1 || itemstack.getCount() != 1 || !itemstack1.getItem().usesDurability()) {
                        return ItemStack.b;
                    }
                }
            }
        }

        if (list.size() == 2) {
            ItemStack itemstack2 = (ItemStack) list.get(0);

            itemstack = (ItemStack) list.get(1);
            if (itemstack2.getItem() == itemstack.getItem() && itemstack2.getCount() == 1 && itemstack.getCount() == 1 && itemstack2.getItem().usesDurability()) {
                Item item = itemstack2.getItem();
                int j = item.getMaxDurability() - itemstack2.getDamage();
                int k = item.getMaxDurability() - itemstack.getDamage();
                int l = j + k + item.getMaxDurability() * 5 / 100;
                int i1 = item.getMaxDurability() - l;

                if (i1 < 0) {
                    i1 = 0;
                }

                ItemStack itemstack3 = new ItemStack(itemstack2.getItem());

                itemstack3.setDamage(i1);
                Map<Enchantment, Integer> map = Maps.newHashMap();
                Map<Enchantment, Integer> map1 = EnchantmentManager.a(itemstack2);
                Map<Enchantment, Integer> map2 = EnchantmentManager.a(itemstack);

                IRegistry.ENCHANTMENT.g().filter(Enchantment::c).forEach((enchantment) -> {
                    int j1 = Math.max((Integer) map1.getOrDefault(enchantment, 0), (Integer) map2.getOrDefault(enchantment, 0));

                    if (j1 > 0) {
                        map.put(enchantment, j1);
                    }

                });
                if (!map.isEmpty()) {
                    EnchantmentManager.a((Map) map, itemstack3);
                }

                return itemstack3;
            }
        }

        return ItemStack.b;
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return RecipeSerializer.o;
    }
}
