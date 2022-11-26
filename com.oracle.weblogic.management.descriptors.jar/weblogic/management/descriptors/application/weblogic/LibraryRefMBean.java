package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface LibraryRefMBean extends XMLElementMBean {
   String getLibraryName();

   void setLibraryName(String var1);

   String getSpecificationVersion();

   void setSpecificationVersion(String var1);

   String getImplementationVersion();

   void setImplementationVersion(String var1);

   String getExactMatch();

   void setExactMatch(String var1);

   String getContextPath();

   void setContextPath(String var1);
}
