package me.sciion.gdx.utils;

import com.badlogic.gdx.math.Vector3;

public class InputUtils {

    private static Vector3 playerInput;
    public static Vector3 playerMouse(){
	if(playerInput == null){
	    playerInput = Vector3.Zero;
	}
	return playerInput;
    }
    
    public static void setPlayerMouse(float x, float z){
	if(playerInput == null){
	    playerInput = Vector3.Zero;
	}
	playerInput.x = x;
	playerInput.z = z;
    }
}
