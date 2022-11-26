package weblogic.application.utils.annotation;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import weblogic.diagnostics.debug.DebugLogger;

final class ClassInfoImpl implements ClassInfo, Serializable {
   private static final long serialVersionUID = -6974697573345703118L;
   private String name;
   private String superName;
   private List interfaces;
   private List annotations;
   private Set fieldAnnotations;
   private Set methodAnnotations;
   private URL url;
   private URL codeSourceUrl;
   private String urlStr;
   private String codeSourceUrlStr;
   private List implementations;
   private List annotatedClasses;
   private int access;
   private boolean parsed;
   private transient boolean shouldBePopulated;
   private boolean isAnnotatedValid;
   private boolean isAnnotated;
   private boolean isNonParsedAnnotation;
   private static final int ASM_VERSION = 458752;
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppAnnoScanVerbose");
   private static Field asmSkipFurtherProcessing;

   public ClassInfoImpl(byte[] classBytes, URL url, URL codeSourceUrl) throws IOException {
      this(classBytes, url, codeSourceUrl);
   }

   public ClassInfoImpl(byte[] classBytes, URL url, URL codeSourceUrl, Class... componentAnnotations) throws IOException {
      this.fieldAnnotations = Collections.emptySet();
      this.methodAnnotations = Collections.emptySet();
      this.shouldBePopulated = true;
      this.isAnnotatedValid = false;
      this.isAnnotated = false;
      this.isNonParsedAnnotation = false;
      this.url = url;
      this.codeSourceUrl = codeSourceUrl;
      ClassReader cr = new ClassReader(classBytes);

      try {
         cr.accept(new CV(this, cr, componentAnnotations), 7);
      } catch (ArrayIndexOutOfBoundsException var7) {
      }

   }

   public ClassInfoImpl(String name) {
      this.fieldAnnotations = Collections.emptySet();
      this.methodAnnotations = Collections.emptySet();
      this.shouldBePopulated = true;
      this.isAnnotatedValid = false;
      this.isAnnotated = false;
      this.isNonParsedAnnotation = false;
      this.name = name;
   }

   public ClassInfoImpl() {
      this.fieldAnnotations = Collections.emptySet();
      this.methodAnnotations = Collections.emptySet();
      this.shouldBePopulated = true;
      this.isAnnotatedValid = false;
      this.isAnnotated = false;
      this.isNonParsedAnnotation = false;
   }

   public String getClassName() {
      return this.name;
   }

   public String getSuperClassName() {
      return this.superName;
   }

   public URL getUrl() {
      if (this.url != null) {
         return this.url;
      } else {
         try {
            if (this.urlStr != null) {
               this.url = new URL(this.urlStr);
            }
         } catch (MalformedURLException var2) {
            throw new RuntimeException(var2);
         }

         return this.url;
      }
   }

   public URL getCodeSourceUrl() {
      if (this.codeSourceUrl != null) {
         return this.codeSourceUrl;
      } else {
         try {
            if (this.codeSourceUrlStr != null) {
               this.codeSourceUrl = new URL(this.codeSourceUrlStr);
            }
         } catch (MalformedURLException var2) {
            throw new RuntimeException(var2);
         }

         return this.codeSourceUrl;
      }
   }

   public Set getFieldAnnotations() {
      return this.fieldAnnotations;
   }

   public Set getMethodAnnotations() {
      return this.methodAnnotations;
   }

   public List getInterfaceNames() {
      return this.interfaces == null ? Collections.emptyList() : this.interfaces;
   }

   public List getClassLevelAnnotationNames() {
      return this.annotations == null ? Collections.emptyList() : this.annotations;
   }

   public boolean isInterface() {
      return (this.access & 512) != 0;
   }

   private boolean isDeclaredAnnotation() {
      return (this.access & 8192) != 0;
   }

   public boolean isAnnotation() {
      return this.isDeclaredAnnotation() || this.isNonParsedAnnotation;
   }

   public boolean isEnum() {
      return (this.access & 16384) != 0;
   }

   public boolean isClass() {
      return !this.isInterface() && !this.isAnnotation() && !this.isEnum();
   }

