package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultClassResolverType;

public class DefaultClassResolverTypeImpl extends ClassResolverTypeImpl implements DefaultClassResolverType {
   private static final long serialVersionUID = 1L;

   public DefaultClassResolverTypeImpl(SchemaType sType) {
      super(sType);
   }
}
