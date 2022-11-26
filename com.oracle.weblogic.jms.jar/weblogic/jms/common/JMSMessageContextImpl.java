package weblogic.jms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Message;

public class JMSMessageContextImpl implements JMSMessageContext {
   private Message msg;
   protected Map propertyMap = new HashMap();
   private boolean result;
   private boolean done;
   private JMSMessageId msgId;
   private Destination destination = null;
   private JMSFailover failover = null;
   private String user = null;

   public JMSMessageContextImpl(Message msg) {
      this.msg = msg;
   }

   public Message getMessage() {
      return this.msg;
   }

   public void setMessage(Message msg) {
      this.msg = msg;
   }

   public String toString() {
      return this.propertyMap.toString() + this.msg.toString();
   }

   public void setProperty(String name, Object value) {
      this.propertyMap.put(name, value);
   }

   public Object getProperty(String name) {
      return this.propertyMap.get(name);
   }

   public void removeProperty(String name) {
      this.propertyMap.remove(name);
   }

   public boolean containsProperty(String name) {
      return this.propertyMap.containsKey(name);
   }

   public Iterator getPropertyNames() {
      return this.propertyMap.keySet().iterator();
   }

   public void setResult(boolean result) {
      this.done = true;
      this.result = result;
   }

   public boolean isDone() {
      return this.done;
   }

   public boolean isContinue() {
      return this.result;
   }

   public void setReturnedMessageId(JMSMessageId msgId) {
      this.msgId = msgId;
   }

   public Destination getDestination() {
      return this.destination;
   }

   public void setDestination(Destination destination) {
      this.destination = destination;
   }

   public JMSFailover getFailover() {
      return this.failover;
   }

   public void setFailover(JMSFailover failover) {
      this.failover = failover;
   }

   public String getUser() {
      return this.user;
   }

   public void setUser(String user) {
      this.user = user;
   }
}
