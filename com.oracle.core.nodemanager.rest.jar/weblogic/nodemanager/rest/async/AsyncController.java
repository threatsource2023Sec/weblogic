package weblogic.nodemanager.rest.async;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.nodemanager.rest.utils.CommonUtils;

public class AsyncController {
   private static final String JOB_NAME_SEPERATOR = "_";
   public static final long JOB_ENTRY_EXPIRY_TIME_IN_MINUTES = 30L;
   private static ExecutorService exeService = Executors.newCachedThreadPool();
   private static Cache taskMap;
   private static AtomicInteger idCounter;

   public static AsyncJob executeTask(Task task) {
      return executeTask(task, -1L);
   }

   public static AsyncJob executeTask(Task task, long waitTime) {
      String domainName = task.getDomainName();
      CommonUtils.ComponentType compType = task.getComponentType();
      CommonUtils.OperationType operationType = task.getOperationType();
      String id = getShortJobId(operationType.toString());
      TaskWrapper taskWrapper = new TaskWrapper(task);
      Future fTask = exeService.submit(taskWrapper);
      AsyncJob asyncJob = new AsyncJob(id, fTask, taskWrapper);
      addJob(asyncJob);
      if (waitTime > 0L) {
         try {
            fTask.get(waitTime, TimeUnit.SECONDS);
         } catch (TimeoutException var11) {
         } catch (InterruptedException var12) {
         } catch (ExecutionException var13) {
         }
      }

      return asyncJob;
   }

   private static String getShortJobId(String operationType) {
      StringBuilder sb = (new StringBuilder()).append("_").append(idCounter.incrementAndGet()).append("_").append(operationType.toLowerCase());
      return sb.toString();
   }

   private static String getFullJobId(String domainName, String compType, String compName, String shortJodId) {
      StringBuilder sb = (new StringBuilder()).append(domainName).append("_").append(compType).append("_").append(compName).append(shortJodId);
      return sb.toString();
   }

   private static void addJob(AsyncJob asyncJob) {
      String fullJobId = getFullJobId(asyncJob.getDomainName(), asyncJob.getComponentType().toString(), asyncJob.getComponentName(), asyncJob.getId());
      taskMap.put(fullJobId, asyncJob);
   }

   public static AsyncJob getJob(String id, String domainName, CommonUtils.ComponentType componentType, String componentName) {
      String fullJobId = getFullJobId(domainName, componentType.toString(), componentName, id);
      AsyncJob job = (AsyncJob)taskMap.getIfPresent(fullJobId);
      return job;
   }

   public static List getJobs(String domainName, CommonUtils.ComponentType componentType, String componentName) {
      List jobs = new ArrayList();
      Iterator var4 = taskMap.asMap().values().iterator();

      while(var4.hasNext()) {
         AsyncJob job = (AsyncJob)var4.next();
         if (job.getDomainName().equals(domainName) && job.getComponentType() == componentType && job.getComponentName().equals(componentName)) {
            jobs.add(job);
         }
      }

      return jobs;
   }

   static {
      taskMap = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();
      idCounter = new AtomicInteger();
   }
}
