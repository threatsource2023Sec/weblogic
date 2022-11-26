package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.AnnotationDefault;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ExceptionTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LineNumber;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LineNumberTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.LocalVariableTable;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.util.GenericSignatureParser;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.MemberKind;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

class BcelMethod extends ResolvedMemberImpl {
   private Method method;
   private ShadowMunger associatedShadowMunger;
   private ResolvedPointcutDefinition preResolvedPointcut;
   private AjAttribute.EffectiveSignatureAttribute effectiveSignature;
   private AjAttribute.MethodDeclarationLineNumberAttribute declarationLineNumber;
   private final BcelObjectType bcelObjectType;
   private int bitflags;
   private static final int KNOW_IF_SYNTHETIC = 1;
   private static final int PARAMETER_NAMES_INITIALIZED = 2;
   private static final int CAN_BE_PARAMETERIZED = 4;
   private static final int UNPACKED_GENERIC_SIGNATURE = 8;
   private static final int IS_AJ_SYNTHETIC = 64;
   private static final int IS_SYNTHETIC = 128;
   private static final int IS_SYNTHETIC_INVERSE = 32639;
   private static final int HAS_ANNOTATIONS = 1024;
   private static final int HAVE_DETERMINED_ANNOTATIONS = 2048;
   private UnresolvedType genericReturnType = null;
   private UnresolvedType[] genericParameterTypes = null;
   public static final AnnotationAJ[] NO_PARAMETER_ANNOTATIONS = new AnnotationAJ[0];

   BcelMethod(BcelObjectType declaringType, Method method) {
      super(method.getName().equals("<init>") ? CONSTRUCTOR : (method.getName().equals("<clinit>") ? STATIC_INITIALIZATION : METHOD), declaringType.getResolvedTypeX(), method.getModifiers(), method.getName(), method.getSignature());
      this.method = method;
      this.sourceContext = declaringType.getResolvedTypeX().getSourceContext();
      this.bcelObjectType = declaringType;
      this.unpackJavaAttributes();
      this.unpackAjAttributes(this.bcelObjectType.getWorld());
   }

   BcelMethod(BcelObjectType declaringType, Method method, List attributes) {
      super(method.getName().equals("<init>") ? CONSTRUCTOR : (method.getName().equals("<clinit>") ? STATIC_INITIALIZATION : METHOD), declaringType.getResolvedTypeX(), method.getModifiers(), method.getName(), method.getSignature());
      this.method = method;
      this.sourceContext = declaringType.getResolvedTypeX().getSourceContext();
      this.bcelObjectType = declaringType;
      this.unpackJavaAttributes();
      this.processAttributes(this.bcelObjectType.getWorld(), attributes);
   }

   private void unpackJavaAttributes() {
      ExceptionTable exnTable = this.method.getExceptionTable();
      this.checkedExceptions = exnTable == null ? UnresolvedType.NONE : UnresolvedType.forNames(exnTable.getExceptionNames());
   }

   public String[] getParameterNames() {
      this.determineParameterNames();
      return super.getParameterNames();
   }

   public int getLineNumberOfFirstInstruction() {
      LineNumberTable lnt = this.method.getLineNumberTable();
      if (lnt == null) {
         return -1;
      } else {
         LineNumber[] lns = lnt.getLineNumberTable();
         return lns != null && lns.length != 0 ? lns[0].getLineNumber() : -1;
      }
   }

