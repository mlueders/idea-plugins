package org.roadkill.plugins.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.wm.IdeFocusManager;

/**
 * User: mlueders
 * Date: 11/30/12 5:26 AM
 */
public class ActivateSelectedEditor extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        Editor editor = FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor();
        if (editor != null) {
            IdeFocusManager focusManager = IdeFocusManager.getInstance(editor.getProject());
            focusManager.requestFocus(editor.getContentComponent(), true);
        }
    }

}
