package MarkedMod.powers.watcher;

import MarkedMod.MarkedMod;
import MarkedMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

import static MarkedMod.MarkedMod.makePowerPath;


public class GainBlockOnApplyMarkPower extends AbstractPower
        implements CloneablePowerInterface {
    public static final String POWER_ID = MarkedMod.makeID(GainBlockOnApplyMarkPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("yin_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("yin_32.png"));

    private static boolean logged = false;

    public GainBlockOnApplyMarkPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(MarkPower.POWER_ID) && source == this.owner && target != this.owner) {
            if (!logged) {
                MarkedMod.logger.info("Adding Block");
                logged = true;
            }
            this.addToTop(new GainBlockAction(AbstractDungeon.player,
                                              AbstractDungeon.player,
                                              this.amount,
                                              Settings.FAST_MODE));

            this.flash();
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


    @Override
    public AbstractPower makeCopy() {
        return new GainBlockOnApplyMarkPower(this.owner, this.amount);
    }
}
