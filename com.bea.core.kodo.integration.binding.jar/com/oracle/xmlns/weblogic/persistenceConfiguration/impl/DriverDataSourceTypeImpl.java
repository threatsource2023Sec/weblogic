package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DriverDataSourceType;

public class DriverDataSourceTypeImpl extends XmlComplexContentImpl implements DriverDataSourceType {
   private static final long serialVersionUID = 1L;

   public DriverDataSourceTypeImpl(SchemaType sType) {
      super(sType);
   }
}
