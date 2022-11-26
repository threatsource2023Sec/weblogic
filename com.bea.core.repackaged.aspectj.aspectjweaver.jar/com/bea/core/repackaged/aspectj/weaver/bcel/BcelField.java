package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Synthetic;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.util.GenericSignatureParser;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.util.List;

final class BcelField extends ResolvedMemberImpl {
   public static int AccSynthetic = 4096;
   private Field field;
   private boolean isAjSynthetic;
   private boolean isSynthetic = false;
   private AnnotationAJ[] annotations;
   private final World world;
   private final BcelObjectType bcelObjectType;
   private UnresolvedType genericFieldType = null;
   private boolean unpackedGenericSignature = false;
   private boolean annotationsOnFieldObjectAreOutOfDate = false;

   BcelField(BcelObjectType declaringType, Field field) {
      super(FIELD, declaringType.getResolvedTypeX(), field.getModifiers(), field.getName(), field.getSignature());
      this.field = field;
      this.world = declaringType.getResolvedTypeX().getWorld();
      this.bcelObjectType = declaringType;
      this.unpackAttributes(this.world);
      this.checkedExceptions = UnresolvedType.NONE;
   }

   BcelField(String declaringTypeName, Field field, World world) {
      super(FIELD, UnresolvedType.forName(declaringTypeName), field.getModifiers(), field.getName(), field.getSignature());
      this.field = field;
      this.world = world;
      this.bcelObjectType = null;
      this.unpackAttributes(world);
      this.checkedExceptions = UnresolvedType.NONE;
   }

   private void unpackAttributes(World world) {
      Attribute[] attrs = this.field.getAttributes();
      if (attrs != null && attrs.length > 0) {
         ISourceContext sourceContext = this.getSourceContext(world);
         List as = Utility.readAjAttributes(this.getDeclaringType().getClassName(), attrs, sourceContext, world, this.bcelObjectType != null ? this.bcelObjectType.getWeaverVersionAttribute() : AjAttribute.WeaverVersionInfo.CURRENT, new BcelConstantPoolReader(this.field.getConstantPool()));
         as.addAll(AtAjAttributes.readAj5FieldAttributes(this.field, this, world.resolve(this.getDeclaringType()), sourceContext, world.getMessageHandler()));
      }

      this.isAjSynthetic = false;

      for(int i = attrs.length - 1; i >= 0; --i) {
         if (attrs[i] instanceof Synthetic) {
            this.isSynthetic = true;
         }
      }

      if ((this.field.getModifiers() & AccSynthetic) != 0) {
         this.isSynthetic = true;
      }

   }

   public boolean isAjSynthetic() {
      return this.isAjSynthetic;
   }

   public boolean isSynthetic() {
      return this.isSynthetic;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      this.ensureAnnotationTypesRetrieved();
      ResolvedType[] arr$ = this.annotationTypes;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedType aType = arr$[i$];
         if (aType.equals(ofType)) {
            return true;
         }
      }

