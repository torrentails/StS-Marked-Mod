package MarkedMod;

import MarkedMod.cards.colorless.Needle;
import MarkedMod.cards.purple.*;
import MarkedMod.crossover.InfiniteSpire;
import MarkedMod.potions.watcher.BlackLotusJuice;
import MarkedMod.relics.AcupunctureKit;
import MarkedMod.stances.DanceOfDeathStance;
import MarkedMod.util.TextureLoader;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

import static MarkedMod.util.TextureLoader.loadTexture;


@SuppressWarnings("SpellCheckingInspection")
@SpireInitializer
public class MarkedMod
        implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PreStartGameSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(MarkedMod.class.getName());

    private static final String modID = "MarkedMod";
    private static final String MODNAME = "Marked Mod";
    private static final String AUTHOR = "torrentails";
    private static final String DESCRIPTION = "Adds cards, relics, a potion, and even a new stance to the Ascetic, all relating to the new \"Mark\" mechanic.";
    public static final String BADGE_IMAGE = "MarkedModResources/images/Badge.png";

    public static boolean isInfiniteSpireLoaded = false;


    public static String checkPath(String path) {
        return checkPath(path, getModID() + "Resources/image/ui/missing_texture.png");
    }


    public static String checkPath(String path, String defaultPath) {
        String retVal = defaultPath != null ? defaultPath : getModID() + "Resources/image/ui/missing_texture.png";

        try {
            loadTexture(path);
            retVal = path;
        } catch (GdxRuntimeException e) {
            logger.info("Could not find texture: " + path);
        }

        return retVal;
    }


    public static String makeCardPath(String path) {
        return checkPath(getModID() + "Resources/images/cards/" + path, getModID() + "Resources/images/cards/beta/beta_img.png");
    }
    
    public static String makeRelicPath(String path) {
        // return getModID() + "Resources/images/relics/" + path;
        return checkPath(getModID() + "Resources/images/relics/" + path);
    }
    
    public static String makeRelicOutlinePath(String path) {
        // return getModID() + "Resources/images/relics/outline/" + path;
        return checkPath(getModID() + "Resources/images/relics/outline/" + path);
    }
    
    public static String makePowerPath(String path) {
        // return getModID() + "Resources/images/powers/" + path;
        return checkPath(getModID() + "Resources/images/powers/" + path);
    }

    
    public MarkedMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
    }


    public static String getModID() {
        return modID;
    }


    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("Initializing " + MODNAME);

        MarkedMod markedMod = new MarkedMod();
        if (Loader.isModLoaded("infinitespire")) {
            MarkedMod.isInfiniteSpireLoaded = true;
            logger.info("Infinite Spire is loaded");
        }

        logger.info(MODNAME + " Initialized");
    }

    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image");

        BaseMod.registerModBadge(TextureLoader.getTexture(BADGE_IMAGE), MODNAME, AUTHOR, DESCRIPTION, null);
    }

    
    public void receiveEditPotions() {
        logger.info("Adding potions");

        BaseMod.addPotion(BlackLotusJuice.class,
                          BlackLotusJuice.COLOR_GAS,
                          BlackLotusJuice.COLOR_LIQUID,
                          BlackLotusJuice.COLOR_SPOTS,
                          BlackLotusJuice.POTION_ID,
                          AbstractPlayer.PlayerClass.WATCHER);
    }

    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelic(new AcupunctureKit(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(AcupunctureKit.ID);

        // I feel like this should go somewhere else but, it works so... meh ¯\_(ツ)_/¯
        receiveEditPotions();
    }

    
    @Override
    public void receiveEditCards() {
        logger.info("Adding cards");

        BaseMod.addCard(new Acupuncture());
        BaseMod.addCard(new FirstStrike());
        BaseMod.addCard(new GentlePulse());
        BaseMod.addCard(new OneThousandNeedles());
        BaseMod.addCard(new PinPointDefense());
        BaseMod.addCard(new GracefulMovements());
        BaseMod.addCard(new NorthStar());
        BaseMod.addCard(new SlowDance());
        BaseMod.addCard(new ChiBlock());

        BaseMod.addCard(new Needle());

        UnlockTracker.unlockCard(Acupuncture.ID);
        UnlockTracker.unlockCard(FirstStrike.ID);
        UnlockTracker.unlockCard(GentlePulse.ID);
        UnlockTracker.unlockCard(OneThousandNeedles.ID);
        UnlockTracker.unlockCard(PinPointDefense.ID);
        UnlockTracker.unlockCard(GracefulMovements.ID);
        UnlockTracker.unlockCard(NorthStar.ID);
        UnlockTracker.unlockCard(SlowDance.ID);
        UnlockTracker.unlockCard(ChiBlock.ID);

        UnlockTracker.unlockCard(Needle.ID);

        if (MarkedMod.isInfiniteSpireLoaded) {
            InfiniteSpire.loadWatcherBlackCards();
        }
    }

    
    @Override
    public void receiveEditStrings() {
        logger.info("Begin editing strings");
        String jsonPath = getModID() + "Resources/localization/";
        String langCode = Settings.language.toString().toLowerCase();

        switch (langCode) {
            case "eng":
            case "tha":
            case "zhs":
                jsonPath = jsonPath + langCode;
                break;

            default:
                jsonPath = jsonPath + "eng";
        }

        BaseMod.loadCustomStringsFile(
                CardStrings.class,
                jsonPath + "/Card-Strings.json");

        BaseMod.loadCustomStringsFile(
                PowerStrings.class,
                jsonPath + "/Power-Strings.json");

        BaseMod.loadCustomStringsFile(
                RelicStrings.class,
                jsonPath + "/Relic-Strings.json");

        BaseMod.loadCustomStringsFile(
                PotionStrings.class,
                jsonPath + "/Potion-Strings.json");

        BaseMod.loadCustomStringsFile(
                StanceStrings.class,
                jsonPath + "/Stance-Strings.json");
    }

    
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String path = getModID() + "Resources/localization/" + Settings.language.toString().toLowerCase();

        String json = Gdx.files.internal(path + "/Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                String description = keyword.DESCRIPTION;
                for (String name : keyword.NAMES) {
                    if (name.equals(DanceOfDeathStance.NAME)) {
                        description = DanceOfDeathStance.DESCRIPTIONS[0];
                        break;
                    }
                }

                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, description);
            }
        }
    }


    @Override
    public void receivePreStartGame() {
        if (MarkedMod.isInfiniteSpireLoaded) {
            InfiniteSpire.removeWatcherBlackCards();
        }
    }
}