   public List getImplementations() {
      return this.implementations == null ? Collections.emptyList() : this.implementations;
   }

   public List getAnnotatedClasses() {
      return this.annotatedClasses == null ? Collections.emptyList() : this.annotatedClasses;
   }

   public void addImplementation(String className) {
      if (this.isInterface() || this.isClass() || !this.parsed) {
         if (this.implementations == null) {
            this.implementations = new ArrayList();
         }

         this.implementations.add(className);
      }

   }

   public void addAnnotatedClass(String className) {
      if (this.isDeclaredAnnotation() || !this.parsed) {
         if (this.annotatedClasses == null) {
            this.annotatedClasses = new ArrayList();
         }

         this.annotatedClasses.add(className);
      }

      if (!this.isNonParsedAnnotation) {
         this.isNonParsedAnnotation = true;
      }

   }

   public boolean shouldBePopulated() {
      return this.shouldBePopulated;
   }

   public boolean isAnnotated() {
      if (this.isAnnotatedValid) {
         return this.isAnnotated;
      } else {
         boolean hasAnyAnnotationsAtAll = false;
         if (this.annotations != null && this.annotations.size() > 0) {
            hasAnyAnnotationsAtAll = true;
         } else if (this.fieldAnnotations != null && this.fieldAnnotations.size() > 0) {
            hasAnyAnnotationsAtAll = true;
         } else if (this.methodAnnotations != null && this.methodAnnotations.size() > 0) {
            hasAnyAnnotationsAtAll = true;
         }

         if (this.parsed) {
            this.isAnnotatedValid = true;
            this.isAnnotated = hasAnyAnnotationsAtAll;
         }

         return hasAnyAnnotationsAtAll;
      }
   }

   private String urlWithoutDateTime(String whichOne, String url) {
      String result = url.replaceAll("appcgen_\\d+_", "appcgen_");
      if (debugger.isDebugEnabled()) {
         debugger.debug("Replacing " + whichOne + " = " + url + " with " + result);
      }

      return result;
   }

   private void disablePopulating() {
      this.shouldBePopulated = false;
   }

   void writeExternal(ObjectOutput out, String[] classNamePool) throws IOException {
      this.writeClassName(out, classNamePool, this.name);
      this.writeClassName(out, classNamePool, this.superName);
      this.writeClassNameCollection(out, classNamePool, this.interfaces);
      this.writeClassNameCollection(out, classNamePool, this.annotations);
      this.writeClassNameCollection(out, classNamePool, this.fieldAnnotations);
      this.writeClassNameCollection(out, classNamePool, this.methodAnnotations);
      if (this.urlStr != null) {
         out.writeObject(this.urlWithoutDateTime("URL", this.urlStr));
      } else {
         out.writeObject(this.url == null ? null : this.urlWithoutDateTime("URL", this.url.toString()));
      }

      if (this.codeSourceUrlStr != null) {
         out.writeObject(this.urlWithoutDateTime("CodeSourceURL", this.codeSourceUrlStr));
      } else {
         out.writeObject(this.codeSourceUrl == null ? null : this.urlWithoutDateTime("CodeSourceURL", this.codeSourceUrl.toString()));
      }

      this.writeClassNameCollection(out, classNamePool, this.implementations);
      this.writeClassNameCollection(out, classNamePool, this.annotatedClasses);
      out.writeInt(this.access);
      out.writeBoolean(this.parsed);
      out.writeBoolean(this.isAnnotatedValid);
      out.writeBoolean(this.isAnnotated);
      out.writeBoolean(this.isNonParsedAnnotation);
   }

   private void writeClassName(ObjectOutput out, String[] classNamePool, String className) throws IOException {
      int index = -1;
      if (className != null) {
         index = Arrays.binarySearch(classNamePool, className);
      }

      out.writeInt(index);
   }

