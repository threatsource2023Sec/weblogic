package weblogic.xml.saaj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Iterator;
import javax.activation.CommandInfo;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MailcapCommandMap;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.xml.saaj.mime4j.decoder.Base64InputStream;
import weblogic.xml.saaj.util.GifDataContentHandler;
import weblogic.xml.saaj.util.HeaderUtils;
import weblogic.xml.saaj.util.IOUtils;
import weblogic.xml.saaj.util.JpegDataContentHandler;

final class AttachmentPartImpl extends AttachmentPart {
   private final MimeHeaders mimeHeaders = new MimeHeaders();
   private DataHandler dataHandler;
   private byte[] rawContent;

   public void setDirectInputStream(InputStream is) {
      this.dataHandler = new DataHandler(new DirectDataSource(is));
   }

   public void removeMimeHeader(String name) {
      this.mimeHeaders.removeHeader(name);
   }

   public void removeAllMimeHeaders() {
      this.mimeHeaders.removeAllHeaders();
   }

   public String[] getMimeHeader(String name) {
      return this.mimeHeaders.getHeader(name);
   }

   public Iterator getAllMimeHeaders() {
      return this.mimeHeaders.getAllHeaders();
   }

   public Iterator getMatchingMimeHeaders(String[] names) {
      return this.mimeHeaders.getMatchingHeaders(names);
   }

   public Iterator getNonMatchingMimeHeaders(String[] names) {
      return this.mimeHeaders.getNonMatchingHeaders(names);
   }

   public void setMimeHeader(String name, String value) {
      this.mimeHeaders.setHeader(name, value);
   }

   public void addMimeHeader(String name, String value) {
      this.mimeHeaders.addHeader(name, value);
   }

   public void setDataHandler(DataHandler dh) {
      if (dh == null) {
         throw new IllegalArgumentException("DataHandler can not be null");
      } else {
         this.setContentType(dh.getContentType());
         this.dataHandler = dh;
      }
   }

   public DataHandler getDataHandler() throws SOAPException {
      if (this.dataHandler == null) {
         throw new SOAPException("There is no data in this AttachmentPart");
      } else {
         return this.dataHandler;
      }
   }

   public void setContent(Object content, String contentType) {
      if (content == null) {
         throw new WebServiceException("AttachmentPart.setContent was called with a null content parameter.  If you want to remove the contents of this AttachmentPart, use clearContent() instead.");
      } else {
         this.setContentType(contentType);
         if (this.isXmlContent(contentType, content)) {
            this.setXmlContent(content, contentType);
         } else {
            this.dataHandler = new DataHandler(content, contentType);
         }

      }
   }

   private boolean isXmlContent(String contentType, Object content) {
      if (contentType == null) {
         return false;
      } else {
         return contentType.trim().equalsIgnoreCase("text/xml") && (content instanceof StreamSource || content instanceof InputStream);
      }
   }

   private void setXmlContent(Object content, String contentType) {
      String attachmentPartAsString;
      try {
         if (content instanceof StreamSource) {
            StreamSource streamSource = (StreamSource)content;
            InputStream is = streamSource.getInputStream();
            if (is == null) {
               Reader reader = streamSource.getReader();
               if (reader == null) {
                  throw new SOAPRuntimeException("Unable to handle Stream Source. How was this Stream Source created?");
               }

               attachmentPartAsString = IOUtils.toString(reader);
            } else {
               attachmentPartAsString = IOUtils.toString(streamSource.getInputStream());
            }
         } else {
            attachmentPartAsString = IOUtils.toString((InputStream)content);
         }
      } catch (IOException var7) {
         throw new SOAPRuntimeException("Error converting StreamSource to String for text/xml content type" + var7);
      }

      this.dataHandler = new DataHandler(attachmentPartAsString, contentType);
   }

   public int getSize() throws SOAPException {
      if (this.rawContent != null) {
         return this.rawContent.length;
      } else if (this.dataHandler == null) {
         return 0;
      } else {
         ByteArrayOutputStream bout = new ByteArrayOutputStream();

         try {
            this.dataHandler.writeTo(bout);
            int size = bout.size();
            bout.close();
            return size;
         } catch (IOException var3) {
            throw new SOAPException(var3);
         }
      }
   }

   public void clearContent() {
      this.dataHandler = null;
      this.rawContent = null;
   }

   public Object getContent() throws SOAPException {
      if (this.dataHandler == null) {
         throw new SOAPException("There is no data in this AttachmentPart");
      } else {
         try {
            String contentType = this.getContentType();
            Object content;
            if (contentType.startsWith("text/xml")) {
               content = new StreamSource(this.dataHandler.getInputStream());
            } else if (!contentType.startsWith("text/plain") && !contentType.startsWith("text/html")) {
               if (!contentType.startsWith("image/jpeg") && !contentType.startsWith("image/gif")) {
                  if (this.isMimeMultipart(contentType)) {
                     content = this.dataHandler.getContent();
                  } else {
                     content = this.dataHandler.getInputStream();
                  }
               } else {
                  content = this.dataHandler.getContent();
               }
            } else {
               content = this.dataHandler.getContent();
            }

            return content;
         } catch (IOException var3) {
            throw new SOAPException("Error retrieving Attachment content", var3);
         }
      }
   }

   private boolean isMimeMultipart(String contentType) {
      return contentType.startsWith("multipart/");
   }

