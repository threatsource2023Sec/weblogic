package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.providers.xacml.entitlement.PolicyManager;
import com.bea.security.providers.xacml.entitlement.RoleManager;
import com.bea.security.providers.xacml.store.MetaDataPolicyStore;
import com.bea.security.providers.xacml.store.PolicyMetaDataImpl;
import com.bea.security.xacml.PolicyMetaData;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyStore;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.management.utils.CreateException;

public class MetaDataXMLDescription {
   private static final String CREATOR_INFO = "wlsCreatorInfo";
   private static final String COLLECTION_NAME = "wlsCollectionName";
   private static final String WLS_ROLE_INFO = "WLSRoleInfo";
   private static final String WLS_POLICY_INFO = "WLSPolicyInfo";
   private static final String WLS_XML_CREATE = "wlsCreatorInfo=\"";
   private static final String WLS_XML_CNAME = "wlsCollectionName=\"";
   private static final String XML_ATTR_END = "\" ";
   private static final String XML_END = "\"/>";
   private static final String METADATA_XMLNS = "urn:bea:xacml:2.0:export-data:meta-data";
   private static final String METADATA_XML_START = "<![CDATA[<MetaData xmlns=\"urn:bea:xacml:2.0:export-data:meta-data\">";
   private static final String METADATA_XML_END = "</MetaData>]]>";

   public static void generateAuthorizerDesciption(PolicyStore store, StringBuffer sb, Set policies, Set policySets) throws PolicyStoreException {
      sb.append("<![CDATA[<MetaData xmlns=\"urn:bea:xacml:2.0:export-data:meta-data\">");
      generatePolicyDesciption(store, sb, policies, policySets);
      if (store instanceof MetaDataPolicyStore) {
         List pciData = ((MetaDataPolicyStore)store).readAllMetaDataEntries();
         if (pciData != null && pciData.size() > 0) {
            sb.append("<WLSPolicyCollectionInfo>");
            Iterator var5 = pciData.iterator();

            while(var5.hasNext()) {
               String value = (String)var5.next();
               sb.append(value);
            }

            sb.append("</WLSPolicyCollectionInfo>");
         }
      }

      sb.append("</MetaData>]]>");
   }

   public static void generateRoleMapperDesciption(PolicyStore store, StringBuffer sb, Set policies, Set policySets) throws PolicyStoreException {
      sb.append("<![CDATA[<MetaData xmlns=\"urn:bea:xacml:2.0:export-data:meta-data\">");
      generatePolicyDesciption(store, sb, policies, policySets);
      if (store instanceof MetaDataPolicyStore) {
         List rciData = ((MetaDataPolicyStore)store).readAllMetaDataEntries();
         if (rciData != null && rciData.size() > 0) {
            sb.append("<WLSRoleCollectionInfo>");
            Iterator var5 = rciData.iterator();

            while(var5.hasNext()) {
               String value = (String)var5.next();
               sb.append(value);
            }

            sb.append("</WLSRoleCollectionInfo>");
         }
      }

      sb.append("</MetaData>]]>");
   }

   public static Map readAuthorizerDescription(PolicyManager manager, String description, DocumentBuilderFactory dbFactory) throws DocumentParseException, CreateException {
      Element root = parseDescription(description, dbFactory);
      if (root == null) {
         return new HashMap();
      } else {
         String namespace = root.getNamespaceURI();
         if (!"urn:bea:xacml:2.0:export-data:meta-data".equals(namespace)) {
            throw new DocumentParseException("MetaData namespace invalid");
         } else {
            setPolicyCollectionInfo(manager, root);
            return getPolicyMetaDataEntries(root, "WLSPolicyInfo");
         }
      }
   }

   public static Map readRoleMapperDescription(RoleManager manager, String description, DocumentBuilderFactory dbFactory) throws DocumentParseException, CreateException {
      Element root = parseDescription(description, dbFactory);
      if (root == null) {
         return new HashMap();
      } else {
         String namespace = root.getNamespaceURI();
         if (!"urn:bea:xacml:2.0:export-data:meta-data".equals(namespace)) {
            throw new DocumentParseException("MetaData namespace invalid");
         } else {
            setRoleCollectionInfo(manager, root);
            return getPolicyMetaDataEntries(root, "WLSRoleInfo");
         }
      }
   }

   private static void setPolicyCollectionInfo(PolicyManager manager, Element root) throws CreateException {
      NodeList data = root.getElementsByTagName("WLSPolicyCollectionInfo");
      if (data != null && data.getLength() > 0) {
         NodeList collectionData = data.item(0).getChildNodes();

         for(int i = 0; i < collectionData.getLength(); ++i) {
            Node info = collectionData.item(i);
            if ("PolicyCollectionInfo".equals(info.getNodeName())) {
               NamedNodeMap attribs = info.getAttributes();
               Node name = attribs.getNamedItem("Name");
               if (name != null) {
                  String cName = name.getNodeValue();
                  String version = "";
                  Node ver = attribs.getNamedItem("Version");
                  if (ver != null) {
                     version = ver.getNodeValue();
                  }

                  String timestamp = "";
                  Node time = attribs.getNamedItem("TimeStamp");
                  if (time != null) {
                     timestamp = time.getNodeValue();
                  }

                  manager.createPolicyCollectionInfo(cName, version, timestamp);
               }
            }
         }
      }

   }

