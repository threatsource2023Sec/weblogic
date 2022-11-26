package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface PreprocessorMBean extends WebElementMBean {
   String getPreprocessorName();

   void setPreprocessorName(String var1);

   String getPreprocessorClass();

   void setPreprocessorClass(String var1);
}
