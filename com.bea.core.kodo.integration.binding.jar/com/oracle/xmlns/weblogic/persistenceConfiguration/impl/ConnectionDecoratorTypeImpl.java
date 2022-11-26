package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConnectionDecoratorType;

public class ConnectionDecoratorTypeImpl extends XmlComplexContentImpl implements ConnectionDecoratorType {
   private static final long serialVersionUID = 1L;

   public ConnectionDecoratorTypeImpl(SchemaType sType) {
      super(sType);
   }
}
