package weblogic.xml.jaxp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import org.xml.sax.InputSource;

public class BufferedInputSource extends InputSource {
   private InputSource source;
   private InputStream is;
   private Reader reader;

   public BufferedInputSource(InputSource source) {
      this.source = source;
   }

   public InputStream getByteStream() {
      InputStream is = this.source.getByteStream();
      if (is != null) {
         return (InputStream)is;
      } else {
         if (is != null && !((InputStream)is).markSupported()) {
            is = new BufferedInputStream((InputStream)is);
            this.is = (InputStream)is;
         }

         return (InputStream)is;
      }
   }

   public Reader getCharacterStream() {
      if (this.reader != null) {
         return this.reader;
      } else {
         Reader reader = this.source.getCharacterStream();
         if (reader != null && !((Reader)reader).markSupported()) {
            reader = new BufferedReader((Reader)reader);
            this.reader = (Reader)reader;
         }

         return (Reader)reader;
      }
   }

   public String getEncoding() {
      return this.source.getEncoding();
   }

   public String getPublicId() {
      return this.source.getPublicId();
   }

   public String getSystemId() {
      return this.source.getSystemId();
   }

   public void setByteStream(InputStream is) {
      this.source.setByteStream(is);
   }

   public void setCharacterStream(Reader reader) {
      this.source.setCharacterStream(reader);
   }

   public void setEncoding(String encoding) {
      this.source.setEncoding(encoding);
   }

   public void setPublicId(String publicId) {
      this.source.setPublicId(publicId);
   }

   public void setSystemId(String systemId) {
      this.source.setSystemId(systemId);
   }
}
