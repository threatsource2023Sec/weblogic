package org.apache.xmlbeans.impl.jam.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.xmlbeans.impl.jam.JAnnotationValue;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.internal.elements.AnnotationValueImpl;
import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;

/** @deprecated */
public abstract class TypedAnnotationProxyBase extends AnnotationProxy {
   private List mValues = null;

   protected TypedAnnotationProxyBase() {
   }

   public void setValue(String name, Object value, JClass type) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else if (value == null) {
         throw new IllegalArgumentException("null value");
      } else {
         if (this.mValues == null) {
            this.mValues = new ArrayList();
         }

         this.mValues.add(new AnnotationValueImpl((ElementContext)this.mContext, name, value, type));
         Method m = this.getSetterFor(name, value.getClass());
         if (m != null) {
            try {
               m.invoke(this, value);
            } catch (IllegalAccessException var6) {
               this.getLogger().warning((Throwable)var6);
            } catch (InvocationTargetException var7) {
               this.getLogger().warning((Throwable)var7);
            }

         }
      }
   }

   public JAnnotationValue[] getValues() {
      if (this.mValues == null) {
         return new JAnnotationValue[0];
      } else {
         JAnnotationValue[] out = new JAnnotationValue[this.mValues.size()];
         this.mValues.toArray(out);
         return out;
      }
   }

   protected Method getSetterFor(String memberName, Class valueType) {
      try {
         return this.getClass().getMethod("set" + memberName, valueType);
      } catch (NoSuchMethodException var4) {
         this.getLogger().warning((Throwable)var4);
         return null;
      }
   }
}
