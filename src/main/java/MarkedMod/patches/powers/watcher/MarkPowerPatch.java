package MarkedMod.patches.powers.watcher;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;


@SpirePatch(clz = MarkPower.class,
            method = "triggerMarks")
public class MarkPowerPatch {

public static void Prefix(MarkPower power, @ByRef AbstractCard[] card) {
    card[0] = new PressurePoints();
}
}
