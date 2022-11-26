package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SafTopicType;

public class SafTopicTypeImpl extends SafDestinationTypeImpl implements SafTopicType {
   private static final long serialVersionUID = 1L;

   public SafTopicTypeImpl(SchemaType sType) {
      super(sType);
   }
}
