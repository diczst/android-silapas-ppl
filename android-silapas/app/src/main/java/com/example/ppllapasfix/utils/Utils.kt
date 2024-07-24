package com.example.ppllapasfix.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.internal.Primitives
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type


fun <T> String?.toModel(classOfT: Class<T>): T? {
    if (this == null) return null
    val obj = Gson().fromJson<Any>(this, classOfT as Type)
    return Primitives.wrap(classOfT).cast(obj)!!
}

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

fun <T> Response<T>.getErrorBody(): ErrorResponse? {
    return try {
        this.errorBody()?.string()?.let { error ->
            Gson().fromJson<ErrorResponse>(error)
        }
    } catch (exception: Exception) {
        null
    }
}

fun <T, S> Response<T>.getErrorBody(classOfT: Class<S>): S? {
    return this.errorBody()?.string()?.let { error ->
        error.toModel(classOfT)
    }
}


data class ErrorResponse(
    val code: String? = null,
    val message: String? = null
)

fun String.dayToBahasaIndonesia(): String {
    return when (this) {
        "Sunday" -> "Minggu"
        "Monday" -> "Senin"
        "Tuesday" -> "Selasa"
        "Wednesday" -> "Rabu"
        "Thursday" -> "Kamis"
        "Friday" -> "Jum'at"
        "Saturday" -> "Sabtu"

        else -> {
            "Day name unknown"
        }
    }
}

fun getRootDirPath(context: Context): String? {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val file: File =
            ContextCompat.getExternalFilesDirs(context.getApplicationContext(), null).get(0)
        file.getAbsolutePath()
    } else {
        context.getApplicationContext().getFilesDir().getAbsolutePath()
    }
}

@SuppressLint("NewApi")
fun getPath(uri: Uri, context: Context): String? {
    var uri: Uri = uri
    val needToCheckUri = Build.VERSION.SDK_INT >= 19
    var selection: String? = null
    var selectionArgs: Array<String>? = null
    // Uri is different in versions after KITKAT (Android 4.4), we need to
    // deal with different Uris.
    if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            if (id.startsWith("raw:")) {
                return id.replaceFirst("raw:".toRegex(), "")
            }
            uri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            when (type) {
                "image" -> uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            selection = "_id=?"
            selectionArgs = arrayOf(
                split[1]
            )
        }
    }
    if ("content".equals(uri.getScheme(), ignoreCase = true)) {
        val projection = arrayOf(
            MediaStore.Images.Media.DATA
        )
        try {
            context.contentResolver.query(uri, projection, selection, selectionArgs, null)
                .use { cursor ->
                    if (cursor != null && cursor.moveToFirst()) {
                        val columnIndex =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        return cursor.getString(columnIndex)
                    }
                }
        } catch (e: java.lang.Exception) {
            Log.e("on getPath", "Exception", e)
        }
    } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
        return uri.getPath()
    }
    return null
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.getAuthority()
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.getAuthority()
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.getAuthority()
}
