package weblogic.rjvm;

import java.io.BufferedReader;
import java.io.IOException;

public class TransportUtils {
   public static BootstrapResult readBootstrapParams(BufferedReader br) throws IOException {
      BootstrapResult result = new BootstrapResult();
      int abbrevSize = MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE;
      int headerLen = 19;
      int peerChannelMaxMessageSize = -1;
      boolean upgradeRequested = false;

      String line;
      while((line = br.readLine()) != null && line.length() != 0) {
         if (line.charAt(0) == "AS".charAt(0) && line.charAt(1) == "AS".charAt(1)) {
            try {
               int incomingAbbrevSize = Integer.parseInt(line.substring(line.indexOf(58) + 1, line.length()));
               abbrevSize = Math.min(abbrevSize, incomingAbbrevSize);
            } catch (Exception var10) {
               result.setInvalidLine(line);
               return result;
            }
         } else if (line.charAt(0) == "HL".charAt(0) && line.charAt(1) == "HL".charAt(1)) {
            try {
               headerLen = Integer.parseInt(line.substring(line.indexOf(58) + 1, line.length()));
            } catch (Exception var9) {
               result.setInvalidLine(line);
               return result;
            }
         } else if (line.charAt(0) == "MS".charAt(0) && line.charAt(1) == "MS".charAt(1)) {
            try {
               peerChannelMaxMessageSize = Integer.parseInt(line.substring(line.indexOf(58) + 1, line.length()));
            } catch (Exception var8) {
               result.setInvalidLine(line);
               return result;
            }
         } else if (line.charAt(0) == "PN".charAt(0) && line.charAt(1) == "PN".charAt(1)) {
            result.setPartitionName(line.substring(line.indexOf(58) + 1, line.length()));
         } else if (line.charAt(0) == "UP".charAt(0) && line.charAt(1) == "UP".charAt(1)) {
            upgradeRequested = Boolean.valueOf(line.substring(line.indexOf(58) + 1, line.length()));
         }
      }

      result.setAbbrevSize(abbrevSize);
      result.setHeaderLength(headerLen);
      result.setPeerChannelMaxMessageSize(peerChannelMaxMessageSize);
      result.setUpgradeRequested(upgradeRequested);
      return result;
   }

   public static int getVersionIntValue(String releaseVersion) {
      return Integer.parseInt(releaseVersion.replaceAll("\\.", ""));
   }

   public static final class BootstrapResult {
      private int abbrevSize;
      private int headerLength;
      private int peerChannelMaxMessageSize;
      private String invalidLine;
      private String partitionName;
      private boolean upgradeRequested = false;

      public void setUpgradeRequested(boolean upgradeRequested) {
         this.upgradeRequested = upgradeRequested;
      }

      public boolean isUpgradeRequested() {
         return this.upgradeRequested;
      }

      public boolean isSuccess() {
         return this.invalidLine == null;
      }

      public String getInvalidLine() {
         return this.invalidLine;
      }

      void setInvalidLine(String line) {
         this.invalidLine = line;
      }

      public int getAbbrevSize() {
         return this.abbrevSize;
      }

      void setAbbrevSize(int abbrevSize) {
         this.abbrevSize = abbrevSize;
      }

      public int getHeaderLength() {
         return this.headerLength;
      }

      void setHeaderLength(int headerLength) {
         this.headerLength = headerLength;
      }

      public int getPeerChannelMaxMessageSize() {
         return this.peerChannelMaxMessageSize;
      }

      void setPeerChannelMaxMessageSize(int peerChannelMaxMessageSize) {
         this.peerChannelMaxMessageSize = peerChannelMaxMessageSize;
      }

      public String getPartitionName() {
         return this.partitionName;
      }

      public void setPartitionName(String partitionName) {
         this.partitionName = partitionName;
      }
   }
}
