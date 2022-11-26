package com.bea.xml_.impl.jam.internal;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JamClassIterator;
import com.bea.xml_.impl.jam.JamClassLoader;
import com.bea.xml_.impl.jam.JamService;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;

public class JamServiceImpl implements JamService {
   private ElementContext mContext;
   private String[] mClassNames;

   public JamServiceImpl(ElementContext ctx, String[] classes) {
      if (ctx == null) {
         throw new IllegalArgumentException("null jcl");
      } else if (classes == null) {
         throw new IllegalArgumentException("null classes");
      } else {
         this.mContext = ctx;
         this.mClassNames = classes;
      }
   }

   public JamClassLoader getClassLoader() {
      return this.mContext.getClassLoader();
   }

   public String[] getClassNames() {
      return this.mClassNames;
   }

   public JamClassIterator getClasses() {
      return new JamClassIterator(this.getClassLoader(), this.getClassNames());
   }

   public JClass[] getAllClasses() {
      JClass[] out = new JClass[this.mClassNames.length];

      for(int i = 0; i < out.length; ++i) {
         out[i] = this.getClassLoader().loadClass(this.mClassNames[i]);
      }

      return out;
   }

   public void setClassNames(String[] names) {
      this.mClassNames = names;
   }
}
