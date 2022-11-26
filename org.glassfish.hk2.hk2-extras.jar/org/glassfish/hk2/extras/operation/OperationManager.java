package org.glassfish.hk2.extras.operation;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface OperationManager {
   OperationHandle createOperation(Annotation var1);

   OperationHandle createAndStartOperation(Annotation var1);

   Set getCurrentOperations(Annotation var1);

   OperationHandle getCurrentOperation(Annotation var1);

   void shutdownAllOperations(Annotation var1);
}
