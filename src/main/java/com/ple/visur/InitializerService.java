package com.ple.visur;

import CursorPositionDC.CursorPositionDC;
import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;
import DataClass.PrimitiveDataClassBrick;

import java.util.HashMap;
import java.util.Stack;

import static com.ple.visur.EditorMode.navigate;
import static com.ple.visur.EditorModelKey.globalVariableMap;

public class InitializerService {

  private final EditorModelCoupler emc;

  public static InitializerService make(EditorModelCoupler editorModelCoupler) {
    return new InitializerService(editorModelCoupler);
  }

  private InitializerService(EditorModelCoupler editorModelCoupler) {
    this.emc = editorModelCoupler;
    initializeEditorModel();
  }

  public void initializeEditorModel() {
    VariableMap initialGvm = new VariableMap(new HashMap<>());
    emc.editorModel.put(globalVariableMap, initialGvm);
    emc.putVirtualCX(0);
    emc.putVirtualXIsAtEndOfLine(false);

    emc.putEditorMode(navigate);

    Stack<EditorSubmode> editorSubmodeStack = new Stack<>();
    editorSubmodeStack.push(EditorSubmode.navigate);
    emc.putEditorSubmodeStack(editorSubmodeStack);

    emc.putExecutionDataStack(new ExecutionDataStack());

    final String initialEditorContent = "Hello world\n" +
      "How are you?\n" +
      "Goodbye\n";
//    final String initialEditorContent = "   ";
//    final String initialEditorContent = "Vehumet is a god of the destructive powers of magic.\n" +
//      "Followers will gain divine assistance in commanding the hermetic arts, and the most favoured stand to gain access to some of the fearsome spells in Vehumet's library.\n" +
//      "One's devotion to Vehumet can be proven by the causing of as much carnage and destruction as possible.\n" +
//      "Worshippers of Vehumet will quickly be able to recover their magical energy upon killing beings.\n" +
//      "As they gain favour, they will also gain enhancements to their destructive spells — first assistance in casting such spells and then increased range for conjurations.\n" +
//      "Vehumet will offer followers the knowledge of increasingly powerful destructive spells as they gain piety.\n" +
//      "Whether \"having spells as a god\" is a good thing is up to you, but it does come with a few downsides. Without active abilities, you have less ways to deal with a dangerous situation. In addition, Vehumet is one of the weaker gods for the early game. For spellcaster backgrounds, the first few spell gifts generally won't be much of an improvement compared to your own spells. Characters new to spellcasting have to take time to train up magic, which might not be all that powerful by the time you hit 1* or even 3*. Furthermore, there are ways to get an \"engine\" without taking up the god slot.";

    initializeUnsetCoordinateBricks();

    emc.initializeEditorContent(initialEditorContent);
    emc.updateNewlineIndices();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(0);
    emc.putGlobalVar("ca", caBVV);
    emc.putIsInCommandState(false);
    emc.putCommandStateContent("");
    emc.putCommandCursor(emc.getCommandStateContent().length());
    if(emc.getEditorContent().length() > 0) {
      emc.putSpan(1);
    } else {
      emc.putSpan(0);
    }

    initializeQuantums();

    initializeKeymaps();

  }

  private void initializeUnsetCoordinateBricks() {
    CursorPositionDC cursorPositionDC = CursorPositionDCHolder.make().cursorPositionDC;
    CompoundDataClassBrick cursorPosDCB = cursorPositionDC.makeBrick();
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPosDCB.getInner("ni");
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cursorPosDCB.getInner("cxcyca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cxcycaDCB.putInner("ca", caDCB);
    cxcycaDCB.putInner("cxcy", cxcyDCB);
    cursorPosDCB.putInner("ni", niDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);

    BrickVisurVar caDCBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar cxDCBVV = BrickVisurVar.make(cxDCB);
    BrickVisurVar cyDCBVV = BrickVisurVar.make(cyDCB);
    BrickVisurVar niBVV = BrickVisurVar.make(niDCB);

    emc.putGlobalVar("ca", caDCBVV);
    emc.putGlobalVar("cx", cxDCBVV);
    emc.putGlobalVar("cy", cyDCBVV);
    emc.putGlobalVar("ni", niBVV);

  }

  private void initializeQuantums() {
    QuantumNameToQuantum quantumNameToQuantum = new QuantumNameToQuantum();
    String startingQuantumName = "character";
    quantumNameToQuantum.put("word", new RegexQuantum("word", "\\S+"));
    quantumNameToQuantum.put("character", new CharacterQuantum());
    quantumNameToQuantum.put("wrappedLine", new WrappedLineQuantum());
    quantumNameToQuantum.put("document", new DocumentQuantum());
    emc.putQuantumNameToQuantum(quantumNameToQuantum);

    KeyToQuantumName keyToQuantumName = new KeyToQuantumName();
    keyToQuantumName.put("q", "character");
    keyToQuantumName.put("w", "word");
    keyToQuantumName.put("e", "wrappedLine");
    keyToQuantumName.put("r", "document");
    emc.putKeyToQuantumName(keyToQuantumName);

    emc.putCursorQuantum(emc.getQuantumNameToQuantum().get(startingQuantumName));
    int bounds[] = emc.getQuantumNameToQuantum().get(startingQuantumName).getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    emc.putIsAtQuantumStart(true);
    System.out.println("start bound = " + bounds[0]);
    System.out.println("end bound = " + bounds[1]);
  }

  private void initializeKeymaps() {
    KeymapMap keymapMap = KeymapMap.make();

    Keymap navigateKeymap = Keymap.make("navigate");
    navigateKeymap = initializeNavigateKeymap(navigateKeymap);
    keymapMap.put(EditorSubmode.navigate, navigateKeymap);

    Keymap insertKeymap = Keymap.make("insert");
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorSubmode.insert, insertKeymap);

    Keymap quantumStartKeymap = Keymap.make("quantumStart");
    quantumStartKeymap = initializeQuantumStartKeymap(quantumStartKeymap);
    keymapMap.put(EditorSubmode.quantumStart, quantumStartKeymap);

    Keymap quantumEndKeymap = Keymap.make("quantumEnd");
    quantumEndKeymap = initializeQuantumEndKeymap(quantumEndKeymap);
    keymapMap.put(EditorSubmode.quantumEnd, quantumEndKeymap);

    Keymap spanKeymap = Keymap.make("span");
    spanKeymap = initializeSpanKeymap(spanKeymap);
    keymapMap.put(EditorSubmode.span, spanKeymap);

    emc.putKeymapMap(keymapMap);
  }

