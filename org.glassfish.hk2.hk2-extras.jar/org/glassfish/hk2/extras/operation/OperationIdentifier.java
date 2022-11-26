package org.glassfish.hk2.extras.operation;

import java.lang.annotation.Annotation;

public interface OperationIdentifier {
   String getOperationIdentifier();

   Annotation getOperationScope();
}
