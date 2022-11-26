package weblogic.xml.saaj.mime4j;

import java.util.HashMap;
import java.util.Map;

public class BodyDescriptor {
   private BodyDescriptor parent;
   private String mimeType;
   private String boundary;
   private String charset;
   private String transferEncoding;
   private Map parameters;
   private boolean contentTypeSet;
   private boolean contentTransferEncSet;

   public BodyDescriptor() {
      this((BodyDescriptor)null);
   }

   public BodyDescriptor(BodyDescriptor parent) {
      this.parent = null;
      this.mimeType = "text/plain";
      this.boundary = null;
      this.charset = "us-ascii";
      this.transferEncoding = "7bit";
      this.parameters = new HashMap();
      this.contentTypeSet = false;
      this.contentTransferEncSet = false;
      if (parent != null && parent.isMimeType("multipart/digest")) {
         this.mimeType = "message/rfc822";
      } else {
         this.mimeType = "text/plain";
      }

   }

   public void addField(String name, String value) {
      name = name.trim().toLowerCase();
      if (name.equals("content-transfer-encoding") && !this.contentTransferEncSet) {
         this.contentTransferEncSet = true;
         value = value.trim().toLowerCase();
         if (value.length() > 0) {
            this.transferEncoding = value;
         }
      } else if (name.equals("content-type") && !this.contentTypeSet) {
         this.contentTypeSet = true;
         value = value.trim();
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (c != '\r' && c != '\n') {
               sb.append(c);
            }
         }

         Map params = this.getHeaderParams(sb.toString());
         String main = (String)params.get("");
         if (main != null) {
            main = main.toLowerCase().trim();
            int index = main.indexOf(47);
            boolean valid = false;
            if (index != -1) {
               String type = main.substring(0, index).trim();
               String subtype = main.substring(index + 1).trim();
               if (type.length() > 0 && subtype.length() > 0) {
                  main = type + "/" + subtype;
                  valid = true;
               }
            }

            if (!valid) {
               main = null;
            }
         }

         String b = (String)params.get("boundary");
         if (main != null && (main.startsWith("multipart/") && b != null || !main.startsWith("multipart/"))) {
            this.mimeType = main;
         }

         if (this.isMultipart()) {
            this.boundary = b;
         }

         String c = (String)params.get("charset");
         if (c != null) {
            c = c.trim();
            if (c.length() > 0) {
               this.charset = c.toLowerCase();
            }
         }

         this.parameters.putAll(params);
         this.parameters.remove("");
         this.parameters.remove("boundary");
         this.parameters.remove("charset");
      }

   }

   private Map getHeaderParams(String headerValue) {
      Map result = new HashMap();
      String main;
      String rest;
      if (headerValue.indexOf(";") == -1) {
         main = headerValue;
         rest = null;
      } else {
         main = headerValue.substring(0, headerValue.indexOf(";"));
         rest = headerValue.substring(main.length() + 1);
      }

      result.put("", main);
      if (rest != null) {
         char[] chars = rest.toCharArray();
         StringBuffer paramName = new StringBuffer();
         StringBuffer paramValue = new StringBuffer();
         byte READY_FOR_NAME = false;
         byte IN_NAME = true;
         byte READY_FOR_VALUE = true;
         byte IN_VALUE = true;
         byte IN_QUOTED_VALUE = true;
         byte VALUE_DONE = true;
         byte ERROR = true;
         byte state = 0;
         boolean escaped = false;

         for(int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            boolean fallThrough;
            switch (state) {
               case 0:
                  if (c == '=') {
                     state = 99;
                     break;
                  } else {
                     paramName = new StringBuffer();
                     paramValue = new StringBuffer();
                     state = 1;
                  }
               case 1:
                  if (c == '=') {
                     if (paramName.length() == 0) {
                        state = 99;
                     } else {
                        state = 2;
                     }
                  } else {
                     paramName.append(c);
                  }
                  break;
               case 2:
                  fallThrough = false;
                  switch (c) {
                     case '\t':
                     case ' ':
                        break;
                     case '"':
                        state = 4;
                        break;
                     default:
                        state = 3;
                        fallThrough = true;
                  }

                  if (!fallThrough) {
                     break;
                  }
               case 3:
                  fallThrough = false;
                  switch (c) {
                     case '\t':
                     case ' ':
                     case ';':
                        result.put(paramName.toString().trim().toLowerCase(), paramValue.toString().trim());
                        state = 5;
                        fallThrough = true;
                        break;
                     default:
                        paramValue.append(c);
                  }

                  if (!fallThrough) {
                     break;
                  }
               case 5:
                  switch (c) {
                     case '\t':
                     case ' ':
                        continue;
                     case ';':
                        state = 0;
                        continue;
                     default:
                        state = 99;
                        continue;
                  }
               case 4:
                  switch (c) {
                     case '"':
                        if (!escaped) {
                           result.put(paramName.toString().trim().toLowerCase(), paramValue.toString());
                           state = 5;
                        } else {
                           escaped = false;
                           paramValue.append(c);
                        }
                        continue;
                     case '\\':
                        if (escaped) {
                           paramValue.append('\\');
                        }

                        escaped = !escaped;
                        continue;
                     default:
                        if (escaped) {
                           paramValue.append('\\');
                        }

                        escaped = false;
                        paramValue.append(c);
                        continue;
                  }
               case 99:
                  if (c == ';') {
                     state = 0;
                  }
            }
         }

         if (state == 3) {
            result.put(paramName.toString().trim().toLowerCase(), paramValue.toString().trim());
         }
      }

      return result;
   }

   public boolean isMimeType(String mimeType) {
      return this.mimeType.equals(mimeType.toLowerCase());
   }

   public boolean isMessage() {
      return this.mimeType.equals("message/rfc822");
   }

   public boolean isMultipart() {
      return this.mimeType.startsWith("multipart/");
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public String getBoundary() {
      return this.boundary;
   }

   public String getCharset() {
      return this.charset;
   }

   public Map getParameters() {
      return this.parameters;
   }

   public String getTransferEncoding() {
      return this.transferEncoding;
   }

   public boolean isBase64Encoded() {
      return "base64".equals(this.transferEncoding);
   }

   public boolean isQuotedPrintableEncoded() {
      return "quoted-printable".equals(this.transferEncoding);
   }

   public String toString() {
      return this.mimeType;
   }
}
