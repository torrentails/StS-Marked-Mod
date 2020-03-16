package MarkedMod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;


public class StunIfMarkedAction extends AbstractGameAction {

    public StunIfMarkedAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
    }


    @Override
    public void update() {
        AbstractPower power = this.target.getPower(MarkPower.POWER_ID);
        if (power != null && power.amount >= this.amount) {
            this.addToBot(new StunMonsterAction((AbstractMonster) this.target, this.source));
        }

        this.isDone = true;
    }
}
