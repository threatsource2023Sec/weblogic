package org.apache.xmlbeans;

import javax.xml.namespace.QName;

public interface UserType {
   QName getName();

   String getJavaName();

   String getStaticHandler();
}
