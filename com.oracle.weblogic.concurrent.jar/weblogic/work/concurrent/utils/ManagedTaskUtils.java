package weblogic.work.concurrent.utils;

import java.util.Map;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;

public class ManagedTaskUtils {
   public static boolean isLongRunning(Object userTask) {
      String hint = getTaskProperty(userTask, "javax.enterprise.concurrent.LONGRUNNING_HINT");
      return hint != null && hint.equalsIgnoreCase("true");
   }

   private static String getTaskProperty(Object userTask, String key) {
      if (userTask instanceof ManagedTask) {
         Map props = ((ManagedTask)userTask).getExecutionProperties();
         return props == null ? null : (String)props.get(key);
      } else {
         return null;
      }
   }

   public static ManagedTaskListener getTaskListener(Object userTask) {
      if (userTask instanceof ManagedTask) {
         ManagedTask managedTask = (ManagedTask)userTask;
         ManagedTaskListener listener = managedTask.getManagedTaskListener();
         if (listener != null) {
            return listener;
         }
      }

      return null;
   }

   public static boolean isCheckUserObject(Object userTask) {
      String hint = getTaskProperty(userTask, "weblogic.concurrent.CHECK_USER_OBJECT");
      return hint == null || !hint.equalsIgnoreCase("false");
   }

   public static boolean isCheckUserObject(Map props) {
      String hint = null;
      if (props != null) {
         hint = (String)props.get("weblogic.concurrent.CHECK_USER_OBJECT");
      }

      return hint == null || !hint.equalsIgnoreCase("false");
   }

   public static String getTaskName(Object userTask) {
      String name = getTaskProperty(userTask, "javax.enterprise.concurrent.IDENTITY_NAME");
      return name != null ? name : userTask.toString();
   }
}
