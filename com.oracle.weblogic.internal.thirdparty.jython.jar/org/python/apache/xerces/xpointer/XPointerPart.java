package org.python.apache.xerces.xpointer;

import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLAttributes;
import org.python.apache.xerces.xni.XNIException;

public interface XPointerPart {
   int EVENT_ELEMENT_START = 0;
   int EVENT_ELEMENT_END = 1;
   int EVENT_ELEMENT_EMPTY = 2;

   void parseXPointer(String var1) throws XNIException;

   boolean resolveXPointer(QName var1, XMLAttributes var2, Augmentations var3, int var4) throws XNIException;

   boolean isFragmentResolved() throws XNIException;

   boolean isChildFragmentResolved() throws XNIException;

   String getSchemeName();

   String getSchemeData();

   void setSchemeName(String var1);

   void setSchemeData(String var1);
}
