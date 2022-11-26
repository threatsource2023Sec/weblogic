package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class CompatibilityMBeanImpl extends XMLElementMBeanDelegate implements CompatibilityMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_byteArrayIsSerializedToOracleBlob = false;
   private boolean byteArrayIsSerializedToOracleBlob = false;
   private boolean isSet_encoding = false;
   private String encoding;
   private boolean isSet_allowReadonlyCreateAndRemove = false;
   private boolean allowReadonlyCreateAndRemove = false;
   private boolean isSet_loadRelatedBeansFromDbInPostCreate = false;
   private boolean loadRelatedBeansFromDbInPostCreate = false;

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.version;
      this.version = value;
      this.isSet_version = value != null;
      this.checkChange("version", old, this.version);
   }

   public boolean getByteArrayIsSerializedToOracleBlob() {
      return this.byteArrayIsSerializedToOracleBlob;
   }

   public void setByteArrayIsSerializedToOracleBlob(boolean value) {
      boolean old = this.byteArrayIsSerializedToOracleBlob;
      this.byteArrayIsSerializedToOracleBlob = value;
      this.isSet_byteArrayIsSerializedToOracleBlob = true;
      this.checkChange("byteArrayIsSerializedToOracleBlob", old, this.byteArrayIsSerializedToOracleBlob);
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public boolean getAllowReadonlyCreateAndRemove() {
      return this.allowReadonlyCreateAndRemove;
   }

   public void setAllowReadonlyCreateAndRemove(boolean value) {
      boolean old = this.allowReadonlyCreateAndRemove;
      this.allowReadonlyCreateAndRemove = value;
      this.isSet_allowReadonlyCreateAndRemove = true;
      this.checkChange("allowReadonlyCreateAndRemove", old, this.allowReadonlyCreateAndRemove);
   }

   public boolean getLoadRelatedBeansFromDbInPostCreate() {
      return this.loadRelatedBeansFromDbInPostCreate;
   }

   public void setLoadRelatedBeansFromDbInPostCreate(boolean value) {
      boolean old = this.loadRelatedBeansFromDbInPostCreate;
      this.loadRelatedBeansFromDbInPostCreate = value;
      this.isSet_loadRelatedBeansFromDbInPostCreate = true;
      this.checkChange("loadRelatedBeansFromDbInPostCreate", old, this.loadRelatedBeansFromDbInPostCreate);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<compatibility");
      result.append(">\n");
      if (this.isSet_byteArrayIsSerializedToOracleBlob || this.getByteArrayIsSerializedToOracleBlob()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<byte-array-is-serialized-to-oracle-blob>").append(ToXML.capitalize(Boolean.valueOf(this.getByteArrayIsSerializedToOracleBlob()).toString())).append("</byte-array-is-serialized-to-oracle-blob>\n");
      }

      if (this.isSet_allowReadonlyCreateAndRemove || this.getAllowReadonlyCreateAndRemove()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<allow-readonly-create-and-remove>").append(ToXML.capitalize(Boolean.valueOf(this.getAllowReadonlyCreateAndRemove()).toString())).append("</allow-readonly-create-and-remove>\n");
      }

      if (this.isSet_loadRelatedBeansFromDbInPostCreate || this.getLoadRelatedBeansFromDbInPostCreate()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<load-related-beans-from-db-in-post-create>").append(ToXML.capitalize(Boolean.valueOf(this.getLoadRelatedBeansFromDbInPostCreate()).toString())).append("</load-related-beans-from-db-in-post-create>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</compatibility>\n");
      return result.toString();
   }
}
