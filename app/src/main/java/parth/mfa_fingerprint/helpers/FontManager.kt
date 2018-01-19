package parth.mfa_fingerprint.helpers

import android.content.Context
import android.graphics.Typeface

/**
 * Created by Parth Chandratreya on 19/01/2018.
 */
class FontManager {
    private val root: String = "fonts/"
    val fa = root + "fontAwesome.otf"

    fun getTypeface(context: Context, font: String) : Typeface {
        return Typeface.createFromAsset(context.assets, font)
    }
}