package weblogic.servlet.utils.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

class RequestItemIterator {
   private static final String CONTENT_TYPE = "Content-type";
   private static final String CONTENT_LENGTH = "Content-length";
   private static final String CONTENT_DISPOSITION = "Content-disposition";
   private static final String FORM_DATA = "form-data";
   private static final String ATTACHMENT = "attachment";
   private static final String MULTIPART = "multipart/";
   private static final String MULTIPART_FORM_DATA = "multipart/form-data";
   private static final String MULTIPART_MIXED = "multipart/mixed";
   private Multipart multipart;
   private final MultipartStream multiStream;
   private final MultipartStream.ProgressNotifier notifier;
   private final byte[] boundary;
   private RequestItem currentItem;
   private String currentFieldName;
   private boolean itemValid;
   private boolean eof;

   RequestItemIterator(Multipart multipart, HttpServletRequest request) throws IOException, ServletException {
      this.multipart = multipart;
      String contentType = request.getContentType();
      if (null != contentType && contentType.toLowerCase().startsWith("multipart/")) {
         InputStream input = request.getInputStream();
         long sizeMax = multipart.getMaxRequestSize();
         if (sizeMax >= 0L) {
            int requestSize = request.getContentLength();
            if (requestSize == -1) {
               input = new LimitedInputStream((InputStream)input, sizeMax) {
                  protected void raiseError(long pSizeMax, long pCount) throws SizeException {
                     throw new SizeException("the request was rejected because its size (" + pCount + ") exceeds the configured maximum (" + pSizeMax + ")");
                  }
               };
            } else if ((long)requestSize > sizeMax) {
               throw new SizeException("the request was rejected because its size (" + requestSize + ") exceeds the configured maximum (" + sizeMax + ")");
            }
         }

         this.boundary = this.getBoundary(contentType);
         if (this.boundary == null) {
            throw new ServletException("the request was rejected because no multipart boundary was found");
         } else {
            this.notifier = new MultipartStream.ProgressNotifier(multipart.getProgressListener(), (long)request.getContentLength());
            this.multiStream = new MultipartStream((InputStream)input, this.boundary, this.notifier);
            this.multiStream.setHeaderEncoding(request.getCharacterEncoding());
            this.findNextItem();
         }
      } else {
         throw new ServletException("the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is " + contentType);
      }
   }

   private boolean findNextItem() throws IOException, ServletException {
      if (this.eof) {
         return false;
      } else {
         if (this.currentItem != null) {
            this.currentItem.close();
            this.currentItem = null;
         }

         while(true) {
            while(true) {
               while(!this.multiStream.skipPreamble()) {
                  if (this.currentFieldName == null) {
                     this.eof = true;
                     return false;
                  }

                  this.multiStream.setBoundary(this.boundary);
                  this.currentFieldName = null;
               }

               PartHeaders headers = this.getParsedHeaders(this.multiStream.readHeaders());
               String fieldName;
               if (this.currentFieldName == null) {
                  fieldName = this.getFieldName(headers);
                  if (fieldName != null) {
                     String subContentType = headers.getHeader("Content-type");
                     if (subContentType == null || !subContentType.toLowerCase().startsWith("multipart/mixed")) {
                        String fileName = this.getFileName(headers);
                        this.currentItem = new RequestItemImpl(headers, fileName, fieldName, headers.getHeader("Content-type"), fileName == null, this.getContentLength(headers));
                        this.notifier.noteItem();
                        this.itemValid = true;
                        return true;
                     }

                     this.currentFieldName = fieldName;
                     byte[] subBoundary = this.getBoundary(subContentType);
                     this.multiStream.setBoundary(subBoundary);
                     continue;
                  }
               } else {
                  fieldName = this.getFileName(headers);
                  if (fieldName != null) {
                     this.currentItem = new RequestItemImpl(headers, fieldName, this.currentFieldName, headers.getHeader("Content-type"), false, this.getContentLength(headers));
                     this.notifier.noteItem();
                     this.itemValid = true;
                     return true;
                  }
               }

               this.multiStream.discardBodyData();
            }
         }
      }
   }

   private long getContentLength(PartHeaders pHeaders) {
      try {
         return Long.parseLong(pHeaders.getHeader("Content-length"));
      } catch (Exception var3) {
         return -1L;
      }
   }

   protected String getFileName(PartHeaders headers) {
      return this.getFileName(headers.getHeader("Content-disposition"));
   }

