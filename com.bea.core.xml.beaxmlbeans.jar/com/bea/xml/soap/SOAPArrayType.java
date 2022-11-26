package com.bea.xml.soap;

import com.bea.xbean.common.PrefixResolver;
import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.common.XmlWhitespace;
import com.bea.xbean.values.XmlValueOutOfRangeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;

public final class SOAPArrayType {
   private QName _type;
   private int[] _ranks;
   private int[] _dimensions;
   private static int[] EMPTY_INT_ARRAY = new int[0];

   public boolean isSameRankAs(SOAPArrayType otherType) {
      if (this._ranks.length != otherType._ranks.length) {
         return false;
      } else {
         for(int i = 0; i < this._ranks.length; ++i) {
            if (this._ranks[i] != otherType._ranks[i]) {
               return false;
            }
         }

         if (this._dimensions.length != otherType._dimensions.length) {
            return false;
         } else {
            return true;
         }
      }
   }

   public static int[] parseSoap11Index(String inbraces) {
      inbraces = XmlWhitespace.collapse(inbraces, 3);
      if (inbraces.startsWith("[") && inbraces.endsWith("]")) {
         return internalParseCommaIntString(inbraces.substring(1, inbraces.length() - 1));
      } else {
         throw new IllegalArgumentException("Misformed SOAP 1.1 index: must be contained in braces []");
      }
   }

   private static int[] internalParseCommaIntString(String csl) {
      List dimStrings = new ArrayList();
      int i = 0;

      while(true) {
         int j = csl.indexOf(44, i);
         if (j < 0) {
            dimStrings.add(csl.substring(i));
            int[] result = new int[dimStrings.size()];
            i = 0;

            for(Iterator it = dimStrings.iterator(); it.hasNext(); ++i) {
               String dimString = XmlWhitespace.collapse((String)it.next(), 3);
               if (!dimString.equals("*") && !dimString.equals("")) {
                  try {
                     result[i] = Integer.parseInt(dimString);
                  } catch (Exception var7) {
                     throw new XmlValueOutOfRangeException("Malformed integer in SOAP array index");
                  }
               } else {
                  result[i] = -1;
               }
            }

            return result;
         }

         dimStrings.add(csl.substring(i, j));
         i = j + 1;
      }
   }

   public SOAPArrayType(String s, PrefixResolver m) {
      int firstbrace = s.indexOf(91);
      if (firstbrace < 0) {
         throw new XmlValueOutOfRangeException();
      } else {
         String firstpart = XmlWhitespace.collapse(s.substring(0, firstbrace), 3);
         int firstcolon = firstpart.indexOf(58);
         String prefix = "";
         if (firstcolon >= 0) {
            prefix = firstpart.substring(0, firstcolon);
         }

         String uri = m.getNamespaceForPrefix(prefix);
         if (uri == null) {
            throw new XmlValueOutOfRangeException();
         } else {
            this._type = QNameHelper.forLNS(firstpart.substring(firstcolon + 1), uri);
            this.initDimensions(s, firstbrace);
         }
      }
   }

   public SOAPArrayType(QName name, String dimensions) {
      int firstbrace = dimensions.indexOf(91);
      if (firstbrace < 0) {
         this._type = name;
         this._ranks = EMPTY_INT_ARRAY;
         dimensions = XmlWhitespace.collapse(dimensions, 3);
         String[] dimStrings = dimensions.split(" ");

         for(int i = 0; i < dimStrings.length; ++i) {
            String dimString = dimStrings[i];
            if (dimString.equals("*")) {
               this._dimensions[i] = -1;
            } else {
               try {
                  this._dimensions[i] = Integer.parseInt(dimStrings[i]);
               } catch (Exception var8) {
                  throw new XmlValueOutOfRangeException();
               }
            }
         }
      } else {
         this._type = name;
         this.initDimensions(dimensions, firstbrace);
      }

   }

   public SOAPArrayType(SOAPArrayType nested, int[] dimensions) {
      this._type = nested._type;
      this._ranks = new int[nested._ranks.length + 1];
      System.arraycopy(nested._ranks, 0, this._ranks, 0, nested._ranks.length);
      this._ranks[this._ranks.length - 1] = nested._dimensions.length;
      this._dimensions = new int[dimensions.length];
      System.arraycopy(dimensions, 0, this._dimensions, 0, dimensions.length);
   }

