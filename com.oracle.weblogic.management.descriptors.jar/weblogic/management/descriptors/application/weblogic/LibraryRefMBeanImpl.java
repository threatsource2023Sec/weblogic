package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class LibraryRefMBeanImpl extends XMLElementMBeanDelegate implements LibraryRefMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_exactMatch = false;
   private String exactMatch;
   private boolean isSet_contextPath = false;
   private String contextPath;
   private boolean isSet_implementationVersion = false;
   private String implementationVersion;
   private boolean isSet_specificationVersion = false;
   private String specificationVersion;
   private boolean isSet_libraryName = false;
   private String libraryName;

   public String getExactMatch() {
      return this.exactMatch;
   }

   public void setExactMatch(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.exactMatch;
      this.exactMatch = value;
      this.isSet_exactMatch = value != null;
      this.checkChange("exactMatch", old, this.exactMatch);
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public void setContextPath(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.contextPath;
      this.contextPath = value;
      this.isSet_contextPath = value != null;
      this.checkChange("contextPath", old, this.contextPath);
   }

   public String getImplementationVersion() {
      return this.implementationVersion;
   }

   public void setImplementationVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.implementationVersion;
      this.implementationVersion = value;
      this.isSet_implementationVersion = value != null;
      this.checkChange("implementationVersion", old, this.implementationVersion);
   }

   public String getSpecificationVersion() {
      return this.specificationVersion;
   }

   public void setSpecificationVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.specificationVersion;
      this.specificationVersion = value;
      this.isSet_specificationVersion = value != null;
      this.checkChange("specificationVersion", old, this.specificationVersion);
   }

   public String getLibraryName() {
      return this.libraryName;
   }

   public void setLibraryName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.libraryName;
      this.libraryName = value;
      this.isSet_libraryName = value != null;
      this.checkChange("libraryName", old, this.libraryName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<library-ref");
      result.append(">\n");
      if (null != this.getLibraryName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<library-name>").append(this.getLibraryName()).append("</library-name>\n");
      }

      if (null != this.getSpecificationVersion()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<specification-version>").append(this.getSpecificationVersion()).append("</specification-version>\n");
      }

      if (null != this.getImplementationVersion()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<implementation-version>").append(this.getImplementationVersion()).append("</implementation-version>\n");
      }

      if (null != this.getExactMatch()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<exact-match>").append(this.getExactMatch()).append("</exact-match>\n");
      }

      if (null != this.getContextPath()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<context-path>").append(this.getContextPath()).append("</context-path>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</library-ref>\n");
      return result.toString();
   }
}
