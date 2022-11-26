package weblogic.ejb.container.persistence;

import java.util.Arrays;
import weblogic.ejb.container.persistence.spi.CmrField;

public final class CmrFieldImpl implements CmrField {
   protected String[] description;
   protected String cmrFieldName;
   protected String cmrFieldType;

   public void setDescriptions(String[] d) {
      this.description = d;
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public void setCmrFieldName(String cmrFieldName) {
      this.cmrFieldName = cmrFieldName;
   }

   public String getName() {
      return this.cmrFieldName;
   }

   public void setCmrFieldType(String cmrFieldType) {
      this.cmrFieldType = cmrFieldType;
   }

   public String getType() {
      return this.cmrFieldType;
   }

   public String toString() {
      return "[CmrFieldImpl description: " + Arrays.toString(this.description) + " name: " + this.cmrFieldName + " type: " + this.cmrFieldType + "]";
   }
}
