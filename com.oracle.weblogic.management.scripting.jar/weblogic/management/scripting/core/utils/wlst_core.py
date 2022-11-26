# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011,2016, Oracle and/or its affiliates. All rights reserved.

"""
This script defines WLST commands can be run in both online and offline modes.
These commands don't need be redefined when WLST user switches between online
and offline mode.

"""

import sys
import java
import jarray
from jarray import array
from java import *
from weblogic import *
from java.lang import *
from javax.management import *
from weblogic.management.scripting import ScriptException
from weblogic.management.scripting.core.utils import WLSTCoreUtil

### Define all the global variables here

global theInterpreter
if theInterpreter == None:
    theInterpreter=WLSTCoreUtil.ensureInterpreter();

WLS_CORE=WLSTCoreUtil.ensureWLCtx(theInterpreter)
nmService=WLS_CORE.getNodeManagerService();
fmtr=WLS_CORE.getWLSTMsgFormatter()
isStandalone=WLS_CORE.isStandalone()

myps1="wls:/offline> "
sys.ps1=myps1
ncPrompt="wls:/offline> "
domainName = "";
connected = "false"
domainType = ""
promptt = ""
hideProm = "false"
scriptMode = "false"
exitonerror = "true"
wlstPrompt = "true"
oldhook=sys.displayhook
true=1
false=0
LAST = None

def loadProperties(fileName):
  # verify theInterpreter is set
  if WLSTCoreUtil.runningWLSTAsModule():
    WLS_CORE.println(fmtr.getCannotLoadPropertiesInModule())
    return
  global theInterpreter
  WLS_CORE.loadProperties(fileName, theInterpreter)

### Custom help commands

def addHelpCommandGroup(groupName, resourceBundleName):
  try:
    WLS_CORE.addHelpCommandGroup(groupName, resourceBundleName)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def addHelpCommand(commandName, groupName, offline='false', online='false'):
  try:
    WLS_CORE.addHelpCommand(commandName, groupName, offline, online)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def debug(val=None):
  try:
    WLS_CORE.debug(val)
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def makePropertiesObject(value):
  try:
    hideDisplay()
    return WLS_CORE.makePropertiesObject(value)
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def makeArrayObject(value):
  try:
    hideDisplay()
    return WLS_CORE.makeArrayObject(value)
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def redirect(outputFile=None, toStdOut="true"):
  try:
    WLS_CORE.redirect(outputFile, toStdOut)
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

def stopRedirect():
  try:
    WLS_CORE.stopRedirect()
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

import weblogic.management.scripting.WLSTConstants as tree

# Un-documented
# @exclude
def evaluatePrompt():
  if WLSTCoreUtil.runningWLSTAsModule():
    return
  global wlstPrompt,lastPrompt,domainName,hideProm
  global domainType,showStack,connected,myps1,promptt,ncPrompt
  if wlstPrompt=="false":
    myps1=sys.ps1
    sys.ps1=myps1
    return
  if hideProm == "false":
    if nmService.isConnectedToNM() == 1 and connected == "false":
      myps1="wls:/nm/"+str(nmService.getDomainName())+"> "
      sys.ps1 = myps1
      return
    if domainType == tree.DEPRECATED_ADMIN_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DOMAIN_CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DOMAIN_CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.EDIT_CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.EDIT_CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.JNDI_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.JNDI_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_DOMAINRUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_DOMAINRUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.EDIT_TREE:
      if WLS_CORE.isEditSessionInProgress() == 1:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+"/"+promptt+" !> "
      else:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_CONFIG_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
  elif hideProm == "true":
    myps1="wls:/> "
    sys.ps1=myps1
  if connected=="false":
    # see if readTemplate or anything is intact
    offlinePrompt = java.lang.String(WLS.getAbsPwd())
    if offlinePrompt != None and offlinePrompt.length()!= 0:
      myps1 = "wls:/offline"+str(offlinePrompt)+"> "
    else:
      myps1=ncPrompt
    sys.ps1=myps1

# Un-documented
# @exclude
def updateCoreGlobals():
  global wlstPrompt,lastPrompt,domainName,hideProm,domainType,showStack
  global connected,myps1,promptt,ncPrompt,isStandalone
  promptt = WLS_CORE.getPrompt()
  domainName = WLS_CORE.getDomainName()
  connected = WLS_CORE.getConnected()
  domainType = WLS_CORE.getDomainType()
  isStandalone = WLS_CORE.isStandalone()
  evaluatePrompt()


# Un-documented
# @exclude
class WLSTException(Exception):
  cause = None
  def __init__(self, value):
    self.value = value
  def __str__(self):
    # See CR344726.
    # return repr(self.value)
    return PyString(self.value)
  def getCause(self):
    return self.cause
  def setCause(self, acause):
    self.cause = acause

# Un-documented
# @exclude
def raiseWLSTException(e):
  wlste = WLSTException(e.getMessage())
  wlste.setCause(e.getCause())
  raise wlste

# Un-documented
# @exclude
def eatdisplay(dummy):
  pass

