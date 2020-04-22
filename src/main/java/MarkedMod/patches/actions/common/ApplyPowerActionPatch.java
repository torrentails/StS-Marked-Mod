package MarkedMod.patches.actions.common;

import MarkedMod.relics.AcupunctureKit;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;


@SuppressWarnings("unused")
public class ApplyPowerActionPatch {

    @SpirePatch(clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR,
                paramtypez = {
                    AbstractCreature.class,
                    AbstractCreature.class,
                    AbstractPower.class,
                    int.class,
                    boolean.class,
                    AttackEffect.class
                })
    public static class constructor {
        public static void Prefix(ApplyPowerAction inst, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, @ByRef int[] stackAmount, boolean isFast, AttackEffect effect) {
            if (AbstractDungeon.player.hasRelic(AcupunctureKit.ID) && powerToApply.ID.equals(MarkPower.POWER_ID)) {
                ++stackAmount[0];
            }
        }
    }
}
