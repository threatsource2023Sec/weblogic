package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

public class MimeType implements Comparable, Serializable {
   private static final long serialVersionUID = 4085923477777865903L;
   protected static final String WILDCARD_TYPE = "*";
   private static final String PARAM_CHARSET = "charset";
   private static final BitSet TOKEN;
   private final String type;
   private final String subtype;
   private final Map parameters;

   public MimeType(String type) {
      this(type, "*");
   }

   public MimeType(String type, String subtype) {
      this(type, subtype, Collections.emptyMap());
   }

   public MimeType(String type, String subtype, Charset charset) {
      this(type, subtype, Collections.singletonMap("charset", charset.name()));
   }

   public MimeType(MimeType other, Charset charset) {
      this(other.getType(), other.getSubtype(), addCharsetParameter(charset, other.getParameters()));
   }

   public MimeType(MimeType other, @Nullable Map parameters) {
      this(other.getType(), other.getSubtype(), parameters);
   }

   public MimeType(String type, String subtype, @Nullable Map parameters) {
      Assert.hasLength(type, "'type' must not be empty");
      Assert.hasLength(subtype, "'subtype' must not be empty");
      this.checkToken(type);
      this.checkToken(subtype);
      this.type = type.toLowerCase(Locale.ENGLISH);
      this.subtype = subtype.toLowerCase(Locale.ENGLISH);
      if (!CollectionUtils.isEmpty(parameters)) {
         Map map = new LinkedCaseInsensitiveMap(parameters.size(), Locale.ENGLISH);
         parameters.forEach((attribute, value) -> {
            this.checkParameters(attribute, value);
            map.put(attribute, value);
         });
         this.parameters = Collections.unmodifiableMap(map);
      } else {
         this.parameters = Collections.emptyMap();
      }

   }

   private void checkToken(String token) {
      for(int i = 0; i < token.length(); ++i) {
         char ch = token.charAt(i);
         if (!TOKEN.get(ch)) {
            throw new IllegalArgumentException("Invalid token character '" + ch + "' in token \"" + token + "\"");
         }
      }

   }

   protected void checkParameters(String attribute, String value) {
      Assert.hasLength(attribute, "'attribute' must not be empty");
      Assert.hasLength(value, "'value' must not be empty");
      this.checkToken(attribute);
      if ("charset".equals(attribute)) {
         value = this.unquote(value);
         Charset.forName(value);
      } else if (!this.isQuotedString(value)) {
         this.checkToken(value);
      }

   }

   private boolean isQuotedString(String s) {
      if (s.length() < 2) {
         return false;
      } else {
         return s.startsWith("\"") && s.endsWith("\"") || s.startsWith("'") && s.endsWith("'");
      }
   }

   protected String unquote(String s) {
      return this.isQuotedString(s) ? s.substring(1, s.length() - 1) : s;
   }

   public boolean isWildcardType() {
      return "*".equals(this.getType());
   }

   public boolean isWildcardSubtype() {
      return "*".equals(this.getSubtype()) || this.getSubtype().startsWith("*+");
   }

   public boolean isConcrete() {
      return !this.isWildcardType() && !this.isWildcardSubtype();
   }

   public String getType() {
      return this.type;
   }

   public String getSubtype() {
      return this.subtype;
   }

   @Nullable
   public Charset getCharset() {
      String charset = this.getParameter("charset");
      return charset != null ? Charset.forName(this.unquote(charset)) : null;
   }

   @Nullable
   public String getParameter(String name) {
      return (String)this.parameters.get(name);
   }

   public Map getParameters() {
      return this.parameters;
   }

