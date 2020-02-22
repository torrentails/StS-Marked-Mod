package MarkedMod.stances;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractCustomStance;
import MarkedMod.vfx.stance.DanceOfDeathParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;


public class DanceOfDeathStance extends AbstractCustomStance
{
    public static final String STANCE_ID = MarkedMod.makeID(DanceOfDeathStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceString.NAME;
    public static final String[] DESCRIPTIONS = stanceString.DESCRIPTION;
    public static final float[] COLORS =
            {54.4f,     // red min
             105.6f,    // red max
             174.4f,    // green min
             225.6f,    // green max
             94.4f,     // blue min
             145.6f};   // blue max

    private static Color cachedColor = null;

    private static final String ENTER_SOUND = "STANCE_ENTER_CALM";
    private static final String LOOP_SOUND = "STANCE_LOOP_DIVINITY";

    private static final int MARK = 2;
    private static float TIMER = 0.1F;
    private static long sfxId = -1L;


    public DanceOfDeathStance() {
        this.ID = STANCE_ID;
        this.name = NAME;
        this.particleTimer = TIMER;
        this.updateDescription();
    }


    @Override
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = TIMER;
                AbstractDungeon.effectsQueue.add(new DanceOfDeathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect(STANCE_ID));
        }

    }


    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }



    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            this.stopIdleSfx();
        }

        CardCrawlGame.sound.play(ENTER_SOUND);
        sfxId = CardCrawlGame.sound.playAndLoop(LOOP_SOUND);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(getColor(1.0f), true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.ID));
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(AbstractDungeon.player, AbstractDungeon.player));
    }


    @Override
    public void stopIdleSfx()
    {
        if (sfxId != -1L)
        {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        }
    }


    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        AbstractCreature source = info.owner;
        if (info.type == DamageInfo.DamageType.NORMAL && target.hasPower(MarkPower.POWER_ID) && target != source)
        {
            float markAmount = (float)target.getPower(MarkPower.POWER_ID).amount;
            if (markAmount > 1.0f) {
                damageAmount += (int)(markAmount * 0.5f + 0.5f);
            }
        }

        return damageAmount;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, info.owner, new MarkPower(target, MARK), MARK));

    }

    public static Color getColor(float a) {
        if (cachedColor == null)
        {
            float r = MathUtils.random(COLORS[0], COLORS[1]);
            float g = MathUtils.random(COLORS[2], COLORS[3]);
            float b = MathUtils.random(COLORS[4], COLORS[5]);

            cachedColor = CardHelper.getColor(r,g,b);
        }

        Color color = new Color(cachedColor);

        if (a > 1.0f) { a = a / 255.0f; }
        color.a = a;

        return color;
    }
}
