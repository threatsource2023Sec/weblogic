package weblogic.connector.external;

import java.util.List;

public interface RAValidationInfo {
   boolean isCompliant();

   List getWarnings();

   boolean hasRAxml();

   boolean isLinkRef();

   String getLinkRef();

   PropSetterTable getRAPropSetterTable();

   String getModuleName();

   PropSetterTable getAdminPropSetterTable(String var1, String var2);

   PropSetterTable getConnectionFactoryPropSetterTable(String var1);

   PropSetterTable getActivationSpecPropSetterTable(String var1);
}
