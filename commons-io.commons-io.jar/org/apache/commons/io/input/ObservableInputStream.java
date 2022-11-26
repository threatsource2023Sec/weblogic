package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObservableInputStream extends ProxyInputStream {
   private final List observers = new ArrayList();

   public ObservableInputStream(InputStream pProxy) {
      super(pProxy);
   }

   public void add(Observer pObserver) {
      this.observers.add(pObserver);
   }

   public void remove(Observer pObserver) {
      this.observers.remove(pObserver);
   }

   public void removeAllObservers() {
      this.observers.clear();
   }

   public int read() throws IOException {
      int result = 0;
      IOException ioe = null;

      try {
         result = super.read();
      } catch (IOException var4) {
         ioe = var4;
      }

      if (ioe != null) {
         this.noteError(ioe);
      } else if (result == -1) {
         this.noteFinished();
      } else {
         this.noteDataByte(result);
      }

      return result;
   }

   public int read(byte[] pBuffer) throws IOException {
      int result = 0;
      IOException ioe = null;

      try {
         result = super.read(pBuffer);
      } catch (IOException var5) {
         ioe = var5;
      }

      if (ioe != null) {
         this.noteError(ioe);
      } else if (result == -1) {
         this.noteFinished();
      } else if (result > 0) {
         this.noteDataBytes(pBuffer, 0, result);
      }

      return result;
   }

   public int read(byte[] pBuffer, int pOffset, int pLength) throws IOException {
      int result = 0;
      IOException ioe = null;

      try {
         result = super.read(pBuffer, pOffset, pLength);
      } catch (IOException var7) {
         ioe = var7;
      }

      if (ioe != null) {
         this.noteError(ioe);
      } else if (result == -1) {
         this.noteFinished();
      } else if (result > 0) {
         this.noteDataBytes(pBuffer, pOffset, result);
      }

      return result;
   }

   protected void noteDataBytes(byte[] pBuffer, int pOffset, int pLength) throws IOException {
      Iterator var4 = this.getObservers().iterator();

      while(var4.hasNext()) {
         Observer observer = (Observer)var4.next();
         observer.data(pBuffer, pOffset, pLength);
      }

   }

   protected void noteFinished() throws IOException {
      Iterator var1 = this.getObservers().iterator();

      while(var1.hasNext()) {
         Observer observer = (Observer)var1.next();
         observer.finished();
      }

   }

   protected void noteDataByte(int pDataByte) throws IOException {
      Iterator var2 = this.getObservers().iterator();

      while(var2.hasNext()) {
         Observer observer = (Observer)var2.next();
         observer.data(pDataByte);
      }

   }

   protected void noteError(IOException pException) throws IOException {
      Iterator var2 = this.getObservers().iterator();

      while(var2.hasNext()) {
         Observer observer = (Observer)var2.next();
         observer.error(pException);
      }

   }

   protected void noteClosed() throws IOException {
      Iterator var1 = this.getObservers().iterator();

      while(var1.hasNext()) {
         Observer observer = (Observer)var1.next();
         observer.closed();
      }

   }

   protected List getObservers() {
      return this.observers;
   }

   public void close() throws IOException {
      IOException ioe = null;

      try {
         super.close();
      } catch (IOException var3) {
         ioe = var3;
      }

      if (ioe == null) {
         this.noteClosed();
      } else {
         this.noteError(ioe);
      }

   }

   public void consume() throws IOException {
      byte[] buffer = new byte[8192];

      int res;
      do {
         res = this.read(buffer);
      } while(res != -1);

   }

   public abstract static class Observer {
      void data(int pByte) throws IOException {
      }

      void data(byte[] pBuffer, int pOffset, int pLength) throws IOException {
      }

      void finished() throws IOException {
      }

      void closed() throws IOException {
      }

      void error(IOException pException) throws IOException {
         throw pException;
      }
   }
}
