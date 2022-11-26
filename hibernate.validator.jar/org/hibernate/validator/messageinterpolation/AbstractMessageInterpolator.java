package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.MessageInterpolator;
import javax.validation.ValidationException;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.engine.messageinterpolation.LocalizedMessage;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenIterator;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

public abstract class AbstractMessageInterpolator implements MessageInterpolator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final int DEFAULT_INITIAL_CAPACITY = 100;
   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   private static final String DEFAULT_VALIDATION_MESSAGES = "org.hibernate.validator.ValidationMessages";
   public static final String USER_VALIDATION_MESSAGES = "ValidationMessages";
   public static final String CONTRIBUTOR_VALIDATION_MESSAGES = "ContributorValidationMessages";
   private final Locale defaultLocale;
   private final ResourceBundleLocator userResourceBundleLocator;
   private final ResourceBundleLocator defaultResourceBundleLocator;
   private final ResourceBundleLocator contributorResourceBundleLocator;
   private final ConcurrentReferenceHashMap resolvedMessages;
   private final ConcurrentReferenceHashMap tokenizedParameterMessages;
   private final ConcurrentReferenceHashMap tokenizedELMessages;
   private final boolean cachingEnabled;
   private static final Pattern LEFT_BRACE = Pattern.compile("\\{", 16);
   private static final Pattern RIGHT_BRACE = Pattern.compile("\\}", 16);
   private static final Pattern SLASH = Pattern.compile("\\\\", 16);
   private static final Pattern DOLLAR = Pattern.compile("\\$", 16);

   public AbstractMessageInterpolator() {
      this((ResourceBundleLocator)null);
   }

   public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator) {
      this(userResourceBundleLocator, (ResourceBundleLocator)null);
   }

   public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator) {
      this(userResourceBundleLocator, contributorResourceBundleLocator, true);
   }

   public AbstractMessageInterpolator(ResourceBundleLocator userResourceBundleLocator, ResourceBundleLocator contributorResourceBundleLocator, boolean cacheMessages) {
      this.defaultLocale = Locale.getDefault();
      if (userResourceBundleLocator == null) {
         this.userResourceBundleLocator = new PlatformResourceBundleLocator("ValidationMessages");
      } else {
         this.userResourceBundleLocator = userResourceBundleLocator;
      }

      if (contributorResourceBundleLocator == null) {
         this.contributorResourceBundleLocator = new PlatformResourceBundleLocator("ContributorValidationMessages", (ClassLoader)null, true);
      } else {
         this.contributorResourceBundleLocator = contributorResourceBundleLocator;
      }

      this.defaultResourceBundleLocator = new PlatformResourceBundleLocator("org.hibernate.validator.ValidationMessages");
      this.cachingEnabled = cacheMessages;
      if (this.cachingEnabled) {
         this.resolvedMessages = new ConcurrentReferenceHashMap(100, 0.75F, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
         this.tokenizedParameterMessages = new ConcurrentReferenceHashMap(100, 0.75F, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
         this.tokenizedELMessages = new ConcurrentReferenceHashMap(100, 0.75F, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.noneOf(ConcurrentReferenceHashMap.Option.class));
      } else {
         this.resolvedMessages = null;
         this.tokenizedParameterMessages = null;
         this.tokenizedELMessages = null;
      }

   }

   public String interpolate(String message, MessageInterpolator.Context context) {
      String interpolatedMessage = message;

      try {
         interpolatedMessage = this.interpolateMessage(message, context, this.defaultLocale);
      } catch (MessageDescriptorFormatException var5) {
         LOG.warn(var5.getMessage());
      }

      return interpolatedMessage;
   }

   public String interpolate(String message, MessageInterpolator.Context context, Locale locale) {
      String interpolatedMessage = message;

      try {
         interpolatedMessage = this.interpolateMessage(message, context, locale);
      } catch (ValidationException var6) {
         LOG.warn(var6.getMessage());
      }

      return interpolatedMessage;
   }

   private String interpolateMessage(String message, MessageInterpolator.Context context, Locale locale) throws MessageDescriptorFormatException {
      if (message.indexOf(123) < 0) {
         return this.replaceEscapedLiterals(message);
      } else {
         String resolvedMessage = null;
         if (this.cachingEnabled) {
            resolvedMessage = (String)this.resolvedMessages.computeIfAbsent(new LocalizedMessage(message, locale), (lm) -> {
               return this.resolveMessage(message, locale);
            });
         } else {
            resolvedMessage = this.resolveMessage(message, locale);
         }

         if (resolvedMessage.indexOf(123) > -1) {
            resolvedMessage = this.interpolateExpression(new TokenIterator(this.getParameterTokens(resolvedMessage, this.tokenizedParameterMessages, InterpolationTermType.PARAMETER)), context, locale);
            resolvedMessage = this.interpolateExpression(new TokenIterator(this.getParameterTokens(resolvedMessage, this.tokenizedELMessages, InterpolationTermType.EL)), context, locale);
         }

         resolvedMessage = this.replaceEscapedLiterals(resolvedMessage);
         return resolvedMessage;
      }
   }

   private List getParameterTokens(String resolvedMessage, ConcurrentReferenceHashMap cache, InterpolationTermType termType) {
      return this.cachingEnabled ? (List)cache.computeIfAbsent(resolvedMessage, (rm) -> {
         return (new TokenCollector(resolvedMessage, termType)).getTokenList();
      }) : (new TokenCollector(resolvedMessage, termType)).getTokenList();
   }

   private String resolveMessage(String message, Locale locale) {
      String resolvedMessage = message;
      ResourceBundle userResourceBundle = this.userResourceBundleLocator.getResourceBundle(locale);
      ResourceBundle constraintContributorResourceBundle = this.contributorResourceBundleLocator.getResourceBundle(locale);
      ResourceBundle defaultResourceBundle = this.defaultResourceBundleLocator.getResourceBundle(locale);
      boolean evaluatedDefaultBundleOnce = false;

      while(true) {
         String userBundleResolvedMessage = this.interpolateBundleMessage(resolvedMessage, userResourceBundle, locale, true);
         if (!this.hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) {
            userBundleResolvedMessage = this.interpolateBundleMessage(resolvedMessage, constraintContributorResourceBundle, locale, true);
         }

         if (evaluatedDefaultBundleOnce && !this.hasReplacementTakenPlace(userBundleResolvedMessage, resolvedMessage)) {
            return resolvedMessage;
         }

         resolvedMessage = this.interpolateBundleMessage(userBundleResolvedMessage, defaultResourceBundle, locale, false);
         evaluatedDefaultBundleOnce = true;
      }
   }

   private String replaceEscapedLiterals(String resolvedMessage) {
      if (resolvedMessage.indexOf(92) > -1) {
         resolvedMessage = LEFT_BRACE.matcher(resolvedMessage).replaceAll("{");
         resolvedMessage = RIGHT_BRACE.matcher(resolvedMessage).replaceAll("}");
         resolvedMessage = SLASH.matcher(resolvedMessage).replaceAll(Matcher.quoteReplacement("\\"));
         resolvedMessage = DOLLAR.matcher(resolvedMessage).replaceAll(Matcher.quoteReplacement("$"));
      }

      return resolvedMessage;
   }

   private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
      return !origMessage.equals(newMessage);
   }

   private String interpolateBundleMessage(String message, ResourceBundle bundle, Locale locale, boolean recursive) throws MessageDescriptorFormatException {
      TokenCollector tokenCollector = new TokenCollector(message, InterpolationTermType.PARAMETER);
      TokenIterator tokenIterator = new TokenIterator(tokenCollector.getTokenList());

      while(tokenIterator.hasMoreInterpolationTerms()) {
         String term = tokenIterator.nextInterpolationTerm();
         String resolvedParameterValue = this.resolveParameter(term, bundle, locale, recursive);
         tokenIterator.replaceCurrentInterpolationTerm(resolvedParameterValue);
      }

      return tokenIterator.getInterpolatedMessage();
   }

   private String interpolateExpression(TokenIterator tokenIterator, MessageInterpolator.Context context, Locale locale) throws MessageDescriptorFormatException {
      while(tokenIterator.hasMoreInterpolationTerms()) {
         String term = tokenIterator.nextInterpolationTerm();
         String resolvedExpression = this.interpolate(context, locale, term);
         tokenIterator.replaceCurrentInterpolationTerm(resolvedExpression);
      }

      return tokenIterator.getInterpolatedMessage();
   }

   public abstract String interpolate(MessageInterpolator.Context var1, Locale var2, String var3);

   private String resolveParameter(String parameterName, ResourceBundle bundle, Locale locale, boolean recursive) throws MessageDescriptorFormatException {
      String parameterValue;
      try {
         if (bundle != null) {
            parameterValue = bundle.getString(this.removeCurlyBraces(parameterName));
            if (recursive) {
               parameterValue = this.interpolateBundleMessage(parameterValue, bundle, locale, recursive);
            }
         } else {
            parameterValue = parameterName;
         }
      } catch (MissingResourceException var7) {
         parameterValue = parameterName;
      }

      return parameterValue;
   }

   private String removeCurlyBraces(String parameter) {
      return parameter.substring(1, parameter.length() - 1);
   }
}
