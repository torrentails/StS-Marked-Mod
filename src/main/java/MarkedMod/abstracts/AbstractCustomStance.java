package MarkedMod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.stances.AbstractStance;


@SuppressWarnings("unused")
public abstract class AbstractCustomStance extends AbstractStance
{
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

    //TODO: onAttacked and other hooks maybe?
    // Not needed for this mod but could be useful for other modders

    public void wasHPLost(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.wasHPLost(info, damageAmount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount) { }

    @Override
    public void onExitStance() { this.stopIdleSfx(); }

    public static Color getColor(float a) { return new Color(0.33f, 0.33f, 0.33f, a); }
}
