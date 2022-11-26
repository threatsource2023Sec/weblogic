package org.hibernate.validator.messageinterpolation;

import java.util.Map;
import javax.validation.MessageInterpolator;

public interface HibernateMessageInterpolatorContext extends MessageInterpolator.Context {
   Class getRootBeanType();

   Map getMessageParameters();

   Map getExpressionVariables();
}
