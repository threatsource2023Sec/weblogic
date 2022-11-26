package weblogic.transaction.internal;

public final class VendorId {
   public static final int MQ_SERIES = 1;
   public static final int COMMS_ORDERBILLING_PRODUCER = 2;
   public static final int COMMS_SUBMITORDER_CONSUMER = 3;
   public static final int BRM = 4;
   public static final int ADAPTER = 5;
   public static final int MSG_BRIDGE = 6;
   public static final int IBO_PERSISTENCE_MANAGER = 7;
   public static final int LOCAL_RESOURCE_ASSIGNMENT_WA = 8;
   public static final int LOCAL_RESOURCE_ASSIGNMENT_WAMQ = 9;
   public static final int MISC = -1;
   private static final String MQ_SERIES_CLASSNAME = "com.ibm.mq.MQXAResource";
   private static final String MQ_SERIES_CLASSNAME2 = "com.ibm.mq.jmqi.JmqiXAResource";
   private static final String MQ_SERIES_WRAPPED_CLASSNAME = "weblogic.deployment.jms.WrappedXAResource_com_ibm_mq_MQXAResource";
   private static final String MQ_SERIES_WRAPPED_CLASSNAME2 = "weblogic.deployment.jms.WrappedXAResource_com_ibm_mq_jmqi_JmqiXAResource";
   private static final String MQ_SERIES_CLASSNAME3 = "com.bea.wli.sb.resources.mqconnection.OSBMQXAResource";
   private static final String ADAPTER_CLASS_NAME = "weblogic.connector.transaction.outbound.RecoveryOnlyXAWrapper";
   private static final String BRM_RESOURCE_NAME = "eis/BRM";
   private static final String COMMS_ORDERBILLING_PRODUCER_RESOURCE_NAME = "eis/wljms/COMMS_ORDERBILLING_PRODUCER";
   private static final String COMMS_SUBMITORDER_CONSUMER_RESOURCE_NAME = "eis/wljms/COMMS_SUBMITORDER_CONSUMER";
   private static final String MSG_BRIDGE_RESOURCE_NAME = "eis/jms/WLSConnectionFactoryJNDIXA";
   private static final String IBOPERSISTENCEMANAGER_RESOURCE_NAME = "eis/jdo/IBOPersistenceManager";
   private static final String RESOURCE_LIST_MQ;
   private static final String RESOURCE_LIST;

   public static int get(String className) {
      if (className.equals("com.ibm.mq.MQXAResource")) {
         return 1;
      } else if (className.equals("weblogic.deployment.jms.WrappedXAResource_com_ibm_mq_MQXAResource")) {
         return 1;
      } else if (className.equals("com.ibm.mq.jmqi.JmqiXAResource")) {
         return 1;
      } else if (className.equals("weblogic.deployment.jms.WrappedXAResource_com_ibm_mq_jmqi_JmqiXAResource")) {
         return 1;
      } else if (className.equals("com.bea.wli.sb.resources.mqconnection.OSBMQXAResource")) {
         return 1;
      } else if (className.equals("eis/BRM")) {
         return 4;
      } else if (className.equals("eis/wljms/COMMS_ORDERBILLING_PRODUCER")) {
         return 2;
      } else if (className.equals("eis/wljms/COMMS_SUBMITORDER_CONSUMER")) {
         return 3;
      } else if (className.startsWith("eis/jdo/IBOPersistenceManager")) {
         return 7;
      } else if (className.equals("weblogic.connector.transaction.outbound.RecoveryOnlyXAWrapper")) {
         return 5;
      } else if (className.startsWith("eis/jms/WLSConnectionFactoryJNDIXA")) {
         return 6;
      } else if (checkForVendorWorkaroundProperty(className, false)) {
         return 8;
      } else {
         return checkForVendorWorkaroundProperty(className, true) ? 9 : -1;
      }
   }

   public static String toString(int vid) {
      switch (vid) {
         case 1:
            return "MQ Series";
         case 2:
            return "Adapter COMMS_ORDERBILLING_PRODUCER";
         case 3:
            return "Adapter COMMS_SUBMITORDER_CONSUMER";
         case 4:
            return "Adapter BRM";
         case 5:
         default:
            return "";
         case 6:
            return "Adapter Message Bridge";
         case 7:
            return "Adapter IBOPERSISTENCEMANAGER";
         case 8:
            return "Adapter Local Resource Assignment WA";
         case 9:
            return "Adapter Local Resource Assignment WA for MQ";
      }
   }

   private static final boolean checkForVendorWorkaroundProperty(String resourceName, boolean mq) {
      String resourceList = RESOURCE_LIST;
      if (mq) {
         resourceList = RESOURCE_LIST_MQ;
      }

      if (resourceList != null && resourceList.length() != 0) {
         String[] result = resourceList.split("[,]");

         for(int i = 0; i < result.length; ++i) {
            if (resourceName.equals(result[i])) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   static {
      String propVal = System.getProperty("weblogic.transaction.localResAssignmentWAMQ");
      if (propVal != null) {
         propVal = propVal.replace("\"", "");
         propVal = propVal.replace("'", "");
      }

      RESOURCE_LIST_MQ = propVal;
      propVal = System.getProperty("weblogic.transaction.localResAssignmentWA");
      if (propVal != null) {
         propVal = propVal.replace("\"", "");
         propVal = propVal.replace("'", "");
      }

      RESOURCE_LIST = propVal;
   }
}
