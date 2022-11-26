package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MetaDataFactoryType;

public class MetaDataFactoryTypeImpl extends XmlComplexContentImpl implements MetaDataFactoryType {
   private static final long serialVersionUID = 1L;

   public MetaDataFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }
}
