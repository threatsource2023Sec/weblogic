package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public final class EndElement extends Element {
   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);
      parser.accept(4);
      switch (parser.currentToken.tokenType) {
         case 0:
            this.name = parser.currentToken.text;
            parser.accept();
            break;
         case 18:
            this.prefix = parser.currentToken.text;
            parser.accept();
            this.name = parser.currentToken.text;
            parser.accept(0);
            break;
         default:
            throw new ParseException("Expected a NAME", parser.getLine(), parser.currentToken);
      }

      parser.accept(2);
      this.type = 3;
      if (!this.setNameSpace(parser)) {
         this.setDefaultNameSpace(parser);
      }

   }

   public String toString() {
      return "</" + this.getName() + ">";
   }
}
