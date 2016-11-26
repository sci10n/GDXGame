package me.sciion.gdx.level;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LevelLoader {

    private TmxMapLoader loader;
    private TiledMap levelMap;
    public LevelLoader(){
	
	levelMap = new TiledMap();
    }
    
    public Level load(String path){
	loader = new TmxMapLoader(new InternalFileHandleResolver());
	levelMap = loader.load(path);
	Level level = new Level();
	level.load(levelMap);
	return level;
    }
    
    
}
