package org.apache.openjpa.lib.meta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import serp.bytecode.lowlevel.ConstantPoolTable;
import serp.util.Strings;

public class ClassArgParser {
   private static final int TOKEN_EOF = -1;
   private static final int TOKEN_NONE = 0;
   private static final int TOKEN_PACKAGE = 1;
   private static final int TOKEN_CLASS = 2;
   private static final int TOKEN_PACKAGE_NOATTR = 3;
   private static final int TOKEN_CLASS_NOATTR = 4;
   private static final Localizer _loc = Localizer.forPackage(ClassArgParser.class);
   private ClassLoader _loader = null;
   private char[] _packageAttr = "name".toCharArray();
   private char[] _classAttr = "name".toCharArray();
   private char[][] _beginElements = new char[][]{{'p'}, {'c'}};
   private char[][] _endElements = new char[][]{"ackage".toCharArray(), "lass".toCharArray()};

   public ClassLoader getClassLoader() {
      return this._loader;
   }

   public void setClassLoader(ClassLoader loader) {
      this._loader = loader;
   }

   public void setMetaDataStructure(String packageElementName, String packageAttributeName, String[] classElementNames, String classAttributeName) {
      char[] buf = new char[classElementNames.length + 1];
      int charIdx = 0;

      while(true) {
         int i;
         for(i = 0; i < buf.length; ++i) {
            if (i == 0) {
               if (charIdx == packageElementName.length()) {
                  throw new UnsupportedOperationException(_loc.get("cant-diff-elems").getMessage());
               }

               buf[i] = packageElementName.charAt(charIdx);
            } else {
               if (charIdx == classElementNames[i - 1].length()) {
                  throw new UnsupportedOperationException(_loc.get("cant-diff-elems").getMessage());
               }

               buf[i] = classElementNames[i - 1].charAt(charIdx);
            }
         }

         if (charsUnique(buf)) {
            this._packageAttr = packageAttributeName == null ? null : packageAttributeName.toCharArray();
            this._classAttr = classAttributeName == null ? null : classAttributeName.toCharArray();
            this._beginElements = new char[classElementNames.length + 1][];
            this._endElements = new char[classElementNames.length + 1][];
            this._beginElements[0] = packageElementName.substring(0, charIdx + 1).toCharArray();
            this._endElements[0] = packageElementName.substring(charIdx + 1).toCharArray();

            for(i = 0; i < classElementNames.length; ++i) {
               this._beginElements[i + 1] = classElementNames[i].substring(0, charIdx + 1).toCharArray();
               this._endElements[i + 1] = classElementNames[i].substring(charIdx + 1).toCharArray();
            }

            return;
         }

         ++charIdx;
      }
   }

   private static boolean charsUnique(char[] buf) {
      for(int i = buf.length - 1; i >= 0; --i) {
         for(int j = 0; j < i; ++j) {
            if (buf[j] == buf[i]) {
               return false;
            }
         }
      }

      return true;
   }

   public Class[] parseTypes(String arg) {
      String[] names = this.parseTypeNames(arg);
      Class[] objs = new Class[names.length];

      for(int i = 0; i < names.length; ++i) {
         objs[i] = Strings.toClass(names[i], this._loader);
      }

      return objs;
   }

   public Class[] parseTypes(MetaDataIterator itr) {
      String[] names = this.parseTypeNames(itr);
      Class[] objs = new Class[names.length];

      for(int i = 0; i < names.length; ++i) {
         objs[i] = Strings.toClass(names[i], this._loader);
      }

      return objs;
   }

   public Map mapTypes(MetaDataIterator itr) {
      Map map = this.mapTypeNames(itr);
      Iterator i = map.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         String[] names = (String[])((String[])entry.getValue());
         Class[] objs = new Class[names.length];

         for(int j = 0; j < names.length; ++j) {
            objs[j] = Strings.toClass(names[j], this._loader);
         }

         entry.setValue(objs);
      }

