package MarkedMod.powers.watcher;

import MarkedMod.MarkedMod;
import MarkedMod.cards.colorless.Needle;
import MarkedMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static MarkedMod.MarkedMod.makePowerPath;


public class GracefulMovementsPower extends AbstractPower
        implements CloneablePowerInterface {
    public static final String POWER_ID = MarkedMod.makeID(GracefulMovementsPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(GracefulMovementsPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(GracefulMovementsPower.class.getSimpleName() + "32.png"));


    public GracefulMovementsPower(AbstractCreature owner, int newAmount) {
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
    public void atStartOfTurn() {
        {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flash();
                this.addToBot(new MakeTempCardInHandAction(new Needle(), this.amount, false));
            }

        }
    }


    @Override
    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new GracefulMovementsPower(this.owner, this.amount);
    }
}