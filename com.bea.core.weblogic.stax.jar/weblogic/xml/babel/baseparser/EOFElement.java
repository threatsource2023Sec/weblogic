package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public class EOFElement extends Element {
   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.type = 9;
   }

   public String toString() {
      return "EOF";
   }
}
