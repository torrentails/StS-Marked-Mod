package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import MarkedMod.cards.colorless.Needle;
import MarkedMod.powers.watcher.GracefulMovementsPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static MarkedMod.MarkedMod.makeCardPath;


public class GracefulMovements
        extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(GracefulMovements.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = GracefulMovements.class.getSimpleName();


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    private boolean next_upgrade_magic;


    public GracefulMovements() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.next_upgrade_magic = false;
        this.cardsToPreview = new Needle();
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // For mods that enable multiple upgrades.
            if (this.next_upgrade_magic) {
                upgradeMagicNumber(UPGRADE_MAGIC);
            } else {
                this.next_upgrade_magic = true;
            }
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot(new ApplyPowerAction(player,
                                           player,
                                           new GracefulMovementsPower(player, this.magicNumber),
                                           this.magicNumber));
    }
}