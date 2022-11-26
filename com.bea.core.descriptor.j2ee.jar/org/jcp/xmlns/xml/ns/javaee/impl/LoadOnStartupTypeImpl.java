package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlInteger;
import org.jcp.xmlns.xml.ns.javaee.LoadOnStartupType;
import org.jcp.xmlns.xml.ns.javaee.NullCharType;

public class LoadOnStartupTypeImpl extends XmlUnionImpl implements LoadOnStartupType, NullCharType, XmlInteger {
   private static final long serialVersionUID = 1L;

   public LoadOnStartupTypeImpl(SchemaType sType) {
      super(sType, false);
   }

   protected LoadOnStartupTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
