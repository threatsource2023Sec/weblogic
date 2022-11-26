package org.opensaml.soap.client.http;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipelineFactory;
import org.opensaml.soap.common.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public class PipelineFactoryHttpSOAPClient extends AbstractPipelineHttpSOAPClient {
   private Logger log = LoggerFactory.getLogger(PipelineFactoryHttpSOAPClient.class);
   private HttpClientMessagePipelineFactory pipelineFactory;
   private Function pipelineNameStrategy;

   public void setPipelineFactory(@Nonnull HttpClientMessagePipelineFactory factory) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.pipelineFactory = (HttpClientMessagePipelineFactory)Constraint.isNotNull(factory, "HttpClientPipelineFactory cannot be null");
   }

   public void setPipelineNameStrategy(@Nullable Function function) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.pipelineNameStrategy = function;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.pipelineFactory == null) {
         throw new ComponentInitializationException("HttpClientPipelineFactory cannot be null");
      }
   }

   protected void doDestroy() {
      this.pipelineFactory = null;
      this.pipelineNameStrategy = null;
      super.doDestroy();
   }

   @Nonnull
   protected HttpClientMessagePipeline resolvePipeline(InOutOperationContext operationContext) throws SOAPException {
      String resolvedPipelineName = null;

      try {
         resolvedPipelineName = this.resolvePipelineName(operationContext);
         this.log.debug("Resolved pipeline name: {}", resolvedPipelineName);
         return resolvedPipelineName != null ? this.newPipeline(resolvedPipelineName) : this.newPipeline();
      } catch (SOAPException var4) {
         this.log.warn("Problem resolving pipeline instance with name: {}", resolvedPipelineName, var4);
         throw var4;
      } catch (Exception var5) {
         this.log.warn("Problem resolving pipeline instance with name: {}", resolvedPipelineName, var5);
         throw new SOAPException("Could not resolve pipeline with name: " + resolvedPipelineName, var5);
      }
   }

   @Nonnull
   protected HttpClientMessagePipeline newPipeline() throws SOAPException {
      return this.pipelineFactory.newInstance();
   }

   @Nullable
   protected HttpClientMessagePipeline newPipeline(@Nullable String name) throws SOAPException {
      return this.pipelineFactory.newInstance(name);
   }

   @Nullable
   protected String resolvePipelineName(@Nonnull InOutOperationContext operationContext) {
      return this.pipelineNameStrategy != null ? (String)this.pipelineNameStrategy.apply(operationContext) : null;
   }
}
