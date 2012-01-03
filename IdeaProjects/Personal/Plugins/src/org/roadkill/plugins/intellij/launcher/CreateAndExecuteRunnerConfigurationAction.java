package org.roadkill.plugins.intellij.launcher;

import com.intellij.execution.*;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowId;
import org.roadkill.plugins.intellij.PluginUtils;

/**
 * User: mike
 * Created: 1/2/12 8:51 PM
 */
public abstract class CreateAndExecuteRunnerConfigurationAction extends AnAction {

    public static class Run extends CreateAndExecuteRunnerConfigurationAction {
        protected Executor getExecutor() {
            return DefaultRunExecutor.getRunExecutorInstance();
        }
    }

    public static class Debug extends CreateAndExecuteRunnerConfigurationAction {
        protected Executor getExecutor() {
            return ExecutorRegistry.getInstance().getExecutorById(ToolWindowId.DEBUG);
        }
    }


    protected abstract Executor getExecutor();

    public void actionPerformed(AnActionEvent e) {
        Project project = PluginUtils.getProject(e);
        RunnerAndConfigurationSettings configuration = createActiveRunnerConfiguration(e);

        RunManagerEx manager = RunManagerEx.getInstanceEx(project);
        manager.addConfiguration(configuration, false);
        manager.setSelectedConfiguration(configuration);

        Executor executor = getExecutor();
        ProgramRunnerUtil.executeConfiguration(project, configuration, executor);
    }

    private RunnerAndConfigurationSettings createActiveRunnerConfiguration(AnActionEvent e) {
        ConfigurationContext context = ConfigurationContext.getFromContext(e.getDataContext());
        return context.getConfiguration();
    }
}
