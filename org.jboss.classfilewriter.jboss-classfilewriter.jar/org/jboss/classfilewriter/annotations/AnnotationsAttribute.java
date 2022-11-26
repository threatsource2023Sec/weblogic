package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.LazySize;

public class AnnotationsAttribute extends Attribute {
   private final List annotations = new ArrayList();

   public AnnotationsAttribute(Type type, ConstPool constPool) {
      super(type.getTag(), constPool);
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      LazySize sizeMarker = stream.writeSize();
      stream.writeShort(this.annotations.size());
      Iterator var3 = this.annotations.iterator();

      while(var3.hasNext()) {
         ClassAnnotation annotation = (ClassAnnotation)var3.next();
         annotation.write(stream);
      }

      sizeMarker.markEnd();
   }

   public void addAnnotation(Annotation annotation) {
      this.annotations.add(AnnotationBuilder.createAnnotation(this.constPool, annotation));
   }

   public void addAnnotation(ClassAnnotation annotation) {
      this.annotations.add(annotation);
   }

   public static enum Type {
      RUNTIME_VISIBLE("RuntimeVisibleAnnotations"),
      RUNTIME_INVISIBLE("RuntimeInvisibleAnnotations");

      private final String tag;

      private Type(String tag) {
         this.tag = tag;
      }

      public String getTag() {
         return this.tag;
      }
   }
}
