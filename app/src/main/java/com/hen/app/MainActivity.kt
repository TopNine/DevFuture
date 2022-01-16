package com.hen.app

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.app.R
import com.hen.core.utils.CacheUtils
import com.hen.app.widget.CodeView
import android.content.Intent
import android.view.View
import android.widget.Button
import com.hen.app.entity.User
import com.hen.core.http.HttpClient
import com.hen.core.utils.Utils
import com.hen.lesson.LessonActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.jvm.JvmStatic

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"
    private lateinit var usernameView: EditText
    private lateinit var passwordView: EditText
    private lateinit var editCodeView: EditText

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usernameView = findViewById(R.id.et_username)
        passwordView = findViewById(R.id.et_password)
        editCodeView = findViewById(R.id.et_code)
        usernameView.setText(CacheUtils.get(usernameKey))
        passwordView.setText(CacheUtils.get(passwordKey))

        val loginBtn = findViewById<Button>(R.id.btn_login)
        val imgCodeView = findViewById<CodeView>(R.id.code_view)
        loginBtn.setOnClickListener(this)
        imgCodeView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            is CodeView -> v.updateCode()
            is Button -> login()
        }
    }

    private fun login() {
        val username = usernameView.text.toString()
        val password = passwordView.text.toString()
        val code = editCodeView.text.toString()
        val user = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {
        if (user.username?.length ?: 0 < 4) {
            Utils.toast("用户名不合法")
            return false
        }
        if (user.password?.length ?: 0 < 4) {
            Utils.toast("密码不合法")
            return false
        }
        return true
    }
}