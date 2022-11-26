package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SchemaFactoryType;

public class SchemaFactoryTypeImpl extends XmlComplexContentImpl implements SchemaFactoryType {
   private static final long serialVersionUID = 1L;

   public SchemaFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
