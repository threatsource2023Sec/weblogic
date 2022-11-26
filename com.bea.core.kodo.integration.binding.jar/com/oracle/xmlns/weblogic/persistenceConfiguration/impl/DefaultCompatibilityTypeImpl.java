package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultCompatibilityType;

public class DefaultCompatibilityTypeImpl extends PersistenceCompatibilityTypeImpl implements DefaultCompatibilityType {
   private static final long serialVersionUID = 1L;

   public DefaultCompatibilityTypeImpl(SchemaType sType) {
      super(sType);
   }
}
