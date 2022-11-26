package org.opensaml.soap.messaging;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.soap.messaging.context.InboundSOAPContext;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.Header;
import org.opensaml.soap.util.SOAPSupport;
import org.opensaml.soap.util.SOAPVersion;

public final class SOAPMessagingSupport {
   private SOAPMessagingSupport() {
   }

   @Nonnull
   public static InboundSOAPContext getInboundSOAPContext(@Nonnull MessageContext messageContext) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      return (InboundSOAPContext)messageContext.getSubcontext(InboundSOAPContext.class, true);
   }

   @Nullable
   public static SOAP11Context getSOAP11Context(@Nonnull MessageContext messageContext, boolean autoCreate) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      return (SOAP11Context)messageContext.getSubcontext(SOAP11Context.class, autoCreate);
   }

   public static void registerUnderstoodHeader(@Nonnull MessageContext msgContext, @Nonnull XMLObject header) {
      InboundSOAPContext inboundContext = getInboundSOAPContext(msgContext);
      inboundContext.getUnderstoodHeaders().add(header);
   }

   public static boolean checkUnderstoodHeader(@Nonnull MessageContext msgContext, @Nonnull XMLObject header) {
      InboundSOAPContext inboundContext = getInboundSOAPContext(msgContext);
      return inboundContext.getUnderstoodHeaders().contains(header);
   }

   public static boolean isSOAPMessage(@Nonnull MessageContext messageContext) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SOAPVersion version = getSOAPVersion(messageContext);
      return version != null;
   }

   public static boolean isSOAP11Message(@Nonnull MessageContext messageContext) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SOAPVersion version = getSOAPVersion(messageContext);
      return version != null && SOAPVersion.SOAP_1_1.equals(version);
   }

   @Nullable
   public static SOAPVersion getSOAPVersion(@Nonnull MessageContext messageContext) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SOAP11Context soap11 = getSOAP11Context(messageContext, false);
      return soap11 != null && soap11.getEnvelope() != null ? SOAPVersion.SOAP_1_1 : null;
   }

   public static boolean isMustUnderstand(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      Constraint.isNotNull(headerBlock, "Header block context cannot be null");
      SOAPVersion soapVersion = getSOAPVersion(messageContext);
      if (soapVersion == null) {
         throw new IllegalArgumentException("Could not determine SOAP version for message context");
      } else {
         switch (soapVersion) {
            case SOAP_1_1:
               return SOAPSupport.getSOAP11MustUnderstandAttribute(headerBlock);
            case SOAP_1_2:
               return SOAPSupport.getSOAP12MustUnderstandAttribute(headerBlock);
            default:
               throw new IllegalArgumentException("Saw unsupported SOAP version: " + soapVersion);
         }
      }
   }

   public static void addMustUnderstand(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock, boolean mustUnderstand) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      Constraint.isNotNull(headerBlock, "Header block context cannot be null");
      SOAPVersion soapVersion = getSOAPVersion(messageContext);
      if (soapVersion == null) {
         throw new IllegalArgumentException("Could not determine SOAP version for message context");
      } else {
         switch (soapVersion) {
            case SOAP_1_1:
               SOAPSupport.addSOAP11MustUnderstandAttribute(headerBlock, mustUnderstand);
               break;
            case SOAP_1_2:
               SOAPSupport.addSOAP12MustUnderstandAttribute(headerBlock, mustUnderstand);
               break;
            default:
               throw new IllegalArgumentException("Saw unsupported SOAP version: " + soapVersion);
         }

      }
   }

   public static void addTargetNode(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock, @Nullable String targetNode) {
      if (targetNode != null) {
         Constraint.isNotNull(messageContext, "Message context cannot be null");
         Constraint.isNotNull(headerBlock, "Header block context cannot be null");
         SOAPVersion soapVersion = getSOAPVersion(messageContext);
         if (soapVersion == null) {
            throw new IllegalArgumentException("Could not determine SOAP version for message context");
         } else {
            switch (soapVersion) {
               case SOAP_1_1:
                  SOAPSupport.addSOAP11ActorAttribute(headerBlock, targetNode);
                  break;
               case SOAP_1_2:
                  SOAPSupport.addSOAP12RoleAttribute(headerBlock, targetNode);
                  break;
               default:
                  throw new IllegalArgumentException("Saw unsupported SOAP version: " + soapVersion);
            }

         }
      }
   }

   @Nonnull
   public static List getInboundHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull QName headerName) {
      InboundSOAPContext inboundContext = getInboundSOAPContext(messageContext);
      return getHeaderBlock(messageContext, headerName, inboundContext.getNodeActors(), inboundContext.isFinalDestination());
   }

   @Nonnull
   public static List getOutboundHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull QName headerName) {
      return getHeaderBlock(messageContext, headerName, (Set)null, true);
   }

   @Nonnull
   public static List getHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull QName headerName, @Nullable Set targetNodes, boolean isFinalDestination) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SOAP11Context soap11 = getSOAP11Context(messageContext, false);
      return soap11 != null && soap11.getEnvelope() != null ? getSOAP11HeaderBlock(soap11.getEnvelope(), headerName, targetNodes, isFinalDestination) : Collections.emptyList();
   }

   @Nonnull
   public static List getSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull QName headerName, @Nullable Set targetNodes, boolean isFinalDestination) {
      Constraint.isNotNull(envelope, "Envelope cannot be null");
      Constraint.isNotNull(headerName, "Header name cannot be null");
      Header envelopeHeader = envelope.getHeader();
      if (envelopeHeader == null) {
         return Collections.emptyList();
      } else {
         LazyList headers = new LazyList();
         Iterator var6 = envelopeHeader.getUnknownXMLObjects(headerName).iterator();

         while(var6.hasNext()) {
            XMLObject header = (XMLObject)var6.next();
            if (isSOAP11HeaderTargetedToNode(header, targetNodes, isFinalDestination)) {
               headers.add(header);
            }
         }

         return headers;
      }
   }

   public static boolean isSOAP11HeaderTargetedToNode(@Nonnull XMLObject header, @Nullable Set nodeActors, boolean isFinalDestination) {
      String headerActor = SOAPSupport.getSOAP11ActorAttribute(header);
      if (headerActor == null) {
         if (isFinalDestination) {
            return true;
         }
      } else {
         if ("http://schemas.xmlsoap.org/soap/actor/next".equals(headerActor)) {
            return true;
         }

         if (nodeActors != null && nodeActors.contains(headerActor)) {
            return true;
         }
      }

      return false;
   }

   public static void addHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock) {
      Constraint.isNotNull(messageContext, "Message context cannot be null");
      SOAP11Context soap11 = getSOAP11Context(messageContext, false);
      if (soap11 != null && soap11.getEnvelope() != null) {
         addSOAP11HeaderBlock(soap11.getEnvelope(), headerBlock);
      } else {
         throw new IllegalArgumentException("Message context did not contain a SOAP Envelope");
      }
   }

   public static void addSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull XMLObject headerBlock) {
      Constraint.isNotNull(envelope, "Envelope cannot be null");
      Constraint.isNotNull(headerBlock, "Header block cannot be null");
      Header envelopeHeader = envelope.getHeader();
      if (envelopeHeader == null) {
         envelopeHeader = (Header)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilder(Header.DEFAULT_ELEMENT_NAME).buildObject(Header.DEFAULT_ELEMENT_NAME);
         envelope.setHeader(envelopeHeader);
      }

      envelopeHeader.getUnknownXMLObjects().add(headerBlock);
   }

   public static void registerSOAP11Fault(@Nonnull MessageContext messageContext, @Nonnull QName faultCode, @Nonnull String faultString, @Nullable String faultActor, @Nullable List detailChildren, @Nullable Map detailAttributes) {
      registerSOAP11Fault(messageContext, SOAPSupport.buildSOAP11Fault(faultCode, faultString, faultActor, detailChildren, detailAttributes));
   }

   public static void registerSOAP11Fault(@Nonnull MessageContext messageContext, @Nullable Fault fault) {
      SOAP11Context soap11Context = getSOAP11Context(messageContext, true);
      soap11Context.setFault(fault);
   }

   public static Fault getSOAP11Fault(@Nonnull MessageContext messageContext) {
      SOAP11Context soap11Context = getSOAP11Context(messageContext, false);
      return soap11Context != null ? soap11Context.getFault() : null;
   }

   public static void clearFault(@Nonnull MessageContext messageContext) {
      SOAP11Context soap11Context = getSOAP11Context(messageContext, false);
      if (soap11Context != null) {
         soap11Context.setFault((Fault)null);
      }

   }
}
