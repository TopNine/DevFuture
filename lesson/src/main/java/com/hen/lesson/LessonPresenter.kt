package com.hen.lesson

import com.google.gson.reflect.TypeToken
import com.hen.core.http.EntityCallback
import com.hen.core.http.HttpClient.get
import com.hen.core.utils.Utils.toast
import com.hen.lesson.entity.Lesson
import java.util.*

class LessonPresenter(val activity: LessonActivity) {
    private var lessons: List<Lesson> = ArrayList()
    private val type = object : TypeToken<List<Lesson>>() {}.type

    fun fetchData() {
        get(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(entity: List<Lesson>) {
                lessons = entity
                activity.runOnUiThread { activity.showResult(entity) }
            }

            override fun onFailure(message: String?) {
                activity.runOnUiThread { toast(message) }
            }
        })
    }

    fun showPlayback() {
        val playbackLessons: MutableList<Lesson> = ArrayList()
        for (lesson in lessons) {
            if (lesson.state === Lesson.State.PLAYBACK) {
                playbackLessons += lesson
            }
        }
        activity.showResult(playbackLessons)
    }

    companion object {
        const val LESSON_PATH = "lessons"
    }
}