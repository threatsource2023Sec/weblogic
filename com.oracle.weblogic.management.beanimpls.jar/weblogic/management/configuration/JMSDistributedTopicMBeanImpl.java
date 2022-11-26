package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JMSDistributedTopic;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JMSDistributedTopicMBeanImpl extends JMSDistributedDestinationMBeanImpl implements JMSDistributedTopicMBean, Serializable {
   private boolean _DynamicallyCreated;
   private JMSDistributedTopicMemberMBean[] _JMSDistributedTopicMembers;
   private JMSTemplateMBean _JMSTemplate;
   private String _JNDIName;
   private String _LoadBalancingPolicy;
   private JMSDistributedTopicMemberMBean[] _Members;
   private String _Name;
   private String _Notes;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private JMSTemplateMBean _Template;
   private transient JMSDistributedTopic _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JMSDistributedTopicMBeanImpl() {
      try {
         this._customizer = new JMSDistributedTopic(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSDistributedTopicMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSDistributedTopic(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSDistributedTopicMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSDistributedTopic(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public JMSTemplateMBean createJMSTemplate(String param0) {
      JMSTemplateMBeanImpl _val = new JMSTemplateMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setJMSTemplate(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getJNDIName() {
      return this._customizer.getJNDIName();
   }

   public JMSDistributedTopicMemberMBean[] getMembers() {
      return this._customizer.getMembers();
   }

   public String getMembersAsString() {
      return this._getHelper()._serializeKeyList(this.getMembers());
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public TargetMBean[] getTargets() {
      return this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(12);
   }

   public boolean isMembersInherited() {
      return false;
   }

   public boolean isMembersSet() {
      return this._isSet(16);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setMembersAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Members);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, JMSDistributedTopicMemberMBean.class, new ReferenceManager.Resolver(this, 16, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JMSDistributedTopicMBeanImpl.this.addMember((JMSDistributedTopicMemberMBean)value);
                        JMSDistributedTopicMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSDistributedTopicMBeanImpl.this._Members, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               JMSDistributedTopicMemberMBean[] var6 = this._Members;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  JMSDistributedTopicMemberMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeMember(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         JMSDistributedTopicMemberMBean[] _oldVal = this._Members;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._Members);
      }
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JMSDistributedTopicMBeanImpl.this.addTarget((TargetMBean)value);
                        JMSDistributedTopicMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSDistributedTopicMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public void destroyJMSTemplate(JMSTemplateMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JMSTemplate;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSTemplate((JMSTemplateMBean)null);
               this._unSet(13);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getJNDIName();
      this._customizer.setJNDIName(param0);
      this._postSet(12, _oldVal, param0);
   }

   public void setMembers(JMSDistributedTopicMemberMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedTopicMemberMBean[] param0 = param0 == null ? new JMSDistributedTopicMemberMBeanImpl[0] : param0;
      JMSDistributedTopicMemberMBean[] _oldVal = this.getMembers();
      this._customizer.setMembers((JMSDistributedTopicMemberMBean[])param0);
      this._postSet(16, _oldVal, param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return JMSDistributedTopicMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
   }

   public boolean addMember(JMSDistributedTopicMemberMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.addMember(param0);
   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public JMSTemplateMBean getJMSTemplate() {
      return this._customizer.getJMSTemplate();
   }

   public String getNotes() {
      return this._customizer.getNotes();
   }

   public boolean isJMSTemplateInherited() {
      return false;
   }

   public boolean isJMSTemplateSet() {
      return this._isSet(13);
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(3);
   }

   public boolean removeMember(JMSDistributedTopicMemberMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      return this._customizer.removeMember(param0);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setJMSTemplate(JMSTemplateMBean param0) {
      if (param0 != null && this.getJMSTemplate() != null && param0 != this.getJMSTemplate()) {
         throw new BeanAlreadyExistsException(this.getJMSTemplate() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 13)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         JMSTemplateMBean _oldVal = this.getJMSTemplate();

         try {
            this._customizer.setJMSTemplate(param0);
         } catch (InvalidAttributeValueException var4) {
            throw new UndeclaredThrowableException(var4);
         }

         this._postSet(13, _oldVal, param0);
      }
   }

   public void setNotes(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getNotes();
      this._customizer.setNotes(param0);
      this._postSet(3, _oldVal, param0);
   }

   public JMSTemplateMBean getTemplate() {
      return this._customizer.getTemplate();
   }

   public String getTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTemplateInherited() {
      return false;
   }

   public boolean isTemplateSet() {
      return this._isSet(14);
   }

   public void setTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSTemplateMBean.class, new ReferenceManager.Resolver(this, 14) {
            public void resolveReference(Object value) {
               try {
                  JMSDistributedTopicMBeanImpl.this.setTemplate((JMSTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSTemplateMBean _oldVal = this._Template;
         this._initializeProperty(14);
         this._postSet(14, _oldVal, this._Template);
      }

   }

   public void useDelegates(DistributedTopicBean param0, SubDeploymentMBean param1) {
      this._customizer.useDelegates(param0, param1);
   }

   public void addJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         JMSDistributedTopicMemberMBean[] _new;
         if (this._isSet(17)) {
            _new = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])this._getHelper()._extendArray(this.getJMSDistributedTopicMembers(), JMSDistributedTopicMemberMBean.class, param0));
         } else {
            _new = new JMSDistributedTopicMemberMBean[]{param0};
         }

         try {
            this.setJMSDistributedTopicMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMSDistributedTopicMemberMBean[] getJMSDistributedTopicMembers() {
      return this._JMSDistributedTopicMembers;
   }

   public boolean isJMSDistributedTopicMembersInherited() {
      return false;
   }

   public boolean isJMSDistributedTopicMembersSet() {
      return this._isSet(17);
   }

   public void removeJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      this.destroyJMSDistributedTopicMember(param0);
   }

   public void setJMSDistributedTopicMembers(JMSDistributedTopicMemberMBean[] param0) throws InvalidAttributeValueException {
      JMSDistributedTopicMemberMBean[] param0 = param0 == null ? new JMSDistributedTopicMemberMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      JMSDistributedTopicMemberMBean[] _oldVal = this._JMSDistributedTopicMembers;
      this._JMSDistributedTopicMembers = (JMSDistributedTopicMemberMBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public void setTemplate(JMSTemplateMBean param0) {
      JMSTemplateMBean _oldVal = this.getTemplate();

      try {
         this._customizer.setTemplate(param0);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(14, _oldVal, param0);
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String param0) {
      JMSDistributedTopicMemberMBeanImpl lookup = (JMSDistributedTopicMemberMBeanImpl)this.lookupJMSDistributedTopicMember(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JMSDistributedTopicMemberMBeanImpl _val = new JMSDistributedTopicMemberMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJMSDistributedTopicMember(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public String getLoadBalancingPolicy() {
      return this._customizer.getLoadBalancingPolicy();
   }

   public boolean isLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isLoadBalancingPolicySet() {
      return this._isSet(15);
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String param0, JMSDistributedTopicMemberMBean param1) {
      return this._customizer.createJMSDistributedTopicMember(param0, param1);
   }

   public void setLoadBalancingPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Round-Robin", "Random"};
      param0 = LegalChecks.checkInEnum("LoadBalancingPolicy", param0, _set);
      String _oldVal = this.getLoadBalancingPolicy();
      this._customizer.setLoadBalancingPolicy(param0);
      this._postSet(15, _oldVal, param0);
   }

   public void destroyJMSDistributedTopicMember(JMSDistributedTopicMemberMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         JMSDistributedTopicMemberMBean[] _old = this.getJMSDistributedTopicMembers();
         JMSDistributedTopicMemberMBean[] _new = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])this._getHelper()._removeElement(_old, JMSDistributedTopicMemberMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSDistributedTopicMembers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void destroyJMSDistributedTopicMember(String param0, JMSDistributedTopicMemberMBean param1) {
      this._customizer.destroyJMSDistributedTopicMember(param0, param1);
   }

   public JMSDistributedTopicMemberMBean lookupJMSDistributedTopicMember(String param0) {
      Object[] aary = (Object[])this._JMSDistributedTopicMembers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JMSDistributedTopicMemberMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JMSDistributedTopicMemberMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._JMSDistributedTopicMembers = new JMSDistributedTopicMemberMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setJMSTemplate((JMSTemplateMBean)null);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setJNDIName((String)null);
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setLoadBalancingPolicy("Round-Robin");
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setMembers(new JMSDistributedTopicMemberMBean[0]);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._customizer.setNotes((String)null);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setTemplate((JMSTemplateMBean)null);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "JMSDistributedTopic";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         JMSDistributedTopicMemberMBean[] oldVal;
         if (name.equals("JMSDistributedTopicMembers")) {
            oldVal = this._JMSDistributedTopicMembers;
            this._JMSDistributedTopicMembers = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])v);
            this._postSet(17, oldVal, this._JMSDistributedTopicMembers);
         } else {
            JMSTemplateMBean oldVal;
            if (name.equals("JMSTemplate")) {
               oldVal = this._JMSTemplate;
               this._JMSTemplate = (JMSTemplateMBean)v;
               this._postSet(13, oldVal, this._JMSTemplate);
            } else {
               String oldVal;
               if (name.equals("JNDIName")) {
                  oldVal = this._JNDIName;
                  this._JNDIName = (String)v;
                  this._postSet(12, oldVal, this._JNDIName);
               } else if (name.equals("LoadBalancingPolicy")) {
                  oldVal = this._LoadBalancingPolicy;
                  this._LoadBalancingPolicy = (String)v;
                  this._postSet(15, oldVal, this._LoadBalancingPolicy);
               } else if (name.equals("Members")) {
                  oldVal = this._Members;
                  this._Members = (JMSDistributedTopicMemberMBean[])((JMSDistributedTopicMemberMBean[])v);
                  this._postSet(16, oldVal, this._Members);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("Notes")) {
                  oldVal = this._Notes;
                  this._Notes = (String)v;
                  this._postSet(3, oldVal, this._Notes);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(10, oldVal, this._Targets);
               } else if (name.equals("Template")) {
                  oldVal = this._Template;
                  this._Template = (JMSTemplateMBean)v;
                  this._postSet(14, oldVal, this._Template);
               } else if (name.equals("customizer")) {
                  JMSDistributedTopic oldVal = this._customizer;
                  this._customizer = (JMSDistributedTopic)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("JMSDistributedTopicMembers")) {
         return this._JMSDistributedTopicMembers;
      } else if (name.equals("JMSTemplate")) {
         return this._JMSTemplate;
      } else if (name.equals("JNDIName")) {
         return this._JNDIName;
      } else if (name.equals("LoadBalancingPolicy")) {
         return this._LoadBalancingPolicy;
      } else if (name.equals("Members")) {
         return this._Members;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Notes")) {
         return this._Notes;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("Template")) {
         return this._Template;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends JMSDistributedDestinationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("notes")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("member")) {
                  return 16;
               }

               if (s.equals("target")) {
                  return 10;
               }
            case 7:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 8:
               if (s.equals("template")) {
                  return 14;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("jms-template")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("load-balancing-policy")) {
                  return 15;
               }
               break;
            case 28:
               if (s.equals("jms-distributed-topic-member")) {
                  return 17;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 13:
               return new JMSTemplateMBeanImpl.SchemaHelper2();
            case 17:
               return new JMSDistributedTopicMemberMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
               return "notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "jndi-name";
            case 13:
               return "jms-template";
            case 14:
               return "template";
            case 15:
               return "load-balancing-policy";
            case 16:
               return "member";
            case 17:
               return "jms-distributed-topic-member";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.isArray(propIndex);
            case 16:
               return true;
            case 17:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            case 17:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends JMSDistributedDestinationMBeanImpl.Helper {
      private JMSDistributedTopicMBeanImpl bean;

      protected Helper(JMSDistributedTopicMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
               return "Notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "JNDIName";
            case 13:
               return "JMSTemplate";
            case 14:
               return "Template";
            case 15:
               return "LoadBalancingPolicy";
            case 16:
               return "Members";
            case 17:
               return "JMSDistributedTopicMembers";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("JMSDistributedTopicMembers")) {
            return 17;
         } else if (propName.equals("JMSTemplate")) {
            return 13;
         } else if (propName.equals("JNDIName")) {
            return 12;
         } else if (propName.equals("LoadBalancingPolicy")) {
            return 15;
         } else if (propName.equals("Members")) {
            return 16;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 3;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("Template")) {
            return 14;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getJMSDistributedTopicMembers()));
         if (this.bean.getJMSTemplate() != null) {
            iterators.add(new ArrayIterator(new JMSTemplateMBean[]{this.bean.getJMSTemplate()}));
         }

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
            childValue = 0L;

            for(int i = 0; i < this.bean.getJMSDistributedTopicMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJMSDistributedTopicMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJMSTemplate());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isLoadBalancingPolicySet()) {
               buf.append("LoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getLoadBalancingPolicy()));
            }

            if (this.bean.isMembersSet()) {
               buf.append("Members");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMembers())));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTemplateSet()) {
               buf.append("Template");
               buf.append(String.valueOf(this.bean.getTemplate()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            JMSDistributedTopicMBeanImpl otherTyped = (JMSDistributedTopicMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSDistributedTopicMembers", this.bean.getJMSDistributedTopicMembers(), otherTyped.getJMSDistributedTopicMembers(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeChildDiff("JMSTemplate", this.bean.getJMSTemplate(), otherTyped.getJMSTemplate(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoadBalancingPolicy", this.bean.getLoadBalancingPolicy(), otherTyped.getLoadBalancingPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Members", this.bean.getMembers(), otherTyped.getMembers(), true);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Template", this.bean.getTemplate(), otherTyped.getTemplate(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSDistributedTopicMBeanImpl original = (JMSDistributedTopicMBeanImpl)event.getSourceBean();
            JMSDistributedTopicMBeanImpl proposed = (JMSDistributedTopicMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("JMSDistributedTopicMembers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJMSDistributedTopicMember((JMSDistributedTopicMemberMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJMSDistributedTopicMember((JMSDistributedTopicMemberMBean)update.getRemovedObject());
                  }

                  if (original.getJMSDistributedTopicMembers() == null || original.getJMSDistributedTopicMembers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("JMSTemplate")) {
                  if (type == 2) {
                     original.setJMSTemplate((JMSTemplateMBean)this.createCopy((AbstractDescriptorBean)proposed.getJMSTemplate()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JMSTemplate", original.getJMSTemplate());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LoadBalancingPolicy")) {
                  original.setLoadBalancingPolicy(proposed.getLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Members")) {
                  original.setMembersAsString(proposed.getMembersAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Template")) {
                  original.setTemplateAsString(proposed.getTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (!prop.equals("DynamicallyCreated")) {
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
            JMSDistributedTopicMBeanImpl copy = (JMSDistributedTopicMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSDistributedTopicMembers")) && this.bean.isJMSDistributedTopicMembersSet() && !copy._isSet(17)) {
               JMSDistributedTopicMemberMBean[] oldJMSDistributedTopicMembers = this.bean.getJMSDistributedTopicMembers();
               JMSDistributedTopicMemberMBean[] newJMSDistributedTopicMembers = new JMSDistributedTopicMemberMBean[oldJMSDistributedTopicMembers.length];

               for(int i = 0; i < newJMSDistributedTopicMembers.length; ++i) {
                  newJMSDistributedTopicMembers[i] = (JMSDistributedTopicMemberMBean)((JMSDistributedTopicMemberMBean)this.createCopy((AbstractDescriptorBean)oldJMSDistributedTopicMembers[i], includeObsolete));
               }

               copy.setJMSDistributedTopicMembers(newJMSDistributedTopicMembers);
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JMSTemplate")) && this.bean.isJMSTemplateSet() && !copy._isSet(13)) {
               Object o = this.bean.getJMSTemplate();
               copy.setJMSTemplate((JMSTemplateMBean)null);
               copy.setJMSTemplate(o == null ? null : (JMSTemplateMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoadBalancingPolicy")) && this.bean.isLoadBalancingPolicySet()) {
               copy.setLoadBalancingPolicy(this.bean.getLoadBalancingPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Members")) && this.bean.isMembersSet()) {
               copy._unSet(copy, 16);
               copy.setMembersAsString(this.bean.getMembersAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Template")) && this.bean.isTemplateSet()) {
               copy._unSet(copy, 14);
               copy.setTemplateAsString(this.bean.getTemplateAsString());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getJMSDistributedTopicMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSTemplate(), clazz, annotation);
         this.inferSubTree(this.bean.getMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getTemplate(), clazz, annotation);
      }
   }
}
