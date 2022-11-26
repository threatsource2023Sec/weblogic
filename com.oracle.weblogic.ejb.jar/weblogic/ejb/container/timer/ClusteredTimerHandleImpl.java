package weblogic.ejb.container.timer;

import javax.ejb.EJBException;
import javax.ejb.TimerHandle;
import weblogic.scheduler.NoSuchObjectLocalException;
import weblogic.scheduler.Timer;
import weblogic.scheduler.TimerException;

public final class ClusteredTimerHandleImpl implements TimerHandle {
   private static final long serialVersionUID = -6940054380113951006L;
   private weblogic.scheduler.TimerHandle handle;

   public ClusteredTimerHandleImpl(Timer timer) {
      try {
         this.handle = timer.getHandle();
      } catch (NoSuchObjectLocalException var4) {
         throw new javax.ejb.NoSuchObjectLocalException("Error getting TimerHandle: " + var4, var4);
      } catch (TimerException var5) {
         EJBException ee = new EJBException("Error getting TimerHandle: " + var5, var5);
         ee.initCause(var5);
         throw ee;
      }
   }

   public javax.ejb.Timer getTimer() {
      try {
         Timer timer = this.handle.getTimer();
         ClusteredTimerImpl ejbTimer = (ClusteredTimerImpl)timer.getListener();
         ejbTimer.setTimer(timer);
         return new TimerWrapper(ejbTimer);
      } catch (NoSuchObjectLocalException var3) {
         throw new javax.ejb.NoSuchObjectLocalException("Error getting Timer: " + var3, var3);
      } catch (TimerException var4) {
         EJBException ee = new EJBException("Error getting Timer: " + var4, var4);
         ee.initCause(var4);
         throw ee;
      }
   }

   public int hashCode() {
      return this.handle.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ClusteredTimerHandleImpl)) {
         return false;
      } else {
         ClusteredTimerHandleImpl other = (ClusteredTimerHandleImpl)obj;
         return this.handle.equals(other.handle);
      }
   }
}
