package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.patterns.Declare;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public abstract class ResolvedType extends UnresolvedType implements AnnotatedElement {
   public static final ResolvedType[] EMPTY_RESOLVED_TYPE_ARRAY = new ResolvedType[0];
   public static final String PARAMETERIZED_TYPE_IDENTIFIER = "P";
   public ResolvedType[] temporaryAnnotationTypes;
   private ResolvedType[] resolvedTypeParams;
   private String binaryPath;
   protected World world;
   protected int bits;
   private static int AnnotationBitsInitialized = 1;
   private static int AnnotationMarkedInherited = 2;
   private static int MungersAnalyzed = 4;
   private static int HasParentMunger = 8;
   private static int TypeHierarchyCompleteBit = 16;
   private static int GroovyObjectInitialized = 32;
   private static int IsGroovyObject = 64;
   protected static Set validBoxing = new HashSet();
   private static final MethodGetter MethodGetterInstance;
   private static final MethodGetterIncludingItds MethodGetterWithItdsInstance;
   private static final PointcutGetter PointcutGetterInstance;
   private static final FieldGetter FieldGetterInstance;
   public CrosscuttingMembers crosscuttingMembers;
   public static final ResolvedType[] NONE;
   public static final ResolvedType[] EMPTY_ARRAY;
   public static final Missing MISSING;
   protected List interTypeMungers = new ArrayList();
   private FuzzyBoolean parameterizedWithTypeVariable;

   protected ResolvedType(String signature, World world) {
      super(signature);
      this.parameterizedWithTypeVariable = FuzzyBoolean.MAYBE;
      this.world = world;
   }

   protected ResolvedType(String signature, String signatureErasure, World world) {
      super(signature, signatureErasure);
      this.parameterizedWithTypeVariable = FuzzyBoolean.MAYBE;
      this.world = world;
   }

   public int getSize() {
      return 1;
   }

   public final Iterator getDirectSupertypes() {
      Iterator interfacesIterator = Iterators.array(this.getDeclaredInterfaces());
      ResolvedType superclass = this.getSuperclass();
      return superclass == null ? interfacesIterator : Iterators.snoc(interfacesIterator, superclass);
   }

   public abstract ResolvedMember[] getDeclaredFields();

   public abstract ResolvedMember[] getDeclaredMethods();

   public abstract ResolvedType[] getDeclaredInterfaces();

   public abstract ResolvedMember[] getDeclaredPointcuts();

   public boolean isCacheable() {
      return true;
   }

   public abstract ResolvedType getSuperclass();

   public abstract int getModifiers();

   public boolean isMissing() {
      return false;
   }

   public static boolean isMissing(UnresolvedType unresolved) {
      if (unresolved instanceof ResolvedType) {
         ResolvedType resolved = (ResolvedType)unresolved;
         return resolved.isMissing();
      } else {
         return unresolved == MISSING;
      }
   }

   public ResolvedType[] getAnnotationTypes() {
      return EMPTY_RESOLVED_TYPE_ARRAY;
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      return null;
   }

   public ResolvedType getResolvedComponentType() {
      return null;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean equals(Object other) {
      if (other instanceof ResolvedType) {
         return this == other;
      } else {
         return super.equals(other);
      }
   }

   public Iterator getFields() {
      final Iterators.Filter dupFilter = Iterators.dupFilter();
      Iterators.Getter typeGetter = new Iterators.Getter() {
         public Iterator get(ResolvedType o) {
            return dupFilter.filter(o.getDirectSupertypes());
         }
      };
      return Iterators.mapOver(Iterators.recur(this, typeGetter), FieldGetterInstance);
   }

   public Iterator getMethods(boolean wantGenerics, boolean wantDeclaredParents) {
      return Iterators.mapOver(this.getHierarchy(wantGenerics, wantDeclaredParents), MethodGetterInstance);
   }

   public Iterator getMethodsIncludingIntertypeDeclarations(boolean wantGenerics, boolean wantDeclaredParents) {
      return Iterators.mapOver(this.getHierarchy(wantGenerics, wantDeclaredParents), MethodGetterWithItdsInstance);
   }

   public Iterator getHierarchy() {
      return this.getHierarchy(false, false);
   }

   public Iterator getHierarchy(final boolean wantGenerics, final boolean wantDeclaredParents) {
      Iterators.Getter interfaceGetter = new Iterators.Getter() {
         List alreadySeen = new ArrayList();

         public Iterator get(ResolvedType type) {
            ResolvedType[] interfaces = type.getDeclaredInterfaces();
            if (!wantDeclaredParents && type.hasNewParentMungers()) {
               List forRemoval = new ArrayList();
               Iterator i$ = type.interTypeMungers.iterator();

               while(true) {
                  ResolvedTypeMunger m;
                  do {
                     ConcreteTypeMunger munger;
                     do {
                        if (!i$.hasNext()) {
                           if (forRemoval.size() > 0) {
                              ResolvedType[] interfaces2 = new ResolvedType[interfaces.length - forRemoval.size()];
                              int p = 0;

                              for(int ii = 0; ii < interfaces.length; ++ii) {
                                 if (!forRemoval.contains(ii)) {
                                    interfaces2[p++] = interfaces[ii];
                                 }
                              }

                              interfaces = interfaces2;
                           }

                           return new Iterators.ResolvedTypeArrayIterator(interfaces, this.alreadySeen, wantGenerics);
                        }

                        munger = (ConcreteTypeMunger)i$.next();
                     } while(munger.getMunger() == null);

                     m = munger.getMunger();
                  } while(m.getKind() != ResolvedTypeMunger.Parent);

                  ResolvedType newType = ((NewParentTypeMunger)m).getNewParent();
                  if (!wantGenerics && newType.isParameterizedOrGenericType()) {
                     newType = newType.getRawType();
                  }

                  for(int iix = 0; iix < interfaces.length; ++iix) {
                     ResolvedType iface = interfaces[iix];
                     if (!wantGenerics && iface.isParameterizedOrGenericType()) {
                        iface = iface.getRawType();
                     }

                     if (newType.getSignature().equals(iface.getSignature())) {
                        forRemoval.add(iix);
                     }
                  }
               }
            } else {
               return new Iterators.ResolvedTypeArrayIterator(interfaces, this.alreadySeen, wantGenerics);
            }
         }
      };
      if (this.isInterface()) {
         return new SuperInterfaceWalker(interfaceGetter, this);
      } else {
         SuperInterfaceWalker superInterfaceWalker = new SuperInterfaceWalker(interfaceGetter);
         Iterator superClassesIterator = new SuperClassWalker(this, superInterfaceWalker, wantGenerics);
         return Iterators.append1(superClassesIterator, superInterfaceWalker);
      }
   }

   public List getMethodsWithoutIterator(boolean includeITDs, boolean allowMissing, boolean genericsAware) {
      List methods = new ArrayList();
      Set knowninterfaces = new HashSet();
      this.addAndRecurse(knowninterfaces, methods, this, includeITDs, allowMissing, genericsAware);
      return methods;
   }

   public List getHierarchyWithoutIterator(boolean includeITDs, boolean allowMissing, boolean genericsAware) {
      List types = new ArrayList();
      Set visited = new HashSet();
      this.recurseHierarchy(visited, types, this, includeITDs, allowMissing, genericsAware);
      return types;
   }

   private void addAndRecurse(Set knowninterfaces, List collector, ResolvedType resolvedType, boolean includeITDs, boolean allowMissing, boolean genericsAware) {
      collector.addAll(Arrays.asList(resolvedType.getDeclaredMethods()));
      if (includeITDs && resolvedType.interTypeMungers != null) {
         Iterator i$ = this.interTypeMungers.iterator();

         while(i$.hasNext()) {
            ConcreteTypeMunger typeTransformer = (ConcreteTypeMunger)i$.next();
            ResolvedMember rm = typeTransformer.getSignature();
            if (rm != null) {
               collector.add(typeTransformer.getSignature());
            }
         }
      }

      if (!resolvedType.isInterface() && !resolvedType.equals(OBJECT)) {
         ResolvedType superType = resolvedType.getSuperclass();
         if (superType != null && !superType.isMissing()) {
            if (!genericsAware && superType.isParameterizedOrGenericType()) {
               superType = superType.getRawType();
            }

            this.addAndRecurse(knowninterfaces, collector, superType, includeITDs, allowMissing, genericsAware);
         }
      }

      ResolvedType[] interfaces = resolvedType.getDeclaredInterfaces();

      for(int i = 0; i < interfaces.length; ++i) {
         ResolvedType iface = interfaces[i];
         if (!genericsAware && iface.isParameterizedOrGenericType()) {
            iface = iface.getRawType();
         }

         boolean shouldSkip = false;

         for(int j = 0; j < resolvedType.interTypeMungers.size(); ++j) {
            ConcreteTypeMunger munger = (ConcreteTypeMunger)resolvedType.interTypeMungers.get(j);
            if (munger.getMunger() != null && munger.getMunger().getKind() == ResolvedTypeMunger.Parent && ((NewParentTypeMunger)munger.getMunger()).getNewParent().equals(iface)) {
               shouldSkip = true;
               break;
            }
         }

         if (!shouldSkip && !knowninterfaces.contains(iface.getSignature())) {
            knowninterfaces.add(iface.getSignature());
            if (allowMissing && iface.isMissing()) {
               if (iface instanceof MissingResolvedTypeWithKnownSignature) {
                  ((MissingResolvedTypeWithKnownSignature)iface).raiseWarningOnMissingInterfaceWhilstFindingMethods();
               }
            } else {
               this.addAndRecurse(knowninterfaces, collector, iface, includeITDs, allowMissing, genericsAware);
            }
         }
      }

   }

   private void recurseHierarchy(Set knowninterfaces, List collector, ResolvedType resolvedType, boolean includeITDs, boolean allowMissing, boolean genericsAware) {
      collector.add(resolvedType);
      if (!resolvedType.isInterface() && !resolvedType.equals(OBJECT)) {
         ResolvedType superType = resolvedType.getSuperclass();
         if (superType != null && !superType.isMissing()) {
            if (!genericsAware && (superType.isParameterizedType() || superType.isGenericType())) {
               superType = superType.getRawType();
            }

            this.recurseHierarchy(knowninterfaces, collector, superType, includeITDs, allowMissing, genericsAware);
         }
      }

      ResolvedType[] interfaces = resolvedType.getDeclaredInterfaces();

      for(int i = 0; i < interfaces.length; ++i) {
         ResolvedType iface = interfaces[i];
         if (!genericsAware && (iface.isParameterizedType() || iface.isGenericType())) {
            iface = iface.getRawType();
         }

         boolean shouldSkip = false;

         for(int j = 0; j < resolvedType.interTypeMungers.size(); ++j) {
            ConcreteTypeMunger munger = (ConcreteTypeMunger)resolvedType.interTypeMungers.get(j);
            if (munger.getMunger() != null && munger.getMunger().getKind() == ResolvedTypeMunger.Parent && ((NewParentTypeMunger)munger.getMunger()).getNewParent().equals(iface)) {
               shouldSkip = true;
               break;
            }
         }

         if (!shouldSkip && !knowninterfaces.contains(iface.getSignature())) {
            knowninterfaces.add(iface.getSignature());
            if (allowMissing && iface.isMissing()) {
               if (iface instanceof MissingResolvedTypeWithKnownSignature) {
                  ((MissingResolvedTypeWithKnownSignature)iface).raiseWarningOnMissingInterfaceWhilstFindingMethods();
               }
            } else {
               this.recurseHierarchy(knowninterfaces, collector, iface, includeITDs, allowMissing, genericsAware);
            }
         }
      }

   }

   public ResolvedType[] getResolvedTypeParameters() {
      if (this.resolvedTypeParams == null) {
         this.resolvedTypeParams = this.world.resolve(this.typeParameters);
      }

      return this.resolvedTypeParams;
   }

   public ResolvedMember lookupField(Member field) {
      Iterator i = this.getFields();

      ResolvedMember resolvedMember;
      do {
         if (!i.hasNext()) {
            return null;
         }

         resolvedMember = (ResolvedMember)i.next();
         if (matches(resolvedMember, field)) {
            return resolvedMember;
         }
      } while(!resolvedMember.hasBackingGenericMember() || !field.getName().equals(resolvedMember.getName()) || !matches(resolvedMember.getBackingGenericMember(), field));

      return resolvedMember;
   }

   public ResolvedMember lookupMethod(Member m) {
      List typesTolookat = new ArrayList();
      typesTolookat.add(this);
      int pos = 0;

      while(true) {
         ResolvedType[] superinterfaces;
         do {
            if (pos >= typesTolookat.size()) {
               return null;
            }

            ResolvedType type = (ResolvedType)typesTolookat.get(pos++);
            if (!type.isMissing()) {
               ResolvedMember[] methods = type.getDeclaredMethods();
               if (methods != null) {
                  for(int i = 0; i < methods.length; ++i) {
                     ResolvedMember method = methods[i];
                     if (matches(method, m)) {
                        return method;
                     }

                     if (method.hasBackingGenericMember() && m.getName().equals(method.getName()) && matches(method.getBackingGenericMember(), m)) {
                        return method;
                     }
                  }
               }
            }

            ResolvedType superclass = type.getSuperclass();
            if (superclass != null) {
               typesTolookat.add(superclass);
            }

            superinterfaces = type.getDeclaredInterfaces();
         } while(superinterfaces == null);

         for(int i = 0; i < superinterfaces.length; ++i) {
            ResolvedType interf = superinterfaces[i];
            if (!typesTolookat.contains(interf)) {
               typesTolookat.add(interf);
            }
         }
      }
   }

   public ResolvedMember lookupMethodInITDs(Member member) {
      Iterator i$ = this.interTypeMungers.iterator();

      ConcreteTypeMunger typeTransformer;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         typeTransformer = (ConcreteTypeMunger)i$.next();
      } while(!matches(typeTransformer.getSignature(), member));

      return typeTransformer.getSignature();
   }

   private ResolvedMember lookupMember(Member m, ResolvedMember[] a) {
      for(int i = 0; i < a.length; ++i) {
         ResolvedMember f = a[i];
         if (matches(f, m)) {
            return f;
         }
      }

      return null;
   }

   public ResolvedMember lookupResolvedMember(ResolvedMember aMember, boolean allowMissing, boolean eraseGenerics) {
      Iterator toSearch = null;
      ResolvedMember found = null;
      if (aMember.getKind() != Member.METHOD && aMember.getKind() != Member.CONSTRUCTOR) {
         if (aMember.getKind() == Member.ADVICE) {
            return null;
         }

         assert aMember.getKind() == Member.FIELD;

         toSearch = this.getFields();
      } else {
         toSearch = this.getMethodsIncludingIntertypeDeclarations(!eraseGenerics, true);
      }

      while(toSearch.hasNext()) {
         ResolvedMember candidate = (ResolvedMember)toSearch.next();
         if (eraseGenerics && candidate.hasBackingGenericMember()) {
            candidate = candidate.getBackingGenericMember();
         }

         if (candidate.matches(aMember, eraseGenerics)) {
            found = candidate;
            break;
         }
      }

      return found;
   }

   public static boolean matches(Member m1, Member m2) {
      if (m1 == null) {
         return m2 == null;
      } else if (m2 == null) {
         return false;
      } else {
         boolean equalNames = m1.getName().equals(m2.getName());
         if (!equalNames) {
            return false;
         } else {
            boolean equalSignatures = m1.getSignature().equals(m2.getSignature());
            if (equalSignatures) {
               return true;
            } else {
               boolean equalCovariantSignatures = m1.getParameterSignature().equals(m2.getParameterSignature());
               return equalCovariantSignatures;
            }
         }
      }
   }

   public static boolean conflictingSignature(Member m1, Member m2) {
      return conflictingSignature(m1, m2, true);
   }

   public static boolean conflictingSignature(Member m1, Member m2, boolean v2itds) {
      if (m1 != null && m2 != null) {
         if (!m1.getName().equals(m2.getName())) {
            return false;
         } else if (m1.getKind() != m2.getKind()) {
            return false;
         } else {
            if (m1.getKind() == Member.FIELD) {
               if (!v2itds) {
                  return m1.getDeclaringType().equals(m2.getDeclaringType());
               }

               if (m1.getDeclaringType().equals(m2.getDeclaringType())) {
                  return true;
               }
            } else if (m1.getKind() == Member.POINTCUT) {
               return true;
            }

            UnresolvedType[] p1 = m1.getGenericParameterTypes();
            UnresolvedType[] p2 = m2.getGenericParameterTypes();
            if (p1 == null) {
               p1 = m1.getParameterTypes();
            }

            if (p2 == null) {
               p2 = m2.getParameterTypes();
            }

            int n = p1.length;
            if (n != p2.length) {
               return false;
            } else {
               for(int i = 0; i < n; ++i) {
                  if (!p1[i].equals(p2[i])) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public Iterator getPointcuts() {
      final Iterators.Filter dupFilter = Iterators.dupFilter();
      Iterators.Getter typeGetter = new Iterators.Getter() {
         public Iterator get(ResolvedType o) {
            return dupFilter.filter(o.getDirectSupertypes());
         }
      };
      return Iterators.mapOver(Iterators.recur(this, typeGetter), PointcutGetterInstance);
   }

   public ResolvedPointcutDefinition findPointcut(String name) {
      Iterator i = this.getPointcuts();

      ResolvedPointcutDefinition f;
      do {
         if (!i.hasNext()) {
            if (!this.getOutermostType().equals(this)) {
               ResolvedType outerType = this.getOutermostType().resolve(this.world);
               f = outerType.findPointcut(name);
               return f;
            }

            return null;
         }

         f = (ResolvedPointcutDefinition)i.next();
      } while(f == null || !name.equals(f.getName()));

      return f;
   }

   public CrosscuttingMembers collectCrosscuttingMembers(boolean shouldConcretizeIfNeeded) {
      this.crosscuttingMembers = new CrosscuttingMembers(this, shouldConcretizeIfNeeded);
      if (this.getPerClause() == null) {
         return this.crosscuttingMembers;
      } else {
         this.crosscuttingMembers.setPerClause(this.getPerClause());
         this.crosscuttingMembers.addShadowMungers(this.collectShadowMungers());
         this.crosscuttingMembers.addTypeMungers(this.getTypeMungers());
         this.crosscuttingMembers.addDeclares(this.collectDeclares(!this.doesNotExposeShadowMungers()));
         this.crosscuttingMembers.addPrivilegedAccesses(this.getPrivilegedAccesses());
         return this.crosscuttingMembers;
      }
   }

   public final List collectDeclares(boolean includeAdviceLike) {
      if (!this.isAspect()) {
         return Collections.emptyList();
      } else {
         List ret = new ArrayList();
         if (!this.isAbstract()) {
            final Iterators.Filter dupFilter = Iterators.dupFilter();
            Iterators.Getter typeGetter = new Iterators.Getter() {
               public Iterator get(ResolvedType o) {
                  return dupFilter.filter(o.getDirectSupertypes());
               }
            };
            Iterator typeIterator = Iterators.recur(this, typeGetter);

            while(typeIterator.hasNext()) {
               ResolvedType ty = (ResolvedType)typeIterator.next();
               Iterator i = ty.getDeclares().iterator();

               while(i.hasNext()) {
                  Declare dec = (Declare)i.next();
                  if (dec.isAdviceLike()) {
                     if (includeAdviceLike) {
                        ret.add(dec);
                     }
                  } else {
                     ret.add(dec);
                  }
               }
            }
         }

         return ret;
      }
   }

   private final List collectShadowMungers() {
      if (this.isAspect() && !this.isAbstract() && !this.doesNotExposeShadowMungers()) {
         List acc = new ArrayList();
         final Iterators.Filter dupFilter = Iterators.dupFilter();
         Iterators.Getter typeGetter = new Iterators.Getter() {
            public Iterator get(ResolvedType o) {
               return dupFilter.filter(o.getDirectSupertypes());
            }
         };
         Iterator typeIterator = Iterators.recur(this, typeGetter);

         while(typeIterator.hasNext()) {
            ResolvedType ty = (ResolvedType)typeIterator.next();
            acc.addAll(ty.getDeclaredShadowMungers());
         }

         return acc;
      } else {
         return Collections.emptyList();
      }
   }

   public void addParent(ResolvedType newParent) {
   }

   protected boolean doesNotExposeShadowMungers() {
      return false;
   }

   public PerClause getPerClause() {
      return null;
   }

   public Collection getDeclares() {
      return Collections.emptyList();
   }

   public Collection getTypeMungers() {
      return Collections.emptyList();
   }

   public Collection getPrivilegedAccesses() {
      return Collections.emptyList();
   }

   public final boolean isInterface() {
      return Modifier.isInterface(this.getModifiers());
   }

   public final boolean isAbstract() {
      return Modifier.isAbstract(this.getModifiers());
   }

   public boolean isClass() {
      return false;
   }

   public boolean isAspect() {
      return false;
   }

   public boolean isAnnotationStyleAspect() {
      return false;
   }

   public boolean isEnum() {
      return false;
   }

   public boolean isAnnotation() {
      return false;
   }

   public boolean isAnonymous() {
      return false;
   }

   public boolean isNested() {
      return false;
   }

   public ResolvedType getOuterClass() {
      return null;
   }

   public void addAnnotation(AnnotationAJ annotationX) {
      throw new RuntimeException("ResolvedType.addAnnotation() should never be called");
   }

   public AnnotationAJ[] getAnnotations() {
      throw new RuntimeException("ResolvedType.getAnnotations() should never be called");
   }

   public boolean hasAnnotations() {
      throw new RuntimeException("ResolvedType.getAnnotations() should never be called");
   }

   public boolean canAnnotationTargetType() {
      return false;
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      return null;
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return false;
   }

   public boolean isSynthetic() {
      return this.signature.indexOf("$ajc") != -1;
   }

   public final boolean isFinal() {
      return Modifier.isFinal(this.getModifiers());
   }

   protected Map getMemberParameterizationMap() {
      if (!this.isParameterizedType()) {
         return Collections.emptyMap();
      } else {
         TypeVariable[] tvs = this.getGenericType().getTypeVariables();
         Map parameterizationMap = new HashMap();

         for(int i = 0; i < tvs.length; ++i) {
            parameterizationMap.put(tvs[i].getName(), this.typeParameters[i]);
         }

         return parameterizationMap;
      }
   }

   public List getDeclaredAdvice() {
      List l = new ArrayList();
      ResolvedMember[] methods = this.getDeclaredMethods();
      if (this.isParameterizedType()) {
         methods = this.getGenericType().getDeclaredMethods();
      }

      Map typeVariableMap = this.getAjMemberParameterizationMap();
      int i = 0;

      for(int len = methods.length; i < len; ++i) {
         ShadowMunger munger = methods[i].getAssociatedShadowMunger();
         if (munger != null) {
            if (this.ajMembersNeedParameterization()) {
               munger = munger.parameterizeWith(this, typeVariableMap);
               if (munger instanceof Advice) {
                  Advice advice = (Advice)munger;
                  UnresolvedType[] ptypes = methods[i].getGenericParameterTypes();
                  UnresolvedType[] newPTypes = new UnresolvedType[ptypes.length];

                  for(int j = 0; j < ptypes.length; ++j) {
                     if (ptypes[j] instanceof TypeVariableReferenceType) {
                        TypeVariableReferenceType tvrt = (TypeVariableReferenceType)ptypes[j];
                        if (typeVariableMap.containsKey(tvrt.getTypeVariable().getName())) {
                           newPTypes[j] = (UnresolvedType)typeVariableMap.get(tvrt.getTypeVariable().getName());
                        } else {
                           newPTypes[j] = ptypes[j];
                        }
                     } else {
                        newPTypes[j] = ptypes[j];
                     }
                  }

                  advice.setBindingParameterTypes(newPTypes);
               }
            }

            munger.setDeclaringType(this);
            l.add(munger);
         }
      }

      return l;
   }

   public List getDeclaredShadowMungers() {
      return this.getDeclaredAdvice();
   }

   public ResolvedMember[] getDeclaredJavaFields() {
      return this.filterInJavaVisible(this.getDeclaredFields());
   }

   public ResolvedMember[] getDeclaredJavaMethods() {
      return this.filterInJavaVisible(this.getDeclaredMethods());
   }

   private ResolvedMember[] filterInJavaVisible(ResolvedMember[] ms) {
      List l = new ArrayList();
      int i = 0;

      for(int len = ms.length; i < len; ++i) {
         if (!ms[i].isAjSynthetic() && ms[i].getAssociatedShadowMunger() == null) {
            l.add(ms[i]);
         }
      }

      return (ResolvedMember[])l.toArray(new ResolvedMember[l.size()]);
   }

   public abstract ISourceContext getSourceContext();

   public static ResolvedType makeArray(ResolvedType type, int dim) {
      if (dim == 0) {
         return type;
      } else {
         ResolvedType array = new ArrayReferenceType("[" + type.getSignature(), "[" + type.getErasureSignature(), type.getWorld(), type);
         return makeArray(array, dim - 1);
      }
   }

   public ResolvedMember lookupMemberNoSupers(Member member) {
      ResolvedMember ret = this.lookupDirectlyDeclaredMemberNoSupers(member);
      if (ret == null && this.interTypeMungers != null) {
         Iterator i$ = this.interTypeMungers.iterator();

         while(i$.hasNext()) {
            ConcreteTypeMunger tm = (ConcreteTypeMunger)i$.next();
            if (matches(tm.getSignature(), member)) {
               return tm.getSignature();
            }
         }
      }

      return ret;
   }

   public ResolvedMember lookupMemberWithSupersAndITDs(Member member) {
      ResolvedMember ret = this.lookupMemberNoSupers(member);
      if (ret != null) {
         return ret;
      } else {
         ResolvedType supert = this.getSuperclass();

         while(ret == null && supert != null) {
            ret = supert.lookupMemberNoSupers(member);
            if (ret == null) {
               supert = supert.getSuperclass();
            }
         }

         return ret;
      }
   }

   public ResolvedMember lookupDirectlyDeclaredMemberNoSupers(Member member) {
      ResolvedMember ret;
      if (member.getKind() == Member.FIELD) {
         ret = this.lookupMember(member, this.getDeclaredFields());
      } else {
         ret = this.lookupMember(member, this.getDeclaredMethods());
      }

      return ret;
   }

   public ResolvedMember lookupMemberIncludingITDsOnInterfaces(Member member) {
      return this.lookupMemberIncludingITDsOnInterfaces(member, this);
   }

   private ResolvedMember lookupMemberIncludingITDsOnInterfaces(Member member, ResolvedType onType) {
      ResolvedMember ret = onType.lookupMemberNoSupers(member);
      if (ret != null) {
         return ret;
      } else {
         ResolvedType superType = onType.getSuperclass();
         if (superType != null) {
            ret = this.lookupMemberIncludingITDsOnInterfaces(member, superType);
         }

         if (ret == null) {
            ResolvedType[] superInterfaces = onType.getDeclaredInterfaces();

            for(int i = 0; i < superInterfaces.length; ++i) {
               ret = superInterfaces[i].lookupMethodInITDs(member);
               if (ret != null) {
                  return ret;
               }
            }
         }

         return ret;
      }
   }

   public List getInterTypeMungers() {
      return this.interTypeMungers;
   }

   public List getInterTypeParentMungers() {
      List l = new ArrayList();
      Iterator i$ = this.interTypeMungers.iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger element = (ConcreteTypeMunger)i$.next();
         if (element.getMunger() instanceof NewParentTypeMunger) {
            l.add(element);
         }
      }

      return l;
   }

   public List getInterTypeMungersIncludingSupers() {
      ArrayList ret = new ArrayList();
      this.collectInterTypeMungers(ret);
      return ret;
   }

   public List getInterTypeParentMungersIncludingSupers() {
      ArrayList ret = new ArrayList();
      this.collectInterTypeParentMungers(ret);
      return ret;
   }

   private void collectInterTypeParentMungers(List collector) {
      Iterator iter = this.getDirectSupertypes();

      while(iter.hasNext()) {
         ResolvedType superType = (ResolvedType)iter.next();
         superType.collectInterTypeParentMungers(collector);
      }

      collector.addAll(this.getInterTypeParentMungers());
   }

   protected void collectInterTypeMungers(List collector) {
      Iterator iter1 = this.getDirectSupertypes();

      while(iter1.hasNext()) {
         ResolvedType superType = (ResolvedType)iter1.next();
         if (superType == null) {
            throw new BCException("UnexpectedProblem: a supertype in the hierarchy for " + this.getName() + " is null");
         }

         superType.collectInterTypeMungers(collector);
      }

      iter1 = collector.iterator();

      while(true) {
         label54:
         while(true) {
            ConcreteTypeMunger superMunger;
            do {
               do {
                  if (!iter1.hasNext()) {
                     collector.addAll(this.getInterTypeMungers());
                     return;
                  }

                  superMunger = (ConcreteTypeMunger)iter1.next();
               } while(superMunger.getSignature() == null);
            } while(!superMunger.getSignature().isAbstract());

            Iterator iter = this.getInterTypeMungers().iterator();

            while(iter.hasNext()) {
               ConcreteTypeMunger myMunger = (ConcreteTypeMunger)iter.next();
               if (conflictingSignature(myMunger.getSignature(), superMunger.getSignature())) {
                  iter1.remove();
                  continue label54;
               }
            }

            if (superMunger.getSignature().isPublic()) {
               iter = this.getMethods(true, true);

               ResolvedMember method;
               do {
                  if (!iter.hasNext()) {
                     continue label54;
                  }

                  method = (ResolvedMember)iter.next();
               } while(!conflictingSignature(method, superMunger.getSignature()));

               iter1.remove();
            }
         }
      }
   }

   public void checkInterTypeMungers() {
      if (!this.isAbstract()) {
         boolean itdProblem = false;

         Iterator i$;
         ConcreteTypeMunger munger;
         for(i$ = this.getInterTypeMungersIncludingSupers().iterator(); i$.hasNext(); itdProblem = this.checkAbstractDeclaration(munger) || itdProblem) {
            munger = (ConcreteTypeMunger)i$.next();
         }

         if (!itdProblem) {
            i$ = this.getInterTypeMungersIncludingSupers().iterator();

            while(i$.hasNext()) {
               munger = (ConcreteTypeMunger)i$.next();
               if (munger.getSignature() != null && munger.getSignature().isAbstract() && munger.getMunger().getKind() != ResolvedTypeMunger.PrivilegedAccess && munger.getMunger().getKind() != ResolvedTypeMunger.MethodDelegate2) {
                  this.world.getMessageHandler().handleMessage(new Message("must implement abstract inter-type declaration: " + munger.getSignature(), "", IMessage.ERROR, this.getSourceLocation(), (Throwable)null, new ISourceLocation[]{this.getMungerLocation(munger)}));
               }
            }

         }
      }
   }

   private boolean checkAbstractDeclaration(ConcreteTypeMunger munger) {
      if (munger.getMunger() != null && munger.getMunger() instanceof NewMethodTypeMunger) {
         ResolvedMember itdMember = munger.getSignature();
         ResolvedType onType = itdMember.getDeclaringType().resolve(this.world);
         if (onType.isInterface() && itdMember.isAbstract() && !itdMember.isPublic()) {
            this.world.getMessageHandler().handleMessage(new Message(WeaverMessages.format("itdAbstractMustBePublicOnInterface", munger.getSignature(), onType), "", Message.ERROR, this.getSourceLocation(), (Throwable)null, new ISourceLocation[]{this.getMungerLocation(munger)}));
            return true;
         }
      }

      return false;
   }

   private ISourceLocation getMungerLocation(ConcreteTypeMunger munger) {
      ISourceLocation sloc = munger.getSourceLocation();
      if (sloc == null) {
         sloc = munger.getAspectType().getSourceLocation();
      }

      return sloc;
   }

   public ResolvedType getDeclaringType() {
      if (this.isArray()) {
         return null;
      } else {
         return !this.isNested() && !this.isAnonymous() ? null : this.getOuterClass();
      }
   }

   public static boolean isVisible(int modifiers, ResolvedType targetType, ResolvedType fromType) {
      if (Modifier.isPublic(modifiers)) {
         return true;
      } else if (Modifier.isPrivate(modifiers)) {
         return targetType.getOutermostType().equals(fromType.getOutermostType());
      } else if (!Modifier.isProtected(modifiers)) {
         return samePackage(targetType, fromType);
      } else {
         return samePackage(targetType, fromType) || targetType.isAssignableFrom(fromType);
      }
   }

   private static boolean samePackage(ResolvedType targetType, ResolvedType fromType) {
      String p1 = targetType.getPackageName();
      String p2 = fromType.getPackageName();
      if (p1 == null) {
         return p2 == null;
      } else {
         return p2 == null ? false : p1.equals(p2);
      }
   }

   private boolean genericTypeEquals(ResolvedType other) {
      if (other.isParameterizedType() || other.isRawType()) {
         other.getGenericType();
      }

      return (this.isParameterizedType() || this.isRawType()) && this.getGenericType().equals(other) || this.equals(other);
   }

   public ResolvedType discoverActualOccurrenceOfTypeInHierarchy(ResolvedType lookingFor) {
      if (!lookingFor.isGenericType()) {
         throw new BCException("assertion failed: method should only be called with generic type, but " + lookingFor + " is " + lookingFor.typeKind);
      } else if (this.equals(OBJECT)) {
         return null;
      } else if (this.genericTypeEquals(lookingFor)) {
         return this;
      } else {
         ResolvedType superT = this.getSuperclass();
         if (superT.genericTypeEquals(lookingFor)) {
            return superT;
         } else {
            ResolvedType[] superIs = this.getDeclaredInterfaces();

            for(int i = 0; i < superIs.length; ++i) {
               ResolvedType superI = superIs[i];
               if (superI.genericTypeEquals(lookingFor)) {
                  return superI;
               }

               ResolvedType checkTheSuperI = superI.discoverActualOccurrenceOfTypeInHierarchy(lookingFor);
               if (checkTheSuperI != null) {
                  return checkTheSuperI;
               }
            }

            return superT.discoverActualOccurrenceOfTypeInHierarchy(lookingFor);
         }
      }
   }

   public ConcreteTypeMunger fillInAnyTypeParameters(ConcreteTypeMunger munger) {
      boolean debug = false;
      ResolvedMember member = munger.getSignature();
      if (munger.isTargetTypeParameterized()) {
         if (debug) {
            System.err.println("Processing attempted parameterization of " + munger + " targetting type " + this);
         }

         if (debug) {
            System.err.println("  This type is " + this + "  (" + this.typeKind + ")");
         }

         if (debug) {
            System.err.println("  Signature that needs parameterizing: " + member);
         }

         ResolvedType onTypeResolved = this.world.resolve(member.getDeclaringType());
         ResolvedType onType = onTypeResolved.getGenericType();
         if (onType == null) {
            this.getWorld().getMessageHandler().handleMessage(MessageUtil.error("The target type for the intertype declaration is not generic", munger.getSourceLocation()));
            return munger;
         }

         member.resolve(this.world);
         if (debug) {
            System.err.println("  Actual target ontype: " + onType + "  (" + onType.typeKind + ")");
         }

         ResolvedType actualTarget = this.discoverActualOccurrenceOfTypeInHierarchy(onType);
         if (actualTarget == null) {
            throw new BCException("assertion failed: asked " + this + " for occurrence of " + onType + " in its hierarchy??");
         }

         if (!actualTarget.isGenericType() && debug) {
            System.err.println("Occurrence in " + this + " is actually " + actualTarget + "  (" + actualTarget.typeKind + ")");
         }

         munger = munger.parameterizedFor(actualTarget);
         if (debug) {
            System.err.println("New sig: " + munger.getSignature());
         }

         if (debug) {
            System.err.println("=====================================");
         }
      }

      return munger;
   }

   public void addInterTypeMunger(ConcreteTypeMunger munger, boolean isDuringCompilation) {
      ResolvedMember sig = munger.getSignature();
      this.bits &= ~MungersAnalyzed;
      if (sig != null && munger.getMunger() != null && munger.getMunger().getKind() != ResolvedTypeMunger.PrivilegedAccess) {
         munger = this.fillInAnyTypeParameters(munger);
         sig = munger.getSignature();
         if (sig.getKind() == Member.METHOD) {
            if (this.clashesWithExistingMember(munger, this.getMethods(true, false))) {
               return;
            }

            if (this.isInterface() && this.clashesWithExistingMember(munger, Arrays.asList(this.world.getCoreType(OBJECT).getDeclaredMethods()).iterator())) {
               return;
            }
         } else if (sig.getKind() == Member.FIELD) {
            if (this.clashesWithExistingMember(munger, Arrays.asList(this.getDeclaredFields()).iterator())) {
               return;
            }

            if (!isDuringCompilation) {
               ResolvedTypeMunger thisRealMunger = munger.getMunger();
               if (thisRealMunger instanceof NewFieldTypeMunger) {
                  NewFieldTypeMunger newFieldTypeMunger = (NewFieldTypeMunger)thisRealMunger;
                  if (newFieldTypeMunger.version == 2) {
                     String thisRealMungerSignatureName = newFieldTypeMunger.getSignature().getName();
                     Iterator i$ = this.interTypeMungers.iterator();

                     while(i$.hasNext()) {
                        ConcreteTypeMunger typeMunger = (ConcreteTypeMunger)i$.next();
                        if (typeMunger.getMunger() instanceof NewFieldTypeMunger && typeMunger.getSignature().getKind() == Member.FIELD) {
                           NewFieldTypeMunger existing = (NewFieldTypeMunger)typeMunger.getMunger();
                           if (existing.getSignature().getName().equals(thisRealMungerSignatureName) && existing.version == 2 && existing.getSignature().getDeclaringType().equals(newFieldTypeMunger.getSignature().getDeclaringType())) {
                              StringBuffer sb = new StringBuffer();
                              sb.append("Cannot handle two aspects both attempting to use new style ITDs for the same named field ");
                              sb.append("on the same target type.  Please recompile at least one aspect with '-Xset:itdVersion=1'.");
                              sb.append(" Aspects involved: " + munger.getAspectType().getName() + " and " + typeMunger.getAspectType().getName() + ".");
                              sb.append(" Field is named '" + existing.getSignature().getName() + "'");
                              this.getWorld().getMessageHandler().handleMessage(new Message(sb.toString(), this.getSourceLocation(), true));
                              return;
                           }
                        }
                     }
                  }
               }
            }
         } else if (this.clashesWithExistingMember(munger, Arrays.asList(this.getDeclaredMethods()).iterator())) {
            return;
         }

         boolean needsAdding = true;
         boolean needsToBeAddedEarlier = false;
         Iterator i = this.interTypeMungers.iterator();

         while(i.hasNext()) {
            ConcreteTypeMunger existingMunger = (ConcreteTypeMunger)i.next();
            boolean v2itds = munger.getSignature().getKind() == Member.FIELD && munger.getMunger() instanceof NewFieldTypeMunger && ((NewFieldTypeMunger)munger.getMunger()).version == 2;
            if (conflictingSignature(existingMunger.getSignature(), munger.getSignature(), v2itds) && isVisible(munger.getSignature().getModifiers(), munger.getAspectType(), existingMunger.getAspectType())) {
               int c = this.compareMemberPrecedence(sig, existingMunger.getSignature());
               if (c == 0) {
                  c = this.getWorld().compareByPrecedenceAndHierarchy(munger.getAspectType(), existingMunger.getAspectType());
               }

               if (c < 0) {
                  this.checkLegalOverride(munger.getSignature(), existingMunger.getSignature(), 17, (ResolvedType)null);
                  needsAdding = false;
                  if (munger.getSignature().getKind() == Member.FIELD && munger.getSignature().getDeclaringType().resolve(this.world).isInterface() && ((NewFieldTypeMunger)munger.getMunger()).version == 2) {
                     needsAdding = true;
                  }
               } else {
                  if (c <= 0) {
                     this.interTypeConflictError(munger, existingMunger);
                     this.interTypeConflictError(existingMunger, munger);
                     return;
                  }

                  this.checkLegalOverride(existingMunger.getSignature(), munger.getSignature(), 17, (ResolvedType)null);
                  if (existingMunger.getSignature().getKind() == Member.FIELD && existingMunger.getSignature().getDeclaringType().resolve(this.world).isInterface() && ((NewFieldTypeMunger)existingMunger.getMunger()).version == 2) {
                     needsToBeAddedEarlier = true;
                  } else {
                     i.remove();
                  }
               }
               break;
            }
         }

         if (needsAdding) {
            if (!needsToBeAddedEarlier) {
               this.interTypeMungers.add(munger);
            } else {
               this.interTypeMungers.add(0, munger);
            }
         }

      } else {
         this.interTypeMungers.add(munger);
      }
   }

   private boolean clashesWithExistingMember(ConcreteTypeMunger typeTransformer, Iterator existingMembers) {
      ResolvedMember typeTransformerSignature = typeTransformer.getSignature();
      ResolvedTypeMunger rtm = typeTransformer.getMunger();
      boolean v2itds = true;
      if (rtm instanceof NewFieldTypeMunger && ((NewFieldTypeMunger)rtm).version == 1) {
         v2itds = false;
      }

      ResolvedMember existingMember;
      boolean isDuplicateOfPreviousITD;
      do {
         do {
            boolean sameReturnTypes;
            label80:
            do {
               while(true) {
                  while(true) {
                     do {
                        do {
                           if (!existingMembers.hasNext()) {
                              return false;
                           }

                           existingMember = (ResolvedMember)existingMembers.next();
                        } while(existingMember.isBridgeMethod());
                     } while(!conflictingSignature(existingMember, typeTransformerSignature, v2itds));

                     if (isVisible(existingMember.getModifiers(), this, typeTransformer.getAspectType())) {
                        int c = this.compareMemberPrecedence(typeTransformerSignature, existingMember);
                        if (c < 0) {
                           ResolvedType typeTransformerTargetType = typeTransformerSignature.getDeclaringType().resolve(this.world);
                           if (typeTransformerTargetType.isInterface()) {
                              ResolvedType existingMemberType = existingMember.getDeclaringType().resolve(this.world);
                              if (rtm instanceof NewMethodTypeMunger && !typeTransformerTargetType.equals(existingMemberType) && Modifier.isPrivate(typeTransformerSignature.getModifiers()) && Modifier.isPublic(existingMember.getModifiers())) {
                                 this.world.getMessageHandler().handleMessage(new Message("private intertype declaration '" + typeTransformerSignature.toString() + "' clashes with public member '" + existingMember.toString() + "'", existingMember.getSourceLocation(), true));
                              }
                           }

                           this.checkLegalOverride(typeTransformerSignature, existingMember, 16, typeTransformer.getAspectType());
                           return true;
                        }

                        if (c <= 0) {
                           sameReturnTypes = existingMember.getReturnType().equals(typeTransformerSignature.getReturnType());
                           continue label80;
                        }

                        this.checkLegalOverride(existingMember, typeTransformerSignature, 1, typeTransformer.getAspectType());
                     } else if (this.isDuplicateMemberWithinTargetType(existingMember, this, typeTransformerSignature)) {
                        this.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("itdMemberConflict", typeTransformer.getAspectType().getName(), existingMember), typeTransformer.getSourceLocation()));
                        return true;
                     }
                  }
               }
            } while(!sameReturnTypes);

            isDuplicateOfPreviousITD = false;
            ResolvedType declaringRt = existingMember.getDeclaringType().resolve(this.world);
            WeaverStateInfo wsi = declaringRt.getWeaverState();
            if (wsi != null) {
               List mungersAffectingThisType = wsi.getTypeMungers(declaringRt);
               if (mungersAffectingThisType != null) {
                  Iterator iterator = mungersAffectingThisType.iterator();

                  while(iterator.hasNext() && !isDuplicateOfPreviousITD) {
                     ConcreteTypeMunger ctMunger = (ConcreteTypeMunger)iterator.next();
                     if (ctMunger.getSignature().equals(existingMember) && ctMunger.aspectType.equals(typeTransformer.getAspectType())) {
                        isDuplicateOfPreviousITD = true;
                     }
                  }
               }
            }
         } while(isDuplicateOfPreviousITD);
      } while(typeTransformerSignature.getName().equals("<init>") && existingMember.isDefaultConstructor());

      String aspectName = typeTransformer.getAspectType().getName();
      ISourceLocation typeTransformerLocation = typeTransformer.getSourceLocation();
      ISourceLocation existingMemberLocation = existingMember.getSourceLocation();
      String msg = WeaverMessages.format("itdMemberConflict", aspectName, existingMember);
      this.getWorld().getMessageHandler().handleMessage(new Message(msg, typeTransformerLocation, true));
      if (existingMemberLocation != null) {
         this.getWorld().getMessageHandler().handleMessage(new Message(msg, existingMemberLocation, true));
      }

      return true;
   }

   private boolean isDuplicateMemberWithinTargetType(ResolvedMember existingMember, ResolvedType targetType, ResolvedMember itdMember) {
      if (!existingMember.isAbstract() && !itdMember.isAbstract()) {
         UnresolvedType declaringType = existingMember.getDeclaringType();
         if (!targetType.equals(declaringType)) {
            return false;
         } else if (Modifier.isPrivate(itdMember.getModifiers())) {
            return false;
         } else if (itdMember.isPublic()) {
            return true;
         } else {
            return targetType.getPackageName().equals(itdMember.getDeclaringType().getPackageName());
         }
      } else {
         return false;
      }
   }

   public boolean checkLegalOverride(ResolvedMember parent, ResolvedMember child, int transformerPosition, ResolvedType aspectType) {
      if (Modifier.isFinal(parent.getModifiers())) {
         if (transformerPosition == 16 && aspectType != null) {
            ResolvedType nonItdDeclaringType = child.getDeclaringType().resolve(this.world);
            WeaverStateInfo wsi = nonItdDeclaringType.getWeaverState();
            if (wsi != null) {
               List transformersOnThisType = wsi.getTypeMungers(nonItdDeclaringType);
               if (transformersOnThisType != null) {
                  Iterator i$ = transformersOnThisType.iterator();

                  while(i$.hasNext()) {
                     ConcreteTypeMunger transformer = (ConcreteTypeMunger)i$.next();
                     if (transformer.aspectType.equals(aspectType) && parent.equalsApartFromDeclaringType(transformer.getSignature())) {
                        return true;
                     }
                  }
               }
            }
         }

         this.world.showMessage(Message.ERROR, WeaverMessages.format("cantOverrideFinalMember", parent), child.getSourceLocation(), (ISourceLocation)null);
         return false;
      } else {
         boolean incompatibleReturnTypes = false;
         ResolvedType rtParentReturnType;
         ResolvedType rtChildReturnType;
         if (this.world.isInJava5Mode() && parent.getKind() == Member.METHOD) {
            rtParentReturnType = parent.resolve(this.world).getGenericReturnType().resolve(this.world);
            rtChildReturnType = child.resolve(this.world).getGenericReturnType().resolve(this.world);
            incompatibleReturnTypes = !rtParentReturnType.isAssignableFrom(rtChildReturnType);
         } else {
            rtParentReturnType = parent.resolve(this.world).getGenericReturnType().resolve(this.world);
            rtChildReturnType = child.resolve(this.world).getGenericReturnType().resolve(this.world);
            incompatibleReturnTypes = !rtParentReturnType.equals(rtChildReturnType);
         }

         if (incompatibleReturnTypes) {
            this.world.showMessage(IMessage.ERROR, WeaverMessages.format("returnTypeMismatch", parent, child), child.getSourceLocation(), parent.getSourceLocation());
            return false;
         } else {
            if (parent.getKind() == Member.POINTCUT) {
               UnresolvedType[] pTypes = parent.getParameterTypes();
               UnresolvedType[] cTypes = child.getParameterTypes();
               if (!Arrays.equals(pTypes, cTypes)) {
                  this.world.showMessage(IMessage.ERROR, WeaverMessages.format("paramTypeMismatch", parent, child), child.getSourceLocation(), parent.getSourceLocation());
                  return false;
               }
            }

            if (isMoreVisible(parent.getModifiers(), child.getModifiers())) {
               this.world.showMessage(IMessage.ERROR, WeaverMessages.format("visibilityReduction", parent, child), child.getSourceLocation(), parent.getSourceLocation());
               return false;
            } else {
               ResolvedType[] childExceptions = this.world.resolve(child.getExceptions());
               ResolvedType[] parentExceptions = this.world.resolve(parent.getExceptions());
               ResolvedType runtimeException = this.world.resolve("java.lang.RuntimeException");
               ResolvedType error = this.world.resolve("java.lang.Error");
               int i = 0;

               label97:
               for(int leni = childExceptions.length; i < leni; ++i) {
                  if (!runtimeException.isAssignableFrom(childExceptions[i]) && !error.isAssignableFrom(childExceptions[i])) {
                     int j = 0;

                     for(int lenj = parentExceptions.length; j < lenj; ++j) {
                        if (parentExceptions[j].isAssignableFrom(childExceptions[i])) {
                           continue label97;
                        }
                     }

                     return false;
                  }
               }

               boolean parentStatic = Modifier.isStatic(parent.getModifiers());
               boolean childStatic = Modifier.isStatic(child.getModifiers());
               if (parentStatic && !childStatic) {
                  this.world.showMessage(IMessage.ERROR, WeaverMessages.format("overriddenStatic", child, parent), child.getSourceLocation(), (ISourceLocation)null);
                  return false;
               } else if (childStatic && !parentStatic) {
                  this.world.showMessage(IMessage.ERROR, WeaverMessages.format("overridingStatic", child, parent), child.getSourceLocation(), (ISourceLocation)null);
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }

   private int compareMemberPrecedence(ResolvedMember m1, ResolvedMember m2) {
      if (Modifier.isProtected(m2.getModifiers()) && m2.getName().charAt(0) == 'c') {
         UnresolvedType declaring = m2.getDeclaringType();
         if (declaring != null && declaring.getName().equals("java.lang.Object") && m2.getName().equals("clone")) {
            return 1;
         }
      }

      if (Modifier.isAbstract(m1.getModifiers())) {
         return -1;
      } else if (Modifier.isAbstract(m2.getModifiers())) {
         return 1;
      } else if (m1.getDeclaringType().equals(m2.getDeclaringType())) {
         return 0;
      } else {
         ResolvedType t1 = m1.getDeclaringType().resolve(this.world);
         ResolvedType t2 = m2.getDeclaringType().resolve(this.world);
         if (t1.isAssignableFrom(t2)) {
            return -1;
         } else {
            return t2.isAssignableFrom(t1) ? 1 : 0;
         }
      }
   }

   public static boolean isMoreVisible(int m1, int m2) {
      if (Modifier.isPrivate(m1)) {
         return false;
      } else if (isPackage(m1)) {
         return Modifier.isPrivate(m2);
      } else if (!Modifier.isProtected(m1)) {
         if (Modifier.isPublic(m1)) {
            return !Modifier.isPublic(m2);
         } else {
            throw new RuntimeException("bad modifier: " + m1);
         }
      } else {
         return Modifier.isPrivate(m2) || isPackage(m2);
      }
   }

   private static boolean isPackage(int i) {
      return 0 == (i & 7);
   }

   private void interTypeConflictError(ConcreteTypeMunger m1, ConcreteTypeMunger m2) {
      this.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("itdConflict", m1.getAspectType().getName(), m2.getSignature(), m2.getAspectType().getName()), m2.getSourceLocation(), this.getSourceLocation());
   }

   public ResolvedMember lookupSyntheticMember(Member member) {
      Iterator i$ = this.interTypeMungers.iterator();

      while(i$.hasNext()) {
         ConcreteTypeMunger m = (ConcreteTypeMunger)i$.next();
         ResolvedMember ret = m.getMatchingSyntheticMember(member);
         if (ret != null) {
            return ret;
         }
      }

      if (this.world.isJoinpointArrayConstructionEnabled() && this.isArray() && member.getKind() == Member.CONSTRUCTOR) {
         ResolvedMemberImpl ret = new ResolvedMemberImpl(Member.CONSTRUCTOR, this, 1, UnresolvedType.VOID, "<init>", this.world.resolve(member.getParameterTypes()));
         int count = ret.getParameterTypes().length;
         String[] paramNames = new String[count];

         for(int i = 0; i < count; ++i) {
            paramNames[i] = "dim" + i;
         }

         ret.setParameterNames(paramNames);
         return ret;
      } else {
         return null;
      }
   }

   public void clearInterTypeMungers() {
      if (this.isRawType()) {
         ResolvedType genericType = this.getGenericType();
         if (genericType.isRawType()) {
            System.err.println("DebugFor341926: Type " + this.getName() + " has an incorrect generic form");
         } else {
            genericType.clearInterTypeMungers();
         }
      }

      this.interTypeMungers = new ArrayList();
   }

   public boolean isTopmostImplementor(ResolvedType interfaceType) {
      boolean b = true;
      if (this.isInterface()) {
         b = false;
      } else if (!interfaceType.isAssignableFrom(this, true)) {
         b = false;
      } else {
         ResolvedType superclass = this.getSuperclass();
         if (superclass.isMissing()) {
            b = true;
         } else if (interfaceType.isAssignableFrom(superclass, true)) {
            b = false;
         }
      }

      return b;
   }

   public ResolvedType getTopmostImplementor(ResolvedType interfaceType) {
      if (this.isInterface()) {
         return null;
      } else if (!interfaceType.isAssignableFrom(this)) {
         return null;
      } else {
         ResolvedType higherType = this.getSuperclass().getTopmostImplementor(interfaceType);
         return higherType != null ? higherType : this;
      }
   }

   public List getExposedPointcuts() {
      List ret = new ArrayList();
      if (this.getSuperclass() != null) {
         ret.addAll(this.getSuperclass().getExposedPointcuts());
      }

      ResolvedType[] arr$ = this.getDeclaredInterfaces();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ResolvedType type = arr$[i$];
         this.addPointcutsResolvingConflicts(ret, Arrays.asList(type.getDeclaredPointcuts()), false);
      }

      this.addPointcutsResolvingConflicts(ret, Arrays.asList(this.getDeclaredPointcuts()), true);
      Iterator i$ = ret.iterator();

      while(i$.hasNext()) {
         ResolvedMember member = (ResolvedMember)i$.next();
         ResolvedPointcutDefinition inherited = (ResolvedPointcutDefinition)member;
         if (inherited != null && inherited.isAbstract() && !this.isAbstract()) {
            this.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("abstractPointcutNotMadeConcrete", inherited, this.getName()), inherited.getSourceLocation(), this.getSourceLocation());
         }
      }

      return ret;
   }

   private void addPointcutsResolvingConflicts(List acc, List added, boolean isOverriding) {
      ResolvedPointcutDefinition toAdd;
      label54:
      for(Iterator i = added.iterator(); i.hasNext(); acc.add(toAdd)) {
         toAdd = (ResolvedPointcutDefinition)i.next();
         Iterator j = acc.iterator();

         while(true) {
            while(true) {
               ResolvedPointcutDefinition existing;
               do {
                  do {
                     do {
                        if (!j.hasNext()) {
                           continue label54;
                        }

                        existing = (ResolvedPointcutDefinition)j.next();
                     } while(toAdd == null);
                  } while(existing == null);
               } while(existing == toAdd);

               UnresolvedType pointcutDeclaringTypeUT = existing.getDeclaringType();
               if (pointcutDeclaringTypeUT != null) {
                  ResolvedType pointcutDeclaringType = pointcutDeclaringTypeUT.resolve(this.getWorld());
                  if (!isVisible(existing.getModifiers(), pointcutDeclaringType, this)) {
                     if (existing.isAbstract() && conflictingSignature(existing, toAdd)) {
                        this.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("pointcutNotVisible", existing.getDeclaringType().getName() + "." + existing.getName() + "()", this.getName()), toAdd.getSourceLocation(), (ISourceLocation)null);
                        j.remove();
                     }
                     continue;
                  }
               }

               if (conflictingSignature(existing, toAdd)) {
                  if (isOverriding) {
                     this.checkLegalOverride(existing, toAdd, 0, (ResolvedType)null);
                     j.remove();
                  } else {
                     this.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("conflictingInheritedPointcuts", this.getName() + toAdd.getSignature()), existing.getSourceLocation(), toAdd.getSourceLocation());
                     j.remove();
                  }
               }
            }
         }
      }

   }

   public ISourceLocation getSourceLocation() {
      return null;
   }

   public boolean isExposedToWeaver() {
      return false;
   }

   public WeaverStateInfo getWeaverState() {
      return null;
   }

   public ReferenceType getGenericType() {
      return null;
   }

   public ResolvedType getRawType() {
      return super.getRawType().resolve(this.world);
   }

   public ResolvedType parameterizedWith(UnresolvedType[] typeParameters) {
      return (ResolvedType)(!this.isGenericType() && !this.isParameterizedType() ? this : TypeFactory.createParameterizedType(this.getGenericType(), typeParameters, this.getWorld()));
   }

   public UnresolvedType parameterize(Map typeBindings) {
      if (!this.isParameterizedType()) {
         return this;
      } else {
         boolean workToDo = false;

         for(int i = 0; i < this.typeParameters.length; ++i) {
            if (this.typeParameters[i].isTypeVariableReference() || this.typeParameters[i] instanceof BoundedReferenceType || this.typeParameters[i].isParameterizedType()) {
               workToDo = true;
            }
         }

         if (!workToDo) {
            return this;
         } else {
            UnresolvedType[] newTypeParams = new UnresolvedType[this.typeParameters.length];

            for(int i = 0; i < newTypeParams.length; ++i) {
               newTypeParams[i] = this.typeParameters[i];
               if (newTypeParams[i].isTypeVariableReference()) {
                  TypeVariableReferenceType tvrt = (TypeVariableReferenceType)newTypeParams[i];
                  UnresolvedType binding = (UnresolvedType)typeBindings.get(tvrt.getTypeVariable().getName());
                  if (binding != null) {
                     newTypeParams[i] = binding;
                  }
               } else if (newTypeParams[i] instanceof BoundedReferenceType) {
                  BoundedReferenceType brType = (BoundedReferenceType)newTypeParams[i];
                  newTypeParams[i] = brType.parameterize(typeBindings);
               } else if (newTypeParams[i].isParameterizedType()) {
                  newTypeParams[i] = newTypeParams[i].parameterize(typeBindings);
               }
            }

            return TypeFactory.createParameterizedType(this.getGenericType(), newTypeParams, this.getWorld());
         }
      }
   }

   public boolean isException() {
      return this.world.getCoreType(UnresolvedType.JL_EXCEPTION).isAssignableFrom(this);
   }

   public boolean isCheckedException() {
      if (!this.isException()) {
         return false;
      } else {
         return !this.world.getCoreType(UnresolvedType.RUNTIME_EXCEPTION).isAssignableFrom(this);
      }
   }

   public final boolean isConvertableFrom(ResolvedType other) {
      if (this.equals(OBJECT)) {
         return true;
      } else if (this.world.isInJava5Mode() && this.isPrimitiveType() ^ other.isPrimitiveType() && validBoxing.contains(this.getSignature() + other.getSignature())) {
         return true;
      } else {
         return !this.isPrimitiveType() && !other.isPrimitiveType() ? this.isCoerceableFrom(other) : this.isAssignableFrom(other);
      }
   }

   public abstract boolean isAssignableFrom(ResolvedType var1);

   public abstract boolean isAssignableFrom(ResolvedType var1, boolean var2);

   public abstract boolean isCoerceableFrom(ResolvedType var1);

   public boolean needsNoConversionFrom(ResolvedType o) {
      return this.isAssignableFrom(o);
   }

   public String getSignatureForAttribute() {
      return this.signature;
   }

   public boolean isParameterizedWithTypeVariable() {
      if (this.parameterizedWithTypeVariable == FuzzyBoolean.MAYBE) {
         if (this.typeParameters == null || this.typeParameters.length == 0) {
            this.parameterizedWithTypeVariable = FuzzyBoolean.NO;
            return false;
         }

         for(int i = 0; i < this.typeParameters.length; ++i) {
            ResolvedType aType = (ResolvedType)this.typeParameters[i];
            if (aType.isTypeVariableReference()) {
               this.parameterizedWithTypeVariable = FuzzyBoolean.YES;
               return true;
            }

            if (aType.isParameterizedType()) {
               boolean b = aType.isParameterizedWithTypeVariable();
               if (b) {
                  this.parameterizedWithTypeVariable = FuzzyBoolean.YES;
                  return true;
               }
            }

            if (aType.isGenericWildcard()) {
               BoundedReferenceType boundedRT = (BoundedReferenceType)aType;
               boolean b;
               UnresolvedType lowerBound;
               if (boundedRT.isExtends()) {
                  b = false;
                  lowerBound = boundedRT.getUpperBound();
                  if (lowerBound.isParameterizedType()) {
                     b = ((ResolvedType)lowerBound).isParameterizedWithTypeVariable();
                  } else if (lowerBound.isTypeVariableReference() && ((TypeVariableReference)lowerBound).getTypeVariable().getDeclaringElementKind() == 1) {
                     b = true;
                  }

                  if (b) {
                     this.parameterizedWithTypeVariable = FuzzyBoolean.YES;
                     return true;
                  }
               }

               if (boundedRT.isSuper()) {
                  b = false;
                  lowerBound = boundedRT.getLowerBound();
                  if (lowerBound.isParameterizedType()) {
                     b = ((ResolvedType)lowerBound).isParameterizedWithTypeVariable();
                  } else if (lowerBound.isTypeVariableReference() && ((TypeVariableReference)lowerBound).getTypeVariable().getDeclaringElementKind() == 1) {
                     b = true;
                  }

                  if (b) {
                     this.parameterizedWithTypeVariable = FuzzyBoolean.YES;
                     return true;
                  }
               }
            }
         }

         this.parameterizedWithTypeVariable = FuzzyBoolean.NO;
      }

      return this.parameterizedWithTypeVariable.alwaysTrue();
   }

   protected boolean ajMembersNeedParameterization() {
      if (this.isParameterizedType()) {
         return true;
      } else {
         ResolvedType superclass = this.getSuperclass();
         return superclass != null && !superclass.isMissing() ? superclass.ajMembersNeedParameterization() : false;
      }
   }

   protected Map getAjMemberParameterizationMap() {
      Map myMap = this.getMemberParameterizationMap();
      return myMap.isEmpty() && this.getSuperclass() != null ? this.getSuperclass().getAjMemberParameterizationMap() : myMap;
   }

   public void setBinaryPath(String binaryPath) {
      this.binaryPath = binaryPath;
   }

   public String getBinaryPath() {
      return this.binaryPath;
   }

   public void ensureConsistent() {
   }

   public boolean isInheritedAnnotation() {
      this.ensureAnnotationBitsInitialized();
      return (this.bits & AnnotationMarkedInherited) != 0;
   }

   private void ensureAnnotationBitsInitialized() {
      if ((this.bits & AnnotationBitsInitialized) == 0) {
         this.bits |= AnnotationBitsInitialized;
         if (this.hasAnnotation(UnresolvedType.AT_INHERITED)) {
            this.bits |= AnnotationMarkedInherited;
         }
      }

   }

   private boolean hasNewParentMungers() {
      if ((this.bits & MungersAnalyzed) == 0) {
         this.bits |= MungersAnalyzed;
         Iterator i$ = this.interTypeMungers.iterator();

         while(i$.hasNext()) {
            ConcreteTypeMunger munger = (ConcreteTypeMunger)i$.next();
            ResolvedTypeMunger resolvedTypeMunger = munger.getMunger();
            if (resolvedTypeMunger != null && resolvedTypeMunger.getKind() == ResolvedTypeMunger.Parent) {
               this.bits |= HasParentMunger;
            }
         }
      }

      return (this.bits & HasParentMunger) != 0;
   }

   public void tagAsTypeHierarchyComplete() {
      if (this.isParameterizedOrRawType()) {
         ReferenceType genericType = this.getGenericType();
         genericType.tagAsTypeHierarchyComplete();
      } else {
         this.bits |= TypeHierarchyCompleteBit;
      }
   }

   public boolean isTypeHierarchyComplete() {
      if (this.isParameterizedOrRawType()) {
         return this.getGenericType().isTypeHierarchyComplete();
      } else {
         return (this.bits & TypeHierarchyCompleteBit) != 0;
      }
   }

   public int getCompilerVersion() {
      return AjAttribute.WeaverVersionInfo.getCurrentWeaverMajorVersion();
   }

   public boolean isPrimitiveArray() {
      return false;
   }

   public boolean isGroovyObject() {
      if ((this.bits & GroovyObjectInitialized) == 0) {
         ResolvedType[] intfaces = this.getDeclaredInterfaces();
         boolean done = false;
         if (intfaces != null) {
            ResolvedType[] arr$ = intfaces;
            int len$ = intfaces.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               ResolvedType intface = arr$[i$];
               if (intface.getName().equals("groovy.lang.GroovyObject")) {
                  this.bits |= IsGroovyObject;
                  done = true;
                  break;
               }
            }
         }

         if (!done && this.getSuperclass().getName().equals("groovy.lang.GroovyObjectSupport")) {
            this.bits |= IsGroovyObject;
         }

         this.bits |= GroovyObjectInitialized;
      }

      return (this.bits & IsGroovyObject) != 0;
   }

   static {
      validBoxing.add("Ljava/lang/Byte;B");
      validBoxing.add("Ljava/lang/Character;C");
      validBoxing.add("Ljava/lang/Double;D");
      validBoxing.add("Ljava/lang/Float;F");
      validBoxing.add("Ljava/lang/Integer;I");
      validBoxing.add("Ljava/lang/Long;J");
      validBoxing.add("Ljava/lang/Short;S");
      validBoxing.add("Ljava/lang/Boolean;Z");
      validBoxing.add("BLjava/lang/Byte;");
      validBoxing.add("CLjava/lang/Character;");
      validBoxing.add("DLjava/lang/Double;");
      validBoxing.add("FLjava/lang/Float;");
      validBoxing.add("ILjava/lang/Integer;");
      validBoxing.add("JLjava/lang/Long;");
      validBoxing.add("SLjava/lang/Short;");
      validBoxing.add("ZLjava/lang/Boolean;");
      MethodGetterInstance = new MethodGetter();
      MethodGetterWithItdsInstance = new MethodGetterIncludingItds();
      PointcutGetterInstance = new PointcutGetter();
      FieldGetterInstance = new FieldGetter();
      NONE = new ResolvedType[0];
      EMPTY_ARRAY = NONE;
      MISSING = new Missing();
   }

   static class SuperInterfaceWalker implements Iterator {
      private Iterators.Getter ifaceGetter;
      Iterator delegate = null;
      public Queue toPersue = new LinkedList();
      public Set visited = new HashSet();

      SuperInterfaceWalker(Iterators.Getter ifaceGetter) {
         this.ifaceGetter = ifaceGetter;
      }

      SuperInterfaceWalker(Iterators.Getter ifaceGetter, ResolvedType interfaceType) {
         this.ifaceGetter = ifaceGetter;
         this.delegate = Iterators.one(interfaceType);
      }

      public boolean hasNext() {
         if (this.delegate == null || !this.delegate.hasNext()) {
            if (this.toPersue.isEmpty()) {
               return false;
            }

            do {
               ResolvedType next = (ResolvedType)this.toPersue.remove();
               this.visited.add(next);
               this.delegate = this.ifaceGetter.get(next);
            } while(!this.delegate.hasNext() && !this.toPersue.isEmpty());
         }

         return this.delegate.hasNext();
      }

      public void push(ResolvedType ret) {
         this.toPersue.add(ret);
      }

      public ResolvedType next() {
         ResolvedType next = (ResolvedType)this.delegate.next();
         if (this.visited.add(next)) {
            this.toPersue.add(next);
         }

         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   static class SuperClassWalker implements Iterator {
      private ResolvedType curr;
      private SuperInterfaceWalker iwalker;
      private boolean wantGenerics;

      public SuperClassWalker(ResolvedType type, SuperInterfaceWalker iwalker, boolean genericsAware) {
         this.curr = type;
         this.iwalker = iwalker;
         this.wantGenerics = genericsAware;
      }

      public boolean hasNext() {
         return this.curr != null;
      }

      public ResolvedType next() {
         ResolvedType ret = this.curr;
         if (!this.wantGenerics && ret.isParameterizedOrGenericType()) {
            ret = ret.getRawType();
         }

         this.iwalker.push(ret);
         this.curr = this.curr.getSuperclass();
         return ret;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   static class Missing extends ResolvedType {
      Missing() {
         super("@missing@", (World)null);
      }

      public final String getName() {
         return "@missing@";
      }

      public final boolean isMissing() {
         return true;
      }

      public boolean hasAnnotation(UnresolvedType ofType) {
         return false;
      }

      public final ResolvedMember[] getDeclaredFields() {
         return ResolvedMember.NONE;
      }

      public final ResolvedMember[] getDeclaredMethods() {
         return ResolvedMember.NONE;
      }

      public final ResolvedType[] getDeclaredInterfaces() {
         return ResolvedType.NONE;
      }

      public final ResolvedMember[] getDeclaredPointcuts() {
         return ResolvedMember.NONE;
      }

      public final ResolvedType getSuperclass() {
         return null;
      }

      public final int getModifiers() {
         return 0;
      }

      public final boolean isAssignableFrom(ResolvedType other) {
         return false;
      }

      public final boolean isAssignableFrom(ResolvedType other, boolean allowMissing) {
         return false;
      }

      public final boolean isCoerceableFrom(ResolvedType other) {
         return false;
      }

      public boolean needsNoConversionFrom(ResolvedType other) {
         return false;
      }

      public ISourceContext getSourceContext() {
         return null;
      }
   }

   static class Primitive extends ResolvedType {
      private final int size;
      private final int index;
      private static final boolean[][] assignTable = new boolean[][]{{true, true, true, true, true, true, true, false, false}, {false, true, true, true, true, true, false, false, false}, {false, false, true, false, false, false, false, false, false}, {false, false, true, true, false, false, false, false, false}, {false, false, true, true, true, true, false, false, false}, {false, false, true, true, false, true, false, false, false}, {false, false, true, true, true, true, true, false, false}, {false, false, false, false, false, false, false, true, false}, {false, false, false, false, false, false, false, false, true}};
      private static final boolean[][] noConvertTable = new boolean[][]{{true, true, false, false, true, false, true, false, false}, {false, true, false, false, true, false, false, false, false}, {false, false, true, false, false, false, false, false, false}, {false, false, false, true, false, false, false, false, false}, {false, false, false, false, true, false, false, false, false}, {false, false, false, false, false, true, false, false, false}, {false, false, false, false, true, false, true, false, false}, {false, false, false, false, false, false, false, true, false}, {false, false, false, false, false, false, false, false, true}};

      Primitive(String signature, int size, int index) {
         super(signature, (World)null);
         this.size = size;
         this.index = index;
         this.typeKind = UnresolvedType.TypeKind.PRIMITIVE;
      }

      public final int getSize() {
         return this.size;
      }

      public final int getModifiers() {
         return 17;
      }

      public final boolean isPrimitiveType() {
         return true;
      }

      public boolean hasAnnotation(UnresolvedType ofType) {
         return false;
      }

      public final boolean isAssignableFrom(ResolvedType other) {
         if (!other.isPrimitiveType()) {
            return !this.world.isInJava5Mode() ? false : validBoxing.contains(this.getSignature() + other.getSignature());
         } else {
            return assignTable[((Primitive)other).index][this.index];
         }
      }

      public final boolean isAssignableFrom(ResolvedType other, boolean allowMissing) {
         return this.isAssignableFrom(other);
      }

      public final boolean isCoerceableFrom(ResolvedType other) {
         if (this == other) {
            return true;
         } else if (!other.isPrimitiveType()) {
            return false;
         } else {
            return this.index <= 6 && ((Primitive)other).index <= 6;
         }
      }

      public ResolvedType resolve(World world) {
         if (this.world != world) {
            throw new IllegalStateException();
         } else {
            this.world = world;
            return super.resolve(world);
         }
      }

      public final boolean needsNoConversionFrom(ResolvedType other) {
         return !other.isPrimitiveType() ? false : noConvertTable[((Primitive)other).index][this.index];
      }

      public final ResolvedMember[] getDeclaredFields() {
         return ResolvedMember.NONE;
      }

      public final ResolvedMember[] getDeclaredMethods() {
         return ResolvedMember.NONE;
      }

      public final ResolvedType[] getDeclaredInterfaces() {
         return ResolvedType.NONE;
      }

      public final ResolvedMember[] getDeclaredPointcuts() {
         return ResolvedMember.NONE;
      }

      public final ResolvedType getSuperclass() {
         return null;
      }

      public ISourceContext getSourceContext() {
         return null;
      }
   }

   private static class FieldGetter implements Iterators.Getter {
      private FieldGetter() {
      }

      public Iterator get(ResolvedType type) {
         return Iterators.array(type.getDeclaredFields());
      }

      // $FF: synthetic method
      FieldGetter(Object x0) {
         this();
      }
   }

   private static class MethodGetterIncludingItds implements Iterators.Getter {
      private MethodGetterIncludingItds() {
      }

      public Iterator get(ResolvedType type) {
         ResolvedMember[] methods = type.getDeclaredMethods();
         if (type.interTypeMungers != null) {
            int additional = 0;
            Iterator i$ = type.interTypeMungers.iterator();

            while(i$.hasNext()) {
               ConcreteTypeMunger typeTransformer = (ConcreteTypeMunger)i$.next();
               ResolvedMember rm = typeTransformer.getSignature();
               if (rm != null) {
                  ++additional;
               }
            }

            if (additional > 0) {
               ResolvedMember[] methods2 = new ResolvedMember[methods.length + additional];
               System.arraycopy(methods, 0, methods2, 0, methods.length);
               additional = methods.length;
               Iterator i$ = type.interTypeMungers.iterator();

               while(i$.hasNext()) {
                  ConcreteTypeMunger typeTransformer = (ConcreteTypeMunger)i$.next();
                  ResolvedMember rm = typeTransformer.getSignature();
                  if (rm != null) {
                     methods2[additional++] = typeTransformer.getSignature();
                  }
               }

               methods = methods2;
            }
         }

         return Iterators.array(methods);
      }

      // $FF: synthetic method
      MethodGetterIncludingItds(Object x0) {
         this();
      }
   }

   private static class PointcutGetter implements Iterators.Getter {
      private PointcutGetter() {
      }

      public Iterator get(ResolvedType o) {
         return Iterators.array(o.getDeclaredPointcuts());
      }

      // $FF: synthetic method
      PointcutGetter(Object x0) {
         this();
      }
   }

   private static class MethodGetter implements Iterators.Getter {
      private MethodGetter() {
      }

      public Iterator get(ResolvedType type) {
         return Iterators.array(type.getDeclaredMethods());
      }

      // $FF: synthetic method
      MethodGetter(Object x0) {
         this();
      }
   }
}
