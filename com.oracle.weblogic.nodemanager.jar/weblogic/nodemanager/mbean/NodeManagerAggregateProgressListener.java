package weblogic.nodemanager.mbean;

import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import weblogic.nodemanager.util.ProgressData;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.client.AggregateProgressBean;
import weblogic.utils.progress.client.AggregateProgressListener;
import weblogic.utils.progress.client.ProgressBean;

@Singleton
public class NodeManagerAggregateProgressListener implements AggregateProgressListener {
   @Inject
   private ProgressTrackerRegistrar registrar;
   private transient ProgressData progressData;

   public void progress(AggregateProgressBean aggregateBean, ProgressBean changedSubsystem) {
      if (this.progressData != null) {
         String currentProgress = aggregateBean.getXmlVersionOfAggregateState(new String[0]);

         try {
            this.progressData.write(currentProgress);
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }
      }
   }

   public void initialize(File progressFile, String domainName, String serverName) throws IOException {
      AggregateProgressBean apb = this.registrar.getAggregateProgress();
      apb.setDomainName(domainName);
      apb.setServerName(serverName);
      apb.setServerDisposition("__**FILL**__");
      this.progressData = new ProgressData(progressFile);
      this.progress(this.registrar.getAggregateProgress(), (ProgressBean)null);
   }

   public void close() {
      this.progressData.close();
   }
}
