package weblogic.upgrade;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.internal.DescriptorHelper;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.management.provider.internal.DescriptorManagerHelperContext;
import weblogic.management.upgrade.ConfigFileHelper;
import weblogic.upgrade.processors.UpgradeProcessor;
import weblogic.upgrade.processors.UpgradeProcessorContext;

public class Upgrade {
   private static String[] PROCESSORS = new String[]{"weblogic.upgrade.processors.config.ConfigUpgradeProcessor", "weblogic.upgrade.processors.config.SecurityUpgradeProcessor"};

   public static void upgradeDomain(String domainLocation, UpgradeContext ctx, Properties upgradeOptions) throws UpgradeException {
      try {
         DescriptorClassLoader.setIncludeSIP(true);
         if (System.getProperty("weblogic.management.convertSecurityExtensionSchema") == null) {
            System.setProperty("weblogic.management.convertSecurityExtensionSchema", "true");
         }

         ClassLoader descriptorClassLoader = DescriptorClassLoader.getClassLoader();
         Thread.currentThread().setContextClassLoader(descriptorClassLoader);
         if (domainLocation != null && ctx != null) {
            Logger logger = ctx.getLogger();
            UpgradeLogger upgradeLogger = new UpgradeLogger(Upgrade.class.getName(), logger, "weblogic.upgrade.UpgradeResourceBundle");
            UpgradeProcessorContext upgradeProcCtx = new UpgradeProcessorContext(ctx, upgradeLogger);
            File domainDir = new File(domainLocation);
            ConfigFileHelper.isUpgradeNeeded(domainDir);
            DomainDir.resetRootDirForExplicitUpgrade(domainDir.getAbsolutePath());
            String configFileName = domainLocation + File.separator + "config" + File.separator + "config.xml";
            File configFile = new File(configFileName);
            if (!configFile.exists()) {
               throw new UpgradeException(ResourceBundleManager.getUpgradeString("CONFIG_NOT_FOUND", new Object[]{configFile.getAbsolutePath()}));
            } else {
               DomainMBean domainMBean = parseConfig(configFile, logger);
               String[] var11 = PROCESSORS;
               int var12 = var11.length;

               for(int var13 = 0; var13 < var12; ++var13) {
                  String processorName = var11[var13];
                  Class clazz = Class.forName(processorName);
                  Object processor = clazz.newInstance();
                  if (!(processor instanceof UpgradeProcessor)) {
                     throw new UpgradeException("Object must be a UpgradeProcessor, not: " + processor.getClass().getName());
                  }

                  UpgradeProcessor upgradeProcessor = (UpgradeProcessor)processor;
                  upgradeProcessor.upgradeConfiguration(domainMBean, upgradeProcCtx);
               }

               AbstractDescriptorBean descriptorBean = (AbstractDescriptorBean)domainMBean;
               DescriptorHelper.saveDescriptorTree(descriptorBean.getDescriptor(), false, domainLocation, "UTF-8");
            }
         } else {
            throw new IllegalArgumentException("Domain or upgrade context can not be null");
         }
      } catch (UpgradeException var18) {
         throw var18;
      } catch (IllegalArgumentException var19) {
         throw var19;
      } catch (Exception var20) {
         throw new UpgradeException(ResourceBundleManager.getUpgradeString("UNEXPECTED_EXCEPTION"), var20);
      }
   }

   private static DomainMBean parseConfig(File cfgFile, Logger logger) throws Exception {
      ArrayList errs = new ArrayList();
      FileInputStream in = null;

      DomainMBean var6;
      try {
         in = new FileInputStream(cfgFile);
         DescriptorManagerHelperContext ctx = new DescriptorManagerHelperContext();
         ctx.setEditable(true);
         ctx.setValidate(false);
         ctx.setTransform(true);
         ctx.setErrors(errs);
         DomainMBean result = (DomainMBean)DescriptorManagerHelper.loadDescriptor(in, ctx).getRootBean();
         if (errs.size() > 0) {
            Iterator i = errs.iterator();

            while(i.hasNext()) {
               Object o = i.next();
               if (o instanceof Exception) {
                  logger.log(Level.SEVERE, ((Exception)o).getMessage());
               } else {
                  logger.log(Level.WARNING, o.toString());
               }
            }

            throw new UpgradeException(ResourceBundleManager.getUpgradeString("VALIDATION_ERRORS", new Object[]{cfgFile.getAbsolutePath()}));
         }

         var6 = result;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var13) {
            }
         }

      }

      return var6;
   }
}
