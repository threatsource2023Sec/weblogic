package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractPolicy extends PolicySchemaObject implements PolicySetMember {
   public static final String VERSION_DEFAULT = "1.0";
   private String description;
   private Target target;
   private List combinerParameters;
   private URI id;
   private String version;
   private URI combiningAlgId;
   private Obligations obligations;

   public AbstractPolicy(URI id, Target target, URI combiningAlgId) {
      this((URI)id, (Target)target, (URI)combiningAlgId, (String)null);
   }

   public AbstractPolicy(URI id, Target target, URI combiningAlgId, String description) {
      this(id, target, combiningAlgId, description, "1.0");
   }

   public AbstractPolicy(URI id, Target target, URI combiningAlgId, String description, String version) {
      this(id, target, combiningAlgId, description, version, (List)null, (Obligations)null);
   }

   public AbstractPolicy(URI id, Target target, URI combiningAlgId, String description, String version, List combinerParameters, Obligations obligations) {
      this.description = description;
      this.target = target;
      this.combinerParameters = combinerParameters != null ? Collections.unmodifiableList(combinerParameters) : null;
      this.id = id;
      this.version = version;
      this.combiningAlgId = combiningAlgId;
      this.obligations = obligations;
   }

   protected AbstractPolicy(AttributeRegistry registry, Node root, String policyPrefix, String combiningName) throws DocumentParseException, URISyntaxException {
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.id = new URI(attrs.getNamedItem(policyPrefix + "Id").getNodeValue());
      } catch (java.net.URISyntaxException var13) {
         throw new URISyntaxException(var13);
      }

      Node versionNode = attrs.getNamedItem("Version");
      if (versionNode != null) {
         this.version = versionNode.getNodeValue();
      } else {
         this.version = "1.0";
      }

      try {
         this.combiningAlgId = new URI(attrs.getNamedItem(combiningName).getNodeValue());
      } catch (java.net.URISyntaxException var12) {
         throw new URISyntaxException(var12);
      }

      this.combinerParameters = new ArrayList();
      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("Description")) {
            Node dchild = child.getFirstChild();
            if (dchild != null) {
               this.description = dchild.getNodeValue();
            }
         } else if (cname.equals("Target")) {
            this.target = new Target(registry, child);
         } else if (cname.equals("Obligations")) {
            this.obligations = new Obligations(registry, child);
         } else if (cname.equals("CombinerParameters")) {
            this.combinerParameters.add(new CombinerParameters(registry, child));
         }
      }

      this.combinerParameters = this.combinerParameters.isEmpty() ? null : Collections.unmodifiableList(this.combinerParameters);
   }

   protected abstract String getPolicyPrefix();

   protected abstract String getCombiningName();

   public abstract IdReference getReference();

   public void encodeAttributes(PrintStream ps) {
      ps.print(' ');
      ps.print(this.getPolicyPrefix());
      ps.print("Id=\"");
      ps.print(this.id);
      ps.print("\"");
      if (this.version != null && !"1.0".equals(this.version)) {
         ps.print(" Version=\"");
         ps.print(this.version);
         ps.print("\"");
      }

      ps.print(" ");
      ps.print(this.getCombiningName());
      ps.print("=\"");
      ps.print(this.combiningAlgId);
      ps.print("\"");
   }

   protected void encodeDescription(PrintStream ps) {
      if (this.description != null) {
         ps.print("<Description>");
         if (this.description.startsWith("<![CDATA[") && this.description.endsWith("]]>")) {
            ps.print(this.description);
         } else {
            ps.print(this.escapeXML(this.description));
         }

         ps.print("</Description>");
      }

   }

   protected void encodeTarget(Map nsMap, PrintStream ps) {
      if (this.target != null) {
         this.target.encode(nsMap, ps);
      }

   }

   protected void encodeCombinerParameters(Map nsMap, PrintStream ps) {
      if (this.combinerParameters != null) {
         Iterator it = this.combinerParameters.iterator();

         while(it.hasNext()) {
            CombinerParameters cp = (CombinerParameters)it.next();
            cp.encode(nsMap, ps);
         }
      }

   }

   protected void encodeObligations(Map nsMap, PrintStream ps) {
      if (this.obligations != null) {
         this.obligations.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AbstractPolicy)) {
         return false;
      } else {
         AbstractPolicy o = (AbstractPolicy)other;
         return (this.target == o.target || this.target != null && this.target.equals(o.target)) && (this.id == o.id || this.id != null && this.id.equals(o.id)) && CollectionUtil.equals(this.combinerParameters, o.combinerParameters) && (this.version == o.version || this.version != null && this.version.equals(o.version) || this.version == null && "1.0".equals(o.version) || o.version == null && "1.0".equals(this.version)) && (this.combiningAlgId == o.combiningAlgId || this.combiningAlgId != null && this.combiningAlgId.equals(o.combiningAlgId)) && (this.obligations == o.obligations || this.obligations != null && this.obligations.equals(o.obligations));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.target);
      result = HashCodeUtil.hash(result, this.id);
      result = HashCodeUtil.hash(result, this.combinerParameters);
      result = HashCodeUtil.hash(result, this.version != null ? this.version : "1.0");
      result = HashCodeUtil.hash(result, this.combiningAlgId);
      result = HashCodeUtil.hash(result, this.obligations);
      return result;
   }

   public String getDescription() {
      return this.description;
   }

   public Target getTarget() {
      return this.target;
   }

   public List getCombinerParameters() {
      return this.combinerParameters;
   }

   public URI getId() {
      return this.id;
   }

   public String getVersion() {
      return this.version == null ? "1.0" : this.version;
   }

   public URI getCombiningAlgId() {
      return this.combiningAlgId;
   }

   public Obligations getObligations() {
      return this.obligations;
   }
}
