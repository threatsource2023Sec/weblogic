package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdbcBrokerFactoryType;

public class JdbcBrokerFactoryTypeImpl extends BrokerFactoryTypeImpl implements JdbcBrokerFactoryType {
   private static final long serialVersionUID = 1L;

   public JdbcBrokerFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
