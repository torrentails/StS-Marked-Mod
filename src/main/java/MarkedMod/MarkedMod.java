package MarkedMod;

import MarkedMod.cards.colorless.Tag;
import MarkedMod.cards.purple.*;
import MarkedMod.potions.watcher.BlackLotusJuice;
import MarkedMod.util.IDCheck;
import MarkedMod.util.TextureLoader;
import MarkedMod.variables.DefaultCustomVariable;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


@SpireInitializer
public class MarkedMod
        implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(MarkedMod.class.getName());
    private static String modID;

    //TODO:
    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "Marked Mod";
    private static final String AUTHOR = "torrentails";
    private static final String DESCRIPTION = "Adds cards, relics, a potion, and even a new stance to the Ascetic, all relating to the new \"Mark\" mechanic.";

    public static final String BADGE_IMAGE = "MarkedModResources/images/Badge.png";

    
    public static String makeCardPath(String resourcePath) {
        // return getModID() + "Resources/images/cards/" + resourcePath;
        // TODO: PICTURES!!!
        return getModID() + "Resources/images/cards/beta_img.png";
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    
    public MarkedMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
      
        setModID("MarkedMod");
        
        logger.info("Done subscribing");

        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }


    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = MarkedMod.class.getResourceAsStream("/IDCheckStrings.json");

        IDCheck EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheck.class);

        logger.info("You are attempting to set your mod ID as: " + ID);

        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }

        logger.info("Success! ID is " + modID);
    }


    public static String getModID() {
        return modID;
    }

    
    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = MarkedMod.class.getResourceAsStream("/IDCheckStrings.json");

        IDCheck EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheck.class);

        String packageName = MarkedMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");

        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }

            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("Initializing " + MODNAME);
        MarkedMod defaultmod = new MarkedMod();
        logger.info(MODNAME + " Initialized");
    }

    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        //TODO:
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        logger.info("Done loading badge Image and mod options");
    }

    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        BaseMod.addPotion(BlackLotusJuice.class,
                          BlackLotusJuice.COLOR_GAS,
                          BlackLotusJuice.COLOR_LIQUID,
                          BlackLotusJuice.COLOR_SPOTS,
                          BlackLotusJuice.POTION_ID,
                          AbstractPlayer.PlayerClass.WATCHER);
        
        logger.info("Done editing potions");
    }

    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        // BaseMod.addRelicToCustomPool(new PlaceholderRelic(), AbstractCard.CardColor.PURPLE);
        // BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), AbstractCard.CardColor.PURPLE);
        // BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), AbstractCard.CardColor.PURPLE);
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        // BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        // UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");

        receiveEditPotions();
    }

    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        
        logger.info("Adding cards");

        // TODO: Add all the other cards
        BaseMod.addCard(new Acupuncture());
        BaseMod.addCard(new FirstStrike());
        BaseMod.addCard(new GentlePulse());
        BaseMod.addCard(new OneThousandNeedles());
        BaseMod.addCard(new PinPointDefense());
        BaseMod.addCard(new GracefulMovements());
        BaseMod.addCard(new NorthStar());
        BaseMod.addCard(new SlowDance());

        BaseMod.addCard(new Tag());
        
        logger.info("Making sure the cards are unlocked.");

        // TODO: Unlock packages maybe?
        UnlockTracker.unlockCard(Acupuncture.ID);
        UnlockTracker.unlockCard(FirstStrike.ID);
        UnlockTracker.unlockCard(GentlePulse.ID);
        UnlockTracker.unlockCard(OneThousandNeedles.ID);
        UnlockTracker.unlockCard(PinPointDefense.ID);
        UnlockTracker.unlockCard(GracefulMovements.ID);
        UnlockTracker.unlockCard(NorthStar.ID);
        UnlockTracker.unlockCard(SlowDance.ID);

        UnlockTracker.unlockCard(Tag.ID);
        
        logger.info("Done adding cards!");
    }

    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/Relic-Strings.json");
        
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/Potion-Strings.json");

        BaseMod.loadCustomStringsFile(StanceStrings.class,
                                      getModID() + "Resources/localization/eng/Stance-Strings.json");
        
        logger.info("Done editing strings");
    }

    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }


    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }


    // Can be quickly inserted anywhere to get a stack trace
    // Probably an easier/better way to do this but I don't care enough rn
    public static void getStacktrace(boolean doTheThing) {
        if (doTheThing) {
            try
            {
                if (!makeID("foo").equals("bar"))
                {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
