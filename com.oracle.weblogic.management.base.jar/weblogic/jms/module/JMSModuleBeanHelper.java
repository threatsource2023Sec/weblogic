package weblogic.jms.module;

import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.utils.FileUtils;

public abstract class JMSModuleBeanHelper {
   public static final String INTEROP_APPLICATION_NAME = "interop-jms";
   private static final String DEFAULT_APPENDIX = "-jms.xml";

   public static String constructDefaultJMSSystemFilename(String name) {
      String lowerName = name.toLowerCase().trim();
      if (lowerName.endsWith("-jms")) {
         lowerName = lowerName.substring(0, lowerName.length() - 4);
      }

      String retVal = "jms/" + FileUtils.mapNameToFileName(lowerName) + "-jms.xml";
      return retVal;
   }

   public static DestinationBean findDestinationBean(String name, JMSBean module) {
      DestinationBean dbean = null;
      if ((dbean = module.lookupQueue(name)) == null) {
         dbean = module.lookupTopic(name);
      }

      return (DestinationBean)dbean;
   }
}
