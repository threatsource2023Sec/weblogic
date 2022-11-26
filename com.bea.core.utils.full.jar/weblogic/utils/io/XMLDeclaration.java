package weblogic.utils.io;

import java.io.InputStream;
import java.io.Reader;

public final class XMLDeclaration {
   private String version;
   private String encoding;
   private String standalone;
   private static final int HEADER_START = 0;
   private static final int HEADER_STARTED = 1;
   private static final int HEADER_XML = 2;
   private static final int SCANNING = 3;
   private static final int IN_ATTRIBUTE = 4;
   private static final int IN_STRING_LITERAL = 5;

   public XMLDeclaration() {
      this.version = null;
      this.encoding = null;
      this.standalone = null;
   }

   public XMLDeclaration(InputStream is) {
      this();
      this.parse(is);
   }

   public String getVersion() {
      return this.version;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public String getStandalone() {
      return this.standalone;
   }

   public void parse(InputStream headerReader) {
      String VERSION = "version";
      String ENCODING = "encoding";
      String STANDALONE = "standalone";
      String XML = "xml";
      String literal = null;
      StringBuffer buffer = new StringBuffer();
      char startQuote = 0;

      try {
         int state = 0;

         while(true) {
            int input = headerReader.read();
            if (input < 0) {
               break;
            }

            char c = (char)input;
            String attribute;
            String value;
            switch (state) {
               case 0:
                  if (c != '<') {
                     return;
                  }

                  state = 1;
                  break;
               case 1:
                  if (c != '?') {
                     return;
                  }

                  state = 2;
                  buffer.setLength(0);
                  break;
               case 2:
                  if (Character.isLetter(c)) {
                     buffer.append(c);
                  } else {
                     if (!Character.isWhitespace(c)) {
                        return;
                     }

                     String instruction = buffer.toString();
                     if (!"xml".equals(instruction)) {
                        return;
                     }

                     state = 3;
                  }
                  break;
               case 3:
               default:
                  if (!Character.isWhitespace(c) && c != '=') {
                     if (Character.isLetter(c)) {
                        state = 4;
                        buffer.setLength(0);
                        buffer.append(c);
                        attribute = null;
                     } else {
                        if (c != '\'' && c != '"') {
                           return;
                        }

                        state = 5;
                        startQuote = c;
                        buffer.setLength(0);
                        value = null;
                     }
                  }
                  break;
               case 4:
                  if (Character.isLetter(c)) {
                     buffer.append(c);
                  } else {
                     if (c != '=' && !Character.isWhitespace(c)) {
                        return;
                     }

                     state = 3;
                     attribute = buffer.toString();
                     if ("version".equals(attribute)) {
                        literal = "version";
                     } else if ("encoding".equals(attribute)) {
                        literal = "encoding";
                     } else if ("standalone".equals(attribute)) {
                        literal = "standalone";
                     }
                  }
                  break;
               case 5:
                  if (c == startQuote) {
                     state = 3;
                     value = buffer.toString();
                     if ("version".equals(literal)) {
                        this.version = value;
                        literal = null;
                     }

                     if ("encoding".equals(literal)) {
                        this.encoding = value;
                        literal = null;
                     }

                     if ("standalone".equals(literal)) {
                        this.standalone = value;
                        literal = null;
                        return;
                     }
                  } else {
                     buffer.append(c);
                  }
            }
         }
      } catch (Exception var15) {
      }

   }

   public void parse(Reader headerReader) {
      String VERSION = "version";
      String ENCODING = "encoding";
      String STANDALONE = "standalone";
      String XML = "xml";
      String literal = null;
      StringBuffer buffer = new StringBuffer();
      char startQuote = 0;

      try {
         int state = 0;

         while(true) {
            int input = headerReader.read();
            if (input < 0) {
               break;
            }

            char c = (char)input;
            String attribute;
            String value;
            switch (state) {
               case 0:
                  if (c != '<') {
                     return;
                  }

                  state = 1;
                  break;
               case 1:
                  if (c != '?') {
                     return;
                  }

                  state = 2;
                  buffer.setLength(0);
                  break;
               case 2:
                  if (Character.isLetter(c)) {
                     buffer.append(c);
                  } else {
                     if (!Character.isWhitespace(c)) {
                        return;
                     }

                     String instruction = buffer.toString();
                     if (!"xml".equals(instruction)) {
                        return;
                     }

                     state = 3;
                  }
                  break;
               case 3:
               default:
                  if (!Character.isWhitespace(c) && c != '=') {
                     if (Character.isLetter(c)) {
                        state = 4;
                        buffer.setLength(0);
                        buffer.append(c);
                        attribute = null;
                     } else {
                        if (c != '\'' && c != '"') {
                           return;
                        }

                        state = 5;
                        startQuote = c;
                        buffer.setLength(0);
                        value = null;
                     }
                  }
                  break;
               case 4:
                  if (Character.isLetter(c)) {
                     buffer.append(c);
                  } else {
                     if (c != '=' && !Character.isWhitespace(c)) {
                        return;
                     }

                     state = 3;
                     attribute = buffer.toString();
                     if ("version".equals(attribute)) {
                        literal = "version";
                     } else if ("encoding".equals(attribute)) {
                        literal = "encoding";
                     } else if ("standalone".equals(attribute)) {
                        literal = "standalone";
                     }
                  }
                  break;
               case 5:
                  if (c == startQuote) {
                     state = 3;
                     value = buffer.toString();
                     if ("version".equals(literal)) {
                        this.version = value;
                        literal = null;
                     }

                     if ("encoding".equals(literal)) {
                        this.encoding = value;
                        literal = null;
                     }

                     if ("standalone".equals(literal)) {
                        this.standalone = value;
                        literal = null;
                        return;
                     }
                  } else {
                     buffer.append(c);
                  }
            }
         }
      } catch (Exception var15) {
      }

   }
}
