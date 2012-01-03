package org.roadkill.plugins.intellij;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.KillableProcess;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: mike
 * Date: 12/28/11 12:34 PM
 */
public class StopAllProcessesAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        ProcessHandler[] handlers = getProcessHandlers(e);

        for (ProcessHandler handler : handlers) {
            killProcess(handler);
        }
    }

    private ProcessHandler[] getProcessHandlers(final AnActionEvent e) {
        Project project = PlatformDataKeys.PROJECT.getData(e.getDataContext());
        return ExecutionManager.getInstance(project).getRunningProcesses();
    }

    private void killProcess(ProcessHandler handler) {
        if (handler instanceof KillableProcess && handler.isProcessTerminating()) {
            ((KillableProcess) handler).killProcess();
        } else if (handler.detachIsDefault()) {
            handler.detachProcess();
        } else {
            handler.destroyProcess();
        }
    }
}