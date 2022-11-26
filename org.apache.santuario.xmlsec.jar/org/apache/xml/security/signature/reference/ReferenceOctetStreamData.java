package org.apache.xml.security.signature.reference;

import java.io.InputStream;

public class ReferenceOctetStreamData implements ReferenceData {
   private InputStream octetStream;
   private String uri;
   private String mimeType;

   public ReferenceOctetStreamData(InputStream octetStream) {
      if (octetStream == null) {
         throw new NullPointerException("octetStream is null");
      } else {
         this.octetStream = octetStream;
      }
   }

   public ReferenceOctetStreamData(InputStream octetStream, String uri, String mimeType) {
      if (octetStream == null) {
         throw new NullPointerException("octetStream is null");
      } else {
         this.octetStream = octetStream;
         this.uri = uri;
         this.mimeType = mimeType;
      }
   }

   public InputStream getOctetStream() {
      return this.octetStream;
   }

   public String getURI() {
      return this.uri;
   }

   public String getMimeType() {
      return this.mimeType;
   }
}
