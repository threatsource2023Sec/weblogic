package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.ExpressionExtensionsManager;
import com.oracle.weblogic.diagnostics.watch.actions.ActionsManager;
import javax.el.ELException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;

@Service
@Singleton
public class WatchExtensionsManager {
   @Inject
   private ActionsManager actionsManager;
   @Inject
   private ExpressionExtensionsManager extensionsManager;

   ExpressionExtensionsManager getExpressionExtensions() {
      return this.extensionsManager;
   }

   public static void validateWatchRuleExpression(String ruleType, String ruleExp) throws ELException {
      WatchNotificationValidators.validateELExpression(ruleType, "EL");
   }

   ActionsManager getActionsManager() {
      return this.actionsManager;
   }
}
