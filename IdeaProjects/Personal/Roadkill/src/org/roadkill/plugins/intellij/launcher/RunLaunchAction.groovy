package org.roadkill.plugins.intellij.launcher

/**
 * User: mlueders
 * Date: 11/28/12
 */
class RunLaunchAction extends AbstractLauncherAction {

    public static class First extends RunLaunchAction {
        public First() {super(LauncherEnum.FIRST)}
    }

    public static class Second extends RunLaunchAction {
        public Second() {super(LauncherEnum.SECOND)}
    }

    public static class Third extends RunLaunchAction {
        public Third() {super(LauncherEnum.THIRD)}
    }

    public static class Fourth extends RunLaunchAction {
        public Fourth() {super(LauncherEnum.FOURTH)}
    }

    public static class Fifth extends RunLaunchAction {
        public Fifth() {super(LauncherEnum.FIFTH)}
    }


    public RunLaunchAction(LauncherEnum launcher) {
        super("Run", launcher, ExecutorService.RUN)
    }

}
