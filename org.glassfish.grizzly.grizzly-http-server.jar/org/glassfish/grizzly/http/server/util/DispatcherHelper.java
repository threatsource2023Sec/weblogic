package org.glassfish.grizzly.http.server.util;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.DataChunk;

public interface DispatcherHelper {
   void mapPath(HttpRequestPacket var1, DataChunk var2, MappingData var3) throws Exception;

   void mapName(DataChunk var1, MappingData var2) throws Exception;
}
