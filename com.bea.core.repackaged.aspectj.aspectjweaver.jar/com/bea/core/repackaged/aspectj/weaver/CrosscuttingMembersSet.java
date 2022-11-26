package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareParents;
import com.bea.core.repackaged.aspectj.weaver.patterns.IVerificationRequired;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CrosscuttingMembersSet {
   private transient World world;
   private final Map members = new HashMap();
   private transient List verificationList = null;
   private List shadowMungers = null;
   private List typeMungers = null;
   private List lateTypeMungers = null;
   private List declareSofts = null;
   private List declareParents = null;
   private List declareAnnotationOnTypes = null;
   private List declareAnnotationOnFields = null;
   private List declareAnnotationOnMethods = null;
   private List declareTypeEows = null;
   private List declareDominates = null;
   private boolean changedSinceLastReset = false;
   public int serializationVersion = 1;

   public CrosscuttingMembersSet(World world) {
      this.world = world;
   }

   public boolean addOrReplaceAspect(ResolvedType aspectType) {
      return this.addOrReplaceAspect(aspectType, true);
   }

   private boolean excludeDueToParentAspectHavingUnresolvedDependency(ResolvedType aspectType) {
      ResolvedType parent = aspectType.getSuperclass();

      boolean excludeDueToParent;
      for(excludeDueToParent = false; parent != null; parent = parent.getSuperclass()) {
         if (parent.isAspect() && parent.isAbstract() && this.world.hasUnsatisfiedDependency(parent)) {
            if (!this.world.getMessageHandler().isIgnoring(IMessage.INFO)) {
               this.world.getMessageHandler().handleMessage(MessageUtil.info("deactivating aspect '" + aspectType.getName() + "' as the parent aspect '" + parent.getName() + "' has unsatisfied dependencies"));
            }

            excludeDueToParent = true;
         }
      }

      return excludeDueToParent;
   }

   public boolean addOrReplaceAspect(ResolvedType aspectType, boolean inWeavingPhase) {
      if (!this.world.isAspectIncluded(aspectType)) {
         return false;
      } else if (this.world.hasUnsatisfiedDependency(aspectType)) {
         return false;
      } else if (this.excludeDueToParentAspectHavingUnresolvedDependency(aspectType)) {
         return false;
      } else {
         boolean change = false;
         CrosscuttingMembers xcut = (CrosscuttingMembers)this.members.get(aspectType);
         if (xcut == null) {
            this.members.put(aspectType, aspectType.collectCrosscuttingMembers(inWeavingPhase));
            this.clearCaches();
            change = true;
         } else if (xcut.replaceWith(aspectType.collectCrosscuttingMembers(inWeavingPhase), inWeavingPhase)) {
            this.clearCaches();
            change = true;
         } else {
            if (inWeavingPhase) {
               this.shadowMungers = null;
            }

            change = false;
         }

         if (aspectType.isAbstract()) {
            boolean ancestorChange = this.addOrReplaceDescendantsOf(aspectType, inWeavingPhase);
            change = change || ancestorChange;
         }

         this.changedSinceLastReset = this.changedSinceLastReset || change;
         return change;
      }
   }

   private boolean addOrReplaceDescendantsOf(ResolvedType aspectType, boolean inWeavePhase) {
      Set knownAspects = this.members.keySet();
      Set toBeReplaced = new HashSet();
      Iterator it = knownAspects.iterator();

      while(it.hasNext()) {
         ResolvedType candidateDescendant = (ResolvedType)it.next();
         if (candidateDescendant != aspectType && aspectType.isAssignableFrom(candidateDescendant, true)) {
            toBeReplaced.add(candidateDescendant);
         }
      }

      boolean change = false;

      boolean thisChange;
      for(Iterator it = toBeReplaced.iterator(); it.hasNext(); change = change || thisChange) {
         ResolvedType next = (ResolvedType)it.next();
         thisChange = this.addOrReplaceAspect(next, inWeavePhase);
      }

      return change;
   }

   public void addAdviceLikeDeclares(ResolvedType aspectType) {
      if (this.members.containsKey(aspectType)) {
         CrosscuttingMembers xcut = (CrosscuttingMembers)this.members.get(aspectType);
         xcut.addDeclares(aspectType.collectDeclares(true));
      }
   }

   public boolean deleteAspect(UnresolvedType aspectType) {
      boolean isAspect = this.members.remove(aspectType) != null;
      this.clearCaches();
      return isAspect;
   }

   public boolean containsAspect(UnresolvedType aspectType) {
      return this.members.containsKey(aspectType);
   }

   public void addFixedCrosscuttingMembers(ResolvedType aspectType) {
      this.members.put(aspectType, aspectType.crosscuttingMembers);
      this.clearCaches();
   }

   private void clearCaches() {
      this.shadowMungers = null;
      this.typeMungers = null;
      this.lateTypeMungers = null;
      this.declareSofts = null;
      this.declareParents = null;
      this.declareAnnotationOnFields = null;
      this.declareAnnotationOnMethods = null;
      this.declareAnnotationOnTypes = null;
      this.declareDominates = null;
   }

   public List getShadowMungers() {
      if (this.shadowMungers == null) {
         List ret = new ArrayList();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getShadowMungers());
         }

         this.shadowMungers = ret;
      }

      return this.shadowMungers;
   }

   public List getTypeMungers() {
      if (this.typeMungers == null) {
         List ret = new ArrayList();
         Iterator i$ = this.members.values().iterator();

         label48:
         while(i$.hasNext()) {
            CrosscuttingMembers xmembers = (CrosscuttingMembers)i$.next();
            Iterator i$ = xmembers.getTypeMungers().iterator();

            while(true) {
               while(true) {
                  if (!i$.hasNext()) {
                     continue label48;
                  }

                  ConcreteTypeMunger mungerToAdd = (ConcreteTypeMunger)i$.next();
                  ResolvedTypeMunger resolvedMungerToAdd = mungerToAdd.getMunger();
                  if (this.isNewStylePrivilegedAccessMunger(resolvedMungerToAdd)) {
                     String newFieldName = resolvedMungerToAdd.getSignature().getName();
                     boolean alreadyExists = false;
                     Iterator i$ = ret.iterator();

                     while(i$.hasNext()) {
                        ConcreteTypeMunger existingMunger = (ConcreteTypeMunger)i$.next();
                        ResolvedTypeMunger existing = existingMunger.getMunger();
                        if (this.isNewStylePrivilegedAccessMunger(existing)) {
                           String existingFieldName = existing.getSignature().getName();
                           if (existingFieldName.equals(newFieldName) && existing.getSignature().getDeclaringType().equals(resolvedMungerToAdd.getSignature().getDeclaringType())) {
                              alreadyExists = true;
                              break;
                           }
                        }
                     }

                     if (!alreadyExists) {
                        ret.add(mungerToAdd);
                     }
                  } else {
                     ret.add(mungerToAdd);
                  }
               }
            }
         }

         this.typeMungers = ret;
      }

      return this.typeMungers;
   }

   public List getTypeMungersOfKind(ResolvedTypeMunger.Kind kind) {
      List collected = null;
      Iterator i$ = this.typeMungers.iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger typeMunger = (ConcreteTypeMunger)i$.next();
         if (typeMunger.getMunger() != null && typeMunger.getMunger().getKind() == kind) {
            if (collected == null) {
               collected = new ArrayList();
            }

            collected.add(typeMunger);
         }
      }

      if (collected == null) {
         return Collections.emptyList();
      } else {
         return collected;
      }
   }

   private boolean isNewStylePrivilegedAccessMunger(ResolvedTypeMunger typeMunger) {
      boolean b = typeMunger != null && typeMunger.getKind() == ResolvedTypeMunger.PrivilegedAccess && typeMunger.getSignature().getKind() == Member.FIELD;
      if (!b) {
         return b;
      } else {
         PrivilegedAccessMunger privAccessMunger = (PrivilegedAccessMunger)typeMunger;
         return privAccessMunger.shortSyntax;
      }
   }

   public List getLateTypeMungers() {
      if (this.lateTypeMungers == null) {
         List ret = new ArrayList();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getLateTypeMungers());
         }

         this.lateTypeMungers = ret;
      }

      return this.lateTypeMungers;
   }

   public List getDeclareSofts() {
      if (this.declareSofts == null) {
         Set ret = new HashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareSofts());
         }

         this.declareSofts = new ArrayList();
         this.declareSofts.addAll(ret);
      }

      return this.declareSofts;
   }

   public List getDeclareParents() {
      if (this.declareParents == null) {
         Set ret = new HashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareParents());
         }

         this.declareParents = new ArrayList();
         this.declareParents.addAll(ret);
      }

      return this.declareParents;
   }

   public List getDeclareAnnotationOnTypes() {
      if (this.declareAnnotationOnTypes == null) {
         Set ret = new LinkedHashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareAnnotationOnTypes());
         }

         this.declareAnnotationOnTypes = new ArrayList();
         this.declareAnnotationOnTypes.addAll(ret);
      }

      return this.declareAnnotationOnTypes;
   }

   public List getDeclareAnnotationOnFields() {
      if (this.declareAnnotationOnFields == null) {
         Set ret = new LinkedHashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareAnnotationOnFields());
         }

         this.declareAnnotationOnFields = new ArrayList();
         this.declareAnnotationOnFields.addAll(ret);
      }

      return this.declareAnnotationOnFields;
   }

   public List getDeclareAnnotationOnMethods() {
      if (this.declareAnnotationOnMethods == null) {
         Set ret = new LinkedHashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareAnnotationOnMethods());
         }

         this.declareAnnotationOnMethods = new ArrayList();
         this.declareAnnotationOnMethods.addAll(ret);
      }

      return this.declareAnnotationOnMethods;
   }

   public List getDeclareTypeEows() {
      if (this.declareTypeEows == null) {
         Set ret = new HashSet();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareTypeErrorOrWarning());
         }

         this.declareTypeEows = new ArrayList();
         this.declareTypeEows.addAll(ret);
      }

      return this.declareTypeEows;
   }

   public List getDeclareDominates() {
      if (this.declareDominates == null) {
         List ret = new ArrayList();
         Iterator i = this.members.values().iterator();

         while(i.hasNext()) {
            ret.addAll(((CrosscuttingMembers)i.next()).getDeclareDominates());
         }

         this.declareDominates = ret;
      }

      return this.declareDominates;
   }

   public ResolvedType findAspectDeclaringParents(DeclareParents p) {
      Set keys = this.members.keySet();
      Iterator iter = keys.iterator();

      while(iter.hasNext()) {
         ResolvedType element = (ResolvedType)iter.next();
         Iterator i = ((CrosscuttingMembers)this.members.get(element)).getDeclareParents().iterator();

         while(i.hasNext()) {
            DeclareParents dp = (DeclareParents)i.next();
            if (dp.equals(p)) {
               return element;
            }
         }
      }

      return null;
   }

   public void reset() {
      this.verificationList = null;
      this.changedSinceLastReset = false;
   }

   public boolean hasChangedSinceLastReset() {
      return this.changedSinceLastReset;
   }

   public void recordNecessaryCheck(IVerificationRequired verification) {
      if (this.verificationList == null) {
         this.verificationList = new ArrayList();
      }

      this.verificationList.add(verification);
   }

   public void verify() {
      if (this.verificationList != null) {
         Iterator iter = this.verificationList.iterator();

         while(iter.hasNext()) {
            IVerificationRequired element = (IVerificationRequired)iter.next();
            element.verify();
         }

         this.verificationList = null;
      }
   }

   public void write(CompressingDataOutputStream stream) throws IOException {
      stream.writeInt(this.shadowMungers.size());
      Iterator iterator = this.shadowMungers.iterator();

      while(iterator.hasNext()) {
         ShadowMunger shadowMunger = (ShadowMunger)iterator.next();
         shadowMunger.write(stream);
      }

   }
}
