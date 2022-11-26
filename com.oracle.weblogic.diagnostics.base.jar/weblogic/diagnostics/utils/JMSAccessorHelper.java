package weblogic.diagnostics.utils;

public class JMSAccessorHelper {
   public static String getLogicalNameForJMSMessageLog(String jmsServer) {
      StringBuilder sb = new StringBuilder();
      sb.append("JMSMessageLog").append("/").append(jmsServer);
      return sb.toString();
   }

   public static String getLogicalNameForJMSSAFMessageLog(String safAgent) {
      StringBuilder sb = new StringBuilder();
      sb.append("JMSSAFMessageLog").append("/").append(safAgent);
      return sb.toString();
   }
}
