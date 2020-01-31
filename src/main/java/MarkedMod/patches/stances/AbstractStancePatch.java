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
}
