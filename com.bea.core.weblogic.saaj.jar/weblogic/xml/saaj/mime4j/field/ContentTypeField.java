package weblogic.xml.saaj.mime4j.field;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.xml.saaj.mime4j.field.contenttype.parser.ContentTypeParser;
import weblogic.xml.saaj.mime4j.field.contenttype.parser.ParseException;
import weblogic.xml.saaj.mime4j.field.contenttype.parser.TokenMgrError;

public class ContentTypeField extends Field {
   public static final String TYPE_MULTIPART_PREFIX = "multipart/";
   public static final String TYPE_MULTIPART_DIGEST = "multipart/digest";
   public static final String TYPE_TEXT_PLAIN = "text/plain";
   public static final String TYPE_MESSAGE_RFC822 = "message/rfc822";
   public static final String PARAM_BOUNDARY = "boundary";
   public static final String PARAM_CHARSET = "charset";
   private String mimeType = "";
   private HashMap parameters = null;
   private ParseException parseException;

   protected ContentTypeField() {
   }

   protected void parseBody(String body) {
      ContentTypeParser parser = new ContentTypeParser(new StringReader(body));

      try {
         parser.parseAll();
      } catch (ParseException var10) {
         this.parseException = var10;
      } catch (TokenMgrError var11) {
         this.parseException = new ParseException(var11.getMessage());
      }

      try {
         String type = parser.getType();
         String subType = parser.getSubType();
         if (type != null && subType != null) {
            this.mimeType = (type + "/" + parser.getSubType()).toLowerCase();
            ArrayList paramNames = parser.getParamNames();
            ArrayList paramValues = parser.getParamValues();
            if (paramNames != null && paramValues != null) {
               for(int i = 0; i < paramNames.size() && i < paramValues.size(); ++i) {
                  if (this.parameters == null) {
                     this.parameters = new HashMap((int)((double)paramNames.size() * 1.3 + 1.0));
                  }

                  String name = ((String)paramNames.get(i)).toLowerCase();
                  String value = (String)paramValues.get(i);
                  this.parameters.put(name, value);
               }

            }
         }
      } catch (NullPointerException var12) {
      }
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public static String getMimeType(ContentTypeField child, ContentTypeField parent) {
      if (child == null || child.getMimeType().length() == 0 || child.isMultipart() && child.getBoundary() == null) {
         return parent != null && parent.isMimeType("multipart/digest") ? "message/rfc822" : "text/plain";
      } else {
         return child.getMimeType();
      }
   }

   public String getParameter(String name) {
      return this.parameters != null ? (String)this.parameters.get(name.toLowerCase()) : null;
   }

   public Map getParameters() {
      return this.parameters != null ? Collections.unmodifiableMap(this.parameters) : Collections.EMPTY_MAP;
   }

   public String getBoundary() {
      return this.getParameter("boundary");
   }

   public String getCharset() {
      return this.getParameter("charset");
   }

   public static String getCharset(ContentTypeField f) {
      return f != null && f.getCharset() != null && f.getCharset().length() > 0 ? f.getCharset() : "us-ascii";
   }

   public boolean isMimeType(String mimeType) {
      return this.mimeType.equalsIgnoreCase(mimeType);
   }

   public boolean isMultipart() {
      return this.mimeType.startsWith("multipart/");
   }
}
