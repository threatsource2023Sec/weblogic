package org.apache.openjpa.kernel;

import java.util.BitSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.StoreException;

public abstract class AbstractPCResultObjectProvider implements PCResultObjectProvider {
   protected final StoreContext ctx;

   public AbstractPCResultObjectProvider(StoreContext ctx) {
      this.ctx = ctx;
   }

   public StoreContext getContext() {
      return this.ctx;
   }

   public void initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch) throws Exception {
      sm.initialize(this.getPCType(), state);
      this.load(sm, fetch);
   }

   public Object getResultObject() throws Exception {
      Class type = this.getPCType();
      MetaDataRepository repos = this.ctx.getConfiguration().getMetaDataRepositoryInstance();
      ClassMetaData meta = repos.getMetaData(type, this.ctx.getClassLoader(), true);
      Object oid = this.getObjectId(meta);
      Object res = this.ctx.find(oid, (FetchConfiguration)null, (BitSet)null, this, 0);
      if (res == null) {
         throw new ObjectNotFoundException(oid);
      } else {
         return res;
      }
   }

   protected abstract Object getObjectId(ClassMetaData var1) throws Exception;

   protected abstract Class getPCType() throws Exception;

   protected abstract void load(OpenJPAStateManager var1, FetchConfiguration var2) throws Exception;

   public void open() throws Exception {
   }

   public boolean supportsRandomAccess() {
      return false;
   }

   public abstract boolean next() throws Exception;

   public boolean absolute(int pos) throws Exception {
      throw new UnsupportedOperationException();
   }

   public int size() throws Exception {
      return Integer.MAX_VALUE;
   }

   public void reset() throws Exception {
      throw new UnsupportedOperationException();
   }

   public void close() throws Exception {
   }

   public void handleCheckedException(Exception e) {
      throw new StoreException(e);
   }
}
