package com.sgsoft.facerecognizer.ui.result

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sgsoft.facerecognizer.api.Face
import java.io.File

@BindingAdapter("bind_items")
fun setItems(view: RecyclerView, items: List<Face>) {
    val adapter = view.adapter as? ResultFragment.FaceAdapter
            ?: ResultFragment.FaceAdapter(view.resources).apply {
                view.adapter = this
            }
    adapter.items = items
    adapter.notifyDataSetChanged()
}

@BindingAdapter("bind_image")
fun setImage(view: ImageView, path: String) {
    Glide.with(view)
            .load(File(path))
            .into(view)
}