package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.BrokerImplType;

public class BrokerImplTypeImpl extends XmlComplexContentImpl implements BrokerImplType {
   private static final long serialVersionUID = 1L;

   public BrokerImplTypeImpl(SchemaType sType) {
      super(sType);
   }
}
