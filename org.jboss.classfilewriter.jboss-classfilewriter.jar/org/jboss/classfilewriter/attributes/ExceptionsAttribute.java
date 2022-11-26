package org.jboss.classfilewriter.attributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ExceptionsAttribute extends Attribute {
   public static final String NAME = "Exceptions";
   private final List exceptionClasses = new ArrayList();
   private final List exceptionClassIndexes = new ArrayList();
   private final ConstPool constPool;

   public ExceptionsAttribute(ConstPool constPool) {
      super("Exceptions", constPool);
      this.constPool = constPool;
   }

   public void addExceptionClass(String exception) {
      this.exceptionClasses.add(exception);
      this.exceptionClassIndexes.add(this.constPool.addClassEntry(exception));
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeInt(2 + this.exceptionClassIndexes.size() * 2);
      stream.writeShort(this.exceptionClassIndexes.size());
      Iterator var2 = this.exceptionClassIndexes.iterator();

      while(var2.hasNext()) {
         int i = (Integer)var2.next();
         stream.writeShort(i);
      }

   }
}
