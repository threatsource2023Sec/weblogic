package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface PreprocessorMappingMBean extends WebElementMBean {
   PreprocessorMBean getPreprocessor();

   void setPreprocessor(PreprocessorMBean var1);

   String getURLPattern();

   void setURLPattern(String var1);
}
