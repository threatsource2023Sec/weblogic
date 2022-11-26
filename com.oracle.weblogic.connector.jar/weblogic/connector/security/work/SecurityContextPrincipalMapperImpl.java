package weblogic.connector.security.work;

import java.util.HashMap;
import java.util.Map;

public class SecurityContextPrincipalMapperImpl implements weblogic.security.container.jca.jaspic.ConnectorCallbackHandler.EISPrincipalMapper {
   String defaultCallerPrincipalMapped;
   Map inboundCallerPrincipalMapping;
   String defaultGroupMappedPrincipal;
   Map inboundGroupPrincipalMapping;

   public void setDefaultCallerPrincipalMapped(String defaultCallerPrincipalMapped) {
      this.defaultCallerPrincipalMapped = defaultCallerPrincipalMapped;
   }

   public void setDefaultGroupMappedPrincipal(String defaultGroupMappedPrincipal) {
      this.defaultGroupMappedPrincipal = defaultGroupMappedPrincipal;
   }

   public String getDefaultCallerPrincipalMapped() {
      return this.defaultCallerPrincipalMapped;
   }

   public Map getInboundCallerPrincipalMapping() {
      return this.inboundCallerPrincipalMapping;
   }

   public String getDefaultGroupMappedPrincipal() {
      return this.defaultGroupMappedPrincipal;
   }

   public Map getInboundGroupPrincipalMapping() {
      return this.inboundGroupPrincipalMapping;
   }

   public SecurityContextPrincipalMapperImpl(String defaultCallerPrincipalMapped, Map inboundCallerPrincipalMapping, String defaultGroupMappedPrincipal, Map inboundGroupPrincipalMapping) {
      this.defaultCallerPrincipalMapped = defaultCallerPrincipalMapped;
      this.inboundCallerPrincipalMapping = new HashMap(inboundCallerPrincipalMapping);
      this.defaultGroupMappedPrincipal = defaultGroupMappedPrincipal;
      this.inboundGroupPrincipalMapping = new HashMap(inboundGroupPrincipalMapping);
   }

   public String mapCallerPrincipal(String eisCaller) {
      if (null != eisCaller && !"".equals(eisCaller)) {
         String result;
         if (this.inboundCallerPrincipalMapping != null && this.inboundCallerPrincipalMapping.containsKey(eisCaller)) {
            result = (String)this.inboundCallerPrincipalMapping.get(eisCaller);
         } else {
            result = this.defaultCallerPrincipalMapped;
         }

         return result;
      } else {
         return null;
      }
   }

   public String mapGroupPrincipal(String eisGroup) {
      if (null != eisGroup && !"".equals(eisGroup)) {
         return this.inboundGroupPrincipalMapping != null && this.inboundGroupPrincipalMapping.containsKey(eisGroup) ? (String)this.inboundGroupPrincipalMapping.get(eisGroup) : this.defaultGroupMappedPrincipal;
      } else {
         return null;
      }
   }

   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append("SecurityContextPrincipalMapperImpl: \n");
      buffer.append("defaultCallerPrincipalMapped = " + this.defaultCallerPrincipalMapped + " \n");
      buffer.append("inboundCallerPrincipalMapping = " + this.inboundCallerPrincipalMapping.toString() + "\n");
      buffer.append("defaultGroupMappedPrincipal = " + this.defaultGroupMappedPrincipal + " \n");
      buffer.append("inboundGroupPrincipalMapping = " + this.inboundGroupPrincipalMapping.toString() + "\n");
      return buffer.toString();
   }
}
