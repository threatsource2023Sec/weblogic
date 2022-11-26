package weblogic.xml.babel.baseparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.xml.babel.scanner.ScannerException;

public final class StartElement extends Element {
   ArrayList attributes = new ArrayList();
   boolean declaresNameSpace;
   boolean declaresDefaultNameSpace;

   public void addAttribute(Attribute attribute) {
      this.attributes.add(attribute);
   }

   public List getAttributes() {
      return this.attributes;
   }

   StartElement() {
   }

   protected void init() {
      super.init();
      this.attributes.clear();
      this.declaresNameSpace = false;
      this.declaresDefaultNameSpace = false;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.parse(parser, -1);
   }

   public void parse(BaseParser parser, int maxAttrs) throws IOException, ScannerException, ParseException {
      this.init();
      this.setPosition(parser);
      parser.accept(1);
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

      int attrCnt = 0;

      while(!parser.compare(2) && !parser.compare(5)) {
         if (attrCnt == maxAttrs) {
            throw new ParserConstraintException("Maximum attribute count of " + maxAttrs + " exceeded for element '" + this.getName() + "'");
         }

         ++attrCnt;
         Attribute attribute = new Attribute();
         attribute.parse(parser);
         this.addAttribute(attribute);
         if (attribute.isNameSpaceDeclaration()) {
            this.declaresNameSpace = true;
         }

         if (attribute.declaresDefaultNameSpace()) {
            this.declaresDefaultNameSpace = true;
         }
      }

      switch (parser.currentToken.tokenType) {
         case 2:
            this.type = 0;
            parser.accept();
            break;
         case 5:
            this.type = 1;
            parser.accept();
            break;
         default:
            throw new ParseException("Expected a > or />", parser.getLine(), parser.currentToken);
      }

      if (!this.setNameSpace(parser)) {
         this.setDefaultNameSpace(parser);
      }

      this.setAttributesNameSpaces(parser);
   }

   private void setAttributesNameSpaces(BaseParser parser) throws IOException, ScannerException, ParseException {
      int i = 0;

      for(int len = this.attributes.size(); i < len; ++i) {
         Attribute attribute = (Attribute)this.attributes.get(i);
         attribute.setNameSpace(parser);
      }

   }

   public boolean declaresNameSpace() {
      return this.declaresNameSpace;
   }

   public boolean declaresDefaultNameSpace() {
      return this.declaresDefaultNameSpace;
   }

   public String toString() {
      String s = "<";
      s = s + this.getName();

      Attribute attribute;
      for(Iterator i = this.attributes.iterator(); i.hasNext(); s = s + attribute.toString()) {
         attribute = (Attribute)i.next();
      }

      if (this.type == 0) {
         s = s + ">";
      } else {
         s = s + "/>";
      }

      return s;
   }
}
