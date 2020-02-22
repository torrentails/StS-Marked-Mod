package MarkedMod.patches.actions.watcher;

import MarkedMod.cards.purple.SlowDance;
import MarkedMod.stances.DanceOfDeathStance;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

import java.lang.reflect.Field;

import static MarkedMod.MarkedMod.logger;

@SuppressWarnings("unused")
public class ChangeStanceActionPatch
{

    @SpirePatch(clz = ChangeStanceAction.class, method = "update")
    public static class PatchUpdate
    {
        public static SpireReturn Prefix(ChangeStanceAction inst)
        {
            try
            {
                Field idField = ChangeStanceAction.class.getDeclaredField("id");
                idField.setAccessible(true);
                String id = (String)idField.get(inst);

                boolean isSlowDancing = false;
                for (AbstractCard card : AbstractDungeon.player.hand.group)
                {
                    if (card.cardID.equals(SlowDance.ID))
                    {
                        isSlowDancing = true;
                        break;
                    }
                }

                if (isSlowDancing && !(id.equals(DanceOfDeathStance.STANCE_ID) || id.equals(NeutralStance.STANCE_ID))) {
                    inst.isDone = true;

                    return SpireReturn.Return(null);
                }
            }

            catch (NoSuchFieldException | IllegalAccessException e)
            {
                logger.error("Can't access id field on ChangeStanceAction");
            }

            return SpireReturn.Continue();
        }
    }
}
