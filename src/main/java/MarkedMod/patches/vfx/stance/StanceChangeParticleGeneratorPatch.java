package MarkedMod.patches.vfx.stance;

import MarkedMod.stances.DanceOfDeathStance;
import MarkedMod.vfx.stance.DanceOfDeathStanceChangeParticle;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

import java.lang.reflect.Field;


@SpirePatch(clz = StanceChangeParticleGenerator.class,
            method = "update")
public class StanceChangeParticleGeneratorPatch {

    public static SpireReturn Prefix(StanceChangeParticleGenerator instance) {

        try
        {
            Field stanceIdField = instance.getClass().getDeclaredField("stanceId");
            stanceIdField.setAccessible(true);

            if (stanceIdField.get(instance).toString().equals(DanceOfDeathStance.STANCE_ID)) {

                Field xField = instance.getClass().getDeclaredField("x");
                xField.setAccessible(true);

                for(int i = 0; i < 10; ++i)
                {
                    AbstractDungeon.effectsQueue.add(new DanceOfDeathStanceChangeParticle((Float) xField.get(instance)));
                }

                instance.isDone = true;

                return SpireReturn.Return(null);

            }
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return SpireReturn.Continue();
    }
}