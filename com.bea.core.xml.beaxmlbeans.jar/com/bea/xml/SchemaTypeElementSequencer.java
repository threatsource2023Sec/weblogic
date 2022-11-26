package com.bea.xml;

import javax.xml.namespace.QName;

public interface SchemaTypeElementSequencer {
   boolean next(QName var1);

   boolean peek(QName var1);
}
