package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static MarkedMod.MarkedMod.makeCardPath;
import static MarkedMod.MarkedMod.logger;

public class GentlePulse
        extends AbstractMarkedCard {

public static final String ID = MarkedMod.makeID(GentlePulse.class.getSimpleName());
private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
public static final String IMG = makeCardPath("Attack.png");
// TODO: Why it no load!?
// public static final String IMG = makeCardPath(FirstStrike.class.getSimpleName() + ".png");


public static final String NAME = cardStrings.NAME;
public static final String DESCRIPTION = cardStrings.DESCRIPTION;

private static final CardRarity RARITY = CardRarity.COMMON;
private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
private static final CardType TYPE = CardType.SKILL;

private static final int COST = 0;
private static final int MAGIC = 8;
private static final int UPGRADE_MAGIC = -2;


public GentlePulse() {
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
    if (monster != null) {
        this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
    }

    triggerMarks();

    Iterator monsters = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
    // Iterator powers;
    // HashMap<AbstractMonster, AbstractPower> toRemove;

    while (monsters.hasNext()) {
        AbstractMonster m = (AbstractMonster) monsters.next();

        this.addToBot(new ReducePowerAction(m, player, MarkPower.POWER_ID, this.magicNumber));

        // powers = m.powers.iterator();
        // toRemove = new HashMap<>();
        //
        // while (powers.hasNext()) {
        //     AbstractPower p = (AbstractPower) powers.next();
        //     if (p.ID.equals("PathToVictoryPower")) {
        //         p.reducePower(this.magicNumber);
        //
        //         if (p.amount <= 0) {
        //             toRemove.put(m, p);
        //             // m.powers.remove(p);
        //         }
        //     }
        // }
        //
        // for(Map.Entry<AbstractMonster, AbstractPower> entry : toRemove.entrySet()) {
        //     entry.getKey().powers.remove(entry.getValue());
        // }
    }
}
}
