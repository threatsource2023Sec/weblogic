package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class Choice extends ContentParticle {
   public Choice() {
      this.setDelimeter('|');
   }

   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(56);
      parser.ignore(19);
      ContentParticle contentParticle = new ContentParticle();
      contentParticle.parse(parser);
      this.addChild(contentParticle);
      parser.ignore(19);

      while(parser.compare(35)) {
         parser.accept();
         parser.ignore(19);
         contentParticle = new ContentParticle();
         contentParticle.parse(parser);
         this.addChild(contentParticle);
         parser.ignore(19);
      }

      parser.accept(31);
   }
}
