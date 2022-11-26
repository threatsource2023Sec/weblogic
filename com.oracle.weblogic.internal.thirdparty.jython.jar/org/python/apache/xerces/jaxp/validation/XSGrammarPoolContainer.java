package org.python.apache.xerces.jaxp.validation;

import org.python.apache.xerces.xni.grammars.XMLGrammarPool;

public interface XSGrammarPoolContainer {
   XMLGrammarPool getGrammarPool();

   boolean isFullyComposed();

   Boolean getFeature(String var1);
}
