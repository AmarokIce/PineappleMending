package club.someoneice.mending;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Mending.MODID, name = Mending.NAME, useMetadata = true)
public class Mending {
    public static final String MODID = "mending";
    public static final String NAME = "Pineapple Mending";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOG = LogManager.getLogger(NAME);

    @Mod.Instance(MODID)
    public static Mending INSTANCE;

    public static MendingEnchantment MENDING;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE = this;

        if (Loader.isModLoaded("pineapple_psychic")) Config.init();

        MinecraftForge.EVENT_BUS.register(this);
        MENDING = new MendingEnchantment();

        Enchantment.addToBookList(MENDING);
    }

    @SubscribeEvent
    public void onPlayerGetEXP(PlayerPickupXpEvent event) {
        EntityPlayer player = event.entityPlayer;
        EntityXPOrb expOrb = event.orb;
        int exp = expOrb.xpValue;

        final ItemStack[] items = new ItemStack[]{
                player.getHeldItem(),
                player.getCurrentArmor(0),
                player.getCurrentArmor(1),
                player.getCurrentArmor(2),
                player.getCurrentArmor(3)
        };

        for (final ItemStack item : items) {
            if (item == null) continue;
            final int lessXp = fixItem(item, exp);
            if (lessXp == -1) continue;
            if (lessXp == 0) {
                expOrb.xpValue = 0;
                break;
            }
            exp = lessXp;
        }
    }

    private int fixItem(ItemStack item, int exp) {
        boolean flag = EnchantmentHelper.getEnchantments(item).containsKey(Config.mendingId);
        if (!flag) return -1;

        int value = exp - item.getItemDamage();
        item.setItemDamage(Math.abs(Math.min(0, value)));
        return Math.max(value, 0);
    }
}
