package kodo.conf.descriptor;

public interface DetachOptionsAllBean extends DetachStateBean {
   boolean getDetachedStateManager();

   void setDetachedStateManager(boolean var1);

   boolean getDetachedStateTransient();

   void setDetachedStateTransient(boolean var1);

   boolean getAccessUnloaded();

   void setAccessUnloaded(boolean var1);

   boolean getDetachedStateField();

   void setDetachedStateField(boolean var1);
}