   public void determineParameterNames() {
      if ((this.bitflags & 2) == 0) {
         this.bitflags |= 2;
         LocalVariableTable varTable = this.method.getLocalVariableTable();
         int len = this.getArity();
         int index;
         if (varTable == null) {
            AnnotationAJ[] annos = this.getAnnotations();
            if (annos != null && annos.length != 0) {
               AnnotationAJ[] axs = this.getAnnotations();

               for(index = 0; index < axs.length; ++index) {
                  AnnotationAJ annotationX = axs[index];
                  String typename = annotationX.getTypeName();
                  if (typename.charAt(0) == 'o' && (typename.equals("com.bea.core.repackaged.aspectj.lang.annotation.Pointcut") || typename.equals("com.bea.core.repackaged.aspectj.lang.annotation.Before") || typename.equals("com.bea.core.repackaged.aspectj.lang.annotation.Around") || typename.startsWith("com.bea.core.repackaged.aspectj.lang.annotation.After"))) {
                     AnnotationGen a = ((BcelAnnotation)annotationX).getBcelAnnotation();
                     if (a != null) {
                        List values = a.getValues();
                        Iterator i$ = values.iterator();

                        while(i$.hasNext()) {
                           NameValuePair nvPair = (NameValuePair)i$.next();
                           if (nvPair.getNameString().equals("argNames")) {
                              String argNames = nvPair.getValue().stringifyValue();
                              StringTokenizer argNameTokenizer = new StringTokenizer(argNames, " ,");
                              List argsList = new ArrayList();

                              while(argNameTokenizer.hasMoreTokens()) {
                                 argsList.add(argNameTokenizer.nextToken());
                              }

                              int requiredCount = this.getParameterTypes().length;

                              while(argsList.size() < requiredCount) {
                                 argsList.add("arg" + argsList.size());
                              }

                              this.setParameterNames((String[])argsList.toArray(new String[0]));
                              return;
                           }
                        }
                     }
                  }
               }
            }

            this.setParameterNames(Utility.makeArgNames(len));
         } else {
            UnresolvedType[] paramTypes = this.getParameterTypes();
            String[] paramNames = new String[len];
            index = Modifier.isStatic(this.modifiers) ? 0 : 1;

            for(int i = 0; i < len; ++i) {
               LocalVariable lv = varTable.getLocalVariable(index);
               if (lv == null) {
                  paramNames[i] = "arg" + i;
               } else {
                  paramNames[i] = lv.getName();
               }

               index += paramTypes[i].getSize();
            }

            this.setParameterNames(paramNames);
         }

      }
   }

   private void unpackAjAttributes(World world) {
      this.associatedShadowMunger = null;
      ResolvedType resolvedDeclaringType = this.getDeclaringType().resolve(world);
      AjAttribute.WeaverVersionInfo wvinfo = this.bcelObjectType.getWeaverVersionAttribute();
      List as = Utility.readAjAttributes(resolvedDeclaringType.getClassName(), this.method.getAttributes(), resolvedDeclaringType.getSourceContext(), world, wvinfo, new BcelConstantPoolReader(this.method.getConstantPool()));
      this.processAttributes(world, as);
      as = AtAjAttributes.readAj5MethodAttributes(this.method, this, resolvedDeclaringType, this.preResolvedPointcut, resolvedDeclaringType.getSourceContext(), world.getMessageHandler());
      this.processAttributes(world, as);
   }

   private void processAttributes(World world, List as) {
      Iterator i$ = as.iterator();

      while(i$.hasNext()) {
         AjAttribute attr = (AjAttribute)i$.next();
         if (attr instanceof AjAttribute.MethodDeclarationLineNumberAttribute) {
            this.declarationLineNumber = (AjAttribute.MethodDeclarationLineNumberAttribute)attr;
         } else if (attr instanceof AjAttribute.AdviceAttribute) {
            this.associatedShadowMunger = ((AjAttribute.AdviceAttribute)attr).reify(this, world, (ResolvedType)this.getDeclaringType());
         } else if (attr instanceof AjAttribute.AjSynthetic) {
            this.bitflags |= 64;
         } else if (attr instanceof AjAttribute.EffectiveSignatureAttribute) {
            this.effectiveSignature = (AjAttribute.EffectiveSignatureAttribute)attr;
         } else {
            if (!(attr instanceof AjAttribute.PointcutDeclarationAttribute)) {
               throw new BCException("weird method attribute " + attr);
            }

            this.preResolvedPointcut = ((AjAttribute.PointcutDeclarationAttribute)attr).reify();
         }
      }

   }

   public String getAnnotationDefaultValue() {
      Attribute[] attrs = this.method.getAttributes();

      for(int i = 0; i < attrs.length; ++i) {
         Attribute attribute = attrs[i];
         if (attribute.getName().equals("AnnotationDefault")) {
            AnnotationDefault def = (AnnotationDefault)attribute;
            return def.getElementValue().stringifyValue();
         }
      }

      return null;
   }

   public String[] getAttributeNames(boolean onlyIncludeAjOnes) {
      Attribute[] as = this.method.getAttributes();
      List names = new ArrayList();

      for(int j = 0; j < as.length; ++j) {
         if (!onlyIncludeAjOnes || as[j].getName().startsWith("com.bea.core.repackaged.aspectj.weaver")) {
            names.add(as[j].getName());
         }
      }

      return (String[])names.toArray(new String[0]);
   }

   public boolean isAjSynthetic() {
      return (this.bitflags & 64) != 0;
   }

   public ShadowMunger getAssociatedShadowMunger() {
      return this.associatedShadowMunger;
   }

