package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignDestinationType;

public class ForeignDestinationTypeImpl extends ForeignJndiObjectTypeImpl implements ForeignDestinationType {
   private static final long serialVersionUID = 1L;

   public ForeignDestinationTypeImpl(SchemaType sType) {
      super(sType);
   }
}
