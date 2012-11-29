package org.roadkill.plugins.intellij.launcher

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * User: mlueders
 * Date: 11/29/12 11:08 AM
 */
abstract class AbstractLauncherAction extends AnAction {

    protected abstract void actionPerformed(AnActionEvent e, LauncherComponent launcherComponent)

    protected LauncherEnum launcher;

    public AbstractLauncherAction(String text, LauncherEnum launcher) {
        super(text)
        this.launcher = launcher
    }

    @Override
    final void actionPerformed(AnActionEvent e) {
        LauncherComponent launcherComponent = LauncherComponent.get(e.project)
        actionPerformed(e, launcherComponent)
    }

}
