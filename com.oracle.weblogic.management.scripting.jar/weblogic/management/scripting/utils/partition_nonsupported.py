# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.

"""
Internal script that makes all these Online WLST commands NO-OP
when user is connected to partition

"""

def unsupported():
  print ''
  print WLSTMsgTextFormatter.getInstance().getNotSupportedInPartitionContext()
  print ''
  return

def shutdown(name="NONAME", entityType='Server', ignoreSessions='true',timeOut=0, force='false',block='true',properties=None):
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

def startServer(adminServerName="myserver",
                domainName="mydomain",
                url=None,
                username=None,
                password=None,
                domainDir=".",
                block='true',
                timeout=120000,
                useNM='true',
                overWriteRootDir='false',
                serverLog=None,
                systemProperties=None,
                jvmArgs = None,
                spaceAsJvmArgsDelimiter = 'false',
                generateDefaultConfig = 'false'):
  unsupported()

  
def state(name, type="Server", returnMap="false"):
  unsupported()
  
def monitorAttribute(attrName, interval, runInBackGround = 'false', path=None, name=None, printTime=None, printCount=None):
  unsupported()

def stopAllMonitors():
  unsupported()
  
def stopMonitor(monName):
  unsupported()
  
def showMonitors():
  unsupported()
  
### NodeManager

def nmConnect(username=None,
              password=None,
              host='localhost',
              port=-1,
              domainName=None,
              domainDir=None,
              nmType='ssl',
              verbose = 'false',
              **useBootProperties):
  unsupported()

def nmDisconnect():
  unsupported()

def nmEnroll(domainDir=None, nmHome=None):
  unsupported()
  
def nmServerLog(serverName='myserver', writer=None, serverType='WebLogic'):
  unsupported()

def nm():
  unsupported()

def nmKill(serverName='myserver', serverType='WebLogic', pluginProps=None):
  unsupported()

def nmSoftRestart(serverName='myserver', serverType=None, pluginProps=None):
  unsupported()

def nmStart(serverName='myserver',
            domainDir=None,
            props=None,
            writer=None,
            serverType = 'WebLogic',
            pluginProps=None):
  unsupported()

def nmServerStatus(serverName='myserver', serverType='WebLogic'):
  unsupported()

def nmLog(writer=None):
  unsupported()

def nmDiagnostics(diagnosticsType=None, command=None, writer=None, serverType=None):
  unsupported()

def nmInvocation(invocationType=None, command=None, writer=None, serverType=None):
  unsupported()

def nmVersion():
  unsupported()

def nmRestart(timeout=0):
  unsupported()

def nmExecScript(scriptName=None,
            scriptDir=None,
            scriptProps=None,
            writer=None,
            timeout=0):
  unsupported()

def stopNodeManager():
  unsupported()

def startNodeManager():
  unsupported()

def nmConnectInternal(domainNameForComp,compName,domainDir,storeUserConfigValue):
  unsupported()

def threadDump(writeToFile="true", fileName=None, serverName = None):
  unsupported()  

def getConfigManager():
  unsupported()

def nmGenBootStartupProps(serverName=None):
  unsupported()

def softRestart(name="NONAME", block="true", properties=None, type=None):
  unsupported()

def scaleUp(clusterName="NONAME", numServers=1, updateConfiguration=false, block=true, timeoutSeconds=600, type="DynamicCluster"):
  unsupported()

def scaleDown(clusterName="NONAME", numServers=1, updateConfiguration=false, block=true, timeoutSeconds=300, type="DynamicCluster"):
  unsupported()

def configToScript(configPath=None,
                   pyPath=None,
                   overWrite="true",
                   propertiesFile=None,
                   createDeploymentScript="false",
                   convertTheseResourcesOnly="",
                   debug = "false"
                   ):
  unsupported()

def importPartition(archiveFileName, partitionName=None, createNew=None , keyFile=None):
  unsupported()

def exportPartition(partitionName, expArchPath, includeAppsNLibs=true, keyFile=None):
  unsupported()

def startPartitionWait(partitionMBean, initialState='RUNNING', timeout=60*1000):
  unsupported()

def migrateResourceGroup(target, currentTarget, newTarget, timeout=-1):
  unsupported()

def viewDebugScope():
  unsupported()

def saveDomain():
  unsupported()

