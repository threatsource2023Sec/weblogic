package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Modifiers;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.SourceFile;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisAnnos;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ClassGen extends Modifiers implements Cloneable {
   private String classname;
   private String superclassname;
   private String filename;
   private int classnameIndex;
   private int superclassnameIndex;
   private int major;
   private int minor;
   private ConstantPool cpool;
   private List fieldsList;
   private List methodsList;
   private List attributesList;
   private List interfaceList;
   private List annotationsList;

   public ClassGen(String classname, String superclassname, String filename, int modifiers, String[] interfacenames, ConstantPool cpool) {
      this.classnameIndex = -1;
      this.superclassnameIndex = -1;
      this.major = 45;
      this.minor = 3;
      this.fieldsList = new ArrayList();
      this.methodsList = new ArrayList();
      this.attributesList = new ArrayList();
      this.interfaceList = new ArrayList();
      this.annotationsList = new ArrayList();
      this.classname = classname;
      this.superclassname = superclassname;
      this.filename = filename;
      this.modifiers = modifiers;
      this.cpool = cpool;
      if (filename != null) {
         this.addAttribute(new SourceFile(cpool.addUtf8("SourceFile"), 2, cpool.addUtf8(filename), cpool));
      }

      this.classnameIndex = cpool.addClass(classname);
      this.superclassnameIndex = cpool.addClass(superclassname);
      if (interfacenames != null) {
         String[] var7 = interfacenames;
         int var8 = interfacenames.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String interfacename = var7[var9];
            this.addInterface(interfacename);
         }
      }

   }

   public ClassGen(String classname, String superclassname, String filename, int modifiers, String[] interfacenames) {
      this(classname, superclassname, filename, modifiers, interfacenames, new ConstantPool());
   }

   public ClassGen(JavaClass clazz) {
      this.classnameIndex = -1;
      this.superclassnameIndex = -1;
      this.major = 45;
      this.minor = 3;
      this.fieldsList = new ArrayList();
      this.methodsList = new ArrayList();
      this.attributesList = new ArrayList();
      this.interfaceList = new ArrayList();
      this.annotationsList = new ArrayList();
      this.classnameIndex = clazz.getClassNameIndex();
      this.superclassnameIndex = clazz.getSuperclassNameIndex();
      this.classname = clazz.getClassName();
      this.superclassname = clazz.getSuperclassName();
      this.filename = clazz.getSourceFileName();
      this.modifiers = clazz.getModifiers();
      this.cpool = clazz.getConstantPool().copy();
      this.major = clazz.getMajor();
      this.minor = clazz.getMinor();
      Method[] methods = clazz.getMethods();
      Field[] fields = clazz.getFields();
      String[] interfaces = clazz.getInterfaceNames();

      for(int i = 0; i < interfaces.length; ++i) {
         this.addInterface(interfaces[i]);
      }

      Attribute[] attributes = clazz.getAttributes();
      Attribute[] var6 = attributes;
      int var7 = attributes.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Attribute attr = var6[var8];
         List annos;
         Iterator var12;
         AnnotationGen anno;
         if (attr instanceof RuntimeVisAnnos) {
            RuntimeVisAnnos rva = (RuntimeVisAnnos)attr;
            annos = rva.getAnnotations();
            var12 = annos.iterator();

            while(var12.hasNext()) {
               anno = (AnnotationGen)var12.next();
               this.annotationsList.add(new AnnotationGen(anno, this.cpool, false));
            }
         } else if (attr instanceof RuntimeInvisAnnos) {
            RuntimeInvisAnnos ria = (RuntimeInvisAnnos)attr;
            annos = ria.getAnnotations();
            var12 = annos.iterator();

            while(var12.hasNext()) {
               anno = (AnnotationGen)var12.next();
               this.annotationsList.add(new AnnotationGen(anno, this.cpool, false));
            }
         } else {
            this.attributesList.add(attr);
         }
      }

      int i;
      for(i = 0; i < methods.length; ++i) {
         this.addMethod(methods[i]);
      }

      for(i = 0; i < fields.length; ++i) {
         this.addField(fields[i]);
      }

   }

   public JavaClass getJavaClass() {
      int[] interfaces = this.getInterfaces();
      Field[] fields = this.getFields();
      Method[] methods = this.getMethods();
      Collection attributes = null;
      if (this.annotationsList.size() == 0) {
         attributes = this.attributesList;
      } else {
         attributes = new ArrayList();
         ((Collection)attributes).addAll(Utility.getAnnotationAttributes(this.cpool, this.annotationsList));
         ((Collection)attributes).addAll(this.attributesList);
      }

      ConstantPool cp = this.cpool.getFinalConstantPool();
      return new JavaClass(this.classnameIndex, this.superclassnameIndex, this.filename, this.major, this.minor, this.modifiers, cp, interfaces, fields, methods, (Attribute[])((Collection)attributes).toArray(new Attribute[((Collection)attributes).size()]));
   }

   public void addInterface(String name) {
      this.interfaceList.add(name);
   }

   public void removeInterface(String name) {
      this.interfaceList.remove(name);
   }

   public int getMajor() {
      return this.major;
   }

   public void setMajor(int major) {
      this.major = major;
   }

   public void setMinor(int minor) {
      this.minor = minor;
   }

   public int getMinor() {
      return this.minor;
   }

   public void addAttribute(Attribute a) {
      this.attributesList.add(a);
   }

   public void addAnnotation(AnnotationGen a) {
      this.annotationsList.add(a);
   }

   public void addMethod(Method m) {
      this.methodsList.add(m);
   }

   public void addEmptyConstructor(int access_flags) {
      InstructionList il = new InstructionList();
      il.append((Instruction)InstructionConstants.THIS);
      il.append((Instruction)(new InvokeInstruction((short)183, this.cpool.addMethodref(this.superclassname, "<init>", "()V"))));
      il.append(InstructionConstants.RETURN);
      MethodGen mg = new MethodGen(access_flags, Type.VOID, Type.NO_ARGS, (String[])null, "<init>", this.classname, il, this.cpool);
      mg.setMaxStack(1);
      mg.setMaxLocals();
      this.addMethod(mg.getMethod());
   }

   public void addField(Field f) {
      this.fieldsList.add(f);
   }

   public boolean containsField(Field f) {
      return this.fieldsList.contains(f);
   }

   public Field containsField(String name) {
      Iterator var2 = this.fieldsList.iterator();

      while(var2.hasNext()) {
         Field field = (Field)var2.next();
         if (field.getName().equals(name)) {
            return field;
         }
      }

      return null;
   }

   public Method containsMethod(String name, String signature) {
      Iterator var3 = this.methodsList.iterator();

      Method method;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         method = (Method)var3.next();
      } while(!method.getName().equals(name) || !method.getSignature().equals(signature));

      return method;
   }

   public void removeAttribute(Attribute a) {
      this.attributesList.remove(a);
   }

   public void removeAnnotation(AnnotationGen a) {
      this.annotationsList.remove(a);
   }

   public void removeMethod(Method m) {
      this.methodsList.remove(m);
   }

   public void replaceMethod(Method old, Method new_) {
      if (new_ == null) {
         throw new ClassGenException("Replacement method must not be null");
      } else {
         int i = this.methodsList.indexOf(old);
         if (i < 0) {
            this.methodsList.add(new_);
         } else {
            this.methodsList.set(i, new_);
         }

      }
   }

   public void replaceField(Field old, Field new_) {
      if (new_ == null) {
         throw new ClassGenException("Replacement method must not be null");
      } else {
         int i = this.fieldsList.indexOf(old);
         if (i < 0) {
            this.fieldsList.add(new_);
         } else {
            this.fieldsList.set(i, new_);
         }

      }
   }

   public void removeField(Field f) {
      this.fieldsList.remove(f);
   }

   public String getClassName() {
      return this.classname;
   }

   public String getSuperclassName() {
      return this.superclassname;
   }

   public String getFileName() {
      return this.filename;
   }

   public void setClassName(String name) {
      this.classname = name.replace('/', '.');
      this.classnameIndex = this.cpool.addClass(name);
   }

   public void setSuperclassName(String name) {
      this.superclassname = name.replace('/', '.');
      this.superclassnameIndex = this.cpool.addClass(name);
   }

   public Method[] getMethods() {
      Method[] methods = new Method[this.methodsList.size()];
      this.methodsList.toArray(methods);
      return methods;
   }

   public void setMethods(Method[] methods) {
      this.methodsList.clear();

      for(int m = 0; m < methods.length; ++m) {
         this.addMethod(methods[m]);
      }

   }

   public void setFields(Field[] fs) {
      this.fieldsList.clear();

      for(int m = 0; m < fs.length; ++m) {
         this.addField(fs[m]);
      }

   }

   public void setMethodAt(Method method, int pos) {
      this.methodsList.set(pos, method);
   }

   public Method getMethodAt(int pos) {
      return (Method)this.methodsList.get(pos);
   }

   public String[] getInterfaceNames() {
      int size = this.interfaceList.size();
      String[] interfaces = new String[size];
      this.interfaceList.toArray(interfaces);
      return interfaces;
   }

   public int[] getInterfaces() {
      int size = this.interfaceList.size();
      int[] interfaces = new int[size];

      for(int i = 0; i < size; ++i) {
         interfaces[i] = this.cpool.addClass((String)this.interfaceList.get(i));
      }

      return interfaces;
   }

   public Field[] getFields() {
      Field[] fields = new Field[this.fieldsList.size()];
      this.fieldsList.toArray(fields);
      return fields;
   }

   public Collection getAttributes() {
      return this.attributesList;
   }

   public AnnotationGen[] getAnnotations() {
      AnnotationGen[] annotations = new AnnotationGen[this.annotationsList.size()];
      this.annotationsList.toArray(annotations);
      return annotations;
   }

   public ConstantPool getConstantPool() {
      return this.cpool;
   }

   public void setConstantPool(ConstantPool constant_pool) {
      this.cpool = constant_pool;
   }

   public void setClassNameIndex(int class_name_index) {
      this.classnameIndex = class_name_index;
      this.classname = this.cpool.getConstantString(class_name_index, (byte)7).replace('/', '.');
   }

   public void setSuperclassNameIndex(int superclass_name_index) {
      this.superclassnameIndex = superclass_name_index;
      this.superclassname = this.cpool.getConstantString(superclass_name_index, (byte)7).replace('/', '.');
   }

   public int getSuperclassNameIndex() {
      return this.superclassnameIndex;
   }

   public int getClassNameIndex() {
      return this.classnameIndex;
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         System.err.println(var2);
         return null;
      }
   }

   public final boolean isAnnotation() {
      return (this.modifiers & 8192) != 0;
   }

   public final boolean isEnum() {
      return (this.modifiers & 16384) != 0;
   }

   public long getSUID() {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);
         dos.writeUTF(this.getClassName());
         int classmods = 0;
         classmods |= this.isPublic() ? 1 : 0;
         classmods |= this.isFinal() ? 16 : 0;
         classmods |= this.isInterface() ? 512 : 0;
         if (this.isAbstract()) {
            if (this.isInterface()) {
               if (this.methodsList.size() > 0) {
                  classmods |= 1024;
               }
            } else {
               classmods |= 1024;
            }
         }

         dos.writeInt(classmods);
         String[] names = this.getInterfaceNames();
         if (names != null) {
            Arrays.sort(names);

            for(int i = 0; i < names.length; ++i) {
               dos.writeUTF(names[i]);
            }
         }

         List relevantFields = new ArrayList();
         Iterator var6 = this.fieldsList.iterator();

         while(true) {
            Field field;
            do {
               do {
                  if (!var6.hasNext()) {
                     Collections.sort(relevantFields, new FieldComparator((FieldComparator)null));
                     int relevantFlags = 223;
                     Iterator var8 = relevantFields.iterator();

                     while(var8.hasNext()) {
                        Field f = (Field)var8.next();
                        dos.writeUTF(f.getName());
                        dos.writeInt(relevantFlags & f.getModifiers());
                        dos.writeUTF(f.getType().getSignature());
                     }

                     List relevantMethods = new ArrayList();
                     List relevantCtors = new ArrayList();
                     boolean hasClinit = false;
                     Iterator var10 = this.methodsList.iterator();

                     Method m;
                     while(var10.hasNext()) {
                        m = (Method)var10.next();
                        boolean couldBeInitializer = m.getName().charAt(0) == '<';
                        if (couldBeInitializer && m.getName().equals("<clinit>")) {
                           hasClinit = true;
                        } else if (couldBeInitializer && m.getName().equals("<init>")) {
                           if (!m.isPrivate()) {
                              relevantCtors.add(m);
                           }
                        } else if (!m.isPrivate()) {
                           relevantMethods.add(m);
                        }
                     }

                     Collections.sort(relevantCtors, new ConstructorComparator((ConstructorComparator)null));
                     Collections.sort(relevantMethods, new MethodComparator((MethodComparator)null));
                     if (hasClinit) {
                        dos.writeUTF("<clinit>");
                        dos.writeInt(8);
                        dos.writeUTF("()V");
                     }

                     relevantFlags = 3391;
                     var10 = relevantCtors.iterator();

                     while(var10.hasNext()) {
                        m = (Method)var10.next();
                        dos.writeUTF(m.getName());
                        dos.writeInt(relevantFlags & m.getModifiers());
                        dos.writeUTF(m.getSignature().replace('/', '.'));
                     }

                     var10 = relevantMethods.iterator();

                     while(var10.hasNext()) {
                        m = (Method)var10.next();
                        dos.writeUTF(m.getName());
                        dos.writeInt(relevantFlags & m.getModifiers());
                        dos.writeUTF(m.getSignature().replace('/', '.'));
                     }

                     dos.flush();
                     dos.close();
                     byte[] bs = baos.toByteArray();
                     MessageDigest md = MessageDigest.getInstance("SHA");
                     byte[] result = md.digest(bs);
                     long suid = 0L;

                     for(int pos = result.length > 8 ? 7 : result.length - 1; pos >= 0; suid = suid << 8 | (long)result[pos--] & 255L) {
                     }

                     return suid;
                  }

                  field = (Field)var6.next();
               } while(field.isPrivate() && field.isStatic());
            } while(field.isPrivate() && field.isTransient());

            relevantFields.add(field);
         }
      } catch (Exception var16) {
         var16.printStackTrace();
         throw new RuntimeException("Unable to calculate suid for " + this.getClassName() + ": " + var16.toString());
      }
   }

   public boolean hasAttribute(String attributeName) {
      Iterator var2 = this.attributesList.iterator();

      while(var2.hasNext()) {
         Attribute attr = (Attribute)var2.next();
         if (attr.getName().equals(attributeName)) {
            return true;
         }
      }

      return false;
   }

   public Attribute getAttribute(String attributeName) {
      Iterator var2 = this.attributesList.iterator();

      while(var2.hasNext()) {
         Attribute attr = (Attribute)var2.next();
         if (attr.getName().equals(attributeName)) {
            return attr;
         }
      }

      return null;
   }

   private static class ConstructorComparator implements Comparator {
      private ConstructorComparator() {
      }

      public int compare(Method m0, Method m1) {
         return m0.getSignature().compareTo(m1.getSignature());
      }

      // $FF: synthetic method
      ConstructorComparator(ConstructorComparator var1) {
         this();
      }
   }

   private static class FieldComparator implements Comparator {
      private FieldComparator() {
      }

      public int compare(Field f0, Field f1) {
         return f0.getName().compareTo(f1.getName());
      }

      // $FF: synthetic method
      FieldComparator(FieldComparator var1) {
         this();
      }
   }

   private static class MethodComparator implements Comparator {
      private MethodComparator() {
      }

      public int compare(Method m0, Method m1) {
         int result = m0.getName().compareTo(m1.getName());
         if (result == 0) {
            result = m0.getSignature().compareTo(m1.getSignature());
         }

         return result;
      }

      // $FF: synthetic method
      MethodComparator(MethodComparator var1) {
         this();
      }
   }
}
