package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.MemberKind;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.PointcutDesignatorHandlerBasedPointcut;
import com.bea.core.repackaged.aspectj.weaver.tools.ContextBasedMatcher;
import com.bea.core.repackaged.aspectj.weaver.tools.PointcutDesignatorHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PatternParser {
   private ITokenSource tokenSource;
   private ISourceContext sourceContext;
   private boolean allowHasTypePatterns;
   private Set pointcutDesignatorHandlers;
   private World world;
   private IToken pendingRightArrows;

   public PatternParser(ITokenSource tokenSource) {
      this.allowHasTypePatterns = false;
      this.pointcutDesignatorHandlers = Collections.emptySet();
      this.tokenSource = tokenSource;
      this.sourceContext = tokenSource.getSourceContext();
   }

   public void setPointcutDesignatorHandlers(Set handlers, World world) {
      this.pointcutDesignatorHandlers = handlers;
      this.world = world;
   }

   public PerClause maybeParsePerClause() {
      IToken tok = this.tokenSource.peek();
      if (tok == IToken.EOF) {
         return null;
      } else if (tok.isIdentifier()) {
         String name = tok.getString();
         if (name.equals("issingleton")) {
            return this.parsePerSingleton();
         } else if (name.equals("perthis")) {
            return this.parsePerObject(true);
         } else if (name.equals("pertarget")) {
            return this.parsePerObject(false);
         } else if (name.equals("percflow")) {
            return this.parsePerCflow(false);
         } else if (name.equals("percflowbelow")) {
            return this.parsePerCflow(true);
         } else {
            return name.equals("pertypewithin") ? this.parsePerTypeWithin() : null;
         }
      } else {
         return null;
      }
   }

   private PerClause parsePerCflow(boolean isBelow) {
      this.parseIdentifier();
      this.eat("(");
      Pointcut entry = this.parsePointcut();
      this.eat(")");
      return new PerCflow(entry, isBelow);
   }

   private PerClause parsePerObject(boolean isThis) {
      this.parseIdentifier();
      this.eat("(");
      Pointcut entry = this.parsePointcut();
      this.eat(")");
      return new PerObject(entry, isThis);
   }

   private PerClause parsePerTypeWithin() {
      this.parseIdentifier();
      this.eat("(");
      TypePattern withinTypePattern = this.parseTypePattern();
      this.eat(")");
      return new PerTypeWithin(withinTypePattern);
   }

   private PerClause parsePerSingleton() {
      this.parseIdentifier();
      this.eat("(");
      this.eat(")");
      return new PerSingleton();
   }

   public Declare parseDeclare() {
      int startPos = this.tokenSource.peek().getStart();
      this.eatIdentifier("declare");
      String kind = this.parseIdentifier();
      Object ret;
      if (kind.equals("error")) {
         this.eat(":");
         ret = this.parseErrorOrWarning(true);
      } else if (kind.equals("warning")) {
         this.eat(":");
         ret = this.parseErrorOrWarning(false);
      } else if (kind.equals("precedence")) {
         this.eat(":");
         ret = this.parseDominates();
      } else {
         if (kind.equals("dominates")) {
            throw new ParserException("name changed to declare precedence", this.tokenSource.peek(-2));
         }

         if (kind.equals("parents")) {
            ret = this.parseParents();
         } else {
            if (!kind.equals("soft")) {
               throw new ParserException("expected one of error, warning, parents, soft, precedence, @type, @method, @constructor, @field", this.tokenSource.peek(-1));
            }

            this.eat(":");
            ret = this.parseSoft();
         }
      }

      int endPos = this.tokenSource.peek(-1).getEnd();
      ((Declare)ret).setLocation(this.sourceContext, startPos, endPos);
      return (Declare)ret;
   }

   public Declare parseDeclareAnnotation() {
      int startPos = this.tokenSource.peek().getStart();
      this.eatIdentifier("declare");
      this.eat("@");
      String kind = this.parseIdentifier();
      this.eat(":");
      DeclareAnnotation ret;
      if (kind.equals("type")) {
         ret = this.parseDeclareAtType();
      } else if (kind.equals("method")) {
         ret = this.parseDeclareAtMethod(true);
      } else if (kind.equals("field")) {
         ret = this.parseDeclareAtField();
      } else {
         if (!kind.equals("constructor")) {
            throw new ParserException("one of type, method, field, constructor", this.tokenSource.peek(-1));
         }

         ret = this.parseDeclareAtMethod(false);
      }

      this.eat(";");
      int endPos = this.tokenSource.peek(-1).getEnd();
      ret.setLocation(this.sourceContext, startPos, endPos);
      return ret;
   }

   public DeclareAnnotation parseDeclareAtType() {
      this.allowHasTypePatterns = true;
      TypePattern p = this.parseTypePattern();
      this.allowHasTypePatterns = false;
      return new DeclareAnnotation(DeclareAnnotation.AT_TYPE, p);
   }

   public DeclareAnnotation parseDeclareAtMethod(boolean isMethod) {
      ISignaturePattern sp = this.parseCompoundMethodOrConstructorSignaturePattern(isMethod);
      return !isMethod ? new DeclareAnnotation(DeclareAnnotation.AT_CONSTRUCTOR, sp) : new DeclareAnnotation(DeclareAnnotation.AT_METHOD, sp);
   }

   public DeclareAnnotation parseDeclareAtField() {
      ISignaturePattern compoundFieldSignaturePattern = this.parseCompoundFieldSignaturePattern();
      DeclareAnnotation da = new DeclareAnnotation(DeclareAnnotation.AT_FIELD, compoundFieldSignaturePattern);
      return da;
   }

   public ISignaturePattern parseCompoundFieldSignaturePattern() {
      int index = this.tokenSource.getIndex();

      try {
         ISignaturePattern atomicFieldSignaturePattern = this.parseMaybeParenthesizedFieldSignaturePattern();

         while(this.isEitherAndOrOr()) {
            if (this.maybeEat("&&")) {
               atomicFieldSignaturePattern = new AndSignaturePattern((ISignaturePattern)atomicFieldSignaturePattern, this.parseMaybeParenthesizedFieldSignaturePattern());
            }

            if (this.maybeEat("||")) {
               atomicFieldSignaturePattern = new OrSignaturePattern((ISignaturePattern)atomicFieldSignaturePattern, this.parseMaybeParenthesizedFieldSignaturePattern());
            }
         }

         return (ISignaturePattern)atomicFieldSignaturePattern;
      } catch (ParserException var6) {
         int nowAt = this.tokenSource.getIndex();
         this.tokenSource.setIndex(index);

         try {
            ISignaturePattern fsp = this.parseFieldSignaturePattern();
            return fsp;
         } catch (Exception var5) {
            this.tokenSource.setIndex(nowAt);
            throw var6;
         }
      }
   }

   private boolean isEitherAndOrOr() {
      String tokenstring = this.tokenSource.peek().getString();
      return tokenstring.equals("&&") || tokenstring.equals("||");
   }

   public ISignaturePattern parseCompoundMethodOrConstructorSignaturePattern(boolean isMethod) {
      ISignaturePattern atomicMethodCtorSignaturePattern = this.parseMaybeParenthesizedMethodOrConstructorSignaturePattern(isMethod);

      while(this.isEitherAndOrOr()) {
         if (this.maybeEat("&&")) {
            atomicMethodCtorSignaturePattern = new AndSignaturePattern((ISignaturePattern)atomicMethodCtorSignaturePattern, this.parseMaybeParenthesizedMethodOrConstructorSignaturePattern(isMethod));
         }

         if (this.maybeEat("||")) {
            atomicMethodCtorSignaturePattern = new OrSignaturePattern((ISignaturePattern)atomicMethodCtorSignaturePattern, this.parseMaybeParenthesizedMethodOrConstructorSignaturePattern(isMethod));
         }
      }

      return (ISignaturePattern)atomicMethodCtorSignaturePattern;
   }

   public DeclarePrecedence parseDominates() {
      List l = new ArrayList();

      do {
         l.add(this.parseTypePattern());
      } while(this.maybeEat(","));

      return new DeclarePrecedence(l);
   }

   private Declare parseParents() {
      this.eat(":");
      this.allowHasTypePatterns = true;
      TypePattern p = this.parseTypePattern(false, false);
      this.allowHasTypePatterns = false;
      IToken t = this.tokenSource.next();
      if (!t.getString().equals("extends") && !t.getString().equals("implements")) {
         throw new ParserException("extends or implements", t);
      } else {
         boolean isExtends = t.getString().equals("extends");
         List l = new ArrayList();

         do {
            l.add(this.parseTypePattern());
         } while(this.maybeEat(","));

         DeclareParents decp = new DeclareParents(p, l, isExtends);
         return decp;
      }
   }

   private Declare parseSoft() {
      TypePattern p = this.parseTypePattern();
      this.eat(":");
      Pointcut pointcut = this.parsePointcut();
      return new DeclareSoft(p, pointcut);
   }

   private Declare parseErrorOrWarning(boolean isError) {
      Pointcut pointcut = null;
      int index = this.tokenSource.getIndex();

      try {
         pointcut = this.parsePointcut();
      } catch (ParserException var13) {
         try {
            this.tokenSource.setIndex(index);
            boolean oldValue = this.allowHasTypePatterns;
            TypePattern typePattern = null;

            try {
               this.allowHasTypePatterns = true;
               typePattern = this.parseTypePattern();
            } finally {
               this.allowHasTypePatterns = oldValue;
            }

            this.eat(":");
            String message = this.parsePossibleStringSequence(true);
            return new DeclareTypeErrorOrWarning(isError, typePattern, message);
         } catch (ParserException var12) {
            throw var13;
         }
      }

      this.eat(":");
      String message = this.parsePossibleStringSequence(true);
      return new DeclareErrorOrWarning(isError, pointcut, message);
   }

   public Pointcut parsePointcut() {
      Pointcut p = this.parseAtomicPointcut();
      if (this.maybeEat("&&")) {
         p = new AndPointcut((Pointcut)p, this.parseNotOrPointcut());
      }

      if (this.maybeEat("||")) {
         p = new OrPointcut((Pointcut)p, this.parsePointcut());
      }

      return (Pointcut)p;
   }

   private Pointcut parseNotOrPointcut() {
      Pointcut p = this.parseAtomicPointcut();
      if (this.maybeEat("&&")) {
         p = new AndPointcut((Pointcut)p, this.parseNotOrPointcut());
      }

      return (Pointcut)p;
   }

   private Pointcut parseAtomicPointcut() {
      int startPos;
      if (this.maybeEat("!")) {
         startPos = this.tokenSource.peek(-1).getStart();
         Pointcut p = new NotPointcut(this.parseAtomicPointcut(), startPos);
         return p;
      } else if (this.maybeEat("(")) {
         Pointcut p = this.parsePointcut();
         this.eat(")");
         return p;
      } else {
         Pointcut p;
         int endPos;
         if (this.maybeEat("@")) {
            startPos = this.tokenSource.peek().getStart();
            p = this.parseAnnotationPointcut();
            endPos = this.tokenSource.peek(-1).getEnd();
            p.setLocation(this.sourceContext, startPos, endPos);
            return p;
         } else {
            startPos = this.tokenSource.peek().getStart();
            p = this.parseSinglePointcut();
            endPos = this.tokenSource.peek(-1).getEnd();
            p.setLocation(this.sourceContext, startPos, endPos);
            return p;
         }
      }
   }

   public Pointcut parseSinglePointcut() {
      int start = this.tokenSource.getIndex();
      IToken t = this.tokenSource.peek();
      Pointcut p = t.maybeGetParsedPointcut();
      if (p != null) {
         this.tokenSource.next();
         return (Pointcut)p;
      } else {
         String kind = this.parseIdentifier();
         if (!kind.equals("execution") && !kind.equals("call") && !kind.equals("get") && !kind.equals("set")) {
            if (kind.equals("args")) {
               p = this.parseArgsPointcut();
            } else if (kind.equals("this")) {
               p = this.parseThisOrTargetPointcut(kind);
            } else if (kind.equals("target")) {
               p = this.parseThisOrTargetPointcut(kind);
            } else if (kind.equals("within")) {
               p = this.parseWithinPointcut();
            } else if (kind.equals("withincode")) {
               p = this.parseWithinCodePointcut();
            } else if (kind.equals("cflow")) {
               p = this.parseCflowPointcut(false);
            } else if (kind.equals("cflowbelow")) {
               p = this.parseCflowPointcut(true);
            } else if (kind.equals("adviceexecution")) {
               this.eat("(");
               this.eat(")");
               p = new KindedPointcut(Shadow.AdviceExecution, new SignaturePattern(Member.ADVICE, ModifiersPattern.ANY, TypePattern.ANY, TypePattern.ANY, NamePattern.ANY, TypePatternList.ANY, ThrowsPattern.ANY, AnnotationTypePattern.ANY));
            } else {
               TypePattern typePat;
               if (kind.equals("handler")) {
                  this.eat("(");
                  typePat = this.parseTypePattern(false, false);
                  this.eat(")");
                  p = new HandlerPointcut(typePat);
               } else if (!kind.equals("lock") && !kind.equals("unlock")) {
                  SignaturePattern sig;
                  if (kind.equals("initialization")) {
                     this.eat("(");
                     sig = this.parseConstructorSignaturePattern();
                     this.eat(")");
                     p = new KindedPointcut(Shadow.Initialization, sig);
                  } else if (kind.equals("staticinitialization")) {
                     this.eat("(");
                     typePat = this.parseTypePattern(false, false);
                     this.eat(")");
                     p = new KindedPointcut(Shadow.StaticInitialization, new SignaturePattern(Member.STATIC_INITIALIZATION, ModifiersPattern.ANY, TypePattern.ANY, typePat, NamePattern.ANY, TypePatternList.EMPTY, ThrowsPattern.ANY, AnnotationTypePattern.ANY));
                  } else if (kind.equals("preinitialization")) {
                     this.eat("(");
                     sig = this.parseConstructorSignaturePattern();
                     this.eat(")");
                     p = new KindedPointcut(Shadow.PreInitialization, sig);
                  } else if (kind.equals("if")) {
                     this.eat("(");
                     if (this.maybeEatIdentifier("true")) {
                        this.eat(")");
                        p = new IfPointcut.IfTruePointcut();
                     } else if (this.maybeEatIdentifier("false")) {
                        this.eat(")");
                        p = new IfPointcut.IfFalsePointcut();
                     } else {
                        if (!this.maybeEat(")")) {
                           throw new ParserException("in annotation style, if(...) pointcuts cannot contain code. Use if() and put the code in the annotated method", t);
                        }

                        p = new IfPointcut("");
                     }
                  } else {
                     boolean matchedByExtensionDesignator = false;
                     Iterator i$ = this.pointcutDesignatorHandlers.iterator();

                     while(i$.hasNext()) {
                        PointcutDesignatorHandler pcd = (PointcutDesignatorHandler)i$.next();
                        if (pcd.getDesignatorName().equals(kind)) {
                           p = this.parseDesignatorPointcut(pcd);
                           matchedByExtensionDesignator = true;
                        }
                     }

                     if (!matchedByExtensionDesignator) {
                        this.tokenSource.setIndex(start);
                        p = this.parseReferencePointcut();
                     }
                  }
               } else {
                  p = this.parseMonitorPointcut(kind);
               }
            }
         } else {
            p = this.parseKindedPointcut(kind);
         }

         return (Pointcut)p;
      }
   }

   private void assertNoTypeVariables(String[] tvs, String errorMessage, IToken token) {
      if (tvs != null) {
         throw new ParserException(errorMessage, token);
      }
   }

   public Pointcut parseAnnotationPointcut() {
      int start = this.tokenSource.getIndex();
      IToken t = this.tokenSource.peek();
      String kind = this.parseIdentifier();
      IToken possibleTypeVariableToken = this.tokenSource.peek();
      String[] typeVariables = this.maybeParseSimpleTypeVariableList();
      if (typeVariables != null) {
         String message = "(";
         this.assertNoTypeVariables(typeVariables, message, possibleTypeVariableToken);
      }

      this.tokenSource.setIndex(start);
      if (kind.equals("annotation")) {
         return this.parseAtAnnotationPointcut();
      } else if (kind.equals("args")) {
         return this.parseArgsAnnotationPointcut();
      } else if (!kind.equals("this") && !kind.equals("target")) {
         if (kind.equals("within")) {
            return this.parseWithinAnnotationPointcut();
         } else if (kind.equals("withincode")) {
            return this.parseWithinCodeAnnotationPointcut();
         } else {
            throw new ParserException("pointcut name", t);
         }
      } else {
         return this.parseThisOrTargetAnnotationPointcut();
      }
   }

   private Pointcut parseAtAnnotationPointcut() {
      this.parseIdentifier();
      this.eat("(");
      if (this.maybeEat(")")) {
         throw new ParserException("@AnnotationName or parameter", this.tokenSource.peek());
      } else {
         ExactAnnotationTypePattern type = this.parseAnnotationNameOrVarTypePattern();
         this.eat(")");
         return new AnnotationPointcut(type);
      }
   }

   private SignaturePattern parseConstructorSignaturePattern() {
      SignaturePattern ret = this.parseMethodOrConstructorSignaturePattern();
      if (ret.getKind() == Member.CONSTRUCTOR) {
         return ret;
      } else {
         throw new ParserException("constructor pattern required, found method pattern", ret);
      }
   }

   private Pointcut parseWithinCodePointcut() {
      this.eat("(");
      SignaturePattern sig = this.parseMethodOrConstructorSignaturePattern();
      this.eat(")");
      return new WithincodePointcut(sig);
   }

   private Pointcut parseCflowPointcut(boolean isBelow) {
      this.eat("(");
      Pointcut entry = this.parsePointcut();
      this.eat(")");
      return new CflowPointcut(entry, isBelow, (int[])null);
   }

   private Pointcut parseWithinPointcut() {
      this.eat("(");
      TypePattern type = this.parseTypePattern();
      this.eat(")");
      return new WithinPointcut(type);
   }

   private Pointcut parseThisOrTargetPointcut(String kind) {
      this.eat("(");
      TypePattern type = this.parseTypePattern();
      this.eat(")");
      return new ThisOrTargetPointcut(kind.equals("this"), type);
   }

   private Pointcut parseThisOrTargetAnnotationPointcut() {
      String kind = this.parseIdentifier();
      this.eat("(");
      if (this.maybeEat(")")) {
         throw new ParserException("expecting @AnnotationName or parameter, but found ')'", this.tokenSource.peek());
      } else {
         ExactAnnotationTypePattern type = this.parseAnnotationNameOrVarTypePattern();
         this.eat(")");
         return new ThisOrTargetAnnotationPointcut(kind.equals("this"), type);
      }
   }

   private Pointcut parseWithinAnnotationPointcut() {
      this.parseIdentifier();
      this.eat("(");
      if (this.maybeEat(")")) {
         throw new ParserException("expecting @AnnotationName or parameter, but found ')'", this.tokenSource.peek());
      } else {
         AnnotationTypePattern type = this.parseAnnotationNameOrVarTypePattern();
         this.eat(")");
         return new WithinAnnotationPointcut(type);
      }
   }

   private Pointcut parseWithinCodeAnnotationPointcut() {
      this.parseIdentifier();
      this.eat("(");
      if (this.maybeEat(")")) {
         throw new ParserException("expecting @AnnotationName or parameter, but found ')'", this.tokenSource.peek());
      } else {
         ExactAnnotationTypePattern type = this.parseAnnotationNameOrVarTypePattern();
         this.eat(")");
         return new WithinCodeAnnotationPointcut(type);
      }
   }

   private Pointcut parseArgsPointcut() {
      TypePatternList arguments = this.parseArgumentsPattern(false);
      return new ArgsPointcut(arguments);
   }

   private Pointcut parseArgsAnnotationPointcut() {
      this.parseIdentifier();
      AnnotationPatternList arguments = this.parseArgumentsAnnotationPattern();
      return new ArgsAnnotationPointcut(arguments);
   }

   private Pointcut parseReferencePointcut() {
      TypePattern onType = this.parseTypePattern();
      NamePattern name = null;
      if (onType.typeParameters.size() > 0) {
         this.eat(".");
         name = this.parseNamePattern();
      } else {
         name = this.tryToExtractName(onType);
      }

      if (name == null) {
         throw new ParserException("name pattern", this.tokenSource.peek());
      } else {
         if (onType.toString().equals("")) {
            onType = null;
         }

         String simpleName = name.maybeGetSimpleName();
         if (simpleName == null) {
            throw new ParserException("(", this.tokenSource.peek(-1));
         } else {
            TypePatternList arguments = this.parseArgumentsPattern(false);
            return new ReferencePointcut(onType, simpleName, arguments);
         }
      }
   }

   private Pointcut parseDesignatorPointcut(PointcutDesignatorHandler pcdHandler) {
      this.eat("(");
      int parenCount = 1;
      StringBuffer pointcutBody = new StringBuffer();

      while(parenCount > 0) {
         if (this.maybeEat("(")) {
            ++parenCount;
            pointcutBody.append("(");
         } else if (this.maybeEat(")")) {
            --parenCount;
            if (parenCount > 0) {
               pointcutBody.append(")");
            }
         } else {
            pointcutBody.append(this.nextToken().getString());
         }
      }

      ContextBasedMatcher pcExpr = pcdHandler.parse(pointcutBody.toString());
      return new PointcutDesignatorHandlerBasedPointcut(pcExpr, this.world);
   }

   public List parseDottedIdentifier() {
      List ret = new ArrayList();
      ret.add(this.parseIdentifier());

      while(this.maybeEat(".")) {
         ret.add(this.parseIdentifier());
      }

      return ret;
   }

   private KindedPointcut parseKindedPointcut(String kind) {
      this.eat("(");
      Shadow.Kind shadowKind = null;
      SignaturePattern sig;
      if (kind.equals("execution")) {
         sig = this.parseMethodOrConstructorSignaturePattern();
         if (sig.getKind() == Member.METHOD) {
            shadowKind = Shadow.MethodExecution;
         } else if (sig.getKind() == Member.CONSTRUCTOR) {
            shadowKind = Shadow.ConstructorExecution;
         }
      } else if (kind.equals("call")) {
         sig = this.parseMethodOrConstructorSignaturePattern();
         if (sig.getKind() == Member.METHOD) {
            shadowKind = Shadow.MethodCall;
         } else if (sig.getKind() == Member.CONSTRUCTOR) {
            shadowKind = Shadow.ConstructorCall;
         }
      } else if (kind.equals("get")) {
         sig = this.parseFieldSignaturePattern();
         shadowKind = Shadow.FieldGet;
      } else {
         if (!kind.equals("set")) {
            throw new ParserException("bad kind: " + kind, this.tokenSource.peek());
         }

         sig = this.parseFieldSignaturePattern();
         shadowKind = Shadow.FieldSet;
      }

      this.eat(")");
      return new KindedPointcut(shadowKind, sig);
   }

   private KindedPointcut parseMonitorPointcut(String kind) {
      this.eat("(");
      this.eat(")");
      return kind.equals("lock") ? new KindedPointcut(Shadow.SynchronizationLock, new SignaturePattern(Member.MONITORENTER, ModifiersPattern.ANY, TypePattern.ANY, TypePattern.ANY, NamePattern.ANY, TypePatternList.ANY, ThrowsPattern.ANY, AnnotationTypePattern.ANY)) : new KindedPointcut(Shadow.SynchronizationUnlock, new SignaturePattern(Member.MONITORENTER, ModifiersPattern.ANY, TypePattern.ANY, TypePattern.ANY, NamePattern.ANY, TypePatternList.ANY, ThrowsPattern.ANY, AnnotationTypePattern.ANY));
   }

   public TypePattern parseTypePattern() {
      return this.parseTypePattern(false, false);
   }

   public TypePattern parseTypePattern(boolean insideTypeParameters, boolean parameterAnnotationsPossible) {
      TypePattern p = this.parseAtomicTypePattern(insideTypeParameters, parameterAnnotationsPossible);
      if (this.maybeEat("&&")) {
         p = new AndTypePattern((TypePattern)p, this.parseNotOrTypePattern(insideTypeParameters, parameterAnnotationsPossible));
      }

      if (this.maybeEat("||")) {
         p = new OrTypePattern((TypePattern)p, this.parseTypePattern(insideTypeParameters, parameterAnnotationsPossible));
      }

      return (TypePattern)p;
   }

   private TypePattern parseNotOrTypePattern(boolean insideTypeParameters, boolean parameterAnnotationsPossible) {
      TypePattern p = this.parseAtomicTypePattern(insideTypeParameters, parameterAnnotationsPossible);
      if (this.maybeEat("&&")) {
         p = new AndTypePattern((TypePattern)p, this.parseTypePattern(insideTypeParameters, parameterAnnotationsPossible));
      }

      return (TypePattern)p;
   }

   private TypePattern parseAtomicTypePattern(boolean insideTypeParameters, boolean parameterAnnotationsPossible) {
      AnnotationTypePattern ap = this.maybeParseAnnotationPattern();
      TypePattern p;
      if (this.maybeEat("!")) {
         TypePattern p = null;
         p = this.parseAtomicTypePattern(insideTypeParameters, parameterAnnotationsPossible);
         Object p;
         if (!(ap instanceof AnyAnnotationTypePattern)) {
            p = new NotTypePattern(p);
            p = new AndTypePattern(this.setAnnotationPatternForTypePattern(TypePattern.ANY, ap, false), p);
         } else {
            p = new NotTypePattern(p);
         }

         return (TypePattern)p;
      } else {
         int openParenPos;
         int closeParenPos;
         if (!this.maybeEat("(")) {
            openParenPos = this.tokenSource.peek().getStart();
            if (ap.start != -1) {
               openParenPos = ap.start;
            }

            p = this.parseSingleTypePattern(insideTypeParameters);
            closeParenPos = this.tokenSource.peek(-1).getEnd();
            p = this.setAnnotationPatternForTypePattern(p, ap, false);
            p.setLocation(this.sourceContext, openParenPos, closeParenPos);
            return p;
         } else {
            openParenPos = this.tokenSource.peek(-1).getStart();
            p = this.parseTypePattern(insideTypeParameters, false);
            Object p;
            if (p instanceof NotTypePattern && !(ap instanceof AnyAnnotationTypePattern)) {
               TypePattern tp = this.setAnnotationPatternForTypePattern(TypePattern.ANY, ap, parameterAnnotationsPossible);
               p = new AndTypePattern(tp, p);
            } else {
               p = this.setAnnotationPatternForTypePattern(p, ap, parameterAnnotationsPossible);
            }

            this.eat(")");
            closeParenPos = this.tokenSource.peek(-1).getStart();
            boolean isVarArgs = this.maybeEat("...");
            if (isVarArgs) {
               ((TypePattern)p).setIsVarArgs(isVarArgs);
            }

            boolean isIncludeSubtypes = this.maybeEat("+");
            if (isIncludeSubtypes) {
               ((TypePattern)p).includeSubtypes = true;
            }

            ((TypePattern)p).start = openParenPos;
            ((TypePattern)p).end = closeParenPos;
            return (TypePattern)p;
         }
      }
   }

   private TypePattern setAnnotationPatternForTypePattern(TypePattern t, AnnotationTypePattern ap, boolean parameterAnnotationsPattern) {
      if (parameterAnnotationsPattern) {
         ap.setForParameterAnnotationMatch();
      }

      if (ap != AnnotationTypePattern.ANY) {
         if (t == TypePattern.ANY) {
            if (t.annotationPattern == AnnotationTypePattern.ANY) {
               return new AnyWithAnnotationTypePattern(ap);
            }

            return new AnyWithAnnotationTypePattern(new AndAnnotationTypePattern(ap, t.annotationPattern));
         }

         if (t.annotationPattern == AnnotationTypePattern.ANY) {
            t.setAnnotationTypePattern(ap);
         } else {
            t.setAnnotationTypePattern(new AndAnnotationTypePattern(ap, t.annotationPattern));
         }
      }

      return t;
   }

   public AnnotationTypePattern maybeParseAnnotationPattern() {
      AnnotationTypePattern ret = AnnotationTypePattern.ANY;
      AnnotationTypePattern nextPattern = null;

      while((nextPattern = this.maybeParseSingleAnnotationPattern()) != null) {
         if (ret == AnnotationTypePattern.ANY) {
            ret = nextPattern;
         } else {
            ret = new AndAnnotationTypePattern((AnnotationTypePattern)ret, nextPattern);
         }
      }

      return (AnnotationTypePattern)ret;
   }

   public AnnotationTypePattern maybeParseSingleAnnotationPattern() {
      AnnotationTypePattern ret = null;
      Map values = null;
      int startIndex = this.tokenSource.getIndex();
      TypePattern p;
      if (this.maybeEat("!")) {
         if (this.maybeEat("@")) {
            NotAnnotationTypePattern ret;
            if (this.maybeEat("(")) {
               p = this.parseTypePattern();
               ret = new NotAnnotationTypePattern(new WildAnnotationTypePattern(p));
               this.eat(")");
               return ret;
            } else {
               p = this.parseSingleTypePattern();
               if (this.maybeEatAdjacent("(")) {
                  values = this.parseAnnotationValues();
                  this.eat(")");
                  ret = new NotAnnotationTypePattern(new WildAnnotationTypePattern(p, values));
               } else {
                  ret = new NotAnnotationTypePattern(new WildAnnotationTypePattern(p));
               }

               return ret;
            }
         } else {
            this.tokenSource.setIndex(startIndex);
            return ret;
         }
      } else if (this.maybeEat("@")) {
         if (this.maybeEat("(")) {
            p = this.parseTypePattern();
            ret = new WildAnnotationTypePattern(p);
            this.eat(")");
            return ret;
         } else {
            int atPos = this.tokenSource.peek(-1).getStart();
            TypePattern p = this.parseSingleTypePattern();
            if (this.maybeEatAdjacent("(")) {
               values = this.parseAnnotationValues();
               this.eat(")");
               ret = new WildAnnotationTypePattern(p, values);
            } else {
               ret = new WildAnnotationTypePattern(p);
            }

            ret.start = atPos;
            return ret;
         }
      } else {
         this.tokenSource.setIndex(startIndex);
         return ret;
      }
   }

   public Map parseAnnotationValues() {
      Map values = new HashMap();
      boolean seenDefaultValue = false;

      do {
         String possibleKeyString = this.parseAnnotationNameValuePattern();
         if (possibleKeyString == null) {
            throw new ParserException("expecting simple literal ", this.tokenSource.peek(-1));
         }

         String valueString;
         if (this.maybeEat("=")) {
            valueString = this.parseAnnotationNameValuePattern();
            if (valueString == null) {
               throw new ParserException("expecting simple literal ", this.tokenSource.peek(-1));
            }

            values.put(possibleKeyString, valueString);
         } else if (this.maybeEat("!=")) {
            valueString = this.parseAnnotationNameValuePattern();
            if (valueString == null) {
               throw new ParserException("expecting simple literal ", this.tokenSource.peek(-1));
            }

            values.put(possibleKeyString + "!", valueString);
         } else {
            if (seenDefaultValue) {
               throw new ParserException("cannot specify two default values", this.tokenSource.peek(-1));
            }

            seenDefaultValue = true;
            values.put("value", possibleKeyString);
         }
      } while(this.maybeEat(","));

      return values;
   }

   public TypePattern parseSingleTypePattern() {
      return this.parseSingleTypePattern(false);
   }

   public TypePattern parseSingleTypePattern(boolean insideTypeParameters) {
      if (insideTypeParameters && this.maybeEat("?")) {
         return this.parseGenericsWildcardTypePattern();
      } else {
         if (this.allowHasTypePatterns) {
            if (this.maybeEatIdentifier("hasmethod")) {
               return this.parseHasMethodTypePattern();
            }

            if (this.maybeEatIdentifier("hasfield")) {
               return this.parseHasFieldTypePattern();
            }
         }

         if (this.maybeEatIdentifier("is")) {
            int pos = this.tokenSource.getIndex() - 1;
            TypePattern typeIsPattern = this.parseIsTypePattern();
            if (typeIsPattern != null) {
               return typeIsPattern;
            }

            this.tokenSource.setIndex(pos);
         }

         List names = this.parseDottedNamePattern();

         int dim;
         for(dim = 0; this.maybeEat("["); ++dim) {
            this.eat("]");
         }

         TypePatternList typeParameters = this.maybeParseTypeParameterList();
         int endPos = this.tokenSource.peek(-1).getEnd();

         boolean includeSubtypes;
         for(includeSubtypes = this.maybeEat("+"); this.maybeEat("["); ++dim) {
            this.eat("]");
         }

         boolean isVarArgs = this.maybeEat("...");
         return (TypePattern)(names.size() == 1 && ((NamePattern)names.get(0)).isAny() && dim == 0 && !isVarArgs && typeParameters == null ? TypePattern.ANY : new WildTypePattern(names, includeSubtypes, dim + (isVarArgs ? 1 : 0), endPos, isVarArgs, typeParameters));
      }
   }

   public TypePattern parseHasMethodTypePattern() {
      int startPos = this.tokenSource.peek(-1).getStart();
      this.eat("(");
      SignaturePattern sp = this.parseMethodOrConstructorSignaturePattern();
      this.eat(")");
      int endPos = this.tokenSource.peek(-1).getEnd();
      HasMemberTypePattern ret = new HasMemberTypePattern(sp);
      ret.setLocation(this.sourceContext, startPos, endPos);
      return ret;
   }

   public TypePattern parseIsTypePattern() {
      int startPos = this.tokenSource.peek(-1).getStart();
      if (!this.maybeEatAdjacent("(")) {
         return null;
      } else {
         IToken token = this.tokenSource.next();
         TypeCategoryTypePattern typeIsPattern = null;
         if (token.isIdentifier()) {
            String category = token.getString();
            if (category.equals("ClassType")) {
               typeIsPattern = new TypeCategoryTypePattern(1);
            } else if (category.equals("AspectType")) {
               typeIsPattern = new TypeCategoryTypePattern(3);
            } else if (category.equals("InterfaceType")) {
               typeIsPattern = new TypeCategoryTypePattern(2);
            } else if (category.equals("InnerType")) {
               typeIsPattern = new TypeCategoryTypePattern(4);
            } else if (category.equals("AnonymousType")) {
               typeIsPattern = new TypeCategoryTypePattern(5);
            } else if (category.equals("EnumType")) {
               typeIsPattern = new TypeCategoryTypePattern(6);
            } else if (category.equals("AnnotationType")) {
               typeIsPattern = new TypeCategoryTypePattern(7);
            } else if (category.equals("FinalType")) {
               typeIsPattern = new TypeCategoryTypePattern(8);
            }
         }

         if (typeIsPattern == null) {
            return null;
         } else if (!this.maybeEat(")")) {
            throw new ParserException(")", this.tokenSource.peek());
         } else {
            int endPos = this.tokenSource.peek(-1).getEnd();
            typeIsPattern.setLocation(this.tokenSource.getSourceContext(), startPos, endPos);
            return typeIsPattern;
         }
      }
   }

   public TypePattern parseHasFieldTypePattern() {
      int startPos = this.tokenSource.peek(-1).getStart();
      this.eat("(");
      SignaturePattern sp = this.parseFieldSignaturePattern();
      this.eat(")");
      int endPos = this.tokenSource.peek(-1).getEnd();
      HasMemberTypePattern ret = new HasMemberTypePattern(sp);
      ret.setLocation(this.sourceContext, startPos, endPos);
      return ret;
   }

   public TypePattern parseGenericsWildcardTypePattern() {
      List names = new ArrayList();
      names.add(new NamePattern("?"));
      TypePattern upperBound = null;
      TypePattern[] additionalInterfaceBounds = new TypePattern[0];
      TypePattern lowerBound = null;
      if (this.maybeEatIdentifier("extends")) {
         upperBound = this.parseTypePattern(false, false);
         additionalInterfaceBounds = this.maybeParseAdditionalInterfaceBounds();
      }

      if (this.maybeEatIdentifier("super")) {
         lowerBound = this.parseTypePattern(false, false);
      }

      int endPos = this.tokenSource.peek(-1).getEnd();
      return new WildTypePattern(names, false, 0, endPos, false, (TypePatternList)null, upperBound, additionalInterfaceBounds, lowerBound);
   }

   protected ExactAnnotationTypePattern parseAnnotationNameOrVarTypePattern() {
      ExactAnnotationTypePattern p = null;
      int startPos = this.tokenSource.peek().getStart();
      if (this.maybeEat("@")) {
         throw new ParserException("@Foo form was deprecated in AspectJ 5 M2: annotation name or var ", this.tokenSource.peek(-1));
      } else {
         p = this.parseSimpleAnnotationName();
         int endPos = this.tokenSource.peek(-1).getEnd();
         ((ExactAnnotationTypePattern)p).setLocation(this.sourceContext, startPos, endPos);
         if (this.maybeEat("(")) {
            String formalName = this.parseIdentifier();
            p = new ExactAnnotationFieldTypePattern((ExactAnnotationTypePattern)p, formalName);
            this.eat(")");
         }

         return (ExactAnnotationTypePattern)p;
      }
   }

   private ExactAnnotationTypePattern parseSimpleAnnotationName() {
      StringBuffer annotationName = new StringBuffer();
      annotationName.append(this.parseIdentifier());

      while(this.maybeEat(".")) {
         annotationName.append('.');
         annotationName.append(this.parseIdentifier());
      }

      UnresolvedType type = UnresolvedType.forName(annotationName.toString());
      ExactAnnotationTypePattern p = new ExactAnnotationTypePattern(type, (Map)null);
      return p;
   }

   public List parseDottedNamePattern() {
      List names = new ArrayList();
      StringBuffer buf = new StringBuffer();
      IToken previous = null;
      boolean justProcessedEllipsis = false;
      boolean justProcessedDot = false;
      boolean onADot = false;

      while(true) {
         IToken tok = null;
         int startPos = this.tokenSource.peek().getStart();
         String afterDot = null;

         while(true) {
            if (previous != null && previous.getString().equals(".")) {
               justProcessedDot = true;
            }

            tok = this.tokenSource.peek();
            onADot = tok.getString().equals(".");
            if (previous != null && !this.isAdjacent(previous, tok)) {
               break;
            }

            if (tok.getString() == "*" || tok.isIdentifier() && tok.getString() != "...") {
               buf.append(tok.getString());
            } else {
               if (tok.getString() == "..." || tok.getLiteralKind() == null) {
                  break;
               }

               String s = tok.getString();
               int dot = s.indexOf(46);
               if (dot != -1) {
                  buf.append(s.substring(0, dot));
                  afterDot = s.substring(dot + 1);
                  previous = this.tokenSource.next();
                  break;
               }

               buf.append(s);
            }

            previous = this.tokenSource.next();
         }

         int endPos = this.tokenSource.peek(-1).getEnd();
         if (buf.length() == 0 && names.isEmpty()) {
            throw new ParserException("name pattern", tok);
         }

         if (buf.length() == 0 && justProcessedEllipsis) {
            throw new ParserException("name pattern cannot finish with ..", tok);
         }

         if (buf.length() == 0 && justProcessedDot && !onADot) {
            throw new ParserException("name pattern cannot finish with .", tok);
         }

         if (buf.length() == 0) {
            names.add(NamePattern.ELLIPSIS);
            justProcessedEllipsis = true;
         } else {
            this.checkLegalName(buf.toString(), previous);
            NamePattern ret = new NamePattern(buf.toString());
            ret.setLocation(this.sourceContext, startPos, endPos);
            names.add(ret);
            justProcessedEllipsis = false;
         }

         if (afterDot == null) {
            buf.setLength(0);
            if (!this.maybeEat(".")) {
               return names;
            }

            previous = this.tokenSource.peek(-1);
         } else {
            buf.setLength(0);
            buf.append(afterDot);
            afterDot = null;
         }
      }
   }

   public String parseAnnotationNameValuePattern() {
      StringBuffer buf = new StringBuffer();
      this.tokenSource.peek().getStart();
      boolean dotOK = false;
      int depth = 0;

      while(true) {
         IToken tok = this.tokenSource.peek();
         if (tok.getString() == ")" && depth == 0 || tok.getString() == "!=" && depth == 0 || tok.getString() == "=" && depth == 0 || tok.getString() == "," && depth == 0) {
            return buf.toString();
         }

         if (tok == IToken.EOF) {
            throw new ParserException("eof", this.tokenSource.peek());
         }

         if (tok.getString() == "(") {
            ++depth;
         }

         if (tok.getString() == ")") {
            --depth;
         }

         if (tok.getString() == "{") {
            ++depth;
         }

         if (tok.getString() == "}") {
            --depth;
         }

         if (tok.getString() == "." && !dotOK) {
            throw new ParserException("dot not expected", tok);
         }

         buf.append(tok.getString());
         this.tokenSource.next();
         dotOK = true;
      }
   }

   public NamePattern parseNamePattern() {
      StringBuffer buf = new StringBuffer();
      IToken previous = null;
      int startPos = this.tokenSource.peek().getStart();

      IToken tok;
      while(true) {
         tok = this.tokenSource.peek();
         if (previous != null && !this.isAdjacent(previous, tok)) {
            break;
         }

         if (tok.getString() != "*" && !tok.isIdentifier()) {
            if (tok.getLiteralKind() == null) {
               break;
            }

            String s = tok.getString();
            if (s.indexOf(46) != -1) {
               break;
            }

            buf.append(s);
         } else {
            buf.append(tok.getString());
         }

         previous = this.tokenSource.next();
      }

      int endPos = this.tokenSource.peek(-1).getEnd();
      if (buf.length() == 0) {
         throw new ParserException("name pattern", tok);
      } else {
         this.checkLegalName(buf.toString(), previous);
         NamePattern ret = new NamePattern(buf.toString());
         ret.setLocation(this.sourceContext, startPos, endPos);
         return ret;
      }
   }

   private void checkLegalName(String s, IToken tok) {
      char ch = s.charAt(0);
      if (ch != '*' && !Character.isJavaIdentifierStart(ch)) {
         throw new ParserException("illegal identifier start (" + ch + ")", tok);
      } else {
         int i = 1;

         for(int len = s.length(); i < len; ++i) {
            ch = s.charAt(i);
            if (ch != '*' && !Character.isJavaIdentifierPart(ch)) {
               throw new ParserException("illegal identifier character (" + ch + ")", tok);
            }
         }

      }
   }

   private boolean isAdjacent(IToken first, IToken second) {
      return first.getEnd() == second.getStart() - 1;
   }

   public ModifiersPattern parseModifiersPattern() {
      int requiredFlags = 0;
      int forbiddenFlags = 0;

      while(true) {
         int start = this.tokenSource.getIndex();
         boolean isForbidden = false;
         isForbidden = this.maybeEat("!");
         IToken t = this.tokenSource.next();
         int flag = ModifiersPattern.getModifierFlag(t.getString());
         if (flag == -1) {
            this.tokenSource.setIndex(start);
            if (requiredFlags == 0 && forbiddenFlags == 0) {
               return ModifiersPattern.ANY;
            }

            return new ModifiersPattern(requiredFlags, forbiddenFlags);
         }

         if (isForbidden) {
            forbiddenFlags |= flag;
         } else {
            requiredFlags |= flag;
         }
      }
   }

   public TypePatternList parseArgumentsPattern(boolean parameterAnnotationsPossible) {
      List patterns = new ArrayList();
      this.eat("(");
      if (this.maybeEat(")")) {
         return new TypePatternList();
      } else {
         do {
            if (this.maybeEat(".")) {
               this.eat(".");
               patterns.add(TypePattern.ELLIPSIS);
            } else {
               patterns.add(this.parseTypePattern(false, parameterAnnotationsPossible));
            }
         } while(this.maybeEat(","));

         this.eat(")");
         return new TypePatternList(patterns);
      }
   }

   public AnnotationPatternList parseArgumentsAnnotationPattern() {
      List patterns = new ArrayList();
      this.eat("(");
      if (this.maybeEat(")")) {
         return new AnnotationPatternList();
      } else {
         do {
            if (this.maybeEat(".")) {
               this.eat(".");
               patterns.add(AnnotationTypePattern.ELLIPSIS);
            } else if (this.maybeEat("*")) {
               patterns.add(AnnotationTypePattern.ANY);
            } else {
               patterns.add(this.parseAnnotationNameOrVarTypePattern());
            }
         } while(this.maybeEat(","));

         this.eat(")");
         return new AnnotationPatternList(patterns);
      }
   }

   public ThrowsPattern parseOptionalThrowsPattern() {
      IToken t = this.tokenSource.peek();
      if (t.isIdentifier() && t.getString().equals("throws")) {
         this.tokenSource.next();
         List required = new ArrayList();
         List forbidden = new ArrayList();

         do {
            boolean isForbidden = this.maybeEat("!");
            TypePattern p = this.parseTypePattern();
            if (isForbidden) {
               forbidden.add(p);
            } else {
               required.add(p);
            }
         } while(this.maybeEat(","));

         return new ThrowsPattern(new TypePatternList(required), new TypePatternList(forbidden));
      } else {
         return ThrowsPattern.ANY;
      }
   }

   public SignaturePattern parseMethodOrConstructorSignaturePattern() {
      int startPos = this.tokenSource.peek().getStart();
      AnnotationTypePattern annotationPattern = this.maybeParseAnnotationPattern();
      ModifiersPattern modifiers = this.parseModifiersPattern();
      TypePattern returnType = this.parseTypePattern(false, false);
      NamePattern name = null;
      MemberKind kind;
      TypePattern declaringType;
      if (this.maybeEatNew(returnType)) {
         kind = Member.CONSTRUCTOR;
         if (returnType.toString().length() == 0) {
            declaringType = TypePattern.ANY;
         } else {
            declaringType = returnType;
         }

         returnType = TypePattern.ANY;
         name = NamePattern.ANY;
      } else {
         kind = Member.METHOD;
         IToken nameToken = this.tokenSource.peek();
         declaringType = this.parseTypePattern(false, false);
         if (this.maybeEat(".")) {
            nameToken = this.tokenSource.peek();
            name = this.parseNamePattern();
         } else {
            name = this.tryToExtractName(declaringType);
            if (declaringType.toString().equals("")) {
               declaringType = TypePattern.ANY;
            }
         }

         if (name == null) {
            throw new ParserException("name pattern", this.tokenSource.peek());
         }

         String simpleName = name.maybeGetSimpleName();
         if (simpleName != null && simpleName.equals("new")) {
            throw new ParserException("method name (not constructor)", nameToken);
         }
      }

      TypePatternList parameterTypes = this.parseArgumentsPattern(true);
      ThrowsPattern throwsPattern = this.parseOptionalThrowsPattern();
      SignaturePattern ret = new SignaturePattern(kind, modifiers, returnType, declaringType, name, parameterTypes, throwsPattern, annotationPattern);
      int endPos = this.tokenSource.peek(-1).getEnd();
      ret.setLocation(this.sourceContext, startPos, endPos);
      return ret;
   }

   private boolean maybeEatNew(TypePattern returnType) {
      if (returnType instanceof WildTypePattern) {
         WildTypePattern p = (WildTypePattern)returnType;
         if (p.maybeExtractName("new")) {
            return true;
         }
      }

      int start = this.tokenSource.getIndex();
      if (this.maybeEat(".")) {
         String id = this.maybeEatIdentifier();
         if (id != null && id.equals("new")) {
            return true;
         }

         this.tokenSource.setIndex(start);
      }

      return false;
   }

   public ISignaturePattern parseMaybeParenthesizedFieldSignaturePattern() {
      boolean negated = this.tokenSource.peek().getString().equals("!") && this.tokenSource.peek(1).getString().equals("(");
      if (negated) {
         this.eat("!");
      }

      ISignaturePattern result = null;
      if (this.maybeEat("(")) {
         result = this.parseCompoundFieldSignaturePattern();
         this.eat(")", "missing ')' - unbalanced parentheses around field signature pattern in declare @field");
         if (negated) {
            result = new NotSignaturePattern((ISignaturePattern)result);
         }
      } else {
         result = this.parseFieldSignaturePattern();
      }

      return (ISignaturePattern)result;
   }

   public ISignaturePattern parseMaybeParenthesizedMethodOrConstructorSignaturePattern(boolean isMethod) {
      boolean negated = this.tokenSource.peek().getString().equals("!") && this.tokenSource.peek(1).getString().equals("(");
      if (negated) {
         this.eat("!");
      }

      ISignaturePattern result = null;
      if (this.maybeEat("(")) {
         result = this.parseCompoundMethodOrConstructorSignaturePattern(isMethod);
         this.eat(")", "missing ')' - unbalanced parentheses around method/ctor signature pattern in declare annotation");
         if (negated) {
            result = new NotSignaturePattern((ISignaturePattern)result);
         }
      } else {
         SignaturePattern sp = this.parseMethodOrConstructorSignaturePattern();
         boolean isConstructorPattern = sp.getKind() == Member.CONSTRUCTOR;
         if (isMethod && isConstructorPattern) {
            throw new ParserException("method signature pattern", this.tokenSource.peek(-1));
         }

         if (!isMethod && !isConstructorPattern) {
            throw new ParserException("constructor signature pattern", this.tokenSource.peek(-1));
         }

         result = sp;
      }

      return (ISignaturePattern)result;
   }

   public SignaturePattern parseFieldSignaturePattern() {
      int startPos = this.tokenSource.peek().getStart();
      AnnotationTypePattern annotationPattern = this.maybeParseAnnotationPattern();
      ModifiersPattern modifiers = this.parseModifiersPattern();
      TypePattern returnType = this.parseTypePattern();
      TypePattern declaringType = this.parseTypePattern();
      NamePattern name;
      if (this.maybeEat(".")) {
         name = this.parseNamePattern();
      } else {
         name = this.tryToExtractName(declaringType);
         if (name == null) {
            throw new ParserException("name pattern", this.tokenSource.peek());
         }

         if (declaringType.toString().equals("")) {
            declaringType = TypePattern.ANY;
         }
      }

      SignaturePattern ret = new SignaturePattern(Member.FIELD, modifiers, returnType, declaringType, name, TypePatternList.ANY, ThrowsPattern.ANY, annotationPattern);
      int endPos = this.tokenSource.peek(-1).getEnd();
      ret.setLocation(this.sourceContext, startPos, endPos);
      return ret;
   }

   private NamePattern tryToExtractName(TypePattern nextType) {
      if (nextType == TypePattern.ANY) {
         return NamePattern.ANY;
      } else if (nextType instanceof WildTypePattern) {
         WildTypePattern p = (WildTypePattern)nextType;
         return p.extractName();
      } else {
         return null;
      }
   }

   public TypeVariablePatternList maybeParseTypeVariableList() {
      if (!this.maybeEat("<")) {
         return null;
      } else {
         List typeVars = new ArrayList();
         TypeVariablePattern t = this.parseTypeVariable();
         typeVars.add(t);

         while(this.maybeEat(",")) {
            TypeVariablePattern nextT = this.parseTypeVariable();
            typeVars.add(nextT);
         }

         this.eat(">");
         TypeVariablePattern[] tvs = new TypeVariablePattern[typeVars.size()];
         typeVars.toArray(tvs);
         return new TypeVariablePatternList(tvs);
      }
   }

   public String[] maybeParseSimpleTypeVariableList() {
      if (!this.maybeEat("<")) {
         return null;
      } else {
         List typeVarNames = new ArrayList();

         do {
            typeVarNames.add(this.parseIdentifier());
         } while(this.maybeEat(","));

         this.eat(">", "',' or '>'");
         String[] tvs = new String[typeVarNames.size()];
         typeVarNames.toArray(tvs);
         return tvs;
      }
   }

   public TypePatternList maybeParseTypeParameterList() {
      if (!this.maybeEat("<")) {
         return null;
      } else {
         List typePats = new ArrayList();

         do {
            TypePattern tp = this.parseTypePattern(true, false);
            typePats.add(tp);
         } while(this.maybeEat(","));

         this.eat(">");
         TypePattern[] tps = new TypePattern[typePats.size()];
         typePats.toArray(tps);
         return new TypePatternList(tps);
      }
   }

   public TypeVariablePattern parseTypeVariable() {
      TypePattern upperBound = null;
      TypePattern[] additionalInterfaceBounds = null;
      TypePattern lowerBound = null;
      String typeVariableName = this.parseIdentifier();
      if (this.maybeEatIdentifier("extends")) {
         upperBound = this.parseTypePattern();
         additionalInterfaceBounds = this.maybeParseAdditionalInterfaceBounds();
      } else if (this.maybeEatIdentifier("super")) {
         lowerBound = this.parseTypePattern();
      }

      return new TypeVariablePattern(typeVariableName, upperBound, additionalInterfaceBounds, lowerBound);
   }

   private TypePattern[] maybeParseAdditionalInterfaceBounds() {
      List boundsList = new ArrayList();

      while(this.maybeEat("&")) {
         TypePattern tp = this.parseTypePattern();
         boundsList.add(tp);
      }

      if (boundsList.size() == 0) {
         return null;
      } else {
         TypePattern[] ret = new TypePattern[boundsList.size()];
         boundsList.toArray(ret);
         return ret;
      }
   }

   public String parsePossibleStringSequence(boolean shouldEnd) {
      StringBuffer result = new StringBuffer();
      IToken token = this.tokenSource.next();
      if (token.getLiteralKind() == null) {
         throw new ParserException("string", token);
      } else {
         while(token.getLiteralKind().equals("string")) {
            result.append(token.getString());
            boolean plus = this.maybeEat("+");
            if (!plus) {
               break;
            }

            token = this.tokenSource.next();
            if (token.getLiteralKind() == null) {
               throw new ParserException("string", token);
            }
         }

         this.eatIdentifier(";");
         IToken t = this.tokenSource.next();
         if (shouldEnd && t != IToken.EOF) {
            throw new ParserException("<string>;", token);
         } else {
            int currentIndex = this.tokenSource.getIndex();
            this.tokenSource.setIndex(currentIndex - 1);
            return result.toString();
         }
      }
   }

   public String parseStringLiteral() {
      IToken token = this.tokenSource.next();
      String literalKind = token.getLiteralKind();
      if (literalKind == "string") {
         return token.getString();
      } else {
         throw new ParserException("string", token);
      }
   }

   public String parseIdentifier() {
      IToken token = this.tokenSource.next();
      if (token.isIdentifier()) {
         return token.getString();
      } else {
         throw new ParserException("identifier", token);
      }
   }

   public void eatIdentifier(String expectedValue) {
      IToken next = this.tokenSource.next();
      if (!next.getString().equals(expectedValue)) {
         throw new ParserException(expectedValue, next);
      }
   }

   public boolean maybeEatIdentifier(String expectedValue) {
      IToken next = this.tokenSource.peek();
      if (next.getString().equals(expectedValue)) {
         this.tokenSource.next();
         return true;
      } else {
         return false;
      }
   }

   public void eat(String expectedValue) {
      this.eat(expectedValue, expectedValue);
   }

   private void eat(String expectedValue, String expectedMessage) {
      IToken next = this.nextToken();
      if (next.getString() != expectedValue) {
         if (expectedValue.equals(">") && next.getString().startsWith(">")) {
            this.pendingRightArrows = BasicToken.makeLiteral(next.getString().substring(1).intern(), "string", next.getStart() + 1, next.getEnd());
         } else {
            throw new ParserException(expectedMessage, next);
         }
      }
   }

   private IToken nextToken() {
      if (this.pendingRightArrows != null) {
         IToken ret = this.pendingRightArrows;
         this.pendingRightArrows = null;
         return ret;
      } else {
         return this.tokenSource.next();
      }
   }

   public boolean maybeEatAdjacent(String token) {
      IToken next = this.tokenSource.peek();
      if (next.getString() == token && this.isAdjacent(this.tokenSource.peek(-1), next)) {
         this.tokenSource.next();
         return true;
      } else {
         return false;
      }
   }

   public boolean maybeEat(String token) {
      IToken next = this.tokenSource.peek();
      if (next.getString() == token) {
         this.tokenSource.next();
         return true;
      } else {
         return false;
      }
   }

   public String maybeEatIdentifier() {
      IToken next = this.tokenSource.peek();
      if (next.isIdentifier()) {
         this.tokenSource.next();
         return next.getString();
      } else {
         return null;
      }
   }

   public boolean peek(String token) {
      IToken next = this.tokenSource.peek();
      return next.getString() == token;
   }

   public void checkEof() {
      IToken last = this.tokenSource.next();
      if (last != IToken.EOF) {
         throw new ParserException("unexpected pointcut element: " + last.toString(), last);
      }
   }

   public PatternParser(String data) {
      this(BasicTokenSource.makeTokenSource(data, (ISourceContext)null));
   }

   public PatternParser(String data, ISourceContext context) {
      this(BasicTokenSource.makeTokenSource(data, context));
   }
}
