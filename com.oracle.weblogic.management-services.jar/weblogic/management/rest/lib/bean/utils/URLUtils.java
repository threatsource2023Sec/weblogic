package weblogic.management.rest.lib.bean.utils;

import java.net.URL;
import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class URLUtils {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String reHostToLocalServer(String url) throws Exception {
      return reHost(url, getLocalServerUrl());
   }

   public static String reHost(String url, String serverUrl) throws Exception {
      String path = (new URL(url)).getPath();
      int idx = path.indexOf(getContextRoot());
      String restPath = path.substring(idx);
      return serverUrl + "/" + restPath;
   }

   private static String getLocalServerUrl() throws Exception {
      String localServerName = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getName();
      String partitionName = PartitionUtils.getPartitionName();
      weblogic.management.utils.PartitionUtils.URLInfo urlInfo = weblogic.management.utils.PartitionUtils.findPartitionHttpURL(partitionName, localServerName);
      if (urlInfo == null) {
         throw new AssertionError("findPartitionHttpURL returned null for local server: " + partitionName + " " + localServerName);
      } else {
         return urlInfo.getUrl();
      }
   }

   private static String getContextRoot() throws Exception {
      return "management";
   }
}
