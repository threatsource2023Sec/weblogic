package weblogic.servlet.utils.fileupload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Part;

public class PartItem implements Part {
   private static final long serialVersionUID = 2237570099615271025L;
   private static final String UID = (new UID()).toString().replace(':', '_').replace('-', '_');
   private static int counter = 0;
   private String fieldName;
   private String contentType;
   private boolean isFormField;
   private String fileName;
   private long size = -1L;
   private int sizeThreshold;
   private File repository;
   private byte[] cachedContent;
   private transient DeferredFileOutputStream dfos;
   private transient File tempFile;
   private File dfosFile;
   private PartHeaders headers;
   private Multipart multipart;

   public PartItem(Multipart multipart, PartHeaders headers, String fieldName, String contentType, boolean isFormField, String fileName) {
      this.multipart = multipart;
      this.headers = headers;
      this.fieldName = fieldName;
      this.contentType = contentType;
      this.isFormField = isFormField;
      this.fileName = fileName;
      this.sizeThreshold = multipart.getFileSizeThreshold();
      this.repository = multipart.getRepository();
   }

   public InputStream getInputStream() throws IOException {
      if (!this.isInMemory()) {
         return new FileInputStream(this.dfos.getFile());
      } else {
         if (this.cachedContent == null) {
            this.cachedContent = this.dfos.getData();
         }

         return new ByteArrayInputStream(this.cachedContent);
      }
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getCharSet() {
      ParameterParser parser = new ParameterParser();
      parser.setLowerCaseNames(true);
      Map params = parser.parse(this.getContentType(), ';');
      return (String)params.get("charset");
   }

   public String getFileName() {
      return this.fileName;
   }

   public boolean isInMemory() {
      return this.cachedContent != null ? true : this.dfos.isInMemory();
   }

   public long getSize() {
      if (this.size >= 0L) {
         return this.size;
      } else if (this.cachedContent != null) {
         return (long)this.cachedContent.length;
      } else {
         return this.dfos.isInMemory() ? (long)this.dfos.getData().length : this.dfos.getFile().length();
      }
   }

   public byte[] get() {
      if (this.isInMemory()) {
         if (this.cachedContent == null) {
            this.cachedContent = this.dfos.getData();
         }

         return this.cachedContent;
      } else {
         byte[] fileData = new byte[(int)this.getSize()];
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(this.dfos.getFile());
            fis.read(fileData);
         } catch (IOException var12) {
            fileData = null;
         } finally {
            if (fis != null) {
               try {
                  fis.close();
               } catch (IOException var11) {
               }
            }

         }

         return fileData;
      }
   }

   public String getString(String charset) throws UnsupportedEncodingException {
      return new String(this.get(), charset);
   }

   public String getString() {
      byte[] rawdata = this.get();
      String charset = this.getCharSet();
      if (charset == null) {
         charset = "ISO-8859-1";
      }

      try {
         return new String(rawdata, charset);
      } catch (UnsupportedEncodingException var4) {
         return new String(rawdata);
      }
   }

   public void write(File file) throws IOException {
      if (this.isInMemory()) {
         FileOutputStream fout = null;

         try {
            fout = new FileOutputStream(file);
            fout.write(this.get());
         } finally {
            if (fout != null) {
               fout.close();
            }

         }
      } else {
         File outputFile = this.getStoreLocation();
         if (outputFile == null) {
            throw new IOException("Cannot write uploaded file to disk!");
         }

         this.size = outputFile.length();
         if (!outputFile.renameTo(file)) {
            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
               in = new BufferedInputStream(new FileInputStream(outputFile));
               out = new BufferedOutputStream(new FileOutputStream(file));
               Streams.copy(in, out, false);
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var19) {
                  }
               }

               if (out != null) {
                  try {
                     out.close();
                  } catch (IOException var18) {
                  }
               }

            }
         }
      }

   }

   public void write(String file) throws IOException {
      this.write(new File(this.repository, file));
   }

   public void delete() {
      this.cachedContent = null;
      File outputFile = this.getStoreLocation();
      if (outputFile != null && outputFile.exists()) {
         outputFile.delete();
      }

   }

   public String getName() {
      return this.fieldName;
   }

   public void setName(String fieldName) {
      this.fieldName = fieldName;
   }

   public boolean isFormField() {
      return this.isFormField;
   }

   public void setFormField(boolean state) {
      this.isFormField = state;
   }

   public OutputStream getOutputStream() throws IOException {
      if (this.dfos == null) {
         File outputFile = this.getTempFile();
         this.dfos = new DeferredFileOutputStream(this.sizeThreshold, outputFile);
      }

      return this.dfos;
   }

   public File getStoreLocation() {
      return this.dfos == null ? null : this.dfos.getFile();
   }

   protected void finalize() {
      File outputFile = this.dfos.getFile();
      if (outputFile != null && outputFile.exists()) {
         outputFile.delete();
      }

   }

   protected File getTempFile() {
      if (this.tempFile == null) {
         File tempDir = this.repository;
         if (tempDir == null) {
            tempDir = new File(System.getProperty("java.io.tmpdir"));
         }

         String tempFileName = "upload_" + UID + "_" + getUniqueId() + ".tmp";
         this.tempFile = new File(tempDir, tempFileName);
      }

      return this.tempFile;
   }

   private static String getUniqueId() {
      int limit = 100000000;
      Class var2 = PartItem.class;
      int current;
      synchronized(PartItem.class) {
         current = counter++;
      }

      String id = Integer.toString(current);
      if (current < 100000000) {
         id = ("00000000" + id).substring(id.length());
      }

      return id;
   }

   public String toString() {
      return "File name=" + this.getFileName() + ", StoreLocation=" + this.getStoreLocation() + ", size=" + this.getSize() + "bytes, isFormField=" + this.isFormField() + ", FieldName=" + this.getName();
   }

   public String getHeader(String name) {
      return this.headers.getHeader(name);
   }

   public Collection getHeaders(String name) {
      List values = this.headers.getHeaders(name);
      if (values != Collections.EMPTY_LIST) {
         values = Collections.unmodifiableList(values);
      }

      return values;
   }

   public Collection getHeaderNames() {
      return this.headers.getHeaderNames();
   }

   public String getSubmittedFileName() {
      String fileName = null;
      String cd = this.getHeader("Content-Disposition");
      if (cd != null) {
         String cdl = cd.toLowerCase(Locale.ENGLISH);
         if (cdl.startsWith("form-data") || cdl.startsWith("attachment")) {
            ParameterParser paramParser = new ParameterParser();
            paramParser.setLowerCaseNames(true);
            Map params = paramParser.parse(cd, ';');
            if (params.containsKey("filename")) {
               fileName = (String)params.get("filename");
               if (fileName != null) {
                  fileName = fileName.trim();
               } else {
                  fileName = "";
               }
            }
         }
      }

      return fileName;
   }
}
