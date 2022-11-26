package org.glassfish.admin.rest.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import weblogic.servlet.utils.fileupload.PartItem;

public class MultipartRequestMap extends HashMap {
   private static final long serialVersionUID = 1L;
   private static final String DEFAULT_ENCODING = "UTF-8";
   private String encoding;
   private String tempLocation;

   public MultipartRequestMap(HttpServletRequest request) {
      this(request, System.getProperty("java.io.tmpdir"));
   }

   public MultipartRequestMap(HttpServletRequest request, String tempLocation) {
      try {
         this.tempLocation = tempLocation;
         this.encoding = request.getCharacterEncoding();
         if (this.encoding == null) {
            try {
               request.setCharacterEncoding(this.encoding = "UTF-8");
            } catch (UnsupportedEncodingException var6) {
               Logger.getLogger(MultipartRequestMap.class.getName()).log(Level.SEVERE, (String)null, var6);
            }
         }

         Iterator var3 = request.getParts().iterator();

         while(var3.hasNext()) {
            Part part = (Part)var3.next();
            String fileName = ((PartItem)part).getFileName();
            if (fileName == null) {
               this.putMulti(part.getName(), this.getValue(part));
            } else {
               this.processFilePart(part, fileName);
            }
         }
      } catch (ServletException | IOException var7) {
         Logger.getLogger(MultipartRequestMap.class.getName()).log(Level.SEVERE, (String)null, var7);
      }

   }

   public boolean isFile(String name) {
      List list = (List)this.get(name);
      return list != null && list.get(0) instanceof FileWrapper;
   }

   public String getStringParameter(String name) {
      List list = (List)this.get(name);
      return list != null ? (String)list.get(0) : null;
   }

   public InputStream getInputStream(String name) {
      List list = (List)this.get(name);
      return list != null ? ((FileWrapper)list.get(0)).inputStream : null;
   }

   public String getContentType(String name) {
      List list = (List)this.get(name);
      return list != null ? ((FileWrapper)list.get(0)).contentType : null;
   }

   public String getFileName(String name) {
      List list = (List)this.get(name);
      return list != null ? ((FileWrapper)list.get(0)).fileName : null;
   }

   private void processFilePart(Part part, String fileName) throws IOException {
      this.putMulti(part.getName(), new FileWrapper(fileName, part.getContentType(), part.getInputStream()));
      part.delete();
   }

   private File getFile1(FileWrapper fw) throws IOException {
      File tempFile = new File(this.tempLocation, fw.fileName);
      tempFile.createNewFile();
      tempFile.deleteOnExit();

      try {
         BufferedInputStream input = new BufferedInputStream(fw.inputStream, 8192);
         Throwable var4 = null;

         try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(tempFile), 8192);
            Throwable var6 = null;

            try {
               byte[] buffer = new byte[8192];
               int length = false;

               int length;
               while((length = input.read(buffer)) > 0) {
                  output.write(buffer, 0, length);
               }
            } catch (Throwable var32) {
               var6 = var32;
               throw var32;
            } finally {
               if (output != null) {
                  if (var6 != null) {
                     try {
                        output.close();
                     } catch (Throwable var31) {
                        var6.addSuppressed(var31);
                     }
                  } else {
                     output.close();
                  }
               }

            }
         } catch (Throwable var34) {
            var4 = var34;
            throw var34;
         } finally {
            if (input != null) {
               if (var4 != null) {
                  try {
                     input.close();
                  } catch (Throwable var30) {
                     var4.addSuppressed(var30);
                  }
               } else {
                  input.close();
               }
            }

         }
      } catch (Exception var36) {
         var36.printStackTrace();
      }

      return tempFile;
   }

   private String getValue(Part part) {
      StringBuilder value = new StringBuilder();

      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), this.encoding));
         Throwable var4 = null;

         try {
            char[] buffer = new char[8192];

            int length;
            while((length = reader.read(buffer)) > 0) {
               value.append(buffer, 0, length);
            }
         } catch (Throwable var15) {
            var4 = var15;
            throw var15;
         } finally {
            if (reader != null) {
               if (var4 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var14) {
                     var4.addSuppressed(var14);
                  }
               } else {
                  reader.close();
               }
            }

         }
      } catch (IOException var17) {
         ExceptionUtil.log(var17);
      }

      return value.toString();
   }

   private void putMulti(String key, Object value) {
      List values = (List)super.get(key);
      if (values == null) {
         List values = new ArrayList();
         values.add(value);
         this.put(key, values);
      } else {
         values.add(value);
      }

   }

   private class FileWrapper {
      String fileName;
      String contentType;
      InputStream inputStream;

      public FileWrapper(String fileName, String contentType, InputStream inputStream) {
         this.fileName = fileName;
         this.contentType = contentType;
         this.inputStream = inputStream;
      }
   }
}
