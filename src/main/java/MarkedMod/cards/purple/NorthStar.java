package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static MarkedMod.MarkedMod.makeCardPath;


public class NorthStar
        extends AbstractMarkedCard
{

    public static final String ID = MarkedMod.makeID(NorthStar.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");
    // TODO: Why it no load!?
    // public static final String IMG = makeCardPath(NorthStar.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int MARK = 1;


    public NorthStar()
    {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        Iterator<AbstractMonster> iter;
        for (int i = 0; i < this.energyOnUse; i++) {
            iter = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (iter.hasNext()) {
                monster = iter.next();
                if (monster != null && !monster.isDeadOrEscaped()) {
                    this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
                    applyMark(player, monster, MARK);
                }
            }

            triggerMarks();
        }

        this.addToBot(new PressEndTurnButtonAction());
    }
}
