package me.tianmei.miui.unfuck.entrypoint

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.tianmei.miui.unfuck.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
