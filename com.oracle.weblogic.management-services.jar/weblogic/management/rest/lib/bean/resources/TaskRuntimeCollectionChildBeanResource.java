package weblogic.management.rest.lib.bean.resources;

import weblogic.management.rest.lib.bean.utils.AsyncJobHelper;
import weblogic.management.rest.lib.bean.utils.TaskRuntimeAsyncJobHelper;

public class TaskRuntimeCollectionChildBeanResource extends AbstractJobCollectionChildBeanResource {
   protected AsyncJobHelper createAsyncJobHelper() throws Exception {
      return new TaskRuntimeAsyncJobHelper();
   }
}
