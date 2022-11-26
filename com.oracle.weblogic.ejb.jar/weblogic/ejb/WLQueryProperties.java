package weblogic.ejb;

import javax.ejb.FinderException;

public interface WLQueryProperties extends QueryProperties {
   String GROUP_NAME = "GROUP_NAME";
   String SQL_SELECT_DISTINCT = "SQL_SELECT_DISTINCT";
   String ISOLATION_LEVEL = "ISOLATION_LEVEL";
   String RELATIONSHIP_CACHING_NAME = "RELATIONSHIP_CACHING_NAME";
   int SERIALIZABLE = 1;
   int REPEATABLE_READ = 2;
   int READ_COMMITTED = 3;
   int NONE = 4;
   int READ_UNCOMMITTED = 5;
   int READ_COMMITTED_FOR_UPDATE = 6;

   void setFieldGroupName(String var1) throws FinderException;

   String getFieldGroupName() throws FinderException;

   void setSQLSelectDistinct(boolean var1) throws FinderException;

   boolean getSQLSelectDistinct() throws FinderException;

   void setIsolationLevel(int var1) throws FinderException;

   int getIsolationLevel() throws FinderException;

   void setRelationshipCachingName(String var1) throws FinderException;

   String getRelationshipCachingName() throws FinderException;

   void setSqlShapeName(String var1) throws FinderException;

   String getSqlShapeName() throws FinderException;
}