   public AjAttribute.EffectiveSignatureAttribute getEffectiveSignature() {
      return this.effectiveSignature;
   }

   public boolean hasDeclarationLineNumberInfo() {
      return this.declarationLineNumber != null;
   }

   public int getDeclarationLineNumber() {
      return this.declarationLineNumber != null ? this.declarationLineNumber.getLineNumber() : -1;
   }

   public int getDeclarationOffset() {
      return this.declarationLineNumber != null ? this.declarationLineNumber.getOffset() : -1;
   }

   public ISourceLocation getSourceLocation() {
      ISourceLocation ret = super.getSourceLocation();
      if ((ret == null || ((ISourceLocation)ret).getLine() == 0) && this.hasDeclarationLineNumberInfo()) {
         ISourceContext isc = this.getSourceContext();
         if (isc != null) {
            ret = isc.makeSourceLocation(this.getDeclarationLineNumber(), this.getDeclarationOffset());
         } else {
            ret = new SourceLocation((File)null, this.getDeclarationLineNumber());
         }
      }

      return (ISourceLocation)ret;
   }

   public MemberKind getKind() {
      return this.associatedShadowMunger != null ? ADVICE : super.getKind();
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      this.ensureAnnotationsRetrieved();
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

   public AnnotationAJ[] getAnnotations() {
      this.ensureAnnotationsRetrieved();
      return (this.bitflags & 1024) != 0 ? this.annotations : AnnotationAJ.EMPTY_ARRAY;
   }

   public ResolvedType[] getAnnotationTypes() {
      this.ensureAnnotationsRetrieved();
      return this.annotationTypes;
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      this.ensureAnnotationsRetrieved();
      if ((this.bitflags & 1024) == 0) {
         return null;
      } else {
         for(int i = 0; i < this.annotations.length; ++i) {
            if (this.annotations[i].getTypeName().equals(ofType.getName())) {
               return this.annotations[i];
            }
         }

         return null;
      }
   }

   public void addAnnotation(AnnotationAJ annotation) {
      this.ensureAnnotationsRetrieved();
      if ((this.bitflags & 1024) == 0) {
         this.annotations = new AnnotationAJ[1];
         this.annotations[0] = annotation;
         this.annotationTypes = new ResolvedType[1];
         this.annotationTypes[0] = annotation.getType();
      } else {
         int len = this.annotations.length;
         AnnotationAJ[] ret = new AnnotationAJ[len + 1];
         System.arraycopy(this.annotations, 0, ret, 0, len);
         ret[len] = annotation;
         this.annotations = ret;
         ResolvedType[] newAnnotationTypes = new ResolvedType[len + 1];
         System.arraycopy(this.annotationTypes, 0, newAnnotationTypes, 0, len);
         newAnnotationTypes[len] = annotation.getType();
         this.annotationTypes = newAnnotationTypes;
      }

      this.bitflags |= 1024;
   }

   public void removeAnnotation(ResolvedType annotationType) {
      this.ensureAnnotationsRetrieved();
      if ((this.bitflags & 1024) != 0) {
         int len = this.annotations.length;
         if (len == 1) {
            this.bitflags &= -1025;
            this.annotations = null;
            this.annotationTypes = null;
            return;
         }

         AnnotationAJ[] ret = new AnnotationAJ[len - 1];
         int p = 0;
         AnnotationAJ[] arr$ = this.annotations;
         int len$ = arr$.length;

         int len$;
         for(len$ = 0; len$ < len$; ++len$) {
            AnnotationAJ annotation = arr$[len$];
            if (!annotation.getType().equals(annotationType)) {
               ret[p++] = annotation;
            }
         }

         this.annotations = ret;
         ResolvedType[] newAnnotationTypes = new ResolvedType[len - 1];
         p = 0;
         AnnotationAJ[] arr$ = this.annotations;
         len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AnnotationAJ annotation = arr$[i$];
            if (!annotation.getType().equals(annotationType)) {
               newAnnotationTypes[p++] = annotationType;
            }
         }

         this.annotationTypes = newAnnotationTypes;
      }

      this.bitflags |= 1024;
   }

