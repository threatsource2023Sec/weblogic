package weblogic.deploy.api.internal.utils;

import java.io.File;
import java.util.StringTokenizer;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.logging.Loggable;

public class ConfigHelperLowLevel {
   public static File getConfigRootFile(DeploymentPlanBean plan) {
      return plan != null && plan.getConfigRoot() != null ? new File(plan.getConfigRoot()) : null;
   }

   public static String getText(DDBean bean) {
      String t = bean.getText();
      return t == null ? "" : t;
   }

   public static String getNSPrefix(DDBean ddb, String namespace) {
      if (ddb instanceof DDBeanRoot) {
         String text = ddb.getText();
         if (text == null) {
            return null;
         } else {
            int ndxNS = text.indexOf(namespace);
            if (ndxNS == -1) {
               return null;
            } else {
               int ndxXmlns = text.indexOf("xmlns");
               if (ndxXmlns == -1) {
                  return null;
               } else {
                  return text.charAt(ndxXmlns + "xmlns".length()) == '=' ? null : text.substring(ndxXmlns + "xmlns".length() + 1, ndxNS - 2);
               }
            }
         }
      } else {
         return getNSPrefix(ddb.getRoot(), namespace);
      }
   }

   public static String applyNamespace(String ns, String xpath) {
      if (ns != null) {
         StringTokenizer st = new StringTokenizer(xpath, "/");
         StringBuffer sb = new StringBuffer();

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.indexOf(":") == -1) {
               sb.append(ns + ":");
            }

            sb.append(token);
            if (st.hasMoreTokens()) {
               sb.append("/");
            }
         }

         return sb.toString();
      } else {
         return xpath;
      }
   }

   public static void beanWalker(DDBeanRoot bean, DConfigBeanRoot dcb) throws ConfigurationException {
      checkParam("DDBeanRoot", bean);
      checkParam("DConfigBeanRoot", dcb);
      beanWalker((DDBean)bean, (DConfigBean)dcb);
   }

   private static void beanWalker(DDBean bean, DConfigBean dcb) throws ConfigurationException {
      if (dcb != null) {
         String[] xpaths = dcb.getXpaths();
         if (xpaths != null) {
            for(int i = 0; i < xpaths.length; ++i) {
               DDBean[] beans = bean.getChildBean(xpaths[i]);
               if (beans != null) {
                  for(int j = 0; j < beans.length; ++j) {
                     DConfigBean configBean = dcb.getDConfigBean(beans[j]);
                     beanWalker(beans[j], configBean);
                  }
               }
            }

         }
      }
   }

   public static void checkParam(String paramName, Object param) {
      if (param == null) {
         Loggable l = SPIDeployerLogger.logNullParamLoggable(paramName);
         l.log();
         throw new IllegalArgumentException(l.getMessage());
      }
   }
}
