package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LogType;

public class LogTypeImpl extends XmlComplexContentImpl implements LogType {
   private static final long serialVersionUID = 1L;

   public LogTypeImpl(SchemaType sType) {
      super(sType);
   }
}
