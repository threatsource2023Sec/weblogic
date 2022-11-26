package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.conditions.Condition;

public final class TransformerInput extends BufferedInput {
   private final Attribute inputBufferAttr;
   protected final Transformer transformer;
   protected final Input underlyingInput;
   protected final MemoryManager memoryManager;
   protected final AttributeStorage attributeStorage;

   public TransformerInput(Transformer transformer, Input underlyingInput, Connection connection) {
      this(transformer, underlyingInput, connection.getMemoryManager(), connection);
   }

   public TransformerInput(Transformer transformer, Input underlyingInput, MemoryManager memoryManager, AttributeStorage attributeStorage) {
      this.transformer = transformer;
      this.underlyingInput = underlyingInput;
      this.memoryManager = memoryManager;
      this.attributeStorage = attributeStorage;
      this.inputBufferAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("TransformerInput-" + transformer.getName());
   }

   protected void onOpenInputSource() throws IOException {
      this.underlyingInput.notifyCondition(new TransformerCondition(), new TransformerCompletionHandler());
   }

   protected void onCloseInputSource() throws IOException {
   }

   public final class TransformerCondition implements Condition {
      public boolean check() {
         try {
            CompositeBuffer savedBuffer = (CompositeBuffer)TransformerInput.this.inputBufferAttr.get(TransformerInput.this.attributeStorage);
            Buffer bufferToTransform = savedBuffer;
            boolean hasSavedBuffer = savedBuffer != null;
            Buffer chunkBuffer;
            if (TransformerInput.this.underlyingInput.isBuffered()) {
               chunkBuffer = TransformerInput.this.underlyingInput.takeBuffer();
            } else {
               int size = TransformerInput.this.underlyingInput.size();
               chunkBuffer = TransformerInput.this.memoryManager.allocate(size);

               while(size-- >= 0) {
                  chunkBuffer.put(TransformerInput.this.underlyingInput.read());
               }

               chunkBuffer.flip();
            }

            if (hasSavedBuffer) {
               savedBuffer.append(chunkBuffer);
            } else {
               bufferToTransform = chunkBuffer;
            }

            while(true) {
               while(((Buffer)bufferToTransform).hasRemaining()) {
                  TransformationResult result = TransformerInput.this.transformer.transform(TransformerInput.this.attributeStorage, bufferToTransform);
                  TransformationResult.Status status = result.getStatus();
                  if (status == TransformationResult.Status.COMPLETE) {
                     Buffer outputBuffer = (Buffer)result.getMessage();
                     TransformerInput.this.lock.writeLock().lock();

                     try {
                        TransformerInput.this.append(outputBuffer);
                        if (!TransformerInput.this.isCompletionHandlerRegistered) {
                           boolean var8 = true;
                           return var8;
                        }
                     } finally {
                        TransformerInput.this.lock.writeLock().unlock();
                     }
                  } else {
                     if (status == TransformationResult.Status.INCOMPLETE) {
                        if (!hasSavedBuffer) {
                           if (((Buffer)bufferToTransform).isComposite()) {
                              TransformerInput.this.inputBufferAttr.set((AttributeStorage)TransformerInput.this.attributeStorage, (CompositeBuffer)bufferToTransform);
                           } else {
                              savedBuffer = CompositeBuffer.newBuffer(TransformerInput.this.memoryManager);
                              savedBuffer.append((Buffer)bufferToTransform);
                              TransformerInput.this.inputBufferAttr.set((AttributeStorage)TransformerInput.this.attributeStorage, savedBuffer);
                           }
                        }

                        return false;
                     }

                     if (status == TransformationResult.Status.ERROR) {
                        throw new TransformationException(result.getErrorDescription());
                     }
                  }
               }

               return false;
            }
         } catch (IOException var13) {
            throw new TransformationException(var13);
         }
      }
   }

   public final class TransformerCompletionHandler extends EmptyCompletionHandler {
      public void failed(Throwable throwable) {
         TransformerInput.this.notifyFailure(TransformerInput.this.completionHandler, throwable);
         TransformerInput.this.future.failure(throwable);
      }
   }
}
