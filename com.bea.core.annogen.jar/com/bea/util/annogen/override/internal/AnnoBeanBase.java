package com.bea.util.annogen.override.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnoBeanBase implements AnnoBean {
   private AnnoContext mContext = null;

   public boolean setValue(String valueName, Object value) {
      this.assertInited();
      String methodName = "set_" + valueName;
      Method[] m = this.getClass().getMethods();

      for(int i = 0; i < m.length; ++i) {
         if (m[i].getName().equals(methodName)) {
            try {
               m[i].invoke(this, value);
            } catch (IllegalAccessException var8) {
               var8.printStackTrace();
            } catch (InvocationTargetException var9) {
               var9.printStackTrace();
            } catch (IllegalArgumentException var10) {
               IllegalArgumentException iae2 = new IllegalArgumentException(valueName + " " + value + "  " + value.getClass());
               iae2.initCause(var10);
               this.mContext.getLogger().error((Throwable)iae2);
            }

            return true;
         }
      }

      return false;
   }

   public AnnoBean createNestableBean(Class beanOrAnnoType) {
      this.assertInited();

      try {
         Class c = this.mContext.getAnnobeanClassFor(beanOrAnnoType);
         return c == null ? null : this.mContext.createAnnoBeanFor(c);
      } catch (ClassNotFoundException var3) {
         this.mContext.getLogger().verbose((Throwable)var3, this);
         return null;
      }
   }

   public Object getValue(String name) {
      return null;
   }

   public boolean isDefaultValueUsed(String valueName) {
      return false;
   }

   public Class annotationType() {
      try {
         return this.mContext.getJsr175ClassForAnnobeanClass(this.getClass());
      } catch (ClassNotFoundException var2) {
         this.mContext.getLogger().error((Throwable)var2);
         return null;
      }
   }

   public void init(AnnoContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else if (this.mContext != null) {
         throw new IllegalStateException("already inited");
      } else {
         this.mContext = ctx;
      }
   }

   private void assertInited() {
      if (this.mContext == null) {
         throw new IllegalStateException();
      }
   }
}
