package MarkedMod.patches.monsters;

import MarkedMod.abstracts.AbstractCustomStance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;


public class AbstractMonsterPatch
{
    @SpirePatch(clz = AbstractMonster.class,
                method = "damage")
    public static class Patch_damaage {

        @SpireInsertPatch(locator = Locator_onAttackToChangeDamage.class,
                          localvars = {"damageAmount"})
        public static void onAttackToChangeDamage(AbstractMonster __inst, DamageInfo info, @ByRef int[] damageAmount)
        {
            if (info.owner == AbstractDungeon.player) {
                if (AbstractDungeon.player.stance instanceof AbstractCustomStance) {
                    damageAmount[0] = ((AbstractCustomStance)AbstractDungeon.player.stance).onAttackToChangeDamage(info, damageAmount[0], __inst);
                }
            }
        }


        @SpireInsertPatch(locator = Locator_onAttackedToChangeDamage.class,
                          localvars = {"damageAmount"})
        public static void onAttackedToChangeDamage(AbstractMonster __inst, DamageInfo info, @ByRef int[] damageAmount)
        {
            if (info.owner == AbstractDungeon.player) {
                if (AbstractDungeon.player.stance instanceof AbstractCustomStance) {
                    damageAmount[0] = ((AbstractCustomStance)AbstractDungeon.player.stance).onAttackedToChangeDamage(info, damageAmount[0], __inst);
                }
            }
        }


        @SpireInsertPatch(locator = Locator_onAttack.class,
                          localvars = {"damageAmount"})
        public static void onAttack(AbstractMonster __inst, DamageInfo info, int damageAmount)
        {
            if (info.owner == AbstractDungeon.player) {
                if (AbstractDungeon.player.stance instanceof AbstractCustomStance) {
                    ((AbstractCustomStance)AbstractDungeon.player.stance).onAttack(info, damageAmount, __inst);
                }
            }
        }


        @SpireInsertPatch(locator = Locator_wasHPLost.class,
                          localvars = {"damageAmount"})
        public static void wasHPLost(AbstractMonster __inst, DamageInfo info, int damageAmount)
        {
            if (info.owner == AbstractDungeon.player) {
                if (AbstractDungeon.player.stance instanceof AbstractCustomStance) {
                    ((AbstractCustomStance)AbstractDungeon.player.stance).wasHPLost(info, damageAmount, __inst);
                }
            }
        }
    }


    private static class Locator_onAttackToChangeDamage extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }


    private static class Locator_onAttackedToChangeDamage extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onAttackedToChangeDamage");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }


    private static class Locator_onAttack extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(DamageInfo.class, "owner");
            int[] lines = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
            return new int[]{lines[1]};
        }
    }


    private static class Locator_wasHPLost extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(DamageInfo.class, "owner");
            int[] lines = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
            return new int[]{lines[4]};
        }
    }
}
