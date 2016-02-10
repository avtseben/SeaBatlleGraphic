package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java.util.Random;


public class MainClass extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont fnt;
	HumanPlayerField gf;
	aiPlayerField gf2;
	GameManager game;
	public static Random rand = new Random();
	final int LEFT_INDENT = 117;
	final int BOTTOM_INDENT = 45;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gf = new HumanPlayerField(LEFT_INDENT,BOTTOM_INDENT);
		gf2 = new aiPlayerField(LEFT_INDENT+520,BOTTOM_INDENT);
		fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
		game = new GameManager(gf,gf2);

	}


	@Override
	public void render () {

        update();
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        gf.render(batch);
        gf2.render(batch);
        batch.end();
	}
	public void update() {
	}
}
