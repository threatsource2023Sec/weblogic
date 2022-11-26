package weblogic.diagnostics.descriptor.validation;

import org.jvnet.hk2.annotations.Contract;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFScheduleBean;
import weblogic.diagnostics.descriptor.WLDFWatchBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;

@Contract
public interface WatchValidatorService {
   void validateActionBean(WLDFActionBean var1);

   void validateELExpression(String var1, String var2);

   void validateIncrementInterval(String var1);

   void validateSchedule(WLDFScheduleBean var1);

   void validateWatch(WLDFWatchBean var1);

   void validateRecipients(String[] var1);

   void validateWatchNotificationBean(WLDFWatchNotificationBean var1);

   void validateWatchRule(String var1, String var2, String var3);

   void validateWatchRule(String var1, String var2, String var3, String var4);

   void validateExpressionLanguage(WLDFWatchBean var1, String var2);

   String getExpressionLanguageDefault(WLDFWatchBean var1);
}
