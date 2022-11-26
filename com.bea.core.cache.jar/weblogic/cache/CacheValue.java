package weblogic.cache;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public class CacheValue implements Serializable {
   private Object content;
   private Hashtable attributes = new Hashtable();
   private Hashtable variables;
   private long created;
   private int timeout;
   private boolean flush;

   public void setContent(Object content) {
      this.content = content;
   }

   public Object getContent() {
      return this.content;
   }

   public void setVariables(Hashtable variables) {
      this.variables = variables;
   }

   public Hashtable getVariables() {
      return this.variables;
   }

   public void setCreated(long created) {
      this.created = created;
   }

   public long getCreated() {
      return this.created;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public void setAttribute(String name, Object value) {
      this.attributes.put(name, value);
   }

   public Object getAttribute(String name) {
      return this.attributes.get(name);
   }

   public Enumeration getAttributeNames() {
      return this.attributes.keys();
   }

   public void setFlush(boolean flush) {
      this.flush = flush;
   }

   public boolean getFlush() {
      return this.flush;
   }
}
