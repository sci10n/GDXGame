package me.sciion.gdx.level;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.sciion.gdx.utils.KryoStasis;

public class LevelLoader {

    private TmxMapLoader loader;
    private TiledMap levelMap;
    public LevelLoader(){
	
	levelMap = new TiledMap();
    }
    
    public Level load(String path, KryoStasis networking){
	loader = new TmxMapLoader(new InternalFileHandleResolver());
	levelMap = loader.load(path);
	Level level = new Level(networking);
	level.load(levelMap);
	return level;
    }
    
    
}
