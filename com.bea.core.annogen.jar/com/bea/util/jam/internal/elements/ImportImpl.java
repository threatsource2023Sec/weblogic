package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JImport;
import com.bea.util.jam.visitor.JVisitor;
import com.bea.util.jam.visitor.MVisitor;

public final class ImportImpl extends ElementImpl implements JImport {
   private boolean staticImport = false;
   private String qName = null;

   public ImportImpl(ElementContext ctx, String qName, boolean staticImport) {
      super(ctx);
      if (qName == null) {
         throw new IllegalArgumentException("null qName");
      } else {
         this.staticImport = staticImport;
         this.qName = qName;
      }
   }

   public boolean isStaticImport() {
      return this.staticImport;
   }

   public boolean isStarEnd() {
      return this.getQualifiedName().endsWith("*");
   }

   public String getQualifiedName() {
      return this.qName;
   }

   public void accept(MVisitor visitor) {
   }

   public void accept(JVisitor visitor) {
   }
}
