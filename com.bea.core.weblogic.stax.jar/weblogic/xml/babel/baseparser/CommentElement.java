package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public final class CommentElement extends CharDataElement {
   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);
      this.addToTextArray(parser.currentToken);
      parser.accept(7);
      this.type = 6;
   }

   public String toString() {
      return "<!--" + this.getContent() + "-->";
   }
}