   private void writeClassNameCollection(ObjectOutput out, String[] classNamePool, Collection list) throws IOException {
      if (list == null) {
         out.writeInt(-1);
      } else {
         out.writeInt(list.size());
         int[] indices = new int[list.size()];
         int index = 0;

         String str;
         for(Iterator var6 = list.iterator(); var6.hasNext(); indices[index++] = Arrays.binarySearch(classNamePool, str)) {
            str = (String)var6.next();
         }

         Arrays.sort(indices);

         for(int i = 0; i < indices.length; ++i) {
            out.writeInt(indices[i]);
         }
      }

   }

   void readExternal(ObjectInput in, String[] classNamePool) throws IOException, ClassNotFoundException {
      this.name = this.readClassName(in, classNamePool);
      this.superName = this.readClassName(in, classNamePool);
      this.interfaces = this.readClassNameList(in, classNamePool);
      this.annotations = this.readClassNameList(in, classNamePool);
      this.fieldAnnotations = new HashSet(this.readClassNameList(in, classNamePool));
      this.methodAnnotations = new HashSet(this.readClassNameList(in, classNamePool));
      this.urlStr = (String)in.readObject();
      this.codeSourceUrlStr = (String)in.readObject();
      this.implementations = this.readClassNameList(in, classNamePool);
      this.annotatedClasses = this.readClassNameList(in, classNamePool);
      this.access = in.readInt();
      this.parsed = in.readBoolean();
      this.isAnnotatedValid = in.readBoolean();
      this.isAnnotated = in.readBoolean();
      this.isNonParsedAnnotation = in.readBoolean();
   }

   private String readClassName(ObjectInput in, String[] classNamePool) throws IOException {
      int i = in.readInt();
      return i == -1 ? null : classNamePool[i];
   }

   private List readClassNameList(ObjectInput in, String[] classNamePool) throws IOException {
      int size = in.readInt();
      if (size == -1) {
         return null;
      } else {
         List classNames = new ArrayList();

         for(int i = 0; i < size; ++i) {
            classNames.add(classNamePool[in.readInt()]);
         }

         return classNames;
      }
   }

   private void addAnnotation(String annoClassBinName) {
      if (this.annotations == null) {
         this.annotations = new ArrayList();
      }

      this.annotations.add(annoClassBinName);
   }

   public void appendScanData(StringBuilder builder, String indent) {
      builder.append(indent).append("access: ").append(this.access).append("\n");
      if (this.isNonParsedAnnotation) {
         builder.append(indent).append("isNonParsedAnnotation\n");
      }

      if (this.urlStr != null && this.urlStr.length() > 0) {
         builder.append(indent).append("URL: ").append(this.urlStr).append("\n");
      }

      if (this.annotatedClasses != null && this.annotatedClasses.size() > 0) {
         builder.append(indent).append("Annotated Classes").append("\n");
         Iterator var3 = this.annotatedClasses.iterator();

         while(var3.hasNext()) {
            String annotatedClass = (String)var3.next();
            builder.append(indent).append(indent).append(annotatedClass).append("\n");
         }
      }

   }

   private boolean isExcludedAnnotation(String desc) {
      String className = desc.replace('/', '.');
      return className.startsWith("java.lang.") || className.startsWith("oracle.javatools.annotations.");
   }

   public synchronized void merge(ClassInfo otherClassInfo) {
      synchronized(otherClassInfo) {
         List annotatedClasses = otherClassInfo.getAnnotatedClasses();
         Iterator var4 = annotatedClasses.iterator();

         while(var4.hasNext()) {
            String annotatedClass = (String)var4.next();
            this.addAnnotatedClass(annotatedClass);
         }

         List implementations = otherClassInfo.getImplementations();
         Iterator var9 = implementations.iterator();

         while(var9.hasNext()) {
            String implementation = (String)var9.next();
            this.addImplementation(implementation);
         }

      }
   }

   static {
      try {
         asmSkipFurtherProcessing = ClassReader.class.getField("skipFurtherProcessing");
         if (debugger.isDebugEnabled()) {
            debugger.debug("ASM skip code searched for and found");
         }
      } catch (NoSuchFieldException var1) {
         asmSkipFurtherProcessing = null;
         if (debugger.isDebugEnabled()) {
            debugger.debug("ASM skip code searched for, but not found");
         }
      }

   }

