package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.Iterator;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class Mixed extends ContentParticle {
   public Mixed() {
      this.setDelimeter('|');
   }

   public void init() {
      super.init();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(59);
      parser.ignore(19);
      parser.accept(37);
      parser.ignore(19);

      while(parser.compare(35)) {
         parser.accept();
         parser.ignore(19);
         ContentParticle contentParticle = new Name();
         contentParticle.parse(parser);
         this.addChild(contentParticle);
         parser.ignore(19);
      }

      parser.accept(31);
   }

   public String toString() {
      String val = "(#PCDATA ";

      ContentParticle cp;
      for(Iterator i = this.children.iterator(); i.hasNext(); val = val + " " + this.delimeter + " " + cp.toString()) {
         cp = (ContentParticle)i.next();
      }

      val = val + " )";
      return val;
   }
}
