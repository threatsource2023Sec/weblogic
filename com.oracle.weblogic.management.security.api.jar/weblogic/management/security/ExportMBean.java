package weblogic.management.security;

import java.util.Properties;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.InvalidParameterException;

public interface ExportMBean extends StandardInterface, DescriptorBean {
   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   void exportData(String var1, String var2, Properties var3) throws InvalidParameterException, ErrorCollectionException;
}
