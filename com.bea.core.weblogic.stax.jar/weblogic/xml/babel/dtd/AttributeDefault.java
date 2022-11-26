package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class AttributeDefault extends Declaration {
   private String value;
   private boolean required;
   private boolean implied;
   private boolean fixed;
   private boolean hasDefault;

   public AttributeDefault() {
      this.init();
   }

   public void init() {
      super.init();
      this.required = false;
      this.implied = false;
      this.fixed = false;
      this.hasDefault = false;
   }

   public boolean isRequired() {
      return this.required;
   }

   public boolean isFixed() {
      return this.fixed;
   }

   public boolean isImplied() {
      return this.implied;
   }

   public boolean hasDefault() {
      return this.hasDefault;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      switch (parser.getCurrentToken().tokenType) {
         case 49:
            parser.accept();
            this.required = true;
            break;
         case 50:
            parser.accept();
            this.implied = true;
            break;
         case 51:
            parser.accept();
            this.fixed = true;
         case 13:
         case 14:
         case 15:
         case 19:
            for(this.value = ""; parser.compare(13) || parser.compare(14) || parser.compare(15) || parser.compare(19); this.hasDefault = true) {
               if (!parser.compare(15) && !parser.compare(14)) {
                  if (parser.compare(19)) {
                     this.value = this.value + " ";
                  } else {
                     this.value = this.value + parser.getCurrentToken().getArrayAsString();
                  }
               } else {
                  this.value = this.value + parser.getCurrentToken().text;
               }

               parser.accept();
            }

            return;
         default:
            throw new ParseException("Default declarations must be #REQUIRED, #IMPLIED or #FIXED", parser.getLine(), parser.getCurrentToken());
      }

   }

   public String getValue() {
      return this.value;
   }

   public String toString() {
      if (this.required) {
         return "#REQUIRED";
      } else if (this.implied) {
         return "#IMPLIED";
      } else {
         return this.fixed ? "#FIXED \"" + this.value + "\"" : "\"" + this.value + "\"";
      }
   }
}
