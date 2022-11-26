package kodo.profile;

import java.io.Serializable;

public class FetchStats implements Serializable {
   private int instantiated = 0;
   private int initLoaded = 0;
   private int accessed = 0;
   private int faulted = 0;

   public synchronized void initialLoad(boolean loaded, boolean dfg) {
      ++this.instantiated;
      if (loaded) {
         ++this.initLoaded;
      }

   }

   public synchronized void isLoaded(boolean fault) {
      ++this.accessed;
      if (fault) {
         ++this.faulted;
      }

   }

   public int getInstantiated() {
      return this.instantiated;
   }

   public int getInitLoaded() {
      return this.initLoaded;
   }

   public int getAccessed() {
      return this.accessed;
   }

   public int getFaulted() {
      return this.faulted;
   }
}
