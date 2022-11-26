package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.context.PinpointingMessageHandler;
import com.bea.core.repackaged.aspectj.util.IStructureModel;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutDesignatorHandler;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class World implements Dump.INode {
   private IMessageHandler messageHandler;
   private ICrossReferenceHandler xrefHandler;
   private TypeVariableDeclaringElement typeVariableLookupScope;
   protected TypeMap typeMap;
   private Set pointcutDesignators;
   public static boolean createInjarHierarchy = true;
   private final AspectPrecedenceCalculator precedenceCalculator;
   private final CrosscuttingMembersSet crosscuttingMembersSet;
   private IStructureModel model;
   private Lint lint;
   private boolean XnoInline;
   private boolean XlazyTjp;
   private boolean XhasMember;
   private boolean Xpinpoint;
   private boolean behaveInJava5Way;
   private boolean timing;
   private boolean timingPeriodically;
   private boolean incrementalCompileCouldFollow;
   private String targetAspectjRuntimeLevel;
   private boolean optionalJoinpoint_ArrayConstruction;
   private boolean optionalJoinpoint_Synchronization;
   private boolean addSerialVerUID;
   private Properties extraConfiguration;
   private boolean checkedAdvancedConfiguration;
   private boolean synchronizationPointcutsInUse;
   private boolean runMinimalMemory;
   private boolean transientTjpFields;
   private boolean runMinimalMemorySet;
   private boolean shouldPipelineCompilation;
   private boolean shouldGenerateStackMaps;
   protected boolean bcelRepositoryCaching;
   private boolean fastMethodPacking;
   private int itdVersion;
   private boolean minimalModel;
   private boolean useFinal;
   private boolean targettingRuntime1_6_10;
   private boolean completeBinaryTypes;
   private boolean overWeaving;
   private static boolean systemPropertyOverWeaving = false;
   public boolean forDEBUG_structuralChangesCode;
   public boolean forDEBUG_bridgingCode;
   public boolean optimizedMatching;
   public boolean generateNewLvts;
   protected long timersPerJoinpoint;
   protected long timersPerType;
   public int infoMessagesEnabled;
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(World.class);
   private boolean errorThreshold;
   private boolean warningThreshold;
   private List dumpState_cantFindTypeExceptions;
   public final ResolvedType.Primitive BYTE;
   public final ResolvedType.Primitive CHAR;
   public final ResolvedType.Primitive DOUBLE;
   public final ResolvedType.Primitive FLOAT;
   public final ResolvedType.Primitive INT;
   public final ResolvedType.Primitive LONG;
   public final ResolvedType.Primitive SHORT;
   public final ResolvedType.Primitive BOOLEAN;
   public final ResolvedType.Primitive VOID;
   private Object buildingTypeLock;
   private BoundedReferenceType wildcard;
   private boolean allLintIgnored;
   public static final String xsetAVOID_FINAL = "avoidFinal";
   public static final String xsetWEAVE_JAVA_PACKAGES = "weaveJavaPackages";
   public static final String xsetWEAVE_JAVAX_PACKAGES = "weaveJavaxPackages";
   public static final String xsetCAPTURE_ALL_CONTEXT = "captureAllContext";
   public static final String xsetRUN_MINIMAL_MEMORY = "runMinimalMemory";
   public static final String xsetDEBUG_STRUCTURAL_CHANGES_CODE = "debugStructuralChangesCode";
   public static final String xsetDEBUG_BRIDGING = "debugBridging";
   public static final String xsetTRANSIENT_TJP_FIELDS = "makeTjpFieldsTransient";
   public static final String xsetBCEL_REPOSITORY_CACHING = "bcelRepositoryCaching";
   public static final String xsetPIPELINE_COMPILATION = "pipelineCompilation";
   public static final String xsetGENERATE_STACKMAPS = "generateStackMaps";
   public static final String xsetPIPELINE_COMPILATION_DEFAULT = "true";
   public static final String xsetCOMPLETE_BINARY_TYPES = "completeBinaryTypes";
   public static final String xsetCOMPLETE_BINARY_TYPES_DEFAULT = "false";
   public static final String xsetTYPE_DEMOTION = "typeDemotion";
   public static final String xsetTYPE_DEMOTION_DEBUG = "typeDemotionDebug";
   public static final String xsetTYPE_REFS = "useWeakTypeRefs";
   public static final String xsetBCEL_REPOSITORY_CACHING_DEFAULT = "true";
   public static final String xsetFAST_PACK_METHODS = "fastPackMethods";
   public static final String xsetOVERWEAVING = "overWeaving";
   public static final String xsetOPTIMIZED_MATCHING = "optimizedMatching";
   public static final String xsetTIMERS_PER_JOINPOINT = "timersPerJoinpoint";
   public static final String xsetTIMERS_PER_FASTMATCH_CALL = "timersPerFastMatchCall";
   public static final String xsetITD_VERSION = "itdVersion";
   public static final String xsetITD_VERSION_ORIGINAL = "1";
   public static final String xsetITD_VERSION_2NDGEN = "2";
   public static final String xsetITD_VERSION_DEFAULT = "2";
   public static final String xsetMINIMAL_MODEL = "minimalModel";
   public static final String xsetTARGETING_RUNTIME_1610 = "targetRuntime1_6_10";
   public static final String xsetGENERATE_NEW_LVTS = "generateNewLocalVariableTables";
   private final Map workInProgress1;
   private Map exclusionMap;
   private TimeCollector timeCollector;

   protected World() {
      this.messageHandler = IMessageHandler.SYSTEM_ERR;
      this.xrefHandler = null;
      this.typeMap = new TypeMap(this);
      this.crosscuttingMembersSet = new CrosscuttingMembersSet(this);
      this.model = null;
      this.lint = new Lint(this);
      this.XhasMember = false;
      this.Xpinpoint = false;
      this.behaveInJava5Way = false;
      this.timing = false;
      this.timingPeriodically = true;
      this.incrementalCompileCouldFollow = false;
      this.targetAspectjRuntimeLevel = "1.5";
      this.optionalJoinpoint_ArrayConstruction = false;
      this.optionalJoinpoint_Synchronization = false;
      this.addSerialVerUID = false;
      this.extraConfiguration = null;
      this.checkedAdvancedConfiguration = false;
      this.synchronizationPointcutsInUse = false;
      this.runMinimalMemory = false;
      this.transientTjpFields = false;
      this.runMinimalMemorySet = false;
      this.shouldPipelineCompilation = true;
      this.shouldGenerateStackMaps = false;
      this.bcelRepositoryCaching = "true".equalsIgnoreCase("true");
      this.fastMethodPacking = false;
      this.itdVersion = 2;
      this.minimalModel = true;
      this.useFinal = true;
      this.targettingRuntime1_6_10 = false;
      this.completeBinaryTypes = false;
      this.overWeaving = false;
      this.forDEBUG_structuralChangesCode = false;
      this.forDEBUG_bridgingCode = false;
      this.optimizedMatching = true;
      this.generateNewLvts = true;
      this.timersPerJoinpoint = 25000L;
      this.timersPerType = 250L;
      this.infoMessagesEnabled = 0;
      this.dumpState_cantFindTypeExceptions = null;
      this.BYTE = new ResolvedType.Primitive("B", 1, 0);
      this.CHAR = new ResolvedType.Primitive("C", 1, 1);
      this.DOUBLE = new ResolvedType.Primitive("D", 2, 2);
      this.FLOAT = new ResolvedType.Primitive("F", 1, 3);
      this.INT = new ResolvedType.Primitive("I", 1, 4);
      this.LONG = new ResolvedType.Primitive("J", 2, 5);
      this.SHORT = new ResolvedType.Primitive("S", 1, 6);
      this.BOOLEAN = new ResolvedType.Primitive("Z", 1, 7);
      this.VOID = new ResolvedType.Primitive("V", 0, 8);
      this.buildingTypeLock = new Object();
      this.allLintIgnored = false;
      this.workInProgress1 = new HashMap();
      this.exclusionMap = new HashMap();
      this.timeCollector = null;
      this.typeMap.put("B", this.BYTE);
      this.typeMap.put("S", this.SHORT);
      this.typeMap.put("I", this.INT);
      this.typeMap.put("J", this.LONG);
      this.typeMap.put("F", this.FLOAT);
      this.typeMap.put("D", this.DOUBLE);
      this.typeMap.put("C", this.CHAR);
      this.typeMap.put("Z", this.BOOLEAN);
      this.typeMap.put("V", this.VOID);
      this.precedenceCalculator = new AspectPrecedenceCalculator(this);
   }

   public void accept(Dump.IVisitor visitor) {
      visitor.visitObject("Shadow mungers:");
      visitor.visitList(this.crosscuttingMembersSet.getShadowMungers());
      visitor.visitObject("Type mungers:");
      visitor.visitList(this.crosscuttingMembersSet.getTypeMungers());
      visitor.visitObject("Late Type mungers:");
      visitor.visitList(this.crosscuttingMembersSet.getLateTypeMungers());
      if (this.dumpState_cantFindTypeExceptions != null) {
         visitor.visitObject("Cant find type problems:");
         visitor.visitList(this.dumpState_cantFindTypeExceptions);
         this.dumpState_cantFindTypeExceptions = null;
      }

   }

   public ResolvedType resolve(UnresolvedType ty) {
      return this.resolve(ty, false);
   }

   public ResolvedType resolve(UnresolvedType ty, ISourceLocation isl) {
      ResolvedType ret = this.resolve(ty, true);
      if (ResolvedType.isMissing(ty)) {
         this.getLint().cantFindType.signal(WeaverMessages.format("cantFindType", ty.getName()), isl);
      }

      return ret;
   }

   public ResolvedType[] resolve(UnresolvedType[] types) {
      if (types == null) {
         return ResolvedType.NONE;
      } else {
         ResolvedType[] ret = new ResolvedType[types.length];

         for(int i = 0; i < types.length; ++i) {
            ret[i] = this.resolve(types[i]);
         }

         return ret;
      }
   }

   public ResolvedType resolve(UnresolvedType ty, boolean allowMissing) {
      if (ty instanceof ResolvedType) {
         ResolvedType rty = (ResolvedType)ty;
         rty = this.resolve(rty);
         if (!rty.isTypeVariableReference() || ((TypeVariableReferenceType)rty).isTypeVariableResolved()) {
            return rty;
         }
      }

      if (ty.isTypeVariableReference()) {
         return ty.resolve(this);
      } else {
         String signature = ty.getSignature();
         ResolvedType ret = this.typeMap.get(signature);
         if (ret != null) {
            ret.world = this;
            return ret;
         } else if (!signature.equals("?") && !signature.equals("*")) {
            Object ret;
            synchronized(this.buildingTypeLock) {
               if (ty.isArray()) {
                  ResolvedType componentType = this.resolve(ty.getComponentType(), allowMissing);
                  ret = new ArrayReferenceType(signature, "[" + componentType.getErasureSignature(), this, componentType);
               } else {
                  ret = this.resolveToReferenceType(ty, allowMissing);
                  if (!allowMissing && ((ResolvedType)ret).isMissing()) {
                     ret = this.handleRequiredMissingTypeDuringResolution(ty);
                  }

                  if (this.completeBinaryTypes) {
                     this.completeBinaryType((ResolvedType)ret);
                  }
               }
            }

            ResolvedType result = this.typeMap.get(signature);
            if (result == null && !((ResolvedType)ret).isMissing()) {
               ret = this.ensureRawTypeIfNecessary((ResolvedType)ret);
               this.typeMap.put(signature, ret);
               return ret;
            } else if (result == null) {
               return (ResolvedType)ret;
            } else {
               return result;
            }
         } else {
            ResolvedType something = this.getWildcard();
            this.typeMap.put("?", something);
            return something;
         }
      }
   }

   private BoundedReferenceType getWildcard() {
      if (this.wildcard == null) {
         this.wildcard = new BoundedReferenceType(this);
      }

      return this.wildcard;
   }

   protected void completeBinaryType(ResolvedType ret) {
   }

   public boolean isLocallyDefined(String classname) {
      return false;
   }

   private ResolvedType handleRequiredMissingTypeDuringResolution(UnresolvedType ty) {
      if (this.dumpState_cantFindTypeExceptions == null) {
         this.dumpState_cantFindTypeExceptions = new ArrayList();
      }

      if (this.dumpState_cantFindTypeExceptions.size() < 100) {
         this.dumpState_cantFindTypeExceptions.add(new RuntimeException("Can't find type " + ty.getName()));
      }

      return new MissingResolvedTypeWithKnownSignature(ty.getSignature(), this);
   }

   public ResolvedType resolve(ResolvedType ty) {
      if (ty.isTypeVariableReference()) {
         return ty;
      } else {
         ResolvedType resolved = this.typeMap.get(ty.getSignature());
         if (resolved == null) {
            resolved = this.ensureRawTypeIfNecessary(ty);
            this.typeMap.put(ty.getSignature(), resolved);
            resolved = ty;
         }

         resolved.world = this;
         return resolved;
      }
   }

   private ResolvedType ensureRawTypeIfNecessary(ResolvedType type) {
      if (this.isInJava5Mode() && !type.isRawType()) {
         if (type instanceof ReferenceType && ((ReferenceType)type).getDelegate() != null && type.isGenericType()) {
            ReferenceType rawType = new ReferenceType(type.getSignature(), this);
            rawType.typeKind = UnresolvedType.TypeKind.RAW;
            ReferenceTypeDelegate delegate = ((ReferenceType)type).getDelegate();
            rawType.setDelegate(delegate);
            rawType.setGenericType((ReferenceType)type);
            return rawType;
         } else {
            return type;
         }
      } else {
         return type;
      }
   }

   public ResolvedType resolve(String name) {
      ResolvedType ret = this.resolve(UnresolvedType.forName(name));
      return ret;
   }

   public ReferenceType resolveToReferenceType(String name) {
      return (ReferenceType)this.resolve(name);
   }

   public ResolvedType resolve(String name, boolean allowMissing) {
      return this.resolve(UnresolvedType.forName(name), allowMissing);
   }

   private final ResolvedType resolveToReferenceType(UnresolvedType ty, boolean allowMissing) {
      ReferenceType simpleOrRawType;
      ResolvedType rt;
      if (ty.isParameterizedType()) {
         rt = this.resolveGenericTypeFor(ty, allowMissing);
         if (rt.isMissing()) {
            return rt;
         } else {
            simpleOrRawType = (ReferenceType)rt;
            ReferenceType parameterizedType = TypeFactory.createParameterizedType(simpleOrRawType, ty.typeParameters, this);
            return parameterizedType;
         }
      } else if (ty.isGenericType()) {
         rt = this.resolveGenericTypeFor(ty, false);
         simpleOrRawType = (ReferenceType)rt;
         return simpleOrRawType;
      } else if (ty.isGenericWildcard()) {
         return this.resolveGenericWildcardFor((WildcardedUnresolvedType)ty);
      } else {
         String erasedSignature = ty.getErasureSignature();
         simpleOrRawType = new ReferenceType(erasedSignature, this);
         if (ty.needsModifiableDelegate()) {
            simpleOrRawType.setNeedsModifiableDelegate(true);
         }

         ReferenceTypeDelegate delegate = this.resolveDelegate(simpleOrRawType);
         if (delegate == null) {
            return new MissingResolvedTypeWithKnownSignature(ty.getSignature(), erasedSignature, this);
         } else if (delegate.isGeneric() && this.behaveInJava5Way) {
            simpleOrRawType.typeKind = UnresolvedType.TypeKind.RAW;
            if (simpleOrRawType.hasNewInterfaces()) {
               throw new IllegalStateException("Simple type promoted forced to raw, but it had new interfaces/superclass.  Type is " + simpleOrRawType.getName());
            } else {
               ReferenceType genericType = this.makeGenericTypeFrom(delegate, simpleOrRawType);
               simpleOrRawType.setDelegate(delegate);
               genericType.setDelegate(delegate);
               simpleOrRawType.setGenericType(genericType);
               return simpleOrRawType;
            }
         } else {
            simpleOrRawType.setDelegate(delegate);
            return simpleOrRawType;
         }
      }
   }

   public ResolvedType resolveGenericTypeFor(UnresolvedType anUnresolvedType, boolean allowMissing) {
      String rawSignature = anUnresolvedType.getRawType().getSignature();
      ResolvedType rawType = this.typeMap.get(rawSignature);
      if (rawType == null) {
         rawType = this.resolve(UnresolvedType.forSignature(rawSignature), allowMissing);
         this.typeMap.put(rawSignature, rawType);
      }

      if (rawType.isMissing()) {
         return rawType;
      } else {
         ResolvedType genericType = rawType.getGenericType();
         if (rawType.isSimpleType() && (anUnresolvedType.typeParameters == null || anUnresolvedType.typeParameters.length == 0)) {
            rawType.world = this;
            return rawType;
         } else if (genericType != null) {
            genericType.world = this;
            return genericType;
         } else {
            ReferenceTypeDelegate delegate = this.resolveDelegate((ReferenceType)rawType);
            ReferenceType genericRefType = this.makeGenericTypeFrom(delegate, (ReferenceType)rawType);
            ((ReferenceType)rawType).setGenericType(genericRefType);
            genericRefType.setDelegate(delegate);
            ((ReferenceType)rawType).setDelegate(delegate);
            return genericRefType;
         }
      }
   }

   private ReferenceType makeGenericTypeFrom(ReferenceTypeDelegate delegate, ReferenceType rawType) {
      String genericSig = delegate.getDeclaredGenericSignature();
      return genericSig != null ? new ReferenceType(UnresolvedType.forGenericTypeSignature(rawType.getSignature(), delegate.getDeclaredGenericSignature()), this) : new ReferenceType(UnresolvedType.forGenericTypeVariables(rawType.getSignature(), delegate.getTypeVariables()), this);
   }

   private ReferenceType resolveGenericWildcardFor(WildcardedUnresolvedType aType) {
      BoundedReferenceType ret = null;
      ResolvedType resolvedLowerBound;
      if (aType.isExtends()) {
         resolvedLowerBound = this.resolve(aType.getUpperBound());
         if (resolvedLowerBound.isMissing()) {
            return this.getWildcard();
         }

         ret = new BoundedReferenceType((ReferenceType)resolvedLowerBound, true, this);
      } else if (aType.isSuper()) {
         resolvedLowerBound = this.resolve(aType.getLowerBound());
         if (resolvedLowerBound.isMissing()) {
            return this.getWildcard();
         }

         ret = new BoundedReferenceType((ReferenceType)resolvedLowerBound, false, this);
      } else {
         ret = this.getWildcard();
      }

      return ret;
   }

   protected abstract ReferenceTypeDelegate resolveDelegate(ReferenceType var1);

   public ResolvedType getCoreType(UnresolvedType tx) {
      ResolvedType coreTy = this.resolve(tx, true);
      if (coreTy.isMissing()) {
         MessageUtil.error(this.messageHandler, WeaverMessages.format("cantFindCoreType", tx.getName()));
      }

      return coreTy;
   }

   public ReferenceType lookupOrCreateName(UnresolvedType ty) {
      String signature = ty.getSignature();
      ReferenceType ret = this.lookupBySignature(signature);
      if (ret == null) {
         ret = ReferenceType.fromTypeX(ty, this);
         this.typeMap.put(signature, ret);
      }

      return ret;
   }

   public ReferenceType lookupBySignature(String signature) {
      return (ReferenceType)this.typeMap.get(signature);
   }

   public ResolvedMember resolve(Member member) {
      ResolvedType declaring = member.getDeclaringType().resolve(this);
      if (((ResolvedType)declaring).isRawType()) {
         declaring = ((ResolvedType)declaring).getGenericType();
      }

      ResolvedMember ret;
      if (member.getKind() == Member.FIELD) {
         ret = ((ResolvedType)declaring).lookupField(member);
      } else {
         ret = ((ResolvedType)declaring).lookupMethod(member);
      }

      return ret != null ? ret : ((ResolvedType)declaring).lookupSyntheticMember(member);
   }

   public void setAllLintIgnored() {
      this.allLintIgnored = true;
   }

   public boolean areAllLintIgnored() {
      return this.allLintIgnored;
   }

   public abstract IWeavingSupport getWeavingSupport();

   public final Advice createAdviceMunger(AdviceKind kind, Pointcut p, Member signature, int extraParameterFlags, IHasSourceLocation loc, ResolvedType declaringAspect) {
      AjAttribute.AdviceAttribute attribute = new AjAttribute.AdviceAttribute(kind, p, extraParameterFlags, loc.getStart(), loc.getEnd(), loc.getSourceContext());
      return this.getWeavingSupport().createAdviceMunger(attribute, p, signature, declaringAspect);
   }

   public int compareByPrecedence(ResolvedType aspect1, ResolvedType aspect2) {
      return this.precedenceCalculator.compareByPrecedence(aspect1, aspect2);
   }

   public Integer getPrecedenceIfAny(ResolvedType aspect1, ResolvedType aspect2) {
      return this.precedenceCalculator.getPrecedenceIfAny(aspect1, aspect2);
   }

   public int compareByPrecedenceAndHierarchy(ResolvedType aspect1, ResolvedType aspect2) {
      return this.precedenceCalculator.compareByPrecedenceAndHierarchy(aspect1, aspect2);
   }

   public IMessageHandler getMessageHandler() {
      return this.messageHandler;
   }

   public void setMessageHandler(IMessageHandler messageHandler) {
      if (this.isInPinpointMode()) {
         this.messageHandler = new PinpointingMessageHandler(messageHandler);
      } else {
         this.messageHandler = messageHandler;
      }

   }

   public void showMessage(IMessage.Kind kind, String message, ISourceLocation loc1, ISourceLocation loc2) {
      if (loc1 != null) {
         this.messageHandler.handleMessage(new Message(message, kind, (Throwable)null, loc1));
         if (loc2 != null) {
            this.messageHandler.handleMessage(new Message(message, kind, (Throwable)null, loc2));
         }
      } else {
         this.messageHandler.handleMessage(new Message(message, kind, (Throwable)null, loc2));
      }

   }

   public void setCrossReferenceHandler(ICrossReferenceHandler xrefHandler) {
      this.xrefHandler = xrefHandler;
   }

   public ICrossReferenceHandler getCrossReferenceHandler() {
      return this.xrefHandler;
   }

   public void setTypeVariableLookupScope(TypeVariableDeclaringElement scope) {
      this.typeVariableLookupScope = scope;
   }

   public TypeVariableDeclaringElement getTypeVariableLookupScope() {
      return this.typeVariableLookupScope;
   }

   public List getDeclareParents() {
      return this.crosscuttingMembersSet.getDeclareParents();
   }

   public List getDeclareAnnotationOnTypes() {
      return this.crosscuttingMembersSet.getDeclareAnnotationOnTypes();
   }

   public List getDeclareAnnotationOnFields() {
      return this.crosscuttingMembersSet.getDeclareAnnotationOnFields();
   }

   public List getDeclareAnnotationOnMethods() {
      return this.crosscuttingMembersSet.getDeclareAnnotationOnMethods();
   }

   public List getDeclareTypeEows() {
      return this.crosscuttingMembersSet.getDeclareTypeEows();
   }

   public List getDeclareSoft() {
      return this.crosscuttingMembersSet.getDeclareSofts();
   }

   public CrosscuttingMembersSet getCrosscuttingMembersSet() {
      return this.crosscuttingMembersSet;
   }

   public IStructureModel getModel() {
      return this.model;
   }

   public void setModel(IStructureModel model) {
      this.model = model;
   }

   public Lint getLint() {
      return this.lint;
   }

   public void setLint(Lint lint) {
      this.lint = lint;
   }

   public boolean isXnoInline() {
      return this.XnoInline;
   }

   public void setXnoInline(boolean xnoInline) {
      this.XnoInline = xnoInline;
   }

   public boolean isXlazyTjp() {
      return this.XlazyTjp;
   }

   public void setXlazyTjp(boolean b) {
      this.XlazyTjp = b;
   }

   public boolean isHasMemberSupportEnabled() {
      return this.XhasMember;
   }

   public void setXHasMemberSupportEnabled(boolean b) {
      this.XhasMember = b;
   }

   public boolean isInPinpointMode() {
      return this.Xpinpoint;
   }

   public void setPinpointMode(boolean b) {
      this.Xpinpoint = b;
   }

   public boolean useFinal() {
      return this.useFinal;
   }

   public boolean isMinimalModel() {
      this.ensureAdvancedConfigurationProcessed();
      return this.minimalModel;
   }

   public boolean isTargettingRuntime1_6_10() {
      this.ensureAdvancedConfigurationProcessed();
      return this.targettingRuntime1_6_10;
   }

   public void setBehaveInJava5Way(boolean b) {
      this.behaveInJava5Way = b;
   }

   public void setTiming(boolean timersOn, boolean reportPeriodically) {
      this.timing = timersOn;
      this.timingPeriodically = reportPeriodically;
   }

   public void setErrorAndWarningThreshold(boolean errorThreshold, boolean warningThreshold) {
      this.errorThreshold = errorThreshold;
      this.warningThreshold = warningThreshold;
   }

   public boolean isIgnoringUnusedDeclaredThrownException() {
      return this.errorThreshold || this.warningThreshold;
   }

   public void performExtraConfiguration(String config) {
      if (config != null) {
         this.extraConfiguration = new Properties();

         String v;
         int pos;
         for(int pos = true; (pos = config.indexOf(",")) != -1; config = config.substring(pos + 1)) {
            String nvpair = config.substring(0, pos);
            int pos2 = nvpair.indexOf("=");
            if (pos2 != -1) {
               v = nvpair.substring(0, pos2);
               String v = nvpair.substring(pos2 + 1);
               this.extraConfiguration.setProperty(v, v);
            }
         }

         if (config.length() > 0) {
            int pos2 = config.indexOf("=");
            if (pos2 != -1) {
               String n = config.substring(0, pos2);
               v = config.substring(pos2 + 1);
               this.extraConfiguration.setProperty(n, v);
            }
         }

         this.ensureAdvancedConfigurationProcessed();
      }
   }

   public boolean areInfoMessagesEnabled() {
      if (this.infoMessagesEnabled == 0) {
         this.infoMessagesEnabled = this.messageHandler.isIgnoring(IMessage.INFO) ? 1 : 2;
      }

      return this.infoMessagesEnabled == 2;
   }

   public Properties getExtraConfiguration() {
      return this.extraConfiguration;
   }

   public boolean isInJava5Mode() {
      return this.behaveInJava5Way;
   }

   public boolean isTimingEnabled() {
      return this.timing;
   }

   public void setTargetAspectjRuntimeLevel(String s) {
      this.targetAspectjRuntimeLevel = s;
   }

   public void setOptionalJoinpoints(String jps) {
      if (jps != null) {
         if (jps.indexOf("arrayconstruction") != -1) {
            this.optionalJoinpoint_ArrayConstruction = true;
         }

         if (jps.indexOf("synchronization") != -1) {
            this.optionalJoinpoint_Synchronization = true;
         }

      }
   }

   public boolean isJoinpointArrayConstructionEnabled() {
      return this.optionalJoinpoint_ArrayConstruction;
   }

   public boolean isJoinpointSynchronizationEnabled() {
      return this.optionalJoinpoint_Synchronization;
   }

   public String getTargetAspectjRuntimeLevel() {
      return this.targetAspectjRuntimeLevel;
   }

   public boolean isTargettingAspectJRuntime12() {
      boolean b = false;
      if (!this.isInJava5Mode()) {
         b = true;
      } else {
         b = this.getTargetAspectjRuntimeLevel().equals("1.2");
      }

      return b;
   }

   public void validateType(UnresolvedType type) {
   }

   public boolean isDemotionActive() {
      return true;
   }

   public TypeVariable[] getTypeVariablesCurrentlyBeingProcessed(Class baseClass) {
      return (TypeVariable[])this.workInProgress1.get(baseClass);
   }

   public void recordTypeVariablesCurrentlyBeingProcessed(Class baseClass, TypeVariable[] typeVariables) {
      this.workInProgress1.put(baseClass, typeVariables);
   }

   public void forgetTypeVariablesCurrentlyBeingProcessed(Class baseClass) {
      this.workInProgress1.remove(baseClass);
   }

   public void setAddSerialVerUID(boolean b) {
      this.addSerialVerUID = b;
   }

   public boolean isAddSerialVerUID() {
      return this.addSerialVerUID;
   }

   public void flush() {
      this.typeMap.expendableMap.clear();
   }

   public void ensureAdvancedConfigurationProcessed() {
      if (!this.checkedAdvancedConfiguration) {
         Properties p = this.getExtraConfiguration();
         String s;
         if (p != null) {
            s = p.getProperty("bcelRepositoryCaching", "true");
            this.bcelRepositoryCaching = s.equalsIgnoreCase("true");
            if (!this.bcelRepositoryCaching) {
               this.getMessageHandler().handleMessage(MessageUtil.info("[bcelRepositoryCaching=false] AspectJ will not use a bcel cache for class information"));
            }

            s = p.getProperty("itdVersion", "2");
            if (s.equals("1")) {
               this.itdVersion = 1;
            }

            s = p.getProperty("avoidFinal", "false");
            if (s.equalsIgnoreCase("true")) {
               this.useFinal = false;
            }

            s = p.getProperty("minimalModel", "true");
            if (s.equalsIgnoreCase("false")) {
               this.minimalModel = false;
            }

            s = p.getProperty("targetRuntime1_6_10", "false");
            if (s.equalsIgnoreCase("true")) {
               this.targettingRuntime1_6_10 = true;
            }

            s = p.getProperty("fastPackMethods", "true");
            this.fastMethodPacking = s.equalsIgnoreCase("true");
            s = p.getProperty("pipelineCompilation", "true");
            this.shouldPipelineCompilation = s.equalsIgnoreCase("true");
            s = p.getProperty("generateStackMaps", "false");
            this.shouldGenerateStackMaps = s.equalsIgnoreCase("true");
            s = p.getProperty("completeBinaryTypes", "false");
            this.completeBinaryTypes = s.equalsIgnoreCase("true");
            if (this.completeBinaryTypes) {
               this.getMessageHandler().handleMessage(MessageUtil.info("[completeBinaryTypes=true] Completion of binary types activated"));
            }

            s = p.getProperty("typeDemotion");
            if (s != null) {
               boolean b = this.typeMap.demotionSystemActive;
               if (b && s.equalsIgnoreCase("false")) {
                  System.out.println("typeDemotion=false: type demotion switched OFF");
                  this.typeMap.demotionSystemActive = false;
               } else if (!b && s.equalsIgnoreCase("true")) {
                  System.out.println("typeDemotion=true: type demotion switched ON");
                  this.typeMap.demotionSystemActive = true;
               }
            }

            s = p.getProperty("overWeaving", "false");
            if (s.equalsIgnoreCase("true")) {
               this.overWeaving = true;
            }

            s = p.getProperty("typeDemotionDebug", "false");
            if (s.equalsIgnoreCase("true")) {
               this.typeMap.debugDemotion = true;
            }

            s = p.getProperty("useWeakTypeRefs", "true");
            if (s.equalsIgnoreCase("false")) {
               this.typeMap.policy = 2;
            }

            this.runMinimalMemorySet = p.getProperty("runMinimalMemory") != null;
            s = p.getProperty("runMinimalMemory", "false");
            this.runMinimalMemory = s.equalsIgnoreCase("true");
            s = p.getProperty("debugStructuralChangesCode", "false");
            this.forDEBUG_structuralChangesCode = s.equalsIgnoreCase("true");
            s = p.getProperty("makeTjpFieldsTransient", "false");
            this.transientTjpFields = s.equalsIgnoreCase("true");
            s = p.getProperty("debugBridging", "false");
            this.forDEBUG_bridgingCode = s.equalsIgnoreCase("true");
            s = p.getProperty("generateNewLocalVariableTables", "true");
            this.generateNewLvts = s.equalsIgnoreCase("true");
            if (!this.generateNewLvts) {
               this.getMessageHandler().handleMessage(MessageUtil.info("[generateNewLvts=false] for methods without an incoming local variable table, do not generate one"));
            }

            s = p.getProperty("optimizedMatching", "true");
            this.optimizedMatching = s.equalsIgnoreCase("true");
            if (!this.optimizedMatching) {
               this.getMessageHandler().handleMessage(MessageUtil.info("[optimizedMatching=false] optimized matching turned off"));
            }

            s = p.getProperty("timersPerJoinpoint", "25000");

            try {
               this.timersPerJoinpoint = (long)Integer.parseInt(s);
            } catch (Exception var6) {
               this.getMessageHandler().handleMessage(MessageUtil.error("unable to process timersPerJoinpoint value of " + s));
               this.timersPerJoinpoint = 25000L;
            }

            s = p.getProperty("timersPerFastMatchCall", "250");

            try {
               this.timersPerType = (long)Integer.parseInt(s);
            } catch (Exception var5) {
               this.getMessageHandler().handleMessage(MessageUtil.error("unable to process timersPerType value of " + s));
               this.timersPerType = 250L;
            }
         }

         try {
            if (systemPropertyOverWeaving) {
               this.overWeaving = true;
            }

            s = null;
            s = System.getProperty("aspectj.typeDemotion", "false");
            if (s.equalsIgnoreCase("true")) {
               System.out.println("ASPECTJ: aspectj.typeDemotion=true: type demotion switched ON");
               this.typeMap.demotionSystemActive = true;
            }

            s = System.getProperty("aspectj.minimalModel", "false");
            if (s.equalsIgnoreCase("true")) {
               System.out.println("ASPECTJ: aspectj.minimalModel=true: minimal model switched ON");
               this.minimalModel = true;
            }
         } catch (Throwable var4) {
            System.err.println("ASPECTJ: Unable to read system properties");
            var4.printStackTrace();
         }

         this.checkedAdvancedConfiguration = true;
      }

   }

   public boolean isRunMinimalMemory() {
      this.ensureAdvancedConfigurationProcessed();
      return this.runMinimalMemory;
   }

   public boolean isTransientTjpFields() {
      this.ensureAdvancedConfigurationProcessed();
      return this.transientTjpFields;
   }

   public boolean isRunMinimalMemorySet() {
      this.ensureAdvancedConfigurationProcessed();
      return this.runMinimalMemorySet;
   }

   public boolean shouldFastPackMethods() {
      this.ensureAdvancedConfigurationProcessed();
      return this.fastMethodPacking;
   }

   public boolean shouldPipelineCompilation() {
      this.ensureAdvancedConfigurationProcessed();
      return this.shouldPipelineCompilation;
   }

   public boolean shouldGenerateStackMaps() {
      this.ensureAdvancedConfigurationProcessed();
      return this.shouldGenerateStackMaps;
   }

   public void setIncrementalCompileCouldFollow(boolean b) {
      this.incrementalCompileCouldFollow = b;
   }

   public boolean couldIncrementalCompileFollow() {
      return this.incrementalCompileCouldFollow;
   }

   public void setSynchronizationPointcutsInUse() {
      if (trace.isTraceEnabled()) {
         trace.enter("setSynchronizationPointcutsInUse", this);
      }

      this.synchronizationPointcutsInUse = true;
      if (trace.isTraceEnabled()) {
         trace.exit("setSynchronizationPointcutsInUse");
      }

   }

   public boolean areSynchronizationPointcutsInUse() {
      return this.synchronizationPointcutsInUse;
   }

   public void registerPointcutHandler(PointcutDesignatorHandler designatorHandler) {
      if (this.pointcutDesignators == null) {
         this.pointcutDesignators = new HashSet();
      }

      this.pointcutDesignators.add(designatorHandler);
   }

   public Set getRegisteredPointcutHandlers() {
      return this.pointcutDesignators == null ? Collections.emptySet() : this.pointcutDesignators;
   }

   public void reportMatch(ShadowMunger munger, Shadow shadow) {
   }

   public boolean isOverWeaving() {
      return this.overWeaving;
   }

   public void reportCheckerMatch(Checker checker, Shadow shadow) {
   }

   public boolean isXmlConfigured() {
      return false;
   }

   public boolean isAspectIncluded(ResolvedType aspectType) {
      return true;
   }

   public boolean hasUnsatisfiedDependency(ResolvedType aspectType) {
      return false;
   }

   public TypePattern getAspectScope(ResolvedType declaringType) {
      return null;
   }

   public Map getFixed() {
      return this.typeMap.tMap;
   }

   public Map getExpendable() {
      return this.typeMap.expendableMap;
   }

   public void demote() {
      this.typeMap.demote();
   }

   protected boolean isExpendable(ResolvedType type) {
      return !type.equals(UnresolvedType.OBJECT) && !type.isExposedToWeaver() && !type.isPrimitiveType() && !type.isPrimitiveArray();
   }

   public Map getExclusionMap() {
      return this.exclusionMap;
   }

   public void record(Pointcut pointcut, long timetaken) {
      if (this.timeCollector == null) {
         this.ensureAdvancedConfigurationProcessed();
         this.timeCollector = new TimeCollector(this);
      }

      this.timeCollector.record(pointcut, timetaken);
   }

   public void recordFastMatch(Pointcut pointcut, long timetaken) {
      if (this.timeCollector == null) {
         this.ensureAdvancedConfigurationProcessed();
         this.timeCollector = new TimeCollector(this);
      }

      this.timeCollector.recordFastMatch(pointcut, timetaken);
   }

   public void reportTimers() {
      if (this.timeCollector != null && !this.timingPeriodically) {
         this.timeCollector.report();
         this.timeCollector = new TimeCollector(this);
      }

   }

   public TypeMap getTypeMap() {
      return this.typeMap;
   }

   public static void reset() {
   }

   public int getItdVersion() {
      return this.itdVersion;
   }

   public abstract boolean isLoadtimeWeaving();

   public void classWriteEvent(char[][] compoundName) {
   }

   static {
      try {
         String value = System.getProperty("aspectj.overweaving", "false");
         if (value.equalsIgnoreCase("true")) {
            System.out.println("ASPECTJ: aspectj.overweaving=true: overweaving switched ON");
            systemPropertyOverWeaving = true;
         }
      } catch (Throwable var1) {
         System.err.println("ASPECTJ: Unable to read system properties");
         var1.printStackTrace();
      }

   }

   private static class TimeCollector {
      private World world;
      long joinpointCount;
      long typeCount;
      long perJoinpointCount;
      long perTypes;
      Map joinpointsPerPointcut = new HashMap();
      Map timePerPointcut = new HashMap();
      Map fastMatchTimesPerPointcut = new HashMap();
      Map fastMatchTypesPerPointcut = new HashMap();

      TimeCollector(World world) {
         this.perJoinpointCount = world.timersPerJoinpoint;
         this.perTypes = world.timersPerType;
         this.world = world;
         this.joinpointCount = 0L;
         this.typeCount = 0L;
         this.joinpointsPerPointcut = new HashMap();
         this.timePerPointcut = new HashMap();
      }

      public void report() {
         long totalTime = 0L;

         Iterator i$;
         String p;
         for(i$ = this.joinpointsPerPointcut.keySet().iterator(); i$.hasNext(); totalTime += (Long)this.timePerPointcut.get(p)) {
            p = (String)i$.next();
         }

         this.world.getMessageHandler().handleMessage(MessageUtil.info("Pointcut matching cost (total=" + totalTime / 1000000L + "ms for " + this.joinpointCount + " joinpoint match calls):"));
         i$ = this.joinpointsPerPointcut.keySet().iterator();

         StringBuffer sb;
         while(i$.hasNext()) {
            p = (String)i$.next();
            sb = new StringBuffer();
            sb.append("Time:" + (Long)this.timePerPointcut.get(p) / 1000000L + "ms (jps:#" + this.joinpointsPerPointcut.get(p) + ") matching against " + p);
            this.world.getMessageHandler().handleMessage(MessageUtil.info(sb.toString()));
         }

         this.world.getMessageHandler().handleMessage(MessageUtil.info("---"));
         totalTime = 0L;

         for(i$ = this.fastMatchTimesPerPointcut.keySet().iterator(); i$.hasNext(); totalTime += (Long)this.fastMatchTimesPerPointcut.get(p)) {
            p = (String)i$.next();
         }

         this.world.getMessageHandler().handleMessage(MessageUtil.info("Pointcut fast matching cost (total=" + totalTime / 1000000L + "ms for " + this.typeCount + " fast match calls):"));
         i$ = this.fastMatchTimesPerPointcut.keySet().iterator();

         while(i$.hasNext()) {
            p = (String)i$.next();
            sb = new StringBuffer();
            sb.append("Time:" + (Long)this.fastMatchTimesPerPointcut.get(p) / 1000000L + "ms (types:#" + this.fastMatchTypesPerPointcut.get(p) + ") fast matching against " + p);
            this.world.getMessageHandler().handleMessage(MessageUtil.info(sb.toString()));
         }

         this.world.getMessageHandler().handleMessage(MessageUtil.info("---"));
      }

      void record(Pointcut pointcut, long timetakenInNs) {
         ++this.joinpointCount;
         String pointcutText = pointcut.toString();
         Long jpcounter = (Long)this.joinpointsPerPointcut.get(pointcutText);
         if (jpcounter == null) {
            jpcounter = 1L;
         } else {
            jpcounter = jpcounter + 1L;
         }

         this.joinpointsPerPointcut.put(pointcutText, jpcounter);
         Long time = (Long)this.timePerPointcut.get(pointcutText);
         if (time == null) {
            time = timetakenInNs;
         } else {
            time = time + timetakenInNs;
         }

         this.timePerPointcut.put(pointcutText, time);
         if (this.world.timingPeriodically && this.joinpointCount % this.perJoinpointCount == 0L) {
            long totalTime = 0L;

            Iterator i$;
            String p;
            for(i$ = this.joinpointsPerPointcut.keySet().iterator(); i$.hasNext(); totalTime += (Long)this.timePerPointcut.get(p)) {
               p = (String)i$.next();
            }

            this.world.getMessageHandler().handleMessage(MessageUtil.info("Pointcut matching cost (total=" + totalTime / 1000000L + "ms for " + this.joinpointCount + " joinpoint match calls):"));
            i$ = this.joinpointsPerPointcut.keySet().iterator();

            while(i$.hasNext()) {
               p = (String)i$.next();
               StringBuffer sb = new StringBuffer();
               sb.append("Time:" + (Long)this.timePerPointcut.get(p) / 1000000L + "ms (jps:#" + this.joinpointsPerPointcut.get(p) + ") matching against " + p);
               this.world.getMessageHandler().handleMessage(MessageUtil.info(sb.toString()));
            }

            this.world.getMessageHandler().handleMessage(MessageUtil.info("---"));
         }

      }

      void recordFastMatch(Pointcut pointcut, long timetakenInNs) {
         ++this.typeCount;
         String pointcutText = pointcut.toString();
         Long typecounter = (Long)this.fastMatchTypesPerPointcut.get(pointcutText);
         if (typecounter == null) {
            typecounter = 1L;
         } else {
            typecounter = typecounter + 1L;
         }

         this.fastMatchTypesPerPointcut.put(pointcutText, typecounter);
         Long time = (Long)this.fastMatchTimesPerPointcut.get(pointcutText);
         if (time == null) {
            time = timetakenInNs;
         } else {
            time = time + timetakenInNs;
         }

         this.fastMatchTimesPerPointcut.put(pointcutText, time);
         if (this.world.timingPeriodically && this.typeCount % this.perTypes == 0L) {
            long totalTime = 0L;

            Iterator i$;
            String p;
            for(i$ = this.fastMatchTimesPerPointcut.keySet().iterator(); i$.hasNext(); totalTime += (Long)this.fastMatchTimesPerPointcut.get(p)) {
               p = (String)i$.next();
            }

            this.world.getMessageHandler().handleMessage(MessageUtil.info("Pointcut fast matching cost (total=" + totalTime / 1000000L + "ms for " + this.typeCount + " fast match calls):"));
            i$ = this.fastMatchTimesPerPointcut.keySet().iterator();

            while(i$.hasNext()) {
               p = (String)i$.next();
               StringBuffer sb = new StringBuffer();
               sb.append("Time:" + (Long)this.fastMatchTimesPerPointcut.get(p) / 1000000L + "ms (types:#" + this.fastMatchTypesPerPointcut.get(p) + ") fast matching against " + p);
               this.world.getMessageHandler().handleMessage(MessageUtil.info(sb.toString()));
            }

            this.world.getMessageHandler().handleMessage(MessageUtil.info("---"));
         }

      }
   }

   private static class AspectPrecedenceCalculator {
      private final World world;
      private final Map cachedResults;

      public AspectPrecedenceCalculator(World forSomeWorld) {
         this.world = forSomeWorld;
         this.cachedResults = new HashMap();
      }

      public int compareByPrecedence(ResolvedType firstAspect, ResolvedType secondAspect) {
         PrecedenceCacheKey key = new PrecedenceCacheKey(firstAspect, secondAspect);
         if (this.cachedResults.containsKey(key)) {
            return (Integer)this.cachedResults.get(key);
         } else {
            int order = 0;
            DeclarePrecedence orderer = null;
            Iterator i = this.world.getCrosscuttingMembersSet().getDeclareDominates().iterator();

            while(true) {
               while(true) {
                  DeclarePrecedence d;
                  int thisOrder;
                  do {
                     if (!i.hasNext()) {
                        this.cachedResults.put(key, new Integer(order));
                        return order;
                     }

                     d = (DeclarePrecedence)i.next();
                     thisOrder = d.compare(firstAspect, secondAspect);
                  } while(thisOrder == 0);

                  if (orderer == null) {
                     orderer = d;
                  }

                  if (order != 0 && order != thisOrder) {
                     ISourceLocation[] isls = new ISourceLocation[]{orderer.getSourceLocation(), d.getSourceLocation()};
                     Message m = new Message("conflicting declare precedence orderings for aspects: " + firstAspect.getName() + " and " + secondAspect.getName(), (ISourceLocation)null, true, isls);
                     this.world.getMessageHandler().handleMessage(m);
                  } else {
                     order = thisOrder;
                  }
               }
            }
         }
      }

      public Integer getPrecedenceIfAny(ResolvedType aspect1, ResolvedType aspect2) {
         return (Integer)this.cachedResults.get(new PrecedenceCacheKey(aspect1, aspect2));
      }

      public int compareByPrecedenceAndHierarchy(ResolvedType firstAspect, ResolvedType secondAspect) {
         if (firstAspect.equals(secondAspect)) {
            return 0;
         } else {
            int ret = this.compareByPrecedence(firstAspect, secondAspect);
            if (ret != 0) {
               return ret;
            } else if (firstAspect.isAssignableFrom(secondAspect)) {
               return -1;
            } else {
               return secondAspect.isAssignableFrom(firstAspect) ? 1 : 0;
            }
         }
      }

      private static class PrecedenceCacheKey {
         public ResolvedType aspect1;
         public ResolvedType aspect2;

         public PrecedenceCacheKey(ResolvedType a1, ResolvedType a2) {
            this.aspect1 = a1;
            this.aspect2 = a2;
         }

         public boolean equals(Object obj) {
            if (!(obj instanceof PrecedenceCacheKey)) {
               return false;
            } else {
               PrecedenceCacheKey other = (PrecedenceCacheKey)obj;
               return this.aspect1 == other.aspect1 && this.aspect2 == other.aspect2;
            }
         }

         public int hashCode() {
            return this.aspect1.hashCode() + this.aspect2.hashCode();
         }
      }
   }

   public static class TypeMap {
      public static final int DONT_USE_REFS = 0;
      public static final int USE_WEAK_REFS = 1;
      public static final int USE_SOFT_REFS = 2;
      public List addedSinceLastDemote;
      public List writtenClasses;
      private static boolean debug = false;
      public static boolean useExpendableMap = true;
      private boolean demotionSystemActive;
      private boolean debugDemotion = false;
      public int policy = 1;
      final Map tMap = new HashMap();
      final Map expendableMap = Collections.synchronizedMap(new WeakHashMap());
      private final World w;
      private boolean memoryProfiling = false;
      private int maxExpendableMapSize = -1;
      private int collectedTypes = 0;
      private final ReferenceQueue rq = new ReferenceQueue();

      TypeMap(World w) {
         this.demotionSystemActive = w.isDemotionActive() && (w.isLoadtimeWeaving() || w.couldIncrementalCompileFollow());
         this.addedSinceLastDemote = new ArrayList();
         this.writtenClasses = new ArrayList();
         this.w = w;
         this.memoryProfiling = false;
      }

      public Map getExpendableMap() {
         return this.expendableMap;
      }

      public Map getMainMap() {
         return this.tMap;
      }

      public int demote() {
         return this.demote(false);
      }

      public int demote(boolean atEndOfCompile) {
         if (!this.demotionSystemActive) {
            return 0;
         } else {
            if (this.debugDemotion) {
               System.out.println("Demotion running " + this.addedSinceLastDemote);
            }

            boolean isLtw = this.w.isLoadtimeWeaving();
            int demotionCounter = 0;
            if (isLtw) {
               Iterator i$ = this.addedSinceLastDemote.iterator();

               label118:
               while(true) {
                  String key;
                  ResolvedType type;
                  List typeMungers;
                  do {
                     do {
                        do {
                           do {
                              do {
                                 if (!i$.hasNext()) {
                                    this.addedSinceLastDemote.clear();
                                    break label118;
                                 }

                                 key = (String)i$.next();
                                 type = (ResolvedType)this.tMap.get(key);
                              } while(type == null);
                           } while(type.isAspect());
                        } while(type.equals(UnresolvedType.OBJECT));
                     } while(type.isPrimitiveType());

                     typeMungers = type.getInterTypeMungers();
                  } while(typeMungers != null && typeMungers.size() != 0);

                  this.tMap.remove(key);
                  this.insertInExpendableMap(key, type);
                  ++demotionCounter;
               }
            } else {
               List forRemoval = new ArrayList();
               Iterator i$ = this.addedSinceLastDemote.iterator();

               while(true) {
                  while(i$.hasNext()) {
                     String key = (String)i$.next();
                     ResolvedType type = (ResolvedType)this.tMap.get(key);
                     if (type == null) {
                        forRemoval.add(key);
                     } else if (this.writtenClasses.contains(type.getName())) {
                        if (type != null && !type.isAspect() && !type.equals(UnresolvedType.OBJECT) && !type.isPrimitiveType()) {
                           List typeMungers = type.getInterTypeMungers();
                           if (typeMungers != null && typeMungers.size() != 0) {
                              this.writtenClasses.remove(type.getName());
                              forRemoval.add(key);
                           } else {
                              ReferenceTypeDelegate delegate = ((ReferenceType)type).getDelegate();
                              boolean isWeavable = delegate == null ? false : delegate.isExposedToWeaver();
                              boolean hasBeenWoven = delegate == null ? false : delegate.hasBeenWoven();
                              if (!isWeavable || hasBeenWoven) {
                                 if (this.debugDemotion) {
                                    System.out.println("Demoting " + key);
                                 }

                                 forRemoval.add(key);
                                 this.tMap.remove(key);
                                 this.insertInExpendableMap(key, type);
                                 ++demotionCounter;
                              }
                           }
                        } else {
                           this.writtenClasses.remove(type.getName());
                           forRemoval.add(key);
                        }
                     }
                  }

                  this.addedSinceLastDemote.removeAll(forRemoval);
                  break;
               }
            }

            if (this.debugDemotion) {
               System.out.println("Demoted " + demotionCounter + " types.  Types remaining in fixed set #" + this.tMap.keySet().size() + ".  addedSinceLastDemote size is " + this.addedSinceLastDemote.size());
               System.out.println("writtenClasses.size() = " + this.writtenClasses.size() + ": " + this.writtenClasses);
            }

            if (atEndOfCompile) {
               if (this.debugDemotion) {
                  System.out.println("Clearing writtenClasses");
               }

               this.writtenClasses.clear();
            }

            return demotionCounter;
         }
      }

      private void insertInExpendableMap(String key, ResolvedType type) {
         if (useExpendableMap && !this.expendableMap.containsKey(key)) {
            if (this.policy == 2) {
               this.expendableMap.put(key, new SoftReference(type));
            } else {
               this.expendableMap.put(key, new WeakReference(type));
            }
         }

      }

      public ResolvedType put(String key, ResolvedType type) {
         if (!type.isCacheable()) {
            return type;
         } else if (type.isParameterizedType() && type.isParameterizedWithTypeVariable()) {
            if (debug) {
               System.err.println("Not putting a parameterized type that utilises member declared type variables into the typemap: key=" + key + " type=" + type);
            }

            return type;
         } else if (type.isTypeVariableReference()) {
            if (debug) {
               System.err.println("Not putting a type variable reference type into the typemap: key=" + key + " type=" + type);
            }

            return type;
         } else if (type instanceof BoundedReferenceType) {
            if (debug) {
               System.err.println("Not putting a bounded reference type into the typemap: key=" + key + " type=" + type);
            }

            return type;
         } else if (type instanceof MissingResolvedTypeWithKnownSignature) {
            if (debug) {
               System.err.println("Not putting a missing type into the typemap: key=" + key + " type=" + type);
            }

            return type;
         } else if (type instanceof ReferenceType && ((ReferenceType)type).getDelegate() == null && this.w.isExpendable(type)) {
            if (debug) {
               System.err.println("Not putting expendable ref type with null delegate into typemap: key=" + key + " type=" + type);
            }

            return type;
         } else if (this.w.isExpendable(type)) {
            if (useExpendableMap) {
               if (this.policy == 1) {
                  if (this.memoryProfiling) {
                     this.expendableMap.put(key, new WeakReference(type, this.rq));
                  } else {
                     this.expendableMap.put(key, new WeakReference(type));
                  }
               } else if (this.policy == 2) {
                  if (this.memoryProfiling) {
                     this.expendableMap.put(key, new SoftReference(type, this.rq));
                  } else {
                     this.expendableMap.put(key, new SoftReference(type));
                  }
               }
            }

            if (this.memoryProfiling && this.expendableMap.size() > this.maxExpendableMapSize) {
               this.maxExpendableMapSize = this.expendableMap.size();
            }

            return type;
         } else {
            if (this.demotionSystemActive) {
               this.addedSinceLastDemote.add(key);
            }

            return (ResolvedType)this.tMap.put(key, type);
         }
      }

      public void report() {
         if (this.memoryProfiling) {
            this.checkq();
            this.w.getMessageHandler().handleMessage(MessageUtil.info("MEMORY: world expendable type map reached maximum size of #" + this.maxExpendableMapSize + " entries"));
            this.w.getMessageHandler().handleMessage(MessageUtil.info("MEMORY: types collected through garbage collection #" + this.collectedTypes + " entries"));
         }
      }

      public void checkq() {
         if (this.memoryProfiling) {
            for(Reference r = null; this.rq.poll() != null; ++this.collectedTypes) {
            }

         }
      }

      public ResolvedType get(String key) {
         this.checkq();
         ResolvedType ret = (ResolvedType)this.tMap.get(key);
         if (ret == null) {
            if (this.policy == 1) {
               WeakReference ref = (WeakReference)this.expendableMap.get(key);
               if (ref != null) {
                  ret = (ResolvedType)ref.get();
               }
            } else if (this.policy == 2) {
               SoftReference ref = (SoftReference)this.expendableMap.get(key);
               if (ref != null) {
                  ret = (ResolvedType)ref.get();
               }
            }
         }

         return ret;
      }

      public ResolvedType remove(String key) {
         ResolvedType ret = (ResolvedType)this.tMap.remove(key);
         if (ret == null) {
            if (this.policy == 1) {
               WeakReference wref = (WeakReference)this.expendableMap.remove(key);
               if (wref != null) {
                  ret = (ResolvedType)wref.get();
               }
            } else if (this.policy == 2) {
               SoftReference wref = (SoftReference)this.expendableMap.remove(key);
               if (wref != null) {
                  ret = (ResolvedType)wref.get();
               }
            }
         }

         return ret;
      }

      public void classWriteEvent(String classname) {
         if (this.demotionSystemActive) {
            this.writtenClasses.add(classname);
         }

         if (this.debugDemotion) {
            System.out.println("Class write event for " + classname);
         }

      }

      public void demote(ResolvedType type) {
         String key = type.getSignature();
         if (this.debugDemotion) {
            this.addedSinceLastDemote.remove(key);
         }

         this.tMap.remove(key);
         this.insertInExpendableMap(key, type);
      }
   }
}
