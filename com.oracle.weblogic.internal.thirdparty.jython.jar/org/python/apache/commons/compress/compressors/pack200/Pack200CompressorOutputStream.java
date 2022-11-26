package org.python.apache.commons.compress.compressors.pack200;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.jar.Pack200;
import org.python.apache.commons.compress.compressors.CompressorOutputStream;

public class Pack200CompressorOutputStream extends CompressorOutputStream {
   private boolean finished;
   private final OutputStream originalOutput;
   private final StreamBridge streamBridge;
   private final Map properties;

   public Pack200CompressorOutputStream(OutputStream out) throws IOException {
      this(out, Pack200Strategy.IN_MEMORY);
   }

   public Pack200CompressorOutputStream(OutputStream out, Pack200Strategy mode) throws IOException {
      this(out, mode, (Map)null);
   }

   public Pack200CompressorOutputStream(OutputStream out, Map props) throws IOException {
      this(out, Pack200Strategy.IN_MEMORY, props);
   }

   public Pack200CompressorOutputStream(OutputStream out, Pack200Strategy mode, Map props) throws IOException {
      this.finished = false;
      this.originalOutput = out;
      this.streamBridge = mode.newStreamBridge();
      this.properties = props;
   }

   public void write(int b) throws IOException {
      this.streamBridge.write(b);
   }

   public void write(byte[] b) throws IOException {
      this.streamBridge.write(b);
   }

   public void write(byte[] b, int from, int length) throws IOException {
      this.streamBridge.write(b, from, length);
   }

   public void close() throws IOException {
      this.finish();

      try {
         this.streamBridge.stop();
      } finally {
         this.originalOutput.close();
      }

   }

   public void finish() throws IOException {
      if (!this.finished) {
         this.finished = true;
         Pack200.Packer p = Pack200.newPacker();
         if (this.properties != null) {
            p.properties().putAll(this.properties);
         }

         JarInputStream ji = new JarInputStream(this.streamBridge.getInput());
         Throwable var3 = null;

         try {
            p.pack(ji, this.originalOutput);
         } catch (Throwable var12) {
            var3 = var12;
            throw var12;
         } finally {
            if (ji != null) {
               if (var3 != null) {
                  try {
                     ji.close();
                  } catch (Throwable var11) {
                     var3.addSuppressed(var11);
                  }
               } else {
                  ji.close();
               }
            }

         }
      }

   }
}
