package weblogic.diagnostics.instrumentation.engine.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.objectweb.asm.Type;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfoImpl;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfoImpl;

public class PointcutSpecification implements PointcutExpression, InstrumentationEngineConstants {
   private static final long serialVersionUID = -778557070188760243L;
   private String name;
   private int type;
   private TypeSpecification classSelector;
   private ModifierExpression access;
   private Pattern methodPattern;
   private String methodPatternString;
   private boolean checkMethodAnnotations;
   private TypeSpecification[] argumentSelectors;
   private TypeSpecification returnSelector;
   private PointcutHandlingInfoImpl pointcutHandlingInfo;
   private transient boolean keepHint = false;

   PointcutSpecification() {
   }

   public static PointcutSpecification createPointcut(String name, String pointcutExpr) throws InvalidPointcutException {
      PointcutSpecification retVal = null;
      ByteArrayInputStream in = null;

      try {
         in = new ByteArrayInputStream(pointcutExpr.getBytes());
         PointcutLexer lexer = new PointcutLexer(in);
         PointcutParser parser = new PointcutParser(lexer);
         retVal = parser.pointcut();
         retVal.name = name;
      } catch (Exception var13) {
         throw new InvalidPointcutException(var13);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var12) {
               throw new InvalidPointcutException(var12);
            }
         }

      }

      return retVal;
   }

   public String getName() {
      return this.name;
   }

   public int getType() {
      return this.type;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Pointcut{name=").append(this.name).append(", type=").append(this.type).append(", class-selector=").append(this.classSelector).append(", method-selector=").append(this.methodPatternString).append(", argument-selectors=[");
      int len = this.argumentSelectors != null ? this.argumentSelectors.length : 0;

      for(int i = 0; i < len; ++i) {
         buf.append(this.argumentSelectors[i].toString()).append(" ");
      }

      buf.append("}");
      return buf.toString();
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc) {
      return this.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, (MethodInfo)null);
   }

   public MatchInfo isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, MethodInfo methodInfo) {
      if (this.type == 2) {
         return this.matchWithin(classInstrumentor, methodInfo);
      } else if (this.type != 0 && this.type != 3 && this.type != 5) {
         if (this.type == 1 && methodName.equals("<init>")) {
            return MatchInfo.NO_MATCH;
         } else if (methodInfo != null && !this.access.isMatch(methodInfo.getMethodAccess())) {
            return MatchInfo.NO_MATCH;
         } else {
            boolean retVal = this.hasMatch(classInstrumentor, className, methodName, methodDesc, (List)null);
            if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_WEAVING.debug("isEligibleCallsite: " + retVal);
            }

            if (!retVal) {
               return MatchInfo.NO_MATCH;
            } else {
               MatchInfo info = new MatchInfo(true, this.pointcutHandlingInfo);
               return info;
            }
         }
      } else {
         return MatchInfo.NO_MATCH;
      }
   }

   public MatchInfo isEligibleMethod(ClassInstrumentor classInstrumentor, String className, MethodInfo methodInfo) {
      if (this.type == 2) {
         return this.matchWithin(classInstrumentor, methodInfo);
      } else if (this.type != 1 && this.type != 4 && this.type != 5) {
         String methodName = methodInfo.getMethodName();
         String methodDesc = methodInfo.getMethodDesc();
         if (this.type == 0 && methodName.equals("<init>")) {
            return MatchInfo.NO_MATCH;
         } else if (!this.access.isMatch(methodInfo.getMethodAccess())) {
            return MatchInfo.NO_MATCH;
         } else {
            List annotations = this.checkMethodAnnotations ? methodInfo.getAnnotations() : null;
            boolean retVal = this.hasMatch(classInstrumentor, className, methodName, methodDesc, annotations);
            if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_WEAVING.debug("isEligibleMethod: " + retVal);
            }

            if (!retVal) {
               return MatchInfo.NO_MATCH;
            } else {
               MatchInfo info = new MatchInfo(true, this.pointcutHandlingInfo);
               return info;
            }
         }
      } else {
         return MatchInfo.NO_MATCH;
      }
   }

   public MatchInfo isEligibleCatchBlock(ClassInstrumentor classInstrumentor, String exceptionClassName, MethodInfo methodInfo) throws InvalidPointcutException {
      if (this.type == 2) {
         return this.matchWithin(classInstrumentor, methodInfo);
      } else if (this.type != 5) {
         return MatchInfo.NO_MATCH;
      } else {
         return this.classSelector.hasMatch(classInstrumentor, "L" + exceptionClassName + ";") ? MatchInfo.SIMPLE_MATCH : MatchInfo.NO_MATCH;
      }
   }

   public void accept(PointcutExpressionVisitor visitor) {
      visitor.visit(this);
   }

   private MatchInfo matchWithin(ClassInstrumentor classInstrumentor, MethodInfo methodInfo) {
      if (methodInfo == null) {
         return MatchInfo.PROBABLE_MATCH;
      } else {
         String className = methodInfo.getClassName();
         String methodName = methodInfo.getMethodName();
         String methodDesc = methodInfo.getMethodDesc();

         while(className != null) {
            if (methodName == null) {
               methodName = ".";
            }

            if (methodDesc == null) {
               methodDesc = "()V";
            }

            boolean retVal = this.hasMatch(classInstrumentor, "L" + className + ";", methodName, methodDesc, (List)null);
            if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_WEAVING.debug("matchWithin: " + className + "," + methodName + "," + methodDesc + " returned " + retVal);
            }

            if (retVal) {
               return MatchInfo.SIMPLE_MATCH;
            }

            ClassInfo info = classInstrumentor != null ? classInstrumentor.getClassInfo(className) : null;
            if (info != null) {
               className = info.getOuterClassName();
               methodName = info.getOuterMethodName();
               methodDesc = info.getOuterMethodDesc();
            } else {
               className = null;
            }
         }

         return MatchInfo.NO_MATCH;
      }
   }

   private boolean hasMatch(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, List annotations) {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("Checking className=" + className + " methodName=" + methodName + " methodDesc=" + methodDesc);
      }

      if (annotations != null) {
         if (!this.matchAnnotations(annotations)) {
            return false;
         }
      } else if (!this.methodPattern.matcher(methodName).matches()) {
         return false;
      }

      Type[] argTypes = Type.getArgumentTypes(methodDesc);
      int argCnt = argTypes != null ? argTypes.length : 0;
      int requiredArgCnt = this.argumentSelectors != null ? this.argumentSelectors.length : 0;
      boolean argsUseElipses = false;
      if (requiredArgCnt > 0) {
         argsUseElipses = this.argumentSelectors[requiredArgCnt - 1].isElipses();
         if (argsUseElipses) {
            --requiredArgCnt;
         }
      }

      if (argsUseElipses) {
         if (argCnt < requiredArgCnt) {
            return false;
         }
      } else if (argCnt != requiredArgCnt) {
         return false;
      }

      int i;
      TypeSpecification typeSpec;
      if (this.argumentSelectors != null) {
         for(i = 0; i < requiredArgCnt; ++i) {
            typeSpec = this.argumentSelectors[i];
            if (!typeSpec.isInheritanceAllowed() && !typeSpec.hasMatch(classInstrumentor, argTypes[i].getDescriptor())) {
               return false;
            }
         }
      }

      if (!this.classSelector.hasMatch(classInstrumentor, className)) {
         return false;
      } else {
         if (this.returnSelector != null && !"<init>".equals(methodName)) {
            Type retType = Type.getReturnType(methodDesc);
            if (!this.returnSelector.hasMatch(classInstrumentor, retType.getDescriptor())) {
               return false;
            }
         }

         if (this.argumentSelectors != null) {
            for(i = 0; i < requiredArgCnt; ++i) {
               typeSpec = this.argumentSelectors[i];
               if (typeSpec.isInheritanceAllowed() && !typeSpec.hasMatch(classInstrumentor, argTypes[i].getDescriptor())) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean matchAnnotations(List annotations) {
      Iterator it = annotations.iterator();

      String annoType;
      do {
         if (!it.hasNext()) {
            return false;
         }

         annoType = (String)it.next();
         annoType = annoType.substring(1, annoType.length() - 1);
      } while(!this.methodPattern.matcher(annoType).matches());

      return true;
   }

   void setType(int type) {
      switch (type) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            this.type = type;
         default:
      }
   }

   void setClassSelector(TypeSpecification classSelector) {
      this.classSelector = classSelector;
      ValueHandlingInfo info = this.getValueHandlingInfoFromSelector(classSelector);
      if (info != null) {
         if (this.pointcutHandlingInfo == null) {
            this.pointcutHandlingInfo = new PointcutHandlingInfoImpl();
         }

         this.pointcutHandlingInfo.setClassValueHandlingInfo(info);
      }

   }

   void setAccess(ModifierExpression access) {
      this.access = access;
      if (this.access == null) {
         this.access = new ModifierValue(0);
      }

   }

   void setMethodSelector(String methodSelector) throws InvalidPointcutException {
      try {
         this.checkMethodAnnotations = false;
         if (methodSelector.startsWith("@")) {
            methodSelector = methodSelector.substring(1, methodSelector.length());
            this.checkMethodAnnotations = true;
         }

         this.methodPatternString = methodSelector;
         this.methodPattern = Pattern.compile(this.methodPatternString);
      } catch (PatternSyntaxException var3) {
         throw new InvalidPointcutException("Invalid method pattern " + this.methodPatternString);
      }
   }

   void setReturnSelector(TypeSpecification returnSelector) {
      this.returnSelector = returnSelector;
      ValueHandlingInfo info = this.getValueHandlingInfoFromSelector(returnSelector);
      if (info != null) {
         if (this.pointcutHandlingInfo == null) {
            this.pointcutHandlingInfo = new PointcutHandlingInfoImpl();
         }

         this.pointcutHandlingInfo.setReturnValueHandlingInfo(info);
      }

   }

   void setArgumentSelectors(TypeSpecification[] argumentSelectors) {
      this.argumentSelectors = argumentSelectors;
      if (argumentSelectors != null && argumentSelectors.length > 0) {
         ValueHandlingInfo[] infos = new ValueHandlingInfo[argumentSelectors.length];
         int i = 0;
         boolean allNull = true;
         TypeSpecification[] var5 = argumentSelectors;
         int var6 = argumentSelectors.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            TypeSpecification selector = var5[var7];
            ValueHandlingInfo info = this.getValueHandlingInfoFromSelector(selector);
            if (info != null) {
               allNull = false;
            }

            infos[i++] = info;
         }

         if (!allNull) {
            if (this.pointcutHandlingInfo == null) {
               this.pointcutHandlingInfo = new PointcutHandlingInfoImpl();
            }

            this.pointcutHandlingInfo.setArgumentValueHandlingInfo(infos);
         }
      }

   }

   TypeSpecification getReturnSelector() {
      return this.returnSelector;
   }

   TypeSpecification[] getArgumentSelectors() {
      return this.argumentSelectors;
   }

   PointcutHandlingInfo getPointcutHandlingInfo() {
      return this.pointcutHandlingInfo;
   }

   private ValueHandlingInfo getValueHandlingInfoFromSelector(TypeSpecification selector) {
      if (selector != null && !selector.isSensitive()) {
         ValueHandlingInfoImpl info = new ValueHandlingInfoImpl();
         info.setName(selector.getArgumentName());
         info.setRendererClassName(selector.getValueRendererClassName());
         info.setSensitive(selector.isSensitive());
         info.setGathered(selector.isGatherable());
         return info;
      } else {
         return null;
      }
   }

   public void markAsKeep() {
      this.keepHint = true;
   }

   public boolean getKeepHint() {
      return this.keepHint;
   }
}
