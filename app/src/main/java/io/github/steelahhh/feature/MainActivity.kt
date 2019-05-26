package io.github.steelahhh.feature

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import io.github.steelahhh.R
import io.github.steelahhh.core.Navigator
import io.github.steelahhh.feature.character.search.SearchController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()

    private lateinit var router: Router

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        navigator.bind(router)

        navigator.setRoot(SearchController())
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
