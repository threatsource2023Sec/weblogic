package org.opensaml.soap.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.EncodingStyleBearing;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.soap.soap12.RelayBearing;
import org.opensaml.soap.soap12.RoleBearing;

public final class SOAPSupport {
   private SOAPSupport() {
   }

   public static void addSOAP11MustUnderstandAttribute(@Nonnull XMLObject soapObject, boolean mustUnderstand) {
      if (soapObject instanceof MustUnderstandBearing) {
         ((MustUnderstandBearing)soapObject).setSOAP11MustUnderstand(new XSBooleanValue(mustUnderstand, true));
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither MustUnderstandBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, (new XSBooleanValue(mustUnderstand, true)).toString());
      }

   }

   public static boolean getSOAP11MustUnderstandAttribute(@Nonnull XMLObject soapObject) {
      if (soapObject instanceof MustUnderstandBearing) {
         XSBooleanValue value = ((MustUnderstandBearing)soapObject).isSOAP11MustUnderstandXSBoolean();
         if (value != null) {
            return value.getValue();
         }
      }

      if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
         return false;
      } else {
         String value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME));
         return Objects.equals("1", value) || Objects.equals("true", value);
      }
   }

   public static void addSOAP11ActorAttribute(@Nonnull XMLObject soapObject, @Nonnull String actorURI) {
      String value = (String)Constraint.isNotNull(StringSupport.trimOrNull(actorURI), "Actor URI cannot be null or empty");
      if (soapObject instanceof ActorBearing) {
         ((ActorBearing)soapObject).setSOAP11Actor(value);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither ActorBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(ActorBearing.SOAP11_ACTOR_ATTR_NAME, value);
      }

   }

   @Nullable
   public static String getSOAP11ActorAttribute(@Nonnull XMLObject soapObject) {
      String value = null;
      if (soapObject instanceof ActorBearing) {
         value = StringSupport.trimOrNull(((ActorBearing)soapObject).getSOAP11Actor());
         if (value != null) {
            return value;
         }
      }

      if (soapObject instanceof AttributeExtensibleXMLObject) {
         value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(ActorBearing.SOAP11_ACTOR_ATTR_NAME));
         return value;
      } else {
         return null;
      }
   }

   public static void addSOAP11EncodingStyle(@Nonnull XMLObject soapObject, @Nonnull String encodingStyle) {
      String value = (String)Constraint.isNotNull(StringSupport.trimOrNull(encodingStyle), "Encoding style to add cannot be null or empty");
      if (soapObject instanceof EncodingStyleBearing) {
         EncodingStyleBearing esb = (EncodingStyleBearing)soapObject;
         List list = esb.getSOAP11EncodingStyles();
         if (list == null) {
            list = new LazyList();
            esb.setSOAP11EncodingStyles((List)list);
         }

         ((List)list).add(value);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttributeExtensible");
         }

         AttributeMap am = ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes();
         String list = am.get(EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME);
         if (list == null) {
            list = value;
         } else {
            list = list + " " + value;
         }

         am.put(EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME, list);
      }

   }

   public static void addSOAP11EncodingStyles(@Nonnull XMLObject soapObject, @Nonnull List encodingStyles) {
      Constraint.isNotEmpty(encodingStyles, "Encoding styles list cannot be empty");
      if (soapObject instanceof EncodingStyleBearing) {
         ((EncodingStyleBearing)soapObject).setSOAP11EncodingStyles(encodingStyles);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME, StringSupport.listToStringValue(encodingStyles, " "));
      }

   }

   @Nullable
   public static List getSOAP11EncodingStyles(@Nonnull XMLObject soapObject) {
      if (soapObject instanceof EncodingStyleBearing) {
         List value = ((EncodingStyleBearing)soapObject).getSOAP11EncodingStyles();
         if (value != null) {
            return value;
         }
      }

      if (soapObject instanceof AttributeExtensibleXMLObject) {
         String value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME));
         if (value != null) {
            StringSupport.stringToList(value, " \n\r\t");
         }
      }

      return null;
   }

   public static void addSOAP12EncodingStyleAttribute(@Nonnull XMLObject soapObject, @Nonnull String style) {
      String value = (String)Constraint.isNotNull(StringSupport.trimOrNull(style), "Encoding style to add cannot be null or empty");
      if (soapObject instanceof org.opensaml.soap.soap12.EncodingStyleBearing) {
         ((org.opensaml.soap.soap12.EncodingStyleBearing)soapObject).setSOAP12EncodingStyle(value);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttribtueExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(org.opensaml.soap.soap12.EncodingStyleBearing.SOAP12_ENCODING_STYLE_ATTR_NAME, value);
      }

   }

   @Nullable
   public static String getSOAP12EncodingStyleAttribute(@Nonnull XMLObject soapObject) {
      String style = null;
      if (soapObject instanceof org.opensaml.soap.soap12.EncodingStyleBearing) {
         style = ((org.opensaml.soap.soap12.EncodingStyleBearing)soapObject).getSOAP12EncodingStyle();
      }

      if (style == null && soapObject instanceof AttributeExtensibleXMLObject) {
         style = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(org.opensaml.soap.soap12.EncodingStyleBearing.SOAP12_ENCODING_STYLE_ATTR_NAME));
      }

      return style;
   }

   public static void addSOAP12MustUnderstandAttribute(@Nonnull XMLObject soapObject, boolean mustUnderstand) {
      if (soapObject instanceof org.opensaml.soap.soap12.MustUnderstandBearing) {
         ((org.opensaml.soap.soap12.MustUnderstandBearing)soapObject).setSOAP12MustUnderstand(new XSBooleanValue(mustUnderstand, false));
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither MustUnderstandBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(org.opensaml.soap.soap12.MustUnderstandBearing.SOAP12_MUST_UNDERSTAND_ATTR_NAME, (new XSBooleanValue(mustUnderstand, false)).toString());
      }

   }

   public static boolean getSOAP12MustUnderstandAttribute(@Nonnull XMLObject soapObject) {
      if (soapObject instanceof org.opensaml.soap.soap12.MustUnderstandBearing) {
         XSBooleanValue value = ((org.opensaml.soap.soap12.MustUnderstandBearing)soapObject).isSOAP12MustUnderstandXSBoolean();
         if (value != null) {
            return value.getValue();
         }
      }

      if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
         return false;
      } else {
         String value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(org.opensaml.soap.soap12.MustUnderstandBearing.SOAP12_MUST_UNDERSTAND_ATTR_NAME));
         return Objects.equals("1", value) || Objects.equals("true", value);
      }
   }

   public static void addSOAP12RelayAttribute(@Nonnull XMLObject soapObject, boolean relay) {
      if (soapObject instanceof RelayBearing) {
         ((RelayBearing)soapObject).setSOAP12Relay(new XSBooleanValue(relay, false));
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither RelayBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(RelayBearing.SOAP12_RELAY_ATTR_NAME, (new XSBooleanValue(relay, false)).toString());
      }

   }

   public static boolean getSOAP12RelayAttribute(@Nonnull XMLObject soapObject) {
      if (soapObject instanceof RelayBearing) {
         XSBooleanValue value = ((RelayBearing)soapObject).isSOAP12RelayXSBoolean();
         if (value != null) {
            return value.getValue();
         }
      }

      if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
         return false;
      } else {
         String value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(RelayBearing.SOAP12_RELAY_ATTR_NAME));
         return Objects.equals("1", value) || Objects.equals("true", value);
      }
   }

   public static void addSOAP12RoleAttribute(@Nonnull XMLObject soapObject, @Nonnull String role) {
      String value = (String)Constraint.isNotNull(StringSupport.trimOrNull(role), "Role cannot be null or empty");
      if (soapObject instanceof RoleBearing) {
         ((RoleBearing)soapObject).setSOAP12Role(value);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither RoleBearing nor AttribtueExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(RoleBearing.SOAP12_ROLE_ATTR_NAME, value);
      }

   }

   @Nullable
   public static String getSOAP12RoleAttribute(@Nonnull XMLObject soapObject) {
      String role = null;
      if (soapObject instanceof RoleBearing) {
         role = ((RoleBearing)soapObject).getSOAP12Role();
      }

      if (role == null && soapObject instanceof AttributeExtensibleXMLObject) {
         role = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(RoleBearing.SOAP12_ROLE_ATTR_NAME));
      }

      return role;
   }

   /** @deprecated */
   public static void addHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock) {
      SOAPMessagingSupport.addHeaderBlock(messageContext, headerBlock);
   }

   /** @deprecated */
   public static void addSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull XMLObject headerBlock) {
      SOAPMessagingSupport.addSOAP11HeaderBlock(envelope, headerBlock);
   }

   /** @deprecated */
   @Nonnull
   public static List getInboundHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull QName headerName, @Nullable Set targetNodes, boolean isFinalDestination) {
      return SOAPMessagingSupport.getHeaderBlock(messageContext, headerName, targetNodes, isFinalDestination);
   }

   /** @deprecated */
   @Nonnull
   public static List getSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull QName headerName, @Nullable Set targetNodes, boolean isFinalDestination) {
      return SOAPMessagingSupport.getSOAP11HeaderBlock(envelope, headerName, targetNodes, isFinalDestination);
   }

   /** @deprecated */
   public static boolean isSOAP11HeaderTargetedToNode(@Nonnull XMLObject header, @Nullable Set nodeActors, boolean isFinalDestination) {
      return SOAPMessagingSupport.isSOAP11HeaderTargetedToNode(header, nodeActors, isFinalDestination);
   }

   /** @deprecated */
   public static boolean isSOAPMessage(@Nonnull MessageContext messageContext) {
      return SOAPMessagingSupport.isSOAPMessage(messageContext);
   }

   public static Fault buildSOAP11Fault(@Nonnull QName faultCode, @Nonnull String faultString, @Nullable String faultActor, @Nullable List detailChildren, @Nullable Map detailAttributes) {
      Constraint.isNotNull(faultCode, "faultcode cannot be null");
      Constraint.isNotNull(faultString, "faultstring cannot be null");
      XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
      Fault faultObj = (Fault)builderFactory.getBuilder(Fault.DEFAULT_ELEMENT_NAME).buildObject(Fault.DEFAULT_ELEMENT_NAME);
      FaultCode faultCodeObj = (FaultCode)builderFactory.getBuilder(FaultCode.DEFAULT_ELEMENT_NAME).buildObject(FaultCode.DEFAULT_ELEMENT_NAME);
      FaultString faultStringObj = (FaultString)builderFactory.getBuilder(FaultString.DEFAULT_ELEMENT_NAME).buildObject(FaultString.DEFAULT_ELEMENT_NAME);
      faultCodeObj.setValue(faultCode);
      faultObj.setCode(faultCodeObj);
      faultStringObj.setValue(faultString);
      faultObj.setMessage(faultStringObj);
      if (faultActor != null) {
         FaultActor faultActorObj = (FaultActor)builderFactory.getBuilder(FaultActor.DEFAULT_ELEMENT_NAME).buildObject(FaultActor.DEFAULT_ELEMENT_NAME);
         faultActorObj.setValue(faultActor);
         faultObj.setActor(faultActorObj);
      }

      Detail detailObj = null;
      Iterator var10;
      if (detailChildren != null && !detailChildren.isEmpty()) {
         detailObj = (Detail)builderFactory.getBuilder(Detail.DEFAULT_ELEMENT_NAME).buildObject(Detail.DEFAULT_ELEMENT_NAME);
         var10 = Iterables.filter(detailChildren, Predicates.notNull()).iterator();

         while(var10.hasNext()) {
            XMLObject xo = (XMLObject)var10.next();
            detailObj.getUnknownXMLObjects().add(xo);
         }
      }

      if (detailAttributes != null && !detailAttributes.isEmpty()) {
         if (detailObj == null) {
            detailObj = (Detail)builderFactory.getBuilder(Detail.DEFAULT_ELEMENT_NAME).buildObject(Detail.DEFAULT_ELEMENT_NAME);
         }

         var10 = detailAttributes.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry entry = (Map.Entry)var10.next();
            if (entry.getKey() != null && entry.getValue() != null) {
               detailObj.getUnknownAttributes().put((QName)entry.getKey(), (String)entry.getValue());
            }
         }
      }

      if (detailObj != null && (!detailObj.getUnknownXMLObjects().isEmpty() || !detailObj.getUnknownAttributes().isEmpty())) {
         faultObj.setDetail(detailObj);
      }

      return faultObj;
   }
}
