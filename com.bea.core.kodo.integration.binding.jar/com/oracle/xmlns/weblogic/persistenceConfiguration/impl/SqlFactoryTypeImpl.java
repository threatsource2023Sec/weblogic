package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SqlFactoryType;

public class SqlFactoryTypeImpl extends XmlComplexContentImpl implements SqlFactoryType {
   private static final long serialVersionUID = 1L;

   public SqlFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
