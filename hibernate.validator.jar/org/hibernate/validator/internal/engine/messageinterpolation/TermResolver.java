package org.hibernate.validator.internal.engine.messageinterpolation;

import javax.validation.MessageInterpolator;

public interface TermResolver {
   String interpolate(MessageInterpolator.Context var1, String var2);
}
