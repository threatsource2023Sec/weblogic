package weblogic.xml.babel.dtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class ElementDeclaration extends Declaration {
   private Map attributeDeclarations;
   private List defaultAttributeDeclarations;
   private boolean empty;
   private boolean any;
   private boolean mixed;
   private boolean hasChildren;
   private Children children;
   private Name name;

   public ElementDeclaration() {
      this.init();
   }

   public ElementDeclaration(String localName) {
      this.init();
      this.name.setLocalName(localName);
      this.any = true;
   }

   public void init() {
      super.init();
      this.name = new Name();
      this.children = new Children();
      this.hasChildren = false;
      this.mixed = false;
      this.any = false;
      this.empty = false;
   }

   public boolean hasAttributeDefinitions() {
      return this.attributeDeclarations != null;
   }

   public boolean hasDefaultAttributeDeclarations() {
      return this.defaultAttributeDeclarations != null;
   }

   public void addAttributeList(AttributeListDeclaration attl) {
      Iterator i = attl.getDefinitions().iterator();

      while(i.hasNext()) {
         this.addAttributeDefinition((AttributeDefinition)i.next());
      }

   }

   public void addAttributeDefinition(AttributeDefinition att) {
      if (this.attributeDeclarations == null) {
         this.attributeDeclarations = new HashMap();
      }

      this.attributeDeclarations.put(att.getRawName(), att);
      if (att.hasDefault()) {
         this.addDefaultAttributeDefinition(att);
      }

   }

   public List getDefaultAttributes() {
      return this.defaultAttributeDeclarations;
   }

   public AttributeDefinition getAttributeDefinition(String name) {
      return (AttributeDefinition)this.attributeDeclarations.get(name);
   }

   public void addDefaultAttributeDefinition(AttributeDefinition att) {
      if (this.defaultAttributeDeclarations == null) {
         this.defaultAttributeDeclarations = new ArrayList();
      }

      this.defaultAttributeDeclarations.add(att);
   }

   public boolean isMixed() {
      return this.mixed;
   }

   public boolean isAny() {
      return this.any;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public void parse(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.init();
      parser.accept(38);
      this.name.parse(parser);
      switch (parser.getCurrentToken().tokenType) {
         case 28:
            this.empty = true;
            parser.accept();
            break;
         case 29:
            this.any = true;
            parser.accept();
            break;
         case 59:
            this.mixed = true;
            this.hasChildren = true;
            break;
         default:
            this.hasChildren = true;
            this.children.parse(parser);
      }

      parser.accept(39);
      this.type = 10;
   }

   public Children getChildren() {
      return this.children;
   }

   private String wrap(String s) {
      return "<!ELEMENT " + s + ">";
   }

   public String toString() {
      if (this.empty) {
         return this.wrap(this.name + " EMPTY");
      } else {
         return this.any ? this.wrap(this.name + " ANY") : this.wrap(this.name + " " + this.children);
      }
   }

   public String getRawName() {
      return this.name.getRawName();
   }
}
