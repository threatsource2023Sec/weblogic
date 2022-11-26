package com.bea.util.jam.internal;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassIterator;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamService;
import com.bea.util.jam.internal.elements.ElementContext;
import org.apache.tools.ant.taskdefs.Javac;

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

   public JClass[] getAllClasses(Javac javacTask) {
      JClass[] out = new JClass[this.mClassNames.length];

      for(int i = 0; i < out.length; ++i) {
         out[i] = this.getClassLoader().loadClass(javacTask, this.mClassNames[i]);
      }

      return out;
   }

   public void setClassNames(String[] names) {
      this.mClassNames = names;
   }
}
