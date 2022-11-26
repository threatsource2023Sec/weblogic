package weblogic.management.utils;

import java.net.MalformedURLException;
import java.net.URL;
import weblogic.management.provider.PropertyService;
import weblogic.utils.LocatorUtilities;

public class ManagementHelper {
   public static final String OAM_APPNAME = "bea_wls_management_internal2";
   public static final String NAME = "bea_wls_management_internal2/wl_management";
   public static final String REQUEST_TYPE = "wl_request_type";
   public static final String REQUEST_USERNAME = "username";
   public static final String REQUEST_PASSWORD = "password";
   public static final String MSI_REQUEST = "wl_managed_server_independence_request";
   public static final String MSI_REQUEST_FILE = "wl_managed_server_independence_request_filename";
   public static final String MSI_REQUEST_DOMAIN = "wl_managed_server_independence_request_domain";
   public static final String XML_ENTITY_REQUEST = "wl_xml_entity_request";
   public static final String XML_ENTITY_PATH = "xml-entity-path";
   public static final String XML_REGISTRY_NAME = "xml-registry-name";
   public static final String INIT_REPLICA_REQUEST = "wl_init_replica_request";
   public static final String INIT_REPLICA_SERVER_NAME = "init-replica_server-name";
   public static final String INIT_REPLICA_SERVER_URL = "init-replica_server-url";
   public static final String INIT_REPLICA_VALIDATE = "init-replica-validate";

   public static URL getURL() throws MalformedURLException {
      String httpURL = ((PropertyService)LocatorUtilities.getService(PropertyService.class)).getAdminHttpUrl();
      if (!httpURL.endsWith("/")) {
         httpURL = httpURL + "/";
      }

      return new URL(httpURL + "bea_wls_management_internal2/wl_management");
   }
}
