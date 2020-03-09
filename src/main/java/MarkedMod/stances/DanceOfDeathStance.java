package MarkedMod.stances;

import MarkedMod.MarkedMod;
import MarkedMod.abstracts.AbstractCustomStance;
import MarkedMod.actions.TriggerMarkAction;
import MarkedMod.vfx.stance.DanceOfDeathParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;


public class DanceOfDeathStance
        extends AbstractCustomStance {
    public static final String STANCE_ID = MarkedMod.makeID(DanceOfDeathStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    public static final String NAME = stanceString.NAME;
    public static final String[] DESCRIPTIONS = stanceString.DESCRIPTION;

    public static final Color COLOR_MIN = CardHelper.getColor(92, 92, 92);
    public static final Color COLOR_MAX = CardHelper.getColor(128, 128, 128);

    private static Color cachedColor = null;

    private static final String ENTER_SOUND = "STANCE_ENTER_CALM";
    private static final String LOOP_SOUND = "STANCE_LOOP_DIVINITY";
    private static long sfxId = -1L;

    // TODO: random timmer maybe?
    private static float TIMER = 0.1F;


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
        // TODO: Add a sakura tree
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(getColor(), true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.ID));
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(AbstractDungeon.player, AbstractDungeon.player));
    }


    @Override
    public void wasHPLost(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractCreature source = info.owner;
        if (info.type == DamageInfo.DamageType.NORMAL && target != source) {
            AbstractDungeon.actionManager.addToTop(new TriggerMarkAction(target));
        }
    }


    @Override
    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop(LOOP_SOUND, sfxId);
            sfxId = -1L;
        }
    }


    public static Color getColor() { return getColor(1.0f); }


    public static Color getColor(float a) {
        if (cachedColor == null) {
            cachedColor = COLOR_MIN.cpy().lerp(COLOR_MAX, MathUtils.random(1.0f));

            MarkedMod.logger.info("Caching new color: " + cachedColor.toString());
        }

        Color color = cachedColor.cpy();

        if (a > 1.0f) {
            a = a / 255.0f;
        }
        color.a = a;

        return color;
    }
}
