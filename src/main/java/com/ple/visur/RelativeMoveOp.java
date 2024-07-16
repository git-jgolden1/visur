package com.ple.visur;

import java.util.ArrayList;

public class RelativeMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorContent = emc.getEditorContent();
    ArrayList<Integer> newlineIndices = emc.getNewlineIndices();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum cursorQuantum = emc.getCursorQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int newRealCA = cursorQuantum.move(editorContent, newlineIndices, movementVector);
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    realCABVV.putVal(newRealCA);
    emc.putGlobalVar("realCA", realCABVV);
    int[] newBounds;
    if(newRealCA == 0 && editorContent.length() == 0) {
      newBounds = new int[]{newRealCA, newRealCA};
      emc.putSpan(0);
    } else {
      newBounds = cursorQuantum.getBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    }
    emc.putRealCA(newBounds[0]);
    emc.putCursorQuantumStart(newBounds[0]);
    emc.putCursorQuantumEnd(newBounds[1]);
  }
}
