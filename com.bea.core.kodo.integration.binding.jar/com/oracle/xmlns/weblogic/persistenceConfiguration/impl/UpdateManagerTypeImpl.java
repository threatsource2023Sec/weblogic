package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.UpdateManagerType;

public class UpdateManagerTypeImpl extends XmlComplexContentImpl implements UpdateManagerType {
   private static final long serialVersionUID = 1L;

   public UpdateManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
