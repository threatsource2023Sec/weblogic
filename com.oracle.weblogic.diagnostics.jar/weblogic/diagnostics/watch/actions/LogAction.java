package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.DiagnosticsELContext;
import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.ExpressionEvaluator;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.expressions.Partition;
import com.oracle.weblogic.diagnostics.watch.actions.ActionAdapter;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.logging.NonCatalogLogger;

@Service(
   name = "LogAction"
)
@PerLookup
@AdminServer
@ManagedServer
@Partition
public class LogAction extends ActionAdapter {
   private static final DiagnosticsTextWatchTextFormatter txtFmt = DiagnosticsTextWatchTextFormatter.getInstance();
   @Inject
   private EvaluatorFactory evalFactory;
   public static final String ACTION_NAME = "LogAction";

   public LogAction() {
      super("LogAction");
   }

   public void execute(ActionContext context) {
      ActionConfigBean logActionConfig = context.getActionConfig();
      if (logActionConfig != null && logActionConfig instanceof LogActionConfig) {
         LogActionConfig config = (LogActionConfig)logActionConfig;
         if (config.getSubSystemName() == null) {
            throw new IllegalArgumentException(txtFmt.getLogActionNoSubsystemNameSpecifiedText(config.getName()));
         } else {
            NonCatalogLogger logger = new NonCatalogLogger(config.getSubSystemName());
            String level = config.getLogLevel();
            if (level == null) {
               throw new IllegalArgumentException(txtFmt.getLogActionSeverityNullText(config.getName()));
            } else {
               String message = config.getMessage();
               if (message == null) {
                  throw new IllegalArgumentException(txtFmt.getLogActionNullOrEmptyMessageText(config.getName()));
               } else {
                  ExpressionEvaluator evaluator = this.evalFactory.createEvaluator(new Annotation[0]);

                  try {
                     if (evaluator != null) {
                        Map watchData = context.getWatchData();
                        this.bindWatchData(evaluator, watchData);
                        message = (String)evaluator.evaluate(message, String.class);
                        watchData.put(LogAction.class.getName(), message);
                     }

                     switch (level) {
                        case "Alert":
                           logger.alert(message);
                           break;
                        case "Critical":
                           logger.critical(message);
                           break;
                        case "Emergency":
                           logger.emergency(message);
                           break;
                        case "Error":
                           logger.error(message);
                           break;
                        case "Info":
                           logger.info(message);
                           break;
                        case "Notice":
                           logger.notice(message);
                           break;
                        case "Warning":
                           logger.warning(message);
                           break;
                        default:
                           throw new InternalError(txtFmt.getLogActionUnknownLogLevelText(level));
                     }
                  } finally {
                     if (evaluator != null) {
                        this.evalFactory.destroyEvaluator(evaluator);
                     }

                  }

               }
            }
         }
      } else {
         throw new IllegalArgumentException(txtFmt.getInvalidLogActionConfig());
      }
   }

   void bindWatchData(ExpressionEvaluator evaluator, Map watchData) {
      if (watchData != null) {
         DiagnosticsELContext elContext = evaluator.getELContext();
         Iterator var4 = watchData.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            Object value = entry.getValue() == null ? "" : entry.getValue();
            String key = (String)entry.getKey();
            elContext.bind(key, value);
         }
      }

   }
}
