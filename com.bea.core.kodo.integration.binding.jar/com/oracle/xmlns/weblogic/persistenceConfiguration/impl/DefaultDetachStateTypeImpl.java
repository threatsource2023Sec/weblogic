package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDetachStateType;

public class DefaultDetachStateTypeImpl extends DetachStateTypeImpl implements DefaultDetachStateType {
   private static final long serialVersionUID = 1L;

   public DefaultDetachStateTypeImpl(SchemaType sType) {
      super(sType);
   }
}
