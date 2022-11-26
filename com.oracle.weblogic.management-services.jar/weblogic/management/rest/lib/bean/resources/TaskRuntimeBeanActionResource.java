package weblogic.management.rest.lib.bean.resources;

import weblogic.management.rest.lib.bean.utils.AsyncOperationHelper;
import weblogic.management.rest.lib.bean.utils.TaskRuntimeAsyncOperationHelper;

public class TaskRuntimeBeanActionResource extends AbstractBeanAsyncActionResource {
   protected AsyncOperationHelper createAsyncOperationHelper() throws Exception {
      return new TaskRuntimeAsyncOperationHelper();
   }
}
