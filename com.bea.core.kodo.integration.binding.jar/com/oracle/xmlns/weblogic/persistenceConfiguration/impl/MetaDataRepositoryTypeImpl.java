package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MetaDataRepositoryType;

public class MetaDataRepositoryTypeImpl extends XmlComplexContentImpl implements MetaDataRepositoryType {
   private static final long serialVersionUID = 1L;

   public MetaDataRepositoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