   private String getFileName(String pContentDisposition) {
      String fileName = null;
      if (pContentDisposition != null) {
         String cdl = pContentDisposition.toLowerCase();
         if (cdl.startsWith("form-data") || cdl.startsWith("attachment")) {
            ParameterParser parser = new ParameterParser();
            parser.setLowerCaseNames(true);
            Map params = parser.parse(pContentDisposition, ';');
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

   protected String getFieldName(PartHeaders headers) {
      return this.getFieldName(headers.getHeader("Content-disposition"));
   }

   private String getFieldName(String pContentDisposition) {
      String fieldName = null;
      if (pContentDisposition != null && pContentDisposition.toLowerCase().startsWith("form-data")) {
         ParameterParser parser = new ParameterParser();
         parser.setLowerCaseNames(true);
         Map params = parser.parse(pContentDisposition, ';');
         fieldName = (String)params.get("name");
         if (fieldName != null) {
            fieldName = fieldName.trim();
         }
      }

      return fieldName;
   }

   protected PartHeaders getParsedHeaders(String headerPart) {
      int len = headerPart.length();
      PartHeaders headers = new PartHeaders();
      int start = 0;

      while(true) {
         int end = this.parseEndOfLine(headerPart, start);
         if (start == end) {
            return headers;
         }

         String header = headerPart.substring(start, end);

         int nonWs;
         for(start = end + 2; start < len; start = end + 2) {
            for(nonWs = start; nonWs < len; ++nonWs) {
               char c = headerPart.charAt(nonWs);
               if (c != ' ' && c != '\t') {
                  break;
               }
            }

            if (nonWs == start) {
               break;
            }

            end = this.parseEndOfLine(headerPart, nonWs);
            header = header + " " + headerPart.substring(nonWs, end);
         }

         nonWs = header.indexOf(58);
         if (nonWs != -1) {
            String headerName = header.substring(0, nonWs).trim();
            String headerValue = header.substring(header.indexOf(58) + 1).trim();
            headers.addHeader(headerName, headerValue);
         }
      }
   }

   private int parseEndOfLine(String headerPart, int end) {
      int index = end;

      while(true) {
         int offset = headerPart.indexOf(13, index);
         if (offset == -1 || offset + 1 >= headerPart.length()) {
            throw new IllegalStateException("Expected headers to be terminated by an empty line.");
         }

         if (headerPart.charAt(offset + 1) == '\n') {
            return offset;
         }

         index = offset + 1;
      }
   }

   public boolean hasNext() throws ServletException, IOException {
      if (this.eof) {
         return false;
      } else {
         return this.itemValid ? true : this.findNextItem();
      }
   }

   public RequestItem next() throws ServletException, IOException {
      this.itemValid = false;
      return this.currentItem;
   }

   protected byte[] getBoundary(String contentType) {
      ParameterParser parser = new ParameterParser();
      parser.setLowerCaseNames(true);
      Map params = parser.parse(contentType, new char[]{';', ','});
      String boundaryStr = (String)params.get("boundary");
      if (boundaryStr == null) {
         return null;
      } else {
         byte[] boundary;
         try {
            boundary = boundaryStr.getBytes("ISO-8859-1");
         } catch (UnsupportedEncodingException var7) {
            boundary = boundaryStr.getBytes();
         }

         return boundary;
      }
   }

   private class RequestItemImpl implements RequestItem {
      private final String contentType;
      private final String fieldName;
      private final String name;
      private final boolean formField;
      private InputStream stream;
      private PartHeaders headers;

      RequestItemImpl(PartHeaders pHeaders, String pName, String pFieldName, String pContentType, boolean pFormField, long pContentLength) throws ServletException {
         this.headers = pHeaders;
         this.name = pName;
         this.fieldName = pFieldName;
         this.contentType = pContentType;
         this.formField = pFormField;
         this.stream = RequestItemIterator.this.multiStream.newInputStream();
         long fileSizeMax = RequestItemIterator.this.multipart.getMaxFileSize();
         if (fileSizeMax != -1L) {
            if (pContentLength != -1L) {
               if (pContentLength > fileSizeMax) {
                  throw new ServletException("The field " + this.fieldName + " exceeds its maximum permitted  size of " + fileSizeMax + " characters.");
               }
            } else {
               this.stream = new LimitedInputStream(this.stream, fileSizeMax) {
                  protected void raiseError(long pSizeMax, long pCount) throws SizeException {
                     throw new SizeException("The field " + RequestItemImpl.this.fieldName + " exceeds its maximum permitted  size of " + pSizeMax + " characters.");
                  }
               };
            }
         }

      }

      public String getContentType() {
         return this.contentType;
      }

      public String getFieldName() {
         return this.fieldName;
      }

      public String getName() {
         return this.name;
      }

      public boolean isFormField() {
         return this.formField;
      }

      public InputStream openStream() throws IOException {
         return this.stream;
      }

      public void close() throws IOException {
         this.stream.close();
      }

      public PartHeaders getHeaders() {
         return this.headers;
      }

      public void setHeaders(PartHeaders pHeaders) {
         this.headers = pHeaders;
      }
   }
}
