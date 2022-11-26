package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDriverDataSourceType;

public class DefaultDriverDataSourceTypeImpl extends DriverDataSourceTypeImpl implements DefaultDriverDataSourceType {
   private static final long serialVersionUID = 1L;

   public DefaultDriverDataSourceTypeImpl(SchemaType sType) {
      super(sType);
   }
}
