package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.AttributeUtils;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.EnclosingMethod;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.InnerClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.InnerClasses;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Signature;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.EnumElementValue;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.GenericSignature;
import com.bea.core.repackaged.aspectj.weaver.AbstractReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.AnnotationTargetKind;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.BindingScope;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.SourceContextImpl;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.patterns.Declare;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.weaver.patterns.FormalBinding;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BcelObjectType extends AbstractReferenceTypeDelegate {
   public JavaClass javaClass;
   private boolean artificial;
   private LazyClassGen lazyClassGen = null;
   private int modifiers;
   private String className;
   private String superclassSignature;
   private String superclassName;
   private String[] interfaceSignatures;
   private ResolvedMember[] fields = null;
   private ResolvedMember[] methods = null;
   private ResolvedType[] annotationTypes = null;
   private AnnotationAJ[] annotations = null;
   private TypeVariable[] typeVars = null;
   private String retentionPolicy;
   private AnnotationTargetKind[] annotationTargetKinds;
   private AjAttribute.WeaverVersionInfo wvInfo;
   private ResolvedPointcutDefinition[] pointcuts;
   private ResolvedMember[] privilegedAccess;
   private WeaverStateInfo weaverState;
   private PerClause perClause;
   private List typeMungers;
   private List declares;
   private GenericSignature.FormalTypeParameter[] formalsForResolution;
   private String declaredSignature;
   private boolean hasBeenWoven;
   private boolean isGenericType;
   private boolean isInterface;
   private boolean isEnum;
   private boolean isAnnotation;
   private boolean isAnonymous;
   private boolean isNested;
   private boolean isObject;
   private boolean isAnnotationStyleAspect;
   private boolean isCodeStyleAspect;
   private WeakReference superTypeReference;
   private WeakReference superInterfaceReferences;
   private int bitflag;
   private static final int DISCOVERED_ANNOTATION_RETENTION_POLICY = 1;
   private static final int UNPACKED_GENERIC_SIGNATURE = 2;
   private static final int UNPACKED_AJATTRIBUTES = 4;
   private static final int DISCOVERED_ANNOTATION_TARGET_KINDS = 8;
   private static final int DISCOVERED_DECLARED_SIGNATURE = 16;
   private static final int DISCOVERED_WHETHER_ANNOTATION_STYLE = 32;
   private static final int ANNOTATION_UNPACK_IN_PROGRESS = 256;
   private static final String[] NO_INTERFACE_SIGS = new String[0];

   BcelObjectType(ReferenceType resolvedTypeX, JavaClass javaClass, boolean artificial, boolean exposedToWeaver) {
      super(resolvedTypeX, exposedToWeaver);
      this.wvInfo = AjAttribute.WeaverVersionInfo.UNKNOWN;
      this.pointcuts = null;
      this.privilegedAccess = null;
      this.weaverState = null;
      this.perClause = null;
      this.typeMungers = Collections.emptyList();
      this.declares = Collections.emptyList();
      this.formalsForResolution = null;
      this.declaredSignature = null;
      this.hasBeenWoven = false;
      this.isGenericType = false;
      this.isObject = false;
      this.isAnnotationStyleAspect = false;
      this.isCodeStyleAspect = false;
      this.superTypeReference = new WeakReference((Object)null);
      this.superInterfaceReferences = new WeakReference((Object)null);
      this.bitflag = 0;
      this.javaClass = javaClass;
      this.artificial = artificial;
      this.initializeFromJavaclass();
      resolvedTypeX.setDelegate(this);
      ISourceContext sourceContext = resolvedTypeX.getSourceContext();
      if (sourceContext == SourceContextImpl.UNKNOWN_SOURCE_CONTEXT) {
         ISourceContext sourceContext = new SourceContextImpl(this);
         this.setSourceContext(sourceContext);
      }

      this.isObject = javaClass.getSuperclassNameIndex() == 0;
      this.ensureAspectJAttributesUnpacked();
      this.setSourcefilename(javaClass.getSourceFileName());
   }

   public void setJavaClass(JavaClass newclass, boolean artificial) {
      this.javaClass = newclass;
      this.artificial = artificial;
      this.resetState();
      this.initializeFromJavaclass();
   }

   public boolean isCacheable() {
      return true;
   }

   private void initializeFromJavaclass() {
      this.isInterface = this.javaClass.isInterface();
      this.isEnum = this.javaClass.isEnum();
      this.isAnnotation = this.javaClass.isAnnotation();
      this.isAnonymous = this.javaClass.isAnonymous();
      this.isNested = this.javaClass.isNested();
      this.modifiers = this.javaClass.getModifiers();
      this.superclassName = this.javaClass.getSuperclassName();
      this.className = this.javaClass.getClassName();
      this.cachedGenericClassTypeSignature = null;
   }

   public boolean isInterface() {
      return this.isInterface;
   }

   public boolean isEnum() {
      return this.isEnum;
   }

   public boolean isAnnotation() {
      return this.isAnnotation;
   }

   public boolean isAnonymous() {
      return this.isAnonymous;
   }

   public boolean isNested() {
      return this.isNested;
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public ResolvedType getSuperclass() {
      if (this.isObject) {
         return null;
      } else {
         ResolvedType supertype = (ResolvedType)this.superTypeReference.get();
         if (supertype == null) {
            this.ensureGenericSignatureUnpacked();
            if (this.superclassSignature == null) {
               if (this.superclassName == null) {
                  this.superclassName = this.javaClass.getSuperclassName();
               }

               this.superclassSignature = this.getResolvedTypeX().getWorld().resolve(UnresolvedType.forName(this.superclassName)).getSignature();
            }

            World world = this.getResolvedTypeX().getWorld();
            supertype = world.resolve(UnresolvedType.forSignature(this.superclassSignature));
            this.superTypeReference = new WeakReference(supertype);
         }

         return supertype;
      }
   }

   public World getWorld() {
      return this.getResolvedTypeX().getWorld();
   }

   public ResolvedType[] getDeclaredInterfaces() {
      ResolvedType[] cachedInterfaceTypes = (ResolvedType[])this.superInterfaceReferences.get();
      if (cachedInterfaceTypes != null) {
         return cachedInterfaceTypes;
      } else {
         this.ensureGenericSignatureUnpacked();
         ResolvedType[] interfaceTypes = null;
         int i;
         if (this.interfaceSignatures == null) {
            String[] names = this.javaClass.getInterfaceNames();
            if (names.length == 0) {
               this.interfaceSignatures = NO_INTERFACE_SIGS;
               interfaceTypes = ResolvedType.NONE;
            } else {
               this.interfaceSignatures = new String[names.length];
               interfaceTypes = new ResolvedType[names.length];
               i = 0;

               for(int len = names.length; i < len; ++i) {
                  interfaceTypes[i] = this.getResolvedTypeX().getWorld().resolve(UnresolvedType.forName(names[i]));
                  this.interfaceSignatures[i] = interfaceTypes[i].getSignature();
               }
            }
         } else {
            interfaceTypes = new ResolvedType[this.interfaceSignatures.length];
            int i = 0;

            for(i = this.interfaceSignatures.length; i < i; ++i) {
               interfaceTypes[i] = this.getResolvedTypeX().getWorld().resolve(UnresolvedType.forSignature(this.interfaceSignatures[i]));
            }
         }

         this.superInterfaceReferences = new WeakReference(interfaceTypes);
         return interfaceTypes;
      }
   }

   public ResolvedMember[] getDeclaredMethods() {
      this.ensureGenericSignatureUnpacked();
      if (this.methods == null) {
         Method[] ms = this.javaClass.getMethods();
         ResolvedMember[] newMethods = new ResolvedMember[ms.length];

         for(int i = ms.length - 1; i >= 0; --i) {
            newMethods[i] = new BcelMethod(this, ms[i]);
         }

         this.methods = newMethods;
      }

      return this.methods;
   }

   public ResolvedMember[] getDeclaredFields() {
      this.ensureGenericSignatureUnpacked();
      if (this.fields == null) {
         Field[] fs = this.javaClass.getFields();
         ResolvedMember[] newfields = new ResolvedMember[fs.length];
         int i = 0;

         for(int len = fs.length; i < len; ++i) {
            newfields[i] = new BcelField(this, fs[i]);
         }

         this.fields = newfields;
      }

      return this.fields;
   }

   public TypeVariable[] getTypeVariables() {
      if (!this.isGeneric()) {
         return TypeVariable.NONE;
      } else {
         if (this.typeVars == null) {
            GenericSignature.ClassSignature classSig = this.getGenericClassTypeSignature();
            this.typeVars = new TypeVariable[classSig.formalTypeParameters.length];

            for(int i = 0; i < this.typeVars.length; ++i) {
               GenericSignature.FormalTypeParameter ftp = classSig.formalTypeParameters[i];

               try {
                  this.typeVars[i] = BcelGenericSignatureToTypeXConverter.formalTypeParameter2TypeVariable(ftp, classSig.formalTypeParameters, this.getResolvedTypeX().getWorld());
               } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var5) {
                  throw new IllegalStateException("While getting the type variables for type " + this.toString() + " with generic signature " + classSig + " the following error condition was detected: " + var5.getMessage());
               }
            }
         }

         return this.typeVars;
      }
   }

   public Collection getTypeMungers() {
      return this.typeMungers;
   }

   public Collection getDeclares() {
      return this.declares;
   }

   public Collection getPrivilegedAccesses() {
      return this.privilegedAccess == null ? Collections.emptyList() : Arrays.asList(this.privilegedAccess);
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      return this.pointcuts;
   }

   public boolean isAspect() {
      return this.perClause != null;
   }

   public boolean isAnnotationStyleAspect() {
      if ((this.bitflag & 32) == 0) {
         this.bitflag |= 32;
         this.isAnnotationStyleAspect = !this.isCodeStyleAspect && this.hasAnnotation(AjcMemberMaker.ASPECT_ANNOTATION);
      }

      return this.isAnnotationStyleAspect;
   }

   private void ensureAspectJAttributesUnpacked() {
      if ((this.bitflag & 4) == 0) {
         this.bitflag |= 4;
         IMessageHandler msgHandler = this.getResolvedTypeX().getWorld().getMessageHandler();
         List l = null;

         try {
            l = Utility.readAjAttributes(this.className, this.javaClass.getAttributes(), this.getResolvedTypeX().getSourceContext(), this.getResolvedTypeX().getWorld(), AjAttribute.WeaverVersionInfo.UNKNOWN, new BcelConstantPoolReader(this.javaClass.getConstantPool()));
         } catch (RuntimeException var7) {
            throw new RuntimeException("Problem processing attributes in " + this.javaClass.getFileName(), var7);
         }

         List pointcuts = new ArrayList();
         this.typeMungers = new ArrayList();
         this.declares = new ArrayList();
         this.processAttributes(l, pointcuts, false);
         ReferenceType type = this.getResolvedTypeX();
         AsmManager asmManager = ((BcelWorld)type.getWorld()).getModelAsAsmManager();
         l = AtAjAttributes.readAj5ClassAttributes(asmManager, this.javaClass, type, type.getSourceContext(), msgHandler, this.isCodeStyleAspect);
         AjAttribute.Aspect deferredAspectAttribute = this.processAttributes(l, pointcuts, true);
         if (pointcuts.size() == 0) {
            this.pointcuts = ResolvedPointcutDefinition.NO_POINTCUTS;
         } else {
            this.pointcuts = (ResolvedPointcutDefinition[])pointcuts.toArray(new ResolvedPointcutDefinition[pointcuts.size()]);
         }

         this.resolveAnnotationDeclares(l);
         if (deferredAspectAttribute != null) {
            this.perClause = deferredAspectAttribute.reifyFromAtAspectJ(this.getResolvedTypeX());
         }

         if (this.isAspect() && !Modifier.isAbstract(this.getModifiers()) && this.isGeneric()) {
            msgHandler.handleMessage(MessageUtil.error("The generic aspect '" + this.getResolvedTypeX().getName() + "' must be declared abstract", this.getResolvedTypeX().getSourceLocation()));
         }

      }
   }

   private AjAttribute.Aspect processAttributes(List attributeList, List pointcuts, boolean fromAnnotations) {
      AjAttribute.Aspect deferredAspectAttribute = null;
      Iterator i$ = attributeList.iterator();

      while(i$.hasNext()) {
         AjAttribute a = (AjAttribute)i$.next();
         if (a instanceof AjAttribute.Aspect) {
            if (fromAnnotations) {
               deferredAspectAttribute = (AjAttribute.Aspect)a;
            } else {
               this.perClause = ((AjAttribute.Aspect)a).reify(this.getResolvedTypeX());
               this.isCodeStyleAspect = true;
            }
         } else if (a instanceof AjAttribute.PointcutDeclarationAttribute) {
            pointcuts.add(((AjAttribute.PointcutDeclarationAttribute)a).reify());
         } else if (a instanceof AjAttribute.WeaverState) {
            this.weaverState = ((AjAttribute.WeaverState)a).reify();
         } else if (a instanceof AjAttribute.TypeMunger) {
            this.typeMungers.add(((AjAttribute.TypeMunger)a).reify(this.getResolvedTypeX().getWorld(), this.getResolvedTypeX()));
         } else if (a instanceof AjAttribute.DeclareAttribute) {
            this.declares.add(((AjAttribute.DeclareAttribute)a).getDeclare());
         } else if (a instanceof AjAttribute.PrivilegedAttribute) {
            AjAttribute.PrivilegedAttribute privAttribute = (AjAttribute.PrivilegedAttribute)a;
            this.privilegedAccess = privAttribute.getAccessedMembers();
         } else if (a instanceof AjAttribute.SourceContextAttribute) {
            if (this.getResolvedTypeX().getSourceContext() instanceof SourceContextImpl) {
               AjAttribute.SourceContextAttribute sca = (AjAttribute.SourceContextAttribute)a;
               ((SourceContextImpl)this.getResolvedTypeX().getSourceContext()).configureFromAttribute(sca.getSourceFileName(), sca.getLineBreaks());
               this.setSourcefilename(sca.getSourceFileName());
            }
         } else {
            if (!(a instanceof AjAttribute.WeaverVersionInfo)) {
               throw new BCException("bad attribute " + a);
            }

            this.wvInfo = (AjAttribute.WeaverVersionInfo)a;
         }
      }

      return deferredAspectAttribute;
   }

   private void resolveAnnotationDeclares(List attributeList) {
      FormalBinding[] bindings = new FormalBinding[0];
      IScope bindingScope = new BindingScope(this.getResolvedTypeX(), this.getResolvedTypeX().getSourceContext(), bindings);
      Iterator iter = attributeList.iterator();

      while(iter.hasNext()) {
         AjAttribute a = (AjAttribute)iter.next();
         if (a instanceof AjAttribute.DeclareAttribute) {
            Declare decl = ((AjAttribute.DeclareAttribute)a).getDeclare();
            if (decl instanceof DeclareErrorOrWarning) {
               decl.resolve(bindingScope);
            } else if (decl instanceof DeclarePrecedence) {
               ((DeclarePrecedence)decl).setScopeForResolution(bindingScope);
            }
         }
      }

   }

   public PerClause getPerClause() {
      this.ensureAspectJAttributesUnpacked();
      return this.perClause;
   }

   public JavaClass getJavaClass() {
      return this.javaClass;
   }

   public boolean isArtificial() {
      return this.artificial;
   }

   public void resetState() {
      if (this.javaClass == null) {
         throw new BCException("can't weave evicted type");
      } else {
         this.bitflag = 0;
         this.annotationTypes = null;
         this.annotations = null;
         this.interfaceSignatures = null;
         this.superclassSignature = null;
         this.superclassName = null;
         this.fields = null;
         this.methods = null;
         this.pointcuts = null;
         this.perClause = null;
         this.weaverState = null;
         this.lazyClassGen = null;
         this.hasBeenWoven = false;
         this.isObject = this.javaClass.getSuperclassNameIndex() == 0;
         this.isAnnotationStyleAspect = false;
         this.ensureAspectJAttributesUnpacked();
      }
   }

   public void finishedWith() {
   }

   public WeaverStateInfo getWeaverState() {
      return this.weaverState;
   }

   void setWeaverState(WeaverStateInfo weaverState) {
      this.weaverState = weaverState;
   }

   public void printWackyStuff(PrintStream out) {
      if (this.typeMungers.size() > 0) {
         out.println("  TypeMungers: " + this.typeMungers);
      }

      if (this.declares.size() > 0) {
         out.println("     declares: " + this.declares);
      }

   }

   public LazyClassGen getLazyClassGen() {
      LazyClassGen ret = this.lazyClassGen;
      if (ret == null) {
         ret = new LazyClassGen(this);
         if (this.isAspect()) {
            this.lazyClassGen = ret;
         }
      }

      return ret;
   }

   public boolean isSynthetic() {
      return this.getResolvedTypeX().isSynthetic();
   }

   public AjAttribute.WeaverVersionInfo getWeaverVersionAttribute() {
      return this.wvInfo;
   }

   public ResolvedType[] getAnnotationTypes() {
      this.ensureAnnotationsUnpacked();
      return this.annotationTypes;
   }

   public AnnotationAJ[] getAnnotations() {
      this.ensureAnnotationsUnpacked();
      return this.annotations;
   }

   public boolean hasAnnotations() {
      this.ensureAnnotationsUnpacked();
      return this.annotations.length != 0;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      if (this.isUnpackingAnnotations()) {
         AnnotationGen[] annos = this.javaClass.getAnnotations();
         if (annos != null && annos.length != 0) {
            String lookingForSignature = ofType.getSignature();

            for(int a = 0; a < annos.length; ++a) {
               AnnotationGen annotation = annos[a];
               if (lookingForSignature.equals(annotation.getTypeSignature())) {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         this.ensureAnnotationsUnpacked();
         int i = 0;

         for(int max = this.annotationTypes.length; i < max; ++i) {
            UnresolvedType ax = this.annotationTypes[i];
            if (ax == null) {
               throw new RuntimeException("Annotation entry " + i + " on type " + this.getResolvedTypeX().getName() + " is null!");
            }

            if (ax.equals(ofType)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return this.getRetentionPolicy() == null ? false : this.getRetentionPolicy().equals("RUNTIME");
   }

   public String getRetentionPolicy() {
      if ((this.bitflag & 1) == 0) {
         this.bitflag |= 1;
         this.retentionPolicy = null;
         if (this.isAnnotation()) {
            this.ensureAnnotationsUnpacked();

            for(int i = this.annotations.length - 1; i >= 0; --i) {
               AnnotationAJ ax = this.annotations[i];
               if (ax.getTypeName().equals(UnresolvedType.AT_RETENTION.getName())) {
                  List values = ((BcelAnnotation)ax).getBcelAnnotation().getValues();
                  Iterator it = values.iterator();
                  if (it.hasNext()) {
                     NameValuePair element = (NameValuePair)it.next();
                     EnumElementValue v = (EnumElementValue)element.getValue();
                     this.retentionPolicy = v.getEnumValueString();
                     return this.retentionPolicy;
                  }
               }
            }
         }
      }

      return this.retentionPolicy;
   }

   public boolean canAnnotationTargetType() {
      AnnotationTargetKind[] targetKinds = this.getAnnotationTargetKinds();
      if (targetKinds == null) {
         return true;
      } else {
         for(int i = 0; i < targetKinds.length; ++i) {
            if (targetKinds[i].equals(AnnotationTargetKind.TYPE)) {
               return true;
            }
         }

         return false;
      }
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      if ((this.bitflag & 8) != 0) {
         return this.annotationTargetKinds;
      } else {
         this.bitflag |= 8;
         this.annotationTargetKinds = null;
         List targetKinds = new ArrayList();
         if (this.isAnnotation()) {
            AnnotationAJ[] annotationsOnThisType = this.getAnnotations();

            for(int i = 0; i < annotationsOnThisType.length; ++i) {
               AnnotationAJ a = annotationsOnThisType[i];
               if (a.getTypeName().equals(UnresolvedType.AT_TARGET.getName())) {
                  Set targets = a.getTargets();
                  if (targets != null) {
                     Iterator i$ = targets.iterator();

                     while(i$.hasNext()) {
                        String targetKind = (String)i$.next();
                        if (targetKind.equals("ANNOTATION_TYPE")) {
                           targetKinds.add(AnnotationTargetKind.ANNOTATION_TYPE);
                        } else if (targetKind.equals("CONSTRUCTOR")) {
                           targetKinds.add(AnnotationTargetKind.CONSTRUCTOR);
                        } else if (targetKind.equals("FIELD")) {
                           targetKinds.add(AnnotationTargetKind.FIELD);
                        } else if (targetKind.equals("LOCAL_VARIABLE")) {
                           targetKinds.add(AnnotationTargetKind.LOCAL_VARIABLE);
                        } else if (targetKind.equals("METHOD")) {
                           targetKinds.add(AnnotationTargetKind.METHOD);
                        } else if (targetKind.equals("PACKAGE")) {
                           targetKinds.add(AnnotationTargetKind.PACKAGE);
                        } else if (targetKind.equals("PARAMETER")) {
                           targetKinds.add(AnnotationTargetKind.PARAMETER);
                        } else if (targetKind.equals("TYPE")) {
                           targetKinds.add(AnnotationTargetKind.TYPE);
                        }
                     }
                  }
               }
            }

            if (!targetKinds.isEmpty()) {
               this.annotationTargetKinds = new AnnotationTargetKind[targetKinds.size()];
               return (AnnotationTargetKind[])targetKinds.toArray(this.annotationTargetKinds);
            }
         }

         return this.annotationTargetKinds;
      }
   }

   private boolean isUnpackingAnnotations() {
      return (this.bitflag & 256) != 0;
   }

   private void ensureAnnotationsUnpacked() {
      if (this.isUnpackingAnnotations()) {
         throw new BCException("Re-entered weaver instance whilst unpacking annotations on " + this.className);
      } else {
         if (this.annotationTypes == null) {
            try {
               this.bitflag |= 256;
               AnnotationGen[] annos = this.javaClass.getAnnotations();
               if (annos != null && annos.length != 0) {
                  World w = this.getResolvedTypeX().getWorld();
                  this.annotationTypes = new ResolvedType[annos.length];
                  this.annotations = new AnnotationAJ[annos.length];

                  for(int i = 0; i < annos.length; ++i) {
                     AnnotationGen annotation = annos[i];
                     String typeSignature = annotation.getTypeSignature();
                     ResolvedType rType = w.resolve(UnresolvedType.forSignature(typeSignature));
                     if (rType == null) {
                        throw new RuntimeException("Whilst unpacking annotations on '" + this.getResolvedTypeX().getName() + "', failed to resolve type '" + typeSignature + "'");
                     }

                     this.annotationTypes[i] = rType;
                     this.annotations[i] = new BcelAnnotation(annotation, rType);
                  }
               } else {
                  this.annotationTypes = ResolvedType.NONE;
                  this.annotations = AnnotationAJ.EMPTY_ARRAY;
               }
            } finally {
               this.bitflag &= -257;
            }
         }

      }
   }

   public String getDeclaredGenericSignature() {
      this.ensureGenericInfoProcessed();
      return this.declaredSignature;
   }

   private void ensureGenericSignatureUnpacked() {
      if ((this.bitflag & 2) == 0) {
         this.bitflag |= 2;
         if (this.getResolvedTypeX().getWorld().isInJava5Mode()) {
            GenericSignature.ClassSignature cSig = this.getGenericClassTypeSignature();
            if (cSig != null) {
               this.formalsForResolution = cSig.formalTypeParameters;
               if (this.isNested()) {
                  GenericSignature.FormalTypeParameter[] extraFormals = this.getFormalTypeParametersFromOuterClass();
                  if (extraFormals.length > 0) {
                     List allFormals = new ArrayList();

                     int i;
                     for(i = 0; i < this.formalsForResolution.length; ++i) {
                        allFormals.add(this.formalsForResolution[i]);
                     }

                     for(i = 0; i < extraFormals.length; ++i) {
                        allFormals.add(extraFormals[i]);
                     }

                     this.formalsForResolution = new GenericSignature.FormalTypeParameter[allFormals.size()];
                     allFormals.toArray(this.formalsForResolution);
                  }
               }

               GenericSignature.ClassTypeSignature superSig = cSig.superclassSignature;

               try {
                  ResolvedType rt = BcelGenericSignatureToTypeXConverter.classTypeSignature2TypeX(superSig, this.formalsForResolution, this.getResolvedTypeX().getWorld());
                  this.superclassSignature = rt.getSignature();
                  this.superclassName = rt.getName();
               } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var6) {
                  throw new IllegalStateException("While determining the generic superclass of " + this.className + " with generic signature " + this.getDeclaredGenericSignature() + " the following error was detected: " + var6.getMessage());
               }

               if (cSig.superInterfaceSignatures.length == 0) {
                  this.interfaceSignatures = NO_INTERFACE_SIGS;
               } else {
                  this.interfaceSignatures = new String[cSig.superInterfaceSignatures.length];

                  for(int i = 0; i < cSig.superInterfaceSignatures.length; ++i) {
                     try {
                        this.interfaceSignatures[i] = BcelGenericSignatureToTypeXConverter.classTypeSignature2TypeX(cSig.superInterfaceSignatures[i], this.formalsForResolution, this.getResolvedTypeX().getWorld()).getSignature();
                     } catch (BcelGenericSignatureToTypeXConverter.GenericSignatureFormatException var5) {
                        throw new IllegalStateException("While determing the generic superinterfaces of " + this.className + " with generic signature " + this.getDeclaredGenericSignature() + " the following error was detected: " + var5.getMessage());
                     }
                  }
               }
            }

            if (this.isGeneric()) {
               ReferenceType genericType = this.resolvedTypeX.getGenericType();
               if (genericType != null) {
                  genericType.setStartPos(this.resolvedTypeX.getStartPos());
                  this.resolvedTypeX = genericType;
               }
            }

         }
      }
   }

   public GenericSignature.FormalTypeParameter[] getAllFormals() {
      this.ensureGenericSignatureUnpacked();
      return this.formalsForResolution == null ? new GenericSignature.FormalTypeParameter[0] : this.formalsForResolution;
   }

   public ResolvedType getOuterClass() {
      if (!this.isNested()) {
         throw new IllegalStateException("Can't get the outer class of non-nested type: " + this.className);
      } else {
         Attribute[] arr$ = this.javaClass.getAttributes();
         int len$ = arr$.length;

         int i$;
         Attribute attr;
         for(i$ = 0; i$ < len$; ++i$) {
            attr = arr$[i$];
            if (attr instanceof InnerClasses) {
               InnerClass[] innerClss = ((InnerClasses)attr).getInnerClasses();
               ConstantPool cpool = this.javaClass.getConstantPool();
               InnerClass[] arr$ = innerClss;
               int len$ = innerClss.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  InnerClass innerCls = arr$[i$];
                  if (innerCls.getInnerClassIndex() != 0 && innerCls.getOuterClassIndex() != 0) {
                     ConstantClass innerClsInfo = (ConstantClass)cpool.getConstant(innerCls.getInnerClassIndex());
                     String innerClsName = cpool.getConstantUtf8(innerClsInfo.getNameIndex()).getValue().replace('/', '.');
                     if (innerClsName.compareTo(this.className) == 0) {
                        ConstantClass outerClsInfo = (ConstantClass)cpool.getConstant(innerCls.getOuterClassIndex());
                        String outerClsName = cpool.getConstantUtf8(outerClsInfo.getNameIndex()).getValue().replace('/', '.');
                        UnresolvedType outer = UnresolvedType.forName(outerClsName);
                        return outer.resolve(this.getResolvedTypeX().getWorld());
                     }
                  }
               }
            }
         }

         arr$ = this.javaClass.getAttributes();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            attr = arr$[i$];
            ConstantPool cpool = this.javaClass.getConstantPool();
            if (attr instanceof EnclosingMethod) {
               EnclosingMethod enclosingMethodAttribute = (EnclosingMethod)attr;
               if (enclosingMethodAttribute.getEnclosingClassIndex() != 0) {
                  ConstantClass outerClassInfo = enclosingMethodAttribute.getEnclosingClass();
                  String outerClassName = cpool.getConstantUtf8(outerClassInfo.getNameIndex()).getValue().replace('/', '.');
                  UnresolvedType outer = UnresolvedType.forName(outerClassName);
                  return outer.resolve(this.getResolvedTypeX().getWorld());
               }
            }
         }

         int lastDollar = this.className.lastIndexOf(36);
         if (lastDollar == -1) {
            return null;
         } else {
            String superClassName = this.className.substring(0, lastDollar);
            UnresolvedType outer = UnresolvedType.forName(superClassName);
            return outer.resolve(this.getResolvedTypeX().getWorld());
         }
      }
   }

   private void ensureGenericInfoProcessed() {
      if ((this.bitflag & 16) == 0) {
         this.bitflag |= 16;
         Signature sigAttr = AttributeUtils.getSignatureAttribute(this.javaClass.getAttributes());
         this.declaredSignature = sigAttr == null ? null : sigAttr.getSignature();
         if (this.declaredSignature != null) {
            this.isGenericType = this.declaredSignature.charAt(0) == '<';
         }

      }
   }

   public boolean isGeneric() {
      this.ensureGenericInfoProcessed();
      return this.isGenericType;
   }

   public String toString() {
      return this.javaClass == null ? "BcelObjectType" : "BcelObjectTypeFor:" + this.className;
   }

   public void evictWeavingState() {
      if (!this.getResolvedTypeX().getWorld().couldIncrementalCompileFollow()) {
         if (this.javaClass != null) {
            this.ensureAnnotationsUnpacked();
            this.ensureGenericInfoProcessed();
            this.getDeclaredInterfaces();
            this.getDeclaredFields();
            this.getDeclaredMethods();
            if (this.getResolvedTypeX().getWorld().isXnoInline()) {
               this.lazyClassGen = null;
            }

            if (this.weaverState != null) {
               this.weaverState.setReweavable(false);
               this.weaverState.setUnwovenClassFileData((byte[])null);
            }

            int i;
            for(i = this.methods.length - 1; i >= 0; --i) {
               this.methods[i].evictWeavingState();
            }

            for(i = this.fields.length - 1; i >= 0; --i) {
               this.fields[i].evictWeavingState();
            }

            this.javaClass = null;
            this.artificial = true;
         }

      }
   }

   public void weavingCompleted() {
      this.hasBeenWoven = true;
      if (this.getResolvedTypeX().getWorld().isRunMinimalMemory()) {
         this.evictWeavingState();
      }

      if (this.getSourceContext() != null && !this.getResolvedTypeX().isAspect()) {
         this.getSourceContext().tidy();
      }

   }

   public boolean hasBeenWoven() {
      return this.hasBeenWoven;
   }

   public boolean copySourceContext() {
      return false;
   }

   public void setExposedToWeaver(boolean b) {
      this.exposedToWeaver = b;
   }

   public int getCompilerVersion() {
      return this.wvInfo.getMajorVersion();
   }

   public void ensureConsistent() {
      this.superTypeReference.clear();
      this.superInterfaceReferences.clear();
   }

   public boolean isWeavable() {
      return true;
   }
}
