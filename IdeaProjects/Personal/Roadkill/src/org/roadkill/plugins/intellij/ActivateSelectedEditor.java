package org.roadkill.plugins.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;

/**
 * User: mlueders
 * Date: 11/30/12 5:26 AM
 */
public class ActivateSelectedEditor extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        Editor editor = FileEditorManager.getInstance(e.getProject()).getSelectedTextEditor();
        if (editor != null) {
            editor.getContentComponent().grabFocus();
        }
    }

}