   public void addParameterAnnotation(int param, AnnotationAJ anno) {
      this.ensureParameterAnnotationsRetrieved();
      int i;
      if (this.parameterAnnotations == NO_PARAMETER_ANNOTATIONXS) {
         this.parameterAnnotations = new AnnotationAJ[this.getArity()][];

         for(i = 0; i < this.getArity(); ++i) {
            this.parameterAnnotations[i] = NO_PARAMETER_ANNOTATIONS;
         }
      }

      i = this.parameterAnnotations[param].length;
      AnnotationAJ[] annoArray;
      if (i == 0) {
         annoArray = new AnnotationAJ[]{anno};
         this.parameterAnnotations[param] = annoArray;
      } else {
         annoArray = new AnnotationAJ[i + 1];
         System.arraycopy(this.parameterAnnotations[param], 0, annoArray, 0, i);
         annoArray[i] = anno;
         this.parameterAnnotations[param] = annoArray;
      }

   }

   private void ensureAnnotationsRetrieved() {
      if (this.method != null) {
         if ((this.bitflags & 2048) == 0) {
            this.bitflags |= 2048;
            AnnotationGen[] annos = this.method.getAnnotations();
            if (annos.length == 0) {
               this.annotationTypes = ResolvedType.NONE;
               this.annotations = AnnotationAJ.EMPTY_ARRAY;
            } else {
               int annoCount = annos.length;
               this.annotationTypes = new ResolvedType[annoCount];
               this.annotations = new AnnotationAJ[annoCount];

               for(int i = 0; i < annoCount; ++i) {
                  AnnotationGen annotation = annos[i];
                  this.annotations[i] = new BcelAnnotation(annotation, this.bcelObjectType.getWorld());
                  this.annotationTypes[i] = this.annotations[i].getType();
               }

               this.bitflags |= 1024;
            }

         }
      }
   }

   private void ensureParameterAnnotationsRetrieved() {
      if (this.method != null) {
         AnnotationGen[][] pAnns = this.method.getParameterAnnotations();
         if (this.parameterAnnotationTypes == null || pAnns.length != this.parameterAnnotationTypes.length) {
            if (pAnns == Method.NO_PARAMETER_ANNOTATIONS) {
               this.parameterAnnotationTypes = NO_PARAMETER_ANNOTATION_TYPES;
               this.parameterAnnotations = NO_PARAMETER_ANNOTATIONXS;
            } else {
               AnnotationGen[][] annos = this.method.getParameterAnnotations();
               this.parameterAnnotations = new AnnotationAJ[annos.length][];
               this.parameterAnnotationTypes = new ResolvedType[annos.length][];

               for(int i = 0; i < annos.length; ++i) {
                  AnnotationGen[] annosOnThisParam = annos[i];
                  if (annos[i].length == 0) {
                     this.parameterAnnotations[i] = AnnotationAJ.EMPTY_ARRAY;
                     this.parameterAnnotationTypes[i] = ResolvedType.NONE;
                  } else {
                     this.parameterAnnotations[i] = new AnnotationAJ[annosOnThisParam.length];
                     this.parameterAnnotationTypes[i] = new ResolvedType[annosOnThisParam.length];

                     for(int j = 0; j < annosOnThisParam.length; ++j) {
                        this.parameterAnnotations[i][j] = new BcelAnnotation(annosOnThisParam[j], this.bcelObjectType.getWorld());
                        this.parameterAnnotationTypes[i][j] = this.bcelObjectType.getWorld().resolve(UnresolvedType.forSignature(annosOnThisParam[j].getTypeSignature()));
                     }
                  }
               }
            }
         }

      }
   }

   public AnnotationAJ[][] getParameterAnnotations() {
      this.ensureParameterAnnotationsRetrieved();
      return this.parameterAnnotations;
   }

   public ResolvedType[][] getParameterAnnotationTypes() {
      this.ensureParameterAnnotationsRetrieved();
      return this.parameterAnnotationTypes;
   }

   public boolean canBeParameterized() {
      this.unpackGenericSignature();
      return (this.bitflags & 4) != 0;
   }

   public UnresolvedType[] getGenericParameterTypes() {
      this.unpackGenericSignature();
      return this.genericParameterTypes;
   }

   public UnresolvedType getGenericReturnType() {
      this.unpackGenericSignature();
      return this.genericReturnType;
   }

   public Method getMethod() {
      return this.method;
   }

