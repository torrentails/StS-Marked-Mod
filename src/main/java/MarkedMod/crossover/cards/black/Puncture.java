package MarkedMod.crossover.cards.black;

import MarkedMod.MarkedMod;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;
import infinitespire.abstracts.BlackCard;

import static MarkedMod.MarkedMod.makeCardPath;


public class Puncture
        extends BlackCard {

    public static final String ID = MarkedMod.makeID(Puncture.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String TEXTURE = makeCardPath(Puncture.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int MAGIC = 42;
    private static final int UPGRADE_MAGIC = 27;


    public Puncture() {
        super(ID, NAME, TEXTURE, COST, DESCRIPTION, TYPE, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }


    @Override
    public void useWithEffect(AbstractPlayer player, AbstractMonster monster) {
        if (monster != null) {
            this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
        }
        this.addToBot(new ApplyPowerAction(monster,
                                           player,
                                           new MarkPower(monster, this.magicNumber),
                                           this.magicNumber));
    }
}
