package org.apache.xmlbeans.impl.jam.provider;

import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;
import org.apache.xmlbeans.impl.jam.mutable.MClass;

public class CompositeJamClassBuilder extends JamClassBuilder {
   private JamClassBuilder[] mBuilders;

   public CompositeJamClassBuilder(JamClassBuilder[] builders) {
      if (builders == null) {
         throw new IllegalArgumentException("null builders");
      } else {
         this.mBuilders = builders;
      }
   }

   public void init(ElementContext ctx) {
      for(int i = 0; i < this.mBuilders.length; ++i) {
         this.mBuilders[i].init(ctx);
      }

   }

   public MClass build(String pkg, String cname) {
      MClass out = null;

      for(int i = 0; i < this.mBuilders.length; ++i) {
         out = this.mBuilders[i].build(pkg, cname);
         if (out != null) {
            return out;
         }
      }

      return null;
   }
}
