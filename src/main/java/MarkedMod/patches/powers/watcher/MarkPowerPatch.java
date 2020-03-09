package MarkedMod.patches.powers.watcher;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;


//TODO: This feels kind of hacky. Maybe look at making this work without creating a card?
// Why on earth do they need to check the card in the base game anyways??? Seems pointless...

@SuppressWarnings("unused")
public class MarkPowerPatch {

    @SpirePatch(clz = MarkPower.class, method = "triggerMarks")
    public static class triggerMarks {
        private static AbstractCard bypassCard = new PressurePoints();

        public static void Prefix(MarkPower power, @ByRef AbstractCard[] card)
        {
            card[0] = bypassCard;
        }
    }
}
