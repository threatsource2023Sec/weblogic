package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DataCacheManagerType;

public class DataCacheManagerTypeImpl extends XmlComplexContentImpl implements DataCacheManagerType {
   private static final long serialVersionUID = 1L;

   public DataCacheManagerTypeImpl(SchemaType sType) {
      super(sType);
   }
}
