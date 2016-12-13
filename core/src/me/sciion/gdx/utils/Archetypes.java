package me.sciion.gdx.utils;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;

import me.sciion.gdx.level.components.AutoInputComponent;
import me.sciion.gdx.level.components.CollisionComponent;
import me.sciion.gdx.level.components.ModelComponent;
import me.sciion.gdx.level.components.NetworkedInput;
import me.sciion.gdx.level.components.PlayerInputComponent;
import me.sciion.gdx.level.components.SpatialComponent;

public class Archetypes {

    public Archetype character;
    public Archetype player;
    public Archetype npc;
    public Archetype structure;
    public Archetype marker;
    public Archetype floor;
    public Archetype networked;
    
    public Archetypes(World world){
	character = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.build(world);
	player = new ArchetypeBuilder(character)
		.add(PlayerInputComponent.class)
		.add(NetworkedInput.class)
		.build(world);
	structure = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(CollisionComponent.class)
		.add(ModelComponent.class).build(world);
	floor = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.build(world);
	marker = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.build(world);
	npc= new ArchetypeBuilder(character)
		.add(AutoInputComponent.class)
		.build(world);
	networked = new ArchetypeBuilder(character)
		.add(NetworkedInput.class)
		.build(world);

    }
}
