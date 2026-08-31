package tachiyomi.core.common.storage

import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import tachiyomi.core.common.i18n.stringResource
import tachiyomi.i18n.MR
import java.io.File

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AndroidStorageFolderProvider(
    private val context: Context,
) : FolderProvider {

    override fun directory(): File {
        // KOMA: Use app-specific external storage (scoped storage) instead of
        // Environment.getExternalStorageDirectory() which is blocked on modern Android.
        val dir = context.getExternalFilesDir(null)
        if (dir != null) {
            return File(dir, context.stringResource(MR.strings.app_name))
        }
        // Fallback to internal storage if external is unavailable
        return File(
            context.filesDir.absolutePath + File.separator +
                context.stringResource(MR.strings.app_name),
        )
    }

    override fun path(): String {
        return directory().toUri().toString()
    }
}
