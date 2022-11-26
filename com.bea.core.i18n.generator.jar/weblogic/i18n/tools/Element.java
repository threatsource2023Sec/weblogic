package weblogic.i18n.tools;

import org.xml.sax.Locator;

public class Element {
   private int line = 0;
   private int col = 0;

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.col;
   }

   public void setLocator(Locator l) {
      this.line = l.getLineNumber();
      this.col = l.getColumnNumber();
   }
}
