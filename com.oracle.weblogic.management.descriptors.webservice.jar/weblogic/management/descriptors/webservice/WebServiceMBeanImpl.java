package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;
import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xmlnode.XMLNodeSet;

public class WebServiceMBeanImpl extends XMLElementMBeanDelegate implements WebServiceMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_webServiceName = false;
   private String webServiceName;
   private boolean isSet_security = false;
   private XMLNode security;
   private boolean isSet_style = false;
   private String style;
   private boolean isSet_protocol = false;
   private String protocol;
   private boolean isSet_portName = false;
   private String portName;
   private boolean isSet_jmsURI = false;
   private String jmsURI;
   private boolean isSet_typeMapping = false;
   private TypeMappingMBean typeMapping;
   private boolean isSet_types = false;
   private XMLNodeSet types;
   private boolean isSet_targetNamespace = false;
   private String targetNamespace;
   private boolean isSet_useSOAP12 = false;
   private boolean useSOAP12;
   private boolean isSet_exposeWSDL = false;
   private boolean exposeWSDL = true;
   private boolean isSet_exposeHomePage = false;
   private boolean exposeHomePage = true;
   private boolean isSet_portTypeName = false;
   private String portTypeName;
   private boolean isSet_charset = false;
   private String charset;
   private boolean isSet_uri = false;
   private String uri;
   private boolean isSet_components = false;
   private ComponentsMBean components;
   private boolean isSet_responseBufferSize = false;
   private int responseBufferSize;
   private boolean isSet_operations = false;
   private OperationsMBean operations;
   private boolean ignoreAuthHeader;
   private boolean isSet_ignoreAuthHeader = false;
   private boolean handleAllActors = true;
   private boolean isSet_handleAllActors = false;

   public String getWebServiceName() {
      return this.webServiceName;
   }

   public void setWebServiceName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.webServiceName;
      this.webServiceName = value;
      this.isSet_webServiceName = value != null;
      this.checkChange("webServiceName", old, this.webServiceName);
   }

   public XMLNode getSecurity() {
      return this.security;
   }

   public void setSecurity(XMLNode value) {
      XMLNode old = this.security;
      this.security = value;
      this.isSet_security = value != null;
      this.checkChange("security", old, this.security);
   }

   public String getStyle() {
      return this.style;
   }

   public void setStyle(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.style;
      this.style = value;
      this.isSet_style = value != null;
      this.checkChange("style", old, this.style);
   }

   public String getProtocol() {
      return this.protocol;
   }

   public void setProtocol(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.protocol;
      this.protocol = value;
      this.isSet_protocol = value != null;
      this.checkChange("protocol", old, this.protocol);
   }

   public String getPortName() {
      return this.portName;
   }

   public void setPortName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.portName;
      this.portName = value;
      this.isSet_portName = value != null;
      this.checkChange("portName", old, this.portName);
   }

   public String getJmsURI() {
      return this.jmsURI;
   }

   public void setJmsURI(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.jmsURI;
      this.jmsURI = value;
      this.isSet_jmsURI = value != null;
      this.checkChange("jmsURI", old, this.jmsURI);
   }

   public TypeMappingMBean getTypeMapping() {
      return this.typeMapping;
   }

   public void setTypeMapping(TypeMappingMBean value) {
      TypeMappingMBean old = this.typeMapping;
      this.typeMapping = value;
      this.isSet_typeMapping = value != null;
      this.checkChange("typeMapping", old, this.typeMapping);
   }

   public XMLNodeSet getTypes() {
      return this.types;
   }

   public void setTypes(XMLNodeSet value) {
      XMLNodeSet old = this.types;
      this.types = value;
      this.isSet_types = value != null;
      this.checkChange("types", old, this.types);
   }

   public String getTargetNamespace() {
      return this.targetNamespace;
   }

   public void setTargetNamespace(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.targetNamespace;
      this.targetNamespace = value;
      this.isSet_targetNamespace = value != null;
      this.checkChange("targetNamespace", old, this.targetNamespace);
   }

   public boolean getExposeWSDL() {
      return this.exposeWSDL;
   }

   public void setExposeWSDL(boolean value) {
      boolean old = this.exposeWSDL;
      this.exposeWSDL = value;
      this.isSet_exposeWSDL = true;
      this.checkChange("exposeWSDL", old, this.exposeWSDL);
   }

   public boolean getExposeHomePage() {
      return this.exposeHomePage;
   }

   public void setExposeHomePage(boolean value) {
      boolean old = this.exposeHomePage;
      this.exposeHomePage = value;
      this.isSet_exposeWSDL = true;
      this.checkChange("exposeHomePage", old, this.exposeHomePage);
   }

   public boolean getUseSOAP12() {
      return this.useSOAP12;
   }

   public void setUseSOAP12(boolean value) {
      boolean old = this.useSOAP12;
      this.useSOAP12 = value;
      this.isSet_useSOAP12 = true;
      this.checkChange("useSOAP12", old, this.useSOAP12);
   }

   public boolean getHandleAllActors() {
      return this.handleAllActors;
   }

   public void setHandleAllActors(boolean value) {
      boolean old = this.handleAllActors;
      this.handleAllActors = value;
      this.isSet_handleAllActors = true;
      this.checkChange("handleAllActors", old, this.handleAllActors);
   }

   public String getPortTypeName() {
      return this.portTypeName;
   }

   public void setPortTypeName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.portTypeName;
      this.portTypeName = value;
      this.isSet_portTypeName = value != null;
      this.checkChange("portTypeName", old, this.portTypeName);
   }

   public boolean getIgnoreAuthHeader() {
      return this.ignoreAuthHeader;
   }

   public void setIgnoreAuthHeader(boolean value) {
      boolean old = this.ignoreAuthHeader;
      this.ignoreAuthHeader = value;
      this.isSet_ignoreAuthHeader = true;
      this.checkChange("ignroeAuthHeader", old, this.ignoreAuthHeader);
   }

   public String getCharset() {
      return this.charset;
   }

   public void setCharset(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.charset;
      this.charset = value;
      this.isSet_charset = value != null;
      this.checkChange("charset", old, this.charset);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.uri;
      this.uri = value;
      this.isSet_uri = value != null;
      this.checkChange("uri", old, this.uri);
   }

   public ComponentsMBean getComponents() {
      return this.components;
   }

   public void setComponents(ComponentsMBean value) {
      ComponentsMBean old = this.components;
      this.components = value;
      this.isSet_components = value != null;
      this.checkChange("components", old, this.components);
   }

   public int getResponseBufferSize() {
      return this.responseBufferSize;
   }

   public void setResponseBufferSize(int value) {
      int old = this.responseBufferSize;
      this.responseBufferSize = value;
      this.isSet_responseBufferSize = value != -1;
      this.checkChange("responseBufferSize", old, this.responseBufferSize);
   }

   public OperationsMBean getOperations() {
      return this.operations;
   }

   public void setOperations(OperationsMBean value) {
      OperationsMBean old = this.operations;
      this.operations = value;
      this.isSet_operations = value != null;
      this.checkChange("operations", old, this.operations);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<web-service");
      if (this.isSet_protocol) {
         result.append(" protocol=\"").append(String.valueOf(this.getProtocol())).append("\"");
      }

      if (this.isSet_charset) {
         result.append(" jmsUri=\"").append(String.valueOf(this.getCharset())).append("\"");
      }

      if (this.isSet_useSOAP12) {
         result.append(" useSOAP12=\"").append(String.valueOf(this.getUseSOAP12())).append("\"");
      }

      if (this.isSet_exposeWSDL) {
         result.append(" exposeWSDL=\"").append(String.valueOf(this.getExposeWSDL())).append("\"");
      }

      if (this.isSet_exposeHomePage) {
         result.append(" exposeHomePage=\"").append(String.valueOf(this.getExposeHomePage())).append("\"");
      }

      if (this.isSet_targetNamespace) {
         result.append(" targetNamespace=\"").append(String.valueOf(this.getTargetNamespace())).append("\"");
      }

      if (this.isSet_webServiceName) {
         result.append(" name=\"").append(String.valueOf(this.getWebServiceName())).append("\"");
      }

      if (this.isSet_responseBufferSize) {
         result.append(" responseBufferSize=\"").append(String.valueOf(this.getResponseBufferSize())).append("\"");
      }

      if (this.isSet_style) {
         result.append(" style=\"").append(String.valueOf(this.getStyle())).append("\"");
      }

      if (this.isSet_portTypeName) {
         result.append(" portTypeName=\"").append(String.valueOf(this.getPortTypeName())).append("\"");
      }

      if (this.isSet_jmsURI) {
         result.append(" jmsUri=\"").append(String.valueOf(this.getJmsURI())).append("\"");
      }

      if (this.isSet_uri) {
         result.append(" uri=\"").append(String.valueOf(this.getURI())).append("\"");
      }

      if (this.isSet_portName) {
         result.append(" portName=\"").append(String.valueOf(this.getPortName())).append("\"");
      }

      if (this.isSet_ignoreAuthHeader) {
         result.append(" ignoreAuthHeader=\"").append(String.valueOf(this.getIgnoreAuthHeader())).append("\"");
      }

      result.append(">\n");
      if (null != this.getSecurity()) {
         result.append(ToXML.indent(indentLevel + 2)).append(this.getSecurity()).append("\n");
      }

      if (null != this.getTypes()) {
         result.append(ToXML.indent(indentLevel + 2)).append("\n<types>").append(this.getTypes()).append("\n</types>\n");
      }

      if (null != this.getTypeMapping()) {
         result.append(this.getTypeMapping().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getComponents()) {
         result.append(this.getComponents().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getOperations()) {
         result.append(this.getOperations().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</web-service>\n");
      return result.toString();
   }
}
