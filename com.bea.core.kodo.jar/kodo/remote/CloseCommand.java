package kodo.remote;

import org.apache.openjpa.util.ImplHelper;

class CloseCommand extends KodoCommand {
   public CloseCommand() {
      super((byte)2);
   }

   protected void execute(KodoContextFactory context) {
      Object obj = context.removeContext(this.getClientId());
      ImplHelper.close(obj);
   }
}
