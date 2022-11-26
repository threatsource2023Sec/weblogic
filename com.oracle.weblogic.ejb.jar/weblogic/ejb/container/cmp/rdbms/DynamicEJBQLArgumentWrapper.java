package weblogic.ejb.container.cmp.rdbms;

import java.io.Serializable;

public class DynamicEJBQLArgumentWrapper implements Serializable {
   private String argumentName;
   private boolean isOracleNLSDataType;

   public DynamicEJBQLArgumentWrapper(String name, boolean isNLS) {
      this.argumentName = name;
      this.isOracleNLSDataType = isNLS;
   }

   public String getArgumentName() {
      return this.argumentName;
   }

   public boolean isOracleNLSDataType() {
      return this.isOracleNLSDataType;
   }

   public String toString() {
      return this.argumentName.toString();
   }

   public int hashCode() {
      return this.argumentName.hashCode();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && o instanceof DynamicEJBQLArgumentWrapper) {
         DynamicEJBQLArgumentWrapper other = (DynamicEJBQLArgumentWrapper)o;
         return this.isOracleNLSDataType == other.isOracleNLSDataType && (this.argumentName == other.argumentName || this.argumentName != null && this.argumentName.equals(other.argumentName));
      } else {
         return false;
      }
   }
}
