package com.bea.core.repackaged.aspectj.weaver;

public interface IWeaveRequestor {
   void acceptResult(IUnwovenClassFile var1);

   void processingReweavableState();

   void addingTypeMungers();

   void weavingAspects();

   void weavingClasses();

   void weaveCompleted();
}
