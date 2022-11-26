package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface InvalidationTargetMBean extends XMLElementMBean {
   String getEJBName();

   void setEJBName(String var1);
}
