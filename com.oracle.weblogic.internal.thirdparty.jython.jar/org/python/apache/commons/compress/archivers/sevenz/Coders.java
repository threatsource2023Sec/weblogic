package org.python.apache.commons.compress.archivers.sevenz;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.python.apache.commons.compress.utils.FlushShieldFilterOutputStream;
import org.tukaani.xz.ARMOptions;
import org.tukaani.xz.ARMThumbOptions;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.IA64Options;
import org.tukaani.xz.PowerPCOptions;
import org.tukaani.xz.SPARCOptions;
import org.tukaani.xz.X86Options;

class Coders {
   private static final Map CODER_MAP = new HashMap() {
      private static final long serialVersionUID = 1664829131806520867L;

      {
         this.put(SevenZMethod.COPY, new CopyDecoder());
         this.put(SevenZMethod.LZMA, new LZMADecoder());
         this.put(SevenZMethod.LZMA2, new LZMA2Decoder());
         this.put(SevenZMethod.DEFLATE, new DeflateDecoder());
         this.put(SevenZMethod.BZIP2, new BZIP2Decoder());
         this.put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
         this.put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder(new X86Options()));
         this.put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder(new PowerPCOptions()));
         this.put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder(new IA64Options()));
         this.put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder(new ARMOptions()));
         this.put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder(new ARMThumbOptions()));
         this.put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder(new SPARCOptions()));
         this.put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
      }
   };

   static CoderBase findByMethod(SevenZMethod method) {
      return (CoderBase)CODER_MAP.get(method);
   }

   static InputStream addDecoder(String archiveName, InputStream is, long uncompressedLength, Coder coder, byte[] password) throws IOException {
      CoderBase cb = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
      if (cb == null) {
         throw new IOException("Unsupported compression method " + Arrays.toString(coder.decompressionMethodId) + " used in " + archiveName);
      } else {
         return cb.decode(archiveName, is, uncompressedLength, coder, password);
      }
   }

   static OutputStream addEncoder(OutputStream out, SevenZMethod method, Object options) throws IOException {
      CoderBase cb = findByMethod(method);
      if (cb == null) {
         throw new IOException("Unsupported compression method " + method);
      } else {
         return cb.encode(out, options);
      }
   }

   private static class DummyByteAddingInputStream extends FilterInputStream {
      private boolean addDummyByte;

      private DummyByteAddingInputStream(InputStream in) {
         super(in);
         this.addDummyByte = true;
      }

      public int read() throws IOException {
         int result = super.read();
         if (result == -1 && this.addDummyByte) {
            this.addDummyByte = false;
            result = 0;
         }

         return result;
      }

      public int read(byte[] b, int off, int len) throws IOException {
         int result = super.read(b, off, len);
         if (result == -1 && this.addDummyByte) {
            this.addDummyByte = false;
            b[off] = 0;
            return 1;
         } else {
            return result;
         }
      }

      // $FF: synthetic method
      DummyByteAddingInputStream(InputStream x0, Object x1) {
         this(x0);
      }
   }

   static class BZIP2Decoder extends CoderBase {
      BZIP2Decoder() {
         super(Number.class);
      }

      InputStream decode(String archiveName, InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
         return new BZip2CompressorInputStream(in);
      }

      OutputStream encode(OutputStream out, Object options) throws IOException {
         int blockSize = numberOptionOrDefault(options, 9);
         return new BZip2CompressorOutputStream(out, blockSize);
      }
   }

   static class DeflateDecoder extends CoderBase {
      DeflateDecoder() {
         super(Number.class);
      }

      InputStream decode(String archiveName, InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
         final Inflater inflater = new Inflater(true);
         final InflaterInputStream inflaterInputStream = new InflaterInputStream(new DummyByteAddingInputStream(in), inflater);
         return new InputStream() {
            public int read() throws IOException {
               return inflaterInputStream.read();
            }

            public int read(byte[] b, int off, int len) throws IOException {
               return inflaterInputStream.read(b, off, len);
            }

            public int read(byte[] b) throws IOException {
               return inflaterInputStream.read(b);
            }

            public void close() throws IOException {
               try {
                  inflaterInputStream.close();
               } finally {
                  inflater.end();
               }

            }
         };
      }

      OutputStream encode(OutputStream out, Object options) {
         int level = numberOptionOrDefault(options, 9);
         final Deflater deflater = new Deflater(level, true);
         final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out, deflater);
         return new OutputStream() {
            public void write(int b) throws IOException {
               deflaterOutputStream.write(b);
            }

            public void write(byte[] b) throws IOException {
               deflaterOutputStream.write(b);
            }

            public void write(byte[] b, int off, int len) throws IOException {
               deflaterOutputStream.write(b, off, len);
            }

            public void close() throws IOException {
               try {
                  deflaterOutputStream.close();
               } finally {
                  deflater.end();
               }

            }
         };
      }
   }

   static class BCJDecoder extends CoderBase {
      private final FilterOptions opts;

      BCJDecoder(FilterOptions opts) {
         super();
         this.opts = opts;
      }

      InputStream decode(String archiveName, InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
         try {
            return this.opts.getInputStream(in);
         } catch (AssertionError var8) {
            throw new IOException("BCJ filter used in " + archiveName + " needs XZ for Java > 1.4 - see http://commons.apache.org/proper/commons-compress/limitations.html#7Z", var8);
         }
      }

      OutputStream encode(OutputStream out, Object options) {
         return new FlushShieldFilterOutputStream(this.opts.getOutputStream(new FinishableWrapperOutputStream(out)));
      }
   }

   static class CopyDecoder extends CoderBase {
      CopyDecoder() {
         super();
      }

      InputStream decode(String archiveName, InputStream in, long uncompressedLength, Coder coder, byte[] password) throws IOException {
         return in;
      }

      OutputStream encode(OutputStream out, Object options) {
         return out;
      }
   }
}