   public boolean includes(@Nullable MimeType other) {
      if (other == null) {
         return false;
      } else if (this.isWildcardType()) {
         return true;
      } else {
         if (this.getType().equals(other.getType())) {
            if (this.getSubtype().equals(other.getSubtype())) {
               return true;
            }

            if (this.isWildcardSubtype()) {
               int thisPlusIdx = this.getSubtype().lastIndexOf(43);
               if (thisPlusIdx == -1) {
                  return true;
               }

               int otherPlusIdx = other.getSubtype().lastIndexOf(43);
               if (otherPlusIdx != -1) {
                  String thisSubtypeNoSuffix = this.getSubtype().substring(0, thisPlusIdx);
                  String thisSubtypeSuffix = this.getSubtype().substring(thisPlusIdx + 1);
                  String otherSubtypeSuffix = other.getSubtype().substring(otherPlusIdx + 1);
                  if (thisSubtypeSuffix.equals(otherSubtypeSuffix) && "*".equals(thisSubtypeNoSuffix)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean isCompatibleWith(@Nullable MimeType other) {
      if (other == null) {
         return false;
      } else if (!this.isWildcardType() && !other.isWildcardType()) {
         if (this.getType().equals(other.getType())) {
            if (this.getSubtype().equals(other.getSubtype())) {
               return true;
            }

            if (this.isWildcardSubtype() || other.isWildcardSubtype()) {
               int thisPlusIdx = this.getSubtype().lastIndexOf(43);
               int otherPlusIdx = other.getSubtype().lastIndexOf(43);
               if (thisPlusIdx == -1 && otherPlusIdx == -1) {
                  return true;
               }

               if (thisPlusIdx != -1 && otherPlusIdx != -1) {
                  String thisSubtypeNoSuffix = this.getSubtype().substring(0, thisPlusIdx);
                  String otherSubtypeNoSuffix = other.getSubtype().substring(0, otherPlusIdx);
                  String thisSubtypeSuffix = this.getSubtype().substring(thisPlusIdx + 1);
                  String otherSubtypeSuffix = other.getSubtype().substring(otherPlusIdx + 1);
                  if (thisSubtypeSuffix.equals(otherSubtypeSuffix) && ("*".equals(thisSubtypeNoSuffix) || "*".equals(otherSubtypeNoSuffix))) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean equalsTypeAndSubtype(@Nullable MimeType other) {
      if (other == null) {
         return false;
      } else {
         return this.type.equalsIgnoreCase(other.type) && this.subtype.equalsIgnoreCase(other.subtype);
      }
   }

   public boolean isPresentIn(Collection mimeTypes) {
      Iterator var2 = mimeTypes.iterator();

      MimeType mimeType;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         mimeType = (MimeType)var2.next();
      } while(!mimeType.equalsTypeAndSubtype(this));

      return true;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MimeType)) {
         return false;
      } else {
         MimeType otherType = (MimeType)other;
         return this.type.equalsIgnoreCase(otherType.type) && this.subtype.equalsIgnoreCase(otherType.subtype) && this.parametersAreEqual(otherType);
      }
   }

   private boolean parametersAreEqual(MimeType other) {
      if (this.parameters.size() != other.parameters.size()) {
         return false;
      } else {
         Iterator var2 = this.parameters.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String key = (String)entry.getKey();
            if (!other.parameters.containsKey(key)) {
               return false;
            }

            if ("charset".equals(key)) {
               if (!ObjectUtils.nullSafeEquals(this.getCharset(), other.getCharset())) {
                  return false;
               }
            } else if (!ObjectUtils.nullSafeEquals(entry.getValue(), other.parameters.get(key))) {
               return false;
            }
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.type.hashCode();
      result = 31 * result + this.subtype.hashCode();
      result = 31 * result + this.parameters.hashCode();
      return result;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      this.appendTo(builder);
      return builder.toString();
   }

   protected void appendTo(StringBuilder builder) {
      builder.append(this.type);
      builder.append('/');
      builder.append(this.subtype);
      this.appendTo(this.parameters, builder);
   }

   private void appendTo(Map map, StringBuilder builder) {
      map.forEach((key, val) -> {
         builder.append(';');
         builder.append(key);
         builder.append('=');
         builder.append(val);
      });
   }

   public int compareTo(MimeType other) {
      int comp = this.getType().compareToIgnoreCase(other.getType());
      if (comp != 0) {
         return comp;
      } else {
         comp = this.getSubtype().compareToIgnoreCase(other.getSubtype());
         if (comp != 0) {
            return comp;
         } else {
            comp = this.getParameters().size() - other.getParameters().size();
            if (comp != 0) {
               return comp;
            } else {
               TreeSet thisAttributes = new TreeSet(String.CASE_INSENSITIVE_ORDER);
               thisAttributes.addAll(this.getParameters().keySet());
               TreeSet otherAttributes = new TreeSet(String.CASE_INSENSITIVE_ORDER);
               otherAttributes.addAll(other.getParameters().keySet());
               Iterator thisAttributesIterator = thisAttributes.iterator();
               Iterator otherAttributesIterator = otherAttributes.iterator();

               while(thisAttributesIterator.hasNext()) {
                  String thisAttribute = (String)thisAttributesIterator.next();
                  String otherAttribute = (String)otherAttributesIterator.next();
                  comp = thisAttribute.compareToIgnoreCase(otherAttribute);
                  if (comp != 0) {
                     return comp;
                  }

                  if ("charset".equals(thisAttribute)) {
                     Charset thisCharset = this.getCharset();
                     Charset otherCharset = other.getCharset();
                     if (thisCharset != otherCharset) {
                        if (thisCharset == null) {
                           return -1;
                        }

                        if (otherCharset == null) {
                           return 1;
                        }

                        comp = thisCharset.compareTo(otherCharset);
                        if (comp != 0) {
                           return comp;
                        }
                     }
                  } else {
                     String thisValue = (String)this.getParameters().get(thisAttribute);
                     String otherValue = (String)other.getParameters().get(otherAttribute);
                     if (otherValue == null) {
                        otherValue = "";
                     }

                     comp = thisValue.compareTo(otherValue);
                     if (comp != 0) {
                        return comp;
                     }
                  }
               }

               return 0;
            }
         }
      }
   }

   public static MimeType valueOf(String value) {
      return MimeTypeUtils.parseMimeType(value);
   }

   private static Map addCharsetParameter(Charset charset, Map parameters) {
      Map map = new LinkedHashMap(parameters);
      map.put("charset", charset.name());
      return map;
   }

   static {
      BitSet ctl = new BitSet(128);

      for(int i = 0; i <= 31; ++i) {
         ctl.set(i);
      }

      ctl.set(127);
      BitSet separators = new BitSet(128);
      separators.set(40);
      separators.set(41);
      separators.set(60);
      separators.set(62);
      separators.set(64);
      separators.set(44);
      separators.set(59);
      separators.set(58);
      separators.set(92);
      separators.set(34);
      separators.set(47);
      separators.set(91);
      separators.set(93);
      separators.set(63);
      separators.set(61);
      separators.set(123);
      separators.set(125);
      separators.set(32);
      separators.set(9);
      TOKEN = new BitSet(128);
      TOKEN.set(0, 128);
      TOKEN.andNot(ctl);
      TOKEN.andNot(separators);
   }

   public static class SpecificityComparator implements Comparator {
      public int compare(MimeType mimeType1, MimeType mimeType2) {
         if (mimeType1.isWildcardType() && !mimeType2.isWildcardType()) {
            return 1;
         } else if (mimeType2.isWildcardType() && !mimeType1.isWildcardType()) {
            return -1;
         } else if (!mimeType1.getType().equals(mimeType2.getType())) {
            return 0;
         } else if (mimeType1.isWildcardSubtype() && !mimeType2.isWildcardSubtype()) {
            return 1;
         } else if (mimeType2.isWildcardSubtype() && !mimeType1.isWildcardSubtype()) {
            return -1;
         } else {
            return !mimeType1.getSubtype().equals(mimeType2.getSubtype()) ? 0 : this.compareParameters(mimeType1, mimeType2);
         }
      }

      protected int compareParameters(MimeType mimeType1, MimeType mimeType2) {
         int paramsSize1 = mimeType1.getParameters().size();
         int paramsSize2 = mimeType2.getParameters().size();
         return Integer.compare(paramsSize2, paramsSize1);
      }
   }
}
