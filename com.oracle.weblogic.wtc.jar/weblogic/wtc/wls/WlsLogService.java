package weblogic.wtc.wls;

import com.bea.core.jatmi.intf.LogService;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.wtc.WTCLogger;

public final class WlsLogService implements LogService {
   private int level = -1;
   private DebugLogger CorbaEx = DebugLogger.getDebugLogger("DebugWTCCorbaEx");
   private DebugLogger GwtEx = DebugLogger.getDebugLogger("DebugWTCGwtEx");
   private DebugLogger JatmiEx = DebugLogger.getDebugLogger("DebugWTCJatmiEx");
   private DebugLogger tBridgeEx = DebugLogger.getDebugLogger("DebugWTCtBridgeEx");
   private DebugLogger WtcConfig = DebugLogger.getDebugLogger("DebugWTCConfig");
   private DebugLogger WtcTdomPdu = DebugLogger.getDebugLogger("DebugWTCTdomPdu");
   private DebugLogger WtcUData = DebugLogger.getDebugLogger("DebugWTCUData");
   private boolean _debug = false;

   public void setTraceLevel(int l) {
      this.level = l;
   }

   public void doTrace(String todo) {
      if (this._debug) {
         WTCLogger.logDebugMsg("WlsLogService:" + todo);
      } else {
         WTCLogger.logDebugMsg(todo);
      }

   }

   private boolean typeStatus(int type) {
      if ((type & 4) == 4) {
         if (this.JatmiEx.isDebugEnabled()) {
            return true;
         }

         if (this.level >= 55000) {
            return true;
         }
      }

      if ((type & 2) == 2) {
         if (this.GwtEx.isDebugEnabled()) {
            return true;
         }

         if (this.level >= 25000) {
            return true;
         }
      }

      if ((type & 1) == 1) {
         if (this.tBridgeEx.isDebugEnabled()) {
            return true;
         }

         if (this.level >= 15000) {
            return true;
         }
      }

      if ((type & 8) == 8) {
         if (this.CorbaEx.isDebugEnabled()) {
            return true;
         }

         if (this.level >= 65000) {
            return true;
         }
      }

      if ((type & 16) == 16 && this.WtcConfig.isDebugEnabled()) {
         return true;
      } else if ((type & 32) == 32 && this.WtcTdomPdu.isDebugEnabled()) {
         return true;
      } else {
         return (type & 64) == 64 && this.WtcUData.isDebugEnabled();
      }
   }

   public void doTrace(int type, String todo) {
      if (this.typeStatus(type)) {
         WTCLogger.logDebugMsg(todo);
      }

   }

   public void doTrace(int level, int type, String todo) {
      if (this.typeStatus(type)) {
         WTCLogger.logDebugMsg(todo);
      }

   }

   public boolean isTraceEnabled(int type) {
      switch (type) {
         case 1:
            if (this.tBridgeEx.isDebugEnabled()) {
               return true;
            }

            if (this.level >= 15000) {
               return true;
            }
            break;
         case 2:
            if (this.GwtEx.isDebugEnabled()) {
               return true;
            }

            if (this.level >= 25000) {
               return true;
            }
            break;
         case 4:
            if (this.JatmiEx.isDebugEnabled()) {
               return true;
            }

            if (this.level >= 55000) {
               return true;
            }
            break;
         case 8:
            if (this.CorbaEx.isDebugEnabled()) {
               return true;
            }

            if (this.level >= 65000) {
               return true;
            }
            break;
         case 16:
            if (this.WtcConfig.isDebugEnabled()) {
               return true;
            }
            break;
         case 32:
            if (this.WtcTdomPdu.isDebugEnabled()) {
               return true;
            }
            break;
         case 64:
            if (this.WtcUData.isDebugEnabled()) {
               return true;
            }
      }

      return false;
   }

   public boolean isMixedTraceEnabled(int type) {
      return this.typeStatus(type);
   }
}
