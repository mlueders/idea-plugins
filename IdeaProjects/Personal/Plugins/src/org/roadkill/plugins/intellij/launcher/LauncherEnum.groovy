package org.roadkill.plugins.intellij.launcher

import javax.swing.Icon
import com.intellij.openapi.util.IconLoader

/**
 * User: mlueders
 * Date: 11/28/12
 */
enum LauncherEnum {

    FIRST("Launcher1"),
    SECOND("Launcher2"),
    THIRD("Launcher3"),
    FOURTH("Launcher4"),
    FIFTH("Launcher5")

    String id
    Icon boundIcon
    Icon unboundIcon

    LauncherEnum(String id) {
        this.id = id
        this.boundIcon = IconLoader.getIcon("/icons/${id}-bound.png")
        this.unboundIcon = IconLoader.getIcon("/icons/${id}-unbound.png")
    }

}
