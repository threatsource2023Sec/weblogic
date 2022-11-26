package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneJmxType;

public class NoneJmxTypeImpl extends XmlComplexContentImpl implements NoneJmxType {
   private static final long serialVersionUID = 1L;

   public NoneJmxTypeImpl(SchemaType sType) {
      super(sType);
   }
}
