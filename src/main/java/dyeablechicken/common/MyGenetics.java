package dyeablechicken.common;


import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

import java.util.Arrays;

import static dyeablechicken.util.Logger.log;

public class MyGenetics {

    private Entity myself;
    public Boolean hasGenetics = false;
    private static TrackedData<String> GENETIC_TRACKER = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.STRING);
    private DataTracker myDT;

    public MyGenetics(Entity en){
        this.myself = en;
        myDT = new DataTracker(en);
        myDT.startTracking(GENETIC_TRACKER, Arrays.toString(new int[]{0,1,2}));
    }

    public String getGenetics(){
        if (myself.getEntityWorld().isClient) {
            log("CALLED getGenetics on CLIENT");
        }
        return myDT.get(GENETIC_TRACKER);
    }
    public String getClientGenetics(){
        if (myself.getEntityWorld().isClient) {
            log("CALLED getClientGenetics on CLIENT");
        }
        return myDT.get(GENETIC_TRACKER);
    }

    public void setGenetics(String genes) {
        myDT.set(GENETIC_TRACKER, genes);
        if (myself.getEntityWorld().isClient) {
            log("CALLED setGenetics on CLIENT");
        }
    }
    public int getEntityID(){
            return myself.getEntityId();
    }

}
