package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;

class ResultSizeCommand extends KodoCommand {
   private int _size = -1;

   public ResultSizeCommand() {
      super((byte)17);
   }

   public int size() {
      return this._size;
   }

   protected void execute(KodoContextFactory context) {
      this._size = ((ResultCommand.ContextualResult)context.getContext(this.getClientId())).result.size();
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._size = in.readInt();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeInt(this._size);
   }
}
