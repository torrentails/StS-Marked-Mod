package MarkedMod.cards.purple;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractMarkedCard;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static MarkedMod.MarkedMod.makeCardPath;


public class NorthStar
        extends AbstractMarkedCard {

    public static final String ID = MarkedMod.makeID(NorthStar.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = NorthStar.class.getSimpleName();


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int MAGIC = 1;


    public NorthStar() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (energyOnUse < EnergyPanel.totalCount) energyOnUse = EnergyPanel.totalCount;
        if (upgraded) energyOnUse++;

        int i;
        for (i = 0; i < energyOnUse; i++) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != null && !m.isDeadOrEscaped()) {
                    applyMark(player, m, magicNumber);
                }
            }
        }

        for (i = 0; i < energyOnUse; i++) {
            triggerMarks();
        }

        addToBot(new PressEndTurnButtonAction());
    }
}
