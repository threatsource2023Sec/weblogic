package weblogic.application.utils;

import java.io.IOException;
import java.util.List;

public interface MetaDataFilter {
   List matches(Resource var1) throws IOException;

   public interface Resource {
      String getName();

      byte[] getContent() throws IOException;
   }
}