   boolean hasAllHeaders(MimeHeaders mimeHeaders) {
      if (mimeHeaders != null) {
         Iterator headers = mimeHeaders.getAllHeaders();

         while(headers.hasNext()) {
            MimeHeader mimeHeader = (MimeHeader)headers.next();
            String[] values = this.mimeHeaders.getHeader(mimeHeader.getName());
            boolean found = false;
            if (values != null) {
               for(int j = 0; j < values.length; ++j) {
                  if (mimeHeader.getValue().equalsIgnoreCase(values[j])) {
                     found = true;
                     break;
                  }
               }
            }

            if (!found) {
               return false;
            }
         }
      }

      return true;
   }

   public InputStream getRawContent() throws SOAPException {
      if (this.dataHandler == null) {
         throw new SOAPException("There is no data in this AttachmentPart");
      } else {
         try {
            this.rawContent = this.getRawContentBytes();
            return new ByteArrayInputStream(this.rawContent);
         } catch (Exception var2) {
            throw new SOAPException(var2);
         }
      }
   }

   public InputStream getBase64Content() throws SOAPException {
      InputStream content = this.getRawContent();
      BASE64Encoder encoder = new BASE64Encoder();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
         encoder.encodeBuffer(content, baos);
      } catch (IOException var12) {
         throw new SOAPException("Error encoding base 64 content", var12);
      } finally {
         try {
            content.close();
         } catch (IOException var11) {
            throw new SOAPException("Error closing content input stream", var11);
         }
      }

      return new ByteArrayInputStream(baos.toByteArray());
   }

   public void setRawContent(InputStream content, String contentType) throws SOAPException {
      if (content == null) {
         throw new SOAPException("Supplied content is null");
      } else {
         this.setContentType(contentType);
         if (contentType.equals("application/octet-stream")) {
            this.setDirectInputStream(content);
         } else {
            this.dataHandler = new DataHandler(new DirectDataSource(content, contentType));
         }

      }
   }

   public void setBase64Content(InputStream content, String contentType) throws SOAPException {
      if (content == null) {
         throw new SOAPException("Supplied content is null");
      } else {
         try {
            this.setRawContent(new Base64InputStream(content), contentType);
            this.getRawContent();
         } catch (Exception var4) {
            throw new SOAPException(var4);
         }
      }
   }

   public void setRawContentBytes(byte[] content, int offset, int len, String contentType) throws SOAPException {
      if (content == null) {
         throw new SOAPException("Supplied content is null");
      } else {
         this.setRawContent(new ByteArrayInputStream(content, offset, len), contentType);
      }
   }

   public byte[] getRawContentBytes() throws SOAPException {
      if (this.rawContent != null) {
         return this.rawContent;
      } else if (this.dataHandler == null) {
         throw new SOAPException("There is no data in this AttachmentPart");
      } else {
         try {
            byte[] ret = IOUtils.toByteArray(this.dataHandler.getInputStream());
            this.dataHandler = new DataHandler(new DirectDataSource(new ByteArrayInputStream(ret), this.dataHandler.getContentType()));
            return ret;
         } catch (IOException var2) {
            throw new SOAPException("Error getting content as raw bytes", var2);
         }
      }
   }

   static {
      CommandMap map = CommandMap.getDefaultCommandMap();
      if (map instanceof MailcapCommandMap) {
         String hndlrStr = ";;x-java-content-handler=";
         MailcapCommandMap mailMap = (MailcapCommandMap)map;
         CommandInfo comm = mailMap.getCommand("image/jpeg", "content-handler");
         String commClass = null;
         if (comm != null) {
            commClass = comm.getCommandClass();
         }

         if (!"com.sun.xml.ws.encoding.ImageDataContentHandler".equals(commClass) && !"com.sun.xml.internal.messaging.saaj.soap.ImageDataContentHandler".equals(commClass) && !"com.sun.xml.messaging.saaj.soap.ImageDataContentHandler".equals(commClass)) {
            mailMap.addMailcap("image/jpeg;;x-java-content-handler=" + JpegDataContentHandler.class.getName());
         }

         comm = mailMap.getCommand("image/gif", "content-handler");
         commClass = null;
         if (comm != null) {
            commClass = comm.getCommandClass();
         }

         if (!"com.sun.xml.ws.encoding.ImageDataContentHandler".equals(commClass) && !"com.sun.xml.internal.messaging.saaj.soap.ImageDataContentHandler".equals(commClass) && !"com.sun.xml.messaging.saaj.soap.ImageDataContentHandler".equals(commClass)) {
            mailMap.addMailcap("image/gif;;x-java-content-handler=" + GifDataContentHandler.class.getName());
         }

      } else {
         throw new SOAPRuntimeException("Default CommandMap is not a MailcapCommandMap");
      }
   }

   private final class DirectDataSource implements DataSource {
      private final InputStream sourceStream;
      private final String contentType;

      public DirectDataSource(InputStream sourceStream) {
         this(sourceStream, (String)null);
      }

      public DirectDataSource(InputStream sourceStream, String contentType) {
         this.sourceStream = sourceStream;
         this.contentType = contentType;
      }

      public String getContentType() {
         return this.contentType != null ? this.contentType : HeaderUtils.getContentType(AttachmentPartImpl.this.mimeHeaders);
      }

      public InputStream getInputStream() {
         try {
            if (this.sourceStream.markSupported() && this.sourceStream.available() == 0) {
               this.sourceStream.reset();
            }
         } catch (IOException var2) {
         }

         return this.sourceStream;
      }

      public String getName() {
         throw new UnsupportedOperationException();
      }

      public OutputStream getOutputStream() {
         throw new UnsupportedOperationException();
      }
   }
}
