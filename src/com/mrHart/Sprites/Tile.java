package com.mrHart.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mrHart.Assets.AssetLoader;
import com.mrHart.Depth.DepthRenderable;
import com.mrHart.Handlers.Collisions.UnMovableRect;

/**
 * Creates tiles on the screen. There are a lot of static methods that affect
 * all the tiles created. Render and update are static now, they handle updating
 * and rendering every tile that you have created. When you are done using the
 * tiles, make sure to dispose them statically! Not doing so will possibly
 * result in fatal errors!
 * 
 * May be a temporary value, but right now the max amount of tiles that you can
 * create is 256.
 * 
 * @author Michael James Hart, michaeljh619@yahoo.com
 * @version v1.0
 * @since 12/30/2014
 * 
 */
public class Tile implements DepthRenderable, UnMovableRect, Comparable<DepthRenderable> {
	// Named Constants
	public static final int TILE_SIZE = 25;
	public static int NUM_TILES_CREATED = 0;
	public static Tile[] ALL_TILES = new Tile[255];
	private static int collided = 0;

	// Types of Tiles
	public static final int TYPE_ROCK = 1;

	// File names of Types
	private static TextureRegion rockTextureRegion = AssetLoader.level_tr_Rock;

	// Instance Variables
	private int type; // The type of tile
	private int positionX; // Position x
	private int positionY; // Position y
	private int numTilesX; // This is to determine how many tiles you want to
							// create in the x direction
	private int numTilesY; // This is to determine how many tiles you want to
							// create in the y direction

	private TextureRegion textureRegion; // Used to determine which TR to use
	private Rectangle rect;

	private Tile[][] tileArray; // Will be used to create a rectangle of tiles
								// if so specified

	/*****************************************
	 * Main Methods
	 *****************************************/

	/**
	 * Creates a new rectangular region of tiles. If you want to create just one
	 * tile, then specify numX and numY to 1 and the constructor will take care
	 * not to initialize the array, thereby maximizing efficiency.
	 * 
	 * @param inType
	 * @param inPositionX
	 * @param inPositionY
	 * @param inNumTilesX
	 * @param inNumTilesY
	 */
	public Tile(int inType, int inPositionX, int inPositionY, int inNumTilesX,
			int inNumTilesY) {
		// Add this tile to the static array to check for it's collisions
		ALL_TILES[NUM_TILES_CREATED++] = this;

		type = inType;
		positionX = inPositionX;
		positionY = inPositionY;
		numTilesX = inNumTilesX;
		numTilesY = inNumTilesY;

		// Matrix of tiles
		if (inNumTilesX > 1 || inNumTilesY > 1) {
			tileArray = new Tile[numTilesX][numTilesY];
			setupTileArray();
		}

		rect = new Rectangle(positionX, positionY, numTilesX * TILE_SIZE,
				(numTilesY * TILE_SIZE));

		setupTextureRegion();
	}

	/**
	 * Private Constructor for creating one tile at a location, should only be
	 * used when a matrix of Tiles is creating tiles.
	 */
	private Tile(int inType, int inPositionX, int inPositionY) {
		type = inType;
		positionX = inPositionX;
		positionY = inPositionY;

		setupTextureRegion();
	}

	/**
	 * Renders the tile(s) to the screen.
	 * 
	 * @param batcher
	 *            The already begun spritebatch that will render the tile
	 */
	public void render(SpriteBatch batcher, float runtime) {
		// Uses recursion, basically the matrix will only call the individual
		// tiles render methods
		if (tileArray != null) {
			for (int x = 0; x < numTilesX; x++) {

				for (int y = 0; y < numTilesY; y++) {
					tileArray[x][y].render(batcher, runtime);
				}

			}
		} else {
			batcher.draw(textureRegion, positionX, positionY);
		}
	}

	/*****************************************
	 * Main Methods [END]
	 *****************************************/

	/*****************************************
	 * Setup Methods
	 *****************************************/

	/**
	 * Sets up the tile matrix
	 */
	private void setupTileArray() {
		for (int x = 0; x < numTilesX; x++) {

			for (int y = 0; y < numTilesY; y++) {
				tileArray[x][y] = new Tile(type, (positionX + TILE_SIZE * x),
						(positionY + TILE_SIZE * y));
			}

		}
	}

	/**
	 * Chooses which texture region to used based on constructed type
	 */
	private void setupTextureRegion() {
		// Set up the file name
		switch (type) {
		case TYPE_ROCK:
			textureRegion = rockTextureRegion;
		}
	}

	/*****************************************
	 * Setup Methods [END]
	 *****************************************/
	


	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	@Override
	public float getPositionY() {
		return positionY;
	}

	@Override
	public Rectangle getCollisionBox() {
		return rect;
	}
	
	@Override
	public int compareTo(DepthRenderable arg0) {
		if(positionY > arg0.getPositionY())
			return 1;
		else if(positionY < arg0.getPositionY())
			return -1;
		else
			return 0;
	}
}