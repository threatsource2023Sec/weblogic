package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public class ExternalID extends Declaration {
   private boolean system;
   private String systemLiteral;
   private String pubidLiteral;

   public void init() {
      super.init();
   }

   public String getSystemLiteral() {
      return this.systemLiteral;
   }

   public String getPubidLiteral() {
      return this.pubidLiteral;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      Space space = new Space();
      switch (parser.getCurrentToken().tokenType) {
         case 25:
            parser.accept();
            space.parse(parser);
            this.systemLiteral = parser.getCurrentToken().getArrayAsString();
            parser.accept(13);
            this.system = true;
            break;
         case 26:
            parser.accept();
            space.parse(parser);
            this.systemLiteral = parser.getCurrentToken().getArrayAsString();
            parser.accept(13);
            space.parse(parser);
            this.pubidLiteral = parser.getCurrentToken().getArrayAsString();
            parser.accept(13);
            this.system = false;
      }

   }

   public static boolean checkStarters(int type) {
      switch (type) {
         case 25:
         case 26:
            return true;
         default:
            return false;
      }
   }

   public String toString() {
      return this.system ? "SYSTEM " + this.systemLiteral : "PUBLIC " + this.pubidLiteral + " " + this.systemLiteral;
   }
}
