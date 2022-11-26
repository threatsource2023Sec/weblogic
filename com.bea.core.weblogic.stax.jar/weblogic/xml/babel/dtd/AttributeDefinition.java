package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.Attribute;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public class AttributeDefinition extends Declaration {
   private Name name;
   private Space space;
   private AttributeType attType;
   private AttributeDefault attDefault;

   public AttributeDefinition() {
      this.init();
   }

   public void init() {
      super.init();
      this.name = new Name();
      this.space = new Space();
      this.attDefault = new AttributeDefault();
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.space.parse(parser);
      this.name.parse(parser);
      this.space.parse(parser);
      this.parseAttributeType(parser);
      this.attDefault.parse(parser);
   }

   public boolean hasDefault() {
      return this.attDefault.hasDefault();
   }

   public void parseAttributeType(BaseParser parser) throws IOException, ScannerException, ParseException {
      switch (parser.getCurrentToken().tokenType) {
         case 11:
            this.attType = new Cdata();
            this.attType.parse(parser);
            break;
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         default:
            throw new ParseException("An attribute declaration must include the attribute type which is CDATA,ID,IDREF,IDREFS,ENTITY,ENTITIES,NMTOKEN,NMTOKENS or an enumerated type", parser.getLine(), parser.getCurrentToken());
         case 30:
            this.attType = new Enumeration();
            this.attType.parse(parser);
            break;
         case 41:
            this.attType = new Id();
            this.attType.parse(parser);
            break;
         case 42:
            this.attType = new Idref();
            this.attType.parse(parser);
            break;
         case 43:
            this.attType = new Idrefs();
            this.attType.parse(parser);
            break;
         case 44:
            this.attType = new Entity();
            this.attType.parse(parser);
            break;
         case 45:
            this.attType = new Entities();
            this.attType.parse(parser);
            break;
         case 46:
            this.attType = new Nmtoken();
            this.attType.parse(parser);
            break;
         case 47:
            this.attType = new Nmtokens();
            this.attType.parse(parser);
            break;
         case 48:
            this.attType = new Notation();
            this.attType.parse(parser);
      }

   }

   public String toString() {
      return this.name + " " + this.attType + " " + this.attDefault;
   }

   public int getType() {
      return this.attType.getType();
   }

   public Attribute createDefaultAttribute() {
      Attribute att = new Attribute();
      att.init();
      att.setLocalName(this.name.getRawName());
      att.setValue(this.attDefault.getValue());
      att.setType(this.getType());
      return att;
   }

   public String getRawName() {
      return this.name.getRawName();
   }
}
