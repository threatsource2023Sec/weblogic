package weblogic.diagnostics.descriptor.validation;

import javax.el.ELException;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFScheduleBean;
import weblogic.diagnostics.descriptor.WLDFThreadDumpActionBean;
import weblogic.diagnostics.descriptor.WLDFWatchBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;
import weblogic.utils.LocatorUtilities;

public class WatchNotificationValidators {
   private static final WLDFDescriptorValidationTextTextFormatter txtFmt = WLDFDescriptorValidationTextTextFormatter.getInstance();
   private static final WatchValidatorService validator = (WatchValidatorService)LocatorUtilities.getService(WatchValidatorService.class);

   public static void validateWatch(WLDFWatchBean watch) {
      validator.validateWatch(watch);
   }

   public static void validateSchedule(WLDFScheduleBean schedule) {
      validator.validateSchedule(schedule);
   }

   public static void validateIncrementInterval(String expression) {
      validator.validateIncrementInterval(expression);
   }

   public static void validateWatchRule(String watchName, String ruleType, String ruleExp) {
      validator.validateWatchRule(watchName, ruleType, ruleExp);
   }

   public static void validateWatchRule(String watchName, String ruleType, String ruleExp, String expressionLanguage) {
      validator.validateWatchRule(watchName, ruleType, ruleExp, expressionLanguage);
   }

   public static void validateELExpression(String ruleType, String ruleExp) throws ELException {
      validator.validateELExpression(ruleType, ruleExp);
   }

   public static void validateActionBean(WLDFActionBean actionBean) {
      validator.validateActionBean(actionBean);
   }

   public static void validateRecipients(String[] recipients) {
      validator.validateRecipients(recipients);
   }

   public static void validateWatchNotificationBean(WLDFWatchNotificationBean watchNotif) {
      validator.validateWatchNotificationBean(watchNotif);
   }

   public static void validateExpressionLanguage(WLDFWatchBean bean, String language) {
      validator.validateExpressionLanguage(bean, language);
   }

   public static String getExpressionLanguageDefault(WLDFWatchBean bean) {
      return validator.getExpressionLanguageDefault(bean);
   }

   public static void validateTheadDumpAction(WLDFThreadDumpActionBean action) {
      int totalRequiredExecTime = action.getThreadDumpCount() * action.getThreadDumpDelaySeconds();
      int timeout = action.getTimeout();
      if (timeout > 0 && timeout <= totalRequiredExecTime) {
         throw new IllegalArgumentException(txtFmt.getIllegalThreadDumpTimeoutText(action.getName(), timeout, totalRequiredExecTime));
      }
   }
}
