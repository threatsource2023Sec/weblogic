package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class TransformerOutput extends BufferedOutput {
   private final Attribute outputBufferAttr;
   protected final Transformer transformer;
   protected final Output underlyingOutput;
   protected final MemoryManager memoryManager;
   protected final AttributeStorage attributeStorage;

   public TransformerOutput(Transformer transformer, Output underlyingOutput, Connection connection) {
      this(transformer, underlyingOutput, connection.getMemoryManager(), connection);
   }

   public TransformerOutput(Transformer transformer, Output underlyingOutput, MemoryManager memoryManager, AttributeStorage attributeStorage) {
      this.transformer = transformer;
      this.underlyingOutput = underlyingOutput;
      this.memoryManager = memoryManager;
      this.attributeStorage = attributeStorage;
      this.outputBufferAttr = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("TransformerOutput-" + transformer.getName());
   }

   protected GrizzlyFuture flush0(Buffer buffer, CompletionHandler completionHandler) throws IOException {
      if (buffer != null) {
         CompositeBuffer savedBuffer = (CompositeBuffer)this.outputBufferAttr.get(this.attributeStorage);
         if (savedBuffer != null) {
            savedBuffer.append((Buffer)buffer);
            buffer = savedBuffer;
         }

         do {
            TransformationResult result = this.transformer.transform(this.attributeStorage, buffer);
            TransformationResult.Status status = result.getStatus();
            if (status == TransformationResult.Status.COMPLETE) {
               Buffer outputBuffer = (Buffer)result.getMessage();
               this.underlyingOutput.write(outputBuffer);
               this.transformer.release(this.attributeStorage);
            } else {
               if (status == TransformationResult.Status.INCOMPLETE) {
                  ((Buffer)buffer).compact();
                  if (!((Buffer)buffer).isComposite()) {
                     buffer = CompositeBuffer.newBuffer(this.memoryManager, (Buffer)buffer);
                  }

                  this.outputBufferAttr.set((AttributeStorage)this.attributeStorage, (CompositeBuffer)buffer);
                  return ReadyFutureImpl.create((Throwable)(new IllegalStateException("Can not flush data: Insufficient input data for transformer")));
               }

               if (status == TransformationResult.Status.ERROR) {
                  this.transformer.release(this.attributeStorage);
                  throw new IOException("Transformation exception: " + result.getErrorDescription());
               }
            }
         } while(((Buffer)buffer).hasRemaining());

         return this.underlyingOutput.flush(completionHandler);
      } else {
         return ZERO_READY_FUTURE;
      }
   }

   protected Buffer newBuffer(int size) {
      return this.memoryManager.allocate(size);
   }

   protected Buffer reallocateBuffer(Buffer oldBuffer, int size) {
      return this.memoryManager.reallocate(oldBuffer, size);
   }

   protected void onClosed() throws IOException {
   }
}
