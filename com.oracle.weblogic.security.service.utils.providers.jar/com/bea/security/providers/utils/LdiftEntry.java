package com.bea.security.providers.utils;

import com.bea.common.security.utils.Escaping;
import com.bea.common.store.bootstrap.Entry;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LdiftEntry implements Entry {
   private static final Escaping ESCAPER = new Escaping(new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/'});
   private Map stringAttributes;
   private Map binaryAttributes;
   private LinkedHashMap dnAttributes;
   private boolean readOnly = false;

   public LdiftEntry(Entry entry) {
      this.binaryAttributes = entry.getBinaryAttributes();
      this.stringAttributes = entry.getAttributes();
      if (this.stringAttributes == null) {
         throw new IllegalArgumentException("No DN attribute");
      } else {
         String dn = this.getStringAttributeValue("dn");
         if (dn == null) {
            throw new IllegalArgumentException("No DN attribute");
         } else {
            this.dnAttributes = parseDN(dn);
            this.readOnly = true;
         }
      }
   }

   public LdiftEntry() {
      this.readOnly = false;
   }

   public static LinkedHashMap parseDN(String dn) {
      LinkedHashMap attributes = new LinkedHashMap();
      StringTokenizer tokens = new StringTokenizer(dn, ",", false);

      while(tokens.hasMoreTokens()) {
         String token = tokens.nextToken();
         int index = token.indexOf(61);
         if (index < 0) {
            throw new IllegalArgumentException("Unexpected DN format");
         }

         String attributeName = token.substring(0, index).trim();
         String attributeValue = token.substring(index + 1).trim();
         List c = (List)attributes.get(attributeName);
         if (c == null) {
            c = new ArrayList(3);
         }

         ((List)c).add(attributeValue);
         attributes.put(attributeName, c);
      }

      return attributes;
   }

   public static String unescapeLDAPSpecialCharacters(String value) {
      return value == null ? null : ESCAPER.unescapeString(value);
   }

   public static String escapeLDAPSpecialCharacters(String value) {
      return value == null ? null : ESCAPER.escapeString(value);
   }

   public String getUnescapedStringAttributeValue(String attributeName) {
      return unescapeLDAPSpecialCharacters(this.getStringAttributeValue(attributeName));
   }

   public String getUnescapedDNAttributeValue(String attributeName) {
      return unescapeLDAPSpecialCharacters(this.getDNAttributeValue(attributeName));
   }

   public Collection getStringAttributeValues(String attributeName) {
      return this.stringAttributes == null ? null : (Collection)this.stringAttributes.get(attributeName.toLowerCase());
   }

   public Collection getBinaryAttributeValues(String attributeName) {
      return this.binaryAttributes == null ? null : (Collection)this.binaryAttributes.get(attributeName.toLowerCase());
   }

   public List getDNAttributeValues(String attributeName) {
      return this.dnAttributes == null ? null : (List)this.dnAttributes.get(attributeName);
   }

   public String getStringAttributeValue(String attributeName) {
      Collection c = this.getStringAttributeValues(attributeName);
      return c != null && c.size() > 0 ? (String)c.iterator().next() : null;
   }

   public byte[] getBinaryAttributeValue(String attributeName) {
      Collection c = this.getBinaryAttributeValues(attributeName);
      return c != null && c.size() > 0 ? (byte[])c.iterator().next() : null;
   }

   public String getBinaryAttributeStringValue(String attributeName) {
      byte[] value = this.getBinaryAttributeValue(attributeName);

      try {
         return value == null ? null : new String(value, "UTF8");
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException(var4);
      }
   }

   public String getUnescapedBinaryAttributeStringValue(String attributeName) {
      return unescapeLDAPSpecialCharacters(this.getBinaryAttributeStringValue(attributeName));
   }

   public String getDNAttributeValue(String attributeName) {
      List c = this.getDNAttributeValues(attributeName);
      return c != null && c.size() > 0 ? (String)c.iterator().next() : null;
   }

   public boolean containsAttributeValue(String attributeName, String value) {
      Collection c = this.getStringAttributeValues(attributeName);
      return c != null && c.contains(value);
   }

   public boolean containsAttributeValue(String attributeName, byte[] value) {
      Collection c = this.getBinaryAttributeValues(attributeName);
      if (c != null) {
         Iterator values = c.iterator();

         while(values.hasNext()) {
            if (Arrays.equals(value, (byte[])values.next())) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean containsDNAttributeValue(String attributeName, String value) {
      Collection c = this.getDNAttributeValues(attributeName);
      return c != null && c.contains(value);
   }

   public String getLastDNAttributeValue(String attributeName) {
      List c = this.getDNAttributeValues(attributeName);
      return c != null && c.size() > 0 ? (String)c.get(c.size() - 1) : null;
   }

   public String getUnescapedLastDNAttributeValue(String attributeName) {
      return unescapeLDAPSpecialCharacters(this.getLastDNAttributeValue(attributeName));
   }

   public boolean isEntryClass(String className) {
      return this.containsAttributeValue("objectClass", className);
   }

   public Map getAttributes() {
      return this.stringAttributes;
   }

   public Map getBinaryAttributes() {
      return this.binaryAttributes;
   }

   private void checkEdit() {
      if (this.readOnly) {
         throw new IllegalStateException("LdiftEntry is read only");
      }
   }

   public synchronized void addAttribute(String attributeName, byte[] value) {
      this.checkEdit();
      Collection attributes = this.getBinaryAttributeValues(attributeName);
      if (attributes == null) {
         attributes = new ArrayList();
      }

      ((Collection)attributes).add(value);
      if (this.binaryAttributes == null) {
         this.binaryAttributes = new HashMap();
      }

      this.binaryAttributes.put(attributeName, attributes);
   }

   public synchronized void addAttribute(String attributeName, String value) {
      this.checkEdit();
      Collection attributes = this.getStringAttributeValues(attributeName);
      if (attributes == null) {
         attributes = new ArrayList();
      }

      ((Collection)attributes).add(value);
      if (this.stringAttributes == null) {
         this.stringAttributes = new HashMap();
      }

      this.stringAttributes.put(attributeName, attributes);
   }

   public synchronized void addDNAttribute(String attributeName, String value) {
      this.checkEdit();
      List attributes = this.getDNAttributeValues(attributeName);
      if (attributes == null) {
         attributes = new ArrayList();
      }

      ((List)attributes).add(value);
      if (this.dnAttributes == null) {
         this.dnAttributes = new LinkedHashMap();
      }

      this.dnAttributes.put(attributeName, attributes);
   }

   public void addEscapedAttribute(String attributeName, String unescapedValue) {
      this.addAttribute(attributeName, escapeLDAPSpecialCharacters(unescapedValue));
   }

   public void addEscapedDNAttribute(String attributeName, String unescapedValue) {
      this.addDNAttribute(attributeName, escapeLDAPSpecialCharacters(unescapedValue));
   }

   public void addBinaryAttribute(String attributeName, String value) {
      try {
         byte[] valueBytes = value == null ? null : value.getBytes("UTF8");
         this.addAttribute(attributeName, valueBytes);
      } catch (UnsupportedEncodingException var4) {
         throw new RuntimeException(var4);
      }
   }

   public void addEscapedBinaryAttribute(String attributeName, String unescapedValue) {
      this.addBinaryAttribute(attributeName, escapeLDAPSpecialCharacters(unescapedValue));
   }

   public String makeDNAttribute() {
      if (this.dnAttributes != null && this.dnAttributes.size() >= 1) {
         boolean first = true;
         StringBuffer dn = new StringBuffer();
         Iterator var3 = this.dnAttributes.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();

            String attrValue;
            for(Iterator var5 = ((List)entry.getValue()).iterator(); var5.hasNext(); dn.append((String)entry.getKey()).append("=").append(attrValue)) {
               attrValue = (String)var5.next();
               if (!first) {
                  dn.append(',');
               } else {
                  first = false;
               }
            }
         }

         return dn.toString();
      } else {
         throw new IllegalStateException("DN attributes are not initialized");
      }
   }

   public synchronized String updateDNStringAttribute() {
      this.checkEdit();
      String dn = this.makeDNAttribute();
      Collection attributes = this.getStringAttributeValues("dn");
      if (attributes == null) {
         attributes = new ArrayList();
      } else {
         ((Collection)attributes).clear();
      }

      ((Collection)attributes).add(dn);
      if (this.stringAttributes == null) {
         this.stringAttributes = new HashMap();
      }

      this.stringAttributes.put("dn", attributes);
      return dn;
   }
}
