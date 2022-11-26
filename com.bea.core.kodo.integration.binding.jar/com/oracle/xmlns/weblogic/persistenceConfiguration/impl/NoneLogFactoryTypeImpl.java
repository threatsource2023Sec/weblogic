package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneLogFactoryType;

public class NoneLogFactoryTypeImpl extends LogTypeImpl implements NoneLogFactoryType {
   private static final long serialVersionUID = 1L;

   public NoneLogFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
