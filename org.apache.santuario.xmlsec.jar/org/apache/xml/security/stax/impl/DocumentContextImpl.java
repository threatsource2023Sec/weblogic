package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;

public class DocumentContextImpl implements DocumentContext, Cloneable {
   private String encoding;
   private String baseURI;
   private final Map contentTypeMap = new TreeMap();
   private final Map processorToIndexMap = new HashMap();

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public String getBaseURI() {
      return this.baseURI;
   }

   public void setBaseURI(String baseURI) {
      this.baseURI = baseURI;
   }

   public synchronized void setIsInEncryptedContent(int index, Object key) {
      this.contentTypeMap.put(index, XMLSecurityConstants.ContentType.ENCRYPTION);
      this.processorToIndexMap.put(key, index);
   }

   public synchronized void unsetIsInEncryptedContent(Object key) {
      Integer index = (Integer)this.processorToIndexMap.remove(key);
      this.contentTypeMap.remove(index);
   }

   public boolean isInEncryptedContent() {
      return this.contentTypeMap.containsValue(XMLSecurityConstants.ContentType.ENCRYPTION);
   }

   public synchronized void setIsInSignedContent(int index, Object key) {
      this.contentTypeMap.put(index, XMLSecurityConstants.ContentType.SIGNATURE);
      this.processorToIndexMap.put(key, index);
   }

   public synchronized void unsetIsInSignedContent(Object key) {
      Integer index = (Integer)this.processorToIndexMap.remove(key);
      this.contentTypeMap.remove(index);
   }

   public boolean isInSignedContent() {
      return this.contentTypeMap.containsValue(XMLSecurityConstants.ContentType.SIGNATURE);
   }

   public List getProtectionOrder() {
      return new ArrayList(this.contentTypeMap.values());
   }

   public Map getContentTypeMap() {
      return Collections.unmodifiableMap(this.contentTypeMap);
   }

   protected void setContentTypeMap(Map contentTypeMap) {
      this.contentTypeMap.putAll(contentTypeMap);
   }

   protected DocumentContextImpl clone() throws CloneNotSupportedException {
      DocumentContextImpl documentContext = new DocumentContextImpl();
      documentContext.setEncoding(this.encoding);
      documentContext.setBaseURI(this.baseURI);
      documentContext.setContentTypeMap(this.getContentTypeMap());
      return documentContext;
   }
}
