package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public class Notation extends AttributeType {
   Space space;
   List notations;

   public Notation() {
      this.setType(48);
   }

   public void init() {
      super.init();
      this.space = new Space();
      this.notations = new ArrayList();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(48);
      this.space.parse(parser);
      parser.accept(30);
      this.space.parse(parser);
      Name name = new Name();
      name.parse(parser);
      this.addName(name);
      this.space.parse(parser);

      while(parser.compare(35)) {
         parser.accept();
         this.space.parse(parser);
         name = new Name();
         name.parse(parser);
         this.addName(name);
         this.space.parse(parser);
      }

      parser.accept(31);
   }

   public void addName(Name name) {
      this.notations.add(name);
   }

   public String toString() {
      String val = "(";
      Iterator I = this.notations.iterator();
      if (I.hasNext()) {
         val = val + ((Name)I.next()).toString();
      }

      while(I.hasNext()) {
         val = val + ((Name)I.next()).toString();
      }

      return val + ")";
   }
}
