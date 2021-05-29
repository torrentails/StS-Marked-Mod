package MarkedMod.crossover;

import MarkedMod.crossover.cards.black.Puncture;
import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import infinitespire.patches.CardColorEnumPatch;


public class InfiniteSpire {

    public static void loadWatcherBlackCards() {
        BaseMod.addCard(new Puncture());
    }


    public static void removeWatcherBlackCards() {
        if (AbstractDungeon.player != null && !(AbstractDungeon.player instanceof Watcher)) {
            BaseMod.removeCard(Puncture.ID, CardColorEnumPatch.CardColorPatch.INFINITE_BLACK);
        } else {
            loadWatcherBlackCards();
        }
    }
}
