package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.WritableEntry;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ClassAnnotation implements WritableEntry {
   private final String type;
   private final int typeIndex;
   private final List annotationValues;

   public ClassAnnotation(ConstPool constPool, String type, List annotationValues) {
      this.type = type;
      this.typeIndex = constPool.addUtf8Entry("L" + type.replace('.', '/') + ";");
      this.annotationValues = new ArrayList(annotationValues);
   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.typeIndex);
      stream.writeShort(this.annotationValues.size());
      Iterator var2 = this.annotationValues.iterator();

      while(var2.hasNext()) {
         AnnotationValue value = (AnnotationValue)var2.next();
         value.write(stream);
      }

   }

   public String getType() {
      return this.type;
   }

   public List getAnnotationValues() {
      return this.annotationValues;
   }
}
