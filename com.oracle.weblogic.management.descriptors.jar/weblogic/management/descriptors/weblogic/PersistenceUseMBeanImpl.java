package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PersistenceUseMBeanImpl extends XMLElementMBeanDelegate implements PersistenceUseMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_typeStorage = false;
   private String typeStorage;
   private boolean isSet_typeIdentifier = false;
   private String typeIdentifier;
   private boolean isSet_typeVersion = false;
   private String typeVersion;

   public String getTypeStorage() {
      return this.typeStorage;
   }

   public void setTypeStorage(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.typeStorage;
      this.typeStorage = value;
      this.isSet_typeStorage = value != null;
      this.checkChange("typeStorage", old, this.typeStorage);
   }

   public String getTypeIdentifier() {
      return this.typeIdentifier;
   }

   public void setTypeIdentifier(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.typeIdentifier;
      this.typeIdentifier = value;
      this.isSet_typeIdentifier = value != null;
      this.checkChange("typeIdentifier", old, this.typeIdentifier);
   }

   public String getTypeVersion() {
      return this.typeVersion;
   }

   public void setTypeVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.typeVersion;
      this.typeVersion = value;
      this.isSet_typeVersion = value != null;
      this.checkChange("typeVersion", old, this.typeVersion);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<persistence-use");
      result.append(">\n");
      if (null != this.getTypeIdentifier()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<type-identifier>").append(this.getTypeIdentifier()).append("</type-identifier>\n");
      }

      if (null != this.getTypeVersion()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<type-version>").append(this.getTypeVersion()).append("</type-version>\n");
      }

      if (null != this.getTypeStorage()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<type-storage>").append(this.getTypeStorage()).append("</type-storage>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</persistence-use>\n");
      return result.toString();
   }
}
