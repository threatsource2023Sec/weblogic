package weblogic.ejb.container.cmp.rdbms;

import java.util.ArrayList;
import java.util.List;

public class SqlShape {
   private String sqlShapeName;
   private final List tables = new ArrayList();
   private String[] ejbRelationNames = null;
   private int passThroughColumns = 0;

   public String getSqlShapeName() {
      return this.sqlShapeName;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (!(other instanceof SqlShape)) {
         return false;
      } else {
         SqlShape otherSC = (SqlShape)other;
         return this.sqlShapeName.equals(otherSC.getSqlShapeName());
      }
   }

   public int hashCode() {
      return this.sqlShapeName.hashCode();
   }

   public void setSqlShapeName(String string) {
      this.sqlShapeName = string;
   }

   public List getTables() {
      return this.tables;
   }

   public void addTable(Table table) {
      this.tables.add(table);
   }

   public void addResultColumn() {
      this.tables.add((Object)null);
   }

   public String[] getEjbRelationNames() {
      return this.ejbRelationNames;
   }

   public void setEjbRelationNames(String[] strings) {
      this.ejbRelationNames = strings;
      if (this.ejbRelationNames != null && this.ejbRelationNames.length == 0) {
         this.ejbRelationNames = null;
      }

   }

   public int getPassThroughColumns() {
      return this.passThroughColumns;
   }

   public void setPassThroughColumns(int i) {
      this.passThroughColumns = i;
   }

   public static class Table {
      private String name;
      private List columns = new ArrayList();
      private List ejbRelationshipRoleNames = null;

      public List getColumns() {
         return this.columns;
      }

      public String getName() {
         return this.name;
      }

      public void setColumns(List list) {
         this.columns = list;
      }

      public void setName(String string) {
         this.name = string;
      }

      public void addEjbRelationshipRoleName(String name) {
         if (this.ejbRelationshipRoleNames == null) {
            this.ejbRelationshipRoleNames = new ArrayList();
         }

         this.ejbRelationshipRoleNames.add(name);
      }

      public List getEjbRelationshipRoleNames() {
         return this.ejbRelationshipRoleNames;
      }
   }
}
