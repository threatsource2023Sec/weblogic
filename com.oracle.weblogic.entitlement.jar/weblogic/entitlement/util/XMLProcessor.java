package weblogic.entitlement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.entitlement.data.EResource;
import weblogic.entitlement.data.ERole;
import weblogic.entitlement.expression.EExpression;
import weblogic.entitlement.parser.Parser;
import weblogic.security.service.MBeanResource;
import weblogic.security.service.MBeanResource.ActionType;
import weblogic.security.spi.Resource;
import weblogic.utils.XXEUtils;

public class XMLProcessor {
   private static String RESOURCE_TAG = "resource";
   private static String ROLE_TAG = "role";
   private static String PREDICATE_TAG = "predicate";
   private static String EEXPR_TAG = "eexpr";
   private static String NAME_TAG = "name";
   private static String PARAM_TAG = "param";
   private static String TYPE_TAG = "type";
   private static String VALUE_TAG = "value";
   private static String AUX_TAG = "aux";
   private Document doc = null;

   public XMLProcessor(InputStream in) throws SAXException, ParserConfigurationException, IOException {
      DocumentBuilderFactory dbf = XXEUtils.createDocumentBuilderFactoryInstance();
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
   }

   public void writeElements(LdiftWriter writer) throws Exception {
      NodeList resNodes = this.doc.getElementsByTagName(RESOURCE_TAG);
      int i = 0;

      int i;
      for(i = resNodes.getLength(); i < i; ++i) {
         Element resElt = (Element)resNodes.item(i);
         String resId = getResourceId(resElt);
         EExpression resExpr = Parser.parseResourceExpression(resElt.getAttribute(EEXPR_TAG));
         if (resId.length() != 0 && resExpr != null) {
            writer.write(new EResource(resId, resExpr));
         }

         NodeList roleNodes = resElt.getElementsByTagName(ROLE_TAG);
         int j = 0;

         for(int roleCount = roleNodes.getLength(); j < roleCount; ++j) {
            Element roleElt = (Element)roleNodes.item(j);
            String roleName = roleElt.getAttribute(NAME_TAG);
            String roleExprStr = roleElt.getAttribute(EEXPR_TAG);
            String roleAuxStr = roleElt.getAttribute(AUX_TAG);
            EExpression roleExpr = Parser.parseResourceExpression(roleExprStr);
            writer.write(new ERole(resId, roleName, roleExpr), roleAuxStr);
         }
      }

      NodeList predNodes = this.doc.getElementsByTagName(PREDICATE_TAG);
      i = 0;

      for(int predCount = predNodes.getLength(); i < predCount; ++i) {
         writer.writePredicate(((Element)predNodes.item(i)).getAttribute(NAME_TAG));
      }

   }

   private static String getResourceId(Element resElt) throws Exception {
      String resClassName = resElt.getAttribute(NAME_TAG).trim();
      if (resClassName.length() == 0) {
         return "";
      } else {
         NodeList paramNodes = resElt.getElementsByTagName(PARAM_TAG);
         int paramCount = paramNodes.getLength();
         Class[] types = new Class[paramCount];
         Object[] values = new Object[paramCount];

         for(int i = 0; i < paramCount; ++i) {
            Element param = (Element)paramNodes.item(i);
            types[i] = getClass(param.getAttribute(TYPE_TAG));
            values[i] = getValue(types[i], param.getAttribute(VALUE_TAG));
         }

         Resource res = null;

         try {
            Class resrcClass = Class.forName(resClassName);
            res = (Resource)resrcClass.getConstructor(types).newInstance(values);
         } catch (ClassNotFoundException var10) {
            throw new Exception("Cannot find resource class: " + resClassName);
         } catch (Exception var11) {
            String params = values.length > 0 ? String.valueOf(values[0]) : "none";

            for(int i = 1; i < values.length; ++i) {
               params = params + ", " + values[i];
            }

            throw new Exception("Cannot instantiate resource of type: " + resClassName + " with parameters: " + params);
         }

         return res.toString();
      }
   }

   private static Class getClass(String className) throws ClassNotFoundException {
      try {
         if (className.equals("weblogic.security.service.MBeanResource.ActionType")) {
            return MBeanResource.ActionType.class;
         } else {
            return className.equals("java.lang.String[]") ? String[].class : Class.forName(className);
         }
      } catch (ClassNotFoundException var2) {
         throw new ClassNotFoundException("Cannot instantiate class with the name: " + className);
      }
   }

   private static Object getValue(Class type, String valueStr) throws Exception {
      if (valueStr != null && !valueStr.equalsIgnoreCase("null")) {
         Object value = valueStr;
         if (type.isArray()) {
            StringTokenizer st = new StringTokenizer(valueStr, ",");
            int entryCount = st.countTokens();
            String[] array = new String[entryCount];

            for(int i = 0; st.hasMoreTokens(); ++i) {
               array[i] = st.nextToken().trim();
            }

            value = array;
         } else if (MBeanResource.ActionType.class.isAssignableFrom(type)) {
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
                  throw new Exception("Unknown MBeanResource.ActionType: " + valueStr);
               }

               value = ActionType.EXECUTE;
            }
         } else if (Hashtable.class.isAssignableFrom(type)) {
            value = new Hashtable();
         }

         return value;
      } else {
         return null;
      }
   }
}
