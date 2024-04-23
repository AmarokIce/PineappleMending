package club.someoneice.mending;

import club.someoneice.pineapplepsychic.config.ConfigBeanV2;

public class Config {

    static int mendingId = 70;

    public static void init() {
        ConfigBeanV2 config = new ConfigBeanV2(Mending.MODID);

        config.addNote("An ID for enchantment Mending. The 256 is max (in mc default, without any Ids mod.). Default value: 70");
        mendingId = config.getInteger("mendingId", mendingId);
    }
}
