package weblogic.jms.common;

import java.util.Random;

public final class RandomLoadBalancer implements LoadBalancer {
   private DistributedDestinationImpl[] dests = null;
   private int size;
   private int unit;
   private int[] upper;
   private int[] lower;
   private int[] rate;
   private Random random;

   public RandomLoadBalancer() {
   }

   public RandomLoadBalancer(DistributedDestinationImpl[] dests) {
      this.refresh(dests);
   }

   public void refresh(DistributedDestinationImpl[] dests) {
      if (dests != null && dests.length != 0 && dests[0] != null) {
         this.dests = dests;
         this.random = new Random();

         int i;
         for(i = 0; i < dests.length && dests[i] != null; ++i) {
         }

         this.size = i;
         int[] weight = new int[this.size];
         this.upper = new int[this.size];
         this.lower = new int[this.size];
         this.rate = new int[this.size];
         this.unit = 0;

         for(i = 0; i < this.size; ++i) {
            weight[i] = dests[i].getWeight();
            this.unit += weight[i];
            weight[i] *= this.size;
         }

         for(i = 0; i < this.size; ++i) {
            int min = Integer.MAX_VALUE;
            int minIdx = 0;
            int max = 0;
            int maxIdx = 0;

            for(int j = 0; j < this.size; ++j) {
               if (weight[j] > 0 && weight[j] < min) {
                  min = weight[j];
                  minIdx = j;
               }

               if (weight[j] > max) {
                  max = weight[j];
                  maxIdx = j;
               }
            }

            this.upper[i] = minIdx;
            this.lower[i] = maxIdx;
            if (min >= this.unit) {
               this.rate[i] = this.unit;
            } else {
               this.rate[i] = min;
            }

            weight[minIdx] = 0;
            weight[maxIdx] -= this.unit - min;
         }

      } else {
         this.dests = null;
      }
   }

   public DistributedDestinationImpl getNext(DDTxLoadBalancingOptimizer ddTxLBOptimizer) {
      if (this.dests == null) {
         return null;
      } else {
         int slot = this.random.nextInt(this.size);
         int cell = this.random.nextInt(this.unit);
         DistributedDestinationImpl ret;
         if (cell < this.rate[slot]) {
            ret = this.dests[this.upper[slot]];
         } else {
            ret = this.dests[this.lower[slot]];
         }

         if (ddTxLBOptimizer != null && !ret.isLocal() && !ddTxLBOptimizer.visited(ret)) {
            int i;
            for(i = 0; i < this.size && !ddTxLBOptimizer.visited(this.dests[slot]); ++i) {
               ++slot;
               if (slot >= this.size) {
                  slot = 0;
               }
            }

            if (i < this.size) {
               ret = this.dests[slot];
            } else {
               ddTxLBOptimizer.addVisitedDispatcher(ret);
            }
         }

         return ret;
      }
   }

   public DistributedDestinationImpl getNext(int index) {
      return this.dests[index];
   }

   public int getSize() {
      return this.dests == null ? 0 : this.dests.length;
   }
}
