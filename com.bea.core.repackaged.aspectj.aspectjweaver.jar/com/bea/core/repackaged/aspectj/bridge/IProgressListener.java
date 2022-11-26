package com.bea.core.repackaged.aspectj.bridge;

public interface IProgressListener {
   void setText(String var1);

   void setProgress(double var1);

   void setCancelledRequested(boolean var1);

   boolean isCancelledRequested();
}