      return false;
   }

   public ResolvedType[] getAnnotationTypes() {
      this.ensureAnnotationTypesRetrieved();
      return this.annotationTypes;
   }

   public AnnotationAJ[] getAnnotations() {
      this.ensureAnnotationTypesRetrieved();
      return this.annotations;
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      this.ensureAnnotationTypesRetrieved();
      AnnotationAJ[] arr$ = this.annotations;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AnnotationAJ annotation = arr$[i$];
         if (annotation.getTypeName().equals(ofType.getName())) {
            return annotation;
         }
      }

      return null;
   }

   private void ensureAnnotationTypesRetrieved() {
      if (this.annotationTypes == null) {
         AnnotationGen[] annos = this.field.getAnnotations();
         if (annos.length == 0) {
            this.annotationTypes = ResolvedType.EMPTY_ARRAY;
            this.annotations = AnnotationAJ.EMPTY_ARRAY;
         } else {
            int annosCount = annos.length;
            this.annotationTypes = new ResolvedType[annosCount];
            this.annotations = new AnnotationAJ[annosCount];

            for(int i = 0; i < annosCount; ++i) {
               AnnotationGen anno = annos[i];
               this.annotations[i] = new BcelAnnotation(anno, this.world);
               this.annotationTypes[i] = this.annotations[i].getType();
            }
         }
      }

   }

   public void addAnnotation(AnnotationAJ annotation) {
      this.ensureAnnotationTypesRetrieved();
      int len = this.annotations.length;
      AnnotationAJ[] ret = new AnnotationAJ[len + 1];
      System.arraycopy(this.annotations, 0, ret, 0, len);
      ret[len] = annotation;
      this.annotations = ret;
      ResolvedType[] newAnnotationTypes = new ResolvedType[len + 1];
      System.arraycopy(this.annotationTypes, 0, newAnnotationTypes, 0, len);
      newAnnotationTypes[len] = annotation.getType();
      this.annotationTypes = newAnnotationTypes;
      this.annotationsOnFieldObjectAreOutOfDate = true;
   }

   public void removeAnnotation(AnnotationAJ annotation) {
      this.ensureAnnotationTypesRetrieved();
      int len = this.annotations.length;
      AnnotationAJ[] ret = new AnnotationAJ[len - 1];
      int p = 0;
      AnnotationAJ[] arr$ = this.annotations;
      int len$ = arr$.length;

      int len$;
      for(len$ = 0; len$ < len$; ++len$) {
         AnnotationAJ anno = arr$[len$];
         if (!anno.getType().equals(annotation.getType())) {
            ret[p++] = anno;
         }
      }

      this.annotations = ret;
      ResolvedType[] newAnnotationTypes = new ResolvedType[len - 1];
      p = 0;
      ResolvedType[] arr$ = this.annotationTypes;
      len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedType anno = arr$[i$];
         if (!anno.equals(annotation.getType())) {
            newAnnotationTypes[p++] = anno;
         }
      }

      this.annotationTypes = newAnnotationTypes;
      this.annotationsOnFieldObjectAreOutOfDate = true;
   }

   public UnresolvedType getGenericReturnType() {
      this.unpackGenericSignature();
      return this.genericFieldType;
   }

   public Field getFieldAsIs() {
      return this.field;
   }

   public Field getField(ConstantPool cpool) {
      if (!this.annotationsOnFieldObjectAreOutOfDate) {
         return this.field;
      } else {
         FieldGen newFieldGen = new FieldGen(this.field, cpool);
         newFieldGen.removeAnnotations();
         AnnotationAJ[] arr$ = this.annotations;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AnnotationAJ annotation = arr$[i$];
            newFieldGen.addAnnotation(new AnnotationGen(((BcelAnnotation)annotation).getBcelAnnotation(), cpool, true));
         }

         this.field = newFieldGen.getField();
         this.annotationsOnFieldObjectAreOutOfDate = false;
         return this.field;
      }
   }

   private void unpackGenericSignature() {
      if (!this.unpackedGenericSignature) {
         if (!this.world.isInJava5Mode()) {
            this.genericFieldType = this.getReturnType();
         } else {
            this.unpackedGenericSignature = true;
            String gSig = this.field.getGenericSignature();
            if (gSig != null) {
               GenericSignature.FieldTypeSignature fts = (new GenericSignatureParser()).parseAsFieldSignature(gSig);
               GenericSignature.ClassSignature genericTypeSig = this.bcelObjectType.getGenericClassTypeSignature();
               GenericSignature.FormalTypeParameter[] parentFormals = this.bcelObjectType.getAllFormals();
               GenericSignature.FormalTypeParameter[] typeVars = genericTypeSig == null ? new GenericSignature.FormalTypeParameter[0] : genericTypeSig.formalTypeParameters;
               GenericSignature.FormalTypeParameter[] formals = new GenericSignature.FormalTypeParameter[parentFormals.length + typeVars.length];
               System.arraycopy(typeVars, 0, formals, 0, typeVars.length);
               System.arraycopy(parentFormals, 0, formals, typeVars.length, parentFormals.length);

               try {
                  this.genericFieldType = BcelGenericSignatureToTypeXConverter.fieldTypeSignature2TypeX(fts, formals, this.world);
               } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var8) {
                  throw new IllegalStateException("While determing the generic field type of " + this.toString() + " with generic signature " + gSig + " the following error was detected: " + var8.getMessage());
               }
            } else {
               this.genericFieldType = this.getReturnType();
            }

         }
      }
   }

   public void evictWeavingState() {
      if (this.field != null) {
         this.unpackGenericSignature();
         this.unpackAttributes(this.world);
         this.ensureAnnotationTypesRetrieved();
         this.field = null;
      }

   }
}
