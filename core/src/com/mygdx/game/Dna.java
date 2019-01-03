package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by wietze on 3/11/2018.
 */
public class Dna {

    Vector2[] genes;
    double fitness = 0;

    float mutationRate = 0.01f;
    Sprite rocket;

    Vector2 pos;
    // target is hardcoded.
    Vector2 target = new Vector2(1500,800);

    float scalar = 10;

    public Dna() {

        pos = new Vector2(100,100);
        genes = new Vector2[500];
        this.rocket = new Sprite(new Texture("rocket.png"));
        this.rocket.setSize(this.rocket.getWidth()*0.3f,this.rocket.getHeight()*0.3f);
        this.rocket.setOrigin(this.rocket.getWidth()/2,this.rocket.getHeight()/2);

        for (int i = 0; i < genes.length; i++) {
            Vector2 tmp = new Vector2(MathUtils.random(-10,10),MathUtils.random(-10,10)) ;
            tmp.nor();
            tmp.scl(scalar);
            genes[i] = tmp;
        }
    }

    public void calcFitness() {
        float distance = pos.dst(target);
        // A simple fittnes funciton would be (1/distance)^2
        fitness = Math.pow(1/distance,2);
    }

    public Dna crossOver(Dna partner) {
        // Create new child, this child will inherit half of each parents genetics
        Dna child = new Dna();
        Random r = new Random();
        int midpoint = (r.nextInt(genes.length));

        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) {
                child.genes[i] = genes[i];
            } else {
                child.genes[i] = partner.genes[i];
            }


        }

        return child;
    }

    public void mutate() {
        for (int i = 0; i < genes.length; i++) {
            if(Math.random()< mutationRate){
                Vector2 tmp = new Vector2(MathUtils.random(-10,10),MathUtils.random(-10,10)) ;
                tmp.nor();
                tmp.scl(scalar);
                genes[i] = tmp;    Random r = new Random();

            }
        }
    }

}
