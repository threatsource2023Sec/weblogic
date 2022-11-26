package weblogic.xml.babel.baseparser;

import java.io.IOException;
import weblogic.xml.babel.scanner.ScannerException;

public class ProcessingInstruction extends Element {
   String value;
   protected boolean XMLDecl = false;
   protected XMLDeclaration xmlDeclaration = null;

   protected void init() {
      super.init();
      this.value = null;
      this.xmlDeclaration = null;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);
      parser.accept(8);
      switch (parser.currentToken.tokenType) {
         case 0:
            this.name = parser.currentToken.text;
            parser.accept();
            break;
         case 18:
            this.prefix = parser.currentToken.text;
            parser.accept();
            this.name = parser.currentToken.text;
            parser.accept(0);
            break;
         default:
            throw new ParseException("Expected a NAME", parser.getLine(), parser.currentToken);
      }

      if (parser.compare(17)) {
         this.value = parser.currentToken.text;
         parser.accept();

         for(; parser.compare(17) || parser.compare(19); parser.accept()) {
            if (parser.compare(17)) {
               this.value = this.value + parser.currentToken.text;
            } else {
               this.value = this.value + parser.currentToken.getArrayAsString();
            }
         }
      } else {
         this.value = null;
      }

      parser.accept(9);
      this.type = 4;
      if (this.name.equals("xml")) {
         this.XMLDecl = true;
         this.xmlDeclaration = new XMLDeclaration(this.value);
         this.xmlDeclaration.parse();
      }

   }

   public boolean isXMLDecl() {
      return this.XMLDecl;
   }

   public String getVersion() {
      return this.xmlDeclaration == null ? null : this.xmlDeclaration.getVersion();
   }

   public String getEncoding() {
      return this.xmlDeclaration == null ? null : this.xmlDeclaration.getEncoding();
   }

   public String getStandalone() {
      return this.xmlDeclaration == null ? null : this.xmlDeclaration.getStandalone();
   }

   public String toString() {
      if (this.value != null) {
         return "<?" + this.getName() + " " + this.value + "?>";
      } else {
         return this.name != null ? "<?" + this.getName() + "?>" : "<??>";
      }
   }

   public String getTarget() {
      return this.getName();
   }

   public String getData() {
      return this.value;
   }
}
