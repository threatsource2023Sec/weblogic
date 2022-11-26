package org.python.apache.xerces.impl;

import org.python.apache.xerces.xni.XMLResourceIdentifier;

public interface XMLEntityDescription extends XMLResourceIdentifier {
   void setEntityName(String var1);

   String getEntityName();
}
