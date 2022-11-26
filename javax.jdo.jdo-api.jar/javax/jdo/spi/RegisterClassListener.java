package javax.jdo.spi;

import java.util.EventListener;

public interface RegisterClassListener extends EventListener {
   void registerClass(RegisterClassEvent var1);
}
