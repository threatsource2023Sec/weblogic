package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResolvedMemberImpl extends MemberImpl implements IHasPosition, ResolvedMember {
   private String[] parameterNames;
   private boolean isResolved;
   protected UnresolvedType[] checkedExceptions;
   protected ResolvedMember backingGenericMember;
   protected AnnotationAJ[] annotations;
   protected ResolvedType[] annotationTypes;
   protected AnnotationAJ[][] parameterAnnotations;
   protected ResolvedType[][] parameterAnnotationTypes;
   private boolean isAnnotatedElsewhere;
   private boolean isAjSynthetic;
   protected TypeVariable[] typeVariables;
   protected int start;
   protected int end;
   protected ISourceContext sourceContext;
   private String myParameterSignatureWithBoundsRemoved;
   private String myParameterSignatureErasure;
   public static boolean showParameterNames = true;

   public ResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes) {
      super(kind, declaringType, modifiers, returnType, name, parameterTypes);
      this.parameterNames = null;
      this.isResolved = false;
      this.checkedExceptions = UnresolvedType.NONE;
      this.backingGenericMember = null;
      this.annotations = null;
      this.annotationTypes = null;
      this.parameterAnnotations = (AnnotationAJ[][])null;
      this.parameterAnnotationTypes = (ResolvedType[][])null;
      this.isAnnotatedElsewhere = false;
      this.isAjSynthetic = false;
      this.sourceContext = null;
      this.myParameterSignatureWithBoundsRemoved = null;
      this.myParameterSignatureErasure = null;
   }

   public ResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes, UnresolvedType[] checkedExceptions) {
      super(kind, declaringType, modifiers, returnType, name, parameterTypes);
      this.parameterNames = null;
      this.isResolved = false;
      this.checkedExceptions = UnresolvedType.NONE;
      this.backingGenericMember = null;
      this.annotations = null;
      this.annotationTypes = null;
      this.parameterAnnotations = (AnnotationAJ[][])null;
      this.parameterAnnotationTypes = (ResolvedType[][])null;
      this.isAnnotatedElsewhere = false;
      this.isAjSynthetic = false;
      this.sourceContext = null;
      this.myParameterSignatureWithBoundsRemoved = null;
      this.myParameterSignatureErasure = null;
      this.checkedExceptions = checkedExceptions;
   }

   public ResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes, UnresolvedType[] checkedExceptions, ResolvedMember backingGenericMember) {
      this(kind, declaringType, modifiers, returnType, name, parameterTypes, checkedExceptions);
      this.backingGenericMember = backingGenericMember;
      this.isAjSynthetic = backingGenericMember.isAjSynthetic();
   }

   public ResolvedMemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, String name, String signature) {
      super(kind, declaringType, modifiers, name, signature);
      this.parameterNames = null;
      this.isResolved = false;
      this.checkedExceptions = UnresolvedType.NONE;
      this.backingGenericMember = null;
      this.annotations = null;
      this.annotationTypes = null;
      this.parameterAnnotations = (AnnotationAJ[][])null;
      this.parameterAnnotationTypes = (ResolvedType[][])null;
      this.isAnnotatedElsewhere = false;
      this.isAjSynthetic = false;
      this.sourceContext = null;
      this.myParameterSignatureWithBoundsRemoved = null;
      this.myParameterSignatureErasure = null;
   }

   public static JoinPointSignature[] getJoinPointSignatures(Member joinPointSignature, World inAWorld) {
      ResolvedType originalDeclaringType = joinPointSignature.getDeclaringType().resolve(inAWorld);
      ResolvedMemberImpl firstDefiningMember = (ResolvedMemberImpl)joinPointSignature.resolve(inAWorld);
      if (firstDefiningMember == null) {
         return JoinPointSignature.EMPTY_ARRAY;
      } else {
         ResolvedType firstDefiningType = firstDefiningMember.getDeclaringType().resolve(inAWorld);
         if (firstDefiningType != originalDeclaringType && joinPointSignature.getKind() == Member.CONSTRUCTOR) {
            return JoinPointSignature.EMPTY_ARRAY;
         } else {
            List declaringTypes = new ArrayList();
            accumulateTypesInBetween(originalDeclaringType, firstDefiningType, declaringTypes);
            Set memberSignatures = new HashSet();
            Iterator superTypeIterator = declaringTypes.iterator();

            while(superTypeIterator.hasNext()) {
               ResolvedType declaringType = (ResolvedType)superTypeIterator.next();
               memberSignatures.add(new JoinPointSignature(firstDefiningMember, declaringType));
            }

            if (shouldWalkUpHierarchyFor(firstDefiningMember)) {
               superTypeIterator = firstDefiningType.getDirectSupertypes();
               List typesAlreadyVisited = new ArrayList();
               accumulateMembersMatching(firstDefiningMember, superTypeIterator, typesAlreadyVisited, memberSignatures, false);
            }

            JoinPointSignature[] ret = new JoinPointSignature[memberSignatures.size()];
            memberSignatures.toArray(ret);
            return ret;
         }
      }
   }

   private static boolean shouldWalkUpHierarchyFor(Member aMember) {
      if (aMember.getKind() == Member.CONSTRUCTOR) {
         return false;
      } else if (aMember.getKind() == Member.FIELD) {
         return false;
      } else {
         return !Modifier.isStatic(aMember.getModifiers());
      }
   }

   private static void accumulateTypesInBetween(ResolvedType subType, ResolvedType superType, List types) {
      types.add(subType);
      if (subType != superType) {
         Iterator iter = subType.getDirectSupertypes();

         while(iter.hasNext()) {
            ResolvedType parent = (ResolvedType)iter.next();
            if (superType.isAssignableFrom(parent)) {
               accumulateTypesInBetween(parent, superType, types);
            }
         }

      }
   }

   private static void accumulateMembersMatching(ResolvedMemberImpl memberToMatch, Iterator typesToLookIn, List typesAlreadyVisited, Set foundMembers, boolean ignoreGenerics) {
      while(typesToLookIn.hasNext()) {
         ResolvedType toLookIn = (ResolvedType)typesToLookIn.next();
         if (!typesAlreadyVisited.contains(toLookIn)) {
            typesAlreadyVisited.add(toLookIn);
            ResolvedMemberImpl foundMember = (ResolvedMemberImpl)toLookIn.lookupResolvedMember(memberToMatch, true, ignoreGenerics);
            if (foundMember != null && isVisibleTo(memberToMatch, foundMember)) {
               List declaringTypes = new ArrayList();
               ResolvedType resolvedDeclaringType = foundMember.getDeclaringType().resolve(toLookIn.getWorld());
               accumulateTypesInBetween(toLookIn, resolvedDeclaringType, declaringTypes);
               Iterator i$ = declaringTypes.iterator();

               while(i$.hasNext()) {
                  ResolvedType declaringType = (ResolvedType)i$.next();
                  foundMembers.add(new JoinPointSignature(foundMember, declaringType));
               }

               if (!ignoreGenerics && toLookIn.isParameterizedType() && foundMember.backingGenericMember != null) {
                  foundMembers.add(new JoinPointSignature(foundMember.backingGenericMember, foundMember.declaringType.resolve(toLookIn.getWorld())));
               }

               accumulateMembersMatching(foundMember, toLookIn.getDirectSupertypes(), typesAlreadyVisited, foundMembers, ignoreGenerics);
            }
         }
      }

   }

   private static boolean isVisibleTo(ResolvedMember childMember, ResolvedMember parentMember) {
      if (childMember.getDeclaringType().equals(parentMember.getDeclaringType())) {
         return true;
      } else {
         return !Modifier.isPrivate(parentMember.getModifiers());
      }
   }

   public final int getModifiers(World world) {
      return this.modifiers;
   }

   public final int getModifiers() {
      return this.modifiers;
   }

   public final UnresolvedType[] getExceptions(World world) {
      return this.getExceptions();
   }

   public UnresolvedType[] getExceptions() {
      return this.checkedExceptions;
   }

   public ShadowMunger getAssociatedShadowMunger() {
      return null;
   }

   public boolean isAjSynthetic() {
      return this.isAjSynthetic;
   }

   protected void setAjSynthetic(boolean b) {
      this.isAjSynthetic = b;
   }

   public boolean hasAnnotations() {
      return this.annotationTypes != null;
   }

   public boolean hasAnnotation(UnresolvedType ofType) {
      if (this.backingGenericMember != null) {
         if (this.annotationTypes != null) {
            throw new BCException("Unexpectedly found a backing generic member and a local set of annotations");
         } else {
            return this.backingGenericMember.hasAnnotation(ofType);
         }
      } else {
         if (this.annotationTypes != null) {
            int i = 0;

            for(int max = this.annotationTypes.length; i < max; ++i) {
               if (this.annotationTypes[i].equals(ofType)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public ResolvedType[] getAnnotationTypes() {
      if (this.backingGenericMember != null) {
         if (this.annotationTypes != null) {
            throw new BCException("Unexpectedly found a backing generic member and a local set of annotations");
         } else {
            return this.backingGenericMember.getAnnotationTypes();
         }
      } else {
         return this.annotationTypes;
      }
   }

   public String getAnnotationDefaultValue() {
      throw new UnsupportedOperationException("You should resolve this member and call getAnnotationDefaultValue() on the result...");
   }

   public AnnotationAJ[] getAnnotations() {
      if (this.backingGenericMember != null) {
         return this.backingGenericMember.getAnnotations();
      } else {
         return this.annotations != null ? this.annotations : super.getAnnotations();
      }
   }

   public AnnotationAJ getAnnotationOfType(UnresolvedType ofType) {
      if (this.annotations != null) {
         AnnotationAJ[] arr$ = this.annotations;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            AnnotationAJ annotation = arr$[i$];
            if (annotation.getType().equals(ofType)) {
               return annotation;
            }
         }

         return null;
      } else {
         throw new UnsupportedOperationException("You should resolve this member and call getAnnotationOfType() on the result...");
      }
   }

   public void setAnnotations(AnnotationAJ[] annotations) {
      this.annotations = annotations;
   }

   public void setAnnotationTypes(ResolvedType[] annotationTypes) {
      this.annotationTypes = annotationTypes;
   }

   public ResolvedType[][] getParameterAnnotationTypes() {
      return this.parameterAnnotationTypes;
   }

   public AnnotationAJ[][] getParameterAnnotations() {
      if (this.backingGenericMember != null) {
         return this.backingGenericMember.getParameterAnnotations();
      } else {
         throw new BCException("Cannot return parameter annotations for a " + this.getClass().getName() + " member");
      }
   }

   public void addAnnotation(AnnotationAJ annotation) {
      if (this.annotationTypes == null) {
         this.annotationTypes = new ResolvedType[1];
         this.annotationTypes[0] = annotation.getType();
         this.annotations = new AnnotationAJ[1];
         this.annotations[0] = annotation;
      } else {
         int len = this.annotations.length;
         AnnotationAJ[] ret = new AnnotationAJ[len + 1];
         System.arraycopy(this.annotations, 0, ret, 0, len);
         ret[len] = annotation;
         this.annotations = ret;
         ResolvedType[] newAnnotationTypes = new ResolvedType[len + 1];
         System.arraycopy(this.annotationTypes, 0, newAnnotationTypes, 0, len);
         newAnnotationTypes[len] = annotation.getType();
         this.annotationTypes = newAnnotationTypes;
      }

   }

   public boolean isBridgeMethod() {
      return (this.modifiers & 64) != 0 && this.getKind().equals(METHOD);
   }

   public boolean isVarargsMethod() {
      return (this.modifiers & 128) != 0;
   }

   public void setVarargsMethod() {
      this.modifiers |= 128;
   }

   public boolean isSynthetic() {
      return (this.modifiers & 4096) != 0;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.getKind().write(s);
      s.writeBoolean(s.canCompress());
      if (s.canCompress()) {
         s.writeCompressedSignature(this.getDeclaringType().getSignature());
      } else {
         this.getDeclaringType().write(s);
      }

      s.writeInt(this.modifiers);
      if (s.canCompress()) {
         s.writeCompressedName(this.getName());
         s.writeCompressedSignature(this.getSignature());
      } else {
         s.writeUTF(this.getName());
         s.writeUTF(this.getSignature());
      }

      UnresolvedType.writeArray(this.getExceptions(), s);
      s.writeInt(this.getStart());
      s.writeInt(this.getEnd());
      s.writeBoolean(this.isVarargsMethod());
      if (this.typeVariables == null) {
         s.writeByte(0);
      } else {
         s.writeByte(this.typeVariables.length);

         for(int i = 0; i < this.typeVariables.length; ++i) {
            this.typeVariables[i].write(s);
         }
      }

      String gsig = this.getGenericSignature();
      if (this.getSignature().equals(gsig)) {
         s.writeByte(255);
      } else {
         s.writeByte(this.parameterTypes.length);

         for(int i = 0; i < this.parameterTypes.length; ++i) {
            if (s.canCompress()) {
               s.writeCompressedSignature(this.parameterTypes[i].getSignature());
            } else {
               UnresolvedType array_element = this.parameterTypes[i];
               array_element.write(s);
            }
         }

         if (s.canCompress()) {
            s.writeCompressedSignature(this.returnType.getSignature());
         } else {
            this.returnType.write(s);
         }
      }

   }

   public String getSignatureForAttribute() {
      StringBuffer sb = new StringBuffer();
      int i;
      if (this.typeVariables != null) {
         sb.append("<");

         for(i = 0; i < this.typeVariables.length; ++i) {
            sb.append(this.typeVariables[i].getSignatureForAttribute());
         }

         sb.append(">");
      }

      sb.append("(");

      for(i = 0; i < this.parameterTypes.length; ++i) {
         ResolvedType ptype = (ResolvedType)this.parameterTypes[i];
         sb.append(ptype.getSignatureForAttribute());
      }

      sb.append(")");
      sb.append(((ResolvedType)this.returnType).getSignatureForAttribute());
      return sb.toString();
   }

   public String getGenericSignature() {
      StringBuffer sb = new StringBuffer();
      int i;
      if (this.typeVariables != null) {
         sb.append("<");

         for(i = 0; i < this.typeVariables.length; ++i) {
            sb.append(this.typeVariables[i].getSignature());
         }

         sb.append(">");
      }

      sb.append("(");

      for(i = 0; i < this.parameterTypes.length; ++i) {
         UnresolvedType ptype = this.parameterTypes[i];
         sb.append(ptype.getSignature());
      }

      sb.append(")");
      sb.append(this.returnType.getSignature());
      return sb.toString();
   }

   public static void writeArray(ResolvedMember[] members, CompressingDataOutputStream s) throws IOException {
      s.writeInt(members.length);
      int i = 0;

      for(int len = members.length; i < len; ++i) {
         members[i].write(s);
      }

   }

   public static ResolvedMemberImpl readResolvedMember(VersionedDataInputStream s, ISourceContext sourceContext) throws IOException {
      MemberKind mk = MemberKind.read(s);
      boolean compressed = s.isAtLeast169() ? s.readBoolean() : false;
      UnresolvedType declaringType = compressed ? UnresolvedType.forSignature(s.readUtf8(s.readShort())) : UnresolvedType.read(s);
      int modifiers = s.readInt();
      String name = compressed ? s.readUtf8(s.readShort()) : s.readUTF();
      String signature = compressed ? s.readUtf8(s.readShort()) : s.readUTF();
      ResolvedMemberImpl m = new ResolvedMemberImpl(mk, declaringType, modifiers, name, signature);
      m.checkedExceptions = UnresolvedType.readArray(s);
      m.start = s.readInt();
      m.end = s.readInt();
      m.sourceContext = sourceContext;
      if (s.getMajorVersion() >= 2) {
         if (s.getMajorVersion() >= 3) {
            boolean isvarargs = s.readBoolean();
            if (isvarargs) {
               m.setVarargsMethod();
            }
         }

         int tvcount = s.isAtLeast169() ? s.readByte() : s.readInt();
         if (tvcount != 0) {
            m.typeVariables = new TypeVariable[tvcount];

            for(int i = 0; i < tvcount; ++i) {
               m.typeVariables[i] = TypeVariable.read(s);
               m.typeVariables[i].setDeclaringElement(m);
               m.typeVariables[i].setRank(i);
            }
         }

         if (s.getMajorVersion() >= 3) {
            int pcount = -1;
            boolean hasAGenericSignature = false;
            if (!s.isAtLeast169()) {
               hasAGenericSignature = s.readBoolean();
            } else {
               pcount = s.readByte();
               hasAGenericSignature = pcount >= 0 && pcount < 255;
            }

            if (hasAGenericSignature) {
               int ps = s.isAtLeast169() ? pcount : s.readInt();
               UnresolvedType[] params = new UnresolvedType[ps];

               for(int i = 0; i < params.length; ++i) {
                  if (compressed) {
                     params[i] = TypeFactory.createTypeFromSignature(s.readSignature());
                  } else {
                     params[i] = TypeFactory.createTypeFromSignature(s.readUTF());
                  }
               }

               UnresolvedType rt = compressed ? TypeFactory.createTypeFromSignature(s.readSignature()) : TypeFactory.createTypeFromSignature(s.readUTF());
               m.parameterTypes = params;
               m.returnType = rt;
            }
         }
      }

      return m;
   }

   public static ResolvedMember[] readResolvedMemberArray(VersionedDataInputStream s, ISourceContext context) throws IOException {
      int len = s.readInt();
      ResolvedMember[] members = new ResolvedMember[len];

      for(int i = 0; i < len; ++i) {
         members[i] = readResolvedMember(s, context);
      }

      return members;
   }

   public ResolvedMember resolve(World world) {
      if (this.isResolved) {
         return this;
      } else {
         try {
            int i;
            if (this.typeVariables != null && this.typeVariables.length > 0) {
               for(i = 0; i < this.typeVariables.length; ++i) {
                  this.typeVariables[i] = this.typeVariables[i].resolve(world);
               }
            }

            world.setTypeVariableLookupScope(this);
            this.declaringType = this.declaringType.resolve(world);
            if (this.declaringType.isRawType()) {
               this.declaringType = ((ReferenceType)this.declaringType).getGenericType();
            }

            if (this.parameterTypes != null && this.parameterTypes.length > 0) {
               for(i = 0; i < this.parameterTypes.length; ++i) {
                  this.parameterTypes[i] = this.parameterTypes[i].resolve(world);
               }
            }

            this.returnType = this.returnType.resolve(world);
         } finally {
            world.setTypeVariableLookupScope((TypeVariableDeclaringElement)null);
         }

         this.isResolved = true;
         return this;
      }
   }

   public ISourceContext getSourceContext(World world) {
      return this.getDeclaringType().resolve(world).getSourceContext();
   }

   public String[] getParameterNames() {
      return this.parameterNames;
   }

   public final void setParameterNames(String[] pnames) {
      this.parameterNames = pnames;
   }

   public final String[] getParameterNames(World world) {
      return this.getParameterNames();
   }

   public AjAttribute.EffectiveSignatureAttribute getEffectiveSignature() {
      return null;
   }

   public ISourceLocation getSourceLocation() {
      return this.getSourceContext() == null ? null : this.getSourceContext().makeSourceLocation(this);
   }

   public int getEnd() {
      return this.end;
   }

   public ISourceContext getSourceContext() {
      return this.sourceContext;
   }

   public int getStart() {
      return this.start;
   }

   public void setPosition(int sourceStart, int sourceEnd) {
      this.start = sourceStart;
      this.end = sourceEnd;
   }

   public void setDeclaringType(ReferenceType rt) {
      this.declaringType = rt;
   }

   public void setSourceContext(ISourceContext sourceContext) {
      this.sourceContext = sourceContext;
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.modifiers);
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.modifiers);
   }

   public boolean isDefault() {
      int mods = this.getModifiers();
      return !Modifier.isPublic(mods) && !Modifier.isProtected(mods) && !Modifier.isPrivate(mods);
   }

   public boolean isVisible(ResolvedType fromType) {
      UnresolvedType declaringType = this.getDeclaringType();
      ResolvedType type = null;
      if (fromType.equals(declaringType)) {
         type = fromType;
      } else {
         World world = fromType.getWorld();
         type = declaringType.resolve(world);
      }

      return ResolvedType.isVisible(this.getModifiers(), type, fromType);
   }

   public void setCheckedExceptions(UnresolvedType[] checkedExceptions) {
      this.checkedExceptions = checkedExceptions;
   }

   public void setAnnotatedElsewhere(boolean b) {
      this.isAnnotatedElsewhere = b;
   }

   public boolean isAnnotatedElsewhere() {
      return this.isAnnotatedElsewhere;
   }

   public UnresolvedType getGenericReturnType() {
      return this.getReturnType();
   }

   public UnresolvedType[] getGenericParameterTypes() {
      return this.getParameterTypes();
   }

   public ResolvedMemberImpl parameterizedWith(UnresolvedType[] typeParameters, ResolvedType newDeclaringType, boolean isParameterized) {
      return this.parameterizedWith(typeParameters, newDeclaringType, isParameterized, (List)null);
   }

   public ResolvedMemberImpl parameterizedWith(UnresolvedType[] typeParameters, ResolvedType newDeclaringType, boolean isParameterized, List aliases) {
      if (!this.getDeclaringType().isGenericType() && this.getDeclaringType().getName().indexOf("$") == -1) {
         throw new IllegalStateException("Can't ask to parameterize a member of non-generic type: " + this.getDeclaringType() + "  kind(" + this.getDeclaringType().typeKind + ")");
      } else {
         TypeVariable[] typeVariables = this.getDeclaringType().getTypeVariables();
         if (isParameterized && typeVariables.length != typeParameters.length) {
            throw new IllegalStateException("Wrong number of type parameters supplied");
         } else {
            Map typeMap = new HashMap();
            boolean typeParametersSupplied = typeParameters != null && typeParameters.length > 0;
            int posn;
            if (typeVariables != null) {
               for(posn = 0; posn < typeVariables.length; ++posn) {
                  UnresolvedType ut = !typeParametersSupplied ? typeVariables[posn].getFirstBound() : typeParameters[posn];
                  typeMap.put(typeVariables[posn].getName(), ut);
               }
            }

            if (aliases != null) {
               posn = 0;

               for(Iterator i$ = aliases.iterator(); i$.hasNext(); ++posn) {
                  String typeVariableAlias = (String)i$.next();
                  typeMap.put(typeVariableAlias, !typeParametersSupplied ? typeVariables[posn].getFirstBound() : typeParameters[posn]);
               }
            }

            UnresolvedType parameterizedReturnType = this.parameterize(this.getGenericReturnType(), typeMap, isParameterized, newDeclaringType.getWorld());
            UnresolvedType[] parameterizedParameterTypes = new UnresolvedType[this.getGenericParameterTypes().length];
            UnresolvedType[] genericParameterTypes = this.getGenericParameterTypes();

            for(int i = 0; i < parameterizedParameterTypes.length; ++i) {
               parameterizedParameterTypes[i] = this.parameterize(genericParameterTypes[i], typeMap, isParameterized, newDeclaringType.getWorld());
            }

            ResolvedMemberImpl ret = new ResolvedMemberImpl(this.getKind(), newDeclaringType, this.getModifiers(), parameterizedReturnType, this.getName(), parameterizedParameterTypes, this.getExceptions(), this);
            ret.setTypeVariables(this.getTypeVariables());
            ret.setSourceContext(this.getSourceContext());
            ret.setPosition(this.getStart(), this.getEnd());
            ret.setParameterNames(this.getParameterNames());
            return ret;
         }
      }
   }

   public ResolvedMember parameterizedWith(Map m, World w) {
      this.declaringType = this.declaringType.resolve(w);
      if (this.declaringType.isRawType()) {
         this.declaringType = ((ResolvedType)this.declaringType).getGenericType();
      }

      UnresolvedType parameterizedReturnType = this.parameterize(this.getGenericReturnType(), m, true, w);
      UnresolvedType[] parameterizedParameterTypes = new UnresolvedType[this.getGenericParameterTypes().length];
      UnresolvedType[] genericParameterTypes = this.getGenericParameterTypes();

      for(int i = 0; i < parameterizedParameterTypes.length; ++i) {
         parameterizedParameterTypes[i] = this.parameterize(genericParameterTypes[i], m, true, w);
      }

      ResolvedMemberImpl ret = new ResolvedMemberImpl(this.getKind(), this.declaringType, this.getModifiers(), parameterizedReturnType, this.getName(), parameterizedParameterTypes, this.getExceptions(), this);
      ret.setTypeVariables(this.getTypeVariables());
      ret.setSourceContext(this.getSourceContext());
      ret.setPosition(this.getStart(), this.getEnd());
      ret.setParameterNames(this.getParameterNames());
      return ret;
   }

   public void setTypeVariables(TypeVariable[] tvars) {
      this.typeVariables = tvars;
   }

   public TypeVariable[] getTypeVariables() {
      return this.typeVariables;
   }

   protected UnresolvedType parameterize(UnresolvedType aType, Map typeVariableMap, boolean inParameterizedType, World w) {
      if (aType instanceof TypeVariableReference) {
         String variableName = ((TypeVariableReference)aType).getTypeVariable().getName();
         return !typeVariableMap.containsKey(variableName) ? aType : (UnresolvedType)typeVariableMap.get(variableName);
      } else if (aType.isParameterizedType()) {
         if (inParameterizedType) {
            ResolvedType aType;
            if (w != null) {
               aType = aType.resolve(w);
            } else {
               UnresolvedType dType = this.getDeclaringType();
               aType = aType.resolve(((ResolvedType)dType).getWorld());
            }

            return aType.parameterize(typeVariableMap);
         } else {
            return aType.getRawType();
         }
      } else if (!aType.isArray()) {
         return aType;
      } else {
         int dims = 1;
         String sig = aType.getSignature();
         UnresolvedType arrayType = null;
         UnresolvedType componentSig = UnresolvedType.forSignature(sig.substring(dims));
         UnresolvedType parameterizedComponentSig = this.parameterize(componentSig, typeVariableMap, inParameterizedType, w);
         if (parameterizedComponentSig.isTypeVariableReference() && parameterizedComponentSig instanceof UnresolvedTypeVariableReferenceType && typeVariableMap.containsKey(((UnresolvedTypeVariableReferenceType)parameterizedComponentSig).getTypeVariable().getName())) {
            StringBuffer newsig = new StringBuffer();
            newsig.append("[T");
            newsig.append(((UnresolvedTypeVariableReferenceType)parameterizedComponentSig).getTypeVariable().getName());
            newsig.append(";");
            arrayType = UnresolvedType.forSignature(newsig.toString());
         } else {
            arrayType = ResolvedType.makeArray(parameterizedComponentSig, dims);
         }

         return arrayType;
      }
   }

   public boolean hasBackingGenericMember() {
      return this.backingGenericMember != null;
   }

   public ResolvedMember getBackingGenericMember() {
      return this.backingGenericMember;
   }

   public void resetName(String newName) {
      this.name = newName;
   }

   public void resetKind(MemberKind newKind) {
      this.kind = newKind;
   }

   public void resetModifiers(int newModifiers) {
      this.modifiers = newModifiers;
   }

   public void resetReturnTypeToObjectArray() {
      this.returnType = UnresolvedType.OBJECTARRAY;
   }

   public boolean matches(ResolvedMember aCandidateMatch, boolean ignoreGenerics) {
      ResolvedMemberImpl candidateMatchImpl = (ResolvedMemberImpl)aCandidateMatch;
      if (!this.getName().equals(aCandidateMatch.getName())) {
         return false;
      } else {
         UnresolvedType[] parameterTypes = this.getGenericParameterTypes();
         UnresolvedType[] candidateParameterTypes = aCandidateMatch.getGenericParameterTypes();
         if (parameterTypes.length != candidateParameterTypes.length) {
            return false;
         } else {
            boolean b = false;
            String myParameterSignature = this.getParameterSigWithBoundsRemoved();
            String candidateParameterSignature = candidateMatchImpl.getParameterSigWithBoundsRemoved();
            if (myParameterSignature.equals(candidateParameterSignature)) {
               b = true;
            } else {
               myParameterSignature = this.getParameterSignatureErased();
               candidateParameterSignature = candidateMatchImpl.getParameterSignatureErased();
               b = myParameterSignature.equals(candidateParameterSignature);
            }

            return b;
         }
      }
   }

   private String getParameterSigWithBoundsRemoved() {
      if (this.myParameterSignatureWithBoundsRemoved != null) {
         return this.myParameterSignatureWithBoundsRemoved;
      } else {
         StringBuffer sig = new StringBuffer();
         UnresolvedType[] myParameterTypes = this.getGenericParameterTypes();

         for(int i = 0; i < myParameterTypes.length; ++i) {
            appendSigWithTypeVarBoundsRemoved(myParameterTypes[i], sig, new HashSet());
         }

         this.myParameterSignatureWithBoundsRemoved = sig.toString();
         return this.myParameterSignatureWithBoundsRemoved;
      }
   }

   public String getParameterSignatureErased() {
      if (this.myParameterSignatureErasure == null) {
         StringBuilder sig = new StringBuilder();
         UnresolvedType[] arr$ = this.getParameterTypes();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            UnresolvedType parameter = arr$[i$];
            sig.append(parameter.getErasureSignature());
         }

         this.myParameterSignatureErasure = sig.toString();
      }

      return this.myParameterSignatureErasure;
   }

   public String getSignatureErased() {
      StringBuffer sb = new StringBuffer();
      sb.append("(");
      sb.append(this.getParameterSignatureErased());
      sb.append(")");
      sb.append(this.getReturnType().getErasureSignature());
      return sb.toString();
   }

   public static void appendSigWithTypeVarBoundsRemoved(UnresolvedType aType, StringBuffer toBuffer, Set alreadyUsedTypeVars) {
      if (aType.isTypeVariableReference()) {
         TypeVariableReferenceType typeVariableRT = (TypeVariableReferenceType)aType;
         if (alreadyUsedTypeVars.contains(aType)) {
            toBuffer.append("...");
         } else {
            alreadyUsedTypeVars.add(aType);
            appendSigWithTypeVarBoundsRemoved(typeVariableRT.getTypeVariable().getFirstBound(), toBuffer, alreadyUsedTypeVars);
         }
      } else if (aType.isParameterizedType()) {
         toBuffer.append(aType.getRawType().getSignature());
         toBuffer.append("<");

         for(int i = 0; i < aType.getTypeParameters().length; ++i) {
            appendSigWithTypeVarBoundsRemoved(aType.getTypeParameters()[i], toBuffer, alreadyUsedTypeVars);
         }

         toBuffer.append(">;");
      } else {
         toBuffer.append(aType.getSignature());
      }

   }

   public String toDebugString() {
      StringBuffer r = new StringBuffer();
      int mods = this.modifiers;
      if ((mods & 4096) > 0) {
         mods -= 4096;
      }

      if ((mods & 512) > 0) {
         mods -= 512;
      }

      if ((mods & 131072) > 0) {
         mods -= 131072;
      }

      String modsStr = Modifier.toString(mods);
      if (modsStr.length() != 0) {
         r.append(modsStr).append("(" + mods + ")").append(" ");
      }

      if (this.typeVariables != null && this.typeVariables.length > 0) {
         r.append("<");

         for(int i = 0; i < this.typeVariables.length; ++i) {
            if (i > 0) {
               r.append(",");
            }

            TypeVariable t = this.typeVariables[i];
            r.append(t.toDebugString());
         }

         r.append("> ");
      }

      r.append(this.getGenericReturnType().toDebugString());
      r.append(' ');
      r.append(this.declaringType.getName());
      r.append('.');
      r.append(this.name);
      if (this.kind != FIELD) {
         r.append("(");
         UnresolvedType[] params = this.getGenericParameterTypes();
         boolean parameterNamesExist = showParameterNames && this.parameterNames != null && this.parameterNames.length == params.length;
         if (params.length != 0) {
            int i = 0;

            for(int len = params.length; i < len; ++i) {
               if (i > 0) {
                  r.append(", ");
               }

               r.append(params[i].toDebugString());
               if (parameterNamesExist) {
                  r.append(" ").append(this.parameterNames[i]);
               }
            }
         }

         r.append(")");
      }

      return r.toString();
   }

   public String toGenericString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.getGenericReturnType().getSimpleName());
      buf.append(' ');
      buf.append(this.declaringType.getName());
      buf.append('.');
      buf.append(this.name);
      if (this.kind != FIELD) {
         buf.append("(");
         UnresolvedType[] params = this.getGenericParameterTypes();
         if (params.length != 0) {
            buf.append(params[0].getSimpleName());
            int i = 1;

            for(int len = params.length; i < len; ++i) {
               buf.append(", ");
               buf.append(params[i].getSimpleName());
            }
         }

         buf.append(")");
      }

      return buf.toString();
   }

   public boolean isCompatibleWith(Member am) {
      if (this.kind == METHOD && am.getKind() == METHOD) {
         if (!this.name.equals(am.getName())) {
            return true;
         } else {
            return !equalTypes(this.getParameterTypes(), am.getParameterTypes()) ? true : this.getReturnType().equals(am.getReturnType());
         }
      } else {
         return true;
      }
   }

   private static boolean equalTypes(UnresolvedType[] a, UnresolvedType[] b) {
      int len = a.length;
      if (len != b.length) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (!a[i].equals(b[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public TypeVariable getTypeVariableNamed(String name) {
      if (this.typeVariables != null) {
         for(int i = 0; i < this.typeVariables.length; ++i) {
            if (this.typeVariables[i].getName().equals(name)) {
               return this.typeVariables[i];
            }
         }
      }

      return this.declaringType.getTypeVariableNamed(name);
   }

   public void evictWeavingState() {
   }

   public boolean isEquivalentTo(Object other) {
      return this.equals(other);
   }

   public boolean isDefaultConstructor() {
      return false;
   }
}
