package MarkedMod.stances;

import MarkedMod.MarkedMod;
import MarkedMod.vfx.stance.DanceOfDeathParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;


public class DanceOfDeathStance extends AbstractStance
{
public static final String STANCE_ID = MarkedMod.makeID(DanceOfDeathStance.class.getSimpleName());
private static final PowerStrings stanceString = CardCrawlGame.languagePack.getPowerStrings(STANCE_ID);
public static final String NAME = stanceString.NAME;
public static final String[] DESCRIPTIONS = stanceString.DESCRIPTIONS;
private static long sfxId = -1L;

public DanceOfDeathStance() {
    this.ID = STANCE_ID;
    this.name = NAME;
    this.updateDescription();
}

public void updateAnimation() {
    if (!Settings.DISABLE_EFFECTS) {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.2F;
            AbstractDungeon.effectsQueue.add(new DanceOfDeathParticleEffect());
        }
    }

    this.particleTimer2 -= Gdx.graphics.getDeltaTime();
    if (this.particleTimer2 < 0.0F) {
        this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
        AbstractDungeon.effectsQueue.add(new StanceAuraEffect(STANCE_ID));
    }

}

public void atStartOfTurn() {
    AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(this.ID));
}

public float atDamageGive(float damage, DamageInfo.DamageType type)
{
    return damage;
}

public void updateDescription() {
    this.description = DESCRIPTIONS[0];
}

public void onEnterStance() {
    if (sfxId != -1L) {
        this.stopIdleSfx();
    }

    CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
    sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_WRATH");
    AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SLATE, true));
    AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Divinity"));
    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
}

public void onExitStance() {
    this.stopIdleSfx();
}

public void stopIdleSfx()
{
    if (sfxId != -1L)
    {
        CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
        sfxId = -1L;
    }
}
}
