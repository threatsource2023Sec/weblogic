package weblogic.jms.dispatcher;

import java.io.Externalizable;
import weblogic.messaging.dispatcher.InteropJMSVoidResponsePreDiablo;

public final class VoidResponse extends Response implements Externalizable, InteropJMSVoidResponsePreDiablo {
   static final long serialVersionUID = -5605660440698648780L;
   public static final VoidResponse THE_ONE = new VoidResponse();
}
