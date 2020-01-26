package MarkedMod.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class DanceOfDeathParticleEffect extends AbstractGameEffect
{
private float x;
private float y;
private float vY;
private float dur_div2;
private TextureAtlas.AtlasRegion img;

public DanceOfDeathParticleEffect() {
    this.img = ImageMaster.GLOW_SPARK;// 20
    this.duration = MathUtils.random(1.3F, 1.8F);// 21
    this.scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;// 22
    this.dur_div2 = this.duration / 2.0F;// 23
    // TODO: Edit colors here
    Float colorValue = MathUtils.random(0.1F, 0.3F);
    this.color = new Color(colorValue, colorValue, colorValue, 0.0F);// 24
    this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 30.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 30.0F * Settings.scale);// 26
    this.y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F - -10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 10.0F * Settings.scale);// 30
    this.x -= (float)this.img.packedWidth / 2.0F;// 34
    this.y -= (float)this.img.packedHeight / 2.0F;// 35
    this.renderBehind = MathUtils.randomBoolean(0.2F + (this.scale - 0.5F));// 36
    this.rotation = MathUtils.random(-8.0F, 8.0F);// 37
}// 38

public void update() {
    if (this.duration > this.dur_div2) {// 42
        this.color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);// 43
    } else {
        this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);// 45
    }

    this.vY += Gdx.graphics.getDeltaTime() * 40.0F * Settings.scale;// 47
    this.duration -= Gdx.graphics.getDeltaTime();// 48
    if (this.duration < 0.0F) {// 49
        this.isDone = true;// 50
    }

}// 52

public void render(SpriteBatch sb) {
    sb.setColor(this.color);// 56
    sb.setBlendFunction(770, 1);// 57
    sb.draw(this.img, this.x, this.y + this.vY, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.8F, (0.1F + (this.dur_div2 * 2.0F - this.duration) * 2.0F * this.scale) * Settings.scale, this.rotation);// 58
    sb.setBlendFunction(770, 771);// 69
}// 70

public void dispose() {
}// 74

}
