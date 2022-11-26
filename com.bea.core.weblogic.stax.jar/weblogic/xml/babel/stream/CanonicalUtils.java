package weblogic.xml.babel.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.utils.UnsyncStringBuffer;
import weblogic.xml.babel.reader.XmlChars;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;

public class CanonicalUtils {
   private static boolean containSpecialCharacter(char[] chars) {
      int i = 0;

      while(i < chars.length) {
         switch (chars[i]) {
            case '\u0000':
            case '\t':
            case '\n':
            case '\r':
            case '"':
            case '&':
            case '<':
            case '>':
            case '\ufffe':
            case '\uffff':
               return true;
            default:
               if (!XmlChars.isChar(chars[i])) {
                  return true;
               }

               ++i;
         }
      }

      return false;
   }

   private static String slowNormalizeCharacters(char[] chars, boolean isAttributeValue) {
      UnsyncStringBuffer buf = new UnsyncStringBuffer(128);

      for(int i = 0; i < chars.length; ++i) {
         switch (chars[i]) {
            case '\u0000':
               buf.append("&#x0;");
               break;
            case '\t':
               if (isAttributeValue) {
                  buf.append("&#x9;");
               } else {
                  buf.append('\t');
               }
               break;
            case '\n':
               if (isAttributeValue) {
                  buf.append("&#xA;");
               } else {
                  buf.append('\n');
               }
               break;
            case '\r':
               buf.append("&#xD;");
               break;
            case '"':
               if (isAttributeValue) {
                  buf.append("&quot;");
               } else {
                  buf.append('"');
               }
               break;
            case '&':
               buf.append("&amp;");
               break;
            case '<':
               buf.append("&lt;");
               break;
            case '>':
               if (!isAttributeValue) {
                  buf.append("&gt;");
               } else {
                  buf.append('>');
               }
               break;
            case '\ufffe':
               buf.append("&#xFFFE;");
               break;
            case '\uffff':
               buf.append("&#xFFFF;");
               break;
            default:
               if (!XmlChars.isChar(chars[i])) {
                  buf.append("&#");
                  buf.append(Integer.toString(chars[i]));
                  buf.append(';');
               } else {
                  buf.append(chars[i]);
               }
         }
      }

      return buf.toString();
   }

   public static String normalizeCharacters(String characters, boolean isAttributeValue) {
      if (characters.length() == 0) {
         return null;
      } else {
         char[] chars = characters.toCharArray();
         return containSpecialCharacter(chars) ? slowNormalizeCharacters(chars, isAttributeValue) : characters;
      }
   }

   public static AttributeIterator sortNamespaces(AttributeIterator atts1, AttributeIterator atts2) {
      SortedMap map = new TreeMap();

      Attribute att;
      while(atts1.hasNext()) {
         att = atts1.next();
         map.put(att.getName().getQualifiedName(), att);
      }

      while(atts2.hasNext()) {
         att = atts2.next();
         map.put(att.getName().getQualifiedName(), att);
      }

      return ElementFactory.createAttributeIterator(map.values().iterator());
   }

   public static AttributeIterator sortNamespaces(AttributeIterator atts) {
      SortedMap map = new TreeMap();

      while(atts.hasNext()) {
         Attribute att = atts.next();
         map.put(att.getName().getQualifiedName(), att);
      }

      return ElementFactory.createAttributeIterator(map.values().iterator());
   }

   public static AttributeIterator sortAttributes(AttributeIterator atts) {
      SortedMap map = new TreeMap(new Comparator() {
         public int compare(Object o1, Object o2) {
            XMLName n1 = (XMLName)o1;
            XMLName n2 = (XMLName)o2;
            String uri1 = n1.getNamespaceUri();
            String uri2 = n2.getNamespaceUri();
            if (uri1 == null && uri2 != null) {
               return -1;
            } else if (uri2 == null && uri1 != null) {
               return 1;
            } else {
               int i = 0;
               if (uri1 != null && uri2 != null) {
                  i = uri1.compareTo(uri2);
               }

               if (i == 0) {
                  i = n1.getLocalName().compareTo(n2.getLocalName());
               }

               return i;
            }
         }
      });

      while(atts.hasNext()) {
         Attribute att = atts.next();
         map.put(att.getName(), att);
      }

      return ElementFactory.createAttributeIterator(map.values().iterator());
   }

   public static AttributeIterator mergeNamespaces(AttributeIterator[] attsArray) {
      List list = new ArrayList();

      for(int i = 0; i < attsArray.length; ++i) {
         AttributeIterator atts = attsArray[i];

         while(atts.hasNext()) {
            Attribute att = atts.next();
            list.add(att);
         }
      }

      return ElementFactory.createAttributeIterator(list.iterator());
   }
}
