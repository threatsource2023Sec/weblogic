package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneProfilingType;

public class NoneProfilingTypeImpl extends XmlComplexContentImpl implements NoneProfilingType {
   private static final long serialVersionUID = 1L;

   public NoneProfilingTypeImpl(SchemaType sType) {
      super(sType);
   }
}
