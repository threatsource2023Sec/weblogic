package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MemberImpl implements Member {
   protected MemberKind kind;
   protected int modifiers;
   protected String name;
   protected UnresolvedType declaringType;
   protected UnresolvedType returnType;
   protected UnresolvedType[] parameterTypes;
   private final String erasedSignature;
   private String paramSignature;
   private boolean reportedCantFindDeclaringType = false;
   private boolean reportedUnresolvableMember = false;
   private JoinPointSignatureIterator joinPointSignatures = null;
   private volatile int hashCode = 0;

   public MemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, String name, String erasedSignature) {
      this.kind = kind;
      this.declaringType = declaringType;
      this.modifiers = modifiers;
      this.name = name;
      this.erasedSignature = erasedSignature;
      if (kind == FIELD) {
         this.returnType = UnresolvedType.forSignature(erasedSignature);
         this.parameterTypes = UnresolvedType.NONE;
      } else {
         Object[] returnAndParams = signatureToTypes(erasedSignature);
         this.returnType = (UnresolvedType)returnAndParams[0];
         this.parameterTypes = (UnresolvedType[])((UnresolvedType[])returnAndParams[1]);
      }

   }

   public MemberImpl(MemberKind kind, UnresolvedType declaringType, int modifiers, UnresolvedType returnType, String name, UnresolvedType[] parameterTypes) {
      this.kind = kind;
      this.declaringType = declaringType;
      this.modifiers = modifiers;
      this.returnType = returnType;
      this.name = name;
      this.parameterTypes = parameterTypes;
      if (kind == FIELD) {
         this.erasedSignature = returnType.getErasureSignature();
      } else {
         this.erasedSignature = typesToSignature(returnType, parameterTypes, true);
      }

   }

   public ResolvedMember resolve(World world) {
      return world.resolve((Member)this);
   }

   public static String typesToSignature(UnresolvedType returnType, UnresolvedType[] paramTypes, boolean eraseGenerics) {
      StringBuilder buf = new StringBuilder();
      buf.append("(");
      UnresolvedType[] arr$ = paramTypes;
      int len$ = paramTypes.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         UnresolvedType paramType = arr$[i$];
         if (eraseGenerics) {
            buf.append(paramType.getErasureSignature());
         } else {
            buf.append(paramType.getSignature());
         }
      }

      buf.append(")");
      if (eraseGenerics) {
         buf.append(returnType.getErasureSignature());
      } else {
         buf.append(returnType.getSignature());
      }

      return buf.toString();
   }

   public static String typesToSignature(UnresolvedType[] paramTypes) {
      StringBuffer buf = new StringBuffer();
      buf.append("(");

      for(int i = 0; i < paramTypes.length; ++i) {
         buf.append(paramTypes[i].getSignature());
      }

      buf.append(")");
      return buf.toString();
   }

   private static Object[] signatureToTypes(String sig) {
      boolean hasParameters = sig.charAt(1) != ')';
      if (!hasParameters) {
         UnresolvedType returnType = UnresolvedType.forSignature(sig.substring(2));
         return new Object[]{returnType, UnresolvedType.NONE};
      } else {
         List l = new ArrayList();
         int i = 1;
         boolean hasAnyAnglies = sig.indexOf(60) != -1;

         while(true) {
            while(true) {
               char c = sig.charAt(i);
               if (c == ')') {
                  UnresolvedType[] paramTypes = (UnresolvedType[])l.toArray(new UnresolvedType[l.size()]);
                  UnresolvedType returnType = UnresolvedType.forSignature(sig.substring(i + 1, sig.length()));
                  return new Object[]{returnType, paramTypes};
               }

               int start;
               for(start = i; c == '['; c = sig.charAt(i)) {
                  ++i;
               }

               int nextSemicolon;
               if (c != 'L' && c != 'P') {
                  if (c == 'T') {
                     nextSemicolon = sig.indexOf(59, i);
                     String nextbit = sig.substring(i, nextSemicolon + 1);
                     l.add(UnresolvedType.forSignature(nextbit));
                     i = nextSemicolon + 1;
                  } else {
                     ++i;
                     l.add(UnresolvedType.forSignature(sig.substring(start, i)));
                  }
               } else {
                  nextSemicolon = sig.indexOf(59, i);
                  int firstAngly = hasAnyAnglies ? sig.indexOf(60, i) : -1;
                  if (hasAnyAnglies && firstAngly != -1 && firstAngly <= nextSemicolon) {
                     boolean endOfSigReached = false;
                     int posn = firstAngly;

                     for(int genericDepth = 0; !endOfSigReached; ++posn) {
                        switch (sig.charAt(posn)) {
                           case ';':
                              if (genericDepth == 0) {
                                 endOfSigReached = true;
                              }
                              break;
                           case '<':
                              ++genericDepth;
                           case '=':
                           default:
                              break;
                           case '>':
                              --genericDepth;
                        }
                     }

                     i = posn;
                     l.add(UnresolvedType.forSignature(sig.substring(start, posn)));
                  } else {
                     i = nextSemicolon + 1;
                     l.add(UnresolvedType.forSignature(sig.substring(start, i)));
                  }
               }
            }
         }
      }
   }

   public static MemberImpl field(String declaring, int mods, String name, String signature) {
      return field(declaring, mods, UnresolvedType.forSignature(signature), name);
   }

   public static MemberImpl method(UnresolvedType declaring, int mods, String name, String signature) {
      Object[] pair = signatureToTypes(signature);
      return method(declaring, mods, (UnresolvedType)pair[0], name, (UnresolvedType[])((UnresolvedType[])pair[1]));
   }

   public static MemberImpl monitorEnter() {
      return new MemberImpl(MONITORENTER, UnresolvedType.OBJECT, 8, UnresolvedType.VOID, "<lock>", UnresolvedType.ARRAY_WITH_JUST_OBJECT);
   }

   public static MemberImpl monitorExit() {
      return new MemberImpl(MONITOREXIT, UnresolvedType.OBJECT, 8, UnresolvedType.VOID, "<unlock>", UnresolvedType.ARRAY_WITH_JUST_OBJECT);
   }

   public static Member pointcut(UnresolvedType declaring, String name, String signature) {
      Object[] pair = signatureToTypes(signature);
      return pointcut(declaring, 0, (UnresolvedType)pair[0], name, (UnresolvedType[])((UnresolvedType[])pair[1]));
   }

   private static MemberImpl field(String declaring, int mods, UnresolvedType ty, String name) {
      return new MemberImpl(FIELD, UnresolvedType.forName(declaring), mods, ty, name, UnresolvedType.NONE);
   }

   public static MemberImpl method(UnresolvedType declTy, int mods, UnresolvedType rTy, String name, UnresolvedType[] paramTys) {
      return new MemberImpl(name.equals("<init>") ? CONSTRUCTOR : METHOD, declTy, mods, rTy, name, paramTys);
   }

   private static Member pointcut(UnresolvedType declTy, int mods, UnresolvedType rTy, String name, UnresolvedType[] paramTys) {
      return new MemberImpl(POINTCUT, declTy, mods, rTy, name, paramTys);
   }

   public static ResolvedMemberImpl makeExceptionHandlerSignature(UnresolvedType inType, UnresolvedType catchType) {
      return new ResolvedMemberImpl(HANDLER, inType, 8, "<catch>", "(" + catchType.getSignature() + ")V");
   }

   public final boolean equals(Object other) {
      if (!(other instanceof Member)) {
         return false;
      } else {
         Member o = (Member)other;
         return this.getKind() == o.getKind() && this.getName().equals(o.getName()) && this.getSignature().equals(o.getSignature()) && this.getDeclaringType().equals(o.getDeclaringType());
      }
   }

   public final boolean equalsApartFromDeclaringType(Object other) {
      if (!(other instanceof Member)) {
         return false;
      } else {
         Member o = (Member)other;
         return this.getKind() == o.getKind() && this.getName().equals(o.getName()) && this.getSignature().equals(o.getSignature());
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + this.getKind().hashCode();
         result = 37 * result + this.getName().hashCode();
         result = 37 * result + this.getSignature().hashCode();
         result = 37 * result + this.getDeclaringType().hashCode();
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public int compareTo(Member other) {
      int i = this.getName().compareTo(other.getName());
      return i != 0 ? i : this.getSignature().compareTo(other.getSignature());
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.returnType.getName());
      buf.append(' ');
      if (this.declaringType == null) {
         buf.append("<NULL>");
      } else {
         buf.append(this.declaringType.getName());
      }

      buf.append('.');
      buf.append(this.name);
      if (this.kind != FIELD) {
         buf.append("(");
         if (this.parameterTypes.length != 0) {
            buf.append(this.parameterTypes[0]);
            int i = 1;

            for(int len = this.parameterTypes.length; i < len; ++i) {
               buf.append(", ");
               buf.append(this.parameterTypes[i].getName());
            }
         }

         buf.append(")");
      }

      return buf.toString();
   }

   public MemberKind getKind() {
      return this.kind;
   }

   public UnresolvedType getDeclaringType() {
      return this.declaringType;
   }

   public UnresolvedType getReturnType() {
      return this.returnType;
   }

   public UnresolvedType getGenericReturnType() {
      return this.getReturnType();
   }

   public UnresolvedType[] getGenericParameterTypes() {
      return this.getParameterTypes();
   }

   public final UnresolvedType getType() {
      return this.returnType;
   }

   public String getName() {
      return this.name;
   }

   public UnresolvedType[] getParameterTypes() {
      return this.parameterTypes;
   }

   public String getSignature() {
      return this.erasedSignature;
   }

   public int getArity() {
      return this.parameterTypes.length;
   }

   public String getParameterSignature() {
      if (this.paramSignature == null) {
         StringBuilder sb = new StringBuilder("(");
         UnresolvedType[] arr$ = this.parameterTypes;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            UnresolvedType parameterType = arr$[i$];
            sb.append(parameterType.getSignature());
         }

         this.paramSignature = sb.append(")").toString();
      }

      return this.paramSignature;
   }

   public int getModifiers(World world) {
      ResolvedMember resolved = this.resolve(world);
      if (resolved == null) {
         this.reportDidntFindMember(world);
         return 0;
      } else {
         return resolved.getModifiers();
      }
   }

   public UnresolvedType[] getExceptions(World world) {
      ResolvedMember resolved = this.resolve(world);
      if (resolved == null) {
         this.reportDidntFindMember(world);
         return UnresolvedType.NONE;
      } else {
         return resolved.getExceptions();
      }
   }

   public final boolean isStatic() {
      return Modifier.isStatic(this.modifiers);
   }

   public final boolean isInterface() {
      return Modifier.isInterface(this.modifiers);
   }

   public final boolean isPrivate() {
      return Modifier.isPrivate(this.modifiers);
   }

   public boolean canBeParameterized() {
      return false;
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public AnnotationAJ[] getAnnotations() {
      throw new UnsupportedOperationException("You should resolve this member '" + this + "' and call getAnnotations() on the result...");
   }

   public Collection getDeclaringTypes(World world) {
      ResolvedType myType = this.getDeclaringType().resolve(world);
      Collection ret = new HashSet();
      if (this.kind == CONSTRUCTOR) {
         ret.add(myType);
      } else if (!Modifier.isStatic(this.modifiers) && this.kind != FIELD) {
         this.walkUp(ret, myType);
      } else {
         this.walkUpStatic(ret, myType);
      }

      return ret;
   }

   private boolean walkUp(Collection acc, ResolvedType curr) {
      if (acc.contains(curr)) {
         return true;
      } else {
         boolean b = false;

         for(Iterator i = curr.getDirectSupertypes(); i.hasNext(); b |= this.walkUp(acc, (ResolvedType)i.next())) {
         }

         if (!b && curr.isParameterizedType()) {
            b = this.walkUp(acc, curr.getGenericType());
         }

         if (!b) {
            b = curr.lookupMemberNoSupers(this) != null;
         }

         if (b) {
            acc.add(curr);
         }

         return b;
      }
   }

   private boolean walkUpStatic(Collection acc, ResolvedType curr) {
      if (curr.lookupMemberNoSupers(this) != null) {
         acc.add(curr);
         return true;
      } else {
         boolean b = false;

         for(Iterator i = curr.getDirectSupertypes(); i.hasNext(); b |= this.walkUpStatic(acc, (ResolvedType)i.next())) {
         }

         if (!b && curr.isParameterizedType()) {
            b = this.walkUpStatic(acc, curr.getGenericType());
         }

         if (b) {
            acc.add(curr);
         }

         return b;
      }
   }

   public String[] getParameterNames(World world) {
      ResolvedMember resolved = this.resolve(world);
      if (resolved == null) {
         this.reportDidntFindMember(world);
         return null;
      } else {
         return resolved.getParameterNames();
      }
   }

   public JoinPointSignatureIterator getJoinPointSignatures(World inAWorld) {
      if (this.joinPointSignatures == null) {
         this.joinPointSignatures = new JoinPointSignatureIterator(this, inAWorld);
      }

      this.joinPointSignatures.reset();
      return this.joinPointSignatures;
   }

   private void reportDidntFindMember(World world) {
      if (!this.reportedCantFindDeclaringType && !this.reportedUnresolvableMember) {
         ResolvedType rType = this.getDeclaringType().resolve(world);
         if (rType.isMissing()) {
            world.getLint().cantFindType.signal(WeaverMessages.format("cantFindType", rType.getName()), (ISourceLocation)null);
            this.reportedCantFindDeclaringType = true;
         } else {
            world.getLint().unresolvableMember.signal(this.getName(), (ISourceLocation)null);
            this.reportedUnresolvableMember = true;
         }

      }
   }

   public void wipeJoinpointSignatures() {
      this.joinPointSignatures = null;
   }
}
