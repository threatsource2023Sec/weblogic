package kodo.remote;

import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.meta.ClassMetaData;

class ExtentResultObjectProvider extends ClientResultObjectProvider {
   private final Class _cls;
   private final boolean _subs;

   public ExtentResultObjectProvider(ClientStoreManager store, ClassMetaData meta, boolean subs, FetchConfiguration fetch) {
      super(store, fetch);
      this._cls = meta.getDescribedType();
      this._subs = subs;
   }

   protected ResultCommand newResultCommand() {
      return new ExtentResultCommand(this.getStoreManager().getBrokerId(), this._cls, this._subs, this.getFetchConfiguration());
   }

   protected boolean getResultsOnInitialize() {
      return true;
   }
}
