package kodo.profile;

public interface ProfilingProxyStats {
   void setSize(int var1);

   void setSizeCalled();

   void incrementAccessed();

   void incrementAddCalled();

   void incrementAddCalled(int var1);

   void incrementSetCalled();

   void incrementRemoveCalled();

   void incrementRemoveCalled(int var1);

   void setIndexOfCalled();

   void setContainsCalled();

   void setClearCalled();

   void setRetainCalled();
}
