package MarkedMod.abstracts;

import MarkedMod.actions.TriggerMarkAction;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static MarkedMod.MarkedMod.makeCardPath;
import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;


@SuppressWarnings("unused")
public abstract class AbstractMarkedCard
        extends CustomCard {


    public AbstractMarkedCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardRarity rarity,
                              final CardTarget target) {

        this(id,
             languagePack.getCardStrings(id).NAME,
             img,
             cost,
             languagePack.getCardStrings(id).DESCRIPTION,
             type,
             CardColor.PURPLE,
             rarity,
             target);

    }


    public AbstractMarkedCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        this(id,
             languagePack.getCardStrings(id).NAME,
             img,
             cost,
             languagePack.getCardStrings(id).DESCRIPTION,
             type,
             color,
             rarity,
             target);

    }


    public AbstractMarkedCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, name, makeCardPath(img + ".png"), cost, rawDescription, type, color, rarity, target);

        loadCardImage(img, true);

        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
    }


    protected Texture getPortraitImage() {
        if (this.textureImg == null) {
            return null;
        } else {
            String newPath;

            if (!UnlockTracker.betaCardPref.getBoolean(this.cardID)) {
                int endingIndex = this.textureImg.lastIndexOf(".");
                newPath = this.textureImg.substring(0, endingIndex) + CustomCard.PORTRAIT_ENDING + this.textureImg.substring(endingIndex);
            } else {
                int endingIndex = this.textureImg.lastIndexOf(".");
                int midIndex = this.textureImg.lastIndexOf("/") + 1;
                newPath = this.textureImg.substring(0, midIndex) + "beta/" + this.textureImg.substring(midIndex, endingIndex) +  CustomCard.PORTRAIT_ENDING + this.textureImg.substring(endingIndex);
            }

            System.out.println("Finding texture: " + newPath);

            Texture portraitTexture;
            try {
                portraitTexture = ImageMaster.loadImage(newPath);
            } catch (Exception var5) {
                portraitTexture = null;
            }

            return portraitTexture;
        }
    }


    public void loadCardImage(String img, @SuppressWarnings("SameParameterValue") boolean betaImage) {
        if (!betaImage) { loadCardImage(img); return; }

        String path = makeCardPath("beta/" + img + ".png");
        Texture cardTexture;
        if (imgMap.containsKey(path)) {
            cardTexture = imgMap.get(path);
        } else {
            cardTexture = ImageMaster.loadImage(path);
            imgMap.put(path, cardTexture);
        }

        cardTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        this.jokePortrait = new AtlasRegion(cardTexture, 0, 0, tw, th);
    }


    public void applyMark(AbstractCreature p, AbstractCreature m) {
        this.applyMark(p, m, -1);
    }


    public void applyMark(AbstractCreature p, AbstractCreature m, int amount) {
        if (amount < 0) {
            amount = this.magicNumber;
        }

        this.addToBot(new ApplyPowerAction(m, p, new MarkPower(m, amount), amount));
    }


    public void triggerMarks() {
        this.addToBot(new TriggerMarksAction(this));
    }


    public void triggerMark(AbstractCreature target) {
        this.addToBot(new TriggerMarkAction(target));
    }
}