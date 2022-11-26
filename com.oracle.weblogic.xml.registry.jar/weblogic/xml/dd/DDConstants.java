package weblogic.xml.dd;

import java.util.Locale;

public final class DDConstants {
   public static final String J2EE12_EAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN";
   public static final String J2EE12_EAR_SYSTEM_ID = "http://java.sun.com/j2ee/dtds/application_1_2.dtd";
   public static final String J2EE13_EAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN";
   public static final String J2EE13_EAR_SYSTEM_ID = "http://java.sun.com/dtd/application_1_3.dtd";
   public static final String J2EE12_EAR_LOADER_CLASS = "weblogic.j2ee.dd.xml.J2EEDeploymentDescriptorLoader_J2EE12";
   public static final String J2EE13_EAR_LOADER_CLASS = "weblogic.j2ee.dd.xml.J2EEDeploymentDescriptorLoader_J2EE13";
   public static final String WLSAPP_EAR_LOADER_CLASS_700 = "weblogic.j2ee.dd.xml.WebLogicApplication_1_0";
   public static final String WLSAPP_EAR_LOADER_CLASS_810 = "weblogic.j2ee.dd.xml.WebLogicApplication_2_0";
   public static final String WLSAPP_EAR_LOADER_CLASS_900 = "weblogic.j2ee.dd.xml.WebLogicApplication_3_0";
   public static final String J2EE12_EAR_LOCAL_DTD_NAME = "application_1_2.dtd";
   public static final String WLSAPP_EAR_PUBLIC_ID_700 = "-//BEA Systems, Inc.//DTD WebLogic Application 7.0.0//EN";
   public static final String WLSAPP_EAR_PUBLIC_ID_810 = "-//BEA Systems, Inc.//DTD WebLogic Application 8.1.0//EN";
   public static final String WLSAPP_EAR_PUBLIC_ID_900 = "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN";
   public static final String WLSAPP_EAR_SYSTEM_ID_700 = "http://www.bea.com/servers/wls700/dtd/weblogic-application_1_0.dtd";
   public static final String WLSAPP_EAR_SYSTEM_ID_810 = "http://www.bea.com/servers/wls810/dtd/weblogic-application_2_0.dtd";
   public static final String WLSAPP_EAR_SYSTEM_ID_900 = "http://www.bea.com/servers/wls900/dtd/weblogic-application_3_0.dtd";
   public static final String WLSAPP_EAR_LOCAL_DTD_NAME = "weblogic-application_3_0.dtd";
   public static final String WLSAPP_EAR_PUBLIC_DTD_NAME = "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN";
   public static final String J2EE_EAR_STD_DESCR = "META-INF/application.xml";
   public static final String J2EE_EAR_WL_DESCR = "META-INF/weblogic-application.xml";
   public static final String WEBLOGIC_APPLICATION_PUBLIC_ID = "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN";
   public static final String WEBLOGIC_APPLICATION_SYSTEM_ID = "http://www.bea.com/servers/wls900/dtd/weblogic-application_3_0.dtd";
   public static final String WEBLOGIC_APPLICATION_LOADER_CLASS_700 = "weblogic.j2ee.dd.xml.WebLogicApplication_1_0";
   public static final String WEBLOGIC_APPLICATION_LOADER_CLASS_810 = "weblogic.j2ee.dd.xml.WebLogicApplication_2_0";
   public static final String WEBLOGIC_APPLICATION_LOADER_CLASS_900 = "weblogic.j2ee.dd.xml.WebLogicApplication_3_0";
   public static final String WEBLOGIC_APPLICATION_LOCAL_DTD_NAME = "weblogic-application_3_0.dtd";
   public static final String WEBLOGIC_APPLICATION_STD_DESCR = "META-INF/weblogic-application.xml";
   public static final String WL_DOCTYPE = "<!DOCTYPE weblogic-application PUBLIC '-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN' 'http://www.bea.com/servers/wls900/dtd/weblogic-application_3_0.dtd'>\n";
   public static final String[] validPublicIds = new String[]{"-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN", "-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN", "-//BEA Systems, Inc.//DTD WebLogic Application 7.0.0//EN", "-//BEA Systems, Inc.//DTD WebLogic Application 8.1.0//EN", "-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN"};
   public static final String[] STD_DESCRIPTOR_PATHS;
   public static final String[] WLS_DESCRIPTOR_PATHS;

   static {
      STD_DESCRIPTOR_PATHS = new String[]{"META-INF/application.xml", "META-INF/application.xml".toLowerCase(Locale.US)};
      WLS_DESCRIPTOR_PATHS = new String[]{"META-INF/weblogic-application.xml", "META-INF/weblogic-application.xml".toLowerCase(Locale.US)};
   }
}
