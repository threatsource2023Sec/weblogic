package weblogic.connector.configuration.validation.wl;

import java.util.List;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;

interface WLValidationUtils {
   ConnectionDefinitionBean findMatchingConnectionDefinitionInRA(ConnectorBean var1, String var2);

   List findMatchingAdminInterfaceInRA(ConnectorBean var1, String var2);

   boolean hasMatchingAdminInterfaceInRA(ConnectorBean var1, String var2, String var3);
}
