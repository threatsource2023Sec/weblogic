package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HasMemberTypePattern extends TypePattern {
   private SignaturePattern signaturePattern;
   private static final String declareAtPrefix = "ajc$declare_at";

   public HasMemberTypePattern(SignaturePattern aSignaturePattern) {
      super(false, false);
      this.signaturePattern = aSignaturePattern;
   }

   protected boolean matchesExactly(ResolvedType type) {
      return this.signaturePattern.getKind() == Member.FIELD ? this.hasField(type) : this.hasMethod(type);
   }

   public ISignaturePattern getSignaturePattern() {
      return this.signaturePattern;
   }

   private boolean hasField(ResolvedType type) {
      World world = type.getWorld();
      Iterator iter = type.getFields();

      Member field;
      do {
         do {
            do {
               if (!iter.hasNext()) {
                  return false;
               }

               field = (Member)iter.next();
            } while(field.getName().startsWith("ajc$declare_at"));
         } while(!this.signaturePattern.matches(field, type.getWorld(), false));
      } while(field.getDeclaringType().resolve(world) != type && Modifier.isPrivate(field.getModifiers()));

      return true;
   }

   protected boolean hasMethod(ResolvedType type) {
      World world = type.getWorld();
      Iterator iter = type.getMethods(true, true);

      Member method;
      do {
         do {
            do {
               if (!iter.hasNext()) {
                  List mungers = type.getInterTypeMungersIncludingSupers();
                  Iterator iter = mungers.iterator();

                  ResolvedMember member;
                  do {
                     if (!iter.hasNext()) {
                        return false;
                     }

                     ConcreteTypeMunger munger = (ConcreteTypeMunger)iter.next();
                     member = munger.getSignature();
                  } while(!this.signaturePattern.matches(member, type.getWorld(), false) || !Modifier.isPublic(member.getModifiers()));

                  return true;
               }

               method = (Member)iter.next();
            } while(method.getName().startsWith("ajc$declare_at"));
         } while(!this.signaturePattern.matches(method, type.getWorld(), false));
      } while(method.getDeclaringType().resolve(world) != type && Modifier.isPrivate(method.getModifiers()));

      return true;
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return this.matchesExactly(type);
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      throw new UnsupportedOperationException("hasmethod/field do not support instanceof matching");
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      HasMemberTypePattern ret = new HasMemberTypePattern(this.signaturePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (!scope.getWorld().isHasMemberSupportEnabled()) {
         String msg = WeaverMessages.format("hasMemberNotEnabled", this.toString());
         scope.message(IMessage.ERROR, this, msg);
      }

      this.signaturePattern.resolveBindings(scope, bindings);
      return this;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof HasMemberTypePattern)) {
         return false;
      } else {
         return this == obj ? true : this.signaturePattern.equals(((HasMemberTypePattern)obj).signaturePattern);
      }
   }

   public int hashCode() {
      return this.signaturePattern.hashCode();
   }

   public String toString() {
      StringBuffer buff = new StringBuffer();
      if (this.signaturePattern.getKind() == Member.FIELD) {
         buff.append("hasfield(");
      } else {
         buff.append("hasmethod(");
      }

      buff.append(this.signaturePattern.toString());
      buff.append(")");
      return buff.toString();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(11);
      this.signaturePattern.write(s);
      this.writeLocation(s);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      SignaturePattern sp = SignaturePattern.read(s, context);
      HasMemberTypePattern ret = new HasMemberTypePattern(sp);
      ret.readLocation(context, s);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
