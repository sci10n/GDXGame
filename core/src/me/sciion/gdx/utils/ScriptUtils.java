package me.sciion.gdx.utils;

import org.python.util.PythonInterpreter;

public class ScriptUtils {

    
    private static PythonInterpreter interpriter;
    
    public static void process(String filename){
	if(interpriter == null)
	    interpriter = new PythonInterpreter();
	interpriter.execfile("scripts/collision_trigger.py");
    }
}
