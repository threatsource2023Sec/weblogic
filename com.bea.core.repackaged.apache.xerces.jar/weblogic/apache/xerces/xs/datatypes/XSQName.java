package weblogic.apache.xerces.xs.datatypes;

import weblogic.apache.xerces.xni.QName;

public interface XSQName {
   QName getXNIQName();

   javax.xml.namespace.QName getJAXPQName();
}
