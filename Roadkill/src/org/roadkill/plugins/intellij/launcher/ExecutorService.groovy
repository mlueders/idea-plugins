package org.roadkill.plugins.intellij.launcher

import com.intellij.execution.Executor
import com.intellij.execution.ExecutorRegistry
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.wm.ToolWindowId

/**
 * User: mlueders
 * Date: 11/29/12 11:12 AM
 */
interface ExecutorService {

    ExecutorService RUN = new ExecutorService() {
        @Override
        Executor getExecutor() {
            DefaultRunExecutor.getRunExecutorInstance()
        }
    }

    ExecutorService DEBUG = new ExecutorService() {
        @Override
        Executor getExecutor() {
            ExecutorRegistry.getInstance().getExecutorById(ToolWindowId.DEBUG)
        }
    }

    Executor getExecutor()
}
