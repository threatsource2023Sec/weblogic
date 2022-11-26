package weblogic.j2ee.descriptor.wl.validators;

import weblogic.j2ee.descriptor.wl.OperationPolicyBean;
import weblogic.j2ee.descriptor.wl.OwsmSecurityPolicyBean;
import weblogic.j2ee.descriptor.wl.PortPolicyBean;
import weblogic.j2ee.descriptor.wl.WebservicePolicyRefBean;
import weblogic.j2ee.descriptor.wl.WsPolicyBean;

public class WseePolicyBeanValidator {
   public static final String DIRECTION_BOTH = "both";
   public static final String DIRECTION_INBOUND = "inbound";
   public static final String DIRECTION_OUTBOUND = "outbound";
   private static final String[] DIRECTIONS = new String[]{"both", "inbound", "outbound"};
   public static final String STATUS_ENABLED = "enabled";
   public static final String STATUS_DISABLED = "disabled";
   public static final String STATUS_DELETED = "deleted";
   public static final String[] STATUS = new String[]{"enabled", "disabled", "deleted"};

   public static void validateDirection(String direction) throws IllegalArgumentException {
      for(int i = 0; i < DIRECTIONS.length; ++i) {
         if (DIRECTIONS[i].equals(direction)) {
            return;
         }
      }

      throw new IllegalArgumentException(direction + " is not a valid direction. It must be: " + "both" + ", " + "inbound" + ",  or " + "outbound");
   }

   public static void validateUri(String uriStr) {
      if (uriStr.startsWith("policy:")) {
         uriStr = uriStr.substring(7);
      }

      String[] parts = uriStr.split("/");

      for(int i = 0; i < parts.length; ++i) {
         if (!checkUriPart(parts[i])) {
            throw new IllegalArgumentException(uriStr + " is not a valid URI.");
         }
      }

   }

   private static boolean checkUriPart(String part) {
      boolean isvalid = false;
      if (part != null && !part.isEmpty()) {
         char c = part.charAt(0);
         if (Character.isJavaIdentifierStart(c) || Character.isDigit(c) || '.' == c || '#' == c) {
            isvalid = true;
         }

         if (isvalid) {
            for(int i = 1; i < part.length(); ++i) {
               c = part.charAt(i);
               if (!Character.isJavaIdentifierPart(c) && ' ' != c && '-' != c && '.' != c) {
                  isvalid = false;
                  break;
               }
            }
         }
      }

      return isvalid;
   }

   public static void validateStatus(String status) {
      for(int i = 0; i < STATUS.length; ++i) {
         if (STATUS[i].equals(status)) {
            return;
         }
      }

      throw new IllegalArgumentException(status + " is not a valid status. It must be: " + "enabled" + ", " + "disabled" + ",  or " + "deleted");
   }

   public static void checkDuplicatedPolicyForPort(WebservicePolicyRefBean bean, String portName, String policyUri) throws IllegalArgumentException {
      PortPolicyBean[] ppbs = bean.getPortPolicy();

      for(int i = 0; ppbs != null && i < ppbs.length; ++i) {
         if (ppbs[i].getPortName().equals(portName)) {
            WsPolicyBean[] wsb = ppbs[i].getWsPolicy();
            if (policyExists(wsb, policyUri, (String)null)) {
               throw new IllegalArgumentException(policyUri + " has already been attached to port " + portName);
            }
         }
      }

   }

   public static void checkDuplicatedOwsmSecurityPolicyForPort(WebservicePolicyRefBean bean, String portName, String policyUri) throws IllegalArgumentException {
      PortPolicyBean[] ppbs = bean.getPortPolicy();

      for(int i = 0; ppbs != null && i < ppbs.length; ++i) {
         if (ppbs[i].getPortName().equals(portName)) {
            OwsmSecurityPolicyBean[] owsmSecurityPolicyBeans = ppbs[i].getOwsmSecurityPolicy();
            if (owsmSecurityPolicyExists(owsmSecurityPolicyBeans, policyUri)) {
               throw new IllegalArgumentException(policyUri + " has already been attached to port " + portName);
            }
         }
      }

   }

   public static void checkDuplidatePolicyFroOperation(WebservicePolicyRefBean bean, String operationName, String policyUri, String direction, String linkName) throws IllegalArgumentException {
      OperationPolicyBean[] opbs = bean.getOperationPolicy();
      if (opbs != null && opbs.length > 0) {
         for(int i = 0; i < opbs.length; ++i) {
            String opNameInDD = opbs[i].getOperationName();
            String linkNameInDD = opbs[i].getServiceLink();
            if ((isEmpty(linkNameInDD) || !isEmpty(linkNameInDD) && linkNameInDD.equals(linkName)) && opNameInDD.equals(operationName)) {
               WsPolicyBean[] wsb = opbs[i].getWsPolicy();
               if (policyExists(wsb, policyUri, direction)) {
                  throw new IllegalArgumentException(policyUri + " has already been attached to operation " + operationName);
               }
            }
         }
      }

   }

   public static boolean policyExists(WsPolicyBean[] beans, String policyUri, String direction) {
      if (beans == null) {
         return false;
      } else {
         for(int i = 0; i < beans.length; ++i) {
            WsPolicyBean bean = beans[i];
            if (policyUri.equals(bean.getUri())) {
               if (!bean.getStatus().equals("enabled")) {
                  return false;
               }

               String directionFromBean = bean.getDirection();
               if (direction != null && directionFromBean != null) {
                  if (direction.equals(directionFromBean)) {
                     return true;
                  }

                  if (!direction.equals("both") && !directionFromBean.equals("both")) {
                     continue;
                  }

                  return true;
               }

               return true;
            }
         }

         return false;
      }
   }

   public static boolean owsmSecurityPolicyExists(OwsmSecurityPolicyBean[] beans, String policyUri) {
      if (beans == null) {
         return false;
      } else if (null == policyUri) {
         throw new IllegalArgumentException("Null OWSM Policy found.");
      } else {
         for(int i = 0; i < beans.length; ++i) {
            OwsmSecurityPolicyBean bean = beans[i];
            if (policyUri.equals(bean.getUri())) {
               if (!bean.getStatus().equals("enabled")) {
                  return false;
               }

               return true;
            }
         }

         return false;
      }
   }

   private static boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }
}
