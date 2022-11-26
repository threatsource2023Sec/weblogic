package org.python.apache.xerces.xs.datatypes;

import org.python.apache.xerces.xni.QName;

public interface XSQName {
   QName getXNIQName();

   javax.xml.namespace.QName getJAXPQName();
}
