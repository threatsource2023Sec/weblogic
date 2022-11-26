package org.python.apache.xerces.impl;

import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.parser.XMLDocumentFilter;

public interface RevalidationHandler extends XMLDocumentFilter {
   boolean characterData(String var1, Augmentations var2);
}
