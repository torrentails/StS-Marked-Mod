package MarkedMod.abstracts;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.ArrayList;


public abstract class AbstractCustomStance extends AbstractStance
{
    public ArrayList<Float> colorRedRange;
    public ArrayList<Float> colorGreenRange;
    public ArrayList<Float> colorBlueRange;


    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        return this.onAttackToChangeDamage(info, damageAmount);
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        return this.onAttackedToChangeDamage(info, damageAmount);
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.onAttack(info, damageAmount);
    }

    public void onAttack(DamageInfo info, int damageAmount) { }

    // TODO: onAttacked and other hooks maybe?

    public void wasHPLost(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.wasHPLost(info, damageAmount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount) { }
}
