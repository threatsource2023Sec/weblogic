package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClassResolverType;

public class ClassResolverTypeImpl extends XmlComplexContentImpl implements ClassResolverType {
   private static final long serialVersionUID = 1L;

   public ClassResolverTypeImpl(SchemaType sType) {
      super(sType);
   }
}
