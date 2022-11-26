package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.lang.annotation.Aspect;
import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutDesignatorHandler;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutParameter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;

public class Java15ReflectionBasedReferenceTypeDelegate extends ReflectionBasedReferenceTypeDelegate {
   private AjType myType;
   private ResolvedType[] annotations;
   private ResolvedMember[] pointcuts;
   private ResolvedMember[] methods;
   private ResolvedMember[] fields;
   private TypeVariable[] typeVariables;
   private ResolvedType superclass;
   private ResolvedType[] superInterfaces;
   private String genericSignature = null;
   private JavaLangTypeToResolvedTypeConverter typeConverter;
   private Java15AnnotationFinder annotationFinder = null;
   private ArgNameFinder argNameFinder = null;

   public void initialize(ReferenceType aType, Class aClass, ClassLoader classLoader, World aWorld) {
      super.initialize(aType, aClass, classLoader, aWorld);
      this.myType = AjTypeSystem.getAjType(aClass);
      this.annotationFinder = new Java15AnnotationFinder();
      this.argNameFinder = this.annotationFinder;
      this.annotationFinder.setClassLoader(this.classLoaderReference.getClassLoader());
      this.annotationFinder.setWorld(aWorld);
      this.typeConverter = new JavaLangTypeToResolvedTypeConverter(aWorld);
   }

   public ReferenceType buildGenericType() {
      return (ReferenceType)UnresolvedType.forGenericTypeVariables(this.getResolvedTypeX().getSignature(), this.getTypeVariables()).resolve(this.getWorld());
   }

   public AnnotationAJ[] getAnnotations() {
      return super.getAnnotations();
   }

   public ResolvedType[] getAnnotationTypes() {
      if (this.annotations == null) {
         this.annotations = this.annotationFinder.getAnnotations(this.getBaseClass(), this.getWorld());
      }

      return this.annotations;
   }

   public boolean hasAnnotations() {
      if (this.annotations == null) {
         this.annotations = this.annotationFinder.getAnnotations(this.getBaseClass(), this.getWorld());
      }

      return this.annotations.length != 0;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      ResolvedType[] myAnns = this.getAnnotationTypes();
      ResolvedType toLookFor = ofType.resolve(this.getWorld());

      for(int i = 0; i < myAnns.length; ++i) {
         if (myAnns[i] == toLookFor) {
            return true;
         }
      }

      return false;
   }

   public ResolvedMember[] getDeclaredFields() {
      if (this.fields == null) {
         Field[] reflectFields = this.myType.getDeclaredFields();
         ResolvedMember[] rFields = new ResolvedMember[reflectFields.length];

         for(int i = 0; i < reflectFields.length; ++i) {
            rFields[i] = this.createGenericFieldMember(reflectFields[i]);
         }

         this.fields = rFields;
      }

      return this.fields;
   }

   public String getDeclaredGenericSignature() {
      if (this.genericSignature == null && this.isGeneric()) {
      }

      return this.genericSignature;
   }

   public ResolvedType[] getDeclaredInterfaces() {
      if (this.superInterfaces == null) {
         Type[] genericInterfaces = this.getBaseClass().getGenericInterfaces();
         this.superInterfaces = this.typeConverter.fromTypes(genericInterfaces);
      }

      return this.superInterfaces;
   }

   public ResolvedType getSuperclass() {
      if (this.superclass == null && this.getBaseClass() != Object.class) {
         Type t = this.getBaseClass().getGenericSuperclass();
         if (t != null) {
            this.superclass = this.typeConverter.fromType(t);
         }

         if (t == null) {
            this.superclass = this.getWorld().resolve(UnresolvedType.OBJECT);
         }
      }

      return this.superclass;
   }

