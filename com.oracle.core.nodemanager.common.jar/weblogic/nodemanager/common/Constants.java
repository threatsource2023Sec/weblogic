package weblogic.nodemanager.common;

public interface Constants {
   String SERVER_TYPE_WEBLOGIC = "WebLogic";
   String SERVER_TYPE_COHERENCE = "Coherence";
   int LISTEN_PORT = 5556;
   int PLAIN_LISTEN_PORT = 5556;
   String DEFAULT_CONTEXT_PROTOCOL = "TLS";
   String DEFAULT_IBM_PROTOCOL = "SSL_TLS";
   String VENDOR_PROP = "java.vendor";
   String IBM_VENDOR = "IBM";
   String SERVICE_ENABLED_PROP = "weblogic.nodemanager.ServiceEnabled";
   String NATIVE_ROTATION_ENABLED = "weblogic.nmservice.RotationEnabled";
   String SERVICE_FINDER_PACKAGES = "weblogic.nodemanager.server.jersey.servicefinder.packages";
   String SERVER_MODE_CLASSIC = "CLASSIC";
   String SERVER_MODE_REST = "REST";
   String DIR_PATH_PREFIX = "dirPath=";
}
