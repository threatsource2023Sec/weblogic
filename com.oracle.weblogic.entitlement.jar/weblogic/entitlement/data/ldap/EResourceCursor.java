package weblogic.entitlement.data.ldap;

import java.util.Properties;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPSearchResults;
import weblogic.entitlement.data.EResource;
import weblogic.entitlement.data.EnCursorResourceFilter;
import weblogic.entitlement.data.EnResourceCursor;
import weblogic.security.shared.LoggerWrapper;

class EResourceCursor extends ECursorBase implements EnResourceCursor {
   private EnCursorResourceFilter filter = null;

   public EResourceCursor(LDAPConnection conn, LDAPSearchResults results, int maximumToReturn, EData data, LoggerWrapper traceLogger) {
      super("ResCur", conn, results, maximumToReturn, data, traceLogger);
   }

   public EResourceCursor(EnCursorResourceFilter filter, LDAPConnection conn, LDAPSearchResults results, int maximumToReturn, EData data, LoggerWrapper traceLogger) {
      super("ResCurFil", conn, results, maximumToReturn, data, traceLogger);
      this.filter = filter;
      this.getCurrentResource();
   }

   public Properties getCurrentProperties() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("getCurrentProperties EResource");
      }

      EResource resource = this.data.getResourceFromEntry(this.getCurrentEntry());
      if (resource == null) {
         return null;
      } else {
         Properties props = new Properties();
         props.setProperty("Expression", this.getEntitlement(resource));
         props.setProperty("ResourceId", resource.getName());
         if (resource.isDeployData()) {
            props.setProperty("SourceData", "Deployment");
         }

         String cname = resource.getCollectionName();
         if (cname != null) {
            props.setProperty("CollectionName", cname);
         }

         return props;
      }
   }

   public EResource getCurrentResource() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("getCurrentResource");
      }

      EResource resource = this.data.getResourceFromEntry(this.getCurrentEntry());
      if (this.filter != null && resource != null) {
         if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
            this.traceLogger.debug("getCurrentResource filter");
         }

         for(boolean found = this.filter.isValidResource(resource); !found; found = this.filter.isValidResource(resource)) {
            this.advance(false);
            resource = this.data.getResourceFromEntry(this.getCurrentEntry());
            if (resource == null) {
               break;
            }
         }
      }

      return resource;
   }

   public EResource next() {
      if (this.traceLogger != null && this.traceLogger.isDebugEnabled()) {
         this.traceLogger.debug("next EResource");
      }

      EResource resource = this.getCurrentResource();
      this.advance();
      return resource;
   }

   public void advance() {
      super.advance();
      if (this.filter != null) {
         this.getCurrentResource();
      }

   }
}
