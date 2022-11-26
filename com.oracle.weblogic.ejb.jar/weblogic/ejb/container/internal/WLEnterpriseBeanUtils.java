package weblogic.ejb.container.internal;

import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.kernel.ResettableThreadLocal;
import weblogic.kernel.ThreadLocalInitialValue;

public final class WLEnterpriseBeanUtils {
   private static final Map ALLOWED_ACTIONS = new HashMap();

   private WLEnterpriseBeanUtils() {
   }

   public static int getCurrentState(Object bean) {
      int currentState = 0;
      if (bean != null) {
         currentState = ((WLEnterpriseBean)bean).__WL_getMethodState();
      } else {
         Object invState = AllowedMethodsHelper.getMethodInvocationState();
         if (invState != null) {
            currentState = (Integer)invState;
         }
      }

      return currentState;
   }

   static String getEJBStateAsString(int ejbState) {
      switch (ejbState) {
         case 1:
            return "context setting";
         case 2:
            return "context unsetting";
         case 4:
            return "ejbCreate";
         case 8:
            return "ejbPostCreate";
         case 16:
            return "ejbRemove";
         case 32:
            return "ejbActivate";
         case 64:
            return "ejbPassivate";
         case 128:
            return "running business method";
         case 256:
            return "after begining business method";
         case 512:
            return "before completing business method";
         case 1024:
            return "after completing business method";
         case 2048:
            return "finder method";
         case 4096:
            return "select method";
         case 8192:
            return "home method";
         case 16384:
            return "ejbLoad";
         case 32768:
            return "ejbStore";
         case 65536:
            return "ejbTimeout";
         case 131072:
            return "webservice business method";
         case 262144:
            return "singleton session bean lifecycle callback method";
         case 524288:
            return "stateful session bean lifecycle callback method";
         default:
            throw new IllegalArgumentException("Unknown bean state " + ejbState);
      }
   }

   private static void addAllowedAction(int intKey, String actionMessage) {
      Integer key = intKey;
      String existingActionMessage = (String)ALLOWED_ACTIONS.get(key);
      String msgToPut = null;
      if (existingActionMessage == null) {
         msgToPut = "'" + actionMessage + "'";
      } else {
         msgToPut = existingActionMessage + " OR '" + actionMessage + "'";
      }

      ALLOWED_ACTIONS.put(key, msgToPut);
   }

   static String getEJBOperationAsString(int operation) {
      return (String)ALLOWED_ACTIONS.get(operation);
   }

   public static ResettableThreadLocal newSingletonBeanState() {
      return new ResettableThreadLocal(new ThreadLocalInitialValue() {
         protected Object initialValue() {
            return 0;
         }
      });
   }

   static {
      addAllowedAction(782335, "getting ejb home");
      addAllowedAction(526324, "getting caller principal for the Stateful EJB");
      addAllowedAction(196736, "getting caller principal for the Stateless EJB");
      addAllowedAction(196736, "getting caller principal for the Singleton EJB");
      addAllowedAction(125084, "getting caller principal for Entity EJB");
      addAllowedAction(526324, "is caller in role for Stateful EJB");
      addAllowedAction(196736, "is caller in role for Stateless EJB");
      addAllowedAction(196736, "is caller in role for Singleton EJB");
      addAllowedAction(125084, "is caller in role for Entity EJB");
      addAllowedAction(722932, "getting EJB object for Session bean");
      addAllowedAction(114936, "getting EJB object for Entity bean");
      addAllowedAction(983936, "getting rollback only for session bean");
      addAllowedAction(125084, "getting rollback only for entity bean");
      addAllowedAction(983936, "setting rollback only for session bean");
      addAllowedAction(125084, "setting rollback only for entity bean");
      addAllowedAction(983284, "getting the user transaction");
      addAllowedAction(114936, "getting the primary key");
      addAllowedAction(516348, "getting the Timer Service");
      addAllowedAction(131072, "getting the Message Context for Stateless EJB");
      addAllowedAction(128, "getting the invoked business interface for Session Bean");
      addAllowedAction(128, "getting the was cancelled value for Session Bean");
   }
}
