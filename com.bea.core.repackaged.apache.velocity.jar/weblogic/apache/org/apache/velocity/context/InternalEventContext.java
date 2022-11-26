package weblogic.apache.org.apache.velocity.context;

import weblogic.apache.org.apache.velocity.app.event.EventCartridge;

public interface InternalEventContext {
   EventCartridge attachEventCartridge(EventCartridge var1);

   EventCartridge getEventCartridge();
}
