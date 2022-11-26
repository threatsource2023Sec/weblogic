package org.glassfish.hk2.xml.spi;

import java.io.Serializable;
import javax.xml.namespace.QName;

public interface Model extends Serializable {
   String getOriginalInterface();

   Class getOriginalInterfaceAsClass();

   String getTranslatedClass();

   QName getRootName();

   QName getKeyProperty();

   Class getProxyAsClass();
}
