package weblogic.messaging;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.common.MessageIDFactory;
import weblogic.messaging.common.MessageIDImpl;
import weblogic.messaging.common.MessagingUtilities;

public final class GUMessageID extends MessageIDImpl {
   static final long serialVersionUID = -5575306479376894391L;
   private static final byte DIABLOVERSION = 1;
   private static MessageIDFactory messageIDFactory = new MessageIDFactory();

   public static GUMessageID create() {
      return new GUMessageID(messageIDFactory);
   }

   private GUMessageID(MessageIDFactory messageIDFactory) {
      super(messageIDFactory);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof GUMessageID) ? false : super.equals(o);
      }
   }

   public GUMessageID() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte version = in.readByte();
      if (version != 1) {
         throw MessagingUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
      }
   }
}
