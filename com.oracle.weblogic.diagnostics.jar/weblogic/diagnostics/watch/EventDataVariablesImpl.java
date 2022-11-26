package weblogic.diagnostics.watch;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.archive.EventDataResolver;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

public class EventDataVariablesImpl extends EventDataResolver {
   private static final ColumnInfo[] columns = ArchiveConstants.getColumns(1);
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();

   public EventDataVariablesImpl() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created EventDataVariableImpl " + this);
      }

   }

   Map getWatchData() {
      Map props = new HashMap();

      for(int i = 0; i < columns.length; ++i) {
         String val = "";

         try {
            val = val + this.resolveVariable(i);
         } catch (Exception var5) {
         }

         props.put(columns[i].getColumnName(), val);
      }

      return props;
   }
}
