package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultMetaDataRepositoryType;

public class DefaultMetaDataRepositoryTypeImpl extends MetaDataRepositoryTypeImpl implements DefaultMetaDataRepositoryType {
   private static final long serialVersionUID = 1L;

   public DefaultMetaDataRepositoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
