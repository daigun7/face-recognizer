package com.sgsoft.facerecognizer.ui.result

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sgsoft.facerecognizer.R
import com.sgsoft.facerecognizer.api.CFRModel
import com.sgsoft.facerecognizer.api.Face
import com.sgsoft.facerecognizer.databinding.FragmentResultBinding
import com.sgsoft.facerecognizer.databinding.ItemCelebrityFaceBinding
import com.sgsoft.facerecognizer.databinding.ItemFaceBinding

class ResultFragment : AppCompatDialogFragment() {
    private  lateinit var mModel: CFRModel
    private  lateinit var mFilePath: String

    companion object {
        const val ARGUMENT_MODEL = "model"
        const val ARGUMENT_FILE_PATH = "file_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.AppTheme)

        arguments?.also {
            mModel = it.getParcelable(ARGUMENT_MODEL) as CFRModel
            mFilePath = it.getString(ARGUMENT_FILE_PATH) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_result, container, false)

        val viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        viewModel.clickClose.observe(this, Observer { dismiss() })
        viewModel.setImage(mFilePath)
        viewModel.setItems(mModel.faces)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    class FaceAdapter(private val resources: Resources) : RecyclerView.Adapter<BindingViewHolder>() {
        var items: List<Face> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return if(viewType > 0) {
                val binding: ItemCelebrityFaceBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_celebrity_face, parent, false)

                BindingViewHolder(binding)

            } else {
                val binding: ItemFaceBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_face, parent, false)

                BindingViewHolder(binding)
            }
        }

        override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
            var item = items[position]

            when(holder.binding) {
                is ItemFaceBinding -> {
                    holder.binding.item = com.sgsoft.facerecognizer.model.Face.from(resources, item)
                }

                is ItemCelebrityFaceBinding -> {
                    holder.binding.item = com.sgsoft.facerecognizer.model.Face.from(resources, item)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if(items[position].celebrity != null) BindingViewType.CelebrityFaceView.ordinal
                   else BindingViewType.FaceView.ordinal
        }

        override fun getItemCount(): Int = items.size
    }

    open class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    enum class BindingViewType { FaceView, CelebrityFaceView }
}