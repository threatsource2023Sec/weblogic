package weblogic.xml.util.xed;

import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public abstract class Command {
   private String xpath;

   public abstract String getName();

   public abstract Object evaluate(Context var1) throws StreamEditorException;

   public void setXPath(String xp) {
      this.xpath = xp;
   }

   public String getXPath() {
      return this.xpath;
   }

   public String toString() {
      return "[" + this.getName() + "][" + this.getXPath() + "]";
   }

   public XMLInputStream getResult() throws XMLStreamException {
      return null;
   }

   public boolean isInsertBefore() {
      return false;
   }

   public boolean isInsertAfter() {
      return false;
   }

   public boolean isDelete() {
      return false;
   }

   public boolean isInsertChild() {
      return false;
   }
}
