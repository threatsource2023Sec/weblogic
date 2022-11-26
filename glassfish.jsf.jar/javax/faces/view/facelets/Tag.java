package javax.faces.view.facelets;

import javax.faces.view.Location;

public final class Tag {
   private final TagAttributes attributes;
   private final Location location;
   private final String namespace;
   private final String localName;
   private final String qName;

   public Tag(Location location, String namespace, String localName, String qName, TagAttributes attributes) {
      this.location = location;
      this.namespace = namespace;
      this.localName = localName;
      this.qName = qName;
      this.attributes = attributes;
   }

   public Tag(Tag orig, TagAttributes attributes) {
      this(orig.getLocation(), orig.getNamespace(), orig.getLocalName(), orig.getQName(), attributes);
   }

   public TagAttributes getAttributes() {
      return this.attributes;
   }

   public String getLocalName() {
      return this.localName;
   }

   public Location getLocation() {
      return this.location;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String getQName() {
      return this.qName;
   }

   public String toString() {
      return this.location + " <" + this.qName + ">";
   }
}
