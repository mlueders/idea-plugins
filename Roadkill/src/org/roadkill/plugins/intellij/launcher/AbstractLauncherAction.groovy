package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.Executor
import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * User: mlueders
 * Date: 11/29/12 11:08 AM
 */
abstract class AbstractLauncherAction extends AnAction {

	private Project myProject
    private Executor executor
    private LauncherEnum launcher

    public AbstractLauncherAction(String text, LauncherEnum launcher, ExecutorService executorService, Project myProject) {
        super(text)
        this.launcher = launcher
        this.executor = executorService.executor
		this.myProject = myProject
    }

    @Override
    final void actionPerformed(AnActionEvent e) {
		Project project = getProject(e)
        LauncherComponent launcherComponent = LauncherComponent.get(project)

        RunnerAndConfigurationSettings configuration = launcherComponent.getConfigurationForLauncher(launcher)
        if (configuration == null) {
            configuration = launcherComponent.createRunnerConfigurationFromContextAndMakeActive(e.dataContext)
            launcherComponent.state.setConfigurationName(launcher, configuration.name)
        }
        ProgramRunnerUtil.executeConfiguration(project, configuration, executor);
    }

	/**
	 * There are two contexts in which these actions are created: via plugin.xml and via BindableLauncherComboBoxAction.
	 * When created via plugin.xml, the AnActionEvent will have the correct project associated with it.  When created
	 * dynamically via BindableLauncherComboBoxAction, it sometimes will not if there are multiple projects open.
	 * Hence, this workaround.  See BindableLauncherComboBoxAction:createPopupActionGroup for further explanation.
	 */
	protected Project getProject(AnActionEvent e) {
		myProject ? myProject : e.project
	}

}