# Un-documented
# @exclude
def hideDisplay():
  sys.displayhook=eatdisplay

# Un-documented
# @exclude
def setDumpStackThrowable(value=None):
  WLS_CORE.setDumpStackThrowable(value)

# Un-documented
# @exclude
def startComponentInternal(compName,domainDir,storeUserConfigValue):
  global LAST
  try:
    readDomainOffline(domainDir)
    domainName=get("Name")
    WLS_CORE.printDebug("domainNameForComp = " + domainName)

    nmConnectInternal(domainName, compName, domainDir, storeUserConfigValue)
    WLS_CORE.printDebug("storeUserConfigValue = " + storeUserConfigValue)
    result = nmStart(serverName=compName,serverType=componentType)
    nmDisconnect()
    closeDomain()
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def restartComponentInternal(compName,domainDir,storeUserConfigValue):
  global LAST
  try:
    readDomainOffline(domainDir)
    domainName=get("Name")
    WLS_CORE.printDebug("domainNameForComp = " + domainName)

    nmConnectInternal(domainName, compName, domainDir, storeUserConfigValue)
    WLS_CORE.printDebug("storeUserConfigValue = " + storeUserConfigValue)
    result = nmSoftRestart(serverName=compName,serverType=componentType)
    nmDisconnect()
    closeDomain()
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def stopComponentInternal(compName,domainDir):
  global LAST
  try:
    readDomainOffline(domainDir)
    domainName=get("Name")
    WLS_CORE.printDebug("domainNameForComp = " + domainName)

    nmConnectInternal(domainName,compName,domainDir, "false")
    result = nmKill(serverName=compName,serverType=componentType)
    nmDisconnect()
    closeDomain()
    updateCoreGlobals()
    LAST = result
    return LAST
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

# Un-documented
# @exclude
def readDomainOffline(domainDir):
  try:
    # Convert the Windows path to the Linux path.
    domainDir=domainDir.replace("\\", "/")
    WLS_CORE.println(fmtr.getReadingDomain(domainDir))
    result = readDomain(domainDir)
    updateCoreGlobals()
    LAST = result
    return LAST
  except Exception,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

