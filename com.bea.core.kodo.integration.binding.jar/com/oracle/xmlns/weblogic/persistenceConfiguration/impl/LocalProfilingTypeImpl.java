package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LocalProfilingType;

public class LocalProfilingTypeImpl extends XmlComplexContentImpl implements LocalProfilingType {
   private static final long serialVersionUID = 1L;

   public LocalProfilingTypeImpl(SchemaType sType) {
      super(sType);
   }
}
