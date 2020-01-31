package MarkedMod.patches.vfx.stance;

import MarkedMod.stances.DanceOfDeathStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import javassist.CtBehavior;

import java.lang.reflect.Field;

import static MarkedMod.MarkedMod.logger;


@SpirePatch(clz = StanceAuraEffect.class,
            method = SpirePatch.CONSTRUCTOR)
public class StanceAuraEffectPatch
{
    @SpireInsertPatch(locator = Locator.class)
    public static void AlowOtherStances(StanceAuraEffect effect, String stanceId)
    {
        if (stanceId.equals(DanceOfDeathStance.STANCE_ID))
        {

            try
            {
                Field colorField = AbstractGameEffect.class.getDeclaredField("color");
                colorField.setAccessible(true);

                float[] colors = DanceOfDeathStance.COLORS;
                colorField.set(effect, new Color(
                        MathUtils.random(colors[0], colors[1]),
                        MathUtils.random(colors[2], colors[3]),
                        MathUtils.random(colors[4], colors[5]),
                        0.0F));

            } catch (NoSuchFieldException | IllegalAccessException e)
            {
                logger.warn("Can't access color field on AbstractGameEffect");
                // e.printStackTrace();
            }
        }
    }


    private static class Locator
            extends SpireInsertLocator
    {

        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(Hitbox.class, "cX");

            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
