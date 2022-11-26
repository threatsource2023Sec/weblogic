package org.apache.xmlbeans.impl.jam.annotation;

import java.util.ArrayList;
import java.util.List;
import org.apache.xmlbeans.impl.jam.JAnnotationValue;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.internal.elements.AnnotationValueImpl;
import org.apache.xmlbeans.impl.jam.internal.elements.ElementContext;

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
