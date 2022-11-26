package oracle.jrockit.jfr.openmbean;

import java.util.concurrent.TimeUnit;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.RecordingOptions;
import oracle.jrockit.jfr.RecordingOptionsImpl;

public class RecordingOptionsType extends JFRMBeanType {
   static final Member STARTTIME;
   static final Member DURATION;
   static final Member MAXSIZE;
   static final Member MAXAGE;
   static final Member DESTFILE;
   static final Member COMPRESS;
   static final Member TODISK;

   public RecordingOptionsType() throws OpenDataException {
      super("RecordingOptions", "Recording Options", STARTTIME, DURATION, MAXSIZE, MAXAGE, DESTFILE, COMPRESS, TODISK);
   }

   public CompositeData toCompositeTypeData(RecordingOptions t) throws OpenDataException {
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getStartTime(), t.getDuration(TimeUnit.MILLISECONDS), t.getMaxSize(), t.getMaxAge(TimeUnit.MILLISECONDS), t.getDestination(), t.isDestinationCompressed(), t.isToDisk()});
   }

   public RecordingOptions toJavaTypeData(CompositeData d) throws OpenDataException {
      try {
         return new RecordingOptionsImpl(this.stringAt(d, DESTFILE), this.booleanAt(d, COMPRESS), this.dateAt(d, STARTTIME), this.longAt(d, DURATION), this.longAt(d, MAXSIZE), this.longAt(d, MAXAGE), this.booleanAt(d, TODISK));
      } catch (Exception var3) {
         throw this.openDataException(var3);
      }
   }

   static {
      STARTTIME = new Member("startTime", "Start time of Recording", SimpleType.DATE);
      DURATION = new Member("duration", "Duration of recording", SimpleType.LONG);
      MAXSIZE = new Member("maxSize", "Maximum size (bytes)", SimpleType.LONG);
      MAXAGE = new Member("maxAge", "Maximum age (milliseconds)", SimpleType.LONG);
      DESTFILE = new Member("destinationFile", "Target file for resulting recording", SimpleType.STRING);
      COMPRESS = new Member("destinationCompressed", "Should destination file be compressed", SimpleType.BOOLEAN);
      TODISK = new Member("toDisk", "Record to disk (non-volatile)", SimpleType.BOOLEAN);
   }
}
