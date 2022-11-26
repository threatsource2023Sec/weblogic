package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultSavepointManagerType;

public class DefaultSavepointManagerTypeImpl extends SavepointManagerTypeImpl implements DefaultSavepointManagerType {
   private static final long serialVersionUID = 1L;

   public DefaultSavepointManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
