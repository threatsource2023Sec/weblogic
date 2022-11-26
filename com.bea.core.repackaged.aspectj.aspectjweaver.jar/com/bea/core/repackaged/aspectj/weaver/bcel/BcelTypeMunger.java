package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassFormatException;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Signature;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionBranch;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InvokeInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.bridge.WeaveMessage;
import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import com.bea.core.repackaged.aspectj.bridge.context.ContextToken;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.AnnotationAJ;
import com.bea.core.repackaged.aspectj.weaver.AnnotationOnTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberUtils;
import com.bea.core.repackaged.aspectj.weaver.MethodDelegateTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NameMangler;
import com.bea.core.repackaged.aspectj.weaver.NewConstructorTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewFieldTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewMemberClassTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewMethodTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.NewParentTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.PerObjectInterfaceTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.PrivilegedAccessMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.WeaverStateInfo;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.model.AsmRelationshipProvider;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BcelTypeMunger extends ConcreteTypeMunger {
   private volatile int hashCode = 0;

   public BcelTypeMunger(ResolvedTypeMunger munger, ResolvedType aspectType) {
      super(munger, aspectType);
   }

   public String toString() {
      return "(BcelTypeMunger " + this.getMunger() + ")";
   }

   public boolean shouldOverwrite() {
      return false;
   }

   public boolean munge(BcelClassWeaver weaver) {
      ContextToken tok = CompilationAndWeavingContext.enteringPhase(31, this);
      boolean changed = false;
      boolean worthReporting = true;
      WeaverStateInfo declaringAspect;
      if (weaver.getWorld().isOverWeaving()) {
         declaringAspect = weaver.getLazyClassGen().getType().getWeaverState();
         if (declaringAspect != null && declaringAspect.isAspectAlreadyApplied(this.getAspectType())) {
            return false;
         }
      }

      if (this.munger.getKind() == ResolvedTypeMunger.Field) {
         changed = this.mungeNewField(weaver, (NewFieldTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.Method) {
         changed = this.mungeNewMethod(weaver, (NewMethodTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.InnerClass) {
         changed = this.mungeNewMemberType(weaver, (NewMemberClassTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.MethodDelegate2) {
         changed = this.mungeMethodDelegate(weaver, (MethodDelegateTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.FieldHost) {
         changed = this.mungeFieldHost(weaver, (MethodDelegateTypeMunger.FieldHostTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.PerObjectInterface) {
         changed = this.mungePerObjectInterface(weaver, (PerObjectInterfaceTypeMunger)this.munger);
         worthReporting = false;
      } else if (this.munger.getKind() == ResolvedTypeMunger.PerTypeWithinInterface) {
         changed = this.mungePerTypeWithinTransformer(weaver);
         worthReporting = false;
      } else if (this.munger.getKind() == ResolvedTypeMunger.PrivilegedAccess) {
         changed = this.mungePrivilegedAccess(weaver, (PrivilegedAccessMunger)this.munger);
         worthReporting = false;
      } else if (this.munger.getKind() == ResolvedTypeMunger.Constructor) {
         changed = this.mungeNewConstructor(weaver, (NewConstructorTypeMunger)this.munger);
      } else if (this.munger.getKind() == ResolvedTypeMunger.Parent) {
         changed = this.mungeNewParent(weaver, (NewParentTypeMunger)this.munger);
      } else {
         if (this.munger.getKind() != ResolvedTypeMunger.AnnotationOnType) {
            throw new RuntimeException("unimplemented");
         }

         changed = this.mungeNewAnnotationOnType(weaver, (AnnotationOnTypeMunger)this.munger);
         worthReporting = false;
      }

      if (changed && this.munger.changesPublicSignature()) {
         declaringAspect = weaver.getLazyClassGen().getOrCreateWeaverStateInfo(weaver.getReweavableMode());
         declaringAspect.addConcreteMunger(this);
      }

      NewParentTypeMunger parentTM;
      if (changed && worthReporting) {
         declaringAspect = null;
         AsmManager model = ((BcelWorld)this.getWorld()).getModelAsAsmManager();
         if (model != null) {
            ResolvedType declaringAspect;
            if (this.munger instanceof NewParentTypeMunger) {
               parentTM = (NewParentTypeMunger)this.munger;
               declaringAspect = parentTM.getDeclaringType();
               if (declaringAspect.isParameterizedOrGenericType()) {
                  declaringAspect = declaringAspect.getRawType();
               }

               ResolvedType thisAspect = this.getAspectType();
               AsmRelationshipProvider.addRelationship(model, weaver.getLazyClassGen().getType(), this.munger, thisAspect);
               if (!thisAspect.equals(declaringAspect)) {
                  ResolvedType target = weaver.getLazyClassGen().getType();
                  ResolvedType newParent = parentTM.getNewParent();
                  IProgramElement thisAspectNode = model.getHierarchy().findElementForType(thisAspect.getPackageName(), thisAspect.getClassName());
                  Map declareParentsMap = thisAspectNode.getDeclareParentsMap();
                  if (declareParentsMap == null) {
                     declareParentsMap = new HashMap();
                     thisAspectNode.setDeclareParentsMap((Map)declareParentsMap);
                  }

                  String tname = target.getName();
                  String pname = newParent.getName();
                  List newparents = (List)((Map)declareParentsMap).get(tname);
                  if (newparents == null) {
                     newparents = new ArrayList();
                     ((Map)declareParentsMap).put(tname, newparents);
                  }

                  ((List)newparents).add(pname);
                  AsmRelationshipProvider.addRelationship(model, weaver.getLazyClassGen().getType(), this.munger, declaringAspect);
               }
            } else {
               declaringAspect = this.getAspectType();
               AsmRelationshipProvider.addRelationship(model, weaver.getLazyClassGen().getType(), this.munger, declaringAspect);
            }
         }
      }

      if (changed && worthReporting && this.munger != null && !weaver.getWorld().getMessageHandler().isIgnoring(IMessage.WEAVEINFO)) {
         String tName = weaver.getLazyClassGen().getType().getSourceLocation().getSourceFile().getName();
         if (tName.indexOf("no debug info available") != -1) {
            tName = "no debug info available";
         } else {
            tName = this.getShortname(weaver.getLazyClassGen().getType().getSourceLocation().getSourceFile().getPath());
         }

         String fName = this.getShortname(this.getAspectType().getSourceLocation().getSourceFile().getPath());
         if (this.munger.getKind().equals(ResolvedTypeMunger.Parent)) {
            parentTM = (NewParentTypeMunger)this.munger;
            if (parentTM.isMixin()) {
               weaver.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_MIXIN, new String[]{parentTM.getNewParent().getName(), fName, weaver.getLazyClassGen().getType().getName(), tName}, weaver.getLazyClassGen().getClassName(), this.getAspectType().getName()));
            } else if (parentTM.getNewParent().isInterface()) {
               weaver.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_DECLAREPARENTSIMPLEMENTS, new String[]{weaver.getLazyClassGen().getType().getName(), tName, parentTM.getNewParent().getName(), fName}, weaver.getLazyClassGen().getClassName(), this.getAspectType().getName()));
            } else {
               weaver.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_DECLAREPARENTSEXTENDS, new String[]{weaver.getLazyClassGen().getType().getName(), tName, parentTM.getNewParent().getName(), fName}));
            }
         } else if (!this.munger.getKind().equals(ResolvedTypeMunger.FieldHost)) {
            ResolvedMember declaredSig = this.munger.getSignature();
            String fromString = fName + ":'" + declaredSig + "'";
            String kindString = this.munger.getKind().toString().toLowerCase();
            if (kindString.equals("innerclass")) {
               kindString = "member class";
               fromString = fName;
            }

            weaver.getWorld().getMessageHandler().handleMessage(WeaveMessage.constructWeavingMessage(WeaveMessage.WEAVEMESSAGE_ITD, new String[]{weaver.getLazyClassGen().getType().getName(), tName, kindString, this.getAspectType().getName(), fromString}, weaver.getLazyClassGen().getClassName(), this.getAspectType().getName()));
         }
      }

      CompilationAndWeavingContext.leavingPhase(tok);
      return changed;
   }

   private String getShortname(String path) {
      int takefrom = path.lastIndexOf(47);
      if (takefrom == -1) {
         takefrom = path.lastIndexOf(92);
      }

      return path.substring(takefrom + 1);
   }

   private boolean mungeNewAnnotationOnType(BcelClassWeaver weaver, AnnotationOnTypeMunger munger) {
      try {
         BcelAnnotation anno = (BcelAnnotation)munger.getNewAnnotation();
         weaver.getLazyClassGen().addAnnotation(anno.getBcelAnnotation());
         return true;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("DiagnosticsFor318237: The typemunger " + munger + " contains an annotation of type " + munger.getNewAnnotation().getClass().getName() + " when it should be a BcelAnnotation", var4);
      }
   }

   private boolean mungeNewParent(BcelClassWeaver weaver, NewParentTypeMunger typeTransformer) {
      LazyClassGen newParentTarget = weaver.getLazyClassGen();
      ResolvedType newParent = typeTransformer.getNewParent();
      boolean performChange = true;
      performChange = this.enforceDecpRule1_abstractMethodsImplemented(weaver, typeTransformer.getSourceLocation(), newParentTarget, newParent);
      performChange = this.enforceDecpRule2_cantExtendFinalClass(weaver, typeTransformer.getSourceLocation(), newParentTarget, newParent) && performChange;
      List methods = newParent.getMethodsWithoutIterator(false, true, false);
      Iterator i$ = methods.iterator();

      while(true) {
         ResolvedMember method;
         LazyMethodGen subMethod;
         do {
            do {
               do {
                  do {
                     do {
                        if (!i$.hasNext()) {
                           if (!performChange) {
                              return false;
                           }

                           if (newParent.isClass()) {
                              if (!this.attemptToModifySuperCalls(weaver, newParentTarget, newParent)) {
                                 return false;
                              }

                              newParentTarget.setSuperClass(newParent);
                           } else {
                              newParentTarget.addInterface(newParent, this.getSourceLocation());
                           }

                           return true;
                        }

                        method = (ResolvedMember)i$.next();
                     } while(method.getName().equals("<init>"));

                     subMethod = this.findMatchingMethod(newParentTarget, method);
                  } while(subMethod == null);
               } while(subMethod.isBridgeMethod());
            } while(subMethod.isSynthetic() && method.isSynthetic());
         } while(subMethod.isStatic() && subMethod.getName().startsWith("access$"));

         performChange = this.enforceDecpRule3_visibilityChanges(weaver, newParent, method, subMethod) && performChange;
         performChange = this.enforceDecpRule4_compatibleReturnTypes(weaver, method, subMethod) && performChange;
         performChange = this.enforceDecpRule5_cantChangeFromStaticToNonstatic(weaver, typeTransformer.getSourceLocation(), method, subMethod) && performChange;
      }
   }

   private boolean enforceDecpRule1_abstractMethodsImplemented(BcelClassWeaver weaver, ISourceLocation mungerLoc, LazyClassGen newParentTarget, ResolvedType newParent) {
      if (!newParentTarget.isAbstract() && !newParentTarget.isInterface()) {
         boolean ruleCheckingSucceeded = true;
         List newParentMethods = newParent.getMethodsWithoutIterator(false, true, false);
         Iterator i$ = newParentMethods.iterator();

         while(true) {
            ResolvedMember newParentMethod;
            String newParentMethodName;
            ResolvedMember discoveredImpl;
            label92:
            do {
               do {
                  do {
                     if (!i$.hasNext()) {
                        return ruleCheckingSucceeded;
                     }

                     newParentMethod = (ResolvedMember)i$.next();
                     newParentMethodName = newParentMethod.getName();
                  } while(!newParentMethod.isAbstract());
               } while(newParentMethodName.startsWith("ajc$interField"));

               discoveredImpl = null;
               List targetMethods = newParentTarget.getType().getMethodsWithoutIterator(false, true, false);
               Iterator i$ = targetMethods.iterator();

               do {
                  ResolvedMember targetMethod;
                  do {
                     do {
                        if (!i$.hasNext()) {
                           continue label92;
                        }

                        targetMethod = (ResolvedMember)i$.next();
                     } while(targetMethod.isAbstract());
                  } while(!targetMethod.getName().equals(newParentMethodName));

                  String newParentMethodSig = newParentMethod.getParameterSignature();
                  String targetMethodSignature = targetMethod.getParameterSignature();
                  if (targetMethodSignature.equals(newParentMethodSig)) {
                     discoveredImpl = targetMethod;
                  } else if (targetMethod.hasBackingGenericMember() && targetMethod.getBackingGenericMember().getParameterSignature().equals(newParentMethodSig)) {
                     discoveredImpl = targetMethod;
                  } else if (newParentMethod.hasBackingGenericMember() && newParentMethod.getBackingGenericMember().getParameterSignature().equals(targetMethodSignature)) {
                     discoveredImpl = targetMethod;
                  }
               } while(discoveredImpl == null);
            } while(discoveredImpl != null);

            boolean satisfiedByITD = false;
            Iterator i$ = newParentTarget.getType().getInterTypeMungersIncludingSupers().iterator();

            while(true) {
               while(i$.hasNext()) {
                  ConcreteTypeMunger m = (ConcreteTypeMunger)i$.next();
                  if (m.getMunger() != null && m.getMunger().getKind() == ResolvedTypeMunger.Method) {
                     ResolvedMember sig = m.getSignature();
                     if (!Modifier.isAbstract(sig.getModifiers())) {
                        if (m.isTargetTypeParameterized()) {
                           ResolvedType genericOnType = this.getWorld().resolve(sig.getDeclaringType()).getGenericType();
                           ResolvedType actualOccurrence = newParent.discoverActualOccurrenceOfTypeInHierarchy(genericOnType);
                           if (actualOccurrence == null) {
                              actualOccurrence = newParentTarget.getType().discoverActualOccurrenceOfTypeInHierarchy(genericOnType);
                           }

                           m = m.parameterizedFor(actualOccurrence);
                           sig = m.getSignature();
                        }

                        if (ResolvedType.matches(AjcMemberMaker.interMethod(sig, m.getAspectType(), sig.getDeclaringType().resolve(weaver.getWorld()).isInterface()), newParentMethod)) {
                           satisfiedByITD = true;
                        }
                     }
                  } else if (m.getMunger() != null && m.getMunger().getKind() == ResolvedTypeMunger.MethodDelegate2) {
                     satisfiedByITD = true;
                  }
               }

               if (!satisfiedByITD) {
                  this.error(weaver, "The type " + newParentTarget.getName() + " must implement the inherited abstract method " + newParentMethod.getDeclaringType() + "." + newParentMethodName + newParentMethod.getParameterSignature(), newParentTarget.getType().getSourceLocation(), new ISourceLocation[]{newParentMethod.getSourceLocation(), mungerLoc});
                  ruleCheckingSucceeded = false;
               }
               break;
            }
         }
      } else {
         return true;
      }
   }

   private boolean enforceDecpRule2_cantExtendFinalClass(BcelClassWeaver weaver, ISourceLocation transformerLoc, LazyClassGen targetType, ResolvedType newParent) {
      if (newParent.isFinal()) {
         this.error(weaver, "Cannot make type " + targetType.getName() + " extend final class " + newParent.getName(), targetType.getType().getSourceLocation(), new ISourceLocation[]{transformerLoc});
         return false;
      } else {
         return true;
      }
   }

   private boolean enforceDecpRule3_visibilityChanges(BcelClassWeaver weaver, ResolvedType newParent, ResolvedMember superMethod, LazyMethodGen subMethod) {
      boolean cont = true;
      if (Modifier.isPublic(superMethod.getModifiers())) {
         if (subMethod.isProtected() || subMethod.isDefault() || subMethod.isPrivate()) {
            weaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("Cannot reduce the visibility of the inherited method '" + superMethod + "' from " + newParent.getName(), superMethod.getSourceLocation()));
            cont = false;
         }
      } else if (Modifier.isProtected(superMethod.getModifiers())) {
         if (subMethod.isDefault() || subMethod.isPrivate()) {
            weaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("Cannot reduce the visibility of the inherited method '" + superMethod + "' from " + newParent.getName(), superMethod.getSourceLocation()));
            cont = false;
         }
      } else if (superMethod.isDefault() && subMethod.isPrivate()) {
         weaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("Cannot reduce the visibility of the inherited method '" + superMethod + "' from " + newParent.getName(), superMethod.getSourceLocation()));
         cont = false;
      }

      return cont;
   }

   private boolean enforceDecpRule4_compatibleReturnTypes(BcelClassWeaver weaver, ResolvedMember superMethod, LazyMethodGen subMethod) {
      boolean cont = true;
      String superReturnTypeSig = superMethod.getGenericReturnType().getSignature();
      String subReturnTypeSig = subMethod.getGenericReturnTypeSignature();
      superReturnTypeSig = superReturnTypeSig.replace('.', '/');
      subReturnTypeSig = subReturnTypeSig.replace('.', '/');
      if (!superReturnTypeSig.equals(subReturnTypeSig)) {
         ResolvedType subType = weaver.getWorld().resolve(subMethod.getReturnType());
         ResolvedType superType = weaver.getWorld().resolve(superMethod.getReturnType());
         if (!superType.isAssignableFrom(subType)) {
            weaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("The return type is incompatible with " + superMethod.getDeclaringType() + "." + superMethod.getName() + superMethod.getParameterSignature(), subMethod.getSourceLocation()));
            cont = false;
         }
      }

      return cont;
   }

   private boolean enforceDecpRule5_cantChangeFromStaticToNonstatic(BcelClassWeaver weaver, ISourceLocation mungerLoc, ResolvedMember superMethod, LazyMethodGen subMethod) {
      boolean superMethodStatic = Modifier.isStatic(superMethod.getModifiers());
      if (superMethodStatic && !subMethod.isStatic()) {
         this.error(weaver, "This instance method " + subMethod.getName() + subMethod.getParameterSignature() + " cannot override the static method from " + superMethod.getDeclaringType().getName(), subMethod.getSourceLocation(), new ISourceLocation[]{mungerLoc});
         return false;
      } else if (!superMethodStatic && subMethod.isStatic()) {
         this.error(weaver, "The static method " + subMethod.getName() + subMethod.getParameterSignature() + " cannot hide the instance method from " + superMethod.getDeclaringType().getName(), subMethod.getSourceLocation(), new ISourceLocation[]{mungerLoc});
         return false;
      } else {
         return true;
      }
   }

   public void error(BcelClassWeaver weaver, String text, ISourceLocation primaryLoc, ISourceLocation[] extraLocs) {
      IMessage msg = new Message(text, primaryLoc, true, extraLocs);
      weaver.getWorld().getMessageHandler().handleMessage(msg);
   }

   private LazyMethodGen findMatchingMethod(LazyClassGen type, ResolvedMember searchMethod) {
      String searchName = searchMethod.getName();
      String searchSig = searchMethod.getParameterSignature();
      Iterator i$ = type.getMethodGens().iterator();

      LazyMethodGen method;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         method = (LazyMethodGen)i$.next();
      } while(!method.getName().equals(searchName) || !method.getParameterSignature().equals(searchSig));

      return method;
   }

   public boolean attemptToModifySuperCalls(BcelClassWeaver weaver, LazyClassGen newParentTarget, ResolvedType newParent) {
      ResolvedType currentParentType = newParentTarget.getSuperClass();
      if (((ResolvedType)currentParentType).getGenericType() != null) {
         currentParentType = ((ResolvedType)currentParentType).getGenericType();
      }

      String currentParent = ((ResolvedType)currentParentType).getName();
      if (((ResolvedType)newParent).getGenericType() != null) {
         newParent = ((ResolvedType)newParent).getGenericType();
      }

      List mgs = newParentTarget.getMethodGens();
      Iterator i$ = mgs.iterator();

      while(true) {
         LazyMethodGen aMethod;
         do {
            if (!i$.hasNext()) {
               return true;
            }

            aMethod = (LazyMethodGen)i$.next();
         } while(!LazyMethodGen.isConstructor(aMethod));

         InstructionList insList = aMethod.getBody();

         for(InstructionHandle handle = insList.getStart(); handle != null; handle = handle.getNext()) {
            if (handle.getInstruction().opcode == 183) {
               ConstantPool cpg = newParentTarget.getConstantPool();
               InvokeInstruction invokeSpecial = (InvokeInstruction)handle.getInstruction();
               if (invokeSpecial.getClassName(cpg).equals(currentParent) && invokeSpecial.getMethodName(cpg).equals("<init>")) {
                  ResolvedMember newCtor = this.getConstructorWithSignature((ResolvedType)newParent, invokeSpecial.getSignature(cpg));
                  if (newCtor == null) {
                     boolean satisfiedByITDC = false;
                     Iterator ii = newParentTarget.getType().getInterTypeMungersIncludingSupers().iterator();

                     while(ii.hasNext() && !satisfiedByITDC) {
                        ConcreteTypeMunger m = (ConcreteTypeMunger)ii.next();
                        if (m.getMunger() instanceof NewConstructorTypeMunger && m.getSignature().getSignature().equals(invokeSpecial.getSignature(cpg))) {
                           satisfiedByITDC = true;
                        }
                     }

                     if (!satisfiedByITDC) {
                        String csig = this.createReadableCtorSig((ResolvedType)newParent, cpg, invokeSpecial);
                        weaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("Unable to modify hierarchy for " + newParentTarget.getClassName() + " - the constructor " + csig + " is missing", this.getSourceLocation()));
                        return false;
                     }
                  }

                  int idx = cpg.addMethodref(((ResolvedType)newParent).getName(), invokeSpecial.getMethodName(cpg), invokeSpecial.getSignature(cpg));
                  invokeSpecial.setIndex(idx);
               }
            }
         }
      }
   }

   private String createReadableCtorSig(ResolvedType newParent, ConstantPool cpg, InvokeInstruction invokeSpecial) {
      StringBuffer sb = new StringBuffer();
      Type[] ctorArgs = invokeSpecial.getArgumentTypes(cpg);
      sb.append(newParent.getClassName());
      sb.append("(");

      for(int i = 0; i < ctorArgs.length; ++i) {
         String argtype = ctorArgs[i].toString();
         if (argtype.lastIndexOf(".") != -1) {
            sb.append(argtype.substring(argtype.lastIndexOf(".") + 1));
         } else {
            sb.append(argtype);
         }

         if (i + 1 < ctorArgs.length) {
            sb.append(",");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   private ResolvedMember getConstructorWithSignature(ResolvedType type, String searchSig) {
      ResolvedMember[] arr$ = type.getDeclaredJavaMethods();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedMember method = arr$[i$];
         if (MemberUtils.isConstructor(method) && method.getSignature().equals(searchSig)) {
            return method;
         }
      }

      return null;
   }

   private boolean mungePrivilegedAccess(BcelClassWeaver weaver, PrivilegedAccessMunger munger) {
      LazyClassGen gen = weaver.getLazyClassGen();
      ResolvedMember member = munger.getMember();
      ResolvedType onType = weaver.getWorld().resolve(member.getDeclaringType(), munger.getSourceLocation());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      if (((ResolvedType)onType).equals(gen.getType())) {
         if (member.getKind() == Member.FIELD) {
            this.addFieldGetter(gen, member, AjcMemberMaker.privilegedAccessMethodForFieldGet(this.aspectType, member, munger.shortSyntax));
            this.addFieldSetter(gen, member, AjcMemberMaker.privilegedAccessMethodForFieldSet(this.aspectType, member, munger.shortSyntax));
            return true;
         } else if (member.getKind() == Member.METHOD) {
            this.addMethodDispatch(gen, member, AjcMemberMaker.privilegedAccessMethodForMethod(this.aspectType, member));
            return true;
         } else if (member.getKind() == Member.CONSTRUCTOR) {
            Iterator i = gen.getMethodGens().iterator();

            while(i.hasNext()) {
               LazyMethodGen m = (LazyMethodGen)i.next();
               if (m.getMemberView() != null && m.getMemberView().getKind() == Member.CONSTRUCTOR) {
                  m.forcePublic();
               }
            }

            return true;
         } else if (member.getKind() == Member.STATIC_INITIALIZATION) {
            gen.forcePublic();
            return true;
         } else {
            throw new RuntimeException("unimplemented");
         }
      } else {
         return false;
      }
   }

   private void addFieldGetter(LazyClassGen gen, ResolvedMember field, ResolvedMember accessMethod) {
      LazyMethodGen mg = this.makeMethodGen(gen, accessMethod);
      InstructionList il = new InstructionList();
      InstructionFactory fact = gen.getFactory();
      if (Modifier.isStatic(field.getModifiers())) {
         il.append((Instruction)fact.createFieldAccess(gen.getClassName(), field.getName(), BcelWorld.makeBcelType(field.getType()), (short)178));
      } else {
         il.append((Instruction)InstructionConstants.ALOAD_0);
         il.append((Instruction)fact.createFieldAccess(gen.getClassName(), field.getName(), BcelWorld.makeBcelType(field.getType()), (short)180));
      }

      il.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(field.getType())));
      mg.getBody().insert(il);
      gen.addMethodGen(mg, this.getSignature().getSourceLocation());
   }

   private void addFieldSetter(LazyClassGen gen, ResolvedMember field, ResolvedMember accessMethod) {
      LazyMethodGen mg = this.makeMethodGen(gen, accessMethod);
      InstructionList il = new InstructionList();
      InstructionFactory fact = gen.getFactory();
      Type fieldType = BcelWorld.makeBcelType(field.getType());
      if (Modifier.isStatic(field.getModifiers())) {
         il.append((Instruction)InstructionFactory.createLoad(fieldType, 0));
         il.append((Instruction)fact.createFieldAccess(gen.getClassName(), field.getName(), fieldType, (short)179));
      } else {
         il.append((Instruction)InstructionConstants.ALOAD_0);
         il.append((Instruction)InstructionFactory.createLoad(fieldType, 1));
         il.append((Instruction)fact.createFieldAccess(gen.getClassName(), field.getName(), fieldType, (short)181));
      }

      il.append(InstructionFactory.createReturn(Type.VOID));
      mg.getBody().insert(il);
      gen.addMethodGen(mg, this.getSignature().getSourceLocation());
   }

   private void addMethodDispatch(LazyClassGen gen, ResolvedMember method, ResolvedMember accessMethod) {
      LazyMethodGen mg = this.makeMethodGen(gen, accessMethod);
      InstructionList il = new InstructionList();
      InstructionFactory fact = gen.getFactory();
      Type[] paramTypes = BcelWorld.makeBcelTypes(method.getParameterTypes());
      int pos = 0;
      if (!Modifier.isStatic(method.getModifiers())) {
         il.append((Instruction)InstructionConstants.ALOAD_0);
         ++pos;
      }

      int i = 0;

      for(int len = paramTypes.length; i < len; ++i) {
         Type paramType = paramTypes[i];
         il.append((Instruction)InstructionFactory.createLoad(paramType, pos));
         pos += paramType.getSize();
      }

      il.append(Utility.createInvoke(fact, (BcelWorld)this.aspectType.getWorld(), method));
      il.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(method.getReturnType())));
      mg.getBody().insert(il);
      gen.addMethodGen(mg);
   }

   protected LazyMethodGen makeMethodGen(LazyClassGen gen, ResolvedMember member) {
      try {
         Type returnType = BcelWorld.makeBcelType(member.getReturnType());
         Type[] parameterTypes = BcelWorld.makeBcelTypes(member.getParameterTypes());
         LazyMethodGen ret = new LazyMethodGen(member.getModifiers(), returnType, member.getName(), parameterTypes, UnresolvedType.getNames(member.getExceptions()), gen);
         return ret;
      } catch (ClassFormatException var6) {
         throw new RuntimeException("Problem with makeMethodGen for method " + member.getName() + " in type " + gen.getName() + "  ret=" + member.getReturnType(), var6);
      }
   }

   protected FieldGen makeFieldGen(LazyClassGen gen, ResolvedMember member) {
      return new FieldGen(member.getModifiers(), BcelWorld.makeBcelType(member.getReturnType()), member.getName(), gen.getConstantPool());
   }

   private boolean mungePerObjectInterface(BcelClassWeaver weaver, PerObjectInterfaceTypeMunger munger) {
      LazyClassGen gen = weaver.getLazyClassGen();
      if (this.couldMatch(gen.getBcelObjectType(), munger.getTestPointcut())) {
         FieldGen fg = this.makeFieldGen(gen, AjcMemberMaker.perObjectField(gen.getType(), this.aspectType));
         gen.addField(fg, this.getSourceLocation());
         Type fieldType = BcelWorld.makeBcelType((UnresolvedType)this.aspectType);
         LazyMethodGen mg = new LazyMethodGen(1, fieldType, NameMangler.perObjectInterfaceGet(this.aspectType), new Type[0], new String[0], gen);
         InstructionList il = new InstructionList();
         InstructionFactory fact = gen.getFactory();
         il.append((Instruction)InstructionConstants.ALOAD_0);
         il.append((Instruction)fact.createFieldAccess(gen.getClassName(), fg.getName(), fieldType, (short)180));
         il.append(InstructionFactory.createReturn(fieldType));
         mg.getBody().insert(il);
         gen.addMethodGen(mg);
         LazyMethodGen mg1 = new LazyMethodGen(1, Type.VOID, NameMangler.perObjectInterfaceSet(this.aspectType), new Type[]{fieldType}, new String[0], gen);
         InstructionList il1 = new InstructionList();
         il1.append((Instruction)InstructionConstants.ALOAD_0);
         il1.append((Instruction)InstructionFactory.createLoad(fieldType, 1));
         il1.append((Instruction)fact.createFieldAccess(gen.getClassName(), fg.getName(), fieldType, (short)181));
         il1.append(InstructionFactory.createReturn(Type.VOID));
         mg1.getBody().insert(il1);
         gen.addMethodGen(mg1);
         gen.addInterface(munger.getInterfaceType().resolve(weaver.getWorld()), this.getSourceLocation());
         return true;
      } else {
         return false;
      }
   }

   private boolean mungePerTypeWithinTransformer(BcelClassWeaver weaver) {
      LazyClassGen gen = weaver.getLazyClassGen();
      FieldGen fg = this.makeFieldGen(gen, AjcMemberMaker.perTypeWithinField(gen.getType(), this.aspectType));
      gen.addField(fg, this.getSourceLocation());
      Type fieldType = BcelWorld.makeBcelType((UnresolvedType)this.aspectType);
      LazyMethodGen mg = new LazyMethodGen(9, fieldType, NameMangler.perTypeWithinLocalAspectOf(this.aspectType), new Type[0], new String[0], gen);
      InstructionList il = new InstructionList();
      InstructionFactory fact = gen.getFactory();
      il.append((Instruction)fact.createFieldAccess(gen.getClassName(), fg.getName(), fieldType, (short)178));
      il.append(InstructionFactory.createReturn(fieldType));
      mg.getBody().insert(il);
      gen.addMethodGen(mg);
      return true;
   }

   private boolean couldMatch(BcelObjectType bcelObjectType, Pointcut pointcut) {
      return !bcelObjectType.isInterface();
   }

   private boolean mungeNewMemberType(BcelClassWeaver classWeaver, NewMemberClassTypeMunger munger) {
      World world = classWeaver.getWorld();
      ResolvedType onType = world.resolve(munger.getTargetType());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      return ((ResolvedType)onType).equals(classWeaver.getLazyClassGen().getType());
   }

   private boolean mungeNewMethod(BcelClassWeaver classWeaver, NewMethodTypeMunger munger) {
      World world = classWeaver.getWorld();
      ResolvedMember unMangledInterMethod = munger.getSignature().resolve(world);
      ResolvedMember interMethodBody = munger.getDeclaredInterMethodBody(this.aspectType, world);
      ResolvedMember interMethodDispatcher = munger.getDeclaredInterMethodDispatcher(this.aspectType, world);
      LazyClassGen classGen = classWeaver.getLazyClassGen();
      ResolvedType onType = world.resolve(unMangledInterMethod.getDeclaringType(), munger.getSourceLocation());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      if (((ResolvedType)onType).isAnnotation()) {
         this.signalError("itdmOnAnnotationNotAllowed", classWeaver, (UnresolvedType)onType);
         return false;
      } else if (((ResolvedType)onType).isEnum()) {
         this.signalError("itdmOnEnumNotAllowed", classWeaver, (UnresolvedType)onType);
         return false;
      } else {
         boolean mungingInterface = classGen.isInterface();
         boolean onInterface = ((ResolvedType)onType).isInterface();
         if (onInterface && classGen.getLazyMethodGen(unMangledInterMethod.getName(), unMangledInterMethod.getSignature(), true) != null) {
            return false;
         } else {
            ResolvedMember mangledInterMethod;
            LazyMethodGen mg;
            AnnotationAJ[] annotationsOnRealMember;
            Object toLookOn;
            ResolvedMember realMember;
            int len$;
            int i$;
            AnnotationGen ag;
            int i$;
            if (((ResolvedType)onType).equals(classGen.getType())) {
               mangledInterMethod = AjcMemberMaker.interMethod(unMangledInterMethod, this.aspectType, onInterface);
               mg = this.makeMethodGen(classGen, mangledInterMethod);
               if (mungingInterface) {
                  mg.setAccessFlags(1025);
               }

               if (classWeaver.getWorld().isInJava5Mode()) {
                  annotationsOnRealMember = null;
                  toLookOn = this.aspectType;
                  if (this.aspectType.isRawType()) {
                     toLookOn = this.aspectType.getGenericType();
                  }

                  realMember = this.getRealMemberForITDFromAspect((ResolvedType)toLookOn, interMethodDispatcher, false);
                  if (realMember != null) {
                     annotationsOnRealMember = realMember.getAnnotations();
                  }

                  Set addedAnnotations = new HashSet();
                  AnnotationAJ annotation;
                  if (annotationsOnRealMember != null) {
                     AnnotationAJ[] arr$ = annotationsOnRealMember;
                     i$ = annotationsOnRealMember.length;

                     for(i$ = 0; i$ < i$; ++i$) {
                        annotation = arr$[i$];
                        ag = ((BcelAnnotation)annotation).getBcelAnnotation();
                        AnnotationGen ag = new AnnotationGen(ag, classGen.getConstantPool(), true);
                        mg.addAnnotation(new BcelAnnotation(ag, classWeaver.getWorld()));
                        addedAnnotations.add(annotation.getType());
                     }
                  }

                  if (realMember != null) {
                     this.copyOverParameterAnnotations(mg, realMember);
                  }

                  List allDecams = world.getDeclareAnnotationOnMethods();
                  Iterator i$ = allDecams.iterator();

                  while(i$.hasNext()) {
                     DeclareAnnotation declareAnnotationMC = (DeclareAnnotation)i$.next();
                     if (declareAnnotationMC.matches(unMangledInterMethod, world)) {
                        annotation = declareAnnotationMC.getAnnotation();
                        if (!addedAnnotations.contains(annotation.getType())) {
                           mg.addAnnotation(annotation);
                        }
                     }
                  }
               }

               if (!onInterface && !Modifier.isAbstract(mangledInterMethod.getModifiers())) {
                  InstructionList body = mg.getBody();
                  InstructionFactory fact = classGen.getFactory();
                  int pos = 0;
                  if (!Modifier.isStatic(unMangledInterMethod.getModifiers())) {
                     body.append(InstructionFactory.createThis());
                     ++pos;
                  }

                  Type[] paramTypes = BcelWorld.makeBcelTypes(mangledInterMethod.getParameterTypes());
                  len$ = 0;

                  for(i$ = paramTypes.length; len$ < i$; ++len$) {
                     Type paramType = paramTypes[len$];
                     body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
                     pos += paramType.getSize();
                  }

                  body.append(Utility.createInvoke(fact, classWeaver.getWorld(), interMethodBody));
                  body.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(mangledInterMethod.getReturnType())));
                  if (classWeaver.getWorld().isInJava5Mode()) {
                     this.createAnyBridgeMethodsForCovariance(classWeaver, munger, unMangledInterMethod, (ResolvedType)onType, classGen, paramTypes);
                  }
               }

               if (world.isInJava5Mode()) {
                  String basicSignature = mangledInterMethod.getSignature();
                  String genericSignature = ((ResolvedMemberImpl)mangledInterMethod).getSignatureForAttribute();
                  if (!basicSignature.equals(genericSignature)) {
                     mg.addAttribute(this.createSignatureAttribute(classGen.getConstantPool(), genericSignature));
                  }
               }

               classWeaver.addLazyMethodGen(mg);
               classWeaver.getLazyClassGen().warnOnAddedMethod(mg.getMethod(), this.getSignature().getSourceLocation());
               this.addNeededSuperCallMethods(classWeaver, (ResolvedType)onType, munger.getSuperMethodsCalled());
               return true;
            } else if (onInterface && !Modifier.isAbstract(unMangledInterMethod.getModifiers())) {
               if (!classGen.getType().isTopmostImplementor((ResolvedType)onType)) {
                  ResolvedType rtx = classGen.getType().getTopmostImplementor((ResolvedType)onType);
                  if (rtx == null) {
                     ResolvedType rt = classGen.getType();
                     ISourceLocation sloc;
                     if (rt.isInterface()) {
                        sloc = munger.getSourceLocation();
                        classWeaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("ITD target " + rt.getName() + " is an interface but has been incorrectly determined to be the topmost implementor of " + ((ResolvedType)onType).getName() + ". ITD is " + this.getSignature(), sloc));
                     }

                     if (!((ResolvedType)onType).isAssignableFrom(rt)) {
                        sloc = munger.getSourceLocation();
                        classWeaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error("ITD target " + rt.getName() + " doesn't appear to implement " + ((ResolvedType)onType).getName() + " why did we consider it the top most implementor? ITD is " + this.getSignature(), sloc));
                     }
                  } else if (!rtx.isExposedToWeaver()) {
                     ISourceLocation sLoc = munger.getSourceLocation();
                     classWeaver.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("itdNonExposedImplementor", rtx, this.getAspectType().getName()), sLoc == null ? this.getAspectType().getSourceLocation() : sLoc));
                  }

                  return false;
               } else {
                  mangledInterMethod = AjcMemberMaker.interMethod(unMangledInterMethod, this.aspectType, false);
                  mg = this.makeMethodGen(classGen, mangledInterMethod);
                  if (classWeaver.getWorld().isInJava5Mode()) {
                     annotationsOnRealMember = null;
                     toLookOn = this.aspectType;
                     if (this.aspectType.isRawType()) {
                        toLookOn = this.aspectType.getGenericType();
                     }

                     realMember = this.getRealMemberForITDFromAspect((ResolvedType)toLookOn, interMethodDispatcher, false);
                     if (realMember == null) {
                        throw new BCException("Couldn't find ITD holder member '" + interMethodDispatcher + "' on aspect " + this.aspectType);
                     }

                     annotationsOnRealMember = realMember.getAnnotations();
                     if (annotationsOnRealMember != null) {
                        AnnotationAJ[] arr$ = annotationsOnRealMember;
                        len$ = annotationsOnRealMember.length;

                        for(i$ = 0; i$ < len$; ++i$) {
                           AnnotationAJ annotationX = arr$[i$];
                           AnnotationGen a = ((BcelAnnotation)annotationX).getBcelAnnotation();
                           ag = new AnnotationGen(a, classWeaver.getLazyClassGen().getConstantPool(), true);
                           mg.addAnnotation(new BcelAnnotation(ag, classWeaver.getWorld()));
                        }
                     }

                     this.copyOverParameterAnnotations(mg, realMember);
                  }

                  if (mungingInterface) {
                     mg.setAccessFlags(1025);
                  }

                  Type[] paramTypes = BcelWorld.makeBcelTypes(mangledInterMethod.getParameterTypes());
                  Type returnType = BcelWorld.makeBcelType(mangledInterMethod.getReturnType());
                  InstructionList body = mg.getBody();
                  InstructionFactory fact = classGen.getFactory();
                  len$ = 0;
                  if (!Modifier.isStatic(mangledInterMethod.getModifiers())) {
                     body.append(InstructionFactory.createThis());
                     ++len$;
                  }

                  i$ = 0;

                  for(i$ = paramTypes.length; i$ < i$; ++i$) {
                     Type paramType = paramTypes[i$];
                     body.append((Instruction)InstructionFactory.createLoad(paramType, len$));
                     len$ += paramType.getSize();
                  }

                  body.append(Utility.createInvoke(fact, classWeaver.getWorld(), interMethodBody));
                  Type t = BcelWorld.makeBcelType(interMethodBody.getReturnType());
                  if (!t.equals(returnType)) {
                     body.append(fact.createCast(t, returnType));
                  }

                  body.append(InstructionFactory.createReturn(returnType));
                  mg.definingType = (ResolvedType)onType;
                  if (world.isInJava5Mode()) {
                     String basicSignature = mangledInterMethod.getSignature();
                     String genericSignature = ((ResolvedMemberImpl)mangledInterMethod).getSignatureForAttribute();
                     if (!basicSignature.equals(genericSignature)) {
                        mg.addAttribute(this.createSignatureAttribute(classGen.getConstantPool(), genericSignature));
                     }
                  }

                  classWeaver.addOrReplaceLazyMethodGen(mg);
                  this.addNeededSuperCallMethods(classWeaver, (ResolvedType)onType, munger.getSuperMethodsCalled());
                  this.createBridgeIfNecessary(classWeaver, munger, unMangledInterMethod, classGen);
                  return true;
               }
            } else {
               return false;
            }
         }
      }
   }

   private void createBridgeIfNecessary(BcelClassWeaver classWeaver, NewMethodTypeMunger munger, ResolvedMember unMangledInterMethod, LazyClassGen classGen) {
      if (munger.getDeclaredSignature() != null) {
         boolean needsbridging = false;
         ResolvedMember mungerSignature = munger.getSignature();
         ResolvedMember toBridgeTo = munger.getDeclaredSignature().parameterizedWith((UnresolvedType[])null, mungerSignature.getDeclaringType().resolve(this.getWorld()), false, munger.getTypeVariableAliases());
         if (!toBridgeTo.getReturnType().getErasureSignature().equals(mungerSignature.getReturnType().getErasureSignature())) {
            needsbridging = true;
         }

         UnresolvedType[] originalParams = toBridgeTo.getParameterTypes();
         UnresolvedType[] newParams = mungerSignature.getParameterTypes();

         for(int ii = 0; ii < originalParams.length; ++ii) {
            if (!originalParams[ii].getErasureSignature().equals(newParams[ii].getErasureSignature())) {
               needsbridging = true;
            }
         }

         if (needsbridging) {
            this.createBridge(classWeaver, unMangledInterMethod, classGen, toBridgeTo);
         }
      }

   }

   private void copyOverParameterAnnotations(LazyMethodGen receiverMethod, ResolvedMember donorMethod) {
      AnnotationAJ[][] pAnnos = donorMethod.getParameterAnnotations();
      if (pAnnos != null) {
         int offset = receiverMethod.isStatic() ? 0 : 1;
         int param = 0;

         for(int i = offset; i < pAnnos.length; ++i) {
            AnnotationAJ[] annosOnParam = pAnnos[i];
            if (annosOnParam != null) {
               AnnotationAJ[] arr$ = annosOnParam;
               int len$ = annosOnParam.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  AnnotationAJ anno = arr$[i$];
                  receiverMethod.addParameterAnnotation(param, anno);
               }
            }

            ++param;
         }
      }

   }

   private void createBridge(BcelClassWeaver weaver, ResolvedMember unMangledInterMethod, LazyClassGen classGen, ResolvedMember toBridgeTo) {
      ResolvedMember bridgerMethod = AjcMemberMaker.bridgerToInterMethod(unMangledInterMethod, classGen.getType());
      ResolvedMember bridgingSetter = AjcMemberMaker.interMethodBridger(toBridgeTo, this.aspectType, false);
      LazyMethodGen bridgeMethod = this.makeMethodGen(classGen, bridgingSetter);
      Type[] paramTypes = BcelWorld.makeBcelTypes(bridgingSetter.getParameterTypes());
      Type[] bridgingToParms = BcelWorld.makeBcelTypes(unMangledInterMethod.getParameterTypes());
      Type returnType = BcelWorld.makeBcelType(bridgingSetter.getReturnType());
      InstructionList body = bridgeMethod.getBody();
      InstructionFactory fact = classGen.getFactory();
      int pos = 0;
      if (!Modifier.isStatic(bridgingSetter.getModifiers())) {
         body.append(InstructionFactory.createThis());
         ++pos;
      }

      int i = 0;

      for(int len = paramTypes.length; i < len; ++i) {
         Type paramType = paramTypes[i];
         body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
         if (!bridgingSetter.getParameterTypes()[i].getErasureSignature().equals(unMangledInterMethod.getParameterTypes()[i].getErasureSignature())) {
            body.append(fact.createCast(paramType, bridgingToParms[i]));
         }

         pos += paramType.getSize();
      }

      body.append(Utility.createInvoke(fact, weaver.getWorld(), bridgerMethod));
      body.append(InstructionFactory.createReturn(returnType));
      classGen.addMethodGen(bridgeMethod);
   }

   private Signature createSignatureAttribute(ConstantPool cp, String signature) {
      int nameIndex = cp.addUtf8("Signature");
      int sigIndex = cp.addUtf8(signature);
      return new Signature(nameIndex, 2, sigIndex, cp);
   }

   private void createAnyBridgeMethodsForCovariance(BcelClassWeaver weaver, NewMethodTypeMunger munger, ResolvedMember unMangledInterMethod, ResolvedType onType, LazyClassGen gen, Type[] paramTypes) {
      boolean quitRightNow = false;
      String localMethodName = unMangledInterMethod.getName();
      String erasedSig = unMangledInterMethod.getSignatureErased();
      String localParameterSig = erasedSig.substring(0, erasedSig.lastIndexOf(41) + 1);
      String localReturnTypeESig = unMangledInterMethod.getReturnType().getErasureSignature();
      boolean alreadyDone = false;
      ResolvedMember[] localMethods = onType.getDeclaredMethods();

      for(int i = 0; i < localMethods.length; ++i) {
         ResolvedMember member = localMethods[i];
         if (member.getName().equals(localMethodName) && member.getParameterSignature().equals(localParameterSig)) {
            alreadyDone = true;
         }
      }

      if (!alreadyDone) {
         ResolvedType supertype = onType.getSuperclass();
         if (supertype != null) {
            Iterator iter = supertype.getMethods(true, true);

            while(iter.hasNext() && !quitRightNow) {
               ResolvedMember aMethod = (ResolvedMember)iter.next();
               if (aMethod.getName().equals(localMethodName) && aMethod.getParameterSignature().equals(localParameterSig) && !aMethod.getReturnType().getErasureSignature().equals(localReturnTypeESig) && !Modifier.isPrivate(aMethod.getModifiers())) {
                  this.createBridgeMethod(weaver.getWorld(), munger, unMangledInterMethod, gen, paramTypes, aMethod);
                  quitRightNow = true;
               }
            }
         }
      }

   }

   private void createBridgeMethod(BcelWorld world, NewMethodTypeMunger munger, ResolvedMember unMangledInterMethod, LazyClassGen clazz, Type[] paramTypes, ResolvedMember theBridgeMethod) {
      int pos = 0;
      LazyMethodGen bridgeMethod = this.makeMethodGen(clazz, theBridgeMethod);
      bridgeMethod.setAccessFlags(bridgeMethod.getAccessFlags() | 64);
      Type returnType = BcelWorld.makeBcelType(theBridgeMethod.getReturnType());
      InstructionList body = bridgeMethod.getBody();
      InstructionFactory fact = clazz.getFactory();
      if (!Modifier.isStatic(unMangledInterMethod.getModifiers())) {
         body.append(InstructionFactory.createThis());
         ++pos;
      }

      int i = 0;

      for(int len = paramTypes.length; i < len; ++i) {
         Type paramType = paramTypes[i];
         body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
         pos += paramType.getSize();
      }

      body.append(Utility.createInvoke(fact, world, unMangledInterMethod));
      body.append(InstructionFactory.createReturn(returnType));
      clazz.addMethodGen(bridgeMethod);
   }

   private String stringifyMember(ResolvedMember member) {
      StringBuffer buf = new StringBuffer();
      buf.append(member.getReturnType().getName());
      buf.append(' ');
      buf.append(member.getName());
      if (member.getKind() != Member.FIELD) {
         buf.append("(");
         UnresolvedType[] params = member.getParameterTypes();
         if (params.length != 0) {
            buf.append(params[0]);
            int i = 1;

            for(int len = params.length; i < len; ++i) {
               buf.append(", ");
               buf.append(params[i].getName());
            }
         }

         buf.append(")");
      }

      return buf.toString();
   }

   private boolean mungeMethodDelegate(BcelClassWeaver weaver, MethodDelegateTypeMunger munger) {
      World world = weaver.getWorld();
      LazyClassGen gen = weaver.getLazyClassGen();
      if (!gen.getType().isAnnotation() && !gen.getType().isEnum()) {
         ResolvedMember introduced = munger.getSignature();
         ResolvedType fromType = world.resolve(introduced.getDeclaringType(), munger.getSourceLocation());
         if (((ResolvedType)fromType).isRawType()) {
            fromType = ((ResolvedType)fromType).getGenericType();
         }

         boolean shouldApply = munger.matches(weaver.getLazyClassGen().getType(), this.aspectType);
         if (shouldApply) {
            Type bcelReturnType = BcelWorld.makeBcelType(introduced.getReturnType());
            if (munger.getImplClassName() == null && !munger.specifiesDelegateFactoryMethod()) {
               boolean isOK = false;
               List existingMethods = gen.getMethodGens();
               Iterator i$ = existingMethods.iterator();

               while(i$.hasNext()) {
                  LazyMethodGen m = (LazyMethodGen)i$.next();
                  if (m.getName().equals(introduced.getName()) && m.getParameterSignature().equals(introduced.getParameterSignature()) && m.getReturnType().equals(bcelReturnType)) {
                     isOK = true;
                  }
               }

               if (!isOK) {
                  IMessage msg = new Message("@DeclareParents: No defaultImpl was specified but the type '" + gen.getName() + "' does not implement the method '" + this.stringifyMember(introduced) + "' defined on the interface '" + introduced.getDeclaringType() + "'", weaver.getLazyClassGen().getType().getSourceLocation(), true, new ISourceLocation[]{munger.getSourceLocation()});
                  weaver.getWorld().getMessageHandler().handleMessage(msg);
                  return false;
               } else {
                  return true;
               }
            } else {
               LazyMethodGen mg = new LazyMethodGen(introduced.getModifiers() - 1024, bcelReturnType, introduced.getName(), BcelWorld.makeBcelTypes(introduced.getParameterTypes()), BcelWorld.makeBcelTypesAsClassNames(introduced.getExceptions()), gen);
               int len$;
               if (weaver.getWorld().isInJava5Mode()) {
                  AnnotationAJ[] annotationsOnRealMember = null;
                  ResolvedType toLookOn = weaver.getWorld().lookupOrCreateName(introduced.getDeclaringType());
                  if (((ResolvedType)fromType).isRawType()) {
                     toLookOn = ((ResolvedType)fromType).getGenericType();
                  }

                  ResolvedMember[] ms = toLookOn.getDeclaredJavaMethods();
                  ResolvedMember[] arr$ = ms;
                  len$ = ms.length;

                  int i$;
                  for(i$ = 0; i$ < len$; ++i$) {
                     ResolvedMember m = arr$[i$];
                     if (introduced.getName().equals(m.getName()) && introduced.getSignature().equals(m.getSignature())) {
                        annotationsOnRealMember = m.getAnnotations();
                        break;
                     }
                  }

                  if (annotationsOnRealMember != null) {
                     AnnotationAJ[] arr$ = annotationsOnRealMember;
                     len$ = annotationsOnRealMember.length;

                     for(i$ = 0; i$ < len$; ++i$) {
                        AnnotationAJ anno = arr$[i$];
                        AnnotationGen a = ((BcelAnnotation)anno).getBcelAnnotation();
                        AnnotationGen ag = new AnnotationGen(a, weaver.getLazyClassGen().getConstantPool(), true);
                        mg.addAnnotation(new BcelAnnotation(ag, weaver.getWorld()));
                     }
                  }
               }

               InstructionList body = new InstructionList();
               InstructionFactory fact = gen.getFactory();
               body.append((Instruction)InstructionConstants.ALOAD_0);
               body.append(Utility.createGet(fact, munger.getDelegate(weaver.getLazyClassGen().getType())));
               InstructionBranch ifNonNull = InstructionFactory.createBranchInstruction((short)199, (InstructionHandle)null);
               body.append(ifNonNull);
               body.append((Instruction)InstructionConstants.ALOAD_0);
               if (munger.specifiesDelegateFactoryMethod()) {
                  ResolvedMember rm = munger.getDelegateFactoryMethod(weaver.getWorld());
                  if (rm.getArity() != 0) {
                     ResolvedType parameterType = rm.getParameterTypes()[0].resolve(weaver.getWorld());
                     if (!parameterType.isAssignableFrom(weaver.getLazyClassGen().getType())) {
                        this.signalError("For mixin factory method '" + rm + "': Instance type '" + weaver.getLazyClassGen().getType() + "' is not compatible with factory parameter type '" + parameterType + "'", weaver);
                        return false;
                     }
                  }

                  if (Modifier.isStatic(rm.getModifiers())) {
                     if (rm.getArity() != 0) {
                        body.append((Instruction)InstructionConstants.ALOAD_0);
                     }

                     body.append((Instruction)fact.createInvoke(rm.getDeclaringType().getName(), rm.getName(), rm.getSignature(), (short)184));
                     body.append(Utility.createSet(fact, munger.getDelegate(weaver.getLazyClassGen().getType())));
                  } else {
                     UnresolvedType theAspect = munger.getAspect();
                     body.append((Instruction)fact.createInvoke(theAspect.getName(), "aspectOf", "()" + theAspect.getSignature(), (short)184));
                     if (rm.getArity() != 0) {
                        body.append((Instruction)InstructionConstants.ALOAD_0);
                     }

                     body.append((Instruction)fact.createInvoke(rm.getDeclaringType().getName(), rm.getName(), rm.getSignature(), (short)182));
                     body.append(Utility.createSet(fact, munger.getDelegate(weaver.getLazyClassGen().getType())));
                  }
               } else {
                  body.append(fact.createNew(munger.getImplClassName()));
                  body.append(InstructionConstants.DUP);
                  body.append((Instruction)fact.createInvoke(munger.getImplClassName(), "<init>", Type.VOID, Type.NO_ARGS, (short)183));
                  body.append(Utility.createSet(fact, munger.getDelegate(weaver.getLazyClassGen().getType())));
               }

               InstructionHandle ifNonNullElse = body.append((Instruction)InstructionConstants.ALOAD_0);
               ifNonNull.setTarget(ifNonNullElse);
               body.append(Utility.createGet(fact, munger.getDelegate(weaver.getLazyClassGen().getType())));
               len$ = 0;
               if (!Modifier.isStatic(introduced.getModifiers())) {
                  ++len$;
               }

               Type[] paramTypes = BcelWorld.makeBcelTypes(introduced.getParameterTypes());
               int i = 0;

               for(int len = paramTypes.length; i < len; ++i) {
                  Type paramType = paramTypes[i];
                  body.append((Instruction)InstructionFactory.createLoad(paramType, len$));
                  len$ += paramType.getSize();
               }

               body.append(Utility.createInvoke(fact, (short)185, introduced));
               body.append(InstructionFactory.createReturn(bcelReturnType));
               mg.getBody().append(body);
               weaver.addLazyMethodGen(mg);
               weaver.getLazyClassGen().warnOnAddedMethod(mg.getMethod(), this.getSignature().getSourceLocation());
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean mungeFieldHost(BcelClassWeaver weaver, MethodDelegateTypeMunger.FieldHostTypeMunger munger) {
      LazyClassGen gen = weaver.getLazyClassGen();
      if (!gen.getType().isAnnotation() && !gen.getType().isEnum()) {
         munger.matches(weaver.getLazyClassGen().getType(), this.aspectType);
         ResolvedMember host = AjcMemberMaker.itdAtDeclareParentsField(weaver.getLazyClassGen().getType(), munger.getSignature().getType(), this.aspectType);
         FieldGen field = this.makeFieldGen(weaver.getLazyClassGen(), host);
         field.setModifiers(field.getModifiers() | BcelField.AccSynthetic);
         weaver.getLazyClassGen().addField(field, (ISourceLocation)null);
         return true;
      } else {
         return false;
      }
   }

   private ResolvedMember getRealMemberForITDFromAspect(ResolvedType aspectType, ResolvedMember lookingFor, boolean isCtorRelated) {
      World world = aspectType.getWorld();
      boolean debug = false;
      if (debug) {
         System.err.println("Searching for a member on type: " + aspectType);
         System.err.println("Member we are looking for: " + lookingFor);
      }

      ResolvedMember[] aspectMethods = aspectType.getDeclaredMethods();
      UnresolvedType[] lookingForParams = lookingFor.getParameterTypes();
      ResolvedMember realMember = null;

      for(int i = 0; realMember == null && i < aspectMethods.length; ++i) {
         ResolvedMember member = aspectMethods[i];
         if (member.getName().equals(lookingFor.getName())) {
            UnresolvedType[] memberParams = member.getGenericParameterTypes();
            if (memberParams.length == lookingForParams.length) {
               if (debug) {
                  System.err.println("Reviewing potential candidates: " + member);
               }

               boolean matchOK = true;
               if (isCtorRelated) {
                  for(int j = 0; j < memberParams.length && matchOK; ++j) {
                     ResolvedType pMember = memberParams[j].resolve(world);
                     ResolvedType pLookingFor = lookingForParams[j].resolve(world);
                     if (pMember.isTypeVariableReference()) {
                        pMember = ((TypeVariableReference)pMember).getTypeVariable().getFirstBound().resolve(world);
                     }

                     if (pMember.isParameterizedType() || pMember.isGenericType()) {
                        pMember = pMember.getRawType().resolve(aspectType.getWorld());
                     }

                     if (pLookingFor.isTypeVariableReference()) {
                        pLookingFor = ((TypeVariableReference)pLookingFor).getTypeVariable().getFirstBound().resolve(world);
                     }

                     if (pLookingFor.isParameterizedType() || pLookingFor.isGenericType()) {
                        pLookingFor = pLookingFor.getRawType().resolve(world);
                     }

                     if (debug) {
                        System.err.println("Comparing parameter " + j + "   member=" + pMember + "   lookingFor=" + pLookingFor);
                     }

                     if (!pMember.equals(pLookingFor)) {
                        matchOK = false;
                     }
                  }
               }

               if (matchOK) {
                  realMember = member;
               }
            }
         }
      }

      if (debug && realMember == null) {
         System.err.println("Didn't find a match");
      }

      return realMember;
   }

   private void addNeededSuperCallMethods(BcelClassWeaver weaver, ResolvedType onType, Set neededSuperCalls) {
      LazyClassGen gen = weaver.getLazyClassGen();
      Iterator i$ = neededSuperCalls.iterator();

      while(i$.hasNext()) {
         ResolvedMember superMethod = (ResolvedMember)i$.next();
         if (weaver.addDispatchTarget(superMethod)) {
            boolean isSuper = !superMethod.getDeclaringType().equals(gen.getType());
            String dispatchName;
            if (isSuper) {
               dispatchName = NameMangler.superDispatchMethod(onType, superMethod.getName());
            } else {
               dispatchName = NameMangler.protectedDispatchMethod(onType, superMethod.getName());
            }

            superMethod = superMethod.resolve(weaver.getWorld());
            LazyMethodGen dispatcher = makeDispatcher(gen, dispatchName, superMethod, weaver.getWorld(), isSuper);
            weaver.addLazyMethodGen(dispatcher);
         }
      }

   }

   private void signalError(String msgid, BcelClassWeaver weaver, UnresolvedType onType) {
      IMessage msg = MessageUtil.error(WeaverMessages.format(msgid, onType.getName()), this.getSourceLocation());
      weaver.getWorld().getMessageHandler().handleMessage(msg);
   }

   private void signalError(String msgString, BcelClassWeaver weaver) {
      IMessage msg = MessageUtil.error(msgString, this.getSourceLocation());
      weaver.getWorld().getMessageHandler().handleMessage(msg);
   }

   private boolean mungeNewConstructor(BcelClassWeaver weaver, NewConstructorTypeMunger newConstructorTypeMunger) {
      LazyClassGen currentClass = weaver.getLazyClassGen();
      InstructionFactory fact = currentClass.getFactory();
      ResolvedMember newConstructorMember = newConstructorTypeMunger.getSyntheticConstructor();
      ResolvedType onType = newConstructorMember.getDeclaringType().resolve(weaver.getWorld());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      if (((ResolvedType)onType).isAnnotation()) {
         this.signalError("itdcOnAnnotationNotAllowed", weaver, (UnresolvedType)onType);
         return false;
      } else if (((ResolvedType)onType).isEnum()) {
         this.signalError("itdcOnEnumNotAllowed", weaver, (UnresolvedType)onType);
         return false;
      } else if (!((ResolvedType)onType).equals(currentClass.getType())) {
         return false;
      } else {
         ResolvedMember explicitConstructor = newConstructorTypeMunger.getExplicitConstructor();
         LazyMethodGen mg = this.makeMethodGen(currentClass, newConstructorMember);
         mg.setEffectiveSignature(newConstructorTypeMunger.getSignature(), Shadow.ConstructorExecution, true);
         int i;
         if (weaver.getWorld().isInJava5Mode()) {
            ResolvedMember interMethodDispatcher = AjcMemberMaker.postIntroducedConstructor(this.aspectType, (UnresolvedType)onType, newConstructorTypeMunger.getSignature().getParameterTypes());
            AnnotationAJ[] annotationsOnRealMember = null;
            ResolvedMember realMember = this.getRealMemberForITDFromAspect(this.aspectType, interMethodDispatcher, true);
            if (realMember != null) {
               annotationsOnRealMember = realMember.getAnnotations();
            }

            if (annotationsOnRealMember != null) {
               for(i = 0; i < annotationsOnRealMember.length; ++i) {
                  AnnotationAJ annotationX = annotationsOnRealMember[i];
                  AnnotationGen a = ((BcelAnnotation)annotationX).getBcelAnnotation();
                  AnnotationGen ag = new AnnotationGen(a, weaver.getLazyClassGen().getConstantPool(), true);
                  mg.addAnnotation(new BcelAnnotation(ag, weaver.getWorld()));
               }
            }

            List allDecams = weaver.getWorld().getDeclareAnnotationOnMethods();
            Iterator i = allDecams.iterator();

            while(i.hasNext()) {
               DeclareAnnotation decaMC = (DeclareAnnotation)i.next();
               if (decaMC.matches(explicitConstructor, weaver.getWorld()) && mg.getEnclosingClass().getType() == this.aspectType) {
                  mg.addAnnotation(decaMC.getAnnotation());
               }
            }
         }

         if (mg.getArgumentTypes().length == 0) {
            LazyMethodGen toRemove = null;
            Iterator i$ = currentClass.getMethodGens().iterator();

            while(i$.hasNext()) {
               LazyMethodGen object = (LazyMethodGen)i$.next();
               if (object.getName().equals("<init>") && object.getArgumentTypes().length == 0) {
                  toRemove = object;
               }
            }

            if (toRemove != null) {
               currentClass.removeMethodGen(toRemove);
            }
         }

         currentClass.addMethodGen(mg);
         InstructionList body = mg.getBody();
         UnresolvedType[] declaredParams = newConstructorTypeMunger.getSignature().getParameterTypes();
         Type[] paramTypes = mg.getArgumentTypes();
         i = 1;
         int i = 0;

         int arraySlot;
         for(arraySlot = declaredParams.length; i < arraySlot; ++i) {
            body.append((Instruction)InstructionFactory.createLoad(paramTypes[i], i));
            i += paramTypes[i].getSize();
         }

         Member preMethod = AjcMemberMaker.preIntroducedConstructor(this.aspectType, (UnresolvedType)onType, declaredParams);
         body.append(Utility.createInvoke(fact, (BcelWorld)null, preMethod));
         arraySlot = mg.allocateLocal(1);
         body.append((Instruction)InstructionFactory.createStore(Type.OBJECT, arraySlot));
         body.append((Instruction)InstructionConstants.ALOAD_0);
         UnresolvedType[] superParamTypes = explicitConstructor.getParameterTypes();
         int i = 0;

         for(int len = superParamTypes.length; i < len; ++i) {
            body.append((Instruction)InstructionFactory.createLoad(Type.OBJECT, arraySlot));
            body.append(Utility.createConstant(fact, i));
            body.append(InstructionFactory.createArrayLoad(Type.OBJECT));
            body.append(Utility.createConversion(fact, Type.OBJECT, BcelWorld.makeBcelType(superParamTypes[i])));
         }

         body.append(Utility.createInvoke(fact, (BcelWorld)null, explicitConstructor));
         body.append((Instruction)InstructionConstants.ALOAD_0);
         Member postMethod = AjcMemberMaker.postIntroducedConstructor(this.aspectType, (UnresolvedType)onType, declaredParams);
         UnresolvedType[] postParamTypes = postMethod.getParameterTypes();
         int i = 1;

         for(int len = postParamTypes.length; i < len; ++i) {
            body.append((Instruction)InstructionFactory.createLoad(Type.OBJECT, arraySlot));
            body.append(Utility.createConstant(fact, superParamTypes.length + i - 1));
            body.append(InstructionFactory.createArrayLoad(Type.OBJECT));
            body.append(Utility.createConversion(fact, Type.OBJECT, BcelWorld.makeBcelType(postParamTypes[i])));
         }

         body.append(Utility.createInvoke(fact, (BcelWorld)null, postMethod));
         body.append(InstructionConstants.RETURN);
         this.addNeededSuperCallMethods(weaver, (ResolvedType)onType, this.munger.getSuperMethodsCalled());
         return true;
      }
   }

   private static LazyMethodGen makeDispatcher(LazyClassGen onGen, String dispatchName, ResolvedMember superMethod, BcelWorld world, boolean isSuper) {
      Type[] paramTypes = BcelWorld.makeBcelTypes(superMethod.getParameterTypes());
      Type returnType = BcelWorld.makeBcelType(superMethod.getReturnType());
      int modifiers = 1;
      if (onGen.isInterface()) {
         modifiers |= 1024;
      }

      LazyMethodGen mg = new LazyMethodGen(modifiers, returnType, dispatchName, paramTypes, UnresolvedType.getNames(superMethod.getExceptions()), onGen);
      InstructionList body = mg.getBody();
      if (onGen.isInterface()) {
         return mg;
      } else {
         InstructionFactory fact = onGen.getFactory();
         int pos = 0;
         body.append(InstructionFactory.createThis());
         ++pos;
         int i = 0;

         for(int len = paramTypes.length; i < len; ++i) {
            Type paramType = paramTypes[i];
            body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
            pos += paramType.getSize();
         }

         if (isSuper) {
            body.append(Utility.createSuperInvoke(fact, world, superMethod));
         } else {
            body.append(Utility.createInvoke(fact, world, superMethod));
         }

         body.append(InstructionFactory.createReturn(returnType));
         return mg;
      }
   }

   private boolean mungeNewField(BcelClassWeaver weaver, NewFieldTypeMunger munger) {
      munger.getInitMethod(this.aspectType);
      LazyClassGen gen = weaver.getLazyClassGen();
      ResolvedMember field = munger.getSignature();
      ResolvedType onType = weaver.getWorld().resolve(field.getDeclaringType(), munger.getSourceLocation());
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      boolean onInterface = ((ResolvedType)onType).isInterface();
      if (((ResolvedType)onType).isAnnotation()) {
         this.signalError("itdfOnAnnotationNotAllowed", weaver, (UnresolvedType)onType);
         return false;
      } else if (((ResolvedType)onType).isEnum()) {
         this.signalError("itdfOnEnumNotAllowed", weaver, (UnresolvedType)onType);
         return false;
      } else {
         ResolvedMember interMethodBody = munger.getInitMethod(this.aspectType);
         AnnotationAJ[] annotationsOnRealMember = null;
         ResolvedMember newField;
         if (weaver.getWorld().isInJava5Mode()) {
            ResolvedType toLookOn = this.aspectType;
            if (this.aspectType.isRawType()) {
               toLookOn = this.aspectType.getGenericType();
            }

            newField = this.getRealMemberForITDFromAspect((ResolvedType)toLookOn, interMethodBody, false);
            if (newField != null) {
               annotationsOnRealMember = newField.getAnnotations();
            }
         }

         String fieldName;
         if (((ResolvedType)onType).equals(gen.getType())) {
            ResolvedMember itdfieldGetter;
            if (onInterface) {
               itdfieldGetter = AjcMemberMaker.interFieldInterfaceGetter(field, (ResolvedType)onType, this.aspectType);
               LazyMethodGen mg = this.makeMethodGen(gen, itdfieldGetter);
               gen.addMethodGen(mg);
               LazyMethodGen mg1 = this.makeMethodGen(gen, AjcMemberMaker.interFieldInterfaceSetter(field, (ResolvedType)onType, this.aspectType));
               gen.addMethodGen(mg1);
            } else {
               weaver.addInitializer(this);
               itdfieldGetter = AjcMemberMaker.interFieldClassField(field, this.aspectType, munger.version == 2);
               FieldGen fg = this.makeFieldGen(gen, itdfieldGetter);
               if (annotationsOnRealMember != null) {
                  for(int i = 0; i < annotationsOnRealMember.length; ++i) {
                     AnnotationAJ annotationX = annotationsOnRealMember[i];
                     AnnotationGen a = ((BcelAnnotation)annotationX).getBcelAnnotation();
                     AnnotationGen ag = new AnnotationGen(a, weaver.getLazyClassGen().getConstantPool(), true);
                     fg.addAnnotation(ag);
                  }
               }

               if (weaver.getWorld().isInJava5Mode()) {
                  fieldName = field.getSignature();
                  String genericSignature = field.getReturnType().resolve(weaver.getWorld()).getSignatureForAttribute();
                  if (!fieldName.equals(genericSignature)) {
                     fg.addAttribute(this.createSignatureAttribute(gen.getConstantPool(), genericSignature));
                  }
               }

               gen.addField(fg, this.getSourceLocation());
            }

            return true;
         } else if (onInterface && gen.getType().isTopmostImplementor((ResolvedType)onType)) {
            if (Modifier.isStatic(field.getModifiers())) {
               throw new RuntimeException("unimplemented");
            } else {
               boolean alreadyExists = false;
               if (munger.version == 2) {
                  Iterator i$ = gen.getFieldGens().iterator();

                  while(i$.hasNext()) {
                     BcelField fieldgen = (BcelField)i$.next();
                     if (fieldgen.getName().equals(field.getName())) {
                        alreadyExists = true;
                        break;
                     }
                  }
               }

               newField = AjcMemberMaker.interFieldInterfaceField(field, (UnresolvedType)onType, this.aspectType, munger.version == 2);
               fieldName = newField.getName();
               Type fieldType = BcelWorld.makeBcelType(field.getType());
               if (!alreadyExists) {
                  weaver.addInitializer(this);
                  FieldGen fg = this.makeFieldGen(gen, newField);
                  if (annotationsOnRealMember != null) {
                     for(int i = 0; i < annotationsOnRealMember.length; ++i) {
                        AnnotationAJ annotationX = annotationsOnRealMember[i];
                        AnnotationGen a = ((BcelAnnotation)annotationX).getBcelAnnotation();
                        AnnotationGen ag = new AnnotationGen(a, weaver.getLazyClassGen().getConstantPool(), true);
                        fg.addAnnotation(ag);
                     }
                  }

                  if (weaver.getWorld().isInJava5Mode()) {
                     String basicSignature = field.getSignature();
                     String genericSignature = field.getReturnType().resolve(weaver.getWorld()).getSignatureForAttribute();
                     if (!basicSignature.equals(genericSignature)) {
                        fg.addAttribute(this.createSignatureAttribute(gen.getConstantPool(), genericSignature));
                     }
                  }

                  gen.addField(fg, this.getSourceLocation());
               }

               ResolvedMember itdfieldGetter = AjcMemberMaker.interFieldInterfaceGetter(field, gen.getType(), this.aspectType);
               LazyMethodGen mg = this.makeMethodGen(gen, itdfieldGetter);
               InstructionList il = new InstructionList();
               InstructionFactory fact = gen.getFactory();
               if (Modifier.isStatic(field.getModifiers())) {
                  il.append((Instruction)fact.createFieldAccess(gen.getClassName(), fieldName, fieldType, (short)178));
               } else {
                  il.append((Instruction)InstructionConstants.ALOAD_0);
                  il.append((Instruction)fact.createFieldAccess(gen.getClassName(), fieldName, fieldType, (short)180));
               }

               il.append(InstructionFactory.createReturn(fieldType));
               mg.getBody().insert(il);
               gen.addMethodGen(mg);
               if (munger.getDeclaredSignature() != null) {
                  ResolvedMember toBridgeTo = munger.getDeclaredSignature().parameterizedWith((UnresolvedType[])null, munger.getSignature().getDeclaringType().resolve(this.getWorld()), false, munger.getTypeVariableAliases());
                  boolean needsbridging = false;
                  if (!toBridgeTo.getReturnType().getErasureSignature().equals(munger.getSignature().getReturnType().getErasureSignature())) {
                     needsbridging = true;
                  }

                  if (needsbridging) {
                     ResolvedMember bridgingGetter = AjcMemberMaker.interFieldInterfaceGetter(toBridgeTo, gen.getType(), this.aspectType);
                     this.createBridgeMethodForITDF(weaver, gen, itdfieldGetter, bridgingGetter);
                  }
               }

               ResolvedMember itdfieldSetter = AjcMemberMaker.interFieldInterfaceSetter(field, gen.getType(), this.aspectType);
               LazyMethodGen mg1 = this.makeMethodGen(gen, itdfieldSetter);
               InstructionList il1 = new InstructionList();
               if (Modifier.isStatic(field.getModifiers())) {
                  il1.append((Instruction)InstructionFactory.createLoad(fieldType, 0));
                  il1.append((Instruction)fact.createFieldAccess(gen.getClassName(), fieldName, fieldType, (short)179));
               } else {
                  il1.append((Instruction)InstructionConstants.ALOAD_0);
                  il1.append((Instruction)InstructionFactory.createLoad(fieldType, 1));
                  il1.append((Instruction)fact.createFieldAccess(gen.getClassName(), fieldName, fieldType, (short)181));
               }

               il1.append(InstructionFactory.createReturn(Type.VOID));
               mg1.getBody().insert(il1);
               gen.addMethodGen(mg1);
               if (munger.getDeclaredSignature() != null) {
                  ResolvedMember toBridgeTo = munger.getDeclaredSignature().parameterizedWith((UnresolvedType[])null, munger.getSignature().getDeclaringType().resolve(this.getWorld()), false, munger.getTypeVariableAliases());
                  boolean needsbridging = false;
                  if (!toBridgeTo.getReturnType().getErasureSignature().equals(munger.getSignature().getReturnType().getErasureSignature())) {
                     needsbridging = true;
                  }

                  if (needsbridging) {
                     ResolvedMember bridgingSetter = AjcMemberMaker.interFieldInterfaceSetter(toBridgeTo, gen.getType(), this.aspectType);
                     this.createBridgeMethodForITDF(weaver, gen, itdfieldSetter, bridgingSetter);
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   private void createBridgeMethodForITDF(BcelClassWeaver weaver, LazyClassGen gen, ResolvedMember itdfieldSetter, ResolvedMember bridgingSetter) {
      LazyMethodGen bridgeMethod = this.makeMethodGen(gen, bridgingSetter);
      bridgeMethod.setAccessFlags(bridgeMethod.getAccessFlags() | 64);
      Type[] paramTypes = BcelWorld.makeBcelTypes(bridgingSetter.getParameterTypes());
      Type[] bridgingToParms = BcelWorld.makeBcelTypes(itdfieldSetter.getParameterTypes());
      Type returnType = BcelWorld.makeBcelType(bridgingSetter.getReturnType());
      InstructionList body = bridgeMethod.getBody();
      InstructionFactory fact = gen.getFactory();
      int pos = 0;
      if (!Modifier.isStatic(bridgingSetter.getModifiers())) {
         body.append(InstructionFactory.createThis());
         ++pos;
      }

      int i = 0;

      for(int len = paramTypes.length; i < len; ++i) {
         Type paramType = paramTypes[i];
         body.append((Instruction)InstructionFactory.createLoad(paramType, pos));
         if (!bridgingSetter.getParameterTypes()[i].getErasureSignature().equals(itdfieldSetter.getParameterTypes()[i].getErasureSignature())) {
            body.append(fact.createCast(paramType, bridgingToParms[i]));
         }

         pos += paramType.getSize();
      }

      body.append(Utility.createInvoke(fact, weaver.getWorld(), itdfieldSetter));
      body.append(InstructionFactory.createReturn(returnType));
      gen.addMethodGen(bridgeMethod);
   }

   public ConcreteTypeMunger parameterizedFor(ResolvedType target) {
      return new BcelTypeMunger(this.munger.parameterizedFor(target), this.aspectType);
   }

   public ConcreteTypeMunger parameterizeWith(Map m, World w) {
      return new BcelTypeMunger(this.munger.parameterizeWith(m, w), this.aspectType);
   }

   public List getTypeVariableAliases() {
      return this.munger.getTypeVariableAliases();
   }

   public boolean equals(Object other) {
      if (!(other instanceof BcelTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label38: {
            label27: {
               BcelTypeMunger o = (BcelTypeMunger)other;
               if (o.getMunger() == null) {
                  if (this.getMunger() != null) {
                     break label27;
                  }
               } else if (!o.getMunger().equals(this.getMunger())) {
                  break label27;
               }

               if (o.getAspectType() == null) {
                  if (this.getAspectType() == null) {
                     break label38;
                  }
               } else if (o.getAspectType().equals(this.getAspectType())) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + (this.getMunger() == null ? 0 : this.getMunger().hashCode());
         result = 37 * result + (this.getAspectType() == null ? 0 : this.getAspectType().hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }
}
