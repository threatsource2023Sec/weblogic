package org.apache.openjpa.meta;

import java.io.File;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.SeqValue;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.OpenJPAException;

public class SequenceMetaData implements SourceTracker, MetaDataContext, Closeable, Commentable, Serializable {
   public static final String NAME_SYSTEM = "system";
   public static final String IMPL_NATIVE = "native";
   public static final String IMPL_TIME = "time";
   private static final String PROP_SEQUENCE = "Sequence";
   private static final String PROP_INITIAL_VALUE = "InitialValue";
   private static final String PROP_ALLOCATE = "Allocate";
   private static final String PROP_INCREMENT = "Increment";
   private static final Localizer _loc = Localizer.forPackage(SequenceMetaData.class);
   private MetaDataRepository _repos;
   private SequenceFactory _factory = null;
   private final String _name;
   private int _type = 0;
   private String _plugin = "native";
   private File _source = null;
   private Object _scope = null;
   private int _srcType = 0;
   private String[] _comments = null;
   private String _sequence = null;
   private int _increment = -1;
   private int _allocate = -1;
   private int _initial = -1;
   private transient Seq _instance = null;

   public SequenceMetaData(String name, MetaDataRepository repos) {
      this._name = name;
      this._repos = repos;
   }

   public MetaDataRepository getRepository() {
      return this._repos;
   }

   public String getName() {
      return this._name;
   }

   public File getSourceFile() {
      return this._source;
   }

   public Object getSourceScope() {
      return this._scope;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File file, Object scope, int srcType) {
      this._source = file;
      this._scope = scope;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this._name;
   }

   public int getType() {
      return this._type;
   }

   public void setType(int type) {
      this._type = type;
   }

   public String getSequence() {
      return this._sequence;
   }

   public void setSequence(String sequence) {
      this._sequence = sequence;
   }

   public int getIncrement() {
      return this._increment;
   }

   public void setIncrement(int increment) {
      this._increment = increment;
   }

   public int getAllocate() {
      return this._allocate;
   }

   public void setAllocate(int allocate) {
      this._allocate = allocate;
   }

   public int getInitialValue() {
      return this._initial;
   }

   public void setInitialValue(int initial) {
      this._initial = initial;
   }

   public String getSequencePlugin() {
      return this._plugin;
   }

   public void setSequencePlugin(String plugin) {
      this._plugin = plugin;
   }

   public SequenceFactory getSequenceFactory() {
      return this._factory;
   }

   public void setSequenceFactory(SequenceFactory factory) {
      this._factory = factory;
   }

   public synchronized Seq getInstance(ClassLoader envLoader) {
      if (this._instance == null) {
         this._instance = this.instantiate(envLoader);
      }

      return this._instance;
   }

   protected Seq instantiate(ClassLoader envLoader) {
      if ("system".equals(this._name)) {
         return this._repos.getConfiguration().getSequenceInstance();
      } else {
         try {
            PluginValue plugin = this.newPluginValue("sequence-plugin");
            plugin.setString(this._plugin);
            String clsName = plugin.getClassName();
            Class cls = Class.forName(clsName, true, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(Seq.class)));
            StringBuffer props = new StringBuffer();
            if (plugin.getProperties() != null) {
               props.append(plugin.getProperties());
            }

            this.addStandardProperties(props);
            Seq seq;
            if (Seq.class.isAssignableFrom(cls)) {
               seq = (Seq)AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(cls));
               Configurations.configureInstance(seq, this._repos.getConfiguration(), (String)props.toString());
               seq.setType(this._type);
            } else {
               if (this._factory == null) {
                  throw new MetaDataException(_loc.get("not-seq-cls", this._name, cls));
               }

               seq = this._factory.toSequence(cls, props.toString());
            }

            return seq;
         } catch (OpenJPAException var7) {
            throw var7;
         } catch (Exception var8) {
            Exception e = var8;
            if (var8 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var8).getException();
            }

            throw (new MetaDataException(_loc.get("cant-init-seq", (Object)this._name))).setCause(e);
         }
      }
   }

   protected PluginValue newPluginValue(String property) {
      return new SeqValue(property);
   }

   protected void addStandardProperties(StringBuffer props) {
      this.appendProperty(props, "Sequence", this._sequence);
      this.appendProperty(props, "InitialValue", this._initial);
      this.appendProperty(props, "Allocate", this._allocate);
      this.appendProperty(props, "Increment", this._increment);
   }

   protected void appendProperty(StringBuffer props, String name, String val) {
      if (!StringUtils.isEmpty(val)) {
         if (props.length() > 0) {
            props.append(",");
         }

         props.append(name).append("=").append(val);
      }
   }

   protected void appendProperty(StringBuffer props, String name, int val) {
      if (val != -1) {
         if (props.length() > 0) {
            props.append(",");
         }

         props.append(name).append("=").append(val);
      }
   }

   public void close() {
      if (this._instance != null && !"system".equals(this._name)) {
         try {
            this._instance.close();
         } catch (Exception var2) {
         }
      }

   }

   public String toString() {
      return this._name;
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }

   public interface SequenceFactory extends Serializable {
      Seq toSequence(Class var1, String var2) throws Exception;
   }
}
