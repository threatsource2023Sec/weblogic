package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MappingDefaultsType;

public class MappingDefaultsTypeImpl extends XmlComplexContentImpl implements MappingDefaultsType {
   private static final long serialVersionUID = 1L;

   public MappingDefaultsTypeImpl(SchemaType sType) {
      super(sType);
   }
}
