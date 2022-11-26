package weblogic.diagnostics.watch;

import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;

public final class ConsoleUtils {
   private static final String[] RELATIONAL_OPERATORS = new String[]{"<", "<=", ">", ">=", "=", "!=", "IN", "LIKE", "MATCHES"};
   private static final String[] LOGICAL_OPERATORS = new String[]{"AND", "OR", "NOT", "("};
   private static final String ATTR_SEPARATOR = "//";
   private static final String BEGIN_ENCLOSER = "${";
   private static final String END_ENCLOSER = "}";
   private static final String TYPE_BEGIN_DELIMITER = "[";
   private static final String TYPE_END_DELIMITER = "]";

   public static String[] getRelationalOperators() {
      return RELATIONAL_OPERATORS;
   }

   public static String[] getLogicalOperators() {
      return LOGICAL_OPERATORS;
   }

   public static String buildWatchVariableExpression(String typeName, String instanceName, String attribute) {
      return buildWatchVariableExpression("ServerRuntime", typeName, instanceName, attribute);
   }

   public static String buildWatchVariableExpression(String namespace, String typeName, String instanceName, String attribute) {
      String trimmedInstanceName = instanceName == null ? null : instanceName.trim();
      String trimmedTypeName = typeName == null ? null : typeName.trim();
      String trimmedAttribute = attribute == null ? null : attribute.trim();
      if (trimmedAttribute != null && trimmedAttribute.length() != 0) {
         if (trimmedInstanceName != null && trimmedInstanceName.length() != 0 || trimmedTypeName != null && trimmedTypeName.length() != 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("${");
            if (namespace != null && namespace.trim().length() > 0) {
               buffer.append(namespace.trim());
               buffer.append("//");
            }

            if (trimmedTypeName != null && trimmedTypeName.length() > 0) {
               buffer.append("[");
               buffer.append(trimmedTypeName);
               buffer.append("]");
            }

            if (trimmedInstanceName != null && trimmedInstanceName.length() > 0) {
               buffer.append(trimmedInstanceName);
            }

            buffer.append("//");
            buffer.append(trimmedAttribute);
            buffer.append("}");
            return buffer.toString();
         } else {
            throw new IllegalArgumentException(DiagnosticsTextWatchTextFormatter.getInstance().getIncompleteWatchVariableConsoleText());
         }
      } else {
         throw new IllegalArgumentException(DiagnosticsTextWatchTextFormatter.getInstance().getEmptyWatchAttributeConsoleText());
      }
   }
}
