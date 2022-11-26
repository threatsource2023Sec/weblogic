package weblogic.entitlement.data.ldap;

import java.util.Properties;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPSearchResults;
import weblogic.entitlement.data.ERole;
import weblogic.entitlement.data.EnCursorRoleFilter;
import weblogic.entitlement.data.EnRoleCursor;
import weblogic.entitlement.expression.EAuxiliary;
import weblogic.security.shared.LoggerWrapper;

class ERoleCursor extends ECursorBase implements EnRoleCursor {
   private EnCursorRoleFilter filter = null;

   public ERoleCursor(LDAPConnection conn, LDAPSearchResults results, int maximumToReturn, EData data, LoggerWrapper traceLogger) {
      super("RolCur", conn, results, maximumToReturn, data, traceLogger);
   }

   public ERoleCursor(EnCursorRoleFilter filter, LDAPConnection conn, LDAPSearchResults results, int maximumToReturn, EData data, LoggerWrapper traceLogger) {
      super("RolCurFil", conn, results, maximumToReturn, data, traceLogger);
      this.filter = filter;
      this.getCurrentRole();
   }

   public Properties getCurrentProperties() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("getCurrentProperties ERole");
      }

      ERole role = this.data.getRoleFromEntry(this.getCurrentEntry());
      if (role == null) {
         return null;
      } else {
         Properties props = new Properties();
         props.setProperty("RoleName", role.getName());
         props.setProperty("Expression", this.getEntitlement(role));
         String resId = role.getResourceName();
         if ("" != resId) {
            props.setProperty("ResourceId", resId);
         }

         if (role.isDeployData()) {
            props.setProperty("SourceData", "Deployment");
         }

         EAuxiliary eaux = role.getAuxiliary();
         if (eaux != null) {
            props.setProperty("AuxiliaryData", eaux.toString());
         }

         String cname = role.getCollectionName();
         if (cname != null) {
            props.setProperty("CollectionName", cname);
         }

         return props;
      }
   }

   public ERole getCurrentRole() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("getCurrentRole");
      }

      ERole role = this.data.getRoleFromEntry(this.getCurrentEntry());
      if (this.filter != null && role != null) {
         if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
            this.traceLogger.debug("getCurrentRole filter");
         }

         for(boolean found = this.filter.isValidRole(role); !found; found = this.filter.isValidRole(role)) {
            this.advance(false);
            role = this.data.getRoleFromEntry(this.getCurrentEntry());
            if (role == null) {
               break;
            }
         }
      }

      return role;
   }

   public ERole next() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("next ERole");
      }

      ERole role = this.getCurrentRole();
      this.advance();
      return role;
   }

   public void advance() {
      super.advance();
      if (this.filter != null) {
         this.getCurrentRole();
      }

   }
}
