package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.BootstrapMethods;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Method;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionBranch;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionCP;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionLV;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionSelect;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionTargeter;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InvokeInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LineNumberTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LocalVariableTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.MULTIANEWARRAY;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.MethodGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.RET;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Tag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.WeaveMessage;
import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import com.bea.core.repackaged.aspectj.bridge.context.ContextToken;
import com.bea.core.repackaged.aspectj.util.PartialOrder;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.IClassWeaver;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MissingResolvedTypeWithKnownSignature;
import com.bea.core.repackaged.aspectj.weaver.NewConstructorTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewFieldTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewMethodTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedTypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.model.AsmRelationshipProvider;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.ExactTypePattern;
import com.bea.core.repackaged.aspectj.weaver.tools.Trace;
import com.bea.core.repackaged.aspectj.weaver.tools.TraceFactory;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

class BcelClassWeaver implements IClassWeaver {
   private static Trace trace = TraceFactory.getTraceFactory().getTrace(BcelClassWeaver.class);
   private final LazyClassGen clazz;
   private final List shadowMungers;
   private final List typeMungers;
   private final List lateTypeMungers;
   private List[] indexedShadowMungers;
   private boolean canMatchBodyShadows = false;
   private final BcelObjectType ty;
   private final BcelWorld world;
   private final ConstantPool cpg;
   private final InstructionFactory fact;
   private final List addedLazyMethodGens = new ArrayList();
   private final Set addedDispatchTargets = new HashSet();
   private boolean inReweavableMode = false;
   private List addedSuperInitializersAsList = null;
   private final Map addedSuperInitializers = new HashMap();
   private final List addedThisInitializers = new ArrayList();
   private final List addedClassInitializers = new ArrayList();
   private final Map mapToAnnotationHolder = new HashMap();
   private final List initializationShadows = new ArrayList();
   private static boolean checkedXsetForLowLevelContextCapturing = false;
   private static boolean captureLowLevelContext = false;

   public static boolean weave(BcelWorld world, LazyClassGen clazz, List shadowMungers, List typeMungers, List lateTypeMungers, boolean inReweavableMode) {
      BcelClassWeaver classWeaver = new BcelClassWeaver(world, clazz, shadowMungers, typeMungers, lateTypeMungers);
      classWeaver.setReweavableMode(inReweavableMode);
      boolean b = classWeaver.weave();
      return b;
   }

   private BcelClassWeaver(BcelWorld world, LazyClassGen clazz, List shadowMungers, List typeMungers, List lateTypeMungers) {
      this.world = world;
      this.clazz = clazz;
      this.shadowMungers = shadowMungers;
      this.typeMungers = typeMungers;
      this.lateTypeMungers = lateTypeMungers;
      this.ty = clazz.getBcelObjectType();
      this.cpg = clazz.getConstantPool();
      this.fact = clazz.getFactory();
      this.indexShadowMungers();
      this.initializeSuperInitializerMap(this.ty.getResolvedTypeX());
      if (!checkedXsetForLowLevelContextCapturing) {
         Properties p = world.getExtraConfiguration();
         if (p != null) {
            String s = p.getProperty("captureAllContext", "false");
            captureLowLevelContext = s.equalsIgnoreCase("true");
            if (captureLowLevelContext) {
               world.getMessageHandler().handleMessage(MessageUtil.info("[captureAllContext=true] Enabling collection of low level context for debug/crash messages"));
            }
         }

         checkedXsetForLowLevelContextCapturing = true;
      }

   }

   private boolean canMatch(Shadow.Kind kind) {
      return this.indexedShadowMungers[kind.getKey()] != null;
   }

   private void initializeSuperInitializerMap(ResolvedType child) {
      ResolvedType[] superInterfaces = child.getDeclaredInterfaces();
      int i = 0;

      for(int len = superInterfaces.length; i < len; ++i) {
         if (this.ty.getResolvedTypeX().isTopmostImplementor(superInterfaces[i]) && this.addSuperInitializer(superInterfaces[i])) {
            this.initializeSuperInitializerMap(superInterfaces[i]);
         }
      }

   }

