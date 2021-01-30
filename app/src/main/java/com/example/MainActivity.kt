package com.example

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.statepage.StatePageManager
import com.statepage.simple.ReloadingStatePage

class MainActivity : AppCompatActivity() {

    private lateinit var statePage: ReloadingStatePage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            StatePageManager.show<ReloadingStatePage>(statePage)

//            Handler().postDelayed(
//                    Runnable {
//                        StatePageManager.dismiss<ReloadingStatePage>(statePage)
//                    }, 5000
//            )
        }
        val content_main = findViewById<View>(R.id.content_main)
        val linearLayout = findViewById<View>(R.id.linearLayout)

        statePage = ReloadingStatePage(this) {
            Snackbar.make(statePage.toViewGroup(), "Reloading", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            StatePageManager.dismiss<ReloadingStatePage>(statePage)
        }

        StatePageManager.bind(this, statePage)
//        StatePageManager.bind(content_main, statePage)
//        StatePageManager.bind(fab, statePage)
//        StatePageManager.bind(linearLayout, statePage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}