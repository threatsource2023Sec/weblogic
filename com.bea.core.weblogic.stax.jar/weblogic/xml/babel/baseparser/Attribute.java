package weblogic.xml.babel.baseparser;

import java.io.IOException;
import org.xml.sax.SAXException;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;

public class Attribute extends Element {
   UnsyncStringBuffer value;
   boolean isXMLNS;
   boolean declaresDefaultNameSpace;
   private int type = 11;

   public void init() {
      super.init();
      this.value = new UnsyncStringBuffer();
      this.isXMLNS = false;
      this.declaresDefaultNameSpace = false;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      switch (parser.currentToken.tokenType) {
         case 0:
            this.name = parser.currentToken.text;
            if (isNameSpace(this.name)) {
               this.declaresDefaultNameSpace = true;
            }

            parser.accept();
            break;
         case 18:
            this.prefix = parser.currentToken.text;
            if (isNameSpace(this.prefix)) {
               this.isXMLNS = true;
            }

            parser.accept();
            this.name = parser.currentToken.text;
            parser.accept(0);
            break;
         default:
            throw new ParseException("Expected a NAME", parser.getLine(), parser.currentToken);
      }

      for(; parser.compare(13) || parser.compare(14) || parser.compare(15) || parser.compare(19); parser.accept()) {
         if (parser.compare(15)) {
            this.value.append(parser.currentToken.text);
         } else if (parser.compare(14)) {
            this.value.append(this.normalizeReferenceSpace(parser.currentToken.text));
         } else if (parser.compare(19)) {
            this.value.append(" ");
         } else {
            this.value.append(parser.currentToken.textArray, parser.currentToken.start, parser.currentToken.length);
         }
      }

      try {
         if (this.isXMLNS) {
            parser.putNamespaceURI(this.name, this.value.toString());
         }

         if (this.declaresDefaultNameSpace) {
            parser.setDefaultNameSpace(this.value.toString());
         }

      } catch (SAXException var3) {
         throw new ParseException(var3.getMessage());
      }
   }

   private String normalizeReferenceSpace(String text) {
      char[] val = text.toCharArray();
      char[] newVal = new char[val.length];

      for(int i = 0; i < val.length; ++i) {
         if (val[i] != '\t' && val[i] != '\r' && val[i] != '\n') {
            newVal[i] = val[i];
         } else {
            newVal[i] = ' ';
         }
      }

      return new String(newVal);
   }

   public String normalizeSpace(String text) {
      char[] val = text.toCharArray();
      int oldLength = val.length;
      char[] newVal = new char[oldLength];
      int newLength = 0;

      int i;
      for(i = 0; i < oldLength && val[i] == ' '; ++i) {
      }

      for(; i < oldLength; ++newLength) {
         boolean gotSpace;
         for(gotSpace = false; i < oldLength && val[i] == ' '; gotSpace = true) {
            ++i;
         }

         if (gotSpace) {
            newVal[newLength] = ' ';
         } else {
            newVal[newLength] = val[i];
            ++i;
         }
      }

      if (newLength > 0) {
         while(newVal[newLength - 1] == ' ') {
            --newLength;
         }
      }

      return new String(newVal, 0, newLength);
   }

   static boolean isNameSpace(String text) {
      return "xmlns".equals(text);
   }

   public boolean isNameSpaceDeclaration() {
      return this.isXMLNS;
   }

   public boolean declaresDefaultNameSpace() {
      return this.declaresDefaultNameSpace;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getType() {
      return this.type;
   }

   public void setValue(String v) {
      this.value.setLength(0);
      this.value.append(v);
   }

   public String getValue() {
      return this.value.toString();
   }

   protected boolean setNameSpace(BaseParser parser) throws IOException, ScannerException, ParseException {
      return !this.isXMLNS && !this.declaresDefaultNameSpace ? super.setNameSpace(parser) : false;
   }

   public String toString() {
      return " " + this.getName() + "[" + Token.getString(this.type) + "]=\"" + this.value + "\"";
   }
}
