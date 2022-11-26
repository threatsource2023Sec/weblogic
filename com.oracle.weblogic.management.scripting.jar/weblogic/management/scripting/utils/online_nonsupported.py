# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011,2016, Oracle and/or its affiliates. All rights reserved.

"""
Internal script that makes all these Online WLST commands NO-OP
once a user disconnects from a running server.

"""

def unsupported():
  print ''
  print WLSTMsgTextFormatter.getInstance().getNotSupportedWhenNotConnected()
  print ''
  return

def invoke(methodName, parameters, signatures):
  unsupported()
  
def runtime():
  unsupported()
  
def config():
  unsupported()
  
def custom():
  unsupported()

def domainCustom(objectNamePattern=None):
  unsupported()
  
def editCustom(objectNamePattern=None):
  unsupported()
  
def listCustomMBeans():
  unsupported()
  
def adminConfig():
  unsupported()
  
def reset():
  unsupported()
  
def shutdown(name="NONAME", entityType='Server', ignoreSessions='true',timeOut=0, force='false',block='true',properties=None):
  unsupported()
  
def getTarget(path):
  unsupported()
  
def getTargetArray(type, values):
  unsupported()
  
def suspend(sname=None, ignoreSessions="false", timeOut=0, force="false", block = "true"):
  unsupported()
  
def resume(sname=None, block="true"):
  unsupported()
  
def migrate(serverName, destinationName, sourceDown='false', destinationDown='false', migrationType='all'):
  unsupported()
  
def migrateServer(serverName, machineName, sourceDown='false', destinationDown='false'):
  unsupported()
  
def migrateAll(serverName,
                  destinationName,
                  sourceDown='false',
                  destinationDown='false'):
  unsupported()
  
def deploy(appName,path,targets="",stageMode=None,planPath=None,**options):
  unsupported()
  
def redeploy(appName, planPath=None, **options):
  unsupported()
  
def undeploy(appName,targets=None, **options):
  unsupported()

def state(name, type="Server", returnMap="false"):
  unsupported()

def saveDomain():
  unsupported()
  
def disconnect(force="false"):
  unsupported()
  
def monitorAttribute(attrName, interval, runInBackGround = 'false', path=None, name=None, printTime=None, printCount=None):
  unsupported()

def stopAllMonitors():
  unsupported()
  
def serverConfig():
  unsupported()
  
def serverRuntime():
  unsupported()
  
def domainConfig():
  unsupported()
  
def domainRuntime():
  unsupported()
  
def edit(name=None):
  unsupported()
  
def stopMonitor(monName):
  unsupported()
  
def showMonitors():
  unsupported()
  
def jndi():
  unsupported()
  
def man(attrName=None):
  unsupported()
  
def manual(attrName=None):
  unsupported()
  
def listApplications():
  unsupported()

def listChildTypes(parent=None):
  unsupported()

def createEditSession(name,
                      description=None):
  unsupported()

def destroyEditSession(name, force = 0):
  unsupported()

def showEditSession(name=None):
  unsupported()

def startEdit(waitTimeInMillis=0, timeOutInMillis=-1, exclusive="false"):
  unsupported()
  
def save():
  unsupported()

def resolve(stopOnConflict=false):
  unsupported()

def activate(timeout=60000, block='true'):
  unsupported()
  
def undo(unactivatedChanges='false', defaultAnswer='from_prompt'):
  unsupported()
  
def stopEdit(defaultAnswer='from_prompt'):
  unsupported()
  
def cancelEdit(defaultAnswer='from_prompt'):
  unsupported()
  
def getActivationTask():
  unsupported()
  
def nmEnroll(domainDir=None, nmHome=None):
  unsupported()
  
def distributeApplication(appPath, planPath=None, targets=None, **options ):
  unsupported()
  
def appendToExtensionLoader(codeSource, targets=None, **options ):
  unsupported()
  
def startApplication(appName, **options):
  unsupported()
  
def stopApplication(appName, **options):
  unsupported()
  
def updateApplication(appName, planPath=None, **options):
  unsupported()
  
def getWLDM():
  unsupported()

def showChanges(onlyInMemory="false"):
  unsupported()
  
def validate():
  unsupported()
  
def getMBean(mbeanPath=None):
  unsupported()
  
def getMBI(mbeanType=None):
  unsupported()
  
def threadDump(writeToFile="true", fileName=None, serverName = None):
  unsupported()  

def currentTree():
  unsupported()

def getConfigManager():
  unsupported()

def addListener(mbean, attributeNames=None, logFile=None, listenerName=None):
  unsupported()
  
def removeListener(mbean=None, listenerName=None):
  unsupported()
  
def showListeners():
  unsupported()
  
def viewMBean(mbean):
  unsupported()

def getPath(mbean):
  unsupported()
  
def nmGenBootStartupProps(serverName=None):
  unsupported()

def getAvailableCapturedImages() :
  unsupported()

def purgeCapturedImages(server=None, partition=None, age=None) :
  unsupported()

def saveDiagnosticImageCaptureFile(imageName, outputFile=None) :
  unsupported()

def saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, outputFile=None) :
  unsupported()

def softRestart(name="NONAME", block="true", properties=None, type=None):
  unsupported()

def scaleUp(clusterName="NONAME", numServers=1, updateConfiguration=false, block=true, timeoutSeconds=600, type="DynamicCluster"):
  unsupported()

def scaleDown(clusterName="NONAME", numServers=1, updateConfiguration=false, block=true, timeoutSeconds=300, type="DynamicCluster"):
  unsupported()

def rolloutUpdate(target, rolloutOracleHome="", backupOracleHome="", isRollback="false",
                   javaHome="", applicationProperties="", rolloutOptions="", **options):
  unsupported()

def rolloutOracleHome(target, rolloutOracleHome, backupOracleHome, isRollback="false", rolloutOptions="", **options):
  unsupported()

def rolloutJavaHome(target, javaHome, rolloutOptions="", **options):
  unsupported()

def rolloutApplications(target, applicationProperties, rolloutOptions="", **options):
  unsupported()

def rollingRestart(target, rolloutOptions="", **options):
  unsupported()

def resync(compName):
  unsupported()

def resyncAll():
  unsupported()

def pullComponentChanges(compName):
  unsupported()

def showComponentChanges(compName=None):
  unsupported()
 
