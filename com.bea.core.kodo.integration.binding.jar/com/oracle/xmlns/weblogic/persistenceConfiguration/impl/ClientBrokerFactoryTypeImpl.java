package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClientBrokerFactoryType;

public class ClientBrokerFactoryTypeImpl extends BrokerFactoryTypeImpl implements ClientBrokerFactoryType {
   private static final long serialVersionUID = 1L;

   public ClientBrokerFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
