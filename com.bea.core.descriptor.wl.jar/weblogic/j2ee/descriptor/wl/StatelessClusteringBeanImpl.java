package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class StatelessClusteringBeanImpl extends AbstractDescriptorBean implements StatelessClusteringBean, Serializable {
   private String _HomeCallRouterClassName;
   private boolean _HomeIsClusterable;
   private String _HomeLoadAlgorithm;
   private String _Id;
   private String _StatelessBeanCallRouterClassName;
   private boolean _StatelessBeanIsClusterable;
   private String _StatelessBeanLoadAlgorithm;
   private boolean _UseServersideStubs;
   private static SchemaHelper2 _schemaHelper;

   public StatelessClusteringBeanImpl() {
      this._initializeProperty(-1);
   }

   public StatelessClusteringBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StatelessClusteringBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isHomeIsClusterable() {
      return this._HomeIsClusterable;
   }

   public boolean isHomeIsClusterableInherited() {
      return false;
   }

   public boolean isHomeIsClusterableSet() {
      return this._isSet(0);
   }

   public void setHomeIsClusterable(boolean param0) {
      boolean _oldVal = this._HomeIsClusterable;
      this._HomeIsClusterable = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getHomeLoadAlgorithm() {
      return !this._isSet(1) ? null : this._HomeLoadAlgorithm;
   }

   public boolean isHomeLoadAlgorithmInherited() {
      return false;
   }

   public boolean isHomeLoadAlgorithmSet() {
      return this._isSet(1);
   }

   public void setHomeLoadAlgorithm(String param0) {
      if (param0 == null) {
         this._unSet(1);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"RoundRobin", "Random", "WeightBased", "RoundRobinAffinity", "RandomAffinity", "WeightBasedAffinity", "roundrobin", "round-robin", "random", "weightbased", "weight-based", "round-robin-affinity", "random-affinity", "weight-based-affinity"};
         param0 = LegalChecks.checkInEnum("HomeLoadAlgorithm", param0, _set);
         String _oldVal = this._HomeLoadAlgorithm;
         this._HomeLoadAlgorithm = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public String getHomeCallRouterClassName() {
      return this._HomeCallRouterClassName;
   }

   public boolean isHomeCallRouterClassNameInherited() {
      return false;
   }

   public boolean isHomeCallRouterClassNameSet() {
      return this._isSet(2);
   }

   public void setHomeCallRouterClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HomeCallRouterClassName;
      this._HomeCallRouterClassName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isUseServersideStubs() {
      return this._UseServersideStubs;
   }

   public boolean isUseServersideStubsInherited() {
      return false;
   }

   public boolean isUseServersideStubsSet() {
      return this._isSet(3);
   }

   public void setUseServersideStubs(boolean param0) {
      boolean _oldVal = this._UseServersideStubs;
      this._UseServersideStubs = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isStatelessBeanIsClusterable() {
      return this._StatelessBeanIsClusterable;
   }

   public boolean isStatelessBeanIsClusterableInherited() {
      return false;
   }

   public boolean isStatelessBeanIsClusterableSet() {
      return this._isSet(4);
   }

   public void setStatelessBeanIsClusterable(boolean param0) {
      boolean _oldVal = this._StatelessBeanIsClusterable;
      this._StatelessBeanIsClusterable = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getStatelessBeanLoadAlgorithm() {
      return !this._isSet(5) ? null : this._StatelessBeanLoadAlgorithm;
   }

   public boolean isStatelessBeanLoadAlgorithmInherited() {
      return false;
   }

   public boolean isStatelessBeanLoadAlgorithmSet() {
      return this._isSet(5);
   }

   public void setStatelessBeanLoadAlgorithm(String param0) {
      if (param0 == null) {
         this._unSet(5);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"RoundRobin", "Random", "WeightBased", "RoundRobinAffinity", "RandomAffinity", "WeightBasedAffinity", "roundrobin", "round-robin", "random", "weightbased", "weight-based", "round-robin-affinity", "random-affinity", "weight-based-affinity"};
         param0 = LegalChecks.checkInEnum("StatelessBeanLoadAlgorithm", param0, _set);
         String _oldVal = this._StatelessBeanLoadAlgorithm;
         this._StatelessBeanLoadAlgorithm = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public String getStatelessBeanCallRouterClassName() {
      return this._StatelessBeanCallRouterClassName;
   }

   public boolean isStatelessBeanCallRouterClassNameInherited() {
      return false;
   }

   public boolean isStatelessBeanCallRouterClassNameSet() {
      return this._isSet(6);
   }

   public void setStatelessBeanCallRouterClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._StatelessBeanCallRouterClassName;
      this._StatelessBeanCallRouterClassName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._HomeCallRouterClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._HomeLoadAlgorithm = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._StatelessBeanCallRouterClassName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._StatelessBeanLoadAlgorithm = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._HomeIsClusterable = true;
               if (initOne) {
                  break;
               }
            case 4:
               this._StatelessBeanIsClusterable = true;
               if (initOne) {
                  break;
               }
            case 3:
               this._UseServersideStubs = false;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
               break;
            case 19:
               if (s.equals("home-load-algorithm")) {
                  return 1;
               }

               if (s.equals("home-is-clusterable")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("use-serverside-stubs")) {
                  return 3;
               }
               break;
            case 27:
               if (s.equals("home-call-router-class-name")) {
                  return 2;
               }
               break;
            case 29:
               if (s.equals("stateless-bean-load-algorithm")) {
                  return 5;
               }

               if (s.equals("stateless-bean-is-clusterable")) {
                  return 4;
               }
               break;
            case 37:
               if (s.equals("stateless-bean-call-router-class-name")) {
                  return 6;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "home-is-clusterable";
            case 1:
               return "home-load-algorithm";
            case 2:
               return "home-call-router-class-name";
            case 3:
               return "use-serverside-stubs";
            case 4:
               return "stateless-bean-is-clusterable";
            case 5:
               return "stateless-bean-load-algorithm";
            case 6:
               return "stateless-bean-call-router-class-name";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StatelessClusteringBeanImpl bean;

      protected Helper(StatelessClusteringBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "HomeIsClusterable";
            case 1:
               return "HomeLoadAlgorithm";
            case 2:
               return "HomeCallRouterClassName";
            case 3:
               return "UseServersideStubs";
            case 4:
               return "StatelessBeanIsClusterable";
            case 5:
               return "StatelessBeanLoadAlgorithm";
            case 6:
               return "StatelessBeanCallRouterClassName";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HomeCallRouterClassName")) {
            return 2;
         } else if (propName.equals("HomeLoadAlgorithm")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("StatelessBeanCallRouterClassName")) {
            return 6;
         } else if (propName.equals("StatelessBeanLoadAlgorithm")) {
            return 5;
         } else if (propName.equals("HomeIsClusterable")) {
            return 0;
         } else if (propName.equals("StatelessBeanIsClusterable")) {
            return 4;
         } else {
            return propName.equals("UseServersideStubs") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isHomeCallRouterClassNameSet()) {
               buf.append("HomeCallRouterClassName");
               buf.append(String.valueOf(this.bean.getHomeCallRouterClassName()));
            }

            if (this.bean.isHomeLoadAlgorithmSet()) {
               buf.append("HomeLoadAlgorithm");
               buf.append(String.valueOf(this.bean.getHomeLoadAlgorithm()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isStatelessBeanCallRouterClassNameSet()) {
               buf.append("StatelessBeanCallRouterClassName");
               buf.append(String.valueOf(this.bean.getStatelessBeanCallRouterClassName()));
            }

            if (this.bean.isStatelessBeanLoadAlgorithmSet()) {
               buf.append("StatelessBeanLoadAlgorithm");
               buf.append(String.valueOf(this.bean.getStatelessBeanLoadAlgorithm()));
            }

            if (this.bean.isHomeIsClusterableSet()) {
               buf.append("HomeIsClusterable");
               buf.append(String.valueOf(this.bean.isHomeIsClusterable()));
            }

            if (this.bean.isStatelessBeanIsClusterableSet()) {
               buf.append("StatelessBeanIsClusterable");
               buf.append(String.valueOf(this.bean.isStatelessBeanIsClusterable()));
            }

            if (this.bean.isUseServersideStubsSet()) {
               buf.append("UseServersideStubs");
               buf.append(String.valueOf(this.bean.isUseServersideStubs()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            StatelessClusteringBeanImpl otherTyped = (StatelessClusteringBeanImpl)other;
            this.computeDiff("HomeCallRouterClassName", this.bean.getHomeCallRouterClassName(), otherTyped.getHomeCallRouterClassName(), false);
            this.computeDiff("HomeLoadAlgorithm", this.bean.getHomeLoadAlgorithm(), otherTyped.getHomeLoadAlgorithm(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("StatelessBeanCallRouterClassName", this.bean.getStatelessBeanCallRouterClassName(), otherTyped.getStatelessBeanCallRouterClassName(), false);
            this.computeDiff("StatelessBeanLoadAlgorithm", this.bean.getStatelessBeanLoadAlgorithm(), otherTyped.getStatelessBeanLoadAlgorithm(), false);
            this.computeDiff("HomeIsClusterable", this.bean.isHomeIsClusterable(), otherTyped.isHomeIsClusterable(), false);
            this.computeDiff("StatelessBeanIsClusterable", this.bean.isStatelessBeanIsClusterable(), otherTyped.isStatelessBeanIsClusterable(), false);
            this.computeDiff("UseServersideStubs", this.bean.isUseServersideStubs(), otherTyped.isUseServersideStubs(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StatelessClusteringBeanImpl original = (StatelessClusteringBeanImpl)event.getSourceBean();
            StatelessClusteringBeanImpl proposed = (StatelessClusteringBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HomeCallRouterClassName")) {
                  original.setHomeCallRouterClassName(proposed.getHomeCallRouterClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("HomeLoadAlgorithm")) {
                  original.setHomeLoadAlgorithm(proposed.getHomeLoadAlgorithm());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("StatelessBeanCallRouterClassName")) {
                  original.setStatelessBeanCallRouterClassName(proposed.getStatelessBeanCallRouterClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("StatelessBeanLoadAlgorithm")) {
                  original.setStatelessBeanLoadAlgorithm(proposed.getStatelessBeanLoadAlgorithm());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("HomeIsClusterable")) {
                  original.setHomeIsClusterable(proposed.isHomeIsClusterable());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StatelessBeanIsClusterable")) {
                  original.setStatelessBeanIsClusterable(proposed.isStatelessBeanIsClusterable());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("UseServersideStubs")) {
                  original.setUseServersideStubs(proposed.isUseServersideStubs());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            StatelessClusteringBeanImpl copy = (StatelessClusteringBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HomeCallRouterClassName")) && this.bean.isHomeCallRouterClassNameSet()) {
               copy.setHomeCallRouterClassName(this.bean.getHomeCallRouterClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("HomeLoadAlgorithm")) && this.bean.isHomeLoadAlgorithmSet()) {
               copy.setHomeLoadAlgorithm(this.bean.getHomeLoadAlgorithm());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("StatelessBeanCallRouterClassName")) && this.bean.isStatelessBeanCallRouterClassNameSet()) {
               copy.setStatelessBeanCallRouterClassName(this.bean.getStatelessBeanCallRouterClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("StatelessBeanLoadAlgorithm")) && this.bean.isStatelessBeanLoadAlgorithmSet()) {
               copy.setStatelessBeanLoadAlgorithm(this.bean.getStatelessBeanLoadAlgorithm());
            }

            if ((excludeProps == null || !excludeProps.contains("HomeIsClusterable")) && this.bean.isHomeIsClusterableSet()) {
               copy.setHomeIsClusterable(this.bean.isHomeIsClusterable());
            }

            if ((excludeProps == null || !excludeProps.contains("StatelessBeanIsClusterable")) && this.bean.isStatelessBeanIsClusterableSet()) {
               copy.setStatelessBeanIsClusterable(this.bean.isStatelessBeanIsClusterable());
            }

            if ((excludeProps == null || !excludeProps.contains("UseServersideStubs")) && this.bean.isUseServersideStubsSet()) {
               copy.setUseServersideStubs(this.bean.isUseServersideStubs());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
