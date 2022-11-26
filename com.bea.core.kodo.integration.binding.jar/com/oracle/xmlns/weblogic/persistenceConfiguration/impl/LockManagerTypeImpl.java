package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LockManagerType;

public class LockManagerTypeImpl extends XmlComplexContentImpl implements LockManagerType {
   private static final long serialVersionUID = 1L;

   public LockManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
