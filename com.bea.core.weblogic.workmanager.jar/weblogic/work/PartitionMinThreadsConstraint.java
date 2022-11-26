package weblogic.work;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.utils.Debug;
import weblogic.utils.concurrent.WaterMark;

public class PartitionMinThreadsConstraint {
   private static boolean DEBUG = Debug.getCategory("weblogic.MinThreadsConstraint").isEnabled();
   private WaterMark mark;
   private String partitionName;
   private List minThreadsConstraintList = new CopyOnWriteArrayList();
   private AtomicInteger lastGetNextIndex = new AtomicInteger(0);
   private AtomicInteger sumMinConstraints = new AtomicInteger(0);

   PartitionMinThreadsConstraint(String partitionName, int limit) {
      this.partitionName = partitionName;
      if (limit < 0) {
         limit = 0;
      }

      this.mark = new WaterMark(limit);
      if (DEBUG) {
         log("created '" + partitionName + "' with limit " + limit);
      }

   }

   void add(MinThreadsConstraint minThreadsConstraint) {
      this.minThreadsConstraintList.add(minThreadsConstraint);
      int minConstraintCount = minThreadsConstraint.getCount();
      if (minConstraintCount > 0) {
         this.doUpdateSumMinConstraints(minConstraintCount);
      }

   }

   void remove(MinThreadsConstraint minThreadsConstraint) {
      this.minThreadsConstraintList.remove(minThreadsConstraint);
      int minConstraintCount = minThreadsConstraint.getCount();
      if (minConstraintCount > 0) {
         this.doUpdateSumMinConstraints(-minConstraintCount);
      }

   }

   void updateSumMinConstraints(int oldCount, int newCount) {
      this.doUpdateSumMinConstraints(newCount - oldCount);
   }

   private void doUpdateSumMinConstraints(int delta) {
      int oldValue = this.sumMinConstraints.getAndAdd(delta);
      int count = this.getCount();
      if (this.mark.isEnabled() && delta > 0 && count >= oldValue && delta > count - oldValue) {
         WorkManagerLogger.logPartitionMinThreadsConstraintCapInfo(this.partitionName, count);
      }

   }

   WorkAdapter getNext() {
      WorkAdapter nextWork = null;
      if (this.mark.isEnabled() && !this.minThreadsConstraintList.isEmpty() && !this.overSubscribed()) {
         int size = this.minThreadsConstraintList.size();
         if (size == 1) {
            return ((MinThreadsConstraint)this.minThreadsConstraintList.get(0)).getNextInternal();
         }

         int endPos = this.lastGetNextIndex.getAndIncrement() % size;
         if (endPos < 0) {
            endPos = 0;
            this.lastGetNextIndex.set(0);
         }

         int index = endPos;

         do {
            ++index;
            index %= size;
            nextWork = ((MinThreadsConstraint)this.minThreadsConstraintList.get(index)).getNextInternal();
         } while(nextWork == null && index != endPos);
      }

      return nextWork;
   }

   boolean overSubscribed() {
      return this.mark.getCurrentLevel() < 0;
   }

   boolean limitReached() {
      return this.mark.isEnabled() && this.mark.getCurrentLevel() <= 0;
   }

   int getSumMinConstraints() {
      return this.sumMinConstraints.get();
   }

   public int getCount() {
      return this.mark.getOriginalLevel();
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public synchronized void setCount(int newCount) {
      if (newCount < 0) {
         newCount = 0;
      }

      int oldCount = this.getCount();
      this.mark.resetLevel(newCount);
      int minConstraintsSum = this.sumMinConstraints.get();
      if (this.mark.isEnabled() && oldCount >= minConstraintsSum && newCount < minConstraintsSum) {
         WorkManagerLogger.logPartitionMinThreadsConstraintCapInfo(this.partitionName, newCount);
      }

   }

   public int getExecutingCount() {
      return this.mark.diff();
   }

   boolean tryAcquire() {
      if (DEBUG) {
         int beforeExec = this.mark.getCurrentLevel();
         boolean b = this.mark.tryDecreaseByOne();
         int afterExec = this.mark.getCurrentLevel();
         if (b) {
            log("tryAcquire: " + this.partitionName + " before=" + beforeExec + " ,after=" + afterExec + " ,result=" + b);
         } else if (!this.mark.isEnabled()) {
            this.mark.decreaseLevel(1);
            afterExec = this.mark.getCurrentLevel();
            log("tryAcquire: " + this.partitionName + " before=" + beforeExec + " ,after=" + afterExec + " ,result=true because constraint is disabled");
            return true;
         }

         return b;
      } else {
         boolean result = this.mark.tryDecreaseByOne();
         if (!result && !this.mark.isEnabled()) {
            this.mark.decreaseLevel(1);
            return true;
         } else {
            return result;
         }
      }
   }

   void acquire() {
      if (DEBUG) {
         int beforeExec = this.mark.getCurrentLevel();
         this.mark.decreaseLevel(1);
         int afterExec = this.mark.getCurrentLevel();
         log("acquire: " + this.partitionName + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.decreaseLevel(1);
      }
   }

   void release() {
      if (DEBUG) {
         int beforeExec = this.mark.getCurrentLevel();
         this.mark.increaseLevel(1);
         int afterExec = this.mark.getCurrentLevel();
         log("release: " + this.partitionName + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.increaseLevel(1);
      }
   }

   boolean isEnabled() {
      return this.mark.isEnabled();
   }

   List getMinThreadsConstraintList() {
      return this.minThreadsConstraintList;
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<PartitionMinTC>" + str);
      }

   }
}
