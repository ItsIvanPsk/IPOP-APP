package com.mygdx.ipop_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PlayingScreen implements Screen {

    private Texture spriteSheet;
    final IPOP game;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Rectangle characterBounds;
    private SpriteBatch spriteBatch;

    public PlayingScreen(IPOP game) {
        this.game = game;
        spriteSheet = new Texture(Gdx.files.internal("IPOP-Walking.png")); // Archivo con la hoja de sprites
        TextureRegion[][] spriteSheetFrames = TextureRegion.split(spriteSheet, 32, 32); // Tamaño de cada frame en la hoja de sprites
        Array<TextureRegion> animationFrames = new Array<TextureRegion>();

        // Agregar los frames de la animación al Array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animationFrames.add(spriteSheetFrames[i][j]);
            }
        }

        // Crear la animación con los frames y la velocidad de reproducción
        animation = new Animation<TextureRegion>(0.25f, animationFrames);
        stateTime = 0f;

        characterBounds = new Rectangle(100, 100, 128, 128); // Rectángulo de colisión del personaje
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Actualizar el tiempo de animación
        stateTime += delta;

        // Limpiar la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Obtener el frame actual de la animación
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        // Dibujar el frame actual en la posición del personaje
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, characterBounds.x, characterBounds.y);
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (characterBounds.contains(touchX, touchY)) {
                System.out.println("Personaje presionado");
                game.setScreen(new GameScreen(game));
            }
        }
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    // Resto de métodos de la interfaz Screen
    // ...

    @Override
    public void dispose() {
        spriteSheet.dispose();
        spriteBatch.dispose();
    }
}