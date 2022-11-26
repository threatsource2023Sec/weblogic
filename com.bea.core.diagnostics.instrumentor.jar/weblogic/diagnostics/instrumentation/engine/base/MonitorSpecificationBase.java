package weblogic.diagnostics.instrumentation.engine.base;

import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

public class MonitorSpecificationBase implements InstrumentationEngineConstants, Serializable {
   static final long serialVersionUID = 8553011116555554668L;
   protected String type;
   protected boolean isStandard;
   protected boolean isDelegating;
   protected boolean isCustom;
   protected boolean serverManaged;
   protected String diagnosticVolume;
   protected String eventClassName;
   protected boolean serverScoped;
   protected boolean applicationScoped;
   protected int location;
   protected String codeGenClassName;
   protected String[] attributeNames;
   protected String actionGroupName;
   protected String[] actionTypeNames;
   protected boolean captureArgs;
   protected boolean captureRetVal;
   protected boolean captureThisOnly;
   protected PointcutExpression pointcutExpr;
   protected String[] inclusion_patterns;
   protected String[] exclusion_patterns;
   private static final String VALID_TYPE_PATTERN_STRING = "^[a-zA-Z_]((\\w|/)*)$";
   private static Pattern validTypePattern = initializeValidTypePattern();

   protected MonitorSpecificationBase() {
      this.diagnosticVolume = "Off";
   }

   public MonitorSpecificationBase(String type, int location, PointcutExpression pointcutExpr, boolean captureArgs, boolean captureRetVal) {
      this(type, location, pointcutExpr, captureArgs, captureRetVal, (String[])null, (String[])null);
   }

   public MonitorSpecificationBase(String type, int location, PointcutExpression pointcutExpr, boolean captureArgs, boolean captureRetVal, boolean captureThisOnly) {
      this(type, location, pointcutExpr, captureArgs, captureRetVal, captureThisOnly, (String[])null, (String[])null);
   }

   public MonitorSpecificationBase(String type, int location, PointcutExpression pointcutExpr, boolean captureArgs, boolean captureRetVal, String[] inclusion_patterns, String[] exclusion_patterns) {
      this(type, location, pointcutExpr, captureArgs, captureRetVal, false, inclusion_patterns, exclusion_patterns);
   }

   public MonitorSpecificationBase(String type, int location, PointcutExpression pointcutExpr, boolean captureArgs, boolean captureRetVal, boolean captureThisOnly, String[] inclusion_patterns, String[] exclusion_patterns) {
      this.diagnosticVolume = "Off";
      this.type = type;
      this.location = location;
      this.pointcutExpr = pointcutExpr;
      this.captureArgs = captureArgs;
      this.captureRetVal = captureRetVal;
      this.captureThisOnly = captureThisOnly;
      this.isCustom = true;
      this.inclusion_patterns = inclusion_patterns;
      this.exclusion_patterns = exclusion_patterns;
   }

   protected String[] getStringList(String str, String separator) {
      if (str == null) {
         return new String[0];
      } else {
         String[] list = str.split(separator);
         if (list != null) {
            for(int i = 0; i < list.length; ++i) {
               list[i] = list[i].trim();
            }
         }

         return list;
      }
   }

   public String getType() {
      return this.type;
   }

   void setType(String type) {
      this.type = type;
   }

   String getActionGroupName() {
      return this.actionGroupName;
   }

   public boolean isServerScoped() {
      return this.serverScoped;
   }

   public boolean isServerManaged() {
      return this.serverManaged;
   }

   public String getDiagnosticVolume() {
      return this.diagnosticVolume;
   }

   public String getEventClassName() {
      return this.eventClassName;
   }

   public boolean isApplicationScoped() {
      return this.applicationScoped;
   }

   public boolean isStandardMonitor() {
      return this.isStandard;
   }

   public boolean isDelegatingMonitor() {
      return this.isDelegating;
   }

   public boolean isCustomMonitor() {
      return this.isCustom;
   }

   public int getLocation() {
      return this.location;
   }

   String getCodeGenerator() {
      return this.codeGenClassName;
   }

   public String[] getAttributeNames() {
      return this.attributeNames;
   }

   public String[] getActionTypes() {
      return this.actionTypeNames;
   }

   void setActionTypes(String[] actionTypeNames) {
      this.actionTypeNames = actionTypeNames;
   }

   boolean allowCaptureArguments() {
      return this.captureArgs;
   }

   boolean allowCaptureReturnValue() {
      return this.captureRetVal;
   }

   boolean allowCaptureThisOnly() {
      return this.captureThisOnly;
   }

   boolean isEligibleCallsite(ClassInstrumentor classInstrumentor, String className, String methodName, String methodDesc, MethodInfo methodInfo) throws InvalidPointcutException {
      if (this.pointcutExpr != null) {
         MatchInfo match = this.pointcutExpr.isEligibleCallsite(classInstrumentor, className, methodName, methodDesc, methodInfo);
         if (match.isMatch()) {
            if (methodInfo != null) {
               classInstrumentor.addPointcutHandlngInfoForCallsite(className, methodName, methodDesc, this, match.getPointcutHandlingInfo());
            }

            return true;
         }
      }

      return false;
   }

   boolean isEligibleMethod(ClassInstrumentor classInstrumentor, String className, MethodInfo methodInfo) throws InvalidPointcutException {
      if (this.pointcutExpr != null) {
         MatchInfo match = this.pointcutExpr.isEligibleMethod(classInstrumentor, className, methodInfo);
         if (match.isMatch()) {
            if (methodInfo != null) {
               classInstrumentor.addPointcutHandlngInfoForMethod(methodInfo.getMethodName(), methodInfo.getMethodDesc(), this, match.getPointcutHandlingInfo());
            }

            return true;
         }
      }

      return false;
   }

   boolean isEligibleCatchBlock(ClassInstrumentor classInstrumentor, String exceptionClassName, MethodInfo methodInfo) throws InvalidPointcutException {
      if (this.pointcutExpr != null) {
         MatchInfo match = this.pointcutExpr.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo);
         return match.isMatch();
      } else {
         return false;
      }
   }

   private static Pattern initializeValidTypePattern() {
      Pattern pattern = null;

      try {
         pattern = Pattern.compile("^[a-zA-Z_]((\\w|/)*)$");
      } catch (PatternSyntaxException var2) {
         UnexpectedExceptionHandler.handle("Internal error: ", var2);
      }

      return pattern;
   }

   public static boolean isValidType(String type) {
      return validTypePattern.matcher(type).find();
   }

   public static int getLocationType(String location) {
      int retVal = 0;
      if (location == null) {
         return 0;
      } else {
         location = location.trim();
         if ("before".equals(location)) {
            retVal = 1;
         } else if ("after".equals(location)) {
            retVal = 2;
         } else if ("around".equals(location)) {
            retVal = 3;
         }

         return retVal;
      }
   }

   public String[] getInclusionPatterns() {
      return this.inclusion_patterns;
   }

   public String[] getExclusionPatterns() {
      return this.exclusion_patterns;
   }

   PointcutExpression getPointcutExpression() {
      return this.pointcutExpr;
   }

   public void markPointcutExpressionAsKeep() {
      if (this.pointcutExpr != null) {
         this.pointcutExpr.markAsKeep();
      }

   }

   public void removeUnkeptPointcut() {
      if (this.applicationScoped) {
         if (this.pointcutExpr != null) {
            this.pointcutExpr.markAsKeep();
         }

      } else {
         this.pointcutExpr = null;
      }
   }
}
