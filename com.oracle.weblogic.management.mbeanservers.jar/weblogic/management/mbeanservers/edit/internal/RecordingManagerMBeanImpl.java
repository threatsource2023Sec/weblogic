package weblogic.management.mbeanservers.edit.internal;

import java.util.Map;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.RecordingException;
import weblogic.management.mbeanservers.edit.RecordingManagerMBean;
import weblogic.management.mbeanservers.internal.RecordingManager;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.EditAccess;

public class RecordingManagerMBeanImpl extends ServiceImpl implements RecordingManagerMBean {
   private volatile EditAccess editAccess;

   RecordingManagerMBeanImpl(EditAccess editAccess) {
      super("RecordingManager", RecordingManagerMBean.class.getName(), (Service)null);
      this.editAccess = editAccess;
   }

   public void startRecording(String fileName, boolean append) throws RecordingException {
      RecordingManager.getInstance(this.editAccess).startRecording(fileName, append);
   }

   public void startRecording(String fileName, Map options) throws RecordingException {
      RecordingManager.getInstance(this.editAccess).startRecording(fileName, options);
   }

   public void stopRecording() throws RecordingException {
      RecordingManager.getInstance(this.editAccess).stopRecording();
   }

   public boolean isRecording() {
      return RecordingManager.getInstance(this.editAccess).isRecording();
   }

   public String getRecordingFileName() {
      return RecordingManager.getInstance(this.editAccess).getRecordingFileName();
   }

   public void record(String str) throws RecordingException {
      RecordingManager.getInstance(this.editAccess).record(str);
   }

   public void releaseEditAccess() {
      this.editAccess = null;
   }
}
