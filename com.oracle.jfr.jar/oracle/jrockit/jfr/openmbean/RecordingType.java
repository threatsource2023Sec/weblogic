package oracle.jrockit.jfr.openmbean;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.Recording;
import oracle.jrockit.jfr.RecordingOptions;

public class RecordingType extends JFRMBeanType {
   private final RecordingOptionsType optionsType;

   public RecordingType(RecordingOptionsType optionsType) throws OpenDataException {
      super("Recording", "An active recording", new String[]{"id", "name", "objectName", "dataStartTime", "dataEndTime", "started", "stopped", "running", "options"}, new String[]{"Recording id", "Object name of recording in MBean server", "Recording name", "Start time of available data in recording", "End time of available data in recording", "Is the recording started?", "Is the recording stopped?", "Is the recording running?", "Recording options"}, new OpenType[]{SimpleType.LONG, SimpleType.STRING, SimpleType.OBJECTNAME, SimpleType.DATE, SimpleType.DATE, SimpleType.BOOLEAN, SimpleType.BOOLEAN, SimpleType.BOOLEAN, optionsType.getType()});
      this.optionsType = optionsType;
   }

   public CompositeData toCompositeTypeData(Recording t) throws OpenDataException {
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getId(), t.getName(), t.getObjectName(), t.getDataStartTime(), t.getDataEndTime(), t.isStarted(), t.isStopped(), t.isRunning(), this.optionsType.toCompositeTypeData((RecordingOptions)t)});
   }
}
