package org.python.compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.core.imp;
import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.ClassWriter;
import org.python.objectweb.asm.FieldVisitor;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Type;

public class ClassFile {
   ClassWriter cw;
   int access;
   long mtime;
   public String name;
   String superclass;
   String sfilename;
   String[] interfaces;
   List methodVisitors;
   List fieldVisitors;
   List annotationVisitors;

   public static String fixName(String n) {
      if (n.indexOf(46) == -1) {
         return n;
      } else {
         char[] c = n.toCharArray();

         for(int i = 0; i < c.length; ++i) {
            if (c[i] == '.') {
               c[i] = '/';
            }
         }

         return new String(c);
      }
   }

   public static void visitAnnotations(AnnotationVisitor av, Map fields) {
      Iterator var2 = fields.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry field = (Map.Entry)var2.next();
         visitAnnotation(av, (String)field.getKey(), field.getValue());
      }

   }

   public static void visitAnnotation(AnnotationVisitor av, String fieldName, Object fieldValue) {
      Class fieldValueClass = fieldValue.getClass();
      if (fieldValue instanceof Class) {
         av.visit(fieldName, Type.getType((Class)fieldValue));
      } else if (fieldValueClass.isEnum()) {
         av.visitEnum(fieldName, ProxyCodeHelpers.mapType(fieldValueClass), fieldValue.toString());
      } else if (fieldValue instanceof List) {
         AnnotationVisitor arrayVisitor = av.visitArray(fieldName);
         List fieldList = (List)fieldValue;
         Iterator var6 = fieldList.iterator();

         while(var6.hasNext()) {
            Object arrayField = var6.next();
            visitAnnotation(arrayVisitor, (String)null, arrayField);
         }

         arrayVisitor.visitEnd();
      } else {
         av.visit(fieldName, fieldValue);
      }

   }

   public ClassFile(String name) {
      this(name, "java/lang/Object", 33, -1L);
   }

   public ClassFile(String name, String superclass, int access) {
      this(name, superclass, access, -1L);
   }

   public ClassFile(String name, String superclass, int access, long mtime) {
      this.name = fixName(name);
      this.superclass = fixName(superclass);
      this.interfaces = new String[0];
      this.access = access;
      this.mtime = mtime;
      this.cw = new ClassWriter(2);
      this.methodVisitors = Collections.synchronizedList(new ArrayList());
      this.fieldVisitors = Collections.synchronizedList(new ArrayList());
      this.annotationVisitors = Collections.synchronizedList(new ArrayList());
   }

   public void setSource(String name) {
      this.sfilename = name;
   }

   public void addInterface(String name) throws IOException {
      String[] new_interfaces = new String[this.interfaces.length + 1];
      System.arraycopy(this.interfaces, 0, new_interfaces, 0, this.interfaces.length);
      new_interfaces[this.interfaces.length] = name;
      this.interfaces = new_interfaces;
   }

   public Code addMethod(String name, String type, int access) throws IOException {
      MethodVisitor mv = this.cw.visitMethod(access, name, type, (String)null, (String[])null);
      Code pmv = new Code(mv, type, access);
      this.methodVisitors.add(pmv);
      return pmv;
   }

   public Code addMethod(String name, String type, int access, String[] exceptions) throws IOException {
      MethodVisitor mv = this.cw.visitMethod(access, name, type, (String)null, exceptions);
      Code pmv = new Code(mv, type, access);
      this.methodVisitors.add(pmv);
      return pmv;
   }

   public Code addMethod(String name, String type, int access, String[] exceptions, ProxyCodeHelpers.AnnotationDescr[] methodAnnotationDescrs, ProxyCodeHelpers.AnnotationDescr[][] parameterAnnotationDescrs) throws IOException {
      MethodVisitor mv = this.cw.visitMethod(access, name, type, (String)null, exceptions);
      ProxyCodeHelpers.AnnotationDescr[] var8 = methodAnnotationDescrs;
      int var9 = methodAnnotationDescrs.length;

      int var10;
      for(var10 = 0; var10 < var9; ++var10) {
         ProxyCodeHelpers.AnnotationDescr ad = var8[var10];
         AnnotationVisitor av = mv.visitAnnotation(ad.getName(), true);
         if (ad.hasFields()) {
            visitAnnotations(av, ad.getFields());
         }

         av.visitEnd();
      }

      for(int i = 0; i < parameterAnnotationDescrs.length; ++i) {
         ProxyCodeHelpers.AnnotationDescr[] var16 = parameterAnnotationDescrs[i];
         var10 = var16.length;

         for(int var17 = 0; var17 < var10; ++var17) {
            ProxyCodeHelpers.AnnotationDescr ad = var16[var17];
            AnnotationVisitor av = mv.visitParameterAnnotation(i, ad.getName(), true);
            if (ad.hasFields()) {
               visitAnnotations(av, ad.getFields());
            }

            av.visitEnd();
         }
      }

      Code pmv = new Code(mv, type, access);
      this.methodVisitors.add(pmv);
      return pmv;
   }

   public void addFinalStringLiteral(String name, String value) throws IOException {
      FieldVisitor fv = this.cw.visitField(25, name, "Ljava/lang/String;", (String)null, value);
      this.fieldVisitors.add(fv);
   }

   public void addClassAnnotation(ProxyCodeHelpers.AnnotationDescr annotationDescr) {
      AnnotationVisitor av = this.cw.visitAnnotation(annotationDescr.getName(), true);
      if (annotationDescr.hasFields()) {
         visitAnnotations(av, annotationDescr.getFields());
      }

      this.annotationVisitors.add(av);
   }

   public void addField(String name, String type, int access) throws IOException {
      this.addField(name, type, access, (ProxyCodeHelpers.AnnotationDescr[])null);
   }

   public void addField(String name, String type, int access, ProxyCodeHelpers.AnnotationDescr[] annotationDescrs) throws IOException {
      FieldVisitor fv = this.cw.visitField(access, name, type, (String)null, (Object)null);
      if (annotationDescrs != null) {
         ProxyCodeHelpers.AnnotationDescr[] var6 = annotationDescrs;
         int var7 = annotationDescrs.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ProxyCodeHelpers.AnnotationDescr ad = var6[var8];
            AnnotationVisitor av = fv.visitAnnotation(ad.getName(), true);
            if (ad.hasFields()) {
               visitAnnotations(av, ad.getFields());
            }

            av.visitEnd();
         }
      }

      this.fieldVisitors.add(fv);
   }

   public void endFields() throws IOException {
      Iterator var1 = this.fieldVisitors.iterator();

      while(var1.hasNext()) {
         FieldVisitor fv = (FieldVisitor)var1.next();
         fv.visitEnd();
      }

   }

   public void endMethods() throws IOException {
      for(int i = 0; i < this.methodVisitors.size(); ++i) {
         MethodVisitor mv = (MethodVisitor)this.methodVisitors.get(i);
         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

   }

   public void endClassAnnotations() {
      Iterator var1 = this.annotationVisitors.iterator();

      while(var1.hasNext()) {
         AnnotationVisitor av = (AnnotationVisitor)var1.next();
         av.visitEnd();
      }

   }

   public void write(OutputStream stream) throws IOException {
      String sfilenameShort = this.sfilename;
      if (this.sfilename != null) {
         try {
            Path pth = (new File("dist/Lib")).toPath().normalize().toAbsolutePath();
            Path pth2 = (new File(this.sfilename)).toPath().normalize().toAbsolutePath();
            sfilenameShort = pth.relativize(pth2).toString();
            if (sfilenameShort.startsWith("..")) {
               sfilenameShort = this.sfilename;
            }

            if (File.separatorChar != '/') {
               sfilenameShort = sfilenameShort.replace(File.separatorChar, '/');
            }
         } catch (Exception var6) {
         }
      }

      this.cw.visit(49, 33, this.name, (String)null, this.superclass, this.interfaces);
      AnnotationVisitor av = this.cw.visitAnnotation("Lorg/python/compiler/APIVersion;", true);
      av.visit("value", new Integer(imp.getAPIVersion()));
      av.visitEnd();
      av = this.cw.visitAnnotation("Lorg/python/compiler/MTime;", true);
      av.visit("value", new Long(this.mtime));
      av.visitEnd();
      if (sfilenameShort != null) {
         av = this.cw.visitAnnotation("Lorg/python/compiler/Filename;", true);
         av.visit("value", sfilenameShort);
         av.visitEnd();
         this.cw.visitSource(sfilenameShort, (String)null);
      }

      this.endClassAnnotations();
      this.endFields();
      this.endMethods();
      byte[] ba = this.cw.toByteArray();
      ByteArrayOutputStream baos = new ByteArrayOutputStream(ba.length);
      baos.write(ba, 0, ba.length);
      baos.writeTo(stream);
      baos.close();
   }
}
