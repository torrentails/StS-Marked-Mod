package MarkedMod.cards.colorless;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import static MarkedMod.MarkedMod.makeCardPath;


public class Needle
        extends AbstractMarkedCard
{

    public static final String ID = MarkedMod.makeID(Needle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(Needle.class.getSimpleName() + ".png");


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 2;


    public Needle()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
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
        if (monster != null)
        {
            this.addToBot(new VFXAction(new PressurePointEffect(monster.hb.cX, monster.hb.cY)));
        }

        applyMark(player, monster, this.magicNumber);
    }
}
