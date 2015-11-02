package com.mrHart.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mrHart.Assets.AssetLoader;
import com.mrHart.Assets.EnemyLoader;
import com.mrHart.Tools.Timer;

/**
 * For Twinjas, current dialog box width is 31 chars.
 * 
 * @author Michael
 *
 */
public class Dialog extends Window {
	/*
	 * Constants
	 */
	// Standards
	public static final int POSITION_X = 100;
	public static final int POSITION_Y = 275;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 200;
	// Speakers
	private static final int SPEAKER_POSITION_Y = 35;
	private static final int SPEAKER_POSITION_X_LEFT = 15;
	private static final int SPEAKER_POSITION_X_RIGHT = 615;
	private static final int SPEAKER_POSITION_X_OFFSET = 300;
	private static final int SPEAKER_SCROLL_SPEED = 30;
	// Positioning
	public final static float OFFSET_LEFT_BORDER = 0.06f;
	public final static float OFFSET_TOP_BORDER = 0.11f;
	public final static int DIRECTION_OFFSET = 70;
	// Characters
	public final static int CHAR_NINJA_MALE = 0;
	public final static int CHAR_NINJA_FEMALE = 1;
	public final static int CHAR_NINJA_PIG = 2;
	public final static int CHAR_BOSS_1 = 3;
	// Directions
	public final static int DIRECTION_LEFT = 0;
	public final static int DIRECTION_RIGHT = 1;
	// Font
	public static final float FONT_SIZE_STANDARD = 0.55f;
	public static final String FONT_FILE = "graphics/font/Narkisim.fnt";
	/*
	 * Instance Vars
	 */
	// Font
	private BitmapFont font;
	// Strings
	private String text;
	private String rollingText = "";
	private int currentIndex = 0;
	// Timer stuff
	private float charsPerSecond, interval;
	private Timer timer;
	private Timer finishedTimer;
	private int finishedMillisecondsDelay;
	// Display Position
	private float offsetX, offsetY;
	private float fontSize;
	// Speaker
	private Animation animation;
	private int character;
	private int direction;
	private boolean wasFlipped;
	private Vector2 speakerPosition;
	private int speakerOffsetX, speakerOffsetY;
	private float speakerWidthScale, speakerHeightScale;
	private boolean isDoneSpeaking = false;
	private boolean commenceOutro = false;
	private boolean flipOnce = false;
	public boolean isFinished = false;
	private boolean alreadyFlipped = false;
	
	public Dialog(String inText, float charactersPerSecond, int millisecondsDelay){
		// Setup parent constructor
		super(POSITION_X, POSITION_Y, WIDTH, HEIGHT);
		// Setup fonts
		fontSize = FONT_SIZE_STANDARD;
		font = new BitmapFont(Gdx.files.internal(FONT_FILE));
		font.setScale(fontSize, -fontSize);
		// Set text
		text = inText;
		// Setup timer stuff
		charsPerSecond = charactersPerSecond;
		interval = (1.0f/charsPerSecond)*1000;
		timer = new Timer();
		timer.initMilliseconds((long) interval); 
		// Setup positioning
		offsetX = OFFSET_LEFT_BORDER;
		offsetY = OFFSET_TOP_BORDER;
	  // Setup finished delay
		finishedMillisecondsDelay = millisecondsDelay;
	}
	
/**
 * Creates a standard dialog box that does not have rolling text
 * 
 * @param topLeftX
 * @param topLeftY
 * @param inWidth
 * @param inHeight
 * @param inOffsetX
 * @param inOffsetY
 * @param inFontSize
 * @param inText Text to write to screen
 * @param millisecondsDelay Amount of time to wait after starting to finish
 */
	public Dialog(int topLeftX, int topLeftY, int inWidth, int inHeight,
			float inOffsetX, float inOffsetY, float inFontSize, String inText, 
			int millisecondsDelay) {
		// Setup parent constructor
		super(topLeftX, topLeftY, inWidth, inHeight);
		// Setup fonts
		fontSize = inFontSize;
		font = new BitmapFont(Gdx.files.internal(FONT_FILE));
		font.setScale(fontSize, -fontSize);
		// Set text
		text = inText;
		// Setup positioning
		offsetX = inOffsetX;
		offsetY = inOffsetY;
		// Setup finished delay
		finishedMillisecondsDelay = millisecondsDelay;
		// Set up timer
		finishedTimer = new Timer();
		finishedTimer.initMilliseconds(finishedMillisecondsDelay);
	}

