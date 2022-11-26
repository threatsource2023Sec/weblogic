package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BoundSet {
   static final BoundSet TRUE = new BoundSet();
   static final BoundSet FALSE = new BoundSet();
   HashMap boundsPerVariable = new HashMap();
   HashMap captures = new HashMap();
   Set inThrows = new HashSet();
   private TypeBound[] incorporatedBounds = new TypeBound[0];
   private TypeBound[] unincorporatedBounds = new TypeBound[1024];
   private int unincorporatedBoundsCount = 0;
   private TypeBound[] mostRecentBounds = new TypeBound[4];

   public BoundSet() {
   }

   public void addBoundsFromTypeParameters(InferenceContext18 context, TypeVariableBinding[] typeParameters, InferenceVariable[] variables) {
      int length = typeParameters.length;

      for(int i = 0; i < length; ++i) {
         TypeVariableBinding typeParameter = typeParameters[i];
         InferenceVariable variable = variables[i];
         TypeBound[] someBounds = typeParameter.getTypeBounds(variable, new InferenceSubstitution(context));
         boolean hasProperBound = false;
         if (someBounds.length > 0) {
            hasProperBound = this.addBounds(someBounds, context.environment);
         }

         if (!hasProperBound) {
            this.addBound(new TypeBound(variable, context.object, 2), context.environment);
         }
      }

   }

   public TypeBound[] flatten() {
      int size = 0;

      Iterator outerIt;
      for(outerIt = this.boundsPerVariable.values().iterator(); outerIt.hasNext(); size += ((ThreeSets)outerIt.next()).size()) {
      }

      TypeBound[] collected = new TypeBound[size];
      if (size == 0) {
         return collected;
      } else {
         outerIt = this.boundsPerVariable.values().iterator();

         for(int idx = 0; outerIt.hasNext(); idx = ((ThreeSets)outerIt.next()).flattenInto(collected, idx)) {
         }

         return collected;
      }
   }

   public BoundSet copy() {
      BoundSet copy = new BoundSet();
      Iterator setsIterator = this.boundsPerVariable.entrySet().iterator();

      while(setsIterator.hasNext()) {
         Map.Entry entry = (Map.Entry)setsIterator.next();
         copy.boundsPerVariable.put((InferenceVariable)entry.getKey(), ((ThreeSets)entry.getValue()).copy());
      }

      copy.inThrows.addAll(this.inThrows);
      copy.captures.putAll(this.captures);
      System.arraycopy(this.incorporatedBounds, 0, copy.incorporatedBounds = new TypeBound[this.incorporatedBounds.length], 0, this.incorporatedBounds.length);
      System.arraycopy(this.unincorporatedBounds, 0, copy.unincorporatedBounds = new TypeBound[this.unincorporatedBounds.length], 0, this.unincorporatedBounds.length);
      copy.unincorporatedBoundsCount = this.unincorporatedBoundsCount;
      return copy;
   }

   public void addBound(TypeBound bound, LookupEnvironment environment) {
      if (bound.relation != 2 || bound.right.id != 1) {
         if (bound.left != bound.right) {
            for(int recent = 0; recent < 4; ++recent) {
               if (bound.equals(this.mostRecentBounds[recent])) {
                  if (environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                     TypeBound existing = this.mostRecentBounds[recent];
                     long boundNullBits = bound.right.tagBits & 108086391056891904L;
                     long existingNullBits = existing.right.tagBits & 108086391056891904L;
                     if (boundNullBits != existingNullBits) {
                        if (existingNullBits == 0L) {
                           existing.right = bound.right;
                        } else if (boundNullBits != 0L) {
                           existing.right = environment.createAnnotatedType(existing.right, environment.nullAnnotationsFromTagBits(boundNullBits));
                        }
                     }
                  }

                  return;
               }
            }

            this.mostRecentBounds[3] = this.mostRecentBounds[2];
            this.mostRecentBounds[2] = this.mostRecentBounds[1];
            this.mostRecentBounds[1] = this.mostRecentBounds[0];
            this.mostRecentBounds[0] = bound;
            InferenceVariable variable = bound.left.prototype();
            ThreeSets three = (ThreeSets)this.boundsPerVariable.get(variable);
            if (three == null) {
               this.boundsPerVariable.put(variable, three = new ThreeSets());
            }

            if (three.addBound(bound)) {
               int unincorporatedBoundsLength = this.unincorporatedBounds.length;
               if (this.unincorporatedBoundsCount >= unincorporatedBoundsLength) {
                  System.arraycopy(this.unincorporatedBounds, 0, this.unincorporatedBounds = new TypeBound[unincorporatedBoundsLength * 2], 0, unincorporatedBoundsLength);
               }

               this.unincorporatedBounds[this.unincorporatedBoundsCount++] = bound;
               TypeBinding typeBinding = bound.right;
               if (bound.relation == 4 && typeBinding.isProperType(true)) {
                  three.setInstantiation(typeBinding, variable, environment);
               }

               if (bound.right instanceof InferenceVariable) {
                  InferenceVariable rightIV = (InferenceVariable)bound.right.prototype();
                  three = (ThreeSets)this.boundsPerVariable.get(rightIV);
                  if (three == null) {
                     this.boundsPerVariable.put(rightIV, three = new ThreeSets());
                  }

                  if (three.inverseBounds == null) {
                     three.inverseBounds = new HashMap();
                  }

                  three.inverseBounds.put(rightIV, bound);
               }
            }

         }
      }
   }

   private boolean addBounds(TypeBound[] newBounds, LookupEnvironment environment) {
      boolean hasProperBound = false;

      for(int i = 0; i < newBounds.length; ++i) {
         this.addBound(newBounds[i], environment);
         hasProperBound |= newBounds[i].isBound();
      }

      return hasProperBound;
   }

   public void addBounds(BoundSet that, LookupEnvironment environment) {
      if (that != null && environment != null) {
         this.addBounds(that.flatten(), environment);
      }
   }

   public boolean isInstantiated(InferenceVariable inferenceVariable) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(inferenceVariable.prototype());
      if (three != null) {
         return three.instantiation != null;
      } else {
         return false;
      }
   }

   public TypeBinding getInstantiation(InferenceVariable inferenceVariable, LookupEnvironment environment) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(inferenceVariable.prototype());
      if (three != null) {
         TypeBinding instantiation = three.instantiation;
         return environment != null && environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && instantiation != null && (instantiation.tagBits & 108086391056891904L) == 0L ? three.combineAndUseNullHints(instantiation, inferenceVariable.nullHints, environment) : instantiation;
      } else {
         return null;
      }
   }

   public int numUninstantiatedVariables(InferenceVariable[] variables) {
      int num = 0;

      for(int i = 0; i < variables.length; ++i) {
         if (!this.isInstantiated(variables[i])) {
            ++num;
         }
      }

      return num;
   }

   boolean incorporate(InferenceContext18 context) throws InferenceFailureException {
      if (this.unincorporatedBoundsCount == 0 && this.captures.size() == 0) {
         return true;
      } else {
         do {
            TypeBound[] freshBounds;
            System.arraycopy(this.unincorporatedBounds, 0, freshBounds = new TypeBound[this.unincorporatedBoundsCount], 0, this.unincorporatedBoundsCount);
            this.unincorporatedBoundsCount = 0;
            if (!this.incorporate(context, this.incorporatedBounds, freshBounds)) {
               return false;
            }

            if (!this.incorporate(context, freshBounds, freshBounds)) {
               return false;
            }

            int incorporatedLength = this.incorporatedBounds.length;
            int unincorporatedLength = freshBounds.length;
            TypeBound[] aggregate = new TypeBound[incorporatedLength + unincorporatedLength];
            System.arraycopy(this.incorporatedBounds, 0, aggregate, 0, incorporatedLength);
            System.arraycopy(freshBounds, 0, aggregate, incorporatedLength, unincorporatedLength);
            this.incorporatedBounds = aggregate;
         } while(this.unincorporatedBoundsCount > 0);

         return true;
      }
   }

   boolean incorporate(InferenceContext18 context, TypeBound[] first, TypeBound[] next) throws InferenceFailureException {
      boolean analyzeNull = context.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
      ConstraintTypeFormula[] mostRecentFormulas = new ConstraintTypeFormula[4];
      int i = 0;

      for(int iLength = first.length; i < iLength; ++i) {
         TypeBound boundI = first[i];
         int j = 0;

         for(int jLength = next.length; j < jLength; ++j) {
            TypeBound boundJ = next[j];
            if (boundI != boundJ) {
               int iteration = 1;

               do {
                  ConstraintTypeFormula newConstraint = null;
                  boolean deriveTypeArgumentConstraints = false;
                  if (iteration == 2) {
                     TypeBound boundX = boundI;
                     boundI = boundJ;
                     boundJ = boundX;
                  }

                  label118:
                  switch (boundI.relation) {
                     case 2:
                        switch (boundJ.relation) {
                           case 2:
                              newConstraint = this.combineEqualSupers(boundI, boundJ);
                              deriveTypeArgumentConstraints = TypeBinding.equalsEquals(boundI.left, boundJ.left);
                              break label118;
                           case 3:
                              newConstraint = this.combineSuperAndSub(boundJ, boundI);
                              break label118;
                           case 4:
                              newConstraint = this.combineSameSubSuper(boundJ, boundI);
                           default:
                              break label118;
                        }
                     case 3:
                        switch (boundJ.relation) {
                           case 2:
                              newConstraint = this.combineSuperAndSub(boundI, boundJ);
                              break label118;
                           case 3:
                              newConstraint = this.combineEqualSupers(boundI, boundJ);
                              break label118;
                           case 4:
                              newConstraint = this.combineSameSubSuper(boundJ, boundI);
                           default:
                              break label118;
                        }
                     case 4:
                        switch (boundJ.relation) {
                           case 2:
                           case 3:
                              newConstraint = this.combineSameSubSuper(boundI, boundJ);
                              break;
                           case 4:
                              newConstraint = this.combineSameSame(boundI, boundJ);
                        }
                  }

                  if (newConstraint != null) {
                     if (newConstraint.left == newConstraint.right) {
                        newConstraint = null;
                     } else if (newConstraint.equalsEquals(mostRecentFormulas[0]) || newConstraint.equalsEquals(mostRecentFormulas[1]) || newConstraint.equalsEquals(mostRecentFormulas[2]) || newConstraint.equalsEquals(mostRecentFormulas[3])) {
                        newConstraint = null;
                     }
                  }

                  if (newConstraint != null) {
                     mostRecentFormulas[3] = mostRecentFormulas[2];
                     mostRecentFormulas[2] = mostRecentFormulas[1];
                     mostRecentFormulas[1] = mostRecentFormulas[0];
                     mostRecentFormulas[0] = newConstraint;
                     if (!this.reduceOneConstraint(context, newConstraint)) {
                        return false;
                     }

                     if (analyzeNull) {
                        long nullHints = (newConstraint.left.tagBits | newConstraint.right.tagBits) & 108086391056891904L;
                        if (nullHints != 0L && (TypeBinding.equalsEquals(boundI.left, boundJ.left) || boundI.relation == 4 && TypeBinding.equalsEquals(boundI.right, boundJ.left) || boundJ.relation == 4 && TypeBinding.equalsEquals(boundI.left, boundJ.right))) {
                           boundI.nullHints |= nullHints;
                           boundJ.nullHints |= nullHints;
                        }
                     }
                  }

                  ConstraintFormula[] typeArgumentConstraints = deriveTypeArgumentConstraints ? this.deriveTypeArgumentConstraints(boundI, boundJ) : null;
                  if (typeArgumentConstraints != null) {
                     int k = 0;

                     for(int length = typeArgumentConstraints.length; k < length; ++k) {
                        if (!this.reduceOneConstraint(context, typeArgumentConstraints[k])) {
                           return false;
                        }
                     }
                  }

                  if (iteration == 2) {
                     TypeBound boundX = boundI;
                     boundI = boundJ;
                     boundJ = boundX;
                  }

                  if (first == next) {
                     break;
                  }

                  ++iteration;
               } while(iteration <= 2);
            }
         }
      }

      Iterator captIter = this.captures.entrySet().iterator();

      while(captIter.hasNext()) {
         Map.Entry capt = (Map.Entry)captIter.next();
         ParameterizedTypeBinding gAlpha = (ParameterizedTypeBinding)capt.getKey();
         ParameterizedTypeBinding gA = (ParameterizedTypeBinding)capt.getValue();
         ReferenceBinding g = (ReferenceBinding)gA.original();
         final TypeVariableBinding[] parameters = g.typeVariables();
         InferenceVariable[] alphas = new InferenceVariable[gAlpha.arguments.length];
         System.arraycopy(gAlpha.arguments, 0, alphas, 0, alphas.length);
         InferenceSubstitution theta = new InferenceSubstitution(context.environment, alphas, context.currentInvocation) {
            protected TypeBinding getP(int i) {
               return parameters[i];
            }
         };
         int i = 0;

         for(int length = parameters.length; i < length; ++i) {
            TypeVariableBinding pi = parameters[i];
            InferenceVariable alpha = (InferenceVariable)gAlpha.arguments[i];
            this.addBounds(pi.getTypeBounds(alpha, theta), context.environment);
            TypeBinding ai = gA.arguments[i];
            if (ai instanceof WildcardBinding) {
               WildcardBinding wildcardBinding = (WildcardBinding)ai;
               TypeBinding t = wildcardBinding.bound;
               ThreeSets three = (ThreeSets)this.boundsPerVariable.get(alpha.prototype());
               if (three != null) {
                  Iterator it;
                  TypeBound bound;
                  if (three.sameBounds != null) {
                     it = three.sameBounds.iterator();

                     while(it.hasNext()) {
                        bound = (TypeBound)it.next();
                        if (!(bound.right instanceof InferenceVariable)) {
                           return false;
                        }
                     }
                  }

                  if (three.subBounds != null) {
                     TypeBinding bi1 = pi.firstBound;
                     if (bi1 == null) {
                        bi1 = context.object;
                     }

                     it = three.subBounds.iterator();

                     while(it.hasNext()) {
                        TypeBound bound = (TypeBound)it.next();
                        if (!(bound.right instanceof InferenceVariable)) {
                           TypeBinding r = bound.right;
                           ReferenceBinding[] otherBounds = pi.superInterfaces;
                           Object bi;
                           if (otherBounds == Binding.NO_SUPERINTERFACES) {
                              bi = bi1;
                           } else {
                              int n = otherBounds.length + 1;
                              ReferenceBinding[] allBounds = new ReferenceBinding[n];
                              allBounds[0] = (ReferenceBinding)bi1;
                              System.arraycopy(otherBounds, 0, allBounds, 1, n - 1);
                              bi = context.environment.createIntersectionType18(allBounds);
                           }

                           this.addTypeBoundsFromWildcardBound(context, theta, wildcardBinding.boundKind, t, r, (TypeBinding)bi);
                        }
                     }
                  }

                  if (three.superBounds != null) {
                     it = three.superBounds.iterator();

                     while(it.hasNext()) {
                        bound = (TypeBound)it.next();
                        if (!(bound.right instanceof InferenceVariable)) {
                           if (wildcardBinding.boundKind != 2) {
                              return false;
                           }

                           this.reduceOneConstraint(context, ConstraintTypeFormula.create(bound.right, t, 2));
                        }
                     }
                  }
               }
            } else {
               this.addBound(new TypeBound(alpha, ai, 4), context.environment);
            }
         }
      }

      this.captures.clear();
      return true;
   }

   void addTypeBoundsFromWildcardBound(InferenceContext18 context, InferenceSubstitution theta, int boundKind, TypeBinding t, TypeBinding r, TypeBinding bi) throws InferenceFailureException {
      ConstraintFormula formula = null;
      if (boundKind == 1) {
         if (bi.id == 1) {
            formula = ConstraintTypeFormula.create(t, r, 2);
         }

         if (t.id == 1) {
            formula = ConstraintTypeFormula.create(theta.substitute(theta, bi), r, 2);
         }
      } else {
         formula = ConstraintTypeFormula.create(theta.substitute(theta, bi), r, 2);
      }

      if (formula != null) {
         this.reduceOneConstraint(context, formula);
      }

   }

   private ConstraintTypeFormula combineSameSame(TypeBound boundS, TypeBound boundT) {
      if (!TypeBinding.equalsEquals(boundS.left, boundT.left)) {
         ConstraintTypeFormula newConstraint = this.combineSameSameWithProperType(boundS, boundT);
         if (newConstraint != null) {
            return newConstraint;
         } else {
            newConstraint = this.combineSameSameWithProperType(boundT, boundS);
            return newConstraint != null ? newConstraint : null;
         }
      } else {
         return ConstraintTypeFormula.create(boundS.right, boundT.right, 4, boundS.isSoft || boundT.isSoft);
      }
   }

   private ConstraintTypeFormula combineSameSameWithProperType(TypeBound boundLeft, TypeBound boundRight) {
      TypeBinding u = boundLeft.right;
      if (!u.isProperType(true)) {
         return null;
      } else {
         InferenceVariable alpha = boundLeft.left;
         TypeBinding left = boundRight.left;
         TypeBinding right = boundRight.right.substituteInferenceVariable(alpha, u);
         return ConstraintTypeFormula.create(left, right, 4, boundLeft.isSoft || boundRight.isSoft);
      }
   }

   private ConstraintTypeFormula combineSameSubSuper(TypeBound boundS, TypeBound boundT) {
      InferenceVariable alpha = boundS.left;
      TypeBinding s = boundS.right;
      TypeBinding u;
      if (TypeBinding.equalsEquals(alpha, boundT.left)) {
         u = boundT.right;
         return ConstraintTypeFormula.create(s, u, boundT.relation, boundT.isSoft || boundS.isSoft);
      } else {
         InferenceVariable t;
         if (TypeBinding.equalsEquals(alpha, boundT.right)) {
            t = boundT.left;
            return ConstraintTypeFormula.create(t, s, boundT.relation, boundT.isSoft || boundS.isSoft);
         } else {
            if (boundS.right instanceof InferenceVariable) {
               alpha = (InferenceVariable)boundS.right;
               TypeBinding s = boundS.left;
               if (TypeBinding.equalsEquals(alpha, boundT.left)) {
                  u = boundT.right;
                  return ConstraintTypeFormula.create(s, u, boundT.relation, boundT.isSoft || boundS.isSoft);
               }

               if (TypeBinding.equalsEquals(alpha, boundT.right)) {
                  t = boundT.left;
                  return ConstraintTypeFormula.create(t, s, boundT.relation, boundT.isSoft || boundS.isSoft);
               }
            }

            u = boundS.right;
            if (u.isProperType(true)) {
               boolean substitute = TypeBinding.equalsEquals(alpha, boundT.left);
               TypeBinding left = substitute ? u : boundT.left;
               TypeBinding right = boundT.right.substituteInferenceVariable(alpha, u);
               substitute |= TypeBinding.notEquals(right, boundT.right);
               if (substitute) {
                  return ConstraintTypeFormula.create((TypeBinding)left, right, boundT.relation, boundT.isSoft || boundS.isSoft);
               }
            }

            return null;
         }
      }
   }

   private ConstraintTypeFormula combineSuperAndSub(TypeBound boundS, TypeBound boundT) {
      InferenceVariable alpha = boundS.left;
      if (TypeBinding.equalsEquals(alpha, boundT.left)) {
         return ConstraintTypeFormula.create(boundS.right, boundT.right, 2, boundT.isSoft || boundS.isSoft);
      } else {
         if (boundS.right instanceof InferenceVariable) {
            alpha = (InferenceVariable)boundS.right;
            if (TypeBinding.equalsEquals(alpha, boundT.right)) {
               return ConstraintTypeFormula.create(boundS.left, boundT.left, 3, boundT.isSoft || boundS.isSoft);
            }
         }

         return null;
      }
   }

   private ConstraintTypeFormula combineEqualSupers(TypeBound boundS, TypeBound boundT) {
      if (TypeBinding.equalsEquals(boundS.left, boundT.right)) {
         return ConstraintTypeFormula.create(boundT.left, boundS.right, boundS.relation, boundT.isSoft || boundS.isSoft);
      } else {
         return !TypeBinding.equalsEquals(boundS.right, boundT.left) ? null : ConstraintTypeFormula.create(boundS.left, boundT.right, boundS.relation, boundT.isSoft || boundS.isSoft);
      }
   }

   private ConstraintTypeFormula[] deriveTypeArgumentConstraints(TypeBound boundS, TypeBound boundT) {
      TypeBinding[] supers = this.superTypesWithCommonGenericType(boundS.right, boundT.right);
      return supers == null ? null : this.typeArgumentEqualityConstraints(supers[0], supers[1], boundS.isSoft || boundT.isSoft);
   }

   private ConstraintTypeFormula[] typeArgumentEqualityConstraints(TypeBinding s, TypeBinding t, boolean isSoft) {
      if (s != null && s.kind() == 260 && t != null && t.kind() == 260) {
         if (TypeBinding.equalsEquals(s, t)) {
            return null;
         } else {
            TypeBinding[] sis = s.typeArguments();
            TypeBinding[] tis = t.typeArguments();
            if (sis != null && tis != null && sis.length == tis.length) {
               List result = new ArrayList();

               for(int i = 0; i < sis.length; ++i) {
                  TypeBinding si = sis[i];
                  TypeBinding ti = tis[i];
                  if (!si.isWildcard() && !ti.isWildcard() && !TypeBinding.equalsEquals(si, ti)) {
                     result.add(ConstraintTypeFormula.create(si, ti, 4, isSoft));
                  }
               }

               if (result.size() > 0) {
                  return (ConstraintTypeFormula[])result.toArray(new ConstraintTypeFormula[result.size()]);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public boolean reduceOneConstraint(InferenceContext18 context, ConstraintFormula currentConstraint) throws InferenceFailureException {
      Object result = currentConstraint.reduce(context);
      if (result == ReductionResult.FALSE) {
         return false;
      } else if (result == ReductionResult.TRUE) {
         return true;
      } else if (result == currentConstraint) {
         throw new IllegalStateException("Failed to reduce constraint formula");
      } else {
         if (result != null) {
            if (result instanceof ConstraintFormula) {
               if (!this.reduceOneConstraint(context, (ConstraintFormula)result)) {
                  return false;
               }
            } else if (result instanceof ConstraintFormula[]) {
               ConstraintFormula[] resultArray = (ConstraintFormula[])result;

               for(int i = 0; i < resultArray.length; ++i) {
                  if (!this.reduceOneConstraint(context, resultArray[i])) {
                     return false;
                  }
               }
            } else {
               this.addBound((TypeBound)result, context.environment);
            }
         }

         return true;
      }
   }

   public boolean dependsOnResolutionOf(InferenceVariable alpha, InferenceVariable beta) {
      alpha = alpha.prototype();
      beta = beta.prototype();
      if (TypeBinding.equalsEquals(alpha, beta)) {
         return true;
      } else {
         Iterator captureIter = this.captures.entrySet().iterator();
         boolean betaIsInCaptureLhs = false;

         while(captureIter.hasNext()) {
            Map.Entry entry = (Map.Entry)captureIter.next();
            ParameterizedTypeBinding g = (ParameterizedTypeBinding)entry.getKey();

            for(int i = 0; i < g.arguments.length; ++i) {
               if (TypeBinding.equalsEquals(g.arguments[i], alpha)) {
                  ParameterizedTypeBinding captured = (ParameterizedTypeBinding)entry.getValue();
                  if (captured.mentionsAny(new TypeBinding[]{beta}, -1)) {
                     return true;
                  }

                  if (g.mentionsAny(new TypeBinding[]{beta}, i)) {
                     return true;
                  }
               } else if (TypeBinding.equalsEquals(g.arguments[i], beta)) {
                  betaIsInCaptureLhs = true;
               }
            }
         }

         ThreeSets sets;
         if (betaIsInCaptureLhs) {
            sets = (ThreeSets)this.boundsPerVariable.get(beta);
            if (sets != null && sets.hasDependency(alpha)) {
               return true;
            }
         } else {
            sets = (ThreeSets)this.boundsPerVariable.get(alpha);
            if (sets != null && sets.hasDependency(beta)) {
               return true;
            }
         }

         return false;
      }
   }

   List computeConnectedComponents(InferenceVariable[] inferenceVariables) {
      Map allEdges = new HashMap();

      int j;
      for(int i = 0; i < inferenceVariables.length; ++i) {
         InferenceVariable iv1 = inferenceVariables[i];
         HashSet targetSet = new HashSet();
         allEdges.put(iv1, targetSet);

         for(j = 0; j < i; ++j) {
            InferenceVariable iv2 = inferenceVariables[j];
            if (this.dependsOnResolutionOf(iv1, iv2) || this.dependsOnResolutionOf(iv2, iv1)) {
               targetSet.add(iv2);
               ((Set)allEdges.get(iv2)).add(iv1);
            }
         }
      }

      Set visited = new HashSet();
      List allComponents = new ArrayList();
      InferenceVariable[] var8 = inferenceVariables;
      int var13 = inferenceVariables.length;

      for(j = 0; j < var13; ++j) {
         InferenceVariable inferenceVariable = var8[j];
         Set component = new HashSet();
         this.addConnected(component, inferenceVariable, allEdges, visited);
         if (!component.isEmpty()) {
            allComponents.add(component);
         }
      }

      return allComponents;
   }

   private void addConnected(Set component, InferenceVariable seed, Map allEdges, Set visited) {
      if (visited.add(seed)) {
         component.add(seed);
         Iterator var6 = ((Set)allEdges.get(seed)).iterator();

         while(var6.hasNext()) {
            InferenceVariable next = (InferenceVariable)var6.next();
            this.addConnected(component, next, allEdges, visited);
         }
      }

   }

   public boolean hasCaptureBound(Set variableSet) {
      Iterator captureIter = this.captures.keySet().iterator();

      while(captureIter.hasNext()) {
         ParameterizedTypeBinding g = (ParameterizedTypeBinding)captureIter.next();

         for(int i = 0; i < g.arguments.length; ++i) {
            if (variableSet.contains(g.arguments[i])) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasOnlyTrivialExceptionBounds(InferenceVariable variable, TypeBinding[] upperBounds) {
      if (upperBounds != null) {
         int i = 0;

         while(i < upperBounds.length) {
            switch (upperBounds[i].id) {
               case 1:
               case 21:
               case 25:
                  ++i;
                  break;
               default:
                  return false;
            }
         }
      }

      return true;
   }

   public TypeBinding[] upperBounds(InferenceVariable variable, boolean onlyProper) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(variable.prototype());
      return three != null && three.subBounds != null ? three.upperBounds(onlyProper, variable) : Binding.NO_TYPES;
   }

   TypeBinding[] lowerBounds(InferenceVariable variable, boolean onlyProper) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(variable.prototype());
      return three != null && three.superBounds != null ? three.lowerBounds(onlyProper, variable) : Binding.NO_TYPES;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("Type Bounds:\n");
      TypeBound[] flattened = this.flatten();

      for(int i = 0; i < flattened.length; ++i) {
         buf.append('\t').append(flattened[i].toString()).append('\n');
      }

      buf.append("Capture Bounds:\n");
      Iterator captIter = this.captures.entrySet().iterator();

      while(captIter.hasNext()) {
         Map.Entry capt = (Map.Entry)captIter.next();
         String lhs = String.valueOf(((TypeBinding)capt.getKey()).shortReadableName());
         String rhs = String.valueOf(((TypeBinding)capt.getValue()).shortReadableName());
         buf.append('\t').append(lhs).append(" = capt(").append(rhs).append(")\n");
      }

      return buf.toString();
   }

   public TypeBinding findWrapperTypeBound(InferenceVariable variable) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(variable.prototype());
      return three == null ? null : three.findSingleWrapperType();
   }

   public boolean condition18_5_2_bullet_3_3_1(InferenceVariable alpha, TypeBinding targetType) {
      if (targetType.isBaseType()) {
         return false;
      } else if (InferenceContext18.parameterizedWithWildcard(targetType) != null) {
         return false;
      } else {
         ThreeSets ts = (ThreeSets)this.boundsPerVariable.get(alpha.prototype());
         if (ts == null) {
            return false;
         } else {
            Iterator bounds;
            TypeBound bound;
            if (ts.sameBounds != null) {
               bounds = ts.sameBounds.iterator();

               while(bounds.hasNext()) {
                  bound = (TypeBound)bounds.next();
                  if (InferenceContext18.parameterizedWithWildcard(bound.right) != null) {
                     return true;
                  }
               }
            }

            if (ts.superBounds != null) {
               bounds = ts.superBounds.iterator();

               while(bounds.hasNext()) {
                  bound = (TypeBound)bounds.next();
                  if (InferenceContext18.parameterizedWithWildcard(bound.right) != null) {
                     return true;
                  }
               }
            }

            if (ts.superBounds != null) {
               ArrayList superBounds = new ArrayList(ts.superBounds);
               int len = superBounds.size();

               for(int i = 0; i < len; ++i) {
                  TypeBinding s1 = ((TypeBound)superBounds.get(i)).right;

                  for(int j = i + 1; j < len; ++j) {
                     TypeBinding s2 = ((TypeBound)superBounds.get(j)).right;
                     TypeBinding[] supers = this.superTypesWithCommonGenericType(s1, s2);
                     if (supers != null && supers[0].isProperType(true) && supers[1].isProperType(true) && !TypeBinding.equalsEquals(supers[0], supers[1])) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }
   }

   public boolean condition18_5_2_bullet_3_3_2(InferenceVariable alpha, TypeBinding targetType, InferenceContext18 ctx18) {
      if (!targetType.isParameterizedType()) {
         return false;
      } else {
         TypeBinding g = targetType.original();
         ThreeSets ts = (ThreeSets)this.boundsPerVariable.get(alpha.prototype());
         if (ts == null) {
            return false;
         } else {
            Iterator boundIterator;
            TypeBound b;
            if (ts.sameBounds != null) {
               boundIterator = ts.sameBounds.iterator();

               while(boundIterator.hasNext()) {
                  b = (TypeBound)boundIterator.next();
                  if (this.superOnlyRaw(g, b.right, ctx18.environment)) {
                     return true;
                  }
               }
            }

            if (ts.superBounds != null) {
               boundIterator = ts.superBounds.iterator();

               while(boundIterator.hasNext()) {
                  b = (TypeBound)boundIterator.next();
                  if (this.superOnlyRaw(g, b.right, ctx18.environment)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   private boolean superOnlyRaw(TypeBinding g, TypeBinding s, LookupEnvironment env) {
      if (s instanceof InferenceVariable) {
         return false;
      } else {
         TypeBinding superType = s.findSuperTypeOriginatingFrom(g);
         return superType != null && !superType.isParameterizedType() ? s.isCompatibleWith(env.convertToRawType(g, false)) : false;
      }
   }

   protected TypeBinding[] superTypesWithCommonGenericType(TypeBinding s, TypeBinding t) {
      if (s != null && s.id != 1 && t != null && t.id != 1) {
         if (TypeBinding.equalsEquals(s.original(), t.original())) {
            return new TypeBinding[]{s, t};
         } else {
            TypeBinding tSuper = t.findSuperTypeOriginatingFrom(s);
            if (tSuper != null) {
               return new TypeBinding[]{s, tSuper};
            } else {
               TypeBinding[] result = this.superTypesWithCommonGenericType(s.superclass(), t);
               if (result != null) {
                  return result;
               } else {
                  ReferenceBinding[] superInterfaces = s.superInterfaces();
                  if (superInterfaces != null) {
                     for(int i = 0; i < superInterfaces.length; ++i) {
                        result = this.superTypesWithCommonGenericType(superInterfaces[i], t);
                        if (result != null) {
                           return result;
                        }
                     }
                  }

                  return null;
               }
            }
         }
      } else {
         return null;
      }
   }

   public TypeBinding getEquivalentOuterVariable(InferenceVariable variable, InferenceVariable[] outerVariables) {
      ThreeSets three = (ThreeSets)this.boundsPerVariable.get(variable);
      if (three != null) {
         Iterator var5 = three.sameBounds.iterator();

         while(var5.hasNext()) {
            TypeBound bound = (TypeBound)var5.next();
            InferenceVariable[] var9 = outerVariables;
            int var8 = outerVariables.length;

            for(int var7 = 0; var7 < var8; ++var7) {
               InferenceVariable iv = var9[var7];
               if (TypeBinding.equalsEquals(bound.right, iv)) {
                  return iv;
               }
            }
         }
      }

      InferenceVariable[] var13 = outerVariables;
      int var12 = outerVariables.length;

      for(int var11 = 0; var11 < var12; ++var11) {
         InferenceVariable iv = var13[var11];
         three = (ThreeSets)this.boundsPerVariable.get(iv);
         if (three != null && three.sameBounds != null) {
            Iterator var15 = three.sameBounds.iterator();

            while(var15.hasNext()) {
               TypeBound bound = (TypeBound)var15.next();
               if (TypeBinding.equalsEquals(bound.right, variable)) {
                  return iv;
               }
            }
         }
      }

      return null;
   }

   private class ThreeSets {
      Set superBounds;
      Set sameBounds;
      Set subBounds;
      TypeBinding instantiation;
      Map inverseBounds;
      Set dependencies;

      public ThreeSets() {
      }

      public boolean addBound(TypeBound bound) {
         boolean result = this.addBound1(bound);
         if (result) {
            Set set = this.dependencies == null ? new HashSet() : this.dependencies;
            bound.right.collectInferenceVariables((Set)set);
            if (this.dependencies == null && ((Set)set).size() > 0) {
               this.dependencies = (Set)set;
            }
         }

         return result;
      }

      private boolean addBound1(TypeBound bound) {
         switch (bound.relation) {
            case 2:
               if (this.subBounds == null) {
                  this.subBounds = new HashSet();
               }

               return this.subBounds.add(bound);
            case 3:
               if (this.superBounds == null) {
                  this.superBounds = new HashSet();
               }

               return this.superBounds.add(bound);
            case 4:
               if (this.sameBounds == null) {
                  this.sameBounds = new HashSet();
               }

               return this.sameBounds.add(bound);
            default:
               throw new IllegalArgumentException("Unexpected bound relation in : " + bound);
         }
      }

      public TypeBinding[] lowerBounds(boolean onlyProper, InferenceVariable variable) {
         TypeBinding[] boundTypes = new TypeBinding[this.superBounds.size()];
         Iterator it = this.superBounds.iterator();
         long nullHints = variable.nullHints;
         int i = 0;

         while(true) {
            TypeBound current;
            TypeBinding boundType;
            do {
               if (!it.hasNext()) {
                  if (i == 0) {
                     return Binding.NO_TYPES;
                  }

                  if (i < boundTypes.length) {
                     System.arraycopy(boundTypes, 0, boundTypes = new TypeBinding[i], 0, i);
                  }

                  this.useNullHints(nullHints, boundTypes, variable.environment);
                  InferenceContext18.sortTypes(boundTypes);
                  return boundTypes;
               }

               current = (TypeBound)it.next();
               boundType = current.right;
            } while(onlyProper && !boundType.isProperType(true));

            boundTypes[i++] = boundType;
            nullHints |= current.nullHints;
         }
      }

      public TypeBinding[] upperBounds(boolean onlyProper, InferenceVariable variable) {
         ReferenceBinding[] rights = new ReferenceBinding[this.subBounds.size()];
         TypeBinding simpleUpper = null;
         Iterator it = this.subBounds.iterator();
         long nullHints = variable.nullHints;
         int i = 0;

         while(true) {
            TypeBinding right;
            do {
               if (!it.hasNext()) {
                  if (i == 0) {
                     return simpleUpper != null ? new TypeBinding[]{simpleUpper} : Binding.NO_TYPES;
                  }

                  if (i == 1 && simpleUpper != null) {
                     return new TypeBinding[]{simpleUpper};
                  }

                  if (i < rights.length) {
                     System.arraycopy(rights, 0, rights = new ReferenceBinding[i], 0, i);
                  }

                  this.useNullHints(nullHints, rights, variable.environment);
                  InferenceContext18.sortTypes(rights);
                  return rights;
               }

               right = ((TypeBound)it.next()).right;
            } while(onlyProper && !right.isProperType(true));

            if (right instanceof ReferenceBinding) {
               rights[i++] = (ReferenceBinding)right;
               nullHints |= right.tagBits & 108086391056891904L;
            } else {
               if (simpleUpper != null) {
                  return Binding.NO_TYPES;
               }

               simpleUpper = right;
            }
         }
      }

      public boolean hasDependency(InferenceVariable beta) {
         if (this.dependencies != null && this.dependencies.contains(beta)) {
            return true;
         } else {
            return this.inverseBounds != null && this.inverseBounds.containsKey(beta);
         }
      }

      public int size() {
         int size = 0;
         if (this.superBounds != null) {
            size += this.superBounds.size();
         }

         if (this.sameBounds != null) {
            size += this.sameBounds.size();
         }

         if (this.subBounds != null) {
            size += this.subBounds.size();
         }

         return size;
      }

      public int flattenInto(TypeBound[] collected, int idx) {
         int len;
         if (this.superBounds != null) {
            len = this.superBounds.size();
            System.arraycopy(this.superBounds.toArray(), 0, collected, idx, len);
            idx += len;
         }

         if (this.sameBounds != null) {
            len = this.sameBounds.size();
            System.arraycopy(this.sameBounds.toArray(), 0, collected, idx, len);
            idx += len;
         }

         if (this.subBounds != null) {
            len = this.subBounds.size();
            System.arraycopy(this.subBounds.toArray(), 0, collected, idx, len);
            idx += len;
         }

         return idx;
      }

      public ThreeSets copy() {
         ThreeSets copy = BoundSet.this.new ThreeSets();
         if (this.superBounds != null) {
            copy.superBounds = new HashSet(this.superBounds);
         }

         if (this.sameBounds != null) {
            copy.sameBounds = new HashSet(this.sameBounds);
         }

         if (this.subBounds != null) {
            copy.subBounds = new HashSet(this.subBounds);
         }

         copy.instantiation = this.instantiation;
         if (this.dependencies != null) {
            copy.dependencies = new HashSet(this.dependencies);
         }

         return copy;
      }

      public TypeBinding findSingleWrapperType() {
         if (this.instantiation != null && this.instantiation.isProperType(true)) {
            switch (this.instantiation.id) {
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
                  return this.instantiation;
            }
         }

         Iterator it;
         TypeBinding boundType;
         if (this.subBounds != null) {
            it = this.subBounds.iterator();

            while(it.hasNext()) {
               boundType = ((TypeBound)it.next()).right;
               if (boundType.isProperType(true)) {
                  switch (boundType.id) {
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                        return boundType;
                  }
               }
            }
         }

         if (this.superBounds != null) {
            it = this.superBounds.iterator();

            while(it.hasNext()) {
               boundType = ((TypeBound)it.next()).right;
               if (boundType.isProperType(true)) {
                  switch (boundType.id) {
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                        return boundType;
                  }
               }
            }
         }

         return null;
      }

      private void useNullHints(long nullHints, TypeBinding[] boundTypes, LookupEnvironment environment) {
         if (nullHints == 108086391056891904L) {
            for(int ix = 0; ix < boundTypes.length; ++ix) {
               boundTypes[ix] = boundTypes[ix].withoutToplevelNullAnnotation();
            }
         } else {
            AnnotationBinding[] annot = environment.nullAnnotationsFromTagBits(nullHints);
            if (annot != null) {
               for(int i = 0; i < boundTypes.length; ++i) {
                  boundTypes[i] = environment.createAnnotatedType(boundTypes[i], annot);
               }
            }
         }

      }

      TypeBinding combineAndUseNullHints(TypeBinding type, long nullHints, LookupEnvironment environment) {
         Iterator it;
         if (this.sameBounds != null) {
            for(it = this.sameBounds.iterator(); it.hasNext(); nullHints |= ((TypeBound)it.next()).nullHints) {
            }
         }

         if (this.superBounds != null) {
            for(it = this.superBounds.iterator(); it.hasNext(); nullHints |= ((TypeBound)it.next()).nullHints) {
            }
         }

         if (this.subBounds != null) {
            for(it = this.subBounds.iterator(); it.hasNext(); nullHints |= ((TypeBound)it.next()).nullHints) {
            }
         }

         if (nullHints == 108086391056891904L) {
            return type.withoutToplevelNullAnnotation();
         } else {
            AnnotationBinding[] annot = environment.nullAnnotationsFromTagBits(nullHints);
            return annot != null ? environment.createAnnotatedType(type, annot) : type;
         }
      }

      public void setInstantiation(TypeBinding type, InferenceVariable variable, LookupEnvironment environment) {
         if (environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            long variableBits = variable.tagBits & 108086391056891904L;
            long allBits = type.tagBits | variableBits;
            if (this.instantiation != null) {
               allBits |= this.instantiation.tagBits;
            }

            allBits &= 108086391056891904L;
            if (allBits == 108086391056891904L) {
               allBits = variableBits;
            }

            if (allBits != (type.tagBits & 108086391056891904L)) {
               AnnotationBinding[] annot = environment.nullAnnotationsFromTagBits(allBits);
               if (annot != null) {
                  type = environment.createAnnotatedType(type.withoutToplevelNullAnnotation(), annot);
               } else if (type.hasNullTypeAnnotations()) {
                  type = type.withoutToplevelNullAnnotation();
               }
            }
         }

         this.instantiation = type;
      }
   }
}
