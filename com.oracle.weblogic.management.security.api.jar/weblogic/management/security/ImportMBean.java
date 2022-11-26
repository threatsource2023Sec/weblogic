package weblogic.management.security;

import java.util.Properties;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public interface ImportMBean extends StandardInterface, DescriptorBean {
   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   void importData(String var1, String var2, Properties var3) throws InvalidParameterException, ErrorCollectionException;
}
