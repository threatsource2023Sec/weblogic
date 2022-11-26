package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;
import weblogic.xml.babel.scanner.Token;

public class ContentParticle extends Element {
   protected char delimeter = ' ';
   ArrayList children = new ArrayList();

   public void setDelimeter(char c) {
      this.delimeter = c;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      Sequence sequence = new Sequence();
      Choice choice = new Choice();
      Name name = new Name();
      new ContentParticle();
      Object currentParticle;
      switch (parser.getCurrentToken().tokenType) {
         case 0:
         case 18:
            name.parse(parser);
            currentParticle = name;
            break;
         case 56:
            choice.parse(parser);
            currentParticle = choice;
            break;
         case 57:
            sequence.parse(parser);
            currentParticle = sequence;
            break;
         default:
            throw new ParseException("Expected a NAME, CHOICE, or SEQUENCE", parser.getLine(), parser.getCurrentToken());
      }

      this.parseOperator(parser, (ContentParticle)currentParticle);
   }

   public void parseOperator(BaseParser parser, ContentParticle currentParticle) throws IOException, ScannerException, ParseException {
      switch (parser.getCurrentToken().tokenType) {
         case 32:
            Option option = new Option();
            option.parse(parser);
            option.addChild(currentParticle);
            this.addChild(option);
            break;
         case 33:
            WildCard wildCard = new WildCard();
            wildCard.parse(parser);
            wildCard.addChild(currentParticle);
            this.addChild(wildCard);
            break;
         case 34:
            OneOrMore oneOrMore = new OneOrMore();
            oneOrMore.parse(parser);
            oneOrMore.addChild(currentParticle);
            this.addChild(oneOrMore);
            break;
         default:
            this.addChild(currentParticle);
      }

   }

   public void getChildren(ArrayList starters) {
      Iterator i = this.children.iterator();

      while(i.hasNext()) {
         ContentParticle cp = (ContentParticle)i.next();
         cp.getChildren(starters);
      }

   }

   protected void init() {
      super.init();
      this.children.clear();
   }

   public void addChild(ContentParticle cp) {
      this.children.add(cp);
   }

   public Iterator getIterator() {
      return this.children.iterator();
   }

   public boolean hasChildren() {
      return this.children.size() > 0;
   }

   public static boolean isStarter(Token token) {
      switch (token.tokenType) {
         case 0:
         case 18:
         case 56:
         case 57:
            return true;
         default:
            return false;
      }
   }

   public ContentParticle getFirstChild() {
      return (ContentParticle)this.children.get(0);
   }

   public String toString() {
      String val = "";
      if (this.children.size() == 1) {
         val = this.getFirstChild().toString();
      } else {
         val = "( ";
         Iterator i = this.children.iterator();
         ContentParticle cp;
         if (i.hasNext()) {
            cp = (ContentParticle)i.next();
            val = val + cp.toString();
         }

         while(i.hasNext()) {
            cp = (ContentParticle)i.next();
            val = val + " " + this.delimeter + " " + cp.toString();
         }

         val = val + " )";
      }

      return val;
   }
}
