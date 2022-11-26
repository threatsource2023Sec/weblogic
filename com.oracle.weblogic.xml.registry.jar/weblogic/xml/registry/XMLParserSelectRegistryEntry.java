package weblogic.xml.registry;

public class XMLParserSelectRegistryEntry extends XMLAbstractRegistryEntry {
   private String rootTag;
   private String documentBuilderFactoryClassName;
   private String saxParserFactoryClassName;
   private String transformerFactoryClassName;
   private String parserClassName;

   public XMLParserSelectRegistryEntry(String pubId, String sysId, String rootElementTag, ConfigAbstraction.EntryConfig mbean) {
      super(pubId, sysId, mbean);
      this.rootTag = rootElementTag;
   }

   public String getRootElementTag() {
      return this.rootTag;
   }

   public String getDocumentBuilderFactory() {
      return this.documentBuilderFactoryClassName;
   }

   public void setDocumentBuilderFactory(String val) {
      this.documentBuilderFactoryClassName = val;
   }

   /** @deprecated */
   @Deprecated
   public String getParserClassName() {
      return this.parserClassName;
   }

   /** @deprecated */
   @Deprecated
   public void setParserClassName(String val) {
      this.parserClassName = val;
   }

   public String getSAXParserFactory() {
      return this.saxParserFactoryClassName;
   }

   public void setSAXParserFactory(String val) {
      this.saxParserFactoryClassName = val;
   }

   public String getTransformerFactory() {
      return this.transformerFactoryClassName;
   }

   public void setTransformerFactory(String factoryClassName) {
      this.transformerFactoryClassName = factoryClassName;
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("publicId = ");
      sbuf.append(this.getPublicId() == null ? "null, " : "\"" + this.getPublicId() + "\", ");
      sbuf.append("systemId = ");
      sbuf.append(this.getSystemId() == null ? "null, " : "\"" + this.getSystemId() + "\", ");
      sbuf.append("isPrivate = " + this.isPrivate() + ", ");
      sbuf.append("rootTag = " + this.getRootElementTag() + ", ");
      sbuf.append("documentBuilderFactoryClassName = " + this.documentBuilderFactoryClassName + ", ");
      sbuf.append("saxParserFactoryClassName = " + this.saxParserFactoryClassName + ", ");
      sbuf.append("transformerFactoryClassName = " + this.transformerFactoryClassName);
      return sbuf.toString();
   }
}
