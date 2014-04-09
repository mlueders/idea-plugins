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

		private Project project

        public ClearAction(Project project) {
            super("Clear")
			this.project = project
        }

        @Override
        void actionPerformed(AnActionEvent e) {
            clearConfiguration(project)
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
            BindableLauncherComboBoxAction.this.bindConfiguration(myProject, myConfiguration)
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
			// NOTE: Ideally, we would just create actions without having to provide the project.  Problem is,
			// all events generated from dynamically created actions seem to have the most recently opened project attached.
			// So, if multiple projects are opened, the run/debug/clear actions invoked in one project could refer to the
			// other project when the event is processed.  Sucks but understandable since actions are generally meant to
			// be instantiated via plugin.xml, not dynamically.  As a workaround, pass the project to the actions instead
			// of relying on the project associated with the event.
            actionGroup.add(new RunLaunchAction(launcher, project))
            actionGroup.add(new DebugLaunchAction(launcher, project))
            actionGroup.add(new ClearAction(project))

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

    protected void bindConfiguration(Project project, RunnerAndConfigurationSettings configuration) {
        LauncherState state = LauncherComponent.getState(project)
        state.setConfigurationName(launcher, configuration.name)
    }

    protected void clearConfiguration(Project project) {
        LauncherState state = LauncherComponent.getState(project)
        state.clearConfigurationName(launcher)
    }

}