   private void initDimensions(String s, int firstbrace) {
      List braces = new ArrayList();
      int lastbrace = -1;

      for(int i = firstbrace; i >= 0; i = s.indexOf(91, lastbrace)) {
         lastbrace = s.indexOf(93, i);
         if (lastbrace < 0) {
            throw new XmlValueOutOfRangeException();
         }

         braces.add(s.substring(i + 1, lastbrace));
      }

      String trailer = s.substring(lastbrace + 1);
      if (!XmlWhitespace.isAllSpace(trailer)) {
         throw new XmlValueOutOfRangeException();
      } else {
         this._ranks = new int[braces.size() - 1];

         for(int i = 0; i < this._ranks.length; ++i) {
            String commas = (String)braces.get(i);
            int commacount = 0;

            for(int j = 0; j < commas.length(); ++j) {
               char ch = commas.charAt(j);
               if (ch == ',') {
                  ++commacount;
               } else if (!XmlWhitespace.isSpace(ch)) {
                  throw new XmlValueOutOfRangeException();
               }
            }

            this._ranks[i] = commacount + 1;
         }

         this._dimensions = internalParseCommaIntString((String)braces.get(braces.size() - 1));
      }
   }

   public QName getQName() {
      return this._type;
   }

   public int[] getRanks() {
      int[] result = new int[this._ranks.length];
      System.arraycopy(this._ranks, 0, result, 0, result.length);
      return result;
   }

   public int[] getDimensions() {
      int[] result = new int[this._dimensions.length];
      System.arraycopy(this._dimensions, 0, result, 0, result.length);
      return result;
   }

   public boolean containsNestedArrays() {
      return this._ranks.length > 0;
   }

   public String soap11DimensionString() {
      return this.soap11DimensionString(this._dimensions);
   }

   public String soap11DimensionString(int[] actualDimensions) {
      StringBuffer sb = new StringBuffer();

      int i;
      for(i = 0; i < this._ranks.length; ++i) {
         sb.append('[');

         for(int j = 1; j < this._ranks[i]; ++j) {
            sb.append(',');
         }

         sb.append(']');
      }

      sb.append('[');

      for(i = 0; i < actualDimensions.length; ++i) {
         if (i > 0) {
            sb.append(',');
         }

         if (actualDimensions[i] >= 0) {
            sb.append(actualDimensions[i]);
         }
      }

      sb.append(']');
      return sb.toString();
   }

   private SOAPArrayType() {
   }

   public static SOAPArrayType newSoap12Array(QName itemType, String arraySize) {
      int[] ranks = EMPTY_INT_ARRAY;
      arraySize = XmlWhitespace.collapse(arraySize, 3);
      String[] dimStrings = arraySize.split(" ");
      int[] dimensions = new int[dimStrings.length];

      for(int i = 0; i < dimStrings.length; ++i) {
         String dimString = dimStrings[i];
         if (i == 0 && dimString.equals("*")) {
            dimensions[i] = -1;
         } else {
            try {
               dimensions[i] = Integer.parseInt(dimStrings[i]);
            } catch (Exception var8) {
               throw new XmlValueOutOfRangeException();
            }
         }
      }

      SOAPArrayType sot = new SOAPArrayType();
      sot._ranks = ranks;
      sot._type = itemType;
      sot._dimensions = dimensions;
      return sot;
   }

   public String soap12DimensionString(int[] actualDimensions) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < actualDimensions.length; ++i) {
         if (i > 0) {
            sb.append(' ');
         }

         if (actualDimensions[i] >= 0) {
            sb.append(actualDimensions[i]);
         }
      }

      return sb.toString();
   }

   public SOAPArrayType nestedArrayType() {
      if (!this.containsNestedArrays()) {
         throw new IllegalStateException();
      } else {
         SOAPArrayType result = new SOAPArrayType();
         result._type = this._type;
         result._ranks = new int[this._ranks.length - 1];
         System.arraycopy(this._ranks, 0, result._ranks, 0, result._ranks.length);
         result._dimensions = new int[this._ranks[this._ranks.length - 1]];

         for(int i = 0; i < result._dimensions.length; ++i) {
            result._dimensions[i] = -1;
         }

         return result;
      }
   }

   public int hashCode() {
      return this._type.hashCode() + this._dimensions.length + this._ranks.length + (this._dimensions.length == 0 ? 0 : this._dimensions[0]);
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!obj.getClass().equals(this.getClass())) {
         return false;
      } else {
         SOAPArrayType sat = (SOAPArrayType)obj;
         if (!this._type.equals(sat._type)) {
            return false;
         } else if (this._ranks.length != sat._ranks.length) {
            return false;
         } else if (this._dimensions.length != sat._dimensions.length) {
            return false;
         } else {
            int i;
            for(i = 0; i < this._ranks.length; ++i) {
               if (this._ranks[i] != sat._ranks[i]) {
                  return false;
               }
            }

            for(i = 0; i < this._dimensions.length; ++i) {
               if (this._dimensions[i] != sat._dimensions[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
