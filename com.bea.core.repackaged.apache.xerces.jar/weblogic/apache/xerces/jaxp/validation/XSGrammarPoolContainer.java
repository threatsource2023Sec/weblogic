package weblogic.apache.xerces.jaxp.validation;

import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;

public interface XSGrammarPoolContainer {
   XMLGrammarPool getGrammarPool();

   boolean isFullyComposed();

   Boolean getFeature(String var1);
}
