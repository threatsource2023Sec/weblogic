package org.opensaml.saml.saml1.binding.encoding.impl;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.codec.HTMLEncoder;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPPostEncoder extends BaseSAML1MessageEncoder {
   public static final String DEFAULT_TEMPLATE_ID = "/templates/saml1-post-binding.vm";
   private final Logger log = LoggerFactory.getLogger(HTTPPostEncoder.class);
   private VelocityEngine velocityEngine;
   private String velocityTemplateId;

   public HTTPPostEncoder() {
      this.setVelocityTemplateId("/templates/saml1-post-binding.vm");
   }

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";
   }

   public VelocityEngine getVelocityEngine() {
      return this.velocityEngine;
   }

   public void setVelocityEngine(VelocityEngine newVelocityEngine) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.velocityEngine = newVelocityEngine;
   }

   public String getVelocityTemplateId() {
      return this.velocityTemplateId;
   }

   public void setVelocityTemplateId(String newVelocityTemplateId) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.velocityTemplateId = newVelocityTemplateId;
   }

   protected void doDestroy() {
      this.velocityEngine = null;
      this.velocityTemplateId = null;
      super.doDestroy();
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.velocityEngine == null) {
         throw new ComponentInitializationException("VelocityEngine must be supplied");
      } else if (this.velocityTemplateId == null) {
         throw new ComponentInitializationException("Velocity template id must be supplied");
      }
   }

   protected void doEncode() throws MessageEncodingException {
      MessageContext messageContext = this.getMessageContext();
      SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
      if (outboundMessage == null) {
         throw new MessageEncodingException("No outbound SAML message contained in message context");
      } else {
         String endpointURL = this.getEndpointURL(messageContext).toString();
         this.postEncode(messageContext, endpointURL);
      }
   }

   protected void postEncode(MessageContext messageContext, String endpointURL) throws MessageEncodingException {
      this.log.debug("Invoking velocity template to create POST body");

      try {
         VelocityContext context = new VelocityContext();
         SAMLObject message = (SAMLObject)messageContext.getMessage();
         String encodedEndpointURL = HTMLEncoder.encodeForHTMLAttribute(endpointURL);
         this.log.debug("Encoding action url of '{}' with encoded value '{}'", endpointURL, encodedEndpointURL);
         context.put("action", encodedEndpointURL);
         context.put("binding", this.getBindingURI());
         this.log.debug("Marshalling and Base64 encoding SAML message");
         String messageXML = SerializeSupport.nodeToString(this.marshallMessage(message));
         String encodedMessage = Base64Support.encode(messageXML.getBytes("UTF-8"), false);
         context.put("SAMLResponse", encodedMessage);
         String relayState = SAMLBindingSupport.getRelayState(messageContext);
         if (relayState != null) {
            String encodedRelayState = HTMLEncoder.encodeForHTMLAttribute(relayState);
            this.log.debug("Setting TARGET parameter to: '{}', encoded as '{}'", relayState, encodedRelayState);
            context.put("TARGET", encodedRelayState);
         }

         HttpServletResponse response = this.getHttpServletResponse();
         HttpServletSupport.addNoCacheHeaders(response);
         HttpServletSupport.setUTF8Encoding(response);
         HttpServletSupport.setContentType(response, "text/html");
         Writer out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
         this.velocityEngine.mergeTemplate(this.velocityTemplateId, "UTF-8", context, out);
         out.flush();
      } catch (UnsupportedEncodingException var11) {
         this.log.error("UTF-8 encoding is not supported, this VM is not Java compliant.");
         throw new MessageEncodingException("Unable to encode message, UTF-8 encoding is not supported");
      } catch (Exception var12) {
         this.log.error("Error invoking velocity template", var12);
         throw new MessageEncodingException("Error creating output document", var12);
      }
   }
}
