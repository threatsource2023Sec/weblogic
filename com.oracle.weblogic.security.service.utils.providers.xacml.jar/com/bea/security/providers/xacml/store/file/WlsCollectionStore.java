package com.bea.security.providers.xacml.store.file;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.security.utils.encryption.EncryptedStreamFactory;
import com.bea.security.utils.lock.FileRWLock;
import com.bea.security.utils.lock.ReadWriteLock;
import com.bea.security.xacml.PolicyStoreException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class WlsCollectionStore {
   public static String TYPE_ROLECOLLECTION = "role_collection";
   public static String TYPE_POLICYCOLLECTION = "policy_collection";
   private static String TAG_WLSCOLLECTIONINFO = "WlsCollectionInfo";
   private static String TAG_WLSCOLLECTIONINFOS = "WlsCollectionInfos";
   private static String TAG_WLSCOLLECTIONNAME = "WlsCollectionName";
   private static String TAG_WLSCOLLECTIONVERSION = "WlsCollectionVersion";
   private static String TAG_WLSXMLFRAGMENT = "WlsXMLFragment";
   private static String TAG_WLSCOLLECTIONTIMESTAMP = "WlsCollectionTimestamp";
   private String pciKey = null;
   private JAXPFactoryService jaxpFactoryService;
   private File fileDir;
   private char[] password;
   private String encryptAlgorithm;
   private HashMap map = new HashMap();
   private FileOperation fileOP = null;
   private ReadWriteLock lock = new ReadWriteLock();

   public WlsCollectionStore(File fileDir, char[] password, String encryptAlgorithm, JAXPFactoryService jaxpFactoryService, String fileName, String pciKey) throws PolicyStoreException, DocumentParseException {
      this.fileDir = fileDir;
      this.password = password;
      this.pciKey = pciKey;
      this.encryptAlgorithm = encryptAlgorithm;
      this.jaxpFactoryService = jaxpFactoryService;
      this.fileOP = new FileOperation(fileName);
      this.loadFileStore();
   }

   public void setMetaDataEntry(String key, String value) throws PolicyStoreException {
      try {
         this.lock.writeLock();
         this.refreshFileStore();
         String wlsCollectionName = this.getMetaDataKeyName(key);
         WlsCollectionInfo info = (WlsCollectionInfo)this.map.get(wlsCollectionName);
         if (info == null) {
            info = new WlsCollectionInfo();
            info.setWlsCollectionName(wlsCollectionName);
            this.map.put(wlsCollectionName, info);
         }

         info.setWlsXMLFragment(value);
         this.writeFileStore();
      } catch (DocumentParseException var8) {
         throw new PolicyStoreException(var8);
      } finally {
         this.lock.writeUnLock();
      }

   }

   public String getMetaDataEntry(String key) throws PolicyStoreException {
      try {
         this.refreshFileStoreWithWriteLock();
      } catch (DocumentParseException var8) {
         throw new PolicyStoreException(var8);
      }

      String var4;
      try {
         this.lock.readLock();
         String wlsCollectionName = this.getMetaDataKeyName(key);
         WlsCollectionInfo info = (WlsCollectionInfo)this.map.get(wlsCollectionName);
         if (info != null) {
            var4 = info.getWlsXMLFragment();
            return var4;
         }

         var4 = null;
      } finally {
         this.lock.readUnLock();
      }

      return var4;
   }

   public List getAllMetaDataEntry() throws PolicyStoreException {
      try {
         this.refreshFileStoreWithWriteLock();
      } catch (DocumentParseException var7) {
         throw new PolicyStoreException(var7);
      }

      LinkedList var9;
      try {
         this.lock.readLock();
         List list = new LinkedList();
         Iterator var2 = this.map.values().iterator();

         while(var2.hasNext()) {
            WlsCollectionInfo info = (WlsCollectionInfo)var2.next();
            if (info != null && info.getWlsXMLFragment() != null) {
               list.add(info.getWlsXMLFragment());
            }
         }

         var9 = list;
      } finally {
         this.lock.readUnLock();
      }

      return var9;
   }

   public void makePersistence(Object object) throws Exception {
      if (object instanceof WlsCollectionInfo) {
         WlsCollectionInfo info = (WlsCollectionInfo)object;

         try {
            this.lock.writeLock();
            this.refreshFileStore();
            this.map.put(info.getWlsCollectionName(), info);
            this.writeFileStore();
         } finally {
            this.lock.writeUnLock();
         }

      }
   }

   public boolean has(Object object) {
      if (!(object instanceof WlsCollectionInfo)) {
         return false;
      } else {
         WlsCollectionInfo info = (WlsCollectionInfo)object;
         return this.map.containsKey(info.getWlsCollectionName());
      }
   }

   private String getMetaDataKeyName(String key) throws PolicyStoreException {
      if (key == null) {
         throw new PolicyStoreException("Key not supplied!");
      } else {
         int nameIdx = key.indexOf(this.pciKey);
         if (nameIdx == -1) {
            throw new PolicyStoreException("Unknown key!");
         } else {
            String keyName = key.substring(nameIdx + this.pciKey.length());
            if (keyName.length() == 0) {
               throw new PolicyStoreException("Key name not supplied!");
            } else {
               return keyName;
            }
         }
      }
   }

   private void writeFileStore() throws PolicyStoreException {
      try {
         this.fileOP.writeFile(this.marshallCollectionInfos());
      } catch (DocumentParseException var2) {
         throw new PolicyStoreException(var2);
      } catch (ParserConfigurationException var3) {
         throw new PolicyStoreException(var3);
      }
   }

   private Document marshallCollectionInfos() throws PolicyStoreException, ParserConfigurationException {
      Document doc = this.jaxpFactoryService.newDocumentBuilderFactory().newDocumentBuilder().newDocument();
      Element root = doc.createElement(TAG_WLSCOLLECTIONINFOS);
      Collection infos = this.map.values();
      Iterator var4 = infos.iterator();

      while(var4.hasNext()) {
         WlsCollectionInfo info = (WlsCollectionInfo)var4.next();
         root.appendChild(this.marshallCollectionInfo(doc, info));
      }

      doc.appendChild(root);
      return doc;
   }

   private Node marshallCollectionInfo(Document doc, WlsCollectionInfo info) {
      Element ele = doc.createElement(TAG_WLSCOLLECTIONINFO);
      Element name = doc.createElement(TAG_WLSCOLLECTIONNAME);
      name.appendChild(doc.createTextNode(info.getWlsCollectionName()));
      ele.appendChild(name);
      Element xml;
      if (info.getWlsCollectionVersion() != null) {
         xml = doc.createElement(TAG_WLSCOLLECTIONVERSION);
         xml.appendChild(doc.createTextNode(info.getWlsCollectionVersion()));
         ele.appendChild(xml);
      }

      if (info.getWlsXMLFragment() != null) {
         xml = doc.createElement(TAG_WLSXMLFRAGMENT);
         xml.appendChild(doc.createCDATASection(info.getWlsXMLFragment()));
         ele.appendChild(xml);
      }

      if (info.getWlsCollectionTimestamp() != 0L) {
         xml = doc.createElement(TAG_WLSCOLLECTIONTIMESTAMP);
         xml.appendChild(doc.createTextNode(String.valueOf(info.getWlsCollectionTimestamp())));
         ele.appendChild(xml);
      }

      return ele;
   }

   private void loadFileStore() throws PolicyStoreException, DocumentParseException {
      this.map.clear();
      this.unmarshallCollectionInfos(this.fileOP.readFile());
   }

   private void unmarshallCollectionInfos(Document doc) {
      if (doc != null) {
         NodeList nodes = doc.getElementsByTagName(TAG_WLSCOLLECTIONINFO);

         for(int i = 0; i < nodes.getLength(); ++i) {
            WlsCollectionInfo collectionInfo = this.unmarshallCollectionInfo(nodes.item(i));
            this.map.put(collectionInfo.getWlsCollectionName(), collectionInfo);
         }

      }
   }

   private WlsCollectionInfo unmarshallCollectionInfo(Node node) {
      WlsCollectionInfo collectionInfo = new WlsCollectionInfo();
      NodeList childs = node.getChildNodes();
      Node n = null;

      for(int i = 0; i < childs.getLength(); ++i) {
         n = childs.item(i);
         if (TAG_WLSCOLLECTIONNAME.equals(n.getNodeName())) {
            collectionInfo.setWlsCollectionName(this.getNodeValue(node));
         } else if (TAG_WLSCOLLECTIONVERSION.equals(n.getNodeName())) {
            collectionInfo.setWlsCollectionVersion(this.getNodeValue(node));
         } else if (TAG_WLSXMLFRAGMENT.equals(n.getNodeName())) {
            CDATASection section = (CDATASection)n.getFirstChild();
            collectionInfo.setWlsXMLFragment(section.getNodeValue());
         } else if (TAG_WLSCOLLECTIONTIMESTAMP.equals(n.getNodeName())) {
            String value = this.getNodeValue(node);
            collectionInfo.setWlsCollectionTimestamp(Long.parseLong(value == null ? "0" : value));
         }
      }

      return collectionInfo;
   }

   private String getNodeValue(Node node) {
      return node.getFirstChild() != null && node.getFirstChild().getFirstChild() != null ? node.getFirstChild().getFirstChild().getNodeValue() : null;
   }

   private void refreshFileStore() throws PolicyStoreException, DocumentParseException {
      if (this.fileOP.checkDirty()) {
         this.loadFileStore();
      }

   }

   private void refreshFileStoreWithWriteLock() throws PolicyStoreException, DocumentParseException {
      try {
         this.lock.writeLock();
         this.refreshFileStore();
      } finally {
         this.lock.writeUnLock();
      }

   }

   public static class WlsCollectionInfo {
      private String wlsCollectionName;
      private String wlsCollectionVersion;
      private long wlsCollectionTimestamp;
      private String wlsXMLFragment;

      public WlsCollectionInfo() {
      }

      public WlsCollectionInfo(String name, String version, long time, String xml, String type) {
         this.wlsCollectionName = name;
         this.wlsCollectionVersion = version;
         this.wlsCollectionTimestamp = time;
         this.wlsXMLFragment = xml;
      }

      public String getWlsCollectionName() {
         return this.wlsCollectionName;
      }

      public void setWlsCollectionName(String wlsCollectionName) {
         this.wlsCollectionName = wlsCollectionName;
      }

      public long getWlsCollectionTimestamp() {
         return this.wlsCollectionTimestamp;
      }

      public void setWlsCollectionTimestamp(long wlsCollectionTimestamp) {
         this.wlsCollectionTimestamp = wlsCollectionTimestamp;
      }

      public String getWlsCollectionVersion() {
         return this.wlsCollectionVersion;
      }

      public void setWlsCollectionVersion(String wlsCollectionVersion) {
         this.wlsCollectionVersion = wlsCollectionVersion;
      }

      public String getWlsXMLFragment() {
         return this.wlsXMLFragment;
      }

      public void setWlsXMLFragment(String wlsXMLFragment) {
         this.wlsXMLFragment = wlsXMLFragment;
      }

      public String toString() {
         return "wlsCollectionName[" + this.wlsCollectionName + "]wlsCollectionVersion[" + this.wlsCollectionVersion + "]wlsCollectionTimestamp[" + this.wlsCollectionTimestamp + "]wlsXMLFragment[" + this.wlsXMLFragment + "]";
      }
   }

   private class FileOperation {
      private long modifyTimestamp;
      private File file;
      private FileRWLock fileLock;

      private FileOperation(String fileName) {
         this.file = new File(WlsCollectionStore.this.fileDir, fileName);
         this.modifyTimestamp = this.file.lastModified();
         this.fileLock = new FileRWLock(this.file.getAbsolutePath());
      }

      private Document readFile() throws PolicyStoreException, DocumentParseException {
         if (!this.file.exists()) {
            return null;
         } else {
            Document var1;
            try {
               this.fileLock.readLock();
               this.modifyTimestamp = this.file.lastModified();
               var1 = WlsCollectionStore.this.jaxpFactoryService.newDocumentBuilderFactory().newDocumentBuilder().parse(EncryptedStreamFactory.getDecryptedInputStream(this.fileLock.getFileInputStream(), WlsCollectionStore.this.password, WlsCollectionStore.this.encryptAlgorithm));
            } catch (IOException var12) {
               throw new DocumentParseException(ApiLogger.getIllegalFileFormatForFileStore(), var12);
            } catch (SAXException var13) {
               throw new DocumentParseException(ApiLogger.getIllegalFileFormatForFileStore(), var13);
            } catch (ParserConfigurationException var14) {
               throw new DocumentParseException(var14);
            } finally {
               try {
                  this.fileLock.readUnlock();
               } catch (IOException var11) {
                  throw new PolicyStoreException(var11);
               }
            }

            return var1;
         }
      }

      private void writeFile(Document doc) throws DocumentParseException, PolicyStoreException {
         try {
            this.fileLock.writeLock();
            this.writeXml(doc, EncryptedStreamFactory.getEncryptedOutputStream(this.fileLock.getFileOutputStream(), WlsCollectionStore.this.password, WlsCollectionStore.this.encryptAlgorithm));
         } catch (IOException var10) {
            throw new PolicyStoreException(var10);
         } finally {
            try {
               this.fileLock.writeUnlock();
            } catch (IOException var9) {
               throw new PolicyStoreException(var9);
            }

            this.modifyTimestamp = this.file.lastModified();
         }

      }

      private void writeXml(Document doc, OutputStream out) throws DocumentParseException, PolicyStoreException {
         try {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(out);
            Transformer transformer = WlsCollectionStore.this.jaxpFactoryService.newTransformerFactory().newTransformer();
            transformer.transform(source, result);
         } catch (TransformerConfigurationException var15) {
            throw new DocumentParseException("Parse xml doc error.", var15);
         } catch (TransformerFactoryConfigurationError var16) {
            throw new DocumentParseException("Parse xml doc error.", var16);
         } catch (TransformerException var17) {
            throw new DocumentParseException("Parse xml doc error.", var17);
         } finally {
            try {
               out.flush();
               out.close();
            } catch (IOException var14) {
               throw new PolicyStoreException(var14);
            }
         }

      }

      private boolean checkDirty() {
         return this.file.lastModified() != this.modifyTimestamp;
      }

      // $FF: synthetic method
      FileOperation(String x1, Object x2) {
         this(x1);
      }
   }
}
