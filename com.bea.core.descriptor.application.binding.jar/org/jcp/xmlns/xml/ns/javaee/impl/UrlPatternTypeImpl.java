package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.UrlPatternType;

public class UrlPatternTypeImpl extends JavaStringHolderEx implements UrlPatternType {
   private static final long serialVersionUID = 1L;

   public UrlPatternTypeImpl(SchemaType sType) {
      super(sType, true);
   }

   protected UrlPatternTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