      return map;
   }

   public String[] parseTypeNames(String arg) {
      if (arg == null) {
         return new String[0];
      } else {
         try {
            File file = Files.getFile(arg, this._loader);
            if (arg.endsWith(".class")) {
               return new String[]{this.getFromClassFile(file)};
            }

            if (arg.endsWith(".java")) {
               return new String[]{this.getFromJavaFile(file)};
            }

            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
               Collection col = this.getFromMetaDataFile(file);
               return (String[])((String[])col.toArray(new String[col.size()]));
            }
         } catch (Exception var4) {
            throw new NestableRuntimeException(_loc.get("class-arg", (Object)arg).getMessage(), var4);
         }

         return new String[]{arg};
      }
   }

   public String[] parseTypeNames(MetaDataIterator itr) {
      if (itr == null) {
         return new String[0];
      } else {
         List names = new ArrayList();
         Object source = null;

         try {
            while(itr.hasNext()) {
               source = itr.next();
               this.appendTypeNames(source, itr.getInputStream(), names);
            }
         } catch (Exception var5) {
            throw new NestableRuntimeException(_loc.get("class-arg", source).getMessage(), var5);
         }

         return (String[])((String[])names.toArray(new String[names.size()]));
      }
   }

   private void appendTypeNames(Object source, InputStream in, List names) throws IOException {
      try {
         if (source.toString().endsWith(".class")) {
            names.add(this.getFromClass(in));
         }

         names.addAll(this.getFromMetaData(new InputStreamReader(in)));
      } finally {
         try {
            in.close();
         } catch (IOException var10) {
         }

      }

   }

   public Map mapTypeNames(MetaDataIterator itr) {
      if (itr == null) {
         return Collections.EMPTY_MAP;
      } else {
         Map map = new HashMap();
         Object source = null;
         List names = new ArrayList();

         try {
            for(; itr.hasNext(); names.clear()) {
               source = itr.next();
               this.appendTypeNames(source, itr.getInputStream(), names);
               if (!names.isEmpty()) {
                  map.put(source, (String[])((String[])names.toArray(new String[names.size()])));
               }
            }

            return map;
         } catch (Exception var6) {
            throw new NestableRuntimeException(_loc.get("class-arg", source).getMessage(), var6);
         }
      }
   }

   private String getFromClassFile(File file) throws IOException {
      FileInputStream fin = null;

      String var3;
      try {
         fin = (FileInputStream)AccessController.doPrivileged(J2DoPrivHelper.newFileInputStreamAction(file));
         var3 = this.getFromClass(fin);
      } catch (PrivilegedActionException var12) {
         throw (FileNotFoundException)var12.getException();
      } finally {
         if (fin != null) {
            try {
               fin.close();
            } catch (IOException var11) {
            }
         }

      }

      return var3;
   }

   private String getFromClass(InputStream in) throws IOException {
      ConstantPoolTable table = new ConstantPoolTable(in);
      int idx = table.getEndIndex();
      idx += 2;
      int clsEntry = table.readUnsignedShort(idx);
      int utfEntry = table.readUnsignedShort(table.get(clsEntry));
      return table.readString(table.get(utfEntry)).replace('/', '.');
   }

   private String getFromJavaFile(File file) throws IOException {
      BufferedReader in = null;

      String var6;
      try {
         in = new BufferedReader(new FileReader(file));
         StringBuffer pack = null;

         String line;
         label108:
         while((line = in.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("package ")) {
               line = line.substring(8).trim();
               pack = new StringBuffer();
               int i = 0;

               while(true) {
                  if (i >= line.length() || !Character.isJavaIdentifierPart(line.charAt(i)) && line.charAt(i) != '.') {
                     break label108;
                  }

                  pack.append(line.charAt(i));
                  ++i;
               }
            }
         }

         String clsName = file.getName();
         clsName = clsName.substring(0, clsName.length() - 5);
         if (pack != null && pack.length() > 0) {
            clsName = pack + "." + clsName;
         }

         var6 = clsName;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var13) {
            }
         }

      }

      return var6;
   }

   private Collection getFromMetaDataFile(File file) throws IOException {
      FileReader in = null;

      Collection var3;
      try {
         in = new FileReader(file);
         var3 = this.getFromMetaData(in);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var10) {
            }
         }

      }

      return var3;
   }

   private Collection getFromMetaData(Reader xml) throws IOException {
      Collection names = new ArrayList();
      BufferedReader in = new BufferedReader(xml);
      boolean comment = false;
      int token = false;
      String pkg = "";
      int ch = 0;
      int last = 0;

      for(int last2 = 0; ch == 60 || (ch = in.read()) != -1; last = ch) {
         if (comment && last2 == 45 && last == 45 && ch == 62) {
            comment = false;
         } else if (comment) {
            if (ch == 60) {
               ch = in.read();
               if (ch == -1) {
                  break;
               }
            }
         } else if (last2 == 60 && last == 33 && ch == 45) {
            comment = true;
         } else if (ch == 60) {
            token = false;
            last = ch;
            ch = this.readThroughWhitespace(in);
            if (ch != 47 && ch != 33 && ch != 63) {
               int token = this.readElementToken(ch, in);
               String name;
               switch (token) {
                  case -1:
                     return names;
                  case 0:
                  default:
                     break;
                  case 1:
                     pkg = this.readAttribute(in, this._packageAttr);
                     if (pkg == null) {
                        return names;
                     }
                     break;
                  case 2:
                     name = this.readAttribute(in, this._classAttr);
                     if (name == null) {
                        return names;
                     }

                     if (pkg.length() > 0 && name.indexOf(46) == -1) {
                        names.add(pkg + "." + name);
                     } else {
                        names.add(name);
                     }
                     break;
                  case 3:
                     pkg = this.readElementText(in);
                     if (pkg == null) {
                        return names;
                     }

                     ch = 60;
                     break;
                  case 4:
                     name = this.readElementText(in);
                     if (name == null) {
                        return names;
                     }

                     ch = 60;
                     if (pkg.length() > 0 && name.indexOf(46) == -1) {
                        names.add(pkg + "." + name);
                     } else {
                        names.add(name);
                     }
               }
            }
         }

         last2 = last;
      }

      return names;
   }

   private int readElementToken(int ch, Reader in) throws IOException {
      int matchIdx = -1;
      int matched = 0;
      int dq = 0;

      int i;
      for(int beginIdx = 0; beginIdx < this._beginElements[0].length; ++beginIdx) {
         if (beginIdx != 0) {
            ch = in.read();
         }

         if (ch == -1) {
            return -1;
         }

         matched = 0;

         for(i = 0; i < this._beginElements.length; ++i) {
            if ((dq & 2 << i) == 0) {
               if (ch == this._beginElements[i][beginIdx]) {
                  matchIdx = i;
                  ++matched;
               } else {
                  dq |= 2 << i;
               }
            }
         }

         if (matched == 0) {
            break;
         }
      }

      if (matched != 1) {
         return 0;
      } else {
         char[] match = this._endElements[matchIdx];

         for(i = 0; i < match.length; ++i) {
            ch = in.read();
            if (ch == -1) {
               return -1;
            }

            if (ch != match[i]) {
               return 0;
            }
         }

         ch = in.read();
         if (ch == -1) {
            return -1;
         } else {
            if (ch == 62) {
               if (matchIdx == 0 && this._packageAttr == null) {
                  return 3;
               }

               if (matchIdx != 0 && this._classAttr == null) {
                  return 4;
               }
            } else if (Character.isWhitespace((char)ch)) {
               if (matchIdx == 0 && this._packageAttr != null) {
                  return 1;
               }

               if (matchIdx != 0 && this._classAttr != null) {
                  return 2;
               }
            }

            return 0;
         }
      }
   }

   private String readAttribute(Reader in, char[] name) throws IOException {
      int expected = 0;
      int last = 0;

      while(true) {
         int ch = in.read();
         if (ch == -1) {
            return null;
         }

         if (ch == 62) {
            return "";
         }

         if (ch == name[expected] && (expected != 0 || last == 0 || Character.isWhitespace((char)last))) {
            ++expected;
            if (expected == name.length) {
               ch = this.readThroughWhitespace(in);
               if (ch == -1) {
                  return null;
               }

               if (ch == 61) {
                  this.readThroughWhitespace(in);
                  return this.readAttributeValue(in);
               }

               expected = 0;
            }
         } else {
            expected = 0;
         }

         last = ch;
      }
   }

   private String readElementText(Reader in) throws IOException {
      StringBuffer buf = null;

      while(true) {
         int ch = in.read();
         if (ch == -1) {
            return null;
         }

         if (ch == 60) {
            return buf == null ? "" : buf.toString();
         }

         if (!Character.isWhitespace((char)ch)) {
            if (buf == null) {
               buf = new StringBuffer();
            }

            buf.append((char)ch);
         }
      }
   }

   private int readThroughWhitespace(Reader in) throws IOException {
      int ch;
      do {
         ch = in.read();
      } while(ch != -1 && Character.isWhitespace((char)ch));

      return ch;
   }

   private String readAttributeValue(Reader in) throws IOException {
      StringBuffer buf = new StringBuffer();

      while(true) {
         int ch = in.read();
         if (ch == -1) {
            return null;
         }

         if (ch == 39 || ch == 34) {
            return buf.toString();
         }

         buf.append((char)ch);
      }
   }
}
