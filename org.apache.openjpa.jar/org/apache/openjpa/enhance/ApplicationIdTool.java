package org.apache.openjpa.enhance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.meta.ClassArgParser;
import org.apache.openjpa.lib.util.CodeFormat;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.DelegatingMetaDataFactory;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.UserException;
import serp.bytecode.BCClass;
import serp.bytecode.BCClassLoader;
import serp.bytecode.Project;
import serp.util.Strings;

public class ApplicationIdTool {
   public static final String TOKEN_DEFAULT = "::";
   private static final String TOKENIZER_CUSTOM = "Tokenizer";
   private static final String TOKENIZER_STD = "StringTokenizer";
   private static final Localizer _loc = Localizer.forPackage(ApplicationIdTool.class);
   private final Log _log;
   private final Class _type;
   private final ClassMetaData _meta;
   private boolean _abstract = false;
   private FieldMetaData[] _fields = null;
   private boolean _ignore = true;
   private File _dir = null;
   private Writer _writer = null;
   private String _code = null;
   private String _token = "::";
   private CodeFormat _format = null;

   public ApplicationIdTool(OpenJPAConfiguration conf, Class type) {
      this._log = conf.getLog("openjpa.Enhance");
      this._type = type;
      MetaDataRepository repos = conf.newMetaDataRepositoryInstance();
      repos.setValidate(0);
      repos.setSourceMode(2, false);
      loadObjectIds(repos, true);
      this._meta = repos.getMetaData((Class)type, (ClassLoader)null, false);
      if (this._meta != null) {
         this._abstract = Modifier.isAbstract(this._meta.getDescribedType().getModifiers());
         this._fields = getDeclaredPrimaryKeyFields(this._meta);
      }

   }

   public ApplicationIdTool(OpenJPAConfiguration conf, Class type, ClassMetaData meta) {
      this._log = conf.getLog("openjpa.Enhance");
      this._type = type;
      this._meta = meta;
      if (this._meta != null) {
         this._abstract = Modifier.isAbstract(this._meta.getDescribedType().getModifiers());
         this._fields = getDeclaredPrimaryKeyFields(this._meta);
      }

   }