  private Keymap initializeNavigateKeymap(Keymap keymap) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    keymap.put(KeyPressed.from("h"),
      scs.compile("-1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("l"),
      scs.compile("1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("j"),
      scs.compile("0 1 relativeMove")
    );
    keymap.put(KeyPressed.from("k"),
      scs.compile("0 -1 relativeMove")
    );
    keymap.put(KeyPressed.from("["),
      scs.compile("\"quantumStart\" pushSubmode")
    );
    keymap.put(KeyPressed.from("]"),
      scs.compile("\"quantumEnd\" pushSubmode")
    );
    keymap.put(KeyPressed.from("s"),
      scs.compile("\"span\" pushSubmode")
    );
    keymap.put(KeyPressed.from("i"),
      scs.compile("\"span\" \"tempSpan\" -> \"cursorQuantum\" \"tempCursorQuantum\" -> \"insert\" changeMode 0 setSpan \"character\" changeQuantum")
    );
    keymap.put(KeyPressed.from("a"),
      scs.compile("\"span\" \"tempSpan\" -> \"cursorQuantum\" \"tempCursorQuantum\" -> \"insert\" changeMode 0 setSpan 1 0 relativeMove \"character\" changeQuantum")
    );

    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = ChangeQuantumHandler.make();
    keymap.putHandlers(handlers);
    return keymap;
  }

  private Keymap initializeQuantumStartKeymap(Keymap quantumStartKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = QuantumStartSubmodeHandler.make();
    quantumStartKeymap.putHandlers(handlers);
    return quantumStartKeymap;
  }

  private Keymap initializeQuantumEndKeymap(Keymap quantumEndKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = QuantumEndSubmodeHandler.make();
    quantumEndKeymap.putHandlers(handlers);
    return quantumEndKeymap;
  }

  private Keymap initializeSpanKeymap(Keymap spanKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = SpanSubmodeHandler.make();
    spanKeymap.putHandlers(handlers);
    return spanKeymap;
  }

  private Keymap initializeInsertKeymap(Keymap insertKeymap) {
//    insertKeymap.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Enter")}), Operator.insertNewLine);
//    insertKeymap.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Backspace")}), Operator.deleteCurrentChar);

    CommandCompileService scs = ServiceHolder.commandCompileService;
    insertKeymap.put(KeyPressed.from("Escape"),
      scs.compile("\"tempSpan\" \"span\" -> \"tempCursorQuantum\" changeQuantum \"tempSpan\" removeGlobalVar \"tempCursorQuantum\" removeGlobalVar \"navigate\" changeMode")
    );
    insertKeymap.put(KeyPressed.from("ArrowLeft"),
      scs.compile("-1 0 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowRight"),
      scs.compile("1 0 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowUp"),
      scs.compile("0 -1 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowDown"),
      scs.compile("0 1 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("Backspace"),
      scs.compile("deletePreviousChar -1 0 relativeMove")
    );
//    insertKeymap.put(KeyPressed.from("l"),
//      scs.compile("1 0 relativeMove")
//    );

    final KeymapHandler[] insertKeymapHandlers = new KeymapHandler[1];
    insertKeymapHandlers[0] = InsertModeHandler.make();
    insertKeymap.putHandlers(insertKeymapHandlers);

    return insertKeymap;
  }

}
