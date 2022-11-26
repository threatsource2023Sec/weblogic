package weblogic.spring.tools.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;
import weblogic.spring.tools.SpringInstrumentException;

public class ClassInstrumentorWLDFImpl implements ClassInstrumentor {
   private final InstrumentorEngine engine;

   public ClassInstrumentorWLDFImpl(InstrumentorEngine engine) {
      this.engine = engine;
   }

   public void instrument(String classFile, String className) throws SpringInstrumentException {
      byte[] bs = this.readByteArrayFromFile(classFile);
      bs = this.engine.instrumentClass(this.getClass().getClassLoader(), className, bs);
      this.writeByteArrayToFile(classFile, bs);
      System.out.println("Class [" + className + "] instrumented.");
   }

   private byte[] readByteArrayFromFile(String file) throws SpringInstrumentException {
      InputStream is = null;

      try {
         is = new BufferedInputStream(new FileInputStream(file));
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         int b;
         while((b = is.read()) != -1) {
            bos.write(b);
         }

         byte[] var5 = bos.toByteArray();
         return var5;
      } catch (IOException var14) {
         throw new SpringInstrumentException(var14.getMessage(), var14);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var13) {
            }
         }

      }
   }

   private void writeByteArrayToFile(String file, byte[] bs) throws SpringInstrumentException {
      OutputStream os = null;

      try {
         os = new BufferedOutputStream(new FileOutputStream(file, false));
         os.write(bs);
         os.flush();
      } catch (IOException var12) {
         throw new SpringInstrumentException(var12.getMessage(), var12);
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (IOException var11) {
            }
         }

      }

   }
}
