package org.python.apache.xerces.impl;

import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XNIException;

public interface XMLEntityHandler {
   void startEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException;

   void endEntity(String var1, Augmentations var2) throws XNIException;
}
