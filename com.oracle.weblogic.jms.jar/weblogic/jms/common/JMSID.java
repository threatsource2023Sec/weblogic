package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.JMSIDFactories;
import weblogic.messaging.common.IDFactory;
import weblogic.messaging.common.IDImpl;

public final class JMSID extends IDImpl {
   static final long serialVersionUID = -8642705714360206450L;
   public static final IDFactory idFactory;

   public static JMSID create() {
      return new JMSID(idFactory);
   }

   private JMSID(IDFactory idFactory) {
      super(idFactory);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof JMSID) ? false : super.equals(o);
      }
   }

   public JMSID() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
      super.readExternal(in);
   }

   static {
      idFactory = JMSIDFactories.idFactory;
   }
}
