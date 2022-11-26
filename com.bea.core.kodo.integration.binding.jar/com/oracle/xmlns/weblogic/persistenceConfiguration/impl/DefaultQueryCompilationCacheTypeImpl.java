package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultQueryCompilationCacheType;

public class DefaultQueryCompilationCacheTypeImpl extends QueryCompilationCacheTypeImpl implements DefaultQueryCompilationCacheType {
   private static final long serialVersionUID = 1L;

   public DefaultQueryCompilationCacheTypeImpl(SchemaType sType) {
      super(sType);
   }
}
