package com.unsoldriceball.gundamagedecreaser;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jline.utils.Log;



@Mod(modid = Main.MOD_ID, acceptableRemoteVersions = "*")
public class Main {

    public static final String MOD_ID = "gundamagedecreaser";
    private static final float DAMAGE_FACTOR = 0.575f;
    private static final String[] TARGETS = new String[]
            {
                    "gvclib",
                    "gvcr2"
            };
    private static final String[] MORE_DECREASE_TARGETS = new String[]
            {
                    "vehicle"
            };



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    { //ModがInitializeを呼び出す前に発生するイベント。
        MinecraftForge.EVENT_BUS.register(this); //これでこのクラス内でForgeのイベントが動作するようになるらしい。
    }



    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (event.getEntity().world.isRemote) return;
        // ダメージを受けたエンティティが存在するか
        if (event.getEntityLiving() != null) {
            // ダメージを与えたエンティティを取得
            Entity attacker = event.getSource().getImmediateSource();

            if (attacker != null)
            {
                String attacker_id = attacker.getClass().toString();
                if (!isContainID(attacker_id, TARGETS)) return;      //ダメージ抑制の対象でないEntityならreturn

                // ダメージを(DAMAGE_FACTOR)%に減少させる
                float reducedDamage = event.getAmount() * DAMAGE_FACTOR;
                //もしMORE_DECREASE_TARGETSにも含まれるなら更に(DAMAGE_FACTOR)%に減少させる。
                if (isContainID(attacker_id, MORE_DECREASE_TARGETS))
                {
                    reducedDamage *= DAMAGE_FACTOR;
                }
                event.setAmount(reducedDamage);
            }
        }
    }



    //引数の文字列にtargetsのいずれかが含まれていたらtrueを返す関数。
    private boolean isContainID(String id, String[] targets)
    {
        for (String tar: targets)
        {
            if (id.contains(tar))
            {
                return true;
            }
        }
        return false;
    }
}
