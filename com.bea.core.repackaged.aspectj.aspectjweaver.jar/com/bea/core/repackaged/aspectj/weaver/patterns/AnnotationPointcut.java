package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ConcreteTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.NewFieldTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnnotationPointcut extends NameBindingPointcut {
   private ExactAnnotationTypePattern annotationTypePattern;
   private String declarationText;

   public AnnotationPointcut(ExactAnnotationTypePattern type) {
      this.annotationTypePattern = type;
      this.pointcutKind = 16;
      this.buildDeclarationText();
   }

   public AnnotationPointcut(ExactAnnotationTypePattern type, ShadowMunger munger) {
      this(type);
      this.buildDeclarationText();
   }

   public ExactAnnotationTypePattern getAnnotationTypePattern() {
      return this.annotationTypePattern;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      AnnotationPointcut ret = new AnnotationPointcut((ExactAnnotationTypePattern)this.annotationTypePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return info.getKind() == Shadow.StaticInitialization ? this.annotationTypePattern.fastMatches(info.getType()) : FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      AnnotatedElement toMatchAgainst = null;
      Member member = shadow.getSignature();
      ResolvedMember rMember = member.resolve(shadow.getIWorld());
      if (rMember == null) {
         if (member.getName().startsWith("ajc$")) {
            return FuzzyBoolean.NO;
         } else {
            shadow.getIWorld().getLint().unresolvableMember.signal(member.toString(), this.getSourceLocation());
            return FuzzyBoolean.NO;
         }
      } else {
         Shadow.Kind kind = shadow.getKind();
         if (kind == Shadow.StaticInitialization) {
            toMatchAgainst = rMember.getDeclaringType().resolve(shadow.getIWorld());
         } else if (kind == Shadow.ExceptionHandler) {
            toMatchAgainst = rMember.getParameterTypes()[0].resolve(shadow.getIWorld());
         } else {
            toMatchAgainst = rMember;
            if (rMember.isAnnotatedElsewhere() && (kind == Shadow.FieldGet || kind == Shadow.FieldSet)) {
               List mungers = rMember.getDeclaringType().resolve(shadow.getIWorld()).getInterTypeMungers();
               Iterator iter = mungers.iterator();

               while(iter.hasNext()) {
                  ConcreteTypeMunger typeMunger = (ConcreteTypeMunger)iter.next();
                  if (typeMunger.getMunger() instanceof NewFieldTypeMunger) {
                     ResolvedMember fakerm = typeMunger.getSignature();
                     if (fakerm.equals(member)) {
                        ResolvedMember ajcMethod = AjcMemberMaker.interFieldInitializer(fakerm, typeMunger.getAspectType());
                        ResolvedMember rmm = this.findMethod(typeMunger.getAspectType(), ajcMethod);
                        toMatchAgainst = rmm;
                     }
                  }
               }
            }
         }

         this.annotationTypePattern.resolve(shadow.getIWorld());
         return this.annotationTypePattern.matches((AnnotatedElement)toMatchAgainst);
      }
   }

   private ResolvedMember findMethod(ResolvedType aspectType, ResolvedMember ajcMethod) {
      ResolvedMember[] decMethods = aspectType.getDeclaredMethods();

      for(int i = 0; i < decMethods.length; ++i) {
         ResolvedMember member = decMethods[i];
         if (member.equals(ajcMethod)) {
            return member;
         }
      }

      return null;
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format("atannotationNeedsJava5"), this.getSourceLocation()));
      } else {
         this.annotationTypePattern = (ExactAnnotationTypePattern)this.annotationTypePattern.resolveBindings(scope, bindings, true);
      }
   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      ExactAnnotationTypePattern newType = (ExactAnnotationTypePattern)this.annotationTypePattern.remapAdviceFormals(bindings);
      Pointcut ret = new AnnotationPointcut(newType, bindings.getEnclosingAdvice());
      ret.copyLocationFrom(this);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.annotationTypePattern instanceof BindingAnnotationFieldTypePattern) {
         if (shadow.getKind() != Shadow.MethodExecution) {
            shadow.getIWorld().getMessageHandler().handleMessage(MessageUtil.error("Annotation field binding is only supported at method-execution join points (compiler limitation)", this.getSourceLocation()));
            return Literal.TRUE;
         }

         BindingAnnotationFieldTypePattern btp = (BindingAnnotationFieldTypePattern)this.annotationTypePattern;
         ResolvedType formalType = btp.getFormalType().resolve(shadow.getIWorld());
         UnresolvedType annoType = btp.getAnnotationType();
         Var var = shadow.getKindedAnnotationVar(annoType);
         if (var == null) {
            throw new BCException("Unexpected problem locating annotation at join point '" + shadow + "'");
         }

         state.set(btp.getFormalIndex(), var.getAccessorForValue(formalType, btp.formalName));
      } else if (this.annotationTypePattern instanceof BindingAnnotationTypePattern) {
         BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)this.annotationTypePattern;
         UnresolvedType annotationType = btp.getAnnotationType();
         Var var = shadow.getKindedAnnotationVar(annotationType);
         if (var == null) {
            if (this.matchInternal(shadow).alwaysTrue()) {
               return Literal.TRUE;
            }

            return Literal.FALSE;
         }

         state.set(btp.getFormalIndex(), var);
      }

      return this.matchInternal(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public List getBindingAnnotationTypePatterns() {
      if (this.annotationTypePattern instanceof BindingPattern) {
         List l = new ArrayList();
         l.add((BindingPattern)this.annotationTypePattern);
         return l;
      } else {
         return Collections.emptyList();
      }
   }

   public List getBindingTypePatterns() {
      return Collections.emptyList();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(16);
      this.annotationTypePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern type = AnnotationTypePattern.read(s, context);
      AnnotationPointcut ret = new AnnotationPointcut((ExactAnnotationTypePattern)type);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object other) {
      if (!(other instanceof AnnotationPointcut)) {
         return false;
      } else {
         AnnotationPointcut o = (AnnotationPointcut)other;
         return o.annotationTypePattern.equals(this.annotationTypePattern);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.annotationTypePattern.hashCode();
      return result;
   }

   public void buildDeclarationText() {
      StringBuffer buf = new StringBuffer();
      buf.append("@annotation(");
      String annPatt = this.annotationTypePattern.toString();
      buf.append(annPatt.startsWith("@") ? annPatt.substring(1) : annPatt);
      buf.append(")");
      this.declarationText = buf.toString();
   }

   public String toString() {
      return this.declarationText;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
