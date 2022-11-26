package org.apache.openjpa.enhance;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.CodeFormat;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.ParameterTemplate;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import serp.util.Strings;

public class CodeGenerator {
   private File _dir;
   private CodeFormat _format;
   private ClassMetaData _meta;
   private Class _type;
   private ParameterTemplate _code;

   public CodeGenerator(OpenJPAConfiguration conf, Class type) {
      this(conf.newMetaDataRepositoryInstance().getMetaData((Class)type, (ClassLoader)null, true));
   }

   public CodeGenerator(ClassMetaData meta) {
      this._dir = null;
      this._format = null;
      this._meta = null;
      this._type = null;
      this._code = null;
      this._meta = meta;
      this._type = meta.getDescribedType();
   }

   public File getCodeDirectory() {
      return this._dir;
   }

   public void setDirectory(File dir) {
      this._dir = dir;
   }

   public CodeFormat getCodeFormat() {
      return this._format;
   }

   public void setCodeFormat(CodeFormat format) {
      this._format = format;
   }

   public Class getType() {
      return this._type;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public String getCode() {
      return this._code == null ? null : this._code.toString();
   }

   public void generateCode() {
      String className = Strings.getClassName(this._type);
      String packageName = Strings.getPackageName(this._type);
      String packageDec = "";
      if (packageName.length() > 0) {
         packageDec = "package " + packageName + ";";
      }

      String extendsDec = "";
      String extendsName = "";
      if (!this._type.getSuperclass().getName().equals(Object.class.getName())) {
         extendsName = Strings.getClassName(this._type.getSuperclass());
         extendsDec = "extends " + extendsName;
      }

      String imports = this.getImports();
      String[] fieldCode = this.getFieldCode();
      String constructor = this.getConstructor();
      this._code = new ParameterTemplate();
      String codeStr = this.getClassCode();
      if (codeStr != null) {
         this._code.append(codeStr);
         this._code.setParameter("packageDec", packageDec);
         this._code.setParameter("imports", imports);
         this._code.setParameter("className", className);
         this._code.setParameter("extendsDec", extendsDec);
         this._code.setParameter("constructor", constructor);
         this._code.setParameter("fieldDecs", fieldCode[0]);
         this._code.setParameter("fieldCode", fieldCode[1]);
      } else {
         this._code.append(this.getClassCode(packageDec, imports, className, extendsName, constructor, fieldCode[0], fieldCode[1]));
      }

   }

   public void writeCode() throws IOException {
      if (this._code != null) {
         File file = this.getFile();
         Files.backup(file, false);
         this._code.write(file);
      }
   }

   public void writeCode(Writer out) throws IOException {
      if (this._code != null) {
         this._code.write(out);
      }
   }

   private String getImports() {
      Set pkgs = this.getImportPackages();
      CodeFormat imports = this.newCodeFormat();
      String base = Strings.getPackageName(this._type);
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
      pkgs.add(Strings.getPackageName(this._type.getSuperclass()));
      FieldMetaData[] fields = this._meta.getDeclaredFields();

      int i;
      for(i = 0; i < fields.length; ++i) {
         pkgs.add(Strings.getPackageName(fields[i].getDeclaredType()));
      }

      fields = this._meta.getPrimaryKeyFields();

      for(i = 0; i < fields.length; ++i) {
         pkgs.add(Strings.getPackageName(fields[i].getDeclaredType()));
      }

      return pkgs;
   }

   private String getConstructor() {
      FieldMetaData[] fields = this._meta.getPrimaryKeyFields();
      if (fields.length == 0) {
         return "";
      } else {
         CodeFormat cons = this.newCodeFormat();
         CodeFormat body = this.newCodeFormat();
         cons.tab().append("public ").append(Strings.getClassName(this._type));
         cons.openParen(true);

         for(int i = 0; i < fields.length; ++i) {
            String propertyName = fields[i].getName();
            if (propertyName.startsWith("_")) {
               propertyName = propertyName.substring(1);
            }

            String fieldType = Strings.getClassName(fields[i].getDeclaredType());
            if (i > 0) {
               cons.append(", ");
            }

            cons.append(fieldType).append(" ").append(propertyName);
            if (this._meta.getPCSuperclass() == null) {
               if (i > 0) {
                  body.endl();
               }

               body.tab(2);
               if (propertyName.equals(fields[i].getName())) {
                  body.append("this.");
               }

               body.append(fields[i].getName());
               body.append(" = ").append(propertyName).append(";");
            } else {
               if (i == 0) {
                  body.tab(2).append("super").openParen(true);
               } else {
                  body.append(", ");
               }

               body.append(propertyName);
               if (i == fields.length - 1) {
                  body.closeParen().append(";");
               }
            }
         }

         cons.closeParen();
         cons.openBrace(2).endl();
         cons.append(body.toString()).endl();
         cons.closeBrace(2);
         return cons.toString();
      }
   }

   private String[] getFieldCode() {
      CodeFormat decs = this.newCodeFormat();
      CodeFormat code = this.newCodeFormat();
      FieldMetaData[] fields = this._meta.getDeclaredFields();

      int i;
      for(i = 0; i < fields.length; ++i) {
         this.appendFieldCode(fields[i], decs, code);
      }

      fields = this._meta.getDeclaredUnmanagedFields();

      for(i = 0; i < fields.length; ++i) {
         this.appendFieldCode(fields[i], decs, code);
      }

      return new String[]{decs.toString(), code.toString()};
   }

   private void appendFieldCode(FieldMetaData fmd, CodeFormat decs, CodeFormat code) {
      String fieldName = fmd.getName();
      String capFieldName = StringUtils.capitalize(fieldName);
      String propertyName = fieldName;
      if (fieldName.startsWith("_")) {
         propertyName = fieldName.substring(1);
      }

      String fieldType = Strings.getClassName(fmd.getDeclaredType());
      String keyType = null;
      String elementType = null;
      String paramType = "";
      if (this.useGenericCollections()) {
         Class keyCls;
         if (fmd.getDeclaredTypeCode() == 12) {
            keyCls = fmd.getElement().getDeclaredType();
            elementType = Strings.getClassName(keyCls);
            paramType = decs.getParametrizedType(new String[]{elementType});
         } else if (fmd.getDeclaredTypeCode() == 13) {
            keyCls = fmd.getKey().getDeclaredType();
            Class elmCls = fmd.getElement().getDeclaredType();
            keyType = Strings.getClassName(keyCls);
            elementType = Strings.getClassName(elmCls);
            paramType = decs.getParametrizedType(new String[]{keyType, elementType});
         }
      }

      String fieldValue = this.getInitialValue(fmd);
      if (fieldValue == null) {
         if ("Set".equals(fieldType)) {
            fieldValue = "new HashSet" + paramType + decs.getParens();
         } else if ("TreeSet".equals(fieldType)) {
            fieldValue = "new TreeSet" + paramType + decs.getParens();
         } else if ("Collection".equals(fieldType)) {
            fieldValue = "new ArrayList" + paramType + decs.getParens();
         } else if ("Map".equals(fieldType)) {
            fieldValue = "new HashMap" + paramType + decs.getParens();
         } else if ("TreeMap".equals(fieldType)) {
            fieldValue = "new TreeMap" + paramType + decs.getParens();
         } else if (fmd.getDeclaredTypeCode() != 12 && fmd.getDeclaredTypeCode() != 13) {
            fieldValue = "";
         } else {
            fieldValue = "new " + fieldType + paramType + decs.getParens();
         }
      }

      if (fieldValue.length() > 0) {
         fieldValue = " = " + fieldValue;
      }

      boolean fieldAccess = !this.usePropertyBasedAccess();
      String custom = this.getDeclaration(fmd);
      if (decs.length() > 0) {
         decs.endl();
      }

      ParameterTemplate templ;
      if (custom != null) {
         templ = new ParameterTemplate();
         templ.append(custom);
         templ.setParameter("fieldName", fieldName);
         templ.setParameter("capFieldName", capFieldName);
         templ.setParameter("propertyName", propertyName);
         templ.setParameter("fieldType", fieldType);
         templ.setParameter("keyType", keyType);
         templ.setParameter("elementType", elementType);
         templ.setParameter("fieldValue", fieldValue);
         decs.append(templ.toString());
      } else {
         if (fieldAccess) {
            this.writeAnnotations(decs, this.getFieldAnnotations(fmd), 1);
         }

         decs.tab().append("private ").append(fieldType).append(paramType).append(" ").append(fieldName).append(fieldValue).append(";");
         if (fieldAccess) {
            decs.endl();
         }
      }

      custom = this.getFieldCode(fmd);
      if (code.length() > 0) {
         code.afterSection();
      }

      if (custom != null) {
         templ = new ParameterTemplate();
         templ.append(custom);
         templ.setParameter("fieldName", fieldName);
         templ.setParameter("capFieldName", capFieldName);
         templ.setParameter("propertyName", propertyName);
         templ.setParameter("fieldType", fieldType);
         templ.setParameter("keyType", keyType);
         templ.setParameter("elementType", elementType);
         templ.setParameter("fieldValue", fieldValue);
         code.append(templ.toString());
      } else {
         if (!fieldAccess) {
            this.writeAnnotations(code, this.getFieldAnnotations(fmd), 1);
         }

         code.tab().append("public ").append(fieldType).append(paramType).append(" ");
         if ("boolean".equalsIgnoreCase(fieldType)) {
            code.append("is");
         } else {
            code.append("get");
         }

         code.append(capFieldName).parens();
         code.openBrace(2).endl();
         code.tab(2).append("return ").append(fieldName).append(";").endl();
         code.closeBrace(2).afterSection();
         code.tab().append("public void set").append(capFieldName);
         code.openParen(true).append(fieldType).append(paramType).append(" ").append(propertyName).closeParen();
         code.openBrace(2).endl();
         code.tab(2);
         if (propertyName.equals(fieldName)) {
            code.append("this.");
         }

         code.append(fieldName).append(" = ").append(propertyName).append(";").endl();
         code.closeBrace(2);
      }

   }

   private String getClassCode(String packageDec, String imports, String className, String extendsName, String constructor, String fieldDecs, String fieldCode) {
      CodeFormat code = this.newCodeFormat();
      if (packageDec.length() > 0) {
         code.append(packageDec).afterSection();
      }

      if (imports.length() > 0) {
         code.append(imports).afterSection();
      }

      code.append("/**").endl().append(" * Auto-generated by:").endl().append(" * ").append(this.getClass().getName()).endl().append(" */").endl();
      this.writeAnnotations(code, this.getClassAnnotations(), 0);
      code.append("public class ").append(className);
      if (extendsName.length() > 0) {
         code.extendsDec(1).append(" ").append(extendsName);
      }

      this.openClassBrace(code);
      if (fieldDecs.length() > 0) {
         code.append(fieldDecs).afterSection();
      }

      code.tab().append("public ").append(className).parens();
      code.openBrace(2).endl().closeBrace(2);
      if (constructor.length() > 0) {
         code.afterSection().append(constructor);
      }

      if (fieldCode.length() > 0) {
         code.afterSection().append(fieldCode);
      }

      code.endl();
      this.closeClassBrace(code);
      return code.toString();
   }

   private void writeAnnotations(CodeFormat code, List ann, int tabLevel) {
      if (ann != null && ann.size() != 0) {
         Iterator i = ann.iterator();

         while(i.hasNext()) {
            if (tabLevel > 0) {
               code.tab(tabLevel);
            }

            String s = (String)i.next();
            code.append(s).endl();
         }

      }
   }

   protected void openClassBrace(CodeFormat code) {
      code.openBrace(1).endl();
   }

   protected void closeClassBrace(CodeFormat code) {
      code.closeBrace(1);
   }

   public File getFile() {
      String packageName = Strings.getPackageName(this._type);
      String fileName = Strings.getClassName(this._type) + ".java";
      File dir = Files.getPackageFile(this._dir, packageName, true);
      return new File(dir, fileName);
   }

   protected CodeFormat newCodeFormat() {
      return this._format == null ? new CodeFormat() : (CodeFormat)this._format.clone();
   }

   protected String getClassCode() {
      return null;
   }

   protected String getInitialValue(FieldMetaData field) {
      return null;
   }

   protected String getDeclaration(FieldMetaData field) {
      return null;
   }

   protected String getFieldCode(FieldMetaData field) {
      return null;
   }

   protected boolean usePropertyBasedAccess() {
      return false;
   }

   protected List getClassAnnotations() {
      return null;
   }

   protected List getFieldAnnotations(FieldMetaData field) {
      return null;
   }

   protected boolean useGenericCollections() {
      return false;
   }
}
