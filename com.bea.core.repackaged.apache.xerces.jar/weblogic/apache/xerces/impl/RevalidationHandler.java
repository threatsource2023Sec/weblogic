package weblogic.apache.xerces.impl;

import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.parser.XMLDocumentFilter;

public interface RevalidationHandler extends XMLDocumentFilter {
   boolean characterData(String var1, Augmentations var2);
}
