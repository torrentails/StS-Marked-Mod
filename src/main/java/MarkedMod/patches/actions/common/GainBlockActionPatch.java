package MarkedMod.patches.actions.common;

import MarkedMod.stances.DanceOfDeathStance;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

// TODO: might make this only reducee the block gained by half
@SuppressWarnings("unused")
public class GainBlockActionPatch
{
    @SpirePatch(clz = GainBlockAction.class,
                method = "update")
    public static class PatchUpdate {
        public static SpireReturn<?> Prefix(GainBlockAction inst) {
            if (inst.target.isPlayer) {
                AbstractPlayer player = (AbstractPlayer)inst.target;
                if (player.stance.ID.equals(DanceOfDeathStance.STANCE_ID)) {
                    // TODO: Maybe some fancy effects here.
                    inst.isDone = true;
                    return SpireReturn.Return(null);
                }
            }

            return SpireReturn.Continue();
        }
    }
}
