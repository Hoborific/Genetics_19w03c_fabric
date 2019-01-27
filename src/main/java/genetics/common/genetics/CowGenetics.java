package genetics.common.genetics;

import net.minecraft.entity.Entity;

import java.util.Arrays;
import java.util.Random;

import static genetics.util.Logger.log;

public class CowGenetics extends BaseGenetics {
    public CowGenetics(Entity en) {
        super(en);
    }

    @Override
    public void initializeGenetics() {
        if (!this.getWorld().isClient) {
            log("cow override called");
            Random randy = new Random();
            int[] newGenetics = new int[genomeSize];

            for (int i = 0; i < genomeSize; i++) {
                if (i == 3) { // third index is hide color right now
                    int temp = randy.nextInt(3) + randy.nextInt(3) + randy.nextInt(4); // weighted towards less than 5 instead of random 0-9
                    System.out.println(temp);
                    if (temp <= 1) {
                        temp = randy.nextInt(1); // 50% chance of zero (black)
                    }
                    newGenetics[i] = temp;
                } else if (i == 7) {
                    int temp = 2 + randy.nextInt(8);
                    newGenetics[i] = temp;

                } else {
                    newGenetics[i] = randy.nextInt(10);
                }

            }
            this.setGenetics(newGenetics);
            this.hasGenetics = true;
            log("Initialized Cow Genetics: " + Arrays.toString(this.getGenetics()));
        }
    }


}
