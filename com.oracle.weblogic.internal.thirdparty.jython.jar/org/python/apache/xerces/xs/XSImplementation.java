package org.python.apache.xerces.xs;

import org.w3c.dom.ls.LSInput;

public interface XSImplementation {
   StringList getRecognizedVersions();

   XSLoader createXSLoader(StringList var1) throws XSException;

   StringList createStringList(String[] var1);

   LSInputList createLSInputList(LSInput[] var1);
}
