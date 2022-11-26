package weblogic.xml.schema.binding.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.utils.StringUtils;
import weblogic.xml.schema.binding.BindingConfiguration;
import weblogic.xml.schema.types.XSDNCName;
import weblogic.xml.schema.types.util.NameValidator;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.XMLName;

public final class NameUtil {
   private static final boolean ASSERT = true;
   public static final char HYPHEN = '-';
   public static final char PERIOD = '.';
   public static final char COLON = ':';
   public static final char USCORE = '_';
   public static final char DOT = '·';
   public static final char TELEIA = '·';
   public static final char AYAH = '\u06dd';
   public static final char ELHIZB = '۞';
   private static final boolean DEBUG = false;
   public static final String[] javaKeywords = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "continue", "const", "default", "do", "double", "else", "enum", "extends", "for", "false", "final", "finally", "float", "goto", "if", "implements", "import", "int", "interface", "instanceof", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "threadsafe", "throw", "throws", "transient", "true", "try", "throws", "volatile", "void", "while"};
   private static final Set javaWords = buildKeywords();
   private static final String LANG_PREFIX = "java.";
   private static final int ENDSTR = -1;
   private static final int SKIP = 99;
   private static final int PUNCT = 100;
   private static final int DIGIT = 101;
   private static final int MARK = 102;
   private static final int LETTER_UPPER = 110;
   private static final int LETTER_LOWER = 111;
   private static final int LETTER_NOCASE = 112;

   public static boolean isValidJavaIdentifer(String id) {
      if (id == null) {
         throw new NullPointerException("id cannot be null");
      } else {
         int len = id.length();
         if (len < 1) {
            return false;
         } else if (javaWords.contains(id)) {
            return false;
         } else if (!Character.isJavaIdentifierStart(id.charAt(0))) {
            return false;
         } else if (len == 1) {
            return true;
         } else {
            for(int i = 1; i < len; ++i) {
               if (!Character.isJavaIdentifierPart(id.charAt(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static String getJAXRPCClassName(String name) {
      name = getJavaName(name, false);
      name = uppercaseFirstLetter(name);
      if (!isValidJavaIdentifer(name)) {
         name = fixWordForJAXRPC(name);
      }

      return name;
   }

   public static String getJAXRPCMethodName(String name) {
      name = getJavaName(name, false);
      name = lowercaseFirstLetter(name);
      if (!isValidJavaIdentifer(name)) {
         name = fixWordForJAXRPC(name);
      }

      return name;
   }

   public static XMLName getXMLNameFromClass(Class clazz, BindingConfiguration conf) {
      String uri = getNamespaceFromPackage(clazz, conf);
      String lname = getRootClassNameFromClass(clazz.getName());
      StringBuffer buf = null;
      int i = 0;

      for(int len = lname.length(); i < len; ++i) {
         char c = lname.charAt(i);
         if (!NameValidator.validNCNameChar(c)) {
            if (buf == null) {
               buf = new StringBuffer(lname);
            }

            buf.setCharAt(i, '_');
         }
      }

      if (buf != null) {
         lname = buf.toString();
      }

      XMLName xsd_name = ElementFactory.createXMLName(uri, lname);
      XSDNCName.validateXml(xsd_name.getLocalName());
      return xsd_name;
   }

   public static String getClassNameFromXMLName(XMLName xname) {
      String java_type = getClassName(xname.getLocalName());
      String uri = xname.getNamespaceUri();
      String java_pkg = null;
      if (uri != null) {
         java_pkg = getPackageFromNamespace(uri);
      }

      return java_pkg != null ? java_pkg + "." + java_type : java_type;
   }

   public static String getNamespaceFromPackage(Class clazz, BindingConfiguration c) {
      Class curr_clazz;
      for(curr_clazz = clazz; curr_clazz.isArray(); curr_clazz = curr_clazz.getComponentType()) {
      }

      String pkg_name = getPackageNameFromClass(curr_clazz.getName());
      String mapped = c.getTargetNamespace(pkg_name);
      if (mapped != null) {
         return mapped;
      } else {
         String fixed_tns = c.getFixedTargetNamespace();
         if (fixed_tns != null) {
            return fixed_tns;
         } else {
            if (curr_clazz.isPrimitive()) {
               pkg_name = c.getJavaLanguageNamespaceUri();
            } else if (pkg_name.startsWith("java.")) {
               String rem_str = pkg_name.substring("java.".length());
               pkg_name = c.getJavaLanguageNamespaceUri() + "." + rem_str;
            } else if (c.isReversePackageIntoUrl()) {
               String[] parts = StringUtils.split(pkg_name, '.');
               StringBuffer buf = new StringBuffer(pkg_name.length());

               for(int i = parts.length - 1; i > 0; --i) {
                  buf.append(parts[i]);
                  buf.append('.');
               }

               buf.append(parts[0]);
               pkg_name = buf.toString();
            }

            return c.getTargetNamespacePrefix() + pkg_name;
         }
      }
   }

   public static String getPackageFromNamespace(String ns) {
      if (ns == null) {
         throw new IllegalArgumentException("namespace cannot be null");
      } else {
         if (ns.startsWith("java:")) {
            ns = ns.substring("java:".length());
         }

         return getPackageName(massageUri(ns));
      }
   }

   private static String massageUri(String uri) {
      if (!uri.startsWith("http://") && !uri.startsWith("ftp://") && !uri.startsWith("https://")) {
         return uri;
      } else {
         try {
            URL url = new URL(uri);
            String host = url.getHost();
            StringTokenizer st = new StringTokenizer(host, ".");
            List parts = new ArrayList();

            while(st.hasMoreTokens()) {
               String part = st.nextToken();
               parts.add(part);
            }

            StringBuffer result = new StringBuffer(uri.length());
            int sz = parts.size();

            for(int i = sz - 1; i >= 0; --i) {
               result.append(parts.get(i));
               result.append(".");
            }

            if (url.getFile() != null) {
               result.append(url.getFile());
            }

            if (url.getRef() != null) {
               result.append("#");
               result.append(url.getRef());
            }

            return result.toString();
         } catch (MalformedURLException var8) {
            return uri;
         }
      }
   }

   public static String getClassName(String xml_name) {
      return getJavaName(xml_name);
   }

   public static String getPropertyName(String xml_name) {
      String jname = getJavaName(xml_name);
      return lowercaseFirstLetter(jname);
   }

   public static String getMethodName(String xml_name) {
      String jname = getJavaName(xml_name);
      return lowercaseFirstLetter(jname);
   }

   public static String getPackageName(String in_str) {
      if (in_str == null) {
         throw new IllegalArgumentException("string must not be null");
      } else {
         int len = in_str.length();
         if (len == 0) {
            return "";
         } else {
            List tokens = new ArrayList();
            int begin_idx = 0;
            int end_idx = 0;
            boolean within_str = false;

            int cnt;
            for(cnt = 0; cnt < len; ++cnt) {
               char c = in_str.charAt(cnt);
               if (!Character.isJavaIdentifierPart(c)) {
                  if (within_str) {
                     String tok = in_str.substring(begin_idx, 1 + end_idx);
                     tokens.add(fixWord(tok));
                  }

                  within_str = false;
               } else {
                  if (!within_str) {
                     begin_idx = cnt;
                  }

                  within_str = true;
                  end_idx = cnt;
               }
            }

            if (within_str) {
               String tok = in_str.substring(begin_idx, 1 + end_idx);
               tokens.add(fixWord(tok));
            }

            cnt = tokens.size() - 1;
            if (cnt < 0) {
               return "";
            } else if (cnt == 0) {
               return (String)tokens.get(0);
            } else {
               StringBuffer buf = new StringBuffer(len);

               for(int i = 0; i < cnt; ++i) {
                  buf.append(tokens.get(i));
                  buf.append('.');
               }

               buf.append(tokens.get(cnt));
               return buf.toString();
            }
         }
      }
   }

   public static String getConstantName(String xml_name) {
      StringBuffer buf = new StringBuffer();
      List words = split(xml_name, false);
      int sz = words.size() - 1;

      int len;
      for(len = 0; len < sz; ++len) {
         buf.append((String)words.get(len));
         buf.append('_');
      }

      if (sz >= 0) {
         buf.append((String)words.get(sz));
      }

      len = buf.length();

      for(int j = 0; j < len; ++j) {
         char c = buf.charAt(j);
         buf.setCharAt(j, Character.toUpperCase(c));
      }

      return buf.toString();
   }

   public static String getJavaName(String xml_name) {
      return getJavaName(xml_name, true);
   }

   public static String getJavaName(String xml_name, boolean fix) {
      StringBuffer buf = new StringBuffer();
      List words = split(xml_name, true);
      if (words.size() > 0) {
         String first = (String)words.get(0);
         char f = first.charAt(0);
         if (!Character.isJavaIdentifierStart(f)) {
            buf.append("J");
         }

         Iterator itr = words.iterator();

         while(itr.hasNext()) {
            buf.append((String)itr.next());
         }
      }

      return fix ? fixWord(buf.toString()) : buf.toString();
   }

   public static String upcaseFirstLetter(String s) {
      if (s.length() < 1) {
         return s;
      } else {
         StringBuffer buf = new StringBuffer(s);
         char fc = buf.charAt(0);
         buf.setCharAt(0, Character.toUpperCase(fc));
         return buf.toString();
      }
   }

   public static String lowercaseFirstLetter(String s) {
      if (s.length() < 1) {
         return s;
      } else {
         StringBuffer buf = new StringBuffer(s);
         char fc = buf.charAt(0);
         buf.setCharAt(0, Character.toLowerCase(fc));
         return buf.toString();
      }
   }

   public static String uppercaseFirstLetter(String s) {
      if (s.length() < 1) {
         return s;
      } else {
         StringBuffer buf = new StringBuffer(s);
         char fc = buf.charAt(0);
         buf.setCharAt(0, Character.toUpperCase(fc));
         return buf.toString();
      }
   }

   public static String getRootClassNameFromClass(String fullClassName) {
      int i = fullClassName.lastIndexOf(46);
      return i == -1 ? fullClassName : fullClassName.substring(i + 1);
   }

   public static String getPackageNameFromClass(String fullClassName) {
      boolean is_array = false;
      if (fullClassName.startsWith("[")) {
         is_array = true;
         int last_bracket = fullClassName.lastIndexOf(91);
         int semi = fullClassName.indexOf(59, last_bracket);
         if (semi == -1) {
            fullClassName = fullClassName.substring(last_bracket + 2);
         } else {
            fullClassName = fullClassName.substring(last_bracket + 2, semi);
         }
      }

      String pkg = getPackageNameFromNonArrayClass(fullClassName);
      return pkg.length() < 1 && is_array ? "java.lang" : pkg;
   }

   private static String getPackageNameFromNonArrayClass(String fullClassName) {
      int i = fullClassName.lastIndexOf(46);
      return i == -1 ? "" : fullClassName.substring(0, i);
   }

   public static String getPackageNameFromClass(Class clazz) {
      while(clazz.isArray()) {
         clazz = clazz.getComponentType();
      }

      Package pkg = clazz.getPackage();
      if (pkg == null) {
         return "";
      } else {
         return pkg.getName();
      }
   }

   public static String concatPackage(String pkg, String clazz) {
      if (pkg != null && pkg.length() > 0) {
         return clazz != null && clazz.length() > 0 ? pkg + "." + clazz : pkg;
      } else {
         return clazz;
      }
   }

   public static String getHolderPackage(String pkg) {
      return pkg != null && pkg.length() > 0 ? concatPackage(pkg, "holders") : pkg;
   }

   public static String getHolderClass(String clazz) {
      return clazz + "Holder";
   }

   public static List split(String ncname, boolean upcase) {
      List words = new ArrayList();
      if (ncname == null) {
         return words;
      } else {
         StringBuffer curr_word = new StringBuffer();
         SplitState state = new SplitState();
         state.idx = 0;
         state.str = ncname;
         state.len = ncname.length();
         int ci = true;
         int ci1 = getNextGoodChar(state);

         while(state.idx < state.len) {
            int c_state = state.type;
            if (state.type != 100) {
               curr_word.append((char)ci1);
            }

            ++state.idx;
            ci1 = getNextGoodChar(state);
            if (ci1 == -1 || isBorder(c_state, state.type)) {
               if (curr_word.length() > 0) {
                  if (upcase) {
                     char fc = curr_word.charAt(0);
                     curr_word.setCharAt(0, Character.toUpperCase(fc));
                  }

                  words.add(curr_word.toString());
               }

               if (ci1 == -1) {
                  break;
               }

               curr_word = new StringBuffer();
            }
         }

         return words;
      }
   }

   private static int getNextGoodChar(SplitState state) {
      while(state.idx < state.len) {
         char c = state.str.charAt(state.idx);
         state.type = getCharClass(c);
         if (state.type != 99) {
            return c;
         }

         ++state.idx;
      }

      return -1;
   }

   public static int getCharClass(char c) {
      if (isPunctuation(c)) {
         return 100;
      } else if (Character.isDigit(c)) {
         return 101;
      } else if (Character.isUpperCase(c)) {
         return 110;
      } else if (Character.isLowerCase(c)) {
         return 111;
      } else if (Character.isLetter(c)) {
         return 112;
      } else {
         return Character.isJavaIdentifierPart(c) ? 102 : 99;
      }
   }

   private static boolean isBorder(int c1_state, int c2_state) {
      if (c1_state == 100 ^ c2_state == 100) {
         return true;
      } else if (c1_state == 101 ^ c2_state == 101) {
         return true;
      } else if (c1_state == 111 && c2_state != 111) {
         return true;
      } else {
         return isLetter(c1_state) ^ isLetter(c2_state);
      }
   }

   private static boolean isLetter(int state) {
      return state == 110 || state == 111 || state == 112;
   }

   public static boolean isPunctuation(char c) {
      return c == '-' || c == '.' || c == ':' || c == 183 || c == 903 || c == 1757 || c == 1758;
   }

   private static String fixFirstLetter(String word) {
      if (word.length() < 1) {
         return word;
      } else {
         char fc = word.charAt(0);
         return !Character.isJavaIdentifierStart(fc) ? 'x' + word : word;
      }
   }

   private static String fixWord(String word) {
      if (isJavaReservedWord(word)) {
         word = word + '0';
      }

      return fixFirstLetter(word);
   }

   private static String fixWordForJAXRPC(String word) {
      if (isJavaReservedWord(word)) {
         word = "_" + word;
      }

      return word;
   }

   private static boolean isJavaReservedWord(String word) {
      return isJavaReservedWord(word, true);
   }

   private static boolean isJavaReservedWord(String word, boolean ignore_case) {
      if (ignore_case) {
         word = word.toLowerCase(Locale.ENGLISH);
      }

      return javaWords.contains(word);
   }

   public static void main(String[] args) throws Exception {
      char rn = true;
      System.out.println("rn=Ⅰ\tnum=" + Character.getNumericValue('Ⅰ'));
      StringBuffer test1 = new StringBuffer("Foo");
      test1.append('Ⅰ');
      test1.append("bar");
      String test1str = test1.toString();
      test1.append('Ⅰ');
      test1.append('Ⅰ');
      test1.append('Ⅰ');
      String test2str = test1.toString();
      String[] tests = new String[]{"class-new-if while ", "class", "if", "TOUGHOneHmmm", "SomeBadOne", "someBadOne", ".. A1", "..A1", "A  AA  1 B2", "..A1B2", "ZZA1B2", "ABCD5FDA", "ABCD55FDA", "..A1..", "A1", "A1..", "1a", "..1.2.3.4.a.b.c", "..1.2.ab..3.4.a.b.c", "abc123fgh456", "67abc123fgh456", "..67..abc..123..fgh..456..", "..abc123fgh456", "abc..123..fgh456", "1234", "12...34", "12.34", "..12..34..", "A.B.C.D", "xf1.ser4.fasd3.BIG", "final.class.testing.123", "Z", "  Z  ", "1", " ", ""};

      for(int i = 0; i < tests.length; ++i) {
         System.out.print("FOR: " + tests[i]);
         System.out.print("\tclass = " + getClassName(tests[i]));
         System.out.print("\tmethod = " + getMethodName(tests[i]));
         System.out.print("\tconstant = " + getConstantName(tests[i]));
         System.out.println("\tpackage = " + getPackageName(tests[i]));
      }

   }

   private static Set buildKeywords() {
      Set keys = new HashSet();

      for(int i = 0; i < javaKeywords.length; ++i) {
         keys.add(javaKeywords[i]);
      }

      return keys;
   }

   private static class SplitState {
      int idx;
      String str;
      int len;
      int type;

      private SplitState() {
      }

      // $FF: synthetic method
      SplitState(Object x0) {
         this();
      }
   }
}
