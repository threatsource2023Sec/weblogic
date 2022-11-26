package org.python.apache.xerces.impl.dtd;

import org.python.apache.xerces.impl.XML11DTDScannerImpl;
import org.python.apache.xerces.impl.XMLDTDScannerImpl;
import org.python.apache.xerces.impl.XMLEntityManager;
import org.python.apache.xerces.impl.XMLErrorReporter;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XML11Char;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;
import org.python.apache.xerces.xni.parser.XMLEntityResolver;

public class XML11DTDProcessor extends XMLDTDLoader {
   public XML11DTDProcessor() {
   }

   public XML11DTDProcessor(SymbolTable var1) {
      super(var1);
   }

   public XML11DTDProcessor(SymbolTable var1, XMLGrammarPool var2) {
      super(var1, var2);
   }

   XML11DTDProcessor(SymbolTable var1, XMLGrammarPool var2, XMLErrorReporter var3, XMLEntityResolver var4) {
      super(var1, var2, var3, var4);
   }

   protected boolean isValidNmtoken(String var1) {
      return XML11Char.isXML11ValidNmtoken(var1);
   }

   protected boolean isValidName(String var1) {
      return XML11Char.isXML11ValidName(var1);
   }

   protected XMLDTDScannerImpl createDTDScanner(SymbolTable var1, XMLErrorReporter var2, XMLEntityManager var3) {
      return new XML11DTDScannerImpl(var1, var2, var3);
   }

   protected short getScannerVersion() {
      return 2;
   }
}