   private final class CV extends ClassVisitor {
      private final ClassInfoImpl cii;
      private final Set interestedAnnotations;
      private final boolean processInterestedOnly;
      private boolean skipFurtherProcessing = false;
      private boolean visitAnnotationComplete = false;
      private final ClassReader classReader;

      CV(ClassInfoImpl cii, ClassReader classReader, Class... componentAnnotations) {
         super(458752);
         this.cii = cii;
         this.classReader = classReader;
         if (componentAnnotations != null && componentAnnotations.length > 0) {
            this.interestedAnnotations = new HashSet();
            Class[] var5 = componentAnnotations;
            int var6 = componentAnnotations.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Class anno = var5[var7];
               this.interestedAnnotations.add(anno.getName().replace('.', '/'));
            }
         } else {
            this.interestedAnnotations = Collections.emptySet();
         }

         this.processInterestedOnly = !this.interestedAnnotations.isEmpty();
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         this.cii.name = name;
         this.cii.access = access;
         if (superName != null) {
            this.cii.superName = superName;
         }

         if (interfaces != null) {
            this.cii.interfaces = Arrays.asList(interfaces);
         }

      }

      public void visitEnd() {
         this.cii.parsed = true;
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (visible) {
            desc = desc.substring(1, desc.length() - 1);
            if (!ClassInfoImpl.this.isExcludedAnnotation(desc) && (this.processInterestedOnly && this.isInterestedIn(desc) || !this.processInterestedOnly)) {
               this.cii.addAnnotation(desc);
            }
         }

         return null;
      }

