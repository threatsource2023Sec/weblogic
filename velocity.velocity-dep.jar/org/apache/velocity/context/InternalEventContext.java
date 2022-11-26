package org.apache.velocity.context;

import org.apache.velocity.app.event.EventCartridge;

public interface InternalEventContext {
   EventCartridge attachEventCartridge(EventCartridge var1);

   EventCartridge getEventCartridge();
}
