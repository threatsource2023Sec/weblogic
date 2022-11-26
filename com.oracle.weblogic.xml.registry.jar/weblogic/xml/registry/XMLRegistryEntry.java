package weblogic.xml.registry;

import java.io.Serializable;

public class XMLRegistryEntry implements Serializable {
   private String publicId;
   private String systemId;
   private String rootTag;
   private String entityPath;
   private String parserClassName;
   private String documentBuilderFactoryClassName;
   private String saxParserFactoryClassName;
   private boolean isPrivate = false;

   public XMLRegistryEntry(String pubId, String sysId, String rootElementTag) {
      this.publicId = pubId;
      this.systemId = sysId;
      this.rootTag = rootElementTag;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public String getRootElementTag() {
      return this.rootTag;
   }

   public String getEntityPath() {
      return this.entityPath;
   }

   public void setEntityPath(String val) {
      this.entityPath = val;
   }

   public String getParserClassName() {
      return this.parserClassName;
   }

   public void setParserClassName(String val) {
      this.parserClassName = val;
   }

   public String getDocumentBuilderFactory() {
      return this.documentBuilderFactoryClassName;
   }

   public void setDocumentBuilderFactory(String val) {
      this.documentBuilderFactoryClassName = val;
   }

   public void setPrivate(boolean val) {
      this.isPrivate = val;
   }

   public boolean isPrivate() {
      return this.isPrivate;
   }

   public String getSAXParserFactory() {
      return this.saxParserFactoryClassName;
   }

   public void setSAXParserFactory(String val) {
      this.saxParserFactoryClassName = val;
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("publicId = ");
      sbuf.append(this.publicId == null ? "null" : "\"" + this.publicId + "\"");
      sbuf.append("\nsystemId = ");
      sbuf.append(this.systemId == null ? "null" : "\"" + this.systemId + "\"");
      sbuf.append("\nisPrivate = " + this.isPrivate);
      sbuf.append("\nrootTag = " + this.rootTag);
      sbuf.append("\nentityPath = " + this.entityPath);
      sbuf.append("\nparserClassName = " + this.parserClassName);
      sbuf.append("\ndocumentBuilderFactoryClassName = " + this.documentBuilderFactoryClassName);
      sbuf.append("\nsaxParserFactoryClassName = " + this.saxParserFactoryClassName);
      return sbuf.toString();
   }
}
