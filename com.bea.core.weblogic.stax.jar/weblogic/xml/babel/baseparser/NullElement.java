package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public class NullElement extends Element {
   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.type = 8;
   }

   public String toString() {
      return "NULL";
   }
}
