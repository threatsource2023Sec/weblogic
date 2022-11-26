package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.LazySize;

public class ParameterAnnotationsAttribute extends Attribute {
   private final Map annotations = new HashMap();
   private final int noParameters;

   public ParameterAnnotationsAttribute(Type type, ConstPool constPool, int noParameters) {
      super(type.getTag(), constPool);
      this.noParameters = noParameters;
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      LazySize sizeMarker = stream.writeSize();
      stream.writeByte(this.noParameters);

      for(int i = 0; i < this.noParameters; ++i) {
         if (!this.annotations.containsKey(i)) {
            stream.writeShort(0);
         } else {
            List ans = (List)this.annotations.get(i);
            stream.writeShort(ans.size());
            Iterator var5 = ans.iterator();

            while(var5.hasNext()) {
               ClassAnnotation annotation = (ClassAnnotation)var5.next();
               annotation.write(stream);
            }
         }
      }

      sizeMarker.markEnd();
   }

   public void addAnnotation(int parameter, Annotation annotation) {
      if (!this.annotations.containsKey(parameter)) {
         this.annotations.put(parameter, new ArrayList());
      }

      ((List)this.annotations.get(parameter)).add(AnnotationBuilder.createAnnotation(this.constPool, annotation));
   }

   public void addAnnotation(int parameter, ClassAnnotation annotation) {
      if (!this.annotations.containsKey(parameter)) {
         this.annotations.put(parameter, new ArrayList());
      }

      ((List)this.annotations.get(parameter)).add(annotation);
   }

   public static enum Type {
      RUNTIME_VISIBLE("RuntimeVisibleParameterAnnotations"),
      RUNTIME_INVISIBLE("RuntimeInvisibleParameterAnnotations");

      private final String tag;

      private Type(String tag) {
         this.tag = tag;
      }

      public String getTag() {
         return this.tag;
      }
   }
}
