package org.apache.xmlbeans.impl.jam.provider;

import org.apache.xmlbeans.impl.jam.internal.elements.ClassImpl;
import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;
import org.apache.xmlbeans.impl.jam.mutable.MClass;

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
      return this.mContext;
   }

   protected final void assertInitialized() {
      if (this.mContext == null) {
         throw new IllegalStateException(this + " not yet initialized.");
      }
   }
}
