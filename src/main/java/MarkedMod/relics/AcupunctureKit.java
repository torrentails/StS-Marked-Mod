package MarkedMod.relics;

import MarkedMod.MarkedMod;
import MarkedMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

import static MarkedMod.MarkedMod.makeRelicOutlinePath;
import static MarkedMod.MarkedMod.makeRelicPath;


public class AcupunctureKit
        extends CustomRelic {
    public static final String ID = MarkedMod.makeID(AcupunctureKit.class.getSimpleName());
    public static final RelicTier TIER = RelicTier.COMMON;
    public static final LandingSound SFX = LandingSound.CLINK;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public AcupunctureKit() {
        super(ID, IMG, OUTLINE, TIER, SFX);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
