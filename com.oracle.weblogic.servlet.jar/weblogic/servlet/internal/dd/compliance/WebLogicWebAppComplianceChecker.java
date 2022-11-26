package weblogic.servlet.internal.dd.compliance;

import java.io.File;
import java.nio.charset.Charset;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.CharsetMappingBean;
import weblogic.j2ee.descriptor.wl.CharsetParamsBean;
import weblogic.j2ee.descriptor.wl.InputCharsetBean;
import weblogic.j2ee.descriptor.wl.SessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.VirtualDirectoryMappingBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.utils.ErrorCollectionException;

public class WebLogicWebAppComplianceChecker extends BaseComplianceChecker {
   private static final String CHARSET_MAPPING = "charset-mapping";
   private static final String INPUT_CHARSET = "input-charset";
   private static final String URL_MATCH_MAP = "url-match-map";
   private static final String URL_MATCH_MAP_SUPERCLASS = "weblogic.servlet.utils.URLMapping";

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      WeblogicWebAppBean ext = info.getWeblogicWebAppBean();
      if (ext != null) {
         CharsetParamsBean charset = (CharsetParamsBean)DescriptorUtils.getOrCreateFirstChild(ext, ext.getCharsetParams(), "CharsetParams");
         if (charset != null) {
            this.validateCharsetMapping(charset.getCharsetMappings());
            this.validateInputCharset(charset.getInputCharsets());
         }

         VirtualDirectoryMappingBean[] virtual = ext.getVirtualDirectoryMappings();
         this.validateVirtualDirectoryMappings(virtual);
         this.validateURLMatchMap(ext.getUrlMatchMaps().length > 0 ? ext.getUrlMatchMaps()[0] : null, info);
         this.validateSessionDescriptor((SessionDescriptorBean)DescriptorUtils.getOrCreateFirstChild(ext, ext.getSessionDescriptors(), "SessionDescriptor"));
         this.checkForExceptions();
      }
   }

   private void validateCharsetMapping(CharsetMappingBean[] maps) {
      if (maps != null) {
         for(int i = 0; i < maps.length; ++i) {
            CharsetMappingBean charset = maps[i];
            String javaCharsetName = charset.getJavaCharsetName();
            if (!Charset.isSupported(javaCharsetName)) {
               this.update(this.fmt.warning() + this.fmt.UNSUPPORTED_ENCODING("charset-mapping", javaCharsetName));
            }
         }

      }
   }

   private void validateInputCharset(InputCharsetBean[] inputs) {
      if (inputs != null) {
         for(int i = 0; i < inputs.length; ++i) {
            String charsetName = inputs[i].getJavaCharsetName();
            if (!Charset.isSupported(charsetName)) {
               this.update(this.fmt.warning() + this.fmt.UNSUPPORTED_ENCODING("input-charset", charsetName));
            }
         }

      }
   }

   private void validateVirtualDirectoryMappings(VirtualDirectoryMappingBean[] virtual) {
      if (virtual != null) {
         for(int i = 0; i < virtual.length; ++i) {
            String localPath = virtual[i].getLocalPath();
            if (localPath != null) {
               File f = new File(localPath);
               if (!f.exists()) {
                  this.update(this.fmt.warning() + this.fmt.INVALID_LOCAL_PATH(localPath));
               }
            } else {
               this.update(this.fmt.warning() + this.fmt.INVALID_LOCAL_PATH(localPath));
            }
         }

      }
   }

   private void validateURLMatchMap(String className, DeploymentInfo info) {
      ClassLoader cl = info.getClassLoader();
      if (className != null) {
         this.isClassAssignable(cl, "url-match-map", className, "weblogic.servlet.utils.URLMapping");
      }

   }

   private void validateSessionDescriptor(SessionDescriptorBean sd) {
      if (sd != null) {
         String cookieDomain = sd.getCookieDomain();
         if (cookieDomain != null && cookieDomain != null && !cookieDomain.startsWith(".")) {
            this.update(this.fmt.warning() + this.fmt.INVALID_COOKIE_DOMAIN(cookieDomain));
         }

      }
   }
}
