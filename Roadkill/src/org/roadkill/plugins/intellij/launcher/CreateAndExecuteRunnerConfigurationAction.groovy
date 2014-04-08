package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.Executor
import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * User: mike
 * Created: 1/2/12 8:51 PM
 */
public abstract class CreateAndExecuteRunnerConfigurationAction extends AnAction {

    public static class Run extends CreateAndExecuteRunnerConfigurationAction {
        public Run() {
            super(ExecutorService.RUN)
        }
    }

    public static class Debug extends CreateAndExecuteRunnerConfigurationAction {
        public Debug() {
            super(ExecutorService.DEBUG)
        }
    }


    private Executor executor

    protected CreateAndExecuteRunnerConfigurationAction(ExecutorService executorService) {
        this.executor = executorService.executor
    }

    public void actionPerformed(AnActionEvent e) {
        LauncherComponent launcherComponent = LauncherComponent.get(e.project)
        RunnerAndConfigurationSettings configuration =
                launcherComponent.createRunnerConfigurationFromContextAndMakeActive(e.dataContext)
        ProgramRunnerUtil.executeConfiguration(e.project, configuration, executor)
    }

}
