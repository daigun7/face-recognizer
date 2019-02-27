package com.sgsoft.facerecognizer.ui.result

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
import com.sgsoft.facerecognizer.BR
import com.sgsoft.facerecognizer.R
import com.sgsoft.facerecognizer.databinding.FragmentResultBinding
import com.sgsoft.facerecognizer.databinding.ItemCelebrityFaceBinding
import com.sgsoft.facerecognizer.databinding.ItemFaceBinding
import com.sgsoft.facerecognizer.model.FaceEntity

class ResultFragment : AppCompatDialogFragment() {
    private var mFaces: List<FaceEntity>? = null
    private var mImage: String? = null

    companion object {
        const val ARGUMENT_FACES = "faces"
        const val ARGUMENT_IMAGE = "image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.AppTheme)

        arguments?.also {
            mFaces = it.getParcelableArrayList(ARGUMENT_FACES)
            mImage = it.getString(ARGUMENT_IMAGE) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_result, container, false)

        val viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        mImage?.let {
            viewModel.setImage(it)
        }

        mFaces?.let {
            viewModel.setItems(it)
        }

        viewModel.clickClose.observe(this, Observer { dismiss() })

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    class FaceAdapter : RecyclerView.Adapter<BindingViewHolder>() {
        var items: List<FaceEntity> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
            return if(viewType == BindingViewType.CelebrityFaceView.value) {
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
            val item = items[position]

            holder.binding.setVariable(BR.item, item)
            holder.binding.executePendingBindings()
        }

        override fun getItemViewType(position: Int): Int {
            return if(items[position].celebrity != null)
                BindingViewType.CelebrityFaceView.value else BindingViewType.FaceView.value
        }

        override fun getItemCount(): Int = items.size
    }

    open class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    enum class BindingViewType(val value: Int) { FaceView(0), CelebrityFaceView(1) }
}