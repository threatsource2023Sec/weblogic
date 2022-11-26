package weblogic.management.configuration;

public interface Http2ConfigMBean extends ConfigurationMBean {
   int DEFAULT_HEADER_TABLE_SIZE = 4096;
   int DEFAULT_MAX_CONCURRENT_STREAMS = 300;
   int DEFAULT_INITIAL_WINDOW_SIZE = 65535;
   int DEFAULT_MAX_FRAME_SIZE = 16384;
   int DEFAULT_MAX_HEADER_LIST_SIZE = Integer.MAX_VALUE;

   int getHeaderTableSize();

   void setHeaderTableSize(int var1);

   int getMaxConcurrentStreams();

   void setMaxConcurrentStreams(int var1);

   int getInitialWindowSize();

   void setInitialWindowSize(int var1);

   int getMaxFrameSize();

   void setMaxFrameSize(int var1);

   int getMaxHeaderListSize();

   void setMaxHeaderListSize(int var1);
}
