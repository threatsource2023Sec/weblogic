package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class ParsedEntityDeclaration extends EntityDeclaration {
   public ParsedEntityDeclaration() {
      this.init();
   }

   public void init() {
      super.init();
   }

   protected void parseType(BaseParser parser) throws IOException, ScannerException, ParseException {
      parser.accept(53);
      this.space.parse(parser);
   }

   public String toString() {
      String s = "<!ENTITY % " + this.name;
      if (!this.isExternal) {
         s = s + " \"" + this.data + "\"";
      } else {
         s = s + " " + this.externalID;
      }

      s = s + " >";
      return s;
   }
}
