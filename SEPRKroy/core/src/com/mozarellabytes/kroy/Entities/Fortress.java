package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class Fortress {

    private GameScreen gameScreen;
    private float type, HP, AP, maxHP, range;
    private Vector2  position;
    private Texture texture;
    private String name;
    private Random rand;
    private ArrayList<Bomb> bombs;
    private long lastFire;
    private int delay;

    public Fortress(GameScreen gameScreen, float x, float y, float range, float maxHP, float AP) {
        this.gameScreen = gameScreen;
        this.position = new Vector2(x, y);
        this.range = range;
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.AP = AP;
        this.texture = new Texture(Gdx.files.internal("sprites/fortress.png"));
        this.rand = new Random();
        this.name = "Fortress";
        this.bombs = new ArrayList<Bomb>();
        this.lastFire = System.currentTimeMillis();
        this.delay = 1500;
    }

    public void checkRange(FireTruck target) {
        Vector2 targetPos = new Vector2(((float) (target.getPosition().x + 0.5)), (float) (target.getPosition().y + 0.5));
        if (targetPos.dst(this.position) <= range) {
            ArrayList<Vector2> truckTarget = new ArrayList<Vector2>();
            for (int i = -2; i < 2; i++){
                for (int j = -2; j < 2; j++){
                    truckTarget.add(new Vector2((float)targetPos.x + i, (float) (targetPos.y + j)));
                }
            }
            int randomIndex = rand.nextInt(truckTarget.size());
            if (truckTarget.get(randomIndex).equals(targetPos)){
                attack(target);
            }
        }
    }

    private void attack(FireTruck target){
        if (this.lastFire + this.delay < System.currentTimeMillis()) {
            this.lastFire = System.currentTimeMillis();
            this.bombs.add(new Bomb(this.position.x, this.position.y, target, this.AP, 3f));
        }
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public float getRange() {
        return this.range;
    }

    public float getHP() {
        return this.HP;
    }

    public float getMaxHP() {
        return this.maxHP;
    }

    public void damage(float HP){
        this.HP -= HP;
    }

    public Rectangle getArea() {
        Rectangle area = new Rectangle();
        area.x = this.position.x-2;
        area.y = this.position.y-2;
        area.width = 4;
        area.height = 6;
        return area;
    }

    public String getName() {
        return this.name;
    }

    public float getAP() {
        return this.AP;
    }

    public ArrayList<Bomb> getBombs() {
        return this.bombs;
    }

    public void removeBomb(Bomb bomb) {
        this.bombs.remove(bomb);
    }

    public long lastFired() {
        return this.lastFire;
    }
}

