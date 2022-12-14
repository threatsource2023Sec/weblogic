package weblogic.application;

import java.io.File;
import weblogic.application.descriptor.ApplicationDescriptorConstants;

public interface ApplicationConstants extends ApplicationDescriptorConstants {
   String APP_XML_URI = "META-INF/application.xml";
   String APP_CLIENT_URI = "META-INF/application-client.xml";
   String WL_APP_XML_URI = "META-INF/weblogic-application.xml";
   String WL_APP_CLIENT_URI = "META-INF/weblogic-application-client.xml";
   String PERMISSIONS_XML_URI = "META-INF/permissions.xml";
   String WL_EXT_XML_NAME = "weblogic-extension.xml";
   String WL_EXT_XML_URI = "META-INF/weblogic-extension.xml";
   String APP_INF = "APP-INF";
   String APP_INF_CLASSES = "APP-INF" + File.separator + "classes";
   String APP_INF_LIB = "APP-INF" + File.separator + "lib";
   String WEB_INF = "WEB-INF";
   String WEB_INF_CLASSES = "WEB-INF" + File.separator + "classes";
   String WEB_INF_LIB = "WEB-INF" + File.separator + "lib";
   String APP_LIB = "lib";
   String UNSET_APP_CONTEXT_ROOT = "__BEA_WLS_INTERNAL_UNSET_CONTEXT_ROOT";
   String APP_NAME_TOKEN = "${APPNAME}";
   String CONTAINER_DEBUGGER_NAME = "DebugAppContainer";
   String TOOLS_DEBUGGER_NAME = "DebugAppContainerTools";
   String FASTSWAP_DEBUGGER_NAME = "DebugClassRedef";
   String ANNOSCAN_VERBOSE_DEBUGGER_NAME = "DebugAppAnnoScanVerbose";
   String ANNOSCAN_DATA_DEBUGGER_NAME = "DebugAppAnnoScanData";
   String ANNOQUERY_VERBOSE_DEBUGGER_NAME = "DebugAppAnnoQueryVerbose";
   String ANNOQUERY_DEBUGGER_NAME = "DebugAppAnnoQuery";
   String ANNOLOOKUP_VERBOSE_DEBUGGER_NAME = "DebugAppAnnoVerboseLookup";
   String ANNOLOOKUP_DEBUGGER_NAME = "DebugAppAnnoLookup";
   String APP_TIMING_DEBUGGER_NAME = "DebugAppTiming";
   String APP_METADATACACHE_DEBUGGER_NAME = "DebugAppMetadataCache";
   String META_INF = "META-INF";
   String WL_INTERNAL_DIRECTORY = "META-INF/.WL_internal/";
   String GENERATED_OUTPUT_DIR = "META-INF/.WL_internal/generated/";
   String CACHE_DIR = "META-INF/.WL_internal/cache/";
   String GENERATED_OUTPUT_DIR_FOR_EAR = "META-INF/.WL_internal/generated/ear/";
   String CACHE_DIR_FOR_EAR = "META-INF/.WL_internal/cache/ear/";
   String sep = File.separator;
   String PER_DD_NAME = "persistence.xml";
   String PER_DD_URI = "META-INF/persistence.xml";
   String PER_DD_PATH = sep + "META-INF" + sep + "persistence.xml";
   String APPCLIENT_SUN_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN";
   String APPCLIENT_SUN_SYSTEM_ID = "http://java.sun.com/dtd/application-client_1_3.dtd";
   String APPCLIENT_SUN_LOCAL_ID = "application-client_1_3.dtd";
   String APPCLIENT_SUN_PUBLIC_ID12 = "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.2//EN";
   String APPCLIENT_SUN_SYSTEM_ID12 = "http://java.sun.com/j2ee/dtds/application-client_1_2.dtd";
   String APPCLIENT_SUN_LOCAL_ID12 = "application-client_1_2.dtd";
   String APPCLIENT_BEA_PUBLIC_ID = "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 J2EE Application Client//EN";
   String APPCLIENT_BEA_SYSTEM_ID = "http://www.bea.com/servers/wls700/dtd/weblogic-appclient.dtd";
   String APPCLIENT_BEA_LOCAL_ID = "weblogic-appclient.dtd";
   String APPCLIENT_BEA_PUBLIC_ID12 = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 J2EE Application Client//EN";
   String APPCLIENT_BEA_SYSTEM_ID12 = "http://www.bea.com/servers/wls600/dtd/weblogic-appclient.dtd";
   String APPCLIENT_BEA_LOCAL_ID12 = "weblogic-appclient12.dtd";
   String WEB_CLS_GEN_JAR_NAME = "_wl_cls_gen.jar";
}
