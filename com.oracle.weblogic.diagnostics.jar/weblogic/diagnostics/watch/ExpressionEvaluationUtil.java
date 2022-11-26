package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.Notification;
import com.oracle.weblogic.diagnostics.expressions.ExpressionEvaluator;
import java.util.Iterator;

public final class ExpressionEvaluationUtil {
   public static void bindNotification(ExpressionEvaluator expressionEvaluator, Notification notif) {
      Iterator var2 = notif.keyList().iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         expressionEvaluator.getELContext().bind(key.toString(), notif.getValue(key));
      }

   }
}
