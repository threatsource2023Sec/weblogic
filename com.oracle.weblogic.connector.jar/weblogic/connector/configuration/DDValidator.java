package weblogic.connector.configuration;

import java.io.File;
import java.util.zip.ZipEntry;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAConfigurationException;
import weblogic.j2ee.descriptor.wl.LinkRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorExtensionBean;
import weblogic.utils.jars.VirtualJarFile;

public class DDValidator {
   static void validateRARAndAltDD(VirtualJarFile vjar, File altDD) throws RAConfigurationException {
      boolean altDDSpecified = false;
      boolean raFileSpecified = false;
      boolean weblogicRAFileSpecified = false;
      String errorString = "";
      if (altDD != null) {
         altDDSpecified = true;
      }

      if (!altDDSpecified) {
         if (vjar == null) {
            errorString = Debug.getExceptionNoDescriptorOrAltDD();
         } else {
            ZipEntry wlraEntry = vjar.getEntry("META-INF/weblogic-ra.xml");
            ZipEntry raEntry = vjar.getEntry("META-INF/ra.xml");
            weblogicRAFileSpecified = wlraEntry != null;
            raFileSpecified = raEntry != null;
            if (!raFileSpecified && !weblogicRAFileSpecified) {
               errorString = Debug.getExceptionNoDescriptor();
            }
         }
      }

      if (errorString.length() > 0) {
         throw new RAConfigurationException(errorString);
      }
   }

   public static boolean isLinkRef(WeblogicConnectorExtensionBean wlBean) {
      if (wlBean == null) {
         return false;
      } else {
         LinkRefBean linkRefBean = wlBean.getLinkRef();
         if (linkRefBean == null) {
            return false;
         } else {
            String linkref = linkRefBean.getRaLinkRef();
            return linkref != null && linkref.trim().length() > 0;
         }
      }
   }
}