   private static FieldMetaData[] getDeclaredPrimaryKeyFields(ClassMetaData meta) {
      if (meta.getPCSuperclass() == null) {
         return meta.getPrimaryKeyFields();
      } else {
         FieldMetaData[] fields = meta.getPrimaryKeyFields();
         List decs = new ArrayList(fields.length);

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i].getDeclaringType() == meta.getDescribedType()) {
               decs.add(fields[i]);
            }
         }

         return (FieldMetaData[])((FieldMetaData[])decs.toArray(new FieldMetaData[decs.size()]));
      }
   }

   public boolean getIgnoreErrors() {
      return this._ignore;
   }

   public void setIgnoreErrors(boolean ignore) {
      this._ignore = ignore;
   }

   public CodeFormat getCodeFormat() {
      return this._format;
   }

   public void setCodeFormat(CodeFormat format) {
      this._format = format;
   }

   public File getDirectory() {
      return this._dir;
   }

   public void setDirectory(File dir) {
      this._dir = dir;
   }

   public String getToken() {
      return this._token;
   }

   public void setToken(String token) {
      this._token = token;
   }

   public Writer getWriter() {
      return this._writer;
   }

   public void setWriter(Writer writer) {
      this._writer = writer;
   }

   public Class getType() {
      return this._type;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public String getCode() {
      return this._code;
   }

   public boolean isInnerClass() {
      Class oidClass = this._meta.getObjectIdType();
      return oidClass.getName().indexOf(36) != -1;
   }

   private String getClassName() {
      if (this._meta.isOpenJPAIdentity()) {
         return null;
      } else {
         String className = Strings.getClassName(this._meta.getObjectIdType());
         if (this.isInnerClass()) {
            className = className.substring(className.lastIndexOf(36) + 1);
         }

         return className;
      }
   }

   public boolean run() {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("appid-start", (Object)this._type));
      }

      if (this._meta != null && this._meta.getIdentityType() == 2 && !this._meta.isOpenJPAIdentity()) {
         Class oidClass = this._meta.getObjectIdType();
         Class superOidClass = null;
         if (this._meta.getPCSuperclass() != null) {
            superOidClass = this._meta.getPCSuperclassMetaData().getObjectIdType();
            if (oidClass == null || oidClass.equals(superOidClass)) {
               if (this._log.isWarnEnabled()) {
                  this._log.warn(_loc.get("appid-warn", (Object)this._type));
               }

               return false;
            }
         }

         if (oidClass == null) {
            throw (new UserException(_loc.get("no-id-class", (Object)this._type))).setFatal(true);
         } else {
            boolean bytes = false;

            for(int i = 0; !bytes && i < this._fields.length; ++i) {
               bytes = this._fields[i].getDeclaredType() == byte[].class;
            }

            String className = this.getClassName();
            String packageName = Strings.getPackageName(oidClass);
            String packageDec = "";
            if (packageName.length() > 0) {
               packageDec = "package " + packageName + ";";
            }

            String imports = this.getImports();
            String fieldDecs = this.getFieldDeclarations();
            String constructor = this.getConstructor(superOidClass != null);
            String properties = this.getProperties();
            String fromStringCode = this.getFromStringCode(superOidClass != null);
            String toStringCode = this.getToStringCode(superOidClass != null);
            String equalsCode = this.getEqualsCode(superOidClass != null);
            String hashCodeCode = this.getHashCodeCode(superOidClass != null);
            CodeFormat code = this.newCodeFormat();
            if (!this.isInnerClass() && packageDec.length() > 0) {
               code.append(packageDec).afterSection();
            }

            if (!this.isInnerClass() && imports.length() > 0) {
               code.append(imports).afterSection();
            }

            code.append("/**").endl().append(" * ").append(_loc.get("appid-comment-for", (Object)this._type.getName())).endl().append(" *").endl().append(" * ").append(_loc.get("appid-comment-gen")).endl().append(" * ").append(this.getClass().getName()).endl().append(" */").endl();
            code.append("public ");
            if (this.isInnerClass()) {
               code.append("static ");
            }

            code.append("class ").append(className);
            if (code.getBraceOnSameLine()) {
               code.append(" ");
            } else {
               code.endl().tab();
            }

            if (superOidClass != null) {
               code.append("extends " + Strings.getClassName(superOidClass));
               if (code.getBraceOnSameLine()) {
                  code.append(" ");
               } else {
                  code.endl().tab();
               }
            }

            code.append("implements Serializable").openBrace(1).endl();
            if (bytes) {
               code.tab().append("private static final char[] HEX = ").append("new char[] {").endl();
               code.tab(2).append("'0', '1', '2', '3', '4', '5', '6', '7',").endl();
               code.tab(2).append("'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'").endl();
               code.tab().append("};").endl(2);
            }

            code.tab().append("static").openBrace(2).endl();
            code.tab(2).append("// register persistent class in JVM").endl();
            if (JavaVersions.VERSION >= 5) {
               code.tab(2).append("try { Class.forName").openParen(true).append("\"").append(this._type.getName()).append("\"").closeParen().append(";").append(" }").endl();
               code.tab(2).append("catch").openParen(true).append("Exception e").closeParen().append(" {}").endl();
            } else {
               code.tab(2).append("Class c = ").append(this._type.getName()).append(".class;").endl();
            }

            code.closeBrace(2);
            if (fieldDecs.length() > 0) {
               code.endl(2).append(fieldDecs);
            }

            code.afterSection().tab().append("public ").append(className).parens().openBrace(2).endl();
            code.closeBrace(2);
            code.afterSection().append(constructor);
            if (properties.length() > 0) {
               code.afterSection().append(properties);
            }

            if (toStringCode.length() > 0) {
               code.afterSection().append(toStringCode);
            }

            if (hashCodeCode.length() > 0) {
               code.afterSection().append(hashCodeCode);
            }

            if (equalsCode.length() > 0) {
               code.afterSection().append(equalsCode);
            }

            if (fromStringCode.length() > 0) {
               code.afterSection().append(fromStringCode);
            }

            if (bytes) {
               code.afterSection().append(this.getToBytesByteArrayCode());
               code.afterSection().append(this.getToStringByteArrayCode());
               code.afterSection().append(this.getEqualsByteArrayCode());
               code.afterSection().append(this.getHashCodeByteArrayCode());
            }

            if (superOidClass == null && this.getTokenizer(false) == "Tokenizer") {
               code.afterSection().append(this.getCustomTokenizerClass());
            }

            code.endl();
            code.closeBrace(1);
            this._code = code.toString();
            if (this.isInnerClass()) {
               this._code = code.getTab() + Strings.replace(this._code, J2DoPrivHelper.getLineSeparator(), J2DoPrivHelper.getLineSeparator() + code.getTab());
            }

            return true;
         }
      } else if (!this._ignore) {
         throw new UserException(_loc.get("appid-invalid", (Object)this._type));
      } else {
         if (this._log.isWarnEnabled()) {
            this._log.warn(_loc.get("appid-warn", (Object)this._type));
         }

         return false;
      }
   }

   public void record() throws IOException {
      if (this._code != null) {
         Writer writer = this._writer;
         if (writer == null) {
            File file = this.getFile();
            Files.backup(file, false);
            writer = new FileWriter(file);
         }

         PrintWriter printer = new PrintWriter((Writer)writer);
         printer.print(this._code);
         printer.flush();
         if (this._writer == null) {
            ((Writer)writer).close();
         }

      }
   }

   private String getImports() {
      Set pkgs = this.getImportPackages();
      CodeFormat imports = this.newCodeFormat();
      String base = Strings.getPackageName(this._meta.getObjectIdType());
      Iterator itr = pkgs.iterator();

      while(itr.hasNext()) {
         String pkg = (String)itr.next();
         if (pkg.length() > 0 && !"java.lang".equals(pkg) && !base.equals(pkg)) {
            if (imports.length() > 0) {
               imports.endl();
            }

            imports.append("import ").append(pkg).append(".*;");
         }
      }

      return imports.toString();
   }

   public Set getImportPackages() {
      Set pkgs = new TreeSet();
      pkgs.add(Strings.getPackageName(this._type));
      Class superOidClass = null;
      if (this._meta != null && this._meta.getPCSuperclassMetaData() != null) {
         superOidClass = this._meta.getPCSuperclassMetaData().getObjectIdType();
      }

      if (superOidClass != null) {
         pkgs.add(Strings.getPackageName(superOidClass));
      }

      pkgs.add("java.io");
      pkgs.add("java.util");

      for(int i = 0; i < this._fields.length; ++i) {
         Class type = this._fields[i].getObjectIdFieldType();
         if (type != byte[].class && type != char[].class && !type.getName().startsWith("java.sql.")) {
            pkgs.add(Strings.getPackageName(type));
         }
      }

      return pkgs;
   }

   private String getFieldDeclarations() {
      CodeFormat code = this.newCodeFormat();

      for(int i = 0; i < this._fields.length; ++i) {
         if (i > 0) {
            code.endl();
         }

         code.tab().append("public ").append(this.getTypeName(this._fields[i])).append(" ").append(this._fields[i].getName()).append(";");
      }

      return code.toString();
   }

   private String getTypeName(FieldMetaData fmd) {
      Class type = fmd.getObjectIdFieldType();
      if (type == byte[].class) {
         return "byte[]";
      } else if (type == char[].class) {
         return "char[]";
      } else {
         return type.getName().startsWith("java.sql.") ? type.getName() : Strings.getClassName(type);
      }
   }

   private String getProperties() {
      if (this._meta.getAccessType() == 2) {
         return "";
      } else {
         CodeFormat code = this.newCodeFormat();

         for(int i = 0; i < this._fields.length; ++i) {
            if (i > 0) {
               code.afterSection();
            }

            String typeName = this.getTypeName(this._fields[i]);
            String propName = StringUtils.capitalize(this._fields[i].getName());
            code.tab().append("public ").append(typeName).append(" ");
            if (this._fields[i].getDeclaredTypeCode() != 0 && this._fields[i].getDeclaredTypeCode() != 16) {
               code.append("get");
            } else {
               code.append("is");
            }

            code.append(propName).parens().openBrace(2).endl();
            code.tab(2).append("return ").append(this._fields[i].getName()).append(";").endl();
            code.closeBrace(2);
            code.afterSection();
            code.tab().append("public void set").append(propName);
            code.openParen(true).append(typeName).append(" ").append(this._fields[i].getName()).closeParen();
            code.openBrace(2).endl();
            code.tab(2).append("this.").append(this._fields[i].getName()).append(" = ").append(this._fields[i].getName()).append(";").endl();
            code.closeBrace(2);
         }

         return code.toString();
      }
   }

   private String getConstructor(boolean hasSuperclass) {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("public ");
      code.append(this.getClassName());
      code.openParen(true).append("String str").closeParen();
      code.openBrace(2).endl();
      if (this._fields.length != 0 || hasSuperclass && this._meta.getPrimaryKeyFields().length > 0) {
         code.tab(2).append("fromString").openParen(true).append("str").closeParen().append(";").endl();
      }

      code.closeBrace(2);
      return code.toString();
   }

   private String getFromStringCode(boolean hasSuperclass) {
      if (this.hasConcreteSuperclass()) {
         return "";
      } else if (this._fields.length == 0) {
         return "";
      } else {
         hasSuperclass = hasSuperclass && getDeclaredPrimaryKeyFields(this._meta.getPCSuperclassMetaData()).length > 0;
         String toke = this.getTokenizer(hasSuperclass);
         CodeFormat code = this.newCodeFormat();
         if (!this._abstract && !hasSuperclass) {
            code.tab().append("private void fromString");
         } else {
            code.tab().append("protected ").append(toke).append(" fromString");
         }

         code.openParen(true).append("String str").closeParen();
         code.openBrace(2).endl();

         int i;
         for(i = 0; i < this._fields.length; ++i) {
            if (this._fields[i].getObjectIdFieldType() == Object.class) {
               code.tab(2).append("throw new UnsupportedOperationException").parens().append(";").endl();
               code.closeBrace(2);
               return code.toString();
            }
         }

         if (toke != null) {
            code.tab(2).append(toke).append(" toke = ");
            if (hasSuperclass) {
               code.append("super.fromString").openParen(true).append("str").closeParen();
            } else {
               code.append("new ").append(toke).openParen(true).append("str");
               if (toke == "StringTokenizer") {
                  code.append(", \"").append(this._token).append("\"");
               }

               code.closeParen();
            }

            code.append(";").endl();
         }

         for(i = 0; i < this._fields.length; ++i) {
            if (toke != null) {
               code.tab(2).append("str = toke.nextToken").parens().append(";").endl();
            }

            code.tab(2).append(this.getConversionCode(this._fields[i], "str")).endl();
         }

         if (this._abstract || hasSuperclass) {
            code.tab(2).append("return toke;").endl();
         }

         code.closeBrace(2);
         return code.toString();
      }
   }

   private String getTokenizer(boolean hasSuperclass) {
      if (!this._abstract && !hasSuperclass && this._fields.length == 1) {
         return null;
      } else {
         return this._token.length() == 1 ? "StringTokenizer" : "Tokenizer";
      }
   }

   private String getConversionCode(FieldMetaData field, String var) {
      CodeFormat parse = this.newCodeFormat();
      if (field.getName().equals(var)) {
         parse.append("this.");
      }

      parse.append(field.getName()).append(" = ");
      Class type = field.getObjectIdFieldType();
      if (type == Date.class) {
         parse.append("new Date").openParen(true).append("Long.parseLong").openParen(true).append(var).closeParen().closeParen();
      } else if (type != java.sql.Date.class && type != Timestamp.class && type != Time.class) {
         if (type == String.class) {
            parse.append(var);
         } else if (type == Character.class) {
            parse.append("new Character").openParen(true).append(var).append(".charAt").openParen(true).append((int)0).closeParen().closeParen();
         } else if (type == byte[].class) {
            parse.append("toBytes").openParen(true).append(var).closeParen();
         } else if (type == char[].class) {
            parse.append(var).append(".toCharArray").parens();
         } else if (!type.isPrimitive()) {
            parse.append("new ").append(Strings.getClassName(type)).openParen(true).append(var).closeParen();
         } else {
            switch (type.getName().charAt(0)) {
               case 'b':
                  if (type == Boolean.TYPE) {
                     parse.append("\"true\".equals").openParen(true).append(var).closeParen();
                  } else {
                     parse.append("Byte.parseByte").openParen(true).append(var).closeParen();
                  }
                  break;
               case 'c':
                  parse.append(var).append(".charAt").openParen(true).append((int)0).closeParen();
                  break;
               case 'd':
                  parse.append("Double.parseDouble").openParen(true).append(var).closeParen();
               case 'e':
               case 'g':
               case 'h':
               case 'j':
               case 'k':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               default:
                  break;
               case 'f':
                  parse.append("Float.parseFloat").openParen(true).append(var).closeParen();
                  break;
               case 'i':
                  parse.append("Integer.parseInt").openParen(true).append(var).closeParen();
                  break;
               case 'l':
                  parse.append("Long.parseLong").openParen(true).append(var).closeParen();
                  break;
               case 's':
                  parse.append("Short.parseShort").openParen(true).append(var).closeParen();
            }
         }
      } else {
         parse.append(type.getName()).append(".valueOf").openParen(true).append(var).closeParen();
      }

      if (!type.isPrimitive() && type != byte[].class) {
         CodeFormat isNull = this.newCodeFormat();
         isNull.append("if").openParen(true).append("\"null\".equals").openParen(true).append(var).closeParen().closeParen().endl().tab(3);
         if (field.getName().equals(var)) {
            isNull.append("this.");
         }

         isNull.append(field.getName()).append(" = null;").endl();
         isNull.tab(2).append("else").endl();
         isNull.tab(3).append(parse);
         parse = isNull;
      }

      return parse.append(";").toString();
   }

   private String getEqualsCode(boolean hasSuperclass) {
      if (!this.hasConcreteSuperclass() && (!hasSuperclass || this._fields.length != 0)) {
         CodeFormat code = this.newCodeFormat();
         code.tab().append("public boolean equals").openParen(true).append("Object obj").closeParen().openBrace(2).endl();
         code.tab(2).append("if").openParen(true).append("this == obj").closeParen().endl();
         code.tab(3).append("return true;").endl();
         String className = this.getClassName();
         if (hasSuperclass) {
            code.tab(2).append("if").openParen(true).append("!super.equals").openParen(true).append("obj").closeParen().closeParen().endl();
            code.tab(3).append("return false;").endl();
         } else {
            code.tab(2).append("if").openParen(true).append("obj == null || obj.getClass").parens().append(" != ").append("getClass").parens().closeParen().endl();
            code.tab(3).append("return false;").endl();
         }

         for(int i = 0; i < this._fields.length; ++i) {
            if (i == 0) {
               code.endl().tab(2).append(className).append(" other = ").openParen(false).append(className).closeParen().append(" obj;").endl();
            }

            if (i == 0) {
               code.tab(2).append("return ");
            } else {
               code.endl().tab(3).append("&& ");
            }

            String name = this._fields[i].getName();
            Class type = this._fields[i].getObjectIdFieldType();
            if (type.isPrimitive()) {
               code.openParen(false).append(name).append(" == ").append("other.").append(name).closeParen();
            } else if (type == byte[].class) {
               code.openParen(false).append("equals").openParen(true).append(name).append(", ").append("other.").append(name).closeParen().closeParen();
            } else if (type == char[].class) {
               code.append("(").openParen(false).append(name).append(" == null && other.").append(name).append(" == null").closeParen().endl();
               code.tab(3).append("|| ");
               code.openParen(false).append(name).append(" != null ").append("&& String.valueOf").openParen(true).append(name).closeParen().append(".").endl();
               code.tab(3).append("equals").openParen(true).append("String.valueOf").openParen(true).append("other.").append(name).closeParen().closeParen().closeParen().append(")");
            } else {
               code.append("(").openParen(false).append(name).append(" == null && other.").append(name).append(" == null").closeParen().endl();
               code.tab(3).append("|| ");
               code.openParen(false).append(name).append(" != null ").append("&& ").append(name).append(".equals").openParen(true).append("other.").append(name).closeParen().closeParen().append(")");
            }
         }

         if (this._fields.length == 0) {
            code.tab(2).append("return true;").endl();
         } else {
            code.append(";").endl();
         }

         code.closeBrace(2);
         return code.toString();
      } else {
         return "";
      }
   }

   private String getHashCodeCode(boolean hasSuperclass) {
      if (this.hasConcreteSuperclass() || hasSuperclass && this._fields.length == 0) {
         return "";
      } else {
         CodeFormat code = this.newCodeFormat();
         code.tab().append("public int hashCode").parens().openBrace(2).endl();
         if (this._fields.length == 0) {
            code.tab(2).append("return 17;").endl();
         } else if (this._fields.length == 1 && !hasSuperclass) {
            code.tab(2).append("return ");
            this.appendHashCodeCode(this._fields[0], code);
            code.append(";").endl();
         } else {
            code.tab(2).append("int rs = ");
            if (hasSuperclass) {
               code.append("super.hashCode").openParen(true).closeParen().append(";");
            } else {
               code.append("17;");
            }

            code.endl();

            for(int i = 0; i < this._fields.length; ++i) {
               code.tab(2).append("rs = rs * 37 + ");
               this.appendHashCodeCode(this._fields[i], code);
               code.append(";").endl();
            }

            code.tab(2).append("return rs;").endl();
         }

         code.closeBrace(2);
         return code.toString();
      }
   }

   private boolean hasConcreteSuperclass() {
      for(ClassMetaData sup = this._meta.getPCSuperclassMetaData(); sup != null; sup = sup.getPCSuperclassMetaData()) {
         if (!Modifier.isAbstract(sup.getDescribedType().getModifiers())) {
            return true;
         }
      }

      return false;
   }

   private void appendHashCodeCode(FieldMetaData field, CodeFormat code) {
      String name = field.getName();
      if ("rs".equals(name)) {
         name = "this." + name;
      }

      Class type = field.getObjectIdFieldType();
      if (type.isPrimitive()) {
         if (type == Boolean.TYPE) {
            code.append("(").openParen(false).append(name).closeParen().append(" ? 1 : 0").append(")");
         } else if (type == Long.TYPE) {
            code.openParen(false).append("int").closeParen().append(" ").openParen(false).append(name).append(" ^ ").openParen(false).append(name).append(" >>> 32").closeParen().closeParen();
         } else if (type == Double.TYPE) {
            code.openParen(false).append("int").closeParen().append(" ").openParen(false).append("Double.doubleToLongBits").openParen(true).append(name).closeParen().endl();
            code.tab(3).append("^ ").openParen(false).append("Double.doubleToLongBits").openParen(true).append(name).closeParen().append(" >>> 32").closeParen().closeParen();
         } else if (type == Float.TYPE) {
            code.append("Float.floatToIntBits").openParen(true).append(name).closeParen();
         } else if (type == Integer.TYPE) {
            code.append(name);
         } else {
            code.openParen(false).append("int").closeParen().append(" ").append(name);
         }
      } else if (type == byte[].class) {
         code.append("hashCode").openParen(true).append(name).closeParen();
      } else if (type == char[].class) {
         code.append("(").openParen(false).append(name).append(" == null").closeParen().append(" ? 0 : ").append("String.valueOf").openParen(true).append(name).closeParen().append(".hashCode").parens().append(")");
      } else {
         code.append("(").openParen(false).append(name).append(" == null").closeParen().append(" ? 0 : ").append(name).append(".hashCode").parens().append(")");
      }

   }

   private String getToStringCode(boolean hasSuperclass) {
      if (!this.hasConcreteSuperclass() && (!hasSuperclass || this._fields.length != 0)) {
         CodeFormat code = this.newCodeFormat();
         code.tab().append("public String toString").parens().openBrace(2).endl();
         String appendDelimiter = "+ \"" + this._token + "\" + ";

         for(int i = 0; i < this._fields.length; ++i) {
            if (i == 0) {
               code.tab(2).append("return ");
               if (hasSuperclass && getDeclaredPrimaryKeyFields(this._meta.getPCSuperclassMetaData()).length > 0) {
                  code.append("super.toString").parens();
                  code.endl().tab(3).append(appendDelimiter);
               }
            } else {
               code.endl().tab(3).append(appendDelimiter);
            }

            String name = this._fields[i].getName();
            Class type = this._fields[i].getObjectIdFieldType();
            if (type == String.class) {
               code.append(name);
            } else if (type == byte[].class) {
               code.append("toString").openParen(true).append(name).closeParen();
            } else if (type == char[].class) {
               code.openParen(true).openParen(true).append(name).append(" == null").closeParen().append(" ? \"null\"").append(": String.valueOf").openParen(true).append(name).closeParen().closeParen();
            } else if (type == Date.class) {
               code.openParen(true).openParen(true).append(name).append(" == null").closeParen().append(" ? \"null\"").endl().tab(4).append(": String.valueOf").openParen(true).append(name).append(".getTime").parens().closeParen().closeParen();
            } else {
               code.append("String.valueOf").openParen(true).append(name).closeParen();
            }
         }

         if (this._fields.length == 0) {
            code.tab(2).append("return \"\"");
         }

         code.append(";").endl();
         code.closeBrace(2);
         return code.toString();
      } else {
         return "";
      }
   }

   private String getToBytesByteArrayCode() {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("private static byte[] toBytes").openParen(true).append("String s").closeParen().openBrace(2).endl();
      code.tab(2).append("if").openParen(true).append("\"null\".equals").openParen(true).append("s").closeParen().closeParen().endl();
      code.tab(3).append("return null;").endl(2);
      code.tab(2).append("int len = s.length").parens().append(";").endl();
      code.tab(2).append("byte[] r = new byte[len / 2];").endl();
      code.tab(2).append("for").openParen(true).append("int i = 0; i < r.length; i++").closeParen().openBrace(3).endl();
      code.tab(3).append("int digit1 = s.charAt").openParen(true).append("i * 2").closeParen().append(", ").append("digit2 = s.charAt").openParen(true).append("i * 2 + 1").closeParen().append(";").endl();
      code.tab(3).append("if").openParen(true).append("digit1 >= '0' && digit1 <= '9'").closeParen().endl();
      code.tab(4).append("digit1 -= '0';").endl();
      code.tab(3).append("else if").openParen(true).append("digit1 >= 'A' && digit1 <= 'F'").closeParen().endl();
      code.tab(4).append("digit1 -= 'A' - 10;").endl();
      code.tab(3).append("if").openParen(true).append("digit2 >= '0' && digit2 <= '9'").closeParen().endl();
      code.tab(4).append("digit2 -= '0';").endl();
      code.tab(3).append("else if").openParen(true).append("digit2 >= 'A' && digit2 <= 'F'").closeParen().endl();
      code.tab(4).append("digit2 -= 'A' - 10;").endl(2);
      code.tab(3).append("r[i] = (byte) ").openParen(false).openParen(false).append("digit1 << 4").closeParen().append(" + digit2").closeParen().append(";").endl();
      code.closeBrace(3).endl();
      code.tab(2).append("return r;").endl();
      code.closeBrace(2);
      return code.toString();
   }

   private String getToStringByteArrayCode() {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("private static String toString").openParen(true).append("byte[] b").closeParen().openBrace(2).endl();
      code.tab(2).append("if").openParen(true).append("b == null").closeParen().endl();
      code.tab(3).append("return \"null\";").endl(2);
      code.tab(2).append("StringBuffer r = new StringBuffer").openParen(true).append("b.length * 2").closeParen().append(";").endl();
      code.tab(2).append("for").openParen(true).append("int i = 0; i < b.length; i++").closeParen().endl();
      code.tab(3).append("for").openParen(true).append("int j = 1; j >= 0; j--").closeParen().endl();
      code.tab(4).append("r.append").openParen(true).append("HEX[").openParen(false).append("b[i] >> ").openParen(false).append("j * 4").closeParen().closeParen().append(" & 0xF]").closeParen().append(";").endl();
      code.tab(2).append("return r.toString").parens().append(";").endl();
      code.closeBrace(2);
      return code.toString();
   }

   private String getEqualsByteArrayCode() {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("private static boolean equals").openParen(true).append("byte[] b1, byte[] b2").closeParen().openBrace(2).endl();
      code.tab(2).append("if").openParen(true).append("b1 == null && b2 == null").closeParen().endl();
      code.tab(3).append("return true;").endl();
      code.tab(2).append("if").openParen(true).append("b1 == null || b2 == null").closeParen().endl();
      code.tab(3).append("return false;").endl();
      code.tab(2).append("if").openParen(true).append("b1.length != b2.length").closeParen().endl();
      code.tab(3).append("return false;").endl();
      code.tab(2).append("for").openParen(true).append("int i = 0; i < b1.length; i++").closeParen().endl();
      code.tab(3).append("if").openParen(true).append("b1[i] != b2[i]").closeParen().endl();
      code.tab(4).append("return false;").endl();
      code.tab(2).append("return true;").endl();
      code.closeBrace(2);
      return code.toString();
   }

   private String getHashCodeByteArrayCode() {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("private static int hashCode").openParen(true).append("byte[] b").closeParen().openBrace(2).endl();
      code.tab(2).append("if").openParen(true).append("b == null").closeParen().endl();
      code.tab(3).append("return 0;").endl();
      code.tab(2).append("int sum = 0;").endl();
      code.tab(2).append("for").openParen(true).append("int i = 0; i < b.length; i++").closeParen().endl();
      code.tab(3).append("sum += b[i];").endl();
      code.tab(2).append("return sum;").endl();
      code.closeBrace(2);
      return code.toString();
   }

   private String getCustomTokenizerClass() {
      CodeFormat code = this.newCodeFormat();
      code.tab().append("protected static class ").append("Tokenizer").openBrace(2).endl();
      code.tab(2).append("private final String str;").endl();
      code.tab(2).append("private int last;").afterSection();
      code.tab(2).append("public Tokenizer (String str)").openBrace(3).endl();
      code.tab(3).append("this.str = str;").endl();
      code.closeBrace(3).afterSection();
      code.tab(2).append("public String nextToken ()").openBrace(3).endl();
      code.tab(3).append("int next = str.indexOf").openParen(true).append("\"").append(this._token).append("\", last").closeParen().append(";").endl();
      code.tab(3).append("String part;").endl();
      code.tab(3).append("if").openParen(true).append("next == -1").closeParen().openBrace(4).endl();
      code.tab(4).append("part = str.substring").openParen(true).append("last").closeParen().append(";").endl();
      code.tab(4).append("last = str.length").parens().append(";").endl().closeBrace(4);
      if (!code.getBraceOnSameLine()) {
         code.endl().tab(3);
      } else {
         code.append(" ");
      }

      code.append("else").openBrace(4).endl();
      code.tab(4).append("part = str.substring").openParen(true).append("last, next").closeParen().append(";").endl();
      code.tab(4).append("last = next + ").append(this._token.length()).append(";").endl().closeBrace(4).endl();
      code.tab(3).append("return part;").endl();
      code.closeBrace(3);
      code.endl().closeBrace(2);
      return code.toString();
   }

   private File getFile() {
      if (this._meta == null) {
         return null;
      } else {
         String packageName = Strings.getPackageName(this._meta.getObjectIdType());
         String fileName = Strings.getClassName(this._meta.getObjectIdType()) + ".java";
         File dir = null;
         if (this._dir == null && Strings.getPackageName(this._type).equals(packageName)) {
            dir = Files.getSourceFile(this._type);
            if (dir != null) {
               dir = dir.getParentFile();
            }
         }

         if (dir == null) {
            dir = Files.getPackageFile(this._dir, packageName, true);
         }

         return new File(dir, fileName);
      }
   }

   private CodeFormat newCodeFormat() {
      return this._format == null ? new CodeFormat() : (CodeFormat)this._format.clone();
   }

   public static void main(String[] args) throws IOException, ClassNotFoundException {
      Options opts = new Options();
      final String[] arguments = opts.setFromCmdLine(args);
      boolean ret = Configurations.runAgainstAllAnchors(opts, new Configurations.Runnable() {
         public boolean run(Options opts) throws ClassNotFoundException, IOException {
            OpenJPAConfiguration conf = new OpenJPAConfigurationImpl();

            boolean var3;
            try {
               var3 = ApplicationIdTool.run(conf, arguments, opts);
            } finally {
               conf.close();
            }

            return var3;
         }
      });
      if (!ret) {
         System.err.println(_loc.get("appid-usage"));
      }

   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Options opts) throws IOException, ClassNotFoundException {
      Flags flags = new Flags();
      flags.ignoreErrors = opts.removeBooleanProperty("ignoreErrors", "i", flags.ignoreErrors);
      flags.directory = Files.getFile(opts.removeProperty("directory", "d", (String)null), (ClassLoader)null);
      flags.token = opts.removeProperty("token", "t", flags.token);
      flags.name = opts.removeProperty("name", "n", flags.name);
      flags.suffix = opts.removeProperty("suffix", "s", flags.suffix);
      Options formatOpts = new Options();
      Iterator itr = opts.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         String key = (String)entry.getKey();
         if (key.startsWith("codeFormat.")) {
            formatOpts.put(key.substring(11), entry.getValue());
            itr.remove();
         } else if (key.startsWith("cf.")) {
            formatOpts.put(key.substring(3), entry.getValue());
            itr.remove();
         }
      }

      if (!formatOpts.isEmpty()) {
         flags.format = new CodeFormat();
         formatOpts.setInto(flags.format);
      }

      Configurations.populateConfiguration(conf, opts);
      ClassLoader loader = conf.getClassResolverInstance().getClassLoader(ApplicationIdTool.class, (ClassLoader)null);
      return run(conf, args, flags, loader);
   }

   public static boolean run(OpenJPAConfiguration conf, String[] args, Flags flags, ClassLoader loader) throws IOException, ClassNotFoundException {
      MetaDataRepository repos = conf.newMetaDataRepositoryInstance();
      repos.setValidate(0, true);
      loadObjectIds(repos, flags.name == null && flags.suffix == null);
      Log log = conf.getLog("openjpa.Tool");
      Object classes;
      if (args.length == 0) {
         log.info(_loc.get("running-all-classes"));
         classes = repos.loadPersistentTypes(true, loader);
      } else {
         ClassArgParser cap = conf.getMetaDataRepositoryInstance().getMetaDataFactory().newClassArgParser();
         cap.setClassLoader(loader);
         classes = new HashSet();

         for(int i = 0; i < args.length; ++i) {
            ((Collection)classes).addAll(Arrays.asList(cap.parseTypes(args[i])));
         }
      }

      if (flags.name != null && ((Collection)classes).size() > 1) {
         throw new UserException(_loc.get("name-mult-args", classes));
      } else {
         BCClassLoader bc = (BCClassLoader)AccessController.doPrivileged(J2DoPrivHelper.newBCClassLoaderAction(new Project()));
         Iterator itr = ((Collection)classes).iterator();

         while(itr.hasNext()) {
            Class cls = (Class)itr.next();
            log.info(_loc.get("appid-running", (Object)cls));
            ClassMetaData meta = repos.getMetaData((Class)cls, (ClassLoader)null, false);
            setObjectIdType(meta, flags, bc);
            ApplicationIdTool tool = new ApplicationIdTool(conf, cls, meta);
            tool.setDirectory(flags.directory);
            tool.setIgnoreErrors(flags.ignoreErrors);
            tool.setToken(flags.token);
            tool.setCodeFormat(flags.format);
            if (tool.run()) {
               log.info(_loc.get("appid-output", (Object)tool.getFile()));
               tool.record();
            } else {
               log.info(_loc.get("appid-norun"));
            }
         }

         bc.getProject().clear();
         return true;
      }
   }

   private static void setObjectIdType(ClassMetaData meta, Flags flags, BCClassLoader bc) throws ClassNotFoundException {
      if (meta != null && (meta.getObjectIdType() == null || meta.isOpenJPAIdentity() && flags.name != null) && getDeclaredPrimaryKeyFields(meta).length != 0) {
         Class desc = meta.getDescribedType();
         Class cls = null;
         if (flags.name != null) {
            cls = loadClass(desc, flags.name, bc);
         } else if (flags.suffix != null) {
            cls = loadClass(desc, desc.getName() + flags.suffix, bc);
         }

         meta.setObjectIdType(cls, false);
      }
   }

   private static Class loadClass(Class context, String name, BCClassLoader bc) throws ClassNotFoundException {
      if (name.indexOf(46) == -1 && context.getName().indexOf(46) != -1) {
         name = Strings.getPackageName(context) + "." + name;
      }

      ClassLoader loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(context));
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      try {
         return Class.forName(name, false, loader);
      } catch (Throwable var5) {
         BCClass oid = bc.getProject().loadClass(name, (ClassLoader)null);
         oid.addDefaultConstructor();
         return Class.forName(name, false, bc);
      }
   }

   private static void loadObjectIds(MetaDataRepository repos, boolean fatal) {
      MetaDataFactory mdf = repos.getMetaDataFactory();
      if (mdf instanceof DelegatingMetaDataFactory) {
         mdf = ((DelegatingMetaDataFactory)mdf).getInnermostDelegate();
      }

      if (mdf instanceof ObjectIdLoader) {
         ((ObjectIdLoader)mdf).setLoadObjectIds();
      } else if (fatal) {
         throw (new InvalidStateException(_loc.get("factory-not-oidloader"))).setFatal(true);
      }

   }

   public interface ObjectIdLoader {
      void setLoadObjectIds();
   }

   public static class Flags {
      public File directory = null;
      public boolean ignoreErrors = true;
      public String token = "::";
      public CodeFormat format = null;
      public String name = null;
      public String suffix = null;
   }
}
