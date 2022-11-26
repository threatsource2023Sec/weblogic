package weblogicx.xml.stream;

import java.util.EventObject;
import java.util.Map;
import org.xml.sax.Locator;

public abstract class XMLEvent extends EventObject implements Cloneable {
   private int line;
   private int column;
   private String publicId;
   private String systemId;
   private Map prefixMap;
   private Locator locator;

   public XMLEvent(Object source, Locator locator) {
      super(source);
      this.line = locator.getLineNumber();
      this.column = locator.getColumnNumber();
      this.locator = locator;
   }

   protected Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public Locator getLocator() {
      return this.locator;
   }

   public int getLineNumber() {
      return this.line;
   }

   public int getColumnNumber() {
      return this.column;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public Map getPrefixMap() {
      return this.prefixMap;
   }

   public void setPrefixMap(Map prefixMap) {
      this.prefixMap = prefixMap;
   }
}
