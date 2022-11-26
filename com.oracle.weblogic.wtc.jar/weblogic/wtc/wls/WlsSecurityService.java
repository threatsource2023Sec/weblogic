package weblogic.wtc.wls;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import com.bea.core.jatmi.intf.TCSecurityService;
import java.io.Serializable;
import java.security.AccessController;
import javax.security.auth.login.LoginException;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.wtc.jatmi.AppKey;
import weblogic.wtc.jatmi.TPException;

public final class WlsSecurityService implements TCSecurityService, Serializable {
   static final long serialVersionUID = -2563145107053158113L;
   private static final AuthenticatedSubject myKid = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SEL_LDAP = "LDAP";
   private static final String SEL_CUSTOM = "Custom";
   private ClassLoader _loader = null;
   private String _anon_username = null;
   private PrincipalAuthenticator _pa = null;

   public WlsSecurityService() {
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[ WlsSecurityService()");
         this._anon_username = WLSPrincipals.getAnonymousUsername();
         this._pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(myKid, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
         ntrace.doTrace("] WlsSecurityService/10");
      } else {
         this._anon_username = WLSPrincipals.getAnonymousUsername();
         this._pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(myKid, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
      }

   }

   static AuthenticatedSubject getKernelUser() {
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/getKernelUser()");
         ntrace.doTrace("] WlsSecurityService/getKernelUser/10/" + myKid);
      }

      return myKid;
   }

   public void shutdown(int type) {
   }

   public void pushUser(TCAuthenticatedUser sub) {
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/pushUser(" + sub + ")");
         sub.setAsCurrentUser();
         ntrace.doTrace("] WlsSecurityService/pushUser/10/");
      } else {
         sub.setAsCurrentUser();
      }

   }

   public TCAuthenticatedUser getUser() {
      AuthenticatedSubject subj;
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/getUser()");
         subj = SecurityServiceManager.getCurrentSubject(myKid);
         WlsAuthenticatedUser user = new WlsAuthenticatedUser(subj);
         ntrace.doTrace("] WlsSecurityService/getUser/10/" + user);
         return user;
      } else {
         subj = SecurityServiceManager.getCurrentSubject(myKid);
         return new WlsAuthenticatedUser(subj);
      }
   }

   public void popUser() {
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/popUser()");
         SecurityServiceManager.popSubject(myKid);
         ntrace.doTrace("] WlsSecurityService/popUser/10/");
      } else {
         SecurityServiceManager.popSubject(myKid);
      }

   }

   public String getAnonymousUserName() {
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/getAnonymousUserName()");
         ntrace.doTrace("]/WlsSecurityService/getAnonymousUserName/10/" + this._anon_username);
      }

      return this._anon_username;
   }

   public TCAuthenticatedUser impersonate(String username) throws LoginException {
      AuthenticatedSubject subj;
      if (ntrace.isTraceEnabled(4)) {
         ntrace.doTrace("[/WlsSecurityService/impersonate(" + username + ")");
         subj = this._pa.impersonateIdentity(username);
         WlsAuthenticatedUser user = new WlsAuthenticatedUser(subj);
         ntrace.doTrace("]/WlsSecurityService/impersonate/10/" + user);
         return user;
      } else {
         subj = this._pa.impersonateIdentity(username);
         return new WlsAuthenticatedUser(subj);
      }
   }

   public TCAppKey getAppKeyGenerator(String sel, String p1, String p2, boolean p3, int p4) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      TCAppKey appkey = null;
      boolean need_delegate = false;
      if (traceEnabled) {
         ntrace.doTrace("[/WlsSecurityService/getAppKeyGenerator/" + sel + ", p1 = " + p1 + ", p2 = " + p2 + ", p3 = " + p3 + ", p4 = " + p4);
      }

      if (sel != null) {
         String cname;
         String param;
         if (sel.compareToIgnoreCase("LDAP") != 0) {
            if (sel.compareToIgnoreCase("Custom") != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("unsupported AppKey Generator type: " + sel);
                  ntrace.doTrace("]/WlsSecurityService/getAppKeyGenerator/10/null");
               }

               return null;
            }

            need_delegate = true;
            cname = p1;
            param = p2;
         } else {
            cname = new String("weblogic.wtc.wls.ldapAppKey");
            StringBuffer sb;
            if (p1 != null && p1.length() != 0) {
               sb = new StringBuffer(p1);
            } else {
               sb = new StringBuffer("TUXEDO_UID");
            }

            sb.append(" ");
            if (p2 != null && p2.length() != 0) {
               sb.append(p2);
            } else {
               sb.append("TUXEDO_GID");
            }

            param = new String(sb);
         }

         try {
            Class cobj;
            if (this._loader == null) {
               cobj = this.getClass();
               this._loader = cobj.getClassLoader();
            }

            cobj = this._loader.loadClass(cname);
            Object nobj = cobj.newInstance();
            if (nobj instanceof AppKey) {
               appkey = new WlsAppKeyDelegate((AppKey)nobj);
            } else {
               appkey = (TCAppKey)nobj;
            }

            ((TCAppKey)appkey).init(param, p3, p4);
            if (!need_delegate) {
               if (traceEnabled) {
                  ntrace.doTrace("Cache enabled");
               }

               ((TCAppKey)appkey).doCache(true);
            } else {
               ((TCAppKey)appkey).doCache(false);
            }
         } catch (ClassNotFoundException var13) {
            if (traceEnabled) {
               ntrace.doTrace("]/WlsSecurityService/getAppKeyGenerator/10/null");
            }

            return null;
         } catch (TPException var14) {
            if (traceEnabled) {
               ntrace.doTrace("]/WlsSecurityService/getAppKeyGenerator/20/null");
            }

            return null;
         } catch (Exception var15) {
            if (traceEnabled) {
               ntrace.doTrace("]/WlsSecurityService/getAppKeyGenerator/30/null");
            }

            return null;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WlsSecurityService/getAppKeyGenerator/40/success");
      }

      return (TCAppKey)appkey;
   }

   public int getSecProviderId() {
      return 0;
   }

   public String getSecProviderName() {
      return "WLS Security Service for TC";
   }
}
