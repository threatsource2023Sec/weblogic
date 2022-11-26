package com.bea.common.store.bootstrap;

import com.bea.common.security.utils.encoders.BASE64Encoder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LDIFTUtils {
   public static final String DN = "dn";
   public static final String OU = "ou";
   public static final String DC = "dc";
   public static final String CN = "cn";
   public static final String OBJECTCLASS = "objectClass";

   private LDIFTUtils() {
   }

   public static void writeLDIFFile(String fileName, List entries) throws IOException {
      writeLDIFFile(new File(fileName), entries);
   }

   public static void writeLDIFFile(File file, List entries) throws IOException {
      BASE64Encoder encoder = new BASE64Encoder();
      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));

      try {
         for(int i = 0; i < entries.size(); ++i) {
            Entry entry = (Entry)entries.get(i);
            writeEntry(out, entry, encoder);
         }
      } finally {
         out.close();
      }

   }

   private static void writeEntry(PrintWriter out, Entry ldifEntry, BASE64Encoder encoder) throws IOException {
      Map attributes = ldifEntry.getAttributes();
      Map binaryAttributes = ldifEntry.getBinaryAttributes();
      Map.Entry dnAttribute = getDNAttribute(attributes);
      if (dnAttribute == null) {
         throw new IOException("DN attribute is not available in the LDIF entry");
      } else {
         writeAttribute(out, dnAttribute);
         Iterator var6 = attributes.entrySet().iterator();

         Map.Entry entry;
         while(var6.hasNext()) {
            entry = (Map.Entry)var6.next();
            if (!"dn".equals(entry.getKey())) {
               writeAttribute(out, entry);
            }
         }

         if (binaryAttributes != null) {
            var6 = binaryAttributes.entrySet().iterator();

            while(var6.hasNext()) {
               entry = (Map.Entry)var6.next();
               writeBinaryAttribute(out, entry, encoder);
            }
         }

         out.println("");
      }
   }

   private static void writeAttribute(PrintWriter out, Map.Entry attribute) throws IOException {
      Collection attrValues = (Collection)attribute.getValue();
      String[] attrValueStrings = (String[])((String[])attrValues.toArray(new String[0]));
      String beforeColon = (String)attribute.getKey() + ": ";

      for(int i = 0; i < attrValueStrings.length; ++i) {
         out.println(beforeColon + attrValueStrings[i]);
      }

   }

   private static void writeBinaryAttribute(PrintWriter out, Map.Entry attribute, BASE64Encoder encoder) throws IOException {
      Collection attrValues = (Collection)attribute.getValue();
      byte[][] attrValueBinarys = (byte[][])((byte[][])attrValues.toArray(new byte[0][]));
      String beforeColon = (String)attribute.getKey() + ":: ";

      for(int i = 0; i < attrValueBinarys.length; ++i) {
         if (attrValueBinarys[i] != null) {
            String encodedString = encoder.encodeBuffer(attrValueBinarys[i]);
            out.println(beforeColon + encodedString);
         }
      }

   }

   private static Map.Entry getDNAttribute(Map attributes) {
      if (attributes == null) {
         return null;
      } else {
         Iterator var1 = attributes.entrySet().iterator();

         Map.Entry entry;
         do {
            if (!var1.hasNext()) {
               return null;
            }

            entry = (Map.Entry)var1.next();
         } while(!"dn".equals(entry.getKey()));

         return entry;
      }
   }

   public static final void addAttribute(Map bucket, String attributeName, String attributeValue) {
      if (attributeName != null && !attributeName.equals("") && attributeValue != null && !attributeValue.equals("")) {
         ArrayList list = new ArrayList();
         list.add(attributeValue);
         bucket.put(attributeName, list);
      }
   }

   public static final void buildBaseAttributes(Map baseAttributes, String dnString, String ouString, String[] objectClassStrings) {
      addAttribute(baseAttributes, "dn", dnString);
      addAttribute(baseAttributes, "ou", ouString);
      if (objectClassStrings.length > 0) {
         ArrayList list = new ArrayList();

         for(int i = 0; i < objectClassStrings.length; ++i) {
            list.add(objectClassStrings[i]);
         }

         baseAttributes.put("objectClass", list);
      }

   }
}