   private void unpackGenericSignature() {
      if ((this.bitflags & 8) == 0) {
         this.bitflags |= 8;
         if (!this.bcelObjectType.getWorld().isInJava5Mode()) {
            this.genericReturnType = this.getReturnType();
            this.genericParameterTypes = this.getParameterTypes();
         } else {
            String gSig = this.method.getGenericSignature();
            if (gSig != null) {
               GenericSignature.MethodTypeSignature mSig = (new GenericSignatureParser()).parseAsMethodSignature(gSig);
               if (mSig.formalTypeParameters.length > 0) {
                  this.bitflags |= 4;
               }

               this.typeVariables = new TypeVariable[mSig.formalTypeParameters.length];

               for(int i = 0; i < this.typeVariables.length; ++i) {
                  GenericSignature.FormalTypeParameter methodFtp = mSig.formalTypeParameters[i];

                  try {
                     this.typeVariables[i] = BcelGenericSignatureToTypeXConverter.formalTypeParameter2TypeVariable(methodFtp, mSig.formalTypeParameters, this.bcelObjectType.getWorld());
                  } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var11) {
                     throw new IllegalStateException("While getting the type variables for method " + this.toString() + " with generic signature " + mSig + " the following error condition was detected: " + var11.getMessage());
                  }
               }

               GenericSignature.FormalTypeParameter[] parentFormals = this.bcelObjectType.getAllFormals();
               GenericSignature.FormalTypeParameter[] formals = new GenericSignature.FormalTypeParameter[parentFormals.length + mSig.formalTypeParameters.length];
               System.arraycopy(mSig.formalTypeParameters, 0, formals, 0, mSig.formalTypeParameters.length);
               System.arraycopy(parentFormals, 0, formals, mSig.formalTypeParameters.length, parentFormals.length);
               GenericSignature.TypeSignature returnTypeSignature = mSig.returnType;

               try {
                  this.genericReturnType = BcelGenericSignatureToTypeXConverter.typeSignature2TypeX(returnTypeSignature, formals, this.bcelObjectType.getWorld());
               } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var10) {
                  throw new IllegalStateException("While determing the generic return type of " + this.toString() + " with generic signature " + gSig + " the following error was detected: " + var10.getMessage());
               }

               GenericSignature.TypeSignature[] paramTypeSigs = mSig.parameters;
               if (paramTypeSigs.length == 0) {
                  this.genericParameterTypes = UnresolvedType.NONE;
               } else {
                  this.genericParameterTypes = new UnresolvedType[paramTypeSigs.length];
               }

               for(int i = 0; i < paramTypeSigs.length; ++i) {
                  try {
                     this.genericParameterTypes[i] = BcelGenericSignatureToTypeXConverter.typeSignature2TypeX(paramTypeSigs[i], formals, this.bcelObjectType.getWorld());
                  } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var9) {
                     throw new IllegalStateException("While determining the generic parameter types of " + this.toString() + " with generic signature " + gSig + " the following error was detected: " + var9.getMessage());
                  }

                  if (paramTypeSigs[i] instanceof GenericSignature.TypeVariableSignature) {
                     this.bitflags |= 4;
                  }
               }
            } else {
               this.genericReturnType = this.getReturnType();
               this.genericParameterTypes = this.getParameterTypes();
            }

         }
      }
   }

   public void evictWeavingState() {
      if (this.method != null) {
         this.unpackGenericSignature();
         this.unpackJavaAttributes();
         this.ensureAnnotationsRetrieved();
         this.ensureParameterAnnotationsRetrieved();
         this.determineParameterNames();
         this.method = null;
      }

   }

   public boolean isSynthetic() {
      if ((this.bitflags & 1) == 0) {
         this.workOutIfSynthetic();
      }

      return (this.bitflags & 128) != 0;
   }

   private void workOutIfSynthetic() {
      if ((this.bitflags & 1) == 0) {
         this.bitflags |= 1;
         JavaClass jc = this.bcelObjectType.getJavaClass();
         this.bitflags &= 32639;
         if (jc != null) {
            if (jc.getMajor() < 49) {
               String[] synthetics = this.getAttributeNames(false);
               if (synthetics != null) {
                  for(int i = 0; i < synthetics.length; ++i) {
                     if (synthetics[i].equals("Synthetic")) {
                        this.bitflags |= 128;
                        break;
                     }
                  }
               }
            } else if ((this.modifiers & 4096) != 0) {
               this.bitflags |= 128;
            }

         }
      }
   }

   public boolean isEquivalentTo(Object other) {
      if (!(other instanceof BcelMethod)) {
         return false;
      } else {
         BcelMethod o = (BcelMethod)other;
         return this.getMethod().getCode().getCodeString().equals(o.getMethod().getCode().getCodeString());
      }
   }

   public boolean isDefaultConstructor() {
      boolean mightBe = !this.hasDeclarationLineNumberInfo() && this.name.equals("<init>") && this.parameterTypes.length == 0;
      return mightBe;
   }
}
