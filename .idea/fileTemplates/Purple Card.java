#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import static MarkedMod.MarkedMod.makeCardPath;


public class ${NAME} extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(${NAME}.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath(${NAME}.class.getSimpleName() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.UNCOMMON;
private static final CardTarget TARGET = CardTarget.ENEMY;
private static final CardType TYPE = CardType.SKILL;

private static final int COST = 1;
private static final int DAMAGE = 6;
private static final int UPGRADE_PLUS_DMG = 3;
private static final int BLOCK = 5;
private static final int UPGRADE_PLUS_BLOCK = 3;
private static final int MAGIC = 1;
private static final int UPGRADE_MAGIC = 1;


public ${NAME}() {
    super(ID, IMG, COST, TYPE, RARITY, TARGET);
    baseDamage = DAMAGE;
    baseBlock = BLOCK;
    baseMagicNumber = magicNumber = MAGIC;
}


@Override
public void upgrade() {
    if (!upgraded) {
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_DMG);
        upgradeBlock(UPGRADE_PLUS_BLOCK);
        upgradeMagicNumber(UPGRADE_MAGIC);
        initializeDescription();
    }
}


@Override
public void use(AbstractPlayer player, AbstractMonster monster) {
    if (monster != null) {
        this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
    }
}
}
