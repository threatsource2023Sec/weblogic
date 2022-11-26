package weblogic.apache.xerces.xni.grammars;

import weblogic.apache.xerces.xs.XSModel;

public interface XSGrammar extends Grammar {
   XSModel toXSModel();

   XSModel toXSModel(XSGrammar[] var1);
}
