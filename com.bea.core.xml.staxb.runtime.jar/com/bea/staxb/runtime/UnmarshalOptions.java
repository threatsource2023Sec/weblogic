package com.bea.staxb.runtime;

import java.util.Collection;
import java.util.Map;

public final class UnmarshalOptions {
   private Collection errorListener = null;
   private ObjectFactory initialObjectFactory;
   private StreamReaderFromNode streamReaderFromNode;
   private NodeFromStreamReader nodeFromStreamReader;
   private boolean forceDotNetCompatibleUnmarshal;
   private Map namespaceMapping;
   private boolean validation;
   private boolean attributeValidationCompatMode = false;

   public UnmarshalOptions() {
   }

   public UnmarshalOptions(Collection errorListener) {
      this.errorListener = errorListener;
   }

   public boolean isValidation() {
      return this.validation;
   }

   public void setValidation(boolean validation) {
      this.validation = validation;
   }

   public boolean isAttributeValidationCompatMode() {
      return this.attributeValidationCompatMode;
   }

   public void setAttributeValidationCompatMode(boolean compatMode) {
      this.attributeValidationCompatMode = compatMode;
   }

   public ObjectFactory getInitialObjectFactory() {
      return this.initialObjectFactory;
   }

   public void setInitialObjectFactory(ObjectFactory initialObjectFactory) {
      this.initialObjectFactory = initialObjectFactory;
   }

   public Collection getErrorListener() {
      return this.errorListener;
   }

   public void setErrorListener(Collection errorListener) {
      this.errorListener = errorListener;
   }

   public StreamReaderFromNode getStreamReaderFromNode() {
      return this.streamReaderFromNode;
   }

   public void setStreamReaderFromNode(StreamReaderFromNode streamReaderFromNode) {
      this.streamReaderFromNode = streamReaderFromNode;
   }

   public NodeFromStreamReader getNodeFromStreamReader() {
      return this.nodeFromStreamReader;
   }

   public void setNodeFromStreamReader(NodeFromStreamReader nodeFromStreamReader) {
      this.nodeFromStreamReader = nodeFromStreamReader;
   }

   public boolean isForceDotNetCompatibleUnmarshal() {
      return this.forceDotNetCompatibleUnmarshal;
   }

   public void setForceDotNetCompatibleUnMarshal(boolean forceDotNetCompatibleUnmarshal) {
      this.forceDotNetCompatibleUnmarshal = forceDotNetCompatibleUnmarshal;
   }

   public Map getNamespaceMapping() {
      return this.namespaceMapping;
   }

   public void setNamespaceMapping(Map namespaceMapping) {
      this.namespaceMapping = namespaceMapping;
   }
}
