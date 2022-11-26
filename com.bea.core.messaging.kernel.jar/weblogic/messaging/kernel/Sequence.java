package weblogic.messaging.kernel;

import java.util.List;

public interface Sequence {
   String getName();

   int getMode();

   void setPassthru(boolean var1);

   boolean isPassthru();

   void setOverride(boolean var1);

   boolean isOverride();

   long getLastValue();

   void setLastValue(long var1) throws KernelException;

   long getLastAssignedValue();

   Object getUserData();

   void setUserData(Object var1) throws KernelException;

   KernelRequest delete(boolean var1) throws KernelException;

   List getAllSequenceNumberRanges();
}
