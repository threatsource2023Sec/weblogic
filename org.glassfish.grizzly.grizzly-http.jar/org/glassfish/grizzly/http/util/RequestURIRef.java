package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.utils.Charsets;

public class RequestURIRef {
   private boolean isDecoded;
   private Charset decodedURIEncoding;
   private boolean wasSlashAllowed = true;
   private Charset defaultURIEncoding;
   private final DataChunk originalRequestURIDC;
   private final DataChunk requestURIDC;
   private final DataChunk decodedRequestURIDC;
   private byte[] preallocatedDecodedURIBuffer;

   public RequestURIRef() {
      this.defaultURIEncoding = Charsets.UTF8_CHARSET;
      this.originalRequestURIDC = DataChunk.newInstance();
      this.requestURIDC = new DataChunk() {
         public void notifyDirectUpdate() {
            if (this.type == DataChunk.Type.Buffer) {
               int start = this.getStart();
               int end = this.getEnd();
               byte[] bytes = new byte[end - start];
               Buffer currentBuffer = this.getBufferChunk().getBuffer();
               int pos = currentBuffer.position();
               int lim = currentBuffer.limit();
               Buffers.setPositionLimit(currentBuffer, start, end);
               currentBuffer.get(bytes);
               Buffers.setPositionLimit(currentBuffer, pos, lim);
               this.setBytes(bytes);
            }

         }
      };
      this.decodedRequestURIDC = DataChunk.newInstance();
   }

   public void init(Buffer input, int start, int end) {
      this.originalRequestURIDC.setBuffer(input, start, end);
      this.requestURIDC.setBuffer(input, start, end);
   }

   public void init(byte[] input, int start, int end) {
      this.originalRequestURIDC.setBytes(input, start, end);
      this.requestURIDC.setBytes(input, start, end);
   }

   public void init(String requestUri) {
      this.originalRequestURIDC.setString(requestUri);
      this.requestURIDC.setString(requestUri);
   }

   public final DataChunk getOriginalRequestURIBC() {
      return this.originalRequestURIDC;
   }

   public final DataChunk getRequestURIBC() {
      return this.requestURIDC;
   }

   public final DataChunk getDecodedRequestURIBC() throws CharConversionException {
      return this.getDecodedRequestURIBC(this.wasSlashAllowed, this.defaultURIEncoding);
   }

   public DataChunk getDecodedRequestURIBC(boolean isSlashAllowed) throws CharConversionException {
      return this.getDecodedRequestURIBC(isSlashAllowed, this.defaultURIEncoding);
   }

   public DataChunk getDecodedRequestURIBC(boolean isSlashAllowed, Charset charset) throws CharConversionException {
      if (this.isDecoded && isSlashAllowed == this.wasSlashAllowed && charset == this.decodedURIEncoding) {
         return this.decodedRequestURIDC;
      } else {
         this.checkDecodedURICapacity(this.requestURIDC.getLength());
         this.decodedRequestURIDC.setBytes(this.preallocatedDecodedURIBuffer);
         HttpRequestURIDecoder.decode(this.requestURIDC, this.decodedRequestURIDC, isSlashAllowed, charset);
         this.isDecoded = true;
         this.wasSlashAllowed = isSlashAllowed;
         this.decodedURIEncoding = charset;
         return this.decodedRequestURIDC;
      }
   }

   public String getURI() {
      return this.getURI((Charset)null);
   }

   public String getURI(Charset encoding) {
      return this.getRequestURIBC().toString(encoding);
   }

   public void setURI(String uri) {
      this.getRequestURIBC().setString(uri);
   }

   public final String getDecodedURI() throws CharConversionException {
      return this.getDecodedURI(this.wasSlashAllowed);
   }

   public final String getDecodedURI(boolean isSlashAllowed) throws CharConversionException {
      return this.getDecodedURI(isSlashAllowed, this.defaultURIEncoding);
   }

   public String getDecodedURI(boolean isSlashAllowed, Charset encoding) throws CharConversionException {
      this.getDecodedRequestURIBC(isSlashAllowed, encoding);
      return this.decodedRequestURIDC.toString();
   }

   public void setDecodedURI(String uri) {
      this.decodedRequestURIDC.setString(uri);
      this.isDecoded = true;
   }

   public boolean isDecoded() {
      return this.isDecoded;
   }

   public Charset getDefaultURIEncoding() {
      return this.defaultURIEncoding;
   }

   public void setDefaultURIEncoding(Charset defaultURIEncoding) {
      this.defaultURIEncoding = defaultURIEncoding;
   }

   public void recycle() {
      this.originalRequestURIDC.recycle();
      this.decodedRequestURIDC.recycle();
      this.requestURIDC.recycle();
      this.isDecoded = false;
      this.wasSlashAllowed = true;
      this.decodedURIEncoding = null;
      this.defaultURIEncoding = Charsets.UTF8_CHARSET;
   }

   private void checkDecodedURICapacity(int size) {
      if (this.preallocatedDecodedURIBuffer == null || this.preallocatedDecodedURIBuffer.length < size) {
         this.preallocatedDecodedURIBuffer = new byte[size];
      }

   }
}
