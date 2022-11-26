package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MappingFactoryType;

public class MappingFactoryTypeImpl extends XmlComplexContentImpl implements MappingFactoryType {
   private static final long serialVersionUID = 1L;

   public MappingFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
