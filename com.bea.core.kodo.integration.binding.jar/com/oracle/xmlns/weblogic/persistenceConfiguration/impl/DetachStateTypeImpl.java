package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DetachStateType;

public class DetachStateTypeImpl extends XmlComplexContentImpl implements DetachStateType {
   private static final long serialVersionUID = 1L;

   public DetachStateTypeImpl(SchemaType sType) {
      super(sType);
   }
}
