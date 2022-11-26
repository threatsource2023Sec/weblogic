package org.glassfish.grizzly;

import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;

public abstract class AbstractTransformer implements Transformer {
   protected final AttributeBuilder attributeBuilder;
   protected final Attribute stateAttr;
   private MemoryManager memoryManager;

   public AbstractTransformer() {
      this.attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
      String namePrefix = this.getNamePrefix();
      this.stateAttr = this.attributeBuilder.createAttribute(namePrefix + ".state");
   }

   protected String getNamePrefix() {
      return this.getClass().getName();
   }

   public final TransformationResult transform(AttributeStorage storage, Object input) throws TransformationException {
      return this.saveLastResult(storage, this.transformImpl(storage, input));
   }

   protected abstract TransformationResult transformImpl(AttributeStorage var1, Object var2) throws TransformationException;

   public final TransformationResult getLastResult(AttributeStorage storage) {
      LastResultAwareState state = (LastResultAwareState)this.stateAttr.get(storage);
      return state != null ? state.getLastResult() : null;
   }

   protected final TransformationResult saveLastResult(AttributeStorage storage, TransformationResult result) {
      this.obtainStateObject(storage).setLastResult(result);
      return result;
   }

   public void release(AttributeStorage storage) {
      this.stateAttr.remove(storage);
   }

   protected MemoryManager obtainMemoryManager(AttributeStorage storage) {
      if (this.memoryManager != null) {
         return this.memoryManager;
      } else if (storage instanceof Connection) {
         Connection connection = (Connection)storage;
         return connection.getMemoryManager();
      } else {
         return MemoryManager.DEFAULT_MEMORY_MANAGER;
      }
   }

   public MemoryManager getMemoryManager() {
      return this.memoryManager;
   }

   public void setMemoryManager(MemoryManager memoryManager) {
      this.memoryManager = memoryManager;
   }

   public static Object getValue(AttributeStorage storage, Attribute attribute, Object defaultValue) {
      Object value = attribute.get(storage);
      return value != null ? value : defaultValue;
   }

   protected final LastResultAwareState obtainStateObject(AttributeStorage storage) {
      LastResultAwareState value = (LastResultAwareState)this.stateAttr.get(storage);
      if (value == null) {
         value = this.createStateObject();
         this.stateAttr.set((AttributeStorage)storage, value);
      }

      return value;
   }

   protected LastResultAwareState createStateObject() {
      return new LastResultAwareState();
   }

   public static class LastResultAwareState {
      protected TransformationResult lastResult;

      public TransformationResult getLastResult() {
         return this.lastResult;
      }

      public void setLastResult(TransformationResult lastResult) {
         this.lastResult = lastResult;
      }
   }
}
