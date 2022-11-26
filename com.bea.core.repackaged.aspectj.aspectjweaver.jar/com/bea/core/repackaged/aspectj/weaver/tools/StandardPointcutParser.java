package com.bea.core.repackaged.aspectj.weaver.tools;

import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.weaver.BindingScope;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.StandardPointcutExpressionImpl;
import com.bea.core.repackaged.aspectj.weaver.internal.tools.TypePatternMatcherImpl;
import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.CflowPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.FormalBinding;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.KindedPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.OrPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ParserException;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.SimpleScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.ThisOrTargetPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.aspectj.weaver.reflect.PointcutParameterImpl;
import com.bea.core.repackaged.aspectj.weaver.reflect.ReflectionWorld;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class StandardPointcutParser {
   private World world;
   private final Set supportedPrimitives;
   private final Set pointcutDesignators = new HashSet();

   public static Set getAllSupportedPointcutPrimitives() {
      Set primitives = new HashSet();
      primitives.add(PointcutPrimitive.ADVICE_EXECUTION);
      primitives.add(PointcutPrimitive.ARGS);
      primitives.add(PointcutPrimitive.CALL);
      primitives.add(PointcutPrimitive.EXECUTION);
      primitives.add(PointcutPrimitive.GET);
      primitives.add(PointcutPrimitive.HANDLER);
      primitives.add(PointcutPrimitive.INITIALIZATION);
      primitives.add(PointcutPrimitive.PRE_INITIALIZATION);
      primitives.add(PointcutPrimitive.SET);
      primitives.add(PointcutPrimitive.STATIC_INITIALIZATION);
      primitives.add(PointcutPrimitive.TARGET);
      primitives.add(PointcutPrimitive.THIS);
      primitives.add(PointcutPrimitive.WITHIN);
      primitives.add(PointcutPrimitive.WITHIN_CODE);
      primitives.add(PointcutPrimitive.AT_ANNOTATION);
      primitives.add(PointcutPrimitive.AT_THIS);
      primitives.add(PointcutPrimitive.AT_TARGET);
      primitives.add(PointcutPrimitive.AT_ARGS);
      primitives.add(PointcutPrimitive.AT_WITHIN);
      primitives.add(PointcutPrimitive.AT_WITHINCODE);
      primitives.add(PointcutPrimitive.REFERENCE);
      return primitives;
   }

   public static StandardPointcutParser getPointcutParserSupportingAllPrimitives(World world) {
      StandardPointcutParser p = new StandardPointcutParser(world);
      return p;
   }

   public static StandardPointcutParser getPointcutParserSupportingSpecifiedPrimitives(Set supportedPointcutKinds, World world) {
      StandardPointcutParser p = new StandardPointcutParser(supportedPointcutKinds, world);
      return p;
   }

   protected StandardPointcutParser(World world) {
      this.supportedPrimitives = getAllSupportedPointcutPrimitives();
      this.world = world;
   }

   private StandardPointcutParser(Set supportedPointcutKinds, World world) {
      this.supportedPrimitives = supportedPointcutKinds;
      Iterator iter = supportedPointcutKinds.iterator();

      PointcutPrimitive element;
      do {
         if (!iter.hasNext()) {
            this.world = world;
            return;
         }

         element = (PointcutPrimitive)iter.next();
      } while(element != PointcutPrimitive.IF && element != PointcutPrimitive.CFLOW && element != PointcutPrimitive.CFLOW_BELOW);

      throw new UnsupportedOperationException("Cannot handle if, cflow, and cflowbelow primitives");
   }

   public void setLintProperties(Properties properties) {
      this.getWorld().getLint().setFromProperties(properties);
   }

   public void registerPointcutDesignatorHandler(PointcutDesignatorHandler designatorHandler) {
      this.pointcutDesignators.add(designatorHandler);
      if (this.world != null) {
         this.world.registerPointcutHandler(designatorHandler);
      }

   }

   public PointcutParameter createPointcutParameter(String name, Class type) {
      return new PointcutParameterImpl(name, type);
   }

   public StandardPointcutExpression parsePointcutExpression(String expression) throws UnsupportedPointcutPrimitiveException, IllegalArgumentException {
      return this.parsePointcutExpression(expression, (Class)null, new PointcutParameter[0]);
   }

   public StandardPointcutExpression parsePointcutExpression(String expression, Class inScope, PointcutParameter[] formalParameters) throws UnsupportedPointcutPrimitiveException, IllegalArgumentException {
      StandardPointcutExpressionImpl pcExpr = null;

      try {
         Pointcut pc = this.resolvePointcutExpression(expression, inScope, formalParameters);
         pc = this.concretizePointcutExpression(pc, inScope, formalParameters);
         this.validateAgainstSupportedPrimitives(pc, expression);
         pcExpr = new StandardPointcutExpressionImpl(pc, expression, formalParameters, this.getWorld());
         return pcExpr;
      } catch (ParserException var6) {
         throw new IllegalArgumentException(this.buildUserMessageFromParserException(expression, var6));
      } catch (ReflectionWorld.ReflectionWorldException var7) {
         var7.printStackTrace();
         throw new IllegalArgumentException(var7.getMessage());
      }
   }

   protected Pointcut resolvePointcutExpression(String expression, Class inScope, PointcutParameter[] formalParameters) {
      try {
         PatternParser parser = new PatternParser(expression);
         parser.setPointcutDesignatorHandlers(this.pointcutDesignators, this.world);
         Pointcut pc = parser.parsePointcut();
         this.validateAgainstSupportedPrimitives(pc, expression);
         IScope resolutionScope = this.buildResolutionScope(inScope == null ? Object.class : inScope, formalParameters);
         pc = pc.resolve(resolutionScope);
         return pc;
      } catch (ParserException var7) {
         throw new IllegalArgumentException(this.buildUserMessageFromParserException(expression, var7));
      }
   }

   protected Pointcut concretizePointcutExpression(Pointcut pc, Class inScope, PointcutParameter[] formalParameters) {
      ResolvedType declaringTypeForResolution = null;
      if (inScope != null) {
         declaringTypeForResolution = this.getWorld().resolve(inScope.getName());
      } else {
         declaringTypeForResolution = ResolvedType.OBJECT.resolve(this.getWorld());
      }

      IntMap arity = new IntMap(formalParameters.length);

      for(int i = 0; i < formalParameters.length; ++i) {
         arity.put(i, i);
      }

      return pc.concretize(declaringTypeForResolution, declaringTypeForResolution, arity);
   }

   public TypePatternMatcher parseTypePattern(String typePattern) throws IllegalArgumentException {
      try {
         TypePattern tp = (new PatternParser(typePattern)).parseTypePattern();
         tp.resolve(this.world);
         return new TypePatternMatcherImpl(tp, this.world);
      } catch (ParserException var3) {
         throw new IllegalArgumentException(this.buildUserMessageFromParserException(typePattern, var3));
      } catch (ReflectionWorld.ReflectionWorldException var4) {
         throw new IllegalArgumentException(var4.getMessage());
      }
   }

   private World getWorld() {
      return this.world;
   }

   Set getSupportedPrimitives() {
      return this.supportedPrimitives;
   }

   IMessageHandler setCustomMessageHandler(IMessageHandler aHandler) {
      IMessageHandler current = this.getWorld().getMessageHandler();
      this.getWorld().setMessageHandler(aHandler);
      return current;
   }

   private IScope buildResolutionScope(Class inScope, PointcutParameter[] formalParameters) {
      if (formalParameters == null) {
         formalParameters = new PointcutParameter[0];
      }

      FormalBinding[] formalBindings = new FormalBinding[formalParameters.length];

      for(int i = 0; i < formalBindings.length; ++i) {
         formalBindings[i] = new FormalBinding(this.toUnresolvedType(formalParameters[i].getType()), formalParameters[i].getName(), i);
      }

      if (inScope == null) {
         SimpleScope ss = new SimpleScope(this.getWorld(), formalBindings);
         ss.setImportedPrefixes(new String[]{"java.lang.", "java.util."});
         return ss;
      } else {
         ResolvedType inType = this.getWorld().resolve(inScope.getName());
         ISourceContext sourceContext = new ISourceContext() {
            public ISourceLocation makeSourceLocation(IHasPosition position) {
               return new SourceLocation(new File(""), 0);
            }

            public ISourceLocation makeSourceLocation(int line, int offset) {
               return new SourceLocation(new File(""), line);
            }

            public int getOffset() {
               return 0;
            }

            public void tidy() {
            }
         };
         BindingScope bScope = new BindingScope(inType, sourceContext, formalBindings);
         bScope.setImportedPrefixes(new String[]{"java.lang.", "java.util."});
         return bScope;
      }
   }

   private UnresolvedType toUnresolvedType(Class clazz) {
      return clazz.isArray() ? UnresolvedType.forSignature(clazz.getName().replace('.', '/')) : UnresolvedType.forName(clazz.getName());
   }

   private void validateAgainstSupportedPrimitives(Pointcut pc, String expression) {
      boolean isThis;
      switch (pc.getPointcutKind()) {
         case 1:
            this.validateKindedPointcut((KindedPointcut)pc, expression);
            break;
         case 2:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.WITHIN)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.WITHIN);
            }
            break;
         case 3:
            isThis = ((ThisOrTargetPointcut)pc).isThis();
            if (isThis && !this.supportedPrimitives.contains(PointcutPrimitive.THIS)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.THIS);
            }

            if (!this.supportedPrimitives.contains(PointcutPrimitive.TARGET)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.TARGET);
            }
            break;
         case 4:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.ARGS)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.ARGS);
            }
            break;
         case 5:
            this.validateAgainstSupportedPrimitives(((AndPointcut)pc).getLeft(), expression);
            this.validateAgainstSupportedPrimitives(((AndPointcut)pc).getRight(), expression);
            break;
         case 6:
            this.validateAgainstSupportedPrimitives(((OrPointcut)pc).getLeft(), expression);
            this.validateAgainstSupportedPrimitives(((OrPointcut)pc).getRight(), expression);
            break;
         case 7:
            this.validateAgainstSupportedPrimitives(((NotPointcut)pc).getNegatedPointcut(), expression);
            break;
         case 8:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.REFERENCE)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.REFERENCE);
            }
            break;
         case 9:
         case 14:
         case 15:
            throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.IF);
         case 10:
            CflowPointcut cfp = (CflowPointcut)pc;
            if (cfp.isCflowBelow()) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.CFLOW_BELOW);
            }

            throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.CFLOW);
         case 11:
         case 20:
         default:
            throw new IllegalArgumentException("Unknown pointcut kind: " + pc.getPointcutKind());
         case 12:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.WITHIN_CODE)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.WITHIN_CODE);
            }
            break;
         case 13:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.HANDLER)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.HANDLER);
            }
            break;
         case 16:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.AT_ANNOTATION)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_ANNOTATION);
            }
            break;
         case 17:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.AT_WITHIN)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_WITHIN);
            }
            break;
         case 18:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.AT_WITHINCODE)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_WITHINCODE);
            }
            break;
         case 19:
            isThis = ((ThisOrTargetAnnotationPointcut)pc).isThis();
            if (isThis && !this.supportedPrimitives.contains(PointcutPrimitive.AT_THIS)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_THIS);
            }

            if (!this.supportedPrimitives.contains(PointcutPrimitive.AT_TARGET)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_TARGET);
            }
            break;
         case 21:
            if (!this.supportedPrimitives.contains(PointcutPrimitive.AT_ARGS)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.AT_ARGS);
            }
         case 22:
      }

   }

   private void validateKindedPointcut(KindedPointcut pc, String expression) {
      Shadow.Kind kind = pc.getKind();
      if (kind != Shadow.MethodCall && kind != Shadow.ConstructorCall) {
         if (kind != Shadow.MethodExecution && kind != Shadow.ConstructorExecution) {
            if (kind == Shadow.AdviceExecution) {
               if (!this.supportedPrimitives.contains(PointcutPrimitive.ADVICE_EXECUTION)) {
                  throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.ADVICE_EXECUTION);
               }
            } else if (kind == Shadow.FieldGet) {
               if (!this.supportedPrimitives.contains(PointcutPrimitive.GET)) {
                  throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.GET);
               }
            } else if (kind == Shadow.FieldSet) {
               if (!this.supportedPrimitives.contains(PointcutPrimitive.SET)) {
                  throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.SET);
               }
            } else if (kind == Shadow.Initialization) {
               if (!this.supportedPrimitives.contains(PointcutPrimitive.INITIALIZATION)) {
                  throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.INITIALIZATION);
               }
            } else if (kind == Shadow.PreInitialization) {
               if (!this.supportedPrimitives.contains(PointcutPrimitive.PRE_INITIALIZATION)) {
                  throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.PRE_INITIALIZATION);
               }
            } else if (kind == Shadow.StaticInitialization && !this.supportedPrimitives.contains(PointcutPrimitive.STATIC_INITIALIZATION)) {
               throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.STATIC_INITIALIZATION);
            }
         } else if (!this.supportedPrimitives.contains(PointcutPrimitive.EXECUTION)) {
            throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.EXECUTION);
         }
      } else if (!this.supportedPrimitives.contains(PointcutPrimitive.CALL)) {
         throw new UnsupportedPointcutPrimitiveException(expression, PointcutPrimitive.CALL);
      }

   }

   private String buildUserMessageFromParserException(String pc, ParserException ex) {
      StringBuffer msg = new StringBuffer();
      msg.append("Pointcut is not well-formed: expecting '");
      msg.append(ex.getMessage());
      msg.append("'");
      IHasPosition location = ex.getLocation();
      msg.append(" at character position ");
      msg.append(location.getStart());
      msg.append("\n");
      msg.append(pc);
      msg.append("\n");

      int j;
      for(j = 0; j < location.getStart(); ++j) {
         msg.append(" ");
      }

      for(j = location.getStart(); j <= location.getEnd(); ++j) {
         msg.append("^");
      }

      msg.append("\n");
      return msg.toString();
   }
}
