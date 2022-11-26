package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResolvedPointcutDefinition extends ResolvedMemberImpl {
   private Pointcut pointcut;
   public static final ResolvedPointcutDefinition DUMMY;
   public static final ResolvedPointcutDefinition[] NO_POINTCUTS;

   public ResolvedPointcutDefinition(UnresolvedType declaringType, int modifiers, String name, UnresolvedType[] parameterTypes, Pointcut pointcut) {
      this(declaringType, modifiers, name, parameterTypes, UnresolvedType.VOID, pointcut);
   }

   public ResolvedPointcutDefinition(UnresolvedType declaringType, int modifiers, String name, UnresolvedType[] parameterTypes, UnresolvedType returnType, Pointcut pointcut) {
      super(POINTCUT, declaringType, modifiers, returnType, name, parameterTypes);
      this.pointcut = pointcut;
      this.checkedExceptions = UnresolvedType.NONE;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.getDeclaringType().write(s);
      s.writeInt(this.getModifiers());
      s.writeUTF(this.getName());
      UnresolvedType.writeArray(this.getParameterTypes(), s);
      this.pointcut.write(s);
   }

   public static ResolvedPointcutDefinition read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      ResolvedPointcutDefinition rpd = new ResolvedPointcutDefinition(UnresolvedType.read(s), s.readInt(), s.readUTF(), UnresolvedType.readArray(s), Pointcut.read(s, context));
      rpd.setSourceContext(context);
      return rpd;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("pointcut ");
      buf.append(this.getDeclaringType() == null ? "<nullDeclaringType>" : this.getDeclaringType().getName());
      buf.append(".");
      buf.append(this.getName());
      buf.append("(");

      for(int i = 0; i < this.getParameterTypes().length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.getParameterTypes()[i].toString());
      }

      buf.append(")");
      return buf.toString();
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public boolean isAjSynthetic() {
      return true;
   }

   public ResolvedMemberImpl parameterizedWith(UnresolvedType[] typeParameters, ResolvedType newDeclaringType, boolean isParameterized) {
      TypeVariable[] typeVariables = this.getDeclaringType().resolve(newDeclaringType.getWorld()).getTypeVariables();
      if (isParameterized && typeVariables.length != typeParameters.length) {
         throw new IllegalStateException("Wrong number of type parameters supplied");
      } else {
         Map typeMap = new HashMap();
         boolean typeParametersSupplied = typeParameters != null && typeParameters.length > 0;
         if (typeVariables != null) {
            for(int i = 0; i < typeVariables.length; ++i) {
               UnresolvedType ut = !typeParametersSupplied ? typeVariables[i].getFirstBound() : typeParameters[i];
               typeMap.put(typeVariables[i].getName(), ut);
            }
         }

         UnresolvedType parameterizedReturnType = this.parameterize(this.getGenericReturnType(), typeMap, isParameterized, newDeclaringType.getWorld());
         UnresolvedType[] parameterizedParameterTypes = new UnresolvedType[this.getGenericParameterTypes().length];

         for(int i = 0; i < parameterizedParameterTypes.length; ++i) {
            parameterizedParameterTypes[i] = this.parameterize(this.getGenericParameterTypes()[i], typeMap, isParameterized, newDeclaringType.getWorld());
         }

         ResolvedPointcutDefinition ret = new ResolvedPointcutDefinition(newDeclaringType, this.getModifiers(), this.getName(), parameterizedParameterTypes, parameterizedReturnType, this.pointcut.parameterizeWith(typeMap, newDeclaringType.getWorld()));
         ret.setTypeVariables(this.getTypeVariables());
         ret.setSourceContext(this.getSourceContext());
         ret.setPosition(this.getStart(), this.getEnd());
         ret.setParameterNames(this.getParameterNames());
         return ret;
      }
   }

   public void setPointcut(Pointcut pointcut) {
      this.pointcut = pointcut;
   }

   static {
      DUMMY = new ResolvedPointcutDefinition(UnresolvedType.OBJECT, 0, "missing", UnresolvedType.NONE, Pointcut.makeMatchesNothing(Pointcut.RESOLVED));
      NO_POINTCUTS = new ResolvedPointcutDefinition[0];
   }
}
