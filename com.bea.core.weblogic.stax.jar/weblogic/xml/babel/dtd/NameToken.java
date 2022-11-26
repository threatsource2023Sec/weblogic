package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;

public class NameToken extends AttributeType {
   private String name;

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      Token currentToken = parser.getCurrentToken();
      parser.accept(46);
      this.name = currentToken.text;
   }

   public String toString() {
      return this.name;
   }
}
