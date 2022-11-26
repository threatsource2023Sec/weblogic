package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.DiagnosticsELContext;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFBean;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.WLDFScalingActionBean;
import weblogic.diagnostics.descriptor.WLDFScheduleBean;
import weblogic.diagnostics.descriptor.WLDFWatchBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;
import weblogic.diagnostics.descriptor.validation.WatchValidatorService;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.diagnostics.logging.LogVariablesImpl;
import weblogic.diagnostics.query.QueryFactory;
import weblogic.diagnostics.query.QueryParsingException;
import weblogic.diagnostics.query.VariableIndexResolver;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.internal.ScheduleExpressionWrapper;

@Service
@Singleton
public class WatchValidatorServiceImpl implements WatchValidatorService {
   private static final DiagnosticsTextWatchTextFormatter textFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static ELContext parserContext;
   private static ValidatorFactory factory;
   @Inject
   private ActiveBeanUtil activeBeanUtils;

   public void validateWatch(WLDFWatchBean watch) {
      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         WLDFSystemResourceMBean systemResource = this.findParentSystemResource((DescriptorBean)watch);
         if (systemResource != null && !this.isClone(systemResource)) {
            this.validateWatchRule(watch.getName(), watch.getRuleType(), watch.getRuleExpression(), watch.getExpressionLanguage());
            boolean scheduleSet = ((DescriptorBean)watch).isSet("Schedule");
            if (scheduleSet) {
               boolean isHarvesterRule = isHarvesterRule(watch.getRuleType());
               boolean isEL = isELExpressionLanguage(watch.getExpressionLanguage());
               if (!isHarvesterRule || !isEL) {
                  throw new IllegalArgumentException(textFormatter.getCalendarSchedulingNotAllowedForWLDFLanguageText());
               }
            }

            validateActionSet(watch.getNotifications());
         }
      }
   }

   public void validateExpressionLanguage(WLDFWatchBean watch, String language) {
      if (language != null && language.length() != 0) {
         switch (language) {
            case "WLDF":
               WLDFSystemResourceMBean parentSystemResource = this.findParentSystemResource((DescriptorBean)watch);
               if (parentSystemResource != null && this.isNonGlobalSystemResource(parentSystemResource)) {
                  throw new IllegalArgumentException(textFormatter.getWLDFExpressionLanguageNotAllowedText());
               }
            case "EL":
               return;
            default:
               throw new IllegalArgumentException(textFormatter.getIllegalPolicyExpressionLanguageText(language));
         }
      } else {
         throw new IllegalArgumentException(textFormatter.getExpressionLanguageCanNotBeNullOrEmptyText());
      }
   }

   public String getExpressionLanguageDefault(WLDFWatchBean watch) {
      WLDFSystemResourceMBean parentSystemResource = this.findParentSystemResource((DescriptorBean)watch);
      String languageType = "WLDF";
      if (parentSystemResource != null && !this.isDomainSystemResource(parentSystemResource)) {
         languageType = "EL";
      }

      return languageType;
   }

   public void validateSchedule(WLDFScheduleBean schedule) {
      ScheduleExpression scheduleExpression = WatchUtils.createScheduleExpression(schedule);
      ScheduleExpressionWrapper.create(scheduleExpression);
   }

   public void validateIncrementInterval(String expression) {
      if (expression.contains("/")) {
         String[] incrementComponents = expression.split("/");
         if (incrementComponents.length != 2) {
            throw new IllegalArgumentException(textFormatter.getIllegalWatchScheduleIncrementPattern(expression));
         }

         String intervalString = incrementComponents[1].trim();
         Integer interval = Integer.parseInt(intervalString);
         if (interval <= 0) {
            throw new IllegalArgumentException(textFormatter.getIllegalWatchScheduleIntervalValue(intervalString));
         }
      }

   }

   public void validateWatchRule(String watchName, String ruleType, String ruleExp) {
      this.validateWatchRule(watchName, ruleType, ruleExp, "WLDF");
   }

   public void validateWatchRule(String watchName, String ruleType, String ruleExp, String expressionLanguage) {
      try {
         if (isELExpressionLanguage(expressionLanguage)) {
            this.validateELExpression(ruleType, ruleExp);
         } else {
            validateWLDFQueryExpression(watchName, ruleType, ruleExp);
         }

      } catch (ELException | QueryParsingException var6) {
         throw new IllegalArgumentException(var6);
      }
   }

   public void validateELExpression(String ruleType, String ruleExp) throws ELException {
      if (ruleExp != null && !ruleExp.isEmpty()) {
         parseELExpression(ruleExp);
      }
   }

   public void validateActionBean(WLDFActionBean actionBean) {
      try {
         if (actionBean != null) {
            ActionConfigBean configBean = WLDFActionsFactory.convertToActionConfig(actionBean);
            if (configBean == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("No configuration bean associated with action type " + actionBean.getType());
               }

            } else {
               Set validationSet = null;
               ValidatorFactory validationFactory = getBeanValidationFactory();
               Validator validator = validationFactory.getValidator();
               validationSet = validator.validate(configBean, new Class[0]);
               if (validationSet != null && validationSet.size() > 0) {
                  StringBuffer buf = new StringBuffer();
                  Iterator it = validationSet.iterator();

                  while(it.hasNext()) {
                     ConstraintViolation violation = (ConstraintViolation)it.next();
                     buf.append(violation.getPropertyPath()).append(": ").append(violation.getMessage());
                     if (it.hasNext()) {
                        buf.append('\n');
                     }
                  }

                  throw new IllegalArgumentException(buf.toString());
               }
            }
         }
      } catch (IllegalAccessException | ClassNotFoundException | IntrospectionException | InstantiationException var9) {
         throw new RuntimeException(var9);
      }
   }

   private boolean isClone(ConfigurationMBean bean) {
      if (this.activeBeanUtils.toOriginalBean(bean) == bean) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(bean.getName() + " is NOT a clone");
         }

         return false;
      } else {
         return true;
      }
   }

   private boolean isNonGlobalSystemResource(WLDFSystemResourceMBean parentSystemResource) {
      WebLogicMBean parentOfSystemResource = parentSystemResource.getParent();
      if (!this.isClone(parentSystemResource) && !(parentOfSystemResource instanceof DomainMBean)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(parentSystemResource.getName() + " is not a Domain-level WLDFSystemResourceMBean, parent: " + parentOfSystemResource.getName());
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isDomainSystemResource(WLDFSystemResourceMBean parentSystemResource) {
      WebLogicMBean parentOfSystemResource = parentSystemResource.getParent();
      if (!this.isClone(parentSystemResource) && parentOfSystemResource instanceof DomainMBean) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(parentSystemResource.getName() + " is a Domain-level WLDFSystemResourceMBean, parent: " + parentOfSystemResource.getName());
         }

         return true;
      } else {
         return false;
      }
   }

   private WLDFSystemResourceMBean findParentSystemResource(DescriptorBean bean) {
      WLDFSystemResourceMBean parentResource = null;
      DescriptorBean parentBean = bean.getParentBean();
      if (parentBean != null) {
         if (!(parentBean instanceof WLDFResourceBean)) {
            return this.findParentSystemResource(parentBean);
         }

         Descriptor parentDescriptor = parentBean.getDescriptor();
         Object configExtension = DescriptorInfoUtils.getDescriptorConfigExtension(parentDescriptor);
         if (configExtension != null && configExtension instanceof WLDFSystemResourceMBean) {
            parentResource = (WLDFSystemResourceMBean)configExtension;
            if (!this.isClone(parentResource)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(((WLDFBean)bean).getName() + " - found parent SystemResource, containing MBean: " + parentResource.getName());
               }

               return parentResource;
            }
         }
      }

      return null;
   }

   public void validateRecipients(String[] recipients) {
      if (recipients != null) {
         StringBuffer buffer = new StringBuffer();

         for(int i = 0; i < recipients.length; ++i) {
            buffer.append(recipients[i]);
            if (i < recipients.length - 1) {
               buffer.append(',');
            }
         }

         try {
            InternetAddress.parse(buffer.toString(), false);
         } catch (AddressException var4) {
            throw new IllegalArgumentException(var4);
         }
      }
   }

   public void validateWatchNotificationBean(WLDFWatchNotificationBean watchNotif) {
      HashSet names = new HashSet();
      WLDFNotificationBean[] notifs = watchNotif.getNotifications();
      if (notifs != null) {
         for(int i = 0; i < notifs.length; ++i) {
            String name = notifs[i].getName();
            if (names.contains(name)) {
               String msg = DiagnosticsLogger.logNotificationNameExistingLoggable(name).getMessage();
               throw new IllegalArgumentException(msg);
            }

            names.add(name);
         }

      }
   }

   private static void validateWLDFQueryExpression(String watchName, String ruleType, String ruleExp) throws QueryParsingException {
      if (WLDFHarvesterUtils.isHarvesterValidationAvailable()) {
         boolean isHarvesterRule = isHarvesterRule(ruleType);
         if (ruleExp != null && ruleExp.length() != 0) {
            VariableIndexResolver vir = null;
            if (isHarvesterRule) {
               vir = new HarvesterVariableValidator(watchName);
            } else if (isLogRule(ruleType)) {
               vir = LogVariablesImpl.getInstance();
            } else if (isEventRule(ruleType)) {
               vir = new FixedSetVariableValidator(1);
            }

            QueryFactory.createQuery((VariableIndexResolver)vir, ruleExp);
         } else {
            throw new InvalidWatchException(textFormatter.getWLDFLanguageRuleExpressionCanNotBeNullText());
         }
      }
   }

   private static boolean isEventRule(String ruleType) {
      return ruleType.equals("EventData");
   }

   private static boolean isLogRule(String ruleType) {
      return ruleType.equals("Log");
   }

   private static boolean isHarvesterRule(String ruleType) {
      return ruleType.equals("Harvester");
   }

   private static boolean isELExpressionLanguage(String expressionLanguage) {
      return expressionLanguage.equals("EL");
   }

   private static void parseELExpression(String ruleExp) throws ELException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Parsing EL expression: " + ruleExp);
      }

      ELContext validatorContext = getParserContext();
      ExpressionFactory.newInstance().createValueExpression(validatorContext, "${" + ruleExp + "}", Object.class);
   }

   private static synchronized ELContext getParserContext() {
      if (parserContext == null) {
         ServiceLocator locator = WatchUtils.getServiceLocator();
         if (locator != null) {
            parserContext = new DiagnosticsELContext(new Annotation[0]);
            locator.inject(parserContext);
            locator.postConstruct(parserContext);
         }
      }

      return parserContext;
   }

   private static void validateActionSet(WLDFNotificationBean[] assignedActions) {
      if (assignedActions != null && assignedActions.length != 0) {
         List scalingActionsSet = null;
         WLDFNotificationBean[] var2 = assignedActions;
         int var3 = assignedActions.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFNotificationBean action = var2[var4];
            if (action instanceof WLDFScalingActionBean) {
               if (scalingActionsSet == null) {
                  scalingActionsSet = new ArrayList();
               }

               scalingActionsSet.add(action.getName());
            }
         }

         if (scalingActionsSet != null && scalingActionsSet.size() > 1) {
            throw new IllegalStateException(textFormatter.getScalingActionsStateErrorText(scalingActionsSet.toString()));
         }
      }
   }

   private static synchronized ValidatorFactory getBeanValidationFactory() {
      if (factory == null) {
         factory = Validation.buildDefaultValidatorFactory();
      }

      return factory;
   }
}
