package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.SafQueueType;

public class SafQueueTypeImpl extends SafDestinationTypeImpl implements SafQueueType {
   private static final long serialVersionUID = 1L;

   public SafQueueTypeImpl(SchemaType sType) {
      super(sType);
   }
}
