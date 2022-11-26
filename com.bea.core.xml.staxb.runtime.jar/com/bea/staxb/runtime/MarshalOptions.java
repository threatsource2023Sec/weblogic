package com.bea.staxb.runtime;

import com.bea.staxb.runtime.internal.XOPMarshaller;
import java.util.Collection;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

public final class MarshalOptions {
   private Collection errorListener = null;
   private boolean prettyPrint = false;
   private int prettyPrintIndent = 2;
   private boolean useDefaultNamespaceForRootElement;
   private String characterEncoding;
   private boolean checkSupertypes;
   private NamespaceContext namespaceContext;
   private String schemaLocation;
   private String noNamespaceschemaLocation;
   private Map initialNamespaces;
   private StaxWriterToNode staxWriterToNode;
   private boolean forceIncludeXsiType = false;
   private boolean forceOracle1012Compatible = false;
   private XOPMarshaller marshaller = null;
   private boolean forceDotNetCompatibleMarshal;
   private boolean isRootObj = true;
   private static final MarshalOptions DEFAULTS = new MarshalOptions();

   public static MarshalOptions getDefaults() {
      return DEFAULTS;
   }

   public Collection getErrorListener() {
      return this.errorListener;
   }

   public void setErrorListener(Collection errorListener) {
      this.errorListener = errorListener;
   }

   public boolean isRootObj() {
      return this.isRootObj;
   }

   public void setRootObj(boolean isRootObj) {
      this.isRootObj = isRootObj;
   }

   public boolean isPrettyPrint() {
      return this.prettyPrint;
   }

   public void setPrettyPrint(boolean prettyPrint) {
      this.prettyPrint = prettyPrint;
   }

   public int getPrettyPrintIndent() {
      return this.prettyPrintIndent;
   }

   public void setPrettyPrintIndent(int prettyPrintIndent) {
      this.prettyPrintIndent = prettyPrintIndent;
   }

   public boolean isUseDefaultNamespaceForRootElement() {
      return this.useDefaultNamespaceForRootElement;
   }

   public void setUseDefaultNamespaceForRootElement(boolean useDefaultNamespaceForRootElement) {
      this.useDefaultNamespaceForRootElement = useDefaultNamespaceForRootElement;
   }

   public String getCharacterEncoding() {
      return this.characterEncoding;
   }

   public void setCharacterEncoding(String characterEncoding) {
      this.characterEncoding = characterEncoding;
   }

   public boolean isCheckSupertypes() {
      return this.checkSupertypes;
   }

   public void setCheckSupertypes(boolean checkSupertypes) {
      this.checkSupertypes = checkSupertypes;
   }

   public NamespaceContext getNamespaceContext() {
      return this.namespaceContext;
   }

   public void setNamespaceContext(NamespaceContext namespaceContext) {
      this.namespaceContext = namespaceContext;
   }

   public String getSchemaLocation() {
      return this.schemaLocation;
   }

   public void setSchemaLocation(String schemaLocation) {
      this.schemaLocation = schemaLocation;
   }

   public String getNoNamespaceschemaLocation() {
      return this.noNamespaceschemaLocation;
   }

   public void setNoNamespaceschemaLocation(String noNamespaceschemaLocation) {
      this.noNamespaceschemaLocation = noNamespaceschemaLocation;
   }

   public Map getInitialNamespaces() {
      return this.initialNamespaces;
   }

   public void setInitialNamespaces(Map initialNamespaces) {
      this.initialNamespaces = initialNamespaces;
   }

   public StaxWriterToNode getStaxWriterToNode() {
      return this.staxWriterToNode;
   }

   public void setStaxWriterToNode(StaxWriterToNode staxWriterToNode) {
      this.staxWriterToNode = staxWriterToNode;
   }

   public boolean isForceIncludeXsiType() {
      return this.forceIncludeXsiType;
   }

   public void setForceIncludeXsiType(boolean forceIncludeXsiType) {
      this.forceIncludeXsiType = forceIncludeXsiType;
   }

   public boolean isForceOracle1012Compatible() {
      return this.forceOracle1012Compatible;
   }

   public void setForceOracle1012Compatible(boolean forceOracle1012Compatible) {
      if (forceOracle1012Compatible) {
         this.setForceIncludeXsiType(true);
      }

      this.forceOracle1012Compatible = forceOracle1012Compatible;
   }

   public XOPMarshaller getMarshaller() {
      return this.marshaller;
   }

   public void setMarshaller(XOPMarshaller marshaller) {
      this.marshaller = marshaller;
   }

   public boolean isForceDotNetCompatibleMarshal() {
      return this.forceDotNetCompatibleMarshal;
   }

   public void setForceDotNetCompatibleMarshal(boolean forceDotNetCompatibleMarshal) {
      this.forceDotNetCompatibleMarshal = forceDotNetCompatibleMarshal;
   }
}
