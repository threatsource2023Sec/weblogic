package weblogic.ejb.container.cmp.rdbms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class RDBMSRelation implements Serializable {
   private static final long serialVersionUID = 3661666804538715956L;
   private String m_name = null;
   private String m_tableName = null;
   private RDBMSRole m_role1 = null;
   private RDBMSRole m_role2 = null;

   public void setName(String name) {
      this.m_name = name;
   }

   public String getName() {
      return this.m_name;
   }

   public void setTableName(String name) {
      this.m_tableName = name;
   }

   public String getTableName() {
      return this.m_tableName;
   }

   public void setRole1(RDBMSRole role) {
      this.m_role1 = role;
   }

   public RDBMSRole getRole1() {
      return this.m_role1;
   }

   public void setRole2(RDBMSRole role) {
      this.m_role2 = role;
   }

   public RDBMSRole getRole2() {
      return this.m_role2;
   }

   public String toString() {
      return "[RDBMSRelation name:" + this.m_name + " table:" + this.m_tableName + " role1:" + this.m_role1 + " rol2:" + this.m_role2 + "]";
   }

   public static class RDBMSRole implements Serializable {
      private static final long serialVersionUID = -8166176319205262263L;
      private String m_name = null;
      private String m_groupName = null;
      private Map m_columnMap = new HashMap();
      private Map m_keyTableMap = null;
      boolean dbCascadeDelete = false;
      private boolean queryCachingEnabled = false;
      private String m_fkTableName = null;
      private String m_pkTableName = null;

      public void setName(String name) {
         this.m_name = name;
      }

      public String getName() {
         return this.m_name;
      }

      public void setForeignKeyTableName(String name) {
         this.m_fkTableName = name;
      }

      public String getForeignKeyTableName() {
         return this.m_fkTableName;
      }

      public void setPrimaryKeyTableName(String name) {
         this.m_pkTableName = name;
      }

      public String getPrimaryKeyTableName() {
         return this.m_pkTableName;
      }

      public void setGroupName(String name) {
         this.m_groupName = name;
      }

      public String getGroupName() {
         return this.m_groupName;
      }

      public void setColumnMap(Map ol) {
         this.m_columnMap = ol;
      }

      public Map getColumnMap() {
         return this.m_columnMap;
      }

      public void setDBCascadeDelete(boolean dbCascadeDelete) {
         this.dbCascadeDelete = dbCascadeDelete;
      }

      public boolean getDBCascadeDelete() {
         return this.dbCascadeDelete;
      }

      public void setQueryCachingEnabled(boolean queryCachingEnabled) {
         this.queryCachingEnabled = queryCachingEnabled;
      }

      public boolean isQueryCachingEnabled() {
         return this.queryCachingEnabled;
      }

      public String toString() {
         return "[RDBMSRole name:" + this.m_name + " group:" + this.m_groupName + " columnMap:" + this.m_columnMap + "]";
      }
   }
}
