package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PointbaseDictionaryType;

public class PointbaseDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements PointbaseDictionaryType {
   private static final long serialVersionUID = 1L;

   public PointbaseDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
