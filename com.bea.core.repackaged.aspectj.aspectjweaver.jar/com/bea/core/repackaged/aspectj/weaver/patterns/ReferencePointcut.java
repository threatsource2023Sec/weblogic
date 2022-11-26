package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.TypeVariable;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReferencePointcut extends Pointcut {
   public UnresolvedType onType;
   public TypePattern onTypeSymbolic;
   public String name;
   public TypePatternList arguments;
   private Map typeVariableMap;
   private boolean concretizing = false;

   public ReferencePointcut(TypePattern onTypeSymbolic, String name, TypePatternList arguments) {
      this.onTypeSymbolic = onTypeSymbolic;
      this.name = name;
      this.arguments = arguments;
      this.pointcutKind = 8;
   }

   public ReferencePointcut(UnresolvedType onType, String name, TypePatternList arguments) {
      this.onType = onType;
      this.name = name;
      this.arguments = arguments;
      this.pointcutKind = 8;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return FuzzyBoolean.NO;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (this.onType != null) {
         buf.append(this.onType);
         buf.append(".");
      }

      buf.append(this.name);
      buf.append(this.arguments.toString());
      return buf.toString();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(8);
      if (this.onType != null) {
         s.writeBoolean(true);
         this.onType.write(s);
      } else {
         s.writeBoolean(false);
      }

      s.writeUTF(this.name);
      this.arguments.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      UnresolvedType onType = null;
      if (s.readBoolean()) {
         onType = UnresolvedType.read(s);
      }

      ReferencePointcut ret = new ReferencePointcut(onType, s.readUTF(), TypePatternList.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      if (this.onTypeSymbolic != null) {
         this.onType = this.onTypeSymbolic.resolveExactType(scope, bindings);
         if (ResolvedType.isMissing(this.onType)) {
            return;
         }
      }

      ResolvedType searchType;
      if (this.onType != null) {
         searchType = scope.getWorld().resolve(this.onType);
      } else {
         searchType = scope.getEnclosingType();
      }

      if (searchType.isTypeVariableReference()) {
         searchType = ((TypeVariableReference)searchType).getTypeVariable().getFirstBound().resolve(scope.getWorld());
      }

      this.arguments.resolveBindings(scope, bindings, true, true);
      ResolvedPointcutDefinition pointcutDef = searchType.findPointcut(this.name);
      if (pointcutDef == null && this.onType == null) {
         while(true) {
            UnresolvedType declaringType = searchType.getDeclaringType();
            if (declaringType == null) {
               break;
            }

            searchType = declaringType.resolve(scope.getWorld());
            pointcutDef = searchType.findPointcut(this.name);
            if (pointcutDef != null) {
               this.onType = searchType;
               break;
            }
         }
      }

      if (pointcutDef == null) {
         scope.message(IMessage.ERROR, this, "can't find referenced pointcut " + this.name);
      } else if (!pointcutDef.isVisible(scope.getEnclosingType())) {
         scope.message(IMessage.ERROR, this, "pointcut declaration " + pointcutDef + " is not accessible");
      } else {
         if (Modifier.isAbstract(pointcutDef.getModifiers())) {
            if (this.onType != null && !this.onType.isTypeVariableReference()) {
               scope.message(IMessage.ERROR, this, "can't make static reference to abstract pointcut");
               return;
            }

            if (!searchType.isAbstract()) {
               scope.message(IMessage.ERROR, this, "can't use abstract pointcut in concrete context");
               return;
            }
         }

         ResolvedType[] parameterTypes = scope.getWorld().resolve(pointcutDef.getParameterTypes());
         if (parameterTypes.length != this.arguments.size()) {
            scope.message(IMessage.ERROR, this, "incompatible number of arguments to pointcut, expected " + parameterTypes.length + " found " + this.arguments.size());
         } else {
            if (this.onType != null) {
               if (this.onType.isParameterizedType()) {
                  this.typeVariableMap = new HashMap();
                  ResolvedType underlyingGenericType = ((ResolvedType)this.onType).getGenericType();
                  TypeVariable[] tVars = underlyingGenericType.getTypeVariables();
                  ResolvedType[] typeParams = ((ResolvedType)this.onType).getResolvedTypeParameters();

                  for(int i = 0; i < tVars.length; ++i) {
                     this.typeVariableMap.put(tVars[i].getName(), typeParams[i]);
                  }
               } else if (this.onType.isGenericType()) {
                  scope.message(MessageUtil.error(WeaverMessages.format("noRawTypePointcutReferences"), this.getSourceLocation()));
               }
            }

            int i = 0;

            for(int len = this.arguments.size(); i < len; ++i) {
               TypePattern p = this.arguments.get(i);
               if (this.typeVariableMap != null) {
                  p = p.parameterizeWith(this.typeVariableMap, scope.getWorld());
               }

               if (p == TypePattern.NO) {
                  scope.message(IMessage.ERROR, this, "bad parameter to pointcut reference");
                  return;
               }

               boolean reportProblem = false;
               if (parameterTypes[i].isTypeVariableReference() && p.getExactType().isTypeVariableReference()) {
                  UnresolvedType One = ((TypeVariableReference)parameterTypes[i]).getTypeVariable().getFirstBound();
                  UnresolvedType Two = ((TypeVariableReference)p.getExactType()).getTypeVariable().getFirstBound();
                  reportProblem = !One.resolve(scope.getWorld()).isAssignableFrom(Two.resolve(scope.getWorld()));
               } else {
                  reportProblem = !p.matchesSubtypes(parameterTypes[i]) && !p.getExactType().equals(UnresolvedType.OBJECT);
               }

               if (reportProblem) {
                  scope.message(IMessage.ERROR, this, "incompatible type, expected " + parameterTypes[i].getName() + " found " + p + ".  Check the type specified in your pointcut");
                  return;
               }
            }

         }
      }
   }

   public void postRead(ResolvedType enclosingType) {
      this.arguments.postRead(enclosingType);
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      throw new RuntimeException("shouldn't happen");
   }

   public Pointcut concretize1(ResolvedType searchStart, ResolvedType declaringType, IntMap bindings) {
      if (this.concretizing) {
         searchStart.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("circularPointcutDeclaration", this), this.getSourceLocation()));
         Pointcut p = Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
         p.sourceContext = this.sourceContext;
         return p;
      } else {
         try {
            this.concretizing = true;
            if (this.onType != null) {
               searchStart = this.onType.resolve(searchStart.getWorld());
               if (searchStart.isMissing()) {
                  Pointcut var22 = Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
                  return var22;
               }

               if (this.onType.isTypeVariableReference() && declaringType.isParameterizedType()) {
                  TypeVariable[] tvs = declaringType.getGenericType().getTypeVariables();
                  String typeVariableName = ((TypeVariableReference)this.onType).getTypeVariable().getName();

                  for(int i = 0; i < tvs.length; ++i) {
                     if (tvs[i].getName().equals(typeVariableName)) {
                        ResolvedType realOnType = declaringType.getTypeParameters()[i].resolve(declaringType.getWorld());
                        this.onType = realOnType;
                        searchStart = realOnType;
                        break;
                     }
                  }
               }
            }

            if (declaringType == null) {
               declaringType = searchStart;
            }

            ResolvedPointcutDefinition pointcutDec = declaringType.findPointcut(this.name);
            boolean foundMatchingPointcut = pointcutDec != null && Modifier.isPrivate(pointcutDec.getModifiers());
            if (!foundMatchingPointcut) {
               pointcutDec = searchStart.findPointcut(this.name);
               if (pointcutDec == null) {
                  searchStart.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("cantFindPointcut", this.name, searchStart.getName()), this.getSourceLocation()));
                  Pointcut var25 = Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
                  return var25;
               }
            }

            if (pointcutDec.isAbstract()) {
               ShadowMunger enclosingAdvice = bindings.getEnclosingAdvice();
               searchStart.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("abstractPointcut", pointcutDec), this.getSourceLocation(), null == enclosingAdvice ? null : enclosingAdvice.getSourceLocation());
               Pointcut var27 = Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
               return var27;
            } else {
               TypePatternList arguments = this.arguments.resolveReferences(bindings);
               IntMap newBindings = new IntMap();
               int i = 0;

               Object tVars;
               for(tVars = arguments.size(); i < tVars; ++i) {
                  TypePattern p = arguments.get(i);
                  if (p != TypePattern.NO && p instanceof BindingTypePattern) {
                     newBindings.put(i, ((BindingTypePattern)p).getFormalIndex());
                  }
               }

               if (searchStart.isParameterizedType()) {
                  this.typeVariableMap = new HashMap();
                  ResolvedType underlyingGenericType = searchStart.getGenericType();
                  tVars = underlyingGenericType.getTypeVariables();
                  ResolvedType[] typeParams = searchStart.getResolvedTypeParameters();

                  for(int i = 0; i < ((Object[])tVars).length; ++i) {
                     this.typeVariableMap.put(((TypeVariable)((Object[])tVars)[i]).getName(), typeParams[i]);
                  }
               }

               newBindings.copyContext(bindings);
               newBindings.pushEnclosingDefinition(pointcutDec);

               try {
                  Pointcut ret = pointcutDec.getPointcut();
                  if (this.typeVariableMap != null && !this.hasBeenParameterized) {
                     ret = ret.parameterizeWith(this.typeVariableMap, searchStart.getWorld());
                     ret.hasBeenParameterized = true;
                  }

                  tVars = ret.concretize(searchStart, declaringType, newBindings);
                  return (Pointcut)tVars;
               } finally {
                  newBindings.popEnclosingDefinitition();
               }
            }
         } finally {
            this.concretizing = false;
         }
      }
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      ReferencePointcut ret = new ReferencePointcut(this.onType, this.name, this.arguments);
      ret.onTypeSymbolic = this.onTypeSymbolic;
      ret.typeVariableMap = typeVariableMap;
      return ret;
   }

   protected boolean shouldCopyLocationForConcretize() {
      return false;
   }

   public boolean equals(Object other) {
      if (!(other instanceof ReferencePointcut)) {
         return false;
      } else if (this == other) {
         return true;
      } else {
         boolean var10000;
         label38: {
            ReferencePointcut o = (ReferencePointcut)other;
            if (o.name.equals(this.name) && o.arguments.equals(this.arguments)) {
               if (o.onType == null) {
                  if (this.onType == null) {
                     break label38;
                  }
               } else if (o.onType.equals(this.onType)) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (this.onType == null ? 0 : this.onType.hashCode());
      result = 37 * result + this.arguments.hashCode();
      result = 37 * result + this.name.hashCode();
      return result;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
