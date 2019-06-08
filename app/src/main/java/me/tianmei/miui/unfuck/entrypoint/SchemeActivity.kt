package me.tianmei.miui.unfuck.entrypoint;

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import me.tianmei.miui.unfuck.settings.UserPreferences

class SchemeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SchemeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            parseIntent()
        } catch (e: Exception) {
            Log.e(TAG, "fail parse intent", e)
        }

        finish()
    }

    private fun parseIntent() {
        val data: Uri? = intent?.data?.normalizeScheme()
        Log.d(TAG, "data: $data")

        if (data != null) {
            Log.d(TAG, "fragment: ${data.fragment}")
            val queryWord = getQueryWord(data)
            Log.d(TAG, "query word: $queryWord")

            if (queryWord != null) {
                handleQueryWord(queryWord)
            }
        }
    }

    private fun getQueryWord(data: Uri): String? {
        // https://search.browser.miui.com/v5/#page=browser-search&q=%E5%AE%8F%E7%8A%B6%E5%85%83&appendIframe=baidu&iframeFrom=1002253t
        return data.fragment?.splitToSequence("&")?.first { it.startsWith("q=") }?.substringAfter("=")
    }

    private fun handleQueryWord(queryWord: String) {
        val searchEngine = getSharedPreferences(UserPreferences.PREFERENCE_NAME, Context.MODE_PRIVATE).getString(
            "pref_search_engine",
            "web_search"
        )
        Log.d(TAG, "selected search engine: $searchEngine")

        when (searchEngine) {
            "web_search" -> {
                val intent = Intent(Intent.ACTION_WEB_SEARCH)
                intent.putExtra(SearchManager.QUERY, queryWord)
                startActivity(intent)
            }

            "google.com" -> {
                val url = Uri.parse("https://www.google.com/search?q=" + Uri.encode(queryWord))
                Log.d(TAG, "url = $url")
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }

            "bing.com" -> {
                val url = Uri.parse("https://www.bing.com/search?q=" + Uri.encode(queryWord))
                Log.d(TAG, "url = $url")
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }

            "baidu.com" -> {
                val url = Uri.parse("https://www.baidu.com/s?wd=" + Uri.encode(queryWord))
                Log.d(TAG, "url = $url")
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }

            else -> {
                Log.e(TAG, "unsupported choice: $searchEngine")
            }
        }
    }
}
