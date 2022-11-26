package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.PrincipalValidationService;
import com.bea.common.security.servicecfg.PrincipalValidationServiceConfig;
import com.bea.common.security.spi.PrincipalValidationProvider;
import com.bea.common.security.spi.PrincipalValidatorWrapper;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.PrincipalValidator;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class PrincipalValidationServiceImpl implements ServiceLifecycleSpi, PrincipalValidationService {
   private static final String CSS_PV_IMPL = "com.bea.common.security.provider.PrincipalValidatorImpl";
   private static final String WLS_PV_IMPL = "weblogic.security.provider.PrincipalValidatorImpl";
   private static final String CHECK_WLS_DUPLICATE_PV_IMPL_PROP = "com.bea.common.security.CheckWLSDuplicatePVImpl";
   private static final String ENABLE_NO_OP_PRINCIPAL_VALIDATOR = "weblogic.security.principal.validation.NoopPrincipalValidator";
   private LoggerSpi logger;
   private AuditService auditService;
   private PrincipalValidator[] principalValidators;
   private boolean checkWLSDuplicatePVImpl = true;
   private static boolean enableNoopPrincipalValidator = false;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PrincipalValidationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      this.checkWLSDuplicatePVImpl = Boolean.valueOf(System.getProperty("com.bea.common.security.CheckWLSDuplicatePVImpl", "true"));
      if (config != null && config instanceof PrincipalValidationServiceConfig) {
         PrincipalValidationServiceConfig myconfig = (PrincipalValidationServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String[] providerNames = myconfig.getPrincipalValidationProviderNames();
         ArrayList validators = new ArrayList();

         for(int i = 0; i < providerNames.length; ++i) {
            PrincipalValidationProvider provider = (PrincipalValidationProvider)dependentServices.getService(providerNames[i]);
            if (debug) {
               this.logger.debug(method + " got PrincipalValidationProvider " + providerNames[i]);
            }

            this.addPrincipalValidator(provider, validators);
         }

         this.principalValidators = (PrincipalValidator[])((PrincipalValidator[])validators.toArray(new PrincipalValidator[validators.size()]));
         if (this.principalValidators.length < 1) {
            throw new ServiceConfigurationException(ServiceLogger.getNoObjectsFound(method, "PrincipalValidator"));
         } else {
            if (debug) {
               this.logger.debug(method + " got " + this.principalValidators.length + " PrincipalValidationProviders");
            }

            return Delegator.getProxy(PrincipalValidationService.class, this);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "PrincipalValidationServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   private void addPrincipalValidator(PrincipalValidationProvider provider, ArrayList validators) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".addPrincipalValidator" : null;
      if (debug) {
         this.logger.debug(method);
      }

      PrincipalValidator validator1 = provider.getPrincipalValidator();
      if (validator1 == null) {
         if (debug) {
            this.logger.debug(method + " dropping PrincipalValidationProvider since it returned a null PrincipalValidator");
         }

      } else {
         PrincipalValidatorWrapper pvWrapper1 = (PrincipalValidatorWrapper)validator1;
         if (debug) {
            this.logger.debug(method + " got PrincipalValidator, type=" + pvWrapper1.getPrincipalValidatorType() + ", basePrincipalType=" + validator1.getPrincipalBaseClass());
         }

         Iterator iter = validators.iterator();

         while(iter.hasNext()) {
            PrincipalValidatorWrapper pvWrapper2 = (PrincipalValidatorWrapper)iter.next();
            if (pvWrapper1.getPrincipalValidatorType().equals(pvWrapper2.getPrincipalValidatorType())) {
               if (debug) {
                  this.logger.debug(method + " dropping PrincipalValidator since we already have one of its type");
               }

               return;
            }
         }

         iter = validators.iterator();

         Class base1;
         Class base2;
         PrincipalValidator validator2;
         do {
            if (!iter.hasNext()) {
               if (debug) {
                  this.logger.debug(method + " using PrincipalValidator");
               }

               validators.add(validator1);
               return;
            }

            validator2 = (PrincipalValidator)iter.next();
            base1 = validator1.getPrincipalBaseClass();
            base2 = validator2.getPrincipalBaseClass();
         } while(!base1.equals(base2));

         if (this.checkWLSDuplicatePVImpl) {
            PrincipalValidatorWrapper pvWrapper2 = (PrincipalValidatorWrapper)validator2;
            String currentPVType = pvWrapper1.getPrincipalValidatorType();
            String storedPVType = pvWrapper2.getPrincipalValidatorType();
            if (currentPVType.equals("weblogic.security.provider.PrincipalValidatorImpl") && storedPVType.equals("com.bea.common.security.provider.PrincipalValidatorImpl") || storedPVType.equals("weblogic.security.provider.PrincipalValidatorImpl") && currentPVType.equals("com.bea.common.security.provider.PrincipalValidatorImpl")) {
               if (debug) {
                  this.logger.debug(method + " dropping default PrincipalValidatorImpl since we already have it");
               }

               return;
            }
         }

         throw new SecurityException(ServiceLogger.getValidatorCollision(validator1.getClass().getName(), validator2.getClass().getName()));
      }
   }

   public boolean validate(Set principals) {
      return this.validate(principals, (String)null);
   }

   public boolean validate(Set principals, String identityDomain) {
      boolean debug = this.logger.isDebugEnabled();
      String method = null;
      if (debug) {
         method = this.getClass().getName() + ".validate(Principals, identityDomain)";
         this.logger.debug(method);
         this.logger.debug("identity domain: " + identityDomain);
      }

      Iterator iter = principals.iterator();

      Principal principal;
      do {
         if (!iter.hasNext()) {
            if (debug) {
               this.logger.debug(method + " validated all principals");
            }

            return true;
         }

         principal = (Principal)iter.next();
         if (identityDomain != null && principal instanceof WLSAbstractPrincipal) {
            ((WLSAbstractPrincipal)principal).setIdentityDomain(identityDomain);
         }
      } while(this.validate(principal));

      if (this.auditService.isAuditEnabled()) {
         AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", (ContextHandler)null, AtnEventTypeV2.VALIDATEIDENTITY, (Exception)null);
         this.auditService.writeEvent(atnAuditEvent);
      }

      return false;
   }

   public void sign(Set principals) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".sign(Principals)");
      }

      Iterator iter = principals.iterator();

      do {
         if (!iter.hasNext()) {
            return;
         }
      } while(this.sign((Principal)iter.next()));

      throw new IllegalArgumentException(ServiceLogger.getUnableToSignPricipal(this.getClass().getName() + ".sign(Principals)"));
   }

   private boolean validate(Principal principal) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".validate(Principal)";
      if (debug) {
         this.logger.debug(method + " Principal=" + principal);
      }

      if (principal == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullParameterSupplied(method));
      } else {
         if (debug) {
            this.logger.debug(method + " PrincipalClassName=" + principal.getClass().getName());
         }

         boolean validated = false;
         boolean valid = false;

         for(int i = 0; i < this.principalValidators.length; ++i) {
            PrincipalValidator validator = this.principalValidators[i];
            if (debug) {
               this.logger.debug(method + " trying PrincipalValidator for " + validator.getPrincipalBaseClass());
            }

            if (validator.getPrincipalBaseClass().isInstance(principal)) {
               if (debug) {
                  this.logger.debug(method + " PrincipalValidator handles this PrincipalClass");
               }

               validated = true;

               try {
                  valid = validator.validate(principal);
                  if (debug) {
                     this.logger.debug(method + " PrincipalValidator said the principal is " + (valid ? "valid" : "invalid"));
                  }
               } catch (SecurityException var9) {
                  valid = false;
                  if (debug) {
                     this.logger.debug(method + " PrincipalValidator caught SecurityException", var9);
                  }
               }

               if (!valid) {
                  if (debug) {
                     this.logger.debug("Atleast one configured PrincipalValidator did not successfully validate this principal, returning false");
                  }

                  return false;
               }
            } else if (debug) {
               this.logger.debug(method + " PrincipalValidator does not handle this PrincipalClass");
            }
         }

         if (debug) {
            this.logger.debug(method + (validated ? " One or more" : " No configured") + " PrincipalValidators handled this PrincipalClass");
         }

         if (!validated && enableNoopPrincipalValidator) {
            if (debug) {
               this.logger.debug(method + "Validation by NoopPrincipalValidator");
            }

            valid = true;
            validated = true;
         }

         if (debug) {
            this.logger.debug("Returning the result of validation: " + (validated && valid));
         }

         return validated && valid;
      }
   }

   private boolean sign(Principal principal) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".sign(Principal)";
      if (debug) {
         this.logger.debug(method + " Principal=" + principal);
      }

      if (principal == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullParameterSupplied(method));
      } else {
         if (debug) {
            this.logger.debug(method + " PrincipalClassName=" + principal.getClass().getName());
         }

         boolean signed = false;

         for(int i = 0; i < this.principalValidators.length; ++i) {
            PrincipalValidator validator = this.principalValidators[i];
            if (debug) {
               this.logger.debug(method + " trying PrincipalValidator for " + validator.getPrincipalBaseClass());
            }

            if (validator.getPrincipalBaseClass().isInstance(principal)) {
               if (debug) {
                  this.logger.debug(method + " PrincipalValidator handles this PrincipalClass");
               }

               signed = (Boolean)AccessController.doPrivileged(new SignPrincipalAction(validator, principal));
               if (debug) {
                  this.logger.debug(method + " PrincipalValidator " + (!signed ? "did not sign the principal, returning false" : "signed the principal"));
               }

               if (!signed) {
                  return false;
               }
            }
         }

         if (!signed && enableNoopPrincipalValidator) {
            signed = true;
            if (debug) {
               this.logger.debug(method + " No configured PrincipalValidator handled this PrincipalClass , Signing by NoopPrincipalValidator");
            }
         }

         if (debug) {
            if (!signed) {
               this.logger.debug(method + " No PrincipalValidator handled this PrincipalClass, returning false");
            } else {
               this.logger.debug(method + " All required PrincipalValidators signed this PrincipalClass, returning true");
            }
         }

         return signed;
      }
   }

   static {
      try {
         String strNoOpPVProperty = System.getProperty("weblogic.security.principal.validation.NoopPrincipalValidator");
         if (strNoOpPVProperty != null) {
            enableNoopPrincipalValidator = Boolean.valueOf(strNoOpPVProperty);
         }
      } catch (Exception var1) {
         enableNoopPrincipalValidator = false;
      }

   }

   private static class SignPrincipalAction implements PrivilegedAction {
      private Principal principal;
      private PrincipalValidator validator;

      public SignPrincipalAction(PrincipalValidator validator, Principal principal) {
         this.validator = validator;
         this.principal = principal;
      }

      public Object run() {
         return this.validator.sign(this.principal) ? Boolean.TRUE : Boolean.FALSE;
      }
   }
}
