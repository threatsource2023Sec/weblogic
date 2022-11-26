package org.apache.velocity.app.event;

public interface MethodExceptionEventHandler extends EventHandler {
   Object methodException(Class var1, String var2, Exception var3) throws Exception;
}
