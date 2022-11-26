package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AggregateListenerType;

public class AggregateListenerTypeImpl extends XmlComplexContentImpl implements AggregateListenerType {
   private static final long serialVersionUID = 1L;

   public AggregateListenerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
