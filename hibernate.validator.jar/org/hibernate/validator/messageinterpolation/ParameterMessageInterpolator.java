package org.hibernate.validator.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import org.hibernate.validator.internal.engine.messageinterpolation.ParameterTermResolver;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ParameterMessageInterpolator extends AbstractMessageInterpolator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public String interpolate(MessageInterpolator.Context context, Locale locale, String term) {
      if (InterpolationTerm.isElExpression(term)) {
         LOG.warnElIsUnsupported(term);
         return term;
      } else {
         ParameterTermResolver parameterTermResolver = new ParameterTermResolver();
         return parameterTermResolver.interpolate(context, term);
      }
   }
}
