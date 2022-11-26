package com.oracle.tyrus.fallback.wls;

import com.oracle.tyrus.fallback.spi.StickyNode;
import weblogic.protocol.LocalServerIdentity;

public class WLStickyNode implements StickyNode {
   private static String SECONDARY_SERVER_ID = "!NONE";
   private static String SPLITOR = "!";
   private String stickinessInfo = null;

   public String getStickinessInfo() {
      if (this.stickinessInfo == null) {
         this.stickinessInfo = SPLITOR + String.valueOf(LocalServerIdentity.getIdentity().hashCode()) + SECONDARY_SERVER_ID;
      }

      return this.stickinessInfo;
   }
}
