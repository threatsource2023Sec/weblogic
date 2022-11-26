package weblogic.management.mbeanservers;

import java.util.HashSet;
import java.util.Set;
import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfo;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.mbeanservers.internal.MBeanCICInterceptor;
import weblogic.management.visibility.utils.MBeanNameUtil;

public class MBeanInfoBuilder {
   public static final boolean globalMBeansVisibleToPartitions = Boolean.getBoolean("_GlobalMBeansVisibleToPartitions");
   private static final String VISIBLETOPARTITIONS_ANNO_IN_WLS = "com.bea.VisibleToPartitions";
   private static final String VISIBLETOPARTITIONS_ANNO_IN_JMXFMW = "VisibleToPartitions";

   public static MBeanInfo rebuildModelMBeanInfo(MBeanInfo mbeanInfo, MBeanAttributeInfo[] oldAttrInfos, MBeanOperationInfo[] oldOperInfos) {
      Set modelAttrInfos = new HashSet();
      if (oldAttrInfos == null) {
         modelAttrInfos = null;
      } else {
         for(int i = 0; i < oldAttrInfos.length; ++i) {
            if (oldAttrInfos[i] != null) {
               ModelMBeanAttributeInfo modelAttrInfo = new ModelMBeanAttributeInfo(oldAttrInfos[i].getName(), oldAttrInfos[i].getType(), oldAttrInfos[i].getDescription(), oldAttrInfos[i].isReadable(), oldAttrInfos[i].isWritable(), oldAttrInfos[i].isIs(), oldAttrInfos[i].getDescriptor());
               modelAttrInfos.add(modelAttrInfo);
            } else {
               modelAttrInfos.add((Object)null);
            }
         }
      }

      ModelMBeanAttributeInfo[] modelAttrInfoArray = modelAttrInfos == null ? null : (ModelMBeanAttributeInfo[])modelAttrInfos.toArray(new ModelMBeanAttributeInfo[modelAttrInfos.size()]);
      Set modelOperInfos = new HashSet();
      if (oldOperInfos == null) {
         modelOperInfos = null;
      } else {
         for(int i = 0; i < oldOperInfos.length; ++i) {
            if (oldOperInfos[i] != null) {
               ModelMBeanOperationInfo modelOperInfo = new ModelMBeanOperationInfo(oldOperInfos[i].getName(), oldOperInfos[i].getDescription(), oldOperInfos[i].getSignature(), oldOperInfos[i].getReturnType(), oldOperInfos[i].getImpact(), oldOperInfos[i].getDescriptor());
               modelOperInfos.add(modelOperInfo);
            } else {
               modelOperInfos.add((Object)null);
            }
         }
      }

      ModelMBeanOperationInfo[] modelOperInfoArray = modelOperInfos == null ? null : (ModelMBeanOperationInfo[])modelOperInfos.toArray(new ModelMBeanOperationInfo[modelOperInfos.size()]);
      Set modelConsInfos = new HashSet();
      MBeanConstructorInfo[] oldConsInfos = mbeanInfo.getConstructors();
      if (oldConsInfos == null) {
         modelConsInfos = null;
      } else {
         for(int i = 0; i < oldConsInfos.length; ++i) {
            if (oldConsInfos[i] != null) {
               ModelMBeanConstructorInfo modelConsInfo = new ModelMBeanConstructorInfo(oldConsInfos[i].getName(), oldConsInfos[i].getDescription(), oldConsInfos[i].getSignature());
               modelConsInfos.add(modelConsInfo);
            } else {
               modelConsInfos.add((Object)null);
            }
         }
      }

      ModelMBeanConstructorInfo[] modelConsInfoArray = modelConsInfos == null ? null : (ModelMBeanConstructorInfo[])modelConsInfos.toArray(new ModelMBeanConstructorInfo[modelConsInfos.size()]);
      Set modelNotifInfos = new HashSet();
      MBeanNotificationInfo[] oldNotifInfos = mbeanInfo.getNotifications();
      if (oldNotifInfos == null) {
         modelNotifInfos = null;
      } else {
         for(int i = 0; i < oldNotifInfos.length; ++i) {
            if (oldNotifInfos[i] != null) {
               ModelMBeanNotificationInfo modelNotifInfo = new ModelMBeanNotificationInfo(oldNotifInfos[i].getNotifTypes(), oldNotifInfos[i].getName(), oldNotifInfos[i].getDescription(), oldNotifInfos[i].getDescriptor());
               modelNotifInfos.add(modelNotifInfo);
            } else {
               modelNotifInfos.add((Object)null);
            }
         }
      }

      ModelMBeanNotificationInfo[] modelNotifInfoArray = modelNotifInfos == null ? null : (ModelMBeanNotificationInfo[])modelNotifInfos.toArray(new ModelMBeanNotificationInfo[modelNotifInfos.size()]);
      return new ModelMBeanInfoSupport(mbeanInfo.getClassName(), mbeanInfo.getDescription(), modelAttrInfoArray, modelConsInfoArray, modelOperInfoArray, modelNotifInfoArray, mbeanInfo.getDescriptor());
   }

