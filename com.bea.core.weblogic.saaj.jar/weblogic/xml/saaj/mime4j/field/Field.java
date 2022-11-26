package weblogic.xml.saaj.mime4j.field;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Field {
   public static final String SENDER = "Sender";
   public static final String FROM = "From";
   public static final String TO = "To";
   public static final String CC = "Cc";
   public static final String BCC = "Bcc";
   public static final String REPLY_TO = "Reply-To";
   public static final String RESENT_SENDER = "Resent-Sender";
   public static final String RESENT_FROM = "Resent-From";
   public static final String RESENT_TO = "Resent-To";
   public static final String RESENT_CC = "Resent-Cc";
   public static final String RESENT_BCC = "Resent-Bcc";
   public static final String DATE = "Date";
   public static final String RESENT_DATE = "Resent-Date";
   public static final String SUBJECT = "Subject";
   public static final String CONTENT_TYPE = "Content-Type";
   public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
   private static final String FIELD_NAME_PATTERN = "^([\\x21-\\x39\\x3b-\\x7e]+)[ \t]*:";
   private static Pattern fieldNamePattern = Pattern.compile("^([\\x21-\\x39\\x3b-\\x7e]+)[ \t]*:");
   private String name;
   private String body;
   private String raw;

   protected Field() {
   }

   public static Field parse(String s) {
      String raw = s;
      s = s.replaceAll("\r|\n", "");
      Matcher fieldMatcher = fieldNamePattern.matcher(s);
      if (!fieldMatcher.find()) {
         throw new IllegalArgumentException("Invalid field in string");
      } else {
         String name = fieldMatcher.group(1);
         String body = s.substring(fieldMatcher.end());
         if (body.length() > 0 && body.charAt(0) == ' ') {
            body = body.substring(1);
         }

         Field f = null;
         if (name.equalsIgnoreCase("Content-Transfer-Encoding")) {
            f = new ContentTransferEncodingField();
         } else if (name.equalsIgnoreCase("Content-Type")) {
            f = new ContentTypeField();
         } else if (!name.equalsIgnoreCase("Date") && !name.equalsIgnoreCase("Resent-Date")) {
            if (!name.equalsIgnoreCase("From") && !name.equalsIgnoreCase("Resent-From")) {
               if (!name.equalsIgnoreCase("Sender") && !name.equalsIgnoreCase("Resent-Sender")) {
                  if (!name.equalsIgnoreCase("To") && !name.equalsIgnoreCase("Cc") && !name.equalsIgnoreCase("Bcc") && !name.equalsIgnoreCase("Resent-To") && !name.equalsIgnoreCase("Resent-Cc") && !name.equalsIgnoreCase("Resent-Bcc")) {
                     f = new UnstructuredField();
                  } else {
                     f = new AddressListField();
                  }
               } else {
                  f = new MailboxField();
               }
            } else {
               f = new MailboxListField();
            }
         } else {
            f = new DateTimeField();
         }

         ((Field)f).name = name;
         ((Field)f).raw = raw;
         ((Field)f).body = body;
         ((Field)f).parseBody(body);
         return (Field)f;
      }
   }

   protected abstract void parseBody(String var1);

   public String getName() {
      return this.name;
   }

   public String getRaw() {
      return this.raw;
   }

   public String getBody() {
      return this.body;
   }

   public boolean isContentType() {
      return "Content-Type".equalsIgnoreCase(this.name);
   }

   public boolean isSubject() {
      return "Subject".equalsIgnoreCase(this.name);
   }

   public boolean isFrom() {
      return "From".equalsIgnoreCase(this.name);
   }

   public boolean isTo() {
      return "To".equalsIgnoreCase(this.name);
   }

   public String toString() {
      return this.raw;
   }
}
