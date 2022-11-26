package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DisabledQueryCacheType;

public class DisabledQueryCacheTypeImpl extends QueryCacheTypeImpl implements DisabledQueryCacheType {
   private static final long serialVersionUID = 1L;

   public DisabledQueryCacheTypeImpl(SchemaType sType) {
      super(sType);
   }
}
