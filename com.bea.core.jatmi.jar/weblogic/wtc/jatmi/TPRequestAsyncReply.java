package weblogic.wtc.jatmi;

import java.io.Serializable;

public interface TPRequestAsyncReply extends Serializable {
   void success(Reply var1);

   void failure(TPException var1);
}
