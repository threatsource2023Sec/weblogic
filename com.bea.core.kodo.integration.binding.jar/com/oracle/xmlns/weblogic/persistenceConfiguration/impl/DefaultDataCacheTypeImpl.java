package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDataCacheType;

public class DefaultDataCacheTypeImpl extends DataCacheTypeImpl implements DefaultDataCacheType {
   private static final long serialVersionUID = 1L;

   public DefaultDataCacheTypeImpl(SchemaType sType) {
      super(sType);
   }
}
