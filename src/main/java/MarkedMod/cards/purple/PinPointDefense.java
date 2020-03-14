package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.powers.watcher.ApplyMarkOnAttackedPower;
import MarkedMod.powers.watcher.GainBlockOnApplyMarkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static MarkedMod.MarkedMod.makeCardPath;


public class PinPointDefense
        extends AbstractMarkedCard {

public static final String ID = MarkedMod.makeID(PinPointDefense.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath(PinPointDefense.class.getSimpleName() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.UNCOMMON;
private static final CardTarget TARGET = CardTarget.SELF;
private static final CardType TYPE = CardType.POWER;

private static final int COST = 1;
private static final int MAGIC = 2;
private static final int UPGRADE_PLUS_MAGIC = 1;
private static final int MARK = 3;


public PinPointDefense() {
    super(ID, IMG, COST, TYPE, RARITY, TARGET);
    baseMagicNumber = magicNumber = MAGIC;
}


@Override
public void upgrade() {
    if (!upgraded) {
        upgradeName();
        this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
        initializeDescription();
    }
}


@Override
public void use(AbstractPlayer player, AbstractMonster monster) {
    this.addToBot(new ApplyPowerAction(player, player, new ApplyMarkOnAttackedPower(player, MARK), MARK));
    this.addToBot(new ApplyPowerAction(player, player, new GainBlockOnApplyMarkPower(player, this.magicNumber), this.magicNumber));
}
}
