package com.bea.core.repackaged.aspectj.weaver;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class JoinPointSignatureIterator implements Iterator {
   ResolvedType firstDefiningType;
   private Member signaturesOfMember;
   private ResolvedMember firstDefiningMember;
   private World world;
   private List discoveredSignatures = new ArrayList();
   private List additionalSignatures = Collections.emptyList();
   private Iterator discoveredSignaturesIterator = null;
   private Iterator superTypeIterator = null;
   private boolean isProxy = false;
   private Set visitedSuperTypes = new HashSet();
   private List yetToBeProcessedSuperMembers = null;
   private boolean iteratingOverDiscoveredSignatures = true;
   private boolean couldBeFurtherAsYetUndiscoveredSignatures = true;
   private static final UnresolvedType jlrProxy = UnresolvedType.forSignature("Ljava/lang/reflect/Proxy;");

   public JoinPointSignatureIterator(Member joinPointSignature, World world) {
      this.signaturesOfMember = joinPointSignature;
      this.world = world;
      this.addSignaturesUpToFirstDefiningMember();
      if (!this.shouldWalkUpHierarchy()) {
         this.couldBeFurtherAsYetUndiscoveredSignatures = false;
      }

   }

   public void reset() {
      this.discoveredSignaturesIterator = this.discoveredSignatures.iterator();
      this.additionalSignatures.clear();
      this.iteratingOverDiscoveredSignatures = true;
   }

   public boolean hasNext() {
      if (this.iteratingOverDiscoveredSignatures && this.discoveredSignaturesIterator.hasNext()) {
         return true;
      } else if (this.couldBeFurtherAsYetUndiscoveredSignatures) {
         return this.additionalSignatures.size() > 0 ? true : this.findSignaturesFromSupertypes();
      } else {
         return false;
      }
   }

   public JoinPointSignature next() {
      if (this.iteratingOverDiscoveredSignatures && this.discoveredSignaturesIterator.hasNext()) {
         return (JoinPointSignature)this.discoveredSignaturesIterator.next();
      } else if (this.additionalSignatures.size() > 0) {
         return (JoinPointSignature)this.additionalSignatures.remove(0);
      } else {
         throw new NoSuchElementException();
      }
   }

   public void remove() {
      throw new UnsupportedOperationException("can't remove from JoinPointSignatureIterator");
   }

   private void addSignaturesUpToFirstDefiningMember() {
      ResolvedType originalDeclaringType = this.signaturesOfMember.getDeclaringType().resolve(this.world);
      ResolvedType superType = originalDeclaringType.getSuperclass();
      if (superType != null && superType.equals(jlrProxy)) {
         this.isProxy = true;
      }

      if (this.world.isJoinpointArrayConstructionEnabled() && originalDeclaringType.isArray()) {
         Member m = this.signaturesOfMember;
         ResolvedMember rm = new ResolvedMemberImpl(m.getKind(), m.getDeclaringType(), m.getModifiers(), m.getReturnType(), m.getName(), m.getParameterTypes());
         this.discoveredSignatures.add(new JoinPointSignature(rm, originalDeclaringType));
         this.couldBeFurtherAsYetUndiscoveredSignatures = false;
      } else {
         this.firstDefiningMember = this.signaturesOfMember instanceof ResolvedMember ? (ResolvedMember)this.signaturesOfMember : this.signaturesOfMember.resolve(this.world);
         if (this.firstDefiningMember == null) {
            this.couldBeFurtherAsYetUndiscoveredSignatures = false;
         } else {
            this.firstDefiningType = this.firstDefiningMember.getDeclaringType().resolve(this.world);
            if (this.firstDefiningType == originalDeclaringType || this.signaturesOfMember.getKind() != Member.CONSTRUCTOR) {
               if (originalDeclaringType == this.firstDefiningType) {
                  this.discoveredSignatures.add(new JoinPointSignature(this.firstDefiningMember, originalDeclaringType));
               } else {
                  List declaringTypes = new ArrayList();
                  this.accumulateTypesInBetween(originalDeclaringType, this.firstDefiningType, declaringTypes);
                  Iterator i$ = declaringTypes.iterator();

                  while(i$.hasNext()) {
                     ResolvedType declaringType = (ResolvedType)i$.next();
                     this.discoveredSignatures.add(new JoinPointSignature(this.firstDefiningMember, declaringType));
                  }
               }

            }
         }
      }
   }

   private void accumulateTypesInBetween(ResolvedType subType, ResolvedType superType, List types) {
      types.add(subType);
      if (subType != superType) {
         Iterator iter = subType.getDirectSupertypes();

         while(iter.hasNext()) {
            ResolvedType parent = (ResolvedType)iter.next();
            if (superType.isAssignableFrom(parent, true)) {
               this.accumulateTypesInBetween(parent, superType, types);
            }
         }

      }
   }

   private boolean shouldWalkUpHierarchy() {
      if (this.signaturesOfMember.getKind() == Member.CONSTRUCTOR) {
         return false;
      } else if (this.signaturesOfMember.getKind() == Member.FIELD) {
         return false;
      } else {
         return !Modifier.isStatic(this.signaturesOfMember.getModifiers());
      }
   }

   private boolean findSignaturesFromSupertypes() {
      this.iteratingOverDiscoveredSignatures = false;
      if (this.superTypeIterator == null) {
         this.superTypeIterator = this.firstDefiningType.getDirectSupertypes();
      }

      if (!this.superTypeIterator.hasNext()) {
         if (this.yetToBeProcessedSuperMembers != null && !this.yetToBeProcessedSuperMembers.isEmpty()) {
            SearchPair nextUp = (SearchPair)this.yetToBeProcessedSuperMembers.remove(0);
            this.firstDefiningType = nextUp.type;
            this.firstDefiningMember = nextUp.member;
            this.superTypeIterator = null;
            return this.findSignaturesFromSupertypes();
         } else {
            this.couldBeFurtherAsYetUndiscoveredSignatures = false;
            return false;
         }
      } else {
         ResolvedType superType = (ResolvedType)this.superTypeIterator.next();
         if (this.isProxy && (superType.isGenericType() || superType.isParameterizedType())) {
            superType = superType.getRawType();
         }

         if (this.visitedSuperTypes.contains(superType)) {
            return this.findSignaturesFromSupertypes();
         } else {
            this.visitedSuperTypes.add(superType);
            if (superType.isMissing()) {
               this.warnOnMissingType(superType);
               return this.findSignaturesFromSupertypes();
            } else {
               ResolvedMemberImpl foundMember = (ResolvedMemberImpl)superType.lookupResolvedMember(this.firstDefiningMember, true, this.isProxy);
               if (foundMember != null && this.isVisibleTo(this.firstDefiningMember, foundMember)) {
                  List declaringTypes = new ArrayList();
                  ResolvedType resolvedDeclaringType = foundMember.getDeclaringType().resolve(this.world);
                  this.accumulateTypesInBetween(superType, resolvedDeclaringType, declaringTypes);

                  JoinPointSignature member;
                  for(Iterator i$ = declaringTypes.iterator(); i$.hasNext(); this.additionalSignatures.add(member)) {
                     ResolvedType declaringType = (ResolvedType)i$.next();
                     member = null;
                     if (this.isProxy && (declaringType.isGenericType() || declaringType.isParameterizedType())) {
                        declaringType = declaringType.getRawType();
                     }

                     member = new JoinPointSignature(foundMember, declaringType);
                     this.discoveredSignatures.add(member);
                     if (this.additionalSignatures == Collections.EMPTY_LIST) {
                        this.additionalSignatures = new ArrayList();
                     }
                  }

                  if (!this.isProxy && superType.isParameterizedType() && foundMember.backingGenericMember != null) {
                     JoinPointSignature member = new JoinPointSignature(foundMember.backingGenericMember, foundMember.declaringType.resolve(this.world));
                     this.discoveredSignatures.add(member);
                     if (this.additionalSignatures == Collections.EMPTY_LIST) {
                        this.additionalSignatures = new ArrayList();
                     }

                     this.additionalSignatures.add(member);
                  }

                  if (this.yetToBeProcessedSuperMembers == null) {
                     this.yetToBeProcessedSuperMembers = new ArrayList();
                  }

                  this.yetToBeProcessedSuperMembers.add(new SearchPair(foundMember, superType));
                  return true;
               } else {
                  return this.findSignaturesFromSupertypes();
               }
            }
         }
      }
   }

   private boolean isVisibleTo(ResolvedMember childMember, ResolvedMember parentMember) {
      if (childMember.getDeclaringType().equals(parentMember.getDeclaringType())) {
         return true;
      } else {
         return !Modifier.isPrivate(parentMember.getModifiers());
      }
   }

   private void warnOnMissingType(ResolvedType missing) {
      if (missing instanceof MissingResolvedTypeWithKnownSignature) {
         MissingResolvedTypeWithKnownSignature mrt = (MissingResolvedTypeWithKnownSignature)missing;
         mrt.raiseWarningOnJoinPointSignature(this.signaturesOfMember.toString());
      }

   }

   private static class SearchPair {
      public ResolvedMember member;
      public ResolvedType type;

      public SearchPair(ResolvedMember member, ResolvedType type) {
         this.member = member;
         this.type = type;
      }
   }
}
