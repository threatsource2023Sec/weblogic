package weblogic.servlet.http2;

import weblogic.management.configuration.Http2ConfigMBean;

public class RemoteSettings extends DefaultSettings {
   public RemoteSettings(Http2ConfigMBean defaultSettings) {
      this.current.put(1, (long)defaultSettings.getHeaderTableSize());
      this.current.put(2, 1L);
      this.current.put(3, (long)defaultSettings.getMaxConcurrentStreams());
      this.current.put(4, (long)defaultSettings.getInitialWindowSize());
      this.current.put(5, (long)defaultSettings.getMaxFrameSize());
      this.current.put(6, (long)defaultSettings.getMaxHeaderListSize());
   }

   void throwException(String msg, int errorCode) throws ConnectionException {
      throw new ConnectionException(msg, errorCode);
   }
}
