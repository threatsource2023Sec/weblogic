package kodo.jdbc.conf.customizers;

import kodo.conf.descriptor.ClusterRemoteCommitProviderBean;
import kodo.conf.descriptor.CustomRemoteCommitProviderBean;
import kodo.conf.descriptor.ExecutionContextNameProviderBean;
import kodo.conf.descriptor.ExportProfilingBean;
import kodo.conf.descriptor.GUIJMXBean;
import kodo.conf.descriptor.GUIProfilingBean;
import kodo.conf.descriptor.JMSRemoteCommitProviderBean;
import kodo.conf.descriptor.JMX2JMXBean;
import kodo.conf.descriptor.JMXBean;
import kodo.conf.descriptor.LocalJMXBean;
import kodo.conf.descriptor.LocalProfilingBean;
import kodo.conf.descriptor.MX4J1JMXBean;
import kodo.conf.descriptor.NoneJMXBean;
import kodo.conf.descriptor.NoneProfilingBean;
import kodo.conf.descriptor.ProfilingBean;
import kodo.conf.descriptor.RemoteCommitProviderBean;
import kodo.conf.descriptor.SingleJVMRemoteCommitProviderBean;
import kodo.conf.descriptor.StackExecutionContextNameProviderBean;
import kodo.conf.descriptor.TCPRemoteCommitProviderBean;
import kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBean;
import kodo.conf.descriptor.UserObjectExecutionContextNameProviderBean;
import kodo.conf.descriptor.WLS81JMXBean;
import kodo.jdbc.conf.descriptor.AccessDictionaryBean;
import kodo.jdbc.conf.descriptor.CustomDictionaryBean;
import kodo.jdbc.conf.descriptor.DB2DictionaryBean;
import kodo.jdbc.conf.descriptor.DBDictionaryBean;
import kodo.jdbc.conf.descriptor.DerbyDictionaryBean;
import kodo.jdbc.conf.descriptor.EmpressDictionaryBean;
import kodo.jdbc.conf.descriptor.FoxProDictionaryBean;
import kodo.jdbc.conf.descriptor.HSQLDictionaryBean;
import kodo.jdbc.conf.descriptor.InformixDictionaryBean;
import kodo.jdbc.conf.descriptor.JDataStoreDictionaryBean;
import kodo.jdbc.conf.descriptor.MySQLDictionaryBean;
import kodo.jdbc.conf.descriptor.OracleDictionaryBean;
import kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean;
import kodo.jdbc.conf.descriptor.PointbaseDictionaryBean;
import kodo.jdbc.conf.descriptor.PostgresDictionaryBean;
import kodo.jdbc.conf.descriptor.SQLServerDictionaryBean;
import kodo.jdbc.conf.descriptor.SybaseDictionaryBean;

public class PersistenceUnitConfigurationBeanCustomizer {
   private final PersistenceUnitConfigurationBean customized;

   public PersistenceUnitConfigurationBeanCustomizer(PersistenceUnitConfigurationBean custom) {
      this.customized = custom;
   }

   public Class[] getRemoteCommitProviderTypes() {
      return new Class[]{JMSRemoteCommitProviderBean.class, SingleJVMRemoteCommitProviderBean.class, TCPRemoteCommitProviderBean.class, ClusterRemoteCommitProviderBean.class, CustomRemoteCommitProviderBean.class};
   }

   public RemoteCommitProviderBean getRemoteCommitProvider() {
      RemoteCommitProviderBean provider = null;
      provider = this.customized.getJMSRemoteCommitProvider();
      if (provider != null) {
         return provider;
      } else {
         RemoteCommitProviderBean provider = this.customized.getSingleJVMRemoteCommitProvider();
         if (provider != null) {
            return provider;
         } else {
            RemoteCommitProviderBean provider = this.customized.getTCPRemoteCommitProvider();
            if (provider != null) {
               return provider;
            } else {
               RemoteCommitProviderBean provider = this.customized.getClusterRemoteCommitProvider();
               if (provider != null) {
                  return provider;
               } else {
                  RemoteCommitProviderBean provider = this.customized.getCustomRemoteCommitProvider();
                  return provider;
               }
            }
         }
      }
   }

   public RemoteCommitProviderBean createRemoteCommitProvider(Class type) {
      this.destroyRemoteCommitProvider();
      RemoteCommitProviderBean provider = null;
      if (JMSRemoteCommitProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createJMSRemoteCommitProvider();
      } else if (SingleJVMRemoteCommitProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createSingleJVMRemoteCommitProvider();
      } else if (TCPRemoteCommitProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createTCPRemoteCommitProvider();
      } else if (ClusterRemoteCommitProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createClusterRemoteCommitProvider();
      } else if (CustomRemoteCommitProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createCustomRemoteCommitProvider();
      }

      if (provider != null) {
         ((RemoteCommitProviderBean)provider).setName("RemoteCommitProvider");
      }

      return (RemoteCommitProviderBean)provider;
   }

