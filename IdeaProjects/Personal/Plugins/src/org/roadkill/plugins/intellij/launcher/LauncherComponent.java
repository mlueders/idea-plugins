package org.roadkill.plugins.intellij.launcher;

import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManagerEx;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: mlueders
 * Date: 11/27/12
 */
public class LauncherComponent implements ProjectComponent {

    public static class LauncherState {

        private PropertiesComponent properties;

        LauncherState(Project project) {
            properties = PropertiesComponent.getInstance(project);
        }

        private LauncherState(PropertiesComponent properties) {
            this.properties = properties;
        }

        public String getConfigurationName(LauncherEnum launcher) {
            return properties.getValue(launcher.getId());
        }

        public void clearConfigurationName(LauncherEnum launcher) {
            setConfigurationName(launcher, null);
        }

        public void setConfigurationName(LauncherEnum launcher, @Nullable String configurationName) {
            properties.setValue(launcher.getId(), configurationName);
        }
    }

    public static LauncherState getState(Project project) {
        return get(project).getState();
    }

    public static LauncherComponent get(Project project) {
        return (LauncherComponent) project.getComponent(NAME);
    }

    public static final String NAME = "com.pfs.LauncherComponent";

    public LauncherState state;
    private Project project;

    public LauncherComponent(Project project) {
        this.project = project;
        this.state = new LauncherState(project);
    }

    public LauncherState getState() {
        return state;
    }

    public void runBoundConfiguration(LauncherEnum launcher) {
        execConfiguration(launcher, ExecutorService.RUN);
    }

    public void debugBoundConfiguration(LauncherEnum launcher) {
        execConfiguration(launcher, ExecutorService.DEBUG);
    }

    public void execConfiguration(LauncherEnum launcher, ExecutorService executorService) {
        Executor executor = executorService.getExecutor();
        String configurationName = state.getConfigurationName(launcher);
        RunnerAndConfigurationSettings configuration = getConfigurationWithName(configurationName);

        if (configuration != null) {
            ProgramRunnerUtil.executeConfiguration(project, configuration, executor);
        }
    }

    public RunnerAndConfigurationSettings getConfigurationWithName(String name) {
        if (name != null) {
            for (RunnerAndConfigurationSettings configuration : getLauncherConfigurations()) {
                if (name.equals(configuration.getName())) {
                    return configuration;
                }
            }
        }
        return null;
    }

    public List<RunnerAndConfigurationSettings> getLauncherConfigurations() {
        List<RunnerAndConfigurationSettings> activeConfigurationList = new ArrayList<RunnerAndConfigurationSettings>();
        RunManagerEx runManager = RunManagerEx.getInstanceEx(project);
        ConfigurationType[] types = runManager.getConfigurationFactories();

        for (ConfigurationType type: types) {
            RunnerAndConfigurationSettings[] configurations = runManager.getConfigurationSettings(type);
            Collections.addAll(activeConfigurationList, configurations);
        }
        return activeConfigurationList;
    }

    @NotNull
    public String getComponentName() {
        return NAME;
    }

    public void initComponent() {}

    public void disposeComponent() {}

    public void projectOpened() {}

    public void projectClosed() {}

}
