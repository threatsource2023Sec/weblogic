package weblogic.validation.injection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.WeakHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;
import javax.xml.bind.ValidationException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.validation.Bindable;
import weblogic.validation.Jndi;
import weblogic.validation.ValidationContext;
import weblogic.validation.ValidationContextManager;

public class ValidationManager {
   private static ValidationManager defaultInstance;
   Map vcsByJndi = new WeakHashMap();
   private ValidationContextManager vcm = ValidationContextManager.getInstance();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
   private static final String endofline = System.getProperty("line.separator");
   private static final boolean USE_JNDI = true;

   private ValidationManager() {
   }

   public static ValidationManager defaultInstance() {
      if (defaultInstance == null) {
         defaultInstance = new ValidationManager();
      }

      return defaultInstance;
   }

   public void bindValidation(Context javaCompCtx, List validationDescriptorURLs) throws NamingException, ValidationException {
      URL validationDescriptor = getSingleValidationDescriptor(validationDescriptorURLs);
      ValidationContext valCtx = null;
      if (validationDescriptor != null) {
         valCtx = this.vcm.registerURL(true, validationDescriptor);
      }

      if (valCtx != null) {
         javaCompCtx.bind(Jndi.VALIDATION_CONTEXT_BINDING.key, new ValidationOpaqueReference(valCtx));
      }

      ValidationBean vb = ValidationManager.ValidationBean.fromContext();
      if (vb == null) {
         vb = this.getDefaultValidationBean(valCtx);
      }

      javaCompCtx.bind(Jndi.VALIDATOR_BINDING.key, new ValidationOpaqueReference(vb.getValidator()));
      javaCompCtx.bind(Jndi.VALIDATOR_FACTORY_BINDING.key, new ValidationOpaqueReference(vb.getValidatorFactory()));
   }

   public static URL getSingleValidationDescriptor(List validationDescriptorURLs) throws ValidationException {
      URL validationDescriptor = null;
      if (validationDescriptorURLs != null) {
         if (validationDescriptorURLs.size() > 1) {
            StringBuilder urls = new StringBuilder();

            URL oneUrl;
            for(Iterator var3 = validationDescriptorURLs.iterator(); var3.hasNext(); urls.append(oneUrl.toString())) {
               oneUrl = (URL)var3.next();
               if (urls.length() > 0) {
                  urls.append(", ");
               }
            }

            throw new ValidationException("More than one validation.xml descriptor file found in web module!  Found at: " + urls.toString());
         }

         if (validationDescriptorURLs.size() == 1) {
            validationDescriptor = (URL)validationDescriptorURLs.get(0);
         }
      }

      return validationDescriptor;
   }

   public void bindValidation(Context javaCompCtx, List validationDescriptorURLs, ValidationBean vb) throws NamingException, ValidationException {
      URL validationDescriptor = getSingleValidationDescriptor(validationDescriptorURLs);
      ValidationContext valCtx = null;
      if (validationDescriptor != null) {
         valCtx = this.vcm.registerURL(true, validationDescriptor);
      }

      if (valCtx != null) {
         javaCompCtx.bind(Jndi.VALIDATION_CONTEXT_BINDING.key, new ValidationOpaqueReference(valCtx));
      }

      javaCompCtx.bind(Jndi.VALIDATOR_BINDING.key, new ValidationOpaqueReference(vb.getValidator()));
      javaCompCtx.bind(Jndi.VALIDATOR_FACTORY_BINDING.key, new ValidationOpaqueReference(vb.getValidatorFactory()));
   }

   public void unbindValidation(Context javaCompCtx) {
      try {
         javaCompCtx.unbind(Jndi.VALIDATOR_FACTORY_BINDING.key);
      } catch (NamingException var5) {
      }

      try {
         javaCompCtx.unbind(Jndi.VALIDATOR_BINDING.key);
      } catch (NamingException var4) {
      }

      try {
         javaCompCtx.unbind(Jndi.VALIDATION_CONTEXT_BINDING.key);
      } catch (NamingException var3) {
      }

   }

   public ValidationBean getDefaultValidationBean(URL validationDescriptorURL) {
      return this.getDefaultValidationBean(this.vcm.registerURL(false, validationDescriptorURL));
   }

   public ValidationBean getDefaultValidationBean(Bindable binder, URL validationDescriptorURL) throws NamingException, ValidationException, IOException {
      return this.getDefaultValidationBean(binder, validationDescriptorURL, "META-INF/validation.xml");
   }

