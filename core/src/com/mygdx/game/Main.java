package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;
import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite target;
    int popSize = 50;

    ArrayList<Dna> population;

    int counter = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        population = new ArrayList<Dna>();
        target = new Sprite(new Texture("target.png"));
        target.setPosition(1500, 800);
        target.setSize(target.getWidth() * 0.5f, target.getHeight() * 0.5f);

        for (int i = 0; i < popSize; i++) {
            population.add(new Dna());
        }

    }

    private void update(float delta) {
        for (Dna rocket : population) {
            rocket.pos.add(rocket.genes[counter]);
            rocket.rocket.setPosition(rocket.pos.x, rocket.pos.y);
            rocket.rocket.setRotation(rocket.genes[counter].angle());
            if (counter == rocket.genes.length-1) {
                // new generation?
                counter = 0;
                // pick best 2 rocket and make child
            } else {
                counter++;
            }
            rocket.calcFitness();
        }


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        target.draw(batch);
        for(Dna rocket: population){
            rocket.rocket.draw(batch);
        }
        batch.end();

        update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
