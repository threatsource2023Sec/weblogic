package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ParserFactoryMBeanImpl extends XMLElementMBeanDelegate implements ParserFactoryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_documentBuilderFactory = false;
   private String documentBuilderFactory;
   private boolean isSet_transformerFactory = false;
   private String transformerFactory;
   private boolean isSet_saxparserFactory = false;
   private String saxparserFactory;

   public String getDocumentBuilderFactory() {
      return this.documentBuilderFactory;
   }

   public void setDocumentBuilderFactory(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.documentBuilderFactory;
      this.documentBuilderFactory = value;
      this.isSet_documentBuilderFactory = value != null;
      this.checkChange("documentBuilderFactory", old, this.documentBuilderFactory);
   }

   public String getTransformerFactory() {
      return this.transformerFactory;
   }

   public void setTransformerFactory(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.transformerFactory;
      this.transformerFactory = value;
      this.isSet_transformerFactory = value != null;
      this.checkChange("transformerFactory", old, this.transformerFactory);
   }

   public String getSaxparserFactory() {
      return this.saxparserFactory;
   }

   public void setSaxparserFactory(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.saxparserFactory;
      this.saxparserFactory = value;
      this.isSet_saxparserFactory = value != null;
      this.checkChange("saxparserFactory", old, this.saxparserFactory);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<parser-factory");
      result.append(">\n");
      if (null != this.getSaxparserFactory()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<saxparser-factory>").append(this.getSaxparserFactory()).append("</saxparser-factory>\n");
      }

      if (null != this.getDocumentBuilderFactory()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<document-builder-factory>").append(this.getDocumentBuilderFactory()).append("</document-builder-factory>\n");
      }

      if (null != this.getTransformerFactory()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<transformer-factory>").append(this.getTransformerFactory()).append("</transformer-factory>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</parser-factory>\n");
      return result.toString();
   }
}
