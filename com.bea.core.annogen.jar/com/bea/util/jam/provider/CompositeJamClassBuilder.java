package com.bea.util.jam.provider;

import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MClass;
import org.apache.tools.ant.taskdefs.Javac;

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
      return this.build((Javac)null, pkg, cname);
   }

   public MClass build(Javac javacTask, String pkg, String cname) {
      MClass out = null;

      for(int i = 0; i < this.mBuilders.length; ++i) {
         out = this.mBuilders[i].build(javacTask, pkg, cname);
         if (out != null) {
            return out;
         }
      }

      return null;
   }
}
