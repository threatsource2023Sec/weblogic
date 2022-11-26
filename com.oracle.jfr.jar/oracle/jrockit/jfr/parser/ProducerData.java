package oracle.jrockit.jfr.parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ProducerData implements FLRProducer {
   final int id;
   final String namespace;
   final URI uri;
   final String name;
   final String desc;
   final List events;
   final HashMap contentTypes;
   final List structs;

   public ProducerData(int id, String name, String desc, URI uri, String namespace, List events, HashMap contentTypes, List structs) {
      this.id = id;
      this.name = name;
      this.desc = desc;
      this.uri = uri;
      this.namespace = namespace;
      this.events = events;
      this.contentTypes = contentTypes;
      this.structs = structs;
   }

   public String getDescription() {
      return this.desc;
   }

   public List getEventInfos() {
      ArrayList l = new ArrayList(this.events.size());
      l.addAll(this.events);
      return l;
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public URI getURI() {
      return this.uri;
   }

   public String getURIString() {
      return this.uri.toString();
   }

   public String toString() {
      StringBuilder buf = (new StringBuilder(this.name)).append("[").append(this.id).append(',').append(this.desc).append(',').append(this.uri).append("]");
      return buf.toString();
   }
}
