package org.opensaml.soap.wssecurity.util;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.TokenTypeBearing;
import org.opensaml.soap.wssecurity.UsageBearing;

public final class WSSecuritySupport {
   private WSSecuritySupport() {
   }

   public static void addWSUId(XMLObject soapObject, String id) {
      if (soapObject instanceof IdBearing) {
         ((IdBearing)soapObject).setWSUId(id);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither IdBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(IdBearing.WSU_ID_ATTR_NAME, id);
      }

   }

   public static String getWSUId(XMLObject soapObject) {
      String value = null;
      if (soapObject instanceof IdBearing) {
         value = StringSupport.trimOrNull(((IdBearing)soapObject).getWSUId());
         if (value != null) {
            return value;
         }
      }

      if (soapObject instanceof AttributeExtensibleXMLObject) {
         value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(IdBearing.WSU_ID_ATTR_NAME));
         return value;
      } else {
         return null;
      }
   }

   public static void addWSSE11TokenType(XMLObject soapObject, String tokenType) {
      if (soapObject instanceof TokenTypeBearing) {
         ((TokenTypeBearing)soapObject).setWSSE11TokenType(tokenType);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither TokenTypeBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(TokenTypeBearing.WSSE11_TOKEN_TYPE_ATTR_NAME, tokenType);
      }

   }

   public static String getWSSE11TokenType(XMLObject soapObject) {
      String value = null;
      if (soapObject instanceof TokenTypeBearing) {
         value = StringSupport.trimOrNull(((TokenTypeBearing)soapObject).getWSSE11TokenType());
         if (value != null) {
            return value;
         }
      }

      if (soapObject instanceof AttributeExtensibleXMLObject) {
         value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(TokenTypeBearing.WSSE11_TOKEN_TYPE_ATTR_NAME));
         return value;
      } else {
         return null;
      }
   }

   public static void addWSSEUsage(XMLObject soapObject, String usage) {
      if (soapObject instanceof UsageBearing) {
         UsageBearing usageBearing = (UsageBearing)soapObject;
         List list = usageBearing.getWSSEUsages();
         if (list == null) {
            list = new LazyList();
            usageBearing.setWSSEUsages((List)list);
         }

         ((List)list).add(usage);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither UsageBearing nor AttributeExtensible");
         }

         AttributeMap am = ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes();
         String list = am.get(UsageBearing.WSSE_USAGE_ATTR_NAME);
         if (list == null) {
            list = usage;
         } else {
            list = list + " " + usage;
         }

         am.put(UsageBearing.WSSE_USAGE_ATTR_NAME, list);
      }

   }

   public static void addWSSEUsages(XMLObject soapObject, List usages) {
      if (soapObject instanceof UsageBearing) {
         ((UsageBearing)soapObject).setWSSEUsages(usages);
      } else {
         if (!(soapObject instanceof AttributeExtensibleXMLObject)) {
            throw new IllegalArgumentException("Specified object was neither UsageBearing nor AttributeExtensible");
         }

         ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(UsageBearing.WSSE_USAGE_ATTR_NAME, StringSupport.listToStringValue(usages, " "));
      }

   }

   public static List getWSSEUsages(XMLObject soapObject) {
      if (soapObject instanceof UsageBearing) {
         List value = ((UsageBearing)soapObject).getWSSEUsages();
         if (value != null) {
            return value;
         }
      }

      if (soapObject instanceof AttributeExtensibleXMLObject) {
         String value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(UsageBearing.WSSE_USAGE_ATTR_NAME));
         if (value != null) {
            StringSupport.stringToList(value, " \n\r\t");
         }
      }

      return null;
   }
}
