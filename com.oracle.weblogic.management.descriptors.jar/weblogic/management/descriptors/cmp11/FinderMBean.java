package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLElementMBean;

public interface FinderMBean extends XMLElementMBean {
   String getFinderName();

   void setFinderName(String var1);

   String[] getFinderParams();

   void setFinderParams(String[] var1);

   void addFinderParam(String var1);

   String getFinderQuery();

   void setFinderQuery(String var1);

   String getFinderSQL();

   void setFinderSQL(String var1);

   boolean isFindForUpdate();

   void setFindForUpdate(boolean var1);
}
