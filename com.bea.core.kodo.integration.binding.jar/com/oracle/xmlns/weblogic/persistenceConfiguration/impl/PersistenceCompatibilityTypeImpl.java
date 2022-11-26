package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceCompatibilityType;

public class PersistenceCompatibilityTypeImpl extends XmlComplexContentImpl implements PersistenceCompatibilityType {
   private static final long serialVersionUID = 1L;

   public PersistenceCompatibilityTypeImpl(SchemaType sType) {
      super(sType);
   }
}
