package com.solarmetric.profile;

import java.io.Serializable;

public class ProfilingEvent implements Serializable {
   private static final long serialVersionUID = 1L;
   private int _threadHash;
   private int _peHash;
   private long _time;

   private ProfilingEvent() {
   }

   public ProfilingEvent(ProfilingEnvironment pe) {
      this._time = System.currentTimeMillis();
      this._threadHash = Thread.currentThread().hashCode();
      this._peHash = pe.hashCode();
   }

   public int getThreadHash() {
      return this._threadHash;
   }

   public int getProfilingEnvironmentHash() {
      return this._peHash;
   }

   public long getTime() {
      return this._time;
   }
}
