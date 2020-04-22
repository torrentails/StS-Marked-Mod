package MarkedMod.patches.powers.watcher;

import MarkedMod.relics.AcupunctureKit;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

@SuppressWarnings("unused")
public class MarkPowerPatch {

    @SpirePatch(clz = MarkPower.class, method = SpirePatch.CONSTRUCTOR, paramtypez={
            AbstractCreature.class,
            int.class
    })
    public static class constructor {
        public static void Prefix(MarkPower inst, AbstractCreature owner, @ByRef int[] amt) {
            if (AbstractDungeon.player.hasRelic(AcupunctureKit.ID)) {
                AbstractDungeon.player.getRelic(AcupunctureKit.ID).flash();
                ++amt[0];
            }
        }
    }


    // Why on earth do they need to check the card in the base game anyways??? Seems pointless...
    @SpirePatch(clz = MarkPower.class, method = "triggerMarks")
    public static class triggerMarks {
        private static AbstractCard bypassCard = new PressurePoints();


        public static void Prefix(MarkPower inst, @ByRef AbstractCard[] card) {
            card[0] = bypassCard;
        }
    }
}
