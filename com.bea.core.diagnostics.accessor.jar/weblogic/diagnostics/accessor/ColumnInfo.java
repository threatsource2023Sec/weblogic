package weblogic.diagnostics.accessor;

import java.io.Serializable;

public class ColumnInfo implements Serializable {
   static final long serialVersionUID = -665469141712332428L;
   private String name;
   private int type;
   public static final int COLTYPE_INT = 1;
   public static final int COLTYPE_LONG = 2;
   public static final int COLTYPE_FLOAT = 3;
   public static final int COLTYPE_DOUBLE = 4;
   public static final int COLTYPE_STRING = 5;
   public static final int COLTYPE_OBJECT = 6;

   public ColumnInfo(String name, int type) {
      this.name = name;
      this.type = type;
   }

   public String getColumnName() {
      return this.name;
   }

   public int getColumnType() {
      return this.type;
   }

   public String getColumnTypeName() {
      switch (this.type) {
         case 1:
            return Integer.class.getName();
         case 2:
            return Long.class.getName();
         case 3:
            return Float.class.getName();
         case 4:
            return Double.class.getName();
         case 5:
            return String.class.getName();
         default:
            return Object.class.getName();
      }
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof ColumnInfo) {
         ColumnInfo other = (ColumnInfo)obj;
         return this.name.equals(other.name) && this.isCompatibleType(other);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.type * 31 + this.name.hashCode();
   }

   private boolean isCompatibleType(ColumnInfo other) {
      return this.type == other.type || other.type == 6 || this.isNumberType(this.type) && this.isNumberType(other.type);
   }

   private boolean isNumberType(int cType) {
      switch (cType) {
         case 1:
         case 2:
         case 3:
         case 4:
            return true;
         default:
            return false;
      }
   }

   public String toString() {
      return "ColumnInfo{" + this.name + "," + this.type + "}";
   }
}
