package dyeablechicken.common;


import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class MyGenetics {

    Object myself;
    public Boolean hasGenetics;
    protected int[] genetics; //new int[]{}
    public static TrackedData<String> GENETIC_TRACKER;
    public boolean registeredTracker = false;
    static{GENETIC_TRACKER = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.STRING);}


    public MyGenetics(Object en){
        this.myself = en;

    }

    public int[] getGenetics(){
        return genetics;
    }
    public void setGenetics(int[] genes){
        this.genetics = genes;
    }
}
