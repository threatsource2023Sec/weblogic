package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SavepointManagerType;

public class SavepointManagerTypeImpl extends XmlComplexContentImpl implements SavepointManagerType {
   private static final long serialVersionUID = 1L;

   public SavepointManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
