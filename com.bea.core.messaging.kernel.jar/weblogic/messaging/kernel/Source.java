package weblogic.messaging.kernel;

import java.util.List;
import weblogic.work.WorkManager;

public interface Source {
   ReceiveRequest receive(Expression var1, int var2, boolean var3, Object var4, long var5, boolean var7, String var8) throws KernelException;

   ListenRequest listen(Expression var1, int var2, boolean var3, Object var4, Listener var5, String var6, WorkManager var7) throws KernelException;

   ListenRequest listen(Expression var1, int var2, boolean var3, Object var4, Listener var5, MultiListener var6, String var7, WorkManager var8) throws KernelException;

   KernelRequest delete(MessageElement var1) throws KernelException;

   KernelRequest acknowledge(MessageElement var1) throws KernelException;

   KernelRequest acknowledge(List var1) throws KernelException;

   void negativeAcknowledge(MessageElement var1, long var2, KernelRequest var4) throws KernelException;

   void negativeAcknowledge(List var1, long var2, KernelRequest var4) throws KernelException;

   void negativeAcknowledge(MessageElement var1, long var2, boolean var4, KernelRequest var5) throws KernelException;

   void negativeAcknowledge(List var1, long var2, boolean var4, KernelRequest var5) throws KernelException;

   void negativeAcknowledge(MessageElement var1, long var2, boolean var4, String var5, KernelRequest var6) throws KernelException;

   void negativeAcknowledge(List var1, long var2, boolean var4, String var5, KernelRequest var6) throws KernelException;

   void associate(MessageElement var1, RedeliveryParameters var2) throws KernelException;

   void associate(List var1, RedeliveryParameters var2) throws KernelException;
}
