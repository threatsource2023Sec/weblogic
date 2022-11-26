package com.bea.util.annogen.override.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.override.AnnoContext;
import com.bea.util.jam.provider.JamLogger;
import java.util.HashMap;
import java.util.Map;

public class AnnoBeanSetImpl implements AnnoBeanSet {
   private Map mBeanClass2AnnoClass = new HashMap();
   private JamLogger mLogger = null;
   private AnnoContext mContext = null;

   public AnnoBeanSetImpl(AnnoContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException();
      } else {
         this.mLogger = ctx.getLogger();
         this.mContext = ctx;
      }
   }

   public boolean containsBeanFor(Class requestedClass) {
      Class beanClass;
      try {
         beanClass = this.mContext.getAnnobeanClassFor(requestedClass);
      } catch (ClassNotFoundException var4) {
         this.mLogger.verbose((Throwable)var4, this);
         return false;
      }

      return this.mBeanClass2AnnoClass.containsKey(beanClass);
   }

   public AnnoBean findOrCreateBeanFor(Class requestedClass) {
      Class beanClass;
      try {
         beanClass = this.mContext.getAnnobeanClassFor(requestedClass);
      } catch (ClassNotFoundException var4) {
         this.mLogger.verbose((Throwable)var4, this);
         return null;
      }

      AnnoBean ap = (AnnoBean)this.mBeanClass2AnnoClass.get(beanClass);
      if (ap != null) {
         return ap;
      } else {
         ap = this.mContext.createAnnoBeanFor(beanClass);
         if (ap != null) {
            this.mBeanClass2AnnoClass.put(beanClass, ap);
            return ap;
         } else {
            return null;
         }
      }
   }

   public void put(AnnoBean bean) {
      if (bean == null) {
         throw new IllegalArgumentException();
      } else {
         this.mBeanClass2AnnoClass.put(bean.getClass(), bean);
      }
   }

   public AnnoBean removeBeanFor(Class requestedClass) {
      Class beanClass;
      try {
         beanClass = this.mContext.getAnnobeanClassFor(requestedClass);
      } catch (ClassNotFoundException var4) {
         this.mLogger.verbose((Throwable)var4, this);
         return null;
      }

      return (AnnoBean)this.mBeanClass2AnnoClass.remove(beanClass);
   }

   public AnnoBean[] getAll() {
      AnnoBean[] out = new AnnoBean[this.mBeanClass2AnnoClass.values().size()];
      this.mBeanClass2AnnoClass.values().toArray(out);
      return out;
   }

   public int size() {
      return this.mBeanClass2AnnoClass.size();
   }
}
