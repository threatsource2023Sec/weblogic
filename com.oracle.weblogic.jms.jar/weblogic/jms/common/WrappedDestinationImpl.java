package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.JMSEnvironment;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.jndi.internal.NamingNode;

@CrossPartitionAware
public class WrappedDestinationImpl implements OpaqueReference, Externalizable, Aggregatable {
   private static DestinationImplObserver observer;
   private static final long serialVersionUID = -4813981323958454755L;
   private static Map partitionsDests = new HashMap();
   private DestinationImpl destinationImpl;

   public WrappedDestinationImpl() {
   }

   public WrappedDestinationImpl(DestinationImpl destinationImpl) {
      this.destinationImpl = destinationImpl;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      if (this.destinationImpl == null) {
         throw new NameNotFoundException("Name not found");
      } else {
         return this.destinationImpl;
      }
   }

   public String toString() {
      return this.destinationImpl == null ? null : this.destinationImpl.toString();
   }

   public void onBind(NamingNode store, String name, Aggregatable newData) throws NamingException {
      String absoluteName = this.getFullJNDINodeName(store) + '.' + name;
      boolean nullNewDataArg = false;
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      Map partitionDests = getPartitionDests(partitionName);
      List destList = getDests(absoluteName, partitionDests);
      DestinationImpl newValue;
      if (newData == null) {
         newValue = this.destinationImpl;
         if (!destList.contains(this.destinationImpl.getId())) {
            destList.add(this.destinationImpl.getId());
         }
      } else {
         newValue = ((WrappedDestinationImpl)newData).destinationImpl;
         if (!destList.contains(newValue.getId())) {
            destList.add(newValue.getId());
         }
      }

      if (observer != null) {
         observer.newDestination(this.getBoundName(store, name), newValue);
      }

   }

   public void onRebind(NamingNode store, String name, Aggregatable other) throws NamingException {
      throw new NameAlreadyBoundException("Name already bound");
   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      String absoluteName = this.getFullJNDINodeName(store) + '.' + name;
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      Map partitionDests = getPartitionDests(partitionName);
      List destList = getDests(absoluteName, partitionDests);
      if (observer != null) {
         observer.removeDestination(this.getBoundName(store, name), this.destinationImpl);
      }

      if (other != null) {
         destList.remove(((WrappedDestinationImpl)other).destinationImpl.getId());
      } else if (this.destinationImpl != null) {
         destList.remove(this.destinationImpl.getId());
      }

      return destList.size() == 0;
   }

   private String getBoundName(NamingNode store, String name) {
      String boundWhere = null;

      try {
         String nameInNamespace = store.getNameInNamespace();
         if (nameInNamespace != null && nameInNamespace.length() != 0) {
            boundWhere = nameInNamespace + '.' + name;
         } else {
            boundWhere = name;
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return boundWhere;
   }

   public static void setObserver(DestinationImplObserver paramObserver) {
      observer = paramObserver;
   }

   private static List getDests(String absoluteName, Map partitionDests) {
      synchronized(partitionDests) {
         List destList = (List)partitionDests.get(absoluteName);
         if (destList != null) {
            return destList;
         } else {
            destList = Collections.synchronizedList(new LinkedList());
            partitionDests.put(absoluteName, destList);
            return destList;
         }
      }
   }

   private String getFullJNDINodeName(NamingNode store) {
      return JMSEnvironment.getJMSEnvironment().getFullJNDINodeName(store);
   }

   private static Map getPartitionDests(String partitionName) {
      synchronized(partitionsDests) {
         Map partitionDests = (Map)partitionsDests.get(partitionName);
         if (partitionDests != null) {
            return partitionDests;
         } else {
            Map partitionDests = new HashMap();
            partitionsDests.put(partitionName, partitionDests);
            return partitionDests;
         }
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.destinationImpl = new DestinationImpl();
      this.destinationImpl.readExternal(in);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.destinationImpl.writeExternal(out);
   }
}
