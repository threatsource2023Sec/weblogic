package org.python.apache.xerces.parsers;

import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;
import org.python.apache.xerces.xni.parser.XMLParserConfiguration;

public class XMLDocumentParser extends AbstractXMLDocumentParser {
   public XMLDocumentParser() {
      super((XMLParserConfiguration)ObjectFactory.createObject("org.python.apache.xerces.xni.parser.XMLParserConfiguration", "org.python.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
   }

   public XMLDocumentParser(XMLParserConfiguration var1) {
      super(var1);
   }

   public XMLDocumentParser(SymbolTable var1) {
      super((XMLParserConfiguration)ObjectFactory.createObject("org.python.apache.xerces.xni.parser.XMLParserConfiguration", "org.python.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
   }

   public XMLDocumentParser(SymbolTable var1, XMLGrammarPool var2) {
      super((XMLParserConfiguration)ObjectFactory.createObject("org.python.apache.xerces.xni.parser.XMLParserConfiguration", "org.python.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", var2);
   }
}
