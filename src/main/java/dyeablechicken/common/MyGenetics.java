package dyeablechicken.common;


public class MyGenetics {

    Object myself;
    public Boolean hasGenetics;
    protected int[] genetics; //new int[]{}

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
