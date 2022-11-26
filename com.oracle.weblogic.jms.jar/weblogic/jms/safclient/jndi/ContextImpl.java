package weblogic.jms.safclient.jndi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.w3c.dom.Document;
import weblogic.jms.extensions.ClientSAF;
import weblogic.jms.safclient.ClientSAFDelegate;
import weblogic.jms.safclient.admin.ConfigurationUtils;
import weblogic.jms.safclient.agent.DestinationImpl;

public class ContextImpl implements Context {
   private ClientSAF provider = null;
   private HashMap contextMap = new HashMap();
   private HashMap jmsMap = new HashMap();

   public ContextImpl(ClientSAF provider, Document configuration, ClientSAFDelegate root) throws JMSException {
      this.provider = provider;
      ConfigurationUtils.doJNDIConnectionFactories(configuration, root, this.contextMap);
      ConfigurationUtils.doJNDIDestinations(configuration, this.jmsMap, this.contextMap);
   }

   public DestinationImpl getDestination(String groupName, String destinationName) {
      synchronized(this.jmsMap) {
         HashMap destinationMap = (HashMap)this.jmsMap.get(groupName);
         return destinationMap == null ? null : (DestinationImpl)destinationMap.get(destinationName);
      }
   }

   public DestinationImpl getDestination(String destinationName) {
      synchronized(this.jmsMap) {
         Iterator it = this.jmsMap.keySet().iterator();

         DestinationImpl retVal;
         do {
            if (!it.hasNext()) {
               return null;
            }

            HashMap destinationMap = (HashMap)this.jmsMap.get(it.next());
            retVal = (DestinationImpl)destinationMap.get(destinationName);
         } while(retVal == null);

         return retVal;
      }
   }

   public Map getDestinationMap() {
      return this.jmsMap;
   }

   public int howManyDestinationsWithThisName(String destinationName) {
      int retVal = 0;
      synchronized(this.jmsMap) {
         Iterator it = this.jmsMap.keySet().iterator();

         while(it.hasNext()) {
            HashMap destinationMap = (HashMap)this.jmsMap.get(it.next());
            if (destinationMap.containsKey(destinationName)) {
               ++retVal;
            }
         }

         return retVal;
      }
   }

   public Object lookup(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Object lookup(String name) throws NamingException {
      synchronized(this.contextMap) {
         if (!this.contextMap.containsKey(name)) {
            throw new NamingException("No element with key \"" + name + "\" was bound into the JNDI context");
         } else {
            return this.contextMap.get(name);
         }
      }
   }

   public void bind(Name name, Object obj) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void bind(String name, Object obj) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void rebind(Name name, Object obj) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void rebind(String name, Object obj) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void unbind(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void unbind(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void rename(Name oldName, Name newName) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void rename(String oldName, String newName) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NamingEnumeration list(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NamingEnumeration list(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NamingEnumeration listBindings(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NamingEnumeration listBindings(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void destroySubcontext(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void destroySubcontext(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Context createSubcontext(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Context createSubcontext(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Object lookupLink(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Object lookupLink(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NameParser getNameParser(Name name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public NameParser getNameParser(String name) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Name composeName(Name name, Name prefix) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public String composeName(String name, String prefix) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Object addToEnvironment(String propName, Object propVal) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Object removeFromEnvironment(String propName) throws NamingException {
      throw new NamingException("Not implemented");
   }

   public Hashtable getEnvironment() throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void close() throws NamingException {
      if (this.provider != null) {
         this.provider.close();
      }

   }

   public String getNameInNamespace() throws NamingException {
      throw new NamingException("Not implemented");
   }

   public void shutdown(JMSException reason) {
      synchronized(this.contextMap) {
         Iterator it = this.contextMap.keySet().iterator();

         while(it.hasNext()) {
            Object toRemove = this.contextMap.get(it.next());
            if (toRemove instanceof Shutdownable) {
               Shutdownable shutmeDown = (Shutdownable)toRemove;
               shutmeDown.shutdown(reason);
            }
         }

         this.contextMap.clear();
      }

      synchronized(this.jmsMap) {
         this.jmsMap.clear();
      }
   }
}
