package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.BrokerFactoryType;

public class BrokerFactoryTypeImpl extends XmlComplexContentImpl implements BrokerFactoryType {
   private static final long serialVersionUID = 1L;

   public BrokerFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
