package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ComponentsMBeanImpl extends XMLElementMBeanDelegate implements ComponentsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_javaClassComponents = false;
   private List javaClassComponents;
   private boolean isSet_statelessEJBs = false;
   private List statelessEJBs;
   private boolean isSet_useMultiplePorts = false;
   private boolean useMultiplePorts;
   private boolean isSet_jmsReceiveTopics = false;
   private List jmsReceiveTopics;
   private boolean isSet_jmsReceiveQueues = false;
   private List jmsReceiveQueues;
   private boolean isSet_jmsSendDestinations = false;
   private List jmsSendDestinations;
   private boolean isSet_usePortTypeName = false;
   private boolean usePortTypeName;
   private boolean isSet_statefulJavaClassComponents = false;
   private List statefulJavaClassComponents;

   public JavaClassMBean[] getJavaClassComponents() {
      if (this.javaClassComponents == null) {
         return new JavaClassMBean[0];
      } else {
         JavaClassMBean[] result = new JavaClassMBean[this.javaClassComponents.size()];
         result = (JavaClassMBean[])((JavaClassMBean[])this.javaClassComponents.toArray(result));
         return result;
      }
   }

   public void setJavaClassComponents(JavaClassMBean[] value) {
      JavaClassMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getJavaClassComponents();
      }

      this.isSet_javaClassComponents = true;
      if (this.javaClassComponents == null) {
         this.javaClassComponents = Collections.synchronizedList(new ArrayList());
      } else {
         this.javaClassComponents.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.javaClassComponents.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("JavaClassComponents", _oldVal, this.getJavaClassComponents());
      }

   }

   public void addJavaClassComponent(JavaClassMBean value) {
      this.isSet_javaClassComponents = true;
      if (this.javaClassComponents == null) {
         this.javaClassComponents = Collections.synchronizedList(new ArrayList());
      }

      this.javaClassComponents.add(value);
   }

   public void removeJavaClassComponent(JavaClassMBean value) {
      if (this.javaClassComponents != null) {
         this.javaClassComponents.remove(value);
      }
   }

   public StatelessEJBMBean[] getStatelessEJBs() {
      if (this.statelessEJBs == null) {
         return new StatelessEJBMBean[0];
      } else {
         StatelessEJBMBean[] result = new StatelessEJBMBean[this.statelessEJBs.size()];
         result = (StatelessEJBMBean[])((StatelessEJBMBean[])this.statelessEJBs.toArray(result));
         return result;
      }
   }

   public void setStatelessEJBs(StatelessEJBMBean[] value) {
      StatelessEJBMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getStatelessEJBs();
      }

      this.isSet_statelessEJBs = true;
      if (this.statelessEJBs == null) {
         this.statelessEJBs = Collections.synchronizedList(new ArrayList());
      } else {
         this.statelessEJBs.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.statelessEJBs.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("StatelessEJBs", _oldVal, this.getStatelessEJBs());
      }

   }

   public void addStatelessEJB(StatelessEJBMBean value) {
      this.isSet_statelessEJBs = true;
      if (this.statelessEJBs == null) {
         this.statelessEJBs = Collections.synchronizedList(new ArrayList());
      }

      this.statelessEJBs.add(value);
   }

   public void removeStatelessEJB(StatelessEJBMBean value) {
      if (this.statelessEJBs != null) {
         this.statelessEJBs.remove(value);
      }
   }

   public boolean getUseMultiplePorts() {
      return this.useMultiplePorts;
   }

   public void setUseMultiplePorts(boolean value) {
      boolean old = this.useMultiplePorts;
      this.useMultiplePorts = value;
      this.isSet_useMultiplePorts = true;
      this.checkChange("useMultiplePorts", old, this.useMultiplePorts);
   }

   public JMSReceiveTopicMBean[] getJMSReceiveTopics() {
      if (this.jmsReceiveTopics == null) {
         return new JMSReceiveTopicMBean[0];
      } else {
         JMSReceiveTopicMBean[] result = new JMSReceiveTopicMBean[this.jmsReceiveTopics.size()];
         result = (JMSReceiveTopicMBean[])((JMSReceiveTopicMBean[])this.jmsReceiveTopics.toArray(result));
         return result;
      }
   }

   public void setJMSReceiveTopics(JMSReceiveTopicMBean[] value) {
      JMSReceiveTopicMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getJMSReceiveTopics();
      }

      this.isSet_jmsReceiveTopics = true;
      if (this.jmsReceiveTopics == null) {
         this.jmsReceiveTopics = Collections.synchronizedList(new ArrayList());
      } else {
         this.jmsReceiveTopics.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.jmsReceiveTopics.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("JMSReceiveTopics", _oldVal, this.getJMSReceiveTopics());
      }

   }

   public void addJMSReceiveTopic(JMSReceiveTopicMBean value) {
      this.isSet_jmsReceiveTopics = true;
      if (this.jmsReceiveTopics == null) {
         this.jmsReceiveTopics = Collections.synchronizedList(new ArrayList());
      }

      this.jmsReceiveTopics.add(value);
   }

   public void removeJMSReceiveTopic(JMSReceiveTopicMBean value) {
      if (this.jmsReceiveTopics != null) {
         this.jmsReceiveTopics.remove(value);
      }
   }

   public JMSReceiveQueueMBean[] getJMSReceiveQueues() {
      if (this.jmsReceiveQueues == null) {
         return new JMSReceiveQueueMBean[0];
      } else {
         JMSReceiveQueueMBean[] result = new JMSReceiveQueueMBean[this.jmsReceiveQueues.size()];
         result = (JMSReceiveQueueMBean[])((JMSReceiveQueueMBean[])this.jmsReceiveQueues.toArray(result));
         return result;
      }
   }

   public void setJMSReceiveQueues(JMSReceiveQueueMBean[] value) {
      JMSReceiveQueueMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getJMSReceiveQueues();
      }

      this.isSet_jmsReceiveQueues = true;
      if (this.jmsReceiveQueues == null) {
         this.jmsReceiveQueues = Collections.synchronizedList(new ArrayList());
      } else {
         this.jmsReceiveQueues.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.jmsReceiveQueues.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("JMSReceiveQueues", _oldVal, this.getJMSReceiveQueues());
      }

   }

   public void addJMSReceiveQueue(JMSReceiveQueueMBean value) {
      this.isSet_jmsReceiveQueues = true;
      if (this.jmsReceiveQueues == null) {
         this.jmsReceiveQueues = Collections.synchronizedList(new ArrayList());
      }

      this.jmsReceiveQueues.add(value);
   }

   public void removeJMSReceiveQueue(JMSReceiveQueueMBean value) {
      if (this.jmsReceiveQueues != null) {
         this.jmsReceiveQueues.remove(value);
      }
   }

   public JMSSendDestinationMBean[] getJMSSendDestinations() {
      if (this.jmsSendDestinations == null) {
         return new JMSSendDestinationMBean[0];
      } else {
         JMSSendDestinationMBean[] result = new JMSSendDestinationMBean[this.jmsSendDestinations.size()];
         result = (JMSSendDestinationMBean[])((JMSSendDestinationMBean[])this.jmsSendDestinations.toArray(result));
         return result;
      }
   }

   public void setJMSSendDestinations(JMSSendDestinationMBean[] value) {
      JMSSendDestinationMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getJMSSendDestinations();
      }

      this.isSet_jmsSendDestinations = true;
      if (this.jmsSendDestinations == null) {
         this.jmsSendDestinations = Collections.synchronizedList(new ArrayList());
      } else {
         this.jmsSendDestinations.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.jmsSendDestinations.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("JMSSendDestinations", _oldVal, this.getJMSSendDestinations());
      }

   }

   public void addJMSSendDestination(JMSSendDestinationMBean value) {
      this.isSet_jmsSendDestinations = true;
      if (this.jmsSendDestinations == null) {
         this.jmsSendDestinations = Collections.synchronizedList(new ArrayList());
      }

      this.jmsSendDestinations.add(value);
   }

   public void removeJMSSendDestination(JMSSendDestinationMBean value) {
      if (this.jmsSendDestinations != null) {
         this.jmsSendDestinations.remove(value);
      }
   }

   public boolean getUsePortTypeName() {
      return this.usePortTypeName;
   }

   public void setUsePortTypeName(boolean value) {
      boolean old = this.usePortTypeName;
      this.usePortTypeName = value;
      this.isSet_usePortTypeName = true;
      this.checkChange("usePortTypeName", old, this.usePortTypeName);
   }

   public StatefulJavaClassMBean[] getStatefulJavaClassComponents() {
      if (this.statefulJavaClassComponents == null) {
         return new StatefulJavaClassMBean[0];
      } else {
         StatefulJavaClassMBean[] result = new StatefulJavaClassMBean[this.statefulJavaClassComponents.size()];
         result = (StatefulJavaClassMBean[])((StatefulJavaClassMBean[])this.statefulJavaClassComponents.toArray(result));
         return result;
      }
   }

   public void setStatefulJavaClassComponents(StatefulJavaClassMBean[] value) {
      StatefulJavaClassMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getStatefulJavaClassComponents();
      }

      this.isSet_statefulJavaClassComponents = true;
      if (this.statefulJavaClassComponents == null) {
         this.statefulJavaClassComponents = Collections.synchronizedList(new ArrayList());
      } else {
         this.statefulJavaClassComponents.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.statefulJavaClassComponents.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("StatefulJavaClassComponents", _oldVal, this.getStatefulJavaClassComponents());
      }

   }

   public void addStatefulJavaClassComponent(StatefulJavaClassMBean value) {
      this.isSet_statefulJavaClassComponents = true;
      if (this.statefulJavaClassComponents == null) {
         this.statefulJavaClassComponents = Collections.synchronizedList(new ArrayList());
      }

      this.statefulJavaClassComponents.add(value);
   }

   public void removeStatefulJavaClassComponent(StatefulJavaClassMBean value) {
      if (this.statefulJavaClassComponents != null) {
         this.statefulJavaClassComponents.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<components");
      if (this.isSet_useMultiplePorts) {
         result.append(" use-multiple-ports=\"").append(String.valueOf(this.getUseMultiplePorts())).append("\"");
      }

      if (this.isSet_usePortTypeName) {
         result.append(" use-port-type-name=\"").append(String.valueOf(this.getUsePortTypeName())).append("\"");
      }

      result.append(">\n");
      int i;
      if (null != this.getStatelessEJBs()) {
         for(i = 0; i < this.getStatelessEJBs().length; ++i) {
            result.append(this.getStatelessEJBs()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getJavaClassComponents()) {
         for(i = 0; i < this.getJavaClassComponents().length; ++i) {
            result.append(this.getJavaClassComponents()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getStatefulJavaClassComponents()) {
         for(i = 0; i < this.getStatefulJavaClassComponents().length; ++i) {
            result.append(this.getStatefulJavaClassComponents()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getJMSSendDestinations()) {
         for(i = 0; i < this.getJMSSendDestinations().length; ++i) {
            result.append(this.getJMSSendDestinations()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getJMSReceiveTopics()) {
         for(i = 0; i < this.getJMSReceiveTopics().length; ++i) {
            result.append(this.getJMSReceiveTopics()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getJMSReceiveQueues()) {
         for(i = 0; i < this.getJMSReceiveQueues().length; ++i) {
            result.append(this.getJMSReceiveQueues()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</components>\n");
      return result.toString();
   }
}
