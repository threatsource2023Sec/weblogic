package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceServerType;

public class PersistenceServerTypeImpl extends XmlComplexContentImpl implements PersistenceServerType {
   private static final long serialVersionUID = 1L;

   public PersistenceServerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
