package kodo.profile;

import com.solarmetric.profile.ProfilingAgent;
import kodo.kernel.KodoStoreContext;
import org.apache.openjpa.meta.FieldMetaData;

public class ProfilingProxyStatsImpl implements ProfilingProxyStats {
   private KodoStoreContext _ctx;
   private String _fullFieldName;
   private ProfilingAgent _agent;
   private boolean _firstCall = true;
   private boolean _containsCalled = false;
   private boolean _sizeCalled = false;
   private boolean _sizeRecorded = false;
   private boolean _indexOfCalled = false;
   private boolean _clearCalled = false;
   private boolean _retainCalled = false;
   private int _accessed = 0;

   public ProfilingProxyStatsImpl(KodoStoreContext ctx, FieldMetaData fmd) {
      this._ctx = ctx;
      this._fullFieldName = fmd.getFullName(false);
      this._agent = ctx.getProfilingAgent();
   }

   public void setSize(int size) {
      if (!this._sizeRecorded) {
         this._sizeRecorded = true;
         int addToAccessedKnownSize = this._accessed;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 9, 0, size, addToAccessedKnownSize);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public void setSizeCalled() {
      if (!this._sizeCalled) {
         this._sizeCalled = true;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 1, 1, 0, 0);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public boolean isSizeCalled() {
      return this._sizeCalled;
   }

   public void incrementAccessed() {
      ++this._accessed;
      int addToAccessedKnownSize = 0;
      if (this._sizeRecorded) {
         addToAccessedKnownSize = 1;
      }

      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 8, 1, 0, addToAccessedKnownSize);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public int getAccessed() {
      return this._accessed;
   }

   public void incrementAddCalled() {
      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 5, 1, 0, 0);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public void incrementAddCalled(int num) {
      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 5, num, 0, 0);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public void incrementSetCalled() {
      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 6, 1, 0, 0);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public void incrementRemoveCalled() {
      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 7, 1, 0, 0);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public void incrementRemoveCalled(int num) {
      ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 7, num, 0, 0);
      this._firstCall = false;
      this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
   }

   public void setIndexOfCalled() {
      if (!this._indexOfCalled) {
         this._indexOfCalled = true;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 2, 1, 0, 0);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public void setContainsCalled() {
      if (!this._containsCalled) {
         this._containsCalled = true;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 0, 1, 0, 0);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public boolean isContainsCalled() {
      return this._containsCalled;
   }

   public void setClearCalled() {
      if (!this._clearCalled) {
         this._clearCalled = true;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 3, 1, 0, 0);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public boolean isClearCalled() {
      return this._clearCalled;
   }

   public void setRetainCalled() {
      if (!this._retainCalled) {
         this._retainCalled = true;
         ProxyUpdateInfo info = new ProxyUpdateInfo(this._fullFieldName, this._firstCall, 4, 1, 0, 0);
         this._firstCall = false;
         this._agent.handleEvent(new ProxyUpdateEvent(this._ctx, info));
      }
   }

   public boolean isRetainCalled() {
      return this._retainCalled;
   }
}