	/**
	 * Creates a dialog box that has rolling text
	 * 
	 * @param topLeftX
	 * @param topLeftY
	 * @param inWidth
	 * @param inHeight
	 * @param inText The text that should be displayed
	 * @param charactersPerSecond How fast the text should be written to screen
	 * @param millisecondsDelay The amount of time to wait after all the text has been written out
	 */
	public Dialog(int topLeftX, int topLeftY, int inWidth, int inHeight,
			float inOffsetX, float inOffsetY, float inFontSize, String inText, float charactersPerSecond,
			int millisecondsDelay) {
		// Setup parent constructor
		super(topLeftX, topLeftY, inWidth, inHeight);
		// Setup fonts
		fontSize = inFontSize;
		font = new BitmapFont(Gdx.files.internal(FONT_FILE));
		font.setScale(fontSize, -fontSize);
		// Set text
		text = inText;
		// Setup timer stuff
		charsPerSecond = charactersPerSecond;
		interval = (1.0f/charsPerSecond)*1000;
		timer = new Timer();
		timer.initMilliseconds((long) interval); 
		// Setup positioning
		offsetX = inOffsetX;
		offsetY = inOffsetY;
		// Setup finished delay
		finishedMillisecondsDelay = millisecondsDelay;
	}
	
	/**
	 * Update method used for rolling text dialog
	 */
	public void update(){
		// Check if the timer is done and is not null to proceed, AKA rolling text dialog
	  // Check if the current index is below the text length
		if(timer != null && timer.isDone()
				&& currentIndex < text.length()){
				rollingText += text.charAt(currentIndex);
				currentIndex++;
				timer.initMilliseconds((long) interval); 
		}
		else if(timer != null && currentIndex >= text.length()
				&& !isDoneSpeaking){
			// Only occurs once
			isDoneSpeaking = true;
			// Set up timer
			finishedTimer = new Timer();
			finishedTimer.initMilliseconds(finishedMillisecondsDelay);
		}
		
		// Check if the finished delay is over
		if(finishedTimer != null && finishedTimer.isDone()){
			commenceOutro = true;
		}
		// Check if we are moving the speaker out of the screen
		if(commenceOutro){
			// Move the speaker away from the speaking spot
			if(direction == DIRECTION_RIGHT && 
					speakerPosition.x >= (SPEAKER_POSITION_X_LEFT - SPEAKER_POSITION_X_OFFSET)){
				speakerPosition.x -= SPEAKER_SCROLL_SPEED;
			}
			else if(direction == DIRECTION_RIGHT && 
					speakerPosition.x < (SPEAKER_POSITION_X_LEFT - SPEAKER_POSITION_X_OFFSET)){
				isFinished = true;
			}
			
			if(direction == DIRECTION_LEFT && 
					speakerPosition.x <= (SPEAKER_POSITION_X_RIGHT + SPEAKER_POSITION_X_OFFSET)){
				speakerPosition.x += SPEAKER_SCROLL_SPEED;
			}
			else if(direction == DIRECTION_LEFT && 
					speakerPosition.x > (SPEAKER_POSITION_X_RIGHT + SPEAKER_POSITION_X_OFFSET)){
				isFinished = true;
			}
		}
		
		// Move the speaker towards the speaking spot
		if(direction == DIRECTION_RIGHT && speakerPosition.x <= SPEAKER_POSITION_X_LEFT
				&& !isDoneSpeaking){
			speakerPosition.x += SPEAKER_SCROLL_SPEED;
		}
		else if(direction == DIRECTION_LEFT && speakerPosition.x >= SPEAKER_POSITION_X_RIGHT
				&& !isDoneSpeaking){
			speakerPosition.x -= SPEAKER_SCROLL_SPEED;
		}
	}
	
