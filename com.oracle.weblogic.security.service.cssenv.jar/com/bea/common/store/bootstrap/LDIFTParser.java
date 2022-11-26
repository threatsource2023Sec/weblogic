package com.bea.common.store.bootstrap;

import com.bea.common.security.utils.encoders.BASE64Decoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LDIFTParser {
   private LDIFTParser() {
   }

   public static List parse(String fileName) throws IOException {
      return parse(new File(fileName));
   }

   public static List parse(File file) throws IOException {
      List entries = new ArrayList();
      BASE64Decoder b64 = new BASE64Decoder();
      BufferedReader br = new BufferedReader(new FileReader(file));

      Entry e;
      try {
         while((e = parseEntry(br, b64)) != null) {
            entries.add(e);
         }
      } finally {
         br.close();
      }

      return entries != null ? Collections.unmodifiableList(entries) : null;
   }

   private static Entry parseEntry(BufferedReader br, BASE64Decoder b64) throws IOException {
      Map attributes = new HashMap();
      Map binaryAttributes = new HashMap();
      boolean lineRead = false;
      ArrayList ldifEntry = new ArrayList();

      String line;
      while((line = br.readLine()) != null) {
         if (line.length() <= 0) {
            if (!lineRead) {
               continue;
            }
            break;
         } else {
            lineRead = true;
            ldifEntry.add(line);
         }
      }

      if (lineRead) {
         StringBuffer accumLine = null;

         for(int i = 0; i < ldifEntry.size(); ++i) {
            String currentLine = (String)((String)ldifEntry.get(i));
            if (currentLine.indexOf(32) == 0) {
               if (accumLine == null) {
                  accumLine = new StringBuffer();
               }

               accumLine.append("\n");
               accumLine.append(currentLine);
            } else {
               if (accumLine != null && accumLine.length() > 0) {
                  String holdLine = collapseLine(accumLine).toString();
                  boolean isBinary = false;
                  int colonIdx = holdLine.indexOf(58);
                  String key = holdLine.substring(0, colonIdx).toLowerCase();
                  if (holdLine.length() > colonIdx && holdLine.charAt(colonIdx + 1) == ':') {
                     ++colonIdx;
                     isBinary = true;
                  }

                  String value = holdLine.substring(colonIdx + 1);
                  if (value != null && value.length() > 0) {
                     Object values;
                     if (isBinary) {
                        values = (Collection)binaryAttributes.get(key);
                        if (values == null) {
                           values = new ArrayList();
                           binaryAttributes.put(key, values);
                        }

                        ((Collection)values).add(b64.decodeBuffer(value.trim()));
                     } else {
                        values = (Collection)attributes.get(key);
                        if (values == null) {
                           values = new ArrayList();
                           attributes.put(key, values);
                        }

                        ((Collection)values).add(value.trim());
                     }
                  }
               }

               accumLine = new StringBuffer(currentLine);
            }
         }

         if (accumLine != null && accumLine.length() > 0) {
            String holdLine = accumLine.toString();
            boolean isBinary = false;
            int colonIdx = holdLine.indexOf(58);
            String key = holdLine.substring(0, colonIdx).toLowerCase();
            if (holdLine.length() > colonIdx && holdLine.charAt(colonIdx + 1) == ':') {
               ++colonIdx;
               isBinary = true;
            }

            String value = holdLine.substring(colonIdx + 1);
            if (value != null && value.length() > 0) {
               Object values;
               if (isBinary) {
                  values = (Collection)binaryAttributes.get(key);
                  if (values == null) {
                     values = new ArrayList();
                     binaryAttributes.put(key, values);
                  }

                  ((Collection)values).add(b64.decodeBuffer(value.trim()));
               } else {
                  values = (Collection)attributes.get(key);
                  if (values == null) {
                     values = new ArrayList();
                     attributes.put(key, values);
                  }

                  ((Collection)values).add(value.trim());
               }
            }
         }

         return new EntryImpl(!attributes.isEmpty() ? attributes : null, !binaryAttributes.isEmpty() ? binaryAttributes : null);
      } else {
         return null;
      }
   }

   private static String collapseLine(StringBuffer osb) {
      StringBuffer copy = new StringBuffer(osb.toString());

      for(int i = 0; (i = copy.indexOf("\n ", i)) >= 0; i += 2) {
         copy.deleteCharAt(i);
         copy.deleteCharAt(i);
      }

      return copy.toString();
   }

   public static class EntryImpl implements Entry {
      private Map attributes;
      private Map binaryAttributes;

      public EntryImpl(Map attributes, Map binaryAttributes) {
         this.attributes = conformMap(attributes);
         this.binaryAttributes = conformMap(binaryAttributes);
      }

      private static Map conformMap(Map attributes) {
         if (attributes == null) {
            return null;
         } else {
            Iterator var1 = attributes.entrySet().iterator();

            while(var1.hasNext()) {
               Map.Entry entry = (Map.Entry)var1.next();
               Collection values = (Collection)entry.getValue();
               if (values != null) {
                  entry.setValue(Collections.unmodifiableCollection(values));
               }
            }

            return Collections.unmodifiableMap(attributes);
         }
      }

      public Map getAttributes() {
         return this.attributes;
      }

      public Map getBinaryAttributes() {
         return this.binaryAttributes;
      }
   }
}
