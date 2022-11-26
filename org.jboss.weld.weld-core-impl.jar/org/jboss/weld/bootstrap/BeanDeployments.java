package org.jboss.weld.bootstrap;

class BeanDeployments {
   static String getFinalId(String beanArchiveId, String delimiter) {
      if (delimiter.isEmpty()) {
         return beanArchiveId;
      } else {
         int idx = beanArchiveId.indexOf(delimiter);
         if (idx < 0) {
            return beanArchiveId;
         } else {
            String beforeDelimiter = beanArchiveId.substring(0, idx);
            int suffixIdx = beanArchiveId.lastIndexOf(".");
            if (suffixIdx + 1 == idx + delimiter.length()) {
               suffixIdx = -1;
            }

            return suffixIdx < 0 ? beforeDelimiter : beforeDelimiter + beanArchiveId.substring(suffixIdx, beanArchiveId.length());
         }
      }
   }

   private BeanDeployments() {
   }
}
