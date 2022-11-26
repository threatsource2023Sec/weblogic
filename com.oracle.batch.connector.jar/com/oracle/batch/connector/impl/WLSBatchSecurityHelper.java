package com.oracle.batch.connector.impl;

import com.ibm.jbatch.spi.BatchSecurityHelper;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

public class WLSBatchSecurityHelper implements BatchSecurityHelper {
   private static final String UNKNOWN_ELEMENT = " ";
   private static final String UNKNOWN_TAG = " : : ";

   public String getCurrentTag() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return cic == null ? " : : " : cic.getPartitionId() + ":" + cic.getApplicationId() + ":" + cic.getApplicationName();
   }

   public boolean isAdmin(String tag) {
      return BatchConfigBeanHelper.getAdminCallerPrefix() != null || " : : ".equals(tag);
   }

   static String extractPartitionId(String tag) {
      return tag == null ? " " : tag.split(":")[0];
   }

   static String extractApplicationId(String tag) {
      String[] tags = tag == null ? " ".split(":") : tag.split(":");
      return tags.length >= 1 ? tags[1] : " ";
   }

   static String extractApplicationName(String tag) {
      String[] tags = tag == null ? " ".split(":") : tag.split(":");
      return tags.length >= 2 ? tags[2] : " ";
   }
}
