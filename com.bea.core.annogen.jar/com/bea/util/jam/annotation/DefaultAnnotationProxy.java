package com.bea.util.jam.annotation;

import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.internal.elements.AnnotationValueImpl;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.provider.JamLogger;
import com.bea.util.jam.provider.JamServiceContext;
import java.util.ArrayList;
import java.util.List;

/** @deprecated */
@Deprecated
public class DefaultAnnotationProxy {
   public static final String SINGLE_MEMBER_NAME = "value";
   protected JamServiceContext mContext;
   private List mValues = new ArrayList();

   public void init(JamServiceContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null logger");
      } else {
         this.mContext = ctx;
      }
   }

   public JAnnotationValue getValue(String named) {
      if (named == null) {
         throw new IllegalArgumentException("null name");
      } else {
         named = named.trim();
         JAnnotationValue[] values = this.getValues();

         for(int i = 0; i < values.length; ++i) {
            if (named.equals(values[i].getName())) {
               return values[i];
            }
         }

         return null;
      }
   }

   public JAnnotationValue[] getValues() {
      JAnnotationValue[] out = new JAnnotationValue[this.mValues.size()];
      this.mValues.toArray(out);
      return out;
   }

   public void setValue(String name, Object value, JClass type) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else if (type == null) {
         throw new IllegalArgumentException("null type");
      } else if (value == null) {
         throw new IllegalArgumentException("null value");
      } else {
         name = name.trim();
         this.mValues.add(new AnnotationValueImpl((ElementContext)this.mContext, name, value, type));
      }
   }

   private JamLogger getLogger() {
      return this.mContext.getLogger();
   }
}
