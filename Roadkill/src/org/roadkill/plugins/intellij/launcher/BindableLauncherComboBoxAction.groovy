package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.RunManagerEx
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.openapi.project.IndexNotReadyException
import com.intellij.openapi.project.Project
import org.roadkill.plugins.intellij.launcher.LauncherComponent.LauncherState

import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

import com.intellij.openapi.actionSystem.*

/**
 * User: mlueders
 * Date: 11/25/12
 */
class BindableLauncherComboBoxAction extends ComboBoxAction {

    public static class First extends BindableLauncherComboBoxAction {
        public First() {super(LauncherEnum.FIRST)}
    }

    public static class Second extends BindableLauncherComboBoxAction {
        public Second() {super(LauncherEnum.SECOND)}
    }

    public static class Third extends BindableLauncherComboBoxAction {
        public Third() {super(LauncherEnum.THIRD)}
    }

    public static class Fourth extends BindableLauncherComboBoxAction {
        public Fourth() {super(LauncherEnum.FOURTH)}
    }

    public static class Fifth extends BindableLauncherComboBoxAction {
        public Fifth() {super(LauncherEnum.FIFTH)}
    }


    private class ClearAction extends AnAction {

        public ClearAction() {
            super("Clear")
        }

        @Override
        void actionPerformed(AnActionEvent e) {
            clearConfiguration(e)
        }
    }

    /**
     * MenuAction blatantly copied from com.intellij.execution.actions.RunConfigurationAction
     */
    private class MenuAction extends AnAction {
        private final RunnerAndConfigurationSettings myConfiguration
        private final Project myProject

        public MenuAction(final RunnerAndConfigurationSettings configuration, final Project project) {
            myConfiguration = configuration
            myProject = project
            String name = configuration.getName()
            if (name == null || name.length() == 0) {
                name = " "
            }
            final Presentation presentation = getTemplatePresentation()
            presentation.setText(name, false)
            presentation.setDescription("Start " + configuration.getType().getConfigurationTypeDescription() + " '" + name + "'")
            updateIcon(presentation)
        }

        private void updateIcon(final Presentation presentation) {
            setConfigurationIcon(presentation, myConfiguration, myProject)
        }

        private void setConfigurationIcon(final Presentation presentation, final RunnerAndConfigurationSettings settings, final Project project) {
            try {
                presentation.setIcon(RunManagerEx.getInstanceEx(project).getConfigurationIcon(settings))
            }
            catch (IndexNotReadyException ignored) {}
        }

        public void update(final AnActionEvent e) {
            super.update(e)
            updateIcon(e.getPresentation())
        }

        public void actionPerformed(final AnActionEvent e) {
            BindableLauncherComboBoxAction.this.bindConfiguration(e, myConfiguration)
        }
    }



    private LauncherEnum launcher

    public BindableLauncherComboBoxAction(LauncherEnum launcher) {
        this.launcher = launcher
    }

    @Override
    public JComponent createCustomComponent(final Presentation presentation) {
        JPanel panel = new JPanel(new BorderLayout())
        panel.border = BorderFactory.createEmptyBorder(0, 2, 0, 2)
        panel.opaque = false
        panel.add(createComboBoxButton(presentation))
        panel
    }

    @Override
    protected DefaultActionGroup createPopupActionGroup(JComponent button) {
        DefaultActionGroup actionGroup = new DefaultActionGroup()
        Project project = PlatformDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext(button))

        if (project != null) {
            actionGroup.add(new RunLaunchAction(launcher))
            actionGroup.add(new DebugLaunchAction(launcher))
            actionGroup.add(new ClearAction())

            DefaultActionGroup historyActionGroup = new DefaultActionGroup("Bind", true)
            List<RunnerAndConfigurationSettings> launcherConfigurations = LauncherComponent.get(project).launcherConfigurations
            for (RunnerAndConfigurationSettings configuration : launcherConfigurations) {
                MenuAction action = new MenuAction(configuration, project)
                historyActionGroup.add(action)
            }
            actionGroup.add(historyActionGroup)
        }
        return actionGroup
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e)

        if (e.project != null) {
            String configurationName = getConfigurationName(e.project)
            e.presentation.icon = (configurationName == null) ? launcher.unboundIcon : launcher.boundIcon
            e.presentation.description = (configurationName == null) ? "" : "Bound ${configurationName}"
        }
    }

    private String getConfigurationName(Project project) {
        LauncherState state = LauncherComponent.getState(project)
        state.getConfigurationName(launcher)
    }

    protected void bindConfiguration(AnActionEvent e, RunnerAndConfigurationSettings configuration) {
        LauncherState state = LauncherComponent.getState(e.project)
        state.setConfigurationName(launcher, configuration.name)
    }

    protected void clearConfiguration(AnActionEvent e) {
        LauncherState state = LauncherComponent.getState(e.project)
        state.clearConfigurationName(launcher)
    }

}
