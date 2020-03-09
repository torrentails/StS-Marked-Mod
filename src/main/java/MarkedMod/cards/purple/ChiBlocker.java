package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

import static MarkedMod.MarkedMod.makeCardPath;


public class ChiBlocker
        extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(ChiBlocker.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(ChiBlocker.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int MAGIC = 30;
    private static final float UPGRADE_MAGIC_MUL = 0.333f;


    public ChiBlocker() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            int magicNumberDelta = (int)Math.ceil((float)this.baseMagicNumber * UPGRADE_MAGIC_MUL);
            upgradeMagicNumber(-magicNumberDelta);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractPower power = monster.getPower(MarkPower.POWER_ID);
        if (power == null) { return; }

        int amountRemoved = power.amount;
        addToTop(new RemoveSpecificPowerAction(monster, player, power));
        if (amountRemoved < this.magicNumber) { return; }

        addToBot(new StunMonsterAction(monster, player));
    }


    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        if (monster == null || !monster.hasPower(MarkPower.POWER_ID) || !this.cardPlayable(monster)) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }

        return true;
    }
}
