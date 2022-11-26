package weblogic.jms.dispatcher;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.JMSEnvironment;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.rmi.extensions.PortableRemoteObject;

@CrossPartitionAware
public final class DispatcherWrapper extends weblogic.messaging.dispatcher.DispatcherWrapper {
   static final long serialVersionUID = -569390197367234160L;

   public DispatcherWrapper() {
   }

   public DispatcherWrapper(weblogic.messaging.dispatcher.DispatcherImpl dispatcherImpl, String partitionId, String partitionName, String connectionPartitionName) {
      super(dispatcherImpl, partitionId, partitionName, connectionPartitionName);
   }

   private DispatcherImpl localJMSDispatcherImpl() throws IOException {
      DispatcherImpl tmpDispatcher = this.interopDispatcher;
      if (tmpDispatcher != null && this.interopDispatcher.isPartitionActive()) {
         return tmpDispatcher;
      } else {
         JMSDispatcher jmsDispatcher = JMSEnvironment.getJMSEnvironment().findDispatcherByPartitionIdUnmarshalException(this.getPartitionId());
         weblogic.messaging.dispatcher.DispatcherImpl localDispatcher = (weblogic.messaging.dispatcher.DispatcherImpl)jmsDispatcher.getDelegate();
         return this.interopDispatcher = localDispatcher.getInteropDispatcher();
      }
   }

   protected void writeExternalInterop(ObjectOutput out) throws IOException {
      out.writeObject(this.localJMSDispatcherImpl());
      out.writeObject(this.localJMSDispatcherImpl());
   }

   protected void readExternalInterop(ObjectInput in) throws IOException, ClassNotFoundException {
      DispatcherRemote d1 = (DispatcherRemote)PortableRemoteObject.narrow(in.readObject(), DispatcherRemote.class);
      DispatcherOneWay d2 = (DispatcherOneWay)PortableRemoteObject.narrow(in.readObject(), DispatcherOneWay.class);
      DispatcherInteropAdapter dia = new DispatcherInteropAdapter(d1, d2);
      this.dispatcherRemote = dia;
      this.dispatcherOneWay = dia;
   }
}
