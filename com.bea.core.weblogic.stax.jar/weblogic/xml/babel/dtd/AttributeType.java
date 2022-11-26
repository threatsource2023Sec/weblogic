package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;

public class AttributeType extends Declaration {
   protected int tokenType;

   public AttributeType() {
      this.init();
      this.tokenType = 20;
   }

   public void setType(int type) {
      this.tokenType = type;
   }

   public int getType() {
      return this.tokenType;
   }

   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(this.tokenType);
   }

   public String toString() {
      return Token.getString(this.tokenType);
   }
}