componentType=""
# Un-documented
# @exclude
def nmConnectInternal(domainNameForComp,compName,domainDir,storeUserConfigValue):
  global componentType

  try:
    # Find component in the system components
    try:
      cd("/")
      WLS_CORE.printDebug("cd('/')")
      WLS.setShowLSResult(0)
      rootEntries=ls()
      WLS.setShowLSResult(1)
      if (rootEntries.find("SystemComponent") == -1):
        raiseWLSTException(Throwable(fmtr.getSystemComponentNotFound(domainNameForComp)))

      cd("SystemComponent")
      WLS_CORE.printDebug("cd('SystemComponent')")

      WLS.setShowLSResult(0)
      instances = ls()
      WLS.setShowLSResult(1)
      if (instances.find(compName) == -1) :
        raise WLSTException(fmtr.getComponentNotFound(compName))
      cd(compName)
      WLS_CORE.printDebug("cd('/SystemComponent/" + compName + ")")
    except ScriptException,e:
      if exitonerror=="true":
        raiseWLSTException(e)
      else:
        WLS_CORE.println(fmtr.getComponentNotFound(compName))
        return

    # Find system component type
    try:
      componentType=get("ComponentType")
      WLS_CORE.printDebug("componentType = " + componentType)
    except ScriptException,e:
      if exitonerror=="true":
        raiseWLSTException(e)
      else:
        WLS_CORE.println(fmtr.getComponentTypeNotFound(machine, compName))
        return

    # Find machine for the system component
    try:
      machineValue=get("Machine")
      if (machineValue == None):
        if isStandalone:
          # Workaround for bug 16575138. 
          WLS_CORE.printDebug("No machine found from /SystemComponent. Get Machines from the root /")
          cd("/")

          machines=get("Machines")
          WLS_CORE.printDebug("cd('/'); get('Machines')")

          if (len(machines) == 0):
            raiseWLSTException(Throwable(fmtr.getMachineNotFound(compName)))

          for (counter, m) in enumerate(machines):
            # In CAM standalone env, mostly like there is only one machine
            # in a domain
            # If there are multiple machines returned, only get the first one.
            if (counter == 0):
              machine=m.getName()
              WLS_CORE.printDebug("Got the machine name: " + machine)

        else:
          # in collocated case
          raiseWLSTException(Throwable(fmtr.getMachineNotFound(compName)))
      else:
        machine=machineValue.getName()
      WLS_CORE.printDebug("machine = " + machine)
    except ScriptException,e:
      if exitonerror=="true":
        raiseWLSTException(e)
      else:
        WLS_CORE.println(fmtr.getMachineNotFound(compName))
        return


    # Find username for the Node Manager
    try:
      cd("/")
      WLS_CORE.printDebug("cd('/')")

      try:
        sc=get("SecurityConfiguration")
        WLS_CORE.printDebug("get('SecurityConfiguration')")
        domainNameFromsc=sc.getName()
        WLS_CORE.printDebug("Got the name for SecurityConfiguration: " + domainNameFromsc )
      except Exception,e:
        # Cannot get the SecurityConfiguration MBean from the root
        cd("/SecurityConfiguration")
        WLS.setShowLSResult(0)
        lsscEntries=ls()
        WLS.setShowLSResult(1)
        # Split the output of ls().
        # Will be something like drw-, NO_NAME_0
        scEntries=lsscEntries.split()
        WLS_CORE.printDebug("lsscEntries.split()")

        if (len(scEntries) == 0):
          raiseWLSTException(Throwable(fmtr.getSecurityConfigurationNotFound(domainNameForComp)))

        for (counter, scEntry) in enumerate(scEntries):
          # The second value (counter=1) is the name of SecurityConfiguration
          if counter == 1:
            domainNameFromsc=scEntry
            WLS_CORE.printDebug("Got the security confiruation name: " + domainNameFromsc)

      
      cd("/SecurityConfiguration/" + domainNameFromsc)
      WLS_CORE.printDebug("cd('/SecurityConfiguration/" + domainNameFromsc + "')")

      username=get("NodeManagerUsername")
      password=""       
      WLS_CORE.printDebug("username = " + username)
    except ScriptException,e:
      if exitonerror=="true":
        raiseWLSTException(e)
      else:
        WLS_CORE.println(fmtr.getUsernameAndPasswordNotFound())
        return

    # Find the machine
    try:
      WLS_CORE.printDebug("cd(/Machine/" + machine + ")")
      cd("/Machine/" + machine)
    except ScriptException,e:
      if exitonerror=="true":
        raiseWLSTException(e)
      else:
        WLS_CORE.println(fmtr.getMachineNotFound(compName))
        return

    # Check if there is a NodeManager associated with this machine
    WLS.setShowLSResult(0)
    lsNMEntries=ls()
    WLS_CORE.printDebug("lsNMEntries == " + lsNMEntries)
    WLS.setShowLSResult(1)
    # Split the output of ls().
    # Will be something like drw-, NodeManager 
    NMEntries=lsNMEntries.split()
    WLS_CORE.printDebug("lsNMEntries.split()")
    WLS_CORE.printDebug("len(NMEntries) = " + str(len(NMEntries)))

    foundNM="false"
    if (len(NMEntries) != 0):
      for (counter, NMEntry) in enumerate(NMEntries):
        if NMEntry == "NodeManager":
          foundNM="true"
          break;

    if (foundNM == "false"):
      # No NodeManager is associated with this machine, then use the default values for Node Manager.
      WLS_CORE.printDebug("Cannot find a node-manager associated with the machine in config.xml. Use default values for Node Manager.")
      nmType="SSL"
      host="localhost"
      port="5556"
      WLS_CORE.printDebug("nmType = " + nmType)
      WLS_CORE.printDebug("host = " + host)
      WLS_CORE.printDebug("port = " + str(port))
    else:
      try:
        #bug 17799997
        cd("NodeManager")
        WLS_CORE.printDebug("cd(NodeManager)")
        WLS.setShowLSResult(0)
        lsNodeManager=ls()
        WLS_CORE.printDebug("lsNodeManager == " + lsNodeManager)
        WLS.setShowLSResult(1)
        # Split the output of ls() (drw-, NodeManager)
        NMContents=lsNodeManager.split()
        WLS_CORE.printDebug("len(NMContents) = " + str(len(NMContents)))
        
        NodeManagerName=""
        if (len(NMContents) != 0):
            for(counter,NMContent) in enumerate(NMContents):
                if NMContent == "NodeManager":
                    NodeManagerName = NMContent
                    break;
                elif NMContent == machine:
                    NodeManagerName = machine
                    break;

        assert(NodeManagerName == machine or NodeManagerName == "NodeManager")
        WLS_CORE.printDebug("cd(" +  NodeManagerName + ")")
        cd(NodeManagerName)
        # Find the Node Manager Connection Type
        nmType=get("NMType")
        if nmType == None:
          nmType="SSL"
        WLS_CORE.printDebug("nmType = " + nmType)
      except ScriptException,e:
        if exitonerror=="true":
          raiseWLSTException(e)
        else:
          WLS_CORE.println(fmtr.getNMTypeNotFound())
          return

      # Find Node Manager listening address and port
      try:
        host=get("ListenAddress")
        port=get("ListenPort")
        WLS_CORE.printDebug("host = " + host + ", port = " + str(port))
      except ScriptException,e:
        if exitonerror=="true":
          raiseWLSTException(e)
        else:
          WLS_CORE.println(fmtr.getAddressAndPortNotFound())
          return

    WLS_CORE.printDebug("Calling nmConnect('" + username + "','','" +
      host + "','" + str(port) + "','" + domainNameForComp + "','" +
      "','" + nmType + "')")

    nmConnect(username,
              password,
              host,
              port,
              domainNameForComp,
              None,
              nmType,
              storeUserConfig=storeUserConfigValue)

    updateCoreGlobals()
  except ScriptException,e:
    updateCoreGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_CORE.println(e.getMessage())