   public ValidationBean getDefaultValidationBean(Bindable binder, URL validationDescriptorURL, String descriptorName) throws NamingException, ValidationException, IOException {
      URL validationDescriptor = null;
      ValidationContext valCtx = null;

      try {
         ValidationContextManager.getInstance().setValidationPhase(true);
         validationDescriptor = this.validateValidationDescriptor(validationDescriptorURL, descriptorName);
      } finally {
         ValidationContextManager.getInstance().setValidationPhase(false);
      }

      if (binder == null) {
         throw new IllegalArgumentException("binder cannot be null");
      } else {
         if (validationDescriptor != null) {
            valCtx = this.vcm.registerURL(true, validationDescriptor);
         }

         if (valCtx != null) {
            binder.bind(Jndi.VALIDATION_CONTEXT.key, valCtx);
         }

         ValidationBean vb = ValidationManager.ValidationBean.fromContext();
         if (vb == null) {
            try {
               ValidationContextManager.getInstance().cacheValidationContext(valCtx);
               vb = this.getDefaultValidationBean(valCtx);
            } finally {
               ValidationContextManager.getInstance().clearCachedValidationContext();
            }
         }

         binder.bind(Jndi.VALIDATOR.key, new ValidationOpaqueReference(vb.getValidator()));
         binder.bind(Jndi.VALIDATOR_FACTORY.key, new ValidationOpaqueReference(vb.getValidatorFactory()));
         return vb;
      }
   }

   private URL validateValidationDescriptor(URL validationDescriptor, String validatorDescriptorName) throws ValidationException, IOException {
      URL result = validationDescriptor;
      int count = validationDescriptor == null ? 0 : 1;

      Enumeration fromCL;
      for(fromCL = Thread.currentThread().getContextClassLoader().getResources(validatorDescriptorName); fromCL.hasMoreElements(); ++count) {
         result = (URL)fromCL.nextElement();
      }

      if (count <= 1) {
         return result;
      } else {
         StringBuffer sb = new StringBuffer("More than one validation descriptor found!");
         if (validationDescriptor != null) {
            sb.append(validationDescriptor).append(endofline);
         }

         while(fromCL.hasMoreElements()) {
            sb.append(fromCL.nextElement()).append(endofline);
         }

         throw new ValidationException(sb.toString());
      }
   }

   private ValidationBean getDefaultValidationBean(ValidationContext valCtx) {
      ValidatorFactory vf = Validation.byDefaultProvider().providerResolver(new NonCachingProviderResolver()).configure().buildValidatorFactory();
      Validator v = vf.getValidator();
      return valCtx != null ? new ValidationBean(v, vf, valCtx.hasValidDescriptor()) : new ValidationBean(v, vf, false);
   }

   static class NonCachingProviderResolver implements ValidationProviderResolver {
      public List getValidationProviders() {
         List providers = this.loadProviders(Thread.currentThread().getContextClassLoader());
         return providers != null && !providers.isEmpty() ? providers : this.loadProviders(NonCachingProviderResolver.class.getClassLoader());
      }

      private List loadProviders(ClassLoader classloader) {
         ServiceLoader loader = ServiceLoader.load(ValidationProvider.class, classloader);
         Iterator providerIterator = loader.iterator();
         List validationProviderList = new ArrayList();

         while(providerIterator.hasNext()) {
            try {
               validationProviderList.add(providerIterator.next());
            } catch (ServiceConfigurationError var6) {
            }
         }

         return validationProviderList;
      }
   }

   public static class ValidationBean {
      protected boolean isValidDescriptor;
      protected Validator validator;
      protected ValidatorFactory validatorFactory;

      private ValidationBean() throws NamingException {
         Context initialContext = new InitialContext();
         this.validator = (Validator)initialContext.lookup(Jndi.VALIDATOR.key);
         this.validatorFactory = (ValidatorFactory)initialContext.lookup(Jndi.VALIDATOR_FACTORY.key);
      }

      private ValidationBean(Validator aValidator, ValidatorFactory aValidatorFactory, boolean isAValidDescriptor) {
         this.isValidDescriptor = isAValidDescriptor;
         this.validator = aValidator;
         this.validatorFactory = aValidatorFactory;
      }

      public Validator getValidator() {
         return this.validator;
      }

      public ValidatorFactory getValidatorFactory() {
         return this.validatorFactory;
      }

      private static ValidationBean fromContext() {
         try {
            return new ValidationBean();
         } catch (NamingException var1) {
            if (ValidationManager.debugLogger.isDebugEnabled()) {
               ValidationManager.debugLogger.debug("Could not find Validator on the context. Will create a default one instead.", var1);
            }

            return null;
         }
      }

      public String toString() {
         return super.toString() + "\nValidator: " + this.validator + "\nValidatorFactory: " + this.validatorFactory;
      }

      // $FF: synthetic method
      ValidationBean(Validator x0, ValidatorFactory x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
