package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class OneOrMore extends ContentParticle {
   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      parser.accept(34);
   }

   public String toString() {
      return this.getFirstChild().toString() + " +";
   }
}
