package weblogic.xml.domimpl;

public class SaverOptions {
   private String encoding;
   private Boolean writeXmlDeclaration;
   private boolean prettyPrint;
   private boolean noflush = false;
   private static final SaverOptions DEFAULTS = new SaverOptions();

   public static SaverOptions getDefaults() {
      return new SaverOptions();
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public boolean isWriteXmlDeclaration() {
      return this.writeXmlDeclaration;
   }

   public boolean isSetWriteXmlDeclaration() {
      return this.writeXmlDeclaration != null;
   }

   public void setWriteXmlDeclaration(boolean writeXmlDeclaration) {
      this.writeXmlDeclaration = writeXmlDeclaration;
   }

   public boolean isPrettyPrint() {
      return this.prettyPrint;
   }

   public void setPrettyPrint(boolean prettyPrint) {
      this.prettyPrint = prettyPrint;
   }
}
