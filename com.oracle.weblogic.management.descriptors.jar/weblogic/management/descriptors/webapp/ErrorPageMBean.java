package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ErrorPageMBean extends WebElementMBean {
   String getErrorCode();

   void setErrorCode(String var1);

   String getError();

   void setError(String var1);

   String getExceptionType();

   void setExceptionType(String var1);

   String getLocation();

   void setLocation(String var1);
}
