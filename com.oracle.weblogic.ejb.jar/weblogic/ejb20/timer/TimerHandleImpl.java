package weblogic.ejb20.timer;

import java.io.Serializable;
import javax.ejb.EJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.TimerHelper;
import weblogic.ejb.container.interfaces.TimerIntf;
import weblogic.logging.Loggable;

public final class TimerHandleImpl implements TimerHandle, Serializable {
   private static final long serialVersionUID = 3158846282971285273L;
   private transient TimerIntf timer;
   private Long timerID;
   private String appName;
   private String jarName;
   private String ejbName;

   public TimerHandleImpl(TimerIntf timer, Long timerID, String appName, String jarName, String ejbName) {
      this.timer = timer;
      this.timerID = timerID;
      this.appName = appName;
      this.jarName = jarName;
      this.ejbName = ejbName;
   }

   public Timer getTimer() {
      if (this.timer != null) {
         if (!this.timer.exists()) {
            Loggable l = EJBLogger.logExpiredTimerHandleLoggable();
            throw new NoSuchObjectLocalException(l.getMessage());
         } else {
            return this.timer;
         }
      } else {
         ApplicationContextInternal ac = ApplicationAccess.getApplicationAccess().getApplicationContext(this.appName);
         ModuleRegistry mr = ac.getModuleContext(this.jarName).getRegistry();
         TimerHelper th = (TimerHelper)mr.get(this.ejbName + "#" + TimerHelper.class.getName());
         Loggable l;
         if (th == null) {
            l = EJBLogger.logTimerHandleInvokedOutsideOriginalAppContextLoggable();
            throw new EJBException(l.getMessage());
         } else {
            this.timer = (TimerIntf)th.getTimer(this.timerID);
            if (this.timer != null) {
               return this.timer;
            } else {
               l = EJBLogger.logExpiredTimerHandleLoggable();
               throw new NoSuchObjectLocalException(l.getMessage());
            }
         }
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.ejbName == null ? 0 : this.ejbName.hashCode());
      result = 31 * result + (this.jarName == null ? 0 : this.jarName.hashCode());
      result = 31 * result + (this.timerID == null ? 0 : this.timerID.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof TimerHandleImpl)) {
         return false;
      } else {
         TimerHandleImpl other = (TimerHandleImpl)obj;
         if (!this.ejbName.equals(other.ejbName)) {
            return false;
         } else if (!this.jarName.equals(other.jarName)) {
            return false;
         } else {
            return this.timerID.equals(other.timerID);
         }
      }
   }

   public String toString() {
      return "[TimerHandle] timerID: " + this.timerID + " jarName: " + this.jarName + " ejbName: " + this.ejbName;
   }
}
