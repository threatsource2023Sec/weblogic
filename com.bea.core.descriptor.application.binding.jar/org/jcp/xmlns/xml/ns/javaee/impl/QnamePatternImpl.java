package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.QnamePattern;

public class QnamePatternImpl extends JavaStringHolderEx implements QnamePattern {
   private static final long serialVersionUID = 1L;

   public QnamePatternImpl(SchemaType sType) {
      super(sType, false);
   }

   protected QnamePatternImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
