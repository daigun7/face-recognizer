package com.sgsoft.facerecognizer.ui.tab.celebrity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sgsoft.facerecognizer.R
import com.sgsoft.facerecognizer.common.fragment.BaseFragment
import com.sgsoft.facerecognizer.databinding.FragmentCelebrityFaceBinding
import com.sgsoft.facerecognizer.extension.createTempFile
import com.sgsoft.facerecognizer.extension.getRealPath
import com.sgsoft.facerecognizer.model.FaceEntity
import com.sgsoft.facerecognizer.ui.result.ResultFragment
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject

class CelebrityFaceTabFragment : BaseFragment() {
    @Inject
    lateinit var mViewModelFactory: CelebrityFaceTabViewModelFactory

    private lateinit var mViewModel: CelebrityFaceTabViewModel
    private var mImageFilePath: String? = null

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_PICK = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCelebrityFaceBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_celebrity_face, container, false)

        mViewModel = ViewModelProviders.of(
                this, mViewModelFactory).get(CelebrityFaceTabViewModel::class.java)

        mViewModel.clickCamera.observe(this, Observer { dispatchImageCaptureIntent() })
        mViewModel.clickAlbum.observe(this, Observer { dispatchPickIntent() })
        mViewModel.faceList.observe(this, Observer { onFaceRecognized(it) })
        mViewModel.error.observe(this, Observer {  })
        mViewModel.loading.observe(this, Observer {
            if(it) {
                showProgress()
            } else {
                hideProgress()
            }
        })

        binding.viewModel = mViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val context = activity?.applicationContext ?: return

        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    mImageFilePath?.also {
                        mViewModel.getCelebrityFaces(File(it))
                    }
                }
                REQUEST_PICK -> {
                    data?.data?.getRealPath(context.contentResolver)?.also {
                        mImageFilePath = it
                        mViewModel.getCelebrityFaces(File(it))
                    }
                }
            }
        }
    }

    private fun onFaceRecognized(faces: List<FaceEntity>) {
        if(faces.isEmpty()) {
            showMessage(R.string.no_matches_found)
            return
        }

        val fragment = ResultFragment()

        fragment.arguments = Bundle().apply {
            putParcelableArrayList(ResultFragment.ARGUMENT_FACES, faces as ArrayList<FaceEntity>)
            putString(ResultFragment.ARGUMENT_IMAGE, mImageFilePath)
        }

        fragmentManager?.let {
            fragment.show(it, ResultFragment::class.java.name)
        }
    }

    private fun dispatchImageCaptureIntent() {
        val context = activity?.applicationContext ?: return

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(context.packageManager)?.also {
                val tempFile = try {
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.createTempFile()

                } catch (ex: IOException) {
                    null
                }

                tempFile?.also { file ->
                    mImageFilePath = file.absolutePath

                    FileProvider.getUriForFile(context, context.packageName, file).also {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, it)
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    private fun dispatchPickIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_PICK)
    }
}