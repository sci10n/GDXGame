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

    public Archetype characterArchetype;
    public Archetype playerArchetype;
    public Archetype npcArchetype;
    public Archetype structureArchetype;
    public Archetype markerArchetype;
    public Archetype floorArchetype;
    public Archetype networkedEntity;
    
    public Archetypes(World world){
	characterArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.build(world);
	playerArchetype = new ArchetypeBuilder(characterArchetype)
		.add(PlayerInputComponent.class).build(world);
	structureArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(CollisionComponent.class)
		.add(ModelComponent.class).build(world);
	floorArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.build(world);
	markerArchetype = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.build(world);
	npcArchetype= new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(AutoInputComponent.class)
		.build(world);
	networkedEntity = new ArchetypeBuilder()
		.add(SpatialComponent.class)
		.add(ModelComponent.class)
		.add(CollisionComponent.class)
		.add(NetworkedInput.class)
		.build(world);
    }
}