   public TypeVariable[] getTypeVariables() {
      TypeVariable[] workInProgressSetOfVariables = this.getResolvedTypeX().getWorld().getTypeVariablesCurrentlyBeingProcessed(this.getBaseClass());
      if (workInProgressSetOfVariables != null) {
         return workInProgressSetOfVariables;
      } else {
         if (this.typeVariables == null) {
            java.lang.reflect.TypeVariable[] tVars = this.getBaseClass().getTypeParameters();
            TypeVariable[] rTypeVariables = new TypeVariable[tVars.length];

            int i;
            for(i = 0; i < tVars.length; ++i) {
               rTypeVariables[i] = new TypeVariable(tVars[i].getName());
            }

            this.getResolvedTypeX().getWorld().recordTypeVariablesCurrentlyBeingProcessed(this.getBaseClass(), rTypeVariables);

            for(i = 0; i < tVars.length; ++i) {
               TypeVariableReferenceType tvrt = (TypeVariableReferenceType)this.typeConverter.fromType(tVars[i]);
               TypeVariable tv = tvrt.getTypeVariable();
               rTypeVariables[i].setSuperclass(tv.getSuperclass());
               rTypeVariables[i].setAdditionalInterfaceBounds(tv.getSuperInterfaces());
               rTypeVariables[i].setDeclaringElement(tv.getDeclaringElement());
               rTypeVariables[i].setDeclaringElementKind(tv.getDeclaringElementKind());
               rTypeVariables[i].setRank(tv.getRank());
            }

            this.typeVariables = rTypeVariables;
            this.getResolvedTypeX().getWorld().forgetTypeVariablesCurrentlyBeingProcessed(this.getBaseClass());
         }

         return this.typeVariables;
      }
   }

   public ResolvedMember[] getDeclaredMethods() {
      if (this.methods == null) {
         Method[] reflectMethods = this.myType.getDeclaredMethods();
         Constructor[] reflectCons = this.myType.getDeclaredConstructors();
         ResolvedMember[] rMethods = new ResolvedMember[reflectMethods.length + reflectCons.length];

         int i;
         for(i = 0; i < reflectMethods.length; ++i) {
            rMethods[i] = this.createGenericMethodMember(reflectMethods[i]);
         }

         for(i = 0; i < reflectCons.length; ++i) {
            rMethods[i + reflectMethods.length] = this.createGenericConstructorMember(reflectCons[i]);
         }

         this.methods = rMethods;
      }

      return this.methods;
   }

   public ResolvedType getGenericResolvedType() {
      ResolvedType rt = this.getResolvedTypeX();
      return !rt.isParameterizedType() && !rt.isRawType() ? rt : rt.getGenericType();
   }

