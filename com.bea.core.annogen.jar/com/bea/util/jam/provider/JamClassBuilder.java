package com.bea.util.jam.provider;

import com.bea.util.jam.internal.elements.ClassImpl;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MClass;
import org.apache.tools.ant.taskdefs.Javac;

public abstract class JamClassBuilder {
   private ElementContext mContext = null;

   public void init(ElementContext ctx) {
      if (this.mContext != null) {
         throw new IllegalStateException("init called more than once");
      } else if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else {
         this.mContext = ctx;
      }
   }

   public abstract MClass build(String var1, String var2);

   public abstract MClass build(Javac var1, String var2, String var3);

   protected MClass createClassToBuild(String packageName, String className, String[] importSpecs, JamClassPopulator pop) {
      if (this.mContext == null) {
         throw new IllegalStateException("init not called");
      } else if (packageName == null) {
         throw new IllegalArgumentException("null pkg");
      } else if (className == null) {
         throw new IllegalArgumentException("null class");
      } else if (pop == null) {
         throw new IllegalArgumentException("null pop");
      } else {
         this.assertInitialized();
         className = className.replace('.', '$');
         ClassImpl out = new ClassImpl(packageName, className, this.mContext, importSpecs, pop);
         return out;
      }
   }

   protected MClass createClassToBuild(String packageName, String className, String[] importSpecs) {
      if (this.mContext == null) {
         throw new IllegalStateException("init not called");
      } else if (packageName == null) {
         throw new IllegalArgumentException("null pkg");
      } else if (className == null) {
         throw new IllegalArgumentException("null class");
      } else {
         this.assertInitialized();
         className = className.replace('.', '$');
         ClassImpl out = new ClassImpl(packageName, className, this.mContext, importSpecs);
         return out;
      }
   }

   protected JamLogger getLogger() {
      return this.mContext.getLogger();
   }

   protected final void assertInitialized() {
      if (this.mContext == null) {
         throw new IllegalStateException(this + " not yet initialized.");
      }
   }
}
