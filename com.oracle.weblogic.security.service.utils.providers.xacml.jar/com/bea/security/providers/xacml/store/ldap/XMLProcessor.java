package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.providers.xacml.entitlement.EntitlementConverter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.security.service.MBeanResource.ActionType;
import weblogic.security.spi.Resource;
import weblogic.utils.XXEUtils;

public class XMLProcessor {
   private static String RESOURCE_TAG = "resource";
   private static String ROLE_TAG = "role";
   private static String EEXPR_TAG = "eexpr";
   private static String NAME_TAG = "name";
   private static String PARAM_TAG = "param";
   private static String TYPE_TAG = "type";
   private static String VALUE_TAG = "value";
   private static String AUX_TAG = "aux";
   private Document doc = null;
   private EntitlementConverter converter = null;
   private List policies = null;

   XMLProcessor(InputStream in) throws SAXException, ParserConfigurationException, IOException, URISyntaxException {
      DocumentBuilderFactory dbf = null;

      try {
         Class wlsDbf = Class.forName("weblogic.xml.jaxp.WebLogicDocumentBuilderFactory");
         dbf = (DocumentBuilderFactory)wlsDbf.newInstance();
      } catch (Exception var4) {
         dbf = XXEUtils.createDocumentBuilderFactoryInstance();
      }

      dbf.setValidating(false);
      dbf.setIgnoringComments(true);
      dbf.setIgnoringElementContentWhitespace(true);
      dbf.setCoalescing(true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      db.setErrorHandler(new ErrorHandler() {
         public void fatalError(SAXParseException spe) throws SAXException {
            throw new SAXException("Fatal Error: " + this.getParseExceptionInfo(spe));
         }

         public void error(SAXParseException spe) throws SAXException {
            throw new SAXException("Error: " + this.getParseExceptionInfo(spe));
         }

         public void warning(SAXParseException spe) throws SAXException {
         }

         private String getParseExceptionInfo(SAXParseException spe) {
            return "URI=" + spe.getSystemId() + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();
         }
      });
      this.doc = db.parse(in);
      if (this.policies == null) {
         this.policies = new ArrayList();
      }

      this.converter = new EntitlementConverter(new ConverterLogger());
   }

   List getConvertedPolicies() throws EntlConversionException {
      Set alreadySeen = new HashSet();
      ConverterLogger logger = new ConverterLogger();
      NodeList resNodes = this.doc.getElementsByTagName(RESOURCE_TAG);
      int i = 0;

      for(int resCount = resNodes.getLength(); i < resCount; ++i) {
         Element resElt = (Element)resNodes.item(i);
         String resId = getResourceId(resElt);
         if (!alreadySeen.contains(resId)) {
            alreadySeen.add(resId);
            String resExpr = resElt.getAttribute(EEXPR_TAG);
            if (resId.length() != 0 && resExpr != null) {
               ConvertedPolicy pol = new ConvertedPolicy(resId, resExpr);
               this.policies.add(pol);
            }

            NodeList roleNodes = resElt.getElementsByTagName(ROLE_TAG);
            int j = 0;

            for(int roleCount = roleNodes.getLength(); j < roleCount; ++j) {
               Element roleElt = (Element)roleNodes.item(j);
               ConvertedPolicy pol = new ConvertedPolicy(resId, roleElt.getAttribute(EEXPR_TAG), roleElt.getAttribute(NAME_TAG), roleElt.getAttribute(AUX_TAG));
               this.policies.add(pol);
            }
         } else {
            logger.warn("ignore duplicate item: " + resId);
         }
      }

      return this.policies;
   }

   private static String getResourceId(Element resElt) throws EntlConversionException {
      String resClassName = resElt.getAttribute(NAME_TAG).trim();
      if (resClassName.length() == 0) {
         return "";
      } else {
         NodeList paramNodes = resElt.getElementsByTagName(PARAM_TAG);
         int paramCount = paramNodes.getLength();
         Class[] types = new Class[paramCount];
         Object[] values = new Object[paramCount];

         try {
            for(int i = 0; i < paramCount; ++i) {
               Element param = (Element)paramNodes.item(i);
               types[i] = getClass(param.getAttribute(TYPE_TAG));
               values[i] = getValue(types[i], param.getAttribute(VALUE_TAG));
            }

            Resource res = null;
            Class resrcClass = Class.forName(resClassName);
            res = (Resource)resrcClass.getConstructor(types).newInstance(values);
            return res.toString();
         } catch (IllegalArgumentException var8) {
            throw new EntlConversionException(var8);
         } catch (SecurityException var9) {
            throw new EntlConversionException(var9);
         } catch (ClassNotFoundException var10) {
            throw new EntlConversionException(var10);
         } catch (InstantiationException var11) {
            throw new EntlConversionException(var11);
         } catch (IllegalAccessException var12) {
            throw new EntlConversionException(var12);
         } catch (InvocationTargetException var13) {
            throw new EntlConversionException(var13);
         } catch (NoSuchMethodException var14) {
            throw new EntlConversionException(var14);
         }
      }
   }

   private static Class getClass(String className) throws ClassNotFoundException {
      if (className.equals("weblogic.security.service.MBeanResource.ActionType")) {
         return Class.forName("weblogic.security.service.MBeanResource$ActionType");
      } else {
         return className.equals("java.lang.String[]") ? String[].class : Class.forName(className);
      }
   }

   private static Object getValue(Class type, String valueStr) throws ClassNotFoundException, EntlConversionException {
      if (valueStr != null && !valueStr.equalsIgnoreCase("null")) {
         Object value = valueStr;
         if (type.isArray()) {
            value = valueStr.split("\\s*,\\s*");
         } else if (Class.forName("weblogic.security.service.MBeanResource$ActionType").isAssignableFrom(type)) {
            if (valueStr.equalsIgnoreCase("find")) {
               value = ActionType.FIND;
            } else if (valueStr.equalsIgnoreCase("register")) {
               value = ActionType.REGISTER;
            } else if (valueStr.equalsIgnoreCase("unregister")) {
               value = ActionType.UNREGISTER;
            } else if (valueStr.equalsIgnoreCase("write")) {
               value = ActionType.WRITE;
            } else if (valueStr.equalsIgnoreCase("read")) {
               value = ActionType.READ;
            } else {
               if (!valueStr.equalsIgnoreCase("execute")) {
                  throw new EntlConversionException(ProvidersLogger.getUnknownMBeanResourceActionType(valueStr));
               }

               value = ActionType.EXECUTE;
            }
         } else if (Class.forName("java.util.Hashtable").isAssignableFrom(type)) {
            value = new Hashtable();
         }

         return value;
      } else {
         return null;
      }
   }
}
