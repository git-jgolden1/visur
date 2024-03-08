package com.ple.visur;

import java.util.ArrayList;

public class RelativeMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionDataStack();
    String editorContent = ems.getEditorContent();
    ArrayList<Integer> newlineIndices = ems.getNewlineIndices();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum currentQuantum = ems.getCurrentQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int contentX = ems.getGlobalVar("ca").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    CursorPosition pos = new CursorPosition(contentX, contentY);
    int[] bounds = currentQuantum.getBoundaries(editorContent, newlineIndices, contentX, contentY, false);
    CursorPosition newCursorPosition = currentQuantum.move(editorContent, newlineIndices, pos, movementVector, bounds);
    if(newCursorPosition.x < editorContent.length()) {
      ems.putGlobalVar("ca", new IntVisurVar(newCursorPosition.x));
    }
    ems.putGlobalVar("contentY", new IntVisurVar(newCursorPosition.y));
    int[] newBounds = currentQuantum.getBoundaries(editorContent, newlineIndices, newCursorPosition.x, newCursorPosition.y, false);
    ems.putQuantumStart(newBounds[0]);
    ems.putQuantumEnd(newBounds[1]);
  }
}
