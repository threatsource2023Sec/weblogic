package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.Declare;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareAnnotation;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareSoft;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareTypeErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.PointcutRewriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CrosscuttingMembers {
   private final ResolvedType inAspect;
   private final World world;
   private PerClause perClause;
   private List shadowMungers = new ArrayList(4);
   private List typeMungers = new ArrayList(4);
   private List lateTypeMungers = new ArrayList(0);
   private Set declareParents = new HashSet();
   private Set declareSofts = new HashSet();
   private List declareDominates = new ArrayList(4);
   private Set declareAnnotationsOnType = new LinkedHashSet();
   private Set declareAnnotationsOnField = new LinkedHashSet();
   private Set declareAnnotationsOnMethods = new LinkedHashSet();
   private Set declareTypeEow = new HashSet();
   private boolean shouldConcretizeIfNeeded = true;
   private final Hashtable cflowFields = new Hashtable();
   private final Hashtable cflowBelowFields = new Hashtable();

   public CrosscuttingMembers(ResolvedType inAspect, boolean shouldConcretizeIfNeeded) {
      this.inAspect = inAspect;
      this.world = inAspect.getWorld();
      this.shouldConcretizeIfNeeded = shouldConcretizeIfNeeded;
   }

   public void addConcreteShadowMunger(ShadowMunger m) {
      this.shadowMungers.add(m);
   }

   public void addShadowMungers(Collection c) {
      Iterator i$ = c.iterator();

      while(i$.hasNext()) {
         ShadowMunger munger = (ShadowMunger)i$.next();
         this.addShadowMunger(munger);
      }

   }

   private void addShadowMunger(ShadowMunger m) {
      if (!this.inAspect.isAbstract()) {
         this.addConcreteShadowMunger(m.concretize(this.inAspect, this.world, this.perClause));
      }
   }

   public void addTypeMungers(Collection c) {
      this.typeMungers.addAll(c);
   }

   public void addTypeMunger(ConcreteTypeMunger m) {
      if (m == null) {
         throw new Error("FIXME AV - should not happen or what ?");
      } else {
         this.typeMungers.add(m);
      }
   }

   public void addLateTypeMungers(Collection c) {
      this.lateTypeMungers.addAll(c);
   }

   public void addLateTypeMunger(ConcreteTypeMunger m) {
      this.lateTypeMungers.add(m);
   }

   public void addDeclares(Collection declares) {
      Iterator i$ = declares.iterator();

      while(i$.hasNext()) {
         Declare declare = (Declare)i$.next();
         this.addDeclare(declare);
      }

   }

   public void addDeclare(Declare declare) {
      if (declare instanceof DeclareErrorOrWarning) {
         ShadowMunger m = new Checker((DeclareErrorOrWarning)declare);
         m.setDeclaringType(declare.getDeclaringType());
         this.addShadowMunger(m);
      } else if (declare instanceof DeclarePrecedence) {
         this.declareDominates.add(declare);
      } else if (declare instanceof DeclareParents) {
         DeclareParents dp = (DeclareParents)declare;
         this.exposeTypes(dp.getParents().getExactTypes());
         this.declareParents.add(dp);
      } else if (declare instanceof DeclareSoft) {
         DeclareSoft d = (DeclareSoft)declare;
         ShadowMunger m = Advice.makeSoftener(this.world, d.getPointcut(), d.getException(), this.inAspect, d);
         m.setDeclaringType(d.getDeclaringType());
         Pointcut concretePointcut = d.getPointcut().concretize(this.inAspect, d.getDeclaringType(), 0, m);
         m.pointcut = concretePointcut;
         this.declareSofts.add(new DeclareSoft(d.getException(), concretePointcut));
         this.addConcreteShadowMunger(m);
      } else if (declare instanceof DeclareAnnotation) {
         DeclareAnnotation da = (DeclareAnnotation)declare;
         if (da.getAspect() == null) {
            da.setAspect(this.inAspect);
         }

         if (da.isDeclareAtType()) {
            this.declareAnnotationsOnType.add(da);
         } else if (da.isDeclareAtField()) {
            this.declareAnnotationsOnField.add(da);
         } else if (da.isDeclareAtMethod() || da.isDeclareAtConstuctor()) {
            this.declareAnnotationsOnMethods.add(da);
         }
      } else {
         if (!(declare instanceof DeclareTypeErrorOrWarning)) {
            throw new RuntimeException("unimplemented");
         }

         this.declareTypeEow.add((DeclareTypeErrorOrWarning)declare);
      }

   }

   public void exposeTypes(List typesToExpose) {
      Iterator i$ = typesToExpose.iterator();

      while(i$.hasNext()) {
         UnresolvedType typeToExpose = (UnresolvedType)i$.next();
         this.exposeType(typeToExpose);
      }

   }

   public void exposeType(UnresolvedType typeToExpose) {
      if (!ResolvedType.isMissing((UnresolvedType)typeToExpose)) {
         if (((UnresolvedType)typeToExpose).isParameterizedType() || ((UnresolvedType)typeToExpose).isRawType()) {
            if (typeToExpose instanceof ResolvedType) {
               typeToExpose = ((ResolvedType)typeToExpose).getGenericType();
            } else {
               typeToExpose = UnresolvedType.forSignature(((UnresolvedType)typeToExpose).getErasureSignature());
            }
         }

         String signatureToLookFor = ((UnresolvedType)typeToExpose).getSignature();
         Iterator iterator = this.typeMungers.iterator();

         while(iterator.hasNext()) {
            ConcreteTypeMunger cTM = (ConcreteTypeMunger)iterator.next();
            ResolvedTypeMunger rTM = cTM.getMunger();
            if (rTM != null && rTM instanceof ExposeTypeMunger) {
               String exposedType = ((ExposeTypeMunger)rTM).getExposedTypeSignature();
               if (exposedType.equals(signatureToLookFor)) {
                  return;
               }
            }
         }

         this.addTypeMunger(this.world.getWeavingSupport().concreteTypeMunger(new ExposeTypeMunger((UnresolvedType)typeToExpose), this.inAspect));
      }
   }

   public void addPrivilegedAccesses(Collection accessedMembers) {
      int version = this.inAspect.getCompilerVersion();
      Iterator i$ = accessedMembers.iterator();

      while(i$.hasNext()) {
         ResolvedMember member = (ResolvedMember)i$.next();
         ResolvedMember resolvedMember = this.world.resolve((Member)member);
         if (resolvedMember == null) {
            resolvedMember = member;
            if (member.hasBackingGenericMember()) {
               resolvedMember = member.getBackingGenericMember();
            }
         } else {
            UnresolvedType unresolvedDeclaringType = member.getDeclaringType().getRawType();
            UnresolvedType resolvedDeclaringType = resolvedMember.getDeclaringType().getRawType();
            if (!unresolvedDeclaringType.equals(resolvedDeclaringType)) {
               resolvedMember = member;
            }
         }

         PrivilegedAccessMunger privilegedAccessMunger = new PrivilegedAccessMunger(resolvedMember, version >= 7);
         ConcreteTypeMunger concreteTypeMunger = this.world.getWeavingSupport().concreteTypeMunger(privilegedAccessMunger, this.inAspect);
         this.addTypeMunger(concreteTypeMunger);
      }

   }

   public Collection getCflowEntries() {
      List ret = new ArrayList();
      Iterator i$ = this.shadowMungers.iterator();

      while(i$.hasNext()) {
         ShadowMunger m = (ShadowMunger)i$.next();
         if (m instanceof Advice) {
            Advice a = (Advice)m;
            if (a.getKind().isCflow()) {
               ret.add(a);
            }
         }
      }

      return ret;
   }

   public boolean replaceWith(CrosscuttingMembers other, boolean careAboutShadowMungers) {
      boolean changed = false;
      if (careAboutShadowMungers && (this.perClause == null || !this.perClause.equals(other.perClause))) {
         changed = true;
         this.perClause = other.perClause;
      }

      HashSet theseTypeMungers;
      HashSet otherTypeMungers;
      Iterator iter;
      HashSet trimmedThis;
      HashSet trimmedOther;
      if (careAboutShadowMungers) {
         theseTypeMungers = new HashSet();
         otherTypeMungers = new HashSet();
         iter = this.shadowMungers.iterator();

         label241:
         while(true) {
            while(true) {
               while(iter.hasNext()) {
                  ShadowMunger munger = (ShadowMunger)iter.next();
                  if (munger instanceof Advice) {
                     Advice adviceMunger = (Advice)munger;
                     if (!this.world.isXnoInline() && adviceMunger.getKind().equals(AdviceKind.Around)) {
                        otherTypeMungers.add(adviceMunger);
                     } else {
                        theseTypeMungers.add(adviceMunger);
                     }
                  } else {
                     theseTypeMungers.add(munger);
                  }
               }

               trimmedThis = new HashSet();
               trimmedThis.addAll(other.shadowMungers);
               trimmedOther = new HashSet();
               Set otherInlinedAroundMungers = new HashSet();
               Iterator i$ = trimmedThis.iterator();

               while(true) {
                  while(true) {
                     ShadowMunger munger;
                     while(i$.hasNext()) {
                        munger = (ShadowMunger)i$.next();
                        if (munger instanceof Advice) {
                           Advice adviceMunger = (Advice)munger;
                           if (!this.world.isXnoInline() && adviceMunger.getKind().equals(AdviceKind.Around)) {
                              otherInlinedAroundMungers.add(this.rewritePointcutInMunger(adviceMunger));
                           } else {
                              trimmedOther.add(this.rewritePointcutInMunger(adviceMunger));
                           }
                        } else {
                           trimmedOther.add(this.rewritePointcutInMunger(munger));
                        }
                     }

                     if (!theseTypeMungers.equals(trimmedOther)) {
                        changed = true;
                     }

                     if (!this.equivalent(otherTypeMungers, otherInlinedAroundMungers)) {
                        changed = true;
                     }

                     if (!changed) {
                        i$ = this.shadowMungers.iterator();

                        while(i$.hasNext()) {
                           munger = (ShadowMunger)i$.next();
                           int i = other.shadowMungers.indexOf(munger);
                           ShadowMunger otherMunger = (ShadowMunger)other.shadowMungers.get(i);
                           if (munger instanceof Advice) {
                              ((Advice)otherMunger).setHasMatchedSomething(((Advice)munger).hasMatchedSomething());
                           }
                        }
                     }

                     this.shadowMungers = other.shadowMungers;
                     break label241;
                  }
               }
            }
         }
      }

      theseTypeMungers = new HashSet();
      otherTypeMungers = new HashSet();
      if (!careAboutShadowMungers) {
         iter = this.typeMungers.iterator();

         Object o;
         ConcreteTypeMunger typeMunger;
         while(iter.hasNext()) {
            o = iter.next();
            if (o instanceof ConcreteTypeMunger) {
               typeMunger = (ConcreteTypeMunger)o;
               if (!typeMunger.existsToSupportShadowMunging()) {
                  theseTypeMungers.add(typeMunger);
               }
            } else {
               theseTypeMungers.add(o);
            }
         }

         iter = other.typeMungers.iterator();

         while(iter.hasNext()) {
            o = iter.next();
            if (o instanceof ConcreteTypeMunger) {
               typeMunger = (ConcreteTypeMunger)o;
               if (!typeMunger.existsToSupportShadowMunging()) {
                  otherTypeMungers.add(typeMunger);
               }
            } else {
               otherTypeMungers.add(o);
            }
         }
      } else {
         theseTypeMungers.addAll(this.typeMungers);
         otherTypeMungers.addAll(other.typeMungers);
      }

      Iterator iterator;
      if (theseTypeMungers.size() != otherTypeMungers.size()) {
         changed = true;
         this.typeMungers = other.typeMungers;
      } else {
         boolean shouldOverwriteThis = false;
         boolean foundInequality = false;
         iterator = theseTypeMungers.iterator();

         while(iterator.hasNext() && !foundInequality) {
            Object thisOne = iterator.next();
            boolean foundInOtherSet = false;
            Iterator i$ = otherTypeMungers.iterator();

            while(true) {
               while(i$.hasNext()) {
                  Object otherOne = i$.next();
                  if (thisOne instanceof ConcreteTypeMunger && ((ConcreteTypeMunger)thisOne).shouldOverwrite()) {
                     shouldOverwriteThis = true;
                  }

                  if (thisOne instanceof ConcreteTypeMunger && otherOne instanceof ConcreteTypeMunger) {
                     if (((ConcreteTypeMunger)thisOne).equivalentTo(otherOne)) {
                        foundInOtherSet = true;
                     } else if (thisOne.equals(otherOne)) {
                        foundInOtherSet = true;
                     }
                  } else if (thisOne.equals(otherOne)) {
                     foundInOtherSet = true;
                  }
               }

               if (!foundInOtherSet) {
                  foundInequality = true;
               }
               break;
            }
         }

         if (foundInequality) {
            changed = true;
         }

         if (shouldOverwriteThis) {
            this.typeMungers = other.typeMungers;
         }
      }

      if (!this.lateTypeMungers.equals(other.lateTypeMungers)) {
         changed = true;
         this.lateTypeMungers = other.lateTypeMungers;
      }

      if (!this.declareDominates.equals(other.declareDominates)) {
         changed = true;
         this.declareDominates = other.declareDominates;
      }

      if (!this.declareParents.equals(other.declareParents)) {
         if (!careAboutShadowMungers) {
            trimmedThis = new HashSet();
            Iterator iterator = this.declareParents.iterator();

            while(iterator.hasNext()) {
               DeclareParents decp = (DeclareParents)iterator.next();
               if (!decp.isMixin()) {
                  trimmedThis.add(decp);
               }
            }

            trimmedOther = new HashSet();
            iterator = other.declareParents.iterator();

            while(iterator.hasNext()) {
               DeclareParents decp = (DeclareParents)iterator.next();
               if (!decp.isMixin()) {
                  trimmedOther.add(decp);
               }
            }

            if (!trimmedThis.equals(trimmedOther)) {
               changed = true;
               this.declareParents = other.declareParents;
            }
         } else {
            changed = true;
            this.declareParents = other.declareParents;
         }
      }

      if (!this.declareSofts.equals(other.declareSofts)) {
         changed = true;
         this.declareSofts = other.declareSofts;
      }

      if (!this.declareAnnotationsOnType.equals(other.declareAnnotationsOnType)) {
         changed = true;
         this.declareAnnotationsOnType = other.declareAnnotationsOnType;
      }

      if (!this.declareAnnotationsOnField.equals(other.declareAnnotationsOnField)) {
         changed = true;
         this.declareAnnotationsOnField = other.declareAnnotationsOnField;
      }

      if (!this.declareAnnotationsOnMethods.equals(other.declareAnnotationsOnMethods)) {
         changed = true;
         this.declareAnnotationsOnMethods = other.declareAnnotationsOnMethods;
      }

      if (!this.declareTypeEow.equals(other.declareTypeEow)) {
         changed = true;
         this.declareTypeEow = other.declareTypeEow;
      }

      return changed;
   }

   private boolean equivalent(Set theseInlinedAroundMungers, Set otherInlinedAroundMungers) {
      if (theseInlinedAroundMungers.size() != otherInlinedAroundMungers.size()) {
         return false;
      } else {
         Iterator iter = theseInlinedAroundMungers.iterator();

         boolean foundIt;
         do {
            if (!iter.hasNext()) {
               return true;
            }

            Advice thisAdvice = (Advice)iter.next();
            foundIt = false;
            Iterator iterator = otherInlinedAroundMungers.iterator();

            while(iterator.hasNext()) {
               Advice otherAdvice = (Advice)iterator.next();
               if (thisAdvice.equals(otherAdvice)) {
                  if (!(thisAdvice.getSignature() instanceof ResolvedMemberImpl) || !((ResolvedMemberImpl)thisAdvice.getSignature()).isEquivalentTo(otherAdvice.getSignature())) {
                     return false;
                  }

                  foundIt = true;
               }
            }
         } while(foundIt);

         return false;
      }
   }

   private ShadowMunger rewritePointcutInMunger(ShadowMunger munger) {
      PointcutRewriter pr = new PointcutRewriter();
      Pointcut p = munger.getPointcut();
      Pointcut newP = pr.rewrite(p);
      if (p.m_ignoreUnboundBindingForNames.length != 0) {
         newP.m_ignoreUnboundBindingForNames = p.m_ignoreUnboundBindingForNames;
      }

      munger.setPointcut(newP);
      return munger;
   }

   public void setPerClause(PerClause perClause) {
      if (this.shouldConcretizeIfNeeded) {
         this.perClause = perClause.concretize(this.inAspect);
      } else {
         this.perClause = perClause;
      }

   }

   public List getDeclareDominates() {
      return this.declareDominates;
   }

   public Collection getDeclareParents() {
      return this.declareParents;
   }

   public Collection getDeclareSofts() {
      return this.declareSofts;
   }

   public List getShadowMungers() {
      return this.shadowMungers;
   }

   public List getTypeMungers() {
      return this.typeMungers;
   }

   public List getLateTypeMungers() {
      return this.lateTypeMungers;
   }

   public Collection getDeclareAnnotationOnTypes() {
      return this.declareAnnotationsOnType;
   }

   public Collection getDeclareAnnotationOnFields() {
      return this.declareAnnotationsOnField;
   }

   public Collection getDeclareAnnotationOnMethods() {
      return this.declareAnnotationsOnMethods;
   }

   public Collection getDeclareTypeErrorOrWarning() {
      return this.declareTypeEow;
   }

   public Map getCflowBelowFields() {
      return this.cflowBelowFields;
   }

   public Map getCflowFields() {
      return this.cflowFields;
   }

   public void clearCaches() {
      this.cflowFields.clear();
      this.cflowBelowFields.clear();
   }
}
