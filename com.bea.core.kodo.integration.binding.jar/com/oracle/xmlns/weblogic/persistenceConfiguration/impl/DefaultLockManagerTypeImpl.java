package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultLockManagerType;

public class DefaultLockManagerTypeImpl extends LockManagerTypeImpl implements DefaultLockManagerType {
   private static final long serialVersionUID = 1L;

   public DefaultLockManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