   private static void setRoleCollectionInfo(RoleManager manager, Element root) throws CreateException {
      NodeList data = root.getElementsByTagName("WLSRoleCollectionInfo");
      if (data != null && data.getLength() > 0) {
         NodeList collectionData = data.item(0).getChildNodes();

         for(int i = 0; i < collectionData.getLength(); ++i) {
            Node info = collectionData.item(i);
            if ("RoleCollectionInfo".equals(info.getNodeName())) {
               NamedNodeMap attribs = info.getAttributes();
               Node name = attribs.getNamedItem("Name");
               if (name != null) {
                  String cName = name.getNodeValue();
                  String version = "";
                  Node ver = attribs.getNamedItem("Version");
                  if (ver != null) {
                     version = ver.getNodeValue();
                  }

                  String timestamp = "";
                  Node time = attribs.getNamedItem("TimeStamp");
                  if (time != null) {
                     timestamp = time.getNodeValue();
                  }

                  manager.createRoleCollectionInfo(cName, version, timestamp);
               }
            }
         }
      }

   }

   private static Map getPolicyMetaDataEntries(Element root, String metadataElementName) {
      Map entries = new HashMap();
      NodeList data = root.getElementsByTagName("WLSMetaData");
      if (data != null) {
         for(int i = 0; i < data.getLength(); ++i) {
            Node item = data.item(i);
            if (item.getNodeType() == 1) {
               Element wlsMetadata = (Element)item;
               String id = wlsMetadata.getAttribute("PolicyId");
               if (id == null || id.length() == 0) {
                  id = wlsMetadata.getAttribute("PolicySetId");
               }

               String status = wlsMetadata.getAttribute("Status");
               if (status == null) {
                  status = String.valueOf(3);
               }

               if (id != null && id.length() > 0) {
                  String infoName = null;
                  Node info = wlsMetadata.getFirstChild();
                  if (info != null) {
                     infoName = info.getNodeName();
                  }

                  PolicyMetaData pmd = null;
                  if (metadataElementName.equals(infoName)) {
                     NamedNodeMap attribs = info.getAttributes();
                     String creator = "mbean";
                     Node creatorInfo = attribs.getNamedItem("wlsCreatorInfo");
                     if (creatorInfo != null) {
                        creator = creatorInfo.getNodeValue();
                     }

                     String cName = null;
                     Node collection = attribs.getNamedItem("wlsCollectionName");
                     if (collection != null) {
                        cName = collection.getNodeValue();
                     }

                     pmd = getPolicyMetaData(metadataElementName, creator, cName);
                  }

                  Map mdMap = new HashMap();
                  mdMap.put("status", status);
                  mdMap.put("metadata", pmd);
                  entries.put(id, mdMap);
               }
            }
         }
      }

      return entries;
   }

   private static PolicyMetaData getPolicyMetaData(String elementName, String creator, String cName) {
      String xmlStart = "<" + elementName + " ";
      Map index = new HashMap();
      index.put("wlsCreatorInfo", creator);
      String value;
      if (cName != null) {
         index.put("wlsCollectionName", cName);
         String xmlName = XMLEscaper.escapeXMLChars(cName);
         value = xmlStart + "wlsCreatorInfo=\"" + creator + "\" " + "wlsCollectionName=\"" + xmlName + "\"/>";
      } else {
         value = xmlStart + "wlsCreatorInfo=\"" + creator + "\"/>";
      }

      return new PolicyMetaDataImpl(elementName, value, index);
   }

   private static Element parseDescription(String description, DocumentBuilderFactory dbFactory) throws DocumentParseException {
      Element root = null;
      if (description != null && description.length() > 0) {
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);

         try {
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(description)));
            root = doc.getDocumentElement();
         } catch (ParserConfigurationException var5) {
            throw new DocumentParseException(var5);
         } catch (SAXException var6) {
            throw new DocumentParseException(var6);
         } catch (IOException var7) {
            throw new DocumentParseException(var7);
         }
      }

      return root;
   }

   private static void generatePolicyDesciption(PolicyStore store, StringBuffer sb, Set policies, Set policySets) throws PolicyStoreException {
      try {
         Iterator var4;
         PolicyMetaData md;
         int status;
         if (policySets != null && !policySets.isEmpty()) {
            for(var4 = policySets.iterator(); var4.hasNext(); sb.append("</WLSMetaData>")) {
               PolicySet p = (PolicySet)var4.next();
               md = null;
               if (store instanceof MetaDataPolicyStore) {
                  md = ((MetaDataPolicyStore)store).getPolicyMetaDataEntry(p.getId(), p.getVersion());
               }

               status = store.getPolicySetStatus(p.getId(), p.getVersion());
               sb.append("<WLSMetaData PolicySetId=\"");
               sb.append(p.getId());
               sb.append("\" Status=\"");
               sb.append(status);
               sb.append("\">");
               if (md != null && md.getValue() != null) {
                  sb.append(md.getValue());
               }
            }
         }

         if (policies != null && !policies.isEmpty()) {
            for(var4 = policies.iterator(); var4.hasNext(); sb.append("</WLSMetaData>")) {
               Policy p = (Policy)var4.next();
               md = null;
               if (store instanceof MetaDataPolicyStore) {
                  md = ((MetaDataPolicyStore)store).getPolicyMetaDataEntry(p.getId(), p.getVersion());
               }

               status = store.getPolicyStatus(p.getId(), p.getVersion());
               sb.append("<WLSMetaData PolicyId=\"");
               sb.append(p.getId());
               sb.append("\" Status=\"");
               sb.append(status);
               sb.append("\">");
               if (md != null && md.getValue() != null) {
                  sb.append(md.getValue());
               }
            }
         }

      } catch (URISyntaxException var8) {
         throw new PolicyStoreException(var8);
      }
   }
}
