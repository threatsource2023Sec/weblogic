package org.apache.xml.security.stax.ext;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public abstract class AbstractBufferingOutputProcessor extends AbstractOutputProcessor {
   private final ArrayDeque xmlSecEventBuffer = new ArrayDeque(100);

   protected AbstractBufferingOutputProcessor() throws XMLSecurityException {
   }

   protected Deque getXmlSecEventBuffer() {
      return this.xmlSecEventBuffer;
   }

   public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      this.xmlSecEventBuffer.offer(xmlSecEvent);
   }

   public void doFinal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
      OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
      this.flushBufferAndCallbackAfterHeader(subOutputProcessorChain, this.getXmlSecEventBuffer());
      subOutputProcessorChain.doFinal();
      subOutputProcessorChain.removeProcessor(this);
   }

   protected abstract void processHeaderEvent(OutputProcessorChain var1) throws XMLStreamException, XMLSecurityException;

   protected void flushBufferAndCallbackAfterHeader(OutputProcessorChain outputProcessorChain, Deque xmlSecEventDeque) throws XMLStreamException, XMLSecurityException {
      this.processHeaderEvent(outputProcessorChain);

      while(!xmlSecEventDeque.isEmpty()) {
         XMLSecEvent xmlSecEvent = (XMLSecEvent)xmlSecEventDeque.pop();
         outputProcessorChain.reset();
         outputProcessorChain.processEvent(xmlSecEvent);
      }

      outputProcessorChain.reset();
   }
}
