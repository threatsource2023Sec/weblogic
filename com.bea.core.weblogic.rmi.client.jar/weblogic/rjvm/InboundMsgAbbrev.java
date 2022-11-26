package weblogic.rjvm;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.ServiceContext;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.utils.collections.Stack;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.Immutable;

final class InboundMsgAbbrev {
   private static final boolean DEBUG = false;
   private static final Class[] ABBREV_CLASSES = new Class[]{String.class, ServiceContext.class, ClassTableEntry.class, JVMID.class, AuthenticatedUser.class, RuntimeMethodDescriptor.class, Immutable.class};
   private final Stack abbrevs = new Stack();

   void read(MsgAbbrevInputStream in, BubblingAbbrever at) throws IOException, ClassNotFoundException {
      int numAbbrevs = in.readLength();

      for(int i = 0; i < numAbbrevs; ++i) {
         int abbrev = in.readLength();
         Object o;
         if (abbrev > at.getCapacity()) {
            o = this.readObject(in);
            at.getAbbrev(o);
            this.abbrevs.push(o);
         } else {
            o = at.getValue(abbrev);
            this.abbrevs.push(o);
         }
      }

   }

   private Object readObject(MsgAbbrevInputStream in) throws IOException, ClassNotFoundException {
      int typecode = in.read();
      switch (typecode) {
         case 0:
            return (new ServerChannelInputStream(in)).readObjectValidated(ABBREV_CLASSES);
         case 1:
            return in.readASCII();
         default:
            throw new StreamCorruptedException("Unknown typecode: '" + typecode + "'");
      }
   }

   void reset() {
      this.abbrevs.clear();
   }

   void writeTo(MsgAbbrevOutputStream out) throws IOException {
      int abbrevsLen = this.abbrevs.size();

      for(int i = 0; i < abbrevsLen; ++i) {
         out.getAbbrevs().addAbbrev(this.getAbbrev());
      }

   }

   Object getAbbrev() {
      return this.abbrevs.pop();
   }

   public String toString() {
      return super.toString() + " - abbrevs: '" + this.abbrevs + "'";
   }

   private static class ServerChannelInputStream extends FilteringObjectInputStream implements ServerChannelStream {
      private final ServerChannel serverChannel;

      private ServerChannelInputStream(MsgAbbrevInputStream in) throws IOException {
         super(in);
         this.serverChannel = in.getServerChannel();
      }

      public ServerChannel getServerChannel() {
         return this.serverChannel;
      }

      protected Class resolveClass(ObjectStreamClass descriptor) throws ClassNotFoundException, IOException {
         try {
            this.checkLegacyBlacklistIfNeeded(descriptor.getName());
         } catch (InvalidClassException var4) {
            throw var4;
         }

         Class c = super.resolveClass(descriptor);
         if (c == null) {
            throw new ClassNotFoundException("super.resolveClass returns null.");
         } else {
            ObjectStreamClass localDesc = ObjectStreamClass.lookup(c);
            if (localDesc != null && localDesc.getSerialVersionUID() != descriptor.getSerialVersionUID()) {
               throw new ClassNotFoundException("different serialVersionUID. local: " + localDesc.getSerialVersionUID() + " remote: " + descriptor.getSerialVersionUID());
            } else {
               return c;
            }
         }
      }

      protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
         String[] var2 = interfaces;
         int var3 = interfaces.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String intf = var2[var4];
            if (intf.equals("java.rmi.registry.Registry")) {
               throw new InvalidObjectException("Unauthorized proxy deserialization");
            }
         }

         return super.resolveProxyClass(interfaces);
      }

      // $FF: synthetic method
      ServerChannelInputStream(MsgAbbrevInputStream x0, Object x1) throws IOException {
         this(x0);
      }
   }
}
