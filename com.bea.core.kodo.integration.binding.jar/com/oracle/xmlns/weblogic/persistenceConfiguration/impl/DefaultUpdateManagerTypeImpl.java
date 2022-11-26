package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultUpdateManagerType;

public class DefaultUpdateManagerTypeImpl extends UpdateManagerTypeImpl implements DefaultUpdateManagerType {
   private static final long serialVersionUID = 1L;

   public DefaultUpdateManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
