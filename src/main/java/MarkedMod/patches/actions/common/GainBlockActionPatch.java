package MarkedMod.patches.actions.common;

import MarkedMod.stances.DanceOfDeathStance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;


@SuppressWarnings("unused")
public class GainBlockActionPatch {
    @SpirePatch(clz = GainBlockAction.class,
                method = "update")
    public static class PatchUpdate {
        @SpireInsertPatch(locator = Locator.class)
        public static void insert(GainBlockAction inst) {
            if (inst.target.isPlayer) {
                AbstractPlayer player = (AbstractPlayer) inst.target;
                if (player.stance.ID.equals(DanceOfDeathStance.STANCE_ID)) {
                    // Always gain at least 1 block
                    inst.amount = Math.max(Math.floorDiv(inst.amount, 2), 1);
                }
            }
        }


        // public static SpireReturn<?> Prefix(GainBlockAction inst) {
        //     if (inst.target.isPlayer && inst.amount <= 1) {
        //         AbstractPlayer player = (AbstractPlayer)inst.target;
        //         if (player.stance.ID.equals(DanceOfDeathStance.STANCE_ID)) {
        //             inst.isDone = true;
        //             return SpireReturn.Return(null);
        //         }
        //     }
        //
        //     return SpireReturn.Continue();
        // }
    }


    private static class Locator
            extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "effectList");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
