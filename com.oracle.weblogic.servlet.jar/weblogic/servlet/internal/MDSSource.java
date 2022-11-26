package weblogic.servlet.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import oracle.jsp.provider.JspResourceProvider;
import weblogic.utils.classloaders.Source;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public class MDSSource implements Source {
   private JspResourceProvider provider;
   private String requestURI;
   private String providerURI;
   private long lastModified;
   private InputStream inputStream;
   private Chunk head;
   private byte[] bytes = null;
   private long length = Long.MIN_VALUE;
   private boolean exists = false;

   public MDSSource(String uri, JspResourceProvider jrp) {
      this.provider = jrp;
      this.requestURI = uri;

      try {
         this.providerURI = this.provider.getProviderURI(this.requestURI);
         this.lastModified = this.provider.getLastModified(this.providerURI);
         this.inputStream = this.provider.fromStream(this.providerURI);
         this.exists = true;
      } catch (FileNotFoundException var4) {
      } catch (IOException var5) {
      }

   }

   public boolean exists() {
      return this.exists;
   }

   public String getProviderURI() {
      return this.providerURI;
   }

   public byte[] getBytes() throws IOException {
      if (this.bytes != null) {
         return this.bytes;
      } else {
         long size = this.length();
         if (size == Long.MIN_VALUE) {
            throw new IOException("Can't get bytes from stream, provider: " + this.provider + ", requestURI: " + this.requestURI + ", providerURI: " + this.providerURI + ", stream: " + this.inputStream);
         } else {
            this.bytes = new byte[(int)size];
            int offset = 0;

            for(Chunk tmp = this.head; tmp != null; tmp = tmp.next) {
               System.arraycopy(tmp.buf, 0, this.bytes, offset, tmp.end);
               offset += tmp.end;
            }

            return this.bytes;
         }
      }
   }

   public URL getCodeSourceURL() {
      return this.getURL();
   }

   public InputStream getInputStream() throws IOException {
      return new UnsyncByteArrayInputStream(this.getBytes());
   }

   public URL getURL() {
      try {
         return new URL(this.requestURI);
      } catch (MalformedURLException var2) {
         return null;
      }
   }

   public long lastModified() {
      return this.lastModified;
   }

   public long length() {
      if (this.length == Long.MIN_VALUE) {
         this.head = Chunk.getChunk();

         try {
            this.length = (long)Chunk.chunkFully(this.head, this.inputStream);
         } catch (IOException var2) {
         }
      }

      return this.length;
   }
}