   public static MBeanInfo rebuildOpenMBeanInfo(MBeanInfo mbeanInfo, MBeanAttributeInfo[] oldAttrInfos, MBeanOperationInfo[] oldOperInfos) {
      Set openAttrInfos = new HashSet();
      if (oldAttrInfos == null) {
         openAttrInfos = null;
      } else {
         for(int i = 0; i < oldAttrInfos.length; ++i) {
            if (oldAttrInfos[i] != null) {
               OpenMBeanAttributeInfoSupport oldAttrInfo = (OpenMBeanAttributeInfoSupport)OpenMBeanAttributeInfoSupport.class.cast(oldAttrInfos[i]);
               OpenMBeanAttributeInfo openAttrInfo = new OpenMBeanAttributeInfoSupport(oldAttrInfo.getName(), oldAttrInfo.getDescription(), oldAttrInfo.getOpenType(), oldAttrInfo.isReadable(), oldAttrInfo.isWritable(), oldAttrInfo.isIs(), oldAttrInfo.getDescriptor());
               openAttrInfos.add(openAttrInfo);
            } else {
               openAttrInfos.add((Object)null);
            }
         }
      }

      OpenMBeanAttributeInfo[] openAttrInfoArray = openAttrInfos == null ? null : (OpenMBeanAttributeInfo[])openAttrInfos.toArray(new OpenMBeanAttributeInfo[openAttrInfos.size()]);
      Set openOperInfos = new HashSet();
      if (oldOperInfos == null) {
         openOperInfos = null;
      } else {
         for(int i = 0; i < oldOperInfos.length; ++i) {
            if (oldOperInfos[i] == null) {
               openOperInfos.add((Object)null);
            } else {
               OpenMBeanOperationInfoSupport oldOperInfo = (OpenMBeanOperationInfoSupport)OpenMBeanOperationInfoSupport.class.cast(oldOperInfos[i]);
               MBeanParameterInfo[] oldParamInfos = oldOperInfo.getSignature();
               Set openParamInfos = new HashSet();
               if (oldParamInfos == null) {
                  openParamInfos = null;
               } else {
                  for(int j = 0; j < oldParamInfos.length; ++j) {
                     if (oldParamInfos[j] == null) {
                        openParamInfos.add((Object)null);
                     }

                     OpenMBeanParameterInfoSupport oldParamInfo = (OpenMBeanParameterInfoSupport)OpenMBeanParameterInfoSupport.class.cast(oldParamInfos[j]);
                     OpenMBeanParameterInfoSupport openParamInfo = new OpenMBeanParameterInfoSupport(oldParamInfo.getName(), oldParamInfo.getDescription(), oldParamInfo.getOpenType(), oldParamInfo.getDescriptor());
                     openParamInfos.add(openParamInfo);
                  }
               }

               OpenMBeanParameterInfo[] openParamInfoArray = openParamInfos == null ? null : (OpenMBeanParameterInfo[])openParamInfos.toArray(new OpenMBeanParameterInfo[openParamInfos.size()]);
               OpenMBeanOperationInfo openOperInfo = new OpenMBeanOperationInfoSupport(oldOperInfo.getName(), oldOperInfo.getDescription(), openParamInfoArray, oldOperInfo.getReturnOpenType(), oldOperInfo.getImpact(), oldOperInfo.getDescriptor());
               openOperInfos.add(openOperInfo);
            }
         }
      }

      OpenMBeanOperationInfo[] openOperInfoArray = openOperInfos == null ? null : (OpenMBeanOperationInfo[])openOperInfos.toArray(new OpenMBeanOperationInfo[openOperInfos.size()]);
      Set openConsInfos = new HashSet();
      MBeanConstructorInfo[] oldConsInfos = mbeanInfo.getConstructors();
      if (oldConsInfos == null) {
         openConsInfos = null;
      } else {
         for(int i = 0; i < oldConsInfos.length; ++i) {
            if (oldConsInfos[i] == null) {
               openConsInfos.add((Object)null);
            } else {
               OpenMBeanConstructorInfoSupport oldConsInfo = (OpenMBeanConstructorInfoSupport)OpenMBeanConstructorInfoSupport.class.cast(oldConsInfos[i]);
               MBeanParameterInfo[] oldParams = oldConsInfo.getSignature();
               Set openParamInfos = new HashSet();
               if (oldParams == null) {
                  openParamInfos = null;
               } else {
                  for(int j = 0; j < oldParams.length; ++j) {
                     if (oldParams[j] == null) {
                        openParamInfos.add((Object)null);
                     }

                     OpenMBeanParameterInfoSupport oldParamInfo = (OpenMBeanParameterInfoSupport)OpenMBeanParameterInfoSupport.class.cast(oldParams[j]);
                     OpenMBeanParameterInfoSupport openParamInfo = new OpenMBeanParameterInfoSupport(oldParamInfo.getName(), oldParamInfo.getDescription(), oldParamInfo.getOpenType(), oldParamInfo.getDescriptor());
                     openParamInfos.add(openParamInfo);
                  }
               }

               OpenMBeanParameterInfo[] openParamInfoArray = openParamInfos == null ? null : (OpenMBeanParameterInfo[])openParamInfos.toArray(new OpenMBeanParameterInfo[openParamInfos.size()]);
               OpenMBeanConstructorInfo modelConsInfo = new OpenMBeanConstructorInfoSupport(oldConsInfos[i].getName(), oldConsInfos[i].getDescription(), openParamInfoArray);
               openConsInfos.add(modelConsInfo);
            }
         }
      }

      OpenMBeanConstructorInfo[] openConsInfoArray = openConsInfos == null ? null : (OpenMBeanConstructorInfo[])openConsInfos.toArray(new OpenMBeanConstructorInfo[openConsInfos.size()]);
      return new OpenMBeanInfoSupport(mbeanInfo.getClassName(), mbeanInfo.getDescription(), openAttrInfoArray, openConsInfoArray, openOperInfoArray, mbeanInfo.getNotifications(), mbeanInfo.getDescriptor());
   }

