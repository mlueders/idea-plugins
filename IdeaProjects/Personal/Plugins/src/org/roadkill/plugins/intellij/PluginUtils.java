package org.roadkill.plugins.intellij;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.awt.event.ActionEvent;

/**
 * User: mike
 * Created: 1/2/12 8:58 PM
 */
public class PluginUtils {

    public static Editor getEditor(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        return PlatformDataKeys.EDITOR.getData(dataContext);
    }

    public static Project getProject(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        return PlatformDataKeys.PROJECT.getData(dataContext);
    }

}
