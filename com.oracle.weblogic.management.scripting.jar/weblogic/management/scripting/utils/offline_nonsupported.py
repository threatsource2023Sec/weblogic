# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011 by Oracle and/or its affiliates.  All rights reserved.

"""
Internal Script that sets the offline commands to NO-OP
once a user connects to a running server. These are mostly
Offline WLST commands

"""
from weblogic.management.scripting.utils import WLSTMsgTextFormatter

def notSupportedWhileConnected():
  print ''
  print WLSTMsgTextFormatter.getInstance().getNotSupportedWhileConnected()
  print ''
  return

def readTemplate(templateFileName):
  notSupportedWhileConnected()
def setOption(optionName, value):
  notSupportedWhileConnected()
def assign(srcType, srcName, destType, destName):
  notSupportedWhileConnected()
def unassign(srcType, srcName, destType, destName):
  notSupportedWhileConnected()
def assignAll(srcType, destType, destName):
  notSupportedWhileConnected()
def unassignAll(srcType, destType, destName):
  notSupportedWhileConnected()
def writeDomain(domainDir):
  notSupportedWhileConnected()
def closeTemplate():
  notSupportedWhileConnected()
def readDomain(domainDir):
  notSupportedWhileConnected()
def addTemplate(templateFile):
  notSupportedWhileConnected()
def updateDomain():
  notSupportedWhileConnected()
def closeDomain():
  notSupportedWhileConnected()
def loadDB(DBVersion, ConnectionPoolName):
  notSupportedWhileConnected()
def updateCmo():
  notSupportedWhileConnected()
def updatePrompt():
  notSupportedWhileConnected()
