package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.cards.colorless.Tag;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static MarkedMod.MarkedMod.makeCardPath;


public class Acupuncture extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(Acupuncture.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath(Acupuncture.class.getSimpleName() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.ENEMY;
private static final CardType TYPE = CardType.ATTACK;

private static final int COST = 1;
private static final int DAMAGE = 3;
private static final int UPGRADE_PLUS_DMG = 2;


public Acupuncture() {
    super(ID, IMG, COST, TYPE, RARITY, TARGET);
    baseDamage = DAMAGE;
    this.cardsToPreview = new Tag();
}


@Override
public void upgrade() {
    if (!upgraded) {
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_DMG);
        initializeDescription();
    }
}


@Override
public void use(AbstractPlayer player, AbstractMonster monster) {
    AbstractDungeon.actionManager.addToBottom(
        new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));

    this.addToBot(new MakeTempCardInHandAction(new Tag(), 1));
}
}
