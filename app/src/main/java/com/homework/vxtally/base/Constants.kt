package com.homework.vxtally.base

import android.os.Environment

const val KEY_TITLE = "KEY_TITLE"

const val KEY_IO_TYPE = "KEY_IO_TYPE"

val APP_ROOT_FILE_PATH = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_PICTURES
).absolutePath + "/VxTally"