package weblogic.messaging.dispatcher;

import java.io.Externalizable;

public class VoidResponse extends Response implements Externalizable, InteropJMSVoidResponsePreDiablo {
   static final long serialVersionUID = -5605660440698648780L;
   public static final VoidResponse THE_ONE = new VoidResponse();
}
