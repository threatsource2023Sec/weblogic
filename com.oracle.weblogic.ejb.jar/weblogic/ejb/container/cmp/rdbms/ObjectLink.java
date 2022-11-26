package weblogic.ejb.container.cmp.rdbms;

public final class ObjectLink {
   public static final boolean verbose = false;
   public static final boolean debug = false;
   private String beanField = null;
   private String dbmsColumn = null;

   public ObjectLink() {
   }

   public ObjectLink(String beanField, String dbmsColumn) {
      this.setBeanField(beanField);
      this.setDBMSColumn(dbmsColumn);
   }

   public void setBeanField(String fieldName) {
      this.beanField = fieldName;
   }

   public String getBeanField() {
      return this.beanField;
   }

   public void setDBMSColumn(String colName) {
      this.dbmsColumn = colName;
   }

   public String getDBMSColumn() {
      return this.dbmsColumn;
   }

   public boolean equals(Object other) {
      if (!(other instanceof ObjectLink)) {
         return false;
      } else {
         ObjectLink otherLink = (ObjectLink)other;
         if (!this.beanField.equals(otherLink.getBeanField())) {
            return false;
         } else {
            return this.dbmsColumn.equals(otherLink.getDBMSColumn());
         }
      }
   }

   public int hashCode() {
      return this.beanField.hashCode() | this.dbmsColumn.hashCode();
   }

   public String toString() {
      return "[ObjectLink: field:" + this.beanField + " to column:" + this.dbmsColumn + "]";
   }
}
