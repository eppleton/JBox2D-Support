/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.jbox2d;

/**
 *
 * @author antonepple
 */
import org.jbox2d.common.Vec2;
 
public class Camera {
	public final String TAG = this.getClass().getSimpleName();
 
	public Vec2 rotationOrigin = new Vec2();
	public float rotation = 0;
 
	public boolean updated = false;
 
	public Vec2 scaleWorldToScreen = new Vec2();
	public Vec2 scaleScreenToWorld = new Vec2();
 
	// Coordinate of bottom left hand corner of screen
	public Vec2 screenMin;
	// Coordinate of top right hand corner of screen
	public Vec2 screenMax;
 
	// Coordinate of bottom left hand corner of world
	public Vec2 worldMin;
	// Coordinate of bottom top right corner of world
	public Vec2 worldMax;
 
	/*
	 *  Screen max/min in world - i.e. if the world extends from 0 - 100 but you only want to display 
	 *  from 50 - 100 then screenMinInWorld = (50, 50) screenMaxInWorld(100, 100)
	 */
	public Vec2 screenMinInWorld;
	public Vec2 screenMaxInWorld;
 
 
    public Camera(Vec2 worldMin, Vec2 worldMax, Vec2 screenMin, Vec2 screenMax, Vec2 screenMinInWorld, Vec2 screenMaxInWorld) {
    	setCamera(worldMin, worldMax, screenMin, screenMax, screenMinInWorld, screenMaxInWorld);
    }
    public Camera( Vec2 worldMax, Vec2 screenMax, Vec2 screenMinInWorld, Vec2 screenMaxInWorld) {
    	setCamera(new Vec2(), worldMax, new Vec2(), screenMax, screenMinInWorld, screenMaxInWorld);
    }
    public Camera( Vec2 worldMax, Vec2 screenMax) {
    	setCamera(new Vec2(), worldMax, new Vec2(), screenMax, new Vec2(), worldMax);
    }
 
    public void setCamera(Vec2 worldMin, Vec2 worldMax, Vec2 screenMin, Vec2 screenMax, Vec2 screenMinInWorld, Vec2 screenMaxInWorld) {
 
    	scaleWorldToScreen.x = (screenMax.x-screenMin.x)/(screenMaxInWorld.x-screenMinInWorld.x);
    	scaleWorldToScreen.y = (screenMax.y-screenMin.y)/(screenMaxInWorld.y-screenMinInWorld.y);
 
    	scaleScreenToWorld.x = 1/scaleWorldToScreen.x;
    	scaleScreenToWorld.y = 1/scaleWorldToScreen.y;
 
    	this.screenMin = screenMin;
    	this.screenMax = screenMax;
    	this.worldMin = worldMin;
    	this.worldMax = worldMax;
    	this.screenMinInWorld = screenMinInWorld;
    	this.screenMaxInWorld = screenMaxInWorld;
    }
 
    public Vec2 worldToScreen(float x, float y) {
		return worldToScreen(new Vec2(x,y));
	}
    public Vec2 worldToScreen(Vec2 world) {
    	Vec2 screen = new Vec2();
    	screen.x = screenMin.x + scaleWorldToScreen.x * (world.x - worldMin.x - screenMinInWorld.x);
    	screen.y = screenMin.y + scaleWorldToScreen.y * (world.y - worldMin.y - screenMinInWorld.y);
 
    	return screen;
      }
 
	public Vec2 screenToWorld(float x, float y) {
		return screenToWorld(new Vec2(x,y));
	}
	public Vec2 screenToWorld(Vec2 screen) {
		Vec2 world = new Vec2();
		world.x = (screen.x - screenMin.x)*scaleScreenToWorld.x + worldMin.x + screenMinInWorld.x;
		world.y = (screen.y - screenMin.y)*scaleScreenToWorld.y + worldMin.y + screenMinInWorld.y;
		return world;
	}
 
	public float worldWidth() {
		return (worldMax.x - worldMin.x);
	}
	public float worldHeight() {
		return (worldMax.y - worldMin.y);
	}
	public float screenWidth() {
		return (screenMax.x - screenMin.x);
	}
	public float screenHeight() {
		return (screenMax.y - screenMin.y);
	}
 
	public String toString() {
		String s = "";
		s += screenMin.toString()+" -> "+screenToWorld(screenMin).toString()+"\n";
		s += screenMax.toString()+" -> "+screenToWorld(screenMax).toString();
		return s;
	}
 
	// Used if the screen dimensions change i.e. if a user adjusts the 
	// dimensions of the window
	public void reshape(float x1, float y1, float x2, float y2 ) {
		this.screenMin.set(x1, y1);
		this.screenMax.set(x2, y2);
 
		setCamera(worldMin, worldMax, screenMin, screenMax, screenMinInWorld, screenMaxInWorld);
	}
 
	// Pan by a vector 
	public void pan (Vec2 pan) {
		screenMinInWorld = screenMinInWorld.add(pan);
		screenMaxInWorld = screenMaxInWorld.add(pan);
		updated = true;
	}
 
	// Zoom by a vector 
	public void zoom (Vec2 zoom) {
		screenMinInWorld = screenMinInWorld.add(zoom);
		screenMaxInWorld = screenMaxInWorld.sub(zoom);
		updated = true;
	}
 
	// rotate by a certain angle
	public void rotate(float amount) {
		rotation += amount;
		updated = true;
	}
}