   public static MBeanInfo buildMBeanInfoWithAnno(ObjectName objectName, MBeanInfo mbeanInfo, String partitionName) {
      if (mbeanInfo != null && objectName != null) {
         String globalPartitionName = "DOMAIN";
         if (partitionName != null && !partitionName.equals(globalPartitionName)) {
            if (MBeanPartitionUtil.showCoherenceMBeanInPartitionContext(partitionName, objectName)) {
               return mbeanInfo;
            } else {
               ComponentInvocationContext mbeanCIC = MBeanCICInterceptor.getCICForMBean(objectName);
               if (mbeanCIC == null || mbeanCIC.getPartitionName() != null && (!mbeanCIC.getPartitionName().equals(globalPartitionName) || mbeanCIC.getPartitionName().equals(globalPartitionName) && mbeanCIC.getApplicationId() != null)) {
                  return mbeanInfo;
               } else {
                  MBeanAttributeInfo[] newAttributeinfos = updateAttributeInfoBasedOnAnno(objectName, mbeanInfo);
                  MBeanOperationInfo[] newOperationInfos = updateOperationInfoBasedOnAnno(objectName, mbeanInfo);
                  if (mbeanInfo instanceof ModelMBeanInfo) {
                     return rebuildModelMBeanInfo(mbeanInfo, newAttributeinfos, newOperationInfos);
                  } else {
                     return mbeanInfo instanceof OpenMBeanInfo ? rebuildOpenMBeanInfo(mbeanInfo, newAttributeinfos, newOperationInfos) : new MBeanInfo(mbeanInfo.getClassName(), mbeanInfo.getDescription(), newAttributeinfos, mbeanInfo.getConstructors(), newOperationInfos, mbeanInfo.getNotifications(), mbeanInfo.getDescriptor());
                  }
               }
            }
         } else {
            return mbeanInfo;
         }
      } else {
         return null;
      }
   }

