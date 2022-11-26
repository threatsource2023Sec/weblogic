package org.opensaml.saml.common.binding.artifact.impl;

import java.io.IOException;
import java.io.StringReader;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.artifact.BasicSAMLArtifactMapEntry;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.storage.StorageSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StorageServiceSAMLArtifactMapEntryFactory extends AbstractInitializableComponent implements SAMLArtifactMap.SAMLArtifactMapEntryFactory, StorageSerializer {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(StorageServiceSAMLArtifactMapEntryFactory.class);
   @Nonnull
   private ParserPool parserPool = XMLObjectProviderRegistrySupport.getParserPool();

   @Nonnull
   public ParserPool getParserPool() {
      return this.parserPool;
   }

   public void setParserPool(@Nonnull ParserPool pool) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.parserPool = (ParserPool)Constraint.isNotNull(pool, "ParserPool cannot be null");
   }

   @Nonnull
   public SAMLArtifactMap.SAMLArtifactMapEntry newEntry(@Nonnull @NotEmpty String artifact, @Nonnull @NotEmpty String issuerId, @Nonnull @NotEmpty String relyingPartyId, @Nonnull SAMLObject samlMessage) {
      try {
         return new BasicSAMLArtifactMapEntry(artifact, issuerId, relyingPartyId, samlMessage);
      } catch (UnmarshallingException | MarshallingException var6) {
         throw new XMLRuntimeException("Error creating BasicSAMLArtifactMapEntry", var6);
      }
   }

   @Nonnull
   public String serialize(@Nonnull SAMLArtifactMap.SAMLArtifactMapEntry instance) throws IOException {
      this.log.debug("Serializing SAMLArtifactMapEntry for storage");

      Element marshalledMessage;
      try {
         marshalledMessage = XMLObjectSupport.marshall(instance.getSamlMessage());
      } catch (MarshallingException var5) {
         throw new IOException("Error marshalling SAML message", var5);
      }

      Element rootElement = marshalledMessage.getOwnerDocument().createElementNS((String)null, "Mapping");
      rootElement.setAttributeNS((String)null, "issuer", instance.getIssuerId());
      rootElement.setAttributeNS((String)null, "relyingParty", instance.getRelyingPartyId());
      rootElement.appendChild(marshalledMessage);
      String serializedMessage = SerializeSupport.nodeToString(rootElement);
      if (this.log.isTraceEnabled()) {
         this.log.trace("Serialized SAMLArtifactMapEntry data is:");
         this.log.trace(serializedMessage);
      }

      return serializedMessage;
   }

   @Nonnull
   public SAMLArtifactMap.SAMLArtifactMapEntry deserialize(long version, @Nonnull @NotEmpty String context, @Nonnull @NotEmpty String key, @Nonnull @NotEmpty String value, @Nullable Long expiration) throws IOException {
      this.log.debug("Deserializing artifact mapping data from stored string");
      if (this.log.isTraceEnabled()) {
         this.log.trace("Serialized SAMLArtifactMapEntry data is:");
         this.log.trace(value);
      }

      try {
         Element rootElement = this.getParserPool().parse(new StringReader(value)).getDocumentElement();
         Node messageElement = rootElement.getFirstChild();
         if (!ElementSupport.isElementNamed(rootElement, (String)null, "Mapping")) {
            throw new IOException("SAMLArtifactMapEntry XML not rooted by expected element");
         } else if (messageElement != null && messageElement.getNodeType() == 1) {
            String issuer = rootElement.getAttributeNS((String)null, "issuer");
            String relyingParty = rootElement.getAttributeNS((String)null, "relyingParty");
            if (issuer != null && relyingParty != null) {
               Unmarshaller unmarshaller = XMLObjectSupport.getUnmarshaller((Element)rootElement.getFirstChild());
               if (unmarshaller == null) {
                  throw new UnmarshallingException("Unable to obtain unmarshaller for element " + QNameSupport.getNodeQName(rootElement.getFirstChild()));
               } else {
                  XMLObject message = unmarshaller.unmarshall((Element)rootElement.removeChild(messageElement));
                  rootElement.getOwnerDocument().replaceChild(messageElement, rootElement);
                  if (!(message instanceof SAMLObject)) {
                     throw new IOException("SAMLArtifactMapEntry's XMLObject was not a SAML message");
                  } else {
                     return this.newEntry(key, issuer, relyingParty, (SAMLObject)message);
                  }
               }
            } else {
               throw new IOException("SAMLArtifactMapEntry XML missing issuer or relyingParty attributes");
            }
         } else {
            throw new IOException("SAMLArtifactMapEntry XML missing child element");
         }
      } catch (XMLParserException var13) {
         throw new IOException("Error parsing XML into DOM", var13);
      } catch (UnmarshallingException var14) {
         throw new IOException("Error unmarshalling DOM into SAMLObject", var14);
      }
   }
}
