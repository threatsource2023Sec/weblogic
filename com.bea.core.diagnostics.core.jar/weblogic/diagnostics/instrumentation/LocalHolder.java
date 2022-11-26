package weblogic.diagnostics.instrumentation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public final class LocalHolder {
   public JoinPoint jp = null;
   public Object[] args = null;
   public Throwable th = null;
   public Object ret = null;
   public Object thisArg = null;
   public int monitorIndex = 0;
   public MonitorLocalHolder[] monitorHolder = null;
   public boolean argsCapture = false;
   private static CopyOnWriteArrayList auxHolders = new CopyOnWriteArrayList();

   public boolean getArgsCapture() {
      return this.argsCapture;
   }

   public Throwable getThrowable() {
      return this.th;
   }

   public Object getThisArg() {
      return this.thisArg;
   }

   public void setRet(Object ret) {
      this.ret = ret;
   }

   public void setThrowable(Throwable th) {
      this.th = th;
   }

   public void setThisArg(Object thisArg) {
      this.thisArg = thisArg;
   }

   public static LocalHolder getInstance(JoinPoint jp, DiagnosticMonitor[] monitors) {
      MonitorLocalHolder[] monitorHolders = null;
      boolean anyCapture = false;

      for(int index = 0; index < monitors.length; ++index) {
         DiagnosticMonitor mon = monitors[index];
         if (mon.isEnabledAndNotDyeFiltered()) {
            if (monitorHolders == null) {
               monitorHolders = new MonitorLocalHolder[monitors.length];
            }

            monitorHolders[index] = new MonitorLocalHolder();
            monitorHolders[index].monitor = mon;
            boolean capture = mon.isArgumentsCaptureNeeded();
            monitorHolders[index].captureArgs = capture;
            if (capture) {
               anyCapture = true;
            }

            if (mon instanceof DelegatingMonitor) {
               DelegatingMonitor delMon = (DelegatingMonitor)mon;
               monitorHolders[index].actions = delMon.getActions();
            }
         }
      }

      if (monitorHolders == null) {
         return null;
      } else {
         LocalHolder newHolder = new LocalHolder();
         newHolder.jp = jp;
         newHolder.monitorHolder = monitorHolders;
         newHolder.argsCapture = anyCapture;
         return newHolder;
      }
   }

   public static LocalHolder getInstance(int classIndex, int jpIndex) {
      try {
         AUXHolder auxHolder = (AUXHolder)auxHolders.get(classIndex);
         if (auxHolder != null && auxHolder.joinPointAuxInfos != null) {
            if (auxHolder.useLoaderRef && auxHolder.loaderRef.get() == null) {
               return null;
            } else {
               JoinPointAuxInfo jpAuxInfo = (JoinPointAuxInfo)auxHolder.joinPointAuxInfos.get(jpIndex);
               return jpAuxInfo == null ? null : getInstance(jpAuxInfo.jp, jpAuxInfo.jpMons);
            }
         } else {
            return null;
         }
      } catch (Throwable var4) {
         return null;
      }
   }

   public static int addAUXHolder(ClassLoader loader, String className) {
      if (className == null) {
         return -1;
      } else {
         AUXHolder auxHolder = new AUXHolder();
         if (loader != null) {
            auxHolder.useLoaderRef = true;
            auxHolder.loaderRef = new WeakReference(loader);
         }

         auxHolder.joinPointAuxInfos = new ArrayList();
         return !auxHolders.add(auxHolder) ? -1 : auxHolders.indexOf(auxHolder);
      }
   }

   public static int addAuxJPMons(int classIndex, JoinPointAuxInfo jpAuxInfo) {
      if (classIndex >= 0 && classIndex <= auxHolders.size() && jpAuxInfo != null) {
         AUXHolder auxHolder = (AUXHolder)auxHolders.get(classIndex);
         if (auxHolder == null) {
            return -1;
         } else {
            return !auxHolder.joinPointAuxInfos.add(jpAuxInfo) ? -1 : auxHolder.joinPointAuxInfos.indexOf(jpAuxInfo);
         }
      } else {
         return -1;
      }
   }

   public void resetPostBegin() {
      this.args = null;
   }

   public static class JoinPointAuxInfo {
      private JoinPoint jp = null;
      private DiagnosticMonitor[] jpMons = null;

      public void setJoinPoint(JoinPoint jp) {
         if (this.jp == null) {
            this.jp = jp;
         }

      }

      public void setJPMons(DiagnosticMonitor[] jpMons) {
         if (this.jpMons == null) {
            this.jpMons = jpMons;
         }

      }
   }

   private static class AUXHolder {
      private boolean useLoaderRef;
      private WeakReference loaderRef;
      private ArrayList joinPointAuxInfos;

      private AUXHolder() {
         this.useLoaderRef = false;
         this.loaderRef = null;
         this.joinPointAuxInfos = null;
      }

      // $FF: synthetic method
      AUXHolder(Object x0) {
         this();
      }
   }
}
