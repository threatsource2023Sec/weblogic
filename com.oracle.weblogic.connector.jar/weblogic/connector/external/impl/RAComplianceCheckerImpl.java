package weblogic.connector.external.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.connector.configuration.DDUtil;
import weblogic.connector.configuration.validation.ValidatingMessageImpl;
import weblogic.connector.configuration.validation.wl.WLOutboundValidator;
import weblogic.connector.deploy.DeployerUtil;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.exception.RAConfigurationException;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.WLRAConfigurationException;
import weblogic.connector.external.RAComplianceChecker;
import weblogic.connector.external.RAComplianceException;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public final class RAComplianceCheckerImpl implements RAComplianceChecker {
   private List classFinders = new ArrayList(1);
   private RAValidationInfoImpl raValidationInfo = new RAValidationInfoImpl();
   private GenericClassLoader raClassLoader = null;
   private ConnectorBean raConnectorBean = null;
   private WeblogicConnectorBean wlraConnectorBean = null;
   private ConnectorAPContext apcontext;
   private RAInfo raInfo;
   public static final RAComplianceChecker factoryHelper = new RAComplianceCheckerImpl();

   public RAComplianceChecker createChecker() {
      return null;
   }

   public boolean validate(GenericClassLoader cl, RarArchive rar, File altDD, File config, DeploymentPlanBean plan, boolean insideEar, AdditionalAnnotatedClassesProvider provider) throws RAComplianceException {
      boolean isCompliant = false;

      try {
         String moduleName = DDUtil.getModuleName(rar.getOriginalRarFilename());
         PermissionsBean permissionsBean = null;
         if (!insideEar) {
            permissionsBean = DDUtil.getPermissionsBean((AppDeploymentMBean)null, plan, rar, moduleName);
         }

         RAInfo raInfo = DDUtil.getRAInfo(rar, altDD, (File)null, moduleName, (AppDeploymentMBean)null, plan, cl, insideEar, provider, permissionsBean, false);
         DeployerUtil.updateClassFinder(cl, rar, this.classFinders);
         isCompliant = this.validate(rar.getOriginalRarFilename(), raInfo, cl).isCompliant();
      } catch (WLRAConfigurationException var12) {
         Debug.logComplianceWLRAConfigurationException(var12.getMessage());
      } catch (RAConfigurationException var13) {
         this.handleRAException(var13);
      } catch (RAException var14) {
         RAComplianceException race = new RAComplianceException();
         race.addMessage("failed to validate adapter due to exception:" + var14.getMessage());
         race.initCause(var14);
         throw race;
      }

      return isCompliant;
   }

   public RAValidationInfo validate(String moduleOrVjarName, RAInfo raInfo, GenericClassLoader cl) throws RAComplianceException {
      this.raValidationInfo.setLinkRef(raInfo.getLinkref());
      this.raConnectorBean = raInfo.getConnectorBean();
      this.wlraConnectorBean = raInfo.getWeblogicConnectorBean();
      this.raClassLoader = cl;
      this.apcontext = raInfo.getConnectorAPContext();
      this.raInfo = raInfo;
      this.validate(moduleOrVjarName);
      return this.raValidationInfo;
   }

   private void validate(String moduleOrVjarName) throws RAComplianceException {
      RAComplianceException racex = new RAComplianceException();
      this.raValidationInfo.setModuleName(moduleOrVjarName);
      if (this.raConnectorBean != null) {
         this.raValidationInfo.setHasRAxml(!this.raValidationInfo.isLinkRef());
      } else if (this.raValidationInfo.isLinkRef()) {
         Debug.logComplianceIsLinkRef(this.raValidationInfo.getLinkRef());
      }

      RAValidateEngine ve = new RAValidateEngine(this.raConnectorBean, this.raClassLoader, this.raValidationInfo, this.wlraConnectorBean, this.apcontext, this.raInfo.getCriticalSubComponents(), this.raInfo.getSubComponentsChild2ParentMap());
      ValidatingMessageImpl validationResult = ve.validate();
      this.raInfo.setValidationMessage(validationResult);
      if (!validationResult.hasCriticalError()) {
         this.validateRAInfo(validationResult);
      }

      List raErrors = validationResult.getCriticalErrors();
      List raNonCriticalErrors = validationResult.getNonCriticalErrors();
      List raWarnings = validationResult.getWarnings();
      this.raValidationInfo.setWarnings(raWarnings);
      if (raErrors.isEmpty() && raWarnings.isEmpty() && raNonCriticalErrors.isEmpty()) {
         Debug.logNoComplianceErrors(moduleOrVjarName);
      } else {
         ConnectorLogger.logNumComplianceErrorsAndWarnings(moduleOrVjarName, raErrors.size(), raNonCriticalErrors.size(), raWarnings.size());
      }

      Iterator var8 = raErrors.iterator();

      while(var8.hasNext()) {
         String error = (String)var8.next();
         racex.addMessage(error);
      }

      String warning;
      RAComplianceException warnings;
      Iterator var12;
      if (raNonCriticalErrors.size() > 0) {
         warnings = new RAComplianceException();
         var12 = raNonCriticalErrors.iterator();

         while(var12.hasNext()) {
            warning = (String)var12.next();
            warnings.addMessage(warning);
         }

         ConnectorLogger.logComplianceNonCriticalErrors(moduleOrVjarName, warnings.getMessage());
      }

      if (raWarnings.size() > 0) {
         warnings = new RAComplianceException();
         var12 = raWarnings.iterator();

         while(var12.hasNext()) {
            warning = (String)var12.next();
            warnings.addMessage(warning);
         }

         Debug.logComplianceWarnings(moduleOrVjarName, warnings.getMessage());
      }

      if (raErrors.size() > 0) {
         this.raValidationInfo.setCompliant(false);
         throw racex;
      } else {
         this.raValidationInfo.setCompliant(true);
      }
   }

   private void validateRAInfo(ValidatingMessageImpl validationResult) {
      WLOutboundValidator.validateRAInfo(this.raInfo, validationResult);
   }

   private void handleRAException(RAConfigurationException ex) {
      Throwable c1 = ex.getCause();
      String msg;
      if (c1 == null) {
         msg = ex.getMessage();
      } else {
         Throwable c2 = c1.getCause();
         if (c2 != null) {
            msg = c2.getClass().getName() + ": " + c2.toString();
         } else {
            msg = c1.getClass().getName() + ": " + c1.toString();
         }
      }

      Debug.logComplianceRAConfigurationException(msg);
   }
}
