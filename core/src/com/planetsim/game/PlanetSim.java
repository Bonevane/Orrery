package com.planetsim.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.planetsim.camera.Camera;
import com.planetsim.input.Controller;
import com.planetsim.input.Gestures;
import com.planetsim.input.KeyManager;
import com.planetsim.states.SaveState;
import com.planetsim.states.State;
import com.planetsim.states.UniverseState;
import com.planetsim.universe.Universe;

public class PlanetSim extends ApplicationAdapter{

	private int width, height;
	public static int initialWidth, initialHeight;


	private ShapeRenderer g;
	private BitmapFont font;
	private BitmapFont font2;
	private SpriteBatch batch;
	private Textures textures;
	public static Stage stage;


	//Input
	private KeyManager keyManager;
	private Controller controller;
	private InputMultiplexer multiplexer;

	//States
	public State menuState;
	public State universeState;

	//Camera
	private Camera camera;
	private Matrix4 defaultProjection;


	public Music bgm;
	public Sound collision;
	public boolean muted;

	//config
	public boolean debugMode = false;

	//Handler
	private Handler handler;
	public SaveState saveFile;



	public PlanetSim() {}

	private void init()
	{
		this.width = Gdx.graphics.getWidth();
		initialWidth = width;
		this.height = Gdx.graphics.getHeight();
		initialHeight = height;

		//handler
		this.handler = new Handler(this);
		this.controller = new Controller(handler);
		this.handler.setController(controller);

		textures = new Textures();
		stage = new Stage(new ScreenViewport());

		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(new Gestures(handler)));
		multiplexer.addProcessor(new InputAdapter() {@Override
			public boolean scrolled(float amountX, float amountY) {
				if(amountX > 0 || amountY > 0)
					handler.getCamera().zoomOut();
				else if (amountX < 0 || amountY < 0)
					handler.getCamera().zoomIn();
				return false;
			}});
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);

		this.camera = new Camera(this, handler.getUniverse(), handler);

		//Setting the default state
		this.universeState = new UniverseState(handler);
		State.setState(universeState);
	}

	private void tick()
	{
		keyManager.tick(camera);
		camera.setCamToOrbit();
		if(State.getState() != null)
			State.getState().tick();
	}

	@Override
	public void create () {
		setCursor();
		init();

		saveFile = new SaveState(handler);
		batch = new SpriteBatch();
		g = new ShapeRenderer();
		keyManager = new KeyManager(handler);
		font = new BitmapFont();//Gdx.files.internal("font.fnt"));
		font2 = new BitmapFont(Gdx.files.internal("font.fnt"));
		defaultProjection = new Matrix4(batch.getProjectionMatrix());
		bgm = Gdx.audio.newMusic(Gdx.files.internal("Glory.ogg"));
		bgm.play(); bgm.setLooping(true);
		collision = Gdx.audio.newSound(Gdx.files.internal("selection.ogg"));
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.dispose();
		batch = new SpriteBatch();
		defaultProjection = new Matrix4(batch.getProjectionMatrix());

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera.getCamera().viewportWidth = width;
		camera.getCamera().viewportHeight = height;
		camera.getCamera().update();
		g.setProjectionMatrix(camera.getCamera().combined);

		if(State.getState() != null) {
			State.getState().render(g);
		}
		if(debugMode)
		{
			System.out.println(height);
			batch.begin();
			batch.setProjectionMatrix(defaultProjection);
			font.setColor(Color.YELLOW);
			font.getData().setScale(1);
			font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - 12);
			font.draw(batch,"Entities: "+handler.getUniverse().getEntities().size(), 0, height - 36);
			font.draw(batch,"Zoom: "+ handler.getCamera().getZoomLevel(), 0, height - 60);
			font.draw(batch,"Paused: "+ handler.getController().pausedGame, 0, height - 84);
			font.draw(batch,"Draw Tails: "+ handler.getController().drawTails, 0, height - 106);
			font.draw(batch,"Trail Length: "+ Universe.trailSize, 0, height - 128);
			font.draw(batch,"Field Strength: "+ handler.getUniverse().GRAVITATIONAL_CONSTANT, 0, height - 150);
			font.draw(batch,"X: "+ camera.getXPos(), 0, height - 174);
			font.draw(batch,"Y: "+ camera.getYPos(), 0, height - 198);

			batch.end();
		}
		if(muted){
			batch.begin();
			batch.draw(Textures.mute, width - (160 * handler.getHeight() / 720.0f), height - (90 * handler.getHeight() / 720.0f), 50, 50);
			batch.end();
		}
		if(controller.pausedGame){
			batch.begin();
			batch.draw(Textures.pause, width - (220 * handler.getHeight() / 720.0f), height - (90 * handler.getHeight() / 720.0f), 50, 50);
			//font2.setColor(0.9f, 0.9f, 0.9f, 1.0f);
			//font2.draw(batch, "PAUSED", width / 2.0f - 120, height / 2.0f, 200, 0, true);
			batch.end();
		}

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		tick();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		g.dispose();
		font.dispose();
		font2.dispose();
		collision.dispose();
		bgm.dispose();
	}

	private void setCursor(){
		Pixmap pixmap = new Pixmap(Gdx.files.internal("cursor.png"));
		int xHotspot = 0, yHotspot = 0;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose();
		Gdx.graphics.setCursor(cursor);
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public Camera getCamera()
	{
		return this.camera;
	}

	public KeyManager getKeyManager()
	{
		return this.keyManager;
	}

	public SpriteBatch getSpriteBatch() { return this.batch; }

	public ShapeRenderer getShapeRenderer() { return this.g; }

	public BitmapFont getFont() { return this.font; }

	public Matrix4 getDefaultProjection() { return this.defaultProjection; }
}
