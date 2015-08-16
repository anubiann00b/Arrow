package me.shreyasr.arrow.entity.attack;

import me.shreyasr.arrow.Game;
import me.shreyasr.arrow.projectiles.Projectile;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.PolarVelocity;

public class AttackBow implements Attack {

    public static class Builder {

        private int fireTime = 600;
        private int reloadTime = 0;
        private int spreadShots = 1;
        private int spreadAngle = 0;
        private int barrageShots = 0;
        private int pierce = 1;
        private int damage = 5;
        private int speed = 8;
        private long powerupTimer =0;
        private long startTime=0;

        public Builder setFireTime(int fireTime) {
            this.fireTime = fireTime;
            return this;
        }

        public Builder setReloadTime(int reloadTime) {
            this.reloadTime = reloadTime;
            return this;
        }

        public Builder setSpreadShots(int spreadShots) {
            this.spreadShots = spreadShots;
            return this;
        }

        public Builder setSpreadAngle(int spreadAngle) {
            this.spreadAngle = spreadAngle;
            return this;
        }

        public Builder setBarrageShots(int barrageShots) {
            this.barrageShots = barrageShots;
            return this;
        }

        public Builder setPierce(int pierce) {
            this.pierce = pierce;
            return this;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public AttackBow create() {
            if (reloadTime == 0)
                reloadTime = fireTime;
            return new AttackBow(fireTime, reloadTime, spreadShots,
                    barrageShots, spreadAngle, pierce, damage, speed);
        }
    }
    private int timer;
    private int shotCounter;
    private final int fireTime;
    private final int reloadTime;
    private final int barrageShots;
    private final int spreadShots;
    private final double spread;
    private final int pierce;
    public static int damage;
    public static int powerup;
    public static long powerupTimer;
    public static long startTime;
    private final int speed;

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setPowerup(int powerup) {
        this.powerup = powerup;
    }
    public void setPowerupTimer(long powerupTimer){
        this.powerupTimer = powerupTimer;
    }
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    public int getDamage(){
        return damage;
    }
    public int getPowerup(){
        return powerup;
    }
    public long getPowerupTimer(){
        return powerupTimer;
    }
    public long getStartTime(){
        return startTime;
    }

    private AttackBow(int fireTime, int reloadTime, int spreadShots, int barrageShots,
                      int spread, int pierce, int damage, int speed) {
        this.fireTime = fireTime;
        this.reloadTime = reloadTime;
        this.speed = speed;
        this.spread = spread * Math.PI / 180;
        this.spreadShots = spreadShots;
        this.barrageShots = barrageShots;
        this.pierce = pierce;
        this.damage = damage;
    }

    @Override
    public void update(double delta, CartesianPosition target) {
        if (timer<0 && !target.isEmpty()) {
            double startDir = target.getDir() - spread * spreadShots /2;
            if(powerup==1 && (powerupTimer <= (System.currentTimeMillis()- startTime))) {
                Game.addMessage("Powerup expired");
                powerup = 0;
                damage = 5;
            }
            for(int i=0;i< spreadShots;i++) {
                if(powerup==1) {
                    Game.inst.addProjectile(new Projectile(new PolarVelocity((float) (startDir + i * spread), (float) speed),
                            Game.player.pos, "fire_arrow", System.currentTimeMillis(), -1));
                } else {
                    Game.inst.addProjectile(new Projectile(new PolarVelocity((float) (startDir + i * spread), (float) speed),
                            Game.player.pos, "arrow", System.currentTimeMillis(), -1));
                }
            }
            if (shotCounter <= 0) {
                timer = reloadTime;
                shotCounter = barrageShots;
            } else {
                timer = fireTime;
                shotCounter--;
            }
        }
        timer -= delta*1000/60d;
    }
}
