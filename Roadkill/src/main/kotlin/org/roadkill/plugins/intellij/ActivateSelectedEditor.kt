package org.roadkill.plugins.intellij

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.wm.IdeFocusManager


class ActivateSelectedEditor : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = FileEditorManager.getInstance(e.project!!).selectedTextEditor
        if (editor != null) {
            val focusManager = IdeFocusManager.getInstance(editor.project)
            focusManager.requestFocus(editor.contentComponent, true)
        }
    }

}