   public void destroyRemoteCommitProvider() {
      RemoteCommitProviderBean provider = null;
      provider = this.customized.getJMSRemoteCommitProvider();
      if (provider != null) {
         this.customized.destroyJMSRemoteCommitProvider();
      } else {
         RemoteCommitProviderBean provider = this.customized.getSingleJVMRemoteCommitProvider();
         if (provider != null) {
            this.customized.destroySingleJVMRemoteCommitProvider();
         } else {
            RemoteCommitProviderBean provider = this.customized.getTCPRemoteCommitProvider();
            if (provider != null) {
               this.customized.destroyTCPRemoteCommitProvider();
            } else {
               RemoteCommitProviderBean provider = this.customized.getClusterRemoteCommitProvider();
               if (provider != null) {
                  this.customized.destroyClusterRemoteCommitProvider();
               } else {
                  RemoteCommitProviderBean provider = this.customized.getCustomRemoteCommitProvider();
                  if (provider != null) {
                     this.customized.destroyCustomRemoteCommitProvider();
                  }

               }
            }
         }
      }
   }

   public Class[] getDBDictionaryTypes() {
      return new Class[]{AccessDictionaryBean.class, DB2DictionaryBean.class, DerbyDictionaryBean.class, EmpressDictionaryBean.class, FoxProDictionaryBean.class, HSQLDictionaryBean.class, InformixDictionaryBean.class, JDataStoreDictionaryBean.class, MySQLDictionaryBean.class, OracleDictionaryBean.class, PointbaseDictionaryBean.class, PostgresDictionaryBean.class, SQLServerDictionaryBean.class, SybaseDictionaryBean.class, CustomDictionaryBean.class};
   }

