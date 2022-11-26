package weblogic.connector.external;

import java.util.Map;

public interface SecurityIdentityInfo {
   String getManageAsPrincipalName();

   boolean useCallerForRunAs();

   String getRunAsPrincipalName();

   boolean useCallerForRunWorkAs();

   String getRunWorkAsPrincipalName();

   String getDefaultPrincipalName();

   boolean isInboundMappingRequired();

   String getDefaultCallerPrincipalMapped();

   Map getInboundCallerPrincipalMapping();

   String getDefaultGroupMappedPrincipal();

   Map getInboundGroupPrincipalMapping();
}