      public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
         if (this.skipFurtherProcessing) {
            return null;
         } else {
            if (!this.visitAnnotationComplete) {
               this.visitAnnotationComplete = true;
               if (this.processInterestedOnly && (this.cii.annotations == null || this.cii.annotations.isEmpty())) {
                  this.skipFurtherProcessing = true;
                  if (ClassInfoImpl.asmSkipFurtherProcessing != null) {
                     this.setClassReaderInSkipMode();
                  }

                  this.cii.disablePopulating();
                  return null;
               }
            }

            return ClassInfoImpl.this.new FieldVisitorImpl(access, name, desc, signature, value);
         }
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         if (this.skipFurtherProcessing) {
            return null;
         } else {
            if (!this.visitAnnotationComplete) {
               this.visitAnnotationComplete = true;
               if (this.processInterestedOnly && (this.cii.annotations == null || this.cii.annotations.isEmpty())) {
                  this.skipFurtherProcessing = true;
                  if (ClassInfoImpl.asmSkipFurtherProcessing != null) {
                     this.setClassReaderInSkipMode();
                  }

                  this.cii.disablePopulating();
                  return null;
               }
            }

            return ClassInfoImpl.this.new MethodVisitorImpl(access, name, desc, signature, exceptions);
         }
      }

      public void visitInnerClass(String name, String outerName, String innerName, int access) {
         if (!this.skipFurtherProcessing) {
            if (!this.visitAnnotationComplete) {
               this.visitAnnotationComplete = true;
               if (this.processInterestedOnly && (this.cii.annotations == null || this.cii.annotations.isEmpty())) {
                  this.skipFurtherProcessing = true;
                  if (ClassInfoImpl.asmSkipFurtherProcessing != null) {
                     this.setClassReaderInSkipMode();
                  }

                  this.cii.disablePopulating();
                  return;
               }
            }

            super.visitInnerClass(name, outerName, innerName, access);
         }
      }

      private boolean isInterestedIn(String desc) {
         return this.interestedAnnotations.contains(desc);
      }

      private void setClassReaderInSkipMode() {
         try {
            ClassInfoImpl.asmSkipFurtherProcessing.set(this.classReader, true);
         } catch (IllegalAccessException var2) {
            if (ClassInfoImpl.debugger.isDebugEnabled()) {
               ClassInfoImpl.debugger.debug("ASM skip mode found but could not be set");
            }
         }

      }
   }

   public static class MethodInfoImpl extends InfoImpl implements MethodInfo, Serializable {
      String signature;
      String[] exceptions;

      public MethodInfoImpl(int access, String name, String desc, String signature, String[] exceptions) {
         super(access, name, desc);
         this.signature = signature;
         this.exceptions = exceptions;
      }

      public String getSignature() {
         return this.signature;
      }

      public String[] getExceptions() {
         return this.exceptions;
      }

      public String toString() {
         return super.toString() + ", signature: " + this.signature + ", exceptions: " + Arrays.toString(this.exceptions);
      }

      protected boolean equalInfos(MethodInfoImpl one, Object object) {
         MethodInfoImpl other = (MethodInfoImpl)object;
         return this.equalObjects(one.signature, other.signature) && Arrays.equals(one.exceptions, other.exceptions);
      }
   }

   private class MethodVisitorImpl extends MethodVisitor {
      public MethodVisitorImpl(int access, String name, String desc, String signature, String[] exceptions) {
         super(458752);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (visible) {
            desc = desc.substring(1, desc.length() - 1);
            if (!ClassInfoImpl.this.isExcludedAnnotation(desc)) {
               if (ClassInfoImpl.this.methodAnnotations.isEmpty()) {
                  ClassInfoImpl.this.methodAnnotations = new HashSet();
               }

               ClassInfoImpl.this.methodAnnotations.add(desc);
            }
         }

         return null;
      }
   }

   private static class FieldInfoImpl extends InfoImpl implements FieldInfo, Serializable {
      Object value;

      public FieldInfoImpl(int access, String name, String desc, Object value) {
         super(access, name, desc);
         this.value = value;
      }

      public Object getValue() {
         return this.value;
      }

      public String toString() {
         return super.toString() + ", value: " + this.value;
      }

      protected boolean equalInfos(FieldInfoImpl one, Object object) {
         FieldInfoImpl other = (FieldInfoImpl)object;
         return this.equalObjects(one.value, other.value);
      }
   }

   private abstract static class InfoImpl implements Info, Serializable {
      int access;
      String name;
      String desc;
      List annotations = Collections.emptyList();

      public InfoImpl() {
      }

      public InfoImpl(int access, String name, String desc) {
         this.access = access;
         this.name = name;
         this.desc = desc;
      }

      public int getAccess() {
         return this.access;
      }

      public String getDesc() {
         return this.desc;
      }

      public String getName() {
         return this.name;
      }

      public void addAnnotation(String name, String desc) {
         if (this.annotations.isEmpty()) {
            this.annotations = new LinkedList();
         }

         this.annotations.add(desc);
      }

      public List getAnnotations() {
         return this.annotations;
      }

      public String toString() {
         return super.toString() + ", access: " + this.access + ", name: " + this.name + ", desc: " + this.desc + ", annotations: " + this.annotations;
      }

      public int hashCode() {
         int retVal = this.access;
         if (this.name != null) {
            retVal ^= this.name.hashCode();
         }

         if (this.desc != null) {
            retVal ^= this.desc.hashCode();
         }

         return retVal;
      }

      public boolean equals(Object other) {
         if (other == null) {
            return false;
         } else {
            return this.getClass().isAssignableFrom(other.getClass()) ? this.equalInfos(this, other) : false;
         }
      }

      protected boolean equalInfos(InfoImpl one, Object object) {
         InfoImpl other = (InfoImpl)object;
         return one.access == other.access && this.equalObjects(one.name, one.name) && this.equalObjects(one.desc, other.desc);
      }

      protected boolean equalObjects(Object one, Object other) {
         return one == null ? other == null : one.equals(other);
      }
   }

   private class FieldVisitorImpl extends FieldVisitor {
      public FieldVisitorImpl(int access, String name, String desc, String signature, Object value) {
         super(458752);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (visible) {
            desc = desc.substring(1, desc.length() - 1);
            if (!ClassInfoImpl.this.isExcludedAnnotation(desc)) {
               if (ClassInfoImpl.this.fieldAnnotations.isEmpty()) {
                  ClassInfoImpl.this.fieldAnnotations = new HashSet();
               }

               ClassInfoImpl.this.fieldAnnotations.add(desc);
            }
         }

         return null;
      }
   }
}
