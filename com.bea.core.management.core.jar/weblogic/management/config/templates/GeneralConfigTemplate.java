package weblogic.management.config.templates;

import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import weblogic.utils.LocatorUtilities;

public class GeneralConfigTemplate {
   public static boolean requireEjbRefDConfig(DDBean ddb, DConfigBean parent) {
      return GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.requireEjbRefDConfig(ddb, parent);
   }

   public static void configureSecurity(DConfigBean bean) {
      GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.configureSecurity(bean);
   }

   public static void configureWeblogicApplication(DConfigBean bean) {
      GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.configureWeblogicApplication(bean);
   }

   public static void configureEntityDescriptor(DConfigBean bean) {
      GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.configureEntityDescriptor(bean);
   }

   public static void configureMessageDrivenDescriptor(DConfigBean bean) {
      GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.configureMessageDrivenDescriptor(bean);
   }

   public static void configureAdminObj(DConfigBean bean) {
      GeneralConfigTemplate.ConfigTemplateInitializer.configTemplate.configureAdminObj(bean);
   }

   private static class ConfigTemplateInitializer {
      private static final DeployConfigTemplateService configTemplate = (DeployConfigTemplateService)LocatorUtilities.getService(DeployConfigTemplateService.class);
   }
}
