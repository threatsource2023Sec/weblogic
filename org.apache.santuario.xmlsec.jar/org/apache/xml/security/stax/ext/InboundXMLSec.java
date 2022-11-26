package org.apache.xml.security.stax.ext;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xml.security.stax.impl.DocumentContextImpl;
import org.apache.xml.security.stax.impl.InboundSecurityContextImpl;
import org.apache.xml.security.stax.impl.InputProcessorChainImpl;
import org.apache.xml.security.stax.impl.XMLSecurityStreamReader;
import org.apache.xml.security.stax.impl.processor.input.LogInputProcessor;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.stax.impl.processor.input.XMLSecurityInputProcessor;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundXMLSec {
   protected static final transient Logger LOG = LoggerFactory.getLogger(InboundXMLSec.class);
   private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
   private final XMLSecurityProperties securityProperties;

   public InboundXMLSec(XMLSecurityProperties securityProperties) {
      this.securityProperties = securityProperties;
   }

   public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader) throws XMLStreamException {
      return this.processInMessage(xmlStreamReader, (List)null, (SecurityEventListener)null);
   }

   public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader, List requestSecurityEvents, SecurityEventListener securityEventListener) throws XMLStreamException {
      if (requestSecurityEvents == null) {
         requestSecurityEvents = Collections.emptyList();
      }

      InboundSecurityContextImpl inboundSecurityContext = new InboundSecurityContextImpl();
      inboundSecurityContext.putList(SecurityEvent.class, requestSecurityEvents);
      inboundSecurityContext.addSecurityEventListener(securityEventListener);
      inboundSecurityContext.put("XMLInputFactory", xmlInputFactory);
      DocumentContextImpl documentContext = new DocumentContextImpl();
      documentContext.setEncoding(xmlStreamReader.getEncoding() != null ? xmlStreamReader.getEncoding() : StandardCharsets.UTF_8.name());
      Location location = xmlStreamReader.getLocation();
      if (location != null) {
         documentContext.setBaseURI(location.getSystemId());
      }

      InputProcessorChainImpl inputProcessorChain = new InputProcessorChainImpl(inboundSecurityContext, documentContext);
      inputProcessorChain.addProcessor(new XMLEventReaderInputProcessor(this.securityProperties, xmlStreamReader));
      List additionalInputProcessors = this.securityProperties.getInputProcessorList();
      if (!additionalInputProcessors.isEmpty()) {
         Iterator inputProcessorIterator = additionalInputProcessors.iterator();

         while(inputProcessorIterator.hasNext()) {
            InputProcessor inputProcessor = (InputProcessor)inputProcessorIterator.next();
            inputProcessorChain.addProcessor(inputProcessor);
         }
      }

      inputProcessorChain.addProcessor(new XMLSecurityInputProcessor(this.securityProperties));
      if (LOG.isTraceEnabled()) {
         LogInputProcessor LOGInputProcessor = new LogInputProcessor(this.securityProperties);
         LOGInputProcessor.addAfterProcessor(XMLSecurityInputProcessor.class.getName());
         inputProcessorChain.addProcessor(LOGInputProcessor);
      }

      return new XMLSecurityStreamReader(inputProcessorChain, this.securityProperties);
   }

   static {
      xmlInputFactory.setProperty("javax.xml.stream.supportDTD", false);
      xmlInputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);

      try {
         xmlInputFactory.setProperty("org.codehaus.stax2.internNames", true);
         xmlInputFactory.setProperty("org.codehaus.stax2.internNsUris", true);
         xmlInputFactory.setProperty("org.codehaus.stax2.preserveLocation", false);
      } catch (IllegalArgumentException var1) {
         LOG.debug(var1.getMessage(), var1);
      }

   }
}
