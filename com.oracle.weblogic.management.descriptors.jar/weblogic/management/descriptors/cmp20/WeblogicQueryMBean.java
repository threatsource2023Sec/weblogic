package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.ejb20.QueryMethodMBean;

public interface WeblogicQueryMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   QueryMethodMBean getQueryMethod();

   void setQueryMethod(QueryMethodMBean var1);

   String getWeblogicQl();

   void setWeblogicQl(String var1);

   String getGroupName();

   void setGroupName(String var1);

   String getCachingName();

   void setCachingName(String var1);

   int getMaxElements();

   void setMaxElements(int var1);

   boolean isIncludeUpdates();

   void setIncludeUpdates(boolean var1);

   boolean getSqlSelectDistinct();

   void setSqlSelectDistinct(boolean var1);
}
