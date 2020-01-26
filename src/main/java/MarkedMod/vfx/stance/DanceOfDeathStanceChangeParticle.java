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


public class DanceOfDeathStanceChangeParticle extends AbstractGameEffect
{
private TextureAtlas.AtlasRegion img;
private float x;
private float y;
private float delayTimer;

public DanceOfDeathStanceChangeParticle(float playerX) {
    this.img = ImageMaster.STRIKE_LINE;// 16
    this.startingDuration = 1.0F;// 20
    this.duration = this.startingDuration;// 21
    Float colorVal = MathUtils.random(0.1F, 0.3F);
    this.color = new Color(colorVal, colorVal, colorVal, 0.0F);// 22
    this.x = MathUtils.random(-30.0F, 30.0F) * Settings.scale - (float)this.img.packedWidth / 2.0F;// 23
    this.y = (float)Settings.HEIGHT / 2.0F + MathUtils.random(-150.0F, 150.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;// 24
    this.scale = MathUtils.random(2.2F, 2.5F) * Settings.scale;// 25
    this.delayTimer = MathUtils.random(0.5F);// 26
    this.rotation = MathUtils.random(89.0F, 91.0F);// 27
    this.renderBehind = MathUtils.randomBoolean(0.9F);// 28
}// 29

public void update() {
    if (this.delayTimer > 0.0F) {// 32
        this.delayTimer -= Gdx.graphics.getDeltaTime();// 33
    } else {
        this.duration -= Gdx.graphics.getDeltaTime();// 37
        if (this.duration < 0.0F) {// 38
            this.isDone = true;// 39
        } else {
            if (this.duration > this.startingDuration / 2.0F) {// 43
                this.color.a = Interpolation.pow3In.apply(0.6F, 0.0F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));// 44
            } else {
                this.color.a = Interpolation.fade.apply(0.0F, 0.6F, this.duration / (this.startingDuration / 2.0F));// 49
            }

        }
    }
}// 34 40 51

public void render(SpriteBatch sb) {
    if (this.delayTimer <= 0.0F) {// 55
        sb.setColor(this.color);// 59
        sb.setBlendFunction(770, 1);// 60
        sb.draw(this.img, AbstractDungeon.player.hb.cX + this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(2.9F, 3.1F), this.scale * MathUtils.random(0.95F, 1.05F), this.rotation);// 61 69 70
        sb.setBlendFunction(770, 771);// 72
    }
}// 56 73

public void dispose() {
}// 77
}