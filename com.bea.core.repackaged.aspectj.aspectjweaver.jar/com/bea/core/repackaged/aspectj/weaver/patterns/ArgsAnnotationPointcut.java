package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
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
import java.util.List;
import java.util.Map;

public class ArgsAnnotationPointcut extends NameBindingPointcut {
   private AnnotationPatternList arguments;
   private String declarationText;

   public ArgsAnnotationPointcut(AnnotationPatternList arguments) {
      this.arguments = arguments;
      this.pointcutKind = 21;
      this.buildDeclarationText();
   }

   public AnnotationPatternList getArguments() {
      return this.arguments;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      ArgsAnnotationPointcut ret = new ArgsAnnotationPointcut(this.arguments.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      this.arguments.resolve(shadow.getIWorld());
      FuzzyBoolean ret = this.arguments.matches(shadow.getIWorld().resolve(shadow.getArgTypes()));
      return ret;
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format("atargsNeedsJava5"), this.getSourceLocation()));
      } else {
         this.arguments.resolveBindings(scope, bindings, true);
         if (this.arguments.ellipsisCount > 1) {
            scope.message(IMessage.ERROR, this, "uses more than one .. in args (compiler limitation)");
         }

      }
   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("argsInDeclare"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else {
         AnnotationPatternList list = this.arguments.resolveReferences(bindings);
         Pointcut ret = new ArgsAnnotationPointcut(list);
         ret.copyLocationFrom(this);
         return ret;
      }
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      int len = shadow.getArgCount();
      int numArgsMatchedByEllipsis = len + this.arguments.ellipsisCount - this.arguments.size();
      if (numArgsMatchedByEllipsis < 0) {
         return Literal.FALSE;
      } else if (numArgsMatchedByEllipsis > 0 && this.arguments.ellipsisCount == 0) {
         return Literal.FALSE;
      } else {
         Test ret = Literal.TRUE;
         int argsIndex = 0;

         for(int i = 0; i < this.arguments.size(); ++i) {
            if (this.arguments.get(i) == AnnotationTypePattern.ELLIPSIS) {
               argsIndex += numArgsMatchedByEllipsis;
            } else if (this.arguments.get(i) == AnnotationTypePattern.ANY) {
               ++argsIndex;
            } else {
               ExactAnnotationTypePattern ap = (ExactAnnotationTypePattern)this.arguments.get(i);
               UnresolvedType argType = shadow.getArgType(argsIndex);
               ResolvedType rArgType = argType.resolve(shadow.getIWorld());
               if (rArgType.isMissing()) {
                  shadow.getIWorld().getLint().cantFindType.signal(new String[]{WeaverMessages.format("cftArgType", argType.getName())}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
               }

               ResolvedType rAnnType = ap.getAnnotationType().resolve(shadow.getIWorld());
               if (ap instanceof BindingAnnotationTypePattern) {
                  BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)ap;
                  Var annvar = shadow.getArgAnnotationVar(argsIndex, rAnnType);
                  state.set(btp.getFormalIndex(), annvar);
               }

               if (!ap.matches(rArgType).alwaysTrue()) {
                  ret = Test.makeAnd((Test)ret, Test.makeHasAnnotation(shadow.getArgVar(argsIndex), rAnnType));
               }

               ++argsIndex;
            }
         }

         return (Test)ret;
      }
   }

   public List getBindingAnnotationTypePatterns() {
      List l = new ArrayList();
      AnnotationTypePattern[] pats = this.arguments.getAnnotationPatterns();

      for(int i = 0; i < pats.length; ++i) {
         if (pats[i] instanceof BindingAnnotationTypePattern) {
            l.add((BindingPattern)pats[i]);
         }
      }

      return l;
   }

   public List getBindingTypePatterns() {
      return Collections.emptyList();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(21);
      this.arguments.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationPatternList annotationPatternList = AnnotationPatternList.read(s, context);
      ArgsAnnotationPointcut ret = new ArgsAnnotationPointcut(annotationPatternList);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ArgsAnnotationPointcut)) {
         return false;
      } else {
         ArgsAnnotationPointcut other = (ArgsAnnotationPointcut)obj;
         return other.arguments.equals(this.arguments);
      }
   }

   public int hashCode() {
      return 17 + 37 * this.arguments.hashCode();
   }

   private void buildDeclarationText() {
      StringBuffer buf = new StringBuffer("@args");
      buf.append(this.arguments.toString());
      this.declarationText = buf.toString();
   }

   public String toString() {
      return this.declarationText;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
