package weblogic.management.mbeanservers.edit;

import java.util.Map;
import weblogic.management.mbeanservers.Service;

public interface RecordingManagerMBean extends Service {
   String OBJECT_NAME = "com.bea:Name=RecordingManager,Type=" + RecordingManagerMBean.class.getName();

   void startRecording(String var1, boolean var2) throws RecordingException;

   void startRecording(String var1, Map var2) throws RecordingException;

   void stopRecording() throws RecordingException;

   boolean isRecording();

   String getRecordingFileName();

   void record(String var1) throws RecordingException;

   void releaseEditAccess();
}
