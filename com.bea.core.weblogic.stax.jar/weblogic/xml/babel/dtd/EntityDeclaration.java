package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.baseparser.Space;
import weblogic.xml.babel.scanner.ScannerException;

public abstract class EntityDeclaration extends Declaration {
   protected Name name;
   protected Space space;
   protected ExternalID externalID;
   protected boolean isExternal;
   protected String data;

   public EntityDeclaration() {
      this.init();
   }

   public void init() {
      super.init();
      this.name = new Name();
      this.space = new Space();
      this.externalID = new ExternalID();
      this.isExternal = false;
   }

   protected abstract void parseType(BaseParser var1) throws IOException, ScannerException, ParseException;

   protected void parseNData(BaseParser parser) throws IOException, ScannerException, ParseException {
   }

   protected void parseValue(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.data = "";

      while(parser.compare(13) || parser.compare(15) || parser.compare(14)) {
         this.data = this.data + parser.getCurrentToken().getArrayAsString();
         parser.accept();
      }

   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.parseType(parser);
      this.space.parse(parser);
      this.name.parse(parser);
      this.space.parse(parser);
      switch (parser.getCurrentToken().tokenType) {
         case 13:
         case 14:
         case 15:
            this.isExternal = false;
            this.parseValue(parser);
            EntityTable internalTable = parser.getInternalEntityTable();
            if (internalTable != null) {
               internalTable.put(this.name.getRawName(), this.data);
            }
            break;
         case 25:
         case 26:
            this.isExternal = true;
            this.externalID.parse(parser);
            this.parseNData(parser);
            EntityTable table = parser.getExternalEntityTable();
            if (table != null) {
               table.put(this.name.getRawName(), this.externalID);
            }
            break;
         case 39:
            this.data = "";
            break;
         default:
            throw new ParseException("An Entity Declaration must contain an EntityValue or ExternalID", parser.getLine(), parser.getCurrentToken());
      }

      this.space.parse(parser);
      parser.accept(39);
      this.type = 14;
   }
}
