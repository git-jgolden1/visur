package com.ple.visur;

public enum BusEvent {
  //sent in js in eb.onopen, consumed by CanvasWasChangedVerticle
  canvasWasChanged,

  //send in CanvasWasPressedVerticle, consumed in visur.js before modelWasChanged event is sent in eb.onopen
  canvasWasChangedEventComplete,

  //sent in js in keyDown event listener, consumed by KeyWasPressedVerticle
  keyWasPressed,

  //sent in KeyWasPressedVerticle and in visur.js in eb.onopen, consumed by ModelWasChangedVerticle
  modelWasChanged,

  //sent in ModelWasChangedVerticle, consumed in visur.js in eventbus.onopen
  viewWasChanged
}

