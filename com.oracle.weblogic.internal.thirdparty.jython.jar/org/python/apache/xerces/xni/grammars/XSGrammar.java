package org.python.apache.xerces.xni.grammars;

import org.python.apache.xerces.xs.XSModel;

public interface XSGrammar extends Grammar {
   XSModel toXSModel();

   XSModel toXSModel(XSGrammar[] var1);
}
