package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import com.bea.core.jatmi.intf.TCSecurityService;
import javax.security.auth.login.LoginException;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.jatmi.TPException;

public final class TCSecurityManager {
   private static TCSecurityManager _tc_sec = null;
   private static TCSecurityService _tc_sec_svc = null;
   private static String _dflt_anon = "anonymous";
   private static String _glb_tpusr = null;
   private static String SEL_TPUSRFILE = "TpUsrFile";
   public static final int TC_SEC_SHUTDOWN_NORMAL = 0;
   public static final int TC_SEC_SHUTDOWN_FORCE = 1;
   public static final int TC_SEC_PROVIDER_WLS = 0;
   public static final int TC_SEC_PROVIDER_CE = 0;

   public static void initialize(TCSecurityService tcss) throws TPException {
      if (tcss == null) {
         throw new TPException(12, "null security service");
      } else if (_tc_sec != null) {
         throw new TPException(12, "Security Manager already configured!");
      } else {
         _tc_sec_svc = tcss;
         _tc_sec = new TCSecurityManager();
         ntrace.doTrace("INFO: TC security service instantiated!");
      }
   }

   public static TCSecurityManager getSecurityService() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCSecurityManager/getSecurityService()");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCSecurityManager/getSecurityService/" + _tc_sec);
      }

      return _tc_sec;
   }

   public void shutdown(int type) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCSecurityManager/shutdown(" + type + ")");
      }

      if (_tc_sec != null) {
         if (type < 0 || type > 1) {
            type = 1;
         }

         _tc_sec.shutdown(type);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCSecurityManager/shutdown/10");
      }

   }

   public static void setAsCurrentUser(TCAuthenticatedUser user) {
      if (_tc_sec_svc != null) {
         _tc_sec_svc.pushUser(user);
      }

   }

   public static TCAuthenticatedUser getCurrentUser() {
      return _tc_sec_svc != null ? _tc_sec_svc.getUser() : null;
   }

   public static void removeCurrentUser() {
      if (_tc_sec_svc != null) {
         _tc_sec_svc.popUser();
      }

   }

   public static String getAnonymousUserName() {
      return _tc_sec_svc != null ? _tc_sec_svc.getAnonymousUserName() : _dflt_anon;
   }

   public static TCAuthenticatedUser impersonate(String user) throws LoginException {
      return _tc_sec_svc != null ? _tc_sec_svc.impersonate(user) : null;
   }

   public static void setGlobalTpUsrFile(String fname) {
      _glb_tpusr = fname;
   }

   public TCAppKey getAppKeyGenerator(String sel, String p1, String p2, boolean p3, int p4) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      boolean cached = false;
      if (traceEnabled) {
         ntrace.doTrace("[TCSecurityManager/getAppKeyGenerator/sel =" + sel + ", p1 = " + p1 + ", p2 = , p3 = " + p3 + ", p4 = " + p4);
      }

      TCAppKey appkey;
      if (sel != null && sel.compareToIgnoreCase(SEL_TPUSRFILE) != 0) {
         if ((appkey = _tc_sec_svc.getAppKeyGenerator(sel, p1, p2, p3, p4)) == null) {
            if (traceEnabled) {
               ntrace.doTrace("]TCSecurityManager/getAppKeyGenerator/40/return null.");
            }

            return null;
         }
      } else {
         String cname = new String("weblogic.wtc.jatmi.tpusrAppKey");
         String param = p1;
         if (p1 == null && (param = _glb_tpusr) != null) {
            cached = true;
            if (traceEnabled) {
               ntrace.doTrace("User Record cache enabled");
            }
         }

         ClassLoader loader = Thread.currentThread().getContextClassLoader();

         try {
            Class cobj = loader.loadClass(cname);
            appkey = (TCAppKey)cobj.newInstance();
            appkey.init(param, p3, p4);
            appkey.doCache(cached);
         } catch (ClassNotFoundException var13) {
            WTCLogger.logErrorCustomAppKeyClassNotFound(cname);
            if (traceEnabled) {
               ntrace.doTrace("]/TCSecurityManager/getAppKeyGenerator/10/null");
            }

            return null;
         } catch (TPException var14) {
            WTCLogger.logErrorAppKeyInitFailure(var14.toString());
            if (traceEnabled) {
               ntrace.doTrace("]/TCSecurityManager/getAppKeyGenerator/20/null");
            }

            return null;
         } catch (Exception var15) {
            WTCLogger.logErrorCreateAppKeyClassInstanceFailure(var15.toString());
            if (traceEnabled) {
               ntrace.doTrace("]/TCSecurityManager/getAppKeyGenerator/30/null");
            }

            return null;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]TCSecurityManager/getAppKeyGenerator/50/return" + appkey);
      }

      return appkey;
   }

   public static int getSecProviderId() {
      return _tc_sec_svc.getSecProviderId();
   }

   public static String getSecProviderName() {
      return _tc_sec_svc.getSecProviderName();
   }
}
