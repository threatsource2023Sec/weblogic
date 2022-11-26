package org.apache.openjpa.kernel;

import org.apache.openjpa.lib.rop.ResultObjectProvider;

public interface PCResultObjectProvider extends ResultObjectProvider {
   void initialize(OpenJPAStateManager var1, PCState var2, FetchConfiguration var3) throws Exception;
}
