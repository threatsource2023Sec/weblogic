package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class AutomaticKeyGenerationMBeanImpl extends XMLElementMBeanDelegate implements AutomaticKeyGenerationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_generatorName = false;
   private String generatorName;
   private boolean isSet_selectFirstSequenceKeyBeforeUpdate = false;
   private boolean selectFirstSequenceKeyBeforeUpdate;
   private boolean isSet_keyCacheSize = false;
   private int keyCacheSize;
   private boolean isSet_generatorType = false;
   private String generatorType;

   public String getGeneratorName() {
      return this.generatorName;
   }

   public void setGeneratorName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.generatorName;
      this.generatorName = value;
      this.isSet_generatorName = value != null;
      this.checkChange("generatorName", old, this.generatorName);
   }

   public boolean getSelectFirstSequenceKeyBeforeUpdate() {
      return this.selectFirstSequenceKeyBeforeUpdate;
   }

   public void setSelectFirstSequenceKeyBeforeUpdate(boolean value) {
      boolean old = this.selectFirstSequenceKeyBeforeUpdate;
      this.selectFirstSequenceKeyBeforeUpdate = value;
      this.isSet_selectFirstSequenceKeyBeforeUpdate = true;
      this.checkChange("selectFirstSequenceKeyBeforeUpdate", old, this.selectFirstSequenceKeyBeforeUpdate);
   }

   public int getKeyCacheSize() {
      return this.keyCacheSize;
   }

   public void setKeyCacheSize(int value) {
      int old = this.keyCacheSize;
      this.keyCacheSize = value;
      this.isSet_keyCacheSize = value != -1;
      this.checkChange("keyCacheSize", old, this.keyCacheSize);
   }

   public String getGeneratorType() {
      return this.generatorType;
   }

   public void setGeneratorType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.generatorType;
      this.generatorType = value;
      this.isSet_generatorType = value != null;
      this.checkChange("generatorType", old, this.generatorType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<automatic-key-generation");
      result.append(">\n");
      if (null != this.getGeneratorType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<generator-type>").append(this.getGeneratorType()).append("</generator-type>\n");
      }

      if (null != this.getGeneratorName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<generator-name>").append(this.getGeneratorName()).append("</generator-name>\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<key-cache-size>").append(this.getKeyCacheSize()).append("</key-cache-size>\n");
      result.append(ToXML.indent(indentLevel)).append("</automatic-key-generation>\n");
      return result.toString();
   }
}
