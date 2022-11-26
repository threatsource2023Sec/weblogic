package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneLockManagerType;

public class NoneLockManagerTypeImpl extends LockManagerTypeImpl implements NoneLockManagerType {
   private static final long serialVersionUID = 1L;

   public NoneLockManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
