package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public class AttributeListDeclaration extends Declaration {
   private Name name;
   private List attributeDefinitions;
   private Space space;

   public AttributeListDeclaration() {
      this.init();
   }

   public void init() {
      super.init();
      this.name = new Name();
      this.space = new Space();
      this.attributeDefinitions = new ArrayList();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      parser.accept(40);
      this.init();
      this.name.parse(parser);
      this.space.parse(parser);

      while(Name.checkStarters(parser.getCurrentToken().tokenType)) {
         AttributeDefinition attDef = new AttributeDefinition();
         attDef.parse(parser);
         this.addDefinition(attDef);
         this.space.parse(parser);
      }

      parser.accept(39);
      this.type = 11;
   }

   public void addDefinition(AttributeDefinition attDef) {
      this.attributeDefinitions.add(attDef);
   }

   public List getDefinitions() {
      return this.attributeDefinitions;
   }

   public String toString() {
      String val = "<!ATTLIST " + this.name;

      for(Iterator I = this.attributeDefinitions.iterator(); I.hasNext(); val = val + " \n\t" + (AttributeDefinition)I.next()) {
      }

      val = val + " >";
      return val;
   }

   public String getRawName() {
      return this.name.getRawName();
   }
}
