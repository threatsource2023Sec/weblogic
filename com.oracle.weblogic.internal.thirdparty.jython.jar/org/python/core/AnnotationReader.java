package org.python.core;

import java.io.IOException;
import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.ClassReader;
import org.python.objectweb.asm.ClassVisitor;

public class AnnotationReader extends ClassVisitor {
   private boolean nextVisitIsVersion = false;
   private boolean nextVisitIsMTime = false;
   private boolean nextVisitIsFilename = false;
   private int version = -1;
   private long mtime = -1L;
   private String filename = null;

   public AnnotationReader(byte[] data) throws IOException {
      super(327680);

      ClassReader r;
      try {
         r = new ClassReader(data);
      } catch (ArrayIndexOutOfBoundsException var5) {
         IOException ioe = new IOException("Malformed bytecode: not enough data", var5);
         throw ioe;
      }

      r.accept(this, 0);
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      this.nextVisitIsVersion = desc.equals("Lorg/python/compiler/APIVersion;");
      this.nextVisitIsMTime = desc.equals("Lorg/python/compiler/MTime;");
      this.nextVisitIsFilename = desc.equals("Lorg/python/compiler/Filename;");
      return new AnnotationVisitor(327680) {
         public void visit(String name, Object value) {
            if (AnnotationReader.this.nextVisitIsVersion) {
               AnnotationReader.this.version = (Integer)value;
               AnnotationReader.this.nextVisitIsVersion = false;
            } else if (AnnotationReader.this.nextVisitIsMTime) {
               AnnotationReader.this.mtime = (Long)value;
               AnnotationReader.this.nextVisitIsMTime = false;
            } else if (AnnotationReader.this.nextVisitIsFilename) {
               AnnotationReader.this.filename = (String)value;
               AnnotationReader.this.nextVisitIsFilename = false;
            }

         }
      };
   }

   public int getVersion() {
      return this.version;
   }

   public long getMTime() {
      return this.mtime;
   }

   public String getFilename() {
      return this.filename;
   }
}
