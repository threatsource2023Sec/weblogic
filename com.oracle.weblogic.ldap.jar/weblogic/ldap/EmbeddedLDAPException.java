package weblogic.ldap;

import org.jvnet.hk2.annotations.Service;
import weblogic.security.utils.EmbeddedLDAPGeneralService;
import weblogic.utils.NestedRuntimeException;

class EmbeddedLDAPException extends NestedRuntimeException {
   public EmbeddedLDAPException() {
   }

   public EmbeddedLDAPException(String msg) {
      super(msg);
   }

   public EmbeddedLDAPException(Throwable nested) {
      super(nested);
   }

   public EmbeddedLDAPException(String msg, Throwable nested) {
      super(msg, nested);
   }

   @Service
   private static class EmbeddedLDAPGeneralServiceImpl implements EmbeddedLDAPGeneralService {
      public String getEmbeddedLDAPHost() {
         return EmbeddedLDAP.getEmbeddedLDAPHost();
      }

      public int getEmbeddedLDAPPort() {
         return EmbeddedLDAP.getEmbeddedLDAPPort();
      }

      public boolean getEmbeddedLDAPUseSSL() {
         return EmbeddedLDAP.getEmbeddedLDAPUseSSL();
      }
   }
}
