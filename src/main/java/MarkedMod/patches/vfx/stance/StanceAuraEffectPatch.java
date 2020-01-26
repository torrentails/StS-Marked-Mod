package MarkedMod.patches.vfx.stance;

import MarkedMod.stances.DanceOfDeathStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import javassist.CtBehavior;

import java.lang.reflect.Field;


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
                Field colorField = effect.getClass().getDeclaredField("color");
                colorField.setAccessible(true);

                Float colorValue = MathUtils.random(0.1F, 0.3F);
                colorField.set(effect, new Color(colorValue, colorValue, colorValue, 0.0F));

            } catch (NoSuchFieldException | IllegalAccessException e)
            {
                e.printStackTrace();
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
