package com.mygdx.ipop_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.mygdx.ipop_game.IPOP;
import com.mygdx.ipop_game.models.GameRecord;
import com.mygdx.ipop_game.models.Record;
import com.mygdx.ipop_game.utils.ApiWs;
import com.mygdx.ipop_game.utils.WebServiceConstants;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class EndGameScreen implements Screen {

    private Texture background, itemBackground, saveScore;

    private Rectangle goBackBtn, goNextBtn, saveScoreBtn;
    final IPOP game;
    final GameRecord gr;
    public float stateTime = 0f;
    private ArrayList<Record> rankings = new ArrayList<>();
    private ArrayList<Record> allRankings = new ArrayList<>();

    public EndGameScreen(IPOP game, GameRecord gr) {
        this.game = game;
        this.gr = gr;
        background = new Texture(Gdx.files.internal("Mansion.png"));
        itemBackground = new Texture(Gdx.files.internal("panel-background.png"));

        saveScore = new Texture(Gdx.files.internal("save_score_button.png"));
        saveScoreBtn = new Rectangle(25 , 900, 100, 100);

    }

    @Override
    public void show() {  }

    int x = 600;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 2400, 1080);
        game.batch.draw(itemBackground, 400, 100, 1500, 750);
        game.font.getData().setScale(5.0f);

        game.font.draw(
                game.batch,
                "Username - " + gr.getAliasPlayer(),
                x, 700
        );
        game.font.draw(
                game.batch,
                "Score - " + gr.getScore(),
                x, 600
        );
        game.font.draw(
                game.batch,
                "Game Duration - " + gr.getTimeStart() + " - " + gr.getTimeEnd(),
                x, 500
        );
        game.font.draw(
                game.batch,
                "Correct Totems: " + gr.getAliasPlayer() + " / " + (gr.wrongTotems + gr.correctTotems),
                x, 400
        );

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (saveScoreBtn.contains(touchX, touchY)) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("aliasPlayer", gr.aliasPlayer);
                    json.put("timeStart", gr.timeStart);
                    json.put("timeEnd", gr.timeEnd);
                    json.put("correctTotems", gr.correctTotems);
                    json.put("wrongTotems", gr.wrongTotems);
                    json.put("nameCycle", gr.playerOcupation);
                    new ApiWs().sendPost(WebServiceConstants.api + "api/set_ranking",json);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                game.setScreen(new MainMenuScreen(game));
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {  }

    @Override
    public void pause() {  }

    @Override
    public void resume() {  }

    @Override
    public void hide() {  }

    @Override
    public void dispose() {  }
}