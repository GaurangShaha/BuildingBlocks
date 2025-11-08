package building.blocks.test.common.test

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

/**
 * [MainThreadTestListener] is a Kotest [TestListener] that configures the main dispatcher
 * for tests to use an [UnconfinedTestDispatcher] during the execution of a [Spec].
 *
 * This listener ensures that any code that uses [Dispatchers.Main] within the test
 * spec will be executed on an [UnconfinedTestDispatcher], allowing for immediate
 * execution of coroutines without the need for explicit yielding.
 *
 * **Functionality:**
 *
 * - `beforeSpec(spec: Spec)`:
 *    This function is invoked *before* the execution of any tests within a [Spec]. It sets [Dispatchers.Main] to
 *    an [UnconfinedTestDispatcher], initialized with a [TestCoroutineScheduler]. This effectively overrides
 *    the default main dispatcher for the duration of the test spec.
 *
 * - `afterSpec(spec: Spec)`:
 *    This function is invoked *after* the execution of all tests within a [Spec]. It resets [Dispatchers.Main] to
 *    its original value using [Dispatchers.resetMain()]. This is crucial to prevent interference with other tests or
 *    the application's normal operation outside of the test spec.
 */
@OptIn(ExperimentalCoroutinesApi::class)
public class MainThreadTestListener : TestListener {
    override suspend fun beforeSpec(spec: Spec) {
        Dispatchers.setMain(UnconfinedTestDispatcher(TestCoroutineScheduler()))
    }

    override suspend fun afterSpec(spec: Spec) {
        Dispatchers.resetMain()
    }
}
