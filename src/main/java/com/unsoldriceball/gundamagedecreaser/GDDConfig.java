package com.unsoldriceball.gundamagedecreaser;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.unsoldriceball.gundamagedecreaser.GDDMain.ID_MOD;




@Config(modid = ID_MOD)
public class GDDConfig
{
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0.0d, max = 1.0d)
    public static float damageMultiplier = 0.15f;



    @Mod.EventBusSubscriber(modid = ID_MOD)
    private static class EventHandler
    {
        //Configが変更されたときに呼び出される。変更を適用する関数。
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(ID_MOD))
            {
                ConfigManager.sync(ID_MOD, Config.Type.INSTANCE);
            }
        }
    }
}