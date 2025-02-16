package android.artisan.ui.compositionlocal

import android.artisan.ui.component.shared.SharedUIController
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalSharedUIController: ProvidableCompositionLocal<SharedUIController> =
    staticCompositionLocalOf { error("SharedUIController is not provided") }
