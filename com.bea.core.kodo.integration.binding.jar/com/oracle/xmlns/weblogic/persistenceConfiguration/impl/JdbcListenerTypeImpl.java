package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdbcListenerType;

public class JdbcListenerTypeImpl extends XmlComplexContentImpl implements JdbcListenerType {
   private static final long serialVersionUID = 1L;

   public JdbcListenerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