   private void indexShadowMungers() {
      this.indexedShadowMungers = new List[14];
      Iterator i$ = this.shadowMungers.iterator();

      while(i$.hasNext()) {
         ShadowMunger shadowMunger = (ShadowMunger)i$.next();
         int couldMatchKinds = shadowMunger.getPointcut().couldMatchKinds();
         Shadow.Kind[] arr$ = Shadow.SHADOW_KINDS;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Shadow.Kind kind = arr$[i$];
            if (kind.isSet(couldMatchKinds)) {
               byte k = kind.getKey();
               if (this.indexedShadowMungers[k] == null) {
                  this.indexedShadowMungers[k] = new ArrayList();
                  if (!kind.isEnclosingKind()) {
                     this.canMatchBodyShadows = true;
                  }
               }

               this.indexedShadowMungers[k].add(shadowMunger);
            }
         }
      }

   }

   private boolean addSuperInitializer(ResolvedType onType) {
      if (((ResolvedType)onType).isRawType() || ((ResolvedType)onType).isParameterizedType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      IfaceInitList l = (IfaceInitList)this.addedSuperInitializers.get(onType);
      if (l != null) {
         return false;
      } else {
         l = new IfaceInitList((ResolvedType)onType);
         this.addedSuperInitializers.put(onType, l);
         return true;
      }
   }

   public void addInitializer(ConcreteTypeMunger cm) {
      NewFieldTypeMunger m = (NewFieldTypeMunger)cm.getMunger();
      ResolvedType onType = m.getSignature().getDeclaringType().resolve(this.world);
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      if (Modifier.isStatic(m.getSignature().getModifiers())) {
         this.addedClassInitializers.add(cm);
      } else if (onType == this.ty.getResolvedTypeX()) {
         this.addedThisInitializers.add(cm);
      } else {
         IfaceInitList l = (IfaceInitList)this.addedSuperInitializers.get(onType);
         l.list.add(cm);
      }

   }

   public boolean addDispatchTarget(ResolvedMember m) {
      return this.addedDispatchTargets.add(m);
   }

   public void addLazyMethodGen(LazyMethodGen gen) {
      this.addedLazyMethodGens.add(gen);
   }

   public void addOrReplaceLazyMethodGen(LazyMethodGen mg) {
      if (!this.alreadyDefined(this.clazz, mg)) {
         Iterator i = this.addedLazyMethodGens.iterator();

         LazyMethodGen existing;
         do {
            if (!i.hasNext()) {
               this.addedLazyMethodGens.add(mg);
               return;
            }

            existing = (LazyMethodGen)i.next();
         } while(!this.signaturesMatch(mg, existing));

         if (existing.definingType != null) {
            if (!mg.definingType.isAssignableFrom(existing.definingType)) {
               if (existing.definingType.isAssignableFrom(mg.definingType)) {
                  i.remove();
                  this.addedLazyMethodGens.add(mg);
               } else {
                  throw new BCException("conflict between: " + mg + " and " + existing);
               }
            }
         }
      }
   }

   private boolean alreadyDefined(LazyClassGen clazz, LazyMethodGen mg) {
      Iterator i = clazz.getMethodGens().iterator();

      LazyMethodGen existing;
      do {
         if (!i.hasNext()) {
            return false;
         }

         existing = (LazyMethodGen)i.next();
      } while(!this.signaturesMatch(mg, existing));

      if (!mg.isAbstract() && existing.isAbstract()) {
         i.remove();
         return false;
      } else {
         return true;
      }
   }

   private boolean signaturesMatch(LazyMethodGen mg, LazyMethodGen existing) {
      return mg.getName().equals(existing.getName()) && mg.getSignature().equals(existing.getSignature());
   }

   protected static LazyMethodGen makeBridgeMethod(LazyClassGen gen, ResolvedMember member) {
      int mods = member.getModifiers();
      if (Modifier.isAbstract(mods)) {
         mods -= 1024;
      }

      LazyMethodGen ret = new LazyMethodGen(mods, BcelWorld.makeBcelType(member.getReturnType()), member.getName(), BcelWorld.makeBcelTypes(member.getParameterTypes()), UnresolvedType.getNames(member.getExceptions()), gen);
      return ret;
   }

   private static void createBridgeMethod(BcelWorld world, LazyMethodGen whatToBridgeToMethodGen, LazyClassGen clazz, ResolvedMember theBridgeMethod) {
      int pos = 0;
      ResolvedMember whatToBridgeTo = whatToBridgeToMethodGen.getMemberView();
      if (whatToBridgeTo == null) {
         whatToBridgeTo = new ResolvedMemberImpl(Member.METHOD, whatToBridgeToMethodGen.getEnclosingClass().getType(), whatToBridgeToMethodGen.getAccessFlags(), whatToBridgeToMethodGen.getName(), whatToBridgeToMethodGen.getSignature());
      }

      LazyMethodGen bridgeMethod = makeBridgeMethod(clazz, theBridgeMethod);
      int newflags = bridgeMethod.getAccessFlags() | 64 | 4096;
      if ((newflags & 256) != 0) {
         newflags -= 256;
      }

      bridgeMethod.setAccessFlags(newflags);
      Type returnType = BcelWorld.makeBcelType(theBridgeMethod.getReturnType());
      Type[] paramTypes = BcelWorld.makeBcelTypes(theBridgeMethod.getParameterTypes());
      Type[] newParamTypes = whatToBridgeToMethodGen.getArgumentTypes();
      InstructionList body = bridgeMethod.getBody();
      InstructionFactory fact = clazz.getFactory();
      if (!whatToBridgeToMethodGen.isStatic()) {
         body.append(InstructionFactory.createThis());
         ++pos;
      }

      int i = 0;

      for(int len = paramTypes.length; i < len; ++i) {
         Type paramType = paramTypes[i];
         body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
         if (!newParamTypes[i].equals(paramTypes[i])) {
            if (world.forDEBUG_bridgingCode) {
               System.err.println("Bridging: Cast " + newParamTypes[i] + " from " + paramTypes[i]);
            }

            body.append(fact.createCast(paramTypes[i], newParamTypes[i]));
         }

         pos += paramType.getSize();
      }

      body.append(Utility.createInvoke(fact, world, (Member)whatToBridgeTo));
      body.append(InstructionFactory.createReturn(returnType));
      clazz.addMethodGen(bridgeMethod);
   }

   public boolean weave() {
      if (this.clazz.isWoven() && !this.clazz.isReweavable()) {
         if (this.world.getLint().nonReweavableTypeEncountered.isEnabled()) {
            this.world.getLint().nonReweavableTypeEncountered.signal(this.clazz.getType().getName(), this.ty.getSourceLocation());
         }

         return false;
      } else {
         Set aspectsAffectingType = null;
         if (this.inReweavableMode || this.clazz.getType().isAspect()) {
            aspectsAffectingType = new HashSet();
         }

         boolean isChanged = false;
         if (this.clazz.getType().isAspect()) {
            isChanged = true;
         }

         WeaverStateInfo typeWeaverState = this.world.isOverWeaving() ? this.getLazyClassGen().getType().getWeaverState() : null;
         Iterator i$ = this.typeMungers.iterator();

         while(true) {
            BcelTypeMunger munger;
            do {
               boolean typeMungerAffectedType;
               do {
                  do {
                     ConcreteTypeMunger o;
                     do {
                        if (!i$.hasNext()) {
                           isChanged = this.weaveDeclareAtMethodCtor(this.clazz) || isChanged;
                           isChanged = this.weaveDeclareAtField(this.clazz) || isChanged;
                           this.addedSuperInitializersAsList = new ArrayList(this.addedSuperInitializers.values());
                           this.addedSuperInitializersAsList = PartialOrder.sort(this.addedSuperInitializersAsList);
                           if (this.addedSuperInitializersAsList == null) {
                              throw new BCException("circularity in inter-types");
                           }

                           LazyMethodGen staticInit = this.clazz.getStaticInitializer();
                           staticInit.getBody().insert(this.genInitInstructions(this.addedClassInitializers, true));
                           List methodGens = new ArrayList(this.clazz.getMethodGens());
                           Iterator i$ = methodGens.iterator();

                           while(true) {
                              boolean typeMungerAffectedType;
                              LazyMethodGen mg;
                              do {
                                 do {
                                    if (!i$.hasNext()) {
                                       i$ = methodGens.iterator();

                                       while(i$.hasNext()) {
                                          mg = (LazyMethodGen)i$.next();
                                          if (mg.hasBody()) {
                                             this.implement(mg);
                                          }
                                       }

                                       if (!this.initializationShadows.isEmpty()) {
                                          List recursiveCtors = new ArrayList();

                                          while(true) {
                                             if (!this.inlineSelfConstructors(methodGens, recursiveCtors)) {
                                                this.positionAndImplement(this.initializationShadows);
                                                break;
                                             }
                                          }
                                       }

                                       if (this.lateTypeMungers != null) {
                                          i$ = this.lateTypeMungers.iterator();

                                          label123:
                                          while(true) {
                                             BcelTypeMunger munger;
                                             do {
                                                do {
                                                   do {
                                                      if (!i$.hasNext()) {
                                                         break label123;
                                                      }

                                                      munger = (BcelTypeMunger)i$.next();
                                                   } while(!munger.matches(this.clazz.getType()));

                                                   typeMungerAffectedType = munger.munge(this);
                                                } while(!typeMungerAffectedType);

                                                isChanged = true;
                                             } while(!this.inReweavableMode && !this.clazz.getType().isAspect());

                                             aspectsAffectingType.add(munger.getAspectType().getSignature());
                                          }
                                       }

                                       if (isChanged) {
                                          this.clazz.getOrCreateWeaverStateInfo(this.inReweavableMode);
                                          this.weaveInAddedMethods();
                                       }

                                       if (this.inReweavableMode) {
                                          WeaverStateInfo wsi = this.clazz.getOrCreateWeaverStateInfo(true);
                                          wsi.addAspectsAffectingType(aspectsAffectingType);
                                          wsi.setUnwovenClassFileData(this.ty.getJavaClass().getBytes());
                                          wsi.setReweavable(true);
                                       } else {
                                          this.clazz.getOrCreateWeaverStateInfo(false).setReweavable(false);
                                       }

                                       i$ = methodGens.iterator();

                                       while(i$.hasNext()) {
                                          mg = (LazyMethodGen)i$.next();
                                          BcelMethod method = mg.getMemberView();
                                          if (method != null) {
                                             method.wipeJoinpointSignatures();
                                          }
                                       }

                                       return isChanged;
                                    }

                                    mg = (LazyMethodGen)i$.next();
                                 } while(!mg.hasBody());

                                 if (this.world.isJoinpointSynchronizationEnabled() && this.world.areSynchronizationPointcutsInUse() && mg.getMethod().isSynchronized()) {
                                    transformSynchronizedMethod(mg);
                                 }

                                 typeMungerAffectedType = this.match(mg);
                              } while(!typeMungerAffectedType);

                              if (this.inReweavableMode || this.clazz.getType().isAspect()) {
                                 aspectsAffectingType.addAll(this.findAspectsForMungers(mg));
                              }

                              isChanged = true;
                           }
                        }

                        o = (ConcreteTypeMunger)i$.next();
                     } while(!(o instanceof BcelTypeMunger));

                     munger = (BcelTypeMunger)o;
                  } while(typeWeaverState != null && typeWeaverState.isAspectAlreadyApplied(munger.getAspectType()));

                  typeMungerAffectedType = munger.munge(this);
               } while(!typeMungerAffectedType);

               isChanged = true;
            } while(!this.inReweavableMode && !this.clazz.getType().isAspect());

            aspectsAffectingType.add(munger.getAspectType().getSignature());
         }
      }
   }

   private static ResolvedMember isOverriding(ResolvedType typeToCheck, ResolvedMember methodThatMightBeGettingOverridden, String mname, String mrettype, int mmods, boolean inSamePackage, UnresolvedType[] methodParamsArray) {
      if (Modifier.isStatic(methodThatMightBeGettingOverridden.getModifiers())) {
         return null;
      } else if (Modifier.isPrivate(methodThatMightBeGettingOverridden.getModifiers())) {
         return null;
      } else if (!methodThatMightBeGettingOverridden.getName().equals(mname)) {
         return null;
      } else if (methodThatMightBeGettingOverridden.getParameterTypes().length != methodParamsArray.length) {
         return null;
      } else if (!isVisibilityOverride(mmods, methodThatMightBeGettingOverridden, inSamePackage)) {
         return null;
      } else {
         if (typeToCheck.getWorld().forDEBUG_bridgingCode) {
            System.err.println("  Bridging:seriously considering this might be getting overridden '" + methodThatMightBeGettingOverridden + "'");
         }

         World w = typeToCheck.getWorld();
         boolean sameParams = true;
         int p = 0;

         for(int max = methodThatMightBeGettingOverridden.getParameterTypes().length; p < max; ++p) {
            UnresolvedType mtmbgoParameter = methodThatMightBeGettingOverridden.getParameterTypes()[p];
            UnresolvedType ptype = methodParamsArray[p];
            if (mtmbgoParameter.isTypeVariableReference()) {
               if (!mtmbgoParameter.resolve(w).isAssignableFrom(ptype.resolve(w))) {
                  sameParams = false;
               }
            } else {
               boolean b = !methodThatMightBeGettingOverridden.getParameterTypes()[p].getErasureSignature().equals(methodParamsArray[p].getErasureSignature());
               UnresolvedType parameterType = methodThatMightBeGettingOverridden.getParameterTypes()[p];
               if (parameterType instanceof UnresolvedTypeVariableReferenceType) {
                  parameterType = ((UnresolvedTypeVariableReferenceType)parameterType).getTypeVariable().getFirstBound();
               }

               if (b) {
                  sameParams = false;
               }
            }
         }

         if (sameParams) {
            if (typeToCheck.isParameterizedType()) {
               return methodThatMightBeGettingOverridden.getBackingGenericMember();
            }

            if (methodThatMightBeGettingOverridden.getReturnType().getErasureSignature().equals(mrettype)) {
               return methodThatMightBeGettingOverridden;
            }

            ResolvedType superReturn = typeToCheck.getWorld().resolve(UnresolvedType.forSignature(methodThatMightBeGettingOverridden.getReturnType().getErasureSignature()));
            ResolvedType subReturn = typeToCheck.getWorld().resolve(UnresolvedType.forSignature(mrettype));
            if (superReturn.isAssignableFrom(subReturn)) {
               return methodThatMightBeGettingOverridden;
            }
         }

         return null;
      }
   }

   static boolean isVisibilityOverride(int methodMods, ResolvedMember inheritedMethod, boolean inSamePackage) {
      int inheritedModifiers = inheritedMethod.getModifiers();
      if (Modifier.isStatic(inheritedModifiers)) {
         return false;
      } else if (methodMods == inheritedModifiers) {
         return true;
      } else if (Modifier.isPrivate(inheritedModifiers)) {
         return false;
      } else {
         boolean isPackageVisible = !Modifier.isPrivate(inheritedModifiers) && !Modifier.isProtected(inheritedModifiers) && !Modifier.isPublic(inheritedModifiers);
         return !isPackageVisible || inSamePackage;
      }
   }

   public static void checkForOverride(ResolvedType typeToCheck, String mname, String mparams, String mrettype, int mmods, String mpkg, UnresolvedType[] methodParamsArray, List overriddenMethodsCollector) {
      if (typeToCheck != null) {
         if (!(typeToCheck instanceof MissingResolvedTypeWithKnownSignature)) {
            if (typeToCheck.getWorld().forDEBUG_bridgingCode) {
               System.err.println("  Bridging:checking for override of " + mname + " in " + typeToCheck);
            }

            String packageName = typeToCheck.getPackageName();
            if (packageName == null) {
               packageName = "";
            }

            boolean inSamePackage = packageName.equals(mpkg);
            ResolvedMember[] methods = typeToCheck.getDeclaredMethods();

            for(int ii = 0; ii < methods.length; ++ii) {
               ResolvedMember methodThatMightBeGettingOverridden = methods[ii];
               ResolvedMember isOverriding = isOverriding(typeToCheck, methodThatMightBeGettingOverridden, mname, mrettype, mmods, inSamePackage, methodParamsArray);
               if (isOverriding != null) {
                  overriddenMethodsCollector.add(isOverriding);
               }
            }

            List l = typeToCheck.isRawType() ? typeToCheck.getGenericType().getInterTypeMungers() : typeToCheck.getInterTypeMungers();
            Iterator iterator = l.iterator();

            while(iterator.hasNext()) {
               ConcreteTypeMunger o = (ConcreteTypeMunger)iterator.next();
               if (o instanceof BcelTypeMunger) {
                  BcelTypeMunger element = (BcelTypeMunger)o;
                  if (element.getMunger() instanceof NewMethodTypeMunger) {
                     if (typeToCheck.getWorld().forDEBUG_bridgingCode) {
                        System.err.println("Possible ITD candidate " + element);
                     }

                     ResolvedMember aMethod = element.getSignature();
                     ResolvedMember isOverriding = isOverriding(typeToCheck, aMethod, mname, mrettype, mmods, inSamePackage, methodParamsArray);
                     if (isOverriding != null) {
                        overriddenMethodsCollector.add(isOverriding);
                     }
                  }
               }
            }

            if (!typeToCheck.equals(UnresolvedType.OBJECT)) {
               ResolvedType superclass = typeToCheck.getSuperclass();
               checkForOverride(superclass, mname, mparams, mrettype, mmods, mpkg, methodParamsArray, overriddenMethodsCollector);
               ResolvedType[] interfaces = typeToCheck.getDeclaredInterfaces();

               for(int i = 0; i < interfaces.length; ++i) {
                  ResolvedType anInterface = interfaces[i];
                  checkForOverride(anInterface, mname, mparams, mrettype, mmods, mpkg, methodParamsArray, overriddenMethodsCollector);
               }

            }
         }
      }
   }

   public static boolean calculateAnyRequiredBridgeMethods(BcelWorld world, LazyClassGen clazz) {
      world.ensureAdvancedConfigurationProcessed();
      if (!world.isInJava5Mode()) {
         return false;
      } else if (clazz.isInterface()) {
         return false;
      } else {
         boolean didSomething = false;
         List methods = clazz.getMethodGens();
         Set methodsSet = new HashSet();

         int i;
         LazyMethodGen bridgeToCandidate;
         for(i = 0; i < methods.size(); ++i) {
            bridgeToCandidate = (LazyMethodGen)methods.get(i);
            StringBuilder sb = new StringBuilder(bridgeToCandidate.getName());
            sb.append(bridgeToCandidate.getSignature());
            methodsSet.add(sb.toString());
         }

         for(i = 0; i < methods.size(); ++i) {
            bridgeToCandidate = (LazyMethodGen)methods.get(i);
            if (!bridgeToCandidate.isBridgeMethod()) {
               String name = bridgeToCandidate.getName();
               String psig = bridgeToCandidate.getParameterSignature();
               String rsig = bridgeToCandidate.getReturnType().getSignature();
               if (!bridgeToCandidate.isStatic() && !name.endsWith("init>")) {
                  if (world.forDEBUG_bridgingCode) {
                     System.err.println("Bridging: Determining if we have to bridge to " + clazz.getName() + "." + name + "" + bridgeToCandidate.getSignature());
                  }

                  ResolvedType theSuperclass = clazz.getSuperClass();
                  if (world.forDEBUG_bridgingCode) {
                     System.err.println("Bridging: Checking supertype " + theSuperclass);
                  }

                  String pkgName = clazz.getPackageName();
                  UnresolvedType[] bm = BcelWorld.fromBcel(bridgeToCandidate.getArgumentTypes());
                  List overriddenMethodsCollector = new ArrayList();
                  checkForOverride(theSuperclass, name, psig, rsig, bridgeToCandidate.getAccessFlags(), pkgName, bm, overriddenMethodsCollector);
                  if (overriddenMethodsCollector.size() != 0) {
                     Iterator i$ = overriddenMethodsCollector.iterator();

                     while(i$.hasNext()) {
                        ResolvedMember overriddenMethod = (ResolvedMember)i$.next();
                        String key = overriddenMethod.getName() + overriddenMethod.getSignatureErased();
                        boolean alreadyHaveABridgeMethod = methodsSet.contains(key);
                        if (!alreadyHaveABridgeMethod) {
                           if (world.forDEBUG_bridgingCode) {
                              System.err.println("Bridging:bridging to '" + overriddenMethod + "'");
                           }

                           createBridgeMethod(world, bridgeToCandidate, clazz, overriddenMethod);
                           methodsSet.add(key);
                           didSomething = true;
                        }
                     }
                  }

                  String[] interfaces = clazz.getInterfaceNames();

                  for(int j = 0; j < interfaces.length; ++j) {
                     if (world.forDEBUG_bridgingCode) {
                        System.err.println("Bridging:checking superinterface " + interfaces[j]);
                     }

                     ResolvedType interfaceType = world.resolve(interfaces[j]);
                     overriddenMethodsCollector.clear();
                     checkForOverride(interfaceType, name, psig, rsig, bridgeToCandidate.getAccessFlags(), clazz.getPackageName(), bm, overriddenMethodsCollector);
                     Iterator i$ = overriddenMethodsCollector.iterator();

                     while(i$.hasNext()) {
                        ResolvedMember overriddenMethod = (ResolvedMember)i$.next();
                        String key = overriddenMethod.getName() + overriddenMethod.getSignatureErased();
                        boolean alreadyHaveABridgeMethod = methodsSet.contains(key);
                        if (!alreadyHaveABridgeMethod) {
                           createBridgeMethod(world, bridgeToCandidate, clazz, overriddenMethod);
                           methodsSet.add(key);
                           didSomething = true;
                           if (world.forDEBUG_bridgingCode) {
                              System.err.println("Bridging:bridging to " + overriddenMethod);
                           }
                        }
                     }
                  }
               }
            }
         }

         return didSomething;
      }
   }

   private boolean weaveDeclareAtMethodCtor(LazyClassGen clazz) {
      List reportedProblems = new ArrayList();
      List allDecams = this.world.getDeclareAnnotationOnMethods();
      if (allDecams.isEmpty()) {
         return false;
      } else {
         boolean isChanged = false;
         List itdMethodsCtors = this.getITDSubset(clazz, ResolvedTypeMunger.Method);
         itdMethodsCtors.addAll(this.getITDSubset(clazz, ResolvedTypeMunger.Constructor));
         if (!itdMethodsCtors.isEmpty()) {
            isChanged = this.weaveAtMethodOnITDSRepeatedly(allDecams, itdMethodsCtors, reportedProblems);
         }

         List decaMs = this.getMatchingSubset(allDecams, clazz.getType());
         if (decaMs.isEmpty()) {
            return false;
         } else {
            Set unusedDecams = new HashSet();
            unusedDecams.addAll(decaMs);
            ArrayList worthRetrying;
            boolean modificationOccured;
            ArrayList annotationsToAdd;
            Iterator i$;
            DeclareAnnotation decaM;
            AnnotationGen a;
            if (this.addedLazyMethodGens != null) {
               Iterator i$ = this.addedLazyMethodGens.iterator();

               while(i$.hasNext()) {
                  LazyMethodGen method = (LazyMethodGen)i$.next();
                  ResolvedMember resolvedmember = new ResolvedMemberImpl(ResolvedMember.METHOD, method.getEnclosingClass().getType(), method.getAccessFlags(), BcelWorld.fromBcel(method.getReturnType()), method.getName(), BcelWorld.fromBcel(method.getArgumentTypes()), UnresolvedType.forNames(method.getDeclaredExceptions()));
                  resolvedmember.setAnnotationTypes(method.getAnnotationTypes());
                  resolvedmember.setAnnotations(method.getAnnotations());
                  worthRetrying = new ArrayList();
                  modificationOccured = false;
                  Iterator i$ = decaMs.iterator();

                  while(i$.hasNext()) {
                     DeclareAnnotation decam = (DeclareAnnotation)i$.next();
                     if (decam.matches(resolvedmember, this.world)) {
                        if (this.doesAlreadyHaveAnnotation(resolvedmember, decam, reportedProblems, false)) {
                           unusedDecams.remove(decam);
                        } else {
                           AnnotationGen a = ((BcelAnnotation)decam.getAnnotation()).getBcelAnnotation();
                           AnnotationAJ aj = new BcelAnnotation(new AnnotationGen(a, clazz.getConstantPool(), true), this.world);
                           method.addAnnotation(aj);
                           resolvedmember.addAnnotation(decam.getAnnotation());
                           AsmRelationshipProvider.addDeclareAnnotationMethodRelationship(decam.getSourceLocation(), clazz.getName(), resolvedmember, this.world.getModelAsAsmManager());
                           this.reportMethodCtorWeavingMessage(clazz, resolvedmember, decam, method.getDeclarationLineNumber());
                           isChanged = true;
                           modificationOccured = true;
                           unusedDecams.remove(decam);
                        }
                     } else if (!decam.isStarredAnnotationPattern()) {
                        worthRetrying.add(decam);
                     }
                  }

                  while(!worthRetrying.isEmpty() && modificationOccured) {
                     modificationOccured = false;
                     annotationsToAdd = new ArrayList();
                     i$ = worthRetrying.iterator();

                     while(i$.hasNext()) {
                        decaM = (DeclareAnnotation)i$.next();
                        if (decaM.matches(resolvedmember, this.world)) {
                           if (this.doesAlreadyHaveAnnotation(resolvedmember, decaM, reportedProblems, false)) {
                              unusedDecams.remove(decaM);
                           } else {
                              a = ((BcelAnnotation)decaM.getAnnotation()).getBcelAnnotation();
                              AnnotationAJ aj = new BcelAnnotation(new AnnotationGen(a, clazz.getConstantPool(), true), this.world);
                              method.addAnnotation(aj);
                              resolvedmember.addAnnotation(decaM.getAnnotation());
                              AsmRelationshipProvider.addDeclareAnnotationMethodRelationship(decaM.getSourceLocation(), clazz.getName(), resolvedmember, this.world.getModelAsAsmManager());
                              isChanged = true;
                              modificationOccured = true;
                              annotationsToAdd.add(decaM);
                              unusedDecams.remove(decaM);
                           }
                        }
                     }

                     worthRetrying.removeAll(annotationsToAdd);
                  }
               }
            }

            List members = clazz.getMethodGens();
            if (!members.isEmpty()) {
               for(int memberCounter = 0; memberCounter < members.size(); ++memberCounter) {
                  LazyMethodGen mg = (LazyMethodGen)members.get(memberCounter);
                  if (!mg.getName().startsWith("ajc$")) {
                     worthRetrying = new ArrayList();
                     modificationOccured = false;
                     annotationsToAdd = null;
                     i$ = decaMs.iterator();

                     AnnotationGen a;
                     while(i$.hasNext()) {
                        decaM = (DeclareAnnotation)i$.next();
                        if (decaM.matches(mg.getMemberView(), this.world)) {
                           if (this.doesAlreadyHaveAnnotation(mg.getMemberView(), decaM, reportedProblems, true)) {
                              unusedDecams.remove(decaM);
                           } else {
                              if (annotationsToAdd == null) {
                                 annotationsToAdd = new ArrayList();
                              }

                              a = ((BcelAnnotation)decaM.getAnnotation()).getBcelAnnotation();
                              a = new AnnotationGen(a, clazz.getConstantPool(), true);
                              annotationsToAdd.add(a);
                              mg.addAnnotation(decaM.getAnnotation());
                              AsmRelationshipProvider.addDeclareAnnotationMethodRelationship(decaM.getSourceLocation(), clazz.getName(), mg.getMemberView(), this.world.getModelAsAsmManager());
                              this.reportMethodCtorWeavingMessage(clazz, mg.getMemberView(), decaM, mg.getDeclarationLineNumber());
                              isChanged = true;
                              modificationOccured = true;
                              unusedDecams.remove(decaM);
                           }
                        } else if (!decaM.isStarredAnnotationPattern()) {
                           worthRetrying.add(decaM);
                        }
                     }

                     while(!worthRetrying.isEmpty() && modificationOccured) {
                        modificationOccured = false;
                        List forRemoval = new ArrayList();
                        Iterator i$ = worthRetrying.iterator();

                        while(i$.hasNext()) {
                           DeclareAnnotation decaM = (DeclareAnnotation)i$.next();
                           if (decaM.matches(mg.getMemberView(), this.world)) {
                              if (this.doesAlreadyHaveAnnotation(mg.getMemberView(), decaM, reportedProblems, true)) {
                                 unusedDecams.remove(decaM);
                              } else {
                                 if (annotationsToAdd == null) {
                                    annotationsToAdd = new ArrayList();
                                 }

                                 a = ((BcelAnnotation)decaM.getAnnotation()).getBcelAnnotation();
                                 AnnotationGen ag = new AnnotationGen(a, clazz.getConstantPool(), true);
                                 annotationsToAdd.add(ag);
                                 mg.addAnnotation(decaM.getAnnotation());
                                 AsmRelationshipProvider.addDeclareAnnotationMethodRelationship(decaM.getSourceLocation(), clazz.getName(), mg.getMemberView(), this.world.getModelAsAsmManager());
                                 isChanged = true;
                                 modificationOccured = true;
                                 forRemoval.add(decaM);
                                 unusedDecams.remove(decaM);
                              }
                           }
                        }

                        worthRetrying.removeAll(forRemoval);
                     }

                     if (annotationsToAdd != null) {
                        Method oldMethod = mg.getMethod();
                        MethodGen myGen = new MethodGen(oldMethod, clazz.getClassName(), clazz.getConstantPool(), false);
                        Iterator i$ = annotationsToAdd.iterator();

                        while(i$.hasNext()) {
                           a = (AnnotationGen)i$.next();
                           myGen.addAnnotation(a);
                        }

                        Method newMethod = myGen.getMethod();
                        members.set(memberCounter, new LazyMethodGen(newMethod, clazz));
                     }
                  }
               }

               this.checkUnusedDeclareAts(unusedDecams, false);
            }

            return isChanged;
         }
      }
   }

   private void reportMethodCtorWeavingMessage(LazyClassGen clazz, ResolvedMember member, DeclareAnnotation decaM, int memberLineNumber) {
      if (!this.getWorld().getMessageHandler().isIgnoring(IMessage.WEAVEINFO)) {
         StringBuffer parmString = new StringBuffer("(");
         UnresolvedType[] paramTypes = member.getParameterTypes();

         for(int i = 0; i < paramTypes.length; ++i) {
            UnresolvedType type = paramTypes[i];
            String s = com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.signatureToString(type.getSignature());
            if (s.lastIndexOf(46) != -1) {
               s = s.substring(s.lastIndexOf(46) + 1);
            }

            parmString.append(s);
            if (i + 1 < paramTypes.length) {
               parmString.append(",");
            }
         }

         parmString.append(")");
         String methodName = member.getName();
         StringBuffer sig = new StringBuffer();
         sig.append(com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility.accessToString(member.getModifiers()));
         sig.append(" ");
         sig.append(member.getReturnType().toString());
         sig.append(" ");
         sig.append(member.getDeclaringType().toString());
         sig.append(".");
         sig.append(methodName.equals("<init>") ? "new" : methodName);
         sig.append(parmString);
         StringBuffer loc = new StringBuffer();
         if (clazz.getFileName() == null) {
            loc.append("no debug info available");
         } else {
            loc.append(clazz.getFileName());
            if (memberLineNumber != -1) {
               loc.append(":" + memberLineNumber);
            }
         }

         this.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_ANNOTATES, new String[]{sig.toString(), loc.toString(), decaM.getAnnotationString(), methodName.startsWith("<init>") ? "constructor" : "method", decaM.getAspect().toString(), Utility.beautifyLocation(decaM.getSourceLocation())}));
      }

   }

   private List getMatchingSubset(List declareAnnotations, ResolvedType type) {
      List subset = new ArrayList();
      Iterator i$ = declareAnnotations.iterator();

      while(i$.hasNext()) {
         DeclareAnnotation da = (DeclareAnnotation)i$.next();
         if (da.couldEverMatch(type)) {
            subset.add(da);
         }
      }

      return subset;
   }

   private List getITDSubset(LazyClassGen clazz, ResolvedTypeMunger.Kind wantedKind) {
      List subset = new ArrayList();
      Iterator i$ = clazz.getBcelObjectType().getTypeMungers().iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger typeMunger = (ConcreteTypeMunger)i$.next();
         if (typeMunger.getMunger().getKind() == wantedKind) {
            subset.add(typeMunger);
         }
      }

      return subset;
   }

   public LazyMethodGen locateAnnotationHolderForFieldMunger(LazyClassGen clazz, ConcreteTypeMunger fieldMunger) {
      NewFieldTypeMunger newFieldMunger = (NewFieldTypeMunger)fieldMunger.getMunger();
      ResolvedMember lookingFor = AjcMemberMaker.interFieldInitializer(newFieldMunger.getSignature(), clazz.getType());
      Iterator i$ = clazz.getMethodGens().iterator();

      LazyMethodGen method;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         method = (LazyMethodGen)i$.next();
      } while(!method.getName().equals(lookingFor.getName()));

      return method;
   }

   public LazyMethodGen locateAnnotationHolderForMethodCtorMunger(LazyClassGen clazz, ConcreteTypeMunger methodCtorMunger) {
      ResolvedTypeMunger rtMunger = methodCtorMunger.getMunger();
      ResolvedMember lookingFor = null;
      if (rtMunger instanceof NewMethodTypeMunger) {
         NewMethodTypeMunger nftm = (NewMethodTypeMunger)rtMunger;
         lookingFor = AjcMemberMaker.interMethodDispatcher(nftm.getSignature(), methodCtorMunger.getAspectType());
      } else {
         if (!(rtMunger instanceof NewConstructorTypeMunger)) {
            throw new BCException("Not sure what this is: " + methodCtorMunger);
         }

         NewConstructorTypeMunger nftm = (NewConstructorTypeMunger)rtMunger;
         lookingFor = AjcMemberMaker.postIntroducedConstructor(methodCtorMunger.getAspectType(), nftm.getSignature().getDeclaringType(), nftm.getSignature().getParameterTypes());
      }

      String name = lookingFor.getName();
      String paramSignature = lookingFor.getParameterSignature();
      Iterator i$ = clazz.getMethodGens().iterator();

      LazyMethodGen member;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         member = (LazyMethodGen)i$.next();
      } while(!member.getName().equals(name) || !member.getParameterSignature().equals(paramSignature));

      return member;
   }

   private boolean weaveAtFieldRepeatedly(List decaFs, List itdFields, List reportedErrors) {
      boolean isChanged = false;
      Iterator iter = itdFields.iterator();

      while(iter.hasNext()) {
         BcelTypeMunger fieldMunger = (BcelTypeMunger)iter.next();
         ResolvedMember itdIsActually = fieldMunger.getSignature();
         Set worthRetrying = new LinkedHashSet();
         boolean modificationOccured = false;
         Iterator iter2 = decaFs.iterator();

         while(iter2.hasNext()) {
            DeclareAnnotation decaF = (DeclareAnnotation)iter2.next();
            if (decaF.matches(itdIsActually, this.world)) {
               LazyMethodGen annotationHolder;
               if (decaF.isRemover()) {
                  annotationHolder = this.locateAnnotationHolderForFieldMunger(this.clazz, fieldMunger);
                  if (annotationHolder.hasAnnotation(decaF.getAnnotationType())) {
                     isChanged = true;
                     annotationHolder.removeAnnotation(decaF.getAnnotationType());
                     AsmRelationshipProvider.addDeclareAnnotationRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), itdIsActually.getSourceLocation(), true);
                  } else {
                     worthRetrying.add(decaF);
                  }
               } else {
                  annotationHolder = this.locateAnnotationHolderForFieldMunger(this.clazz, fieldMunger);
                  if (!this.doesAlreadyHaveAnnotation(annotationHolder, itdIsActually, decaF, reportedErrors)) {
                     annotationHolder.addAnnotation(decaF.getAnnotation());
                     AsmRelationshipProvider.addDeclareAnnotationRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), itdIsActually.getSourceLocation(), false);
                     isChanged = true;
                     modificationOccured = true;
                  }
               }
            } else if (!decaF.isStarredAnnotationPattern()) {
               worthRetrying.add(decaF);
            }
         }

         while(!worthRetrying.isEmpty() && modificationOccured) {
            modificationOccured = false;
            List forRemoval = new ArrayList();
            Iterator iter2 = worthRetrying.iterator();

            while(iter2.hasNext()) {
               DeclareAnnotation decaF = (DeclareAnnotation)iter2.next();
               if (decaF.matches(itdIsActually, this.world)) {
                  LazyMethodGen annotationHolder;
                  if (decaF.isRemover()) {
                     annotationHolder = this.locateAnnotationHolderForFieldMunger(this.clazz, fieldMunger);
                     if (annotationHolder.hasAnnotation(decaF.getAnnotationType())) {
                        isChanged = true;
                        annotationHolder.removeAnnotation(decaF.getAnnotationType());
                        AsmRelationshipProvider.addDeclareAnnotationRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), itdIsActually.getSourceLocation(), true);
                        forRemoval.add(decaF);
                     }
                  } else {
                     annotationHolder = this.locateAnnotationHolderForFieldMunger(this.clazz, fieldMunger);
                     if (!this.doesAlreadyHaveAnnotation(annotationHolder, itdIsActually, decaF, reportedErrors)) {
                        annotationHolder.addAnnotation(decaF.getAnnotation());
                        AsmRelationshipProvider.addDeclareAnnotationRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), itdIsActually.getSourceLocation(), false);
                        isChanged = true;
                        modificationOccured = true;
                        forRemoval.add(decaF);
                     }
                  }
               }
            }

            worthRetrying.removeAll(forRemoval);
         }
      }

      return isChanged;
   }

   private boolean weaveAtMethodOnITDSRepeatedly(List decaMCs, List itdsForMethodAndConstructor, List reportedErrors) {
      boolean isChanged = false;
      AsmManager asmManager = this.world.getModelAsAsmManager();
      Iterator i$ = itdsForMethodAndConstructor.iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger methodctorMunger = (ConcreteTypeMunger)i$.next();
         ResolvedMember unMangledInterMethod = methodctorMunger.getSignature();
         List worthRetrying = new ArrayList();
         boolean modificationOccured = false;
         Iterator iter2 = decaMCs.iterator();

         while(iter2.hasNext()) {
            DeclareAnnotation decaMC = (DeclareAnnotation)iter2.next();
            if (decaMC.matches(unMangledInterMethod, this.world)) {
               LazyMethodGen annotationHolder = this.locateAnnotationHolderForMethodCtorMunger(this.clazz, methodctorMunger);
               if (annotationHolder != null && !this.doesAlreadyHaveAnnotation(annotationHolder, unMangledInterMethod, decaMC, reportedErrors)) {
                  annotationHolder.addAnnotation(decaMC.getAnnotation());
                  isChanged = true;
                  AsmRelationshipProvider.addDeclareAnnotationRelationship(asmManager, decaMC.getSourceLocation(), unMangledInterMethod.getSourceLocation(), false);
                  this.reportMethodCtorWeavingMessage(this.clazz, unMangledInterMethod, decaMC, -1);
                  modificationOccured = true;
               }
            } else if (!decaMC.isStarredAnnotationPattern()) {
               worthRetrying.add(decaMC);
            }
         }

         label56:
         while(!worthRetrying.isEmpty() && modificationOccured) {
            modificationOccured = false;
            List forRemoval = new ArrayList();
            Iterator iter2 = worthRetrying.iterator();

            while(true) {
               while(true) {
                  if (!iter2.hasNext()) {
                     continue label56;
                  }

                  DeclareAnnotation decaMC = (DeclareAnnotation)iter2.next();
                  if (!decaMC.matches(unMangledInterMethod, this.world)) {
                     break;
                  }

                  LazyMethodGen annotationHolder = this.locateAnnotationHolderForFieldMunger(this.clazz, methodctorMunger);
                  if (!this.doesAlreadyHaveAnnotation(annotationHolder, unMangledInterMethod, decaMC, reportedErrors)) {
                     annotationHolder.addAnnotation(decaMC.getAnnotation());
                     unMangledInterMethod.addAnnotation(decaMC.getAnnotation());
                     AsmRelationshipProvider.addDeclareAnnotationRelationship(asmManager, decaMC.getSourceLocation(), unMangledInterMethod.getSourceLocation(), false);
                     isChanged = true;
                     modificationOccured = true;
                     forRemoval.add(decaMC);
                     break;
                  }
               }

               worthRetrying.removeAll(forRemoval);
            }
         }
      }

      return isChanged;
   }

   private boolean dontAddTwice(DeclareAnnotation decaF, AnnotationAJ[] dontAddMeTwice) {
      AnnotationAJ[] arr$ = dontAddMeTwice;
      int len$ = dontAddMeTwice.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         AnnotationAJ ann = arr$[i$];
         if (ann != null && decaF.getAnnotation().getTypeName().equals(ann.getTypeName())) {
            return true;
         }
      }

      return false;
   }

   private AnnotationAJ[] removeFromAnnotationsArray(AnnotationAJ[] annotations, AnnotationAJ annotation) {
      for(int i = 0; i < annotations.length; ++i) {
         if (annotations[i] != null && annotation.getTypeName().equals(annotations[i].getTypeName())) {
            AnnotationAJ[] newArray = new AnnotationAJ[annotations.length - 1];
            int index = 0;

            for(int j = 0; j < annotations.length; ++j) {
               if (j != i) {
                  newArray[index++] = annotations[j];
               }
            }

            return newArray;
         }
      }

      return annotations;
   }

   private boolean weaveDeclareAtField(LazyClassGen clazz) {
      List reportedProblems = new ArrayList();
      List allDecafs = this.world.getDeclareAnnotationOnFields();
      if (allDecafs.isEmpty()) {
         return false;
      } else {
         boolean typeIsChanged = false;
         List relevantItdFields = this.getITDSubset(clazz, ResolvedTypeMunger.Field);
         if (relevantItdFields != null) {
            typeIsChanged = this.weaveAtFieldRepeatedly(allDecafs, relevantItdFields, reportedProblems);
         }

         List decafs = this.getMatchingSubset(allDecafs, clazz.getType());
         if (decafs.isEmpty()) {
            return typeIsChanged;
         } else {
            List fields = clazz.getFieldGens();
            if (fields != null) {
               Set unusedDecafs = new HashSet();
               unusedDecafs.addAll(decafs);
               Iterator i$ = fields.iterator();

               label102:
               while(true) {
                  BcelField field;
                  do {
                     if (!i$.hasNext()) {
                        this.checkUnusedDeclareAts(unusedDecafs, true);
                        return typeIsChanged;
                     }

                     field = (BcelField)i$.next();
                  } while(field.getName().startsWith("ajc$"));

                  Set worthRetrying = new LinkedHashSet();
                  boolean modificationOccured = false;
                  AnnotationAJ[] dontAddMeTwice = field.getAnnotations();
                  Iterator i$ = decafs.iterator();

                  while(true) {
                     while(true) {
                        while(i$.hasNext()) {
                           DeclareAnnotation decaf = (DeclareAnnotation)i$.next();
                           if (decaf.getAnnotation() == null) {
                              return false;
                           }

                           if (decaf.matches(field, this.world)) {
                              if (decaf.isRemover()) {
                                 AnnotationAJ annotation = decaf.getAnnotation();
                                 if (field.hasAnnotation(annotation.getType())) {
                                    typeIsChanged = true;
                                    field.removeAnnotation(annotation);
                                    AsmRelationshipProvider.addDeclareAnnotationFieldRelationship(this.world.getModelAsAsmManager(), decaf.getSourceLocation(), clazz.getName(), field, true);
                                    this.reportFieldAnnotationWeavingMessage(clazz, field, decaf, true);
                                    dontAddMeTwice = this.removeFromAnnotationsArray(dontAddMeTwice, annotation);
                                 } else {
                                    worthRetrying.add(decaf);
                                 }

                                 unusedDecafs.remove(decaf);
                              } else {
                                 if (!this.dontAddTwice(decaf, dontAddMeTwice)) {
                                    if (this.doesAlreadyHaveAnnotation(field, decaf, reportedProblems, true)) {
                                       unusedDecafs.remove(decaf);
                                       continue;
                                    }

                                    field.addAnnotation(decaf.getAnnotation());
                                 }

                                 AsmRelationshipProvider.addDeclareAnnotationFieldRelationship(this.world.getModelAsAsmManager(), decaf.getSourceLocation(), clazz.getName(), field, false);
                                 this.reportFieldAnnotationWeavingMessage(clazz, field, decaf, false);
                                 typeIsChanged = true;
                                 modificationOccured = true;
                                 unusedDecafs.remove(decaf);
                              }
                           } else if (!decaf.isStarredAnnotationPattern() || decaf.isRemover()) {
                              worthRetrying.add(decaf);
                           }
                        }

                        while(!worthRetrying.isEmpty() && modificationOccured) {
                           modificationOccured = false;
                           List forRemoval = new ArrayList();
                           Iterator iter = worthRetrying.iterator();

                           while(iter.hasNext()) {
                              DeclareAnnotation decaF = (DeclareAnnotation)iter.next();
                              if (decaF.matches(field, this.world)) {
                                 if (decaF.isRemover()) {
                                    AnnotationAJ annotation = decaF.getAnnotation();
                                    if (field.hasAnnotation(annotation.getType())) {
                                       modificationOccured = true;
                                       typeIsChanged = true;
                                       forRemoval.add(decaF);
                                       field.removeAnnotation(annotation);
                                       AsmRelationshipProvider.addDeclareAnnotationFieldRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), clazz.getName(), field, true);
                                       this.reportFieldAnnotationWeavingMessage(clazz, field, decaF, true);
                                    }
                                 } else {
                                    unusedDecafs.remove(decaF);
                                    if (!this.doesAlreadyHaveAnnotation(field, decaF, reportedProblems, true)) {
                                       field.addAnnotation(decaF.getAnnotation());
                                       AsmRelationshipProvider.addDeclareAnnotationFieldRelationship(this.world.getModelAsAsmManager(), decaF.getSourceLocation(), clazz.getName(), field, false);
                                       modificationOccured = true;
                                       typeIsChanged = true;
                                       forRemoval.add(decaF);
                                    }
                                 }
                              }
                           }

                           worthRetrying.removeAll(forRemoval);
                        }
                        continue label102;
                     }
                  }
               }
            } else {
               return typeIsChanged;
            }
         }
      }
   }

   private void checkUnusedDeclareAts(Set unusedDecaTs, boolean isDeclareAtField) {
      Iterator i$ = unusedDecaTs.iterator();

      while(true) {
         DeclareAnnotation declA;
         boolean shouldCheck;
         ExactTypePattern exactTypePattern;
         do {
            if (!i$.hasNext()) {
               return;
            }

            declA = (DeclareAnnotation)i$.next();
            shouldCheck = declA.isExactPattern() || declA.getSignaturePattern().getExactDeclaringTypes().size() != 0;
            if (shouldCheck && declA.getKind() != DeclareAnnotation.AT_CONSTRUCTOR) {
               if (declA.getSignaturePattern().isMatchOnAnyName()) {
                  shouldCheck = false;
               } else {
                  List declaringTypePatterns = declA.getSignaturePattern().getExactDeclaringTypes();
                  if (declaringTypePatterns.size() == 0) {
                     shouldCheck = false;
                  } else {
                     Iterator i$ = declaringTypePatterns.iterator();

                     while(i$.hasNext()) {
                        exactTypePattern = (ExactTypePattern)i$.next();
                        if (exactTypePattern.isIncludeSubtypes()) {
                           shouldCheck = false;
                           break;
                        }
                     }
                  }
               }
            }
         } while(!shouldCheck);

         boolean itdMatch = false;
         List lst = this.clazz.getType().getInterTypeMungers();
         Iterator iterator = lst.iterator();

         while(iterator.hasNext() && !itdMatch) {
            ConcreteTypeMunger element = (ConcreteTypeMunger)iterator.next();
            if (element.getMunger() instanceof NewFieldTypeMunger) {
               NewFieldTypeMunger nftm = (NewFieldTypeMunger)element.getMunger();
               itdMatch = declA.matches(nftm.getSignature(), this.world);
            } else if (element.getMunger() instanceof NewMethodTypeMunger) {
               NewMethodTypeMunger nmtm = (NewMethodTypeMunger)element.getMunger();
               itdMatch = declA.matches(nmtm.getSignature(), this.world);
            } else if (element.getMunger() instanceof NewConstructorTypeMunger) {
               NewConstructorTypeMunger nctm = (NewConstructorTypeMunger)element.getMunger();
               itdMatch = declA.matches(nctm.getSignature(), this.world);
            }
         }

         if (!itdMatch) {
            exactTypePattern = null;
            Message message;
            if (isDeclareAtField) {
               message = new Message("The field '" + declA.getSignaturePattern().toString() + "' does not exist", declA.getSourceLocation(), true);
            } else {
               message = new Message("The method '" + declA.getSignaturePattern().toString() + "' does not exist", declA.getSourceLocation(), true);
            }

            this.world.getMessageHandler().handleMessage(message);
         }
      }
   }

   private void reportFieldAnnotationWeavingMessage(LazyClassGen clazz, BcelField theField, DeclareAnnotation decaf, boolean isRemove) {
      if (!this.getWorld().getMessageHandler().isIgnoring(IMessage.WEAVEINFO)) {
         this.world.getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(isRemove ? WeaveMessage.WEAVEMESSAGE_REMOVES_ANNOTATION : WeaveMessage.WEAVEMESSAGE_ANNOTATES, new String[]{theField.getFieldAsIs().toString() + "' of type '" + clazz.getName(), clazz.getFileName(), decaf.getAnnotationString(), "field", decaf.getAspect().toString(), Utility.beautifyLocation(decaf.getSourceLocation())}));
      }

   }

   private boolean doesAlreadyHaveAnnotation(ResolvedMember rm, DeclareAnnotation deca, List reportedProblems, boolean reportError) {
      if (rm.hasAnnotation(deca.getAnnotationType())) {
         if (reportError && this.world.getLint().elementAlreadyAnnotated.isEnabled()) {
            Integer uniqueID = new Integer(rm.hashCode() * deca.hashCode());
            if (!reportedProblems.contains(uniqueID)) {
               reportedProblems.add(uniqueID);
               this.world.getLint().elementAlreadyAnnotated.signal(new String[]{rm.toString(), deca.getAnnotationType().toString()}, rm.getSourceLocation(), new ISourceLocation[]{deca.getSourceLocation()});
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean doesAlreadyHaveAnnotation(LazyMethodGen rm, ResolvedMember itdfieldsig, DeclareAnnotation deca, List reportedProblems) {
      if (rm != null && rm.hasAnnotation(deca.getAnnotationType())) {
         if (this.world.getLint().elementAlreadyAnnotated.isEnabled()) {
            Integer uniqueID = new Integer(rm.hashCode() * deca.hashCode());
            if (!reportedProblems.contains(uniqueID)) {
               reportedProblems.add(uniqueID);
               reportedProblems.add(new Integer(itdfieldsig.hashCode() * deca.hashCode()));
               this.world.getLint().elementAlreadyAnnotated.signal(new String[]{itdfieldsig.toString(), deca.getAnnotationType().toString()}, rm.getSourceLocation(), new ISourceLocation[]{deca.getSourceLocation()});
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private Set findAspectsForMungers(LazyMethodGen mg) {
      Set aspectsAffectingType = new HashSet();
      Iterator i$ = mg.matchedShadows.iterator();

      while(i$.hasNext()) {
         BcelShadow shadow = (BcelShadow)i$.next();
         Iterator i$ = shadow.getMungers().iterator();

         while(i$.hasNext()) {
            ShadowMunger munger = (ShadowMunger)i$.next();
            if (munger instanceof BcelAdvice) {
               BcelAdvice bcelAdvice = (BcelAdvice)munger;
               if (bcelAdvice.getConcreteAspect() != null) {
                  aspectsAffectingType.add(bcelAdvice.getConcreteAspect().getSignature());
               }
            }
         }
      }

      return aspectsAffectingType;
   }

   private boolean inlineSelfConstructors(List methodGens, List recursiveCtors) {
      boolean inlinedSomething = false;
      List newRecursiveCtors = new ArrayList();
      Iterator i$ = methodGens.iterator();

      while(i$.hasNext()) {
         LazyMethodGen methodGen = (LazyMethodGen)i$.next();
         if (methodGen.getName().equals("<init>")) {
            InstructionHandle ih = this.findSuperOrThisCall(methodGen);
            if (ih != null && this.isThisCall(ih)) {
               LazyMethodGen donor = this.getCalledMethod(ih);
               if (donor.equals(methodGen)) {
                  newRecursiveCtors.add(donor);
               } else if (!recursiveCtors.contains(donor)) {
                  inlineMethod(donor, methodGen, ih);
                  inlinedSomething = true;
               }
            }
         }
      }

      recursiveCtors.addAll(newRecursiveCtors);
      return inlinedSomething;
   }

   private void positionAndImplement(List initializationShadows) {
      Iterator i$ = initializationShadows.iterator();

      while(i$.hasNext()) {
         BcelShadow s = (BcelShadow)i$.next();
         this.positionInitializationShadow(s);
         s.implement();
      }

   }

   private void positionInitializationShadow(BcelShadow s) {
      LazyMethodGen mg = s.getEnclosingMethod();
      InstructionHandle call = this.findSuperOrThisCall(mg);
      InstructionList body = mg.getBody();
      ShadowRange r = new ShadowRange(body);
      r.associateWithShadow(s);
      if (s.getKind() == Shadow.PreInitialization) {
         r.associateWithTargets(Range.genStart(body, body.getStart().getNext()), Range.genEnd(body, call.getPrev()));
      } else {
         r.associateWithTargets(Range.genStart(body, call.getNext()), Range.genEnd(body));
      }

   }

   private boolean isThisCall(InstructionHandle ih) {
      InvokeInstruction inst = (InvokeInstruction)ih.getInstruction();
      return inst.getClassName(this.cpg).equals(this.clazz.getName());
   }

   public static void inlineMethod(LazyMethodGen donor, LazyMethodGen recipient, InstructionHandle call) {
      InstructionFactory fact = recipient.getEnclosingClass().getFactory();
      IntMap frameEnv = new IntMap();
      InstructionList argumentStores = genArgumentStores(donor, recipient, frameEnv, fact);
      InstructionList inlineInstructions = genInlineInstructions(donor, recipient, frameEnv, fact, false);
      inlineInstructions.insert(argumentStores);
      recipient.getBody().append(call, inlineInstructions);
      Utility.deleteInstruction(call, recipient);
   }

   public static void transformSynchronizedMethod(LazyMethodGen synchronizedMethod) {
      if (trace.isTraceEnabled()) {
         trace.enter("transformSynchronizedMethod", synchronizedMethod);
      }

      InstructionFactory fact = synchronizedMethod.getEnclosingClass().getFactory();
      InstructionList body = synchronizedMethod.getBody();
      InstructionList prepend = new InstructionList();
      Type enclosingClassType = BcelWorld.makeBcelType((UnresolvedType)synchronizedMethod.getEnclosingClass().getType());
      InstructionHandle element;
      InstructionHandle monitorExitBlockStart;
      Type classType;
      InstructionList finallyBlock;
      InstructionHandle element;
      InstructionHandle monitorExitBlockStart;
      if (synchronizedMethod.isStatic()) {
         if (synchronizedMethod.getEnclosingClass().isAtLeastJava5()) {
            int slotForLockObject = synchronizedMethod.allocateLocal(enclosingClassType);
            prepend.append(fact.createConstant(enclosingClassType));
            prepend.append(InstructionFactory.createDup(1));
            prepend.append((Instruction)InstructionFactory.createStore(enclosingClassType, slotForLockObject));
            prepend.append(InstructionFactory.MONITORENTER);
            InstructionList finallyBlock = new InstructionList();
            finallyBlock.append((Instruction)InstructionFactory.createLoad(Type.getType(Class.class), slotForLockObject));
            finallyBlock.append(InstructionConstants.MONITOREXIT);
            finallyBlock.append(InstructionConstants.ATHROW);
            InstructionHandle walker = body.getStart();

            ArrayList rets;
            for(rets = new ArrayList(); walker != null; walker = walker.getNext()) {
               if (walker.getInstruction().isReturnInstruction()) {
                  rets.add(walker);
               }
            }

            if (!rets.isEmpty()) {
               Iterator iter = rets.iterator();

               while(iter.hasNext()) {
                  element = (InstructionHandle)iter.next();
                  InstructionList monitorExitBlock = new InstructionList();
                  monitorExitBlock.append((Instruction)InstructionFactory.createLoad(enclosingClassType, slotForLockObject));
                  monitorExitBlock.append(InstructionConstants.MONITOREXIT);
                  monitorExitBlockStart = body.insert(element, monitorExitBlock);
                  Iterator i$ = element.getTargetersCopy().iterator();

                  while(i$.hasNext()) {
                     InstructionTargeter targeter = (InstructionTargeter)i$.next();
                     if (!(targeter instanceof LocalVariableTag) && !(targeter instanceof LineNumberTag)) {
                        if (!(targeter instanceof InstructionBranch)) {
                           throw new BCException("Unexpected targeter encountered during transform: " + targeter);
                        }

                        targeter.updateTarget(element, monitorExitBlockStart);
                     }
                  }
               }
            }

            InstructionHandle finallyStart = finallyBlock.getStart();
            element = body.getStart();
            element = body.getEnd();
            body.insert(body.getStart(), prepend);
            synchronizedMethod.getBody().append(finallyBlock);
            synchronizedMethod.addExceptionHandler(element, element, finallyStart, (ObjectType)null, false);
            synchronizedMethod.addExceptionHandler(finallyStart, finallyStart.getNext(), finallyStart, (ObjectType)null, false);
         } else {
            classType = BcelWorld.makeBcelType((UnresolvedType)synchronizedMethod.getEnclosingClass().getType());
            Type clazzType = Type.getType(Class.class);
            finallyBlock = new InstructionList();
            finallyBlock.append(InstructionFactory.createDup(1));
            int slotForThis = synchronizedMethod.allocateLocal(classType);
            finallyBlock.append((Instruction)InstructionFactory.createStore(clazzType, slotForThis));
            finallyBlock.append(InstructionFactory.MONITORENTER);
            String fieldname = synchronizedMethod.getEnclosingClass().allocateField("class$");
            FieldGen f = new FieldGen(10, Type.getType(Class.class), fieldname, synchronizedMethod.getEnclosingClass().getConstantPool());
            synchronizedMethod.getEnclosingClass().addField(f, (ISourceLocation)null);
            String name = synchronizedMethod.getEnclosingClass().getName();
            prepend.append((Instruction)fact.createGetStatic(name, fieldname, Type.getType(Class.class)));
            prepend.append(InstructionFactory.createDup(1));
            prepend.append(InstructionFactory.createBranchInstruction((short)199, finallyBlock.getStart()));
            prepend.append(InstructionFactory.POP);
            prepend.append(fact.createConstant(name));
            monitorExitBlockStart = prepend.getEnd();
            prepend.append((Instruction)fact.createInvoke("java.lang.Class", "forName", clazzType, new Type[]{Type.getType(String.class)}, (short)184));
            monitorExitBlockStart = prepend.getEnd();
            prepend.append(InstructionFactory.createDup(1));
            prepend.append((Instruction)fact.createPutStatic(synchronizedMethod.getEnclosingClass().getType().getName(), fieldname, Type.getType(Class.class)));
            prepend.append(InstructionFactory.createBranchInstruction((short)167, finallyBlock.getStart()));
            InstructionList catchBlockForLiteralLoadingFail = new InstructionList();
            catchBlockForLiteralLoadingFail.append(fact.createNew((ObjectType)Type.getType(NoClassDefFoundError.class)));
            catchBlockForLiteralLoadingFail.append(InstructionFactory.createDup_1(1));
            catchBlockForLiteralLoadingFail.append(InstructionFactory.SWAP);
            catchBlockForLiteralLoadingFail.append((Instruction)fact.createInvoke("java.lang.Throwable", "getMessage", Type.getType(String.class), new Type[0], (short)182));
            catchBlockForLiteralLoadingFail.append((Instruction)fact.createInvoke("java.lang.NoClassDefFoundError", "<init>", Type.VOID, new Type[]{Type.getType(String.class)}, (short)183));
            catchBlockForLiteralLoadingFail.append(InstructionFactory.ATHROW);
            InstructionHandle catchBlockStart = catchBlockForLiteralLoadingFail.getStart();
            prepend.append(catchBlockForLiteralLoadingFail);
            prepend.append(finallyBlock);
            InstructionList finallyBlock = new InstructionList();
            finallyBlock.append((Instruction)InstructionFactory.createLoad(Type.getType(Class.class), slotForThis));
            finallyBlock.append(InstructionConstants.MONITOREXIT);
            finallyBlock.append(InstructionConstants.ATHROW);
            InstructionHandle walker = body.getStart();

            ArrayList rets;
            for(rets = new ArrayList(); walker != null; walker = walker.getNext()) {
               if (walker.getInstruction().isReturnInstruction()) {
                  rets.add(walker);
               }
            }

            InstructionHandle ret;
            if (rets.size() > 0) {
               Iterator i$ = rets.iterator();

               while(i$.hasNext()) {
                  ret = (InstructionHandle)i$.next();
                  InstructionList monitorExitBlock = new InstructionList();
                  monitorExitBlock.append((Instruction)InstructionFactory.createLoad(classType, slotForThis));
                  monitorExitBlock.append(InstructionConstants.MONITOREXIT);
                  InstructionHandle monitorExitBlockStart = body.insert(ret, monitorExitBlock);
                  Iterator i$ = ret.getTargetersCopy().iterator();

                  while(i$.hasNext()) {
                     InstructionTargeter targeter = (InstructionTargeter)i$.next();
                     if (!(targeter instanceof LocalVariableTag) && !(targeter instanceof LineNumberTag)) {
                        if (!(targeter instanceof InstructionBranch)) {
                           throw new BCException("Unexpected targeter encountered during transform: " + targeter);
                        }

                        targeter.updateTarget(ret, monitorExitBlockStart);
                     }
                  }
               }
            }

            InstructionHandle finallyStart = finallyBlock.getStart();
            ret = body.getStart();
            InstructionHandle catchPosition = body.getEnd();
            body.insert(body.getStart(), prepend);
            synchronizedMethod.getBody().append(finallyBlock);
            synchronizedMethod.addExceptionHandler(ret, catchPosition, finallyStart, (ObjectType)null, false);
            synchronizedMethod.addExceptionHandler(monitorExitBlockStart, monitorExitBlockStart, catchBlockStart, (ObjectType)Type.getType(ClassNotFoundException.class), true);
            synchronizedMethod.addExceptionHandler(finallyStart, finallyStart.getNext(), finallyStart, (ObjectType)null, false);
         }
      } else {
         classType = BcelWorld.makeBcelType((UnresolvedType)synchronizedMethod.getEnclosingClass().getType());
         prepend.append((Instruction)InstructionFactory.createLoad(classType, 0));
         prepend.append(InstructionFactory.createDup(1));
         int slotForThis = synchronizedMethod.allocateLocal(classType);
         prepend.append((Instruction)InstructionFactory.createStore(classType, slotForThis));
         prepend.append(InstructionFactory.MONITORENTER);
         finallyBlock = new InstructionList();
         finallyBlock.append((Instruction)InstructionFactory.createLoad(classType, slotForThis));
         finallyBlock.append(InstructionConstants.MONITOREXIT);
         finallyBlock.append(InstructionConstants.ATHROW);
         InstructionHandle walker = body.getStart();

         ArrayList rets;
         for(rets = new ArrayList(); walker != null; walker = walker.getNext()) {
            if (walker.getInstruction().isReturnInstruction()) {
               rets.add(walker);
            }
         }

         if (!rets.isEmpty()) {
            Iterator iter = rets.iterator();

            while(iter.hasNext()) {
               element = (InstructionHandle)iter.next();
               InstructionList monitorExitBlock = new InstructionList();
               monitorExitBlock.append((Instruction)InstructionFactory.createLoad(classType, slotForThis));
               monitorExitBlock.append(InstructionConstants.MONITOREXIT);
               monitorExitBlockStart = body.insert(element, monitorExitBlock);
               Iterator i$ = element.getTargetersCopy().iterator();

               while(i$.hasNext()) {
                  InstructionTargeter targeter = (InstructionTargeter)i$.next();
                  if (!(targeter instanceof LocalVariableTag) && !(targeter instanceof LineNumberTag)) {
                     if (!(targeter instanceof InstructionBranch)) {
                        throw new BCException("Unexpected targeter encountered during transform: " + targeter);
                     }

                     targeter.updateTarget(element, monitorExitBlockStart);
                  }
               }
            }
         }

         element = finallyBlock.getStart();
         element = body.getStart();
         monitorExitBlockStart = body.getEnd();
         body.insert(body.getStart(), prepend);
         synchronizedMethod.getBody().append(finallyBlock);
         synchronizedMethod.addExceptionHandler(element, monitorExitBlockStart, element, (ObjectType)null, false);
         synchronizedMethod.addExceptionHandler(element, element.getNext(), element, (ObjectType)null, false);
      }

      if (trace.isTraceEnabled()) {
         trace.exit("transformSynchronizedMethod");
      }

   }

   static InstructionList genInlineInstructions(LazyMethodGen donor, LazyMethodGen recipient, IntMap frameEnv, InstructionFactory fact, boolean keepReturns) {
      InstructionList footer = new InstructionList();
      InstructionHandle end = footer.append(InstructionConstants.NOP);
      InstructionList ret = new InstructionList();
      InstructionList sourceList = donor.getBody();
      Map srcToDest = new HashMap();
      ConstantPool donorCpg = donor.getEnclosingClass().getConstantPool();
      ConstantPool recipientCpg = recipient.getEnclosingClass().getConstantPool();
      boolean isAcrossClass = donorCpg != recipientCpg;
      BootstrapMethods bootstrapMethods = null;

      for(InstructionHandle src = sourceList.getStart(); src != null; src = src.getNext()) {
         Instruction fresh = Utility.copyInstruction(src.getInstruction());
         if (((Instruction)fresh).isConstantPoolInstruction() && isAcrossClass) {
            InstructionCP cpi = (InstructionCP)fresh;
            cpi.setIndex(recipientCpg.addConstant(donorCpg.getConstant(cpi.getIndex()), donorCpg));
         }

         Object dest;
         if (src.getInstruction() == Range.RANGEINSTRUCTION) {
            dest = ret.append(Range.RANGEINSTRUCTION);
         } else if (((Instruction)fresh).isReturnInstruction()) {
            if (keepReturns) {
               dest = ret.append((Instruction)fresh);
            } else {
               dest = ret.append(InstructionFactory.createBranchInstruction((short)167, end));
            }
         } else if (fresh instanceof InstructionBranch) {
            dest = ret.append((InstructionBranch)fresh);
         } else if (!((Instruction)fresh).isLocalVariableInstruction() && !(fresh instanceof RET)) {
            dest = ret.append((Instruction)fresh);
         } else {
            int oldIndex = ((Instruction)fresh).getIndex();
            int freshIndex;
            if (!frameEnv.hasKey(oldIndex)) {
               freshIndex = recipient.allocateLocal(2);
               frameEnv.put(oldIndex, freshIndex);
            } else {
               freshIndex = frameEnv.get(oldIndex);
            }

            if (fresh instanceof RET) {
               ((Instruction)fresh).setIndex(freshIndex);
            } else {
               fresh = ((InstructionLV)fresh).setIndexAndCopyIfNecessary(freshIndex);
            }

            dest = ret.append((Instruction)fresh);
         }

         srcToDest.put(src, dest);
      }

      Map tagMap = new HashMap();
      Map shadowMap = new HashMap();
      InstructionHandle dest = ret.getStart();

      for(InstructionHandle src = sourceList.getStart(); dest != null; src = src.getNext()) {
         Instruction inst = dest.getInstruction();
         if (inst instanceof InstructionBranch) {
            InstructionBranch branch = (InstructionBranch)inst;
            InstructionHandle oldTarget = branch.getTarget();
            InstructionHandle newTarget = (InstructionHandle)srcToDest.get(oldTarget);
            if (newTarget != null) {
               branch.setTarget(newTarget);
               if (branch instanceof InstructionSelect) {
                  InstructionSelect select = (InstructionSelect)branch;
                  InstructionHandle[] oldTargets = select.getTargets();

                  for(int k = oldTargets.length - 1; k >= 0; --k) {
                     select.setTarget(k, (InstructionHandle)srcToDest.get(oldTargets[k]));
                  }
               }
            }
         }

         Iterator tIter = src.getTargeters().iterator();

         while(tIter.hasNext()) {
            InstructionTargeter old = (InstructionTargeter)tIter.next();
            if (old instanceof Tag) {
               Tag oldTag = (Tag)old;
               Tag fresh = (Tag)tagMap.get(oldTag);
               if (fresh == null) {
                  fresh = oldTag.copy();
                  if (old instanceof LocalVariableTag) {
                     LocalVariableTag lvTag = (LocalVariableTag)old;
                     LocalVariableTag lvTagFresh = (LocalVariableTag)fresh;
                     if (lvTag.getSlot() == 0) {
                        fresh = new LocalVariableTag(lvTag.getRealType().getSignature(), "ajc$aspectInstance", frameEnv.get(lvTag.getSlot()), 0);
                     } else {
                        lvTagFresh.updateSlot(frameEnv.get(lvTag.getSlot()));
                     }
                  }

                  tagMap.put(oldTag, fresh);
               }

               dest.addTargeter((InstructionTargeter)fresh);
            } else if (old instanceof ExceptionRange) {
               ExceptionRange er = (ExceptionRange)old;
               if (er.getStart() == src) {
                  ExceptionRange freshEr = new ExceptionRange(recipient.getBody(), er.getCatchType(), er.getPriority());
                  freshEr.associateWithTargets(dest, (InstructionHandle)srcToDest.get(er.getEnd()), (InstructionHandle)srcToDest.get(er.getHandler()));
               }
            } else if (old instanceof ShadowRange) {
               ShadowRange oldRange = (ShadowRange)old;
               if (oldRange.getStart() == src) {
                  BcelShadow oldShadow = oldRange.getShadow();
                  BcelShadow freshEnclosing = oldShadow.getEnclosingShadow() == null ? null : (BcelShadow)shadowMap.get(oldShadow.getEnclosingShadow());
                  BcelShadow freshShadow = oldShadow.copyInto(recipient, freshEnclosing);
                  ShadowRange freshRange = new ShadowRange(recipient.getBody());
                  freshRange.associateWithShadow(freshShadow);
                  freshRange.associateWithTargets(dest, (InstructionHandle)srcToDest.get(oldRange.getEnd()));
                  shadowMap.put(oldShadow, freshShadow);
               }
            }
         }

         dest = dest.getNext();
      }

      if (!keepReturns) {
         ret.append(footer);
      }

      return ret;
   }

   private static InstructionList genArgumentStores(LazyMethodGen donor, LazyMethodGen recipient, IntMap frameEnv, InstructionFactory fact) {
      InstructionList ret = new InstructionList();
      int donorFramePos = 0;
      if (!donor.isStatic()) {
         int targetSlot = recipient.allocateLocal(Type.OBJECT);
         ret.insert((Instruction)InstructionFactory.createStore(Type.OBJECT, targetSlot));
         frameEnv.put(donorFramePos, targetSlot);
         ++donorFramePos;
      }

      Type[] argTypes = donor.getArgumentTypes();
      int i = 0;

      for(int len = argTypes.length; i < len; ++i) {
         Type argType = argTypes[i];
         int argSlot = recipient.allocateLocal(argType);
         ret.insert((Instruction)InstructionFactory.createStore(argType, argSlot));
         frameEnv.put(donorFramePos, argSlot);
         donorFramePos += argType.getSize();
      }

      return ret;
   }

   private LazyMethodGen getCalledMethod(InstructionHandle ih) {
      InvokeInstruction inst = (InvokeInstruction)ih.getInstruction();
      String methodName = inst.getName(this.cpg);
      String signature = inst.getSignature(this.cpg);
      return this.clazz.getLazyMethodGen(methodName, signature);
   }

   private void weaveInAddedMethods() {
      Collections.sort(this.addedLazyMethodGens, new Comparator() {
         public int compare(LazyMethodGen aa, LazyMethodGen bb) {
            int i = aa.getName().compareTo(bb.getName());
            return i != 0 ? i : aa.getSignature().compareTo(bb.getSignature());
         }
      });
      Iterator i$ = this.addedLazyMethodGens.iterator();

      while(i$.hasNext()) {
         LazyMethodGen addedMember = (LazyMethodGen)i$.next();
         this.clazz.addMethodGen(addedMember);
      }

   }

   private InstructionHandle findSuperOrThisCall(LazyMethodGen mg) {
      int depth = 1;

      for(InstructionHandle start = mg.getBody().getStart(); start != null; start = start.getNext()) {
         Instruction inst = start.getInstruction();
         if (inst.opcode == 183 && ((InvokeInstruction)inst).getName(this.cpg).equals("<init>")) {
            --depth;
            if (depth == 0) {
               return start;
            }
         } else if (inst.opcode == 187) {
            ++depth;
         }
      }

      return null;
   }

   private boolean match(LazyMethodGen mg) {
      List shadowAccumulator = new ArrayList();
      boolean isOverweaving = this.world.isOverWeaving();
      boolean startsAngly = mg.getName().charAt(0) == '<';
      if (startsAngly && mg.getName().equals("<init>")) {
         return this.matchInit(mg, shadowAccumulator);
      } else if (!this.shouldWeaveBody(mg)) {
         return false;
      } else {
         BcelShadow enclosingShadow;
         if (startsAngly && mg.getName().equals("<clinit>")) {
            enclosingShadow = BcelShadow.makeStaticInitialization(this.world, mg);
         } else if (mg.isAdviceMethod()) {
            enclosingShadow = BcelShadow.makeAdviceExecution(this.world, mg);
         } else {
            AjAttribute.EffectiveSignatureAttribute effective = mg.getEffectiveSignature();
            if (effective == null) {
               if (isOverweaving && mg.getName().startsWith("ajc$")) {
                  return false;
               }

               enclosingShadow = BcelShadow.makeMethodExecution(this.world, mg, !this.canMatchBodyShadows);
            } else {
               if (!effective.isWeaveBody()) {
                  return false;
               }

               ResolvedMember rm = effective.getEffectiveSignature();
               this.fixParameterNamesForResolvedMember(rm, mg.getMemberView());
               this.fixAnnotationsForResolvedMember(rm, mg.getMemberView());
               enclosingShadow = BcelShadow.makeShadowForMethod(this.world, mg, effective.getShadowKind(), rm);
            }
         }

         if (this.canMatchBodyShadows) {
            for(InstructionHandle h = mg.getBody().getStart(); h != null; h = h.getNext()) {
               this.match(mg, h, enclosingShadow, shadowAccumulator);
            }
         }

         if (this.canMatch(enclosingShadow.getKind()) && (mg.getName().charAt(0) != 'a' || !mg.getName().startsWith("ajc$interFieldInit")) && this.match(enclosingShadow, shadowAccumulator)) {
            enclosingShadow.init();
         }

         mg.matchedShadows = shadowAccumulator;
         return !shadowAccumulator.isEmpty();
      }
   }

   private boolean matchInit(LazyMethodGen mg, List shadowAccumulator) {
      InstructionHandle superOrThisCall = this.findSuperOrThisCall(mg);
      if (superOrThisCall == null) {
         return false;
      } else {
         BcelShadow enclosingShadow = BcelShadow.makeConstructorExecution(this.world, mg, superOrThisCall);
         if (mg.getEffectiveSignature() != null) {
            enclosingShadow.setMatchingSignature(mg.getEffectiveSignature().getEffectiveSignature());
         }

         boolean beforeSuperOrThisCall = true;
         InstructionHandle curr;
         if (this.shouldWeaveBody(mg)) {
            if (this.canMatchBodyShadows) {
               for(curr = mg.getBody().getStart(); curr != null; curr = curr.getNext()) {
                  if (curr == superOrThisCall) {
                     beforeSuperOrThisCall = false;
                  } else {
                     this.match(mg, curr, beforeSuperOrThisCall ? null : enclosingShadow, shadowAccumulator);
                  }
               }
            }

            if (this.canMatch(Shadow.ConstructorExecution)) {
               this.match(enclosingShadow, shadowAccumulator);
            }
         }

         if (!this.isThisCall(superOrThisCall)) {
            curr = enclosingShadow.getRange().getStart();
            Iterator i = this.addedSuperInitializersAsList.iterator();

            label54:
            while(true) {
               BcelShadow initShadow;
               InstructionList inits;
               do {
                  if (!i.hasNext()) {
                     InstructionList inits = this.genInitInstructions(this.addedThisInitializers, false);
                     enclosingShadow.getRange().insert(inits, Range.OutsideBefore);
                     break label54;
                  }

                  IfaceInitList l = (IfaceInitList)i.next();
                  Member ifaceInitSig = AjcMemberMaker.interfaceConstructor(l.onType);
                  initShadow = BcelShadow.makeIfaceInitialization(this.world, mg, ifaceInitSig);
                  inits = this.genInitInstructions(l.list, false);
               } while(!this.match(initShadow, shadowAccumulator) && inits.isEmpty());

               initShadow.initIfaceInitializer(curr);
               initShadow.getRange().insert(inits, Range.OutsideBefore);
            }
         }

         boolean addedInitialization = this.match(BcelShadow.makeUnfinishedInitialization(this.world, mg), this.initializationShadows);
         addedInitialization |= this.match(BcelShadow.makeUnfinishedPreinitialization(this.world, mg), this.initializationShadows);
         mg.matchedShadows = shadowAccumulator;
         return addedInitialization || !shadowAccumulator.isEmpty();
      }
   }

   private boolean shouldWeaveBody(LazyMethodGen mg) {
      if (mg.isBridgeMethod()) {
         return false;
      } else if (mg.isAjSynthetic()) {
         return mg.getName().equals("<clinit>");
      } else {
         AjAttribute.EffectiveSignatureAttribute a = mg.getEffectiveSignature();
         return a != null ? a.isWeaveBody() : true;
      }
   }

   private InstructionList genInitInstructions(List list, boolean isStatic) {
      list = PartialOrder.sort(list);
      if (list == null) {
         throw new BCException("circularity in inter-types");
      } else {
         InstructionList ret = new InstructionList();

         ResolvedMember initMethod;
         for(Iterator i$ = list.iterator(); i$.hasNext(); ret.append(Utility.createInvoke(this.fact, this.world, initMethod))) {
            ConcreteTypeMunger cmunger = (ConcreteTypeMunger)i$.next();
            NewFieldTypeMunger munger = (NewFieldTypeMunger)cmunger.getMunger();
            initMethod = munger.getInitMethod(cmunger.getAspectType());
            if (!isStatic) {
               ret.append((Instruction)InstructionConstants.ALOAD_0);
            }
         }

         return ret;
      }
   }

   private void match(LazyMethodGen mg, InstructionHandle ih, BcelShadow enclosingShadow, List shadowAccumulator) {
      Instruction i = ih.getInstruction();
      if (this.canMatch(Shadow.ExceptionHandler) && !Range.isRangeHandle(ih)) {
         Set targeters = ih.getTargetersCopy();
         Iterator i$ = targeters.iterator();

         label131:
         while(true) {
            while(true) {
               ExceptionRange er;
               do {
                  InstructionTargeter t;
                  do {
                     if (!i$.hasNext()) {
                        break label131;
                     }

                     t = (InstructionTargeter)i$.next();
                  } while(!(t instanceof ExceptionRange));

                  er = (ExceptionRange)t;
               } while(er.getCatchType() == null);

               if (this.isInitFailureHandler(ih)) {
                  return;
               }

               if (!ih.getInstruction().isStoreInstruction() && ih.getInstruction().getOpcode() != 0) {
                  mg.getBody().insert(ih, InstructionConstants.NOP);
                  InstructionHandle newNOP = ih.getPrev();
                  er.updateTarget(ih, newNOP, mg.getBody());
                  Iterator i$ = targeters.iterator();

                  while(i$.hasNext()) {
                     InstructionTargeter t2 = (InstructionTargeter)i$.next();
                     newNOP.addTargeter(t2);
                  }

                  ih.removeAllTargeters();
                  this.match(BcelShadow.makeExceptionHandler(this.world, er, mg, newNOP, enclosingShadow), shadowAccumulator);
               } else {
                  this.match(BcelShadow.makeExceptionHandler(this.world, er, mg, ih, enclosingShadow), shadowAccumulator);
               }
            }
         }
      }

      if (i instanceof FieldInstruction && (this.canMatch(Shadow.FieldGet) || this.canMatch(Shadow.FieldSet))) {
         FieldInstruction fi = (FieldInstruction)i;
         if (fi.opcode != 181 && fi.opcode != 179) {
            if (this.canMatch(Shadow.FieldGet)) {
               this.matchGetInstruction(mg, ih, enclosingShadow, shadowAccumulator);
            }
         } else {
            InstructionHandle prevHandle = ih.getPrev();
            Instruction prevI = prevHandle.getInstruction();
            if (Utility.isConstantPushInstruction(prevI)) {
               Member field = BcelWorld.makeFieldJoinPointSignature(this.clazz, (FieldInstruction)i);
               ResolvedMember resolvedField = field.resolve(this.world);
               if (resolvedField != null && !Modifier.isFinal(resolvedField.getModifiers()) && this.canMatch(Shadow.FieldSet)) {
                  this.matchSetInstruction(mg, ih, enclosingShadow, shadowAccumulator);
               }
            } else if (this.canMatch(Shadow.FieldSet)) {
               this.matchSetInstruction(mg, ih, enclosingShadow, shadowAccumulator);
            }
         }
      } else if (i instanceof InvokeInstruction) {
         InvokeInstruction ii = (InvokeInstruction)i;
         if (ii.getMethodName(this.clazz.getConstantPool()).equals("<init>")) {
            if (this.canMatch(Shadow.ConstructorCall)) {
               this.match(BcelShadow.makeConstructorCall(this.world, mg, ih, enclosingShadow), shadowAccumulator);
            }
         } else if (ii.opcode == 183) {
            String onTypeName = ii.getClassName(this.cpg);
            if (onTypeName.equals(mg.getEnclosingClass().getName())) {
               this.matchInvokeInstruction(mg, ih, ii, enclosingShadow, shadowAccumulator);
            }
         } else if (ii.getOpcode() != 186) {
            this.matchInvokeInstruction(mg, ih, ii, enclosingShadow, shadowAccumulator);
         }
      } else {
         BcelShadow ctorCallShadow;
         if (this.world.isJoinpointArrayConstructionEnabled() && i.isArrayCreationInstruction()) {
            if (this.canMatch(Shadow.ConstructorCall)) {
               if (i.opcode == 189) {
                  ctorCallShadow = BcelShadow.makeArrayConstructorCall(this.world, mg, ih, enclosingShadow);
                  this.match(ctorCallShadow, shadowAccumulator);
               } else if (i.opcode == 188) {
                  ctorCallShadow = BcelShadow.makeArrayConstructorCall(this.world, mg, ih, enclosingShadow);
                  this.match(ctorCallShadow, shadowAccumulator);
               } else if (i instanceof MULTIANEWARRAY) {
                  ctorCallShadow = BcelShadow.makeArrayConstructorCall(this.world, mg, ih, enclosingShadow);
                  this.match(ctorCallShadow, shadowAccumulator);
               }
            }
         } else if (this.world.isJoinpointSynchronizationEnabled() && (i.getOpcode() == 194 || i.getOpcode() == 195)) {
            if (i.getOpcode() == 194) {
               ctorCallShadow = BcelShadow.makeMonitorEnter(this.world, mg, ih, enclosingShadow);
               this.match(ctorCallShadow, shadowAccumulator);
            } else {
               ctorCallShadow = BcelShadow.makeMonitorExit(this.world, mg, ih, enclosingShadow);
               this.match(ctorCallShadow, shadowAccumulator);
            }
         }
      }

   }

   private boolean isInitFailureHandler(InstructionHandle ih) {
      InstructionHandle twoInstructionsAway = ih.getNext().getNext();
      if (twoInstructionsAway.getInstruction().opcode == 179) {
         String name = ((FieldInstruction)twoInstructionsAway.getInstruction()).getFieldName(this.cpg);
         if (name.equals("ajc$initFailureCause")) {
            return true;
         }
      }

      return false;
   }

   private void matchSetInstruction(LazyMethodGen mg, InstructionHandle ih, BcelShadow enclosingShadow, List shadowAccumulator) {
      FieldInstruction fi = (FieldInstruction)ih.getInstruction();
      Member field = BcelWorld.makeFieldJoinPointSignature(this.clazz, fi);
      if (!field.getName().startsWith("ajc$")) {
         ResolvedMember resolvedField = field.resolve(this.world);
         if (resolvedField != null) {
            if (!Modifier.isFinal(resolvedField.getModifiers()) || !Utility.isConstantPushInstruction(ih.getPrev().getInstruction())) {
               if (!resolvedField.isSynthetic()) {
                  BcelShadow bs = BcelShadow.makeFieldSet(this.world, resolvedField, mg, ih, enclosingShadow);
                  String cname = fi.getClassName(this.cpg);
                  if (!resolvedField.getDeclaringType().getName().equals(cname)) {
                     bs.setActualTargetType(cname);
                  }

                  this.match(bs, shadowAccumulator);
               }
            }
         }
      }
   }

   private void matchGetInstruction(LazyMethodGen mg, InstructionHandle ih, BcelShadow enclosingShadow, List shadowAccumulator) {
      FieldInstruction fi = (FieldInstruction)ih.getInstruction();
      Member field = BcelWorld.makeFieldJoinPointSignature(this.clazz, fi);
      if (!field.getName().startsWith("ajc$")) {
         ResolvedMember resolvedField = field.resolve(this.world);
         if (resolvedField != null) {
            if (!resolvedField.isSynthetic()) {
               BcelShadow bs = BcelShadow.makeFieldGet(this.world, resolvedField, mg, ih, enclosingShadow);
               String cname = fi.getClassName(this.cpg);
               if (!resolvedField.getDeclaringType().getName().equals(cname)) {
                  bs.setActualTargetType(cname);
               }

               this.match(bs, shadowAccumulator);
            }
         }
      }
   }

   private ResolvedMember findResolvedMemberNamed(ResolvedType type, String methodName) {
      ResolvedMember[] allMethods = type.getDeclaredMethods();

      for(int i = 0; i < allMethods.length; ++i) {
         ResolvedMember member = allMethods[i];
         if (member.getName().equals(methodName)) {
            return member;
         }
      }

      return null;
   }

   private ResolvedMember findResolvedMemberNamed(ResolvedType type, String methodName, UnresolvedType[] params) {
      ResolvedMember[] allMethods = type.getDeclaredMethods();
      List candidates = new ArrayList();

      ResolvedMember candidate;
      for(int i = 0; i < allMethods.length; ++i) {
         candidate = allMethods[i];
         if (candidate.getName().equals(methodName) && candidate.getArity() == params.length) {
            candidates.add(candidate);
         }
      }

      if (candidates.size() == 0) {
         return null;
      } else if (candidates.size() == 1) {
         return (ResolvedMember)candidates.get(0);
      } else {
         Iterator i$ = candidates.iterator();

         boolean allOK;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            candidate = (ResolvedMember)i$.next();
            allOK = true;
            UnresolvedType[] candidateParams = candidate.getParameterTypes();

            for(int p = 0; p < candidateParams.length; ++p) {
               if (!candidateParams[p].getErasureSignature().equals(params[p].getErasureSignature())) {
                  allOK = false;
                  break;
               }
            }
         } while(!allOK);

         return candidate;
      }
   }

   private void fixParameterNamesForResolvedMember(ResolvedMember rm, ResolvedMember declaredSig) {
      UnresolvedType memberHostType = declaredSig.getDeclaringType();
      String methodName = declaredSig.getName();
      String[] pnames = null;
      if (rm.getKind() == Member.METHOD && !rm.isAbstract()) {
         ResolvedMember resolvedDooberry;
         if (!methodName.startsWith("ajc$inlineAccessMethod") && !methodName.startsWith("ajc$superDispatch")) {
            resolvedDooberry = AjcMemberMaker.interMethodDispatcher(rm.resolve(this.world), memberHostType).resolve(this.world);
            ResolvedMember theRealMember = this.findResolvedMemberNamed(memberHostType.resolve(this.world), resolvedDooberry.getName());
            if (theRealMember != null) {
               pnames = theRealMember.getParameterNames();
               if (pnames.length > 0 && pnames[0].equals("ajc$this_")) {
                  String[] pnames2 = new String[pnames.length - 1];
                  System.arraycopy(pnames, 1, pnames2, 0, pnames2.length);
                  pnames = pnames2;
               }
            }
         } else {
            resolvedDooberry = this.world.resolve(declaredSig);
            pnames = resolvedDooberry.getParameterNames();
         }
      }

      rm.setParameterNames(pnames);
   }

   private void fixAnnotationsForResolvedMember(ResolvedMember rm, ResolvedMember declaredSig) {
      try {
         UnresolvedType memberHostType = declaredSig.getDeclaringType();
         boolean containsKey = this.mapToAnnotationHolder.containsKey(rm);
         ResolvedMember realAnnotationHolder = (ResolvedMember)this.mapToAnnotationHolder.get(rm);
         String methodName = declaredSig.getName();
         if (!containsKey) {
            ResolvedMember realThing;
            if (rm.getKind() == Member.FIELD) {
               if (methodName.startsWith("ajc$inlineAccessField")) {
                  realAnnotationHolder = this.world.resolve(rm);
               } else {
                  realThing = AjcMemberMaker.interFieldInitializer(rm, memberHostType);
                  realAnnotationHolder = this.world.resolve(realThing);
               }
            } else if (rm.getKind() == Member.METHOD && !rm.isAbstract()) {
               if (!methodName.startsWith("ajc$inlineAccessMethod") && !methodName.startsWith("ajc$superDispatch")) {
                  realThing = AjcMemberMaker.interMethodDispatcher(rm.resolve(this.world), memberHostType).resolve(this.world);
                  realAnnotationHolder = this.findResolvedMemberNamed(memberHostType.resolve(this.world), realThing.getName(), realThing.getParameterTypes());
                  if (realAnnotationHolder == null) {
                     throw new UnsupportedOperationException("Known limitation in M4 - can't find ITD members when type variable is used as an argument and has upper bound specified");
                  }
               } else {
                  realAnnotationHolder = this.world.resolve(declaredSig);
               }
            } else if (rm.getKind() == Member.CONSTRUCTOR) {
               realThing = AjcMemberMaker.postIntroducedConstructor(memberHostType.resolve(this.world), rm.getDeclaringType(), rm.getParameterTypes());
               realAnnotationHolder = this.world.resolve(realThing);
               if (realAnnotationHolder == null) {
                  throw new UnsupportedOperationException("Known limitation in M4 - can't find ITD members when type variable is used as an argument and has upper bound specified");
               }
            }

            this.mapToAnnotationHolder.put(rm, realAnnotationHolder);
         }

         AnnotationAJ[] annotations;
         ResolvedType[] annotationTypes;
         if (realAnnotationHolder != null) {
            annotationTypes = realAnnotationHolder.getAnnotationTypes();
            annotations = realAnnotationHolder.getAnnotations();
            if (annotationTypes == null) {
               annotationTypes = ResolvedType.EMPTY_ARRAY;
            }

            if (annotations == null) {
               annotations = AnnotationAJ.EMPTY_ARRAY;
            }
         } else {
            annotations = AnnotationAJ.EMPTY_ARRAY;
            annotationTypes = ResolvedType.EMPTY_ARRAY;
         }

         rm.setAnnotations(annotations);
         rm.setAnnotationTypes(annotationTypes);
      } catch (UnsupportedOperationException var9) {
         throw var9;
      } catch (Throwable var10) {
         throw new BCException("Unexpectedly went bang when searching for annotations on " + rm, var10);
      }
   }

   private void matchInvokeInstruction(LazyMethodGen mg, InstructionHandle ih, InvokeInstruction invoke, BcelShadow enclosingShadow, List shadowAccumulator) {
      String methodName = invoke.getName(this.cpg);
      if (methodName.startsWith("ajc$")) {
         Member jpSig = this.world.makeJoinPointSignatureForMethodInvocation(this.clazz, invoke);
         ResolvedMember declaredSig = jpSig.resolve(this.world);
         if (declaredSig == null) {
            return;
         }

         if (declaredSig.getKind() == Member.FIELD) {
            Shadow.Kind kind;
            if (jpSig.getReturnType().equals(UnresolvedType.VOID)) {
               kind = Shadow.FieldSet;
            } else {
               kind = Shadow.FieldGet;
            }

            if (this.canMatch(Shadow.FieldGet) || this.canMatch(Shadow.FieldSet)) {
               this.match(BcelShadow.makeShadowForMethodCall(this.world, mg, ih, enclosingShadow, kind, declaredSig), shadowAccumulator);
            }
         } else if (!declaredSig.getName().startsWith("ajc$")) {
            if (this.canMatch(Shadow.MethodCall)) {
               this.match(BcelShadow.makeShadowForMethodCall(this.world, mg, ih, enclosingShadow, Shadow.MethodCall, declaredSig), shadowAccumulator);
            }
         } else {
            AjAttribute.EffectiveSignatureAttribute effectiveSig = declaredSig.getEffectiveSignature();
            if (effectiveSig == null) {
               return;
            }

            if (effectiveSig.isWeaveBody()) {
               return;
            }

            ResolvedMember rm = effectiveSig.getEffectiveSignature();
            this.fixParameterNamesForResolvedMember(rm, declaredSig);
            this.fixAnnotationsForResolvedMember(rm, declaredSig);
            if (this.canMatch(effectiveSig.getShadowKind())) {
               this.match(BcelShadow.makeShadowForMethodCall(this.world, mg, ih, enclosingShadow, effectiveSig.getShadowKind(), rm), shadowAccumulator);
            }
         }
      } else if (this.canMatch(Shadow.MethodCall)) {
         boolean proceed = true;
         if (this.world.isOverWeaving()) {
            String s = invoke.getClassName(mg.getConstantPool());
            if (s.length() > 4 && s.charAt(4) == 'a' && (s.equals("com.bea.core.repackaged.aspectj.runtime.internal.CFlowCounter") || s.equals("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack") || s.equals("com.bea.core.repackaged.aspectj.runtime.reflect.Factory"))) {
               proceed = false;
            } else if (methodName.equals("aspectOf")) {
               proceed = false;
            }
         }

         if (proceed) {
            this.match(BcelShadow.makeMethodCall(this.world, mg, ih, enclosingShadow), shadowAccumulator);
         }
      }

   }

   private boolean match(BcelShadow shadow, List shadowAccumulator) {
      if (captureLowLevelContext) {
         ContextToken shadowMatchToken = CompilationAndWeavingContext.enteringPhase(28, shadow);
         boolean isMatched = false;
         Shadow.Kind shadowKind = shadow.getKind();
         List candidateMungers = this.indexedShadowMungers[shadowKind.getKey()];
         if (candidateMungers != null) {
            ContextToken mungerMatchToken;
            for(Iterator i$ = candidateMungers.iterator(); i$.hasNext(); CompilationAndWeavingContext.leavingPhase(mungerMatchToken)) {
               ShadowMunger munger = (ShadowMunger)i$.next();
               mungerMatchToken = CompilationAndWeavingContext.enteringPhase(30, munger.getPointcut());
               if (munger.match(shadow, this.world)) {
                  shadow.addMunger(munger);
                  isMatched = true;
                  if (shadow.getKind() == Shadow.StaticInitialization) {
                     this.clazz.warnOnAddedStaticInitializer(shadow, munger.getSourceLocation());
                  }
               }
            }

            if (isMatched) {
               shadowAccumulator.add(shadow);
            }
         }

         CompilationAndWeavingContext.leavingPhase(shadowMatchToken);
         return isMatched;
      } else {
         boolean isMatched = false;
         Shadow.Kind shadowKind = shadow.getKind();
         List candidateMungers = this.indexedShadowMungers[shadowKind.getKey()];
         if (candidateMungers != null) {
            Iterator i$ = candidateMungers.iterator();

            while(i$.hasNext()) {
               ShadowMunger munger = (ShadowMunger)i$.next();
               if (munger.match(shadow, this.world)) {
                  shadow.addMunger(munger);
                  isMatched = true;
                  if (shadow.getKind() == Shadow.StaticInitialization) {
                     this.clazz.warnOnAddedStaticInitializer(shadow, munger.getSourceLocation());
                  }
               }
            }

            if (isMatched) {
               shadowAccumulator.add(shadow);
            }
         }

         return isMatched;
      }
   }

   private void implement(LazyMethodGen mg) {
      List shadows = mg.matchedShadows;
      if (shadows != null) {
         Iterator i$ = shadows.iterator();

         while(i$.hasNext()) {
            BcelShadow shadow = (BcelShadow)i$.next();
            ContextToken tok = CompilationAndWeavingContext.enteringPhase(29, shadow);
            shadow.implement();
            CompilationAndWeavingContext.leavingPhase(tok);
         }

         mg.getMaxLocals();
         mg.matchedShadows = null;
      }
   }

   public LazyClassGen getLazyClassGen() {
      return this.clazz;
   }

   public BcelWorld getWorld() {
      return this.world;
   }

   public void setReweavableMode(boolean mode) {
      this.inReweavableMode = mode;
   }

   public boolean getReweavableMode() {
      return this.inReweavableMode;
   }

   public String toString() {
      return "BcelClassWeaver instance for : " + this.clazz;
   }

   private static class IfaceInitList implements PartialOrder.PartialComparable {
      final ResolvedType onType;
      List list = new ArrayList();

      IfaceInitList(ResolvedType onType) {
         this.onType = onType;
      }

      public int compareTo(Object other) {
         IfaceInitList o = (IfaceInitList)other;
         if (this.onType.isAssignableFrom(o.onType)) {
            return 1;
         } else {
            return o.onType.isAssignableFrom(this.onType) ? -1 : 0;
         }
      }

      public int fallbackCompareTo(Object other) {
         return 0;
      }
   }
}
