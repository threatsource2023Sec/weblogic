package weblogic.diagnostics.watch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.InvalidHarvesterInstanceNameException;
import weblogic.diagnostics.harvester.InvalidHarvesterNamespaceException;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.utils.PlatformConstants;

public class HarvesterVariablesParser {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static Pattern TYPE_PAT = Pattern.compile("([a-zA-Z_$][a-zA-Z0-9_$]*\\.)*[a-zA-Z_$][a-zA-Z0-9_$]*");

   static HarvesterVariablesParser getInstance() {
      return HarvesterVariablesParser.SingletonWrapper.SINGLETON;
   }

   static String[] parse(String name, String watchName) {
      if (watchName == null) {
         watchName = "Unknown";
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Get properties for " + name);
      }

      String errors = "";
      int errorCount = 0;
      String namespace = "ServerRuntime";
      String attributeExpression = null;
      String typeAndInstanceField = null;
      String[] components = name.trim().split("//");
      if (components.length < 2) {
         String msg = DiagnosticsTextTextFormatter.getInstance().getBadHarvesterVariableName(name, watchName);
         throw new IllegalArgumentException(msg);
      } else {
         if (components.length > 2) {
            namespace = components[0].trim();
            typeAndInstanceField = components[1].trim();
            attributeExpression = components[2].trim();
         } else {
            typeAndInstanceField = components[0].trim();
            attributeExpression = components[1].trim();
         }

         int instanceIndex = 0;
         String typeName = null;
         String instanceName = null;
         if (typeAndInstanceField.startsWith("[")) {
            int typeTerminusIndex = typeAndInstanceField.indexOf(93);
            if (typeTerminusIndex > 0) {
               typeName = typeAndInstanceField.substring(1, typeTerminusIndex).trim();
               instanceIndex = typeTerminusIndex + 1;
            }

            if (instanceIndex > 0 && instanceIndex < typeAndInstanceField.length()) {
               instanceName = typeAndInstanceField.substring(instanceIndex).trim();
            }
         } else {
            instanceName = typeAndInstanceField;
         }

         StringBuilder var10000;
         if (namespace != null && namespace.length() > 0) {
            try {
               WLDFHarvesterUtils.validateNamespace(namespace);
            } catch (InvalidHarvesterNamespaceException var16) {
               var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
               ++errorCount;
               errors = var10000.append(errorCount).append(") ").append(var16.getMessage()).toString();
            }
         }

         String typeFromObjectName = null;
         String msg;
         if (instanceName != null && instanceName.length() > 0) {
            try {
               WLDFHarvesterUtils.normalizeInstanceName(instanceName);
            } catch (InvalidHarvesterInstanceNameException var15) {
               msg = DiagnosticsTextTextFormatter.getInstance().getInvalidObjectName(instanceName, name, watchName);
               var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
               ++errorCount;
               errors = var10000.append(errorCount).append(") ").append(msg).toString();
            }

            typeFromObjectName = WLDFHarvesterUtils.getTypeForInstance(instanceName);
            if (typeName == null || typeName.length() == 0) {
               typeName = typeFromObjectName;
            }
         }

         if (typeName != null && typeName.length() != 0) {
            Matcher mt = TYPE_PAT.matcher(typeName);
            if (!mt.matches()) {
               msg = DiagnosticsTextTextFormatter.getInstance().getBadHarvesterVariableType(typeName, name, watchName);
               var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
               ++errorCount;
               errors = var10000.append(errorCount).append(") ").append(msg).toString();
            }
         }

         String msg;
         if (typeName != null && typeFromObjectName != null && !typeName.equals(typeFromObjectName)) {
            msg = DiagnosticsTextTextFormatter.getInstance().getHarvesterVariableTypeMismatch(name, instanceName, typeName, typeFromObjectName);
            var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
            ++errorCount;
            errors = var10000.append(errorCount).append(") ").append(msg).toString();
         }

         if ((instanceName == null || instanceName.length() == 0) && (typeName == null || typeName.length() == 0)) {
            msg = DiagnosticsTextTextFormatter.getInstance().getMissingBothTypeAndInstanceName(name, watchName);
            var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
            ++errorCount;
            errors = var10000.append(errorCount).append(") ").append(msg).toString();
         }

         if (attributeExpression == null) {
            msg = DiagnosticsTextWatchTextFormatter.getInstance().getNullWatchVariableAttributeNameText(attributeExpression, watchName);
            var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
            ++errorCount;
            errors = var10000.append(errorCount).append(") ").append(msg).toString();
         } else if (attributeExpression.length() == 0) {
            msg = DiagnosticsTextTextFormatter.getInstance().getEmptyAttributeName(name, watchName);
            var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
            ++errorCount;
            errors = var10000.append(errorCount).append(") ").append(msg).toString();
         } else {
            try {
               attributeExpression = normalizeAttributeName(typeName, attributeExpression, instanceName);
            } catch (Exception var14) {
               msg = DiagnosticsTextTextFormatter.getInstance().getBadHarvesterVariableAttr(attributeExpression, name, watchName);
               var10000 = (new StringBuilder()).append(errors).append("").append(PlatformConstants.EOL);
               ++errorCount;
               errors = var10000.append(errorCount).append(") ").append(msg).toString();
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Normalized attribute: " + attributeExpression);
            }
         }

         if (errorCount > 0) {
            msg = DiagnosticsTextTextFormatter.getInstance().getErrorsOcurredParsingHarvesterVariableName(name, watchName, errors, errorCount);
            throw new IllegalArgumentException(msg);
         } else {
            return new String[]{namespace, typeName, instanceName, attributeExpression};
         }
      }
   }

   private static String normalizeAttributeName(String typeName, String attributeName, String instanceName) {
      if (typeName != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Normalizing attribute " + attributeName + " based on type name " + typeName);
         }

         attributeName = WLDFHarvesterUtils.normalizeAttributeSpecification(typeName, attributeName);
      } else if (instanceName != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Normalizing attribute " + attributeName + " based on instance name " + instanceName);
         }

         attributeName = WLDFHarvesterUtils.normalizeAttributeForInstance(instanceName, attributeName);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Normalizing attribute " + attributeName + " using default normalizer");
         }

         attributeName = WLDFHarvesterUtils.normalizeAttributeSpecification((String)null, attributeName);
      }

      return attributeName;
   }

   private static class SingletonWrapper {
      private static HarvesterVariablesParser SINGLETON = new HarvesterVariablesParser();
   }
}
