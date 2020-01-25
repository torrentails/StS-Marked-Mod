package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import java.util.Iterator;

import static MarkedMod.MarkedMod.makeCardPath;


public class FirstStrike
        extends AbstractCard {

public static final String ID = MarkedMod.makeID(FirstStrike.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath("Attack.png");
// TODO: Why it no load!?
// public static final String IMG = makeCardPath(FirstStrike.class.getSimpleName().toLowerCase() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.ENEMY;
private static final CardType TYPE = CardType.SKILL;

private static final int COST = 0;
private static final int MAGIC = 13;
private static final int UPGRADE_MAGIC = 5;


public FirstStrike() {
    super(ID, IMG, COST, TYPE, RARITY, TARGET);
    baseMagicNumber = magicNumber = MAGIC;
}


@Override
public void upgrade() {
    if (!upgraded) {
        upgradeName();
        upgradeMagicNumber(UPGRADE_MAGIC);
        initializeDescription();
    }
}


@Override
public void use(AbstractPlayer player, AbstractMonster monster) {
    Iterator powers = monster.powers.iterator();
    boolean hasMark = false;
    while (powers.hasNext()) {
        AbstractPower power = (AbstractPower) powers.next();
        if (power.ID.equals("PathToVictoryPower")) {
            hasMark = true;
            break;
        }
    }

    // TODO: Say something if the enemy does have Mark
    if (!hasMark) {
        if (monster != null) {
            this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
        }

        applyMark(player, monster, this.magicNumber);
    }
}
}
