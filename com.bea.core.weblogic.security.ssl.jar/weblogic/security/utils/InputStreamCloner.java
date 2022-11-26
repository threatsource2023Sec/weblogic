package weblogic.security.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import weblogic.security.SSL.jsseadapter.PEMInputStream;

public class InputStreamCloner {
   private static final int COPY_SIZE = 1024;
   private boolean copied = false;
   private InputStream original = null;
   private byte[] bytes = null;
   private int hashcode = 0;
   private PEMInputStream pemIS = null;

   public InputStreamCloner(InputStream in) {
      if (in instanceof PEMInputStream) {
         this.pemIS = (PEMInputStream)in;
      } else {
         this.original = in;
      }

   }

   private synchronized void copyStream() throws IOException {
      if (!this.copied && this.pemIS == null) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();

         try {
            byte[] buffer = new byte[1024];

            int len;
            while((len = this.original.read(buffer)) != -1) {
               out.write(buffer, 0, len);
            }

            this.bytes = out.toByteArray();
            this.copied = true;
         } finally {
            out.close();
         }
      }
   }

   public InputStream cloneStream() throws IOException {
      if (this.pemIS != null) {
         return new PEMInputStream(this.pemIS.getPEMData());
      } else {
         if (!this.copied) {
            this.copyStream();
         }

         return new ByteArrayInputStream(this.bytes);
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && obj instanceof InputStreamCloner) {
         InputStreamCloner streamCloner = (InputStreamCloner)obj;
         if (this.pemIS == null) {
            if (this.pemIS == null && streamCloner.pemIS != null) {
               return false;
            } else if (this.original == streamCloner.original) {
               return true;
            } else {
               try {
                  this.copyStream();
                  streamCloner.copyStream();
               } catch (IOException var4) {
                  throw new RuntimeException(var4);
               }

               return Arrays.equals(this.bytes, streamCloner.bytes);
            }
         } else {
            return streamCloner.pemIS != null && this.pemIS.equals(streamCloner.pemIS);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      if (this.pemIS != null) {
         return this.pemIS.hashCode();
      } else {
         if (this.hashcode == 0) {
            try {
               this.copyStream();
            } catch (IOException var3) {
               throw new RuntimeException(var3);
            }

            if (this.bytes == null) {
               return 0;
            }

            int hash = 1;

            for(int i = 0; i < this.bytes.length; ++i) {
               hash = hash * 31 + this.bytes[i];
            }

            this.hashcode = hash;
         }

         return this.hashcode;
      }
   }
}
