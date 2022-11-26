package org.apache.openjpa.lib.meta;

import java.io.IOException;

public interface MetaDataFilter {
   boolean matches(Resource var1) throws IOException;

   public interface Resource {
      String getName();

      byte[] getContent() throws IOException;
   }
}