	/**
	 * Render Method
	 */
	public void render(SpriteBatch batcher, float runtime, int cameraPositionX){
		// Render the window
		if(direction == DIRECTION_LEFT){
			super.render(batcher, cameraPositionX - 400 - DIRECTION_OFFSET, 0);
			// Render the font depending on whether it is rolling text or straight text
			if(timer == null){
				font.drawWrapped(batcher, text, cameraPositionX - 400 - DIRECTION_OFFSET + position.x + (width * offsetX), 
						position.y + (height * offsetY), width - (width * (offsetX*2)));
			}
			else{
				font.drawWrapped(batcher, rollingText, cameraPositionX - 400 - DIRECTION_OFFSET + position.x + (width * offsetX), 
						position.y + (height * offsetY), width - (width * (offsetX*2)));
			}
		}
		else{
			super.render(batcher, cameraPositionX - 400 + DIRECTION_OFFSET, 0);
			// Render the font depending on whether it is rolling text or straight text
			if(timer == null){
				font.drawWrapped(batcher, text, cameraPositionX - 400 + DIRECTION_OFFSET + position.x + (width * offsetX), 
						position.y + (height * offsetY), width - (width * (offsetX*2)));
			}
			else{
				font.drawWrapped(batcher, rollingText, cameraPositionX - 400 + DIRECTION_OFFSET + position.x + (width * offsetX), 
						position.y + (height * offsetY), width - (width * (offsetX*2)));
			}
		}
		
		// Flip the animation once if the direction is switched
		if(!flipOnce){
			// Make sure the direction is the correct direction
			if(direction == DIRECTION_LEFT && !alreadyFlipped){
				for(int x = 0; x < animation.getKeyFrames().length; x++){
					if(!animation.getKeyFrames()[x].isFlipX()){
						wasFlipped = true;
						animation.getKeyFrames()[x].flip(true, false);
					}
					else{
						wasFlipped = false;
						break;
					}
				}
				
				speakerPosition.set(SPEAKER_POSITION_X_RIGHT + SPEAKER_POSITION_X_OFFSET,
						SPEAKER_POSITION_Y);
			}
			else if(direction == DIRECTION_RIGHT && !alreadyFlipped){
				for(int x = 0; x < animation.getKeyFrames().length; x++){
					if(animation.getKeyFrames()[x].isFlipX()){
						wasFlipped = true;
						animation.getKeyFrames()[x].flip(true, false);
					}
					else{
						wasFlipped = false;
						break;
					}
				}
				
				speakerPosition.set(SPEAKER_POSITION_X_LEFT - SPEAKER_POSITION_X_OFFSET,
						SPEAKER_POSITION_Y);
			}
			else if(direction == DIRECTION_LEFT && alreadyFlipped){
				for(int x = 0; x < animation.getKeyFrames().length; x++){
					if(animation.getKeyFrames()[x].isFlipX()){
						wasFlipped = true;
						animation.getKeyFrames()[x].flip(true, false);
					}
					else{
						wasFlipped = false;
						break;
					}
				}
				
				speakerPosition.set(SPEAKER_POSITION_X_RIGHT + SPEAKER_POSITION_X_OFFSET,
						SPEAKER_POSITION_Y);
			}
			else if(direction == DIRECTION_RIGHT && alreadyFlipped){
				for(int x = 0; x < animation.getKeyFrames().length; x++){
					if(!animation.getKeyFrames()[x].isFlipX()){
						wasFlipped = true;
						animation.getKeyFrames()[x].flip(true, false);
					}
					else{
						wasFlipped = false;
						break;
					}
				}
				
				speakerPosition.set(SPEAKER_POSITION_X_LEFT - SPEAKER_POSITION_X_OFFSET,
						SPEAKER_POSITION_Y);
			}
			
			flipOnce = true;
		}
		
		// Render the speaker
		if(animation != null){
			if(direction == DIRECTION_RIGHT)
				batcher.draw(animation.getKeyFrame(runtime), cameraPositionX - 400 + speakerPosition.x + speakerOffsetX, 
						speakerPosition.y + speakerOffsetY, 200 * speakerWidthScale, 500 * speakerHeightScale);
			else
				batcher.draw(animation.getKeyFrame(runtime), cameraPositionX - 400 + speakerPosition.x - speakerOffsetX, 
						speakerPosition.y + speakerOffsetY, 200 * speakerWidthScale, 500 * speakerHeightScale);
		}
		else{
			System.err.println("Animation rendered in Dialog is null!");
		}
	}
	
	/**
	 * Disposes parent graphics and sets the used animations back to original positioning.
	 */
	public void dispose(){
		super.dispose();
		
		if(animation != null && wasFlipped){
			for(int x = 0; x < animation.getKeyFrames().length; x++){
				animation.getKeyFrames()[x].flip(true, false);
			}
		}
	}
	
	/****************************
	 * Setters
	 ****************************/
	public void setText(String inText){
		text = inText;
	}
	public void setSpeed(float inCharsPerSecond){
		charsPerSecond = inCharsPerSecond;
	}
	/**
	 * The speaker is the person who is doing the talking, the character will be placed to the
	 * right or the left of the dialog box
	 * 
	 * Pre-Condition: The speaker must already be loaded into RAM with the asset loader!
	 * 								This is to prevent unnecessary extra graphics from being loaded.
	 * Post-Condition: The animation for the speaker may be flipped depending on the side 
	 * 								 the speaker was supposed to be on, so ensure you dispose this object
	 * 								 after using so it flips the keyframes back to the correct position.
	 * 
	 * @param character
	 */
	public void setSpeaker(int inCharacter, int inDirection){
		character = inCharacter;
		switch(character){
		case CHAR_NINJA_MALE:
			// Set the animation
			animation =  AssetLoader.maleNinjaIdle;
			speakerOffsetX = -30;
			speakerOffsetY = 0;
			speakerWidthScale = speakerHeightScale = 1.0f;
			break;
		case CHAR_NINJA_FEMALE:
			animation = AssetLoader.femaleNinjaIdle;
			speakerOffsetX = -15;
			speakerOffsetY = 0;
			speakerWidthScale = speakerHeightScale = 1.0f;
			break;
		case CHAR_NINJA_PIG:
			animation = AssetLoader.pigNinjaIdle;
			speakerOffsetX = -20;
			speakerOffsetY = 0;
			speakerWidthScale = speakerHeightScale = 1.0f;
			break;
		case CHAR_BOSS_1:
			animation = EnemyLoader.yellowBlob_Animations[EnemyLoader.INDEX_MOVE];
			speakerOffsetX = 50;
			speakerOffsetY = 30;
			speakerWidthScale = 1.8f;
			speakerHeightScale = .9f;
			alreadyFlipped = true;
			break;
		}
		
		speakerPosition = new Vector2();
		direction = inDirection;
	}
}
