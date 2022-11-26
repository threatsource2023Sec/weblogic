package com.oracle.wls.shaded.org.apache.xalan.res;

import com.oracle.wls.shaded.org.apache.xpath.res.XPATHMessages;
import java.util.ListResourceBundle;

public class XSLMessages extends XPATHMessages {
   private static ListResourceBundle XSLTBundle = null;
   private static final String XSLT_ERROR_RESOURCES = "com.oracle.wls.shaded.org.apache.xalan.res.XSLTErrorResources";

   public static final String createMessage(String msgKey, Object[] args) {
      if (XSLTBundle == null) {
         XSLTBundle = loadResourceBundle("com.oracle.wls.shaded.org.apache.xalan.res.XSLTErrorResources");
      }

      return XSLTBundle != null ? createMsg(XSLTBundle, msgKey, args) : "Could not load any resource bundles.";
   }

   public static final String createWarning(String msgKey, Object[] args) {
      if (XSLTBundle == null) {
         XSLTBundle = loadResourceBundle("com.oracle.wls.shaded.org.apache.xalan.res.XSLTErrorResources");
      }

      return XSLTBundle != null ? createMsg(XSLTBundle, msgKey, args) : "Could not load any resource bundles.";
   }
}
