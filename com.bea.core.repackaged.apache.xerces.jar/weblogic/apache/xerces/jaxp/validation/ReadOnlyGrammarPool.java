package weblogic.apache.xerces.jaxp.validation;

import weblogic.apache.xerces.xni.grammars.Grammar;
import weblogic.apache.xerces.xni.grammars.XMLGrammarDescription;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;

final class ReadOnlyGrammarPool implements XMLGrammarPool {
   private final XMLGrammarPool core;

   public ReadOnlyGrammarPool(XMLGrammarPool var1) {
      this.core = var1;
   }

   public void cacheGrammars(String var1, Grammar[] var2) {
   }

   public void clear() {
   }

   public void lockPool() {
   }

   public Grammar retrieveGrammar(XMLGrammarDescription var1) {
      return this.core.retrieveGrammar(var1);
   }

   public Grammar[] retrieveInitialGrammarSet(String var1) {
      return this.core.retrieveInitialGrammarSet(var1);
   }

   public void unlockPool() {
   }
}
