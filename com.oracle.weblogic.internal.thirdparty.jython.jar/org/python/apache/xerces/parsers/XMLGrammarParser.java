package org.python.apache.xerces.parsers;

import org.python.apache.xerces.impl.dv.DTDDVFactory;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.xni.parser.XMLParserConfiguration;

public abstract class XMLGrammarParser extends XMLParser {
   protected DTDDVFactory fDatatypeValidatorFactory;

   protected XMLGrammarParser(SymbolTable var1) {
      super((XMLParserConfiguration)ObjectFactory.createObject("org.python.apache.xerces.xni.parser.XMLParserConfiguration", "org.python.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
   }
}
