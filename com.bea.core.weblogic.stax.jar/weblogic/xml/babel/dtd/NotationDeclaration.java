package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public class NotationDeclaration extends Declaration {
   private Name name;
   private Space space;
   private boolean publicID;
   private boolean externalID;
   private String systemLiteral;
   private String pubidLiteral;
   private boolean isSYSTEM;

   public NotationDeclaration() {
      this.init();
   }

   public void init() {
      super.init();
      this.name = new Name();
      this.space = new Space();
      this.publicID = false;
      this.externalID = false;
      this.systemLiteral = "";
      this.pubidLiteral = "";
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(55);
      this.space.parse(parser);
      this.name.parse(parser);
      this.space.parse(parser);
      switch (parser.getCurrentToken().tokenType) {
         case 25:
            this.isSYSTEM = true;
            parser.accept();
            this.space.parse(parser);
            if (parser.compare(13)) {
               this.systemLiteral = parser.getCurrentToken().getArrayAsString();
               parser.accept();
            } else {
               this.systemLiteral = "";
            }

            this.externalID = true;
            break;
         case 26:
            this.isSYSTEM = false;
            parser.accept();
            this.space.parse(parser);
            if (parser.compare(13)) {
               this.pubidLiteral = parser.getCurrentToken().getArrayAsString();
               parser.accept();
            } else {
               this.pubidLiteral = "";
            }

            this.space.parse(parser);
            if (parser.getCurrentToken().tokenType == 13) {
               this.systemLiteral = parser.getCurrentToken().getArrayAsString();
               parser.accept();
               this.publicID = false;
               this.externalID = true;
            } else {
               this.externalID = false;
               this.publicID = true;
            }
      }

      this.space.parse(parser);
      parser.accept(39);
      this.type = 12;
   }

   public String toString() {
      String val = "<!NOTATION " + this.name;
      if (this.publicID) {
         val = val + " PUBLIC \"" + this.pubidLiteral + "\"";
      } else if (this.isSYSTEM) {
         val = val + " SYSTEM \"" + this.systemLiteral + "\"";
      } else {
         val = val + " PUBLIC \"" + this.pubidLiteral + "\" \"" + this.systemLiteral + "\"";
      }

      return val + ">";
   }
}
