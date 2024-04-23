package club.someoneice.mending;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class MendingEnchantment extends Enchantment {
    public MendingEnchantment() {
        super(Config.mendingId, -1, EnumEnchantmentType.breakable);
        this.setName("mending");
    }

    @Override
    public int getMinEnchantability(int weight) {
        return -1;
    }

    @Override
    public int getMaxEnchantability(int weight) {
        return -1;
    }
}
