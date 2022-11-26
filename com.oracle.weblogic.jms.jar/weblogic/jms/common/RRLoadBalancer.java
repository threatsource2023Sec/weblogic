package weblogic.jms.common;

public final class RRLoadBalancer implements LoadBalancer {
   private DistributedDestinationImpl[] dests = null;
   private int current;
   private int pass;
   private int min;
   private int max;
   private int size;
   private int count;

   public RRLoadBalancer() {
   }

   public RRLoadBalancer(DistributedDestinationImpl[] dests) {
      if (dests != null && dests.length != 0 && dests[0] != null) {
         this.refresh(dests);
         this.current = 0;
         this.pass = 0;
         this.count = -1;
      } else {
         this.dests = null;
      }
   }

   public void refresh(DistributedDestinationImpl[] dests) {
      if (dests != null && dests.length != 0 && dests[0] != null) {
         this.min = dests[0].getWeight();
         this.max = dests[0].getWeight();

         int i;
         for(i = 1; i < dests.length && dests[i] != null; ++i) {
            int weight = dests[i].getWeight();
            if (weight < this.min) {
               this.min = weight;
            } else if (weight > this.max) {
               this.max = weight;
            }
         }

         if (this.dests != null && this.size > i) {
            while(this.current < i && this.current >= 0 && this.dests[this.current] != dests[this.current]) {
               --this.current;
            }
         }

         this.size = i;
         this.dests = dests;
      } else {
         this.dests = null;
      }
   }

   public DistributedDestinationImpl getNext(DDTxLoadBalancingOptimizer ddTxLBOptimizer) {
      if (this.dests == null) {
         return null;
      } else {
         if (++this.current >= this.size) {
            this.current = 0;
         }

         if (++this.count >= this.size) {
            this.count = 0;
            if (++this.pass >= this.max) {
               this.pass = 0;
            }
         }

         while(this.pass >= this.min && this.dests[this.current].getWeight() <= this.pass) {
            if (++this.current >= this.size) {
               this.current = 0;
            }

            if (++this.count >= this.size) {
               this.count = 0;
               if (++this.pass >= this.max) {
                  this.pass = 0;
               }
            }
         }

         int finalPick = this.current;
         if (ddTxLBOptimizer != null && !this.dests[finalPick].isLocal() && !ddTxLBOptimizer.visited(this.dests[finalPick])) {
            int i;
            for(i = 0; i < this.size && !ddTxLBOptimizer.visited(this.dests[finalPick]); ++i) {
               ++finalPick;
               if (finalPick >= this.size) {
                  finalPick = 0;
               }
            }

            if (i >= this.size) {
               ddTxLBOptimizer.addVisitedDispatcher(this.dests[finalPick]);
            }
         }

         return this.dests[finalPick];
      }
   }

   public DistributedDestinationImpl getNext(int index) {
      return this.dests[index];
   }

   public int getSize() {
      return this.dests == null ? 0 : this.dests.length;
   }
}
