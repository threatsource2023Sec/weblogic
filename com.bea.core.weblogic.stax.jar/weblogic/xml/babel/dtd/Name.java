package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class Name extends ContentParticle {
   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      switch (parser.getCurrentToken().tokenType) {
         case 0:
            this.name = parser.getCurrentToken().text;
            parser.accept();
            break;
         case 18:
            this.prefix = parser.getCurrentToken().text;
            parser.accept();
            this.name = parser.getCurrentToken().text;
            parser.accept(0);
            break;
         default:
            throw new ParseException("Expected a NAME", parser.getLine(), parser.getCurrentToken());
      }

   }

   public static boolean checkStarters(int type) {
      switch (type) {
         case 0:
         case 18:
            return true;
         default:
            return false;
      }
   }

   public String toString() {
      return this.getRawName();
   }
}
