package com.unsoldriceball.gundamagedecreaser;


import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;




@Mod(modid = GDDMain.ID_MOD, acceptableRemoteVersions = "*")
public class GDDMain
{
    public static final String ID_MOD = "gundamagedecreaser";


    private static final String[] TARGETS = new String[]
            {
                    "gvclib",
                    "gvcr2"
            };
    private static final String[] MORE_DECREASE_TARGETS = new String[]
            {
                    "vehicle"
            };




    //ModがInitializeを呼び出す前に発生するイベント。
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //これでこのクラス内でForgeのイベントが動作するようになるらしい。
        MinecraftForge.EVENT_BUS.register(this);
    }




    //Entityが攻撃を受けたときのイベント。
    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        //現在の処理がサーバー側かつダメージを受けたエンティティが存在するか
        if (event.getEntity().world.isRemote) return;
        if (event.getEntityLiving() != null)
        {

            final Entity _ATTACKER = event.getSource().getImmediateSource();        // ダメージを与えたエンティティを取得

            if (_ATTACKER != null)
            {
                final String _ID_ATTACKER = _ATTACKER.getClass().toString();

                //ダメージ抑制の対象かどうか
                if (isContainID(_ID_ATTACKER, TARGETS))
                {

                    float _reduced_damage = event.getAmount() * GDDConfig.damageMultiplier;       // ダメージを(DAMAGE_FACTOR)%に減少させる

                    //もしMORE_DECREASE_TARGETSにも含まれるなら更に(damageMultiplier)%に減少させる。
                    if (isContainID(_ID_ATTACKER, MORE_DECREASE_TARGETS))
                    {
                        _reduced_damage *= GDDConfig.damageMultiplier;
                    }
                    event.setAmount(_reduced_damage);
                }
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
