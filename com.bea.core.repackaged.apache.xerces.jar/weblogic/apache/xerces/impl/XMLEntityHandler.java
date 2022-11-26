package weblogic.apache.xerces.impl;

import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XNIException;

public interface XMLEntityHandler {
   void startEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException;

   void endEntity(String var1, Augmentations var2) throws XNIException;
}
