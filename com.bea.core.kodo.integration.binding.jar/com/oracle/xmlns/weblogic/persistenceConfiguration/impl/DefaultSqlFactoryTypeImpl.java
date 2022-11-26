package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultSqlFactoryType;

public class DefaultSqlFactoryTypeImpl extends SqlFactoryTypeImpl implements DefaultSqlFactoryType {
   private static final long serialVersionUID = 1L;

   public DefaultSqlFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
