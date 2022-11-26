package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.patterns.Declare;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReferenceType extends ResolvedType {
   public static final ReferenceType[] EMPTY_ARRAY = new ReferenceType[0];
   private final List derivativeTypes = new ArrayList();
   ReferenceType genericType = null;
   ReferenceType rawType = null;
   ReferenceTypeDelegate delegate = null;
   int startPos = 0;
   int endPos = 0;
   ResolvedMember[] parameterizedMethods = null;
   ResolvedMember[] parameterizedFields = null;
   ResolvedMember[] parameterizedPointcuts = null;
   WeakReference parameterizedInterfaces = new WeakReference((Object)null);
   Collection parameterizedDeclares = null;
   private ResolvedType[] annotationTypes = null;
   private AnnotationAJ[] annotations = null;
   private ResolvedType newSuperclass;
   private ResolvedType[] newInterfaces;
   WeakReference superclassReference = new WeakReference((Object)null);

   public ReferenceType(String signature, World world) {
      super(signature, world);
   }

   public ReferenceType(String signature, String signatureErasure, World world) {
      super(signature, signatureErasure, world);
   }

   public static ReferenceType fromTypeX(UnresolvedType tx, World world) {
      ReferenceType rt = new ReferenceType(tx.getErasureSignature(), world);
      rt.typeKind = tx.typeKind;
      return rt;
   }

   public ReferenceType(ResolvedType theGenericType, ResolvedType[] theParameters, World aWorld) {
      super(makeParameterizedSignature(theGenericType, theParameters), theGenericType.signatureErasure, aWorld);
      ReferenceType genericReferenceType = (ReferenceType)theGenericType;
      this.typeParameters = theParameters;
      this.genericType = genericReferenceType;
      this.typeKind = UnresolvedType.TypeKind.PARAMETERIZED;
      this.delegate = genericReferenceType.getDelegate();
      genericReferenceType.addDependentType(this);
   }

   synchronized void addDependentType(ReferenceType dependent) {
      synchronized(this.derivativeTypes) {
         this.derivativeTypes.add(new WeakReference(dependent));
      }
   }

   public void checkDuplicates(ReferenceType newRt) {
      synchronized(this.derivativeTypes) {
         List forRemoval = new ArrayList();
         Iterator i$ = this.derivativeTypes.iterator();

         while(i$.hasNext()) {
            WeakReference derivativeTypeReference = (WeakReference)i$.next();
            ReferenceType derivativeType = (ReferenceType)derivativeTypeReference.get();
            if (derivativeType == null) {
               forRemoval.add(derivativeTypeReference);
            } else if (derivativeType.getTypekind() == newRt.getTypekind() && this.equal2(newRt.getTypeParameters(), derivativeType.getTypeParameters()) && World.TypeMap.useExpendableMap) {
               throw new IllegalStateException();
            }
         }

         this.derivativeTypes.removeAll(forRemoval);
      }
   }

   private boolean equal2(UnresolvedType[] typeParameters, UnresolvedType[] resolvedParameters) {
      if (typeParameters.length != resolvedParameters.length) {
         return false;
      } else {
         int len = typeParameters.length;

         for(int p = 0; p < len; ++p) {
            if (!typeParameters[p].equals(resolvedParameters[p])) {
               return false;
            }
         }

         return true;
      }
   }

   public String getSignatureForAttribute() {
      return this.genericType != null && this.typeParameters != null ? makeDeclaredSignature(this.genericType, this.typeParameters) : this.getSignature();
   }

   public ReferenceType(UnresolvedType genericType, World world) {
      super(genericType.getSignature(), world);
      this.typeKind = UnresolvedType.TypeKind.GENERIC;
      this.typeVariables = genericType.typeVariables;
   }

   public boolean isClass() {
      return this.getDelegate().isClass();
   }

   public int getCompilerVersion() {
      return this.getDelegate().getCompilerVersion();
   }

   public boolean isGenericType() {
      return !this.isParameterizedType() && !this.isRawType() && this.getDelegate().isGeneric();
   }

   public String getGenericSignature() {
      String sig = this.getDelegate().getDeclaredGenericSignature();
      return sig == null ? "" : sig;
   }

   public AnnotationAJ[] getAnnotations() {
      return this.getDelegate().getAnnotations();
   }

   public boolean hasAnnotations() {
      return this.getDelegate().hasAnnotations();
   }

   public void addAnnotation(AnnotationAJ annotationX) {
      if (this.annotations == null) {
         this.annotations = new AnnotationAJ[]{annotationX};
      } else {
         AnnotationAJ[] newAnnotations = new AnnotationAJ[this.annotations.length + 1];
         System.arraycopy(this.annotations, 0, newAnnotations, 1, this.annotations.length);
         newAnnotations[0] = annotationX;
         this.annotations = newAnnotations;
      }

      this.addAnnotationType(annotationX.getType());
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      boolean onDelegate = this.getDelegate().hasAnnotation(ofType);
      if (onDelegate) {
         return true;
      } else {
         if (this.annotationTypes != null) {
            for(int i = 0; i < this.annotationTypes.length; ++i) {
               if (this.annotationTypes[i].equals(ofType)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private void addAnnotationType(ResolvedType ofType) {
      if (this.annotationTypes == null) {
         this.annotationTypes = new ResolvedType[1];
         this.annotationTypes[0] = ofType;
      } else {
         ResolvedType[] newAnnotationTypes = new ResolvedType[this.annotationTypes.length + 1];
         System.arraycopy(this.annotationTypes, 0, newAnnotationTypes, 1, this.annotationTypes.length);
         newAnnotationTypes[0] = ofType;
         this.annotationTypes = newAnnotationTypes;
      }

   }

   public ResolvedType[] getAnnotationTypes() {
      if (this.getDelegate() == null) {
         throw new BCException("Unexpected null delegate for type " + this.getName());
      } else if (this.annotationTypes == null) {
         return this.getDelegate().getAnnotationTypes();
      } else {
         ResolvedType[] delegateAnnotationTypes = this.getDelegate().getAnnotationTypes();
         ResolvedType[] result = new ResolvedType[this.annotationTypes.length + delegateAnnotationTypes.length];
         System.arraycopy(delegateAnnotationTypes, 0, result, 0, delegateAnnotationTypes.length);
         System.arraycopy(this.annotationTypes, 0, result, delegateAnnotationTypes.length, this.annotationTypes.length);
         return result;
      }
   }

   public String getNameAsIdentifier() {
      return this.getRawName().replace('.', '_');
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      AnnotationAJ[] axs = this.getDelegate().getAnnotations();
      if (axs != null) {
         for(int i = 0; i < axs.length; ++i) {
            if (axs[i].getTypeSignature().equals(ofType.getSignature())) {
               return axs[i];
            }
         }
      }

      if (this.annotations != null) {
         String searchSig = ofType.getSignature();

         for(int i = 0; i < this.annotations.length; ++i) {
            if (this.annotations[i].getTypeSignature().equals(searchSig)) {
               return this.annotations[i];
            }
         }
      }

      return null;
   }

   public boolean isAspect() {
      return this.getDelegate().isAspect();
   }

   public boolean isAnnotationStyleAspect() {
      return this.getDelegate().isAnnotationStyleAspect();
   }

   public boolean isEnum() {
      return this.getDelegate().isEnum();
   }

   public boolean isAnnotation() {
      return this.getDelegate().isAnnotation();
   }

   public boolean isAnonymous() {
      return this.getDelegate().isAnonymous();
   }

   public boolean isNested() {
      return this.getDelegate().isNested();
   }

   public ResolvedType getOuterClass() {
      return this.getDelegate().getOuterClass();
   }

   public String getRetentionPolicy() {
      return this.getDelegate().getRetentionPolicy();
   }

   public boolean isAnnotationWithRuntimeRetention() {
      return this.getDelegate().isAnnotationWithRuntimeRetention();
   }

   public boolean canAnnotationTargetType() {
      return this.getDelegate().canAnnotationTargetType();
   }

   public AnnotationTargetKind[] getAnnotationTargetKinds() {
      return this.getDelegate().getAnnotationTargetKinds();
   }

   public boolean isCoerceableFrom(ResolvedType o) {
      ResolvedType other = o.resolve(this.world);
      if (!this.isAssignableFrom(other) && !other.isAssignableFrom(this)) {
         if (this.isParameterizedType() && other.isParameterizedType()) {
            return this.isCoerceableFromParameterizedType(other);
         } else if (this.isParameterizedType() && other.isRawType()) {
            return ((ReferenceType)this.getRawType()).isCoerceableFrom(other.getGenericType());
         } else if (this.isRawType() && other.isParameterizedType()) {
            return this.getGenericType().isCoerceableFrom(other.getRawType());
         } else if (!this.isInterface() && !other.isInterface()) {
            return false;
         } else if (!this.isFinal() && !other.isFinal()) {
            ResolvedMember[] a = this.getDeclaredMethods();
            ResolvedMember[] b = other.getDeclaredMethods();
            int ai = 0;

            for(int alen = a.length; ai < alen; ++ai) {
               int bi = 0;

               for(int blen = b.length; bi < blen; ++bi) {
                  if (!b[bi].isCompatibleWith(a[ai])) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private final boolean isCoerceableFromParameterizedType(ResolvedType other) {
      if (!other.isParameterizedType()) {
         return false;
      } else {
         ResolvedType myRawType = this.getRawType();
         ResolvedType theirRawType = other.getRawType();
         if ((myRawType == theirRawType || myRawType.isCoerceableFrom(theirRawType)) && this.getTypeParameters().length == other.getTypeParameters().length) {
            ResolvedType[] myTypeParameters = this.getResolvedTypeParameters();
            ResolvedType[] theirTypeParameters = other.getResolvedTypeParameters();

            for(int i = 0; i < myTypeParameters.length; ++i) {
               if (myTypeParameters[i] != theirTypeParameters[i]) {
                  BoundedReferenceType wildcard;
                  if (myTypeParameters[i].isGenericWildcard()) {
                     wildcard = (BoundedReferenceType)myTypeParameters[i];
                     if (!wildcard.canBeCoercedTo(theirTypeParameters[i])) {
                        return false;
                     }
                  } else {
                     TypeVariable tv;
                     TypeVariableReferenceType tvrt;
                     if (myTypeParameters[i].isTypeVariableReference()) {
                        tvrt = (TypeVariableReferenceType)myTypeParameters[i];
                        tv = tvrt.getTypeVariable();
                        tv.resolve(this.world);
                        if (!tv.canBeBoundTo(theirTypeParameters[i])) {
                           return false;
                        }
                     } else if (theirTypeParameters[i].isTypeVariableReference()) {
                        tvrt = (TypeVariableReferenceType)theirTypeParameters[i];
                        tv = tvrt.getTypeVariable();
                        tv.resolve(this.world);
                        if (!tv.canBeBoundTo(myTypeParameters[i])) {
                           return false;
                        }
                     } else {
                        if (!theirTypeParameters[i].isGenericWildcard()) {
                           return false;
                        }

                        wildcard = (BoundedReferenceType)theirTypeParameters[i];
                        if (!wildcard.canBeCoercedTo(myTypeParameters[i])) {
                           return false;
                        }
                     }
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean isAssignableFrom(ResolvedType other) {
      return this.isAssignableFrom(other, false);
   }

   public boolean isAssignableFrom(ResolvedType other, boolean allowMissing) {
      if (other.isPrimitiveType()) {
         if (!this.world.isInJava5Mode()) {
            return false;
         }

         if (ResolvedType.validBoxing.contains(this.getSignature() + other.getSignature())) {
            return true;
         }
      }

      if (this == other) {
         return true;
      } else if (this.getSignature().equals("Ljava/lang/Object;")) {
         return true;
      } else if (!this.isTypeVariableReference() && other.getSignature().equals("Ljava/lang/Object;")) {
         return false;
      } else {
         boolean thisRaw = this.isRawType();
         if (thisRaw && other.isParameterizedOrGenericType()) {
            return this.isAssignableFrom(other.getRawType());
         } else {
            boolean thisGeneric = this.isGenericType();
            if (thisGeneric && other.isParameterizedOrRawType()) {
               return this.isAssignableFrom(other.getGenericType());
            } else {
               ResolvedType[] myParameters;
               int len$;
               if (this.isParameterizedType() && ((ReferenceType)this.getRawType()).isAssignableFrom(other)) {
                  boolean wildcardsAllTheWay = true;
                  myParameters = this.getResolvedTypeParameters();

                  for(len$ = 0; len$ < myParameters.length; ++len$) {
                     if (!myParameters[len$].isGenericWildcard()) {
                        wildcardsAllTheWay = false;
                     } else {
                        BoundedReferenceType boundedRT = (BoundedReferenceType)myParameters[len$];
                        if (boundedRT.isExtends() || boundedRT.isSuper()) {
                           wildcardsAllTheWay = false;
                        }
                     }
                  }

                  if (wildcardsAllTheWay && !other.isParameterizedType()) {
                     return true;
                  }

                  ResolvedType[] theirParameters = other.getResolvedTypeParameters();
                  boolean parametersAssignable = true;
                  if (myParameters.length != theirParameters.length) {
                     parametersAssignable = false;
                  } else {
                     for(int i = 0; i < myParameters.length && parametersAssignable; ++i) {
                        if (myParameters[i] != theirParameters[i]) {
                           ResolvedType mp = myParameters[i];
                           ResolvedType tp = theirParameters[i];
                           if (mp.isParameterizedType() && tp.isParameterizedType()) {
                              if (!mp.getGenericType().equals(tp.getGenericType())) {
                                 parametersAssignable = false;
                                 break;
                              }

                              UnresolvedType[] mtps = mp.getTypeParameters();
                              UnresolvedType[] ttps = tp.getTypeParameters();

                              for(int ii = 0; ii < mtps.length; ++ii) {
                                 if (!mtps[ii].isTypeVariableReference() || !ttps[ii].isTypeVariableReference()) {
                                    parametersAssignable = false;
                                    break;
                                 }

                                 TypeVariable mtv = ((TypeVariableReferenceType)mtps[ii]).getTypeVariable();
                                 boolean b = mtv.canBeBoundTo((ResolvedType)ttps[ii]);
                                 if (!b) {
                                    parametersAssignable = false;
                                    break;
                                 }
                              }
                           } else if (myParameters[i].isTypeVariableReference() && theirParameters[i].isTypeVariableReference()) {
                              TypeVariable myTV = ((TypeVariableReferenceType)myParameters[i]).getTypeVariable();
                              boolean b = myTV.canBeBoundTo(theirParameters[i]);
                              if (!b) {
                                 parametersAssignable = false;
                                 break;
                              }
                           } else {
                              if (!myParameters[i].isGenericWildcard()) {
                                 parametersAssignable = false;
                                 break;
                              }

                              BoundedReferenceType wildcardType = (BoundedReferenceType)myParameters[i];
                              if (!wildcardType.alwaysMatches(theirParameters[i])) {
                                 parametersAssignable = false;
                                 break;
                              }
                           }
                        }
                     }
                  }

                  if (parametersAssignable) {
                     return true;
                  }
               }

               if (this.isTypeVariableReference() && !other.isTypeVariableReference()) {
                  TypeVariable aVar = ((TypeVariableReference)this).getTypeVariable();
                  return aVar.resolve(this.world).canBeBoundTo(other);
               } else if (other.isTypeVariableReference()) {
                  TypeVariableReferenceType otherType = (TypeVariableReferenceType)other;
                  return this instanceof TypeVariableReference ? ((TypeVariableReference)this).getTypeVariable().resolve(this.world).canBeBoundTo(otherType.getTypeVariable().getFirstBound().resolve(this.world)) : this.isAssignableFrom(otherType.getTypeVariable().getFirstBound().resolve(this.world));
               } else if (allowMissing && other.isMissing()) {
                  return false;
               } else {
                  ResolvedType[] interfaces = other.getDeclaredInterfaces();
                  myParameters = interfaces;
                  len$ = interfaces.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     ResolvedType intface = myParameters[i$];
                     boolean b;
                     if (thisRaw && intface.isParameterizedOrGenericType()) {
                        b = this.isAssignableFrom(intface.getRawType(), allowMissing);
                     } else {
                        b = this.isAssignableFrom(intface, allowMissing);
                     }

                     if (b) {
                        return true;
                     }
                  }

                  ResolvedType superclass = other.getSuperclass();
                  if (superclass != null) {
                     boolean b;
                     if (thisRaw && superclass.isParameterizedOrGenericType()) {
                        b = this.isAssignableFrom(superclass.getRawType(), allowMissing);
                     } else {
                        b = this.isAssignableFrom(superclass, allowMissing);
                     }

                     if (b) {
                        return true;
                     }
                  }

                  return false;
               }
            }
         }
      }
   }

   public ISourceContext getSourceContext() {
      return this.getDelegate().getSourceContext();
   }

   public ISourceLocation getSourceLocation() {
      ISourceContext isc = this.getDelegate().getSourceContext();
      return isc.makeSourceLocation(new Position(this.startPos, this.endPos));
   }

   public boolean isExposedToWeaver() {
      return this.getDelegate() == null || this.delegate.isExposedToWeaver();
   }

   public WeaverStateInfo getWeaverState() {
      return this.getDelegate().getWeaverState();
   }

   public ResolvedMember[] getDeclaredFields() {
      if (this.parameterizedFields != null) {
         return this.parameterizedFields;
      } else if (!this.isParameterizedType() && !this.isRawType()) {
         return this.getDelegate().getDeclaredFields();
      } else {
         ResolvedMember[] delegateFields = this.getDelegate().getDeclaredFields();
         this.parameterizedFields = new ResolvedMember[delegateFields.length];

         for(int i = 0; i < delegateFields.length; ++i) {
            this.parameterizedFields[i] = delegateFields[i].parameterizedWith(this.getTypesForMemberParameterization(), this, this.isParameterizedType());
         }

         return this.parameterizedFields;
      }
   }

   public ResolvedType[] getDeclaredInterfaces() {
      ResolvedType[] interfaces = (ResolvedType[])this.parameterizedInterfaces.get();
      if (interfaces != null) {
         return interfaces;
      } else {
         ResolvedType[] delegateInterfaces = this.getDelegate().getDeclaredInterfaces();
         ResolvedType[] newInterfacesFromGenericType;
         if (this.isRawType()) {
            if (this.newInterfaces != null) {
               throw new IllegalStateException("The raw type should never be accumulating new interfaces, they should be on the generic type.  Type is " + this.getName());
            }

            newInterfacesFromGenericType = this.genericType.newInterfaces;
            if (newInterfacesFromGenericType != null) {
               ResolvedType[] extraInterfaces = new ResolvedType[delegateInterfaces.length + newInterfacesFromGenericType.length];
               System.arraycopy(delegateInterfaces, 0, extraInterfaces, 0, delegateInterfaces.length);
               System.arraycopy(newInterfacesFromGenericType, 0, extraInterfaces, delegateInterfaces.length, newInterfacesFromGenericType.length);
               delegateInterfaces = extraInterfaces;
            }
         } else if (this.newInterfaces != null) {
            newInterfacesFromGenericType = new ResolvedType[delegateInterfaces.length + this.newInterfaces.length];
            System.arraycopy(delegateInterfaces, 0, newInterfacesFromGenericType, 0, delegateInterfaces.length);
            System.arraycopy(this.newInterfaces, 0, newInterfacesFromGenericType, delegateInterfaces.length, this.newInterfaces.length);
            delegateInterfaces = newInterfacesFromGenericType;
         }

         if (this.isParameterizedType()) {
            interfaces = new ResolvedType[delegateInterfaces.length];

            for(int i = 0; i < delegateInterfaces.length; ++i) {
               if (delegateInterfaces[i].isParameterizedType()) {
                  interfaces[i] = delegateInterfaces[i].parameterize(this.getMemberParameterizationMap()).resolve(this.world);
               } else {
                  interfaces[i] = delegateInterfaces[i];
               }
            }

            this.parameterizedInterfaces = new WeakReference(interfaces);
            return interfaces;
         } else if (this.isRawType()) {
            UnresolvedType[] paramTypes = this.getTypesForMemberParameterization();
            interfaces = new ResolvedType[delegateInterfaces.length];
            int i = 0;

            for(int max = interfaces.length; i < max; ++i) {
               interfaces[i] = delegateInterfaces[i];
               if (interfaces[i].isGenericType()) {
                  interfaces[i] = interfaces[i].getRawType().resolve(this.getWorld());
               } else if (interfaces[i].isParameterizedType()) {
                  UnresolvedType[] toUseForParameterization = this.determineThoseTypesToUse(interfaces[i], paramTypes);
                  interfaces[i] = interfaces[i].parameterizedWith(toUseForParameterization);
               }
            }

            this.parameterizedInterfaces = new WeakReference(interfaces);
            return interfaces;
         } else {
            if (this.getDelegate().isCacheable()) {
               this.parameterizedInterfaces = new WeakReference(delegateInterfaces);
            }

            return delegateInterfaces;
         }
      }
   }

   private UnresolvedType[] determineThoseTypesToUse(ResolvedType parameterizedInterface, UnresolvedType[] paramTypes) {
      UnresolvedType[] tParms = parameterizedInterface.getTypeParameters();
      UnresolvedType[] retVal = new UnresolvedType[tParms.length];

      for(int i = 0; i < tParms.length; ++i) {
         UnresolvedType tParm = tParms[i];
         if (tParm.isTypeVariableReference()) {
            TypeVariableReference tvrt = (TypeVariableReference)tParm;
            TypeVariable tv = tvrt.getTypeVariable();
            int rank = this.getRank(tv.getName());
            if (rank != -1) {
               retVal[i] = paramTypes[rank];
            } else {
               retVal[i] = tParms[i];
            }
         } else {
            retVal[i] = tParms[i];
         }
      }

      return retVal;
   }

   private int getRank(String tvname) {
      TypeVariable[] thisTypesTVars = this.getGenericType().getTypeVariables();

      for(int i = 0; i < thisTypesTVars.length; ++i) {
         TypeVariable tv = thisTypesTVars[i];
         if (tv.getName().equals(tvname)) {
            return i;
         }
      }

      return -1;
   }

   public ResolvedMember[] getDeclaredMethods() {
      if (this.parameterizedMethods != null) {
         return this.parameterizedMethods;
      } else if (!this.isParameterizedType() && !this.isRawType()) {
         return this.getDelegate().getDeclaredMethods();
      } else {
         ResolvedMember[] delegateMethods = this.getDelegate().getDeclaredMethods();
         UnresolvedType[] parameters = this.getTypesForMemberParameterization();
         this.parameterizedMethods = new ResolvedMember[delegateMethods.length];

         for(int i = 0; i < delegateMethods.length; ++i) {
            this.parameterizedMethods[i] = delegateMethods[i].parameterizedWith(parameters, this, this.isParameterizedType());
         }

         return this.parameterizedMethods;
      }
   }

   public ResolvedMember[] getDeclaredPointcuts() {
      if (this.parameterizedPointcuts != null) {
         return this.parameterizedPointcuts;
      } else if (!this.isParameterizedType()) {
         return this.getDelegate().getDeclaredPointcuts();
      } else {
         ResolvedMember[] delegatePointcuts = this.getDelegate().getDeclaredPointcuts();
         this.parameterizedPointcuts = new ResolvedMember[delegatePointcuts.length];

         for(int i = 0; i < delegatePointcuts.length; ++i) {
            this.parameterizedPointcuts[i] = delegatePointcuts[i].parameterizedWith(this.getTypesForMemberParameterization(), this, this.isParameterizedType());
         }

         return this.parameterizedPointcuts;
      }
   }

   private UnresolvedType[] getTypesForMemberParameterization() {
      UnresolvedType[] parameters = null;
      if (this.isParameterizedType()) {
         parameters = this.getTypeParameters();
      } else if (this.isRawType()) {
         TypeVariable[] tvs = this.getGenericType().getTypeVariables();
         parameters = new UnresolvedType[tvs.length];

         for(int i = 0; i < tvs.length; ++i) {
            parameters[i] = tvs[i].getFirstBound();
         }
      }

      return parameters;
   }

   public TypeVariable[] getTypeVariables() {
      if (this.typeVariables == null) {
         this.typeVariables = this.getDelegate().getTypeVariables();

         for(int i = 0; i < this.typeVariables.length; ++i) {
            this.typeVariables[i].resolve(this.world);
         }
      }

      return this.typeVariables;
   }

   public PerClause getPerClause() {
      PerClause pclause = this.getDelegate().getPerClause();
      if (pclause != null && this.isParameterizedType()) {
         Map parameterizationMap = this.getAjMemberParameterizationMap();
         pclause = (PerClause)pclause.parameterizeWith(parameterizationMap, this.world);
      }

      return pclause;
   }

   public Collection getDeclares() {
      if (this.parameterizedDeclares != null) {
         return this.parameterizedDeclares;
      } else {
         Collection declares = null;
         if (this.ajMembersNeedParameterization()) {
            Collection genericDeclares = this.getDelegate().getDeclares();
            this.parameterizedDeclares = new ArrayList();
            Map parameterizationMap = this.getAjMemberParameterizationMap();
            Iterator i$ = genericDeclares.iterator();

            while(i$.hasNext()) {
               Declare declareStatement = (Declare)i$.next();
               this.parameterizedDeclares.add(declareStatement.parameterizeWith(parameterizationMap, this.world));
            }

            declares = this.parameterizedDeclares;
         } else {
            declares = this.getDelegate().getDeclares();
         }

         Iterator i$ = declares.iterator();

         while(i$.hasNext()) {
            Declare d = (Declare)i$.next();
            d.setDeclaringType(this);
         }

         return declares;
      }
   }

   public Collection getTypeMungers() {
      return this.getDelegate().getTypeMungers();
   }

   public Collection getPrivilegedAccesses() {
      return this.getDelegate().getPrivilegedAccesses();
   }

   public int getModifiers() {
      return this.getDelegate().getModifiers();
   }

   public ResolvedType getSuperclass() {
      ResolvedType ret = null;
      if (this.newSuperclass != null) {
         if (this.isParameterizedType() && this.newSuperclass.isParameterizedType()) {
            return this.newSuperclass.parameterize(this.getMemberParameterizationMap()).resolve(this.getWorld());
         } else {
            if (this.getDelegate().isCacheable()) {
               this.superclassReference = new WeakReference(ret);
            }

            return this.newSuperclass;
         }
      } else {
         try {
            this.world.setTypeVariableLookupScope(this);
            ret = this.getDelegate().getSuperclass();
         } finally {
            this.world.setTypeVariableLookupScope((TypeVariableDeclaringElement)null);
         }

         if (this.isParameterizedType() && ret.isParameterizedType()) {
            ret = ret.parameterize(this.getMemberParameterizationMap()).resolve(this.getWorld());
         }

         if (this.getDelegate().isCacheable()) {
            this.superclassReference = new WeakReference(ret);
         }

         return ret;
      }
   }

   public ReferenceTypeDelegate getDelegate() {
      return this.delegate;
   }

   public void setDelegate(ReferenceTypeDelegate delegate) {
      if (this.delegate != null && this.delegate.copySourceContext() && this.delegate.getSourceContext() != SourceContextImpl.UNKNOWN_SOURCE_CONTEXT) {
         ((AbstractReferenceTypeDelegate)delegate).setSourceContext(this.delegate.getSourceContext());
      }

      this.delegate = delegate;
      synchronized(this.derivativeTypes) {
         List forRemoval = new ArrayList();
         Iterator i$ = this.derivativeTypes.iterator();

         while(i$.hasNext()) {
            WeakReference derivativeRef = (WeakReference)i$.next();
            ReferenceType derivative = (ReferenceType)derivativeRef.get();
            if (derivative != null) {
               derivative.setDelegate(delegate);
            } else {
               forRemoval.add(derivativeRef);
            }
         }

         this.derivativeTypes.removeAll(forRemoval);
      }

      if (this.isRawType() && this.getGenericType() != null) {
         ReferenceType genType = this.getGenericType();
         if (genType.getDelegate() != delegate) {
            genType.setDelegate(delegate);
         }
      }

      this.clearParameterizationCaches();
      this.ensureConsistent();
   }

   private void clearParameterizationCaches() {
      this.parameterizedFields = null;
      this.parameterizedInterfaces.clear();
      this.parameterizedMethods = null;
      this.parameterizedPointcuts = null;
      this.superclassReference = new WeakReference((Object)null);
   }

   public int getEndPos() {
      return this.endPos;
   }

   public int getStartPos() {
      return this.startPos;
   }

   public void setEndPos(int endPos) {
      this.endPos = endPos;
   }

   public void setStartPos(int startPos) {
      this.startPos = startPos;
   }

   public boolean doesNotExposeShadowMungers() {
      return this.getDelegate().doesNotExposeShadowMungers();
   }

   public String getDeclaredGenericSignature() {
      return this.getDelegate().getDeclaredGenericSignature();
   }

   public void setGenericType(ReferenceType rt) {
      this.genericType = rt;
      if (this.typeKind == UnresolvedType.TypeKind.SIMPLE) {
         this.typeKind = UnresolvedType.TypeKind.RAW;
         this.signatureErasure = this.signature;
         if (this.newInterfaces != null) {
            throw new IllegalStateException("Simple type promoted to raw, but simple type had new interfaces/superclass.  Type is " + this.getName());
         }
      }

      if (this.typeKind == UnresolvedType.TypeKind.RAW) {
         this.genericType.addDependentType(this);
      }

      if (this.isRawType()) {
         this.genericType.rawType = this;
      }

      if (this.isRawType() && rt.isRawType()) {
         (new RuntimeException("PR341926 diagnostics: Incorrect setup for a generic type, raw type should not point to raw: " + this.getName())).printStackTrace();
      }

   }

   public void demoteToSimpleType() {
      this.genericType = null;
      this.typeKind = UnresolvedType.TypeKind.SIMPLE;
      this.signatureErasure = null;
   }

   public ReferenceType getGenericType() {
      return this.isGenericType() ? this : this.genericType;
   }

   private static String makeParameterizedSignature(ResolvedType aGenericType, ResolvedType[] someParameters) {
      String rawSignature = aGenericType.getErasureSignature();
      StringBuffer ret = new StringBuffer();
      ret.append("P");
      ret.append(rawSignature.substring(1, rawSignature.length() - 1));
      ret.append("<");

      for(int i = 0; i < someParameters.length; ++i) {
         ret.append(someParameters[i].getSignature());
      }

      ret.append(">;");
      return ret.toString();
   }

   private static String makeDeclaredSignature(ResolvedType aGenericType, UnresolvedType[] someParameters) {
      StringBuffer ret = new StringBuffer();
      String rawSig = aGenericType.getErasureSignature();
      ret.append(rawSig.substring(0, rawSig.length() - 1));
      ret.append("<");

      for(int i = 0; i < someParameters.length; ++i) {
         if (someParameters[i] instanceof ReferenceType) {
            ret.append(((ReferenceType)someParameters[i]).getSignatureForAttribute());
         } else {
            if (!(someParameters[i] instanceof ResolvedType.Primitive)) {
               throw new IllegalStateException("DebugFor325731: expected a ReferenceType or Primitive but was " + someParameters[i] + " of type " + someParameters[i].getClass().getName());
            }

            ret.append(((ResolvedType.Primitive)someParameters[i]).getSignatureForAttribute());
         }
      }

      ret.append(">;");
      return ret.toString();
   }

   public void ensureConsistent() {
      this.annotations = null;
      this.annotationTypes = null;
      this.newSuperclass = null;
      this.bits = 0;
      this.newInterfaces = null;
      this.typeVariables = null;
      this.parameterizedInterfaces.clear();
      this.superclassReference = new WeakReference((Object)null);
      if (this.getDelegate() != null) {
         this.delegate.ensureConsistent();
      }

      if (this.isParameterizedOrRawType()) {
         ReferenceType genericType = this.getGenericType();
         if (genericType != null) {
            genericType.ensureConsistent();
         }
      }

   }

   public void addParent(ResolvedType newParent) {
      if (this.isRawType()) {
         throw new IllegalStateException("The raw type should never be accumulating new interfaces, they should be on the generic type.  Type is " + this.getName());
      } else {
         if (newParent.isClass()) {
            this.newSuperclass = newParent;
            this.superclassReference = new WeakReference((Object)null);
         } else {
            if (this.newInterfaces == null) {
               this.newInterfaces = new ResolvedType[1];
               this.newInterfaces[0] = newParent;
            } else {
               ResolvedType[] existing = this.getDelegate().getDeclaredInterfaces();
               if (existing != null) {
                  for(int i = 0; i < existing.length; ++i) {
                     if (existing[i].equals(newParent)) {
                        return;
                     }
                  }
               }

               ResolvedType[] newNewInterfaces = new ResolvedType[this.newInterfaces.length + 1];
               System.arraycopy(this.newInterfaces, 0, newNewInterfaces, 1, this.newInterfaces.length);
               newNewInterfaces[0] = newParent;
               this.newInterfaces = newNewInterfaces;
            }

            if (this.isGenericType()) {
               synchronized(this.derivativeTypes) {
                  Iterator i$ = this.derivativeTypes.iterator();

                  while(i$.hasNext()) {
                     WeakReference derivativeTypeRef = (WeakReference)i$.next();
                     ReferenceType derivativeType = (ReferenceType)derivativeTypeRef.get();
                     if (derivativeType != null) {
                        derivativeType.parameterizedInterfaces.clear();
                     }
                  }
               }
            }

            this.parameterizedInterfaces.clear();
         }

      }
   }

   private boolean equal(UnresolvedType[] typeParameters, ResolvedType[] resolvedParameters) {
      if (typeParameters.length != resolvedParameters.length) {
         return false;
      } else {
         int len = typeParameters.length;

         for(int p = 0; p < len; ++p) {
            if (!typeParameters[p].equals(resolvedParameters[p])) {
               return false;
            }
         }

         return true;
      }
   }

   public ReferenceType findDerivativeType(ResolvedType[] typeParameters) {
      synchronized(this.derivativeTypes) {
         List forRemoval = new ArrayList();
         Iterator i$ = this.derivativeTypes.iterator();

         while(i$.hasNext()) {
            WeakReference derivativeTypeRef = (WeakReference)i$.next();
            ReferenceType derivativeType = (ReferenceType)derivativeTypeRef.get();
            if (derivativeType == null) {
               forRemoval.add(derivativeTypeRef);
            } else if (!derivativeType.isRawType() && this.equal(derivativeType.typeParameters, typeParameters)) {
               return derivativeType;
            }
         }

         this.derivativeTypes.removeAll(forRemoval);
         return null;
      }
   }

   public boolean hasNewInterfaces() {
      return this.newInterfaces != null;
   }
}
