package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.actions.StunIfMarkedAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static MarkedMod.MarkedMod.makeCardPath;


public class ChiBlock
        extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(ChiBlock.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(ChiBlock.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int MAGIC = 30;
    private static final float UPGRADE_MAGIC_MUL = 0.333f;
    private static final int MARK = 12;


    public ChiBlock() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            int magicNumberDelta = Math.round((float)this.baseMagicNumber * UPGRADE_MAGIC_MUL);
            upgradeMagicNumber(-magicNumberDelta);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.applyMark(player, monster, MARK);
        addToBot(new StunIfMarkedAction(monster, player, this.magicNumber));
    }
}
