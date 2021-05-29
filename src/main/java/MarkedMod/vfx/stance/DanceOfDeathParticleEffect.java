package MarkedMod.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


@SuppressWarnings("FieldMayBeFinal")
public class DanceOfDeathParticleEffect
        extends AbstractGameEffect {
    private static AtlasRegion[] PETALS = {
            new AtlasRegion(ImageMaster.PETAL_VFX[0], 0, 0, 32, 32),
            new AtlasRegion(ImageMaster.PETAL_VFX[1], 0, 0, 32, 32),
            new AtlasRegion(ImageMaster.PETAL_VFX[2], 0, 0, 32, 32),
            new AtlasRegion(ImageMaster.PETAL_VFX[3], 0, 0, 32, 32),
            new AtlasRegion(ImageMaster.PETAL_VFX[2], 0, 0, 32, 32),
            new AtlasRegion(ImageMaster.PETAL_VFX[1], 0, 0, 32, 32)
    };

    private Hitbox hitBox;

    private float x;
    private float y;
    private float deltaY = 0;
    private float deltaX = 0;
    private float vH;
    private float vW;

    private float fadeDuration;
    private float lastDuration;
    private float maxDuration;
    private float animationSpeed;
    private int index = 0;
    private AtlasRegion img;


    public DanceOfDeathParticleEffect() {
        this.img = PETALS[0];

        this.duration = MathUtils.random(3.5F, 5.0F);
        this.maxDuration = this.lastDuration = this.duration;
        this.fadeDuration = this.duration / 3.0F;

        this.color = Color.PINK.cpy();
        this.color.mul(MathUtils.random(0.8f, 1.2f),
                       MathUtils.random(0.8f, 1.2f),
                       MathUtils.random(0.8f, 1.2f),
                       1.0f);

        this.scale = MathUtils.random(0.6F, 1.0F) * 0.5f * Settings.scale;
        this.hitBox = AbstractDungeon.player.hb;

        this.x = hitBox.cX - (float) this.img.packedWidth / 2.0F +
                 MathUtils.random(
                         (-hitBox.width / 2.0F - 30.0F * Settings.scale) * 1.25f,
                         /* -hitBox.width / 2.0F + 30.0F * Settings.scale */ 0.0f);

        this.y = hitBox.cY - (float) this.img.packedHeight / 2.0F * Settings.scale +
                 MathUtils.random(
                         hitBox.height / 2.0F + 30.0F * Settings.scale,
                         hitBox.height / 2.0F - 10.0F * Settings.scale);

        this.vH = MathUtils.random(0.5f, 1.125f);
        this.vW = MathUtils.random(0.875f, 1.125f);

        this.renderBehind = MathUtils.randomBoolean(0.8F + (this.scale - 0.5F));
        this.rotation = MathUtils.random(-33.0F, 33.0F);
        this.animationSpeed = MathUtils.random(0.125f, 0.25f);
    }


    @Override
    public void update() {

        if (this.duration < this.fadeDuration) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, MathUtils.clamp(this.duration / this.fadeDuration, 0.0f, 1.0f));
        }

        this.deltaY = Interpolation.sine.apply(1.0f, 0.0f, this.duration / this.maxDuration) * hitBox.height * this.vH;

        this.deltaX = Interpolation.circleOut.apply(1.0f, 0.0f, this.duration / this.maxDuration) *
                      hitBox.width *
                      1.2f *
                      this.vW;

        this.rotation -= MathUtils.random(0.1f, 0.15f);

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }


    @Override
    public void render(SpriteBatch sb) {
        while (this.lastDuration >= this.duration) {
            this.lastDuration -= animationSpeed;
            this.index = (this.index + 1) % PETALS.length;
        }

        this.img = PETALS[this.index];

        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);

        sb.draw(this.img,
                this.x + this.deltaX,
                this.y - this.deltaY,
                (float) this.img.packedWidth / 2.0F,
                (float) this.img.packedHeight / 2.0F,
                (float) this.img.packedWidth,
                (float) this.img.packedHeight,
                this.scale,
                this.scale,
                this.rotation);

        sb.setBlendFunction(770, 771);
    }


    @Override
    public void dispose() {
    }
}
