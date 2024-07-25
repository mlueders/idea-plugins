package org.roadkill.plugins.intellij

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorAction
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler
import com.intellij.openapi.editor.actions.KillRingUtil

class CutLineAction : EditorAction(Handler()) {

    class Handler : EditorWriteActionHandler() {
        override fun executeWriteAction(editor: Editor, caret: Caret?, dataContext: DataContext) {
            if (!editor.selectionModel.hasSelection()) {
                editor.selectionModel.selectLineAtCaret()
            }

            KillRingUtil.cut(
                editor, editor.selectionModel.selectionStart, editor.selectionModel.selectionEnd
            )
        }
    }

}