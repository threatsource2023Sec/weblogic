package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FoxproDictionaryType;

public class FoxproDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements FoxproDictionaryType {
   private static final long serialVersionUID = 1L;

   public FoxproDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
