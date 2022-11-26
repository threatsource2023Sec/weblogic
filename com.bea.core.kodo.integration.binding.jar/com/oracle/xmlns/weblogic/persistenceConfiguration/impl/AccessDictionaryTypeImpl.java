package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AccessDictionaryType;

public class AccessDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements AccessDictionaryType {
   private static final long serialVersionUID = 1L;

   public AccessDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