   private ResolvedMember createGenericMethodMember(Method forMethod) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(Member.METHOD, this.getGenericResolvedType(), forMethod.getModifiers(), this.typeConverter.fromType(forMethod.getReturnType()), forMethod.getName(), this.typeConverter.fromTypes(forMethod.getParameterTypes()), this.typeConverter.fromTypes(forMethod.getExceptionTypes()), forMethod);
      ret.setAnnotationFinder(this.annotationFinder);
      ret.setGenericSignatureInformationProvider(new Java15GenericSignatureInformationProvider(this.getWorld()));
      return ret;
   }

   private ResolvedMember createGenericConstructorMember(Constructor forConstructor) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(Member.METHOD, this.getGenericResolvedType(), forConstructor.getModifiers(), UnresolvedType.VOID, "<init>", this.typeConverter.fromTypes(forConstructor.getParameterTypes()), this.typeConverter.fromTypes(forConstructor.getExceptionTypes()), forConstructor);
      ret.setAnnotationFinder(this.annotationFinder);
      ret.setGenericSignatureInformationProvider(new Java15GenericSignatureInformationProvider(this.getWorld()));
      return ret;
   }

   private ResolvedMember createGenericFieldMember(Field forField) {
      ReflectionBasedResolvedMemberImpl ret = new ReflectionBasedResolvedMemberImpl(Member.FIELD, this.getGenericResolvedType(), forField.getModifiers(), this.typeConverter.fromType(forField.getType()), forField.getName(), new UnresolvedType[0], forField);
      ret.setAnnotationFinder(this.annotationFinder);
      ret.setGenericSignatureInformationProvider(new Java15GenericSignatureInformationProvider(this.getWorld()));
      return ret;
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      if (this.pointcuts == null) {
         Pointcut[] pcs = this.myType.getDeclaredPointcuts();
         this.pointcuts = new ResolvedMember[pcs.length];
         InternalUseOnlyPointcutParser parser = null;
         World world = this.getWorld();
         if (world instanceof ReflectionWorld) {
            parser = new InternalUseOnlyPointcutParser(this.classLoaderReference.getClassLoader(), (ReflectionWorld)this.getWorld());
         } else {
            parser = new InternalUseOnlyPointcutParser(this.classLoaderReference.getClassLoader());
         }

         Set additionalPointcutHandlers = world.getRegisteredPointcutHandlers();
         Iterator handlerIterator = additionalPointcutHandlers.iterator();

         while(handlerIterator.hasNext()) {
            PointcutDesignatorHandler handler = (PointcutDesignatorHandler)handlerIterator.next();
            parser.registerPointcutDesignatorHandler(handler);
         }

         for(int i = 0; i < pcs.length; ++i) {
            AjType[] ptypes = pcs[i].getParameterTypes();
            UnresolvedType[] weaverPTypes = new UnresolvedType[ptypes.length];

            for(int j = 0; j < weaverPTypes.length; ++j) {
               weaverPTypes[j] = this.typeConverter.fromType(ptypes[j].getJavaClass());
            }

            this.pointcuts[i] = new DeferredResolvedPointcutDefinition(this.getResolvedTypeX(), pcs[i].getModifiers(), pcs[i].getName(), weaverPTypes);
         }

         PointcutParameter[][] parameters = new PointcutParameter[pcs.length][];

         int i;
         for(i = 0; i < pcs.length; ++i) {
            AjType[] ptypes = pcs[i].getParameterTypes();
            String[] pnames = pcs[i].getParameterNames();
            if (pnames.length != ptypes.length) {
               pnames = this.tryToDiscoverParameterNames(pcs[i]);
               if (pnames == null || pnames.length != ptypes.length) {
                  throw new IllegalStateException("Required parameter names not available when parsing pointcut " + pcs[i].getName() + " in type " + this.getResolvedTypeX().getName());
               }
            }

            parameters[i] = new PointcutParameter[ptypes.length];

            for(int j = 0; j < parameters[i].length; ++j) {
               parameters[i][j] = parser.createPointcutParameter(pnames[j], ptypes[j].getJavaClass());
            }

            String pcExpr = pcs[i].getPointcutExpression().toString();
            com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut pc = parser.resolvePointcutExpression(pcExpr, this.getBaseClass(), parameters[i]);
            ((ResolvedPointcutDefinition)this.pointcuts[i]).setParameterNames(pnames);
            ((ResolvedPointcutDefinition)this.pointcuts[i]).setPointcut(pc);
         }

         for(i = 0; i < this.pointcuts.length; ++i) {
            ResolvedPointcutDefinition rpd = (ResolvedPointcutDefinition)this.pointcuts[i];
            rpd.setPointcut(parser.concretizePointcutExpression(rpd.getPointcut(), this.getBaseClass(), parameters[i]));
         }
      }

      return this.pointcuts;
   }

   private String[] tryToDiscoverParameterNames(Pointcut pcut) {
      Method[] ms = pcut.getDeclaringType().getJavaClass().getDeclaredMethods();
      Method[] arr$ = ms;
      int len$ = ms.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Method m = arr$[i$];
         if (m.getName().equals(pcut.getName())) {
            return this.argNameFinder.getParameterNames(m);
         }
      }

      return null;
   }

   public boolean isAnnotation() {
      return this.getBaseClass().isAnnotation();
   }

   public boolean isAnnotationStyleAspect() {
      return this.getBaseClass().isAnnotationPresent(Aspect.class);
   }

   public boolean isAnnotationWithRuntimeRetention() {
      if (!this.isAnnotation()) {
         return false;
      } else if (this.getBaseClass().isAnnotationPresent(Retention.class)) {
         Retention retention = (Retention)this.getBaseClass().getAnnotation(Retention.class);
         RetentionPolicy policy = retention.value();
         return policy == RetentionPolicy.RUNTIME;
      } else {
         return false;
      }
   }

   public boolean isAspect() {
      return this.myType.isAspect();
   }

   public boolean isEnum() {
      return this.getBaseClass().isEnum();
   }

   public boolean isGeneric() {
      return this.getBaseClass().getTypeParameters().length > 0;
   }

   public boolean isAnonymous() {
      return this.myClass.isAnonymousClass();
   }

   public boolean isNested() {
      return this.myClass.isMemberClass();
   }

   public ResolvedType getOuterClass() {
      return ReflectionBasedReferenceTypeDelegateFactory.resolveTypeInWorld(this.myClass.getEnclosingClass(), this.world);
   }
}
