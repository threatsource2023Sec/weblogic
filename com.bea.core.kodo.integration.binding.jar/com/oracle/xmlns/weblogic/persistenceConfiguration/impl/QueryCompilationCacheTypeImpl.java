package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.QueryCompilationCacheType;

public class QueryCompilationCacheTypeImpl extends XmlComplexContentImpl implements QueryCompilationCacheType {
   private static final long serialVersionUID = 1L;

   public QueryCompilationCacheTypeImpl(SchemaType sType) {
      super(sType);
   }
}
