package com.bea.common.security.xacml.context;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.attr.Bag;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Attribute extends ContextSchemaObject {
   private static final long serialVersionUID = -1285696818824143651L;
   private URI attributeId;
   private URI dataType;
   private String issuer;
   private List avs;

   public Attribute(URI attributeId, URI dataType, List avs) {
      this(attributeId, dataType, (String)null, (List)avs);
   }

   public Attribute(URI attributeId, URI dataType, Bag values) {
      this(attributeId, dataType, (String)null, (Bag)values);
   }

   public Attribute(URI attributeId, URI dataType, String issuer, Bag values) {
      this(attributeId, dataType, issuer, convertValues(values));
   }

   private static List convertValues(Bag values) {
      List avs = null;
      if (values != null) {
         avs = new ArrayList();
         Iterator var2 = values.iterator();

         while(var2.hasNext()) {
            com.bea.common.security.xacml.attr.AttributeValue av = (com.bea.common.security.xacml.attr.AttributeValue)var2.next();
            avs.add(new AttributeValue(av));
         }
      }

      return avs;
   }

   public Attribute(URI attributeId, URI dataType, String issuer, List avs) {
      this.attributeId = attributeId;
      this.dataType = dataType;
      this.issuer = issuer;
      this.avs = avs != null ? Collections.unmodifiableList(avs) : null;
   }

   protected Attribute(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.attributeId = new URI(attrs.getNamedItem("AttributeId").getNodeValue());
         this.dataType = new URI(attrs.getNamedItem("DataType").getNodeValue());
      } catch (java.net.URISyntaxException var10) {
         throw new URISyntaxException(var10);
      }

      Node issuerNode = attrs.getNamedItem("Issuer");
      if (issuerNode != null) {
         this.issuer = issuerNode.getNodeValue();
      }

      NodeList nodes = root.getChildNodes();
      List avs = new ArrayList();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         if (this.getLocalName(node).equals("AttributeValue")) {
            com.bea.common.security.xacml.attr.AttributeValue av = registry.getAttribute(this.dataType, node);
            if (av != null) {
               avs.add(new AttributeValue(av));
            }
         }
      }

      this.avs = !avs.isEmpty() ? Collections.unmodifiableList(avs) : null;
   }

   public String getElementName() {
      return "Attribute";
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.attributeId != null) {
         ps.print(" AttributeId=\"");
         ps.print(this.attributeId);
         ps.print("\"");
      }

      if (this.dataType != null) {
         ps.print(" DataType=\"");
         ps.print(this.dataType);
         ps.print("\"");
      }

      if (this.issuer != null) {
         ps.print(" Issuer=\"");
         ps.print(this.escapeXML(this.issuer));
         ps.print("\"");
      }

   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.avs != null) {
         Iterator var3 = this.avs.iterator();

         while(var3.hasNext()) {
            AttributeValue av = (AttributeValue)var3.next();
            av.encode(nsMap, ps);
         }
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Attribute)) {
         return false;
      } else {
         Attribute o = (Attribute)other;
         return (this.attributeId == o.attributeId || this.attributeId != null && this.attributeId.equals(o.attributeId)) && (this.dataType == o.dataType || this.dataType != null && this.dataType.equals(o.dataType)) && (this.issuer == o.issuer || this.issuer != null && this.issuer.equals(o.issuer)) && CollectionUtil.equals(this.avs, o.avs);
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.attributeId);
      result = HashCodeUtil.hash(result, this.dataType);
      result = HashCodeUtil.hash(result, this.issuer);
      result = HashCodeUtil.hash(result, this.avs);
      return result;
   }

   public URI getAttributeId() {
      return this.attributeId;
   }

   public URI getDataType() {
      return this.dataType;
   }

   public String getIssuer() {
      return this.issuer;
   }

   public List getAttributeValues() {
      return this.avs;
   }
}
