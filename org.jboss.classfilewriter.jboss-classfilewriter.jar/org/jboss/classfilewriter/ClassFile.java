package org.jboss.classfilewriter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jboss.classfilewriter.annotations.AnnotationBuilder;
import org.jboss.classfilewriter.annotations.AnnotationsAttribute;
import org.jboss.classfilewriter.attributes.Attribute;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class ClassFile implements WritableEntry {
   private final String name;
   private final String superclass;
   private final int accessFlags;
   private final int version;
   private final ConstPool constPool;
   private final List interfaces;
   private final Set fields;
   private final Set methods;
   private byte[] bytecode;
   private final List attributes;
   private final AnnotationsAttribute runtimeVisibleAnnotationsAttribute;
   private final ClassLoader classLoader;
   private final ClassFactory classFactory;

   /** @deprecated */
   @Deprecated
   public ClassFile(String name, String superclass, String... interfaces) {
      this(name, AccessFlag.of(32, 1), superclass, (ClassLoader)null, interfaces);
   }

   /** @deprecated */
   @Deprecated
   public ClassFile(String name, int accessFlags, String superclass, String... interfaces) {
      this(name, accessFlags, superclass, (ClassLoader)null, interfaces);
   }

   /** @deprecated */
   @Deprecated
   public ClassFile(String name, String superclass, ClassLoader classLoader, String... interfaces) {
      this(name, AccessFlag.of(32, 1), superclass, classLoader, interfaces);
   }

   /** @deprecated */
   @Deprecated
   public ClassFile(String name, int accessFlags, String superclass, ClassLoader classLoader, String... interfaces) {
      this(name, accessFlags, superclass, 50, classLoader, interfaces);
   }

   /** @deprecated */
   @Deprecated
   public ClassFile(String name, int accessFlags, String superclass, int version, ClassLoader classLoader, String... interfaces) {
      this.constPool = new ConstPool();
      this.interfaces = new ArrayList();
      this.fields = new HashSet();
      this.methods = new HashSet();
      this.attributes = new ArrayList();
      if (version > 50 && classLoader == null) {
         throw new IllegalArgumentException("ClassLoader must be specified if version is greater than Java 6");
      } else {
         this.version = version;
         this.classLoader = classLoader;
         this.classFactory = null;
         this.name = name.replace('/', '.');
         this.superclass = superclass;
         this.accessFlags = accessFlags;
         this.interfaces.addAll(Arrays.asList(interfaces));
         this.runtimeVisibleAnnotationsAttribute = new AnnotationsAttribute(AnnotationsAttribute.Type.RUNTIME_VISIBLE, this.constPool);
         this.attributes.add(this.runtimeVisibleAnnotationsAttribute);
      }
   }

   public ClassFile(String name, String superclass, ClassLoader classLoader, ClassFactory classFactory, String... interfaces) {
      this(name, AccessFlag.of(32, 1), superclass, classLoader, classFactory, interfaces);
   }

   public ClassFile(String name, int accessFlags, String superclass, ClassLoader classLoader, ClassFactory classFactory, String... interfaces) {
      this(name, accessFlags, superclass, 50, classLoader, classFactory, interfaces);
   }

   public ClassFile(String name, int accessFlags, String superclass, int version, ClassLoader classLoader, ClassFactory classFactory, String... interfaces) {
      this.constPool = new ConstPool();
      this.interfaces = new ArrayList();
      this.fields = new HashSet();
      this.methods = new HashSet();
      this.attributes = new ArrayList();
      if (version > 50 && classLoader == null) {
         throw new IllegalArgumentException("ClassLoader must be specified if version is greater than Java 6");
      } else if (classFactory == null) {
         throw new IllegalArgumentException("ClassFactory must be specified");
      } else {
         this.version = version;
         this.classLoader = classLoader;
         this.classFactory = classFactory;
         this.name = name.replace('/', '.');
         this.superclass = superclass;
         this.accessFlags = accessFlags;
         this.interfaces.addAll(Arrays.asList(interfaces));
         this.runtimeVisibleAnnotationsAttribute = new AnnotationsAttribute(AnnotationsAttribute.Type.RUNTIME_VISIBLE, this.constPool);
         this.attributes.add(this.runtimeVisibleAnnotationsAttribute);
      }
   }

   public void addInterface(String iface) {
      this.interfaces.add(iface);
   }

   public ClassField addField(int accessFlags, String name, String descriptor) {
      return this.addField(accessFlags, name, (String)descriptor, (String)null);
   }

   public ClassField addField(int accessFlags, String name, String descriptor, String signature) {
      ClassField field = new ClassField((short)accessFlags, name, descriptor, this, this.constPool);
      if (this.fields.contains(field)) {
         throw new DuplicateMemberException("Field  already exists. Field: " + name + " Descriptor:" + signature);
      } else {
         this.fields.add(field);
         field.setSignature(signature);
         return field;
      }
   }

   public ClassField addField(int accessFlags, String name, Class type) {
      return this.addField(accessFlags, name, DescriptorUtils.makeDescriptor(type));
   }

   public ClassField addField(int accessFlags, String name, Class type, String genericSignature) {
      return this.addField(accessFlags, name, DescriptorUtils.makeDescriptor(type), genericSignature);
   }

   public ClassField addField(Field field) {
      ClassField classField = this.addField((short)field.getModifiers(), field.getName(), (Class)field.getType(), (String)null);
      Annotation[] var3 = field.getDeclaredAnnotations();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         classField.getRuntimeVisibleAnnotationsAttribute().addAnnotation(AnnotationBuilder.createAnnotation(this.constPool, annotation));
      }

      return classField;
   }

   public ClassMethod addMethod(int accessFlags, String name, String returnType, String... parameters) {
      ClassMethod method = new ClassMethod(name, returnType, parameters, accessFlags, this);
      if (this.methods.contains(method)) {
         throw new DuplicateMemberException("Method  already exists. Method: " + name + " Parameters:" + Arrays.toString(parameters) + " Return Type: " + returnType);
      } else {
         this.methods.add(method);
         return method;
      }
   }

   public ClassMethod addMethod(Method method) {
      ClassMethod classMethod = this.addMethod(method.getModifiers() & -1025 & -257, method.getName(), DescriptorUtils.makeDescriptor(method.getReturnType()), DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      Class[] var3 = method.getExceptionTypes();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         Class e = var3[var5];
         classMethod.addCheckedExceptions(e);
      }

      Annotation[] var12 = method.getDeclaredAnnotations();
      var4 = var12.length;

      for(var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var12[var5];
         classMethod.getRuntimeVisibleAnnotationsAttribute().addAnnotation(AnnotationBuilder.createAnnotation(this.constPool, annotation));
      }

      int count = 0;
      Annotation[][] var14 = method.getParameterAnnotations();
      var5 = var14.length;

      for(int var16 = 0; var16 < var5; ++var16) {
         Annotation[] parameterAnnotations = var14[var16];
         Annotation[] var8 = parameterAnnotations;
         int var9 = parameterAnnotations.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Annotation annotation = var8[var10];
            classMethod.getRuntimeVisibleParameterAnnotationsAttribute().addAnnotation(count, AnnotationBuilder.createAnnotation(this.constPool, annotation));
         }

         ++count;
      }

      return classMethod;
   }

   public ClassMethod addConstructor(Constructor method) {
      ClassMethod classMethod = this.addMethod(method.getModifiers(), "<init>", "V", DescriptorUtils.parameterDescriptors(method.getParameterTypes()));
      Class[] var3 = method.getExceptionTypes();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         Class e = var3[var5];
         classMethod.addCheckedExceptions(e);
      }

      Annotation[] var12 = method.getDeclaredAnnotations();
      var4 = var12.length;

      for(var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var12[var5];
         classMethod.getRuntimeVisibleAnnotationsAttribute().addAnnotation(AnnotationBuilder.createAnnotation(this.constPool, annotation));
      }

      int count = 0;
      Annotation[][] var14 = method.getParameterAnnotations();
      var5 = var14.length;

      for(int var16 = 0; var16 < var5; ++var16) {
         Annotation[] parameterAnnotations = var14[var16];
         Annotation[] var8 = parameterAnnotations;
         int var9 = parameterAnnotations.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Annotation annotation = var8[var10];
            classMethod.getRuntimeVisibleParameterAnnotationsAttribute().addAnnotation(count, AnnotationBuilder.createAnnotation(this.constPool, annotation));
         }

         ++count;
      }

      return classMethod;
   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      int nameIndex = this.constPool.addClassEntry(this.name);
      int superClassIndex = this.constPool.addClassEntry(this.superclass);
      List interfaceIndexes = new ArrayList(this.interfaces.size());
      Iterator var5 = this.interfaces.iterator();

      while(var5.hasNext()) {
         String i = (String)var5.next();
         interfaceIndexes.add(this.constPool.addClassEntry(i));
      }

      stream.writeInt(-889275714);
      stream.writeInt(this.version);
      this.constPool.write(stream);
      stream.writeShort(this.accessFlags);
      stream.writeShort(nameIndex);
      stream.writeShort(superClassIndex);
      stream.writeShort(interfaceIndexes.size());
      var5 = interfaceIndexes.iterator();

      while(var5.hasNext()) {
         int i = (Integer)var5.next();
         stream.writeShort(i);
      }

      stream.writeShort(this.fields.size());
      var5 = this.fields.iterator();

      while(var5.hasNext()) {
         ClassField field = (ClassField)var5.next();
         field.write(stream);
      }

      stream.writeShort(this.methods.size());
      var5 = this.methods.iterator();

      while(var5.hasNext()) {
         ClassMethod method = (ClassMethod)var5.next();
         method.write(stream);
      }

      stream.writeShort(this.attributes.size());
      var5 = this.attributes.iterator();

      while(var5.hasNext()) {
         Attribute attribute = (Attribute)var5.next();
         attribute.write(stream);
      }

   }

   public Class define() {
      return this.defineInternal(this.classLoader, (ProtectionDomain)null);
   }

   /** @deprecated */
   @Deprecated
   public Class define(ClassLoader loader) {
      return this.defineInternal(loader, (ProtectionDomain)null);
   }

   public Class define(ProtectionDomain domain) {
      return this.defineInternal(this.classLoader, domain);
   }

   /** @deprecated */
   @Deprecated
   public Class define(ClassLoader loader, ProtectionDomain domain) {
      return this.defineInternal(loader, domain);
   }

   private Class defineInternal(ClassLoader loader, ProtectionDomain domain) {
      byte[] b = this.toBytecode();
      ClassFactory classFactory = this.classFactory == null ? DefaultClassFactory.INSTANCE : this.classFactory;
      return classFactory.defineClass(loader, this.name, b, 0, b.length, domain);
   }

   public byte[] toBytecode() {
      if (this.bytecode == null) {
         try {
            ByteArrayDataOutputStream out = new ByteArrayDataOutputStream();
            this.write(out);
            this.bytecode = out.getBytes();
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }
      }

      return this.bytecode;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public ConstPool getConstPool() {
      return this.constPool;
   }

   public String getDescriptor() {
      return DescriptorUtils.makeDescriptor(this.name);
   }

   public AnnotationsAttribute getRuntimeVisibleAnnotationsAttribute() {
      return this.runtimeVisibleAnnotationsAttribute;
   }

   public String getName() {
      return this.name;
   }

   public String getSuperclass() {
      return this.superclass;
   }

   public List getInterfaces() {
      return Collections.unmodifiableList(this.interfaces);
   }

   public Set getFields() {
      return Collections.unmodifiableSet(this.fields);
   }

   public Set getMethods() {
      return Collections.unmodifiableSet(this.methods);
   }
}
