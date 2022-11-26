package org.glassfish.grizzly;

import org.glassfish.grizzly.attributes.AttributeStorage;

public interface Transformer {
   String getName();

   TransformationResult transform(AttributeStorage var1, Object var2) throws TransformationException;

   TransformationResult getLastResult(AttributeStorage var1);

   void release(AttributeStorage var1);

   boolean hasInputRemaining(AttributeStorage var1, Object var2);
}
