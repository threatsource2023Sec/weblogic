package weblogic.connector.configuration.validation;

import java.lang.annotation.ElementType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAException;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.validation.injection.ValidationManager;

public class BeanValidator {
   private ValidationManager.ValidationBean validationBean;
   private ValidatorFactory vf;
   private javax.validation.Validator v;

   public BeanValidator(VirtualJarFile vjar) throws RAException {
      if (vjar.getResource("META-INF/validation.xml") == null) {
         ConnectorLogger.logUsingDefaultValidationXml(vjar.getName());
         this.validationBean = getDefaultValidationBean();
      } else {
         ConnectorLogger.logUsingRarValidationXml(vjar.getName());
         this.validationBean = this.getModuleValidationBean(vjar);
      }

      this.vf = this.validationBean.getValidatorFactory();
      TraversableResolver tr = new ConnectorTraversableResolver();
      this.v = this.vf.usingContext().traversableResolver(tr).getValidator();
      Debug.deployment("BeanValidator: initialized for adapter archieve " + vjar.getName());
   }

   public ValidationManager.ValidationBean getModuleValidationBean(VirtualJarFile vjar) throws RAException {
      URL url = null;

      try {
         if (vjar.isDirectory()) {
            url = new URL("file", "", vjar.getName() + "/META-INF/validation.xml");
         } else {
            url = new URL("jar", "", "file:" + vjar.getName() + "!/META-INF/validation.xml");
         }
      } catch (MalformedURLException var4) {
         throw new RAException("Error when compute out URL for vjar " + vjar, var4);
      }

      Debug.deployment("BeanValidator: use URL: " + url);
      return this.validationBean = ValidationManager.defaultInstance().getDefaultValidationBean(url);
   }

   public static ValidationManager.ValidationBean getDefaultValidationBean() {
      return ValidationManager.defaultInstance().getDefaultValidationBean((URL)null);
   }

   public javax.validation.Validator getValidator() {
      return this.v;
   }

   public ValidatorFactory getValidatorFactory() {
      return this.vf;
   }

   public void validate(Object bean, String info) throws RAException {
      Set errors;
      try {
         errors = this.getValidator().validate(bean, new Class[]{Default.class});
      } catch (Throwable var9) {
         ConnectorLogger.logBeanValidationFailed(info, bean.toString(), var9);
         RAException raex = new RAException("Error when validating adapter's bean " + bean, var9);
         throw raex;
      }

      if (errors.isEmpty()) {
         Debug.deployment("BeanValidator: Bean validation passed for " + info + " on bean instance " + bean);
      } else {
         StringBuilder errMsg = new StringBuilder();
         int i = 1;
         Iterator var6 = errors.iterator();

         while(var6.hasNext()) {
            ConstraintViolation error = (ConstraintViolation)var6.next();
            String err = "[" + i++ + "]: find error [" + error.getMessage() + "] when validating property [" + error.getPropertyPath() + "] with new value [" + error.getInvalidValue() + "] on bean instance [" + error.getRootBean() + "]";
            errMsg.append("\n" + err);
         }

         String error = errMsg.toString();
         ConnectorLogger.logFindConstraintViolationErrors(info, bean.toString(), errors.size(), error);
         ConstraintViolationException cve = new WLSConstraintViolationException("Found " + errors.size() + " constraint violation error(s) for " + info + " on instance " + bean + ": " + error, errors);
         RAException raex = new RAException("Bean validation failed for " + info, cve);
         throw raex;
      }
   }

   public static class ConnectorTraversableResolver implements TraversableResolver {
      public boolean isCascadable(Object arg0, Path.Node arg1, Class arg2, Path arg3, ElementType arg4) {
         return true;
      }

      public boolean isReachable(Object arg0, Path.Node arg1, Class arg2, Path arg3, ElementType arg4) {
         return true;
      }
   }
}
