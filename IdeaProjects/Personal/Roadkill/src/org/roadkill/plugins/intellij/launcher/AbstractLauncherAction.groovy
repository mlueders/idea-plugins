package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.Executor
import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * User: mlueders
 * Date: 11/29/12 11:08 AM
 */
abstract class AbstractLauncherAction extends AnAction {

    private Executor executor
    private LauncherEnum launcher

    public AbstractLauncherAction(String text, LauncherEnum launcher, ExecutorService executorService) {
        super(text)
        this.launcher = launcher
        this.executor = executorService.executor
    }

    @Override
    final void actionPerformed(AnActionEvent e) {
        LauncherComponent launcherComponent = LauncherComponent.get(e.project)

        RunnerAndConfigurationSettings configuration = launcherComponent.getConfigurationForLauncher(launcher)
        if (configuration == null) {
            configuration = launcherComponent.createRunnerConfigurationFromContextAndMakeActive(e.dataContext)
            launcherComponent.state.setConfigurationName(launcher, configuration.name)
        }
        ProgramRunnerUtil.executeConfiguration(e.project, configuration, executor);
    }

}
