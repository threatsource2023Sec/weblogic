package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.io.PrintStream;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class IdReference extends PolicySchemaObject implements PolicySetMember {
   private String version;
   private String earliestVersion;
   private String latestVersion;
   private URI reference;

   public IdReference(URI reference) {
      this(reference, (String)null);
   }

   public IdReference(URI reference, String version) {
      this(reference, version, (String)null);
   }

   public IdReference(URI reference, String version, String earliestVersion) {
      this(reference, version, earliestVersion, (String)null);
   }

   public IdReference(URI reference, String version, String earliestVersion, String latestVersion) {
      this.reference = reference;
      this.version = version;
      this.earliestVersion = earliestVersion;
      this.latestVersion = latestVersion;
   }

   protected IdReference(Node root) throws URISyntaxException {
      NamedNodeMap attrs = root.getAttributes();
      Node verNode = attrs.getNamedItem("Version");
      if (verNode != null) {
         this.version = verNode.getNodeValue();
      }

      Node earNode = attrs.getNamedItem("EarliestVersion");
      if (earNode != null) {
         this.earliestVersion = earNode.getNodeValue();
      }

      Node latNode = attrs.getNamedItem("LatestVersion");
      if (latNode != null) {
         this.latestVersion = latNode.getNodeValue();
      }

      try {
         this.reference = new URI(root.getFirstChild().getNodeValue());
      } catch (java.net.URISyntaxException var7) {
         throw new URISyntaxException(var7);
      }
   }

   public void encodeAttributes(PrintStream ps) {
      if (this.version != null) {
         ps.print(" Version=\"");
         ps.print(this.version);
         ps.print("\"");
      }

      if (this.earliestVersion != null) {
         ps.print(" EarliestVersion=\"");
         ps.print(this.earliestVersion);
         ps.print("\"");
      }

      if (this.latestVersion != null) {
         ps.print(" LatestVersion=\"");
         ps.print(this.latestVersion);
         ps.print("\"");
      }

   }

   public void encodeValue(PrintStream ps) {
      if (this.reference != null) {
         ps.print(this.reference);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof IdReference)) {
         return false;
      } else {
         IdReference o = (IdReference)other;
         return (this.reference == o.reference || this.reference != null && this.reference.equals(o.reference)) && (this.version == o.version || this.version != null && this.version.equals(o.version)) && (this.earliestVersion == o.earliestVersion || this.earliestVersion != null && this.earliestVersion.equals(o.earliestVersion)) && (this.latestVersion == o.latestVersion || this.latestVersion != null && this.latestVersion.equals(o.latestVersion));
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.reference);
      result = HashCodeUtil.hash(result, this.version);
      result = HashCodeUtil.hash(result, this.earliestVersion);
      result = HashCodeUtil.hash(result, this.latestVersion);
      return result;
   }

   public String getVersion() {
      return this.version;
   }

   public String getEarliestVersion() {
      return this.earliestVersion;
   }

   public String getLatestVersion() {
      return this.latestVersion;
   }

   public URI getReference() {
      return this.reference;
   }
}
