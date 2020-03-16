package MarkedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;


public class RemoveHalfBlockAction extends AbstractGameAction {
    public RemoveHalfBlockAction(AbstractCreature target, AbstractCreature source) {
        this.setValues(target, source, this.amount);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.duration = 0.25f;
    }


    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == 0.25F && this.target.currentBlock > 0) {
            this.target.loseBlock(Math.floorDiv(this.target.currentBlock + 1, 2));
        }

        this.tickDuration();
    }
}