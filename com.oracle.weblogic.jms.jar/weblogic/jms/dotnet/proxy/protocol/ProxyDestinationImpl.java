package weblogic.jms.dotnet.proxy.protocol;

import java.io.Externalizable;
import java.io.IOException;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import weblogic.jms.common.BufferDataInputStream;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.ObjectIOBypass;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

public class ProxyDestinationImpl implements MarshalReadable, MarshalWritable {
   private static final int EXTVERSION = 1;
   private static final int _SERIALIZED_DESTINATION = 1;
   private static final int _HAS_CREATE_DESTINATION_ARG = 2;
   private static final int _IS_DISTRIBUTED_DESTINATION = 3;
   public static final byte TYPE_QUEUE = 1;
   public static final byte TYPE_TOPIC = 2;
   public static final byte TYPE_TEMP_QUEUE = 3;
   public static final byte TYPE_TEMP_TOPIC = 4;
   public transient Destination destination;
   private String name;
   private int type;
   private String createDestinationArgument;
   private boolean marshaled;
   private byte[] marshaledDestination;
   private boolean isDD;

   public ProxyDestinationImpl(String name, Destination destination) throws JMSException {
      this.setupDestination(destination);
   }

   public ProxyDestinationImpl(Destination destination) throws JMSException {
      this.setupDestination(destination);
   }

   private void setupDestination(Destination destination) throws JMSException {
      this.destination = destination;
      if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
         JMSDebug.JMSDotNetProxy.debug("Destination = " + destination.getClass());
      }

      if (destination instanceof Externalizable) {
         if (destination instanceof DestinationImpl) {
            this.name = ((DestinationImpl)destination).getName();
            this.type = ((DestinationImpl)destination).getType();
            if (destination instanceof DistributedDestinationImpl) {
               this.isDD = true;
               this.createDestinationArgument = ((DistributedDestinationImpl)destination).getCreateDestinationArgument();
            } else {
               this.createDestinationArgument = ((DestinationImpl)destination).getCreateDestinationArgument();
            }
         } else if (destination instanceof Queue) {
            this.name = ((Queue)destination).getQueueName();
            if (destination instanceof TemporaryQueue) {
               this.type = 4;
            } else {
               this.type = 1;
            }
         } else if (destination instanceof Topic) {
            this.name = ((Topic)destination).getTopicName();
            if (destination instanceof TemporaryTopic) {
               this.type = 8;
            } else {
               this.type = 2;
            }
         }

         this.marshaledDestination = ProxyUtil.marshalExternalizable((Externalizable)destination);
         this.marshaled = true;
      }

   }

   public String getName() {
      return this.name;
   }

   public Destination getJMSDestination() {
      return this.destination;
   }

   public byte[] getMarshaledDestination() {
      return this.marshaledDestination;
   }

   private void unmarshalDestination() {
      BufferDataInputStream stream = new BufferDataInputStream((ObjectIOBypass)null, this.marshaledDestination);
      if (this.isDD) {
         this.destination = new DistributedDestinationImpl();
      } else {
         this.destination = new DestinationImpl();
      }

      try {
         if (this.isDD) {
            ((DistributedDestinationImpl)this.destination).readExternal(stream);
         } else {
            ((DestinationImpl)this.destination).readExternal(stream);
         }
      } catch (IOException var3) {
         var3.printStackTrace();
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
      }

   }

   public ProxyDestinationImpl() {
   }

   public int getMarshalTypeCode() {
      return 41;
   }

   public void marshal(MarshalWriter mw) {
      MarshalBitMask versionFlags = new MarshalBitMask(1);
      if (this.marshaled) {
         versionFlags.setBit(1);
      }

      if (this.createDestinationArgument != null) {
         versionFlags.setBit(2);
      }

      if (this.isDD) {
         versionFlags.setBit(3);
      }

      versionFlags.marshal(mw);
      mw.writeString(this.name);
      mw.writeByte((byte)this.type);
      if (this.marshaled) {
         mw.writeInt(this.marshaledDestination.length);
         mw.write(this.marshaledDestination, 0, this.marshaledDestination.length);
      }

      if (this.createDestinationArgument != null) {
         mw.writeString(this.createDestinationArgument);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      this.marshaled = versionFlags.isSet(1);
      this.name = mr.readString();
      this.type = mr.readByte();
      this.isDD = versionFlags.isSet(3);
      if (this.marshaled) {
         int size = mr.readInt();
         this.marshaledDestination = new byte[size];
         mr.read(this.marshaledDestination, 0, size);
         this.unmarshalDestination();
      }

      if (versionFlags.isSet(2)) {
         this.createDestinationArgument = mr.readString();
      }

   }

   public String toString() {
      return "ProxyDestination<+ " + this.name + ">";
   }
}
