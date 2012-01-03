package org.roadkill.plugins.intellij;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

/**
 * User: mike
 * Created: 12/28/11 4:41 PM
 */
public class ShowInProjectViewAction extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        Editor editor = PluginUtils.getEditor(event);
        Project project = PluginUtils.getProject(event);

        if (project != null && editor != null) {
            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());

            if (psiFile != null) {
                ProjectView projectView = ProjectView.getInstance(project);
                projectView.select(psiFile, psiFile.getVirtualFile(), true);
            }
        }
    }
}
