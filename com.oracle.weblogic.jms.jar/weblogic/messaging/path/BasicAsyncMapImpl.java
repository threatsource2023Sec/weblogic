package weblogic.messaging.path;

import java.util.Map;
import weblogic.common.CompletionRequest;

class BasicAsyncMapImpl implements AsyncMap {
   private Map map;

   BasicAsyncMapImpl(Map map) {
      this.map = map;
   }

   public void putIfAbsent(Object key, Object value, CompletionRequest completionRequest) {
      Object returnValue;
      try {
         synchronized(this.map) {
            if (!this.map.containsKey(key)) {
               returnValue = this.map.put(key, value);
            } else {
               returnValue = this.map.get(key);
            }
         }
      } catch (RuntimeException var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      } catch (Error var9) {
         notifyCaller(completionRequest, var9);
         throw var9;
      }

      completionRequest.setResult(returnValue);
   }

   private static void notifyCaller(CompletionRequest completionRequest, Throwable throwable) {
      synchronized(completionRequest) {
         if (completionRequest.hasResult()) {
            return;
         }
      }

      completionRequest.setResult(throwable);
   }

   public void put(Object key, Object value, CompletionRequest completionRequest) {
      Object returnValue;
      try {
         synchronized(this.map) {
            returnValue = this.map.put(key, value);
         }
      } catch (RuntimeException var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      } catch (Error var9) {
         notifyCaller(completionRequest, var9);
         throw var9;
      }

      completionRequest.setResult(returnValue);
   }

   public void get(Object key, CompletionRequest completionRequest) {
      Object returnValue;
      try {
         synchronized(this.map) {
            returnValue = this.map.get(key);
         }
      } catch (RuntimeException var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      } catch (Error var8) {
         notifyCaller(completionRequest, var8);
         throw var8;
      }

      completionRequest.setResult(returnValue);
   }

   public void remove(Object key, Object value, CompletionRequest completionRequest) {
      Boolean returnValue;
      try {
         synchronized(this.map) {
            Object mapValue = this.map.get(key);
            if (mapValue == value || mapValue != null && mapValue.equals(value)) {
               this.map.remove(key);
               returnValue = Boolean.TRUE;
            } else {
               returnValue = Boolean.FALSE;
            }
         }
      } catch (RuntimeException var9) {
         notifyCaller(completionRequest, var9);
         throw var9;
      } catch (Error var10) {
         notifyCaller(completionRequest, var10);
         throw var10;
      }

      completionRequest.setResult(returnValue);
   }

   public void close(CompletionRequest completionRequest) {
      Boolean returnValue;
      try {
         synchronized(this.map) {
            this.map.clear();
         }

         returnValue = Boolean.TRUE;
      } catch (RuntimeException var6) {
         notifyCaller(completionRequest, var6);
         throw var6;
      } catch (Error var7) {
         notifyCaller(completionRequest, var7);
         throw var7;
      }

      completionRequest.setResult(returnValue);
   }
}
