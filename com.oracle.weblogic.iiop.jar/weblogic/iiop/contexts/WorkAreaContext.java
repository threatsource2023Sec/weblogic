package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;

public class WorkAreaContext extends ServiceContext {
   public WorkAreaContext(WorkContextOutput out) {
      super(1111834891);
      ContextOutputImpl cout = (ContextOutputImpl)out;
      this.context_data = cout.getDelegate().getBuffer();
      cout.getDelegate().close();
   }

   public WorkAreaContext(CorbaInputStream in) {
      super(1111834891);
      this.context_data = in.read_octet_sequence();
   }

   public WorkContextInput getInputStream() {
      CorbaInputStream inputStream = IiopProtocolFacade.createInputStream(this.context_data);
      inputStream.consumeEndian();
      return new ContextInputImpl(inputStream);
   }

   public String toString() {
      return "WorkAreaContext";
   }
}
