package com.bea.xml_.impl.jam.annotation;

import com.bea.xml_.impl.jam.JAnnotationValue;
import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.internal.elements.AnnotationValueImpl;
import com.bea.xml_.impl.jam.internal.elements.ElementContext;
import java.util.ArrayList;
import java.util.List;

/** @deprecated */
public class DefaultAnnotationProxy extends AnnotationProxy {
   private List mValues = new ArrayList();

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
         this.mValues.add(new AnnotationValueImpl((ElementContext)this.getLogger(), name, value, type));
      }
   }
}
