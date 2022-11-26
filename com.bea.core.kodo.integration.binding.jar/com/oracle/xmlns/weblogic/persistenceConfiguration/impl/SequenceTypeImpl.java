package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SequenceType;

public class SequenceTypeImpl extends XmlComplexContentImpl implements SequenceType {
   private static final long serialVersionUID = 1L;

   public SequenceTypeImpl(SchemaType sType) {
      super(sType);
   }
}
