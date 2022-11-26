package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DynamicSchemaFactoryType;

public class DynamicSchemaFactoryTypeImpl extends SchemaFactoryTypeImpl implements DynamicSchemaFactoryType {
   private static final long serialVersionUID = 1L;

   public DynamicSchemaFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
