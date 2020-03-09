package MarkedMod.potions.watcher;

import MarkedMod.MarkedMod;
import MarkedMod.stances.DanceOfDeathStance;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class BlackLotusJuice extends AbstractPotion
{


    public static final String POTION_ID = MarkedMod.makeID(BlackLotusJuice.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public static final Color COLOR_GAS = DanceOfDeathStance.COLOR_MAX.cpy();
    public static final Color COLOR_LIQUID = DanceOfDeathStance.COLOR_MIN.cpy().mul(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color COLOR_SPOTS = null;


    public BlackLotusJuice() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.EYE, PotionEffect.NONE,
              COLOR_GAS, COLOR_LIQUID, COLOR_SPOTS);

        this.labOutlineColor = Settings.PURPLE_RELIC_COLOR;
        this.isThrown = false;
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0];
        this.isThrown = false;

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        // TODO: as custom Keyword maybe?
        this.tips.add(new PowerTip(TipHelper.capitalize(DanceOfDeathStance.NAME), DanceOfDeathStance.DESCRIPTIONS[0]));

    }


    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ChangeStanceAction(DanceOfDeathStance.STANCE_ID));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BlackLotusJuice();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}
