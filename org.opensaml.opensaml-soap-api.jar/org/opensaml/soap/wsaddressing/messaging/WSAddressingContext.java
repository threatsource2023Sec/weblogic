package org.opensaml.soap.wsaddressing.messaging;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;

public class WSAddressingContext extends BaseContext {
   private String actionURI;
   private String faultActionURI;
   private String messageIDURI;
   private String relatesToURI;
   private String relatesToRelationshipType;

   public String getActionURI() {
      return this.actionURI;
   }

   public void setActionURI(String uri) {
      this.actionURI = StringSupport.trimOrNull(uri);
   }

   public String getFaultActionURI() {
      return this.faultActionURI;
   }

   public void setFaultActionURI(String uri) {
      this.faultActionURI = StringSupport.trimOrNull(uri);
   }

   public String getMessageIDURI() {
      return this.messageIDURI;
   }

   public void setMessageIDURI(String uri) {
      this.messageIDURI = StringSupport.trimOrNull(uri);
   }

   public String getRelatesToURI() {
      return this.relatesToURI;
   }

   public void setRelatesToURI(String uri) {
      this.relatesToURI = StringSupport.trimOrNull(uri);
   }

   public String getRelatesToRelationshipType() {
      return this.relatesToRelationshipType;
   }

   public void setRelatesToRelationshipType(String value) {
      this.relatesToRelationshipType = StringSupport.trimOrNull(value);
   }
}
