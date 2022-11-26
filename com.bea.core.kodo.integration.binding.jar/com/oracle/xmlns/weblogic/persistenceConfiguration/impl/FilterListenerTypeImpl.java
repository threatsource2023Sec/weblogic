package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FilterListenerType;

public class FilterListenerTypeImpl extends XmlComplexContentImpl implements FilterListenerType {
   private static final long serialVersionUID = 1L;

   public FilterListenerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
