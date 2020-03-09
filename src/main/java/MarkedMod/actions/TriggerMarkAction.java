package MarkedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class TriggerMarkAction extends AbstractGameAction
{
    public TriggerMarkAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {

        for (AbstractPower power : this.target.powers) {
            power.triggerMarks(null);
        }

        this.isDone = true;
    }
}
