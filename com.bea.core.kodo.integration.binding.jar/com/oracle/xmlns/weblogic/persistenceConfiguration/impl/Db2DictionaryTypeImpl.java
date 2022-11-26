package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Db2DictionaryType;

public class Db2DictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements Db2DictionaryType {
   private static final long serialVersionUID = 1L;

   public Db2DictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
