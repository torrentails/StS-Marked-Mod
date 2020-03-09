package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import static MarkedMod.MarkedMod.makeCardPath;

public class GentlePulse
        extends AbstractMarkedCard {

public static final String ID = MarkedMod.makeID(GentlePulse.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath(GentlePulse.class.getSimpleName() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
private static final CardType TYPE = CardType.SKILL;

private static final int COST = 0;


public GentlePulse() {
    super(ID, IMG, COST, TYPE, RARITY, TARGET);
    this.exhaust = true;
}


@Override
public void upgrade() {
    if (!upgraded) {
        upgradeName();
        this.selfRetain = true;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}


@Override
public void use(AbstractPlayer player, AbstractMonster monster) { triggerMarks(); }
}
