package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface StatementMBean extends XMLElementMBean {
   boolean isProfilingEnabled();

   void setProfilingEnabled(boolean var1);
}
