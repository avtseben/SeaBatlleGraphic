package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;


public class MainClass extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont fnt;
	HumanPlayer pl1;
	AiPlayer pl2;
	GameManager game;
	public static Random rand = new Random();
	public final static int LEFT_INDENT = 117;
	public final static int BOTTOM_INDENT = 96;
	final static int NEXT_FIELD_INDENT = 520;

	@Override
	public void create () {
		batch = new SpriteBatch();
		pl1 = new HumanPlayer(new SeaField(LEFT_INDENT,BOTTOM_INDENT));
		pl2 = new AiPlayer(new SeaField(LEFT_INDENT+NEXT_FIELD_INDENT,BOTTOM_INDENT));
		fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
		game = new GameManager(pl1,pl2);

	}


	@Override
	public void render () {

        update();
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
		game.render(batch);
        batch.end();
	}
	public void update() {
	}
}
