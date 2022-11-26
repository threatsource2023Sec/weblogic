package weblogic.j2ee.descriptor.wl60;

public interface FinderBean {
   String getFinderName();

   void setFinderName(String var1);

   String[] getFinderParams();

   void addFinderParam(String var1);

   void removeFinderParam(String var1);

   void setFinderParams(String[] var1);

   String getFinderQuery();

   void setFinderQuery(String var1);

   String getFinderSql();

   void setFinderSql(String var1);

   boolean isFindForUpdate();

   void setFindForUpdate(boolean var1);

   String getId();

   void setId(String var1);
}
