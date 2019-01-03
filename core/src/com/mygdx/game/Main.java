package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends ApplicationAdapter {

    public OrthographicCamera camera;
    FillViewport viewport;

    public static final int screenWidth=1800;
    public static final int screenHeight=1000;

    SpriteBatch batch;
    Sprite target;
    int popSize = 50;

    ArrayList<Dna> population;
    ArrayList<Dna> matingPool;

    ArrayList<Double> fittnesses;

    int counter = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.position.set(screenWidth/2, screenHeight/2, 0);
        viewport = new FillViewport(screenWidth, screenHeight, camera);


        population = new ArrayList<Dna>();
        matingPool = new ArrayList<Dna>();

        fittnesses = new ArrayList<Double>();
        target = new Sprite(new Texture("target.png"));
        target.setPosition(1500, 800);
        target.setSize(target.getWidth() * 0.5f, target.getHeight() * 0.5f);

        for (int i = 0; i < popSize; i++) {
            population.add(new Dna());
        }

    }

    private void update(float delta) {
        fittnesses.clear();
        matingPool.clear();
        for (Dna rocket : population) {
            rocket.pos.add(rocket.genes[counter]);
            rocket.rocket.setPosition(rocket.pos.x, rocket.pos.y);
            rocket.rocket.setRotation(rocket.genes[counter].angle());
            if (counter == rocket.genes.length-1) {
                // new generation
                counter = 0;
                for (Dna r : population){
                    r.calcFitness();
                    fittnesses.add(r.fitness);
                }
                //normalize the fittnes data
                ArrayList<Double> normalised = norm(fittnesses);

                for (int i = 0; i < normalised.size(); i++) {
                    for (int j = 0; j < normalised.get(i) * 100; j++) {
                        // TODO clear this later
                        matingPool.add(population.get(i));
                    }
                }
                // pick best 2 rocket and make child, do this 50 times to make new population
                for (int i = 0; i < popSize; i++) {
                    Dna child = matingPool.get(MathUtils.random(matingPool.size()-1)).crossOver(matingPool.get(MathUtils.random(matingPool.size()-1)));
                    child.mutate();
                    population.set(i,child);
                }
            }

        }
        counter++;


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        target.draw(batch);
        for(Dna rocket: population){
            rocket.rocket.draw(batch);
        }
        batch.end();

        update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public ArrayList<Double> norm(ArrayList<Double> f){
        double max = Collections.max(f);
        double min = Collections.min(f);
        double delta = max - min;
        ArrayList<Double> res = new ArrayList<Double>();

        for(double fit:f){
            res.add((fit - min) / delta);
        }
        return res;
    }
}
