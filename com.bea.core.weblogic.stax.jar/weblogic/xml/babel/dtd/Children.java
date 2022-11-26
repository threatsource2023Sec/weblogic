package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class Children extends ContentParticle {
   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      ContentParticle contentParticle = null;
      switch (parser.getCurrentToken().tokenType) {
         case 56:
            ContentParticle contentParticle = new Choice();
            contentParticle.parse(parser);
            this.parseOperator(parser, contentParticle);
            break;
         case 57:
            ContentParticle contentParticle = new Sequence();
            contentParticle.parse(parser);
            this.parseOperator(parser, contentParticle);
            break;
         case 58:
         default:
            throw new ParseException("Expected a CHOICE or a SEQUENCE", parser.getLine(), parser.getCurrentToken());
         case 59:
            contentParticle = new Mixed();
            contentParticle.parse(parser);
            if (contentParticle.hasChildren()) {
               if (parser.getCurrentToken().tokenType != 33) {
                  throw new ParseException("A mixed declaration with contents other than #PCDATA must be followed by *", parser.getLine(), parser.getCurrentToken());
               }

               this.parseOperator(parser, contentParticle);
            } else {
               this.addChild(contentParticle);
            }
      }

   }
}
