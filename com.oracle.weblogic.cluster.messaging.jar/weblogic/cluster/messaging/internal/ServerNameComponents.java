package weblogic.cluster.messaging.internal;

import java.io.Serializable;

public class ServerNameComponents implements Comparable, Serializable {
   private static final long serialVersionUID = -23435234656756L;
   public static final String ENABLE_ORDINAL_GROUPING = "enableOrdinalGrouping";
   public static final int MAX_SUPPORT_LEN = Integer.toString(Integer.MAX_VALUE).length() - 1;
   private static final boolean enableOrdinalGrouping = Boolean.parseBoolean(System.getProperty("enableOrdinalGrouping", "true"));
   static final int BASE_OF_CALCULATED_SEQ_NO = 1000;
   private final String serverName;
   private final String prefix;
   private final int seqNo;

   private ServerNameComponents(String serverName, String prefix, int seqNo) {
      this.serverName = serverName;
      this.prefix = prefix;
      this.seqNo = seqNo;
   }

   public int compareTo(Object other) {
      assert other instanceof ServerNameComponents;

      ServerNameComponents otherInfo = (ServerNameComponents)other;
      int result = this.prefix.compareTo(otherInfo.prefix);
      if (result == 0) {
         result = Integer.compare(this.seqNo, otherInfo.seqNo);
         if (result == 0) {
            result = this.serverName.compareTo(otherInfo.serverName);
         }
      }

      return result;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public int getSeqNo() {
      return this.seqNo;
   }

   public static ServerNameComponents parseServername(String serverName) {
      int seqNo;
      if (enableOrdinalGrouping) {
         int i;
         for(i = serverName.length() - 1; i >= 0; --i) {
            char ch = serverName.charAt(i);
            if (!Character.isDigit(ch)) {
               break;
            }
         }

         if (i < serverName.length() - 1) {
            if (serverName.length() - i - 1 > MAX_SUPPORT_LEN) {
               i = serverName.length() - MAX_SUPPORT_LEN - 1;
            }

            String prefix;
            if (i == -1) {
               prefix = "";
               seqNo = Integer.parseInt(serverName);
            } else {
               prefix = serverName.substring(0, i + 1);
               seqNo = Integer.parseInt(serverName.substring(i + 1));
            }

            return new ServerNameComponents(serverName, prefix, seqNo);
         }
      }

      seqNo = calculateGroupNoFromServerName(serverName);
      return new ServerNameComponents(serverName, serverName, seqNo);
   }

   private static int calculateGroupNoFromServerName(String serverName) {
      int num = 0;
      byte[] var2 = serverName.getBytes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte b = var2[var4];
         num += b;
      }

      num = 1000 + Math.abs(num);
      return num;
   }
}
