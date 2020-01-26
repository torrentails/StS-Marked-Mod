package MarkedMod.patches.stances;

import MarkedMod.stances.DanceOfDeathStance;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static MarkedMod.MarkedMod.logger;

public class AbstractStancePatch
{

    @SpirePatch(clz = AbstractStance.class,
                method = "getStanceFromName")
    public static class getStanceFromName
    {
        public static SpireReturn<AbstractStance> Prefix(String stanceID)
        {
            if (stanceID.equals(DanceOfDeathStance.STANCE_ID))
            {
                return SpireReturn.Return(new DanceOfDeathStance());
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractStance.class,
                method = "atDamageGive",
                paramtypez = {
                    float.class,
                    DamageInfo.DamageType.class,
                })
    public static class atDamageGive
    {
        public static SpireReturn<Float> Prefix(AbstractStance instance, float damage, DamageInfo.DamageType type) {
            if (damage > 0 && type == DamageInfo.DamageType.NORMAL)
            {
                logger.info("Triggered 'atDamageGive' \n Damage: " + String.valueOf(damage) + " | DamageType: " + type.toString());
                try
                {
                    throw new Exception();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            return SpireReturn.Continue();
        }
    }
}
