package org.roadkill.plugins.intellij.launcher

/**
 * User: mlueders
 * Date: 11/27/12
 */
class DebugLaunchAction extends AbstractLauncherAction {

    public static class First extends DebugLaunchAction {
        public First() {
            super(LauncherEnum.FIRST)
        }
    }

    public static class Second extends DebugLaunchAction {
        public Second() {
            super(LauncherEnum.SECOND)
        }
    }

    public static class Third extends DebugLaunchAction {
        public Third() {
            super(LauncherEnum.THIRD)
        }
    }

    public static class Fourth extends DebugLaunchAction {
        public Fourth() {
            super(LauncherEnum.FOURTH)
        }
    }

    public static class Fifth extends DebugLaunchAction {
        public Fifth() {
            super(LauncherEnum.FIFTH)
        }
    }


    public DebugLaunchAction(LauncherEnum launcher) {
        super("Debug", launcher, ExecutorService.DEBUG)
    }

}
