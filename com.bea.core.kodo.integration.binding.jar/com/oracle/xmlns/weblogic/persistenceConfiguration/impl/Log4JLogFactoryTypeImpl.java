package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Log4JLogFactoryType;

public class Log4JLogFactoryTypeImpl extends LogTypeImpl implements Log4JLogFactoryType {
   private static final long serialVersionUID = 1L;

   public Log4JLogFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
