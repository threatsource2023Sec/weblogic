package org.apache.openjpa.jdbc.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.XMLFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SQLErrorCodeReader {
   private Log log = null;
   public static final String ERROR_CODE_DELIMITER = ",";
   public static final Map storeErrorTypes = new HashMap();
   private static final Localizer _loc;

   public List getDictionaries(InputStream in) {
      List result = new ArrayList();
      DocumentBuilder builder = XMLFactory.getDOMParser(false, false);

      try {
         Document doc = builder.parse(in);
         Element root = doc.getDocumentElement();
         NodeList nodes = root.getElementsByTagName("dictionary");

         for(int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            NamedNodeMap attrs = node.getAttributes();
            Node dictionary = attrs.getNamedItem("class");
            if (dictionary != null) {
               result.add(dictionary.getNodeValue());
            }
         }
      } catch (Throwable var19) {
         if (this.log.isWarnEnabled()) {
            this.log.error(_loc.get("error-code-parse-error"));
         }
      } finally {
         try {
            in.close();
         } catch (IOException var18) {
         }

      }

      return result;
   }

   public void parse(InputStream in, String dictName, DBDictionary dict) {
      if (in != null && dict != null) {
         this.log = dict.conf.getLog("openjpa.jdbc.JDBC");
         DocumentBuilder builder = XMLFactory.getDOMParser(false, false);

         try {
            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getElementsByTagName("dictionary");

            for(int i = 0; i < nodes.getLength(); ++i) {
               Node node = nodes.item(i);
               NamedNodeMap attrs = node.getAttributes();
               Node dictionary = attrs.getNamedItem("class");
               if (dictionary != null && dictionary.getNodeValue().equals(dictName)) {
                  readErrorCodes(node, dict);
               }
            }
         } catch (Throwable var20) {
            if (this.log.isWarnEnabled()) {
               this.log.error(_loc.get("error-code-parse-error"));
            }
         } finally {
            try {
               in.close();
            } catch (IOException var19) {
            }

         }

      }
   }

   static void readErrorCodes(Node node, DBDictionary dict) {
      NodeList children = node.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         short nodeType = child.getNodeType();
         if (nodeType == 1) {
            String errorType = child.getNodeName();
            Node textNode = child.getFirstChild();
            if (storeErrorTypes.containsKey(errorType) && textNode != null) {
               String errorCodes = textNode.getNodeValue();
               if (!StringUtils.isEmpty(errorCodes)) {
                  String[] codes = errorCodes.split(",");

                  for(int k = 0; k < codes.length; ++k) {
                     dict.addErrorCode((Integer)storeErrorTypes.get(errorType), codes[k].trim());
                  }
               }
            }
         }
      }

   }

   static {
      storeErrorTypes.put("lock", new Integer(1));
      storeErrorTypes.put("object-exists", new Integer(5));
      storeErrorTypes.put("object-not-found", new Integer(2));
      storeErrorTypes.put("optimistic", new Integer(3));
      storeErrorTypes.put("referential-integrity", new Integer(4));
      _loc = Localizer.forPackage(SQLErrorCodeReader.class);
   }
}
