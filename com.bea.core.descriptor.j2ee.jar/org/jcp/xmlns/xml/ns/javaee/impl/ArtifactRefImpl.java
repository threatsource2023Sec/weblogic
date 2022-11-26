package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import org.jcp.xmlns.xml.ns.javaee.ArtifactRef;

public class ArtifactRefImpl extends JavaStringHolderEx implements ArtifactRef {
   private static final long serialVersionUID = 1L;

   public ArtifactRefImpl(SchemaType sType) {
      super(sType, false);
   }

   protected ArtifactRefImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }
}
