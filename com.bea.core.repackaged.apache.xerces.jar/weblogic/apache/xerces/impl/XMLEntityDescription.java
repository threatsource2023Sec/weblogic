package weblogic.apache.xerces.impl;

import weblogic.apache.xerces.xni.XMLResourceIdentifier;

public interface XMLEntityDescription extends XMLResourceIdentifier {
   void setEntityName(String var1);

   String getEntityName();
}
