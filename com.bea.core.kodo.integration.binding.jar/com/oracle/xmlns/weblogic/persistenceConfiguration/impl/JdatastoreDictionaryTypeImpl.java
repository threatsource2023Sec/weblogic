package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdatastoreDictionaryType;

public class JdatastoreDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements JdatastoreDictionaryType {
   private static final long serialVersionUID = 1L;

   public JdatastoreDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
