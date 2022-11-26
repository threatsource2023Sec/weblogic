package weblogic.ejb.container.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.utils.collections.StackPool;

public final class ShrinkablePool extends StackPool implements weblogic.utils.collections.Pool {
   private static final DebugLogger debugLogger;
   private int watermark;
   private int initialObjectsInPool;

   public ShrinkablePool(int maxsize, int minsize) {
      super(maxsize);
      this.initialObjectsInPool = minsize;
      this.watermark = this.initialObjectsInPool;
   }

   public synchronized Object remove() {
      if (this.getPointer() > 0) {
         Object o = this.decrementPointerAndGetValue();
         int pointerVal = this.getPointer();
         this.setValueAt(pointerVal, (Object)null);
         if (pointerVal < this.watermark) {
            this.watermark = pointerVal;
         }

         return o;
      } else {
         return null;
      }
   }

   synchronized Collection trim(boolean idleTimeout) {
      if (debugLogger.isDebugEnabled()) {
         debug("trimAndResetMark entered.  initialObjectsInPool = " + this.initialObjectsInPool + ", pointer = " + this.getPointer() + ", watermark = " + this.watermark);
      }

      if (this.getPointer() <= this.initialObjectsInPool) {
         return null;
      } else {
         int currPointer = this.getPointer();
         int newPointer = this.initialObjectsInPool;
         if (idleTimeout) {
            newPointer = currPointer - this.watermark;
         }

         if (newPointer < this.initialObjectsInPool) {
            newPointer = this.initialObjectsInPool;
         }

         List l = new ArrayList();

         for(int i = newPointer; i < currPointer; ++i) {
            l.add(this.getValueAt(i));
            this.setValueAt(i, (Object)null);
         }

         this.setPointer(newPointer);
         this.watermark = newPointer;
         if (debugLogger.isDebugEnabled()) {
            debug("trimAndResetMark exiting. new pointer = " + newPointer);
         }

         return l;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[FencedPool] " + s);
   }

   static {
      debugLogger = EJBDebugService.poolingLogger;
   }
}
