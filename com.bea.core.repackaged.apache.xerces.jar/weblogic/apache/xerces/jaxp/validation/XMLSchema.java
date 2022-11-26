package weblogic.apache.xerces.jaxp.validation;

import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;

final class XMLSchema extends AbstractXMLSchema {
   private final XMLGrammarPool fGrammarPool;
   private final boolean fFullyComposed;

   public XMLSchema(XMLGrammarPool var1) {
      this(var1, true);
   }

   public XMLSchema(XMLGrammarPool var1, boolean var2) {
      this.fGrammarPool = var1;
      this.fFullyComposed = var2;
   }

   public XMLGrammarPool getGrammarPool() {
      return this.fGrammarPool;
   }

   public boolean isFullyComposed() {
      return this.fFullyComposed;
   }
}
