package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomMappingFactoryType;

public class CustomMappingFactoryTypeImpl extends MappingFactoryTypeImpl implements CustomMappingFactoryType {
   private static final long serialVersionUID = 1L;

   public CustomMappingFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
