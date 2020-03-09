package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.stances.DanceOfDeathStance;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static MarkedMod.MarkedMod.makeCardPath;


public class SlowDance
        extends AbstractMarkedCard
{

    public static final String ID = MarkedMod.makeID(SlowDance.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(SlowDance.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;


    public SlowDance()
    {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        // this.selfRetain = true;
        this.exhaust = true;
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }


    @Override
    public void triggerWhenDrawn() {
        this.addToTop(new ChangeStanceAction(DanceOfDeathStance.STANCE_ID));

        if (!upgraded)
        {
            this.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            // this.selfRetain = false;
            this.exhaust = false;
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
}
