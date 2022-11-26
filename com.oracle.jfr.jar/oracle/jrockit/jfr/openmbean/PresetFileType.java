package oracle.jrockit.jfr.openmbean;

import java.util.List;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import oracle.jrockit.jfr.PresetFile;

public class PresetFileType extends JFRMBeanType {
   private final EventDefaultType eventDefaultType;

   public PresetFileType(EventDefaultType eventDefaultType) throws OpenDataException {
      super("Preset", "Flight Recorder Preset", new String[]{"name", "description", "settings"}, new String[]{"Name", "Description", "Settings"}, new OpenType[]{SimpleType.STRING, SimpleType.STRING, new ArrayType(1, eventDefaultType.getType())});
      this.eventDefaultType = eventDefaultType;
   }

   public CompositeData toCompositeTypeData(PresetFile t) throws OpenDataException {
      List l = this.eventDefaultType.toCompositeData(t.getSettings().getAll());
      CompositeData[] array = (CompositeData[])l.toArray(new CompositeData[l.size()]);
      return new CompositeDataSupport(this.getType(), this.getNames(), new Object[]{t.getName(), t.getDescription(), array});
   }
}