   public static MBeanOperationInfo[] updateOperationInfoBasedOnAnno(ObjectName objectName, MBeanInfo mbeanInfo) {
      if (mbeanInfo != null && objectName != null) {
         MBeanOperationInfo[] oldOperInfos = mbeanInfo.getOperations();
         if (oldOperInfos != null && oldOperInfos.length != 0) {
            Set newOperInfos = new HashSet();

            for(int i = 0; i < oldOperInfos.length; ++i) {
               if (globalMBeansVisibleToPartitions) {
                  if (isVisibleToPartitionsNeverSetOnInterface(objectName, mbeanInfo)) {
                     if (isVisibleToPartitionsAlwaysSetOnOperation(objectName, oldOperInfos[i])) {
                        newOperInfos.add(oldOperInfos[i]);
                     }
                  } else if (!isVisibleToPartitionsNeverSetOnOperation(objectName, oldOperInfos[i])) {
                     newOperInfos.add(oldOperInfos[i]);
                  }
               } else if (isVisibleToPartitionsAlwaysSetOnInterface(objectName, mbeanInfo)) {
                  if (!isVisibleToPartitionsNeverSetOnOperation(objectName, oldOperInfos[i])) {
                     newOperInfos.add(oldOperInfos[i]);
                  }
               } else if (isVisibleToPartitionsAlwaysSetOnOperation(objectName, oldOperInfos[i])) {
                  newOperInfos.add(oldOperInfos[i]);
               }
            }

            if (mbeanInfo instanceof ModelMBeanInfo) {
               return (ModelMBeanOperationInfo[])((ModelMBeanOperationInfo[])newOperInfos.toArray(new ModelMBeanOperationInfo[newOperInfos.size()]));
            } else if (mbeanInfo instanceof OpenMBeanInfo) {
               return (OpenMBeanOperationInfoSupport[])((OpenMBeanOperationInfoSupport[])newOperInfos.toArray(new OpenMBeanOperationInfoSupport[newOperInfos.size()]));
            } else {
               return (MBeanOperationInfo[])((MBeanOperationInfo[])newOperInfos.toArray(new MBeanOperationInfo[newOperInfos.size()]));
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static MBeanAttributeInfo[] updateAttributeInfoBasedOnAnno(ObjectName objectName, MBeanInfo mbeanInfo) {
      if (objectName != null && mbeanInfo != null) {
         MBeanAttributeInfo[] oldAttrInfos = mbeanInfo.getAttributes();
         if (oldAttrInfos != null && oldAttrInfos.length != 0) {
            Set newAttrInfos = new HashSet();

            for(int i = 0; i < oldAttrInfos.length; ++i) {
               if (globalMBeansVisibleToPartitions) {
                  if (isVisibleToPartitionsNeverSetOnInterface(objectName, mbeanInfo)) {
                     if (isVisibleToPartitionsAlwaysSetOnAttribute(objectName, oldAttrInfos[i])) {
                        newAttrInfos.add(oldAttrInfos[i]);
                     }
                  } else if (!isVisibleToPartitionsNeverSetOnAttribute(objectName, oldAttrInfos[i])) {
                     newAttrInfos.add(oldAttrInfos[i]);
                  }
               } else if (isVisibleToPartitionsAlwaysSetOnInterface(objectName, mbeanInfo)) {
                  if (!isVisibleToPartitionsNeverSetOnAttribute(objectName, oldAttrInfos[i])) {
                     newAttrInfos.add(oldAttrInfos[i]);
                  }
               } else if (isVisibleToPartitionsAlwaysSetOnAttribute(objectName, oldAttrInfos[i])) {
                  newAttrInfos.add(oldAttrInfos[i]);
               }
            }

            if (mbeanInfo instanceof ModelMBeanInfo) {
               return (ModelMBeanAttributeInfo[])((ModelMBeanAttributeInfo[])newAttrInfos.toArray(new ModelMBeanAttributeInfo[newAttrInfos.size()]));
            } else if (mbeanInfo instanceof OpenMBeanInfo) {
               return (OpenMBeanAttributeInfoSupport[])((OpenMBeanAttributeInfoSupport[])newAttrInfos.toArray(new OpenMBeanAttributeInfoSupport[newAttrInfos.size()]));
            } else {
               return (MBeanAttributeInfo[])((MBeanAttributeInfo[])newAttrInfos.toArray(new MBeanAttributeInfo[newAttrInfos.size()]));
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean sureMBeanVisibleToPartitions(ObjectName objectName, MBeanInfo mbeanInfo) {
      if (objectName != null && mbeanInfo != null) {
         if (globalMBeansVisibleToPartitions) {
            if (isVisibleToPartitionsNeverSetOnInterface(objectName, mbeanInfo)) {
               return !isVisibleToPartitionsNeverSetAllOrNone(objectName, mbeanInfo);
            } else {
               return true;
            }
         } else if (isVisibleToPartitionsNeverSetOnInterface(objectName, mbeanInfo) && isVisibleToPartitionsNeverSetAllOrNone(objectName, mbeanInfo)) {
            return false;
         } else if (!isVisibleToPartitionsSetOnInterface(objectName, mbeanInfo)) {
            return !isVisibleToPartitionsNeverSetAllOrNone(objectName, mbeanInfo);
         } else {
            return true;
         }
      } else {
         return globalMBeansVisibleToPartitions;
      }
   }

   public static boolean sureAttributeVisibleToPartitions(ObjectName objectName, MBeanInfo mBeanInfo, String attribute) {
      if (objectName != null && mBeanInfo != null) {
         MBeanAttributeInfo attrInfo = null;
         MBeanAttributeInfo[] attrInfos = mBeanInfo.getAttributes();

         for(int i = 0; i < attrInfos.length; ++i) {
            if (attrInfos[i] != null && attrInfos[i].getName().equals(attribute)) {
               attrInfo = attrInfos[i];
               break;
            }
         }

         if (!globalMBeansVisibleToPartitions && !isVisibleToPartitionsSetOnInterface(objectName, mBeanInfo) && !isVisibleToPartitionsSetOnAttribute(objectName, attrInfo)) {
            return false;
         } else {
            if (isVisibleToPartitionsNeverSetOnInterface(objectName, mBeanInfo)) {
               if (!isVisibleToPartitionsSetOnAttribute(objectName, attrInfo) || isVisibleToPartitionsNeverSetOnAttribute(objectName, attrInfo)) {
                  return false;
               }
            } else if (isVisibleToPartitionsNeverSetOnAttribute(objectName, attrInfo)) {
               return false;
            }

            return true;
         }
      } else {
         return globalMBeansVisibleToPartitions;
      }
   }

   public static boolean sureOperationVisibleToPartitions(ObjectName objectName, MBeanInfo mBeanInfo, String operation, String[] paramTypes) {
      if (objectName != null && mBeanInfo != null) {
         MBeanOperationInfo operInfo = null;
         MBeanOperationInfo[] operInfos = mBeanInfo.getOperations();

         for(int i = 0; i < operInfos.length; ++i) {
            if (operInfos[i] != null && operInfos[i].getName().equals(operation)) {
               MBeanParameterInfo[] paramInfos = operInfos[i].getSignature();
               if (paramTypes == null) {
                  if (paramInfos == null || paramInfos.length == 0) {
                     operInfo = operInfos[i];
                     break;
                  }
               } else if (paramTypes.length == paramInfos.length) {
                  boolean hasDifferentParamType = false;

                  for(int j = 0; j < paramInfos.length; ++j) {
                     if (!paramInfos[j].getType().equals(paramTypes[j])) {
                        hasDifferentParamType = true;
                        break;
                     }
                  }

                  if (!hasDifferentParamType) {
                     operInfo = operInfos[i];
                     break;
                  }
               }
            }
         }

         if (!globalMBeansVisibleToPartitions && !isVisibleToPartitionsSetOnInterface(objectName, mBeanInfo) && !isVisibleToPartitionsSetOnOperation(objectName, operInfo)) {
            return false;
         } else {
            if (isVisibleToPartitionsNeverSetOnInterface(objectName, mBeanInfo)) {
               if (!isVisibleToPartitionsSetOnOperation(objectName, operInfo) || isVisibleToPartitionsNeverSetOnOperation(objectName, operInfo)) {
                  return false;
               }
            } else if (isVisibleToPartitionsNeverSetOnOperation(objectName, operInfo)) {
               return false;
            }

            return true;
         }
      } else {
         return globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsSetOnInterface(ObjectName oname, MBeanInfo mBeanInfo) {
      if (oname != null && mBeanInfo != null) {
         Descriptor descriptor = mBeanInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null;
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsSetOnAttribute(ObjectName oname, MBeanAttributeInfo attrInfo) {
      if (oname != null && attrInfo != null) {
         Descriptor descriptor = attrInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null;
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsSetOnOperation(ObjectName oname, MBeanOperationInfo operInfo) {
      if (oname != null && operInfo != null) {
         Descriptor descriptor = operInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null;
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsAlwaysSetOnInterface(ObjectName oname, MBeanInfo mBeanInfo) {
      if (oname != null && mBeanInfo != null) {
         Descriptor descriptor = mBeanInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("ALWAYS");
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsAlwaysSetOnAttribute(ObjectName oname, MBeanAttributeInfo attrInfo) {
      if (oname != null && attrInfo != null) {
         Descriptor descriptor = attrInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("ALWAYS");
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsAlwaysSetOnOperation(ObjectName oname, MBeanOperationInfo operInfo) {
      if (oname != null && operInfo != null) {
         Descriptor descriptor = operInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("ALWAYS");
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsNeverSetAllOrNone(ObjectName oname, MBeanInfo mBeanInfo) {
      if (oname != null && mBeanInfo != null) {
         Descriptor descriptor = mBeanInfo.getDescriptor();
         MBeanOperationInfo[] operationInfos = mBeanInfo.getOperations();
         if (operationInfos == null) {
            return false;
         } else {
            boolean foundAlways = false;

            for(int i = 0; i < operationInfos.length; ++i) {
               if (operationInfos[i] != null) {
                  descriptor = operationInfos[i].getDescriptor();
                  if (getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("ALWAYS")) {
                     foundAlways = true;
                     break;
                  }
               }
            }

            if (foundAlways) {
               return false;
            } else {
               MBeanAttributeInfo[] attributeInfos = mBeanInfo.getAttributes();
               if (attributeInfos == null) {
                  return false;
               } else {
                  for(int i = 0; i < attributeInfos.length; ++i) {
                     if (attributeInfos[i] != null) {
                        descriptor = attributeInfos[i].getDescriptor();
                        if (getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("ALWAYS") && (!MBeanNameUtil.isWLSMBean(oname) || !attributeInfos[i].getName().equals("Type") && !attributeInfos[i].getName().equals("Name") && !attributeInfos[i].getName().equals("Notes"))) {
                           foundAlways = true;
                           break;
                        }
                     }
                  }

                  return !foundAlways;
               }
            }
         }
      } else {
         return globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsNeverSetOnInterface(ObjectName oname, MBeanInfo mBeanInfo) {
      if (oname != null && mBeanInfo != null) {
         Descriptor descriptor = mBeanInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("NEVER");
      } else {
         return globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsNeverSetOnAttribute(ObjectName oname, MBeanAttributeInfo attrInfo) {
      if (oname != null && attrInfo != null) {
         Descriptor descriptor = attrInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("NEVER");
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   public static boolean isVisibleToPartitionsNeverSetOnOperation(ObjectName oname, MBeanOperationInfo operInfo) {
      if (oname != null && operInfo != null) {
         Descriptor descriptor = operInfo.getDescriptor();
         return getAnnoFieldValue(descriptor) != null && getAnnoFieldValue(descriptor).equals("NEVER");
      } else {
         return !globalMBeansVisibleToPartitions;
      }
   }

   private static String getAnnoFieldValue(Descriptor descriptor) {
      if (descriptor == null) {
         return null;
      } else if (descriptor.getFieldValue("com.bea.VisibleToPartitions") != null) {
         return (String)descriptor.getFieldValue("com.bea.VisibleToPartitions");
      } else {
         return descriptor.getFieldValue("VisibleToPartitions") != null ? (String)descriptor.getFieldValue("VisibleToPartitions") : null;
      }
   }
}
