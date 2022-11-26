package weblogic.servlet.utils.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import weblogic.servlet.HTTPLogger;

public class Multipart {
   private final String location;
   private final long maxFileSize;
   private final long maxRequestSize;
   private final int fileSizeThreshold;
   private File repository;
   private ProgressListener listener;
   private final HttpServletRequest request;
   private ArrayList parts;
   private List unmodifiableParts;
   private Exception partsParseException;

   public Multipart(HttpServletRequest request, String location, long maxFileSize, long maxRequestSize, int fileSizeThreshold) {
      this.request = request;
      this.location = location;
      this.maxFileSize = maxFileSize;
      this.maxRequestSize = maxRequestSize;
      this.fileSizeThreshold = fileSizeThreshold;
      this.repository = (File)request.getServletContext().getAttribute("javax.servlet.context.tempdir");
      if (location != null && location.length() != 0) {
         File tempFile = new File(location);
         if (tempFile.isAbsolute()) {
            this.repository = tempFile;
         } else {
            this.repository = new File(this.repository, location);
         }
      }

   }

   public String getLocation() {
      return this.location;
   }

   public int getFileSizeThreshold() {
      return this.fileSizeThreshold;
   }

   public long getMaxFileSize() {
      return this.maxFileSize;
   }

   public long getMaxRequestSize() {
      return this.maxRequestSize;
   }

   public File getRepository() {
      return this.repository;
   }

   private boolean isMultipart() {
      if (!this.request.getMethod().toLowerCase().equals("post")) {
         return false;
      } else {
         String contentType = this.request.getContentType();
         if (contentType == null) {
            return false;
         } else {
            return contentType.toLowerCase().startsWith("multipart/form-data");
         }
      }
   }

   private void initParts() throws IOException, ServletException {
      if (this.parts == null) {
         this.parts = new ArrayList();

         try {
            RequestItemIterator iter = new RequestItemIterator(this, this.request);

            while(iter.hasNext()) {
               RequestItem requestItem = iter.next();
               PartItem partItem = new PartItem(this, requestItem.getHeaders(), requestItem.getFieldName(), requestItem.getContentType(), requestItem.isFormField(), requestItem.getName());
               Streams.copy(requestItem.openStream(), partItem.getOutputStream(), true);
               this.parts.add(partItem);
            }
         } catch (SizeException var4) {
            throw new IllegalStateException(var4);
         } catch (BoundaryTooLongException var5) {
            HTTPLogger.logMalformedRequest(this.request.getRequestURI() + " , Cause: The boundary specified in the Content-type header is too long", -1);
            this.setPartsParseException(new ServletException("The boundary specified in the Content-type header is too long", var5));
         }

      }
   }

   public synchronized Collection getParts() throws IOException, ServletException {
      if (!this.isMultipart()) {
         throw new ServletException("The request content-type is not a multipart/form-data");
      } else {
         this.initParts();
         if (null == this.unmodifiableParts) {
            this.unmodifiableParts = Collections.unmodifiableList(this.parts);
         }

         return this.unmodifiableParts;
      }
   }

   public Part getPart(String name) throws IOException, ServletException {
      if (!this.isMultipart()) {
         throw new ServletException("The request content-type is not a multipart/form-data");
      } else {
         this.initParts();
         Iterator var2 = this.parts.iterator();

         Part part;
         String fieldName;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            part = (Part)var2.next();
            fieldName = part.getName();
         } while(!name.equals(fieldName));

         return part;
      }
   }

   public ProgressListener getProgressListener() {
      return this.listener;
   }

   public void setProgressListener(ProgressListener pListener) {
      this.listener = pListener;
   }

   public Exception getPartsParseException() {
      return this.partsParseException;
   }

   public void setPartsParseException(Exception partsParseException) {
      this.partsParseException = partsParseException;
   }
}