   public DBDictionaryBean getDBDictionary() {
      DBDictionaryBean provider = null;
      provider = this.customized.getAccessDictionary();
      if (provider != null) {
         return provider;
      } else {
         DBDictionaryBean provider = this.customized.getDB2Dictionary();
         if (provider != null) {
            return provider;
         } else {
            DBDictionaryBean provider = this.customized.getDerbyDictionary();
            if (provider != null) {
               return provider;
            } else {
               DBDictionaryBean provider = this.customized.getEmpressDictionary();
               if (provider != null) {
                  return provider;
               } else {
                  DBDictionaryBean provider = this.customized.getFoxProDictionary();
                  if (provider != null) {
                     return provider;
                  } else {
                     DBDictionaryBean provider = this.customized.getHSQLDictionary();
                     if (provider != null) {
                        return provider;
                     } else {
                        DBDictionaryBean provider = this.customized.getInformixDictionary();
                        if (provider != null) {
                           return provider;
                        } else {
                           DBDictionaryBean provider = this.customized.getJDataStoreDictionary();
                           if (provider != null) {
                              return provider;
                           } else {
                              DBDictionaryBean provider = this.customized.getMySQLDictionary();
                              if (provider != null) {
                                 return provider;
                              } else {
                                 DBDictionaryBean provider = this.customized.getOracleDictionary();
                                 if (provider != null) {
                                    return provider;
                                 } else {
                                    DBDictionaryBean provider = this.customized.getPointbaseDictionary();
                                    if (provider != null) {
                                       return provider;
                                    } else {
                                       DBDictionaryBean provider = this.customized.getPostgresDictionary();
                                       if (provider != null) {
                                          return provider;
                                       } else {
                                          DBDictionaryBean provider = this.customized.getSQLServerDictionary();
                                          if (provider != null) {
                                             return provider;
                                          } else {
                                             DBDictionaryBean provider = this.customized.getSybaseDictionary();
                                             return (DBDictionaryBean)(provider != null ? provider : this.customized.getCustomDictionary());
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public DBDictionaryBean createDBDictionary(Class type) {
      this.destroyDBDictionary();
      DBDictionaryBean dictBean = null;
      if (AccessDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createAccessDictionary();
      } else if (DB2DictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createDB2Dictionary();
      } else if (DerbyDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createDerbyDictionary();
      } else if (EmpressDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createEmpressDictionary();
      } else if (FoxProDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createFoxProDictionary();
      } else if (HSQLDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createHSQLDictionary();
      } else if (InformixDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createInformixDictionary();
      } else if (JDataStoreDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createJDataStoreDictionary();
      } else if (MySQLDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createMySQLDictionary();
      } else if (OracleDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createOracleDictionary();
      } else if (PointbaseDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createPointbaseDictionary();
      } else if (PostgresDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createPostgresDictionary();
      } else if (SQLServerDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createSQLServerDictionary();
      } else if (SybaseDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createSybaseDictionary();
      } else if (CustomDictionaryBean.class.getName().equals(type.getName())) {
         dictBean = this.customized.createCustomDictionary();
      }

      if (dictBean != null) {
         ((DBDictionaryBean)dictBean).setName("DBDictionary");
      }

      return (DBDictionaryBean)dictBean;
   }

   public void destroyDBDictionary() {
      DBDictionaryBean provider = null;
      provider = this.customized.getAccessDictionary();
      if (provider != null) {
         this.customized.destroyAccessDictionary();
      } else {
         DBDictionaryBean provider = this.customized.getDB2Dictionary();
         if (provider != null) {
            this.customized.destroyDB2Dictionary();
         } else {
            DBDictionaryBean provider = this.customized.getDerbyDictionary();
            if (provider != null) {
               this.customized.destroyDerbyDictionary();
            } else {
               DBDictionaryBean provider = this.customized.getEmpressDictionary();
               if (provider != null) {
                  this.customized.destroyEmpressDictionary();
               } else {
                  DBDictionaryBean provider = this.customized.getFoxProDictionary();
                  if (provider != null) {
                     this.customized.destroyFoxProDictionary();
                  } else {
                     DBDictionaryBean provider = this.customized.getHSQLDictionary();
                     if (provider != null) {
                        this.customized.destroyHSQLDictionary();
                     } else {
                        DBDictionaryBean provider = this.customized.getInformixDictionary();
                        if (provider != null) {
                           this.customized.destroyInformixDictionary();
                        } else {
                           DBDictionaryBean provider = this.customized.getJDataStoreDictionary();
                           if (provider != null) {
                              this.customized.destroyJDataStoreDictionary();
                           } else {
                              DBDictionaryBean provider = this.customized.getMySQLDictionary();
                              if (provider != null) {
                                 this.customized.destroyMySQLDictionary();
                              } else {
                                 DBDictionaryBean provider = this.customized.getOracleDictionary();
                                 if (provider != null) {
                                    this.customized.destroyOracleDictionary();
                                 } else {
                                    DBDictionaryBean provider = this.customized.getPointbaseDictionary();
                                    if (provider != null) {
                                       this.customized.destroyPointbaseDictionary();
                                    } else {
                                       DBDictionaryBean provider = this.customized.getPostgresDictionary();
                                       if (provider != null) {
                                          this.customized.destroyPostgresDictionary();
                                       } else {
                                          DBDictionaryBean provider = this.customized.getSQLServerDictionary();
                                          if (provider != null) {
                                             this.customized.destroySQLServerDictionary();
                                          } else {
                                             DBDictionaryBean provider = this.customized.getSybaseDictionary();
                                             if (provider != null) {
                                                this.customized.destroySybaseDictionary();
                                             } else {
                                                DBDictionaryBean provider = this.customized.getCustomDictionary();
                                                this.customized.destroyCustomDictionary();
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public Class[] getExecutionContextNameProviderTypes() {
      return new Class[]{StackExecutionContextNameProviderBean.class, TransactionNameExecutionContextNameProviderBean.class, UserObjectExecutionContextNameProviderBean.class};
   }

   public ExecutionContextNameProviderBean getExecutionContextNameProvider() {
      ExecutionContextNameProviderBean provider = null;
      provider = this.customized.getStackExecutionContextNameProvider();
      if (provider != null) {
         return provider;
      } else {
         ExecutionContextNameProviderBean provider = this.customized.getTransactionNameExecutionContextNameProvider();
         if (provider != null) {
            return provider;
         } else {
            ExecutionContextNameProviderBean provider = this.customized.getUserObjectExecutionContextNameProvider();
            return provider != null ? provider : provider;
         }
      }
   }

   public ExecutionContextNameProviderBean createExecutionContextNameProvider(Class type) {
      this.destroyExecutionContextNameProvider();
      ExecutionContextNameProviderBean provider = null;
      if (StackExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createStackExecutionContextNameProvider();
      } else if (TransactionNameExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createTransactionNameExecutionContextNameProvider();
      } else if (UserObjectExecutionContextNameProviderBean.class.getName().equals(type.getName())) {
         provider = this.customized.createUserObjectExecutionContextNameProvider();
      }

      return (ExecutionContextNameProviderBean)provider;
   }

   public void destroyExecutionContextNameProvider() {
      ExecutionContextNameProviderBean provider = null;
      provider = this.customized.getStackExecutionContextNameProvider();
      if (provider != null) {
         this.customized.destroyStackExecutionContextNameProvider();
      } else {
         ExecutionContextNameProviderBean provider = this.customized.getTransactionNameExecutionContextNameProvider();
         if (provider != null) {
            this.customized.destroyTransactionNameExecutionContextNameProvider();
         } else {
            ExecutionContextNameProviderBean provider = this.customized.getUserObjectExecutionContextNameProvider();
            if (provider != null) {
               this.customized.destroyUserObjectExecutionContextNameProvider();
            }
         }
      }
   }

   public Class[] getJMXTypes() {
      return new Class[]{NoneJMXBean.class, LocalJMXBean.class, GUIJMXBean.class, JMX2JMXBean.class, MX4J1JMXBean.class, WLS81JMXBean.class};
   }

   public JMXBean getJMX() {
      JMXBean provider = null;
      provider = this.customized.getNoneJMX();
      if (provider != null) {
         return provider;
      } else {
         JMXBean provider = this.customized.getLocalJMX();
         if (provider != null) {
            return provider;
         } else {
            JMXBean provider = this.customized.getGUIJMX();
            if (provider != null) {
               return provider;
            } else {
               JMXBean provider = this.customized.getJMX2JMX();
               if (provider != null) {
                  return provider;
               } else {
                  JMXBean provider = this.customized.getMX4J1JMX();
                  if (provider != null) {
                     return provider;
                  } else {
                     JMXBean provider = this.customized.getWLS81JMX();
                     return provider != null ? provider : null;
                  }
               }
            }
         }
      }
   }

   public JMXBean createJMX(Class type) {
      this.destroyJMX();
      JMXBean provider = null;
      if (NoneJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createNoneJMX();
      } else if (LocalJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createLocalJMX();
      } else if (GUIJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createGUIJMX();
      } else if (JMX2JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createJMX2JMX();
      } else if (MX4J1JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createMX4J1JMX();
      } else if (WLS81JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createWLS81JMX();
      }

      return (JMXBean)provider;
   }

   public void destroyJMX() {
      JMXBean provider = null;
      provider = this.customized.getNoneJMX();
      if (provider != null) {
         this.customized.destroyNoneJMX();
      } else {
         JMXBean provider = this.customized.getLocalJMX();
         if (provider != null) {
            this.customized.destroyLocalJMX();
         } else {
            JMXBean provider = this.customized.getGUIJMX();
            if (provider != null) {
               this.customized.destroyGUIJMX();
            } else {
               JMXBean provider = this.customized.getJMX2JMX();
               if (provider != null) {
                  this.customized.destroyJMX2JMX();
               } else {
                  JMXBean provider = this.customized.getMX4J1JMX();
                  if (provider != null) {
                     this.customized.destroyMX4J1JMX();
                  } else {
                     JMXBean provider = this.customized.getWLS81JMX();
                     if (provider != null) {
                        this.customized.destroyWLS81JMX();
                     }
                  }
               }
            }
         }
      }
   }

   public Class[] getProfilingTypes() {
      return new Class[]{NoneProfilingBean.class, LocalProfilingBean.class, ExportProfilingBean.class, GUIProfilingBean.class};
   }

   public ProfilingBean getProfiling() {
      ProfilingBean provider = null;
      provider = this.customized.getNoneProfiling();
      if (provider != null) {
         return provider;
      } else {
         ProfilingBean provider = this.customized.getLocalProfiling();
         if (provider != null) {
            return provider;
         } else {
            ProfilingBean provider = this.customized.getExportProfiling();
            if (provider != null) {
               return provider;
            } else {
               ProfilingBean provider = this.customized.getGUIProfiling();
               return provider != null ? provider : null;
            }
         }
      }
   }

   public ProfilingBean createProfiling(Class type) {
      this.destroyProfiling();
      ProfilingBean provider = null;
      if (NoneProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createNoneProfiling();
      } else if (LocalProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createLocalProfiling();
      } else if (ExportProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createExportProfiling();
      } else if (GUIProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createGUIProfiling();
      }

      return (ProfilingBean)provider;
   }

   public void destroyProfiling() {
      ProfilingBean provider = null;
      provider = this.customized.getNoneProfiling();
      if (provider != null) {
         this.customized.destroyNoneProfiling();
      } else {
         ProfilingBean provider = this.customized.getLocalProfiling();
         if (provider != null) {
            this.customized.destroyLocalProfiling();
         } else {
            ProfilingBean provider = this.customized.getExportProfiling();
            if (provider != null) {
               this.customized.destroyExportProfiling();
            } else {
               ProfilingBean provider = this.customized.getGUIProfiling();
               if (provider != null) {
                  this.customized.destroyGUIProfiling();
               }
            }
         }
      }
   }
}
