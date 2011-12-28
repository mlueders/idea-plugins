package org.roadkill.plugins.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.editor.actions.KillRingUtil;

/**
 * User: mike
 * Date: 12/28/11 3:36 PM
 */
public class CutLineAction extends EditorAction {

    public CutLineAction() {
        super(new Handler());
    }

    public static class Handler extends EditorWriteActionHandler {
        public void executeWriteAction(Editor editor, DataContext dataContext) {
            if (!editor.getSelectionModel().hasSelection()) {
                editor.getSelectionModel().selectLineAtCaret();
            }

            int selectionStart = editor.getSelectionModel().getSelectionStart();
            int selectionEnd = editor.getSelectionModel().getSelectionEnd();

            KillRingUtil.cut(editor, selectionStart, selectionEnd);
        }
    }

}
