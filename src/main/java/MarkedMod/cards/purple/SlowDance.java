package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.stances.DanceOfDeathStance;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;


    public SlowDance()
    {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
    }


    @Override
    public boolean cardPlayable(AbstractMonster m) {
        return false;
    }


    @Override
    public void triggerWhenDrawn() {
        this.addToBot(new ChangeStanceAction(DanceOfDeathStance.STANCE_ID));
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = false;
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}
}
