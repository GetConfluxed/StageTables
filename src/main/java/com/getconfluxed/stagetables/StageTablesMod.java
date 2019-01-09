package com.getconfluxed.stagetables;

import java.util.HashMap;
import java.util.Map;

import com.getconfluxed.stagetables.condition.CrTStageCondition;
import com.getconfluxed.stagetables.entry.StageEntry;
import com.getconfluxed.stagetables.table.StageTable;

import crafttweaker.CraftTweakerAPI;
import net.darkhax.bookshelf.lib.LoggingHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import stanhebben.zenscript.annotations.ZenMethod;

@Mod(modid = "stagetables", name = "Stage Tables", version = "@VERSION@", dependencies = "required-after:bookshelf@[2.2.458,);", certificateFingerprint = "@FINGERPRINT@")
public class StageTablesMod {
    
    public static final LoggingHelper LOG = new LoggingHelper("Stage Tables");
    
    private static final Map<String, StageTable> stageTables = new HashMap<>();
    
    @Instance("stagetables")
    public static StageTablesMod instance;
    
    @EventHandler
    public void onPreInit (FMLPreInitializationEvent event) {

    	CraftTweakerAPI.registerGlobalSymbol("stageTables", CraftTweakerAPI.getJavaStaticFieldSymbol(StageTablesMod.class, "instance"));
        CraftTweakerAPI.registerClass(StageTable.class);
        CraftTweakerAPI.registerClass(StageEntry.class);
        CraftTweakerAPI.registerClass(CrTStageCondition.class);
    }
    
    @EventHandler
    public void onLoadComplete (FMLLoadCompleteEvent event) {
        
    	if (stageTables.isEmpty()) {

    		LOG.info("It looks like you haven't registered any stage tables yet. You can find info on how to use this mod here: ");
    	}
    	
    	else {
    		
    		LOG.info("Loaded {} stage tables.", stageTables.size());
    	}
    	
    	if (stageTables.size() > 99) {
    		
    		LOG.info("Wow! That's a lot of tables!");
    	}
    }
    
    /**
     * Creates a new table for a name. If that table already exists it will be replaced.
     *
     * @param name The name of the table to create.
     * @return The created stage table object.
     */
    @ZenMethod
    public StageTable createTable (String name) {
        
        final StageTable table = new StageTable(name);
        
        if (stageTables.containsKey(name)) {
            
            LOG.warn("The stage table {} has been created twice. It is being overidden!", name);
        }
        
        stageTables.put(name, table);
        return table;
    }
    
    
}