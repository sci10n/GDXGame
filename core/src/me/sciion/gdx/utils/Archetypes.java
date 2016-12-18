package me.sciion.gdx.utils;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;

import me.sciion.gdx.level.components.AutoInput;
import me.sciion.gdx.level.components.Model;
import me.sciion.gdx.level.components.Physics;
import me.sciion.gdx.level.components.PlayerInput;
import me.sciion.gdx.level.components.Spatial;
import me.sciion.gdx.level.components.Velocity;


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
		.add(Spatial.class)
		.add(Velocity.class)
		.add(Model.class)
		.add(Physics.class)
		.build(world);
	player = new ArchetypeBuilder(character)
		.add(PlayerInput.class)
		.build(world);
	structure = new ArchetypeBuilder()
		.add(Spatial.class)
		.add(Physics.class)
		.add(Model.class).build(world);
	floor = new ArchetypeBuilder()
		.add(Spatial.class)
		.add(Model.class)
		.build(world);
	marker = new ArchetypeBuilder()
		.add(Spatial.class)
		.build(world);
	npc= new ArchetypeBuilder(character)
		.add(AutoInput.class)
		.build(world);

    }
}
