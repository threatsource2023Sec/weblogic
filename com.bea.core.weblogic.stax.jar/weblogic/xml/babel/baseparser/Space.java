package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public final class Space extends CharDataElement {
   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);

      while(parser.compare(19)) {
         this.addToTextArrayNonAlloc(parser.currentToken);
         parser.accept();
         this.type = 7;
      }

   }
}
