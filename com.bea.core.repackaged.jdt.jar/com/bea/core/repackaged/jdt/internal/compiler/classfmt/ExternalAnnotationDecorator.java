package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryField;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryMethod;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryNestedType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExternalAnnotationDecorator implements IBinaryType {
   private IBinaryType inputType;
   private ExternalAnnotationProvider annotationProvider;
   private boolean isFromSource;

   public ExternalAnnotationDecorator(IBinaryType toDecorate, ExternalAnnotationProvider externalAnnotationProvider) {
      if (toDecorate == null) {
         throw new NullPointerException("toDecorate");
      } else {
         this.inputType = toDecorate;
         this.annotationProvider = externalAnnotationProvider;
      }
   }

   public ExternalAnnotationDecorator(IBinaryType toDecorate, boolean isFromSource) {
      if (toDecorate == null) {
         throw new NullPointerException("toDecorate");
      } else {
         this.isFromSource = isFromSource;
         this.inputType = toDecorate;
      }
   }

   public char[] getFileName() {
      return this.inputType.getFileName();
   }

   public boolean isBinaryType() {
      return this.inputType.isBinaryType();
   }

   public IBinaryAnnotation[] getAnnotations() {
      return this.inputType.getAnnotations();
   }

   public IBinaryTypeAnnotation[] getTypeAnnotations() {
      return this.inputType.getTypeAnnotations();
   }

   public char[] getEnclosingMethod() {
      return this.inputType.getEnclosingMethod();
   }

   public char[] getEnclosingTypeName() {
      return this.inputType.getEnclosingTypeName();
   }

   public IBinaryField[] getFields() {
      return this.inputType.getFields();
   }

   public char[] getGenericSignature() {
      return this.inputType.getGenericSignature();
   }

   public char[][] getInterfaceNames() {
      return this.inputType.getInterfaceNames();
   }

   public IBinaryNestedType[] getMemberTypes() {
      return this.inputType.getMemberTypes();
   }

   public IBinaryMethod[] getMethods() {
      return this.inputType.getMethods();
   }

   public char[][][] getMissingTypeNames() {
      return this.inputType.getMissingTypeNames();
   }

   public char[] getName() {
      return this.inputType.getName();
   }

   public char[] getSourceName() {
      return this.inputType.getSourceName();
   }

   public char[] getSuperclassName() {
      return this.inputType.getSuperclassName();
   }

   public long getTagBits() {
      return this.inputType.getTagBits();
   }

   public boolean isAnonymous() {
      return this.inputType.isAnonymous();
   }

   public boolean isLocal() {
      return this.inputType.isLocal();
   }

   public boolean isMember() {
      return this.inputType.isMember();
   }

   public char[] sourceFileName() {
      return this.inputType.sourceFileName();
   }

   public int getModifiers() {
      return this.inputType.getModifiers();
   }

   public char[] getModule() {
      return this.inputType.getModule();
   }

   public static ZipFile getAnnotationZipFile(String basePath, ZipFileProducer producer) throws IOException {
      File annotationBase = new File(basePath);
      if (!annotationBase.isFile()) {
         return null;
      } else {
         return producer != null ? producer.produce() : new ZipFile(annotationBase);
      }
   }

   public static ExternalAnnotationProvider externalAnnotationProvider(String basePath, String qualifiedBinaryTypeName, ZipFile zipFile) throws IOException {
      String qualifiedBinaryFileName = qualifiedBinaryTypeName + ".eea";
      if (zipFile == null) {
         File annotationBase = new File(basePath);
         if (annotationBase.isDirectory()) {
            String filePath = annotationBase.getAbsolutePath() + '/' + qualifiedBinaryFileName;

            try {
               return new ExternalAnnotationProvider(new FileInputStream(filePath), qualifiedBinaryTypeName);
            } catch (FileNotFoundException var6) {
               return null;
            }
         }
      } else {
         ZipEntry entry = zipFile.getEntry(qualifiedBinaryFileName);
         if (entry != null) {
            return new ExternalAnnotationProvider(zipFile.getInputStream(entry), qualifiedBinaryTypeName);
         }
      }

      return null;
   }

   public static IBinaryType create(IBinaryType toDecorate, String basePath, String qualifiedBinaryTypeName, ZipFile zipFile) throws IOException {
      ExternalAnnotationProvider externalAnnotationProvider = externalAnnotationProvider(basePath, qualifiedBinaryTypeName, zipFile);
      return (IBinaryType)(externalAnnotationProvider == null ? toDecorate : new ExternalAnnotationDecorator(toDecorate, externalAnnotationProvider));
   }

   public ITypeAnnotationWalker enrichWithExternalAnnotationsFor(ITypeAnnotationWalker walker, Object member, LookupEnvironment environment) {
      if (walker == ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER && this.annotationProvider != null) {
         if (member == null) {
            return this.annotationProvider.forTypeHeader(environment);
         }

         char[] methodSignature;
         if (member instanceof IBinaryField) {
            IBinaryField field = (IBinaryField)member;
            methodSignature = field.getGenericSignature();
            if (methodSignature == null) {
               methodSignature = field.getTypeName();
            }

            return this.annotationProvider.forField(field.getName(), methodSignature, environment);
         }

         if (member instanceof IBinaryMethod) {
            IBinaryMethod method = (IBinaryMethod)member;
            methodSignature = method.getGenericSignature();
            if (methodSignature == null) {
               methodSignature = method.getMethodDescriptor();
            }

            return this.annotationProvider.forMethod(method.isConstructor() ? TypeConstants.INIT : method.getSelector(), methodSignature, environment);
         }
      }

      return walker;
   }

   public BinaryTypeBinding.ExternalAnnotationStatus getExternalAnnotationStatus() {
      if (this.annotationProvider == null) {
         return this.isFromSource ? BinaryTypeBinding.ExternalAnnotationStatus.FROM_SOURCE : BinaryTypeBinding.ExternalAnnotationStatus.NO_EEA_FILE;
      } else {
         return BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
      }
   }

   public interface ZipFileProducer {
      ZipFile produce() throws IOException;
   }
}
