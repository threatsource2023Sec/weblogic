package com.bea.security.providers.xacml.entitlement.p13n;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DateAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.security.providers.xacml.entitlement.function.ObjectFunctionLibrary;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;

public class DateTimeEvaluators implements com.bea.security.providers.xacml.DateTimeEvaluators {
   public static final String TIME_ATTR = "urn:bea:xacml:2.0:environment:context:portal:time";
   public static final String DATE_ATTR = "urn:bea:xacml:2.0:environment:context:portal:date";
   public static final String DATETIME_ATTR = "urn:bea:xacml:2.0:environment:context:portal:timeInstant";
   private final URI TIME_ATTR_URI;
   private final URI DATE_ATTR_URI;
   private final URI DATETIME_ATTR_URI;
   private final URI OBJECT_TYPE;
   private final ObjectFunctionLibrary ofl;

   public DateTimeEvaluators() throws URISyntaxException {
      try {
         this.TIME_ATTR_URI = new URI("urn:bea:xacml:2.0:environment:context:portal:time");
         this.DATE_ATTR_URI = new URI("urn:bea:xacml:2.0:environment:context:portal:date");
         this.DATETIME_ATTR_URI = new URI("urn:bea:xacml:2.0:environment:context:portal:timeInstant");
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }

      this.OBJECT_TYPE = Type.OBJECT.getDataType();
      this.ofl = new ObjectFunctionLibrary();
   }

   public AttributeEvaluator getDateEvaluator() {
      return new AttributeEvaluatorWrapper(Type.DATE) {
         public DateAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            AttributeEvaluatableFactory fac = context.getEnvironmentAttributes();
            if (fac != null) {
               AttributeEvaluator aeObj = fac.getEvaluatable(DateTimeEvaluators.this.DATE_ATTR_URI, DateTimeEvaluators.this.OBJECT_TYPE);
               if (aeObj != null) {
                  Bag obj = aeObj.evaluateToBag(context);
                  if (obj != null && obj.size() == 1) {
                     return DateTimeEvaluators.this.ofl.convertObjectToDate(context, (JavaObjectAttribute)obj.iterator().next());
                  }
               }
            }

            return null;
         }
      };
   }

   public AttributeEvaluator getDateTimeEvaluator() {
      return new AttributeEvaluatorWrapper(Type.DATE_TIME) {
         public DateTimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            AttributeEvaluatableFactory fac = context.getEnvironmentAttributes();
            if (fac != null) {
               AttributeEvaluator aeObj = fac.getEvaluatable(DateTimeEvaluators.this.DATETIME_ATTR_URI, DateTimeEvaluators.this.OBJECT_TYPE);
               if (aeObj != null) {
                  Bag obj = aeObj.evaluateToBag(context);
                  if (obj != null && obj.size() == 1) {
                     return DateTimeEvaluators.this.ofl.convertObjectToDateTime(context, (JavaObjectAttribute)obj.iterator().next());
                  }
               }
            }

            return null;
         }
      };
   }

   public AttributeEvaluator getTimeEvaluator() {
      return new AttributeEvaluatorWrapper(Type.TIME) {
         public TimeAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
            AttributeEvaluatableFactory fac = context.getEnvironmentAttributes();
            if (fac != null) {
               AttributeEvaluator aeObj = fac.getEvaluatable(DateTimeEvaluators.this.TIME_ATTR_URI, DateTimeEvaluators.this.OBJECT_TYPE);
               if (aeObj != null) {
                  Bag obj = aeObj.evaluateToBag(context);
                  if (obj != null && obj.size() == 1) {
                     return DateTimeEvaluators.this.ofl.convertObjectToTime(context, (JavaObjectAttribute)obj.iterator().next());
                  }
               }
            }

            return null;
         }
      };
   }
}
