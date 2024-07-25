package org.roadkill.plugins.intellij

import com.intellij.execution.ExecutionManager
import com.intellij.execution.KillableProcess
import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys


class StopAllProcessesAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val handlers = getProcessHandlers(e)

        for (handler in handlers) {
            killProcess(handler)
        }
    }

    private fun getProcessHandlers(e: AnActionEvent): Array<ProcessHandler> {
        val project = PlatformDataKeys.PROJECT.getData(e.dataContext)
        return ExecutionManager.getInstance(project!!).getRunningProcesses()
    }

    private fun killProcess(handler: ProcessHandler) {
        if (handler is KillableProcess && handler.isProcessTerminating) {
            (handler as KillableProcess).killProcess()
        } else if (handler.detachIsDefault()) {
            handler.detachProcess()
        } else {
            handler.destroyProcess()
        }
    }
}