package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassParser;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.INVOKEINTERFACE;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InvokeInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.MULTIANEWARRAY;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ClassLoaderReference;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ClassLoaderRepository;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ClassPath;
import com.bea.core.repackaged.aspectj.apache.bcel.util.NonCachingClassLoaderRepository;
import com.bea.core.repackaged.aspectj.apache.bcel.util.Repository;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IRelationship;
import com.bea.core.repackaged.aspectj.asm.internal.CharOperation;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.WeaveMessage;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.AdviceKind;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.AnnotationOnTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.Checker;
import com.bea.core.repackaged.aspectj.weaver.ICrossReferenceHandler;
import com.bea.core.repackaged.aspectj.weaver.IWeavingSupport;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberImpl;
import com.bea.core.repackaged.aspectj.weaver.MemberKind;
import com.bea.core.repackaged.aspectj.weaver.NewParentTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceTypeDelegate;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.loadtime.definition.Definition;
import com.bea.core.repackaged.aspectj.weaver.loadtime.definition.DocumentParser;
import com.bea.core.repackaged.aspectj.weaver.model.AsmRelationshipProvider;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.ParserException;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class BcelWorld extends World implements Repository {
   private final ClassPathManager classPath;
   protected Repository delegate;
   private BcelWeakClassLoaderReference loaderRef;
   private final BcelWeavingSupport bcelWeavingSupport;
   private boolean isXmlConfiguredWorld;
   private WeavingXmlConfig xmlConfiguration;
   private List typeDelegateResolvers;
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(BcelWorld.class);
   private List aspectRequiredTypesProcessed;
   private Map aspectRequiredTypes;

   public BcelWorld() {
      this("");
   }

   public BcelWorld(String cp) {
      this((List)makeDefaultClasspath(cp), IMessageHandler.THROW, (ICrossReferenceHandler)null);
   }

   public IRelationship.Kind determineRelKind(ShadowMunger munger) {
      AdviceKind ak = ((Advice)munger).getKind();
      if (ak.getKey() == AdviceKind.Before.getKey()) {
         return IRelationship.Kind.ADVICE_BEFORE;
      } else if (ak.getKey() == AdviceKind.After.getKey()) {
         return IRelationship.Kind.ADVICE_AFTER;
      } else if (ak.getKey() == AdviceKind.AfterThrowing.getKey()) {
         return IRelationship.Kind.ADVICE_AFTERTHROWING;
      } else if (ak.getKey() == AdviceKind.AfterReturning.getKey()) {
         return IRelationship.Kind.ADVICE_AFTERRETURNING;
      } else if (ak.getKey() == AdviceKind.Around.getKey()) {
         return IRelationship.Kind.ADVICE_AROUND;
      } else if (ak.getKey() != AdviceKind.CflowEntry.getKey() && ak.getKey() != AdviceKind.CflowBelowEntry.getKey() && ak.getKey() != AdviceKind.InterInitializer.getKey() && ak.getKey() != AdviceKind.PerCflowEntry.getKey() && ak.getKey() != AdviceKind.PerCflowBelowEntry.getKey() && ak.getKey() != AdviceKind.PerThisEntry.getKey() && ak.getKey() != AdviceKind.PerTargetEntry.getKey() && ak.getKey() != AdviceKind.Softener.getKey() && ak.getKey() != AdviceKind.PerTypeWithinEntry.getKey()) {
         throw new RuntimeException("Shadow.determineRelKind: What the hell is it? " + ak);
      } else {
         return null;
      }
   }

   public void reportMatch(ShadowMunger munger, Shadow shadow) {
      if (this.getCrossReferenceHandler() != null) {
         this.getCrossReferenceHandler().addCrossReference(munger.getSourceLocation(), shadow.getSourceLocation(), this.determineRelKind(munger).getName(), ((Advice)munger).hasDynamicTests());
      }

      if (!this.getMessageHandler().isIgnoring(IMessage.WEAVEINFO)) {
         this.reportWeavingMessage(munger, shadow);
      }

      if (this.getModel() != null) {
         AsmRelationshipProvider.addAdvisedRelationship(this.getModelAsAsmManager(), shadow, munger);
      }

   }

   private void reportWeavingMessage(ShadowMunger munger, Shadow shadow) {
      Advice advice = (Advice)munger;
      AdviceKind aKind = advice.getKind();
      if (aKind != null && advice.getConcreteAspect() != null) {
         if (aKind.equals(AdviceKind.Before) || aKind.equals(AdviceKind.After) || aKind.equals(AdviceKind.AfterReturning) || aKind.equals(AdviceKind.AfterThrowing) || aKind.equals(AdviceKind.Around) || aKind.equals(AdviceKind.Softener)) {
            if (shadow.getKind() == Shadow.SynchronizationUnlock) {
               if (advice.lastReportedMonitorExitJoinpointLocation == null) {
                  advice.lastReportedMonitorExitJoinpointLocation = shadow.getSourceLocation();
               } else {
                  if (this.areTheSame(shadow.getSourceLocation(), advice.lastReportedMonitorExitJoinpointLocation)) {
                     advice.lastReportedMonitorExitJoinpointLocation = null;
                     return;
                  }

                  advice.lastReportedMonitorExitJoinpointLocation = shadow.getSourceLocation();
               }
            }

            String description = advice.getKind().toString();
            String advisedType = shadow.getEnclosingType().getName();
            String advisingType = advice.getConcreteAspect().getName();
            Message msg = null;
            if (advice.getKind().equals(AdviceKind.Softener)) {
               msg = WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_SOFTENS, new String[]{advisedType, this.beautifyLocation(shadow.getSourceLocation()), advisingType, this.beautifyLocation(munger.getSourceLocation())}, advisedType, advisingType);
            } else {
               boolean runtimeTest = advice.hasDynamicTests();
               String joinPointDescription = shadow.toString();
               msg = WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_ADVISES, new String[]{joinPointDescription, advisedType, this.beautifyLocation(shadow.getSourceLocation()), description, advisingType, this.beautifyLocation(munger.getSourceLocation()), runtimeTest ? " [with runtime test]" : ""}, advisedType, advisingType);
            }

            this.getMessageHandler().handleMessage(msg);
         }
      }
   }

   private boolean areTheSame(ISourceLocation locA, ISourceLocation locB) {
      if (locA == null) {
         return locB == null;
      } else if (locB == null) {
         return false;
      } else if (locA.getLine() != locB.getLine()) {
         return false;
      } else {
         File fA = locA.getSourceFile();
         File fB = locA.getSourceFile();
         if (fA == null) {
            return fB == null;
         } else {
            return fB == null ? false : fA.getName().equals(fB.getName());
         }
      }
   }

   private String beautifyLocation(ISourceLocation isl) {
      StringBuffer nice = new StringBuffer();
      if (isl != null && isl.getSourceFile() != null && isl.getSourceFile().getName().indexOf("no debug info available") == -1) {
         int takeFrom = isl.getSourceFile().getPath().lastIndexOf(47);
         if (takeFrom == -1) {
            takeFrom = isl.getSourceFile().getPath().lastIndexOf(92);
         }

         int binary = isl.getSourceFile().getPath().lastIndexOf(33);
         if (binary != -1 && binary < takeFrom) {
            String pathToBinaryLoc = isl.getSourceFile().getPath().substring(0, binary + 1);
            if (pathToBinaryLoc.indexOf(".jar") != -1) {
               int lastSlash = pathToBinaryLoc.lastIndexOf(47);
               if (lastSlash == -1) {
                  lastSlash = pathToBinaryLoc.lastIndexOf(92);
               }

               nice.append(pathToBinaryLoc.substring(lastSlash + 1));
            }
         }

         nice.append(isl.getSourceFile().getPath().substring(takeFrom + 1));
         if (isl.getLine() != 0) {
            nice.append(":").append(isl.getLine());
         }

         if (isl.getSourceFileName() != null) {
            nice.append("(from " + isl.getSourceFileName() + ")");
         }
      } else {
         nice.append("no debug info available");
      }

      return nice.toString();
   }

   private static List makeDefaultClasspath(String cp) {
      List classPath = new ArrayList();
      classPath.addAll(getPathEntries(cp));
      classPath.addAll(getPathEntries(ClassPath.getClassPath()));
      return classPath;
   }

   private static List getPathEntries(String s) {
      List ret = new ArrayList();
      StringTokenizer tok = new StringTokenizer(s, File.pathSeparator);

      while(tok.hasMoreTokens()) {
         ret.add(tok.nextToken());
      }

      return ret;
   }

   public BcelWorld(List classPath, IMessageHandler handler, ICrossReferenceHandler xrefHandler) {
      this.bcelWeavingSupport = new BcelWeavingSupport();
      this.isXmlConfiguredWorld = false;
      this.aspectRequiredTypesProcessed = new ArrayList();
      this.aspectRequiredTypes = null;
      this.classPath = new ClassPathManager(classPath, handler);
      this.setMessageHandler(handler);
      this.setCrossReferenceHandler(xrefHandler);
      this.delegate = this;
   }

   public BcelWorld(ClassPathManager cpm, IMessageHandler handler, ICrossReferenceHandler xrefHandler) {
      this.bcelWeavingSupport = new BcelWeavingSupport();
      this.isXmlConfiguredWorld = false;
      this.aspectRequiredTypesProcessed = new ArrayList();
      this.aspectRequiredTypes = null;
      this.classPath = cpm;
      this.setMessageHandler(handler);
      this.setCrossReferenceHandler(xrefHandler);
      this.delegate = this;
   }

   public BcelWorld(ClassLoader loader, IMessageHandler handler, ICrossReferenceHandler xrefHandler) {
      this.bcelWeavingSupport = new BcelWeavingSupport();
      this.isXmlConfiguredWorld = false;
      this.aspectRequiredTypesProcessed = new ArrayList();
      this.aspectRequiredTypes = null;
      this.classPath = null;
      this.loaderRef = new BcelWeakClassLoaderReference(loader);
      this.setMessageHandler(handler);
      this.setCrossReferenceHandler(xrefHandler);
   }

   public void ensureRepositorySetup() {
      if (this.delegate == null) {
         this.delegate = this.getClassLoaderRepositoryFor(this.loaderRef);
      }

   }

   public Repository getClassLoaderRepositoryFor(ClassLoaderReference loader) {
      return (Repository)(this.bcelRepositoryCaching ? new ClassLoaderRepository(loader) : new NonCachingClassLoaderRepository(loader));
   }

   public void addPath(String name) {
      this.classPath.addPath(name, this.getMessageHandler());
   }

   public static Type makeBcelType(UnresolvedType type) {
      return Type.getType(type.getErasureSignature());
   }

   static Type[] makeBcelTypes(UnresolvedType[] types) {
      Type[] ret = new Type[types.length];
      int i = 0;

      for(int len = types.length; i < len; ++i) {
         ret[i] = makeBcelType(types[i]);
      }

      return ret;
   }

   public static Type[] makeBcelTypes(String[] types) {
      if (types != null && types.length != 0) {
         Type[] ret = new Type[types.length];
         int i = 0;

         for(int len = types.length; i < len; ++i) {
            ret[i] = makeBcelType(types[i]);
         }

         return ret;
      } else {
         return null;
      }
   }

   public static Type makeBcelType(String type) {
      return Type.getType(type);
   }

   static String[] makeBcelTypesAsClassNames(UnresolvedType[] types) {
      String[] ret = new String[types.length];
      int i = 0;

      for(int len = types.length; i < len; ++i) {
         ret[i] = types[i].getName();
      }

      return ret;
   }

   public static UnresolvedType fromBcel(Type t) {
      return UnresolvedType.forSignature(t.getSignature());
   }

   static UnresolvedType[] fromBcel(Type[] ts) {
      UnresolvedType[] ret = new UnresolvedType[ts.length];
      int i = 0;

      for(int len = ts.length; i < len; ++i) {
         ret[i] = fromBcel(ts[i]);
      }

      return ret;
   }

   public ResolvedType resolve(Type t) {
      return this.resolve(fromBcel(t));
   }

   protected ReferenceTypeDelegate resolveDelegate(ReferenceType ty) {
      String name = ty.getName();
      this.ensureAdvancedConfigurationProcessed();
      JavaClass jc = this.lookupJavaClass(this.classPath, name);
      if (jc != null) {
         return this.buildBcelDelegate(ty, jc, false, false);
      } else {
         if (this.typeDelegateResolvers != null) {
            Iterator i$ = this.typeDelegateResolvers.iterator();

            while(i$.hasNext()) {
               TypeDelegateResolver tdr = (TypeDelegateResolver)i$.next();
               ReferenceTypeDelegate delegate = tdr.getDelegate(ty);
               if (delegate != null) {
                  return delegate;
               }
            }
         }

         return null;
      }
   }

   public BcelObjectType buildBcelDelegate(ReferenceType type, JavaClass jc, boolean artificial, boolean exposedToWeaver) {
      BcelObjectType ret = new BcelObjectType(type, jc, artificial, exposedToWeaver);
      return ret;
   }

   private JavaClass lookupJavaClass(ClassPathManager classPath, String name) {
      if (classPath == null) {
         try {
            this.ensureRepositorySetup();
            JavaClass jc = this.delegate.loadClass(name);
            if (trace.isTraceEnabled()) {
               trace.event("lookupJavaClass", this, (Object[])(new Object[]{name, jc}));
            }

            return jc;
         } catch (ClassNotFoundException var11) {
            if (trace.isTraceEnabled()) {
               trace.error("Unable to find class '" + name + "' in repository", var11);
            }

            return null;
         }
      } else {
         ClassPathManager.ClassFile file = null;

         ClassParser parser;
         try {
            JavaClass jc;
            try {
               file = classPath.find(UnresolvedType.forName(name));
               if (file != null) {
                  parser = new ClassParser(file.getInputStream(), file.getPath());
                  jc = parser.parse();
                  JavaClass var6 = jc;
                  return var6;
               }

               parser = null;
            } catch (IOException var12) {
               if (trace.isTraceEnabled()) {
                  trace.error("IOException whilst processing class", var12);
               }

               jc = null;
               return jc;
            }
         } finally {
            if (file != null) {
               file.close();
            }

         }

         return parser;
      }
   }

   public BcelObjectType addSourceObjectType(JavaClass jc, boolean artificial) {
      return this.addSourceObjectType(jc.getClassName(), jc, artificial);
   }

   public BcelObjectType addSourceObjectType(String classname, JavaClass jc, boolean artificial) {
      BcelObjectType ret = null;
      if (!jc.getClassName().equals(classname)) {
         throw new RuntimeException(jc.getClassName() + "!=" + classname);
      } else {
         String signature = UnresolvedType.forName(jc.getClassName()).getSignature();
         ResolvedType resolvedTypeFromTypeMap = this.typeMap.get(signature);
         if (resolvedTypeFromTypeMap != null && !(resolvedTypeFromTypeMap instanceof ReferenceType)) {
            StringBuffer exceptionText = new StringBuffer();
            exceptionText.append("Found invalid (not a ReferenceType) entry in the type map. ");
            exceptionText.append("Signature=[" + signature + "] Found=[" + resolvedTypeFromTypeMap + "] Class=[" + resolvedTypeFromTypeMap.getClass() + "]");
            throw new BCException(exceptionText.toString());
         } else {
            ReferenceType referenceTypeFromTypeMap = (ReferenceType)resolvedTypeFromTypeMap;
            if (referenceTypeFromTypeMap == null) {
               if (jc.isGeneric() && this.isInJava5Mode()) {
                  ReferenceType rawType = ReferenceType.fromTypeX(UnresolvedType.forRawTypeName(jc.getClassName()), this);
                  ret = this.buildBcelDelegate(rawType, jc, artificial, true);
                  ReferenceType genericRefType = new ReferenceType(UnresolvedType.forGenericTypeSignature(signature, ret.getDeclaredGenericSignature()), this);
                  rawType.setDelegate(ret);
                  genericRefType.setDelegate(ret);
                  rawType.setGenericType(genericRefType);
                  this.typeMap.put(signature, rawType);
               } else {
                  referenceTypeFromTypeMap = new ReferenceType(signature, this);
                  ret = this.buildBcelDelegate(referenceTypeFromTypeMap, jc, artificial, true);
                  this.typeMap.put(signature, referenceTypeFromTypeMap);
               }
            } else {
               ret = this.buildBcelDelegate(referenceTypeFromTypeMap, jc, artificial, true);
            }

            return ret;
         }
      }
   }

   public BcelObjectType addSourceObjectType(String classname, byte[] bytes, boolean artificial) {
      BcelObjectType retval = null;
      String signature = UnresolvedType.forName(classname).getSignature();
      ResolvedType resolvedTypeFromTypeMap = this.typeMap.get(signature);
      if (resolvedTypeFromTypeMap != null && !(resolvedTypeFromTypeMap instanceof ReferenceType)) {
         StringBuffer exceptionText = new StringBuffer();
         exceptionText.append("Found invalid (not a ReferenceType) entry in the type map. ");
         exceptionText.append("Signature=[" + signature + "] Found=[" + resolvedTypeFromTypeMap + "] Class=[" + resolvedTypeFromTypeMap.getClass() + "]");
         throw new BCException(exceptionText.toString());
      } else {
         ReferenceType referenceTypeFromTypeMap = (ReferenceType)resolvedTypeFromTypeMap;
         if (referenceTypeFromTypeMap == null) {
            JavaClass jc = Utility.makeJavaClass(classname, bytes);
            if (jc.isGeneric() && this.isInJava5Mode()) {
               referenceTypeFromTypeMap = ReferenceType.fromTypeX(UnresolvedType.forRawTypeName(jc.getClassName()), this);
               retval = this.buildBcelDelegate(referenceTypeFromTypeMap, jc, artificial, true);
               ReferenceType genericRefType = new ReferenceType(UnresolvedType.forGenericTypeSignature(signature, retval.getDeclaredGenericSignature()), this);
               referenceTypeFromTypeMap.setDelegate(retval);
               genericRefType.setDelegate(retval);
               referenceTypeFromTypeMap.setGenericType(genericRefType);
               this.typeMap.put(signature, referenceTypeFromTypeMap);
            } else {
               referenceTypeFromTypeMap = new ReferenceType(signature, this);
               retval = this.buildBcelDelegate(referenceTypeFromTypeMap, jc, artificial, true);
               this.typeMap.put(signature, referenceTypeFromTypeMap);
            }
         } else {
            ReferenceTypeDelegate existingDelegate = referenceTypeFromTypeMap.getDelegate();
            if (!(existingDelegate instanceof BcelObjectType)) {
               throw new IllegalStateException("For " + classname + " should be BcelObjectType, but is " + existingDelegate.getClass());
            }

            retval = (BcelObjectType)existingDelegate;
            retval = this.buildBcelDelegate(referenceTypeFromTypeMap, Utility.makeJavaClass(classname, bytes), artificial, true);
         }

         return retval;
      }
   }

   void deleteSourceObjectType(UnresolvedType ty) {
      this.typeMap.remove(ty.getSignature());
   }

   public static Member makeFieldJoinPointSignature(LazyClassGen cg, FieldInstruction fi) {
      ConstantPool cpg = cg.getConstantPool();
      return MemberImpl.field(fi.getClassName(cpg), fi.opcode != 178 && fi.opcode != 179 ? 0 : 8, fi.getName(cpg), fi.getSignature(cpg));
   }

   public Member makeJoinPointSignatureFromMethod(LazyMethodGen mg, MemberKind kind) {
      Member ret = mg.getMemberView();
      if (ret == null) {
         int mods = mg.getAccessFlags();
         if (mg.getEnclosingClass().isInterface()) {
            mods |= 512;
         }

         return new ResolvedMemberImpl(kind, UnresolvedType.forName(mg.getClassName()), mods, fromBcel(mg.getReturnType()), mg.getName(), fromBcel(mg.getArgumentTypes()));
      } else {
         return ret;
      }
   }

   public Member makeJoinPointSignatureForMonitorEnter(LazyClassGen cg, InstructionHandle h) {
      return MemberImpl.monitorEnter();
   }

   public Member makeJoinPointSignatureForMonitorExit(LazyClassGen cg, InstructionHandle h) {
      return MemberImpl.monitorExit();
   }

   public Member makeJoinPointSignatureForArrayConstruction(LazyClassGen cg, InstructionHandle handle) {
      Instruction i = handle.getInstruction();
      ConstantPool cpg = cg.getConstantPool();
      Member retval = null;
      Type ot;
      UnresolvedType ut;
      if (i.opcode == 189) {
         ot = i.getType(cpg);
         ut = fromBcel(ot);
         ut = UnresolvedType.makeArray(ut, 1);
         retval = MemberImpl.method(ut, 1, UnresolvedType.VOID, "<init>", new ResolvedType[]{this.INT});
      } else if (i instanceof MULTIANEWARRAY) {
         MULTIANEWARRAY arrayInstruction = (MULTIANEWARRAY)i;
         ut = null;
         short dimensions = arrayInstruction.getDimensions();
         ObjectType ot = arrayInstruction.getLoadClassType(cpg);
         if (ot != null) {
            ut = fromBcel((Type)ot);
            ut = UnresolvedType.makeArray(ut, dimensions);
         } else {
            Type t = arrayInstruction.getType(cpg);
            ut = fromBcel(t);
         }

         ResolvedType[] parms = new ResolvedType[dimensions];

         for(int ii = 0; ii < dimensions; ++ii) {
            parms[ii] = this.INT;
         }

         retval = MemberImpl.method(ut, 1, UnresolvedType.VOID, "<init>", parms);
      } else {
         if (i.opcode != 188) {
            throw new BCException("Cannot create array construction signature for this non-array instruction:" + i);
         }

         ot = i.getType();
         ut = fromBcel(ot);
         retval = MemberImpl.method(ut, 1, UnresolvedType.VOID, "<init>", new ResolvedType[]{this.INT});
      }

      return retval;
   }

   public Member makeJoinPointSignatureForMethodInvocation(LazyClassGen cg, InvokeInstruction ii) {
      ConstantPool cpg = cg.getConstantPool();
      String name = ii.getName(cpg);
      String declaring = ii.getClassName(cpg);
      UnresolvedType declaringType = null;
      String signature = ii.getSignature(cpg);
      if (name.startsWith("ajc$privMethod$")) {
         try {
            declaringType = UnresolvedType.forName(declaring);
            String typeNameAsFoundInAccessorName = declaringType.getName().replace('.', '_');
            int indexInAccessorName = name.lastIndexOf(typeNameAsFoundInAccessorName);
            if (indexInAccessorName != -1) {
               String methodName = name.substring(indexInAccessorName + typeNameAsFoundInAccessorName.length() + 1);
               ResolvedType resolvedDeclaringType = declaringType.resolve(this);
               ResolvedMember[] methods = resolvedDeclaringType.getDeclaredMethods();
               ResolvedMember[] arr$ = methods;
               int len$ = methods.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  ResolvedMember method = arr$[i$];
                  if (method.getName().equals(methodName) && method.getSignature().equals(signature) && Modifier.isPrivate(method.getModifiers())) {
                     return method;
                  }
               }
            }
         } catch (Exception var17) {
            var17.printStackTrace();
         }
      }

      int modifier = ii instanceof INVOKEINTERFACE ? 512 : (ii.opcode == 184 ? 8 : (ii.opcode == 183 && !name.equals("<init>") ? 2 : 0));
      if (ii.opcode == 184) {
         ResolvedType appearsDeclaredBy = this.resolve(declaring);
         Iterator iterator = appearsDeclaredBy.getMethods(true, true);

         while(iterator.hasNext()) {
            ResolvedMember method = (ResolvedMember)iterator.next();
            if (Modifier.isStatic(method.getModifiers()) && name.equals(method.getName()) && signature.equals(method.getSignature())) {
               declaringType = method.getDeclaringType();
               break;
            }
         }
      }

      if (declaringType == null) {
         if (declaring.charAt(0) == '[') {
            declaringType = UnresolvedType.forSignature(declaring);
         } else {
            declaringType = UnresolvedType.forName(declaring);
         }
      }

      return MemberImpl.method(declaringType, modifier, name, signature);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("BcelWorld(");
      buf.append(")");
      return buf.toString();
   }

   public static BcelObjectType getBcelObjectType(ResolvedType concreteAspect) {
      if (concreteAspect == null) {
         return null;
      } else if (!(concreteAspect instanceof ReferenceType)) {
         return null;
      } else {
         ReferenceTypeDelegate rtDelegate = ((ReferenceType)concreteAspect).getDelegate();
         return rtDelegate instanceof BcelObjectType ? (BcelObjectType)rtDelegate : null;
      }
   }

   public void tidyUp() {
      this.classPath.closeArchives();
      this.typeMap.report();
      this.typeMap.demote(true);
   }

   public JavaClass findClass(String className) {
      return this.lookupJavaClass(this.classPath, className);
   }

   public JavaClass loadClass(String className) throws ClassNotFoundException {
      return this.lookupJavaClass(this.classPath, className);
   }

   public void storeClass(JavaClass clazz) {
   }

   public void removeClass(JavaClass clazz) {
      throw new RuntimeException("Not implemented");
   }

   public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
      throw new RuntimeException("Not implemented");
   }

   public void clear() {
      this.delegate.clear();
   }

   public void validateType(UnresolvedType type) {
      ResolvedType result = this.typeMap.get(type.getSignature());
      if (result != null) {
         if (result.isExposedToWeaver()) {
            result.ensureConsistent();
         }
      }
   }

   private boolean applyDeclareParents(DeclareParents p, ResolvedType onType) {
      boolean didSomething = false;
      List newParents = p.findMatchingNewParents(onType, true);
      if (!newParents.isEmpty()) {
         didSomething = true;
         BcelObjectType classType = getBcelObjectType(onType);
         Iterator i$ = newParents.iterator();

         while(i$.hasNext()) {
            ResolvedType newParent = (ResolvedType)i$.next();
            onType.addParent(newParent);
            ResolvedTypeMunger newParentMunger = new NewParentTypeMunger(newParent, p.getDeclaringType());
            newParentMunger.setSourceLocation(p.getSourceLocation());
            onType.addInterTypeMunger(new BcelTypeMunger(newParentMunger, this.getCrosscuttingMembersSet().findAspectDeclaringParents(p)), false);
         }
      }

      return didSomething;
   }

   private boolean applyDeclareAtType(DeclareAnnotation decA, ResolvedType onType, boolean reportProblems) {
      boolean didSomething = false;
      if (decA.matches(onType)) {
         if (onType.hasAnnotation(decA.getAnnotation().getType())) {
            return false;
         }

         AnnotationAJ annoX = decA.getAnnotation();
         boolean isOK = this.checkTargetOK(decA, onType, annoX);
         if (isOK) {
            didSomething = true;
            ResolvedTypeMunger newAnnotationTM = new AnnotationOnTypeMunger(annoX);
            newAnnotationTM.setSourceLocation(decA.getSourceLocation());
            onType.addInterTypeMunger(new BcelTypeMunger(newAnnotationTM, decA.getAspect().resolve(this)), false);
            decA.copyAnnotationTo(onType);
         }
      }

      return didSomething;
   }

   private boolean applyDeclareAtField(DeclareAnnotation deca, ResolvedType type) {
      boolean changedType = false;
      ResolvedMember[] fields = type.getDeclaredFields();
      ResolvedMember[] arr$ = fields;
      int len$ = fields.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedMember field = arr$[i$];
         if (deca.matches(field, this)) {
            AnnotationAJ anno = deca.getAnnotation();
            if (!field.hasAnnotation(anno.getType())) {
               field.addAnnotation(anno);
               changedType = true;
            }
         }
      }

      return changedType;
   }

   private boolean checkTargetOK(DeclareAnnotation decA, ResolvedType onType, AnnotationAJ annoX) {
      return !annoX.specifiesTarget() || (!onType.isAnnotation() || annoX.allowedOnAnnotationType()) && annoX.allowedOnRegularType();
   }

   protected void weaveInterTypeDeclarations(ResolvedType onType) {
      List declareParentsList = this.getCrosscuttingMembersSet().getDeclareParents();
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      ((ResolvedType)onType).clearInterTypeMungers();
      List decpToRepeat = new ArrayList();
      boolean aParentChangeOccurred = false;
      boolean anAnnotationChangeOccurred = false;
      Iterator i$ = declareParentsList.iterator();

      boolean typeChanged;
      while(i$.hasNext()) {
         DeclareParents decp = (DeclareParents)i$.next();
         typeChanged = this.applyDeclareParents(decp, (ResolvedType)onType);
         if (typeChanged) {
            aParentChangeOccurred = true;
         } else if (!decp.getChild().isStarAnnotation()) {
            decpToRepeat.add(decp);
         }
      }

      i$ = this.getCrosscuttingMembersSet().getDeclareAnnotationOnTypes().iterator();

      DeclareAnnotation deca;
      while(i$.hasNext()) {
         deca = (DeclareAnnotation)i$.next();
         typeChanged = this.applyDeclareAtType(deca, (ResolvedType)onType, true);
         if (typeChanged) {
            anAnnotationChangeOccurred = true;
         }
      }

      i$ = this.getCrosscuttingMembersSet().getDeclareAnnotationOnFields().iterator();

      while(i$.hasNext()) {
         deca = (DeclareAnnotation)i$.next();
         if (this.applyDeclareAtField(deca, (ResolvedType)onType)) {
            anAnnotationChangeOccurred = true;
         }
      }

      while((aParentChangeOccurred || anAnnotationChangeOccurred) && !decpToRepeat.isEmpty()) {
         aParentChangeOccurred = false;
         anAnnotationChangeOccurred = false;
         List decpToRepeatNextTime = new ArrayList();
         Iterator i$ = decpToRepeat.iterator();

         while(i$.hasNext()) {
            DeclareParents decp = (DeclareParents)i$.next();
            if (this.applyDeclareParents(decp, (ResolvedType)onType)) {
               aParentChangeOccurred = true;
            } else {
               decpToRepeatNextTime.add(decp);
            }
         }

         i$ = this.getCrosscuttingMembersSet().getDeclareAnnotationOnTypes().iterator();

         DeclareAnnotation deca;
         while(i$.hasNext()) {
            deca = (DeclareAnnotation)i$.next();
            if (this.applyDeclareAtType(deca, (ResolvedType)onType, false)) {
               anAnnotationChangeOccurred = true;
            }
         }

         i$ = this.getCrosscuttingMembersSet().getDeclareAnnotationOnFields().iterator();

         while(i$.hasNext()) {
            deca = (DeclareAnnotation)i$.next();
            if (this.applyDeclareAtField(deca, (ResolvedType)onType)) {
               anAnnotationChangeOccurred = true;
            }
         }

         decpToRepeat = decpToRepeatNextTime;
      }

   }

   public IWeavingSupport getWeavingSupport() {
      return this.bcelWeavingSupport;
   }

   public void reportCheckerMatch(Checker checker, Shadow shadow) {
      IMessage iMessage = new Message(checker.getMessage(shadow), shadow.toString(), checker.isError() ? IMessage.ERROR : IMessage.WARNING, shadow.getSourceLocation(), (Throwable)null, new ISourceLocation[]{checker.getSourceLocation()}, true, 0, -1, -1);
      this.getMessageHandler().handleMessage(iMessage);
      if (this.getCrossReferenceHandler() != null) {
         this.getCrossReferenceHandler().addCrossReference(checker.getSourceLocation(), shadow.getSourceLocation(), checker.isError() ? IRelationship.Kind.DECLARE_ERROR.getName() : IRelationship.Kind.DECLARE_WARNING.getName(), false);
      }

      if (this.getModel() != null) {
         AsmRelationshipProvider.addDeclareErrorOrWarningRelationship(this.getModelAsAsmManager(), shadow, checker);
      }

   }

   public AsmManager getModelAsAsmManager() {
      return (AsmManager)this.getModel();
   }

   void raiseError(String message) {
      this.getMessageHandler().handleMessage(MessageUtil.error(message));
   }

   public void setXmlFiles(List xmlFiles) {
      if (!this.isXmlConfiguredWorld && !xmlFiles.isEmpty()) {
         this.raiseError("xml configuration files only supported by the compiler when -xmlConfigured option specified");
      } else {
         if (!xmlFiles.isEmpty()) {
            this.xmlConfiguration = new WeavingXmlConfig(this, 1);
         }

         Iterator i$ = xmlFiles.iterator();

         while(i$.hasNext()) {
            File xmlfile = (File)i$.next();

            try {
               Definition d = DocumentParser.parse(xmlfile.toURI().toURL());
               this.xmlConfiguration.add(d);
            } catch (MalformedURLException var5) {
               this.raiseError("Unexpected problem processing XML config file '" + xmlfile.getName() + "' :" + var5.getMessage());
            } catch (Exception var6) {
               this.raiseError("Unexpected problem processing XML config file '" + xmlfile.getName() + "' :" + var6.getMessage());
            }
         }

      }
   }

   public void addScopedAspect(String name, String scope) {
      this.isXmlConfiguredWorld = true;
      if (this.xmlConfiguration == null) {
         this.xmlConfiguration = new WeavingXmlConfig(this, 2);
      }

      this.xmlConfiguration.addScopedAspect(name, scope);
   }

   public void setXmlConfigured(boolean b) {
      this.isXmlConfiguredWorld = b;
   }

   public boolean isXmlConfigured() {
      return this.isXmlConfiguredWorld && this.xmlConfiguration != null;
   }

   public WeavingXmlConfig getXmlConfiguration() {
      return this.xmlConfiguration;
   }

   public boolean isAspectIncluded(ResolvedType aspectType) {
      return !this.isXmlConfigured() ? true : this.xmlConfiguration.specifiesInclusionOfAspect(aspectType.getName());
   }

   public TypePattern getAspectScope(ResolvedType declaringType) {
      return this.xmlConfiguration.getScopeFor(declaringType.getName());
   }

   public boolean hasUnsatisfiedDependency(ResolvedType aspectType) {
      String aspectName = aspectType.getName();
      if (aspectType.hasAnnotations()) {
         AnnotationAJ[] annos = aspectType.getAnnotations();
         AnnotationAJ[] arr$ = annos;
         int len$ = annos.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AnnotationAJ anno = arr$[i$];
            if (anno.getTypeName().equals("com.bea.core.repackaged.aspectj.lang.annotation.RequiredTypes")) {
               String values = anno.getStringFormOfValue("value");
               if (values != null && values.length() > 2) {
                  values = values.substring(1, values.length() - 1);
                  StringTokenizer tokenizer = new StringTokenizer(values, ",");
                  boolean anythingMissing = false;

                  while(tokenizer.hasMoreElements()) {
                     String requiredTypeName = tokenizer.nextToken();
                     ResolvedType rt = this.resolve(UnresolvedType.forName(requiredTypeName));
                     if (rt.isMissing()) {
                        if (!this.getMessageHandler().isIgnoring(IMessage.INFO)) {
                           this.getMessageHandler().handleMessage(MessageUtil.info("deactivating aspect '" + aspectName + "' as it requires type '" + requiredTypeName + "' which cannot be found on the classpath"));
                        }

                        anythingMissing = true;
                        if (this.aspectRequiredTypes == null) {
                           this.aspectRequiredTypes = new HashMap();
                        }

                        this.aspectRequiredTypes.put(aspectName, requiredTypeName);
                     }
                  }

                  if (anythingMissing) {
                     return true;
                  }

                  return false;
               }

               return false;
            }
         }
      }

      if (this.aspectRequiredTypes == null) {
         return false;
      } else if (!this.aspectRequiredTypesProcessed.contains(aspectName)) {
         String requiredTypeName = (String)this.aspectRequiredTypes.get(aspectName);
         if (requiredTypeName == null) {
            this.aspectRequiredTypesProcessed.add(aspectName);
            return false;
         } else {
            ResolvedType rt = this.resolve(UnresolvedType.forName(requiredTypeName));
            if (!rt.isMissing()) {
               this.aspectRequiredTypesProcessed.add(aspectName);
               this.aspectRequiredTypes.remove(aspectName);
               return false;
            } else {
               if (!this.getMessageHandler().isIgnoring(IMessage.INFO)) {
                  this.getMessageHandler().handleMessage(MessageUtil.info("deactivating aspect '" + aspectName + "' as it requires type '" + requiredTypeName + "' which cannot be found on the classpath"));
               }

               this.aspectRequiredTypesProcessed.add(aspectName);
               return true;
            }
         }
      } else {
         return this.aspectRequiredTypes.containsKey(aspectName);
      }
   }

   public void addAspectRequires(String aspectClassName, String requiredType) {
      if (this.aspectRequiredTypes == null) {
         this.aspectRequiredTypes = new HashMap();
      }

      this.aspectRequiredTypes.put(aspectClassName, requiredType);
   }

   public World.TypeMap getTypeMap() {
      return this.typeMap;
   }

   public boolean isLoadtimeWeaving() {
      return false;
   }

   public void addTypeDelegateResolver(TypeDelegateResolver typeDelegateResolver) {
      if (this.typeDelegateResolvers == null) {
         this.typeDelegateResolvers = new ArrayList();
      }

      this.typeDelegateResolvers.add(typeDelegateResolver);
   }

   public void classWriteEvent(char[][] compoundName) {
      this.typeMap.classWriteEvent(new String(CharOperation.concatWith(compoundName, '.')));
   }

   public void demote(ResolvedType type) {
      this.typeMap.demote(type);
   }

   static class WeavingXmlConfig {
      static final int MODE_COMPILE = 1;
      static final int MODE_LTW = 2;
      private int mode;
      private boolean initialized = false;
      private List definitions = new ArrayList();
      private List resolvedIncludedAspects = new ArrayList();
      private Map scopes = new HashMap();
      private List includedFastMatchPatterns = Collections.emptyList();
      private List includedPatterns = Collections.emptyList();
      private List excludedFastMatchPatterns = Collections.emptyList();
      private List excludedPatterns = Collections.emptyList();
      private BcelWorld world;

      public WeavingXmlConfig(BcelWorld bcelWorld, int mode) {
         this.world = bcelWorld;
         this.mode = mode;
      }

      public void add(Definition d) {
         this.definitions.add(d);
      }

      public void addScopedAspect(String aspectName, String scope) {
         this.ensureInitialized();
         this.resolvedIncludedAspects.add(aspectName);

         try {
            TypePattern scopePattern = (new PatternParser(scope)).parseTypePattern();
            scopePattern.resolve(this.world);
            this.scopes.put(aspectName, scopePattern);
            if (!this.world.getMessageHandler().isIgnoring(IMessage.INFO)) {
               this.world.getMessageHandler().handleMessage(MessageUtil.info("Aspect '" + aspectName + "' is scoped to apply against types matching pattern '" + scopePattern.toString() + "'"));
            }
         } catch (Exception var4) {
            this.world.getMessageHandler().handleMessage(MessageUtil.error("Unable to parse scope as type pattern.  Scope was '" + scope + "': " + var4.getMessage()));
         }

      }

      public void ensureInitialized() {
         if (!this.initialized) {
            try {
               this.resolvedIncludedAspects = new ArrayList();
               Iterator i$ = this.definitions.iterator();

               while(i$.hasNext()) {
                  Definition definition = (Definition)i$.next();
                  List aspectNames = definition.getAspectClassNames();
                  Iterator i$ = aspectNames.iterator();

                  String includePattern;
                  TypePattern includedPattern;
                  while(i$.hasNext()) {
                     String name = (String)i$.next();
                     this.resolvedIncludedAspects.add(name);
                     includePattern = definition.getScopeForAspect(name);
                     if (includePattern != null) {
                        try {
                           includedPattern = (new PatternParser(includePattern)).parseTypePattern();
                           includedPattern.resolve(this.world);
                           this.scopes.put(name, includedPattern);
                           if (!this.world.getMessageHandler().isIgnoring(IMessage.INFO)) {
                              this.world.getMessageHandler().handleMessage(MessageUtil.info("Aspect '" + name + "' is scoped to apply against types matching pattern '" + includedPattern.toString() + "'"));
                           }
                        } catch (Exception var13) {
                           this.world.getMessageHandler().handleMessage(MessageUtil.error("Unable to parse scope as type pattern.  Scope was '" + includePattern + "': " + var13.getMessage()));
                        }
                     }
                  }

                  try {
                     List includePatterns = definition.getIncludePatterns();
                     if (includePatterns.size() > 0) {
                        this.includedPatterns = new ArrayList();
                        this.includedFastMatchPatterns = new ArrayList();
                     }

                     Iterator i$ = includePatterns.iterator();

                     while(i$.hasNext()) {
                        includePattern = (String)i$.next();
                        if (includePattern.endsWith("..*")) {
                           this.includedFastMatchPatterns.add(includePattern.substring(0, includePattern.length() - 2));
                        } else {
                           includedPattern = (new PatternParser(includePattern)).parseTypePattern();
                           this.includedPatterns.add(includedPattern);
                        }
                     }

                     List excludePatterns = definition.getExcludePatterns();
                     if (excludePatterns.size() > 0) {
                        this.excludedPatterns = new ArrayList();
                        this.excludedFastMatchPatterns = new ArrayList();
                     }

                     Iterator i$ = excludePatterns.iterator();

                     while(i$.hasNext()) {
                        String excludePattern = (String)i$.next();
                        if (excludePattern.endsWith("..*")) {
                           this.excludedFastMatchPatterns.add(excludePattern.substring(0, excludePattern.length() - 2));
                        } else {
                           TypePattern excludedPattern = (new PatternParser(excludePattern)).parseTypePattern();
                           this.excludedPatterns.add(excludedPattern);
                        }
                     }
                  } catch (ParserException var14) {
                     this.world.getMessageHandler().handleMessage(MessageUtil.error("Unable to parse type pattern: " + var14.getMessage()));
                  }
               }
            } finally {
               this.initialized = true;
            }
         }

      }

      public boolean specifiesInclusionOfAspect(String name) {
         this.ensureInitialized();
         return this.resolvedIncludedAspects.contains(name);
      }

      public TypePattern getScopeFor(String name) {
         return (TypePattern)this.scopes.get(name);
      }

      public boolean excludesType(ResolvedType type) {
         if (this.mode == 2) {
            return false;
         } else {
            String typename = type.getName();
            boolean excluded = false;
            Iterator i$ = this.excludedFastMatchPatterns.iterator();

            while(i$.hasNext()) {
               String excludedPattern = (String)i$.next();
               if (typename.startsWith(excludedPattern)) {
                  excluded = true;
                  break;
               }
            }

            if (!excluded) {
               i$ = this.excludedPatterns.iterator();

               while(i$.hasNext()) {
                  TypePattern excludedPattern = (TypePattern)i$.next();
                  if (excludedPattern.matchesStatically(type)) {
                     excluded = true;
                     break;
                  }
               }
            }

            return excluded;
         }
      }
   }
}
