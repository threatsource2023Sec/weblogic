package weblogic.management.security.authorization;

import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public interface PolicyAuxiliaryMBean extends PolicyEditorMBean {
   void exportResource(String var1, String var2) throws InvalidParameterException, ErrorCollectionException;

   String[] listAllURIs(String var1, String var2);

   String[] getResourceNames(String var1);
}
