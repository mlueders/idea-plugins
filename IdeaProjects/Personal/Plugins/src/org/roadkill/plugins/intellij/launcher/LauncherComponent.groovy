package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.RunManagerEx
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * User: mlueders
 * Date: 11/27/12
 */
public class LauncherComponent implements ProjectComponent {

    public static class LauncherState {

        private PropertiesComponent properties

        LauncherState(Project project) {
            properties = PropertiesComponent.getInstance(project)
        }

        private LauncherState(PropertiesComponent properties) {
            this.properties = properties
        }

        public String getConfigurationName(LauncherEnum launcher) {
            properties.getValue(launcher.id)
        }

        public void clearConfigurationName(LauncherEnum launcher) {
            setConfigurationName(launcher, null)
        }

        public void setConfigurationName(LauncherEnum launcher, @Nullable String configurationName) {
            properties.setValue(launcher.id, configurationName)
        }
    }

    public static LauncherState getState(Project project) {
        return get(project).getState()
    }

    public static LauncherComponent get(Project project) {
        return (LauncherComponent) project.getComponent(NAME)
    }

    public static final String NAME = "com.pfs.LauncherComponent"

    private LauncherState state
    private Project project

    public LauncherComponent(Project project) {
        this.project = project
        this.state = new LauncherState(project)
    }

    public LauncherState getState() {
        state
    }

    public RunnerAndConfigurationSettings getConfigurationForLauncher(LauncherEnum launcher) {
        String configurationName = state.getConfigurationName(launcher)
        (configurationName == null) ? null : getConfigurationWithName(configurationName)
    }

    private RunnerAndConfigurationSettings getConfigurationWithName(String name) {
        getLauncherConfigurations().find { RunnerAndConfigurationSettings configuration ->
            name == configuration.name
        }
    }

    public List<RunnerAndConfigurationSettings> getLauncherConfigurations() {
        List<RunnerAndConfigurationSettings> activeConfigurationList = new ArrayList<RunnerAndConfigurationSettings>()
        RunManagerEx runManager = getRunManager()

        for (ConfigurationType type: runManager.configurationFactories) {
            activeConfigurationList.addAll(runManager.getConfigurationSettings(type))
        }
        activeConfigurationList
    }

    public RunnerAndConfigurationSettings createRunnerConfigurationFromContextAndMakeActive(DataContext context) {
        ConfigurationContext configurationContext = ConfigurationContext.getFromContext(context)
        RunnerAndConfigurationSettings configuration = configurationContext.configuration

        RunManagerEx manager = getRunManager()
        manager.addConfiguration(configuration, false)
        manager.setSelectedConfiguration(configuration)
        configuration
    }

    private RunManagerEx getRunManager() {
        RunManagerEx.getInstanceEx(project)
    }

    @NotNull
    public String getComponentName() {
        NAME
    }

    public void initComponent() {}

    public void disposeComponent() {}

    public void projectOpened() {}

    public void projectClosed() {}

}
